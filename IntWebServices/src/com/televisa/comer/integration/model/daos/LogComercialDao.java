/**
* Project: Integraton Paradigm - EveTV
*
* File: LogComercialDao.java
*
* Created on: Septiembre 23, 2017 at 11:00
*
* Copyright (c) - OMW - 2017
*/
package com.televisa.comer.integration.model.daos;

import com.televisa.comer.integration.model.beans.ResponseBean;
import com.televisa.comer.integration.model.beans.ResponseUpdDao;
import com.televisa.comer.integration.model.beans.pgm.EvetvLogComercialProcesadoBean;
import com.televisa.comer.integration.model.beans.pgm.EvetvLogComercialStatusTab;
import com.televisa.comer.integration.model.beans.pgm.EvetvLogComercialTab;
import com.televisa.comer.integration.model.beans.pgm.OrdHeaderModulosTab;
import com.televisa.comer.integration.model.connection.ConnectionAs400;
import com.televisa.comer.integration.model.interfaces.LogComercialInterface;

import com.televisa.comer.integration.service.beans.pgm.commerciallog.ActiveBreak;

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

/** Clase objeto de acceso a datos para logica del Log Comercial
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Septiembre 23, 2017, 12:00 pm
 */
public class LogComercialDao implements LogComercialInterface {

    /**
     * Inserta log comercial en base de datos
     * @autor Jorge Luis Bautista Santiago
     * @param toEvetvLogComercialTab
     * @return Integer
     */
    @Override
    public Integer insertLogComercial(EvetvLogComercialTab toEvetvLogComercialTab) {
        Integer    liResponse = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        String     lsQueryParadigm = getQueryInsertLogComercial(toEvetvLogComercialTab);
        try {
            Statement loStmt = loCnn.createStatement();
            liResponse = loStmt.executeUpdate(lsQueryParadigm);
            //System.out.println("\tInsert en DAO: OK ");
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
     * Genera instruccion para insertar log comercial en base de datos
     * @autor Jorge Luis Bautista Santiago
     * @param toEvetvLogComercialTab
     * @return String
     */
    @Override
    public String getQueryInsertLogComercial(EvetvLogComercialTab toEvetvLogComercialTab) {
        
        String lsStnid =  toEvetvLogComercialTab.getLsStnid() == null ? "null" :  toEvetvLogComercialTab.getLsStnid();
        String lsBcstdt =  toEvetvLogComercialTab.getLsBcstdt() == null ? "null" :  toEvetvLogComercialTab.getLsBcstdt();
        String lsBrkdtid = toEvetvLogComercialTab.getLsBrkdtid()  == null ? "null" :  toEvetvLogComercialTab.getLsBrkdtid() ;
        String lsDesccorte =  toEvetvLogComercialTab.getLsDesccorte() == null ? "null" :  toEvetvLogComercialTab.getLsDesccorte();
        String lsActtim =  toEvetvLogComercialTab.getLsActtim() == null ? "null" :  toEvetvLogComercialTab.getLsActtim();
        String lsOverlay =  toEvetvLogComercialTab.getLsOverlay() == null ? "null" :  toEvetvLogComercialTab.getLsOverlay();
        String lsAction =  toEvetvLogComercialTab.getLsAction() == null ? "null" :  toEvetvLogComercialTab.getLsAction();
        String lsOrdid = toEvetvLogComercialTab.getLsOrdid()  == null ? "null" :  toEvetvLogComercialTab.getLsOrdid() ;
        String lsSptmstid = toEvetvLogComercialTab.getLsSptmstid()  == null ? "null" :  toEvetvLogComercialTab.getLsSptmstid() ;
        String lsSptlen =  toEvetvLogComercialTab.getLsSptlen() == null ? "null" :  toEvetvLogComercialTab.getLsSptlen();
        String lsSpotformatid =  toEvetvLogComercialTab.getLsSpotformatid() == null ? "null" :  toEvetvLogComercialTab.getLsSpotformatid();
        String lsSpotcomments =  toEvetvLogComercialTab.getLsSpotcomments() == null ? "null" :  toEvetvLogComercialTab.getLsSpotcomments();
        String lsBrnd =  toEvetvLogComercialTab.getLsBrnd() == null ? "null" :  toEvetvLogComercialTab.getLsBrnd();
        String lsAutoid =  toEvetvLogComercialTab.getLsAutoid() == null ? "null" :  toEvetvLogComercialTab.getLsAutoid();
        String lsBookingpos =  toEvetvLogComercialTab.getLsBookingpos() == null ? "null" :  toEvetvLogComercialTab.getLsBookingpos();
        String lsWarning =  toEvetvLogComercialTab.getLsWarning() == null ? "null" :  toEvetvLogComercialTab.getLsWarning();
        String lsUntnum = toEvetvLogComercialTab.getLsUntnum()  == null ? "null" :  toEvetvLogComercialTab.getLsUntnum() ;
        String lsSptrt =  toEvetvLogComercialTab.getLsSptrt() == null ? "null" :  toEvetvLogComercialTab.getLsSptrt();
        String lsValidate = toEvetvLogComercialTab.getLsValidate() == null ? "null" :  toEvetvLogComercialTab.getLsValidate();
        
        if(lsStnid.trim().equalsIgnoreCase("")){  lsStnid = "null"; }
        if(lsBcstdt.trim().equalsIgnoreCase("")){  lsBcstdt = "null"; }
        if(lsBrkdtid.trim().equalsIgnoreCase("")){lsBrkdtid = "null";}
        if(lsDesccorte.trim().equalsIgnoreCase("")){  lsDesccorte = "null"; }
        if(lsActtim.trim().equalsIgnoreCase("")){  lsActtim = "null"; }
        if(lsOverlay.trim().equalsIgnoreCase("")){  lsOverlay = "null"; }
        if(lsAction.trim().equalsIgnoreCase("")){  lsAction = "null"; }
        if(lsOrdid.trim().equalsIgnoreCase("")){ lsOrdid = "null";}
        if(lsSptmstid.trim().equalsIgnoreCase("")){ lsSptmstid = "null";}
        if(lsSptlen.trim().equalsIgnoreCase("")){  lsSptlen = "null"; }
        if(lsSpotformatid.trim().equalsIgnoreCase("")){  lsSpotformatid = "null"; }
        if(lsSpotcomments.trim().equalsIgnoreCase("")){  lsSpotcomments = "null"; }
        if(lsBrnd.trim().equalsIgnoreCase("")){  lsBrnd = "null"; }
        if(lsAutoid.trim().equalsIgnoreCase("")){  lsAutoid = "null"; }
        if(lsBookingpos.trim().equalsIgnoreCase("")){  lsBookingpos = "null"; }
        if(lsWarning.trim().equalsIgnoreCase("")){  lsWarning = "null"; }
        if(lsUntnum.trim().equalsIgnoreCase("")){ lsUntnum = "null";}
        if(lsSptrt.trim().equalsIgnoreCase("")){  lsSptrt = "null"; }
        if(lsValidate.trim().equalsIgnoreCase("")){ lsValidate = "null";}
        
        String lsQuery =
        "INSERT INTO EVENTAS.EVETV_LOG_COMERCIAL(STNID,\n" + 
        "                                        BCSTDT,\n" + 
        "                                        BRKDTID,\n" + 
        "                                        DESCCORTE,\n" + 
        "                                        ACTTIM,\n" + 
        "                                        OVERLAY,\n" + 
        "                                        ACTION,\n" + 
        "                                        ORDID,\n" + 
        "                                        SPTMSTID,\n" + 
        "                                        SPTLEN,\n" + 
        "                                        SPOTFORMATID,\n" + 
        "                                        SPOTCOMMENTS,\n" + 
        "                                        BRND,\n" + 
        "                                        AUTOID,\n" + 
        "                                        BOOKINGPOS,\n" + 
        "                                        WARNING,\n" + 
        "                                        UNTNUM,\n" + 
        "                                        SPTRT,\n" + //recordar la coma
/*CH*/        "                                        VALIDATE\n" + 
        "                                       )\n" + 
        "                   VALUES ('" + lsStnid + "',\n";
        if(lsBcstdt.equalsIgnoreCase("null")){lsQuery += "null,\n";}else{lsQuery+="'" + lsBcstdt + "',\n";}
        if(lsBrkdtid.equalsIgnoreCase("null")){lsQuery += "null,\n";}else{lsQuery+="" + lsBrkdtid + ",\n";}
        if(lsDesccorte.equalsIgnoreCase("null")){lsQuery += "null,\n";}else{lsQuery+="'" + lsDesccorte + "',\n";}
        if(lsActtim.equalsIgnoreCase("null")){lsQuery += "null,\n";}else{lsQuery+="'" + lsActtim + "',\n";}
        if(lsOverlay.equalsIgnoreCase("null")){lsQuery += "null,\n";}else{lsQuery+="'" + lsOverlay + "',\n";}
        if(lsAction.equalsIgnoreCase("null")){lsQuery += "null,\n";}else{lsQuery+="'" + lsAction + "',\n";}
        if(lsOrdid.equalsIgnoreCase("null")){lsQuery += "null,\n";}else{lsQuery+="" + lsOrdid + ",\n";}
        if(lsSptmstid.equalsIgnoreCase("null")){lsQuery += "null,\n";}else{lsQuery+="" + lsSptmstid + ",\n";}
        if(lsSptlen.equalsIgnoreCase("null")){lsQuery += "null,\n";}else{lsQuery+="'" + lsSptlen + "',\n";}
        if(lsSpotformatid.equalsIgnoreCase("null")){lsQuery += "null,\n";}else{lsQuery+="'" + lsSpotformatid + "',\n";}
        if(lsSpotcomments.equalsIgnoreCase("null")){lsQuery += "null,\n";}else{lsQuery+="'" + lsSpotcomments + "',\n";}
        if(lsBrnd.equalsIgnoreCase("null")){lsQuery += "null,\n";}else{lsQuery+="'" + lsBrnd + "',\n";}
        if(lsAutoid.equalsIgnoreCase("null")){lsQuery += "null,\n";}else{lsQuery+="'" + lsAutoid + "',\n";}
        if(lsBookingpos.equalsIgnoreCase("null")){lsQuery += "null,\n";}else{lsQuery+="'" + lsBookingpos + "',\n";}
        if(lsWarning.equalsIgnoreCase("null")){lsQuery += "null,\n";}else{lsQuery+="'" + lsWarning + "',\n";}
        if(lsUntnum.equalsIgnoreCase("null")){lsQuery += "null,\n";}else{lsQuery+="" + lsUntnum + ",\n";}
        if(lsSptrt.equalsIgnoreCase("null")){lsQuery += "null,\n";}else{lsQuery+="'" + lsSptrt + "',\n";}
        if(lsValidate.equalsIgnoreCase("null")){lsQuery += "null\n";}else{lsQuery+="'" + lsValidate + "'\n";}        
        lsQuery +=                
        "                          )";
        return lsQuery;
    }

    /**
     * Obtiene count de los breaks del canal y fecha especificados
     * @autor Jorge Luis Bautista Santiago
     * @param tsStnid
     * @param tsBcstdt
     * @return Integer
     */
    @Override
    public Integer validateSpotLogComercial(String tsStnid, String tsBcstdt) {
        Integer    loFlag = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        ResultSet  loRs = null;
        String     lsQueryParadigm = getQueryValidateSpotLogComercial(tsStnid, tsBcstdt);
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                loFlag = loRs.getInt(1);
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
        return loFlag;
    }

    /**
     * Valida existencia de spot en log comercial del canal y fecha especificados
     * @autor Jorge Luis Bautista Santiago
     * @param tsStnid
     * @param tsBcstdt
     * @return Integer
     */
    @Override
    public String getQueryValidateSpotLogComercial(String tsStnid, String tsBcstdt) {
        String lsQuery = "SELECT COUNT(1) VAL_RES\n" + 
        "FROM PARADB.SPTMST, PARADB.ORDLN\n" + 
        "WHERE SPTMST.ORDID = ORDLN.ORDID\n" + 
        "AND SPTMST.ORDLNID = ORDLN.ORDLNID\n" + 
        "AND SPTMST.STNID = '" +  tsStnid + "' -- parametro del canal que se recibe\n" + 
        "AND SPTMST.BCSTDT = '" + tsBcstdt + "' -- parametro de la fecha de transmision que se recibe\n" + 
        "AND SPTMST.ACTSTS = 1\n" + 
        "AND ORDLN.ORDLNTYP IN (0, 2)\n" + 
        "AND SPTMST.SPTCHR NOT IN (1,2,5,6,12)\n" + 
        "AND SPTMST.USRCHR NOT IN ('$','@','#')\n" + 
        "AND ORDLN.SECNUM NOT IN 9\n";
        return lsQuery;
    }

    /**
     * Obtiene count de los breaks del canal y fecha especificados
     * @autor Jorge Luis Bautista Santiago
     * @param tsStnid
     * @param tsBcstdt
     * @return Integer
     */
    @Override
    public Integer validateBreakLogComercial(String tsStnid, String tsBcstdt) {
        Integer    loFlag = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        ResultSet  loRs = null;
        String     lsQueryParadigm = getQueryValidateBreakLogComercial(tsStnid, tsBcstdt);
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                loFlag = loRs.getInt(1);
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
        return loFlag;
    }

    /**
     * Genera intruccion para obtener todos los breaks del canal y fecha especificados
     * @autor Jorge Luis Bautista Santiago
     * @param tsStnid
     * @param tsBcstdt
     * @return String
     */
    @Override
    public String getQueryValidateBreakLogComercial(String tsStnid, String tsBcstdt) {
        String lsQuery = 
            "SELECT\n" + 
            "    COUNT(1)\n" + 
            "FROM    PARADB.LOGEDT LOGEDT\n" + 
            "        LEFT OUTER JOIN PARADB.LOGLINE LOGLINE\n" + 
            "            ON LOGEDT.LOGEDTID = LOGLINE.LOGEDTID\n" + 
            "        LEFT OUTER JOIN PARADB.BRKDT BRKDT\n" + 
            "            ON  LOGEDT.LOGEDTID = BRKDT.LOGEDTID\n" + 
            "        LEFT OUTER JOIN PARADB.LOGCMT LOGCMT\n" + 
            "            ON  LOGEDT.LOGEDTID = LOGCMT.LOGEDTID\n" + 
            "WHERE LOGEDT.ALTLOG = 0\n" + 
            "AND LOGEDT.STNID IN ('" + tsStnid + "') -- PARAMETRO DE CANAL\n" + 
            "AND LOGEDT.BCSTDT = '" + tsBcstdt + "' -- PARAMETRO DE FECHA\n" + 
            "AND LOGEDT.FMTTYP in (2)\n" + 
            "AND  BRKDT.BRKCHRRULE IN ('CO','')\n" + 
            "AND BRKDT.INVFL = 1\n";
        return lsQuery;
    }

    /**
     * Obtiene todos los spots del canal y fecha especificados
     * @autor Jorge Luis Bautista Santiago
     * @param tsStnid
     * @param tsBcstdt
     * @return String
     */
    @Override
    public Integer validateAllSpotByAutoId(String tsStnid, String tsBcstdt) {
        Integer    loFlag = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        ResultSet  loRs = null;
        String     lsQueryParadigm = getQueryValidateAllSpotByAutoId(tsStnid, tsBcstdt);
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                loFlag = loRs.getInt(1);
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
        return loFlag;
    }

    /**
     * Genera instruccion para obtener todos los spots del canal y fecha especificados
     * @autor Jorge Luis Bautista Santiago
     * @param tsStnid
     * @param tsBcstdt
     * @return String
     */
    @Override
    public String getQueryValidateAllSpotByAutoId(String tsStnid, String tsBcstdt) {
        String lsQuery = 
            "SELECT B.SPTMSTID, F.AUTOID\n" + 
            "FROM PARADB.ORDLN A\n" + 
            "JOIN PARADB.SPTMST B ON A.ORDID = B.ORDID AND A.ORDLNID = B.ORDLNID\n" + 
            "JOIN PARADB.ORDCMT D ON A.ORDID = D.ORDID AND A.ORDLNID = B.ORDLNID AND B.ORDID = D.ORDID\n" + 
            "JOIN PARADB.SPTCPY E ON B.SPTMSTID = E.SPTMSTID --AND .SPTMSTID = E.SPTMSTID\n" + 
            "JOIN PARADB.CPYANC F ON E.ADVID = F.ADVID AND E.EXTCPYNUM = F.EXTCPYNUM\n" + 
            "WHERE B.STNID = '" + tsStnid + "' -- PARAMETRO CANAL\n" + 
            "AND B.BCSTDT = '" + tsBcstdt + "' -- PARAMETRO FECHA\n" + 
            "AND B.ACTSTS = 1\n" + 
            "AND A.ORDLNTYP IN (0,2)\n" + 
            "AND B.SPTCHR NOT IN (1,2,5,6,12)\n" + 
            "AND B.USRCHR NOT IN ('$','@','#')\n" + 
            "AND A.SECNUM NOT IN 9\n" + 
            "AND D.CMT = 'MODULOS'";
        return lsQuery;
    }
    
    /**
     * Convierte una cadena con formato en fecha
     * @autor Jorge Luis Bautista Santiago
     * @param tsCadDate
     * @return Date
     */
    public Date convertStringToDate(String tsCadDate){
        Date             ltResponse = null;
        SimpleDateFormat loFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date   ltParsed;
        try {
            ltParsed = loFormat.parse(tsCadDate);
            ltResponse = new java.sql.Date(ltParsed.getTime());
        } catch (ParseException loEx) {
            ;
        }
        return ltResponse;
    }

    /**
     * Valida la existencia del log comercial
     * @autor Jorge Luis Bautista Santiago
     * @param toEvetvLogComercialTab
     * @return ResponseBean
     */
    @Override
    public ResponseBean validateExistLogComercial(EvetvLogComercialTab toEvetvLogComercialTab) {
        ResponseBean              loResponse = new ResponseBean();
        EvetvLogComercialTab      loLogComResBean = getOrdLogComercialByKeyId(toEvetvLogComercialTab);
        if(loLogComResBean != null){
            loResponse.setLsResponse("ERROR");
            loResponse.setLsMessageResponse("Si Existe Al Buscar Por OrderIdEveTv y SPTMSTID_EVETV");
        }else{
            loResponse.setLsResponse("OK");
            loResponse.setLsMessageResponse("No Existe En Base de Datos");
        }
        return loResponse;
    }

    /**
     * Genera instruccion para obtener Log comercial en base al key
     * @autor Jorge Luis Bautista Santiago
     * @param toEvetvLogComercialTab
     * @return EvetvLogComercialTab
     */
    @Override
    public EvetvLogComercialTab getOrdLogComercialByKeyId(EvetvLogComercialTab toEvetvLogComercialTab) {
        Connection              loCnn = new ConnectionAs400().getConnection();
        ResultSet               loRs = null;
        EvetvLogComercialTab    loLcomBean = new EvetvLogComercialTab();
        String                  lsQueryParadigm = getQueryOrdLogComercialByKeyId(toEvetvLogComercialTab);
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                loLcomBean.setLsStnid(loRs.getString("STNID") == null ? null : 
                                          loRs.getString("STNID").trim()); 
                loLcomBean.setLsBcstdt(loRs.getString("BCSTDT") == null ? null : 
                                          loRs.getString("BCSTDT").trim()); 
                loLcomBean.setLsBrkdtid(loRs.getString("BRKDTID") == null ? null : 
                                          loRs.getString("BRKDTID").trim()); 
                loLcomBean.setLsDesccorte(loRs.getString("DESCCORTE") == null ? null : 
                                          loRs.getString("DESCCORTE").trim()); 
                loLcomBean.setLsActtim(loRs.getString("ACTTIM") == null ? null : 
                                          loRs.getString("ACTTIM").trim()); 
                loLcomBean.setLsOverlay(loRs.getString("OVERLAY") == null ? null : 
                                          loRs.getString("OVERLAY").trim()); 
                loLcomBean.setLsAction(loRs.getString("ACTION") == null ? null : 
                                          loRs.getString("ACTION").trim()); 
                loLcomBean.setLsOrdid(loRs.getString("ORDID") == null ? null : 
                                          loRs.getString("ORDID").trim()); 
                loLcomBean.setLsSptmstid(loRs.getString("SPTMSTID") == null ? null : 
                                          loRs.getString("SPTMSTID").trim()); 
                loLcomBean.setLsSptlen(loRs.getString("SPTLEN") == null ? null : 
                                          loRs.getString("SPTLEN").trim()); 
                loLcomBean.setLsSpotformatid(loRs.getString("SPOTFORMATID") == null ? null : 
                                          loRs.getString("SPOTFORMATID").trim()); 
                loLcomBean.setLsSpotcomments(loRs.getString("SPOTCOMMENTS") == null ? null : 
                                          loRs.getString("SPOTCOMMENTS").trim()); 
                loLcomBean.setLsBrnd(loRs.getString("BRND") == null ? null : 
                                          loRs.getString("BRND").trim()); 
                loLcomBean.setLsAutoid(loRs.getString("AUTOID") == null ? null : 
                                          loRs.getString("AUTOID").trim()); 
                loLcomBean.setLsBookingpos(loRs.getString("BOOKINGPOS") == null ? null : 
                                          loRs.getString("BOOKINGPOS").trim()); 
                loLcomBean.setLsWarning(loRs.getString("WARNING") == null ? null : 
                                          loRs.getString("WARNING").trim()); 
                loLcomBean.setLsUntnum(loRs.getString("UNTNUM") == null ? null : 
                                          loRs.getString("UNTNUM").trim()); 
                loLcomBean.setLsSptrt(loRs.getString("SPTRT") == null ? null : 
                                          loRs.getString("SPTRT").trim()); 
               
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
        return loLcomBean;
    }

    /**
     * Genera instruccion para obtener Log comercial en base al key
     * @autor Jorge Luis Bautista Santiago
     * @param toEvetvLogComercialTab
     * @return String
     */
    @Override
    public String getQueryOrdLogComercialByKeyId(EvetvLogComercialTab toEvetvLogComercialTab) {
        String lsQuery = 
            " SELECT STNID,\n" + 
            "        BCSTDT,\n" + 
            "        BRKDTID,\n" + 
            "        DESCCORTE,\n" + 
            "        ACTTIM,\n" + 
            "        OVERLAY,\n" + 
            "        ACTION,\n" + 
            "        ORDID,\n" + 
            "        SPTMSTID,\n" + 
            "        SPTLEN,\n" + 
            "        SPOTFORMATID,\n" + 
            "        SPOTCOMMENTS,\n" + 
            "        BRND,\n" + 
            "        AUTOID,\n" + 
            "        BOOKINGPOS,\n" + 
            "        WARNING,\n" + 
            "        UNTNUM,\n" + 
            "        SPTRT\n" + 
            "   FROM EVENTAS.EVETV_LOG_COMERCIAL\n" + 
            "  WHERE STNID = '" + toEvetvLogComercialTab.getLsStnid() + "'\n" + 
            "    AND BRKDTID = " + toEvetvLogComercialTab.getLsBrkdtid() + "\n" + 
            "    AND SPTMSTID = " + toEvetvLogComercialTab.getLsSptmstid() + "";
        return lsQuery;
    }
    public String getQueryOrdLogComercialByKeyIdCount(EvetvLogComercialTab toEvetvLogComercialTab) {
        String lsQuery = 
            " SELECT COUNT(1)\n" + 
            "   FROM EVENTAS.EVETV_LOG_COMERCIAL\n" + 
            "  WHERE STNID = '" + toEvetvLogComercialTab.getLsStnid() + "'\n" + 
            "    AND BRKDTID = " + toEvetvLogComercialTab.getLsBrkdtid() + "\n" + 
            "    AND SPTMSTID = " + toEvetvLogComercialTab.getLsSptmstid() + "";
        return lsQuery;
    }

    /**
     * Actualiza registro en Log comercial
     * @autor Jorge Luis Bautista Santiago
     * @param toEvetvLogComercialTab
     * @return String
     */
    @Override
    public Integer updateLogComercial(EvetvLogComercialTab toEvetvLogComercialTab) {
        Integer    liResponse = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        String     lsQueryParadigm = getQueryUpdateLogComercial(toEvetvLogComercialTab);
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
     * Genera instruccion para actualizar registro en Log comercial
     * @autor Jorge Luis Bautista Santiago
     * @param toEvetvLogComercialTab
     * @return String
     */
    @Override
    public String getQueryUpdateLogComercial(EvetvLogComercialTab toEvetvLogComercialTab) {
       String lsQuery = 
           " UPDATE EVENTAS.EVETV_LOG_COMERCIAL\n" + 
           "    SET BCSTDT = '" + toEvetvLogComercialTab.getLsBcstdt() + "',\n" + 
           "        DESCCORTE = '" + toEvetvLogComercialTab.getLsDesccorte() + "',\n" + 
           "        ACTTIM = '" + toEvetvLogComercialTab.getLsActtim() + "',\n" + 
           "        OVERLAY = '" + toEvetvLogComercialTab.getLsOverlay() + "',\n" + 
           "        ACTION = '" + toEvetvLogComercialTab.getLsAction() + "',\n" + 
           "        ORDID = " + toEvetvLogComercialTab.getLsOrdid() + ",\n" + 
           "        SPTLEN = '" + toEvetvLogComercialTab.getLsSptlen() + "',\n" + 
           "        SPOTFORMATID = '" + toEvetvLogComercialTab.getLsSpotformatid() + "',\n" + 
           "        SPOTCOMMENTS = '" + toEvetvLogComercialTab.getLsSpotcomments() + "',\n" + 
           "        BRND = '" + toEvetvLogComercialTab.getLsBrnd() + "',\n" + 
           "        AUTOID = '" + toEvetvLogComercialTab.getLsAutoid() + "',\n" + 
           "        BOOKINGPOS = '" + toEvetvLogComercialTab.getLsBookingpos() + "',\n" + 
           "        WARNING = '" + toEvetvLogComercialTab.getLsWarning() + "',\n" + 
           "        UNTNUM = " + toEvetvLogComercialTab.getLsUntnum() + ",\n" + 
           "        SPTRT = '" + toEvetvLogComercialTab.getLsSptrt() + "'     \n" + 
           "  WHERE STNID    = '" + toEvetvLogComercialTab.getLsStnid() + "'\n" + 
           "    AND BRKDTID  = " + toEvetvLogComercialTab.getLsBrkdtid() + "\n" + 
           "    AND SPTMSTID = " + toEvetvLogComercialTab.getLsSptmstid() + "";
       return lsQuery;
    }
    
    /**
     * Valida existencia de registro en Log comercial en base al key
     * @autor Jorge Luis Bautista Santiago
     * @param toEvetvLogComercialTab
     * @return Integer
     */
    public Integer validateExistLogComercialInt(EvetvLogComercialTab toEvetvLogComercialTab) {
        Integer              loResponse = 0;
        loResponse = getOrdLogComercialByKeyIdInt(toEvetvLogComercialTab);
        return loResponse;
    }
    
    /**
     * Genera instruccion para Log comercial en base al key
     * @autor Jorge Luis Bautista Santiago
     * @param toEvetvLogComercialTab
     * @return String 
     */
    public Integer getOrdLogComercialByKeyIdInt(EvetvLogComercialTab toEvetvLogComercialTab) {
        Connection              loCnn = new ConnectionAs400().getConnection();
        ResultSet               loRs = null;
        Integer                 loLcomBean = 0;
        String                  lsQueryParadigm = getQueryOrdLogComercialByKeyIdCount(toEvetvLogComercialTab);
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                loLcomBean = loRs.getInt(1);                                
            }
        } catch (SQLException loExSql) {
            System.out.println("ERROR AL EJECUTAR(INT): ");
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
        return loLcomBean;
    }
    
    /**
     * Genera instruccion para Log comercial en base al key
     * @autor Jorge Luis Bautista Santiago
     * @param toEvetvLogComercialTab
     * @return String 
     */
    public String getQueryOrdLogComercialByKeyIdInt(EvetvLogComercialTab toEvetvLogComercialTab) {
        String lsQuery = 
            " SELECT STNID,\n" + 
            "        BCSTDT,\n" + 
            "        BRKDTID,\n" + 
            "        DESCCORTE,\n" + 
            "        ACTTIM,\n" + 
            "        OVERLAY,\n" + 
            "        ACTION,\n" + 
            "        ORDID,\n" + 
            "        SPTMSTID,\n" + 
            "        SPTLEN,\n" + 
            "        SPOTFORMATID,\n" + 
            "        SPOTCOMMENTS,\n" + 
            "        BRND,\n" + 
            "        AUTOID,\n" + 
            "        BOOKINGPOS,\n" + 
            "        WARNING,\n" + 
            "        UNTNUM,\n" + 
            "        SPTRT\n" + 
            "   FROM EVENTAS.EVETV_LOG_COMERCIAL\n" + 
            "  WHERE STNID = '" + toEvetvLogComercialTab.getLsStnid() + "'\n" + 
            "    AND BCSTDT = " + toEvetvLogComercialTab.getLsBcstdt() + "\n";
        return lsQuery;
    }
    
    /**
     * Actualiza la tabla paradb.SPTMST
     * @autor Jorge Luis Bautista Santiago
     * @param tsStnid
     * @param tsBcstdt
     * @return Integer
     */
    public Integer updateSPTMST(String tsStnid, String tsBcstdt) {
        Integer    liResponse = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        String     lsQueryParadigm = getQueryupdateSPTMST(tsStnid, tsBcstdt);
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
     * Genera instrucicon para obtener instruccion para actualizar tabla paradb.SPTMST
     * @autor Jorge Luis Bautista Santiago
     * @param tsStnid
     * @param tsBcstdt
     * @return String
     */
    public String getQueryupdateSPTMST(String tsStnid, String tsBcstdt){
        String lsQuery = 
            "UPDATE paradb.SPTMST A\n" + 
            "SET A.BRKDTID = (SELECT B.BRKDTID FROM EVENTAS.EVETV_LOG_COMERCIAL B WHERE A.SPTMSTID = B.SPTMSTID),\n" + 
            "A.UNTNUM = (SELECT B.UNTNUM FROM EVENTAS.EVETV_LOG_COMERCIAL B WHERE A.SPTMSTID = B.SPTMSTID), " +
            " A.EXCFL = 0, A.PLOEXCREA = 0\n" + 
            " WHERE A.SPTMSTID IN (SELECT B.SPTMSTID FROM EVENTAS.EVETV_LOG_COMERCIAL B " +
            " WHERE A.SPTMSTID = B.SPTMSTID)\n" + 
            "AND A.STNID = '" + tsStnid + "'\n" + 
            "AND A.BCSTDT = '" + tsBcstdt + "'\n";
        return lsQuery;
    }
    
    /**
     * Actualiza la tabla paradb.SPTCPY
     * @autor Jorge Luis Bautista Santiago
     * @param tsStnid
     * @param tsBcstdt
     * @return Integer
     */
    public Integer updateSPTCPY(String tsStnid, String tsBcstdt) {
        Integer    liResponse = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        String     lsQueryParadigm = getQueryupdateSPTCPY(tsStnid, tsBcstdt);
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
     * Obtiene instruccion para actualizar tabla paradb.SPTCPY
     * @autor Jorge Luis Bautista Santiago
     * @param tsStnid
     * @param tsBcstdt
     * @return String
     */
    public String getQueryupdateSPTCPY(String tsStnid, String tsBcstdt){
        String lsQuery = 
            "UPDATE paradb.SPTCPY A\n" + 
            "   SET A.USRBITS = (SELECT B.SPOTCOMMENTS FROM EVENTAS.EVETV_LOG_COMERCIAL B " +
            " WHERE A.SPTMSTID = B.SPTMSTID AND B.STNID = '" + tsStnid + "'\n" + 
            "   AND B.BCSTDT = '" + tsBcstdt + "' AND SPOTCOMMENTS <> '')\n" + 
            " WHERE A.SPTMSTID IN (SELECT B.SPTMSTID FROM EVENTAS.EVETV_LOG_COMERCIAL B " +
            " WHERE A.SPTMSTID = B.SPTMSTID AND B.STNID = '" + tsStnid + "'\n" + 
            "   AND B.BCSTDT = '" + tsBcstdt + "' AND SPOTCOMMENTS <> '')\n";
        return lsQuery;
    }
    
    /**
     * Obtiene count de los breaks del canal y fecha especificados
     * @autor Jorge Luis Bautista Santiago
     * @param tsStnid
     * @param tsBcstdt
     * @return Integer
     */
    public Integer validateLogComercialProcessed(String tsStnid, String tsBcstdt) {
        Integer    loFlag = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        ResultSet  loRs = null;
        String     lsQueryParadigm = getQueryLogComercialProcessed(tsStnid, tsBcstdt);
        System.out.println(lsQueryParadigm);
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                loFlag = loRs.getInt(1);
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
        return loFlag;
    }

    /**
     * Valida existencia de spot en log comercial del canal y fecha especificados
     * @autor Jorge Luis Bautista Santiago
     * @param tsStnid
     * @param tsBcstdt
     * @return Integer
     */
    public String getQueryLogComercialProcessed(String tsStnid, String tsBcstdt) {
        String lsQuery = "SELECT COUNT(*) \n" + 
        "  FROM EVENTAS.EVETV_LOG_COMERCIAL_PROCESADO \n" + 
        " WHERE STNID   = '" + tsStnid + "'\n" + 
        "   AND BCSTDT  = '" + tsBcstdt + "'\n" + 
        "   AND ESTATUS = 'OK'";
        return lsQuery;
    }
    
    /**
     * Ejecuta procedimiento en base de datos para el encabezado de la orden
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdHeaderModulosTab
     * @return ResponseUpdDao
     */
    public ResponseUpdDao callProcedureCommercialLog(String tsStnid, String tsBcstdt) throws SQLException {
        ResponseUpdDao    loResponseUpdDao = new ResponseUpdDao();
        Connection        loCnn = new ConnectionAs400().getConnection();
        CallableStatement loCallStmt = null;
        System.out.println("Parametros(callProcedureCommercialLog).........");
        System.out.println("tsStnid: ["+tsStnid+"]");
        System.out.println("tsBcstdt: ["+tsBcstdt+"]");
        java.sql.Date     ltDate = getDateYYYYMMDD(tsBcstdt);
        System.out.println("ltDate: ["+ltDate+"]");
        String            lsQueryParadigm = "call EVENTAS.EVETV_LOG_COMERCIAL(?,?)";
        try {
            loCallStmt = loCnn.prepareCall(lsQueryParadigm);
            loCallStmt.setString(1, tsStnid);
            loCallStmt.setDate(2, ltDate);
            loCallStmt.execute();
            loResponseUpdDao.setLsResponse("OK");
            loResponseUpdDao.setLiAffected(0);
            loResponseUpdDao.setLsMessage("Success");
        } catch (SQLException loExSql) {
            System.out.println("ERROR AL EJECUTAR: ");
            System.out.println(lsQueryParadigm);
            loResponseUpdDao.setLsResponse("ERROR");
            loResponseUpdDao.setLiAffected(0);
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
     * Ejecuta procedimiento en base de datos para el encabezado de la orden
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdHeaderModulosTab
     * @return ResponseUpdDao
     */
    public ResponseUpdDao callProcedureValidate(String tsStnid, String tsBcstdt) throws SQLException {
        ResponseUpdDao    loResponseUpdDao = new ResponseUpdDao();
        Connection        loCnn = new ConnectionAs400().getConnection();
        CallableStatement loCallStmt = null;
        //System.out.println("Parametros(callProcedureValidate).........");
        //System.out.println("tsStnid: ["+tsStnid+"]");
        //System.out.println("tsBcstdt: ["+tsBcstdt+"]");
        java.sql.Date     ltDate = getDateYYYYMMDD(tsBcstdt);
        //System.out.println("ltDate: ["+ltDate+"]");
        String            lsQueryParadigm = "call EVENTAS.EVETV_LOG_COMERCIAL_VALIDA(?,?)";
        try {
            loCallStmt = loCnn.prepareCall(lsQueryParadigm);
            loCallStmt.setString(1, tsStnid);
            loCallStmt.setDate(2, ltDate);
            loCallStmt.execute();
            loResponseUpdDao.setLsResponse("OK");
            loResponseUpdDao.setLiAffected(0);
            loResponseUpdDao.setLsMessage("Success");
        } catch (SQLException loExSql) {
            System.out.println("ERROR AL EJECUTAR: ");
            System.out.println(lsQueryParadigm);
            loResponseUpdDao.setLsResponse("ERROR");
            loResponseUpdDao.setLiAffected(0);
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
     * Convierte una cadena en formato fecha a una fecha real sql con ese mismo formato
     * @autor Jorge Luis Bautista Santiago
     * @param lsDateStr
     * @return java.sql.Date
     */
    private java.sql.Date getDateYYYYMMDD(String lsDateStr){
        SimpleDateFormat loFormatText = new SimpleDateFormat("yyyy-MM-dd");
        String           lsStrDate = lsDateStr;
        java.util.Date             ltDatePivot = null;
        try {
            ltDatePivot = loFormatText.parse(lsStrDate);
        } catch (ParseException loEx) {
            System.out.println("ERROR al PARSEAR (getDateYYYYMMDD)");
            loEx.printStackTrace();
        }
        java.sql.Date ltDateResponse = new java.sql.Date(ltDatePivot.getTime());
        return ltDateResponse;
    }
    
    /**
     * Obtiene registro de la tabla de log comercial procesado mediante el canal y la fecha
     * @autor Jorge Luis Bautista Santiago
     * @param tsStnid
     * @param tsBcstdt
     * @return EvetvLogComercialProcesadoBean
     */
    public EvetvLogComercialProcesadoBean getLogComercialProcesado(String tsStnid, String tsBcstdt){
        EvetvLogComercialProcesadoBean loEvetvLogComercialProcesadoBean = new EvetvLogComercialProcesadoBean();
        Connection              loCnn = new ConnectionAs400().getConnection();
        ResultSet               loRs = null;
        String                  lsQueryParadigm = getQueryLogComercialProcesado(tsStnid, tsBcstdt);
        //System.out.println(lsQueryParadigm);
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                loEvetvLogComercialProcesadoBean.setLsStnid(loRs.getString("STNID") == null ? null : 
                                                            loRs.getString("STNID").trim());  
                loEvetvLogComercialProcesadoBean.setLsBcstdt(loRs.getString("BCSTDT") == null ? null : 
                                                            loRs.getString("BCSTDT").trim()); 
                loEvetvLogComercialProcesadoBean.setLsEstatus(loRs.getString("ESTATUS") == null ? null : 
                                                            loRs.getString("ESTATUS").trim()); 
                loEvetvLogComercialProcesadoBean.setLsMensaje(loRs.getString("MENSAJE") == null ? null : 
                                                            loRs.getString("MENSAJE").trim()); 
               
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
        
        return loEvetvLogComercialProcesadoBean;
    }
    
    /**
     * Genera intruccion para obtener registro de la tabla de log comercial procesado mediante el canal y la fecha
     * @autor Jorge Luis Bautista Santiago
     * @param tsStnid
     * @param tsBcstdt
     * @return String
     */
    private String getQueryLogComercialProcesado(String tsStnid, String tsBcstdt){
        String lsQuery = "SELECT STNID,\n" + 
        "       BCSTDT,\n" + 
        "       ESTATUS,\n" + 
        "       MENSAJE\n" + 
        "  FROM EVENTAS.EVETV_LOG_COMERCIAL_PROCESADO\n" + 
        " WHERE STNID  = '" + tsStnid + "'\n" + 
        "   AND BCSTDT = '" + tsBcstdt + "'";
        
        return lsQuery;
    }
    
    /**
     * Revisa si existe un KO despues de la validacion
     * @autor Jorge Luis Bautista Santiago
     * @param tsStnid
     * @param tsBcstdt
     * @return Integer
     */
    public Integer validateKoLogCommercial(String tsStnid, String tsBcstdt) {
        //tsBcstdt formato YYYY-MM-DD
        Integer    loFlag = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        ResultSet  loRs = null;
        String     lsQueryParadigm = 
            "SELECT COUNT(1) \n" + 
            "  FROM EVENTAS.EVETV_LOG_COMERCIAL_STATUS \n" + 
            " WHERE STATUS = 'KO'\n" + 
            "   AND STNID = '"+tsStnid+"'\n" + 
            "   AND BCSTDT = '"+tsBcstdt+"'";
        System.out.println(lsQueryParadigm);
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                loFlag = loRs.getInt(1);
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
        return loFlag;
    }
    
    /**
     * Genera instruccion para obtener Log comercial en base al key
     * @autor Jorge Luis Bautista Santiago
     * @param toEvetvLogComercialTab
     * @return EvetvLogComercialTab
     */
    public List<EvetvLogComercialStatusTab> getLogComercialStatus(String tsStnid, String tsDateReal) {
        List<EvetvLogComercialStatusTab> laList =
            new ArrayList<EvetvLogComercialStatusTab>();
        
        Connection              loCnn = new ConnectionAs400().getConnection();
        ResultSet               loRs = null;        
        String                  lsQueryParadigm = 
            "SELECT IDPROCESO,\n" + 
            "       STNID,\n" + 
            "       BCSTDT,\n" + 
            "       TIPO,\n" + 
            "       MENSAJE,\n" + 
            "       STATUS\n" + 
            "  FROM EVENTAS.EVETV_LOG_COMERCIAL_STATUS\n" + 
            " WHERE STNID  = '"+tsStnid+"'\n" + 
            "   AND BCSTDT = '"+tsDateReal+"'";
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                EvetvLogComercialStatusTab    loLcomBean = new EvetvLogComercialStatusTab();
                loLcomBean.setLsIdProceso(loRs.getString("IDPROCESO") == null ? null : 
                                          loRs.getString("IDPROCESO").trim()); 
                loLcomBean.setLsStnid(loRs.getString("STNID") == null ? null : 
                                          loRs.getString("STNID").trim()); 
                loLcomBean.setLsBcstdt(loRs.getString("BCSTDT") == null ? null : 
                                          loRs.getString("BCSTDT").trim()); 
                loLcomBean.setLsTipo(loRs.getString("TIPO") == null ? null : 
                                          loRs.getString("TIPO").trim()); 
                loLcomBean.setLsMensaje(loRs.getString("MENSAJE") == null ? null : 
                                          loRs.getString("MENSAJE").trim()); 
                loLcomBean.setLsStatus(loRs.getString("STATUS") == null ? null : 
                                          loRs.getString("STATUS").trim()); 
                laList.add(loLcomBean);
               
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
        return laList;
    }

    /**
     * Ejecuta procedimiento en base de datos para el eliminar registros insertados en 
     * la validacion
     * @autor Jorge Luis Bautista Santiago
     * @param tsStnid
     * @param tsBcstdt
     * @return ResponseUpdDao
     */
    public ResponseUpdDao rollbackValidateLogComercial(String tsStnid, String tsBcstdt) throws SQLException {
        ResponseUpdDao    loResponseUpdDao = new ResponseUpdDao();
        Connection        loCnn = new ConnectionAs400().getConnection();
        CallableStatement loCallStmt = null;
        //System.out.println("Parametros(callProcedureCommercialLog).........");
        //System.out.println("tsStnid: ["+tsStnid+"]");
        //System.out.println("tsBcstdt: ["+tsBcstdt+"]");
        java.sql.Date     ltDate = getDateYYYYMMDD(tsBcstdt);
        //System.out.println("ltDate: ["+ltDate+"]");
        String            lsQueryParadigm = "call EVENTAS.EVETV_LOG_COMERCIAL_BORRA(?,?)";
        try {
            loCallStmt = loCnn.prepareCall(lsQueryParadigm);
            loCallStmt.setString(1, tsStnid);
            loCallStmt.setDate(2, ltDate);
            loCallStmt.execute();
            loResponseUpdDao.setLsResponse("OK");
            loResponseUpdDao.setLiAffected(0);
            loResponseUpdDao.setLsMessage("Success");
        } catch (SQLException loExSql) {
            System.out.println("ERROR AL EJECUTAR: ");
            System.out.println(lsQueryParadigm);
            loResponseUpdDao.setLsResponse("ERROR");
            loResponseUpdDao.setLiAffected(0);
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
     * Inserta en tabla auxiliar Lista de breaks a validar
     * @autor Jorge Luis Bautista Santiago
     * @param toEvetvLogComercialTab
     * @return Integer
     */
    public Integer insertListBreaks(String tsStnid, 
                                    String tsBcstdt,
                                    ActiveBreak toActiveBreak) {
        Integer    liResponse = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        String     lsQueryParadigm = getQueryInsertActiveBreak(tsStnid, tsBcstdt, toActiveBreak);
        try {
            Statement loStmt = loCnn.createStatement();
            liResponse = loStmt.executeUpdate(lsQueryParadigm);
            //System.out.println(" Dao insertListBreaks OK ");
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
    
    public String getQueryInsertActiveBreak(String tsStnid, 
                                            String tsBcstdt,
                                            ActiveBreak toActiveBreak){
        String lsQuery = "";
        String lsStnid =  tsStnid;
        String lsBcstdt =  tsBcstdt;
        String lsBreaktid = toActiveBreak.getBreakID()  == null ? "null" : toActiveBreak.getBreakID() ;
        String lsDescription =  toActiveBreak.getDescription()  == null ? "null" : toActiveBreak.getDescription() ;
        String lsFechaBreak =  toActiveBreak.getDate()  == null ? "null" : toActiveBreak.getDate() ;
        String lsHour =  toActiveBreak.getHour()  == null ? "null" : toActiveBreak.getHour() ;
        String lsTotalDuration =  toActiveBreak.getTotalDuration()  == null ? "null" : toActiveBreak.getTotalDuration() ;
        String lsCommercialDuration =  toActiveBreak.getComercialDuration()  == null ? "null" : toActiveBreak.getComercialDuration() ;
        String lsTypeBreak =  toActiveBreak.getTypeBreak()  == null ? "null" : toActiveBreak.getTypeBreak() ;
        
        if(lsStnid.trim().equalsIgnoreCase("")){  lsStnid = "null"; }
        if(lsBcstdt.trim().equalsIgnoreCase("")){  lsBcstdt = "null"; }
        if(lsBreaktid.trim().equalsIgnoreCase("")){  lsBreaktid = "null"; }
        if(lsDescription.trim().equalsIgnoreCase("")){  lsDescription = "null"; }
        if(lsFechaBreak.trim().equalsIgnoreCase("")){  lsFechaBreak = "null"; }
        if(lsHour.trim().equalsIgnoreCase("")){  lsHour = "null"; }
        if(lsTotalDuration.trim().equalsIgnoreCase("")){  lsTotalDuration = "null"; }
        if(lsCommercialDuration.trim().equalsIgnoreCase("")){  lsCommercialDuration = "null"; }
        if(lsTypeBreak.trim().equalsIgnoreCase("")){  lsTypeBreak = "null"; }
        // Convertir formato de fecha_break
        if(!lsFechaBreak.equalsIgnoreCase("null")){
            lsFechaBreak = buildDateYYYYMMDD(lsFechaBreak);
        }
        
        lsQuery = "INSERT INTO EVENTAS.EVETV_LISTA_BREAKS(STNID,\n" + 
        "                                        BCSTDT,\n" + 
        "                                        BREAKID,\n" + 
        "                                        DESCRIPTION,\n" + 
        "                                        FECHA_BREAK,\n" + 
        "                                        HOUR,\n" + 
        "                                        TOTALDURATION,\n" + 
        "                                        COMMERCIALDURATION,\n" + 
        "                                        TYPEBREAK\n" + 
        "                                       )\n" + 
        "                   VALUES ('" + lsStnid + "',\n";
        if(lsBcstdt.equalsIgnoreCase("null")){lsQuery += "null,\n";}else{lsQuery+="'" + lsBcstdt + "',\n";}
        if(lsBreaktid.equalsIgnoreCase("null")){lsQuery += "null,\n";}else{lsQuery+="'" + lsBreaktid + "',\n";}
        if(lsDescription.equalsIgnoreCase("null")){lsQuery += "null,\n";}else{lsQuery+="'" + lsDescription + "',\n";}
        if(lsFechaBreak.equalsIgnoreCase("null")){lsQuery += "null,\n";}else{lsQuery+="'" + lsFechaBreak + "',\n";}
        if(lsHour.equalsIgnoreCase("null")){lsQuery += "null,\n";}else{lsQuery+="'" + lsHour + "',\n";}
        if(lsTotalDuration.equalsIgnoreCase("null")){lsQuery += "null,\n";}else{lsQuery+="'" + lsTotalDuration + "',\n";}
        if(lsCommercialDuration.equalsIgnoreCase("null")){lsQuery += "null,\n";}else{lsQuery+="'" + lsCommercialDuration + "',\n";}
        if(lsTypeBreak.equalsIgnoreCase("null")){lsQuery += "null\n";}else{lsQuery+="'" + lsTypeBreak + "'\n";}
        lsQuery +=  "                        )";
        
        return lsQuery;
    }
    
    /**
     * Construye una cadena con el formato de fecha solicitado
     * @autor Jorge Luis Bautista Santiago
     * @param tsDate
     * @return String
     */
    private String buildDateYYYYMMDD(String tsDate){
        String   lsRes = "";
        String[] laDate = tsDate.split("/");
        lsRes = laDate[2] + "-" + laDate[1] + "-" + laDate[0];
        return lsRes;
    }
    
    /**
     * Obtiene mensaje de validacion en ok
     * @autor Jorge Luis Bautista Santiago
     * @param tsStnid
     * @param tsDate
     * @return String
     */
    public String getMessageValidationOk(String tsStnid, String tsDate){
        String   lsRes = "Enviar VALIDATE en N, Solicitud Sin Incidencias";
        //Validar que exista
        Integer    loFlag = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        ResultSet  loRs = null;
        String     lsQueryParadigm = getValidateMessageOk(tsStnid, tsDate);
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                loFlag = loRs.getInt(1);
            }
            if(loFlag > 0){
                Connection              loCnnSt = new ConnectionAs400().getConnection();
                ResultSet               loRsSt = null;
                String                  lsQueryParadigmSt 
                    = getQueryMessageOk(tsStnid, tsDate);
                try {
                    Statement loStmtSt = loCnnSt.createStatement();
                    loRsSt = loStmtSt.executeQuery(lsQueryParadigmSt);  
                    while(loRsSt.next()){
                        lsRes = 
                            loRsSt.getString("MENSAJE") == null ? "Enviar VALIDATE en N, Solicitud Sin Incidencias" : 
                            loRsSt.getString("MENSAJE").trim();
                       
                    }
                } catch (SQLException loExSqlSt) {
                    System.out.println("ERROR AL EJECUTAR: ");
                    System.out.println(lsQueryParadigmSt);
                    loExSqlSt.printStackTrace();
                }
                finally{
                    try {
                        loCnnSt.close();
                        loRsSt.close();
                    } catch (SQLException loExSt) {
                        loExSt.printStackTrace();
                    }
                }    
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
        return lsRes;
    }
    
    private String getValidateMessageOk(String tsStnid, String tsDate){
        String lsReturn = 
        "SELECT COUNT(1)\n" + 
        "  FROM EVENTAS.EVETV_LOG_COMERCIAL_STATUS \n" + 
        " WHERE STATUS = 'OK' \n" + 
        "   AND STNID = '" + tsStnid + "'\n" + 
        "   AND BCSTDT = '" + tsDate + "'";
        
        return lsReturn;
    }
    
    private String getQueryMessageOk(String tsStnid, String tsDate){
        String lsReturn = 
        "SELECT IDPROCESO,\n" + 
        "       STNID,\n" + 
        "       BCSTDT,\n" + 
        "       TIPO,\n" + 
        "       MENSAJE,\n" + 
        "       STATUS \n" + 
        "  FROM EVENTAS.EVETV_LOG_COMERCIAL_STATUS \n" + 
        " WHERE STATUS = 'OK' \n" + 
        "   AND STNID = '" + tsStnid + "'\n" + 
        "   AND BCSTDT = '" + tsDate + "'";
        
        return lsReturn;
    }
    
}
