package com.televisa.comer.integration.model.interfaces;

import com.televisa.comer.integration.model.beans.ResponseBean;
import com.televisa.comer.integration.model.beans.pgm.EvetvLogComercialTab;
import com.televisa.comer.integration.model.beans.pgm.OrdLinesModulosTab;

public interface LogComercialInterface {
    // 1) NA
    // 2) Insertar en Tabla de LogComercial
    public Integer insertLogComercial(EvetvLogComercialTab toEvetvLogComercialTab);
    public String getQueryInsertLogComercial(EvetvLogComercialTab toEvetvLogComercialTab);
    // 3) Validar si el Spot Ya existe 
    public Integer validateSpotLogComercial(String tsStnid, String tsBcstdt);
    public String getQueryValidateSpotLogComercial(String tsStnid, String tsBcstdt);
    // 4) Validar si el Break Ya existe 
    public Integer validateBreakLogComercial(String tsStnid, String tsBcstdt);
    public String getQueryValidateBreakLogComercial(String tsStnid, String tsBcstdt);
    // 5) Validar que todos los spots contengan el mismo AutoId
    public Integer validateAllSpotByAutoId(String tsStnid, String tsBcstdt);
    public String getQueryValidateAllSpotByAutoId(String tsStnid, String tsBcstdt);
    // 6) NA (responder a evetv)
    // 7) 3 updates (sugerencia, ponerlo en un SP ya que se necesita una transaccion)
    // 8) Validar que no existan spots encimados
      
    //
    public ResponseBean validateExistLogComercial(EvetvLogComercialTab toEvetvLogComercialTab);
    public EvetvLogComercialTab getOrdLogComercialByKeyId(EvetvLogComercialTab toEvetvLogComercialTab);
    public String getQueryOrdLogComercialByKeyId(EvetvLogComercialTab toEvetvLogComercialTab);
    
    // Update en Tabla de LogComercial
    public Integer updateLogComercial(EvetvLogComercialTab toEvetvLogComercialTab);
    public String getQueryUpdateLogComercial(EvetvLogComercialTab toEvetvLogComercialTab);
    
    
}
