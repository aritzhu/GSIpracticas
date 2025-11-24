package GSILabs.connect;

import Applicacion.BSystem.PublicBusinessSystem;
import Dominio.BModel.*;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class BusinessServer {

    public static void main(String[] args) {
        try {
            // 1. Crear instancia del sistema de negocio
            PublicBusinessSystem bsystem = new PublicBusinessSystem();

            // 2. Crear propietarios
            Propietario p1 = new Propietario("P001", "prop1", "pass1", 40, new Date());
            Propietario p2 = new Propietario("P002", "prop2", "pass2", 35, new Date());

            // 3. Crear bares y restaurantes con propietarios
            List<Propietario> dueños1 = new ArrayList<>();
            dueños1.add(p1);
            List<Propietario> dueños2 = new ArrayList<>();
            dueños2.add(p2);

            Bar bar1 = new Bar("Pub Central", new Direccion("Bilbao", "Bizkaia", "Licenciado Poza", 47), dueños1);
            Bar bar2 = new Bar("Bar La Esquina", new Direccion("Pamplona", "Navarra", "Calle Mayor", 12), dueños2);

            Restaurante r1 = new Restaurante("Restaurante El Faro", new Direccion("Bilbao", "Bizkaia", "Calle del Puerto", 5), dueños1);
            Restaurante r2 = new Restaurante("Restaurante La Plaza", new Direccion("Pamplona", "Navarra", "Calle San Juan", 10), dueños2);

            // 4. Añadir locales al sistema
            bsystem.nuevoLocal(bar1);
            bsystem.nuevoLocal(bar2);
            bsystem.nuevoLocal(r1);
            bsystem.nuevoLocal(r2);

            // 5. Crear clientes
            Cliente c1 = new Cliente(new ArrayList<>(), "C001", "usuario1", "pass1", 25, new Date());
            Cliente c2 = new Cliente(new ArrayList<>(), "C002", "usuario2", "pass2", 30, new Date());

            bsystem.nuevoUsuario(c1);
            bsystem.nuevoUsuario(c2);

            // 6. Añadir algunas reviews de ejemplo
            Review rv1 = new Review(5, "Excelente bar", new Date(), c1);
            Review rv2 = new Review(3, "Normalito", new Date(), c2);
            bar1.addReview(rv1);
            bar2.addReview(rv2);

            // 7. Exportar el objeto RMI una sola vez
            Object stub = UnicastRemoteObject.exportObject(bsystem, 0);

            // 8. Crear registro RMI y vincular stubs usando el mismo objeto
            Registry registry1099 = LocateRegistry.createRegistry(1099);
            registry1099.rebind("ClientGateway", (Remote) stub);
            registry1099.rebind("AdminGateway", (Remote) stub);

            System.out.println("Servidor RMI iniciado correctamente en puerto 1099.");
            System.out.println("Identificadores publicados: ClientGateway, AdminGateway");

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
