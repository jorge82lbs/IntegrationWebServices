/**
* Project: Integraton Paradigm - EveTV
*
* File: EvetvIntServiceBitacoraTab.java
*
* Created on: Septiembre 23, 2017 at 11:00
*
* Copyright (c) - OMW - 2017
*/

package com.televisa.comer.integration.model.beans;

/** Clase bean que Respuesta de proceso general
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Septiembre 23, 2017, 12:00 pm
 */
public class ResponseBean {
    private String lsResponse;
    private String lsType;
    private String lsMessageResponse;
    
    public void setLsType(String lsType) {
        this.lsType = lsType;
    }

    public String getLsType() {
        return lsType;
    }
    
    public void setLsResponse(String lsResponse) {
        this.lsResponse = lsResponse;
    }

    public String getLsResponse() {
        return lsResponse;
    }

    public void setLsMessageResponse(String lsMessageResponse) {
        this.lsMessageResponse = lsMessageResponse;
    }

    public String getLsMessageResponse() {
        return lsMessageResponse;
    }

}
