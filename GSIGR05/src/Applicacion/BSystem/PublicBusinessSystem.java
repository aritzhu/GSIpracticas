package Applicacion.BSystem;

import Dominio.BModel.*;
import Dominio.IBModelo.Local;
import GSILabs.connect.AdminGateway;
import GSILabs.connect.ClientGateway;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Clase que expone el BusinessSystem a RMI para clientes y administradores
 */
public class PublicBusinessSystem extends BusinessSystem implements ClientGateway, AdminGateway {

    // ---------------- AdminGateway ----------------

    @Override
    public Boolean eliminaLocal(Local l) throws RemoteException {
        return eliminarLocal(l);
    }

    @Override
    public Boolean eliminaReviewsDeLocal(Local l) throws RemoteException {
        if (l == null) return false;
        Review[] reviews = verReviews(l);
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
        return super.getDatabase().getReviews().remove(r);
    }


    @Override
    public Integer eliminaReviewsDeUsuario(Cliente c) throws RemoteException {
        if (c == null) return -1;
        int cont = 0;
        for (Review r : super.getDatabase().getReviews()) {
            if (r.getAutor().equals(c)) {
                if (eliminaReview(r)) cont++;
            }
        }
        return cont;
    }

    @Override
    public Boolean insertaReviewFalsa(Local l, Integer puntuacion) throws RemoteException {
        if (l == null || puntuacion == null) return false;
        Cliente clienteFalso = new Cliente(List.of(),"fakeID","fakeNick","fakePass",99,new Date() );

        Review r = new Review(puntuacion, "Review falsa", new Date(), clienteFalso);

        return nuevaReview(r);
    }


    // ---------------- ClientGateway ----------------

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
            if (l instanceof Bar && (ciudad == null || ciudad.isEmpty() || l.getDireccion().getLocalidad().equals(ciudad))) {
                List<Review> reviews = l.getReviews(); // tu método devuelve List<Review>
                double media = 0;
                if (reviews != null && !reviews.isEmpty()) {
                    double suma = 0;
                    for (Review r : reviews) {
                        suma += r.getValoracion();
                    }
                    media = suma / reviews.size();
                }

                if (media > mejorPunt) {
                    mejor = (Bar) l;
                    mejorPunt = media;
                }
            }
        }
        return mejor;
    }



    @Override
    public Restaurante[] mejoresRestaurantes(String ciudad, Integer num) throws RemoteException {
        Restaurante[] todos = listarRestaurantes(ciudad, null);

        // Ordenar de mayor a menor según la media de reviews
        Arrays.sort(todos, (r1, r2) -> {
            double media1 = r1.getReviews().stream().mapToInt(Review::getValoracion).average().orElse(0);
            double media2 = r2.getReviews().stream().mapToInt(Review::getValoracion).average().orElse(0);
            return Double.compare(media2, media1); // descendente
        });

        // Crear array del tamaño exacto que se pide
        Restaurante[] top = new Restaurante[num];
        for (int i = 0; i < num; i++) {
            if (i < todos.length) {
                top[i] = todos[i];
            } else {
                top[i] = null; // rellenar con null si no hay suficientes
            }
        }

        return top;
    }



    // ---------------- LocalFinder ----------------

    @Override
    public Local getLocal(String name) throws RemoteException {
        for (Local l : super.getDatabase().getLocales()) {
            if (l.getNombre().equals(name)) {
                return l;
            }
        }
        return null;
    }

    @Override
    public Local[] getLocals(String name) throws RemoteException {
        List<Local> encontrados = new ArrayList<>();
        for (Local l : super.getDatabase().getLocales()) {
            if (l.getNombre() != null && l.getNombre().contains(name)) {
                encontrados.add(l);
            }
        }
        return encontrados.toArray(new Local[0]);
    }
}
