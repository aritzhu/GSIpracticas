/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GSILabs.persistence;

import Dominio.BModel.Propietario;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author inaki
 */
public class test_parse_propietario {

    public static void main(String[] args) {
        try {
            File xmlFile = new File("src/pruebasXML/propietario.xml");

            Propietario propietario = XMLParser.parsePropietario(xmlFile);

            System.out.println("OBJETO PROPIETARIO (modo file) CREADO DESDE XML");
            System.out.println("ID: " + propietario.getID());
            System.out.println("Nick: " + propietario.getNick());
            System.out.println("Contraseña: " + propietario.getContrasenia());
            System.out.println("Edad: " + propietario.getEdad());
            System.out.println("Fecha nacimiento: " + propietario.getFechaNacimiento());
            System.out.println("Locales: " + (propietario.getLocales() != null ? propietario.getLocales().size() : 0));
            System.out.println("Contestaciones: " + (propietario.getContestaciones() != null ? propietario.getContestaciones().size() : 0));

        } catch (XMLParsingException e) {
            System.err.println("Error en el XML: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("--------------------------------------");

        try {
            // 1️⃣ Leer todo el XML a String
            String xmlContent = new String(
                java.nio.file.Files.readAllBytes(
                    java.nio.file.Paths.get("src/pruebasXML/propietario.xml")
                )
            );

            // 2️⃣ Parsear desde String
            Propietario propietario = XMLParser.parsePropietario(xmlContent);

            // 3️⃣ Mostrar resultados
            System.out.println("OBJETO PROPIETARIO (modo string) DESDE STRING XML");
            System.out.println("ID: " + propietario.getID());
            System.out.println("Nick: " + propietario.getNick());
            System.out.println("Contraseña: " + propietario.getContrasenia());
            System.out.println("Edad: " + propietario.getEdad());
            System.out.println("Fecha nacimiento: " + propietario.getFechaNacimiento());
            System.out.println("Locales: " + (propietario.getLocales() != null ? propietario.getLocales().size() : 0));
            System.out.println("Contestaciones: " + (propietario.getContestaciones() != null ? propietario.getContestaciones().size() : 0));

        } catch (XMLParsingException e) {
            System.err.println("Error en el XML: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error leyendo archivo: " + e.getMessage());
        }
    }
}
