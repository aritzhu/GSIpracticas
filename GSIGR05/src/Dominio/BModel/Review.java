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

public class Review implements XMLRepresentable{
    private int valoracion;
    private String comentario;
    private Date fechaVisita;
    private Date fechaEscritura;

   private Propietario autor;

    public Review(int valoracion, String comentario, Date fechaVisita, Propietario autor) {
        if (comentario.length() <= 500) {
            this.valoracion = valoracion;
            this.comentario = comentario;
            this.fechaVisita = fechaVisita;
            this.fechaEscritura = new Date();
            this.autor = autor;
        } else {
            System.out.println("Comentario demasiado largo");
        }
    }

    public Review() {
    }
    
    
     public int getValoracion() {
        return valoracion;
    }

    public String getComentario() {
        return comentario;
    }

    public Date getFechaVisita() {
        return fechaVisita;
    }

    public Date getFechaEscritura() {
        return fechaEscritura;
    }

    public Propietario getAutor() {
        return autor;
    }
    
    @Override
    public String toXML() {
        StringBuilder xml = new StringBuilder();
        xml.append("<Review>\n");
        xml.append("   <Valoracion>").append(valoracion).append("</Valoracion>\n");
        xml.append("   <Comentario>").append(comentario).append("</Comentario>\n");
        if (fechaVisita != null)
            xml.append("   <FechaVisita>").append(fechaVisita).append("</FechaVisita>\n");
        if (fechaEscritura != null)
            xml.append("   <FechaEscritura>").append(fechaEscritura).append("</FechaEscritura>\n");
        if (autor != null)
            xml.append("   <Autor id=\"").append(autor.getID()).append("\"/>\n");
        xml.append("</Review>\n");
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

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public void setFechaEscritura(Date fechaEscritura) {
        this.fechaEscritura = fechaEscritura;
    }
    
    
}

