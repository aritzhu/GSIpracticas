/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio.IBModelo;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author alumno
 */
public class Usuario {
    private String ID;
    private String nick; 
    private String contrasenia;
    private int edad; // > 14
    private Date fechaNacimineto;
  
    public Usuario(String ID, String nick, String contrasenia, int edad, Date fechaNacimineto) {
        this.ID = ID;
        this.nick = nick;
        this.contrasenia = contrasenia;
        this.edad = edad;
        this.fechaNacimineto = fechaNacimineto;
    }
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Date getFechaNacimineto() {
        return fechaNacimineto;
    }

    public void setFechaNacimineto(Date fechaNacimineto) {
        this.fechaNacimineto = fechaNacimineto;
    }

    
    public boolean esMayorDeEdad() {
        if (fechaNacimineto == null) return false;
        LocalDate nacimiento = fechaNacimineto.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate();

        LocalDate hoy = LocalDate.now();

        int anos_usuario = Period.between(nacimiento, hoy).getYears();

        return anos_usuario >= 14;
    }
    
    
}
