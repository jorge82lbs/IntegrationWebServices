/**
* Project: Integraton Paradigm - EveTV
*
* File: IntegrationBsLogComercial.java
*
* Created on: Septiembre 23, 2017 at 11:00
*
* Copyright (c) - OMW - 2017
*/

package com.televisa.comer.integration.service.implementation;

import com.televisa.comer.integration.model.beans.ResponseBean;
import com.televisa.comer.integration.model.beans.ResponseUpdDao;
import com.televisa.comer.integration.model.beans.pgm.EvetvIntServiceBitacoraTab;
import com.televisa.comer.integration.model.beans.pgm.EvetvLogComercialProcesadoBean;
import com.televisa.comer.integration.model.beans.pgm.EvetvLogComercialStatusTab;
import com.televisa.comer.integration.model.beans.pgm.EvetvLogComercialTab;
import com.televisa.comer.integration.model.daos.EntityMappedDao;
import com.televisa.comer.integration.model.daos.LogComercialDao;
import com.televisa.comer.integration.service.beans.pgm.commerciallog.ActiveBreak;
import com.televisa.comer.integration.service.beans.pgm.commerciallog.Break;
import com.televisa.comer.integration.service.beans.pgm.commerciallog.Breaks;
import com.televisa.comer.integration.service.beans.pgm.commerciallog.CodRespuesta;
import com.televisa.comer.integration.service.beans.pgm.commerciallog.CommercialList;
import com.televisa.comer.integration.service.beans.pgm.commerciallog.Detalle;
import com.televisa.comer.integration.service.beans.pgm.commerciallog.Encabezado;
import com.televisa.comer.integration.service.beans.pgm.commerciallog.ItemCabecera;
import com.televisa.comer.integration.service.beans.pgm.commerciallog.ItemRespuesta;
import com.televisa.comer.integration.service.beans.pgm.commerciallog.ItemRespuestaValidate;
import com.televisa.comer.integration.service.beans.pgm.commerciallog.ListaMensaje;
import com.televisa.comer.integration.service.beans.pgm.commerciallog.RecibirDatosExternosResponse;
import com.televisa.comer.integration.service.beans.pgm.commerciallog.RecibirDatosExternosResult;
import com.televisa.comer.integration.service.beans.pgm.commerciallog.Spot;
import com.televisa.comer.integration.service.beans.pgm.commerciallog.SpotList;
import com.televisa.comer.integration.service.beans.pgm.commerciallog.ListBreaks;

import com.televisa.comer.integration.service.beans.types.EmailDestinationAddress;
import com.televisa.comer.integration.service.beans.types.EvetvIntConfigParamTabBean;
import com.televisa.comer.integration.service.beans.types.EvetvIntServicesLogBean;
import com.televisa.comer.integration.service.email.MailManagement;
import com.televisa.comer.integration.service.interfaces.LogComercialInterface;
import com.televisa.comer.integration.service.utils.UtilsIntegrationService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import java.sql.SQLException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import javax.xml.bind.JAXB;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

/** Servicio Web expuesto para Log Comercial
 * en la capa de servicio
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Septiembre 23, 2017, 12:00 pm
 */
@WebService
public class IntegrationBsLogComercial implements LogComercialInterface{
//public class IntegrationBsLogComercial implements SOAPHandler<SOAPMessageContext>{

    @Resource 
    private WebServiceContext loWsc;
    
    /**
     * Recibe Log Comercial de neptuno y procesa en paradigm dicho log
     * @autor Jorge Luis Bautista Santiago
     * @param toCommercialList
     * @return RecibirDatosExternosResponse
     */
    @Override
    @WebMethod
    public RecibirDatosExternosResponse enviarLogComercial(@WebParam(name = "commercialList") 
                                                           CommercialList toCommercialList) {
        RecibirDatosExternosResponse loResponse = new RecibirDatosExternosResponse();
        //################################################
        boolean                      lbSecurityFalg = false;
        boolean                      lbInitFalg = true;
        String                       lsEmailMsgErr = "";
        String                       lsParadigmOrderID = "";
        boolean                      lbFlagSendMail = true;
        Integer                      liCountInsert = 0;
        EntityMappedDao              loEntityMappedDao = new EntityMappedDao();
        String                       lsNomFile = "LogComercial-" + getId() + ".xml";
        String                       lsIndFileType = "REQUEST";
        String                       lsIndServiceType = "WsLogComercial";
        String                       lsIndEstatus = "A";
        String                       lsNomUserName = "neptuno";
        Integer                      liIdRequest = loEntityMappedDao.getMaxIdParadigm("RstRequest") + 1; 
        String                       lsIdService = loEntityMappedDao.getWsParadigmOrigin("WsLogComercial");
        UtilsIntegrationService      loUtIn = new UtilsIntegrationService();
        CodRespuesta                 loCodRespuesta = new CodRespuesta();
        EvetvIntConfigParamTabBean   loError = new EvetvIntConfigParamTabBean();
        String                       lsFieldValidate = null;
        String                       lsFieldParent = null;
        boolean                      lbProcessParadigm = true;        
        String                       lsStnid = "";
        String                       lsBcstdt = "";  
        String                       lsStnidValidate = "";
        String                       lsBcstdtValidate = "";  
        String                       lsValidate = "";
        String                       lsValCte = "null";
        List<EvetvLogComercialTab>   laLogsComercialTab = new ArrayList<EvetvLogComercialTab>();                       
        
        try{
            ByteArrayOutputStream loBaos = new ByteArrayOutputStream();
            JAXB.marshal(toCommercialList, loBaos);
            InputStream loFileXml = new ByteArrayInputStream(loBaos.toByteArray()); 
            
            loEntityMappedDao.insertEvetvIntXmlFilesTab(liIdRequest, 
                                                        Integer.parseInt(lsIdService), 
                                                        lsNomFile, 
                                                        lsIndFileType, 
                                                        lsIndServiceType, 
                                                        lsIndEstatus, 
                                                        lsNomUserName, 
                                                        loFileXml
                                                       );
        }catch(Exception loEx){
            System.out.println("Archivo Request no guardado");
        }
        //################################################
        loEntityMappedDao.insertLogServicesRequest(liIdRequest, 
                                                   Integer.parseInt(lsIdService), 
                                                   "WsLogComercial", 
                                                   "neptuno"
                                                   );
        EvetvIntServicesLogBean loEvetvIntServicesLogBean = new EvetvIntServicesLogBean();
        loEvetvIntServicesLogBean.setLiIdLogServices(liIdRequest);
        loEvetvIntServicesLogBean.setLiIdService(Integer.parseInt(lsIdService));
        loEvetvIntServicesLogBean.setLiIndProcess(0);
        loEvetvIntServicesLogBean.setLsIndResponse("N");
        loEvetvIntServicesLogBean.setLsIndEstatus("A");
        loEvetvIntServicesLogBean.setLsAttribute9("WsLogComercial");
        loEvetvIntServicesLogBean.setLsAttribute10("Execution");
        loEvetvIntServicesLogBean.setLsAttribute15("neptuno");
        loEntityMappedDao.insertServicesLogWs(loEvetvIntServicesLogBean);
// Validar configuracion de Seguridad para este servicio
        String lsSecurity = 
            loEntityMappedDao.getSecurityService(lsIdService) == null ? "NO": 
            loEntityMappedDao.getSecurityService(lsIdService);
        
        if(!lsSecurity.equalsIgnoreCase("NO")){
            lbSecurityFalg = true;
        }
        
        if(lbSecurityFalg){
            String lsResponse = null;
            MessageContext mc = loWsc.getMessageContext();
            Map requestHeader = (Map)mc.get(MessageContext.HTTP_REQUEST_HEADERS);
            if(requestHeader.get("Username") != null && requestHeader.get("Password") != null){
                List userList = (List) requestHeader.get("Username");
                List passwordList = (List) requestHeader.get("Password");
                if(userList != null && passwordList != null){
                    String lsUserName = userList.get(0) == null ? null : userList.get(0).toString();
                    String lsPassword = passwordList.get(0) == null ? null : passwordList.get(0).toString();
                    lsResponse = "zero kills"; 
                    //Obtener usuario y contrase�a de la bd
                    String lsUsernameBd = 
                        loEntityMappedDao.getGeneralParameter("UsrNeptuno", "AUTHENTICATION");
                    String lsPasswordBd = 
                        loEntityMappedDao.getGeneralParameter("PswNeptuno", "AUTHENTICATION");
                    if(lsUsernameBd.equals(lsUserName) && lsPasswordBd.equals(lsPassword) ){
                        lbInitFalg = true;
                    }else{
                        lbInitFalg = false;
                    }
                    
                }else{
                    lsResponse = "Campos Obligatorios";
                    lbInitFalg = false;
                }
            }else{
                lbInitFalg = false;
            }
        }
// FIN de Validar configuracion de Seguridad para este servicio        
        
        String lsIndProcessR = 
            loEntityMappedDao.getGeneralParameterID("serviceRequest", "PROCESS_INTEGRATION");
        EvetvIntServiceBitacoraTab loEvetvIntServiceBitacoraTabR = new EvetvIntServiceBitacoraTab();
        loEvetvIntServiceBitacoraTabR.setLsIdLogServices(lsIdService);
        loEvetvIntServiceBitacoraTabR.setLsIdService(lsIdService);
        loEvetvIntServiceBitacoraTabR.setLsIndProcess(lsIndProcessR); //Tipo de Proceso
        loEvetvIntServiceBitacoraTabR.setLsNumEvtbProcessId("0");
        loEvetvIntServiceBitacoraTabR.setLsNumPgmProcessId("0");
        
        loEvetvIntServiceBitacoraTabR.setLsIndEvento("Solicitud Recibida para Servicio de Log Commercial");
        loEntityMappedDao.insertBitacoraWs(loEvetvIntServiceBitacoraTabR);
        
        //################################################
        if(lbInitFalg){
        
            boolean  lbChannelSize = loUtIn.validateLength(toCommercialList.getChannelID(),64);
            boolean  lbChannelReq = loUtIn.isRequired(toCommercialList.getChannelID(),"S");
            boolean  lbDateSize = loUtIn.validateLength(toCommercialList.getDate(),10);
            boolean  lbDateReq = loUtIn.isRequired(toCommercialList.getDate(),"S");
            boolean  lbDateFormat = loUtIn.isDateNeptuno(toCommercialList.getDate()); 
            
            boolean  lbValidateSize = loUtIn.validateLength(toCommercialList.getValidate(),1);
            boolean  lbValidateFormat = loUtIn.isInSN(toCommercialList.getValidate()); 
            
            if(lbChannelSize && lbChannelReq &&
            lbDateSize && lbDateReq && lbDateFormat &&
            lbValidateSize && lbValidateFormat){
                
                String                     lsChannel = toCommercialList.getChannelID();
                lsStnid = lsChannel;
                lsStnidValidate = lsStnid;
                String                     lsDate = buildDateEveTv(toCommercialList.getDate(),"dd/MM/YYYY");
                lsBcstdt = lsDate;        
                lsBcstdtValidate = lsBcstdt;
                Breaks                     loBreaks = toCommercialList.getBreaks();
                List<Break>                laBreaks = loBreaks.getBreak();
                lsValidate = 
                    toCommercialList.getValidate() == null ? "" : 
                    toCommercialList.getValidate();
                if(lsValidate.trim().equalsIgnoreCase("")){
                    lsValidate = "N";
                }
                if(lsValidate.equalsIgnoreCase("S")){
                    lsValCte = "S";                    
                }else{
                    lsValCte = "N";                    
                }
                System.out.println("Validacion["+lsValidate+"]");
                //Si lbValidate es FALSE siginifica que al menos existe un error
                try{
                    //Eliminar registros insertados
                    String lsDateReal = buildDateYYYYMMDD(lsBcstdt);    
                    System.out.println("rollbackValidation("+lsStnid+")("+lsDateReal+")("+lsIdService+")");
                    rollbackValidation(lsStnid, lsDateReal, lsIdService, "lsType");
                }catch(Exception loExp){
                    System.out.println("Error en rollbackValidation: "+loExp.getMessage()+"");
                }
                //########## Validar en tabla EVENTAS.EVETV_LOG_COMERCIAL_PROCESADO 
                LogComercialDao loLogComercialDao = new LogComercialDao();
                String lsDateReal = buildDateYYYYMMDD(lsBcstdt);
                lsBcstdtValidate = lsDateReal;
                System.out.println("Validacion de log comercial["+lsStnid+"]["+lsDateReal+"]");
                Integer liLogComm = 
                    loLogComercialDao.validateLogComercialProcessed(lsStnid,lsDateReal); //Fecha YYYY-MM-dd
                
                if(liLogComm == 0){
                    System.out.println("PRoceso continua, valor cero");
                    ItemCabecera loItemCabecera = new ItemCabecera();
                    for(Break loBreak : laBreaks){
                        
                        boolean lbBreakIDSize = true;
                        boolean lbBreakIDReq = true;
                        
                        boolean lbBreakDescSize = true;
                        boolean lbBreakDescReq = true;
                        
                        boolean lbBreakHourSize = true;
                        boolean lbBreakHourReq = true;
                        //boolean lbBreakHourFormat = loUtIn.isFormatSchedule(loBreak.getBreakHour());
                        boolean lbBreakHourFormat = true;
                        
                        boolean lbBreakOverSize = true;
                        boolean lbBreakOverReq = true;
                        boolean lbBreakOverVal = true;
                        
                        if(!lsValidate.equalsIgnoreCase("S")){
                            lbBreakIDSize = loUtIn.validateLength(loBreak.getBreakID(),64);
                            lbBreakIDReq = loUtIn.isRequired(loBreak.getBreakID(),"S");
                            
                            lbBreakDescSize = loUtIn.validateLength(loBreak.getBreakDescription(),128);
                            lbBreakDescReq = loUtIn.isRequired(loBreak.getBreakDescription(),"S");
                            
                            lbBreakHourSize = loUtIn.validateLength(loBreak.getBreakHour(),8);
                            lbBreakHourReq = loUtIn.isRequired(loBreak.getBreakHour(),"S");
                            //lbBreakHourFormat = loUtIn.isFormatSchedule(loBreak.getBreakHour());
                            lbBreakHourFormat = true;
                            
                            lbBreakOverSize = loUtIn.validateLength(loBreak.getBreakOverlay(),1);
                            lbBreakOverReq = loUtIn.isRequired(loBreak.getBreakOverlay(),"S");
                            lbBreakOverVal = loUtIn.isInSN(loBreak.getBreakOverlay());
                        }
                        
                        
                        if(lbBreakIDSize && lbBreakIDReq && lbBreakDescSize && lbBreakDescReq && 
                           lbBreakHourSize && lbBreakHourReq && lbBreakHourFormat && lbBreakOverSize &&
                           lbBreakOverReq && lbBreakOverVal
                         ){
                            
                            String lsBreakID = loBreak.getBreakID();
                            String lsBreakDescripcion = loBreak.getBreakDescription();
                            String lsBreakHour = loBreak.getBreakHour();
                            String lsBreakOverlay = loBreak.getBreakOverlay();
                            SpotList loSpotList = loBreak.getSpotList();                           
                            List<Spot> laSpots = loSpotList.getSpot();
                            int liCount = 1;
                            
                            for(Spot loSpot : laSpots){
                                boolean lbAction = true;
                                boolean lbActionSize = true;
                                boolean lbActionReq = true;
                                boolean lbParadigmOrderIDSize = true;
                                boolean lbParadigmSpotIDSize = true;
                                boolean lbDurationSize = true;
                                boolean lbDurationFormat = true;
                                boolean lbSpotFormatIDSize = true;
                                boolean lbSpotFormatIDReq = true;
                                boolean lbSpotCommentsSize = true;
                                boolean lbProductIDSize = true;
                                boolean lbProductIDReq = true;
                                boolean lbMediaIDSize = true;
                                boolean lbMediaIDReq = true;
                                boolean lbBookingPosSize = true;
                                boolean lbWarningSize = true;
                                boolean lbNumOrdenData = true;
                                boolean lbSpotPriceFotmat = true;

                                if(!lsValidate.equalsIgnoreCase("S")){
                                     lbAction = loUtIn.isInIUD(loSpot.getAction());  
                                     lbActionSize = loUtIn.validateLength(loSpot.getAction(),1);
                                     lbActionReq = loUtIn.isRequired(loSpot.getAction(),"S");                        
                                    
                                     lbParadigmOrderIDSize = loUtIn.validateLength(loSpot.getParadigmOrderID(),64);
                                     lbParadigmSpotIDSize = loUtIn.validateLength(loSpot.getParadigmSpotID(),64);
                                    
                                     lbDurationSize = loUtIn.validateLength(loSpot.getDuration(),8);
                                     lbDurationFormat = loUtIn.isFormatSchedule(loSpot.getDuration());
                                    
                                     lbSpotFormatIDSize = loUtIn.validateLength(loSpot.getSpotFormatID(),64);
                                     lbSpotFormatIDReq = loUtIn.isRequired(loSpot.getSpotFormatID(),"S");    
                                    
                                     lbSpotCommentsSize = loUtIn.validateLength(loSpot.getSpotComments(),256);
                                    
                                     lbProductIDSize = loUtIn.validateLength(loSpot.getProductID(),64);
                                     lbProductIDReq = loUtIn.isRequired(loSpot.getProductID(),"S");    
                                    
                                     lbMediaIDSize = loUtIn.validateLength(loSpot.getMediaID(),64);
                                     lbMediaIDReq = loUtIn.isRequired(loSpot.getMediaID(),"S");    
                                
                                    if(loSpot.getBookingPos() != null){
                                        lbBookingPosSize = loUtIn.validateLength(loSpot.getBookingPos(),2);
                                    }
                                    if(loSpot.getWarning() != null){
                                        lbWarningSize = loUtIn.validateLength(loSpot.getWarning(),1);
                                    }
                                    if(loSpot.getNumOrden() != null){
                                        lbNumOrdenData = loUtIn.isNumeric(loSpot.getNumOrden());
                                    }
                                    if(loSpot.getSpotPrice() != null){
                                        lbSpotPriceFotmat = loUtIn.isPriceFormat(loSpot.getSpotPrice());
                                    }
                                }
                                
                                if(lbAction && lbActionSize && lbActionReq && 
                                    lbParadigmOrderIDSize && lbParadigmSpotIDSize && 
                                    lbDurationSize && lbDurationFormat && 
                                    lbSpotFormatIDSize && lbSpotFormatIDReq && 
                                    lbSpotCommentsSize && lbProductIDSize && 
                                    lbProductIDReq && lbMediaIDSize && lbMediaIDReq && 
                                    lbBookingPosSize && lbWarningSize && 
                                    lbNumOrdenData && lbSpotPriceFotmat){
                                
                                    String lsAction = loSpot.getAction();
                                    lsParadigmOrderID = loSpot.getParadigmOrderID();
                                    String lsParadigmSpotID = 
                                        loSpot.getParadigmSpotID() == null ? "":loSpot.getParadigmSpotID();
                                    if(lsParadigmSpotID.length()<1){
                                        lsParadigmSpotID = "0";
                                    }
                                    String lsDuration = loSpot.getDuration();
                                    String lsSpotFormatID = loSpot.getSpotFormatID();
                                    String lsSpotComments = loSpot.getSpotComments();
                                    String lsMediaID = loSpot.getMediaID();
                                    String lsProductID = loSpot.getProductID();
                                    String lsBookingPos = loSpot.getBookingPos();
                                    String lsWarning = loSpot.getWarning();
                                    String lsNumOrden = loSpot.getNumOrden();
                                    String lsASpotPrice = loSpot.getNumOrden();
                                    
                                    //############# Insertar en tabla de EVENTAS.EVETV_LOG_COMERCIAL  ######/
                                    EvetvLogComercialTab loEvetvLogComercialTab = new EvetvLogComercialTab();
                                    loEvetvLogComercialTab.setLsStnid(lsChannel);
                                    loEvetvLogComercialTab.setLsBcstdt(lsDateReal);
                                    loEvetvLogComercialTab.setLsBrkdtid(lsBreakID);
                                    loEvetvLogComercialTab.setLsDesccorte(lsBreakDescripcion);
                                    loEvetvLogComercialTab.setLsActtim(lsBreakHour);
                                    loEvetvLogComercialTab.setLsOverlay(lsBreakOverlay);
                                    loEvetvLogComercialTab.setLsAction(lsAction);
                                    loEvetvLogComercialTab.setLsOrdid(lsParadigmOrderID);
                                    loEvetvLogComercialTab.setLsSptmstid(lsParadigmSpotID);
                                    loEvetvLogComercialTab.setLsSptlen(lsDuration);
                                    loEvetvLogComercialTab.setLsSpotformatid(lsSpotFormatID);
                                    loEvetvLogComercialTab.setLsSpotcomments(lsSpotComments);
                                    loEvetvLogComercialTab.setLsBrnd(lsProductID);
                                    loEvetvLogComercialTab.setLsAutoid(lsMediaID);
                                    loEvetvLogComercialTab.setLsBookingpos(lsBookingPos);
                                    loEvetvLogComercialTab.setLsWarning(lsWarning);
                                    loEvetvLogComercialTab.setLsUntnum(lsNumOrden);
                                    loEvetvLogComercialTab.setLsSptrt(lsASpotPrice);                                    
                                    loEvetvLogComercialTab.setLsValidate(lsValCte);
                                    //System.out.println("Llamando a processInsertLogComercialParadigm");
                                    ResponseBean loRes = 
                                        processInsertLogComercialParadigm(loEvetvLogComercialTab);
                                    System.out.println("Llamando a processInsertLogComercialParadigm" +" key("+
                                                       loEvetvLogComercialTab.getLsStnid()+", "+
                                                       loEvetvLogComercialTab.getLsBrkdtid()+", "+
                                                       loEvetvLogComercialTab.getLsSptmstid()+","+lsValCte+")"+
                                        ".... RESPUESTA("+loRes.getLsResponse()+")");
                                    if(loRes.getLsResponse().equalsIgnoreCase("OK")){
                                        laLogsComercialTab.add(loEvetvLogComercialTab);
                                        liCountInsert++;
                                    }else{
                                        System.out.println("Insertar linea con error");
                                        lbProcessParadigm = false;
                                        ItemRespuesta loItemRespuesta = new ItemRespuesta();
                                        lsFieldParent = "Spot";
                                        lsParadigmOrderID = 
                                            loSpot.getParadigmOrderID() == null ? "0" : loSpot.getParadigmOrderID();
                                        lsParadigmSpotID = 
                                            loSpot.getParadigmSpotID() == null ? "0" : loSpot.getParadigmSpotID();
                                        loItemRespuesta.setElemento("ParadigmOrderID: " + lsParadigmOrderID+
                                                                  " ParadigmSpotID: " + lsParadigmSpotID);
                                        loItemRespuesta.setResultado("KO");     
                                        lsFieldValidate = "ORDID_EVETV-SPTMSTID_EVETV";            
                                        ListaMensaje laLmes = new ListaMensaje();
                                        //loError = getMessageErrDb("DataValidate");
                                        laLmes.setIdError("620");
                                        laLmes.setDescripcion("[" + lsFieldParent + "] " +
                                                              loRes.getLsMessageResponse()+
                                                              " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    
                                        loItemCabecera.getItemRespuesta().add(loItemRespuesta);
                                        loCodRespuesta.getItemCabecera().add(loItemCabecera); 
                                        
                                    }
                                }
                                else{
                                    //Agregado 20180628, no debe ejecutarse validacion nivel 2
                                    //lbProcessParadigm = false;
                                    //-------------------------------
                                    ItemRespuesta loItemRespuesta = new ItemRespuesta();
                                    lsFieldParent = "Spot";
                                    lsParadigmOrderID = 
                                        loSpot.getParadigmOrderID() == null ? "0" : loSpot.getParadigmOrderID();
                                    String lsParadigmSpotID = 
                                        loSpot.getParadigmSpotID() == null ? "0" : loSpot.getParadigmSpotID();
                                    loItemRespuesta.setElemento("ParadigmOrderID: " + lsParadigmOrderID+
                                                              " ParadigmSpotID: " + lsParadigmSpotID);
                                    loItemRespuesta.setResultado("KO");     
                                    lsFieldValidate = "Action";            
                                    if(!lbAction){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("DataValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                              loError.getLsIndDescParameter() + 
                                                              " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    if(!lbActionSize){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("SizeValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                              loError.getLsIndDescParameter() + 
                                                              " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    if(!lbActionReq){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("RequiredValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                              loError.getLsIndDescParameter() + 
                                                              " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    lsFieldValidate = "ParadigmOrderID";            
                                    if(!lbParadigmOrderIDSize){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("SizeValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                              loError.getLsIndDescParameter() + 
                                                              " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    lsFieldValidate = "ParadigmSpotID";            
                                    if(!lbParadigmSpotIDSize){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("SizeValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                              loError.getLsIndDescParameter() + 
                                                              " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    lsFieldValidate = "Duration";            
                                    if(!lbDurationSize){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("SizeValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                              loError.getLsIndDescParameter() + 
                                                              " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    if(!lbDurationFormat){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("FormatValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                              loError.getLsIndDescParameter() + 
                                                              " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    lsFieldValidate = "SpotFormatID";            
                                    if(!lbSpotFormatIDSize){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("SizeValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                              loError.getLsIndDescParameter() + 
                                                              " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    if(!lbSpotFormatIDReq){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("RequiredValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                              loError.getLsIndDescParameter() + 
                                                              " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    lsFieldValidate = "SpotComments";            
                                    if(!lbSpotCommentsSize){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("SizeValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                              loError.getLsIndDescParameter() + 
                                                              " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    lsFieldValidate = "ProductID";            
                                    if(!lbProductIDSize){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("SizeValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                              loError.getLsIndDescParameter() + 
                                                              " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    if(!lbProductIDReq){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("RequiredValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                              loError.getLsIndDescParameter() + 
                                                              " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    lsFieldValidate = "MediaID";            
                                    if(!lbMediaIDSize){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("SizeValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                              loError.getLsIndDescParameter() + 
                                                              " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    if(!lbMediaIDReq){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("RequiredValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                              loError.getLsIndDescParameter() + 
                                                              " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    lsFieldValidate = "BookingPos";            
                                    if(!lbBookingPosSize){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("SizeValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                              loError.getLsIndDescParameter() + 
                                                              " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    lsFieldValidate = "Warning";            
                                    if(!lbWarningSize){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("SizeValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                              loError.getLsIndDescParameter() + 
                                                              " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    lsFieldValidate = "NumOrden";            
                                    if(!lbNumOrdenData){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("DataValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                              loError.getLsIndDescParameter() + 
                                                              " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    lsFieldValidate = "SpotPrice";            
                                    if(!lbSpotPriceFotmat){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("FormatValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                              loError.getLsIndDescParameter() + 
                                                              " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }    
                                    loItemCabecera.getItemRespuesta().add(loItemRespuesta);
                                    loCodRespuesta.getItemCabecera().add(loItemCabecera); 
                                }
                                liCount++;                            
                            }//Fin de Spots             
                            //loCodRespuesta.getItemCabecera().add(itemCabecera);    
                        }else{
                            lsFieldParent = "Break";
                            ItemRespuesta loItemRespuesta = new ItemRespuesta();
                            loItemRespuesta.setIdElemento(lsChannel);
                            loItemRespuesta.setElemento("Channel: " + lsChannel);
                            loItemRespuesta.setResultado("KO");
                            loItemCabecera.setProcessID("0");
                            loItemCabecera.setResultado("KO");
                            loItemCabecera.setTipoProceso("OnLine");
                            lsFieldValidate = "BreakID";            
                            if(!lbBreakIDSize){
                                ListaMensaje laLmes = new ListaMensaje();
                                loError = getMessageErrDb("SizeValidate");
                                laLmes.setIdError(loError.getLsIndValueParameter());
                                laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                      loError.getLsIndDescParameter() + 
                                                      " [" + lsFieldValidate + "]");
                                loItemRespuesta.getListaMensaje().add(laLmes);
                            }
                            if(!lbBreakIDReq){
                                ListaMensaje laLmes = new ListaMensaje();
                                loError = getMessageErrDb("RequiredValidate");
                                laLmes.setIdError(loError.getLsIndValueParameter());
                                laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                      loError.getLsIndDescParameter() + 
                                                      " [" + lsFieldValidate + "]");
                                loItemRespuesta.getListaMensaje().add(laLmes);
                            }
                            lsFieldValidate = "BreakDescription";
                            if(!lbBreakDescSize){
                                ListaMensaje laLmes = new ListaMensaje();
                                loError = getMessageErrDb("SizeValidate");
                                laLmes.setIdError(loError.getLsIndValueParameter());
                                laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                      loError.getLsIndDescParameter() + 
                                                      " [" + lsFieldValidate + "]");
                                loItemRespuesta.getListaMensaje().add(laLmes);
                            }
                            if(!lbBreakDescReq){
                                ListaMensaje laLmes = new ListaMensaje();
                                loError = getMessageErrDb("RequiredValidate");
                                laLmes.setIdError(loError.getLsIndValueParameter());
                                laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                      loError.getLsIndDescParameter() + 
                                                      " [" + lsFieldValidate + "]");
                                loItemRespuesta.getListaMensaje().add(laLmes);
                            }
                            
                            lsFieldValidate = "BreakHour";
                            if(!lbBreakHourSize){
                                ListaMensaje laLmes = new ListaMensaje();
                                loError = getMessageErrDb("SizeValidate");
                                laLmes.setIdError(loError.getLsIndValueParameter());
                                laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                      loError.getLsIndDescParameter() + 
                                                      " [" + lsFieldValidate + "]");
                                loItemRespuesta.getListaMensaje().add(laLmes);
                            }
                            if(!lbBreakHourReq){
                                ListaMensaje laLmes = new ListaMensaje();
                                loError = getMessageErrDb("RequiredValidate");
                                laLmes.setIdError(loError.getLsIndValueParameter());
                                laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                      loError.getLsIndDescParameter() + 
                                                      " [" + lsFieldValidate + "]");
                                loItemRespuesta.getListaMensaje().add(laLmes);
                            }
                            if(!lbBreakHourFormat){
                                ListaMensaje laLmes = new ListaMensaje();
                                loError = getMessageErrDb("FormatValidate");
                                laLmes.setIdError(loError.getLsIndValueParameter());
                                laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                      loError.getLsIndDescParameter() + 
                                                      " [" + lsFieldValidate + "]");
                                loItemRespuesta.getListaMensaje().add(laLmes);
                            }
                            lsFieldValidate = "BreakOverlay";
                            if(!lbBreakOverSize){
                                ListaMensaje laLmes = new ListaMensaje();
                                loError = getMessageErrDb("SizeValidate");
                                laLmes.setIdError(loError.getLsIndValueParameter());
                                laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                      loError.getLsIndDescParameter() + 
                                                      " [" + lsFieldValidate + "]");
                                loItemRespuesta.getListaMensaje().add(laLmes);
                            }
                            if(!lbBreakOverReq){
                                ListaMensaje laLmes = new ListaMensaje();
                                loError = getMessageErrDb("RequiredValidate");
                                laLmes.setIdError(loError.getLsIndValueParameter());
                                laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                      loError.getLsIndDescParameter() + 
                                                      " [" + lsFieldValidate + "]");
                                loItemRespuesta.getListaMensaje().add(laLmes);
                            }
                            if(!lbBreakOverVal){
                                ListaMensaje laLmes = new ListaMensaje();
                                loError = getMessageErrDb("DataValidate");
                                laLmes.setIdError(loError.getLsIndValueParameter());
                                laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                      loError.getLsIndDescParameter() + 
                                                      " [" + lsFieldValidate + "]");
                                loItemRespuesta.getListaMensaje().add(laLmes);
                            }
                            loItemCabecera.getItemRespuesta().add(loItemRespuesta);
                            loCodRespuesta.getItemCabecera().add(loItemCabecera);
                        }
                    }//Fin de CICLO Breaks
                }else{                    
                    lbProcessParadigm = false;
                    lbFlagSendMail    = false;
                    ItemCabecera loItemCabecera = new ItemCabecera();
                   
                    loItemCabecera.setProcessID("0");
                    loItemCabecera.setResultado("KO");
                    loItemCabecera.setTipoProceso("OnLine");
                     
                    ItemRespuesta loItemRespuesta = new ItemRespuesta();
                     
                    loItemRespuesta.setElemento("0");
                    loItemRespuesta.setIdElemento("0");
                    loItemRespuesta.setResultado("KO");   
                    lsFieldParent = "CommercialList";
                    lsFieldValidate = "ChannelID";            
                    String lsMess = "El log comercial del (" + lsDateReal + ") del canal (" + 
                                    lsStnid + ") ya fue procesado";  
                    ListaMensaje laLmes = new ListaMensaje();
                    laLmes.setIdError("0");
                    laLmes.setDescripcion("[" + lsFieldParent + "] " + lsMess + " [" + lsFieldValidate + "]");
                    loItemRespuesta.getListaMensaje().add(laLmes);
                    loItemCabecera.getItemRespuesta().add(loItemRespuesta);
                    loCodRespuesta.getItemCabecera().add(loItemCabecera);
                }  
            }else{
                ItemCabecera loItemCabecera = new ItemCabecera();
                loItemCabecera.setProcessID("0");
                loItemCabecera.setResultado("KO");
                loItemCabecera.setTipoProceso("OnLine");
                ItemRespuesta loItemRespuesta = new ItemRespuesta();
                loItemRespuesta.setElemento("0");
                loItemRespuesta.setIdElemento("0");
                loItemRespuesta.setResultado("KO");   
                lsFieldParent = "CommercialList";
                lsFieldValidate = "ChannelID";            
                if(!lbChannelSize){
                    ListaMensaje laLmes = new ListaMensaje();
                    loError = getMessageErrDb("SizeValidate");
                    laLmes.setIdError(loError.getLsIndValueParameter());
                    laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                          loError.getLsIndDescParameter() + " [" + 
                                          lsFieldValidate + "]");
                    loItemRespuesta.getListaMensaje().add(laLmes);
                }
                if(!lbChannelReq){
                    ListaMensaje laLmes = new ListaMensaje();
                    loError = getMessageErrDb("RequiredValidate");
                    laLmes.setIdError(loError.getLsIndValueParameter());
                    laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                          loError.getLsIndDescParameter() + " [" + 
                                          lsFieldValidate + "]");
                    loItemRespuesta.getListaMensaje().add(laLmes);
                }
                lsFieldValidate = "Date";
                if(!lbDateSize){
                    ListaMensaje laLmes = new ListaMensaje();
                    loError = getMessageErrDb("SizeValidate");
                    laLmes.setIdError(loError.getLsIndValueParameter());
                    laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                          loError.getLsIndDescParameter() + " [" + 
                                          lsFieldValidate + "]");
                    loItemRespuesta.getListaMensaje().add(laLmes);
                }
                if(!lbDateReq){
                    ListaMensaje laLmes = new ListaMensaje();
                    loError = getMessageErrDb("RequiredValidate");
                    laLmes.setIdError(loError.getLsIndValueParameter());
                    laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                          loError.getLsIndDescParameter() + " [" + 
                                          lsFieldValidate + "]");
                    loItemRespuesta.getListaMensaje().add(laLmes);
                }
                if(!lbDateFormat){
                    ListaMensaje laLmes = new ListaMensaje();
                    loError = getMessageErrDb("FormatValidate");
                    laLmes.setIdError(loError.getLsIndValueParameter());
                    laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                          loError.getLsIndDescParameter() + " [" + 
                                          lsFieldValidate + "]");
                    loItemRespuesta.getListaMensaje().add(laLmes);
                }
                
                lsFieldValidate = "Validate";
                if(!lbValidateSize){
                    ListaMensaje laLmes = new ListaMensaje();
                    loError = getMessageErrDb("SizeValidate");
                    laLmes.setIdError(loError.getLsIndValueParameter());
                    laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                          loError.getLsIndDescParameter() + " [" + 
                                          lsFieldValidate + "]");
                    loItemRespuesta.getListaMensaje().add(laLmes);
                }               
                if(!lbValidateFormat){
                    ListaMensaje laLmes = new ListaMensaje();
                    loError = getMessageErrDb("FormatValidate");
                    laLmes.setIdError(loError.getLsIndValueParameter());
                    laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                          loError.getLsIndDescParameter() + " [" + 
                                          lsFieldValidate + "]");
                    loItemRespuesta.getListaMensaje().add(laLmes);
                }
                
                
                loItemCabecera.getItemRespuesta().add(loItemRespuesta);
                loCodRespuesta.getItemCabecera().add(loItemCabecera);
                
            }
        }else{//Autenticacion fallida
            lbProcessParadigm = false;
            lbFlagSendMail    = false;
            ItemCabecera loItemCabecera = new ItemCabecera();
            loItemCabecera.setProcessID("0");
            loItemCabecera.setResultado("KO");
            loItemCabecera.setTipoProceso("OnLine");
            ItemRespuesta loItemRespuesta = new ItemRespuesta();
            loItemRespuesta.setElemento("0");
            loItemRespuesta.setIdElemento("0");
            loItemRespuesta.setResultado("KO");   
            lsFieldParent = "CommercialList";
            lsFieldValidate = "Authentication";            
            String lsMess = "Credenciales no permitidas, verifique Username y Password";  
            ListaMensaje laLmes = new ListaMensaje();
            laLmes.setIdError("0");
            laLmes.setDescripcion("[" + lsFieldParent + "] " + lsMess + " [" + lsFieldValidate + "]");
            loItemRespuesta.getListaMensaje().add(laLmes);
            loItemCabecera.getItemRespuesta().add(loItemRespuesta);
            loCodRespuesta.getItemCabecera().add(loItemCabecera);
        }
        // Todo lo anterior fue para insertar,  ahora la logica de jacobo a partir del punto 2      
        System.out.println("Fin de insertar, lbProcessParadigm["+lbProcessParadigm+"]");
        if(lbProcessParadigm){
            //System.out.println("Numero de Breaks activos ["+toCommercialList.getListBreaks()+"]");
            if(toCommercialList.getListBreaks() != null){
                //Insertar en tabla de ListBreaks
                System.out.println("Insertar en Lista_Breaks("+lsStnidValidate+",("+lsBcstdtValidate+"),List)");
                try{
                    insertBreakList(lsStnidValidate,lsBcstdtValidate,toCommercialList.getListBreaks());
                }catch(Exception loExc){
                    System.out.println("Error en insertBreakList "+loExc.getMessage());
                }
                System.out.println("Insertar en Lista_Breaks....FIN");
                //Bitacora
                String  lsIndProcessCtrlList = 
                    loEntityMappedDao.getGeneralParameterID("Execute", "PROCESS_INTEGRATION");
                EvetvIntServiceBitacoraTab loEvetvIntServiceBitacoraTabCtrlList = new EvetvIntServiceBitacoraTab();
                loEvetvIntServiceBitacoraTabCtrlList.setLsIdLogServices(lsIdService);
                loEvetvIntServiceBitacoraTabCtrlList.setLsIdService(lsIdService);
                loEvetvIntServiceBitacoraTabCtrlList.setLsIndProcess(lsIndProcessCtrlList);
                loEvetvIntServiceBitacoraTabCtrlList.setLsNumEvtbProcessId("0");
                loEvetvIntServiceBitacoraTabCtrlList.setLsNumPgmProcessId("0");
                loEvetvIntServiceBitacoraTabCtrlList.setLsIndEvento("Insert en EVETV_LISTA_BREAKS(" + 
                                                                lsStnid + ", " + 
                                                                lsBcstdtValidate + ")");
                //System.out.println("insertar en bitacora");
                loEntityMappedDao.insertBitacoraWs(loEvetvIntServiceBitacoraTabCtrlList);
            }
            //Validar siempre con EVENTAS.EVETV_LOG_COMERCIAL_VALIDA
            String  lsDateReal = buildDateYYYYMMDD(lsBcstdt);
            System.out.println("Fecha Real(cadena)["+lsDateReal+"]");
            String  lsIndProcessCtrlVal = 
                loEntityMappedDao.getGeneralParameterID("callProcedureValidate", "PROCESS_INTEGRATION");
            EvetvIntServiceBitacoraTab loEvetvIntServiceBitacoraTabCtrlVal = new EvetvIntServiceBitacoraTab();
            loEvetvIntServiceBitacoraTabCtrlVal.setLsIdLogServices(lsIdService);
            loEvetvIntServiceBitacoraTabCtrlVal.setLsIdService(lsIdService);
            loEvetvIntServiceBitacoraTabCtrlVal.setLsIndProcess(lsIndProcessCtrlVal);
            loEvetvIntServiceBitacoraTabCtrlVal.setLsNumEvtbProcessId("0");
            loEvetvIntServiceBitacoraTabCtrlVal.setLsNumPgmProcessId("0");
            loEvetvIntServiceBitacoraTabCtrlVal.setLsIndEvento("callProcedureValidate(" + 
                                                            lsStnid + ", " + 
                                                            lsDateReal + ")");
            //System.out.println("insertar en bitacora");
            loEntityMappedDao.insertBitacoraWs(loEvetvIntServiceBitacoraTabCtrlVal);
            //System.out.println("insertar en bitacora.....FIN");
            String  lsMsgErrrVal = "";
            LogComercialDao loLogComercialDao = new LogComercialDao();
            ResponseUpdDao loResponseUpdDao = new ResponseUpdDao();
            try {
                System.out.println("llamando a callProcedureValidate");
                loResponseUpdDao = loLogComercialDao.callProcedureValidate(lsStnid, lsDateReal);
                lsMsgErrrVal = "Resultado callProcedureValidate("+lsStnid+", "+lsDateReal+"): "+loResponseUpdDao.getLsMessage();
                System.out.println("llamando a callProcedureValidate....SUCCESS!!!");
            } catch (SQLException loEx) {
                lsMsgErrrVal = loEx.getMessage();
            }
            finally{
                loEvetvIntServiceBitacoraTabCtrlVal.setLsIdLogServices(lsIdService);
                loEvetvIntServiceBitacoraTabCtrlVal.setLsIdService(lsIdService);
                loEvetvIntServiceBitacoraTabCtrlVal.setLsIndProcess(lsIndProcessCtrlVal); 
                loEvetvIntServiceBitacoraTabCtrlVal.setLsNumEvtbProcessId("0");
                loEvetvIntServiceBitacoraTabCtrlVal.setLsNumPgmProcessId("0");
                loEvetvIntServiceBitacoraTabCtrlVal.setLsIndEvento(lsMsgErrrVal);
                loEntityMappedDao.insertBitacoraWs(loEvetvIntServiceBitacoraTabCtrlVal);
            }
            //Varificar si existe algun KO en la tabla 
            Integer liReVal = loLogComercialDao.validateKoLogCommercial(lsStnid,lsDateReal);
            if(liReVal > 0 && lsValidate.equalsIgnoreCase("S")){
                String lsType = "rst>0 && val=S";
                //Existen registros en KO y se solicita Validacion
                System.out.println("Existen registros en KO y se solicita Validacion");
                //Llenar request de gallo
                List<EvetvLogComercialStatusTab> laList = 
                    loLogComercialDao.getLogComercialStatus(lsStnid, lsDateReal);
                if(laList.size()>0){
                    System.out.println("seteando respuesta");
                    Encabezado loEncabezado = new Encabezado();
                    loEncabezado.setProcessID(laList.get(0).getLsIdProceso());
                    loEncabezado.setCanal(lsStnid);//laList.get(0).getLsStnid());
                    loEncabezado.setDia(lsDateReal);//laList.get(0).getLsBcstdt());
                    loEncabezado.setTipo("Log Comercial");//laList.get(0).getLsTipo());
                    loEncabezado.setMensaje("");//laList.get(0).getLsMensaje());
                    
                    Detalle loDetalle = new Detalle();
                    for(int li=0; li < laList.size(); li++){
                        //System.out.println("Iterando ("+li+")");
                        ItemRespuestaValidate loItv01 = new ItemRespuestaValidate();
                        loItv01.setProcessID(laList.get(li).getLsIdProceso());
                        loItv01.setTipo(laList.get(li).getLsTipo());
                        loItv01.setMensaje(laList.get(li).getLsMensaje());
                        loDetalle.getItemRespuestaValidate().add(loItv01);    
                    }
                    loEncabezado.setDetalle(loDetalle);
                    loCodRespuesta.getEncabezado().add(loEncabezado);  
                    System.out.println("seteando codRespuesta");
                }else{
                    Encabezado loEncabezado = new Encabezado();
                    loEncabezado.setProcessID("0");
                    loEncabezado.setCanal(toCommercialList.getChannelID());
                    loEncabezado.setDia(toCommercialList.getDate());
                    loEncabezado.setTipo("ERROR");
                    loEncabezado.setMensaje("Error");
                    
                    Detalle loDetalle = new Detalle();
                    
                    ItemRespuestaValidate loItv01 = new ItemRespuestaValidate();
                    loItv01.setProcessID("0");
                    loItv01.setTipo("ERROR");
                    loItv01.setMensaje("Error");
                    loDetalle.getItemRespuestaValidate().add(loItv01);
                    loEncabezado.setDetalle(loDetalle);
                    loCodRespuesta.getEncabezado().add(loEncabezado);                        
                }                
            }
            if(liReVal > 0 && !lsValidate.equalsIgnoreCase("S")){
                String lsType = "rst>0 && val!=S";
                //Existen registros en KO y NO se solicita Validacion
                System.out.println("Existen registros en KO y NO se solicita Validacion");
                ItemCabecera loItemCabecera = new ItemCabecera();
                loItemCabecera.setProcessID("0");
                loItemCabecera.setResultado("KO");
                loItemCabecera.setTipoProceso("OnLine");
                ItemRespuesta loItemRespuesta = new ItemRespuesta();
                loItemRespuesta.setElemento(lsStnid);
                loItemRespuesta.setIdElemento("0");
                loItemRespuesta.setResultado("KO");  
                lsFieldParent = "CommercialList";
                lsFieldValidate = "Validate";
                ListaMensaje laLmes = new ListaMensaje();
                laLmes.setIdError("601");
                laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                      lsStnid+" "+lsDateReal+": Existen Errores, enviar VALIDATE en S" + " [" + 
                                      lsFieldValidate + "]");
                loItemRespuesta.getListaMensaje().add(laLmes);
                loItemCabecera.getItemRespuesta().add(loItemRespuesta);
                loCodRespuesta.getItemCabecera().add(loItemCabecera);    
                //Eliminar registros insertados
                //rollbackValidation(lsStnid, lsDateReal, lsIdService, lsType);
            }
            if(liReVal == 0 && lsValidate.equalsIgnoreCase("S")){
                String lsType = "rst==0 && val=S";
                //NO Existen registros en KO pero se solicita Validacion
                System.out.println("NO Existen registros en KO pero se solicita Validacion");
                ItemCabecera loItemCabecera = new ItemCabecera();
                loItemCabecera.setProcessID("0");
                loItemCabecera.setResultado("OK");
                loItemCabecera.setTipoProceso("OnLine");
                ItemRespuesta loItemRespuesta = new ItemRespuesta();
                loItemRespuesta.setElemento(lsStnid);
                loItemRespuesta.setIdElemento("0");
                loItemRespuesta.setResultado("OK");  
                lsFieldParent = "CommercialList";
                lsFieldValidate = "Validate";
                ListaMensaje laLmes = new ListaMensaje();
                laLmes.setIdError("602");
                
                String lsMess = "Enviar VALIDATE en N, Solicitud Sin Incidencias";
                
                lsMess = loLogComercialDao.getMessageValidationOk(lsStnid, lsDateReal); 
                
                laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                      lsStnid+" "+lsDateReal+": " + lsMess +
                                      " [" + lsFieldValidate + "]");
                loItemRespuesta.getListaMensaje().add(laLmes);
                
                loItemCabecera.getItemRespuesta().add(loItemRespuesta);
                loCodRespuesta.getItemCabecera().add(loItemCabecera);    
                //Eliminar registros insertados
                //rollbackValidation(lsStnid, lsDateReal, lsIdService, lsType);
            }
            if(liReVal == 0 && !lsValidate.equalsIgnoreCase("S")){
                //NO Existen registros en KO y NO se solicita Validacion //CASO DESEADO
                System.out.println("NO Existen registros en KO y NO se solicita Validacion //CASO DESEADO");
                boolean lbPrcXml = true;
                //#############################################
                //String  lsDateReal = buildDateYYYYMMDD(lsBcstdt);
                String  lsIndProcessCtrl = 
                    loEntityMappedDao.getGeneralParameterID("callProcedureCommercialLog", "PROCESS_INTEGRATION");
                EvetvIntServiceBitacoraTab loEvetvIntServiceBitacoraTabCtrl = new EvetvIntServiceBitacoraTab();
                loEvetvIntServiceBitacoraTabCtrl.setLsIdLogServices(lsIdService);
                loEvetvIntServiceBitacoraTabCtrl.setLsIdService(lsIdService);
                loEvetvIntServiceBitacoraTabCtrl.setLsIndProcess(lsIndProcessCtrl);
                loEvetvIntServiceBitacoraTabCtrl.setLsNumEvtbProcessId("0");
                loEvetvIntServiceBitacoraTabCtrl.setLsNumPgmProcessId("0");
                loEvetvIntServiceBitacoraTabCtrl.setLsIndEvento("callProcedureCommercialLog(" + 
                                                                lsStnid + ", " + 
                                                                lsDateReal + ")");
                loEntityMappedDao.insertBitacoraWs(loEvetvIntServiceBitacoraTabCtrl);
                String  lsMsgErrr = "";
                //LogComercialDao loLogComercialDao = new LogComercialDao();
                try {
                    loLogComercialDao.callProcedureCommercialLog(lsStnid, lsDateReal);
                } catch (SQLException loEx) {
                    lsMsgErrr = loEx.getMessage();
                    lbPrcXml = false;                
                    loEvetvIntServiceBitacoraTabCtrl.setLsIdLogServices(lsIdService);
                    loEvetvIntServiceBitacoraTabCtrl.setLsIdService(lsIdService);
                    loEvetvIntServiceBitacoraTabCtrl.setLsIndProcess(lsIndProcessCtrl); 
                    loEvetvIntServiceBitacoraTabCtrl.setLsNumEvtbProcessId("0");
                    loEvetvIntServiceBitacoraTabCtrl.setLsNumPgmProcessId("0");
                    loEvetvIntServiceBitacoraTabCtrl.setLsIndEvento(loEx.getMessage());
                    loEntityMappedDao.insertBitacoraWs(loEvetvIntServiceBitacoraTabCtrl);
                }
    
                if(!lbPrcXml){//Ejecucion de procedimiento Fallido
                    ItemCabecera  loItemCabecera = new ItemCabecera();                
                    loItemCabecera.setProcessID("0");
                    loItemCabecera.setResultado("KO");
                    loItemCabecera.setTipoProceso("OnLine");
                    ItemRespuesta loItemRespuesta = new ItemRespuesta();
                    loItemRespuesta.setIdElemento(lsParadigmOrderID);
                    loItemRespuesta.setElemento("ERROR EN BD");
                    loItemRespuesta.setIdElemento("E");
                    loItemRespuesta.setResultado("KO");
                    ListaMensaje laLmes = new ListaMensaje();
                    laLmes.setIdError("0");
                    laLmes.setDescripcion(lsMsgErrr);                             
                    loItemRespuesta.getListaMensaje().add(laLmes);
                    loItemCabecera.getItemRespuesta().add(loItemRespuesta);                    
                    loCodRespuesta.getItemCabecera().add(loItemCabecera);
                }else{//Ejecucion de Procedimiento satisfactorio
                    lsDateReal = buildDateYYYYMMDD(lsBcstdt);
                    EvetvLogComercialProcesadoBean loEvetvLogComPro = 
                        loLogComercialDao.getLogComercialProcesado(lsStnid, lsDateReal); //Revisar tabla control
                    if(!loEvetvLogComPro.getLsEstatus().equalsIgnoreCase("OK")){
                        //Termino en Fallo
                        ItemCabecera loItemCabecera = new ItemCabecera();                
                        loItemCabecera.setProcessID("0");
                        loItemCabecera.setResultado("KO");
                        loItemCabecera.setTipoProceso("OnLine");
                        ItemRespuesta loItemRespuesta = new ItemRespuesta();
                        loItemRespuesta.setIdElemento(lsParadigmOrderID);
                        loItemRespuesta.setElemento("fail");
                        loItemRespuesta.setIdElemento("E");
                        loItemRespuesta.setResultado("KO");
                        ListaMensaje laLmes = new ListaMensaje();
                        laLmes.setIdError("0");
                        lsEmailMsgErr = loEvetvLogComPro.getLsMensaje();
                        laLmes.setDescripcion(loEvetvLogComPro.getLsMensaje());                             
                        loItemRespuesta.getListaMensaje().add(laLmes);
                        loItemCabecera.getItemRespuesta().add(loItemRespuesta);                    
                        loCodRespuesta.getItemCabecera().add(loItemCabecera);
                    }else{
                        //########################## RESPUESTA A CLIENTE NEPTUNO ########################
                        Breaks                     loBreaks = toCommercialList.getBreaks();
                        List<Break>                laBreaks = loBreaks.getBreak();
                        for(Break loBreak : laBreaks){
                            ItemCabecera loItemCabecera = new ItemCabecera();
                            loItemCabecera.setProcessID(loBreak.getBreakID());
                            loItemCabecera.setResultado("OK");
                            loItemCabecera.setTipoProceso("OnLine");
                            SpotList     loSpotList = loBreak.getSpotList();
                            List<Spot>   laSpots = loSpotList.getSpot();                          
                            for(Spot loSpot : laSpots){
                                ItemRespuesta loItemRespuesta = new ItemRespuesta();
                                loItemRespuesta.setIdElemento(lsParadigmOrderID);
                                loItemRespuesta.setElemento("BreakID: " + loBreak.getBreakID() +
                                                          " ParadigmSpotID: " + loSpot.getParadigmSpotID());
                                loItemRespuesta.setIdElemento("" + loBreak.getBreakID() + "-" + loSpot.getParadigmSpotID());
                                loItemRespuesta.setResultado("OK");
                                loItemCabecera.getItemRespuesta().add(loItemRespuesta);    
                            }
                            loCodRespuesta.getItemCabecera().add(loItemCabecera);
                        }
                    }
                }  
            }
        }
        System.out.println("Armar cadena de respuesta");
        RecibirDatosExternosResult loRecibirDatosExternosResult = new RecibirDatosExternosResult();
        System.out.println("Flag 01");
        loRecibirDatosExternosResult.setCodRespuesta(loCodRespuesta);
        System.out.println("Flag 02");
        loResponse.setRecibirDatosExternosResult(loRecibirDatosExternosResult);
        System.out.println("Flag 03");
        
        //###############################################################################################
        try{
            System.out.println("Creando Archivo XML");
            String lsIndProcessRes = 
                loEntityMappedDao.getGeneralParameterID("FinishResponseNeptuno", "PROCESS_INTEGRATION");
            EvetvIntServiceBitacoraTab loEvetvIntServiceBitacoraTabRes = new EvetvIntServiceBitacoraTab();
            loEvetvIntServiceBitacoraTabRes.setLsIdLogServices(lsIdService);
            loEvetvIntServiceBitacoraTabRes.setLsIdService(lsIdService);
            loEvetvIntServiceBitacoraTabRes.setLsIndProcess(lsIndProcessRes); 
            loEvetvIntServiceBitacoraTabRes.setLsNumEvtbProcessId("0");
            loEvetvIntServiceBitacoraTabRes.setLsNumPgmProcessId("0");
            loEvetvIntServiceBitacoraTabRes.setLsIndEvento("Respuesta a Cliente para Log Commercial");
            loEntityMappedDao.insertBitacoraWs(loEvetvIntServiceBitacoraTabRes);
            
            lsNomFile = "LogComercial-" + getId() + ".xml";
            lsIndFileType = "RESPONSE";
            lsIndServiceType = "WsLogComercial";
            lsIndEstatus = "A";
            lsNomUserName = "neptuno";        
            ByteArrayOutputStream loBaosRes = new ByteArrayOutputStream();
            JAXB.marshal(loResponse, loBaosRes);
            /*
            System.out.println("Guardar archivo fisico");
            try{                        
                StreamResult result =
                new StreamResult(new File("C:\\Users\\JorgeOWM\\Desktop\\LogComm-Response"+getId()+".xml"));
                //transformer.transform(source, result);
                JAXB.marshal(loRecibirDatosExternosResult, result);
            }catch(Exception loExo){
                System.out.println("Error al Guardar archivo fisico "+loExo.getMessage());
            }
            System.out.println("FIN DE ...... Guardar archivo fisico");
            */
            InputStream loFileXmlRes = new ByteArrayInputStream(loBaosRes.toByteArray()); 
            loEntityMappedDao.insertEvetvIntXmlFilesTab(liIdRequest, 
                                                        Integer.parseInt(lsIdService), 
                                                        lsNomFile, 
                                                        lsIndFileType, 
                                                        lsIndServiceType, 
                                                        lsIndEstatus, 
                                                        lsNomUserName, 
                                                        loFileXmlRes
                                                        );
            System.out.println("Creando Archivo XML............OK");
        }catch(Exception loEx){
            System.out.println("Archivo Response No generado");
        }
        //###############################################################################################
        if(!lsValidate.equalsIgnoreCase("S")){
            System.out.println("Validate["+lsValidate+"]");
            try{
                System.out.println("Enviando Correo");
                String lsIndProcessRes = 
                    loEntityMappedDao.getGeneralParameterID("SendEmail", "PROCESS_INTEGRATION");
                EvetvIntServiceBitacoraTab loEvetvIntServiceBitacoraTabRes = new EvetvIntServiceBitacoraTab();
                loEvetvIntServiceBitacoraTabRes.setLsIdLogServices(lsIdService);
                loEvetvIntServiceBitacoraTabRes.setLsIdService(lsIdService);
                loEvetvIntServiceBitacoraTabRes.setLsIndProcess(lsIndProcessRes);
                loEvetvIntServiceBitacoraTabRes.setLsNumEvtbProcessId("0");
                loEvetvIntServiceBitacoraTabRes.setLsNumPgmProcessId("0");
                loEvetvIntServiceBitacoraTabRes.setLsIndEvento("Enviando email del Procesamiento de Log Comercial(" + 
                                                               lsEmailMsgErr + ")");
                loEntityMappedDao.insertBitacoraWs(loEvetvIntServiceBitacoraTabRes);
                System.out.println("Enviando Correo lbFlagSendMail("+lbFlagSendMail+")");
                if(lbFlagSendMail){
                    String lsSubject = 
                        loEntityMappedDao.getGeneralParameter("SubjectLogComercial", "INTEGRATION_EMAIL");
                    System.out.println("Enviando Correo lsSubject("+lsSubject+")");
                    String lsTypeMail = "MAIL_OK";
                    if(lsEmailMsgErr.length() > 0){
                        lsTypeMail = "MAIL_KO";
                    }
                    System.out.println("Enviando Correo lsTypeMail("+lsTypeMail+")");
                    List<EmailDestinationAddress> toEmailDestinationAddress = 
                        loEntityMappedDao.getDestinationAddress(lsIdService,lsTypeMail);
                    MailManagement loMailManagement = new MailManagement();
                    String lsfechaReq = new Date().toString();
                    
                    boolean lbRes = 
                        loMailManagement.sendEmailLogComercial(lsSubject+"("+lsfechaReq+")", 
                                                           toEmailDestinationAddress, 
                                                           laLogsComercialTab, 
                                                           lsEmailMsgErr
                                                           );
                    
                    System.out.println("Enviando Correo lbRes("+lbRes+")");
                }
                System.out.println("Enviando Correo.........OK");
                //#############################################
            }catch(Exception loEx){
                System.out.println("No es Posible enviar correo");
            }
        }else{
            System.out.println("No se envia correo debido a VALIDATE = S");
        }
        System.out.println("Fin COMPLETE!!!!!");
        return loResponse;
    }
    
    /**
     * Construye fecha en formato requerido por neptuno
     * @autor Jorge Luis Bautista Santiago
     * @param tsDate
     * @param tsInMask
     * @return String
     */
    private String buildDateEveTv(String tsDate, String tsInMask){
        String lsResponse = null;
        try{
            String[] laArrDate = tsDate.split("/");
            if(laArrDate.length > 0){
                String lsDay = laArrDate[0];
                String lsMonth = laArrDate[1];            
                String lsYear = convierteFecha(tsDate, tsInMask, "YYYY");
                if(lsYear != null){
                    lsResponse = lsDay + "/" + lsMonth + "/" + lsYear;    
                }
            }
        }catch(Exception loEx){
            loEx.printStackTrace();
            lsResponse = null;
        }
        return lsResponse;
    }
    
    /**
     * Convierte fecha en cadena entre formatos de mascara proporcionados
     * @autor Jorge Luis Bautista Santiago
     * @param tsCadenaFecha
     * @param tsMascaraEntrada
     * @param tsMascaraSalida
     * @return String
     */
    private String convierteFecha(String tsCadenaFecha, 
                                  String tsMascaraEntrada,
                                  String tsMascaraSalida) {

        SimpleDateFormat loSdfEntrada = new SimpleDateFormat(tsMascaraEntrada);
        SimpleDateFormat loSdfSalida = new SimpleDateFormat(tsMascaraSalida);

        Date   loPivot = new Date();
        String lsFormatted = "";

        try {
            loPivot = loSdfEntrada.parse(tsCadenaFecha);
            lsFormatted = loSdfSalida.format(loPivot);
        } 
        catch (ParseException loEx) {
            lsFormatted = null;
        }

        return lsFormatted;
    }
    
    /**
     * Obtiene dinamicamente el mensaje de error configurado de acuerdo al campo
     * @autor Jorge Luis Bautista Santiago
     * @param tsField
     * @return EvetvIntConfigParamTabBean
     */
    private EvetvIntConfigParamTabBean getMessageErrDb(String tsField){
        EvetvIntConfigParamTabBean lsRes = new EvetvIntConfigParamTabBean();
        EntityMappedDao            loEmd = new EntityMappedDao();
        lsRes = loEmd.getGeneralParameterBean(tsField, "VALIDATE_FIELD_WS");
        return lsRes;
    }
    
    /**
     * Procesamiento de insercion a tabla de control de log comercial
     * @autor Jorge Luis Bautista Santiago
     * @param poEvetvLogComercialTab
     * @return ResponseBean
     */
    private ResponseBean processInsertLogComercialParadigm(EvetvLogComercialTab poEvetvLogComercialTab){
        ResponseBean    loResponse = new ResponseBean();
        boolean         lbFalgPrcess = false;
        LogComercialDao loLogComercialDao = new LogComercialDao();
        //System.out.println("(processInsertLogComercialParadigm) verificando si existe");
        Integer         loRb = loLogComercialDao.validateExistLogComercialInt(poEvetvLogComercialTab);
        //System.out.println("(processInsertLogComercialParadigm) verificando si existe.......("+loRb+")");
        Integer         liExe = 0;
        if(loRb > 0){// Ya existe
        //System.out.println("(processInsertLogComercialParadigm) YA EXISTE");
            lbFalgPrcess = false;  
            if(poEvetvLogComercialTab.getLsValidate().equalsIgnoreCase("S")){
                lbFalgPrcess = true;
            }
        }else{
            //Agregado 20180905
            //El registro llave no existe
            lbFalgPrcess = true;            
        }
        if(lbFalgPrcess){
            if(poEvetvLogComercialTab.getLsAction().equalsIgnoreCase("I")){
                //System.out.println("(processInsertLogComercialParadigm) Insertando");
                liExe = loLogComercialDao.insertLogComercial(poEvetvLogComercialTab);
                //System.out.println("(processInsertLogComercialParadigm) Insertando....OK");
            }
            /*if(poEvetvLogComercialTab.getLsAction().equalsIgnoreCase("U")){//agregado 20180905
                //System.out.println("(processInsertLogComercialParadigm) Insertando");
                liExe = loLogComercialDao.insertLogComercial(poEvetvLogComercialTab);
                //System.out.println("(processInsertLogComercialParadigm) Insertando....OK");
            }*/
            if(liExe == 0){
                loResponse.setLsResponse("ERROR");
                loResponse.setLsType("NotInsert");
                loResponse.setLsMessageResponse("Log comercial no insertado verifique ACTION");
            }else{
                loResponse.setLsResponse("OK");
                loResponse.setLsType("InsertOKProcess");
                loResponse.setLsMessageResponse("success");
            }
        }else{
            loResponse.setLsResponse("ERROR");
            loResponse.setLsType("NotInsert");
            loResponse.setLsMessageResponse("Ya existe Sptmstid("+poEvetvLogComercialTab.getLsSptmstid()+")");
        }
        return loResponse;
    }
    
    /**
     * Obtiene identificador unico de acuerdo al momento generado
     * @autor Jorge Luis Bautista Santiago
     * @return String
     */
    private String getId(){
        String     lsResponse = null;
        DateFormat loDf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        lsResponse = loDf.format(new java.util.Date(System.currentTimeMillis()));
        return lsResponse;
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
     * Elimina registros insertados en la validacion
     * @autor Jorge Luis Bautista Santiago
     * @param tsCanal
     * @param tsFecha
     * @return void
     */
    private void rollbackValidation(String tsCanal, String tsFecha, String lsIdService, String tsType){
        System.out.println("Ejecutando Rollback.........");
        //System.out.println(".........Deshabilitado");
        LogComercialDao loLcd = new LogComercialDao();
        EntityMappedDao loEntityMappedDao = new EntityMappedDao();
        String lsIndProcessRes = 
            loEntityMappedDao.getGeneralParameterID("ExeProcedure", "PROCESS_INTEGRATION");
        EvetvIntServiceBitacoraTab loEvetvIntServiceBitacoraTabRes = new EvetvIntServiceBitacoraTab();
        loEvetvIntServiceBitacoraTabRes.setLsIdLogServices(lsIdService);
        loEvetvIntServiceBitacoraTabRes.setLsIdService(lsIdService);
        loEvetvIntServiceBitacoraTabRes.setLsIndProcess(lsIndProcessRes);
        loEvetvIntServiceBitacoraTabRes.setLsNumEvtbProcessId("0");
        loEvetvIntServiceBitacoraTabRes.setLsNumPgmProcessId("0");
        loEvetvIntServiceBitacoraTabRes.setLsIndEvento("["+tsCanal+"]["+tsFecha+"] Rollback registros insertados en validacion");
        loEntityMappedDao.insertBitacoraWs(loEvetvIntServiceBitacoraTabRes);
        try {
            ResponseUpdDao loResponseUpdDao = new ResponseUpdDao();
            loResponseUpdDao = loLcd.rollbackValidateLogComercial(tsCanal, tsFecha);
            EvetvIntServiceBitacoraTab loEvetvIntServiceBitacoraTab = new EvetvIntServiceBitacoraTab();
            loEvetvIntServiceBitacoraTab.setLsIdLogServices(lsIdService);
            loEvetvIntServiceBitacoraTab.setLsIdService(lsIdService);
            loEvetvIntServiceBitacoraTab.setLsIndProcess(lsIndProcessRes);
            loEvetvIntServiceBitacoraTab.setLsNumEvtbProcessId("0");
            loEvetvIntServiceBitacoraTab.setLsNumPgmProcessId("0");
            loEvetvIntServiceBitacoraTab.setLsIndEvento("["+tsCanal+"]["+tsFecha+"]Rollback "+loResponseUpdDao.getLsMessage());
            loEntityMappedDao.insertBitacoraWs(loEvetvIntServiceBitacoraTab);
        } catch (SQLException loEx) {
            EvetvIntServiceBitacoraTab loEvetvIntServiceBitacoraTab = new EvetvIntServiceBitacoraTab();
            loEvetvIntServiceBitacoraTab.setLsIdLogServices(lsIdService);
            loEvetvIntServiceBitacoraTab.setLsIdService(lsIdService);
            loEvetvIntServiceBitacoraTab.setLsIndProcess(lsIndProcessRes);
            loEvetvIntServiceBitacoraTab.setLsNumEvtbProcessId("0");
            loEvetvIntServiceBitacoraTab.setLsNumPgmProcessId("0");
            loEvetvIntServiceBitacoraTab.setLsIndEvento("["+tsCanal+"]["+tsFecha+"]Rollback "+loEx.getMessage());
            loEntityMappedDao.insertBitacoraWs(loEvetvIntServiceBitacoraTab);
        }
    }
    
    public void insertBreakList(String tsStnid, 
                                String tsBcstdt,
                                ListBreaks toListBreaks){
        LogComercialDao loLogComercialDao = new LogComercialDao();
        for(ActiveBreak loActiveBreak : toListBreaks.getActiveBreak()){
            //System.out.println("Insertando: ");   
            //System.out.print("\t: "+tsStnid+","+tsBcstdt+","+loActiveBreak);   
            loLogComercialDao.insertListBreaks(tsStnid, tsBcstdt, loActiveBreak);
        }
    }
      
}
