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
    public Bar(String nombre, Direccion direccion, List<Propietario> dueños, float precioMenu, List<EnumEspecialidadesBar> especialidades) {
        super(nombre, direccion, dueños);
        this.precioMenu = precioMenu;
        this.especialidades = especialidades;
    }

    // Getters y setters
    public float getPrecioMenu() {
        return precioMenu;
    }

    public void setPrecioMenu(float precioMenu) {
        this.precioMenu = precioMenu;
    }

    public List<EnumEspecialidadesBar> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<EnumEspecialidadesBar> especialidades) {
        this.especialidades = especialidades;
    }

}