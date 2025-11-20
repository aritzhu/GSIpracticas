/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Applicacion.BSystem;

import Dominio.BModel.Contestacion;
import Dominio.BModel.Reserva;
import Dominio.BModel.Review;
import Dominio.IBModelo.Local;
import Dominio.IBModelo.Usuario;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alumno
 */
public class EjecuctionTimeDataBase {
    private List<Usuario> usuarios;
    private List<Local> locales;
    private List<Reserva> reservas;
    private List<Contestacion> contestaciones;
    private List<Review> reviews;

    public EjecuctionTimeDataBase() {
        this.usuarios = new ArrayList<>();
        this.locales = new ArrayList<>();
        this.reservas = new ArrayList<>();
        this.contestaciones = new ArrayList<>();
        this.reviews = new ArrayList<>();
        
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public List<Local> getLocales() {
        return locales;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public List<Contestacion> getContestaciones() {
        return contestaciones;
    }

    public List<Review> getReviews() {
        return reviews;
    }
    
    
}
