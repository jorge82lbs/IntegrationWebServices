package com.televisa.comer.integration.model.interfaces;

import com.televisa.comer.integration.model.beans.ResponseBean;
import com.televisa.comer.integration.model.beans.ResponseUpdDao;
import com.televisa.comer.integration.model.beans.pgm.OrdHeaderModulosTab;

import com.televisa.comer.integration.model.beans.pgm.OrdLinesModulosTab;

import java.sql.SQLException;

import java.util.List;

public interface VentaModulosInterface {
    //---------- Encabezado -------------//
    public Integer insertOrdHeaderModulosTab(OrdHeaderModulosTab toOrdHeaderModulosTab);
    public String getQueryInsertOrdHeaderModulos(OrdHeaderModulosTab toOrdHeaderModulosTab);
    public Integer updateOrdHeaderModulosTab(OrdHeaderModulosTab toOrdHeaderModulosTab);
    public String getQueryUpdateOrdHeaderModulos(OrdHeaderModulosTab toOrdHeaderModulosTab);
    public List<OrdHeaderModulosTab> getAllOrdHeaderModulosTab();
    public String getQueryAllOrdHeaderModulosTab();
    public ResponseBean validateExistHeader(OrdHeaderModulosTab toOrdHeaderModulosTab);
    public OrdHeaderModulosTab getHeaderByOrdIdEvetv(OrdHeaderModulosTab toOrdHeaderModulosTab);
    public String getQueryHeaderByOrdIdEvetv(OrdHeaderModulosTab toOrdHeaderModulosTab);
    
    //---------- Lineas ----------------//
    public Integer insertOrdLinesModulosTab(OrdLinesModulosTab toOrdLinesModulosTab);
    public String getQueryInsertOrdLinesModulos(OrdLinesModulosTab toOrdLinesModulosTab);
    public Integer updateOrdLinesModulosTab(OrdLinesModulosTab toOrdOrdLinesModulosTab);
    public String getQueryUpdateOrdLinesModulos(OrdLinesModulosTab toOrdLinesModulosTab);
    public List<OrdLinesModulosTab> getOrdLinesModulosTabByOrdIdEvetv(OrdLinesModulosTab loOrdLinesModulosTab);
    public String getQueryOrdLinesModulosTabByOrdIdEvetv(OrdLinesModulosTab loOrdLinesModulosTab);
    public ResponseBean validateExistLines(OrdLinesModulosTab toOrdLinesModulosTab);
    public OrdLinesModulosTab getOrdLineByKeyId(OrdLinesModulosTab toOrdLinesModulosTab);
    public String getQueryOrdLineByKeyId(OrdLinesModulosTab toOrdLinesModulosTab);
    
    //-------- Procedimientos ----------//
    public ResponseUpdDao callProcedureHeader(OrdHeaderModulosTab toOrdHeaderModulosTab) throws SQLException;
    public ResponseUpdDao callProcedureLines(Integer tiOrderId) throws SQLException;
    public ResponseUpdDao callProcedureCopys(Integer tiOrderId) throws SQLException;
    
}
