package GSILabs.connect;

import Dominio.BModel.Bar;
import Dominio.BModel.Restaurante;
import Dominio.IBModelo.Local;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import javax.swing.*;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class AdminClientHub {

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);

        System.out.print("Host del servidor: ");
        String host = sc.nextLine();

        System.out.print("Puerto del servidor: ");
        int port = Integer.parseInt(sc.nextLine());

        System.out.print("Tag del objeto remoto (AdminGateway o ClientGateway): ");
        String tag = sc.nextLine();

        Map<String, GeoPosition> localesMapa = new LinkedHashMap<>();

        if (tag.equalsIgnoreCase("AdminGateway")) {
            while (true) {
                try {
                    Registry registry = LocateRegistry.getRegistry(host, port);
                    AdminGateway gateway = (AdminGateway) registry.lookup(tag);
                    LocalFinder finder = (LocalFinder) gateway;
                    Local[] locales = finder.getLocals("");

                    if (locales != null && locales.length > 0) {
                        for (Local l : locales) {
                            GeoPosition pos = geocodificar(
                                    l.getDireccion().getLocalidad(),
                                    l.getDireccion().getProvincia(),
                                    l.getDireccion().getCalle(),
                                    String.valueOf(l.getDireccion().getNumero())
                            );
                            if (pos != null) localesMapa.put(l.getNombre(), pos);
                        }
                    }
                    break;
                } catch (Exception e) {
                    System.err.println("Error conectando con el servidor RMI. Reintentando...");
                    Thread.sleep(2000);
                }
            }
        } else if (tag.equalsIgnoreCase("ClientGateway")) {
            while (true) {
                try {
                    Registry registry = LocateRegistry.getRegistry(host, port);
                    ClientGateway gateway = (ClientGateway) registry.lookup(tag);

                    Bar mejor = gateway.mejorBar("Bilbao");
                    if (mejor != null) {
                        GeoPosition pos = geocodificar(
                                mejor.getDireccion().getLocalidad(),
                                mejor.getDireccion().getProvincia(),
                                mejor.getDireccion().getCalle(),
                                String.valueOf(mejor.getDireccion().getNumero())
                        );
                        if (pos != null) localesMapa.put(mejor.getNombre(), pos);
                    }

                    Restaurante[] top = gateway.mejoresRestaurantes("Bilbao", 5);
                    for (Restaurante r : top) {
                        if (r != null) {
                            GeoPosition pos = geocodificar(
                                    r.getDireccion().getLocalidad(),
                                    r.getDireccion().getProvincia(),
                                    r.getDireccion().getCalle(),
                                    String.valueOf(r.getDireccion().getNumero())
                            );
                            if (pos != null) localesMapa.put(r.getNombre(), pos);
                        }
                    }
                    break;
                } catch (Exception e) {
                    System.err.println("Error conectando con el servidor RMI. Reintentando...");
                    Thread.sleep(2000);
                }
            }
        } else {
            System.out.println("Tag erróneo");
            System.exit(0);
        }

        SwingUtilities.invokeLater(() -> crearMapa(localesMapa));
    }

    private static void crearMapa(Map<String, GeoPosition> locales) {
        JFrame frame = new JFrame("Mapa de locales");
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // TileFactory HTTPS
        TileFactoryInfo info = new TileFactoryInfo(0, 17, 17, 256, true, true,
                "https://tile.openstreetmap.org", "x", "y", "z") {
            @Override
            public String getTileUrl(int x, int y, int zoom) {
                int invZoom = getTotalMapZoom() - zoom;
                return baseURL + "/" + invZoom + "/" + x + "/" + y + ".png";
            }
        };
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);

        // Mapa
        JXMapViewer mapViewer = new JXMapViewer();
        mapViewer.setTileFactory(tileFactory);

        // Waypoints
        Set<Waypoint> waypoints = new HashSet<>();
        for (GeoPosition pos : locales.values()) {
            waypoints.add(new DefaultWaypoint(pos));
        }
        WaypointPainter<Waypoint> painter = new WaypointPainter<>();
        painter.setWaypoints(waypoints);
        mapViewer.setOverlayPainter(painter);

        // Panel superior
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTop.setBackground(new Color(0, 0, 0, 120));
        panelTop.setOpaque(true);

        // Combo de locales
        JComboBox<String> comboLocales = new JComboBox<>(locales.keySet().toArray(new String[0]));
        comboLocales.setPreferredSize(new Dimension(200, 30));
        panelTop.add(new JLabel("Selecciona local:"));
        panelTop.add(comboLocales);

        comboLocales.addActionListener(e -> {
            String seleccionado = (String) comboLocales.getSelectedItem();
            if (seleccionado != null) {
                mapViewer.setAddressLocation(locales.get(seleccionado));
            }
        });

        // Botones de zoom
        JButton btnZoomIn = new JButton("+");
        JButton btnZoomOut = new JButton("-");

        btnZoomIn.addActionListener(e -> {
            int zoom = mapViewer.getZoom();
            if (zoom > 0) mapViewer.setZoom(zoom - 1); // Acercar
        });

        btnZoomOut.addActionListener(e -> {
            int zoom = mapViewer.getZoom();
            if (zoom < mapViewer.getTileFactory().getInfo().getMaximumZoomLevel())
                mapViewer.setZoom(zoom + 1); // Alejar
        });

        panelTop.add(btnZoomIn);
        panelTop.add(btnZoomOut);

        frame.add(panelTop, BorderLayout.NORTH);
        frame.add(mapViewer, BorderLayout.CENTER);
        frame.setVisible(true);

        // Centrar mapa en el primer local
        if (!locales.isEmpty()) {
            String primero = locales.keySet().iterator().next();
            mapViewer.setAddressLocation(locales.get(primero));
            mapViewer.setZoom(5); // Valor inicial
        }
    }

    private static GeoPosition geocodificar(String ciudad, String provincia, String calle, String numero) {
        try {
            String direccion = URLEncoder.encode(calle + " " + numero + ", " + ciudad + ", " + provincia, "UTF-8");
            String urlStr = "https://nominatim.openstreetmap.org/search?q=" + direccion + "&format=json&limit=1";
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", "JavaGeocoder");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) response.append(line);
            in.close();

            JSONArray resultados = new JSONArray(response.toString());
            if (resultados.length() > 0) {
                JSONObject obj = resultados.getJSONObject(0);
                double lat = obj.getDouble("lat");
                double lon = obj.getDouble("lon");
                return new GeoPosition(lat, lon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
