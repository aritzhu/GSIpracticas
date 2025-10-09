/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio.BTesting.P08;

import Applicacion.BSystem.BusinessSystem;
import Dominio.BModel.Contestacion;
import Dominio.BModel.Review;

/**
 *
 * @author alumno
 */
public class Tester08 {
    public void comprobarCOntestacion() {
        BusinessSystem bs = new BusinessSystem();
        Contestacion c = new Contestacion();
        Review r = new Review();
        
        if(bs.nuevaContestacion(c, r)){
            System.out.println("Contestacion correcta");
        } else {
            System.out.println("No existe review");
        }
    }
}
