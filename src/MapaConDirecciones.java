import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;
import org.jxmapviewer.viewer.DefaultWaypoint;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class MapaConDirecciones extends JFrame {

    private JXMapViewer mapViewer;
    private Map<String, GeoPosition> direcciones;
    private Set<Waypoint> waypoints;
    private WaypointPainter<Waypoint> waypointPainter;

    public MapaConDirecciones() {
        setTitle("Mapa con direcciones y combo (HTTPS)");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- TileFactory HTTPS ---
        TileFactoryInfo info = new TileFactoryInfo(0, 17, 17,
                256, true, true,
                "https://tile.openstreetmap.org",
                "x", "y", "z") {
            @Override
            public String getTileUrl(int x, int y, int zoom) {
                int invZoom = getTotalMapZoom() - zoom;
                return baseURL + "/" + invZoom + "/" + x + "/" + y + ".png";
            }
        };
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);

        // --- Mapa ---
        mapViewer = new JXMapViewer();
        mapViewer.setTileFactory(tileFactory);
        mapViewer.setZoom(3);

        // --- Lista de direcciones ---
        // Formato: nombre visible, ciudad, provincia, calle, número
        String[][] listaDirecciones = {
                {"Pamplona", "Pamplona", "Navarra", "Calle Mayor", "12"},
                {"Pamplona1", "Pamplona", "Navarra", "Calle Cataluña", "12"},
                {"Narbarte", "Narbarte", "Navarra", "Camino Gorriti", "1"},
        };

        direcciones = new HashMap<>();
        waypoints = new HashSet<>();

        // Geocodificar todas las direcciones y crear waypoints
        for (String[] dir : listaDirecciones) {
            String nombre = dir[0];
            GeoPosition pos = geocodificar(dir[1], dir[2], dir[3], dir[4]);
            if (pos != null) {
                direcciones.put(nombre, pos);
                waypoints.add(new DefaultWaypoint(pos));
            } else {
                System.out.println("No se pudo geocodificar: " + nombre);
            }
        }

        // --- WaypointPainter ---
        waypointPainter = new WaypointPainter<>();
        waypointPainter.setWaypoints(waypoints);
        mapViewer.setOverlayPainter(waypointPainter);

        // Posición inicial
        if (direcciones.containsKey("Narbarte")) {
            mapViewer.setAddressLocation(direcciones.get("Narbarte"));
        }

        // --- Panel combo encima ---
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTop.setBackground(new Color(0, 0, 0, 120));
        panelTop.setOpaque(true);

        JComboBox<String> comboDirecciones = new JComboBox<>(direcciones.keySet().toArray(new String[0]));
        comboDirecciones.setPreferredSize(new Dimension(200, 30));
        panelTop.add(new JLabel("Selecciona dirección:"));
        panelTop.add(comboDirecciones);

        comboDirecciones.addActionListener(e -> {
            String seleccion = (String) comboDirecciones.getSelectedItem();
            if (seleccion != null) {
                mapViewer.setAddressLocation(direcciones.get(seleccion));
            }
        });

        // --- LayeredPane para superposición ---
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        mapViewer.setBounds(0, 0, 1000, 600);
        panelTop.setBounds(0, 0, 1000, 50);

        layeredPane.add(mapViewer, Integer.valueOf(1));
        layeredPane.add(panelTop, Integer.valueOf(2));

        add(layeredPane);
        setVisible(true);
    }

    // --- Función de geocodificación con Nominatim ---
    private GeoPosition geocodificar(String ciudad, String provincia, String calle, String numero) {
        try {
            String direccion = URLEncoder.encode(calle + " " + numero + ", " + ciudad + ", " + provincia, "UTF-8");
            String urlStr = "https://nominatim.openstreetmap.org/search?q=" + direccion + "&format=json&limit=1";
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", "JavaGeocoder");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MapaConDirecciones::new);
    }
}
