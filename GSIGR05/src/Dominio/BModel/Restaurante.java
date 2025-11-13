/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio.BModel;

import Dominio.IBModelo.Local;
import GSILabs.Serializable.XMLRepresentable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
/**
 *
 * @author alumno
 */
public class Restaurante extends Local implements Reservable, XMLRepresentable{
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
    
    @Override
    public String toXML() {
        StringBuilder xml = new StringBuilder();
        xml.append("<Restaurante>\n");

        // Reutilizamos XML de Local
        String localXML = super.toXML();
        localXML = localXML.replaceFirst("<Local>", "").replaceFirst("</Local>$", "");
        xml.append(localXML);

        // Atributos específicos de Restaurante
        xml.append("   <PrecioMenu>").append(precioMenu).append("</PrecioMenu>\n");
        xml.append("   <CapacidadComensales>").append(capacidadComensales).append("</CapacidadComensales>\n");
        xml.append("   <CapacidadComensalesMesa>").append(capacidadComensalesMesa).append("</CapacidadComensalesMesa>\n");

        xml.append("</Restaurante>\n");
        return xml.toString();
    }

    @Override
    public boolean saveToXML(File f) {
        try (FileWriter fw = new FileWriter(f)) {
            fw.write(this.toXML());
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean saveToXML(String filePath) {
        return saveToXML(new File(filePath));
    }

    public void addReview(Review rv) {
        // Asegurarse de que la review no sea nula antes de agregarla
        if (rv != null) {
            this.reviews.add(rv);  // Añadir la review a la lista de reviews
        }
    }
}
