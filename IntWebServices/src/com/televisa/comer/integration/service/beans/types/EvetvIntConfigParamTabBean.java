/**
* Project: Integraton Paradigm - EveTV
*
* File: EvetvIntConfigParamTabBean.java
*
* Created on: Septiembre 23, 2017 at 11:00
*
* Copyright (c) - OMW - 2017
*/
package com.televisa.comer.integration.service.beans.types;

/** Clase Bean para almacenar mapeo con Configuracion de Parametros
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Septiembre 23, 2017, 12:00 pm
 */
public class EvetvIntConfigParamTabBean {
    private String lsIdParameter;
    private String lsNomParameter;
    private String lsIndDescParameter;
    private String lsIndUsedBy;
    private String lsIndValueParameter;
    private String lsIndEstatus;

    public void setLsIdParameter(String lsIdParameter) {
        this.lsIdParameter = lsIdParameter;
    }

    public String getLsIdParameter() {
        return lsIdParameter;
    }

    public void setLsNomParameter(String lsNomParameter) {
        this.lsNomParameter = lsNomParameter;
    }

    public String getLsNomParameter() {
        return lsNomParameter;
    }

    public void setLsIndDescParameter(String lsIndDescParameter) {
        this.lsIndDescParameter = lsIndDescParameter;
    }

    public String getLsIndDescParameter() {
        return lsIndDescParameter;
    }

    public void setLsIndUsedBy(String lsIndUsedBy) {
        this.lsIndUsedBy = lsIndUsedBy;
    }

    public String getLsIndUsedBy() {
        return lsIndUsedBy;
    }

    public void setLsIndValueParameter(String lsIndValueParameter) {
        this.lsIndValueParameter = lsIndValueParameter;
    }

    public String getLsIndValueParameter() {
        return lsIndValueParameter;
    }

    public void setLsIndEstatus(String lsIndEstatus) {
        this.lsIndEstatus = lsIndEstatus;
    }

    public String getLsIndEstatus() {
        return lsIndEstatus;
    }
    
}
