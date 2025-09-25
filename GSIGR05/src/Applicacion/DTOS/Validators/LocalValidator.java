package Applicacion.DTOS.Validators;

import Applicacion.BSystem.BusinessSystem;
import Applicacion.BSystem.EjecuctionTimeDataBase;
import Dominio.IBModelo.Local;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author alumno
 */
public class LocalValidator {

    public boolean ValidarLocal(Local local, EjecuctionTimeDataBase dataBase) {
        List<Local> locales = dataBase.getLocales();
        for (Local l : locales) 
        {
            if (l.getDireccion().getCalle().equals(local.getDireccion().getCalle()) 
                    && l.getDireccion().getNumero() == local.getDireccion().getNumero() )
                return false;
        }
        return true;
    }
}
