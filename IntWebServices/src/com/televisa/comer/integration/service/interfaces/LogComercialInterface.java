package com.televisa.comer.integration.service.interfaces;

import com.televisa.comer.integration.service.beans.pgm.commerciallog.CommercialList;
import com.televisa.comer.integration.service.beans.pgm.commerciallog.RecibirDatosExternosResponse;

public interface LogComercialInterface {
   public RecibirDatosExternosResponse enviarLogComercial(CommercialList commercialList);
}
