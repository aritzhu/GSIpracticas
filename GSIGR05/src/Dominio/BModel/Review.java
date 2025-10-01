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

public class Review {
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
    public Propietario getAutor() {
        return autor;
    }

    public Date getFechaVisita() {
        return fechaVisita;
    }
}

