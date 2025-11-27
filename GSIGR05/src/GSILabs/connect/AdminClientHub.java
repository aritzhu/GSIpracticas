/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GSILabs.connect;

import Dominio.BModel.Bar;
import Dominio.BModel.Restaurante;
import Dominio.BModel.Review;
import Dominio.IBModelo.Local;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author alumno
 */
public class AdminClientHub {public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);

        System.out.print("Host del servidor: ");
        String host = sc.nextLine();

        System.out.print("Puerto del servidor: ");
        int port = Integer.parseInt(sc.nextLine());

        System.out.print("Tag del objeto remoto (AdminGateway o ClientGateway): ");
        String tag = sc.nextLine();
        if (tag.equals("AdminGateway")){
            while (true){
                try {
                    Registry registry = LocateRegistry.getRegistry(host, port);
                    AdminGateway gateway = (AdminGateway) registry.lookup(tag);

                    LocalFinder finder = (LocalFinder) gateway;
                    Local[] locales = finder.getLocals("");
                    if (locales != null && locales.length > 0) {
                        Bar bar = (Bar) locales[0];
                        boolean exito = gateway.insertaReviewFalsa(bar, 5);
                        System.out.println("Inserción de review falsa en " + bar.getNombre() + ": " + exito);

                        Local refreshed = finder.getLocal(bar.getNombre());
                        List<Review> rvs = refreshed.getReviews();

                        System.out.println("\nReviews ACTUALIZADAS desde el servidor:");
                        for (Review r : rvs) {
                            System.out.println(r);
                        }

                    } else {
                        System.out.println("No hay locales registrados.");
                    }
                    break;

                } catch (Exception e) {
                    System.err.println("Error conectando con el servidor RMI. Intentando nuevamente...");
                    Thread.sleep(2000);
                }
            }
        }
        else if (tag.equals("ClientGateway")){
        while (true) {
            try {
                Registry registry = LocateRegistry.getRegistry(host, port);
                ClientGateway gateway = (ClientGateway) registry.lookup(tag);
                Bar mejor = gateway.mejorBar("Bilbao");

                if (mejor != null) {
                    System.out.println("Mejor bar en Bilbao: " + mejor.getNombre());
                } else {
                    System.out.println("No hay bares en Bilbao.");
                }

                Restaurante[] top = gateway.mejoresRestaurantes("Bilbao", 2);
                System.out.println("Mejores restaurantes en Bilbao:");
                for (Restaurante r : top) {
                    if (r != null)
                        System.out.println(" - " + r.getNombre());
                }

                break;
            } catch (Exception e) {
                System.err.println("Error conectando con el servidor RMI. Intentando nuevamente...");
                Thread.sleep(2000);
            }
        }
    }
        else{
            System.out.println("Tag erroneo");
        }
    }
}
