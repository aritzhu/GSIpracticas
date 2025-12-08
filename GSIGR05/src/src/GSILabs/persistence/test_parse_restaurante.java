package GSILabs.persistence;

import Dominio.BModel.Restaurante;
import java.io.File;
import java.io.IOException;

public class test_parse_restaurante {

    public static void main(String[] args) {
        try {
            File xmlFile = new File("src/pruebasXML/restaurante.xml");

            Restaurante r = XMLParser.parseRestaurante(xmlFile);

            System.out.println("OBJETO RESTAURANTE (modo files) CREADO DESDE XML");
            System.out.println("Nombre: " + r.getNombre());
            System.out.println("Precio menú: " + r.getPrecioMenu());
            System.out.println("Capacidad comensales total: " + r.getCapacidadComensales());
            System.out.println("Capacidad comensales mesa: " + r.getCapacidadComensalesMesa());
            System.out.println("Localidad: " + r.getDireccion().getLocalidad());
            System.out.println("Provincia: " + r.getDireccion().getProvincia());
            System.out.println("Calle: " + r.getDireccion().getCalle());
            System.out.println("Número: " + r.getDireccion().getNumero());
            System.out.println("Dueños: " + r.getDueños().size());
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
                    java.nio.file.Paths.get("src/pruebasXML/restaurante.xml")
                )
            );

            Restaurante r = XMLParser.parseRestaurante(xmlContent);

            System.out.println("OBJETO RESTAURANTE (modo string) DESDE STRING XML");
            System.out.println("Nombre: " + r.getNombre());
            System.out.println("Precio menú: " + r.getPrecioMenu());
            System.out.println("Capacidad comensales total: " + r.getCapacidadComensales());
            System.out.println("Capacidad comensales mesa: " + r.getCapacidadComensalesMesa());
            System.out.println("Localidad: " + r.getDireccion().getLocalidad());
            System.out.println("Provincia: " + r.getDireccion().getProvincia());
            System.out.println("Calle: " + r.getDireccion().getCalle());
            System.out.println("Número: " + r.getDireccion().getNumero());
            System.out.println("Dueños: " + r.getDueños().size());

        } catch (XMLParsingException e) {
            System.err.println("Error en el XML: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error leyendo archivo: " + e.getMessage());
        }
    }
}
