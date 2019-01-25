/**
* Project: Integraton Paradigm - EveTV
*
* File: EvetvLogComercialProcesadoBean.java
*
* Created on: Septiembre 23, 2017 at 11:00
*
* Copyright (c) - OMW - 2017
*/
package com.televisa.comer.integration.model.beans.pgm;

/** Clase bean que mapea la tabla de Log Comercial Procesado
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Septiembre 23, 2017, 12:00 pm
 */
public class EvetvLogComercialProcesadoBean {
    private String lsStnid;
    private String lsBcstdt;
    private String lsEstatus;
    private String lsMensaje;

    public void setLsStnid(String lsStnid) {
        this.lsStnid = lsStnid;
    }

    public String getLsStnid() {
        return lsStnid;
    }

    public void setLsBcstdt(String lsBcstdt) {
        this.lsBcstdt = lsBcstdt;
    }

    public String getLsBcstdt() {
        return lsBcstdt;
    }

    public void setLsEstatus(String lsEstatus) {
        this.lsEstatus = lsEstatus;
    }

    public String getLsEstatus() {
        return lsEstatus;
    }

    public void setLsMensaje(String lsMensaje) {
        this.lsMensaje = lsMensaje;
    }

    public String getLsMensaje() {
        return lsMensaje;
    }
}
