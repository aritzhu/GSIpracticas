/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gsigr05;

import Dominio.BTesting.P02.Tester02;
import Dominio.BTesting.P03.Tester03;
import Dominio.BTesting.P04.Tester04;

/**
 *
 * @author alumno
 */
public class GSIGR05 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Tester02 test2 = new Tester02();
        System.out.println(test2.userNotExistNull()); 
        
        Tester03 test3 = new Tester03();
        System.out.println(test3.localDirecctionMustBeUnique()); 
        
        Tester04 test4 = new Tester04();
        System.out.println(test4.localDeletedCanReinsert()); 
        
    }
    
    
}
