/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio.BTesting.P05;

import Applicacion.BSystem.BusinessSystem;
import Dominio.BModel.Cliente;
import Dominio.BModel.Review;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author alumno
 */
public class Tester05 {
    public static void main(String[] args) {
        try {
            BusinessSystem bs = new BusinessSystem();

            List<Review> reviews = new ArrayList<>();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaNacimiento = sdf.parse("15/11/2011");

            Cliente cliente = new Cliente(reviews, "001", "Mario", "1234pass", 34, fechaNacimiento);

            if (cliente.esMayorDeEdad()) {
                System.out.println("Cliente mayor de edad");
            } else {
                System.out.println("Cliente menor de edad");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    
}
