/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio.IBModelo;

import Dominio.BModel.Direccion;
import Dominio.BModel.Propietario;
import Dominio.BModel.Review;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author alumno
 */
public class Local {
    private String nombre; 
    private Direccion dirección; 
    
    private List<Propietario> dueños; // maximo 3 minimo 1
    
    private List<Review> reviews;

    public Local(String nombre, Direccion dirección, List<Propietario> dueños) {
        this.nombre = nombre;
        this.dirección = dirección;
        this.dueños = dueños;
        this.reviews = new ArrayList<>();
    }
    
    public boolean dueñosNecesarios() {
        int cantidad = this.dueños.size();
        return cantidad >= 1 && cantidad <= 3;
    }
    
   public void anadirDueño(Propietario nuevoDueno) {
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
    
}
