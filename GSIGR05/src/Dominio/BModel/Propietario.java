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
    private List<Contestacion> contestaciones;
    public Propietario(List<Review> reviews, String ID, String nick, String contrasenia, int edad, Date fechaNacimineto) {
        super(ID, nick, contrasenia, edad, fechaNacimineto);
        this.locales = locales;
        this.contestaciones = contestaciones;
    }
    
     @Override
    public String toXML() {
        StringBuilder xml = new StringBuilder();

        // Usuario base
        String usuarioXML = super.toXML();
        usuarioXML = usuarioXML.replaceFirst("<Usuario[^>]*>", "").replaceFirst("</Usuario>$", "");

        xml.append("<Propietario id=\"").append(getID()).append("\">\n");
        xml.append(usuarioXML);

        // 🔹 Locales
        xml.append("   <Locales>\n");
        for (Local l : locales) {
            xml.append("       <Local>\n");
            xml.append("           <Nombre>").append(l.getNombre()).append("</Nombre>\n");
            xml.append("           <Direccion>").append(l.getDireccion()).append("</Direccion>\n");
            xml.append("       </Local>\n");
        }
        xml.append("   </Locales>\n");

        // 🔹 Contestaciones
        xml.append("   <Contestaciones>\n");
        for (Contestacion c : contestaciones) {
            xml.append(c.toXML()); // cada contestación ya genera su propio XML
        }
        xml.append("   </Contestaciones>\n");

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
}
