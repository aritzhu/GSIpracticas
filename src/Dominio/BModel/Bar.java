/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio.BModel;

import Applicacion.Enums.EnumEspecialidadesBar;
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
public class Bar extends Local implements Reservable, XMLRepresentable, java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private float precioMenu;
    private final boolean esReservable= true;
    private List<EnumEspecialidadesBar> especialidades;
    private List<Reserva> reservas;

    public Bar(String nombre, Direccion dirección, List<Propietario> dueños) {
        super(nombre, dirección, dueños);
    }
    public Bar(String nombre, Direccion direccion, List<Propietario> dueños, float precioMenu, List<EnumEspecialidadesBar> especialidades) {
        super(nombre, direccion, dueños);
        this.precioMenu = precioMenu;
        this.especialidades = especialidades;
    }
    public boolean esReservable(){
        return esReservable;
    }
    // Getters y setters
    public float getPrecioMenu() {
        return precioMenu;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
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
    
    @Override
public String toXML() {
    StringBuilder xml = new StringBuilder();
    xml.append("<Bar>\n");

    // Reutilizamos XML de Local
    String localXML = super.toXML();
    // Eliminamos etiquetas <Local> para no duplicarlas
    localXML = localXML.replaceFirst("<Local>", "").replaceFirst("</Local>$", "");
    xml.append(localXML);

    // Añadimos atributos específicos de Bar
    xml.append("   <PrecioMenu>").append(precioMenu).append("</PrecioMenu>\n");

    xml.append("   <Especialidades>\n");
    if (especialidades != null) {
        for (EnumEspecialidadesBar esp : especialidades) {
            xml.append("       <Especialidad>").append(esp.toString()).append("</Especialidad>\n");
        }
    }
    xml.append("   </Especialidades>\n");

    // Añadir reservas si hay
    if (reservas != null && !reservas.isEmpty()) {
        xml.append("   <Reservas>\n");
        for (Reserva r : reservas) {
            // Indentamos cada línea de reserva para que quede bonito
            String reservaXML = r.toXML().replaceAll("(?m)^", "       ");
            xml.append(reservaXML).append("\n");
        }
        xml.append("   </Reservas>\n");
    }

    xml.append("</Bar>\n");
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
            this.getReviews().add(rv);
        }
    }
}
