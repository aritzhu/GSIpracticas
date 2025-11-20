/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio.BModel;
import Dominio.IBModelo.Local;
import Dominio.IBModelo.Usuario;
import GSILabs.Serializable.XMLRepresentable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author alumno
 */
public class Propietario extends Usuario implements XMLRepresentable{
    private List<Local> locales;
    
    private final boolean esCliente = false;
    private List<Contestacion> contestaciones;
    
    public Propietario(List<Contestacion> contestaciones, String ID, String nick, String contrasenia, int edad, Date fechaNacimineto) {
        super(ID, nick, contrasenia, edad, fechaNacimineto);
        this.contestaciones = contestaciones;
    }
    public Propietario(String ID, String nick, String contrasenia, int edad, Date fechaNacimineto) {
        super(ID, nick, contrasenia, edad, fechaNacimineto);
    }

    public Propietario(List<Local> locales, List<Contestacion> contestaciones, String ID, String nick, String contrasenia, int edad, Date fechaNacimineto) {
        super(ID, nick, contrasenia, edad, fechaNacimineto);
        this.locales = locales;
        this.contestaciones = contestaciones;
    }
    
    
    public boolean esCliente(){
        return esCliente;
    }
    
     @Override
    public String toXML() {
        StringBuilder xml = new StringBuilder();

        // Usuario base
        String usuarioXML = super.toXML();
        usuarioXML = usuarioXML.replaceFirst("<Usuario[^>]*>", "").replaceFirst("</Usuario>$", "");

        xml.append("<Propietario id=\"").append(getID()).append("\">\n");
        xml.append(usuarioXML);

        xml.append("</Propietario>\n");
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
    public Propietario(List<Local> locales, List<Contestacion> contestaciones, List<Review> reviews,
                   String ID, String nick, String contrasenia, int edad, Date fechaNacimiento) {
    super(ID, nick, contrasenia, edad, fechaNacimiento);
    this.locales = locales != null ? locales : new java.util.ArrayList<>();
    this.contestaciones = contestaciones != null ? contestaciones : new java.util.ArrayList<>();
}
    public Propietario(String ID, String nick, String contrasenia, int edad, Date fechaNacimineto,
                   List<Local> locales, List<Contestacion> contestaciones) {
    super(ID, nick, contrasenia, edad, fechaNacimineto);
    this.locales = locales;
    this.contestaciones = contestaciones;
}

    public List<Local> getLocales() {
        return locales;
    }

    public void setLocales(List<Local> locales) {
        this.locales = locales;
    }

    public List<Contestacion> getContestaciones() {
        return contestaciones;
    }

    public void setContestaciones(List<Contestacion> contestaciones) {
        this.contestaciones = contestaciones;
    }
    
    
    
}
