/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GSILabs.persistence;
import Dominio.BModel.Bar;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author inaki
 */
public class test_parse_bar {
    public static void main(String[] args) {
        try {
            File xmlFile = new File("src/pruebasXML/bar.xml");


            Bar bar = XMLParser.parseBar(xmlFile);

            System.out.println("OBJETO BAR (modo files) CREADO DESDE XML");
            System.out.println("Nombre: " + bar.getNombre());
            System.out.println("Precio menú: " + bar.getPrecioMenu());
            System.out.println("Localidad: " + bar.getDireccion().getLocalidad());
            System.out.println("Provincia: " + bar.getDireccion().getProvincia());
            System.out.println("Calle: " + bar.getDireccion().getCalle());
            System.out.println("Número: " + bar.getDireccion().getNumero());
            System.out.println("Especialidades: " + bar.getEspecialidades());
            System.out.println("Propietarios: " + bar.getDueños().size());
        } 
        catch (XMLParsingException e) {
            System.err.println("Error en el XML: " + e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------------------------------------");
        try {
            // 1️⃣ Leer todo el XML a String (usando la clase Files de NIO)
            String xmlContent = new String(
                java.nio.file.Files.readAllBytes(
                    java.nio.file.Paths.get("src/pruebasXML/bar.xml")
                )
            );

            // 2️⃣ Parsear el XML desde el String (sin usar File)
            Bar bar = XMLParser.parseBar(xmlContent);

            // 3️⃣ Mostrar resultados
            System.out.println("OBJETO BAR (modo string) DESDE STRING XML");
            System.out.println("Nombre: " + bar.getNombre());
            System.out.println("Precio menú: " + bar.getPrecioMenu());
            System.out.println("Localidad: " + bar.getDireccion().getLocalidad());
            System.out.println("Provincia: " + bar.getDireccion().getProvincia());
            System.out.println("Calle: " + bar.getDireccion().getCalle());
            System.out.println("Número: " + bar.getDireccion().getNumero());
            System.out.println("Especialidades: " + bar.getEspecialidades());
            System.out.println("Propietarios: " + bar.getDueños().size());

        } catch (XMLParsingException e) {
            System.err.println("Error en el XML: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error leyendo archivo: " + e.getMessage());
        }
    }
}
