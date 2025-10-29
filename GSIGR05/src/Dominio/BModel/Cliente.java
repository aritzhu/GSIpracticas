/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio.BModel;

import Dominio.IBModelo.Usuario;
import GSILabs.Serializable.XMLRepresentable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;


public class Cliente extends Usuario implements XMLRepresentable{ 
    private List<Review> reviews;

    public Cliente(List<Review> reviews, String ID, String nick, String contrasenia, int edad, Date fechaNacimineto) {
        super(ID, nick, contrasenia, edad, fechaNacimineto);
        this.reviews = reviews;
    }
    
    @Override
    public String toXML() {
        StringBuilder xml = new StringBuilder();

        // Usamos super.toXML() pero quitamos etiquetas de <Usuario>
        String usuarioXML = super.toXML();
        usuarioXML = usuarioXML.replaceFirst("<Usuario>", "").replaceFirst("</Usuario>$", "");

        xml.append("<Cliente id=\"").append(getID()).append("\">\n");
        xml.append(usuarioXML);

        xml.append("   <Reviews>\n");
        for (Review r : reviews) {
            xml.append("       <Review>\n");
            xml.append("           <Valoracion>").append(r.getValoracion()).append("</Valoracion>\n");
            xml.append("           <Comentario>").append(r.getComentario()).append("</Comentario>\n");
            xml.append("           <FechaVisita>").append(r.getFechaVisita()).append("</FechaVisita>\n");
            xml.append("           <FechaEscritura>").append(r.getFechaEscritura()).append("</FechaEscritura>\n");
            xml.append("           <Autor id=\"").append(r.getAutor().getID()).append("\"/>\n");
            xml.append("       </Review>\n");
        }
        xml.append("   </Reviews>\n");

        xml.append("</Cliente>\n");
        return xml.toString();
    }

    @Override
    public boolean saveToXML(File f) {
        try (FileWriter fw = new FileWriter(f)) {
            fw.write(this.toXML());
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean saveToXML(String filePath) {
        return saveToXML(new File(filePath));
    }
}
