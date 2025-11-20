package GSILabs.Pruebas_practica3_e5;

import Applicacion.BSystem.BusinessSystem;
import GSILabs.persistence.XMLParsingException;
import java.io.File;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class P03Tester {

    public static void main(String[] args) {
        try {
            // Ruta del archivo XML original
            File f = new File("src/pruebasXML/businessSystem.xml");

            // Método estático
            BusinessSystem bs1 = BusinessSystem.parseXMLFile(f);
            System.out.println("Sistema cargado con parseXMLFile correctamente.");

            // Guardar el XML de bs1
            File outputFile1 = new File("src/pruebasXML/businessSystem_output_bs1.xml");
            if (bs1.saveToXML(outputFile1)) {
                System.out.println("Archivo XML de bs1 guardado correctamente.");
            } else {
                System.err.println("Error al guardar el archivo XML de bs1.");
            }
            System.out.println("*****************************************");
            // Método de instancia
            BusinessSystem bs2 = new BusinessSystem();
            boolean ok = bs2.loadXMLFile(f);
            System.out.println("Resultado loadXMLFile: " + ok);

            // Guardar el XML de bs2
            File outputFile2 = new File("src/pruebasXML/businessSystem_output_bs2.xml");
            if (bs2.saveToXML(outputFile2)) {
                System.out.println("Archivo XML de bs2 guardado correctamente.");
            } else {
                System.err.println("Error al guardar el archivo XML de bs2.");
            }

        } catch (XMLParsingException e) {
            System.err.println("Error de parseo XML: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
