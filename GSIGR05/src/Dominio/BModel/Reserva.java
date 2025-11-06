/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio.BModel;

import Dominio.IBModelo.Local;
import java.util.Date;

/**
 *
 * @author alumno
 */
public class Reserva {
    private Date fechaReserva;
    private int descuento;
    private Cliente cliente;
    private Local local;

    public Reserva() {
    }

    public Reserva(Date fechaReserva, int descuento, Cliente cliente, Local local) {
        this.fechaReserva = fechaReserva;
        this.descuento = descuento;
        this.cliente = cliente;
        this.local = local;
    }

    public Date getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(Date fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }
    
    
}
