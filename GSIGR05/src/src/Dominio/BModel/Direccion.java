/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio.BModel;

import GSILabs.Serializable.XMLRepresentable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author alumno
 */
public class Direccion implements XMLRepresentable, java.io.Serializable{
    private static final long serialVersionUID = 1L;
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

    
    public String getLocalidad() {
        return localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getCalle() {
        return calle;
    }

    public int getNumero() {
        return numero;
    }
    
    public String toXML() {
    StringBuilder xml = new StringBuilder();
    xml.append("<Direccion>\n");
    xml.append("   <Calle>").append(calle).append("</Calle>\n");
    xml.append("   <Numero>").append(numero).append("</Numero>\n");
    xml.append("   <Localidad>").append(localidad).append("</Localidad>\n");
    xml.append("   <Provincia>").append(provincia).append("</Provincia>\n");
    xml.append("</Direccion>\n");
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
}
