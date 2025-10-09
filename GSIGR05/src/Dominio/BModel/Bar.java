/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio.BModel;

import Applicacion.Enums.EnumEspecialidadesBar;
import Dominio.IBModelo.Local;
import java.util.List;

/**
 *
 * @author alumno
 */
public class Bar extends Local implements Reservable{
    private float precioMenu;
    private List<EnumEspecialidadesBar> especialidades;

    public Bar(String nombre, Direccion dirección, List<Propietario> dueños) {
        super(nombre, dirección, dueños);
    }

}