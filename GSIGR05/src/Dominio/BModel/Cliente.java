/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio.BModel;

import Dominio.IBModelo.Usuario;
import java.util.Date;
import java.util.List;


public class Cliente extends Usuario { 
    private List<Review> reviews;

    public Cliente(List<Review> reviews, String ID, String nick, String contrasenia, int edad, Date fechaNacimineto) {
        super(ID, nick, contrasenia, edad, fechaNacimineto);
        this.reviews = reviews;
    }
}
