/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Applicacion.BSystem;

import Applicacion.DTOS.Validators.LocalValidator;
import Applicacion.Enums.EnumEspecialidadesBar;
import Dominio.BModel.*;
import Dominio.IBModelo.*;
import GSILabs.Serializable.XMLRepresentable;
import GSILabs.persistence.XMLParser;
import GSILabs.persistence.XMLParsingException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import GSILabs.persistence.XMLParsingException;
import java.util.HashSet;
import java.util.Set;
import org.w3c.dom.NodeList;

/**
 *
 * @author alumno
 */
public class BusinessSystem implements LeisureOffice, XMLRepresentable {
    private EjecuctionTimeDataBase database;

    public BusinessSystem() {
        this.database = new EjecuctionTimeDataBase();
    }
    
    @Override
    public boolean nuevoUsuario(Usuario u) {
        if (u == null) {
            return false;
        }
        
        // Evitamos duplicados por ID
        for (Usuario existente : database.getUsuarios()) {
            if (existente.getID().equals(u.getID())) {
                return false;
            }
        }
        database.getUsuarios().add(u);
        return true;
    }

    @Override
    public boolean eliminaUsuario(Usuario u) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean modificaUsuario(Usuario u, Usuario nuevoU) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean existeNick(String nick) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Usuario obtenerUsuario(String nick) {
        List<Usuario> usuarios =  this.database.getUsuarios();
        
        for (Usuario usuario : usuarios) {
            
            if(usuario.getNick().equals(nick)) {
                return usuario;
            }
        }
        
        return null;
    }

    @Override
    public boolean nuevaReview(Review r) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean eliminaReview(Review r) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean existeRewiew(Review r) {
        if (r == null){
            return false;
        }
        
        for (Review existente : database.getReviews()) {
            if (existente.equals(r))
                return true;
        }
        return false;
    }

    @Override
    public boolean nuevaContestacion(Contestacion c, Review r) {
        if (c == null || r == null)
            return false;
                    
        if (!database.getReviews().contains(r))
            return false;
        
        database.getContestaciones().add(c);
        return true;
    }

    @Override
    public boolean tieneContestacion(Review r) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Contestacion obtenerContestacion(Review r) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean eliminaContestacion(Contestacion c) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean eliminaContestacion(Review r) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean nuevoLocal(Local l) {
        if(new LocalValidator().ValidarLocal(l,this.database))
        {
            this.database.getLocales().add(l);
            return true;
        }
        return false;
    }

    @Override
    public boolean eliminarLocal(Local local) {
        List<Local> locales = this.database.getLocales();

        locales.remove(local);
        
        return true;
    }

    @Override
    public Local obtenerLocal(Direccion d) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean asociarLocal(Local l, Propietario p) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean desasociarLocal(Local l, Propietario p) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean actualizarLocal(Local viejoL, Local nuevoL) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Review[] verReviews(Local l) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean nuevaReserva(Cliente c, Reservable r, LocalDate ld, LocalTime lt) {
        if (r == null) {
            try {
                throw new Exception("Error");
            } catch (Exception ex) {
                Logger.getLogger(BusinessSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Reserva[] obtenerReservas(Cliente c) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Reserva[] obtenerReservas(Reservable r) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Reserva[] obtenerReservas(LocalDate ld) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean eliminarReserva(Reserva r) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Local[] listarLocales(String ciudad, String provincia) {
                
        List<Local> locales =  this.database.getLocales();
        
        List<Local> localesListados = null;
        
        int i = 0;
        
        for (Local local : locales) {
            
            if(local.getDireccion().getLocalidad().equals(ciudad) && local.getDireccion().getProvincia().equals(provincia)) {
                localesListados.add(local);
            }
        }
        
        return localesListados.toArray(new Local[0]); 
    }

    @Override
    public Bar[] listarBares(String ciudad, String provincia) {
        return database.getLocales().stream()
            .filter(l -> l instanceof Bar)
            .map(l -> (Bar) l)
            .filter(b -> (ciudad == null || ciudad.isEmpty() || b.getDireccion().getLocalidad().equals(ciudad)) &&
                         (provincia == null || provincia.isEmpty() || b.getDireccion().getProvincia().equals(provincia)))
            .toArray(Bar[]::new);
    }

    @Override
    public Restaurante[] listarRestaurantes(String ciudad, String provincia) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Pub[] listarPubs(String ciudad, String provincia) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public int importaPubs(File f) {
        int cont = 0;  // Contador para el número de bares importados correctamente
        int contgeneral = 0;
        int cont2 = 0;
        try {
            // Se crea una instancia de SpreadSheet a partir del archivo proporcionado
            SpreadSheet spread = SpreadSheet.createFromFile(f);

            // Se obtiene la primera hoja del archivo (se asume que solo hay una hoja)
            Sheet hoja = spread.getSheet(0); 

            // Se obtiene el número total de filas en la hoja
            int totalFilas = hoja.getRowCount();

            // Iterar sobre todas las filas de la hoja
            for (int fila = 0; fila < totalFilas; fila++) {
                try {
                    // Obtener el valor de la primera columna (nombre del bar)
                    Object nombreObj = hoja.getValueAt(0, fila);

                    // Si el nombre está vacío o nulo, se ignora la fila
                    if (nombreObj == null || nombreObj.toString().trim().isEmpty()) {
                        continue; // Ignorar fila vacía
                    }

                    // Se extrae el nombre del bar, limpiando espacios en blanco
                    String nombre = nombreObj.toString().trim();

                    // Obtener los valores de las siguientes columnas (dirección)
                    String calle = hoja.getValueAt(1, fila).toString().trim();
                    String ciudad = hoja.getValueAt(2, fila).toString().trim();
                    String provincia = hoja.getValueAt(3, fila).toString().trim();

                    // Crear un objeto Direccion con los valores extraídos
                    Direccion direccion = new Direccion(ciudad, provincia, calle, 0); // El número de la calle es desconocido

                    // Listado de propietarios (se asume que están en una única celda, separados por comas)
                    List<Propietario> dueños = new ArrayList<>();
                    Object propObj = hoja.getValueAt(4, fila);
                    if (propObj != null && !propObj.toString().trim().isEmpty()) {
                        // Si hay propietarios, se extraen los nicks de la celda
                        String[] nicks = propObj.toString().trim().split("\\s*,\\s*");
                        // Se crea un propietario para cada uno de los nicks
                        for (String nick : nicks) {
                            Propietario p = new Propietario(new ArrayList<>(), "ID-" + nick, nick, "", 0, null);
                            dueños.add(p);
                        }
                    }

                    // Especialidades: las columnas 5 en adelante corresponden a las especialidades
                    List<EnumEspecialidadesBar> especialidades = new ArrayList<>();
                    for (int col = 5; col < hoja.getColumnCount(); col++) {
                        // Obtener el valor de cada columna de especialidades
                        Object espObj = hoja.getValueAt(col, fila);
                        // Si la celda está vacía, se termina de procesar las especialidades
                        if (espObj == null || espObj.toString().trim().isEmpty()) break;
                        try {
                            // Intentar convertir la especialidad a un valor válido del enum
                            especialidades.add(EnumEspecialidadesBar.valueOf(espObj.toString().trim()));
                        } catch (IllegalArgumentException e) {
                            // Si la especialidad no es válida, se ignora sin lanzar excepción
                        }
                    }

                    // Crear un objeto Bar con la información recolectada
                    Bar bar = new Bar(nombre, direccion, dueños, 0, especialidades);
                    contgeneral++;
                    // Verificar si el bar es nuevo, si es así se incrementa el contador
                    if (nuevoLocal(bar)) cont++;


                } catch (Exception filaEx) {
                    // Si ocurre un error al procesar la fila, se captura y se muestra un mensaje de error
                    System.err.println("Error en fila " + fila + ": " + filaEx.getMessage());
                    System.out.println("Error en fila " + fila + ": " + filaEx.getMessage());
                }
            }

        } catch (Exception e) {
            // Capturar cualquier error al leer el archivo o procesar la hoja
            e.printStackTrace();
        }

        // Retornar el número total de bares importados correctamente
        return cont;
    }
    
    @Override
public String toXML() {
    StringBuilder xml = new StringBuilder();
    xml.append("<BusinessSystem>\n");

    // Usuarios
    xml.append("   <Usuarios>\n");

    // Clientes
    xml.append("        <Clientes>\n");
    for (Usuario usuario : database.getUsuarios()) {
        if (usuario.esCliente()) {
            xml.append(indentLines(usuario.toXML(), 3));
        }
    }
    xml.append("        </Clientes>\n");

    // Propietarios
    xml.append("        <Propietarios>\n");
    for (Usuario usuario : database.getUsuarios()) {
        if (!usuario.esCliente()) {
            xml.append(indentLines(usuario.toXML(), 3));
        }
    }
    xml.append("        </Propietarios>\n");

    xml.append("   </Usuarios>\n");

    // Locales
    xml.append("   <Locales>\n");

    // Reservables
    xml.append("        <Reservables>\n");
    for (Local local : database.getLocales()) {
        if (local.esReservable()) {
            xml.append(indentLines(local.toXML(), 3));
        }
    }
    xml.append("        </Reservables>\n");

    // No reservables
    for (Local local : database.getLocales()) {
        if (!local.esReservable()) {
            xml.append(indentLines(local.toXML(), 2));
        }
    }

    xml.append("   </Locales>\n");

    xml.append("</BusinessSystem>\n");

    // Mostrar por pantalla
    System.out.println(xml);

    return xml.toString();
}

private String indentLines(String text, int level) {
    String indent = "   ".repeat(level); // 3 espacios por nivel
    StringBuilder indented = new StringBuilder();
    String[] lines = text.split("\n");
    for (String line : lines) {
        indented.append(indent).append(line).append("\n");
    }
    return indented.toString();
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
        try (FileWriter fw = new FileWriter(new File(filePath))) {
            fw.write(this.toXML());
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    public static BusinessSystem parseXMLFile(File f) throws XMLParsingException {
        BusinessSystem bs = new BusinessSystem();
        if(!bs.loadXMLFile(f))
            throw new XMLParsingException("No se pudo cargar el XML");
        return bs;
    }

    public boolean loadXMLFile(File f) {
    try {
        System.out.println("Cargando XML en Business System");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(f);
        doc.getDocumentElement().normalize();

        Element root = doc.getDocumentElement();
        if (!root.getTagName().equals("BusinessSystem")) {
            System.err.println("Raíz inválida: " + root.getTagName());
            return false;
        }

        // Procesar Propietarios
        NodeList propietariosNodes = root.getElementsByTagName("Propietarios");
        if (propietariosNodes.getLength() > 0) {
            Element propietariosElement = (Element) propietariosNodes.item(0);
            NodeList propietarioList = propietariosElement.getElementsByTagName("Propietario");
            for (int i = 0; i < propietarioList.getLength(); i++) {
                Propietario pr = XMLParser.parsePropietarioBS((Element) propietarioList.item(i));
                this.nuevoUsuario(pr);
            }
        }

        // Procesar Clientes
        NodeList clientesNodes = root.getElementsByTagName("Clientes");
        if (clientesNodes.getLength() > 0) {
            Element clientesElement = (Element) clientesNodes.item(0);
            NodeList clienteList = clientesElement.getElementsByTagName("Cliente");
            for (int i = 0; i < clienteList.getLength(); i++) {
                Cliente c = XMLParser.parseClienteBS((Element) clienteList.item(i));
                this.nuevoUsuario(c);
            }
        }

        // Procesar Locales
        NodeList localesNodes = root.getElementsByTagName("Locales");
        if (localesNodes.getLength() > 0) {
            Element localesElement = (Element) localesNodes.item(0);

            // Bares
           NodeList barNodes = localesElement.getElementsByTagName("Bar");
            for (int i = 0; i < barNodes.getLength(); i++) {
                Bar bar = XMLParser.parseBarBS((Element) barNodes.item(i));
                cargarReviews((Element) barNodes.item(i), bar);
                cargarReservasBar((Element) barNodes.item(i), bar);        // Cargar reservas
                this.nuevoLocal(bar);
            }

            // Pubs
            NodeList pubNodes = localesElement.getElementsByTagName("Pub");
            for (int i = 0; i < pubNodes.getLength(); i++) {
                Pub pub = XMLParser.parsePubBS((Element) pubNodes.item(i));
                cargarReviews((Element) pubNodes.item(i), pub);
                this.nuevoLocal(pub);
            }

            // Restaurantes
            NodeList restNodes = localesElement.getElementsByTagName("Restaurante");
            for (int i = 0; i < restNodes.getLength(); i++) {
                Restaurante r = XMLParser.parseRestauranteBS((Element) restNodes.item(i));
                cargarReviews((Element) restNodes.item(i), r);
                cargarReservasRestaurante((Element) barNodes.item(i), r); 
                this.nuevoLocal(r);
            }
        }

        

        return true;

    } catch (Exception ex) {
        ex.printStackTrace();
        return false;
    }
}
    private void cargarReviews(Element localElement, Local local) throws XMLParsingException {
    NodeList reviewsNodes = localElement.getElementsByTagName("Reviews");
    if (reviewsNodes.getLength() > 0) {
        Element reviewsElement = (Element) reviewsNodes.item(0);
        NodeList reviewList = reviewsElement.getElementsByTagName("Review");
        for (int i = 0; i < reviewList.getLength(); i++) {
            Review rv = XMLParser.parseReviewBS((Element) reviewList.item(i));
            
            // Cargar contestaciones de esta review
            NodeList contestacionesNodes = ((Element) reviewList.item(i)).getElementsByTagName("Contestacion");
            for (int j = 0; j < contestacionesNodes.getLength(); j++) {
                Contestacion c = XMLParser.parseContestacionBS((Element) contestacionesNodes.item(j));
                rv.setContestacion(c);
                
            }
            
            local.addReview(rv);
        }
    }
}
    private void cargarReservasBar(Element localElement, Bar local) throws XMLParsingException {
    NodeList reservasNodes = localElement.getElementsByTagName("Reservas");
    if (reservasNodes.getLength() > 0) {
        Element reservasElement = (Element) reservasNodes.item(0);
        NodeList reservaList = reservasElement.getElementsByTagName("Reserva");

        // Inicializar la lista de reservas si es null
        if (local.getReservas() == null) {
            local.setReservas(new ArrayList<>());
        }

        for (int i = 0; i < reservaList.getLength(); i++) {
            Element reservaElement = (Element) reservaList.item(i);

            // Parsear la reserva
            Reserva r = XMLParser.parseReservaBS(reservaElement);

            // Parsear el cliente dentro de la reserva
            NodeList clienteNodes = reservaElement.getElementsByTagName("Cliente");
            if (clienteNodes.getLength() > 0) {
                Element clienteElement = (Element) clienteNodes.item(0);
                Cliente c = XMLParser.parseClienteBS(clienteElement);
                r.setCliente(c);   // asignar cliente a la reserva
            }

            r.setLocal(local);      // asignar local a la reserva
            local.getReservas().add(r);
        }
    }
}

    private void cargarReservasRestaurante(Element localElement, Restaurante local) throws XMLParsingException {
    NodeList reservasNodes = localElement.getElementsByTagName("Reservas");
    if (reservasNodes.getLength() > 0) {
        Element reservasElement = (Element) reservasNodes.item(0);
        NodeList reservaList = reservasElement.getElementsByTagName("Reserva");
        for (int i = 0; i < reservaList.getLength(); i++) {
            Element reservaElement = (Element) reservaList.item(i);
            
            // Parsear la reserva
            Reserva r = XMLParser.parseReservaBS(reservaElement);
            
            // Parsear el cliente dentro de la reserva
            NodeList clienteNodes = reservaElement.getElementsByTagName("Cliente");
            if (clienteNodes.getLength() > 0) {
                Element clienteElement = (Element) clienteNodes.item(0);
                Cliente c = XMLParser.parseClienteBS(clienteElement);
                r.setCliente(c);   // asignar cliente a la reserva
            }
            
            r.setLocal(local);      // asignar local a la reserva
            if (local.getReservas() == null) {
                local.setReservas(new ArrayList<>()); // inicializar lista si es null
            }
            local.getReservas().add(r);
        }
    }
}


}

