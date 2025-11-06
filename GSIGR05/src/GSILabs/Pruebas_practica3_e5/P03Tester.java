package GSILabs.Pruebas_practica3_e5;

import Applicacion.BSystem.BusinessSystem;
import GSILabs.persistence.XMLParsingException;
import java.io.File;

public class P03Tester {

    public static void main(String[] args) {
        try {
            File f = new File("src/pruebasXML/businessSystem.xml");

            // método estático
            BusinessSystem bs1 = BusinessSystem.parseXMLFile(f);
            System.out.println("Sistema cargado con parseXMLFile correctamente.");

            // método de instancia
            BusinessSystem bs2 = new BusinessSystem();
            boolean ok = bs2.loadXMLFile(f);
            System.out.println("Resultado loadXMLFile: " + ok);

        } catch (XMLParsingException e) {
            System.err.println("Error de parseo XML: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}