/**
* Project: Integraton Paradigm - EveTV
*
* File: OrderModulosBean.java
*
* Created on: Septiembre 23, 2017 at 11:00
*
* Copyright (c) - OMW - 2017
*/

package com.televisa.comer.integration.model.beans.pgm;

import java.util.List;

/** Clase bean que mapea una orden, con encabezado y lineas
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Septiembre 23, 2017, 12:00 pm
 */
public class OrderModulosBean {
    private OrdHeaderModulosTab loOrdHeaderModulosTab;
    private List<OrdLinesModulosTab> laOrdLinesModulosTab;

    public void setLoOrdHeaderModulosTab(OrdHeaderModulosTab loOrdHeaderModulosTab) {
        this.loOrdHeaderModulosTab = loOrdHeaderModulosTab;
    }

    public OrdHeaderModulosTab getLoOrdHeaderModulosTab() {
        return loOrdHeaderModulosTab;
    }

    public void setLaOrdLinesModulosTab(List<OrdLinesModulosTab> laOrdLinesModulosTab) {
        this.laOrdLinesModulosTab = laOrdLinesModulosTab;
    }

    public List<OrdLinesModulosTab> getLaOrdLinesModulosTab() {
        return laOrdLinesModulosTab;
    }

}
