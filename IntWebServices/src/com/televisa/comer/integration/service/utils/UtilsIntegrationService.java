/**
* Project: Integraton Paradigm - EveTV
*
* File: UtilsIntegrationService.java
*
* Created on: Septiembre 23, 2017 at 11:00
*
* Copyright (c) - OMW - 2017
*/
package com.televisa.comer.integration.service.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Clase que contiene diversos metodos utiles para el desarrollo de los diferentes modulos
 * en la capa de servicio
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Septiembre 23, 2017, 12:00 pm
 */
public class UtilsIntegrationService {
    
    public static void main(String a[]){
        UtilsIntegrationService lsUlService = new UtilsIntegrationService();
        String                  lsCadena = "15-10-2017";
        System.out.println("["+lsCadena+"] es Fecha: ("+lsUlService.isDateNeptuno(lsCadena)+")");
    }
    
    /** Valida si la cadena tiene formato de precio paradigm
      * @autor Jorge Luis Bautista 
      * @param tsCadena
      * @return boolean
    */
    public boolean isPriceFormat(String tsCadena){
        boolean lbRes = false;
        lbRes = validateRegularExpression(tsCadena, "^[0-9]+\\.?[0-9]{2}$");
        return lbRes;
    }
    
    /** Valida si la cadena es un numero
      * @autor Jorge Luis Bautista 
      * @param tsCadena
      * @return boolean
    */
    public boolean isNumeric(String tsCadena){
        return validateRegularExpression(tsCadena, "^[0-9]+$");            
    }
    
    /** Valida si la cadena es un numero de un digito
      * @autor Jorge Luis Bautista 
      * @param tsCadena
      * @return boolean
    */
    public boolean isNumericUnique(String tsCadena){
        boolean lbRes = true;
        if(tsCadena != null){
            if(tsCadena.length() > 1){
                lbRes = false;
            }else{
                lbRes = isNumeric(tsCadena);
            }
        }else{
            lbRes = false;
        }
        return lbRes;
    }
    
    /** Valida si la cadena es nulo
      * @autor Jorge Luis Bautista 
      * @param tsCadena
      * @return boolean
    */
    public boolean isNull(String tsCadena){        
        return tsCadena == null ? true : false;
    }
    
    /** Valida si la cadena es requerida
      * @autor Jorge Luis Bautista 
      * @param tsCadena
      * @param tsReq
      * @return boolean
    */
    public boolean isRequired(String tsCadena, String tsReq){        
        boolean lbRes = true;
        if(tsReq.equalsIgnoreCase("S")){
            if(tsCadena == null){
                lbRes = false;
            }else{
                if(tsCadena.length() < 1){
                    lbRes = false;
                }
            }
        }
        return lbRes;
    }
    
    /** Valida longitud de la cadena
      * @autor Jorge Luis Bautista 
      * @param tsCadena
      * @param tiSize
      * @return boolean
    */
    public boolean validateLength(String tsCadena, Integer tiSize){
        boolean lbRes = true;
        if(tsCadena != null){
            if(tsCadena.length() > tiSize){
                lbRes = false;
            }
        }else{
            lbRes = false;
        }
        return lbRes;
    }
    
    
    /** Valida si la cadena es un valor A o S
      * @autor Jorge Luis Bautista 
      * @param tsCadena
      * @return boolean
    */
    public boolean isInAS(String tsCadena){
        boolean lbRes = false;
        if(validateLength(tsCadena,1)){
            lbRes = validateRegularExpression(tsCadena, "[A|S]{1}");            
        }
        return lbRes;
    }
    
    /** Valida si la cadena es un valor A o S
      * @autor Jorge Luis Bautista 
      * @param tsCadena
      * @return boolean
    */
    public boolean isInSN(String tsCadena){
        boolean lbRes = false;
        if(validateLength(tsCadena,1)){
            lbRes = validateRegularExpression(tsCadena, "[S|N]{1}");            
        }
        return lbRes;
    }
    
    /** Valida si la cadena es un valor I, U o D
      * @autor Jorge Luis Bautista 
      * @param tsCadena
      * @return boolean
    */
    public boolean isInIUD(String tsCadena){
        boolean lbRes = false;
        if(validateLength(tsCadena,1)){
            lbRes = validateRegularExpression(tsCadena, "[I|U|D]$");                  
        }
        return lbRes;
    }
    
    /** Valida si la cadena es un valor I, U o D
      * @autor Jorge Luis Bautista 
      * @param tsCadena
      * @return boolean
    */
    public boolean isInCc(String tsCadena){
        boolean lbRes = false;
        if(validateLength(tsCadena,1)){
            lbRes = validateRegularExpression(tsCadena, "[C|c|U|u|D|d]$");                  
        }
        return lbRes;
    }
        
    
    /** Valida si la cadena tiene formato horario hh24:mi:ss
      * @autor Jorge Luis Bautista 
      * @param tsCadena
      * @return boolean
    */
    public boolean isFormatSchedule(String tsCadena){
        //return validateRegularExpression(tsCadena, "([0-1]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$");
        return true;
    }
    
    /** Valida si la cadena es requerida
      * @autor Jorge Luis Bautista 
      * @param tsCadena
      * @return boolean
    */
    public boolean isDateNeptuno(String tsCadena){        
        return validateRegularExpression(tsCadena, 
                                     "^(?:(?:0?[1-9]|1\\d|2[0-8])(\\/|-)(?:0?[1-9]|1[0-2]))(\\/|-)" +
                                     "(?:[1-9]\\d\\d\\d|\\d[1-9]\\d\\d|\\d\\d[1-9]\\d|\\d\\d\\d[1-9])$|^" +
                                     "(?:(?:31(\\/|-)(?:0?[13578]|1[02]))|(?:(?:29|30)(\\/|-)(?:0?[1,3-9]|1[0-2])))" +
                                     "(\\/|-)(?:[1-9]\\d\\d\\d|\\d[1-9]\\d\\d|\\d\\d[1-9]\\d|\\d\\d\\d[1-9])$|^" +
                                     "(29(\\/|-)0?2)(\\/|-)(?:(?:0[48]00|[13579][26]00|[2468][048]00)|(?:\\d\\d)?" +
                                     "(?:0[48]|[2468][048]|[13579][26]))$");            
    }
    
    /** Valida expresiones regulares de forma dinamica
      * @autor Jorge Luis Bautista 
      * @param tsClientString
      * @param tsRegularExpression
      * @return boolean
    */
    public boolean validateRegularExpression(String tsClientString, String tsRegularExpression){
        boolean lbReturn = false;
        String  lsToValidate = 
            tsClientString == null ? "" : tsClientString;
        if(!lsToValidate.trim().equalsIgnoreCase("")){
            Matcher loMat = null;
            Pattern loPat = null;
            String  lsExpReg = tsRegularExpression;
            loPat = Pattern.compile(lsExpReg);
            loMat = loPat.matcher(lsToValidate);
            if (!loMat.find()){
                lbReturn = false; 
            }else{
                lbReturn = true;
            }
        }        
        return lbReturn;
    }
    
}
