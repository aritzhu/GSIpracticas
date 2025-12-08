package Dominio.BTesting.P04;


import Applicacion.BSystem.BusinessSystem;
import Dominio.BModel.Direccion;
import Dominio.IBModelo.Local;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author alumno
 */
public class Tester04 {
    
    private BusinessSystem bs;
    public Tester04() {
    }
    
    public void setUp() {
        this.bs = new BusinessSystem(); 
    }
    
    public String localDeletedCanReinsert() {
        // TODO review the generated test code and remove the default call to fail.
        
        
        this.setUp();
        
        Direccion direccion = new Direccion("Villanueva","Navarra","hay",1);
        
        Local local1 = new Local("si1",direccion,null);
        
        this.bs.nuevoLocal(local1);
        
        this.bs.eliminarLocal(local1);
        
        if(this.bs.nuevoLocal(local1)) {
            return "OK";
        }
        return "Error";
    }
}
