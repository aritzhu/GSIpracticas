package Dominio.BModel;

import Dominio.IBModelo.Local;
import GSILabs.Serializable.XMLRepresentable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Pub extends Local implements Reservable, XMLRepresentable {
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
    
    @Override
    public String toXML() {
        StringBuilder xml = new StringBuilder();
        xml.append("<Pub>\n");

        // Reutilizamos XML de Local
        String localXML = super.toXML();
        localXML = localXML.replaceFirst("<Local>", "").replaceFirst("</Local>$", "");
        xml.append(localXML);

        // Atributos específicos de Pub
        xml.append("   <HoraApertura>").append(horaApertura).append("</HoraApertura>\n");
        xml.append("   <HoraCierre>").append(horaCierre).append("</HoraCierre>\n");

        xml.append("</Pub>\n");
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
