/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio.IBModelo;

import Dominio.BModel.Direccion;
import Dominio.BModel.Propietario;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alumno
 */
public class Local {
    private String nombre; 
    private Direccion direccion; 
    private List<Propietario> dueños; // maximo 3 minimo 1

    public Local(String nombre, Direccion direccion,List<Propietario>  dueños) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.dueños = dueños;
    }


    public String getNombre() {
        return nombre;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public List<Propietario> getDueños() {
        return dueños;
    }

    
    
}
