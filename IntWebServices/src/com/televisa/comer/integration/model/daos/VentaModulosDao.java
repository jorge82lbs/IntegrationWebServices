/**
* Project: Integraton Paradigm - EveTV
*
* File: VentaModulosDao.java
*
* Created on: Septiembre 23, 2017 at 11:00
*
* Copyright (c) - OMW - 2017
*/
package com.televisa.comer.integration.model.daos;

import com.televisa.comer.integration.model.beans.ResponseBean;
import com.televisa.comer.integration.model.beans.ResponseUpdDao;
import com.televisa.comer.integration.model.beans.pgm.OrdHeaderModulosTab;
import com.televisa.comer.integration.model.beans.pgm.OrdLinesModulosTab;
import com.televisa.comer.integration.model.connection.ConnectionAs400;
import com.televisa.comer.integration.model.interfaces.VentaModulosInterface;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.List;

/** Esta clase es una instancia para conexion a Base de Datos para Venta de Modulos<br/><br/>
 *
 * @author Jorge Luis Bautista - Omw
 *
 * @version 01.00.01
 *
 * @date Septiembre 14, 2017, 12:00 pm
 */
public class VentaModulosDao implements VentaModulosInterface {
   
    /**
     * Inserta Encabezado en tabla de control
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdHeaderModulosTab
     * @return Integer
     */
    @Override
    public Integer insertOrdHeaderModulosTab(OrdHeaderModulosTab toOrdHeaderModulosTab) {
        Integer    liResponse = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        String     lsQueryParadigm = getQueryInsertOrdHeaderModulos(toOrdHeaderModulosTab);
        try {
            Statement loStmt = loCnn.createStatement();
            liResponse = loStmt.executeUpdate(lsQueryParadigm);
        } catch (SQLException loExSql) {
            System.out.println("ERROR AL EJECUTAR INSERT HEADER: ");
            System.out.println(lsQueryParadigm);
            loExSql.printStackTrace();
        }
        finally{
            try {
                loCnn.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return liResponse;
    }

    /**
     * Obtiene instrucciona para Insertar Encabezado en tabla de control
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdHeaderModulosTab
     * @return String
     */
    @Override
    public String getQueryInsertOrdHeaderModulos(OrdHeaderModulosTab toOrdHeaderModulosTab) {
        String lsQuery = 
            "INSERT INTO EVENTAS.EVETV_ORDHDR (ORDID_EVETV, \n" + 
            "                          AGYID, \n" + 
            "                          ADVID, \n" + 
            "                          STRDT, \n" + 
            "                          EDT, \n" + 
            "                          MCONTID, \n" + 
            "                          RTCRDDSCR, \n" + 
            "                          IDTARGET, \n" + 
            "                          STNID, \n" + 
            //"                          ORDID, \n" + 
            "                          STATUS, \n" + 
            "                          ACTION\n" + 
            "                         ) \n" + 
            "                  VALUES (" + toOrdHeaderModulosTab.getLsOrdidEvetv() + ", \n" + 
            "                          '" + toOrdHeaderModulosTab.getLsAgyid() + "', \n" + 
            "                          '" + toOrdHeaderModulosTab.getLsAdvid() + "', \n" + 
            "                          '" + toOrdHeaderModulosTab.getLsStrdt() + "', \n" + 
            "                          '" + toOrdHeaderModulosTab.getLsEdt() + "', \n" + 
            "                          '" + toOrdHeaderModulosTab.getLsMcontid() + "', \n" + 
            "                          '" + toOrdHeaderModulosTab.getLsRtcrddscr() + "', \n" + 
            "                          " + toOrdHeaderModulosTab.getLsIdtarget() + ", \n" + 
            "                          '" + toOrdHeaderModulosTab.getLsStnid() + "', \n" + 
            //"                          "+toOrdHeaderModulosTab.getLsOrdid() + ", \n" + 
            "                          " + toOrdHeaderModulosTab.getLsStatus() + ", \n" + 
            "                          '" + toOrdHeaderModulosTab.getLsAction() + "'\n" + 
            "                          )";
        return lsQuery;
    }

    /**
     * Actualiza Encabezado en tabla de control
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdHeaderModulosTab
     * @return Integer
     */
    @Override
    public Integer updateOrdHeaderModulosTab(OrdHeaderModulosTab toOrdHeaderModulosTab) {
        Integer    liReturn = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        String     lsQueryParadigm = getQueryUpdateOrdHeaderModulos(toOrdHeaderModulosTab);
        try {
            Statement loStmt = loCnn.createStatement();
            liReturn = loStmt.executeUpdate(lsQueryParadigm);
        } catch (SQLException loExSql) {
            System.out.println("Error en Update ################"+loExSql.getMessage());
            loExSql.printStackTrace();
        }
        finally{
            try {
                loCnn.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return liReturn;
    }

    /**
     * Obtiene instrucciona para Actualizar Encabezado en tabla de control
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdHeaderModulosTab
     * @return Integer
     */
    @Override
    public String getQueryUpdateOrdHeaderModulos(OrdHeaderModulosTab toOrdHeaderModulosTab) {
        String lsQuery = 
        "   UPDATE EVENTAS.EVETV_ORDHDR\n" + 
        "      SET AGYID = '" + toOrdHeaderModulosTab.getLsAgyid() + "',\n" + 
        "          ADVID = '" + toOrdHeaderModulosTab.getLsAdvid() + "',\n" + 
        "          STRDT = '" + toOrdHeaderModulosTab.getLsStrdt() + "',\n" + 
        "          EDT = '" + toOrdHeaderModulosTab.getLsEdt() + "',\n" + 
        "          MCONTID = '" + toOrdHeaderModulosTab.getLsMcontid() + "',\n" + 
        "          RTCRDDSCR = '" + toOrdHeaderModulosTab.getLsRtcrddscr() + "',\n" + 
        "          IDTARGET = " + toOrdHeaderModulosTab.getLsIdtarget() + ",\n" + 
        "          STNID = '" + toOrdHeaderModulosTab.getLsStnid() + "', \n" + 
        //"          ORDID = '" + toOrdHeaderModulosTab.getLsOrdid() + "',\n" + 
        //"          STATUS = '" + toOrdHeaderModulosTab.getLsStatus() + "',\n" + 
        "          ACTION = '" + toOrdHeaderModulosTab.getLsAction() + "'\n" + 
        "    WHERE ORDID_EVETV = " + toOrdHeaderModulosTab.getLsOrdidEvetv() + "";
        return lsQuery;
    }

    /**
     * Obtiene todos lo headers de la tabla de control
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdHeaderModulosTab
     * @return Integer
     */
    @Override
    public List<OrdHeaderModulosTab> getAllOrdHeaderModulosTab() {
        List<OrdHeaderModulosTab> loAllHeaders = 
            new ArrayList<OrdHeaderModulosTab>();
        Connection                loCnn = new ConnectionAs400().getConnection();
        ResultSet                 loRs = null;
        //Validar bandera tabla EVENTAS.EVETV_PROGRAMAS tiene 0 registros 
        String                    lsQueryParadigm = getQueryAllOrdHeaderModulosTab();
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                OrdHeaderModulosTab loHeaderBean = new OrdHeaderModulosTab();             
                loHeaderBean.setLsOrdidEvetv(loRs.getString("ORDID_EVETV") == null ? null : 
                                          loRs.getString("ORDID_EVETV").trim());
                loHeaderBean.setLsAgyid(loRs.getString("AGYID") == null ? null :
                                         loRs.getString("AGYID").trim());
                loHeaderBean.setLsAdvid(loRs.getString("ADVID") == null ? null :
                                         loRs.getString("ADVID").trim());
                loHeaderBean.setLsStrdt(loRs.getString("STRDT") == null ? null :
                                         loRs.getString("STRDT").trim());
                loHeaderBean.setLsEdt(loRs.getString("EDT") == null ? null :
                                         loRs.getString("EDT").trim());
                loHeaderBean.setLsMcontid(loRs.getString("MCONTID") == null ? null :
                                         loRs.getString("MCONTID").trim());
                loHeaderBean.setLsRtcrddscr(loRs.getString("RTCRDDSCR") == null ? null :
                                         loRs.getString("RTCRDDSCR").trim());
                loHeaderBean.setLsIdtarget(loRs.getString("IDTARGET") == null ? null :
                                         loRs.getString("IDTARGET").trim());
                loHeaderBean.setLsStnid(loRs.getString("STNID") == null ? null :
                                         loRs.getString("STNID").trim());
                loHeaderBean.setLsOrdid(loRs.getString("ORDID") == null ? null :
                                         loRs.getString("ORDID").trim());
                loHeaderBean.setLsStatus(loRs.getString("STATUS") == null ? null :
                                         loRs.getString("STATUS").trim());
                loHeaderBean.setLsAction(loRs.getString("ACTION") == null ? null :
                                         loRs.getString("ACTION").trim());
               
                loAllHeaders.add(loHeaderBean);
            }
        } catch (SQLException loExSql) {
            System.out.println("ERROR AL EJECUTAR: ");
            System.out.println(lsQueryParadigm);
            loExSql.printStackTrace();
        }
        finally{
            try {
                loCnn.close();
                loRs.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return loAllHeaders;
    }
    
    /**
     * Devuelve Instruccion para obtener todos lo headers de la tabla de control
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdHeaderModulosTab
     * @return Integer
     */
    @Override
    public String getQueryAllOrdHeaderModulosTab() {
        String lsQuery = "   SELECT ORDID_EVETV, \n" + 
        "          AGYID, \n" + 
        "          ADVID, \n" + 
        "          STRDT, \n" + 
        "          EDT, \n" + 
        "          MCONTID, \n" + 
        "          RTCRDDSCR, \n" + 
        "          IDTARGET, \n" + 
        "          STNID, \n" + 
        "          ORDID, \n" + 
        "          STATUS, \n" + 
        "          ACTION\n" + 
        "     FROM EVENTAS.EVETV_ORDHDR";
        return lsQuery;
    }
    
    /**
     * Validar si existe un encabezado de una orden determinada
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdHeaderModulosTab
     * @return ResponseBean
     */
    @Override
    public ResponseBean validateExistHeader(OrdHeaderModulosTab toOrdHeaderModulosTab) {
        ResponseBean            loResponse = new ResponseBean();
        OrdHeaderModulosTab     loHeaderResBean = getHeaderByOrdIdEvetv(toOrdHeaderModulosTab);
        if(loHeaderResBean != null){
            loResponse.setLsResponse("ERROR");
            loResponse.setLsMessageResponse("Si Existe Al Buscar Por OrderIdEveTv");
        }else{
            loResponse.setLsResponse("OK");
            loResponse.setLsMessageResponse("No Existe En Base de Datos");
        }
        return loResponse;
    }

    /**
     * Obtiene encabezado de la orden en base a identificador
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdHeaderModulosTab
     * @return OrdHeaderModulosTab
     */
    @Override
    public OrdHeaderModulosTab getHeaderByOrdIdEvetv(OrdHeaderModulosTab toOrdHeaderModulosTab) {
        OrdHeaderModulosTab     loHeaderBean = new OrdHeaderModulosTab();             
        Connection              loCnn = new ConnectionAs400().getConnection();
        ResultSet               loRs = null;
        String                  lsQueryParadigm = getQueryHeaderByOrdIdEvetv(toOrdHeaderModulosTab);
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                loHeaderBean.setLsOrdidEvetv(loRs.getString("ORDID_EVETV") == null ? null : 
                                          loRs.getString("ORDID_EVETV").trim());
                loHeaderBean.setLsAgyid(loRs.getString("AGYID") == null ? null :
                                         loRs.getString("AGYID").trim());
                loHeaderBean.setLsAdvid(loRs.getString("ADVID") == null ? null :
                                         loRs.getString("ADVID").trim());
                loHeaderBean.setLsStrdt(loRs.getString("STRDT") == null ? null :
                                         loRs.getString("STRDT").trim());
                loHeaderBean.setLsEdt(loRs.getString("EDT") == null ? null :
                                         loRs.getString("EDT").trim());
                loHeaderBean.setLsMcontid(loRs.getString("MCONTID") == null ? null :
                                         loRs.getString("MCONTID").trim());
                loHeaderBean.setLsRtcrddscr(loRs.getString("RTCRDDSCR") == null ? null :
                                         loRs.getString("RTCRDDSCR").trim());
                loHeaderBean.setLsIdtarget(loRs.getString("IDTARGET") == null ? null :
                                         loRs.getString("IDTARGET").trim());
                loHeaderBean.setLsStnid(loRs.getString("STNID") == null ? null :
                                         loRs.getString("STNID").trim());
                loHeaderBean.setLsOrdid(loRs.getString("ORDID") == null ? null :
                                         loRs.getString("ORDID").trim());
                loHeaderBean.setLsStatus(loRs.getString("STATUS") == null ? null :
                                         loRs.getString("STATUS").trim());
                loHeaderBean.setLsAction(loRs.getString("ACTION") == null ? null :
                                         loRs.getString("ACTION").trim());
            }
        } catch (SQLException loExSql) {
            System.out.println("ERROR AL EJECUTAR: ");
            System.out.println(lsQueryParadigm);
            loExSql.printStackTrace();
            loHeaderBean  = null;
        }
        finally{
            try {
                loCnn.close();
                loRs.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return loHeaderBean;
    }
    
    /**
     * Gener instruccion para obtener encabezado de la orden en base a identificador
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdHeaderModulosTab
     * @return String
     */
    @Override
    public String getQueryHeaderByOrdIdEvetv(OrdHeaderModulosTab toOrdHeaderModulosTab) {
        String lsQuery = "   SELECT ORDID_EVETV, \n" + 
        "          AGYID, \n" + 
        "          ADVID, \n" + 
        "          STRDT, \n" + 
        "          EDT, \n" + 
        "          MCONTID, \n" + 
        "          RTCRDDSCR, \n" + 
        "          IDTARGET, \n" + 
        "          STNID, \n" + 
        "          ORDID, \n" + 
        "          STATUS, \n" + 
        "          ACTION\n" + 
        "     FROM EVENTAS.EVETV_ORDHDR\n"+
        "    WHERE ORDID_EVETV = " + toOrdHeaderModulosTab.getLsOrdidEvetv() + " ";
        return lsQuery;
    }

    /**
     * Obtiene Linea de la orden en base a identificador encabezado-linea
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdLinesModulosTab
     * @return Integer
     */
    @Override
    public Integer insertOrdLinesModulosTab(OrdLinesModulosTab toOrdLinesModulosTab) {
        Integer    liResponse = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        String     lsQueryParadigm = getQueryInsertOrdLinesModulos(toOrdLinesModulosTab);
        try {
            Statement loStmt = loCnn.createStatement();
            liResponse = loStmt.executeUpdate(lsQueryParadigm);
        } catch (SQLException loExSql) {
            System.out.println("ERROR AL EJECUTAR: ");
            System.out.println(lsQueryParadigm);
            loExSql.printStackTrace();
        }
        finally{
            try {
                loCnn.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return liResponse;
    }

    /**
     * Genera instruccion para obtener Linea de la orden en base a identificador encabezado-linea
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdLinesModulosTab
     * @return String
     */
    @Override
    public String getQueryInsertOrdLinesModulos(OrdLinesModulosTab toOrdLinesModulosTab) {
        String lsQuery = 
            "INSERT INTO EVENTAS.EVETV_ORDLN (ORDID_EVETV, \n" + 
            "                         SPTMSTID_EVETV, \n" + 
            //"                         ORDID, \n" + 
            //"                         ORDLNNUM, \n" + 
            "                         BCSTDT, \n" + 
            "                         BUYUNTID, \n" + 
            "                         BRKDTID, \n" + 
            "                         HOUR, \n" + 
            "                         DURATION, \n" + 
            "                         STNID, \n" + 
            "                         USRCHR, \n" + 
            "                         AUTOID, \n" + 
            "                         BRND, \n" + 
            "                         POSESP, \n" + 
            "                         SPTRT, \n" + 
            "                         REVSTS, \n" + 
            "                         PRIORITY, \n" + 
            "                         DIGITAL, \n" + 
            "                         PAGA, \n" + 
            //"                         SPTMSTID, \n" + 
            "                         STATUS, \n" + 
            //"                         ORDLNID, \n" + 
            "                         ACTION,\n" + 
/*CH*/        "                         STRTIM,\n" + 
/*CH*/        "                         ETIM\n" + 
            "                         ) \n" + 
            "                 VALUES (" + toOrdLinesModulosTab.getLsOrdidEvetv() + ", \n" + 
            "                         '" + toOrdLinesModulosTab.getLsSptmstidEvetv() + "', \n" + 
            //"                         "+toOrdLinesModulosTab.getLsOrdid()+", \n" + 
            //"                         "+toOrdLinesModulosTab.getLsOrdlnnum()+", \n" + 
            "                         '" + toOrdLinesModulosTab.getLsBcstdt() + "', \n" + 
            "                         '" + toOrdLinesModulosTab.getLsBuyuntid() + "', \n" + 
            "                         " + toOrdLinesModulosTab.getLsBrkdtid() + ", \n" + 
            "                         '" + toOrdLinesModulosTab.getLsHour() + "', \n" + 
            "                         '" + toOrdLinesModulosTab.getLsDuration() + "', \n" + 
            "                         '" + toOrdLinesModulosTab.getLsStnid() + "',\n" + 
            "                         '" + toOrdLinesModulosTab.getLsUsrchr() + "', \n" + 
            "                         '" + toOrdLinesModulosTab.getLsAutoid() + "', \n" + 
            "                         '" + toOrdLinesModulosTab.getLsBrnd() + "',\n" + 
            "                         '" + toOrdLinesModulosTab.getLsPosesp() + "', \n" + 
            "                         " + toOrdLinesModulosTab.getLsSptrt() + ", \n" + 
            "                         '" + toOrdLinesModulosTab.getLsRevsts() + "', \n" + 
            "                         '" + toOrdLinesModulosTab.getLsPriority() + "', \n" + 
            "                         '" + toOrdLinesModulosTab.getLsDigital() + "', \n" + 
            "                         '" + toOrdLinesModulosTab.getLsPaga() + "', \n" + 
            //"                         "+toOrdLinesModulosTab.getLsSptmstid()+", \n" + 
            "                         " + toOrdLinesModulosTab.getLsStatus() + ", \n" + 
            //"                         "+toOrdLinesModulosTab.getLsOrdid()+", \n" + 
            "                         '" + toOrdLinesModulosTab.getLsAction() + "',\n" + 
/*CH*/        "                         '" + toOrdLinesModulosTab.getLsRangeAllowedStartTime() + "',\n" + 
/*CH*/        "                         '" + toOrdLinesModulosTab.getLsRangeAllowedEndTime() + "'\n" +             
            "                         )";
        return lsQuery;
    }

    /**
     * Actualiza Linea de la orden en base a identificador encabezado-linea
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdLinesModulosTab
     * @return Integer
     */
    @Override
    public Integer updateOrdLinesModulosTab(OrdLinesModulosTab toOrdLinesModulosTab) {
        Integer    liReturn = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        String     lsQueryParadigm = getQueryUpdateOrdLinesModulos(toOrdLinesModulosTab);
        try {
            Statement loStmt = loCnn.createStatement();
            liReturn = loStmt.executeUpdate(lsQueryParadigm);
        } catch (SQLException loExSql) {
            System.out.println("ERROR AL EJECUTAR: ");
            System.out.println(lsQueryParadigm);
            loExSql.printStackTrace();
        }
        finally{
            try {
                loCnn.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return liReturn;
    }

    /**
     * Genera Instruccion para actualizar Linea de la orden en base a identificador encabezado-linea
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdLinesModulosTab
     * @return Integer
     */
    @Override
    public String getQueryUpdateOrdLinesModulos(OrdLinesModulosTab toOrdLinesModulosTab) {
        String lsQuery = "";
        String lsAction = toOrdLinesModulosTab.getLsAction();
        if(lsAction.equalsIgnoreCase("D")){
            lsQuery = "  UPDATE EVENTAS.EVETV_ORDLN\n" + 
            "     SET "+
            "         ACTION = '" + toOrdLinesModulosTab.getLsAction() + "' \n" + 
            "   WHERE ORDID_EVETV    = " + toOrdLinesModulosTab.getLsOrdidEvetv() + "\n" + 
            "     AND SPTMSTID_EVETV = '" + toOrdLinesModulosTab.getLsSptmstidEvetv() + "'";
        }else{
            lsQuery = 
                "  UPDATE EVENTAS.EVETV_ORDLN\n" + 
                "     SET "+
                //"         ORDID = " + toOrdLinesModulosTab.getLsOrdid() + ", \n" + 
                //"         ORDLNNUM = " + toOrdLinesModulosTab.getLsOrdid() + ", \n" + 
                "         BCSTDT = '" + toOrdLinesModulosTab.getLsBcstdt() + "', \n" + 
                "         BUYUNTID = '" + toOrdLinesModulosTab.getLsBuyuntid() + "', \n" + 
                "         BRKDTID = " + toOrdLinesModulosTab.getLsBrkdtid() + ", \n" + 
                "         HOUR = '" + toOrdLinesModulosTab.getLsHour() + "', \n" + 
                "         DURATION = '" + toOrdLinesModulosTab.getLsDuration() + "', \n" + 
                "         STNID = '" + toOrdLinesModulosTab.getLsStnid() + "', \n" + 
                "         USRCHR = '" + toOrdLinesModulosTab.getLsUsrchr() + "', \n" + 
                "         AUTOID = '" + toOrdLinesModulosTab.getLsAutoid() + "', \n" + 
                "         BRND = '" + toOrdLinesModulosTab.getLsBrnd() + "', \n" + 
                "         POSESP = '" + toOrdLinesModulosTab.getLsPosesp() + "', \n" + 
                "         SPTRT = '" + toOrdLinesModulosTab.getLsSptrt() + "', \n" + 
                "         REVSTS = '" + toOrdLinesModulosTab.getLsRevsts() + "', \n" + 
                "         PRIORITY = '" + toOrdLinesModulosTab.getLsPriority() + "', \n" + 
                "         DIGITAL = '" + toOrdLinesModulosTab.getLsDigital() + "', \n" + 
                "         PAGA = '" + toOrdLinesModulosTab.getLsPaga() + "', \n" + 
                //"         SPTMSTID = " + toOrdLinesModulosTab.getLsSptmstid() + ", \n" + 
                "         STATUS  = '" + toOrdLinesModulosTab.getLsStatus() + "', \n" + 
                //"         ORDLNID = " + toOrdLinesModulosTab.getLsOrdlnid() + ", \n" + 
                "         ACTION = '" + toOrdLinesModulosTab.getLsAction() + "', \n" + 
/*CH*/            "         STRTIM = '" + toOrdLinesModulosTab.getLsRangeAllowedStartTime() + "', \n" +
/*CH*/            "         ETIM = '" + toOrdLinesModulosTab.getLsRangeAllowedEndTime() + "' \n" +                                 
                "   WHERE ORDID_EVETV    = " + toOrdLinesModulosTab.getLsOrdidEvetv() + "\n" + 
                "     AND SPTMSTID_EVETV = '" + toOrdLinesModulosTab.getLsSptmstidEvetv() + "'";
        }
        return lsQuery;
    }
    /**
     * Obtiene Lineas de la orden en base a identificador encabezado-linea
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdLinesModulosTab
     * @return List
     */
    @Override
    public List<OrdLinesModulosTab> getOrdLinesModulosTabByOrdIdEvetv(OrdLinesModulosTab toOrdLinesModulosTab) {
        List<OrdLinesModulosTab> loLinesByOrd = 
            new ArrayList<OrdLinesModulosTab>();
        Connection               loCnn = new ConnectionAs400().getConnection();
        ResultSet                loRs = null;
        String                   lsQueryParadigm = getQueryAllOrdHeaderModulosTab();
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                OrdLinesModulosTab loLinesBean = new OrdLinesModulosTab();             
                loLinesBean.setLsOrdidEvetv(loRs.getString("ORDID_EVETV") == null ? null : 
                                          loRs.getString("ORDID_EVETV").trim()); 
                loLinesBean.setLsSptmstidEvetv(loRs.getString("SPTMSTID_EVETV") == null ? null : 
                                          loRs.getString("SPTMSTID_EVETV").trim());
                loLinesBean.setLsOrdid(loRs.getString("ORDID") == null ? null :
                                         loRs.getString("ORDID").trim());
                loLinesBean.setLsOrdlnnum(loRs.getString("ORDLNNUM") == null ? null :
                                         loRs.getString("ORDLNNUM").trim());
                loLinesBean.setLsBcstdt(loRs.getString("BCSTDT") == null ? null :
                                         loRs.getString("BCSTDT").trim());
                loLinesBean.setLsBuyuntid(loRs.getString("BUYUNTID") == null ? null :
                                         loRs.getString("BUYUNTID").trim());
                loLinesBean.setLsBrkdtid(loRs.getString("BRKDTID") == null ? null :
                                         loRs.getString("BRKDTID").trim());
                loLinesBean.setLsHour(loRs.getString("HOUR") == null ? null :
                                         loRs.getString("HOUR").trim());
                loLinesBean.setLsDuration(loRs.getString("DURATION") == null ? null :
                                         loRs.getString("DURATION").trim());
                loLinesBean.setLsStnid(loRs.getString("STNID") == null ? null :
                                         loRs.getString("STNID").trim());
                loLinesBean.setLsUsrchr(loRs.getString("USRCHR") == null ? null :
                                         loRs.getString("USRCHR").trim());
                loLinesBean.setLsAutoid(loRs.getString("AUTOID") == null ? null :
                                         loRs.getString("AUTOID").trim());
                loLinesBean.setLsBrnd(loRs.getString("BRND") == null ? null :
                                         loRs.getString("BRND").trim());
                loLinesBean.setLsPosesp(loRs.getString("POSESP") == null ? null :
                                         loRs.getString("POSESP").trim());
                loLinesBean.setLsSptrt(loRs.getString("SPTRT") == null ? null :
                                         loRs.getString("SPTRT").trim());
                loLinesBean.setLsRevsts(loRs.getString("REVSTS") == null ? null :
                                         loRs.getString("REVSTS").trim());
                loLinesBean.setLsPriority(loRs.getString("PRIORITY") == null ? null :
                                         loRs.getString("PRIORITY").trim());
                loLinesBean.setLsDigital(loRs.getString("DIGITAL") == null ? null :
                                         loRs.getString("DIGITAL").trim());
                loLinesBean.setLsPaga(loRs.getString("PAGA") == null ? null :
                                         loRs.getString("PAGA").trim());
                loLinesBean.setLsSptmstid(loRs.getString("SPTMSTID") == null ? null :
                                         loRs.getString("SPTMSTID").trim());
                loLinesBean.setLsStatus(loRs.getString("STATUS") == null ? null :
                                         loRs.getString("STATUS").trim());
                loLinesBean.setLsOrdlnid(loRs.getString("ORDLNID") == null ? null :
                                         loRs.getString("ORDLNID").trim());
                loLinesBean.setLsAction(loRs.getString("ACTION") == null ? null :
                                         loRs.getString("ACTION").trim());
                loLinesByOrd.add(loLinesBean);
            }
        } catch (SQLException loExSql) {
            System.out.println("ERROR AL EJECUTAR: ");
            System.out.println(lsQueryParadigm);
            loExSql.printStackTrace();
        }
        finally{
            try {
                loCnn.close();
                loRs.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return loLinesByOrd;
    }

    /**
     * Instruccion para obtener Lineas de la orden en base a identificador encabezado-linea
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdLinesModulosTab
     * @return String
     */
    @Override
    public String getQueryOrdLinesModulosTabByOrdIdEvetv(OrdLinesModulosTab toOrdLinesModulosTab) {
        String lsQuery = "  SELECT ORDID_EVETV, \n" + 
        "         SPTMSTID_EVETV, \n" + 
        "         ORDID, \n" + 
        "         ORDLNNUM, \n" + 
        "         BCSTDT, \n" + 
        "         BUYUNTID, \n" + 
        "         BRKDTID, \n" + 
        "         HOUR, \n" + 
        "         DURATION, \n" + 
        "         STNID, \n" + 
        "         USRCHR, \n" + 
        "         AUTOID, \n" + 
        "         BRND, \n" + 
        "         POSESP, \n" + 
        "         SPTRT, \n" + 
        "         REVSTS, \n" + 
        "         PRIORITY, \n" + 
        "         DIGITAL, \n" + 
        "         PAGA, \n" + 
        "         SPTMSTID, \n" + 
        "         STATUS, \n" + 
        "         ORDLNID, \n" + 
        "         ACTION                        \n" + 
        "    FROM EVENTAS.EVETV_ORDLN\n" + 
        "   WHERE ORDID_EVETV = "+toOrdLinesModulosTab.getLsOrdidEvetv()+"";
        return lsQuery;
    }

    /**
     * Valida existencia de la linea
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdLinesModulosTab
     * @return ResponseBean
     */
    @Override
    public ResponseBean validateExistLines(OrdLinesModulosTab toOrdLinesModulosTab) {
        ResponseBean            loResponse = new ResponseBean();
        Integer                 loLinesResBean = getOrdLineCountByKeyId(toOrdLinesModulosTab);
        if(loLinesResBean > 0 ){
            loResponse.setLsResponse("ERROR");
            loResponse.setLsMessageResponse("Si Existe Al Buscar Por OrderIdEveTv y SPTMSTID_EVETV");
        }else{
            loResponse.setLsResponse("OK");
            loResponse.setLsMessageResponse("No Existe En Base de Datos");
        }
        return loResponse;
    }

    /**
     * Obtiene linea en base a la llave compuesta
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdLinesModulosTab
     * @return ResponseBean
     */
    @Override
    public OrdLinesModulosTab getOrdLineByKeyId(OrdLinesModulosTab toOrdLinesModulosTab) {
        
        
        OrdLinesModulosTab      loLinesBean = new OrdLinesModulosTab();
        String                  lsQueryParadigm = getQueryOrdLineByKeyId(toOrdLinesModulosTab);
        Integer                 liExist = getOrdLineCountByKeyId(toOrdLinesModulosTab);
        if(liExist == 0){
            loLinesBean.setLsOrdidEvetv(toOrdLinesModulosTab.getLsOrdidEvetv()); 
            loLinesBean.setLsSptmstidEvetv(toOrdLinesModulosTab.getLsSptmstidEvetv());
            loLinesBean.setLsOrdid("0");
            loLinesBean.setLsOrdlnnum("0");
            loLinesBean.setLsBcstdt("");
            loLinesBean.setLsBuyuntid("");
            loLinesBean.setLsBrkdtid("");
            loLinesBean.setLsHour("");
            loLinesBean.setLsDuration("");
            loLinesBean.setLsStnid("");
            loLinesBean.setLsUsrchr("");
            loLinesBean.setLsAutoid("");
            loLinesBean.setLsBrnd("");
            loLinesBean.setLsPosesp("");
            loLinesBean.setLsSptrt("");
            loLinesBean.setLsRevsts("");
            loLinesBean.setLsPriority("");
            loLinesBean.setLsDigital("");
            loLinesBean.setLsPaga("");
            loLinesBean.setLsSptmstid("0");
            loLinesBean.setLsStatus("OK");
            loLinesBean.setLsOrdlnid("0");
            loLinesBean.setLsAction("D");
            loLinesBean.setLsRangeAllowedStartTime("");
            loLinesBean.setLsRangeAllowedEndTime("");
        }else{
            Connection loCnn = new ConnectionAs400().getConnection();
            ResultSet               loRs = null;
            try {
                Statement loStmt = loCnn.createStatement();
                loRs = loStmt.executeQuery(lsQueryParadigm);              
                while(loRs.next()){
                    loLinesBean.setLsOrdidEvetv(loRs.getString("ORDID_EVETV") == null ? null : 
                                              loRs.getString("ORDID_EVETV").trim()); 
                    loLinesBean.setLsSptmstidEvetv(loRs.getString("SPTMSTID_EVETV") == null ? null : 
                                              loRs.getString("SPTMSTID_EVETV").trim());
                    loLinesBean.setLsOrdid(loRs.getString("ORDID") == null ? null :
                                             loRs.getString("ORDID").trim());
                    loLinesBean.setLsOrdlnnum(loRs.getString("ORDLNNUM") == null ? null :
                                             loRs.getString("ORDLNNUM").trim());
                    loLinesBean.setLsBcstdt(loRs.getString("BCSTDT") == null ? null :
                                             loRs.getString("BCSTDT").trim());
                    loLinesBean.setLsBuyuntid(loRs.getString("BUYUNTID") == null ? null :
                                             loRs.getString("BUYUNTID").trim());
                    loLinesBean.setLsBrkdtid(loRs.getString("BRKDTID") == null ? null :
                                             loRs.getString("BRKDTID").trim());
                    loLinesBean.setLsHour(loRs.getString("HOUR") == null ? null :
                                             loRs.getString("HOUR").trim());
                    loLinesBean.setLsDuration(loRs.getString("DURATION") == null ? null :
                                             loRs.getString("DURATION").trim());
                    loLinesBean.setLsStnid(loRs.getString("STNID") == null ? null :
                                             loRs.getString("STNID").trim());
                    loLinesBean.setLsUsrchr(loRs.getString("USRCHR") == null ? null :
                                             loRs.getString("USRCHR").trim());
                    loLinesBean.setLsAutoid(loRs.getString("AUTOID") == null ? null :
                                             loRs.getString("AUTOID").trim());
                    loLinesBean.setLsBrnd(loRs.getString("BRND") == null ? null :
                                             loRs.getString("BRND").trim());
                    loLinesBean.setLsPosesp(loRs.getString("POSESP") == null ? null :
                                             loRs.getString("POSESP").trim());
                    loLinesBean.setLsSptrt(loRs.getString("SPTRT") == null ? null :
                                             loRs.getString("SPTRT").trim());
                    loLinesBean.setLsRevsts(loRs.getString("REVSTS") == null ? null :
                                             loRs.getString("REVSTS").trim());
                    loLinesBean.setLsPriority(loRs.getString("PRIORITY") == null ? null :
                                             loRs.getString("PRIORITY").trim());
                    loLinesBean.setLsDigital(loRs.getString("DIGITAL") == null ? null :
                                             loRs.getString("DIGITAL").trim());
                    loLinesBean.setLsPaga(loRs.getString("PAGA") == null ? null :
                                             loRs.getString("PAGA").trim());
                    loLinesBean.setLsSptmstid(loRs.getString("SPTMSTID") == null ? null :
                                             loRs.getString("SPTMSTID").trim());
                    loLinesBean.setLsStatus(loRs.getString("STATUS") == null ? null :
                                             loRs.getString("STATUS").trim());
                    loLinesBean.setLsOrdlnid(loRs.getString("ORDLNID") == null ? null :
                                             loRs.getString("ORDLNID").trim());
                    loLinesBean.setLsAction(loRs.getString("ACTION") == null ? null :
                                             loRs.getString("ACTION").trim());
                    loLinesBean.setLsRangeAllowedStartTime(loRs.getString("STRTIM") == null ? null :
                                             loRs.getString("STRTIM").trim());
                    loLinesBean.setLsRangeAllowedEndTime(loRs.getString("ETIM") == null ? null :
                                             loRs.getString("ETIM").trim());
                }           
            } catch (SQLException loExSql) {
                System.out.println("ERROR AL EJECUTAR: ");
                System.out.println(lsQueryParadigm);
                loExSql.printStackTrace();
            }
            finally{
                try {
                    loCnn.close();
                    loRs.close();
                } catch (SQLException loEx) {
                    loEx.printStackTrace();
                }
            }
        }
        return loLinesBean;
    }

    /**
     * Instruccion para validar existencia de la linea
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdLinesModulosTab
     * @return String
     */
    @Override
    public String getQueryOrdLineByKeyId(OrdLinesModulosTab toOrdLinesModulosTab) {
        String lsQuery = "  SELECT ORDID_EVETV, \n" + 
        "         SPTMSTID_EVETV, \n" + 
        "         ORDID, \n" + 
        "         ORDLNNUM, \n" + 
        "         BCSTDT, \n" + 
        "         BUYUNTID, \n" + 
        "         BRKDTID, \n" + 
        "         HOUR, \n" + 
        "         DURATION, \n" + 
        "         STNID, \n" + 
        "         USRCHR, \n" + 
        "         AUTOID, \n" + 
        "         BRND, \n" + 
        "         POSESP, \n" + 
        "         SPTRT, \n" + 
        "         REVSTS, \n" + 
        "         PRIORITY, \n" + 
        "         DIGITAL, \n" + 
        "         PAGA, \n" + 
        "         SPTMSTID, \n" + 
        "         STATUS, \n" + 
        "         ORDLNID, \n" + 
        "         ACTION,  \n" + 
/*CH*/        "         STRTIM,  \n" + 
/*CH*/        "         ETIM  \n" + 
        "    FROM EVENTAS.EVETV_ORDLN\n" + 
        "   WHERE ORDID_EVETV    = " + toOrdLinesModulosTab.getLsOrdidEvetv() + "\n"+
        "     AND SPTMSTID_EVETV = '" + toOrdLinesModulosTab.getLsSptmstidEvetv() + "' \n";
        return lsQuery;
    }
    
    /**
     * Obtiene linea en base a la llave compuesta
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdLinesModulosTab
     * @return ResponseBean
     */
    public Integer getOrdLineByKeyIdExist(OrdLinesModulosTab toOrdLinesModulosTab, String lsCondition) {
        Connection              loCnn = new ConnectionAs400().getConnection();
        ResultSet               loRs = null;
        Integer                 liResponse = 0;
        String                  lsQueryParadigm = getQueryOrdLineByKeyIdExist(toOrdLinesModulosTab, lsCondition);
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                liResponse = loRs.getInt(1); 
            }
            if(liResponse == 0 && toOrdLinesModulosTab.getLsAction().equalsIgnoreCase("D")){
                System.out.println("***** VALIDACION DELETE ************");
                liResponse = 1;
            }
        } catch (SQLException loExSql) {
            System.out.println("ERROR AL EJECUTAR: ");
            System.out.println(lsQueryParadigm);
            loExSql.printStackTrace();
        }
        finally{
            try {
                loCnn.close();
                loRs.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return liResponse;
    }
    
    /**
     * Instruccion para validar existencia de la linea
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdLinesModulosTab
     * @return String
     */
    public String getQueryOrdLineByKeyIdExist(OrdLinesModulosTab toOrdLinesModulosTab, String tsCondition) {
        String lsQuery = "  SELECT COUNT(1) \n" + 
        "    FROM EVENTAS.EVETV_ORDLN\n" + 
        "   WHERE ORDID_EVETV    = " + toOrdLinesModulosTab.getLsOrdidEvetv() + "\n"+
        "     AND SPTMSTID_EVETV = '" + toOrdLinesModulosTab.getLsSptmstidEvetv() + "' \n";
        if(tsCondition.length() > 0){
            lsQuery += " AND SPTMSTID_EVETV IN (" + tsCondition + ") \n";    
            
        }else{
            lsQuery += " AND 1 = 2 "; //Ninguna linea fue procesada
        }
        return lsQuery;
    }
    

    /**
     * Ejecuta procedimiento en base de datos para el encabezado de la orden
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdHeaderModulosTab
     * @return ResponseUpdDao
     */
    @Override
    public ResponseUpdDao callProcedureHeader(OrdHeaderModulosTab toOrdHeaderModulosTab) throws SQLException {
        ResponseUpdDao    loResponseUpdDao = new ResponseUpdDao();
        Connection        loCnn = new ConnectionAs400().getConnection();
        CallableStatement loCallStmt = null;
        /*
        System.out.println("Parametros(callProcedureHeader).........");
        System.out.println("toOrdHeaderModulosTab.getLsOrdid(): ["+toOrdHeaderModulosTab.getLsOrdidEvetv()+"]");
        System.out.println("toOrdHeaderModulosTab.getLsAdvid(): ["+toOrdHeaderModulosTab.getLsAdvid()+"]");
        System.out.println("toOrdHeaderModulosTab.getLsAgyid(): ["+toOrdHeaderModulosTab.getLsAgyid()+"]");
        System.out.println("toOrdHeaderModulosTab.getLsStnid(): ["+toOrdHeaderModulosTab.getLsStnid()+"]");
        System.out.println("(toOrdHeaderModulosTab.getLsStrdt(): ["+
                           toOrdHeaderModulosTab.getLsStrdt()+"]");
        System.out.println("(toOrdHeaderModulosTab.getLsEdt(): ["+
                           toOrdHeaderModulosTab.getLsEdt()+"]");
        System.out.println("convertStringToDate(toOrdHeaderModulosTab.getLsStrdt()): ["+
                           convertStringToDate(toOrdHeaderModulosTab.getLsStrdt())+"]");
        System.out.println("convertStringToDate(toOrdHeaderModulosTab.getLsEdt()): ["+
                           convertStringToDate(toOrdHeaderModulosTab.getLsEdt())+"]");
        
        System.out.println("toOrdHeaderModulosTab.getLsMcontid(): ["+toOrdHeaderModulosTab.getLsMcontid()+"]");
        System.out.println("toOrdHeaderModulosTab.getLsRtcrddscr(): ["+toOrdHeaderModulosTab.getLsRtcrddscr()+"]");
        System.out.println("toOrdHeaderModulosTab.getLsIdtarget(): ["+toOrdHeaderModulosTab.getLsIdtarget()+"]");
        */
        String            lsQueryParadigm = "call EVENTAS.EVETV_PROCESAENCABEZADO_MODULOS(?,?,?,?,?,?,?,?,?)";
        try {
            loCallStmt = loCnn.prepareCall(lsQueryParadigm);
            loCallStmt.setInt(1, Integer.parseInt(toOrdHeaderModulosTab.getLsOrdidEvetv()));
            loCallStmt.setString(2, toOrdHeaderModulosTab.getLsAdvid());
            loCallStmt.setString(3, toOrdHeaderModulosTab.getLsAgyid());
            loCallStmt.setString(4, toOrdHeaderModulosTab.getLsStnid());
            loCallStmt.setDate(5, convertStringToDate(toOrdHeaderModulosTab.getLsStrdt()));
            loCallStmt.setDate(6, convertStringToDate(toOrdHeaderModulosTab.getLsEdt()));
            loCallStmt.setString(7, toOrdHeaderModulosTab.getLsMcontid());
            loCallStmt.setString(8, toOrdHeaderModulosTab.getLsRtcrddscr());
            loCallStmt.setString(9, toOrdHeaderModulosTab.getLsIdtarget());
            loCallStmt.execute();
        } catch (SQLException loExSql) {
            System.out.println("ERROR AL EJECUTAR: ");
            System.out.println(lsQueryParadigm);
            throw loExSql;
        }
        finally{
            try {
                loCnn.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return loResponseUpdDao;
    }

    /**
     * Ejecuta procedimiento en base de datos para las lineas de la orden
     * @autor Jorge Luis Bautista Santiago
     * @param tiOrderId
     * @return ResponseUpdDao
     */
    @Override
    public ResponseUpdDao callProcedureLines(Integer tiOrderId) throws SQLException {
        ResponseUpdDao    loResponseUpdDao = new ResponseUpdDao();
        Connection        loCnn = new ConnectionAs400().getConnection();
        CallableStatement loCallStmt = null;
        String            lsQueryParadigm = "call EVENTAS.EVETV_PROCESALINEA_MODULOS(?)";
        try {
            loCallStmt = loCnn.prepareCall(lsQueryParadigm);
            loCallStmt.setInt(1, tiOrderId);            
            loCallStmt.execute();
        } catch (SQLException loExSql) {
            System.out.println("ERROR: "+loExSql.getMessage());
            System.out.println("ERROR AL EJECUTAR: ");
            System.out.println(lsQueryParadigm);
            throw loExSql;
        }
        finally{
            try {
                loCnn.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return loResponseUpdDao;
    }

    /**
     * Ejecuta procedimiento en base de datos para los copys de la orden
     * @autor Jorge Luis Bautista Santiago
     * @param tiOrderId
     * @return ResponseUpdDao
     */
    @Override
    public ResponseUpdDao callProcedureCopys(Integer tiOrderId) throws SQLException {
        ResponseUpdDao loResponseUpdDao = new ResponseUpdDao();
        Connection        loCnn = new ConnectionAs400().getConnection();
        CallableStatement loCallStmt = null;
        String            lsQueryParadigm = "call EVENTAS.EVETV_PROCESACOPYS_MODULOS(?)";
        try {
            loCallStmt = loCnn.prepareCall(lsQueryParadigm);
            loCallStmt.setInt(1, tiOrderId);            
            loCallStmt.execute();
        } catch (SQLException loExSql) {
            throw loExSql;
        }
        finally{
            try {
                loCnn.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return loResponseUpdDao;
    }
    
    /**
     * Convierte una cadena con formato a fecha 
     * @autor Jorge Luis Bautista Santiago
     * @param tsCadDate
     * @return Date
     */
    public Date convertStringToDate(String tsCadDate){
        Date             ltResponse = null;
        String[]         laFec = tsCadDate.split("/");
        String           lsCadDate = laFec[2] + "-" + laFec[1] + "-" + laFec[0];
        SimpleDateFormat loFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date   loParsed;
        try {
            loParsed = loFormat.parse(lsCadDate);
            ltResponse = new java.sql.Date(loParsed.getTime());
        } catch (ParseException loEx) {
            System.out.println(loEx.getMessage());
        }
        return ltResponse;
        
    }
    
    /**
     * Obtiene el count del encabezado e investigar la existencia del registro
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdHeaderModulosTab
     * @return Integer
     */
    public Integer getHeaderCountByOrdIdEvetv(OrdHeaderModulosTab toOrdHeaderModulosTab) {
        Integer                 liExist = 0;
        Connection              loCnn = new ConnectionAs400().getConnection();
        ResultSet               loRs = null;
        String                  lsQueryParadigm = 
            getQueryHeaderCountById(toOrdHeaderModulosTab.getLsOrdidEvetv());
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                liExist = loRs.getInt(1);
            }
        } catch (SQLException loExSql) {
            System.out.println("ERROR AL EJECUTAR: ");
            System.out.println(lsQueryParadigm);
            loExSql.printStackTrace();
        }
        finally{
            try {
                loCnn.close();
                loRs.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return liExist;
    }
    
    /**
     * Genera instrucion para obtener el count del encabezado e investigar la existencia del registro
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdHeaderModulosTab
     * @return Integer
     */
    public String getQueryHeaderCountById(String tsId) {
        String lsQuery = "   SELECT COUNT(1) \n" + 
        "     FROM EVENTAS.EVETV_ORDHDR\n"+
        "    WHERE ORDID_EVETV = " + tsId + " ";
        return lsQuery;
    }
    
    /**
     * Genera instruccion para obtener el count la linea e investigar la existencia del registro
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdHeaderModulosTab
     * @return Integer
     */
    public String getQueryOrdLineCountByKeyId(String tsOrdId, String tsLine) {
        String lsQuery = "  SELECT COUNT(1) \n" + 
        "    FROM EVENTAS.EVETV_ORDLN\n" + 
        "   WHERE ORDID_EVETV    = " + tsOrdId + "\n"+
        "     AND SPTMSTID_EVETV = '" + tsLine + "' \n";
        return lsQuery;
    }

    /**
     * Obtiene el count de la lineae investigar la existencia del registro
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdLinesModulosTab
     * @return Integer
     */
    public Integer getOrdLineCountByKeyId(OrdLinesModulosTab toOrdLinesModulosTab) {
        Connection   loCnn = new ConnectionAs400().getConnection();
        ResultSet    loRs = null;
        Integer      loLinesBean = 0;
        String       lsQueryParadigm = getQueryOrdLineCountByKeyId(toOrdLinesModulosTab.getLsOrdidEvetv(), 
                                                                   toOrdLinesModulosTab.getLsSptmstidEvetv());
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                loLinesBean = loRs.getInt(1);
            }
        } catch (SQLException loExSql) {
            System.out.println("ERROR AL EJECUTAR: ");
            System.out.println(lsQueryParadigm);
            loExSql.printStackTrace();
        }
        finally{
            try {
                loCnn.close();
                loRs.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return loLinesBean;
    }       
    
    /**
     * Elimina registros insertados en encabezado para cuando un SP ha fallado
     * @autor Jorge Luis Bautista Santiago
     * @param tsOrdId
     * @return Integer
     */
    public Integer rollbackOrdHeaderModulosTab(String tsOrdId) {
        Integer    liResponse = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        String     lsQueryParadigm = "DELETE FROM EVENTAS.EVETV_ORDHDR WHERE ORDID_EVETV = "+tsOrdId+"";
        try {
            Statement loStmt = loCnn.createStatement();
            liResponse = loStmt.executeUpdate(lsQueryParadigm);
        } catch (SQLException loExSql) {
            System.out.println("ERROR AL EJECUTAR INSERT HEADER: ");
            System.out.println(lsQueryParadigm);
            loExSql.printStackTrace();
        }
        finally{
            try {
                loCnn.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return liResponse;
    }
    
    /**
     * Elimina registros insertados en lineas para cuando un SP ha fallado
     * @autor Jorge Luis Bautista Santiago
     * @param tsOrdId
     * @return Integer
     */
    public Integer rollbackOrdLinesModulosTab(String tsOrdId) {
        Integer    liResponse = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        String     lsQueryParadigm = "DELETE FROM EVENTAS.EVETV_ORDLN WHERE ORDID_EVETV = " + tsOrdId + "";
        try {
            Statement loStmt = loCnn.createStatement();
            liResponse = loStmt.executeUpdate(lsQueryParadigm);
        } catch (SQLException loExSql) {
            System.out.println("ERROR AL EJECUTAR INSERT HEADER: ");
            System.out.println(lsQueryParadigm);
            loExSql.printStackTrace();
        }
        finally{
            try {
                loCnn.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return liResponse;
    }
    
    
    /**
     * Genera Instruccion para obtener el spot id de Paradigm
     * @autor Jorge Luis Bautista Santiago
     * @param tsOrdIdEveTv
     * @param tsSptmstidEveTv
     * @return String
     */
    public String getQuerySpotIdParadigm(String tsOrdIdEveTv, String tsSptmstidEveTv) {
        String lsQuery = 
        "  SELECT ORDID_EVETV, \n" + 
        "         SPTMSTID_EVETV, \n" + 
        "         ORDID, \n" + 
        "         ORDLNNUM, \n" +
        "         SPTMSTID,\n" + 
        "         STATUS, \n" + 
        "         ORDLNID, \n" + 
        "         ACTION  \n" + 
        "    FROM EVENTAS.EVETV_ORDLN\n" + 
        "   WHERE ORDID_EVETV    = " + tsOrdIdEveTv + "\n"+
        "     AND SPTMSTID_EVETV = '" + tsSptmstidEveTv + "' \n";
        return lsQuery;
    }
    
    /**
     * Obtiene linea en base a la llave compuesta
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdLinesModulosTab
     * @return ResponseBean
     */
    public String getSpotIdParadigm(String tsOrdIdEveTv, String tsSptmstidEveTv) {
        String lsSptmstid       = null;
        String                  lsQueryParadigm = getQuerySpotIdParadigm(tsOrdIdEveTv, tsSptmstidEveTv);
        Integer                 liExist = getCountSpotIdParadigm(tsOrdIdEveTv, tsSptmstidEveTv);
        if(liExist > 0){
            Connection loCnn = new ConnectionAs400().getConnection();
            ResultSet               loRs = null;
            try {
                Statement loStmt = loCnn.createStatement();
                loRs = loStmt.executeQuery(lsQueryParadigm);              
                while(loRs.next()){                    
                    lsSptmstid = loRs.getString("SPTMSTID") == null ? null : loRs.getString("SPTMSTID");                   
                }           
            } catch (SQLException loExSql) {
                System.out.println("ERROR AL EJECUTAR: ");
                System.out.println(lsQueryParadigm);
                loExSql.printStackTrace();
            }
            finally{
                try {
                    loCnn.close();
                    loRs.close();
                } catch (SQLException loEx) {
                    loEx.printStackTrace();
                }
            }
        }
        return lsSptmstid;
    }
    
    
    /**
     * Genera Instruccion para obtener el spot id de Paradigm
     * @autor Jorge Luis Bautista Santiago
     * @param tsOrdIdEveTv
     * @param tsSptmstidEveTv
     * @return String
     */
    public String getQueryCountSpotIdParadigm(String tsOrdIdEveTv, String tsSptmstidEveTv) {
        String lsQuery = 
        "  SELECT COUNT(1)\n" + 
        "    FROM EVENTAS.EVETV_ORDLN\n" + 
        "   WHERE ORDID_EVETV    = " + tsOrdIdEveTv + "\n"+
        "     AND SPTMSTID_EVETV = '" + tsSptmstidEveTv + "' \n";
        return lsQuery;
    }
    
    /**
     * Obtiene linea en base a la llave compuesta
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdLinesModulosTab
     * @return ResponseBean
     */
    public Integer getCountSpotIdParadigm(String tsOrdIdEveTv, String tsSptmstidEveTv) {
        Connection              loCnn = new ConnectionAs400().getConnection();
        ResultSet               loRs = null;
        Integer                 liResponse = 0;
        String                  lsQueryParadigm = getQueryCountSpotIdParadigm(tsOrdIdEveTv, tsSptmstidEveTv);
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                liResponse = loRs.getInt(1); 
            }
        } catch (SQLException loExSql) {
            System.out.println("ERROR AL EJECUTAR: ");
            System.out.println(lsQueryParadigm);
            loExSql.printStackTrace();
        }
        finally{
            try {
                loCnn.close();
                loRs.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return liResponse;
    }
    
    /**
     * Obtiene linea en base a la llave compuesta
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdLinesModulosTab
     * @return ResponseBean
     */
    public String getOrderIdParadigm(String tsOrdIdEveTv) {
        String lsOrderid       = null;
        String                  lsQueryParadigm = 
            "SELECT ORDID FROM EVENTAS.EVETV_ORDHDR WHERE ORDID_EVETV = " + tsOrdIdEveTv;       
        Connection loCnn = new ConnectionAs400().getConnection();
        ResultSet               loRs = null;
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);              
            while(loRs.next()){                    
                lsOrderid = loRs.getString("ORDID") == null ? null : loRs.getString("ORDID");                   
            }           
        } catch (SQLException loExSql) {
            System.out.println("ERROR AL EJECUTAR: ");
            System.out.println(lsQueryParadigm);
            loExSql.printStackTrace();
        }
        finally{
            try {
                loCnn.close();
                loRs.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return lsOrderid;
    }
    
    /**
     * Inserta linea en tabla de paradigm EVETV_SPTMST_PREEMPTION
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdLinesModulosTab
     * @return Integer
     */
    public Integer insertLinesPreemptionTab(OrdLinesModulosTab toOrdLinesModulosTab) {
        Integer    liResponse = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        String     lsQueryParadigm = getQueryInsertPreemption(toOrdLinesModulosTab);
        try {
            Statement loStmt = loCnn.createStatement();
            liResponse = loStmt.executeUpdate(lsQueryParadigm);
            System.out.println("insert ok");
        } catch (SQLException loExSql) {
            System.out.println("ERROR AL EJECUTAR: ");
            System.out.println(lsQueryParadigm);
            loExSql.printStackTrace();
        }
        finally{
            try {
                loCnn.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return liResponse;
    }
    
    /**
     * Genera instruccion para obtener Linea de la orden en base a identificador encabezado-linea
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdLinesModulosTab
     * @return String
     */
    public String getQueryInsertPreemption(OrdLinesModulosTab toOrdLinesModulosTab) {
        
        String lsOrdidEvetv = 
            toOrdLinesModulosTab.getLsOrdidEvetv() == null ? "" :   
            toOrdLinesModulosTab.getLsOrdidEvetv()         ;
        String lsSptmstidEvetv = 
            toOrdLinesModulosTab.getLsSptmstidEvetv() == null ? "" :   
            toOrdLinesModulosTab.getLsSptmstidEvetv()      ;
        String lsOrdid = 
            toOrdLinesModulosTab.getLsOrdid() == null ? "" :   
            toOrdLinesModulosTab.getLsOrdid()      ;
        String lsOrdlnnum = 
            toOrdLinesModulosTab.getLsOrdlnnum() == null ? "" :   
            toOrdLinesModulosTab.getLsOrdlnnum()   ;
        String lsBcstdt = 
            toOrdLinesModulosTab.getLsBcstdt() == null ? "" :   
            toOrdLinesModulosTab.getLsBcstdt()     ;
        String lsBuyuntid = 
            toOrdLinesModulosTab.getLsBuyuntid() == null ? "" :   
            toOrdLinesModulosTab.getLsBuyuntid()   ;
        String lsBrkdtid = 
            toOrdLinesModulosTab.getLsBrkdtid() == null ? "" :   
            toOrdLinesModulosTab.getLsBrkdtid()    ;
        String lsHour = 
            toOrdLinesModulosTab.getLsHour() == null ? "" : 
            toOrdLinesModulosTab.getLsHour()       ;
        String lsDuration = 
            toOrdLinesModulosTab.getLsDuration() == null ? "" : 
            toOrdLinesModulosTab.getLsDuration()   ;
        String lsStnid = 
            toOrdLinesModulosTab.getLsStnid() == null ? "" : 
            toOrdLinesModulosTab.getLsStnid()      ;
        String lsUsrchr = 
            toOrdLinesModulosTab.getLsUsrchr() == null ? "" : 
            toOrdLinesModulosTab.getLsUsrchr()     ;
        String lsAutoid = 
            toOrdLinesModulosTab.getLsAutoid() == null ? "" : 
            toOrdLinesModulosTab.getLsAutoid()     ;
        String lsBrnd = 
            toOrdLinesModulosTab.getLsBrnd() == null ? "" : 
            toOrdLinesModulosTab.getLsBrnd()       ;
        String lsPosesp = 
            toOrdLinesModulosTab.getLsPosesp() == null ? "" :  
            toOrdLinesModulosTab.getLsPosesp()     ;
        String lsSptrt = 
            toOrdLinesModulosTab.getLsSptrt() == null ? "" :  
            toOrdLinesModulosTab.getLsSptrt()      ;
        String lsRevsts = 
            toOrdLinesModulosTab.getLsRevsts() == null ? "" :  
            toOrdLinesModulosTab.getLsRevsts()     ;
        String lsPriority = 
            toOrdLinesModulosTab.getLsPriority() == null ? "" : 
            toOrdLinesModulosTab.getLsPriority()   ;
        String lsDigital = 
            toOrdLinesModulosTab.getLsDigital() == null ? "" :  
            toOrdLinesModulosTab.getLsDigital()    ;
        String lsPaga = 
            toOrdLinesModulosTab.getLsPaga() == null ? "" :  
            toOrdLinesModulosTab.getLsPaga()       ;        
        String lsStatus =
            toOrdLinesModulosTab.getLsStatus() == null ? "" : 
            toOrdLinesModulosTab.getLsStatus()     ;
        String lsOrdlnid = 
            toOrdLinesModulosTab.getLsOrdlnid() == null ? "" : 
            toOrdLinesModulosTab.getLsOrdlnid()    ;
        String lsAction =
            toOrdLinesModulosTab.getLsAction() == null ? "" : 
            toOrdLinesModulosTab.getLsAction()     ;
        String lsRangeAllowedStartTime = 
            toOrdLinesModulosTab.getLsRangeAllowedStartTime() == null ? "" :  
            toOrdLinesModulosTab.getLsRangeAllowedStartTime()      ;
        String lsRangeAllowedEndTime = 
            toOrdLinesModulosTab.getLsRangeAllowedEndTime() == null ? "" : 
            toOrdLinesModulosTab.getLsRangeAllowedEndTime()        ;

        if(lsOrdidEvetv.trim().length() == 0){lsOrdidEvetv = "null";}
        if(lsSptmstidEvetv.trim().length() == 0){lsSptmstidEvetv = "null";}
        if(lsOrdid.trim().length() == 0){lsOrdid = "null";}
        if(lsOrdlnnum.trim().length() == 0){lsOrdlnnum = "null";}
        if(lsBcstdt.trim().length() == 0){lsBcstdt = "null";}
        if(lsBuyuntid.trim().length() == 0){lsBuyuntid = "null";}
        if(lsBrkdtid.trim().length() == 0){lsBrkdtid = "null";}
        if(lsHour.trim().length() == 0){lsHour = "null";}
        if(lsDuration.trim().length() == 0){lsDuration = "null";}
        if(lsStnid.trim().length() == 0){lsStnid = "null";}
        if(lsUsrchr.trim().length() == 0){lsUsrchr = "null";}
        if(lsAutoid.trim().length() == 0){lsAutoid = "null";}
        if(lsBrnd.trim().length() == 0){lsBrnd = "null";}
        if(lsPosesp.trim().length() == 0){lsPosesp = "null";}
        if(lsSptrt.trim().length() == 0){lsSptrt = "null";}
        if(lsRevsts.trim().length() == 0){lsRevsts = "null";}
        if(lsPriority.trim().length() == 0){lsPriority = "null";}
        if(lsDigital.trim().length() == 0){lsDigital = "null";}
        if(lsPaga.trim().length() == 0){lsPaga = "null";}
        if(lsStatus.trim().length() == 0){lsStatus = "null";}
        if(lsOrdlnid.trim().length() == 0){lsOrdlnid = "null";}
        if(lsAction.trim().length() == 0){lsAction = "null";}
        if(lsRangeAllowedStartTime.trim().length() == 0){lsRangeAllowedStartTime = "null";}
        if(lsRangeAllowedEndTime.trim().length() == 0){lsRangeAllowedEndTime   = "null";}
              
        /*String lsQuery = 
            "INSERT INTO EVENTAS.EVETV_SPTMST_PREEMPTION (ORDID_EVETV, \n" + 
            "                         SPTMSTID_EVETV, \n" + 
            "                         ORDID, \n" + 
//            "                         ORDLNNUM, \n" + 
            "                         BCSTDT, \n" + 
            "                         BUYUNTID, \n" + 
            "                         BRKDTID, \n" + 
            "                         HOUR, \n" + 
            "                         DURATION, \n" + 
            "                         STNID, \n" + 
            "                         USRCHR, \n" + 
            "                         AUTOID, \n" + 
            "                         BRND, \n" + 
            "                         POSESP, \n" + 
            "                         SPTRT, \n" + 
            "                         REVSTS, \n" + 
            "                         PRIORITY, \n" + 
            "                         DIGITAL, \n" + 
            "                         PAGA, \n" + 
           "                         SPTMSTID, \n" + 
            "                         STATUS, \n" + 
//            "                         ORDLNID, \n" + 
            "                         ACTION,\n" + 
           "                         STRTIM,\n" +
            "                         ETIM\n" +
            "                         ) \n" + 
            "                 VALUES (" + toOrdLinesModulosTab.getLsOrdidEvetv() + ", \n" + 
            "                         '" + toOrdLinesModulosTab.getLsSptmstidEvetv() + "', \n" + 
            "                         " + toOrdLinesModulosTab.getLsOrdid() + ", \n" + 
//            "                         " + toOrdLinesModulosTab.getLsOrdlnnum() + ", \n" + 
            "                         '" + toOrdLinesModulosTab.getLsBcstdt() + "', \n" + 
            "                         '" + toOrdLinesModulosTab.getLsBuyuntid() + "', \n" + 
            "                         " + toOrdLinesModulosTab.getLsBrkdtid() + ", \n" + 
            "                         '" + toOrdLinesModulosTab.getLsHour() + "', \n" + 
            "                         '" + toOrdLinesModulosTab.getLsDuration() + "', \n" + 
            "                         '" + toOrdLinesModulosTab.getLsStnid() + "',\n" + 
            "                         '" + toOrdLinesModulosTab.getLsUsrchr() + "', \n" + 
            "                         '" + toOrdLinesModulosTab.getLsAutoid() + "', \n" + 
            "                         '" + toOrdLinesModulosTab.getLsBrnd() + "',\n" + 
            "                         '" + toOrdLinesModulosTab.getLsPosesp() + "', \n" + 
            "                         " + toOrdLinesModulosTab.getLsSptrt() + ", \n" + 
            "                         '" + toOrdLinesModulosTab.getLsRevsts() + "', \n" + 
            "                         '" + toOrdLinesModulosTab.getLsPriority() + "', \n" + 
            "                         '" + toOrdLinesModulosTab.getLsDigital() + "', \n" + 
            "                         '" + toOrdLinesModulosTab.getLsPaga() + "', \n" + 
            "                         '" + toOrdLinesModulosTab.getLsSptmstidEvetv() + "', \n" + //Spain envia aqui el Sptmstid de Paradigm
            "                         " + toOrdLinesModulosTab.getLsStatus() + ", \n" + 
//            "                         " + toOrdLinesModulosTab.getLsOrdlnid()+", \n" + 
            "                         '" + toOrdLinesModulosTab.getLsAction() + "',\n" + 
            "                         '" + toOrdLinesModulosTab.getLsRangeAllowedStartTime() + "',\n" +
            "                         '" + toOrdLinesModulosTab.getLsRangeAllowedEndTime() + "'\n" +
            "                         )";*/
        String lsQuery = 
            "INSERT INTO EVENTAS.EVETV_SPTMST_PREEMPTION (ORDID_EVETV, \n" + 
            "                         SPTMSTID_EVETV, \n" + 
            "                         ORDID, \n" + 
        //            "                         ORDLNNUM, \n" +
            "                         BCSTDT, \n" + 
            "                         BUYUNTID, \n" + 
            "                         BRKDTID, \n" + 
            "                         HOUR, \n" + 
            "                         DURATION, \n" + 
            "                         STNID, \n" + 
            "                         USRCHR, \n" + 
            "                         AUTOID, \n" + 
            "                         BRND, \n" + 
            "                         POSESP, \n" + 
            "                         SPTRT, \n" + 
            "                         REVSTS, \n" + 
            "                         PRIORITY, \n" + 
            "                         DIGITAL, \n" + 
            "                         PAGA, \n" + 
           "                         SPTMSTID, \n" + 
            "                         STATUS, \n" + 
        //            "                         ORDLNID, \n" +
            "                         ACTION,\n" + 
           "                         STRTIM,\n" +
            "                         ETIM\n" +
            "                         ) \n" + 
            "                 VALUES (" + lsOrdidEvetv + ", \n" + 
            "                         '" + lsSptmstidEvetv + "', \n" + 
            "                         " + lsOrdid + ", \n" + 
        //            "                         " + lsOrdlnnum + ", \n" +
            "                         '" + lsBcstdt + "', \n" + 
            "                         '" + lsBuyuntid + "', \n" + 
            "                         " + lsBrkdtid + ", \n" + 
            "                         '" + lsHour + "', \n" + 
            "                         '" + lsDuration + "', \n" + 
            "                         '" + lsStnid + "',\n" + 
            "                         '" + lsUsrchr + "', \n" + 
            "                         '" + lsAutoid + "', \n" + 
            "                         '" + lsBrnd + "',\n" + 
            "                         '" + lsPosesp + "', \n" + 
            "                         " + lsSptrt + ", \n" + 
            "                         '" + lsRevsts + "', \n" + 
            "                         '" + lsPriority + "', \n" + 
            "                         '" + lsDigital + "', \n" + 
            "                         '" + lsPaga + "', \n" + 
            "                         '" + lsSptmstidEvetv + "', \n" + //Spain envia aqui el Sptmstid de Paradigm
            "                         " + lsStatus + ", \n" + 
        //            "                         " + lsOrdlnid()+", \n" +
            "                         '" + lsAction + "',\n" + 
            "                         '" + lsRangeAllowedStartTime + "',\n" +
            "                         '" + lsRangeAllowedEndTime + "'\n" +
            "                         )";
        return lsQuery;
    }
    
    /**
     * Ejecuta procedimiento en base de datos para las lineas de la orden
     * @autor Jorge Luis Bautista Santiago
     * @param tiSptmstid
     * @return ResponseUpdDao
     */
    public ResponseUpdDao callProcedurePreemption(Integer tiSptmstid) throws SQLException {
        ResponseUpdDao    loResponseUpdDao = new ResponseUpdDao();
        Connection        loCnn = new ConnectionAs400().getConnection();
        CallableStatement loCallStmt = null;
        String            lsQueryParadigm =
            "call EVENTAS.EVETV_SPOTS_VTA_TRADICIONAL_CANCEL(?)";
        try {
            System.out.println("invocando a EVENTAS.EVETV_SPOTS_VTA_TRADICIONAL_CANCEL("+tiSptmstid+")");
            loCallStmt = loCnn.prepareCall(lsQueryParadigm);
            loCallStmt.setInt(1, tiSptmstid);            
            loCallStmt.execute();
            loResponseUpdDao.setLiAffected(1);
            loResponseUpdDao.setLsResponse("OK");
            loResponseUpdDao.setLsMessage("call EVENTAS.EVETV_SPOTS_VTA_TRADICIONAL_CANCEL success!!! con Sptmstid Paradigm ("+tiSptmstid+")");
            System.out.println("invocando a EVENTAS.EVETV_SPOTS_VTA_TRADICIONAL_CANCEL("+tiSptmstid+")..........OK");
        } catch (SQLException loExSql) {
            System.out.println("ERROR: "+loExSql.getMessage());
            System.out.println("ERROR AL EJECUTAR: ");
            System.out.println(lsQueryParadigm);
            loResponseUpdDao.setLiAffected(0);
            loResponseUpdDao.setLsResponse("KO");
            loResponseUpdDao.setLsMessage(loExSql.getMessage());
            throw loExSql;
        }
        finally{
            try {
                loCnn.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return loResponseUpdDao;
    }
        
    /**
     * Instruccion para validar existencia de la linea
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdLinesModulosTab
     * @return String
     */
    public String getQueryPreemptionByKeyIdExist(OrdLinesModulosTab toOrdLinesModulosTab, String tsCondition) {
        String lsQuery = "  SELECT COUNT(1) \n" + 
        "    FROM EVENTAS.EVETV_SPTMST_PREEMPTION\n" + 
        "   WHERE ORDID_EVETV    = " + toOrdLinesModulosTab.getLsOrdidEvetv() + "\n"+
        "     AND SPTMSTID_EVETV = '" + toOrdLinesModulosTab.getLsSptmstidEvetv() + "' \n";
        if(tsCondition.length() > 0){
            lsQuery += " AND SPTMSTID_EVETV IN (" + tsCondition + ") \n";    
            
        }else{
            lsQuery += " AND 1 = 2 "; //Ninguna linea fue procesada
        }
        return lsQuery;
    }
    
    /**
     * Obtiene linea en base a la llave compuesta
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdLinesModulosTab
     * @return ResponseBean
     */
    public Integer getPreemptionByKeyIdExist(OrdLinesModulosTab toOrdLinesModulosTab, String lsCondition) {
        Connection              loCnn = new ConnectionAs400().getConnection();
        ResultSet               loRs = null;
        Integer                 liResponse = 0;
        String                  lsQueryParadigm = getQueryPreemptionByKeyIdExist(toOrdLinesModulosTab, lsCondition);
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                liResponse = loRs.getInt(1); 
            }            
        } catch (SQLException loExSql) {
            System.out.println("ERROR AL EJECUTAR: ");
            System.out.println(lsQueryParadigm);
            loExSql.printStackTrace();
        }
        finally{
            try {
                loCnn.close();
                loRs.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return liResponse;
    }
    
    /**
     * Obtiene linea en base a la llave compuesta
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdLinesModulosTab
     * @return ResponseBean
     */
    public String getSpotIdParadigmPreemption(String tsOrdIdEveTv, String tsSptmstidEveTv) {
        String lsSptmstid       = null;
        String                  lsQueryParadigm = 
            getQuerySpotIdParadigmPreemeption(tsOrdIdEveTv, tsSptmstidEveTv);
        Integer                 liExist = 
            getCountSpotIdParadigmPreemption(tsOrdIdEveTv, tsSptmstidEveTv);
        if(liExist > 0){
            Connection loCnn = new ConnectionAs400().getConnection();
            ResultSet               loRs = null;
            try {
                Statement loStmt = loCnn.createStatement();
                loRs = loStmt.executeQuery(lsQueryParadigm);              
                while(loRs.next()){                    
                    lsSptmstid = loRs.getString("SPTMSTID") == null ? null : loRs.getString("SPTMSTID");                   
                }           
            } catch (SQLException loExSql) {
                System.out.println("ERROR AL EJECUTAR: ");
                System.out.println(lsQueryParadigm);
                loExSql.printStackTrace();
            }
            finally{
                try {
                    loCnn.close();
                    loRs.close();
                } catch (SQLException loEx) {
                    loEx.printStackTrace();
                }
            }
        }
        return lsSptmstid;
    }
    
    /**
     * Genera Instruccion para obtener el spot id de Paradigm
     * @autor Jorge Luis Bautista Santiago
     * @param tsOrdIdEveTv
     * @param tsSptmstidEveTv
     * @return String
     */
    public String getQuerySpotIdParadigmPreemeption(String tsOrdIdEveTv, String tsSptmstidEveTv) {
        String lsQuery = 
        "  SELECT ORDID_EVETV, \n" + 
        "         SPTMSTID_EVETV, \n" + 
        "         ORDID, \n" + 
        "         ORDLNNUM, \n" +
        "         SPTMSTID,\n" + 
        "         STATUS, \n" + 
        "         ORDLNID, \n" + 
        "         ACTION  \n" + 
        "    FROM EVENTAS.EVETV_SPTMST_PREEMPTION\n" + 
        "   WHERE ORDID_EVETV    = " + tsOrdIdEveTv + "\n"+
        "     AND SPTMSTID_EVETV = '" + tsSptmstidEveTv + "' \n";
        return lsQuery;
    }
    
    /**
     * Obtiene linea en base a la llave compuesta
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdLinesModulosTab
     * @return ResponseBean
     */
    public Integer getCountSpotIdParadigmPreemption(String tsOrdIdEveTv, String tsSptmstidEveTv) {
        Connection              loCnn = new ConnectionAs400().getConnection();
        ResultSet               loRs = null;
        Integer                 liResponse = 0;
        String                  lsQueryParadigm = 
            getQueryCountSpotIdParadigmPrmmtn(tsOrdIdEveTv, tsSptmstidEveTv);
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                liResponse = loRs.getInt(1); 
            }
        } catch (SQLException loExSql) {
            System.out.println("ERROR AL EJECUTAR: ");
            System.out.println(lsQueryParadigm);
            loExSql.printStackTrace();
        }
        finally{
            try {
                loCnn.close();
                loRs.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return liResponse;
    }
    
    /**
     * Genera Instruccion para obtener el spot id de Paradigm
     * @autor Jorge Luis Bautista Santiago
     * @param tsOrdIdEveTv
     * @param tsSptmstidEveTv
     * @return String
     */
    public String getQueryCountSpotIdParadigmPrmmtn(String tsOrdIdEveTv, String tsSptmstidEveTv) {
        String lsQuery = 
        "  SELECT COUNT(1)\n" + 
        "    FROM EVENTAS.EVETV_SPTMST_PREEMPTION\n" + 
        "   WHERE ORDID_EVETV    = " + tsOrdIdEveTv + "\n"+
        "     AND SPTMSTID_EVETV = '" + tsSptmstidEveTv + "' \n";
        return lsQuery;
    }
    
    public OrdLinesModulosTab getPreemptionByKeyId(OrdLinesModulosTab toOrdLinesModulosTab) {
        
        
        OrdLinesModulosTab      loLinesBean = new OrdLinesModulosTab();
        String                  lsQueryParadigm = getQueryPreemtionByKeyId(toOrdLinesModulosTab);
        Integer                 liExist = 1;//getOrdLineCountByKeyId(toOrdLinesModulosTab);
        if(liExist == 0){
            loLinesBean.setLsOrdidEvetv(toOrdLinesModulosTab.getLsOrdidEvetv()); 
            loLinesBean.setLsSptmstidEvetv(toOrdLinesModulosTab.getLsSptmstidEvetv());
            loLinesBean.setLsOrdid("0");
            loLinesBean.setLsOrdlnnum("0");
            loLinesBean.setLsBcstdt("");
            loLinesBean.setLsBuyuntid("");
            loLinesBean.setLsBrkdtid("");
            loLinesBean.setLsHour("");
            loLinesBean.setLsDuration("");
            loLinesBean.setLsStnid("");
            loLinesBean.setLsUsrchr("");
            loLinesBean.setLsAutoid("");
            loLinesBean.setLsBrnd("");
            loLinesBean.setLsPosesp("");
            loLinesBean.setLsSptrt("");
            loLinesBean.setLsRevsts("");
            loLinesBean.setLsPriority("");
            loLinesBean.setLsDigital("");
            loLinesBean.setLsPaga("");
            loLinesBean.setLsSptmstid("0");
            loLinesBean.setLsStatus("OK");
            loLinesBean.setLsOrdlnid("0");
            loLinesBean.setLsAction("D");
            loLinesBean.setLsRangeAllowedStartTime("");
            loLinesBean.setLsRangeAllowedEndTime("");
        }else{
            Connection loCnn = new ConnectionAs400().getConnection();
            ResultSet               loRs = null;
            try {
                Statement loStmt = loCnn.createStatement();
                loRs = loStmt.executeQuery(lsQueryParadigm);              
                while(loRs.next()){
                    loLinesBean.setLsOrdidEvetv(loRs.getString("ORDID_EVETV") == null ? null : 
                                              loRs.getString("ORDID_EVETV").trim()); 
                    loLinesBean.setLsSptmstidEvetv(loRs.getString("SPTMSTID_EVETV") == null ? null : 
                                              loRs.getString("SPTMSTID_EVETV").trim());
                    loLinesBean.setLsOrdid(loRs.getString("ORDID") == null ? null :
                                             loRs.getString("ORDID").trim());
                    loLinesBean.setLsOrdlnnum(loRs.getString("ORDLNNUM") == null ? null :
                                             loRs.getString("ORDLNNUM").trim());
                    loLinesBean.setLsBcstdt(loRs.getString("BCSTDT") == null ? null :
                                             loRs.getString("BCSTDT").trim());
                    loLinesBean.setLsBuyuntid(loRs.getString("BUYUNTID") == null ? null :
                                             loRs.getString("BUYUNTID").trim());
                    loLinesBean.setLsBrkdtid(loRs.getString("BRKDTID") == null ? null :
                                             loRs.getString("BRKDTID").trim());
                    loLinesBean.setLsHour(loRs.getString("HOUR") == null ? null :
                                             loRs.getString("HOUR").trim());
                    loLinesBean.setLsDuration(loRs.getString("DURATION") == null ? null :
                                             loRs.getString("DURATION").trim());
                    loLinesBean.setLsStnid(loRs.getString("STNID") == null ? null :
                                             loRs.getString("STNID").trim());
                    loLinesBean.setLsUsrchr(loRs.getString("USRCHR") == null ? null :
                                             loRs.getString("USRCHR").trim());
                    loLinesBean.setLsAutoid(loRs.getString("AUTOID") == null ? null :
                                             loRs.getString("AUTOID").trim());
                    loLinesBean.setLsBrnd(loRs.getString("BRND") == null ? null :
                                             loRs.getString("BRND").trim());
                    loLinesBean.setLsPosesp(loRs.getString("POSESP") == null ? null :
                                             loRs.getString("POSESP").trim());
                    loLinesBean.setLsSptrt(loRs.getString("SPTRT") == null ? null :
                                             loRs.getString("SPTRT").trim());
                    loLinesBean.setLsRevsts(loRs.getString("REVSTS") == null ? null :
                                             loRs.getString("REVSTS").trim());
                    loLinesBean.setLsPriority(loRs.getString("PRIORITY") == null ? null :
                                             loRs.getString("PRIORITY").trim());
                    loLinesBean.setLsDigital(loRs.getString("DIGITAL") == null ? null :
                                             loRs.getString("DIGITAL").trim());
                    loLinesBean.setLsPaga(loRs.getString("PAGA") == null ? null :
                                             loRs.getString("PAGA").trim());
                    loLinesBean.setLsSptmstid(loRs.getString("SPTMSTID") == null ? null :
                                             loRs.getString("SPTMSTID").trim());
                    loLinesBean.setLsStatus(loRs.getString("STATUS") == null ? null :
                                             loRs.getString("STATUS").trim());
                    loLinesBean.setLsOrdlnid(loRs.getString("ORDLNID") == null ? null :
                                             loRs.getString("ORDLNID").trim());
                    loLinesBean.setLsAction(loRs.getString("ACTION") == null ? null :
                                             loRs.getString("ACTION").trim());
                    loLinesBean.setLsRangeAllowedStartTime(loRs.getString("STRTIM") == null ? null :
                                             loRs.getString("STRTIM").trim());
                    loLinesBean.setLsRangeAllowedEndTime(loRs.getString("ETIM") == null ? null :
                                             loRs.getString("ETIM").trim());
                }           
            } catch (SQLException loExSql) {
                System.out.println("ERROR AL EJECUTAR: ");
                System.out.println(lsQueryParadigm);
                loExSql.printStackTrace();
            }
            finally{
                try {
                    loCnn.close();
                    loRs.close();
                } catch (SQLException loEx) {
                    loEx.printStackTrace();
                }
            }
        }
        return loLinesBean;
    }
    
    public String getQueryPreemtionByKeyId(OrdLinesModulosTab toOrdLinesModulosTab) {
        String lsQuery = "  SELECT ORDID_EVETV, \n" + 
        "         SPTMSTID_EVETV, \n" + 
        "         ORDID, \n" + 
        "         ORDLNNUM, \n" + 
        "         BCSTDT, \n" + 
        "         BUYUNTID, \n" + 
        "         BRKDTID, \n" + 
        "         HOUR, \n" + 
        "         DURATION, \n" + 
        "         STNID, \n" + 
        "         USRCHR, \n" + 
        "         AUTOID, \n" + 
        "         BRND, \n" + 
        "         POSESP, \n" + 
        "         SPTRT, \n" + 
        "         REVSTS, \n" + 
        "         PRIORITY, \n" + 
        "         DIGITAL, \n" + 
        "         PAGA, \n" + 
        "         SPTMSTID, \n" + 
        "         STATUS, \n" + 
        "         ORDLNID, \n" + 
        "         ACTION,  \n" + 
    /*CH*/        "         STRTIM,  \n" +
    /*CH*/        "         ETIM  \n" +
        "    FROM EVENTAS.EVETV_SPTMST_PREEMPTION\n" + 
        "   WHERE ORDID_EVETV    = " + toOrdLinesModulosTab.getLsOrdidEvetv() + "\n"+
        "     AND SPTMSTID_EVETV = '" + toOrdLinesModulosTab.getLsSptmstidEvetv() + "' \n";
        return lsQuery;
    }

    
}
