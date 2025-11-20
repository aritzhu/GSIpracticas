/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package Dominio.BTesting.P02;

import Applicacion.BSystem.BusinessSystem;


/**
 *
 * @author alumno
 */
public class Tester02 {
    private BusinessSystem bs;
    public Tester02() {
    }
    
    public void setUp() {
        this.bs = new BusinessSystem(); 
    }
    
    public String userNotExistNull() {
        // TODO review the generated test code and remove the default call to fail.
        
        this.setUp();
        if(this.bs.obtenerUsuario("sdfghfh") == null) {
            return "OK";
        }
        return "Error";
    }
    
}
