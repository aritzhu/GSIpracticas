package Dominio.BModel;

import Dominio.IBModelo.Local;
import Tester.java;
import java.util.List;

public class Pub extends Local implements Reservable {
    private int horaApertura;  // hora en formato 24h
    private int horaCierre;    // hora en formato 24h

    // Constructor
    public Pub(String nombre, Direccion direccion, List<Propietario> dueños, int horaApertura, int horaCierre) {
        super(nombre, direccion, dueños);
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
    }
    // Constructor
    public Pub(String nombre, Direccion direccion, List<Propietario> dueños) {
        super(nombre, direccion, dueños);
    }

    // Getters y setters
    public int getHoraApertura() {
        return horaApertura;
    }

    public void setHoraApertura(int horaApertura) {
        this.horaApertura = horaApertura;
    }

    public int getHoraCierre() {
        return horaCierre;
    }

    public void setHoraCierre(int horaCierre) {
        this.horaCierre = horaCierre;
    }
}
