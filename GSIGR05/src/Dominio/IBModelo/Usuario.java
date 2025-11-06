/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio.IBModelo;

import GSILabs.Serializable.XMLRepresentable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author alumno
 */
public class Usuario implements XMLRepresentable{
    private String ID;
    private String nick; 
    private String contrasenia;
    private int edad; // > 14
    private Date fechaNacimiento;
  
    public Usuario(String ID, String nick, String contrasenia, int edad, Date fechaNacimineto) {
        this.ID = ID;
        this.nick = nick;
        this.contrasenia = contrasenia;
        this.edad = edad;
        this.fechaNacimiento = fechaNacimineto;
    }
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    
    public boolean esMayorDeEdad() {
        if (fechaNacimiento == null) return false;
        LocalDate nacimiento = fechaNacimiento.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate();

        LocalDate hoy = LocalDate.now();

        int anos_usuario = Period.between(nacimiento, hoy).getYears();

        return anos_usuario >= 14;
    }
    
    @Override
    public String toXML() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        StringBuilder xml = new StringBuilder();
        xml.append("<Usuario id=\"").append(ID).append("\">\n");
        xml.append("   <Nick>").append(nick).append("</Nick>\n");
        xml.append("   <Edad>").append(edad).append("</Edad>\n");
        if (fechaNacimineto != null)
            xml.append("   <FechaNacimiento>").append(sdf.format(fechaNacimineto)).append("</FechaNacimiento>\n");
        xml.append("</Usuario>\n");

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
