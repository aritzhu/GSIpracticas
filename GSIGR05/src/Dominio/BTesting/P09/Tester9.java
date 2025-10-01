/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio.BTesting.P09;

import Applicacion.BSystem.BusinessSystem;
import Dominio.BModel.Bar;
import Dominio.BModel.Direccion;
import Dominio.BModel.Propietario;
import Dominio.BModel.Review;
import Dominio.IBModelo.Local;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



/**
 *
 * @author inaki
 */

public class Tester9 {
    public static void main(String[] args) {
        try {
            BusinessSystem bs = new BusinessSystem();

            List<Review> reviews = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaNacimiento = sdf.parse("15/11/1990");

            Propietario dueno1 = new Propietario(reviews, "001", "Mario", "1234pass", 34, fechaNacimiento);
            Propietario dueno2 = new Propietario(reviews, "002", "Ana", "pass5678", 28, fechaNacimiento);
            Propietario dueno3 = new Propietario(reviews, "003", "Luis", "qwerty", 40, fechaNacimiento);
            Propietario dueno4 = new Propietario(reviews, "004", "Laura", "zxcvb", 22, fechaNacimiento);

            Direccion direccion = new Direccion("Pamplona", "Navarra", "Calle Mayor", 10);
            List<Propietario> duenosIniciales = new ArrayList<>();
            duenosIniciales.add(dueno1);

            Bar bar = new Bar("Bar Central", direccion, duenosIniciales);

            // Añadimos 2 dueños más (correcto)
            bar.anadirDueño(dueno2);
            bar.anadirDueño(dueno3);

            // Intentamos añadir un cuarto dueño (debería rechazarlo)
            bar.anadirDueño(dueno4);
            

            // Comprobamos la regla de negocio
            if (bar.dueñosNecesarios()) {
                System.out.println("El bar tiene un número válido de dueños.");
            } else {
                System.out.println("Número incorrecto de dueños en el bar.");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
