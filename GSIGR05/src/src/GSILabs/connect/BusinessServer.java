package GSILabs.connect;

import Applicacion.BSystem.PublicBusinessSystem;
import Dominio.BModel.*;
import Dominio.IBModelo.Local; // Import necesario
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Enumeration;
import java.util.Random;

public class BusinessServer {
    
    public static void main(String[] args) throws UnknownHostException, SocketException {
        try {
            String serverIP = getRealLocalIPv4();
            System.setProperty("java.rmi.server.hostname", serverIP);
            
            PublicBusinessSystem bsystem = new PublicBusinessSystem();
            
            // --- DUEÑOS ---
            Propietario p1 = new Propietario("P001", "prop1", "pass1", 40, new Date());
            Propietario p2 = new Propietario("P002", "prop2", "pass2", 35, new Date());
            List<Propietario> dueños1 = new ArrayList<>(); dueños1.add(p1);
            List<Propietario> dueños2 = new ArrayList<>(); dueños2.add(p2);

            // --- CLIENTES ---
            Cliente c1 = new Cliente(new ArrayList<>(), "C001", "usuario1", "pass1", 25, new Date());
            Cliente c2 = new Cliente(new ArrayList<>(), "C002", "usuario2", "pass2", 30, new Date());
            bsystem.nuevoUsuario(c1);
            bsystem.nuevoUsuario(c2);

            // --- CREACIÓN DE LOCALES ---
            List<Local> todosLosLocales = new ArrayList<>();

            // Bares
            todosLosLocales.add(new Bar("Bar Central", new Direccion("Bilbao","Bizkaia","Calle Licenciado Poza", 47), dueños1));
            todosLosLocales.add(new Bar("Bar La Esquina", new Direccion("Pamplona","Navarra","Calle Mayor", 12), dueños2));
            todosLosLocales.add(new Bar("Bar Gaucho", new Direccion("Pamplona", "Navarra", "Calle Espoz y Mina", 7), dueños1));
            todosLosLocales.add(new Bar("Sagartoki", new Direccion("Vitoria-Gasteiz", "Álava", "Calle Prado", 18), dueños2));
            todosLosLocales.add(new Bar("Bar Zeruko", new Direccion("San Sebastián", "Gipuzkoa", "Calle Pescadería", 10), dueños1));
            todosLosLocales.add(new Bar("Bar El Globo", new Direccion("Bilbao", "Bizkaia", "Calle Diputación", 8), dueños2));

            // Pubs
            todosLosLocales.add(new Pub("Cotton Club", new Direccion("Bilbao", "Bizkaia", "Gregorio de la Revilla", 25), dueños1, 18, 3));
            todosLosLocales.add(new Pub("Sir Winston Churchill Pub", new Direccion("Bilbao", "Bizkaia", "Sabino Arana", 1), dueños2, 16, 2));
            todosLosLocales.add(new Pub("O'Connors Irish Pub", new Direccion("Pamplona", "Navarra", "Paseo de Sarasate", 3), dueños1, 17, 2));
            todosLosLocales.add(new Pub("Zentral", new Direccion("Pamplona", "Navarra", "Mercado de Santo Domingo", 0), dueños2, 23, 6));
            todosLosLocales.add(new Pub("The Dublin House", new Direccion("Vitoria-Gasteiz", "Álava", "Paseo de la Senda", 2), dueños1, 12, 2));

            // Restaurantes
            todosLosLocales.add(new Restaurante("Restaurante El Faro", new Direccion("Bilbao","Bizkaia","Calle Rodríguez Arias", 5), dueños1));
            todosLosLocales.add(new Restaurante("Restaurante La Plaza", new Direccion("Pamplona","Navarra","Calle Estafeta", 10), dueños2));
            todosLosLocales.add(new Restaurante("Restaurante Casa Manolo", new Direccion("Bilbao","Bizkaia","Calle Licenciado Poza", 5), dueños1));
            todosLosLocales.add(new Restaurante("Restaurante Rodero", new Direccion("Pamplona", "Navarra", "Calle Emilio Arrieta", 3), dueños2));
            todosLosLocales.add(new Restaurante("Asador Olaverri", new Direccion("Pamplona", "Navarra", "Calle Santa Marta", 4), dueños1));
            todosLosLocales.add(new Restaurante("Restaurante Europa", new Direccion("Pamplona", "Navarra", "Calle Espoz y Mina", 11), dueños2));
            todosLosLocales.add(new Restaurante("La Olla", new Direccion("Pamplona", "Navarra", "Avenida Roncesvalles", 2), dueños1));
            todosLosLocales.add(new Restaurante("Nerua Guggenheim Bilbao", new Direccion("Bilbao", "Bizkaia", "Abandoibarra Etorbidea", 2), dueños2));
            todosLosLocales.add(new Restaurante("Restaurante Kate Zaharra", new Direccion("Bilbao", "Bizkaia", "Camino Zabalbide", 221), dueños1));
            todosLosLocales.add(new Restaurante("Asador Indusi", new Direccion("Bilbao", "Bizkaia", "Calle García Rivero", 7), dueños2));
            todosLosLocales.add(new Restaurante("Restaurante Arzak", new Direccion("San Sebastián", "Gipuzkoa", "Avenida Alcalde J. Elosegi", 273), dueños1));
            todosLosLocales.add(new Restaurante("Restaurante Akelarre", new Direccion("San Sebastián", "Gipuzkoa", "Paseo Padre Orkolaga", 56), dueños2));
            todosLosLocales.add(new Restaurante("Restaurante El Portalón", new Direccion("Vitoria-Gasteiz", "Álava", "Calle Correría", 151), dueños1));

            // --- REGISTRO Y REVIEWS AUTOMÁTICAS ---
            Random rand = new Random();
            String[] comentarios = {"Fantástico", "Muy bueno", "Regular", "Volveré", "No me gustó", "Increíble ambiente"};
            
            for (Local l : todosLosLocales) {
                // 1. Registrar en el sistema
                bsystem.nuevoLocal(l);
                
                // 2. Añadir entre 1 y 4 reviews a cada local
                int numReviews = rand.nextInt(4) + 1; 
                for (int i = 0; i < numReviews; i++) {
                    int puntuacion = rand.nextInt(5) + 1; // 1 a 5
                    String comment = comentarios[rand.nextInt(comentarios.length)];
                    Cliente autor = (rand.nextBoolean()) ? c1 : c2;
                    
                    Review rev = new Review(puntuacion, comment, new Date(), autor);
                    l.addReview(rev);
                }
            }

            // --- EXPORTAR RMI ---
            Object stub = UnicastRemoteObject.exportObject(bsystem, 0);
            Registry registry1099 = LocateRegistry.createRegistry(1099);
            registry1099.rebind("ClientGateway", (Remote) stub);
            registry1099.rebind("AdminGateway", (Remote) stub);

            System.out.println("Servidor RMI iniciado en puerto 1099.");
            System.out.println("Cargados " + todosLosLocales.size() + " locales con reviews generadas.");

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    
    public static String getRealLocalIPv4() throws SocketException, UnknownHostException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface ni = interfaces.nextElement();
            if (ni.isLoopback() || !ni.isUp()) continue;
            Enumeration<InetAddress> addresses = ni.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress address = addresses.nextElement();
                if (address.isSiteLocalAddress() && address.getHostAddress().indexOf(":") == -1) {
                    return address.getHostAddress();
                }
            }
        }
        return InetAddress.getLocalHost().getHostAddress();
    }
}