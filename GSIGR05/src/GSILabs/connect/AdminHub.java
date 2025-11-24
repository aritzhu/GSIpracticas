package GSILabs.connect;

import Dominio.BModel.Bar;
import Dominio.BModel.Cliente;
import Dominio.IBModelo.Local;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class AdminHub {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Host del servidor: ");
        String host = sc.nextLine();

        System.out.print("Puerto del servidor: ");
        int port = Integer.parseInt(sc.nextLine());

        System.out.print("Tag del objeto remoto (AdminGateway): ");
        String tag = sc.nextLine();

        try {
            // Conectarse al registro RMI
            Registry registry = LocateRegistry.getRegistry(host, port);
            AdminGateway gateway = (AdminGateway) registry.lookup(tag);

            // Ejemplo de método: insertar review falsa en el primer bar de la lista
            LocalFinder finder = (LocalFinder) gateway;
            Local[] locales = finder.getLocals(""); // para que busque todos
            if (locales != null && locales.length > 0) {
                // Se asume que el primer local es un Bar
                Bar bar = (Bar) locales[0];
                boolean exito = gateway.insertaReviewFalsa(bar, 5);
                System.out.println("Inserción de review falsa en " + bar.getNombre() + ": " + exito);
            } else {
                System.out.println("No hay locales registrados.");
            }

        } catch (Exception e) {
            System.err.println("Error conectando con el servidor RMI:");
            e.printStackTrace();
        }
    }
}
