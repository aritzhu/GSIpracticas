/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GSILabs.persistence;

import Dominio.BModel.Cliente;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author alumno
 */
public class test_parse_cliente {
    public static void main(String[] args) {
        try {
            System.out.println("Directorio actual: " + new File(".").getAbsolutePath());
            File xmlFile = new File("src/pruebasXML/cliente.xml");
            System.out.println("Ruta XML: " + xmlFile.getAbsolutePath());
            System.out.println("Existe: " + xmlFile.exists());

            Cliente cliente = XMLParser.parseCliente(xmlFile);

            System.out.println("OBJETO CLIENTE (modo files) CREADO DESDE XML");
            System.out.println("ID: " + cliente.getID());
            System.out.println("Nick: " + cliente.getNick());
            System.out.println("Contraseña: " + cliente.getContrasenia());
            System.out.println("Edad: " + cliente.getEdad());
            System.out.println("Fecha nacimiento: " + cliente.getFechaNacimiento());

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
                    java.nio.file.Paths.get("src/pruebasXML/cliente.xml")
                )
            );

            Cliente cliente = XMLParser.parseCliente(xmlContent);

            System.out.println("OBJETO CLIENTE (modo string XML)");
            System.out.println("ID: " + cliente.getID());
            System.out.println("Nick: " + cliente.getNick());
            System.out.println("Contraseña: " + cliente.getContrasenia());
            System.out.println("Edad: " + cliente.getEdad());
            System.out.println("Fecha nacimiento: " + cliente.getFechaNacimiento());

        } catch (XMLParsingException e) {
            System.err.println("Error en el XML: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error leyendo archivo: " + e.getMessage());
        }
    }
}
