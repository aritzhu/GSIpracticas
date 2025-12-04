import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactory;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;
import org.jxmapviewer.viewer.DefaultWaypoint;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MapaConComboWaypoints extends JFrame {

    public MapaConComboWaypoints() {
        setTitle("titulo");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // --- TILE FACTORY CON HTTPS ---
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
        TileFactory tileFactory = new DefaultTileFactory(info);

        // --- MAPA ---
        JXMapViewer mapViewer = new JXMapViewer();
        mapViewer.setTileFactory(tileFactory);
        mapViewer.setZoom(7);
        mapViewer.setAddressLocation(new GeoPosition(40.4168, -3.7038)); // Madrid por defecto

        // --- LISTA DE CIUDADES ---
        
        //trabajar esta parte de aqui cogiendo los bares
        Map<String, GeoPosition> ciudades = new HashMap<>();
        ciudades.put("Madrid", new GeoPosition(40.4168, -3.7038));
        ciudades.put("Barcelona", new GeoPosition(41.3851, 2.1734));
        ciudades.put("Pamplona", new GeoPosition(42.8125, -1.6458));

        // --- WAYPOINTS ---
        Set<Waypoint> waypoints = new HashSet<>();
        for (GeoPosition pos : ciudades.values()) {
            waypoints.add(new DefaultWaypoint(pos));
        }
        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<>();
        waypointPainter.setWaypoints(waypoints);
        mapViewer.setOverlayPainter(waypointPainter);

        // --- PANEL TRANSPARENTE ENCIMA ---
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTop.setOpaque(false); // completamente transparente

        JComboBox<String> comboCiudades = new JComboBox<>(ciudades.keySet().toArray(new String[0]));
        comboCiudades.setPreferredSize(new Dimension(150, 30));
        panelTop.add(comboCiudades);

        // --- LISTENER DEL COMBO ---
        comboCiudades.addActionListener(e -> {
            String seleccion = (String) comboCiudades.getSelectedItem();
            if (seleccion != null) {
                GeoPosition pos = ciudades.get(seleccion);
                mapViewer.setAddressLocation(pos);
                mapViewer.setZoom(6);
            }
        });

        // --- LAYERED PANE PARA PANEL SOBRE MAPA ---
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        mapViewer.setBounds(0, 0, 800, 600);
        panelTop.setBounds(0, 0, 800, 50);

        layeredPane.add(mapViewer, Integer.valueOf(1)); // capa fondo
        layeredPane.add(panelTop, Integer.valueOf(2));   // capa encima

        add(layeredPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MapaConComboWaypoints().setVisible(true));
    }
}
