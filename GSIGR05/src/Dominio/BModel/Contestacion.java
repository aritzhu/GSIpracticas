/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio.BModel;
import java.util.Date;

/**
 *
 * @author alumno
 */
public class Contestacion {
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
    
    
}
