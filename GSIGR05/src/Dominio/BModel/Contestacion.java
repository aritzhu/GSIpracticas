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
public class Contestacion implements XMLRepresentable, java.io.Serializable{
    private static final long serialVersionUID = 1L;
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
        xml.append("   <Propietario id=\"").append(dueño.getID()).append("\"/>\n");
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
    public Propietario getDueño() {
    return this.dueño;
}

public Review getReview() {
    return this.review;
}

    public void setDueño(Propietario dueño) {
        this.dueño = dueño;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public void setContestacion(String contestacion) {
        this.contestacion = contestacion;
    }

    public void setFechaEscritura(Date fechaEscritura) {
        this.fechaEscritura = fechaEscritura;
    }


public String getContestacion() {
    return this.contestacion;
}

public java.util.Date getFechaEscritura() {
    return this.fechaEscritura;
}
}
