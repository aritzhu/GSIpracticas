package GSILabs.connect;

import Dominio.BModel.Bar;
import Dominio.BModel.Restaurante;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClientHub {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Host del servidor: ");
        String host = sc.nextLine();

        System.out.print("Puerto del servidor: ");
        int port = Integer.parseInt(sc.nextLine());

        System.out.print("Tag del objeto remoto (ClientGateway): ");
        String tag = sc.nextLine();

        try {
            Registry registry = LocateRegistry.getRegistry(host, port);
            ClientGateway gateway = (ClientGateway) registry.lookup(tag);

            // Ejemplo de método: mejor bar en Bilbao
            Bar mejor = gateway.mejorBar("Bilbao");
            if (mejor != null) {
                System.out.println("Mejor bar en Bilbao: " + mejor.getNombre());
            } else {
                System.out.println("No hay bares en Bilbao.");
            }

            // Ejemplo de método: top 2 restaurantes en Bilbao
            Restaurante[] top = gateway.mejoresRestaurantes("Bilbao", 2);
            System.out.println("Mejores restaurantes en Bilbao:");
            for (Restaurante r : top) {
                if (r != null)
                    System.out.println(" - " + r.getNombre());
            }

        } catch (Exception e) {
            System.err.println("Error conectando con el servidor RMI:");
            e.printStackTrace();
        }
    }
}
