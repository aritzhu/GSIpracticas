/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio.BModel;

/**
 *
 * @author alumno
 */
public class Direccion {
    private String localidad; 
    private String provincia; 
    private String calle;
    private int numero; 

    public Direccion(String localidad, String provincia, String calle, int numero) {
        this.localidad = localidad;
        this.provincia = provincia;
        this.calle = calle;
        this.numero = numero;
    }
    
    
}
