package GSILabs.persistence;

import Dominio.BModel.Bar;
import Dominio.BModel.Direccion;
import Dominio.BModel.Propietario;
import Applicacion.Enums.EnumEspecialidadesBar;
import Dominio.BModel.Cliente;
import Dominio.BModel.Contestacion;
import Dominio.BModel.Pub;
import Dominio.BModel.Reserva;
import Dominio.BModel.Restaurante;
import Dominio.BModel.Review;
import Dominio.IBModelo.Local;

import java.io.File;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Opción (a): clase con métodos estáticos de creación desde XML.
 * 
 * Ejemplo: parseBar(File f) y parseBar(String str)
 */
public class XMLParser {

    public static Bar parseBar(String xmlStr) throws XMLParsingException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(xmlStr.getBytes()));

            return parseBarFromDocument(doc);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new XMLParsingException("Error al parsear el XML de Bar: " + e.getMessage());
        }
    }
    public static Bar parseBar(File f) throws XMLParsingException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(f);

            return parseBarFromDocument(doc);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new XMLParsingException("Error al leer el archivo XML de Bar: " + e.getMessage());
        }
    }
    private static Bar parseBarFromDocument(Document doc) throws XMLParsingException {
        Element root = doc.getDocumentElement();
        if (!root.getNodeName().equals("Bar"))
            throw new XMLParsingException("El XML no representa un objeto <Bar>.");

        // --- Campos básicos ---
        String nombre = getTagValue(root, "Nombre");
        String precioStr = getTagValue(root, "PrecioMenu");

        if (nombre == null || precioStr == null)
            throw new XMLParsingException("Faltan campos obligatorios en el XML de Bar.");

        float precioMenu;
        try {
            precioMenu = Float.parseFloat(precioStr);
        } catch (NumberFormatException e) {
            throw new XMLParsingException("Precio de menú inválido: " + precioStr);
        }

        // --- Especialidades ---
        List<EnumEspecialidadesBar> especialidades = new ArrayList<>();
        NodeList espNodes = root.getElementsByTagName("Especialidad");
        for (int i = 0; i < espNodes.getLength(); i++) {
            String val = espNodes.item(i).getTextContent().trim();
            try {
                especialidades.add(EnumEspecialidadesBar.valueOf(val));
            } catch (IllegalArgumentException e) {
                throw new XMLParsingException("Especialidad no válida: " + val);
            }
        }

        // --- Dirección ---
        Element dirElem = (Element) root.getElementsByTagName("Direccion").item(0);
        Direccion direccion = parseDireccion(dirElem);

        // --- Propietarios ---
        List<Propietario> duenos = new ArrayList<>();
        NodeList propietarios = root.getElementsByTagName("Propietario");
        for (int i = 0; i < propietarios.getLength(); i++) {
            Element e = (Element) propietarios.item(i);
            duenos.add(parsePropietario(e));
        }

        // Crear objeto Bar final
        return new Bar(nombre, direccion, duenos, precioMenu, especialidades);
    }
    private static String getTagValue(Element parent, String tag) {
        NodeList list = parent.getElementsByTagName(tag);
        if (list.getLength() == 0) return null;
        return list.item(0).getTextContent();
    }
    private static Direccion parseDireccion(Element e) throws XMLParsingException {
        if (e == null) throw new XMLParsingException("Falta el elemento <direccion> en el XML.");

        String localidad = getTagValue(e, "Localidad");
        String provincia = getTagValue(e, "Provincia");
        String calle = getTagValue(e, "Calle");
        String numeroStr = getTagValue(e, "Numero");

        if (localidad == null || provincia == null || calle == null || numeroStr == null)
            throw new XMLParsingException("Datos incompletos en la dirección.");

        int numero;
        try {
            numero = Integer.parseInt(numeroStr);
        } catch (NumberFormatException ex) {
            throw new XMLParsingException("Número de dirección inválido: " + numeroStr);
        }

        return new Direccion(localidad, provincia, calle, numero);
    }
    private static Propietario parsePropietario(Element e) throws XMLParsingException {
    if (e == null) throw new XMLParsingException("Elemento <propietario> nulo o mal formado.");

    // --- Datos básicos ---
    String id = getTagValue(e, "Id");
    String nick = getTagValue(e, "Nick");
    String contrasenia = getTagValue(e, "Contrasenia");
    String edadStr = getTagValue(e, "Edad");
    String fechaStr = getTagValue(e, "FechaNacimiento");

    if (id == null || nick == null || contrasenia == null || edadStr == null || fechaStr == null)
        throw new XMLParsingException("Datos incompletos del propietario.");

    int edad;
    try {
        edad = Integer.parseInt(edadStr);
    } catch (NumberFormatException ex) {
        throw new XMLParsingException("Edad inválida: " + edadStr);
    }

    Date fecha;
    try {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        fecha = sdf.parse(fechaStr);
    } catch (ParseException ex) {
        throw new XMLParsingException("Fecha de nacimiento inválida: " + fechaStr);
    }
    // --- Crear propietario ---
    return new Propietario(id, nick, contrasenia, edad, fecha);
}
    private static Contestacion parseContestacion(Element e) throws XMLParsingException {
    if (e == null)
        throw new XMLParsingException("Elemento <contestacion> nulo o mal formado.");

    // Tomamos solo el campo que realmente existe en la clase: el texto/comentario
    String comentario = getTagValue(e, "Comentario");

    if (comentario == null || comentario.isEmpty())
        throw new XMLParsingException("Datos incompletos en contestación (falta comentario).");

    // Propietario y Review no se incluyen en el XML (se pasan como null)
    return new Contestacion(null, null, comentario);
}

    private static Local parseLocal(Element e) throws XMLParsingException {
    if (e == null)
        throw new XMLParsingException("Elemento <local> nulo o mal formado.");

    // --- Nombre del local ---
    String nombre = getTagValue(e, "Nombre");
    if (nombre == null)
        throw new XMLParsingException("Nombre de local ausente o nulo.");

    // --- Dirección (subelemento complejo) ---
    Element dirElem = (Element) e.getElementsByTagName("Direccion").item(0);
    Direccion direccion = parseDireccion(dirElem);

    // --- Lista de propietarios ---
    List<Propietario> duenos = new ArrayList<>();
    NodeList propNodes = e.getElementsByTagName("Propietario");
    for (int i = 0; i < propNodes.getLength(); i++) {
        Element propElem = (Element) propNodes.item(i);
        Propietario p = parsePropietario(propElem);
        if (p != null) duenos.add(p);
    }

    // --- Crear objeto Local ---
    return new Local(nombre, direccion, duenos);
}

    
    public static Cliente parseCliente(String xmlStr) throws XMLParsingException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(xmlStr.getBytes()));

            return parseClienteFromDocument(doc);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new XMLParsingException("Error al parsear el XML de Cliente: " + e.getMessage());
        }
    }
    public static Cliente parseCliente(File f) throws XMLParsingException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(f);

            return parseClienteFromDocument(doc);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new XMLParsingException("Error al leer el archivo XML de Cliente: " + e.getMessage());
        }
    }
    private static Cliente parseClienteFromDocument(Document doc) throws XMLParsingException {
        Element root = doc.getDocumentElement();
        if (!root.getNodeName().equalsIgnoreCase("Cliente"))
            throw new XMLParsingException("El XML no representa un objeto <Cliente>.");

        // Campos básicos heredados de Usuario
        String id = getTagValue(root, "Id");
        String nick = getTagValue(root, "Nick");
        String contrasenia = getTagValue(root, "Contrasenia");
        String edadStr = getTagValue(root, "Edad");
        String fechaStr = getTagValue(root, "FechaNacimiento");

        if (id == null || nick == null || contrasenia == null || edadStr == null || fechaStr == null)
            throw new XMLParsingException("Faltan campos obligatorios en el XML de Cliente.");

        int edad;
        try {
            edad = Integer.parseInt(edadStr);
        } catch (NumberFormatException e) {
            throw new XMLParsingException("Edad inválida: " + edadStr);
        }

        Date fecha;
        try {
            fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fechaStr);
        } catch (ParseException e) {
            throw new XMLParsingException("Fecha de nacimiento inválida: " + fechaStr);
        }

        // Lista de reviews (vacía de momento)
        List<Review> reviews = new ArrayList<>();

        return new Cliente(reviews, id, nick, contrasenia, edad, fecha);
    }
    
    public static Review parseReview(String xmlStr) throws XMLParsingException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(xmlStr.getBytes()));

            return parseReviewFromDocument(doc);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new XMLParsingException("Error al parsear el XML de Review: " + e.getMessage());
        }
    }
    public static Review parseReview(File f) throws XMLParsingException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(f);

            return parseReviewFromDocument(doc);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new XMLParsingException("Error al leer el archivo XML de Review: " + e.getMessage());
        }
    }
    
    private static Review parseReviewFromDocument(Document doc) throws XMLParsingException {
        Element root = doc.getDocumentElement();
        if (!root.getNodeName().equalsIgnoreCase("Review")) {
            throw new XMLParsingException("El XML no representa un objeto <Review>.");
        }

        // --- Campos obligatorios ---
        String valoracionStr = getTagValue(root, "Valoracion");
        String comentario = getTagValue(root, "Comentario");
        String fechaVisitaStr = getTagValue(root, "FechaVisita");

        if (valoracionStr == null || comentario == null || fechaVisitaStr == null) {
            throw new XMLParsingException("Faltan campos obligatorios en la Review.");
        }

        int valoracion;
        try {
            valoracion = Integer.parseInt(valoracionStr);
        } catch (NumberFormatException e) {
            throw new XMLParsingException("Valoración inválida: " + valoracionStr);
        }

        Date fechaVisita;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            fechaVisita = sdf.parse(fechaVisitaStr);
        } catch (ParseException e) {
            throw new XMLParsingException("Fecha de visita inválida: " + fechaVisitaStr);
        }

        // --- Autor (Cliente) ---
        Cliente autor = null;
        Element autorElem = (Element) root.getElementsByTagName("Cliente").item(0);
        if (autorElem != null) {
            autor = parseCliente(autorElem);
        }

        // Crear el objeto Review usando el constructor principal
        Review r = new Review(valoracion, comentario, fechaVisita, autor);

        // Si el XML tiene fechaEscritura, la fijamos por reflexión (ya que es privada)
        String fechaEscrituraStr = getTagValue(root, "FechaEscritura");
        if (fechaEscrituraStr != null) {
            try {
                Date fechaEscritura = new SimpleDateFormat("yyyy-MM-dd").parse(fechaEscrituraStr);
                java.lang.reflect.Field f = Review.class.getDeclaredField("fechaEscritura");
                f.setAccessible(true);
                f.set(r, fechaEscritura);
            } catch (Exception e) {
                throw new XMLParsingException("Error al asignar fecha de escritura: " + e.getMessage());
            }
        }

        return r;
    }

    public static Contestacion parseContestacion(String xmlStr) throws XMLParsingException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(xmlStr.getBytes()));

            return parseContestacionFromDocument(doc);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new XMLParsingException("Error al parsear el XML de Contestacion: " + e.getMessage());
        }
    }
    public static Contestacion parseContestacion(File f) throws XMLParsingException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(f);

            return parseContestacionFromDocument(doc);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new XMLParsingException("Error al leer el archivo XML de Contestacion: " + e.getMessage());
        }
    }
    private static Contestacion parseContestacionFromDocument(Document doc) throws XMLParsingException {
    Element root = doc.getDocumentElement();
    
    if (!root.getNodeName().equalsIgnoreCase("Contestacion"))
        throw new XMLParsingException("El XML no representa un objeto <Contestacion>.");

    // --- Propietario ---
    Element propietarioElem = (Element) root.getElementsByTagName("Propietario").item(0);
    if (propietarioElem == null) {
        throw new XMLParsingException("Faltan datos de propietario.");
    }
    Propietario propietario = parsePropietario(propietarioElem);

    // --- Texto de la contestación ---
    String texto = getTagValue(root, "ContestacionTexto");
    if (texto == null) {
        throw new XMLParsingException("Faltan datos de contestacion en la Contestacion.");
    }

    // --- Fecha de escritura ---
    String fechaStr = getTagValue(root, "FechaEscritura");
    if (fechaStr == null) {
        throw new XMLParsingException("Faltan FechaEscritura en la Contestacion.");
    }

    Contestacion c = new Contestacion(propietario, null, texto);

    try {
        Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fechaStr);
        java.lang.reflect.Field f = Contestacion.class.getDeclaredField("fechaEscritura");
        f.setAccessible(true);
        f.set(c, fecha);
    } catch (Exception e) {
        throw new XMLParsingException("Error al asignar fecha de escritura: " + e.getMessage());
    }

    return c;
}


    private static Document buildDocumentFromElement(Element element) throws XMLParsingException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document newDoc = builder.newDocument();
            Element imported = (Element) newDoc.importNode(element, true);
            newDoc.appendChild(imported);
            return newDoc;
        } catch (ParserConfigurationException e) {
            throw new XMLParsingException("Error interno al construir documento auxiliar: " + e.getMessage());
        }
    }
    
    public static Direccion parseDireccion(String xmlStr) throws XMLParsingException {
    try {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(xmlStr.getBytes()));
        Element root = doc.getDocumentElement();

        return parseDireccionFromElement(root);
    } catch (ParserConfigurationException | SAXException | IOException e) {
        throw new XMLParsingException("Error al parsear el XML de Direccion: " + e.getMessage());
    }
}
    public static Direccion parseDireccion(File f) throws XMLParsingException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(f);
            Element root = doc.getDocumentElement();

            return parseDireccionFromElement(root);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new XMLParsingException("Error al leer el archivo XML de Direccion: " + e.getMessage());
        }
    }
    private static Direccion parseDireccionFromElement(Element e) throws XMLParsingException {
        if (e == null)
            throw new XMLParsingException("Elemento <direccion> nulo o mal formado.");

        String localidad = getTagValue(e, "Localidad");
        String provincia = getTagValue(e, "Provincia");
        String calle = getTagValue(e, "Calle");
        String numeroStr = getTagValue(e, "Numero");

        if (localidad == null || provincia == null || calle == null || numeroStr == null)
            throw new XMLParsingException("Datos incompletos en la dirección.");

        int numero;
        try {
            numero = Integer.parseInt(numeroStr);
        } catch (NumberFormatException ex) {
            throw new XMLParsingException("Número de dirección inválido: " + numeroStr);
        }

        return new Direccion(localidad, provincia, calle, numero);
    }
    
    public static Propietario parsePropietario(File file) throws XMLParsingException {
    try {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();

        Element root = doc.getDocumentElement();
        if (!root.getTagName().equals("Propietario")) {
            throw new XMLParsingException("Raíz inválida: se esperaba <propietario>.");
        }

        return parsePropietario(root);

    } catch (Exception ex) {
        throw new XMLParsingException("Error al parsear propietario desde archivo: " + ex.getMessage());
    }
}
public static Propietario parsePropietario(String xmlContent) throws XMLParsingException {
    try {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        InputSource is = new InputSource(new java.io.StringReader(xmlContent));
        Document doc = dBuilder.parse(is);
        doc.getDocumentElement().normalize();

        Element root = doc.getDocumentElement();
        if (!root.getTagName().equals("Propietario")) {
            throw new XMLParsingException("Raíz inválida: se esperaba <propietario>.");
        }

        return parsePropietario(root);

    } catch (Exception ex) {
        throw new XMLParsingException("Error al parsear propietario desde String: " + ex.getMessage());
    }
}

public static Pub parsePub(String xmlStr) throws XMLParsingException {
    try {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(xmlStr.getBytes()));

        return parsePubFromDocument(doc);

    } catch (ParserConfigurationException | SAXException | IOException e) {
        throw new XMLParsingException("Error al parsear el XML de Pub: " + e.getMessage());
    }
}

public static Pub parsePub(File f) throws XMLParsingException {
    try {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(f);

        return parsePubFromDocument(doc);

    } catch (ParserConfigurationException | SAXException | IOException e) {
        throw new XMLParsingException("Error al leer el archivo XML de Pub: " + e.getMessage());
    }
}

private static Pub parsePubFromDocument(Document doc) throws XMLParsingException {

    Element root = doc.getDocumentElement();
    if (!root.getNodeName().equals("Pub"))
        throw new XMLParsingException("El XML no representa un objeto <Pub>.");

    // nombre
    String nombre = getTagValue(root, "Nombre");

    if (nombre == null)
        throw new XMLParsingException("Falta el nombre en el XML Pub.");

    // horas
    String hAperturaStr = getTagValue(root, "HoraApertura");
    String hCierreStr = getTagValue(root, "HoraCierre");

    int horaApertura = 0;
    int horaCierre = 0;

    if (hAperturaStr != null) {
        try {
            horaApertura = Integer.parseInt(hAperturaStr);
        } catch (NumberFormatException e) {
            throw new XMLParsingException("Hora apertura inválida: " + hAperturaStr);
        }
    }

    if (hCierreStr != null) {
        try {
            horaCierre = Integer.parseInt(hCierreStr);
        } catch (NumberFormatException e) {
            throw new XMLParsingException("Hora cierre inválida: " + hCierreStr);
        }
    }

    // dirección
    Element dirElem = (Element) root.getElementsByTagName("Direccion").item(0);
    Direccion direccion = parseDireccion(dirElem);

    // propietarios
    List<Propietario> duenos = new ArrayList<>();
    NodeList propietarios = root.getElementsByTagName("Propietario");
    for (int i = 0; i < propietarios.getLength(); i++) {
        Element e = (Element) propietarios.item(i);
        duenos.add(parsePropietario(e));
    }

    return new Pub(nombre, direccion, duenos, horaApertura, horaCierre);
}

public static Reserva parseReserva(String xmlStr) throws XMLParsingException {
    try {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(xmlStr.getBytes()));

        return parseReservaFromDocument(doc);

    } catch (ParserConfigurationException | SAXException | IOException e) {
        throw new XMLParsingException("Error al parsear XML de Reserva: " + e.getMessage());
    }
}

public static Reserva parseReserva(File f) throws XMLParsingException {
    try {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(f);

        return parseReservaFromDocument(doc);

    } catch (ParserConfigurationException | SAXException | IOException e) {
        throw new XMLParsingException("Error al leer archivo XML de Reserva: " + e.getMessage());
    }
}

private static Reserva parseReservaFromDocument(Document doc) throws XMLParsingException {

    Element root = doc.getDocumentElement();
    if (!root.getNodeName().equals("Reserva"))
        throw new XMLParsingException("El XML no representa un objeto <Reserva>.");

    // --- Campos básicos ---
    String fechaStr = getTagValue(root, "FechaReserva");
    String descuentoStr = getTagValue(root, "Descuento");

    if (fechaStr == null || descuentoStr == null)
        throw new XMLParsingException("Faltan campos obligatorios en Reserva.");

    // --- Fecha ---
    Date fecha;
    try {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        fecha = sdf.parse(fechaStr);
    } catch (ParseException ex) {
        throw new XMLParsingException("Fecha de reserva inválida: " + fechaStr);
    }

    // --- Descuento ---
    int descuento;
    try {
        descuento = Integer.parseInt(descuentoStr);
    } catch (NumberFormatException e) {
        throw new XMLParsingException("Descuento inválido: " + descuentoStr);
    }

    // --- Cliente ---
    Element clienteElem = (Element) root.getElementsByTagName("Cliente").item(0);
    if (clienteElem == null)
        throw new XMLParsingException("Falta <Cliente> en la Reserva.");
    Cliente cliente = parseCliente(clienteElem);


    return new Reserva(fecha, descuento, cliente, null);
}


private static Cliente parseCliente(Element e) throws XMLParsingException {
    if (e == null)
        throw new XMLParsingException("Elemento <Cliente> nulo o mal formado.");

    String id = getTagValue(e, "Id");
    String nick = getTagValue(e, "Nick");
    String contrasenia = getTagValue(e, "Contrasenia");
    String edadStr = getTagValue(e, "Edad");
    String fechaStr = getTagValue(e, "FechaNacimiento");

    if (id == null || nick == null || contrasenia == null || edadStr == null || fechaStr == null)
        throw new XMLParsingException("Datos incompletos del Cliente.");

    int edad;
    try {
        edad = Integer.parseInt(edadStr);
    } catch (NumberFormatException ex) {
        throw new XMLParsingException("Edad inválida: " + edadStr);
    }

    Date fecha;
    try {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        fecha = sdf.parse(fechaStr);
    } catch (ParseException ex) {
        throw new XMLParsingException("Fecha de nacimiento inválida: " + fechaStr);
    }

    // Reviews de cliente NO vienen en XML → se inicializa vacía
    List<Review> reviews = new ArrayList<>();

    return new Cliente(reviews, id, nick, contrasenia, edad, fecha);
}

public static Restaurante parseRestaurante(String xmlStr) throws XMLParsingException {
    try {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(xmlStr.getBytes()));

        return parseRestauranteFromDocument(doc);

    } catch (ParserConfigurationException | SAXException | IOException e) {
        throw new XMLParsingException("Error al parsear el XML de Restaurante: " + e.getMessage());
    }
}

public static Restaurante parseRestaurante(File f) throws XMLParsingException {
    try {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(f);

        return parseRestauranteFromDocument(doc);

    } catch (ParserConfigurationException | SAXException | IOException e) {
        throw new XMLParsingException("Error al leer el archivo XML de Restaurante: " + e.getMessage());
    }
}

private static Restaurante parseRestauranteFromDocument(Document doc) throws XMLParsingException {

    Element root = doc.getDocumentElement();
    if (!root.getNodeName().equals("Restaurante"))
        throw new XMLParsingException("El XML no representa un objeto <Restaurante>.");

    // nombre
    String nombre = getTagValue(root, "Nombre");
    if (nombre == null)
        throw new XMLParsingException("Falta el nombre en el XML Restaurante.");

    // valores numéricos
    float precioMenu;
    int capComensales;
    int capMesa;

    try {
        precioMenu = Float.parseFloat(getTagValue(root, "PrecioMenu"));
    } catch (Exception ex) {
        throw new XMLParsingException("precioMenu inválido");
    }

    try {
        capComensales = Integer.parseInt(getTagValue(root, "CapacidadComensales"));
    } catch (Exception ex) {
        throw new XMLParsingException("capacidadComensales inválido");
    }

    try {
        capMesa = Integer.parseInt(getTagValue(root, "CapacidadComensalesMesa"));
    } catch (Exception ex) {
        throw new XMLParsingException("capacidadComensalesMesa inválido");
    }

    // dirección
    Element dirElem = (Element) root.getElementsByTagName("Direccion").item(0);
    Direccion direccion = parseDireccion(dirElem);

    // propietarios
    List<Propietario> duenos = new ArrayList<>();
    NodeList propietarios = root.getElementsByTagName("Propietario");
    for (int i = 0; i < propietarios.getLength(); i++) {
        Element e = (Element) propietarios.item(i);
        duenos.add(parsePropietario(e));
    }

    return new Restaurante(nombre, direccion, duenos, precioMenu, capComensales, capMesa);
}
    
public static Bar parseBarBS(Element e) throws XMLParsingException {
    String xml = nodeToString(e);
    return parseBar(xml);
}
private static String nodeToString(Node node) throws XMLParsingException {
    try {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        StringWriter buff = new StringWriter();
        transformer.transform(new DOMSource(node), new StreamResult(buff));
        return buff.toString();
    } catch(Exception ex) {
        throw new XMLParsingException("Error convirtiendo nodo a string");
    }
}
public static Pub parsePubBS(Element e) throws XMLParsingException {
    String xml = nodeToString(e);
    return parsePub(xml);
}
public static Restaurante parseRestauranteBS(Element e) throws XMLParsingException {
    String xml = nodeToString(e);
    return parseRestaurante(xml);
}
public static Cliente parseClienteBS(Element e) throws XMLParsingException {
    String xml = nodeToString(e);
    return parseCliente(xml);
}
public static Propietario parsePropietarioBS(Element e) throws XMLParsingException {
    String xml = nodeToString(e);
    return parsePropietario(xml);
}
public static Review parseReviewBS(Element e) throws XMLParsingException {
    String xml = nodeToString(e);
    return parseReview(xml);
}
public static Contestacion parseContestacionBS(Element e) throws XMLParsingException {
    String xml = nodeToString(e);
    return parseContestacion(xml);
}
public static Reserva parseReservaBS(Element e) throws XMLParsingException {
    String xml = nodeToString(e);
    return parseReserva(xml);
}

}
