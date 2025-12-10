package Applicacion.BSystem;

import Dominio.BModel.*;
import Dominio.IBModelo.Local;
import GSILabs.connect.AdminGateway;
import GSILabs.connect.ClientGateway;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PublicBusinessSystem extends BusinessSystem implements ClientGateway, AdminGateway {

    @Override
    public Boolean eliminaLocal(Local l) throws RemoteException {
        if (l == null) return false;
        Local real = getLocal(l.getNombre());
        return real != null && eliminarLocal(real);
    }

    @Override
    public Boolean eliminaReviewsDeLocal(Local l) throws RemoteException {
        if (l == null) return false;
        Local real = getLocal(l.getNombre());
        if (real == null) return false;
        Review[] reviews = verReviews(real);
        boolean resultado = true;
        if (reviews != null) {
            for (Review r : reviews) {
                resultado &= eliminaReview(r);
            }
        }
        return resultado;
    }

    @Override
    public Boolean eliminaReviewRMI(Review r) throws RemoteException {
        return eliminaReview(r);
    }

    @Override
    public Integer eliminaReviewsDeUsuario(Cliente c) throws RemoteException {
        if (c == null) return -1;
        List<Review> toRemove = new ArrayList<>();
        for (Review r : super.getDatabase().getReviews()) {
            if (r.getAutor().equals(c)) {
                toRemove.add(r);
            }
        }
        for (Review r : toRemove) eliminaReview(r);
        return toRemove.size();
    }

    @Override
    public Boolean insertaReviewFalsa(Local l, Integer puntuacion) throws RemoteException {
        if (l == null || puntuacion == null) return false;
        Cliente clienteFalso = new Cliente(List.of(),"fakeID","fakeNick","fakePass",99,new Date());
        Review r = new Review(puntuacion, "Review falsa", new Date(), clienteFalso);
        Local real = getLocal(l.getNombre());
        if (real == null) return false;
        real.addReview(r);
        return nuevaReview(r);
    }

    @Override
    public boolean insertaReview(Review r) throws RemoteException {
        return nuevaReview(r);
    }

    @Override
    public boolean quitaReview(Review r) throws RemoteException {
        return eliminaReview(r);
    }

    @Override
    public Bar mejorBar(String ciudad) throws RemoteException {
        Bar mejor = null;
        double mejorPunt = -1;
        for (Local l : super.getDatabase().getLocales()) {
            if (l instanceof Bar && (ciudad == null || ciudad.isEmpty() || l.getDireccion().getLocalidad().equalsIgnoreCase(ciudad))) {
                double media = calcularMedia(l);
                if (media > mejorPunt) {
                    mejor = (Bar) l;
                    mejorPunt = media;
                }
            }
        }
        return mejor;
    }

    @Override
    public Restaurante mejorRestaurante(String ciudad) throws RemoteException {
        Restaurante mejor = null;
        double mejorPunt = -1;
        for (Local l : super.getDatabase().getLocales()) {
            if (l instanceof Restaurante && (ciudad == null || ciudad.isEmpty() || l.getDireccion().getLocalidad().equalsIgnoreCase(ciudad))) {
                double media = calcularMedia(l);
                if (media > mejorPunt) {
                    mejor = (Restaurante) l;
                    mejorPunt = media;
                }
            }
        }
        return mejor;
    }

    @Override
    public Pub mejorPub(String ciudad) throws RemoteException {
        Pub mejor = null;
        double mejorPunt = -1;
        for (Local l : super.getDatabase().getLocales()) {
            if (l instanceof Pub && (ciudad == null || ciudad.isEmpty() || l.getDireccion().getLocalidad().equalsIgnoreCase(ciudad))) {
                double media = calcularMedia(l);
                if (media > mejorPunt) {
                    mejor = (Pub) l;
                    mejorPunt = media;
                }
            }
        }
        return mejor;
    }

    private double calcularMedia(Local l) {
        List<Review> reviews = l.getReviews();
        return reviews != null && !reviews.isEmpty() ?
                reviews.stream().mapToInt(Review::getValoracion).average().orElse(0) : 0;
    }

    @Override
    public Restaurante[] mejoresRestaurantes(String ciudad, Integer num) throws RemoteException {
        Restaurante[] todos = listarRestaurantes(ciudad, null);
        Arrays.sort(todos, (r1, r2) -> {
            double media1 = r1.getReviews().stream().mapToInt(Review::getValoracion).average().orElse(0);
            double media2 = r2.getReviews().stream().mapToInt(Review::getValoracion).average().orElse(0);
            return Double.compare(media2, media1);
        });
        Restaurante[] top = new Restaurante[num];
        for (int i = 0; i < num; i++) {
            top[i] = i < todos.length ? todos[i] : null;
        }
        return top;
    }

    @Override
    public Local getLocal(String name) throws RemoteException {
        if (name == null) name = "";
        for (Local l : super.getDatabase().getLocales()) {
            if (l.getNombre().equals(name)) {
                return l;
            }
        }
        return null;
    }

    @Override
    public Local[] getLocals(String name) throws RemoteException {
        if (name == null) name = "";
        List<Local> encontrados = new ArrayList<>();
        for (Local l : super.getDatabase().getLocales()) {
            if (l.getNombre() != null && l.getNombre().contains(name)) {
                encontrados.add(l);
            }
        }
        return encontrados.toArray(new Local[0]);
    }

    @Override
    public Local[] getLocalesEnCiudad(String ciudad) throws RemoteException {
        if (ciudad == null || ciudad.isEmpty()) return new Local[0];
        
        List<Local> encontrados = new ArrayList<>();
        // Recorremos todos los locales de la base de datos
        for (Local l : super.getDatabase().getLocales()) {
            // Comprobamos si la dirección coincide con la ciudad solicitada (ignorando mayúsculas/minúsculas)
            if (l.getDireccion().getLocalidad().equalsIgnoreCase(ciudad)) {
                encontrados.add(l);
            }
        }
        return encontrados.toArray(new Local[0]);
    }
    // He actualizado esta función para que detecte ciudades de CUALQUIER local, no solo restaurantes
    @Override
    public String[] getCiudadesConLocales() throws RemoteException {
        Set<String> ciudadesUnicas = new HashSet<>();
        for (Local l : super.getDatabase().getLocales()) { 
             // Ahora coge ciudades de Bares, Pubs y Restaurantes
            if (l != null && l.getDireccion() != null) {
                String ciudad = l.getDireccion().getLocalidad();
                if (ciudad != null && !ciudad.isEmpty()) {
                    ciudadesUnicas.add(ciudad);
                }
            }
        }
        return ciudadesUnicas.toArray(new String[0]);
    }
    @Override
    public boolean publicarReview(String nombreLocal, Review r) throws RemoteException {
        // 1. Buscamos el local real en la base de datos
        Local l = getLocal(nombreLocal);
        if (l != null) {
            // 2. Añadimos la review a la lista del local
            l.addReview(r);
            // 3. Registramos la review en el sistema global
            return nuevaReview(r);
        }
        return false;
    }
    @Override
    
    public Review[] getReviewsDeLocal(String nombreLocal, Cliente cliente) throws RemoteException {
        Local l = getLocal(nombreLocal);
        List<Review> reviewsFiltradas = new ArrayList<>();

        if (l != null && l.getReviews() != null) {
            for (Review r : l.getReviews()) {
                // LÓGICA MEJORADA:
                // Si 'cliente' es null, las devolvemos TODAS (modo ver).
                // Si 'cliente' NO es null, filtramos por ID (modo borrar).
                if (cliente == null || r.getAutor().getID().equals(cliente.getID())) {
                    reviewsFiltradas.add(r);
                }
            }
            return reviewsFiltradas.toArray(new Review[0]);
        }
        return new Review[0];
    }
}
