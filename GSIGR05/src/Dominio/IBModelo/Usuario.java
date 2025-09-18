/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio.IBModelo;

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
}
