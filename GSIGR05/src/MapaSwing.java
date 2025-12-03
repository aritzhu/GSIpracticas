import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;
import org.jxmapviewer.viewer.DefaultWaypoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MapaSwing extends JFrame {

    private JXMapViewer mapViewer;
    private Map<String, GeoPosition> ciudades;
    private Set<Waypoint> waypoints;
    private WaypointPainter<Waypoint> waypointPainter;

    public MapaSwing() {
        setTitle("Mapa Interactivo con JXMapViewer2 (HTTPS)");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear TileFactoryInfo usando HTTPS
        TileFactoryInfo info = new TileFactoryInfo(0, 17, 17,
                256, true, true,
                "https://tile.openstreetmap.org",
                "x", "y", "z") {
            @Override
            public String getTileUrl(int x, int y, int zoom) {
                int invZoom = getTotalMapZoom() - zoom;
                return this.baseURL + "/" + invZoom + "/" + x + "/" + y + ".png";
            }
        };
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);

        // Crear JXMapViewer
        mapViewer = new JXMapViewer();
        mapViewer.setTileFactory(tileFactory);

        // Crear lista de ciudades
        ciudades = new HashMap<>();
        ciudades.put("Madrid", new GeoPosition(40.4168, -3.7038));
        ciudades.put("Barcelona", new GeoPosition(41.3851, 2.1734));
        ciudades.put("Pamplona", new GeoPosition(42.8125, -1.6458));

        // Añadir marcadores
        waypoints = new HashSet<>();
        for (GeoPosition pos : ciudades.values()) {
            waypoints.add(new DefaultWaypoint(pos));
        }
        waypointPainter = new WaypointPainter<>();
        waypointPainter.setWaypoints(waypoints);
        mapViewer.setOverlayPainter(waypointPainter);

        // Centrar mapa en Madrid al inicio
        mapViewer.setZoom(6);
        mapViewer.setAddressLocation(ciudades.get("Madrid"));

        // Panel de checkboxes
        JPanel panelCheck = new JPanel();
        panelCheck.setLayout(new FlowLayout());
        for (String ciudad : ciudades.keySet()) {
            JCheckBox cb = new JCheckBox(ciudad);
            cb.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    // Centrar el mapa en la ciudad seleccionada
                    mapViewer.setAddressLocation(ciudades.get(ciudad));
                    mapViewer.repaint();
                }
            });
            panelCheck.add(cb);
        }

        add(panelCheck, BorderLayout.NORTH);
        add(mapViewer, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MapaSwing::new);
    }
}
