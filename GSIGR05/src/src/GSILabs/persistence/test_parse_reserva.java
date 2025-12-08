/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GSILabs.persistence;

import Dominio.BModel.Reserva;
import Dominio.BModel.Cliente;
import Dominio.IBModelo.Local;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author alumno
 */
public class test_parse_reserva {
    public static void main(String[] args) {

        try {
            System.out.println("Directorio actual: " + new File(".").getAbsolutePath());
            File xmlFile = new File("src/pruebasXML/reserva.xml");
            System.out.println("Ruta XML: " + xmlFile.getAbsolutePath());
            System.out.println("Existe: " + xmlFile.exists());

            Reserva r = XMLParser.parseReserva(xmlFile);

            System.out.println("OBJETO RESERVA (modo file) CREADO DESDE XML");
            System.out.println("Fecha reserva: " + r.getFechaReserva());
            System.out.println("Descuento: " + r.getDescuento());
            System.out.println("Cliente nick: " + r.getCliente().getNick());
            System.out.println("Local nombre: " + r.getLocal().getNombre());

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
                            java.nio.file.Paths.get("src/pruebasXML/reserva.xml")
                    )
            );

            Reserva r = XMLParser.parseReserva(xmlContent);

            System.out.println("OBJETO RESERVA (modo string XML)");
            System.out.println("Fecha reserva: " + r.getFechaReserva());
            System.out.println("Descuento: " + r.getDescuento());
            System.out.println("Cliente nick: " + r.getCliente().getNick());
            System.out.println("Local nombre: " + r.getLocal().getNombre());

        } catch (XMLParsingException e) {
            System.err.println("Error en el XML: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error leyendo archivo: " + e.getMessage());
        }
    }
}
