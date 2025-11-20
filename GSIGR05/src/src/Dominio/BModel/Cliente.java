/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio.BModel;

import Dominio.IBModelo.Usuario;
import GSILabs.Serializable.XMLRepresentable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class Cliente extends Usuario implements XMLRepresentable{ 
    private List<Review> reviews;
    private final boolean esCliente = true;

    public Cliente(List<Review> reviews, String ID, String nick, String contrasenia, int edad, Date fechaNacimineto) {
        super(ID, nick, contrasenia, edad, fechaNacimineto);
        this.reviews = reviews;
    }
    public boolean esCliente(){
        return esCliente;
    }
    
    @Override
    public String toXML() {
        StringBuilder xml = new StringBuilder();

        // Generamos XML para el Usuario (heredado)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        xml.append("<Cliente id=\"").append(getID()).append("\">\n");
        xml.append("   <Nick>").append(getNick()).append("</Nick>\n");
        xml.append("   <Edad>").append(getEdad()).append("</Edad>\n");
        if (getFechaNacimiento() != null) {
            xml.append("   <FechaNacimiento>").append(sdf.format(getFechaNacimiento())).append("</FechaNacimiento>\n");
        }

        xml.append("</Cliente>\n");

        return xml.toString();
    }
    
    public String toXML(int level) {
        String indent = "   ".repeat(level); // 3 espacios por nivel
        StringBuilder xml = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        xml.append(indent).append("<Cliente id=\"").append(getID()).append("\">\n");
        xml.append(indent).append("   <Nick>").append(getNick()).append("</Nick>\n");
        xml.append(indent).append("   <Edad>").append(getEdad()).append("</Edad>\n");
        if (getFechaNacimiento() != null) {
            xml.append(indent).append("   <FechaNacimiento>").append(sdf.format(getFechaNacimiento()))
               .append("</FechaNacimiento>\n");
        }
        xml.append(indent).append("</Cliente>\n");

        return xml.toString();
    }

    @Override
    public boolean saveToXML(File f) {
        try (FileWriter fw = new FileWriter(f)) {
            fw.write(this.toXML());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean saveToXML(String filePath) {
        return saveToXML(new File(filePath));
    }
}
