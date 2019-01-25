/**
* Project: Integraton Paradigm - EveTV
*
* File: EmailDestinationAddress.java
*
* Created on: Septiembre 23, 2017 at 11:00
*
* Copyright (c) - OMW - 2017
*/
package com.televisa.comer.integration.service.beans.types;

/** Clase Bean para almacenar destinatarios en el envio de correos
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Septiembre 23, 2017, 12:00 pm
 */
public class EmailDestinationAddress {
    private String lsNameTo;
    private String lsAddressTo;

    public void setLsNameTo(String lsNameTo) {
        this.lsNameTo = lsNameTo;
    }

    public String getLsNameTo() {
        return lsNameTo;
    }

    public void setLsAddressTo(String lsAddressTo) {
        this.lsAddressTo = lsAddressTo;
    }

    public String getLsAddressTo() {
        return lsAddressTo;
    }
}
