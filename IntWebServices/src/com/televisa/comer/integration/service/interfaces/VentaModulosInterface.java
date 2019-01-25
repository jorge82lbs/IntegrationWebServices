package com.televisa.comer.integration.service.interfaces;

import com.televisa.comer.integration.service.beans.pgm.ventamodulos.CommercialList;
import com.televisa.comer.integration.service.beans.pgm.ventamodulos.RecibirDatosExternosResponse;

public interface VentaModulosInterface {
    public RecibirDatosExternosResponse actualizarCargaeVeTV(CommercialList commercialList);
}
