/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio.BModel;
import Dominio.IBModelo.Local;
import Dominio.IBModelo.Usuario;
import java.util.Date;
import java.util.List;

/**
 *
 * @author alumno
 */
public class Propietario extends Usuario {
    private List<Local> locales;
    private List<Contestacion> contestaciones;
    public Propietario(List<Review> reviews, String ID, String nick, String contrasenia, int edad, Date fechaNacimineto) {
        super(ID, nick, contrasenia, edad, fechaNacimineto);
        this.locales = locales;
        this.contestaciones = contestaciones;
    }
}
