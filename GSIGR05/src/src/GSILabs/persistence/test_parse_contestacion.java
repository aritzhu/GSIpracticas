package GSILabs.persistence;

import Dominio.BModel.Contestacion;
import Dominio.BModel.Review;
import Dominio.BModel.Propietario;
import java.io.File;
import java.io.IOException;

public class test_parse_contestacion {

    public static void main(String[] args) {
        try {
            File xmlFile = new File("src/pruebasXML/contestacion.xml");


            Contestacion c = XMLParser.parseContestacion(xmlFile);

            System.out.println("OBJETO CONTESTACION (modo file) CREADO DESDE XML");
            mostrarContestacion(c);

        } catch (XMLParsingException e) {
            System.err.println("Error en el XML: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("--------------------------------------");

        try {
            String xmlContent = new String(
                java.nio.file.Files.readAllBytes(
                    java.nio.file.Paths.get("src/pruebasXML/contestacion.xml")
                )
            );

            Contestacion c = XMLParser.parseContestacion(xmlContent);

            System.out.println("OBJETO CONTESTACION (modo string) DESDE XML");
            mostrarContestacion(c);

        } catch (XMLParsingException e) {
            System.err.println("Error en el XML: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error leyendo archivo: " + e.getMessage());
        }
    }

    private static void mostrarContestacion(Contestacion c) {
        if (c == null) {
            System.out.println("Contestación nula.");
            return;
        }

        System.out.println("Texto: " + c.getContestacion());
        System.out.println("Fecha escritura: " + c.getFechaEscritura());

        // Ahora usamos los getters públicos en lugar de reflexión
        Propietario p = c.getDueño();
        if (p != null)
            System.out.println("Dueño: " + p.getNick() + " (ID: " + p.getID() + ")");
        else
            System.out.println("Dueño: (no especificado)");

        Review r = c.getReview();
        if (r != null)
            System.out.println("Review → Valoración: " + r.getValoracion() +
                               ", Comentario: " + r.getComentario());
        else
            System.out.println("Review: (no especificada)");
    }
}
