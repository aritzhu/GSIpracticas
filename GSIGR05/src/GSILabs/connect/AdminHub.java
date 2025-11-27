package GSILabs.connect;

import Dominio.BModel.Bar;
import Dominio.BModel.Cliente;
import Dominio.BModel.Review;
import Dominio.IBModelo.Local;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class AdminHub {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Host del servidor: ");
        String host = sc.nextLine();

        System.out.print("Puerto del servidor: ");
        int port = Integer.parseInt(sc.nextLine());

        System.out.print("Tag del objeto remoto (AdminGateway o ): ");
        String tag = sc.nextLine();

        try {
            // Conectarse al registro RMI
            Registry registry = LocateRegistry.getRegistry(host, port);
            AdminGateway gateway = (AdminGateway) registry.lookup(tag);

            LocalFinder finder = (LocalFinder) gateway;
            Local[] locales = finder.getLocals("");
            if (locales != null && locales.length > 0) {
                Bar bar = (Bar) locales[0];
                boolean exito = gateway.insertaReviewFalsa(bar, 5);
                System.out.println("Inserción de review falsa en " + bar.getNombre() + ": " + exito);
                // Recargar el local desde el servidor (evita usar la copia vieja)
                Local refreshed = finder.getLocal(bar.getNombre());
                List<Review> rvs = refreshed.getReviews();

                System.out.println("\nReviews ACTUALIZADAS desde el servidor:");
                for (Review r : rvs) {
                    System.out.println(r);
                }

            } else {
                System.out.println("No hay locales registrados.");
            }

        } catch (Exception e) {
            System.err.println("Error conectando con el servidor RMI:");
            e.printStackTrace();
        }
    }
}
