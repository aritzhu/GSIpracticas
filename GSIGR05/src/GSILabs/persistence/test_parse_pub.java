/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GSILabs.persistence;
import Dominio.BModel.Pub;
import java.io.File;
import java.io.IOException;

public class test_parse_pub {

    public static void main(String[] args) {
        try {
            File xmlFile = new File("src/pruebasXML/pub.xml");

            Pub pub = XMLParser.parsePub(xmlFile);

            System.out.println("OBJETO PUB (modo files) CREADO DESDE XML");
            System.out.println("Nombre: " + pub.getNombre());
            System.out.println("Localidad: " + pub.getDireccion().getLocalidad());
            System.out.println("Provincia: " + pub.getDireccion().getProvincia());
            System.out.println("Calle: " + pub.getDireccion().getCalle());
            System.out.println("Número: " + pub.getDireccion().getNumero());
            System.out.println("Hora apertura: " + pub.getHoraApertura());
            System.out.println("Hora cierre: " + pub.getHoraCierre());
            System.out.println("Dueños: " + pub.getDueños().size());
        } 
        catch (XMLParsingException e) {
            System.err.println("Error en el XML: " + e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------------------------------------");
        try {
            String xmlContent = new String(
                java.nio.file.Files.readAllBytes(
                    java.nio.file.Paths.get("src/pruebasXML/pub.xml")
                )
            );

            Pub pub = XMLParser.parsePub(xmlContent);

            System.out.println("OBJETO PUB (modo string) DESDE STRING XML");
            System.out.println("Nombre: " + pub.getNombre());
            System.out.println("Localidad: " + pub.getDireccion().getLocalidad());
            System.out.println("Provincia: " + pub.getDireccion().getProvincia());
            System.out.println("Calle: " + pub.getDireccion().getCalle());
            System.out.println("Número: " + pub.getDireccion().getNumero());
            System.out.println("Hora apertura: " + pub.getHoraApertura());
            System.out.println("Hora cierre: " + pub.getHoraCierre());
            System.out.println("Dueños: " + pub.getDueños().size());

        } catch (XMLParsingException e) {
            System.err.println("Error en el XML: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error leyendo archivo: " + e.getMessage());
        }
    }

}
