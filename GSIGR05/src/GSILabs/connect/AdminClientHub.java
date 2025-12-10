package GSILabs.connect;

import Dominio.BModel.Bar;
import Dominio.BModel.Cliente;
import Dominio.BModel.Pub;
import Dominio.BModel.Restaurante;
import Dominio.BModel.Review;
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
import java.util.concurrent.ConcurrentHashMap;
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

    private static Map<String, GeoPosition> localesActuales = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);

        System.out.print("Host del servidor: ");
        String host = sc.nextLine();
        if(host.isEmpty()) host = "127.0.0.1"; 

        System.out.print("Puerto del servidor: ");
        String portStr = sc.nextLine();
        int port = portStr.isEmpty() ? 1099 : Integer.parseInt(portStr);

        // Modificado para que sea más claro para el usuario
        System.out.print("Tag (C = Client / A = Admin): ");
        String input = sc.nextLine();
        // Por defecto será "C" si está vacío
        if(input.isEmpty()) input = "C"; 

        try {
            Registry registry = LocateRegistry.getRegistry(host, port);
            
            // Lógica corregida: Acepta "C" pero busca "ClientGateway"
            if (input.equalsIgnoreCase("C") || input.equalsIgnoreCase("ClientGateway")) {
                
                // IMPORTANTE: Aquí buscamos el nombre REAL registrado en el servidor
                ClientGateway gateway = (ClientGateway) registry.lookup("ClientGateway");
                
                // 1. Obtenemos la lista de ciudades disponibles
                String[] ciudades = gateway.getCiudadesConLocales();
                
                if (ciudades == null || ciudades.length == 0) {
                    System.out.println("No hay datos de ciudades en el servidor.");
                    return;
                }
                // 2. Lanzamos la GUI
                SwingUtilities.invokeLater(() -> crearGuiDinamica(gateway, ciudades));

            } else if (input.equalsIgnoreCase("A") || input.equalsIgnoreCase("AdminGateway")) {
                
                // Lógica corregida: Acepta "A" pero busca "AdminGateway"
                AdminGateway gateway = (AdminGateway) registry.lookup("AdminGateway");
                
                LocalFinder finder = (LocalFinder) gateway;
                Local[] locales = finder.getLocals(""); 
                if (locales != null) {
                    for (Local l : locales) {
                        GeoPosition pos = geocodificar(l);
                        if (pos != null) localesActuales.put(l.getNombre(), pos);
                    }
                }
                SwingUtilities.invokeLater(() -> crearGuiDinamica(null, null)); 
            } else {
                System.out.println("Opción no válida. Usa 'C' o 'A'.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    private static void crearGuiDinamica(ClientGateway gateway, String[] ciudadesDisponibles) {
        Cliente clienteGui = new Cliente(new ArrayList<>(), "GUI_USER", "UsuarioApp", "1234", 18, new Date());
        JFrame frame = new JFrame("Mapa de Locales Interactivo");
        frame.setSize(1250, 750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // --- MAPA ---
        JXMapViewer mapViewer = new JXMapViewer();
        TileFactoryInfo info = new TileFactoryInfo(0, 19, 19, 256, true, true,
                "https://tile.openstreetmap.org", "x", "y", "z") {
            @Override
            public String getTileUrl(int x, int y, int zoom) {
                int invZoom = getTotalMapZoom() - zoom;
                return baseURL + "/" + invZoom + "/" + x + "/" + y + ".png";
            }
        };
        mapViewer.setTileFactory(new DefaultTileFactory(info));
        mapViewer.setZoom(7);

        // --- PANELES ---
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTop.setBackground(new Color(50, 50, 50));
        
        // CONTROLES
        JComboBox<String> comboCiudades = new JComboBox<>();
        if (ciudadesDisponibles != null) {
            for (String c : ciudadesDisponibles) comboCiudades.addItem(c);
        }
        
        JLabel lblCiudad = new JLabel(" Ciudad:");
        lblCiudad.setForeground(Color.WHITE);
        
        JComboBox<String> comboLocales = new JComboBox<>();
        comboLocales.setPreferredSize(new Dimension(200, 25));
        
        JLabel lblLocal = new JLabel(" Ir a:");
        lblLocal.setForeground(Color.WHITE);

        JButton btnRuta = new JButton("Ruta");
        btnRuta.setBackground(Color.ORANGE);

        // --- NUEVOS BOTONES "MEJOR DE..." ---
        JButton btnBestBar = new JButton("Mejor Bar");
        btnBestBar.setBackground(new Color(173, 216, 230)); // Azul claro
        
        JButton btnBestRest = new JButton("Mejor Rest.");
        btnBestRest.setBackground(new Color(144, 238, 144)); // Verde claro
        
        JButton btnBestPub = new JButton("Mejor Pub");
        btnBestPub.setBackground(new Color(255, 182, 193)); // Rosa claro

        // --- AÑADIR ---
        if (gateway != null) {
            panelTop.add(lblCiudad);
            panelTop.add(comboCiudades);
        }
        panelTop.add(lblLocal);
        panelTop.add(comboLocales);
        panelTop.add(Box.createHorizontalStrut(10));
        
        // Añadir botones de "Mejores"
        panelTop.add(btnBestBar);
        panelTop.add(btnBestRest);
        panelTop.add(btnBestPub);
        
        panelTop.add(Box.createHorizontalStrut(10));
        panelTop.add(btnRuta);

        // --- EVENTOS ---
        
        ActionListener actionCargarCiudad = e -> {
            String ciudadSel = (String) comboCiudades.getSelectedItem();
            if (ciudadSel == null || gateway == null) return;
            comboCiudades.setEnabled(false);
            frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            
            new Thread(() -> {
                try {
                    System.out.println("Descargando TODOS los datos de " + ciudadSel + "...");
                    localesActuales.clear(); 
                    Local[] todos = gateway.getLocalesEnCiudad(ciudadSel);
                    
                    if (todos != null) {
                        for (Local l : todos) {
                            GeoPosition pos = geocodificar(l);
                            if (pos != null) {
                                String prefijo = "[?]";
                                if (l instanceof Pub) prefijo = "[PUB] ";
                                else if (l instanceof Bar) prefijo = "[BAR] ";
                                else if (l instanceof Restaurante) prefijo = "[REST] ";
                                
                                localesActuales.put(prefijo + l.getNombre(), pos);
                            }
                        }
                    }
                    SwingUtilities.invokeLater(() -> {
                        comboLocales.removeAllItems();
                        for (String nombre : localesActuales.keySet()) comboLocales.addItem(nombre);
                        
                        Set<Waypoint> waypoints = new HashSet<>();
                        for (GeoPosition p : localesActuales.values()) waypoints.add(new DefaultWaypoint(p));
                        WaypointPainter<Waypoint> wp = new WaypointPainter<>();
                        wp.setWaypoints(waypoints);
                        mapViewer.setOverlayPainter(wp);
                        
                        if (localesActuales.size() >= 2) {
                            mapViewer.zoomToBestFit(new HashSet<>(localesActuales.values()), 0.7);
                        } else if (localesActuales.size() == 1) {
                            GeoPosition unico = localesActuales.values().iterator().next();
                            mapViewer.setAddressLocation(unico);
                            mapViewer.setZoom(4);
                        }
                        
                        comboCiudades.setEnabled(true);
                        frame.setCursor(Cursor.getDefaultCursor());
                    });
                } catch (Exception ex) { ex.printStackTrace(); }
            }).start();
        };

        if (gateway != null) {
            comboCiudades.addActionListener(actionCargarCiudad);
            if (comboCiudades.getItemCount() > 0) comboCiudades.setSelectedIndex(0);
        }

        // --- LÓGICA DE BOTONES MEJORES ---
        // Helper para buscar y centrar
        ActionListener actionMejor = (ActionEvent evt) -> {
        if (gateway == null) return;
        String ciudad = (String) comboCiudades.getSelectedItem();
        JButton source = (JButton) evt.getSource();

        new Thread(() -> {
            try {
                Local mejor = null;
                String tipoTemp = ""; // Usamos variables temporales para la lógica
                String prefijoTemp = "";

                if (source == btnBestBar) {
                    mejor = gateway.mejorBar(ciudad);
                    tipoTemp = "Bar";
                    prefijoTemp = "[BAR] ";
                } else if (source == btnBestRest) {
                    mejor = gateway.mejorRestaurante(ciudad);
                    tipoTemp = "Restaurante";
                    prefijoTemp = "[REST] ";
                } else if (source == btnBestPub) {
                    mejor = gateway.mejorPub(ciudad);
                    tipoTemp = "Pub";
                    prefijoTemp = "[PUB] ";
                }

                // --- AQUÍ ESTÁ EL TRUCO ---
                // Creamos variables finales (o efectivamente finales) para pasarlas a la Lambda
                Local finalMejor = mejor;
                String finalTipo = tipoTemp;     // Copia final de tipo
                String finalPrefijo = prefijoTemp; // Copia final de prefijo

                if (finalMejor != null) {
                    String nombreKey = finalPrefijo + finalMejor.getNombre();

                    SwingUtilities.invokeLater(() -> {
                        if (localesActuales.containsKey(nombreKey)) {
                            GeoPosition pos = localesActuales.get(nombreKey);
                            mapViewer.setAddressLocation(pos);
                            mapViewer.setZoom(3);
                            comboLocales.setSelectedItem(nombreKey);

                            // AHORA USAMOS finalTipo EN LUGAR DE tipo
                            JOptionPane.showMessageDialog(frame, 
                                "El mejor " + finalTipo + " es:\n" + finalMejor.getNombre() + "\n(Centrado en mapa)");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Se encontró " + finalMejor.getNombre() + " pero no está geocodificado.");
                        }
                    });
                } else {
                    // AQUÍ TAMBIÉN USAMOS finalTipo
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, "No se encontró ningún " + finalTipo + " en esta ciudad."));
                }
            } catch (Exception ex) { ex.printStackTrace(); }
        }).start();
    };

        btnBestBar.addActionListener(actionMejor);
        btnBestRest.addActionListener(actionMejor);
        btnBestPub.addActionListener(actionMejor);
        

        // --- BOTÓN NUEVA RESEÑA ---
        JButton btnAddReview = new JButton("Nueva Reseña");
        btnAddReview.setBackground(Color.CYAN);
        panelTop.add(Box.createHorizontalStrut(10));
        panelTop.add(btnAddReview);

        // --- LÓGICA DEL BOTÓN MEJORADA ---
        btnAddReview.addActionListener(e -> {
            String seleccionRaw = (String) comboLocales.getSelectedItem();
            
            // 1. Validar selección
            if (seleccionRaw == null) {
                JOptionPane.showMessageDialog(frame, "Por favor, selecciona un local en la lista 'Ir a:'.");
                return;
            }
            // 2. Limpiar el nombre para mostrarlo bonito (Quitamos [BAR], [PUB]...)
            String nombreVisual = seleccionRaw;
            if (seleccionRaw.contains("] ")) {
                nombreVisual = seleccionRaw.substring(seleccionRaw.indexOf("] ") + 2);
            }
            // 3. Crear el panel con diseño BorderLayout para poner título arriba
            JPanel panelForm = new JPanel(new BorderLayout(10, 10));
            panelForm.setPreferredSize(new Dimension(350, 200)); // Hacemos la ventana un poco más ancha
            // --- CABECERA: NOMBRE DEL LOCAL ---
            JLabel lblTitulo = new JLabel("<html>Reseña para:<br/><b><font size='5' color='blue'>" + nombreVisual + "</font></b></html>");
            lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
            lblTitulo.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5)); // Margen
            panelForm.add(lblTitulo, BorderLayout.NORTH);
            // --- CUERPO: INPUTS ---
            JPanel panelInputs = new JPanel(new GridLayout(0, 1, 5, 5)); // Grid para los campos
            // Selector de nota
            JComboBox<Integer> comboNota = new JComboBox<>(new Integer[]{5, 4, 3, 2, 1});
            JPanel pNota = new JPanel(new FlowLayout(FlowLayout.LEFT));
            pNota.add(new JLabel("Puntuación: "));
            pNota.add(comboNota);
            panelInputs.add(pNota);
            // Tex comentario
            panelInputs.add(new JLabel("Tu opinión:"));
            JTextArea textComentario = new JTextArea(4, 20);
            textComentario.setLineWrap(true);
            textComentario.setWrapStyleWord(true);
            JScrollPane scrollComment = new JScrollPane(textComentario);
            panelInputs.add(scrollComment);

            panelForm.add(panelInputs, BorderLayout.CENTER);

            // 4. Mostrar el diálogo
            int result = JOptionPane.showConfirmDialog(frame, panelForm, 
                    "Escribir Reseña", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String comentario = textComentario.getText();
                int nota = (Integer) comboNota.getSelectedItem();

                if (comentario.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "El comentario no puede estar vacío.");
                    return;
                }

                // Guardamos el nombre limpio para usarlo en el hilo (variable efectivamente final)
                final String nombreParaServer = nombreVisual; 

                // 5. Enviar al servidor
                new Thread(() -> {
                    try {
                        // Cliente temporal (GUI)
                        
                        Review nuevaReview = new Review(nota, comentario, new Date(), clienteGui);

                        boolean exito = gateway.publicarReview(nombreParaServer, nuevaReview);

                        SwingUtilities.invokeLater(() -> {
                            if (exito) {
                                JOptionPane.showMessageDialog(frame, "¡Reseña publicada para " + nombreParaServer + "!");
                            } else {
                                JOptionPane.showMessageDialog(frame, "Error: No se pudo guardar la reseña.");
                            }
                        });
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }).start();
            }
        });
        // --- BOTÓN BORRAR RESEÑA ---
        JButton btnDelReview = new JButton("Borrar Reseña");
        btnDelReview.setBackground(Color.PINK); // Color rojo/rosa para indicar peligro/borrar
        panelTop.add(btnDelReview);

        // --- LÓGICA BOTÓN BORRAR ---
        btnDelReview.addActionListener(e -> {
            String seleccionRaw = (String) comboLocales.getSelectedItem();
            
            if (seleccionRaw == null) {
                JOptionPane.showMessageDialog(frame, "Selecciona un local primero.");
                return;
            }
            // Limpiar nombre
            String nombreLimpio = seleccionRaw;
            if (seleccionRaw.contains("] ")) {
                nombreLimpio = seleccionRaw.substring(seleccionRaw.indexOf("] ") + 2);
            }
            final String nombreFinal = nombreLimpio;

            // Hilo para consultar al servidor
            new Thread(() -> {
                try {
                    // 1. Pedir reseñas al servidor
                    Review[] reviews = gateway.getReviewsDeLocal(nombreFinal, clienteGui);

                    SwingUtilities.invokeLater(() -> {
                        if (reviews == null || reviews.length == 0) {
                            JOptionPane.showMessageDialog(frame, "El local '" + nombreFinal + "' no tiene reseñas para borrar.");
                            return;
                        }

                        // 2. Preparar objetos para el desplegable
                        // Creamos una clase 'wrapper' o un String formateado para que se vea bonito en el combo
                        String[] opciones = new String[reviews.length];
                        for (int i = 0; i < reviews.length; i++) {
                            Review r = reviews[i];
                            // Formato: "★5 | Usuario: Pepe | Comentario..."
                            String corto = r.getComentario().length() > 20 ? r.getComentario().substring(0, 20) + "..." : r.getComentario();
                            opciones[i] = "★" + r.getValoracion() + " | " + r.getAutor().getNick() + " | " + corto;
                        }

                        // 3. Mostrar diálogo de selección
                        String elegidoStr = (String) JOptionPane.showInputDialog(
                                frame,
                                "Selecciona la reseña a eliminar de " + nombreFinal + ":",
                                "Borrar Reseña",
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                opciones,
                                opciones[0]);

                        // 4. Si el usuario eligió algo y no dio a Cancelar
                        if (elegidoStr != null) {
                            // Buscar qué review corresponde al String seleccionado (por índice)
                            int index = -1;
                            for(int i=0; i<opciones.length; i++) {
                                if(opciones[i].equals(elegidoStr)) {
                                    index = i;
                                    break;
                                }
                            }
                            if (index != -1) {
                                Review reviewABorrar = reviews[index];
                                borrarReviewEnServer(gateway, reviewABorrar, frame);
                            }
                        }
                    });

                } catch (Exception ex) {
                    ex.printStackTrace();
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, "Error de conexión."));
                }
            }).start();
        });
        // --- BOTÓN VER RESEÑAS  ---
        JButton btnVerReviews = new JButton("Ver Reseñas");
        btnVerReviews.setBackground(new Color(255, 215, 0));
        panelTop.add(Box.createHorizontalStrut(5));
        panelTop.add(btnVerReviews);

        // --- LÓGICA VER RESEÑAS ---
        btnVerReviews.addActionListener(e -> {
            String seleccionRaw = (String) comboLocales.getSelectedItem();
            if (seleccionRaw == null) {
                JOptionPane.showMessageDialog(frame, "Selecciona un local primero.");
                return;
            }
            String nombreLimpio = seleccionRaw;
            if (seleccionRaw.contains("] ")) {
                nombreLimpio = seleccionRaw.substring(seleccionRaw.indexOf("] ") + 2);
            }
            final String nombreFinal = nombreLimpio;

            new Thread(() -> {
                try {
                    // LLAMADA AL SERVIDOR: Pasamos 'null' para pedir TODAS las reseñas
                    Review[] reviews = gateway.getReviewsDeLocal(nombreFinal, null);

                    SwingUtilities.invokeLater(() -> {
                        if (reviews == null || reviews.length == 0) {
                            JOptionPane.showMessageDialog(frame, "El local '" + nombreFinal + "' aún no tiene reseñas.");
                        } else {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Reseñas de ").append(nombreFinal).append(":\n\n");
                            
                            Arrays.sort(reviews, (r1, r2) -> r2.getFechaEscritura().compareTo(r1.getFechaEscritura()));

                            for (Review r : reviews) {
                                sb.append("--------------------------------------------------\n");

                                String estrellas = "★".repeat(r.getValoracion()) + "☆".repeat(5 - r.getValoracion());
                                
                                sb.append(estrellas).append(" (").append(r.getValoracion()).append("/5)  |  ");
                                sb.append("Autor: ").append(r.getAutor().getNick()).append("\n");
                                sb.append("Fecha: ").append(r.getFechaEscritura()).append("\n\n");
                                sb.append(r.getComentario()).append("\n");
                            }
                            sb.append("--------------------------------------------------");


                            JTextArea textArea = new JTextArea(sb.toString());
                            textArea.setEditable(false);
                            textArea.setLineWrap(true);
                            textArea.setWrapStyleWord(true);
                            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
                            
                            JScrollPane scrollPane = new JScrollPane(textArea);
                            scrollPane.setPreferredSize(new Dimension(500, 400));

                            JOptionPane.showMessageDialog(frame, scrollPane, "Opiniones de Clientes", JOptionPane.PLAIN_MESSAGE);
                        }
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, "Error al obtener reseñas."));
                }
            }).start();
        });

        // --- RESTO DE EVENTOS (Zoom, Ruta, etc) ---
        comboLocales.addActionListener(e -> {
            String sel = (String) comboLocales.getSelectedItem();
            if (sel != null && localesActuales.containsKey(sel)) {
                mapViewer.setAddressLocation(localesActuales.get(sel));
                if(mapViewer.getZoom() > 4) mapViewer.setZoom(4);
            }
        });

        btnRuta.addActionListener(e -> {
            if (localesActuales.size() < 2) {
                JOptionPane.showMessageDialog(frame, "Faltan locales."); return;
            }
            String[] nombres = localesActuales.keySet().toArray(new String[0]);
            JList<String> listSelector = new JList<>(nombres);
            listSelector.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            if (JOptionPane.showConfirmDialog(frame, new JScrollPane(listSelector), "Ruta (Ctrl+Click)", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                List<String> seleccionados = listSelector.getSelectedValuesList();
                if (seleccionados.size() < 2) return;
                
                new Thread(() -> {
                    List<GeoPosition> puntos = new ArrayList<>();
                    for(String s : seleccionados) puntos.add(localesActuales.get(s));
                    List<GeoPosition> ordenados = calcularRutaVecinoMasCercano(puntos.get(0), puntos);
                    List<GeoPosition> trazado = new ArrayList<>();
                    try {
                        for(int i=0; i<ordenados.size()-1; i++) {
                            trazado.addAll(obtenerRutaOSRM(ordenados.get(i), ordenados.get(i+1)));
                            Thread.sleep(100); 
                        }
                    } catch(Exception ex) {}
                    SwingUtilities.invokeLater(() -> {
                        RoutePainter rp = new RoutePainter(trazado);
                        Set<Waypoint> wps = new HashSet<>();
                        for(GeoPosition p : ordenados) wps.add(new DefaultWaypoint(p));
                        WaypointPainter<Waypoint> wp = new WaypointPainter<>();
                        wp.setWaypoints(wps);
                        mapViewer.setOverlayPainter(new CompoundPainter<>(Arrays.asList(rp, wp)));
                        mapViewer.zoomToBestFit(new HashSet<>(ordenados), 0.7);
                    });
                }).start();
            }
        });
        
        MouseInputListener mia = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mia);
        mapViewer.addMouseMotionListener(mia);
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapViewer));

        frame.add(panelTop, BorderLayout.NORTH);
        frame.add(mapViewer, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    // --- UTILIDADES (Geocodificar, Ruta, etc. se mantienen igual) ---
    private static GeoPosition geocodificar(Local l) {
        return geocodificar(l.getDireccion().getLocalidad(), l.getDireccion().getProvincia(), l.getDireccion().getCalle(), String.valueOf(l.getDireccion().getNumero()));
    }
    private static GeoPosition geocodificar(String ciudad, String provincia, String calle, String numero) {
        try {
            Thread.sleep(800); 
            String direccion = URLEncoder.encode(calle + " " + numero + ", " + ciudad + ", " + provincia, "UTF-8");
            String urlStr = "https://nominatim.openstreetmap.org/search?q=" + direccion + "&format=json&limit=1";
            HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
            conn.setRequestProperty("User-Agent", "JavaMapApp_StudentProject_1.0"); 
            if(conn.getResponseCode() != 200) return null;
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) response.append(line);
            in.close();
            JSONArray resultados = new JSONArray(response.toString());
            if (resultados.length() > 0) {
                JSONObject obj = resultados.getJSONObject(0);
                return new GeoPosition(obj.getDouble("lat"), obj.getDouble("lon"));
            }
        } catch (Exception e) {}
        return null;
    }
    // Métodos OSRM y Helper se omiten para ahorrar espacio (copialos de la versión anterior si los necesitas, son idénticos)
    private static List<GeoPosition> obtenerRutaOSRM(GeoPosition inicio, GeoPosition fin) {
        List<GeoPosition> rutaSegmento = new ArrayList<>();
        try {
            String urlStr = String.format(Locale.US, "http://router.project-osrm.org/route/v1/driving/%f,%f;%f,%f?overview=full&geometries=geojson", inicio.getLongitude(), inicio.getLatitude(), fin.getLongitude(), fin.getLatitude());
            HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
            if (conn.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) response.append(line);
                JSONObject json = new JSONObject(response.toString());
                JSONArray routes = json.getJSONArray("routes");
                if (routes.length() > 0) {
                    JSONArray coordinates = routes.getJSONObject(0).getJSONObject("geometry").getJSONArray("coordinates");
                    for (int i = 0; i < coordinates.length(); i++) {
                        JSONArray coord = coordinates.getJSONArray(i);
                        rutaSegmento.add(new GeoPosition(coord.getDouble(1), coord.getDouble(0)));
                    }
                }
            }
        } catch (Exception e) {}
        return rutaSegmento;
    }
    private static List<GeoPosition> calcularRutaVecinoMasCercano(GeoPosition inicio, List<GeoPosition> destinos) {
        List<GeoPosition> ruta = new ArrayList<>();
        List<GeoPosition> pendientes = new ArrayList<>(destinos);
        if(!pendientes.contains(inicio)) pendientes.add(inicio);
        ruta.add(inicio); pendientes.remove(inicio);
        GeoPosition actual = inicio;
        while (!pendientes.isEmpty()) {
            GeoPosition masCercano = null; double distMin = Double.MAX_VALUE;
            for (GeoPosition c : pendientes) {
                double d = Math.sqrt(Math.pow(actual.getLatitude()-c.getLatitude(),2) + Math.pow(actual.getLongitude()-c.getLongitude(),2));
                if (d < distMin) { distMin = d; masCercano = c; }
            }
            ruta.add(masCercano); pendientes.remove(masCercano); actual = masCercano;
        }
        return ruta;
    }
    public static class RoutePainter implements Painter<JXMapViewer> {
        private final List<GeoPosition> track;
        public RoutePainter(List<GeoPosition> track) { this.track = track; }
        @Override
        public void paint(Graphics2D g, JXMapViewer map, int w, int h) {
            g = (Graphics2D) g.create();
            Rectangle rect = map.getViewportBounds();
            g.translate(-rect.x, -rect.y);
            g.setColor(new Color(255, 0, 0, 200));
            g.setStroke(new BasicStroke(5));
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int lastX = -1, lastY = -1;
            for (GeoPosition gp : track) {
                Point2D pt = map.getTileFactory().geoToPixel(gp, map.getZoom());
                if (lastX != -1) g.drawLine(lastX, lastY, (int) pt.getX(), (int) pt.getY());
                lastX = (int) pt.getX(); lastY = (int) pt.getY();
            }
            g.dispose();
        }
    }
    // Método auxiliar para el borrado
    private static void borrarReviewEnServer(ClientGateway gateway, Review r, JFrame frame) {
        new Thread(() -> {
            try {
                boolean borrado = gateway.quitaReview(r);
                SwingUtilities.invokeLater(() -> {
                    if (borrado) {
                        JOptionPane.showMessageDialog(frame, "Reseña eliminada correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "No se pudo eliminar la reseña (puede que ya no exista).");
                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }
}