/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dominio.BTesting.P03;

import Applicacion.BSystem.BusinessSystem;
import Dominio.BModel.Direccion;
import Dominio.IBModelo.Local;

/**
 *
 * @author alumno
 */
public class Tester03 {
    private BusinessSystem bs;
    public Tester03() {
    }
    
    public void setUp() {
        this.bs = new BusinessSystem(); 
    }
    
    public String localDirecctionMustBeUnique() {
        // TODO review the generated test code and remove the default call to fail.
        
        
        this.setUp();
        
        Direccion direccion1 = new Direccion("Villanueva","Navarra","hay",1);
         Direccion direccion = new Direccion("Villanueva","Navarra","hay",2);
        
        Local local1 = new Local("si1",direccion,null);
        Local local11 = new Local("si1",direccion1,null);
        
        this.bs.nuevoLocal(local1);
        if(!this.bs.nuevoLocal(local11)) {
            return "OK";
        }
        return "Error";
    }
}
