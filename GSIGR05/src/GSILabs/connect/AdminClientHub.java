package GSILabs.connect;

import Dominio.BModel.Bar;
import Dominio.BModel.Restaurante;
import Dominio.IBModelo.Local;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.io.*;
import java.net.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.*;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
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
        JFrame frame = new JFrame("Mapa de locales (Ruta Real)");
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // --- 1. CONFIGURACIÓN DEL MAPA ---
        TileFactoryInfo info = new TileFactoryInfo(0, 19, 19, 256, true, true,
                "https://tile.openstreetmap.org", "x", "y", "z") {
            @Override
            public String getTileUrl(int x, int y, int zoom) {
                int invZoom = getTotalMapZoom() - zoom;
                return baseURL + "/" + invZoom + "/" + x + "/" + y + ".png";
            }
        };
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        JXMapViewer mapViewer = new JXMapViewer();
        mapViewer.setTileFactory(tileFactory);

        // --- 2. WAYPOINTS (PINES) INICIALES ---
        Set<Waypoint> waypoints = new HashSet<>();
        for (GeoPosition pos : locales.values()) {
            waypoints.add(new DefaultWaypoint(pos));
        }
        
        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<>();
        waypointPainter.setWaypoints(waypoints);
        mapViewer.setOverlayPainter(waypointPainter);

        // --- 3. PANEL SUPERIOR ---
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTop.setBackground(new Color(0, 0, 0, 120));
        panelTop.setOpaque(true);

        // Combo de locales
        JComboBox<String> comboLocales = new JComboBox<>(locales.keySet().toArray(new String[0]));
        comboLocales.setPreferredSize(new Dimension(200, 30));
        
        JLabel lblSel = new JLabel("Centrar en:");
        lblSel.setForeground(Color.WHITE);
        panelTop.add(lblSel);
        panelTop.add(comboLocales);

        comboLocales.addActionListener(e -> {
            String seleccionado = (String) comboLocales.getSelectedItem();
            if (seleccionado != null) {
                mapViewer.setAddressLocation(locales.get(seleccionado));
            }
        });

        // --- 4. BOTÓN DE RUTA REAL ---
        JButton btnRuta = new JButton("Crear Ruta Real (OSRM)");
        btnRuta.setBackground(Color.ORANGE);
        
        btnRuta.addActionListener(e -> {
            // A) Selección de locales
            String[] nombres = locales.keySet().toArray(new String[0]);
            JList<String> listSelector = new JList<>(nombres);
            listSelector.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            int result = JOptionPane.showConfirmDialog(
                frame, 
                new JScrollPane(listSelector), 
                "Selecciona los locales (Ctrl+Click)", 
                JOptionPane.OK_CANCEL_OPTION
            );

            if (result == JOptionPane.OK_OPTION) {
                List<String> seleccionados = listSelector.getSelectedValuesList();

                if (seleccionados.size() < 2) {
                    JOptionPane.showMessageDialog(frame, "Selecciona al menos 2 locales.");
                    return;
                }

                // Deshabilitar botón mientras carga
                btnRuta.setEnabled(false);
                btnRuta.setText("Calculando ruta...");

                // B) Obtener puntos y ordenar por vecino más cercano
                List<GeoPosition> puntosSeleccionados = new ArrayList<>();
                for (String nombre : seleccionados) {
                    puntosSeleccionados.add(locales.get(nombre));
                }

                GeoPosition inicio = puntosSeleccionados.get(0);
                List<GeoPosition> paradasOrdenadas = calcularRutaVecinoMasCercano(inicio, puntosSeleccionados);

                // C) Ejecutar cálculo de ruta OSRM en un hilo separado (para no congelar la GUI)
                new Thread(() -> {
                    List<GeoPosition> rutaRealCompleta = new ArrayList<>();
                    
                    try {
                        // Iterar entre pares de puntos (A->B, B->C...)
                        for (int i = 0; i < paradasOrdenadas.size() - 1; i++) {
                            GeoPosition p1 = paradasOrdenadas.get(i);
                            GeoPosition p2 = paradasOrdenadas.get(i + 1);
                            
                            // Llamada a la API de OSRM
                            List<GeoPosition> segmento = obtenerRutaOSRM(p1, p2);
                            rutaRealCompleta.addAll(segmento);
                            
                            // Pequeña pausa para respetar límites de la API pública
                            Thread.sleep(250);
                        }

                        // D) Actualizar el mapa en el hilo de Swing
                        SwingUtilities.invokeLater(() -> {
                            // Pintor de ruta (Línea roja siguiendo calles)
                            RoutePainter routePainter = new RoutePainter(rutaRealCompleta);
                            
                            // Pintor de Waypoints (Solo los seleccionados en la ruta)
                            Set<Waypoint> routeWaypoints = new HashSet<>();
                            for(GeoPosition p : paradasOrdenadas) {
                                routeWaypoints.add(new DefaultWaypoint(p));
                            }
                            WaypointPainter<Waypoint> wp = new WaypointPainter<>();
                            wp.setWaypoints(routeWaypoints);

                            // Combinar pintores
                            List<Painter<JXMapViewer>> painters = new ArrayList<>();
                            painters.add(routePainter); // Primero la línea
                            painters.add(wp);           // Encima los pines
                            
                            CompoundPainter<JXMapViewer> compoundPainter = new CompoundPainter<>(painters);
                            mapViewer.setOverlayPainter(compoundPainter);
                            
                            // Zoom automático
                            mapViewer.zoomToBestFit(new HashSet<>(paradasOrdenadas), 0.7);
                            
                            // Restaurar botón
                            btnRuta.setText("Crear Ruta Real (OSRM)");
                            btnRuta.setEnabled(true);
                        });

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(frame, "Error calculando la ruta: " + ex.getMessage());
                            btnRuta.setText("Crear Ruta Real (OSRM)");
                            btnRuta.setEnabled(true);
                        });
                    }
                }).start();
            }
        });
        
        panelTop.add(btnRuta);

        // --- 5. CONTROLES DE ZOOM ---
        JButton btnZoomIn = new JButton("+");
        JButton btnZoomOut = new JButton("-");

        btnZoomIn.addActionListener(e -> {
            int zoom = mapViewer.getZoom();
            if (zoom > 0) mapViewer.setZoom(zoom - 1);
        });

        btnZoomOut.addActionListener(e -> {
            int zoom = mapViewer.getZoom();
            if (zoom < mapViewer.getTileFactory().getInfo().getMaximumZoomLevel())
                mapViewer.setZoom(zoom + 1);
        });

        panelTop.add(btnZoomIn);
        panelTop.add(btnZoomOut);

        frame.add(panelTop, BorderLayout.NORTH);
        frame.add(mapViewer, BorderLayout.CENTER);

        // --- 6. LISTENER DE RATÓN ---
        MouseInputListener mia = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mia);
        mapViewer.addMouseMotionListener(mia);
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapViewer));

        frame.setVisible(true);

        if (!locales.isEmpty()) {
            String primero = locales.keySet().iterator().next();
            mapViewer.setAddressLocation(locales.get(primero));
            mapViewer.setZoom(5);
        }
    }

    // --- NUEVO MÉTODO: Conexión a OSRM para obtener coordenadas de calles ---
    private static List<GeoPosition> obtenerRutaOSRM(GeoPosition inicio, GeoPosition fin) {
        List<GeoPosition> rutaSegmento = new ArrayList<>();
        try {
            // Locale.US es IMPORTANTE para que use puntos en decimales (lat 40.5 en vez de 40,5)
            String urlStr = String.format(Locale.US, 
                    "http://router.project-osrm.org/route/v1/driving/%f,%f;%f,%f?overview=full&geometries=geojson",
                    inicio.getLongitude(), inicio.getLatitude(),
                    fin.getLongitude(), fin.getLatitude());

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "JavaMapApp/1.0"); // Identificación básica

            if (conn.getResponseCode() != 200) {
                System.err.println("Error HTTP OSRM: " + conn.getResponseCode());
                return rutaSegmento;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) response.append(line);
            in.close();

            // Parsing JSON
            JSONObject json = new JSONObject(response.toString());
            JSONArray routes = json.getJSONArray("routes");
            
            if (routes.length() > 0) {
                // "geometry" contiene el array de puntos del trazado
                JSONObject geometry = routes.getJSONObject(0).getJSONObject("geometry");
                JSONArray coordinates = geometry.getJSONArray("coordinates");

                for (int i = 0; i < coordinates.length(); i++) {
                    JSONArray coord = coordinates.getJSONArray(i);
                    // OSRM devuelve [lon, lat], Java espera (lat, lon)
                    double lon = coord.getDouble(0);
                    double lat = coord.getDouble(1);
                    rutaSegmento.add(new GeoPosition(lat, lon));
                }
            }

        } catch (Exception e) {
            System.err.println("Error obteniendo ruta OSRM");
            e.printStackTrace();
        }
        return rutaSegmento;
    }

    // --- Geocodificación (Nominatim) ---
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
    
    // --- Algoritmo Vecino más cercano (Orden lógico de visita) ---
    private static List<GeoPosition> calcularRutaVecinoMasCercano(GeoPosition inicio, List<GeoPosition> destinos) {
        List<GeoPosition> ruta = new ArrayList<>();
        List<GeoPosition> pendientes = new ArrayList<>(destinos);
        
        // Aseguramos que inicio esté en la lista y sea el primero
        if(!pendientes.contains(inicio)) pendientes.add(inicio);
        
        // Empezamos por el inicio y lo quitamos de pendientes
        ruta.add(inicio);
        pendientes.remove(inicio);
        
        GeoPosition actual = inicio;
        
        while (!pendientes.isEmpty()) {
            GeoPosition masCercano = null;
            double distMinima = Double.MAX_VALUE;
            
            for (GeoPosition candidato : pendientes) {
                double dist = getDistancia(actual, candidato);
                if (dist < distMinima) {
                    distMinima = dist;
                    masCercano = candidato;
                }
            }
            
            ruta.add(masCercano);
            pendientes.remove(masCercano);
            actual = masCercano;
        }
        return ruta;
    }
    
    // Distancia simple para calcular el orden
    private static double getDistancia(GeoPosition a, GeoPosition b) {
        double lat = a.getLatitude() - b.getLatitude();
        double lon = a.getLongitude() - b.getLongitude();
        return Math.sqrt(lat*lat + lon*lon);
    }

    // --- Pintor de rutas (Línea roja) ---
    public static class RoutePainter implements Painter<JXMapViewer> {
        private final List<GeoPosition> track;

        public RoutePainter(List<GeoPosition> track) {
            this.track = track;
        }

        @Override
        public void paint(Graphics2D g, JXMapViewer map, int w, int h) {
            g = (Graphics2D) g.create();
            
            Rectangle rect = map.getViewportBounds();
            g.translate(-rect.x, -rect.y);

            g.setColor(new Color(255, 0, 0, 200)); // Rojo semi-transparente
            g.setStroke(new BasicStroke(5));       // Grosor 5
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int lastX = -1;
            int lastY = -1;

            for (GeoPosition gp : track) {
                Point2D pt = map.getTileFactory().geoToPixel(gp, map.getZoom());

                if (lastX != -1 && lastY != -1) {
                    g.drawLine(lastX, lastY, (int) pt.getX(), (int) pt.getY());
                }

                lastX = (int) pt.getX();
                lastY = (int) pt.getY();
            }
            g.dispose();
        }
    }
}