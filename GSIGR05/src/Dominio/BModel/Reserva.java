/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio.BModel;

import Dominio.IBModelo.Local;
import GSILabs.Serializable.XMLRepresentable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author alumno
 */
public class Reserva implements XMLRepresentable, java.io.Serializable{
    private static final long serialVersionUID = 1L;
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
    
    @Override
public String toXML() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    StringBuilder xml = new StringBuilder();

    xml.append("<Reserva>\n");
    xml.append("    <FechaReserva>").append(sdf.format(fechaReserva)).append("</FechaReserva>\n");
    xml.append("    <Descuento>").append(descuento).append("</Descuento>\n\n");

    if (cliente != null) {
        xml.append("    <Cliente>\n");
        xml.append("        <Id>").append(cliente.getID()).append("</Id>\n");
        xml.append("        <Nick>").append(cliente.getNick()).append("</Nick>\n");
        xml.append("        <Contrasenia>").append(cliente.getContrasenia()).append("</Contrasenia>\n");
        xml.append("        <Edad>").append(cliente.getEdad()).append("</Edad>\n");
        xml.append("        <FechaNacimiento>")
           .append(sdf.format(cliente.getFechaNacimiento()))
           .append("</FechaNacimiento>\n");
        xml.append("    </Cliente>\n\n");
    }

    xml.append("</Reserva>");
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
