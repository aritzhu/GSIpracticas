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
public class Reserva implements XMLRepresentable{
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
        xml.append("    <fechaReserva>").append(sdf.format(fechaReserva)).append("</fechaReserva>\n");
        xml.append("    <descuento>").append(descuento).append("</descuento>\n\n");

        if (cliente != null) {
            xml.append("    <cliente>\n");
            xml.append("        <id>").append(cliente.getID()).append("</id>\n");
            xml.append("        <nick>").append(cliente.getNick()).append("</nick>\n");
            xml.append("        <contrasenia>").append(cliente.getContrasenia()).append("</contrasenia>\n");
            xml.append("        <edad>").append(cliente.getEdad()).append("</edad>\n");
            xml.append("        <fechaNacimiento>")
               .append(sdf.format(cliente.getFechaNacimiento()))
               .append("</fechaNacimiento>\n");
            xml.append("    </cliente>\n\n");
        }

        if (local != null) {
            xml.append("    <local>\n");
            xml.append("        <nombre>").append(local.getNombre()).append("</nombre>\n");

            if (local.getDireccion() != null) {
                xml.append("        <direccion>\n");
                xml.append("            <localidad>").append(local.getDireccion().getLocalidad()).append("</localidad>\n");
                xml.append("            <provincia>").append(local.getDireccion().getProvincia()).append("</provincia>\n");
                xml.append("            <calle>").append(local.getDireccion().getCalle()).append("</calle>\n");
                xml.append("            <numero>").append(local.getDireccion().getNumero()).append("</numero>\n");
                xml.append("        </direccion>\n");
            }

            xml.append("    </local>\n");
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
