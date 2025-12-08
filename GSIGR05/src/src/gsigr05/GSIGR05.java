/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gsigr05;

import Dominio.BTesting.P01.Tester;
import Dominio.BTesting.P02.Tester02;
import Dominio.BTesting.P03.Tester03;
import Dominio.BTesting.P04.Tester04;
import Dominio.BTesting.P05.Tester05;
import Dominio.BTesting.P06.Tester06;
import Dominio.BTesting.P08.Tester08;
import Dominio.BTesting.P09.Tester09;
import Dominio.BTesting.P10.Tester10;

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
        Tester test1= new Tester();
        test1.test1(); 
        
        Tester02 test2 = new Tester02();
        System.out.println(test2.userNotExistNull()); 
        
        Tester03 test3 = new Tester03();
        System.out.println(test3.localDirecctionMustBeUnique()); 
        
        Tester04 test4 = new Tester04();
        System.out.println(test4.localDeletedCanReinsert()); 
        
        Tester05 test5 = new Tester05();
        test5.edadCorrect();
        
        Tester06 test6 = new Tester06();
        test6.reservaSinBar();
        
        Tester08 test8 = new Tester08();
        test8.comprobarCOntestacion();
        
        Tester09 test9 = new Tester09();
        test9.numeroCorrectoDeDueños();
        
        Tester10 test10 = new Tester10();
        test10.validarReview();
        
    }
    
    
}
