/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio.BModel;
import GSILabs.Serializable.XMLRepresentable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author alumno
 */
public class Contestacion implements XMLRepresentable{
    private Propietario dueño;
    private Review review;
    private String contestacion;
    private Date fechaEscritura;

    
    public Contestacion(Propietario dueño, Review review, String contestacion) {
        this.dueño = dueño;
        this.review = review;
        this.contestacion = contestacion;
        this.fechaEscritura = new Date();
    }
    public Contestacion()
    {
    }
    
    @Override
    public String toXML() {
        StringBuilder xml = new StringBuilder();
        xml.append("<Contestacion>\n");
        xml.append("   <Dueno id=\"").append(dueño.getID()).append("\"/>\n");
        xml.append("   <Review>\n");
        xml.append("       <Valoracion>").append(review.getValoracion()).append("</Valoracion>\n");
        xml.append("       <Comentario>").append(review.getComentario()).append("</Comentario>\n");
        xml.append("   </Review>\n");
        xml.append("   <Texto>").append(contestacion).append("</Texto>\n");
        xml.append("   <FechaEscritura>").append(fechaEscritura).append("</FechaEscritura>\n");
        xml.append("</Contestacion>\n");
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
