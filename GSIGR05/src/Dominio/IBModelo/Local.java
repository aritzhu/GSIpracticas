/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio.IBModelo;

import Dominio.BModel.Direccion;
import Dominio.BModel.Propietario;

import Dominio.BModel.Review;
import GSILabs.Serializable.XMLRepresentable;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import java.util.List;

/**
 *
 * @author alumno
 */
public class Local implements XMLRepresentable, java.io.Serializable{
    private static final long serialVersionUID = 1L;
    private String nombre; 
    private Direccion direccion; 
    private List<Propietario> dueños; // maximo 3 minimo 1
    public List<Review> reviews;
    public boolean esReservable;

    public String getNombre() {
        return nombre;
    }
    public boolean esReservable(){
        return esReservable;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public List<Propietario> getDueños() {
        return dueños;
    }
    
    public Local(String nombre, Direccion direccion, List<Propietario> dueños) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.dueños = dueños;
        this.reviews = new ArrayList<>();
    }
    
    public boolean dueñosNecesarios() {
        int cantidad = this.dueños.size();
        return cantidad >= 1 && cantidad <= 3;
    }
    
   public void anadirDueno(Propietario nuevoDueno) {
        if (this.dueños.size() < 3) {
            this.dueños.add(nuevoDueno);
        } else {
            System.out.println("No se pueden añadir más de 3 dueños a este local.");
            System.out.println("Numero de dueños del local: " + dueños.size());
        }
    }
   
   public boolean anadirReview(Review nuevaReview) {
        // Recorremos las reviews existentes
        for (Review r : reviews) {
            // Mismo autor
            if (r.getAutor().equals(nuevaReview.getAutor())) {
                // Mismo día
                if (esMismoDia(r.getFechaVisita(), nuevaReview.getFechaVisita())) {
                    System.out.println("Ya existe una review de este usuario en la misma fecha.");
                    return false;
                }
            }
        }
        reviews.add(nuevaReview);
        return true;
    }
     private boolean esMismoDia(Date fecha1, Date fecha2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(fecha1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(fecha2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    public List<Review> getReviews() {
        return reviews;
    }
    
    
    @Override
    public String toXML() {
        StringBuilder xml = new StringBuilder();
        xml.append("<Local>\n");
        xml.append("   <Nombre>").append(nombre).append("</Nombre>\n");
        if (direccion != null)
            xml.append(direccion.toXML()); // asumiendo que Direccion tiene toXML()

        // Dueños
        xml.append("   <Propietarios>\n");
        if (dueños != null) {
            for (Propietario p : dueños) {
                xml.append("       <Propietario id=\"").append(p.getID()).append("\"/>\n");
            }
        }
        xml.append("   </Propietarios>\n");

        // Reviews
        xml.append("   <Reviews>\n");
        if (reviews != null) {
            for (Review r : reviews) {
                xml.append(r.toXML());
            }
        }
        xml.append("   </Reviews>\n");

        xml.append("</Local>\n");
        return xml.toString();
    }

    @Override
    public boolean saveToXML(File f) {
        try (FileWriter fw = new FileWriter(f)) {
            fw.write(this.toXML());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean saveToXML(String filePath) {
        return saveToXML(new File(filePath));
    }

     public void addReview(Review rv){
         reviews.add(rv);
     };
    
}
