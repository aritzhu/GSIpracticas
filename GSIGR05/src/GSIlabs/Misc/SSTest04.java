package GSIlabs.Misc;

import Applicacion.BSystem.BusinessSystem;
import Dominio.BModel.*;
import Applicacion.Enums.EnumEspecialidadesBar;
import Tester.java;
import org.jopendocument.dom.spreadsheet.*;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SSTest04 {

    public static void main(String[] args) throws IOException, ParseException {
        // --- Inicialización del sistema ---
        BusinessSystem bs = new BusinessSystem();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // --- Crear propietarios ---
        Date fecha = sdf.parse("15/11/1990");
        Propietario p1 = new Propietario(new ArrayList<>(), "P001", "Mario", "1234pass", 34, fecha);
        Propietario p2 = new Propietario(new ArrayList<>(), "P002", "Lucía", "lucia123", 28, fecha);
        Propietario p3 = new Propietario(new ArrayList<>(), "P003", "Ander", "ander123", 40, fecha);
        Propietario p4 = new Propietario(new ArrayList<>(), "P004", "Ana", "ana456", 30, fecha);
        Propietario p5 = new Propietario(new ArrayList<>(), "P005", "Jon", "jon789", 45, fecha);

        // --- Crear direcciones ---
        Direccion d1 = new Direccion("Pamplona", "Navarra", "Calle Mayor", 10);
        Direccion d2 = new Direccion("Tudela", "Navarra", "Plaza Nueva", 5);
        Direccion d3 = new Direccion("Estella", "Navarra", "Av. del Prado", 22);
        Direccion d4 = new Direccion("Pamplona", "Navarra", "Calle Nueva", 7);
        Direccion d5 = new Direccion("Tudela", "Navarra", "Calle Falsa", 11);

        // --- Crear locales ---
        List<Bar> bares = Arrays.asList(
                new Bar("Bar Central", d1, Arrays.asList(p1, p2), 12.5f,
                        Arrays.asList(EnumEspecialidadesBar.TAPAS, EnumEspecialidadesBar.CERVEZAS)),
                new Bar("El Rincon", d4, Arrays.asList(p4), 15.0f,
                        Arrays.asList(EnumEspecialidadesBar.CERVEZAS, EnumEspecialidadesBar.COCTELES))
        );

        List<Restaurante> restaurantes = Arrays.asList(
                new Restaurante("La Cazuela", d2, Arrays.asList(p2), 25.0f, 50, 6),
                new Restaurante("El Fogón", d5, Arrays.asList(p5), 30.0f, 80, 8)
        );

        List<Pub> pubs = Arrays.asList(
                new Pub("Night Owl", d3, Arrays.asList(p3), 18, 2),
                new Pub("Moonlight", d1, Arrays.asList(p1, p4), 20, 3)
        );

        // --- Añadir locales al sistema ---
        for (Bar b : bares) bs.nuevoLocal(b);
        for (Restaurante r : restaurantes) bs.nuevoLocal(r);
        for (Pub p : pubs) bs.nuevoLocal(p);

        // --- Crear documento ODS con 3 hojas ---
        SpreadSheet spread = SpreadSheet.create(3, 20, 100); // 20 columnas, 100 filas por hoja
        Sheet hojaBar = spread.getSheet(0);
        hojaBar.setName("Bar");

        Sheet hojaRest = spread.getSheet(1);
        hojaRest.setName("Restaurante");

        Sheet hojaPub = spread.getSheet(2);
        hojaPub.setName("Pub");

        // --- Escribir datos ---
        int fila = 0;
        for (Bar b : bares) {
            fila = escribirBar(hojaBar, b, fila);
        }

        fila = 0;
        for (Restaurante r : restaurantes) {
            fila = escribirRestaurante(hojaRest, r, fila);
        }

        fila = 0;
        for (Pub p : pubs) {
            fila = escribirPub(hojaPub, p, fila);
        }

        // --- Guardar archivo ---
        File file = new File("test04.ods");
        spread.saveAs(file);
        System.out.println("✅ Archivo 'test04.ods' generado correctamente con todos los atributos.");
    }

    // --- Métodos de escritura ---
   private static int escribirBar(Sheet sheet, Bar bar, int filaInicio) {
    // Datos básicos
    sheet.setValueAt(bar.getNombre(), 0, filaInicio);
    sheet.setValueAt(bar.getDireccion().getLocalidad(), 1, filaInicio);
    sheet.setValueAt(bar.getDireccion().getProvincia(), 2, filaInicio);
    sheet.setValueAt(bar.getDireccion().getCalle(), 3, filaInicio);

    // Propietarios
    List<Propietario> dueños = bar.getDueños();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 3; i++) {
        if (i < dueños.size()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(dueños.get(i).getNick()); // o getNombre() + " " + getApellido()
        }
    }
    sheet.setValueAt(sb.toString(), 4, filaInicio);

    // Precio menú
    sheet.setValueAt(bar.getPrecioMenu(), 5, filaInicio);

    // Especialidades: cada una en su columna
    List<EnumEspecialidadesBar> esp = bar.getEspecialidades();
    for (int i = 0; i < esp.size(); i++) {
        sheet.setValueAt(esp.get(i).name(), 6 + i, filaInicio);
    }

    return filaInicio + 1;
}


    private static int escribirRestaurante(Sheet sheet, Restaurante r, int filaInicio) {

        sheet.setValueAt(r.getNombre(), 0, filaInicio);
        sheet.setValueAt(r.getDireccion().getLocalidad(), 1, filaInicio);
        sheet.setValueAt(r.getDireccion().getProvincia(), 2, filaInicio);
        sheet.setValueAt(r.getDireccion().getCalle(), 3, filaInicio);

        List<Propietario> dueños = r.getDueños();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            if (i < dueños.size()) {
                if (sb.length() > 0) sb.append(", ");
                sb.append(dueños.get(i).getNick()); // o getNombre() + " " + getApellido()
            }
        }
    sheet.setValueAt(sb.toString(), 4, filaInicio);

        sheet.setValueAt(r.getPrecioMenu(), 5, filaInicio);
        sheet.setValueAt(r.getCapacidadComensales(), 6, filaInicio);
        sheet.setValueAt(r.getCapacidadComensalesMesa(), 7, filaInicio);

        return filaInicio + 1;
    }

    private static int escribirPub(Sheet sheet, Pub pub, int filaInicio) {
        

        sheet.setValueAt(pub.getNombre(), 0, filaInicio);
        sheet.setValueAt(pub.getDireccion().getLocalidad(), 1, filaInicio);
        sheet.setValueAt(pub.getDireccion().getProvincia(), 2, filaInicio);
        sheet.setValueAt(pub.getDireccion().getCalle(), 3, filaInicio);

        List<Propietario> dueños = pub.getDueños();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            if (i < dueños.size()) {
                if (sb.length() > 0) sb.append(", ");
                sb.append(dueños.get(i).getNick()); // o getNombre() + " " + getApellido()
            }
        }
        sheet.setValueAt(sb.toString(), 4, filaInicio);
        

        String apertura = String.format("%02d:00", pub.getHoraApertura());
        String cierre = String.format("%02d:00", pub.getHoraCierre());
        sheet.setValueAt(apertura, 5, filaInicio);
        sheet.setValueAt(cierre, 6, filaInicio);

        return filaInicio + 1;
    }
}
