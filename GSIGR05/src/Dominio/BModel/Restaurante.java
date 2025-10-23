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
   public Restaurante(String nombre, Direccion direccion, List<Propietario> dueños, float precioMenu, int capacidadComensales, int capacidadComensalesMesa) {
        super(nombre, direccion, dueños);
        this.precioMenu = precioMenu;
        this.capacidadComensales = capacidadComensales;
        this.capacidadComensalesMesa = capacidadComensalesMesa;
    }

    public float getPrecioMenu() {
        return precioMenu;
    }

    public void setPrecioMenu(float precioMenu) {
        this.precioMenu = precioMenu;
    }

    public int getCapacidadComensales() {
        return capacidadComensales;
    }

    public void setCapacidadComensales(int capacidadComensales) {
        this.capacidadComensales = capacidadComensales;
    }

    public int getCapacidadComensalesMesa() {
        return capacidadComensalesMesa;
    }

    public void setCapacidadComensalesMesa(int capacidadComensalesMesa) {
        this.capacidadComensalesMesa = capacidadComensalesMesa;
    }
}
