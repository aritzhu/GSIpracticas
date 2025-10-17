package GSIlabs.Misc;

import Applicacion.BSystem.BusinessSystem;
import Dominio.BModel.Bar;

import java.io.File;
import java.net.URISyntaxException;

public class SSTest05 {

    public static void main(String[] args) throws URISyntaxException {
        // Inicialización
        BusinessSystem bs = new BusinessSystem();

        // Ruta al fichero ODS
        File file = null;
        if (SSTest05.class.getResource("/P02Ej05.ods") != null) {
            file = new File(SSTest05.class.getResource("/P02Ej05.ods").toURI());
        } else {
            System.out.println("❌ No se encontró el archivo P02Ej05.ods en el classpath.");
            return;
        }

        // Importar bares
        int numImportados = bs.importaPubs(file);
        System.out.println("Número de bares importados: " + numImportados);

        // Listar todos los bares importados
        Bar[] bares = bs.listarBares("", "");
        System.out.println("Bares actualmente en el sistema:");
        System.out.println("----------------------------------");
        for (Bar b : bares) {
            System.out.println("Nombre: " + b.getNombre());
            System.out.println("Ciudad: " + b.getDireccion().getLocalidad());
            System.out.println("Provincia: " + b.getDireccion().getProvincia());
            System.out.println("Calle: " + b.getDireccion().getCalle());
            System.out.print("Especialidades: ");
            b.getEspecialidades().forEach(e -> System.out.print(e.name() + " "));
            System.out.println("\nPropietarios: ");
            System.out.println(String.join(", ", b.getDueños().stream().map(p -> p.getNick()).toArray(String[]::new)));
            System.out.println("---------------------------");
        }
    }
}
