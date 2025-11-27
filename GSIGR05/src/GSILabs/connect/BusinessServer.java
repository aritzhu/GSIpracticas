package GSILabs.connect;

import Applicacion.BSystem.PublicBusinessSystem;
import Dominio.BModel.*;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jopendocument.util.cc.IPredicate;

public class BusinessServer {
    
    public static void main(String[] args) throws UnknownHostException, SocketException {
        try {
            String serverIP = getRealLocalIPv4();
            System.setProperty("java.rmi.server.hostname", serverIP);
            
            PublicBusinessSystem bsystem = new PublicBusinessSystem();
            Propietario p1 = new Propietario("P001", "prop1", "pass1", 40, new Date());
            Propietario p2 = new Propietario("P002", "prop2", "pass2", 35, new Date());
            List<Propietario> dueños1 = new ArrayList<>();
            dueños1.add(p1);
            List<Propietario> dueños2 = new ArrayList<>();
            dueños2.add(p2);
            Bar bar1 = new Bar("Bar Central", new Direccion("Bilbao", "Bizkaia", "Licenciado Poza", 47), dueños1);
            Bar bar3 = new Bar("Bar New Horizon", new Direccion("Bilbao", "Bizkaia", "Calle Mayor", 47), dueños1);
            Bar bar2 = new Bar("Bar La Esquina", new Direccion("Pamplona", "Navarra", "Calle Mayor", 12), dueños2);
            Restaurante r1 = new Restaurante("Restaurante El Faro", new Direccion("Bilbao", "Bizkaia", "Calle del Puerto", 5), dueños1);
            Restaurante r2 = new Restaurante("Restaurante La Plaza", new Direccion("Pamplona", "Navarra", "Calle San Juan", 10), dueños2);
            Restaurante r3 = new Restaurante("Restaurante Casa Manolo", new Direccion("Bilbao", "Bizkaia", "Calle del Pui", 5), dueños1);
            bsystem.nuevoLocal(bar1);
            bsystem.nuevoLocal(bar2);
            bsystem.nuevoLocal(bar3);
            bsystem.nuevoLocal(r1);
            bsystem.nuevoLocal(r2);
            bsystem.nuevoLocal(r3);
            Cliente c1 = new Cliente(new ArrayList<>(), "C001", "usuario1", "pass1", 25, new Date());
            Cliente c2 = new Cliente(new ArrayList<>(), "C002", "usuario2", "pass2", 30, new Date());
            bsystem.nuevoUsuario(c1);
            bsystem.nuevoUsuario(c2);
            Review rv1 = new Review(5, "Excelente bar", new Date(), c1);
            Review rv2 = new Review(3, "Normalito", new Date(), c2);
            bar1.addReview(rv2);
            bar2.addReview(rv2);
            bar3.addReview(rv1);

            Object stub = UnicastRemoteObject.exportObject(bsystem, 0);

            Registry registry1099 = LocateRegistry.createRegistry(1099);
            registry1099.rebind("ClientGateway", (Remote) stub);
            registry1099.rebind("AdminGateway", (Remote) stub);

            System.out.println("Servidor RMI iniciado correctamente en puerto 1099.");
            System.out.println("Identificadores publicados: ClientGateway, AdminGateway");

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    // Función auxiliar para encontrar la IP de red real
    public static String getRealLocalIPv4() throws SocketException, UnknownHostException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface ni = interfaces.nextElement();
            if (ni.isLoopback() || !ni.isUp()) continue;

            Enumeration<InetAddress> addresses = ni.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress address = addresses.nextElement();
                // isSiteLocalAddress() comprueba IPs privadas (10.x.x.x, 192.168.x.x, etc.)
                // getHostAddress().indexOf(":") == -1 asegura que es IPv4 (no contiene ':')
                if (address.isSiteLocalAddress() && address.getHostAddress().indexOf(":") == -1) {
                    return address.getHostAddress();
                }
            }
        }
        
        // Si no encuentra una IP local de sitio, devuelve la IP estándar del host local (puede ser 127.0.x.x)
        return InetAddress.getLocalHost().getHostAddress();
    }
}
