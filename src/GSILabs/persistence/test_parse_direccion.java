/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GSILabs.persistence;

import Dominio.BModel.Direccion;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author alumno
 */
public class test_parse_direccion {
    public static void main(String[] args) {
        try {
            File xmlFile = new File("src/pruebasXML/direccion.xml");

            Direccion dir = XMLParser.parseDireccion(xmlFile);

            System.out.println("OBJETO DIRECCION (modo files) CREADO DESDE XML");
            System.out.println("Localidad: " + dir.getLocalidad());
            System.out.println("Provincia: " + dir.getProvincia());
            System.out.println("Calle: " + dir.getCalle());
            System.out.println("Numero: " + dir.getNumero());
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
                    java.nio.file.Paths.get("src/pruebasXML/direccion.xml")
                )
            );

            Direccion dir = XMLParser.parseDireccion(xmlContent);

            System.out.println("OBJETO DIRECCION (modo string XML)");
            System.out.println("Localidad: " + dir.getLocalidad());
            System.out.println("Provincia: " + dir.getProvincia());
            System.out.println("Calle: " + dir.getCalle());
            System.out.println("Numero: " + dir.getNumero());

        } catch (XMLParsingException e) {
            System.err.println("Error en el XML: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error leyendo archivo: " + e.getMessage());
        }
    }
}
