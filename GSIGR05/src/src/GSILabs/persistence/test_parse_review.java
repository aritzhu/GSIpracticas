/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GSILabs.persistence;

import Dominio.BModel.Review;
import java.io.File;
import java.io.IOException;

public class test_parse_review {
    public static void main(String[] args) {
        try {
            File xmlFile = new File("src/pruebasXML/review.xml");

            Review review = XMLParser.parseReview(xmlFile);

            System.out.println("OBJETO REVIEW (modo files) CREADO DESDE XML");
            System.out.println("Valoración: " + review.getValoracion());
            System.out.println("Comentario: " + review.getComentario());
            System.out.println("Fecha visita: " + review.getFechaVisita());
            System.out.println("Fecha escritura: " + review.getFechaEscritura());
            System.out.println("Autor (nombre): " + review.getAutor().getNick());
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
                    java.nio.file.Paths.get("src/pruebasXML/review.xml")
                )
            );

            Review review = XMLParser.parseReview(xmlContent);

            System.out.println("OBJETO REVIEW (modo string XML)");
            System.out.println("Valoración: " + review.getValoracion());
            System.out.println("Comentario: " + review.getComentario());
            System.out.println("Fecha visita: " + review.getFechaVisita());
            System.out.println("Fecha escritura: " + review.getFechaEscritura());
            System.out.println("Autor (nombre): " + review.getAutor().getNick());

        } catch (XMLParsingException e) {
            System.err.println("Error en el XML: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error leyendo archivo: " + e.getMessage());
        }
    }
}
