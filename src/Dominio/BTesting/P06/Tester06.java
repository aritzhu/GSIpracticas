/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio.BTesting.P06;

import Applicacion.BSystem.BusinessSystem;
import Dominio.BModel.Bar;
import Dominio.BModel.Cliente;
import Dominio.BModel.Direccion;
import Dominio.BModel.Propietario;
import Dominio.BModel.Reservable;
import Dominio.BModel.Review;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author alumno
 */
public class Tester06 {
    
        public Tester06() {
    }
    
    public void reservaSinBar() {
        try {
            BusinessSystem bs = new BusinessSystem();

            List<Review> reviews = new ArrayList<>();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaNacimiento = sdf.parse("15/11/1990");
            
            
            Propietario dueno1 = new Propietario("001", "Mario", "1234pass", 34, fechaNacimiento);
            
            Direccion direccion = new Direccion("Pamplona", "Navarra", "Calle Mayor", 10);
            List<Propietario> duenosIniciales = new ArrayList<>();
            duenosIniciales.add(dueno1);

            Bar bar = new Bar("Bar Central", direccion, duenosIniciales);
            
            Cliente cliente = new Cliente(reviews, "001", "Mario", "1234pass", 34, fechaNacimiento);
            bs.nuevaReserva(cliente, bar, null, null);
        } catch (Exception e) {
            System.out.println("OK");
            //e.printStackTrace();
        }
    }
}
