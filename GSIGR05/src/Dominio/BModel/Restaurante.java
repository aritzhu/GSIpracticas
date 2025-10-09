/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio.BModel;

import Dominio.IBModelo.Local;
import java.util.List;
/**
 *
 * @author alumno
 */
public class Restaurante extends Local implements Reservable{
    private float precioMenu;
    private int capacidadComensales;
    private int capacidadComensalesMesa;

    public Restaurante(String nombre, Direccion dirección, List<Propietario> dueños) {
        super(nombre, dirección, dueños);
    }
}
