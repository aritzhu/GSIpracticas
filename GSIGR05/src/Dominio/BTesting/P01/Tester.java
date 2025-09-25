/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio.BTesting.P01;

import Applicacion.BSystem.*;
import Dominio.BModel.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author alumno
 */
public class Tester {
    public static void main(String[] args) {
        try {
            BusinessSystem bs = new BusinessSystem();

            List<Review> reviews = new ArrayList<>();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaNacimiento = sdf.parse("15/09/1990");

            Cliente cliente = new Cliente(reviews, "001", "Mario", "1234pass", 34, fechaNacimiento);

            if (bs.nuevoUsuario(cliente)) {
                System.out.println("Cliente creado");
            } else {
                System.out.println("Error al crear");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
