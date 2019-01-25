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

/** Clase bean que mapea Respuesta de Actualizacion den base de datos
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Septiembre 23, 2017, 12:00 pm
 */
public class ResponseUpdDao {
    private String  lsResponse;
    private Integer liAffected;
    private String  lsMessage;

    public void setLsResponse(String lsResponse) {
        this.lsResponse = lsResponse;
    }

    public String getLsResponse() {
        return lsResponse;
    }

    public void setLiAffected(Integer liAffected) {
        this.liAffected = liAffected;
    }

    public Integer getLiAffected() {
        return liAffected;
    }

    public void setLsMessage(String lsMessage) {
        this.lsMessage = lsMessage;
    }

    public String getLsMessage() {
        return lsMessage;
    }
}
