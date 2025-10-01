package Dominio.BTesting.P10;

import Dominio.BModel.Bar;
import Dominio.BModel.Direccion;
import Dominio.BModel.Propietario;
import Dominio.BModel.Review;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Tester10 {
    public static void main(String[] args) {
        try {
            List<Review> reviews = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaNacimiento = sdf.parse("15/11/1990");

            // Creamos usuarios
            Propietario usuario1 = new Propietario(reviews, "001", "Mario", "1234pass", 34, fechaNacimiento);

            // Creamos dirección y local
            Direccion direccion = new Direccion("Pamplona", "Navarra", "Calle Mayor", 10);
            List<Propietario> duenosIniciales = new ArrayList<>();
            duenosIniciales.add(usuario1);

            Bar bar = new Bar("Bar Central", direccion, duenosIniciales);

            
            Date fechaVisita = sdf.parse("01/10/2025");
            Review review1 = new Review(5, "¡Excelente ambiente!", fechaVisita, usuario1);
            bar.anadirReview(review1);

    
            Review review2 = new Review(4, "Segunda review del mismo día", fechaVisita, usuario1);
            if (!bar.anadirReview(review2)) {
                System.out.println("No se pudo añadir la segunda review del mismo usuario el mismo día.");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
