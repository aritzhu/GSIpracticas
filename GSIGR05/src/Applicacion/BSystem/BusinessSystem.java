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

        xml.append("   <Usuarios>\n");
        xml.append("        <Clientes>\n");
        for (Usuario usuario : database.getUsuarios()) {
            if (usuario.esCliente())
                xml.append(usuario.toXML());
        }
        xml.append("        </Clientes>\n");
        xml.append("        <Propietarios>\n");
        for (Usuario usuario : database.getUsuarios()) {
            if (!usuario.esCliente())
                xml.append(usuario.toXML());
        }
        xml.append("        </Propietarios>\n");
        xml.append("   </Usuarios>\n");


        xml.append("   <Locales>\n");
        xml.append("        <Reservables>\n");
        for (Local local : database.getLocales()) {
            if (local.esReservable())
                xml.append(local.toXML());
        }
        xml.append("        </Reservables>\n");
        for (Local local : database.getLocales()) {
            if (!local.esReservable())
                xml.append(local.toXML());
        }
        xml.append("   </Locales>\n");


        xml.append("</BusinessSystem>\n");
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

        // Crear el parser XML
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(f);
        doc.getDocumentElement().normalize();

        // Obtener la raíz
        Element root = doc.getDocumentElement();

        // Verificar que la raíz sea BusinessSystem
        if (root.getTagName().equals("BusinessSystem")) {

            // Procesar los Locales
            NodeList locales = root.getElementsByTagName("Locales");
            if (locales.getLength() > 0) {
                System.out.println("Más de un local detectado");

                // Obtener el primer nodo <Locales>
                Element localesElement = (Element) locales.item(0);

                // Procesar los Bares
                NodeList bares = localesElement.getElementsByTagName("Bar");
                for (int i = 0; i < bares.getLength(); i++) {
                    System.out.println("Cargando Bar");
                    // Parsear cada bar
                    Bar bar = XMLParser.parseBarBS((Element) bares.item(i));

                    // Procesar reviews para el bar
                    NodeList reviewNodes = ((Element) bares.item(i)).getElementsByTagName("reviews");
                    for (int j = 0; j < reviewNodes.getLength(); j++) {
                        NodeList reviewItems = ((Element) reviewNodes.item(j)).getElementsByTagName("review");
                        for (int k = 0; k < reviewItems.getLength(); k++) {
                            Review rv = XMLParser.parseReviewBS((Element) reviewItems.item(k));
                            bar.addReview(rv);  // Añadir la review al bar
                            
                        }
                    }

                    // Añadir el local procesado
                    this.nuevoLocal(bar);
                    System.out.println("OBJETO BAR (modo files) CREADO DESDE XML");
                            System.out.println("Nombre: " + bar.getNombre());
                            System.out.println("Precio menú: " + bar.getPrecioMenu());
                            System.out.println("Localidad: " + bar.getDireccion().getLocalidad());
                            System.out.println("Provincia: " + bar.getDireccion().getProvincia());
                            System.out.println("Calle: " + bar.getDireccion().getCalle());
                            System.out.println("Número: " + bar.getDireccion().getNumero());
                            System.out.println("Especialidades: " + bar.getEspecialidades());
                            System.out.println("Dueños: " + bar.getDueños().size());
                            System.out.println("Reviews: " + bar.getReviews().size());
                            System.out.println("---------------------------------------");
                }

                // Procesar los Pubs
                NodeList pubs = localesElement.getElementsByTagName("Pub");
                for (int i = 0; i < pubs.getLength(); i++) {
                    System.out.println("Cargando Pub");
                    // Parsear cada pub
                    Pub pub = XMLParser.parsePubBS((Element) pubs.item(i));

                    // Procesar reviews para el pub
                    NodeList reviewNodes = ((Element) pubs.item(i)).getElementsByTagName("reviews");
                    for (int j = 0; j < reviewNodes.getLength(); j++) {
                        NodeList reviewItems = ((Element) reviewNodes.item(j)).getElementsByTagName("review");
                        for (int k = 0; k < reviewItems.getLength(); k++) {
                            Review rv = XMLParser.parseReviewBS((Element) reviewItems.item(k));
                            pub.addReview(rv);  // Añadir la review al pub
                        }
                    }
                    
                    // Añadir el local procesado
                    this.nuevoLocal(pub);
                    System.out.println("OBJETO PUB (modo files) CREADO DESDE XML");
                    System.out.println("Nombre: " + pub.getNombre());
                    System.out.println("Localidad: " + pub.getDireccion().getLocalidad());
                    System.out.println("Provincia: " + pub.getDireccion().getProvincia());
                    System.out.println("Calle: " + pub.getDireccion().getCalle());
                    System.out.println("Número: " + pub.getDireccion().getNumero());
                    System.out.println("Hora apertura: " + pub.getHoraApertura());
                    System.out.println("Hora cierre: " + pub.getHoraCierre());
                    System.out.println("Dueños: " + pub.getDueños().size());
                    System.out.println("Reviews: " + pub.getReviews().size());
                    System.out.println("---------------------------------------");
                }

                // Procesar otros locales si es necesario, como restaurantes
                NodeList restaurantes = localesElement.getElementsByTagName("Restaurante");
                for (int i = 0; i < restaurantes.getLength(); i++) {
                    // Parsear cada restaurante
                    Restaurante r = XMLParser.parseRestauranteBS((Element) restaurantes.item(i));

                    // Procesar reviews para el restaurante
                    NodeList reviewNodes = ((Element) restaurantes.item(i)).getElementsByTagName("reviews");
                    for (int j = 0; j < reviewNodes.getLength(); j++) {
                        NodeList reviewItems = ((Element) reviewNodes.item(j)).getElementsByTagName("review");
                        for (int k = 0; k < reviewItems.getLength(); k++) {
                            Review rv = XMLParser.parseReviewBS((Element) reviewItems.item(k));
                            r.addReview(rv);  // Añadir la review al restaurante
                        }
                    }
                    
                    // Añadir el local procesado
                    this.nuevoLocal(r);
                    System.out.println("OBJETO RESTAURANTE (modo files) CREADO DESDE XML");
                    System.out.println("Nombre: " + r.getNombre());
                    System.out.println("Precio menú: " + r.getPrecioMenu());
                    System.out.println("Capacidad comensales total: " + r.getCapacidadComensales());
                    System.out.println("Capacidad comensales mesa: " + r.getCapacidadComensalesMesa());
                    System.out.println("Localidad: " + r.getDireccion().getLocalidad());
                    System.out.println("Provincia: " + r.getDireccion().getProvincia());
                    System.out.println("Calle: " + r.getDireccion().getCalle());
                    System.out.println("Número: " + r.getDireccion().getNumero());
                    System.out.println("Dueños: " + r.getDueños().size());
                    System.out.println("Reviews: " + r.getReviews().size());
                    System.out.println("---------------------------------------");
                }
            } else {
                System.out.println("Ningún local detectado");
            }

            // Procesar los Clientes
            NodeList clientes = root.getElementsByTagName("Clientes");
            for (int i = 0; i < clientes.getLength(); i++) {
                NodeList clienteNodes = ((Element) clientes.item(i)).getElementsByTagName("cliente");
                for (int j = 0; j < clienteNodes.getLength(); j++) {
                    Cliente c = XMLParser.parseClienteBS((Element) clienteNodes.item(j));
                    this.nuevoUsuario(c);
                    System.out.println("OBJETO CLIENTE (modo files) CREADO DESDE XML");
                    System.out.println("ID: " + c.getID());
                    System.out.println("Nick: " + c.getNick());
                    System.out.println("Contraseña: " + c.getContrasenia());
                    System.out.println("Edad: " + c.getEdad());
                    System.out.println("Fecha nacimiento: " + c.getFechaNacimiento());
                    System.out.println("---------------------------------------");
                }
            }

            // Procesar los Propietarios
            NodeList propietarios = root.getElementsByTagName("Propietarios");
            for (int i = 0; i < propietarios.getLength(); i++) {
                NodeList propietarioNodes = ((Element) propietarios.item(i)).getElementsByTagName("propietario");
                for (int j = 0; j < propietarioNodes.getLength(); j++) {
                    Propietario pr = XMLParser.parsePropietarioBS((Element) propietarioNodes.item(j));
                    this.nuevoUsuario(pr);
                    System.out.println("OBJETO PROPIETARIO (modo file) CREADO DESDE XML");
                    System.out.println("ID: " + pr.getID());
                    System.out.println("Nick: " + pr.getNick());
                    System.out.println("Contraseña: " + pr.getContrasenia());
                    System.out.println("Edad: " + pr.getEdad());
                    System.out.println("Fecha nacimiento: " + pr.getFechaNacimiento());
                    System.out.println("Locales: " + (pr.getLocales() != null ? pr.getLocales().size() : 0));
                    System.out.println("Contestaciones: " + (pr.getContestaciones() != null ? pr.getContestaciones().size() : 0));
                    System.out.println("---------------------------------------");
                }
            }

            // Procesar Contestaciones
            NodeList contestaciones = root.getElementsByTagName("Contestaciones");
            for (int i = 0; i < contestaciones.getLength(); i++) {
                NodeList contestacionNodes = ((Element) contestaciones.item(i)).getElementsByTagName("contestacion");
                for (int j = 0; j < contestacionNodes.getLength(); j++) {
                    Contestacion co = XMLParser.parseContestacionBS((Element) contestacionNodes.item(j));
                    this.database.getContestaciones().add(co);
                    // Imprimir detalles de la contestación
                    System.out.println("OBJETO CONTESTACION (modo files) CREADO DESDE XML");

                    // Imprimir detalles del dueño
                    System.out.println("ID Dueño: " + co.getDueño().getID());
                    System.out.println("Nick Dueño: " + co.getDueño().getNick());
                    System.out.println("Edad Dueño: " + co.getDueño().getEdad());
                    System.out.println("Fecha Nacimiento Dueño: " + co.getDueño().getFechaNacimiento());

                    // Imprimir detalles de la review asociada
                    System.out.println("Valoracion Review: " + co.getReview().getValoracion());
                    System.out.println("Comentario Review: " + co.getReview().getComentario());

                    // Imprimir texto de la contestación
                    System.out.println("Texto Contestación: " + co.getContestacion());

                    // Imprimir fecha de escritura de la contestación
                    System.out.println("Fecha Escritura Contestación: " + co.getFechaEscritura());

                    System.out.println("---------------------------------------");
                }
            }

            // Procesar Reservas
            NodeList reservas = root.getElementsByTagName("Reservas");
            for (int i = 0; i < reservas.getLength(); i++) {
                NodeList reservaNodes = ((Element) reservas.item(i)).getElementsByTagName("reserva");
                for (int j = 0; j < reservaNodes.getLength(); j++) {
                    Reserva rs = XMLParser.parseReservaBS((Element) reservaNodes.item(j));
                    this.database.getReservas().add(rs);
                    // Imprimir detalles de la reserva
                    System.out.println("OBJETO RESERVA (modo files) CREADO DESDE XML");

                    // Imprimir fecha de reserva
                    System.out.println("Fecha Reserva: " + rs.getFechaReserva());

                    // Imprimir descuento
                    System.out.println("Descuento: " + rs.getDescuento() + "%");

                    // Imprimir detalles del cliente
                    System.out.println("ID Cliente: " + rs.getCliente().getID());
                    System.out.println("Nick Cliente: " + rs.getCliente().getNick());
                    System.out.println("Edad Cliente: " + rs.getCliente().getEdad());
                    System.out.println("Fecha Nacimiento Cliente: " + rs.getCliente().getFechaNacimiento());

                    // Imprimir detalles del local
                    System.out.println("Nombre Local: " + rs.getLocal().getNombre());
                    System.out.println("Localidad Local: " + rs.getLocal().getDireccion().getLocalidad());
                    System.out.println("Provincia Local: " + rs.getLocal().getDireccion().getProvincia());
                    System.out.println("Calle Local: " + rs.getLocal().getDireccion().getCalle());
                    System.out.println("Número Local: " + rs.getLocal().getDireccion().getNumero());

                    System.out.println("---------------------------------------");
                }
            }

            return true;
        }

        // Código adicional para verificar otras secciones del XML si es necesario
        switch (root.getTagName()) {
            case "Bar":
                return this.nuevoLocal(XMLParser.parseBar(f));

            case "Pub":
                System.out.println("Me meto por aquí");
                return this.nuevoLocal(XMLParser.parsePub(f));

            case "Restaurante":
                return this.nuevoLocal(XMLParser.parseRestaurante(f));

            case "Cliente":
                return this.nuevoUsuario(XMLParser.parseCliente(f));

            case "Propietario":
                return this.nuevoUsuario(XMLParser.parsePropietario(f));

            case "Review":
                return this.nuevaReview(XMLParser.parseReview(f));

            case "Contestacion":
                this.database.getContestaciones().add(XMLParser.parseContestacion(f));
                return true;

            case "Reserva":
                this.database.getReservas().add(XMLParser.parseReserva(f));
                return true;
        }

    } catch (Exception ex) {
        ex.printStackTrace();
        return false;
    }

    return false;
}



}

