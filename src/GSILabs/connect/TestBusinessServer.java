package GSILabs.connect;

import Dominio.BModel.Bar;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TestBusinessServer {
    public static void main(String[] args) {
        try {
            // 1. Dirección y puerto del servidor
            String host = "localhost"; // si está en otra máquina, poner IP
            int port = 1099;

            // 2. Conectarse al registro
            Registry registry = LocateRegistry.getRegistry(host, port);

            // 3. Obtener stub remoto (ClientGateway o AdminGateway)
            Object obj = registry.lookup("ClientGateway");

            // 4. Convertir al tipo esperado
            GSILabs.connect.ClientGateway gateway = (GSILabs.connect.ClientGateway) obj;

            // 5. Llamar a algún método remoto (por ejemplo, obtener el mejor bar en Bilbao)
            Bar mejor = gateway.mejorBar("Bilbao");
            if (mejor != null) {
                System.out.println("Mejor bar en Bilbao: " + mejor.getNombre());
            } else {
                System.out.println("No hay bares en Bilbao.");
            }

        } catch (Exception e) {
            System.err.println("Error en el cliente:");
            e.printStackTrace();
        }
    }
}
