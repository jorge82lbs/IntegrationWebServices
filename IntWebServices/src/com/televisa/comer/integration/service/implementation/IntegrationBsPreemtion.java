/**
* Project: Integraton Paradigm - EveTV
*
* File: IntegrationBsPreemtion.java
*
* Created on: Mayo 09, 2018 at 11:00
*
* Copyright (c) - OMW - 2018
*/
package com.televisa.comer.integration.service.implementation;

import com.televisa.comer.integration.model.beans.ResponseBean;
import com.televisa.comer.integration.model.beans.ResponseUpdDao;
import com.televisa.comer.integration.model.beans.pgm.EvetvIntServiceBitacoraTab;
import com.televisa.comer.integration.model.beans.pgm.OrdHeaderModulosTab;
import com.televisa.comer.integration.model.beans.pgm.OrdLinesModulosTab;
import com.televisa.comer.integration.model.beans.pgm.OrderModulosBean;
import com.televisa.comer.integration.model.daos.EntityMappedDao;
import com.televisa.comer.integration.model.daos.VentaModulosDao;
import com.televisa.comer.integration.service.beans.pgm.preemption.Campaign;
import com.televisa.comer.integration.service.beans.pgm.preemption.Channel;
import com.televisa.comer.integration.service.beans.pgm.preemption.CodRespuesta;
import com.televisa.comer.integration.service.beans.pgm.preemption.CommercialList;
import com.televisa.comer.integration.service.beans.pgm.preemption.ItemCabecera;
import com.televisa.comer.integration.service.beans.pgm.preemption.ItemRespuesta;
import com.televisa.comer.integration.service.beans.pgm.preemption.ListaMensaje;
import com.televisa.comer.integration.service.beans.pgm.preemption.RecibirDatosExternosResponse;
import com.televisa.comer.integration.service.beans.pgm.preemption.RecibirDatosExternosResult;
import com.televisa.comer.integration.service.beans.pgm.preemption.Spot;
import com.televisa.comer.integration.service.beans.types.EmailDestinationAddress;
import com.televisa.comer.integration.service.beans.types.EvetvIntConfigParamTabBean;
import com.televisa.comer.integration.service.beans.types.EvetvIntServicesLogBean;
import com.televisa.comer.integration.service.email.MailManagement;
import com.televisa.comer.integration.service.utils.UtilsIntegrationService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import java.sql.SQLException;

import java.text.DateFormat;
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
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

@WebService
public class IntegrationBsPreemtion{
    
    @Resource 
    private WebServiceContext loWsc;
    
    public IntegrationBsPreemtion() {
        super();
    }

    @WebMethod
    public RecibirDatosExternosResponse cancelarPases(@WebParam(name = "commercialList") 
                                                      CommercialList toCommercialList) {
        //################################################
        boolean                      lbSecurityFalg = false;
        boolean                      lbInitFalg = true;
        EntityMappedDao              loEntityMappedDao = new EntityMappedDao();
        String                       lsNomFile = "Preemtion-" + getId() + ".xml";
        String                       lsIndFileType = "REQUEST";
        String                       lsIndServiceType = "WsPreemtion";
        String                       lsIndEstatus = "A";
        String                       lsNomUserName = "neptuno";
        Integer                      liIdRequest = loEntityMappedDao.getMaxIdParadigm("RstRequest") + 1; 
        String                       lsIdService = loEntityMappedDao.getWsParadigmOrigin("WsPreemtion");
        RecibirDatosExternosResponse loResponse = new RecibirDatosExternosResponse();
        UtilsIntegrationService      loUtIn = new UtilsIntegrationService();
        OrderModulosBean             loOrderModulosBean = new OrderModulosBean();
        String                       lsFieldValidate = null;
        String                       lsFieldParent = null;
        String                       lsMainAction = null;
        boolean                      lbProcessParadigm = true;
        boolean                      lbProcessOrder = true;
        EvetvIntConfigParamTabBean   loError = new EvetvIntConfigParamTabBean();
        String                       lsMode = toCommercialList.getMode();
        List<Campaign>               laCampaign = toCommercialList.getCampaign();
        CodRespuesta                 loCodRespuesta = new CodRespuesta();        
        String                       lsMessageBitacora = "";      
        String                       lsIdServiceCopys = "0";
        List<OrdLinesModulosTab>     laOrdLinesModulosTabMail = new ArrayList<OrdLinesModulosTab>();
        try{
            ByteArrayOutputStream loBaos = new ByteArrayOutputStream();
            JAXB.marshal(toCommercialList, loBaos);
            InputStream           loFileXml = new ByteArrayInputStream(loBaos.toByteArray()); 
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
            System.out.println("Archivo no generado");
        }
        //################################################
        loEntityMappedDao.insertLogServicesRequest(liIdRequest, 
                                                   Integer.parseInt(lsIdService), 
                                                   "WsPreemtion", 
                                                   "neptuno"
                                                   );
        EvetvIntServicesLogBean loEvetvIntServicesLogBean = new EvetvIntServicesLogBean();
        loEvetvIntServicesLogBean.setLiIdLogServices(liIdRequest);
        loEvetvIntServicesLogBean.setLiIdService(Integer.parseInt(lsIdService));
        loEvetvIntServicesLogBean.setLiIndProcess(0);
        loEvetvIntServicesLogBean.setLsIndResponse("N");
        loEvetvIntServicesLogBean.setLsIndEstatus("A");
        loEvetvIntServicesLogBean.setLsAttribute9("WsPreemtion");
        loEvetvIntServicesLogBean.setLsAttribute10("Execution");
        loEvetvIntServicesLogBean.setLsAttribute15("neptuno");
        loEntityMappedDao.insertServicesLogWs(loEvetvIntServicesLogBean);

        String lsIndProcessR = 
            loEntityMappedDao.getGeneralParameterID("serviceRequest", "PROCESS_INTEGRATION");
        EvetvIntServiceBitacoraTab toEvetvIntServiceBitacoraTabR = new EvetvIntServiceBitacoraTab();
        toEvetvIntServiceBitacoraTabR.setLsIdLogServices(lsIdService);
        toEvetvIntServiceBitacoraTabR.setLsIdService(lsIdService);
        toEvetvIntServiceBitacoraTabR.setLsIndProcess(lsIndProcessR); //Tipo de Proceso
        toEvetvIntServiceBitacoraTabR.setLsNumEvtbProcessId("0");
        toEvetvIntServiceBitacoraTabR.setLsNumPgmProcessId("0");
        
        toEvetvIntServiceBitacoraTabR.setLsIndEvento("Solicitud Recibida para Servicio de Preemtion");
        loEntityMappedDao.insertBitacoraWs(toEvetvIntServiceBitacoraTabR);

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
                    //Obtener usuario y contraseña de la bd
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
                //System.out.println(lsResponse);
            }else{
                lbInitFalg = false;
            }
        }
    // FIN de Validar configuracion de Seguridad para este servicio

        //################################################
        if(lbInitFalg){
            boolean                      lbMode = loUtIn.isInAS(lsMode);
            boolean                      lbModeSize = loUtIn.validateLength(lsMode,1);
            boolean                      lbModeReq = loUtIn.isRequired(lsMode,"S");        
       
            if(lbMode && lbModeSize && lbModeReq){
                for(Campaign loCapaign : laCampaign){
                    lbProcessOrder = true;
                    ItemCabecera             loItemCabecera = new ItemCabecera();
                    List<OrdLinesModulosTab> laOrdLinesModulosTab = new ArrayList<OrdLinesModulosTab>();
                    boolean lbActionCamp = true;
                    boolean lbActionCampSize = true;
                    boolean lbActionCampReq = true;
                    boolean lbOrderSize = true;            
                    boolean lbOrderReq = true;                                  
                    
                    boolean lbOrdIdPgmSize = true;
                    boolean lbOrdIdPgmFormat = true;
                    boolean lbOrdIdPgmReq = true;
                    
                    boolean lbAgencySize = true;
                    boolean lbAdvertirserSize = true;             
                    boolean lbAdvertirserReq = true;
                    boolean lbInitialDateSize = true;
                    boolean lbInitialDateReq = true;                   
                    boolean lbEndDateSize = true;
                    boolean lbEndDateReq = true;
                    boolean lbMcSize = true;
                    boolean lbMcReq = true;
                    boolean lbRateCardSize = true;
                    boolean lbRateCardReq = true;
                    boolean lbTargetSize = true;
                    boolean lbTargetReq = true;

                    /*lbActionCamp = loUtIn.isInCc(loCapaign.getAction());  
                    lbActionCampSize = loUtIn.validateLength(loCapaign.getAction(),1);
                    lbActionCampReq = loUtIn.isRequired(loCapaign.getAction(),"S");
                    lbOrderSize = loUtIn.validateLength(loCapaign.getOrderID(),64);                
                    lbOrderReq = loUtIn.isRequired(loCapaign.getOrderID(),"S"); */                                  
                    
                    lbOrdIdPgmSize = loUtIn.validateLength(loCapaign.getOrdIdPgm(),64);
                    lbOrdIdPgmFormat = loUtIn.isNumeric(loCapaign.getOrdIdPgm());
                    lbOrdIdPgmReq = loUtIn.isRequired(loCapaign.getOrdIdPgm(),"S");
                    
                    /*lbAgencySize = loUtIn.validateLength(loCapaign.getAgencyID(),64);   
                    lbAdvertirserSize = loUtIn.validateLength(loCapaign.getAdvertirserID(),64);                
                    lbAdvertirserReq = loUtIn.isRequired(loCapaign.getAdvertirserID(),"S");
                    lbInitialDateSize = loUtIn.validateLength(loCapaign.getInitialDate(),10);
                    lbInitialDateReq = loUtIn.isRequired(loCapaign.getInitialDate(),"S");                      
                    lbEndDateSize = loUtIn.validateLength(loCapaign.getEndDate(),10);
                    lbEndDateReq = loUtIn.isRequired(loCapaign.getEndDate(),"S");                      
                    lbMcSize = loUtIn.validateLength(loCapaign.getMasterContract(),64);
                    lbMcReq = loUtIn.isRequired(loCapaign.getMasterContract(),"S");                      
                    lbRateCardSize = loUtIn.validateLength(loCapaign.getRateCard(),64);
                    lbRateCardReq = loUtIn.isRequired(loCapaign.getRateCard(),"S");                      
                    lbTargetSize = loUtIn.validateLength(loCapaign.getTargetID(),64);
                    lbTargetReq = loUtIn.isRequired(loCapaign.getTargetID(),"S");*/
                    
                    if(lbActionCamp && lbActionCampSize && lbActionCampReq && lbOrderSize && lbOrderReq && lbAgencySize &&
                        lbAdvertirserSize && lbAdvertirserReq && lbInitialDateSize && lbInitialDateReq && lbEndDateSize &&
                        lbEndDateReq && lbMcSize && lbMcReq && lbRateCardSize && lbRateCardReq && lbTargetSize && 
                       lbTargetReq && lbOrdIdPgmSize && lbOrdIdPgmFormat && lbOrdIdPgmReq
                    ){
                        OrdHeaderModulosTab loOrdHeaderModulosTab = new OrdHeaderModulosTab();
                        String lsActionCamp = loCapaign.getAction();   
                        lsMainAction = loCapaign.getAction();   
                        String lsOrderID = loCapaign.getOrderID();
                        String lsOrdIdPgm = loCapaign.getOrdIdPgm();
                        String lsAgencyID = loCapaign.getAgencyID();
                        String lsAdvertirserID = loCapaign.getAdvertirserID();
                        String lsInitialDate = loCapaign.getInitialDate().replace("-", "/");
                        String lsEndDate = loCapaign.getEndDate().replace("-", "/");
                        String lsMasterContract = loCapaign.getMasterContract();
                        String lsRateCard = loCapaign.getRateCard();
                        String lsTargetID = loCapaign.getTargetID();
                        
                        loOrdHeaderModulosTab.setLsOrdidEvetv(lsOrderID);//ORDID_EVETV
                        //ARRIBA EL ORDERID QUE VIENE EN EL REQUEST
                        loOrdHeaderModulosTab.setLsOrdid(lsOrdIdPgm);//ORDID (PARADIGM)
                        loOrdHeaderModulosTab.setLsAgyid(lsAgencyID);//AGYID
                        loOrdHeaderModulosTab.setLsAdvid(lsAdvertirserID);//ADVID
                        loOrdHeaderModulosTab.setLsStrdt(lsInitialDate);//STRDT
                        loOrdHeaderModulosTab.setLsEdt(lsEndDate);//EDT
                        loOrdHeaderModulosTab.setLsMcontid(lsMasterContract);//MCONTID
                        loOrdHeaderModulosTab.setLsRtcrddscr(lsRateCard);//RTCRDDSCR
                        loOrdHeaderModulosTab.setLsIdtarget(lsTargetID);//IDTARGET
                        loOrdHeaderModulosTab.setLsStnid("TVSA");//STNID MANEJAR CODIGO DURO TVSA
                        //loOrdHeaderModulosTab.setLsOrdid(lsOrderID);//ORDID DEJAR NULL? EN UY d NO SE ACTUALIZA
                        loOrdHeaderModulosTab.setLsStatus("1");//STATUS.- Esto se asigna despues?
                        //SIEMRPE ENCENDERLO A 1
                        loOrdHeaderModulosTab.setLsAction(lsActionCamp);//ACTION
                        //Validar OrderID(Paradigm) VS ACTION
                        //ResponseBean loRes = processHeaderParadigm(loOrdHeaderModulosTab);
                        //if(loRes.getLsResponse().equalsIgnoreCase("OK")){
                        if(true){//Aqui no se inserta encabezado (por ahora) es por eso que esta en true
                            loOrderModulosBean.setLoOrdHeaderModulosTab(loOrdHeaderModulosTab);
                            List<Channel> laChannel = loCapaign.getChannel();
                            for(Channel loChannel : laChannel){
    //                            String lsChannel = loChannel.getChannelID();
                                loChannel.getChannelID();
                            }
                            List<Spot> laSpot = loCapaign.getSpot();
                            int liCount = 1;
                            for(Spot loSpot : laSpot){
                                ItemRespuesta loItemRespuesta = new ItemRespuesta();
                                boolean lbAction        =       true;
                                boolean lbActionSize    =       true;
                                boolean lbActionReq     =       true;
                                boolean lbSpotIdSize    =       true;
                                boolean lbSpotIdReq     =       true;
                                boolean lbOrdLnIdPgmSize        =       true;
                                boolean lbOrdLnIdPgmReq =       true;
                                boolean lbOrdLnIdPgmFormat      =       true;
                                boolean lbDateSize      =       true;
                                boolean lbDateReq       =       true;
                                boolean lbBuyUnitIDSize =       true;
                                boolean lbBreakIDSize   =       true;
                                boolean lbBreakIDReq    =       true;
                                boolean lbHourSize      =       true;
                                boolean lbHourReq       =       true;
                                boolean lbDurationSize  =       true;
                                boolean lbDurationReq   =       true;
                                boolean lbDurationFormat        =       true;
                                boolean lbChannelIDSize =       true;
                                boolean lbChannelIDReq  =       true;
                                boolean lbSpotFormatIDSize      =       true;
                                boolean lbSpotFormatIDReq       =       true;
                                boolean lbSpotCommentsSize      =       true;
                                boolean lbMediaIDSize   =       true;
                                boolean lbProductIDSize =       true;
                                boolean lbProductIDReq  =       true;
                                boolean lbBookingPosSize        =       true;
                                boolean lbBookingPosReq =       true;
                                boolean lbBookingPos    =       true;
                                boolean lbSpotPriceSize =       true;
                                boolean lbSpotPriceReq  =       true;
                                boolean lbSpotPrice     =       true;
                                boolean lbRevenueSize   =       true;
                                boolean lbRevenueReq    =       true;
                                boolean lbRevenuePos    =       true;
                                boolean lbPrioritySize  =       true;
                                boolean lbPriorityReq   =       true;
                                boolean lbPriority      =       true;
                                boolean lbDigitalSize   =       true;
                                boolean lbDigitalReq    =       true;
                                boolean lbDigitalPos    =       true;
                                boolean lbPagaSize      =       true;
                                boolean lbPagaReq       =       true;
                                boolean lbPagaPos       =       true;
                                boolean lbRaStartTime   =       true;
                                boolean lbRaEndTime     =       true;
                                
                                lbSpotIdSize = loUtIn.validateLength(loSpot.getSpotID(),64);
                                lbSpotIdReq = loUtIn.isRequired(loSpot.getSpotID(),"S");       
                                
                                /*boolean lbAction = loUtIn.isInCc(loSpot.getAction());  
                                boolean lbActionSize = loUtIn.validateLength(loSpot.getAction(),1);
                                boolean lbActionReq = loUtIn.isRequired(loSpot.getAction(),"S");                        
                                
                                boolean lbSpotIdSize = loUtIn.validateLength(loSpot.getSpotID(),64);
                                boolean lbSpotIdReq = loUtIn.isRequired(loSpot.getSpotID(),"S");                        
                                
                                boolean lbOrdLnIdPgmSize = loUtIn.validateLength(loSpot.getOrdLnIdPgm(),64);//no existe
                                boolean lbOrdLnIdPgmReq = loUtIn.isRequired(loSpot.getOrdLnIdPgm(),"S"); // no existe
                                boolean lbOrdLnIdPgmFormat = loUtIn.isNumeric(loSpot.getOrdLnIdPgm());//no existe
                                 
                                boolean lbDateSize = loUtIn.validateLength(loSpot.getDate(),10);
                                boolean lbDateReq = loUtIn.isRequired(loSpot.getDate(),"S");                        
                                //boolean lbBuyUnitIDSize = loUtIn.validateLength(loSpot.getBuyUnitID(),64);
                                boolean lbBuyUnitIDSize = true; //Puede ser null
                                boolean lbBreakIDSize = loUtIn.validateLength(loSpot.getBreakID(),64);
                                boolean lbBreakIDReq = loUtIn.isRequired(loSpot.getBreakID(),"S");                        
                                boolean lbHourSize = loUtIn.validateLength(loSpot.getHour(),8);
                                boolean lbHourReq = loUtIn.isRequired(loSpot.getHour(),"S");                        
                                boolean lbDurationSize = loUtIn.validateLength(loSpot.getDuration(),8);
                                boolean lbDurationReq = loUtIn.isRequired(loSpot.getDuration(),"S");
                                boolean lbDurationFormat = loUtIn.isFormatSchedule(loSpot.getDuration());                        
                                boolean lbChannelIDSize = loUtIn.validateLength(loSpot.getChannelID(),64);
                                boolean lbChannelIDReq = loUtIn.isRequired(loSpot.getChannelID(),"S");                       
                                boolean lbSpotFormatIDSize = loUtIn.validateLength(loSpot.getSpotFormatID(),64);
                                boolean lbSpotFormatIDReq = loUtIn.isRequired(loSpot.getSpotFormatID(),"S");                                                  
                                boolean lbSpotCommentsSize = loUtIn.validateLength(loSpot.getSpotComments(),256);                        
                               
                                boolean lbMediaIDSize = loUtIn.validateLength(loSpot.getMediaID(),64);                        
                                
                                boolean lbProductIDSize = loUtIn.validateLength(loSpot.getProductID(),64);
                                boolean lbProductIDReq = loUtIn.isRequired(loSpot.getProductID(),"S");                        
                                
                                boolean lbBookingPosSize = loUtIn.validateLength(loSpot.getBookingPos(),1);
                                boolean lbBookingPosReq = loUtIn.isRequired(loSpot.getBookingPos(),"S");
                                boolean lbBookingPos = loUtIn.isInSN(loSpot.getBookingPos());                          
                                
                                boolean lbSpotPriceSize = loUtIn.validateLength(loSpot.getBookingPos(),13);
                                boolean lbSpotPriceReq = loUtIn.isRequired(loSpot.getBookingPos(),"S");
                                boolean lbSpotPrice = loUtIn.isPriceFormat(loSpot.getSpotPrice());                          
                                
                                boolean lbRevenueSize = loUtIn.validateLength(loSpot.getRevenue(),1);
                                boolean lbRevenueReq = loUtIn.isRequired(loSpot.getRevenue(),"S");
                                boolean lbRevenuePos = loUtIn.isInSN(loSpot.getRevenue());                          
                                boolean lbPrioritySize = loUtIn.validateLength(loSpot.getPriority(),1);
                                boolean lbPriorityReq = loUtIn.isRequired(loSpot.getPriority(),"S");
                                boolean lbPriority = loUtIn.isNumericUnique(loSpot.getPriority());                          
                                boolean lbDigitalSize = loUtIn.validateLength(loSpot.getDigital(),1);
                                boolean lbDigitalReq = loUtIn.isRequired(loSpot.getDigital(),"S");
                                boolean lbDigitalPos = loUtIn.isInSN(loSpot.getDigital());                           
                                boolean lbPagaSize = loUtIn.validateLength(loSpot.getPaga(),1);
                                boolean lbPagaReq = loUtIn.isRequired(loSpot.getPaga(),"S");
                                boolean lbPagaPos = loUtIn.isInSN(loSpot.getPaga());                       
                                
                                boolean lbRaStartTime = loUtIn.validateLength(loSpot.getRangeAllowedStartTime(),8);
                                boolean lbRaEndTime = loUtIn.validateLength(loSpot.getRangeAllowedEndTime(),8);*/
                                
                                
                                if(lbAction && lbActionSize && lbActionReq && lbSpotIdSize && lbSpotIdReq && 
                                    lbDateSize && lbDateReq && lbBuyUnitIDSize && lbBreakIDSize && lbBreakIDReq && 
                                    lbHourSize && lbHourReq && lbDurationSize && lbDurationReq && 
                                    lbDurationFormat && lbChannelIDSize && lbChannelIDReq && lbSpotFormatIDSize && 
                                    lbSpotFormatIDReq && lbSpotCommentsSize && lbMediaIDSize && lbProductIDSize && 
                                    lbProductIDReq && lbBookingPosSize && lbBookingPosReq && lbBookingPos && 
                                    lbSpotPriceSize && lbSpotPriceReq && lbSpotPrice &&
                                    lbRevenueSize && lbRevenueReq && lbRevenuePos && lbPrioritySize && 
                                    lbPriorityReq && lbPriority && lbDigitalSize && lbDigitalReq && 
                                    lbDigitalPos && lbPagaSize && lbPagaReq && lbPagaPos 
                                   && lbRaStartTime &&lbRaEndTime && lbOrdLnIdPgmSize && lbOrdLnIdPgmReq && 
                                   lbOrdLnIdPgmFormat
                                ){
                                    OrdLinesModulosTab loOrdLinesModulosTab = new OrdLinesModulosTab();                  
                                    String lsAction = loSpot.getAction();
                                    String lsSpotID = loSpot.getSpotID();
                                    
                                    String lsOrdLnIdPgm = loSpot.getOrdLnIdPgm();
                                    String lsOrdLnNumPgm = loSpot.getOrdLnNumPgm();
                                    
                                    String lsDate = loSpot.getDate().replace("-", "/");
                                    String lsBuyUnitID = loSpot.getBuyUnitID();//
                                    String lsBreakID = loSpot.getBreakID();
                                    String lsHour = loSpot.getHour();
                                    String lsDuration = loSpot.getDuration();
                                    String lsChannelID = loSpot.getChannelID();
                                    String lsSpotFormatID = loSpot.getSpotFormatID();
                                    //String lsSpotComments = loSpot.getSpotComments();
                                    String lsMediaID = loSpot.getMediaID();
                                    String lsProductID = loSpot.getProductID();
                                    String lsBookingPos = loSpot.getBookingPos();
                                    String lsSpotPrice = loSpot.getSpotPrice();
                                    String lsRevenue = loSpot.getRevenue();
                                    String lsPriority = loSpot.getPriority();
                                    String lsDigital = loSpot.getDigital();
                                    String lsPaga = loSpot.getPaga();
                                    String lsRangeAllowedStartTime = loSpot.getRangeAllowedStartTime();
                                    String lsRangeAllowedEndTime = loSpot.getRangeAllowedEndTime();
                                    
                                    loOrdLinesModulosTab.setLsOrdidEvetv(lsOrderID);//ORDID_EVETV.- No puedo mapear, no se cual es. ES EL QUE VIENE
                                    loOrdLinesModulosTab.setLsSptmstidEvetv(lsSpotID);//SPTMSTID_EVETV.- No puedo mapear, no se cual es. SPOTID DE ELLOS SpotID
                                    
                                    loOrdLinesModulosTab.setLsOrdid(lsOrdIdPgm);//ORDID (Paradigm)
                                    loOrdLinesModulosTab.setLsOrdlnnum(lsOrdLnNumPgm);//ORDLNNUM.- puede ser null
                                    loOrdLinesModulosTab.setLsOrdlnid(lsOrdLnIdPgm);//ORDLNID
                                    
                                    loOrdLinesModulosTab.setLsBcstdt(lsDate);//BCSTDT
                                    loOrdLinesModulosTab.setLsBuyuntid(lsBuyUnitID);//BUYUNTID
                                    loOrdLinesModulosTab.setLsBrkdtid(lsBreakID);//BRKDTID
                                    loOrdLinesModulosTab.setLsHour(lsHour);//HOUR
                                    loOrdLinesModulosTab.setLsDuration(lsDuration);//DURATION
                                    loOrdLinesModulosTab.setLsStnid(lsChannelID);//STNID
                                    loOrdLinesModulosTab.setLsUsrchr(lsSpotFormatID); //USRCHR.- No puedo mapear, no se cual es. SpotFormatID
                                    loOrdLinesModulosTab.setLsAutoid(lsMediaID); //AUTOID.- No puedo mapear, no se cual es MediaID
                                    loOrdLinesModulosTab.setLsBrnd(lsProductID); //BRND.- No puedo mapear, no se cual es, MARCA?? ProductID
                                    loOrdLinesModulosTab.setLsPosesp(lsBookingPos);//POSESP.- No puedo mapear, no se cual es BookingPos
                                    loOrdLinesModulosTab.setLsSptrt(lsSpotPrice);//SPTRT
                                    loOrdLinesModulosTab.setLsRevsts(lsRevenue);//REVSTS
                                    loOrdLinesModulosTab.setLsPriority(lsPriority);//PRIORITY
                                    loOrdLinesModulosTab.setLsDigital(lsDigital);//DIGITAL
                                    loOrdLinesModulosTab.setLsPaga(lsPaga);//PAGA
                                    loOrdLinesModulosTab.setLsRangeAllowedStartTime(lsRangeAllowedStartTime);//RangeAllowedStartTime
                                    loOrdLinesModulosTab.setLsRangeAllowedEndTime(lsRangeAllowedEndTime);//RangeAllowedEndTime
                                    
                                    //loOrdLinesModulosTab.setLsSptmstid(lsSpotID);//SPTMSTID. NULL CUANDO ES NUEVO####$$$$$
                                    loOrdLinesModulosTab.setLsStatus("1");//STATUS.- Lo inserto como "1"?
                                    //loOrdLinesModulosTab.setLsOrdlnid(null);//ORDLNID.- Acaso es . ES NULLO
                                    loOrdLinesModulosTab.setLsAction(lsAction);//ACTION.- No puedo mapear, no se cual es
                                    
//==>                               //Aqui viene el spot
                                    System.out.println("*********processLinesParadigm......*******");
                                    ResponseBean loResLin = processLinesParadigm(loOrdLinesModulosTab);
                                                                        
                                    System.out.println("Validar linea ["+loOrdLinesModulosTab.getLsSptmstidEvetv()+"] Resultado("+loResLin.getLsResponse()+")");
                                    if(loResLin.getLsResponse().equalsIgnoreCase("OK")){
                                        laOrdLinesModulosTab.add(loOrdLinesModulosTab);
                                    }else{
                                        lbProcessParadigm = false;
                                        //JLBSlbProcessOrder = false;
                                        lsFieldParent = "Spot";
                                        loItemRespuesta.setElemento("BreakID: " + lsBreakID + " SpotID: " + lsSpotID);
                                        loItemRespuesta.setResultado("KO");      
                                        lsFieldValidate = "ORDID_EVETV-SPTMSTID_EVETV";
                                        
                                        //Obtener el idspot de paradigm, en base a orden y spot de evetv
                                        VentaModulosDao loVtaModulosDao = new VentaModulosDao();
                                        String lsSptmstidParadigm = loOrdLinesModulosTab.getLsSptmstidEvetv();
                                            //loVtaModulosDao.getSpotIdParadigmPreemption(loOrdLinesModulosTab.getLsOrdidEvetv(), 
                                              //                                loOrdLinesModulosTab.getLsSptmstidEvetv());
                                        if(lsSptmstidParadigm != null){
                                            lsFieldValidate = "SpotParadigm: " + lsSptmstidParadigm;
                                        }
                                        ListaMensaje laLmes = new ListaMensaje();
                                        laLmes.setIdError("20");
                                        laLmes.setDescripcion("[" + lsFieldParent + "] " +
                                                              loResLin.getLsMessageResponse()+
                                                              " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                        loItemCabecera.getItemRespuesta().add(loItemRespuesta);
                                        //JLBSloCodRespuesta.getItemCabecera().add(loItemCabecera);  
                                    }
                                }else{
                                    lsFieldParent = "Spot";
                                    String lsSpotID = loSpot.getSpotID() == null ? "0" : loSpot.getSpotID();
                                    String lsBreakID = loSpot.getBreakID() == null ? "0" : loSpot.getBreakID();
                                    loItemRespuesta.setElemento("BreakID: " + lsBreakID + " SpotID: " + lsSpotID);
                                    loItemRespuesta.setResultado("KO");      
                                    lsFieldValidate = "Action";
                                    if(!lbAction){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("DataValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                              loError.getLsIndDescParameter() + " [" + lsFieldValidate + "]");
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
                                    lsFieldValidate = "SpotID";
                                    if(!lbSpotIdSize){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("SizeValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                              loError.getLsIndDescParameter() + 
                                                              " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    if(!lbSpotIdReq){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("RequiredValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                              loError.getLsIndDescParameter() + 
                                                              " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    
                                    lsFieldValidate = "OrdLnIdPgm";
                                    if(!lbOrdLnIdPgmSize){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("SizeValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                              loError.getLsIndDescParameter() + 
                                                              " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    if(!lbOrdLnIdPgmReq){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("RequiredValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                              loError.getLsIndDescParameter() + 
                                                              " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    if(!lbOrdLnIdPgmFormat){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("FormatValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                              loError.getLsIndDescParameter() + 
                                                              " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    lsFieldValidate = "Date";
                                    if(!lbDateSize){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("SizeValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                              loError.getLsIndDescParameter() + 
                                                              " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    if(!lbDateReq){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("RequiredValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                              loError.getLsIndDescParameter() + 
                                                              " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    lsFieldValidate = "BuyUnitID";
                                    if(!lbBuyUnitIDSize){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("SizeValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                              loError.getLsIndDescParameter() + 
                                                              " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
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
                                        laLmes.setDescripcion("[" + lsFieldParent + 
                                                          "] " + loError.getLsIndDescParameter() + 
                                                          " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    lsFieldValidate = "Hour";
                                    if(!lbHourSize){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("SizeValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + 
                                                          "] " + loError.getLsIndDescParameter() + 
                                                          " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    if(!lbHourReq){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("RequiredValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + 
                                                              "] " + loError.getLsIndDescParameter() + 
                                                              " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    lsFieldValidate = "Duration";
                                    if(!lbDurationSize){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("SizeValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                    laLmes.setDescripcion("[" + lsFieldParent + 
                                                          "] " + loError.getLsIndDescParameter() + 
                                                          " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    if(!lbDurationReq){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("RequiredValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + 
                                                          "] " + loError.getLsIndDescParameter() + 
                                                          " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    if(!lbDurationFormat){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("FormatValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                    laLmes.setDescripcion("[" + lsFieldParent + 
                                                          "] " + loError.getLsIndDescParameter() + 
                                                          " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    lsFieldValidate = "ChannelID";
                                    if(!lbChannelIDSize){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("SizeValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + 
                                                          "] " + loError.getLsIndDescParameter() + 
                                                          " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    if(!lbChannelIDReq){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("RequiredValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + 
                                                          "] " + loError.getLsIndDescParameter() + 
                                                          " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    lsFieldValidate = "SpotFormatID";
                                    if(!lbSpotFormatIDSize){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("SizeValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + 
                                                          "] " + loError.getLsIndDescParameter() + 
                                                          " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    if(!lbSpotFormatIDReq){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("RequiredValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + 
                                                          "] " + loError.getLsIndDescParameter() + 
                                                          " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    lsFieldValidate = "SpotComments";
                                    if(!lbSpotCommentsSize){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("SizeValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + 
                                                          "] " + loError.getLsIndDescParameter() + 
                                                          " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    lsFieldValidate = "MediaID";
                                    if(!lbMediaIDSize){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("SizeValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + 
                                                          "] " + loError.getLsIndDescParameter() + 
                                                          " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    lsFieldValidate = "ProductID";
                                    if(!lbProductIDSize){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("SizeValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + 
                                                          "] " + loError.getLsIndDescParameter() + 
                                                          " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    if(!lbProductIDReq){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("RequiredValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + 
                                                          "] " + loError.getLsIndDescParameter() + 
                                                          " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    lsFieldValidate = "BookingPos";
                                    if(!lbBookingPosSize){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("SizeValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + 
                                                          "] " + loError.getLsIndDescParameter() + 
                                                          " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    if(!lbBookingPosReq){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("RequiredValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + 
                                                          "] " + loError.getLsIndDescParameter() + 
                                                          " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    if(!lbBookingPos){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("DataValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + 
                                                          "] " + loError.getLsIndDescParameter() + 
                                                          " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }                            
                                    lsFieldValidate = "SpotPrice";
                                    if(!lbSpotPriceSize){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("SizeValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + 
                                                          "] " + loError.getLsIndDescParameter() + 
                                                          " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    if(!lbSpotPriceReq){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("RequiredValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + 
                                                          "] " + loError.getLsIndDescParameter() + 
                                                          " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    if(!lbSpotPrice){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("FormatValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + 
                                                          "] " + loError.getLsIndDescParameter() + 
                                                          " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }                            
                                    lsFieldValidate = "Revenue";
                                    if(!lbRevenueSize){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("SizeValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + 
                                                          "] " + loError.getLsIndDescParameter() + 
                                                          " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    if(!lbRevenueReq){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("RequiredValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + 
                                                          "] " + loError.getLsIndDescParameter() + 
                                                          " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    if(!lbRevenuePos){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("DataValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + 
                                                          "] " + loError.getLsIndDescParameter() + 
                                                          " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    lsFieldValidate = "Priority";
                                    if(!lbPrioritySize){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("SizeValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + 
                                                          "] " + loError.getLsIndDescParameter() + 
                                                          " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    if(!lbPriorityReq){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("RequiredValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + 
                                                          "] " + loError.getLsIndDescParameter() + 
                                                          " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    if(!lbPriority){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("DataValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + 
                                                          "] " + loError.getLsIndDescParameter() + 
                                                          " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    lsFieldValidate = "Digital";
                                    if(!lbDigitalSize){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("SizeValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + 
                                                          "] " + loError.getLsIndDescParameter() + 
                                                          " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    if(!lbDigitalReq){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("RequiredValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + 
                                                          "] " + loError.getLsIndDescParameter() + 
                                                          " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    if(!lbDigitalPos){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("DataValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + 
                                                          "] " + loError.getLsIndDescParameter() + 
                                                          " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    lsFieldValidate = "Paga";
                                    if(!lbPagaSize){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("SizeValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + 
                                                              "] " + loError.getLsIndDescParameter() + 
                                                              " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    if(!lbPagaReq){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("RequiredValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + 
                                                              "] " + loError.getLsIndDescParameter() + 
                                                              " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    if(!lbPagaPos){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("DataValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + 
                                                              "] " + loError.getLsIndDescParameter() + 
                                                              " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    
                                    lsFieldValidate = "RangeAllowedStartTime";
                                    if(!lbRaStartTime){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("SizeValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + 
                                                              "] " + loError.getLsIndDescParameter() + 
                                                              " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    lsFieldValidate = "RangeAllowedEndTime";
                                    if(!lbRaEndTime){
                                        ListaMensaje laLmes = new ListaMensaje();
                                        loError = getMessageErrDb("SizeValidate");
                                        laLmes.setIdError(loError.getLsIndValueParameter());
                                        laLmes.setDescripcion("[" + lsFieldParent + 
                                                              "] " + loError.getLsIndDescParameter() + 
                                                              " [" + lsFieldValidate + "]");
                                        loItemRespuesta.getListaMensaje().add(laLmes);
                                    }
                                    
                                    loItemCabecera.getItemRespuesta().add(loItemRespuesta);
                                    //JLBSloCodRespuesta.getItemCabecera().add(loItemCabecera);  
                                }
                                liCount++;
                            } // Fin de Ciclo de lectura de Spots // al llegar a este punto, ya se ha insertado en la BD
                            loOrderModulosBean.setLaOrdLinesModulosTab(laOrdLinesModulosTab);
                            //########### Invocar Procedimientos de Jacobo #################
                            //System.out.println("########### Invocar Procedimientos de Jacobo ("+lbProcessOrder+") ################# ["+new Date()+"]");
                            //lbProcessOrder .- Indica que los datos fueron insertados en la tabla, para este caso no me sirve
                            /*if(lbProcessOrder){
                                
                                //#############################################
                                String lsIndProcessCtrl = 
                                    loEntityMappedDao.getGeneralParameterID("InsertCtrlTable", "PROCESS_INTEGRATION");
                                EvetvIntServiceBitacoraTab toEvetvIntServiceBitacoraTabCtrl = new EvetvIntServiceBitacoraTab();
                                toEvetvIntServiceBitacoraTabCtrl.setLsIdLogServices(lsIdService);
                                toEvetvIntServiceBitacoraTabCtrl.setLsIdService(lsIdService);
                                toEvetvIntServiceBitacoraTabCtrl.setLsIndProcess(lsIndProcessCtrl); //Tipo de Proceso
                                toEvetvIntServiceBitacoraTabCtrl.setLsNumEvtbProcessId("0");
                                toEvetvIntServiceBitacoraTabCtrl.setLsNumPgmProcessId("0");
                                toEvetvIntServiceBitacoraTabCtrl.setLsIndEvento("Tablas de Control Actualizadas para Cancelacion-Preemption");
                                loEntityMappedDao.insertBitacoraWs(toEvetvIntServiceBitacoraTabCtrl);
                                //#############################################
                                boolean         lbSpPgm = true;
                                String          lsMessSpPgm = "";
                                VentaModulosDao loVtaModulosDao = new VentaModulosDao();                                                                                                            
                                
                                lsIdServiceCopys = lsIdService;
                                String lsOrderIdNeptuno = loOrderModulosBean.getLoOrdHeaderModulosTab().getLsOrdidEvetv();

                                //####### Lineas(al finalizar, responder a evetv ##########                 
                                Integer liOrderId = 
                                    Integer.parseInt(lsOrderIdNeptuno);
                                //####### Respuesta a eVeTV ########## 
                                try {
                                    System.out.println("Llamando a callProcedurePreemption: ");
                                    loVtaModulosDao.callProcedurePreemption(liOrderId);
                                    lsMessageBitacora = " llamando a callProcedurePreemption para orden " +
                                                        loOrderModulosBean.getLoOrdHeaderModulosTab().getLsOrdidEvetv() + 
                                                        " de EveTV";
                                } catch (SQLException loExLin) {
                                    System.out.println("ERROR en callProcedureLines: " + loExLin.getMessage());
                                    lsMessageBitacora = "ERROR: " + loExLin.getMessage();
                                    lsMessSpPgm += loExLin.getMessage();
                                    lbSpPgm = false;
                                }
                                String lsIndProcess = loEntityMappedDao.getGeneralParameterID("callProcedurePreemption", "PROCESS_INTEGRATION");
                                EvetvIntServiceBitacoraTab toEvetvIntServiceBitacoraTabLin = new EvetvIntServiceBitacoraTab();
                                toEvetvIntServiceBitacoraTabLin.setLsIdLogServices(lsIdService);
                                toEvetvIntServiceBitacoraTabLin.setLsIdService(lsIdService);
                                toEvetvIntServiceBitacoraTabLin.setLsIndProcess(lsIndProcess); //Tipo de Proceso
                                toEvetvIntServiceBitacoraTabLin.setLsNumEvtbProcessId("0");
                                toEvetvIntServiceBitacoraTabLin.setLsNumPgmProcessId("0");
                                
                                toEvetvIntServiceBitacoraTabLin.setLsIndEvento(lsMessageBitacora);
                                loEntityMappedDao.insertBitacoraWs(toEvetvIntServiceBitacoraTabLin);
                                System.out.println("Validacion de Procedimientos ["+lbSpPgm+"]");                     
                                //####### Validar resultado de ejecucion de SP en bd Paradigm ########## 
                                if(lbSpPgm){
                                    lbProcessParadigm = true;    
                                }else{//Alguno de los 2 SP fallo, realizar un rollback cuando se hayan insertado lineas
                                    //###################################
                                    //Verificar si Action nivel Campaign es "I"
                                    
                                    //if(lsMainAction.equalsIgnoreCase("I")){
                                    //    loVtaModulosDao.rollbackOrdLinesModulosTab(lsOrderID);
                                    //    loVtaModulosDao.rollbackOrdHeaderModulosTab(lsOrderID);
                                    //}
                                    //###################################
                                    //loRes.getLsResponse().equalsIgnoreCase("ERROR").- Error
                                    lbProcessParadigm = false;
                                    lbProcessOrder = false;
                                    lsFieldParent = "Campaign";                        
                                    loItemCabecera.setProcessID(lsOrderID);
                                    loItemCabecera.setResultado("KO");
                                    loItemCabecera.setTipoProceso("OnLine");
                                    
                                    ItemRespuesta loIitemRespuesta = new ItemRespuesta();
                                    
                                    loIitemRespuesta.setElemento("OrderID: " + lsOrderID);
                                    loIitemRespuesta.setIdElemento(lsOrderID);
                                    loIitemRespuesta.setResultado("KO");      
                                    lsFieldValidate = "OrderID";
                                    
                                    ListaMensaje laLmes = new ListaMensaje();
                                    laLmes.setIdError("10");
                                    laLmes.setDescripcion("[" + lsFieldParent + "] " + lsMessSpPgm +
                                                          " [" + lsFieldValidate + "]");
                                    loIitemRespuesta.getListaMensaje().add(laLmes);
                                    
                                    loItemCabecera.getItemRespuesta().add(loIitemRespuesta);
                                    //JLBSloCodRespuesta.getItemCabecera().add(loItemCabecera);
                                }
                                //loCodRespuesta.getItemCabecera().add(loItemCabecera);
                            }*/
                            System.out.println("########### Fin de Procesos de BD2 ["+new Date()+"]");
                        }
                        else{
                            //fallo en validacion de processHeaderParadigm
                            lbProcessParadigm = false;
                            lbProcessOrder = false;
                            lsFieldParent = "Campaign";                        
                            loItemCabecera.setProcessID(lsOrderID);
                            loItemCabecera.setResultado("KO");
                            loItemCabecera.setTipoProceso("OnLine");
                            
                            ItemRespuesta itemRespuesta = new ItemRespuesta();
                            
                            itemRespuesta.setElemento("OrderID: " + lsOrderID);
                            itemRespuesta.setIdElemento(lsOrderID);
                            itemRespuesta.setResultado("KO");      
                            lsFieldValidate = "OrderID";
                            
                            ListaMensaje laLmes = new ListaMensaje();
                            laLmes.setIdError("10");
                            laLmes.setDescripcion("[" + lsFieldParent + "] " + "Error General" +
                                                  " [" + lsFieldValidate + "]");
                            itemRespuesta.getListaMensaje().add(laLmes);
                            
                            loItemCabecera.getItemRespuesta().add(itemRespuesta);
                            //JLBSloCodRespuesta.getItemCabecera().add(loItemCabecera);
                        }
                        //loCodRespuesta.getItemCabecera().add(loItemCabecera);                    
                        
                    }else{//Validacion a nivel Header - Campaign
                        lbProcessParadigm = false;
                        lbProcessOrder = false;
                        lsFieldParent = "Campaign";
                        String lsOrderID = 
                            toCommercialList.getCampaign().get(0) == null ? "0" : 
                            toCommercialList.getCampaign().get(0).getOrderID();            
                        loItemCabecera.setProcessID(lsOrderID);
                        loItemCabecera.setResultado("KO");
                        loItemCabecera.setTipoProceso("OnLine");
                        ItemRespuesta loItemRespuesta = new ItemRespuesta();
                        loItemRespuesta.setElemento("OrderID: " + lsOrderID);
                        loItemRespuesta.setIdElemento(lsOrderID);
                        loItemRespuesta.setResultado("KO");      
                        lsFieldValidate = "Action";
                        if(!lbActionCamp){
                            ListaMensaje laLmes = new ListaMensaje();
                            loError = getMessageErrDb("DataValidate");
                            laLmes.setIdError(loError.getLsIndValueParameter());
                            laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                  loError.getLsIndDescParameter() + " [" + lsFieldValidate + "]");
                            loItemRespuesta.getListaMensaje().add(laLmes);
                        }
                        if(!lbActionCampSize){
                            ListaMensaje laLmes = new ListaMensaje();
                            loError = getMessageErrDb("SizeValidate");
                            laLmes.setIdError(loError.getLsIndValueParameter());
                            laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                  loError.getLsIndDescParameter() + " [" + lsFieldValidate + "]");
                            loItemRespuesta.getListaMensaje().add(laLmes); 
                        }
                        if(!lbActionCampReq){
                            ListaMensaje laLmes = new ListaMensaje();
                            loError = getMessageErrDb("RequiredValidate");
                            laLmes.setIdError(loError.getLsIndValueParameter());
                            laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                  loError.getLsIndDescParameter() + " [" + lsFieldValidate + "]");
                            loItemRespuesta.getListaMensaje().add(laLmes);  
                        }
                        lsFieldValidate = "OrderID";
                        if(!lbOrderSize){
                            ListaMensaje laLmes = new ListaMensaje();
                            loError = getMessageErrDb("SizeValidate");
                            laLmes.setIdError(loError.getLsIndValueParameter());
                            laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                  loError.getLsIndDescParameter() + " [" + lsFieldValidate + "]");
                            loItemRespuesta.getListaMensaje().add(laLmes);
                        }
                        if(!lbOrderReq){
                            ListaMensaje laLmes = new ListaMensaje();
                            loError = getMessageErrDb("RequiredValidate");
                            laLmes.setIdError(loError.getLsIndValueParameter());
                            laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                  loError.getLsIndDescParameter() + " [" + lsFieldValidate + "]");
                            loItemRespuesta.getListaMensaje().add(laLmes); 
                        }
                        
                        lsFieldValidate = "OrdIdPgm";
                        if(!lbOrdIdPgmSize){
                            ListaMensaje laLmes = new ListaMensaje();
                            loError = getMessageErrDb("SizeValidate");
                            laLmes.setIdError(loError.getLsIndValueParameter());
                            laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                  loError.getLsIndDescParameter() + " [" + lsFieldValidate + "]");
                            loItemRespuesta.getListaMensaje().add(laLmes);
                        }
                        if(!lbOrdIdPgmReq){
                            ListaMensaje laLmes = new ListaMensaje();
                            loError = getMessageErrDb("RequiredValidate");
                            laLmes.setIdError(loError.getLsIndValueParameter());
                            laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                  loError.getLsIndDescParameter() + " [" + lsFieldValidate + "]");
                            loItemRespuesta.getListaMensaje().add(laLmes); 
                        }
                        if(!lbOrdIdPgmFormat){
                            ListaMensaje laLmes = new ListaMensaje();
                            loError = getMessageErrDb("FormatValidate");
                            laLmes.setIdError(loError.getLsIndValueParameter());
                            laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                  loError.getLsIndDescParameter() + " [" + lsFieldValidate + "]");
                            loItemRespuesta.getListaMensaje().add(laLmes); 
                        }
                        
                        
                        lsFieldValidate = "AgencyID";
                        if(!lbAgencySize){
                            ListaMensaje laLmes = new ListaMensaje();
                            loError = getMessageErrDb("SizeValidate");
                            laLmes.setIdError(loError.getLsIndValueParameter());
                            laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                  loError.getLsIndDescParameter() + " [" + lsFieldValidate + "]");
                            loItemRespuesta.getListaMensaje().add(laLmes);  
                        }
                        lsFieldValidate = "AdvertirserID";
                        if(!lbAdvertirserSize){
                            ListaMensaje laLmes = new ListaMensaje();
                            loError = getMessageErrDb("SizeValidate");
                            laLmes.setIdError(loError.getLsIndValueParameter());
                            laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                  loError.getLsIndDescParameter() + " [" + lsFieldValidate + "]");
                            loItemRespuesta.getListaMensaje().add(laLmes); 
                        }
                        if(!lbAdvertirserReq){
                            ListaMensaje laLmes = new ListaMensaje();
                            loError = getMessageErrDb("RequiredValidate");
                            laLmes.setIdError(loError.getLsIndValueParameter());
                            laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                  loError.getLsIndDescParameter() + " [" + lsFieldValidate + "]");
                            loItemRespuesta.getListaMensaje().add(laLmes); 
                        }
                        lsFieldValidate = "InitialDate";
                        if(!lbInitialDateSize){
                            ListaMensaje laLmes = new ListaMensaje();
                            loError = getMessageErrDb("SizeValidate");
                            laLmes.setIdError(loError.getLsIndValueParameter());
                            laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                  loError.getLsIndDescParameter() + " [" + lsFieldValidate + "]");
                            loItemRespuesta.getListaMensaje().add(laLmes); 
                        }
                        if(!lbInitialDateReq){
                            ListaMensaje laLmes = new ListaMensaje();
                            loError = getMessageErrDb("RequiredValidate");
                            laLmes.setIdError(loError.getLsIndValueParameter());
                            laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                  loError.getLsIndDescParameter() + " [" + lsFieldValidate + "]");
                            loItemRespuesta.getListaMensaje().add(laLmes);
                        }
                        lsFieldValidate = "EndDate";
                        if(!lbEndDateSize){
                            ListaMensaje laLmes = new ListaMensaje();
                            loError = getMessageErrDb("SizeValidate");
                            laLmes.setIdError(loError.getLsIndValueParameter());
                            laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                  loError.getLsIndDescParameter() + " [" + lsFieldValidate + "]");
                            loItemRespuesta.getListaMensaje().add(laLmes);  
                        }
                        if(!lbEndDateReq){
                            ListaMensaje laLmes = new ListaMensaje();
                            loError = getMessageErrDb("RequiredValidate");
                            laLmes.setIdError(loError.getLsIndValueParameter());
                            laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                  loError.getLsIndDescParameter() + " [" + lsFieldValidate + "]");
                            loItemRespuesta.getListaMensaje().add(laLmes); 
                        }
                        lsFieldValidate = "MasterContract";
                        if(!lbMcSize){
                            ListaMensaje laLmes = new ListaMensaje();
                            loError = getMessageErrDb("SizeValidate");
                            laLmes.setIdError(loError.getLsIndValueParameter());
                            laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                  loError.getLsIndDescParameter() + " [" + lsFieldValidate + "]");
                            loItemRespuesta.getListaMensaje().add(laLmes);  
                        }
                        if(!lbMcReq){
                            ListaMensaje laLmes = new ListaMensaje();
                            loError = getMessageErrDb("RequiredValidate");
                            laLmes.setIdError(loError.getLsIndValueParameter());
                            laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                  loError.getLsIndDescParameter() + " [" + lsFieldValidate + "]");
                            loItemRespuesta.getListaMensaje().add(laLmes);  
                        }
                        lsFieldValidate = "RateCard";
                        if(!lbRateCardSize){
                            ListaMensaje laLmes = new ListaMensaje();
                            loError = getMessageErrDb("SizeValidate");
                            laLmes.setIdError(loError.getLsIndValueParameter());
                            laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                  loError.getLsIndDescParameter() + " [" + lsFieldValidate + "]");
                            loItemRespuesta.getListaMensaje().add(laLmes);  
                        }
                        if(!lbRateCardReq){
                            ListaMensaje laLmes = new ListaMensaje();
                            loError = getMessageErrDb("RequiredValidate");
                            laLmes.setIdError(loError.getLsIndValueParameter());
                            laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                  loError.getLsIndDescParameter() + " [" + lsFieldValidate + "]");
                            loItemRespuesta.getListaMensaje().add(laLmes);  
                        }
                        lsFieldValidate = "TargetID";
                        if(!lbTargetSize){
                            ListaMensaje laLmes = new ListaMensaje();
                            loError = getMessageErrDb("SizeValidate");
                            laLmes.setIdError(loError.getLsIndValueParameter());
                            laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                  loError.getLsIndDescParameter() + " [" + lsFieldValidate + "]");
                            loItemRespuesta.getListaMensaje().add(laLmes); 
                        }
                        if(!lbTargetReq){
                            ListaMensaje laLmes = new ListaMensaje();
                            loError = getMessageErrDb("RequiredValidate");
                            laLmes.setIdError(loError.getLsIndValueParameter());
                            laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                                  loError.getLsIndDescParameter() + " [" + lsFieldValidate + "]");
                            loItemRespuesta.getListaMensaje().add(laLmes);
                        }
                        loItemCabecera.getItemRespuesta().add(loItemRespuesta);
                        //JLBSloCodRespuesta.getItemCabecera().add(loItemCabecera);
                    } //FIN de Validacion a nivel Spot   
                    
                    //----------- validacion de bandera lbProcessOrder si es KO ya esta agregado, en caso contrario
                    //----------- es KO agregar respuesta correcta a response
                    if(lbProcessOrder){
                        //Armar cadena de lo que realmente se inserto en las lineas
                        String lsLineCondition = "";
                        for(OrdLinesModulosTab loLineOk : laOrdLinesModulosTab){
                            lsLineCondition += "'" + loLineOk.getLsSptmstidEvetv() + "',";
                        }
                        if(lsLineCondition.length() > 1){
                            lsLineCondition = lsLineCondition.substring(0, lsLineCondition.length()-1);
                        }
                        
                        VentaModulosDao     loVentaModulosDao = new VentaModulosDao();
                        OrdHeaderModulosTab loOrdHeaderModulosTab = new OrdHeaderModulosTab();
                        loOrdHeaderModulosTab.setLsOrdidEvetv(loCapaign.getOrderID());
                        //OrdHeaderModulosTab loOrdHeaderModulosTabRes = 
                        //    loVentaModulosDao.getHeaderByOrdIdEvetv(loOrdHeaderModulosTab);
                        //ItemCabecera loItemCabecera = new ItemCabecera();
                        loItemCabecera.setProcessID("OrdIdEveTv: " + loCapaign.getOrderID()+ 
                                                  " OrdIdParadigm: " + loCapaign.getOrdIdPgm());
                        loItemCabecera.setResultado("OK");
                        loItemCabecera.setTipoProceso("OnLine");
                        List<Spot> laSpot = loCapaign.getSpot();              
                        //System.out.println(" ");
                        for(Spot loSpot : laSpot){
                        //for(OrdLinesModulosTab loLineOk : laOrdLinesModulosTab){
                            //System.out.print("iterando spot: "+loSpot.getSpotID());
                            OrdLinesModulosTab loOrdLinesModulosTab = new OrdLinesModulosTab();
                            loOrdLinesModulosTab.setLsOrdidEvetv(loCapaign.getOrderID());
                            loOrdLinesModulosTab.setLsSptmstidEvetv(loSpot.getSpotID());
                            loOrdLinesModulosTab.setLsAction(loSpot.getAction());
                            //loOrdLinesModulosTab.setLsSptmstidEvetv(loLineOk.getLsSptmstidEvetv());
                            //loOrdLinesModulosTab.setLsAction(loLineOk.getLsAction());
                            Integer liRes = 
                                loVentaModulosDao.getPreemptionByKeyIdExist(loOrdLinesModulosTab, lsLineCondition);
                            System.out.print("Action["+loSpot.getAction()+"] Procesado??["+liRes+"] ");
                            //System.out.print("Action["+loLineOk.getLsAction()+"] Procesado??["+liRes+"] ");
                            if(liRes > 0){
                                //System.out.print(" obtiene loOrdLinesModulosTabRes ");
                                OrdLinesModulosTab loOrdLinesModulosTabRes = 
                                    loVentaModulosDao.getPreemptionByKeyId(loOrdLinesModulosTab);
                                //System.out.print(" OK obtenido sett para mail ");
                                laOrdLinesModulosTabMail.add(loOrdLinesModulosTabRes);
                                //System.out.print(" OK MAIL ");
                                ItemRespuesta loItemRespuesta = new ItemRespuesta();
                                loItemRespuesta.setIdElemento(loOrdLinesModulosTabRes.getLsSptmstidEvetv());
                                loItemRespuesta.setElemento("SpotIdEveTv: " + loOrdLinesModulosTabRes.getLsSptmstidEvetv() + 
                                                          " SpotIdParadigm: " + loOrdLinesModulosTabRes.getLsSptmstid());
                                String lsResultado = loOrdLinesModulosTabRes.getLsStatus().equalsIgnoreCase("E") ? "KO" : "OK";
                                //System.out.print("SpotIdEveTv: " + loOrdLinesModulosTabRes.getLsSptmstidEvetv() + 
                                //                          " SpotIdParadigm: " + loOrdLinesModulosTabRes.getLsSptmstid()+
                                //                 " Resultado: "+lsResultado);
                                loItemRespuesta.setResultado(lsResultado); 
                                loItemCabecera.getItemRespuesta().add(loItemRespuesta);
                            }else{
                                System.out.print(" NO EXISTE LINEA");
                            }
                            System.out.println(" ");
                        }
                        //JLBSloCodRespuesta.getItemCabecera().add(loItemCabecera);  
                        /*AQUI NO SE MANDAN COPYs
                        if(lsMainAction.equalsIgnoreCase("I")){//Ejecutar copys solo si Action grnrral es I
                            // Proceso en Seguno plano
                            executeCopysProcedure(loCapaign.getOrderID(), lsIdServiceCopys, loCapaign.getOrderID());
                            String lsIndProcess = loEntityMappedDao.getGeneralParameterID("callProcedureCopys", 
                                                                                          "PROCESS_INTEGRATION");
                            EvetvIntServiceBitacoraTab toEvetvIntServiceBitacoraTabLin = new EvetvIntServiceBitacoraTab();
                            toEvetvIntServiceBitacoraTabLin.setLsIdLogServices(lsIdServiceCopys);
                            toEvetvIntServiceBitacoraTabLin.setLsIdService(lsIdServiceCopys);
                            toEvetvIntServiceBitacoraTabLin.setLsIndProcess(lsIndProcess); //Tipo de Proceso
                            toEvetvIntServiceBitacoraTabLin.setLsNumEvtbProcessId("0");
                            toEvetvIntServiceBitacoraTabLin.setLsNumPgmProcessId("0");
                            toEvetvIntServiceBitacoraTabLin.setLsIndEvento(lsMessageBitacora);
                            loEntityMappedDao.insertBitacoraWs(toEvetvIntServiceBitacoraTabLin);
                            
                        }
*/
                    }else{
                        System.out.println("**** Orden KO *********: "+loCapaign.getOrderID());
                    }
                    //JLBS_NUEVO
                    loCodRespuesta.getItemCabecera().add(loItemCabecera);                                       
                }// Fin de Iteracion entre Campaign
            }else{//Validacion a nivel superior header Fallida
                lbProcessParadigm = false;
                ItemCabecera loItemCabecera = new ItemCabecera();
                String lsOrderID = 
                    toCommercialList.getCampaign().get(0) == null ? "0" : 
                    toCommercialList.getCampaign().get(0).getOrderID();            
                loItemCabecera.setProcessID(lsOrderID);
                loItemCabecera.setResultado("KO");
                loItemCabecera.setTipoProceso("OnLine");
                ItemRespuesta loItemRespuesta = new ItemRespuesta();
                loItemRespuesta.setElemento("OrderID: " + lsOrderID);
                loItemRespuesta.setIdElemento(lsOrderID);
                loItemRespuesta.setResultado("KO");      
                lsFieldValidate = "Mode";
                lsFieldParent = "";
                if(!lbMode){
                    ListaMensaje laLmes = new ListaMensaje();
                    loError = getMessageErrDb("DataValidate");
                    laLmes.setIdError(loError.getLsIndValueParameter());
                    laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                          loError.getLsIndDescParameter() + " [" + lsFieldValidate + "]");
                    loItemRespuesta.getListaMensaje().add(laLmes);
                }
                if(!lbModeSize){
                    ListaMensaje laLmes = new ListaMensaje();
                    loError = getMessageErrDb("SizeValidate");
                    laLmes.setIdError(loError.getLsIndValueParameter());
                    laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                          loError.getLsIndDescParameter() + " [" + lsFieldValidate + "]");
                    loItemRespuesta.getListaMensaje().add(laLmes);
                }
                if(!lbModeReq){
                    ListaMensaje laLmes = new ListaMensaje();
                    loError = getMessageErrDb("RequiredValidate");
                    laLmes.setIdError(loError.getLsIndValueParameter());
                    laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                          loError.getLsIndDescParameter() + " [" + lsFieldValidate + "]");
                    loItemRespuesta.getListaMensaje().add(laLmes);
                }
                loItemCabecera.getItemRespuesta().add(loItemRespuesta);
                
                loCodRespuesta.getItemCabecera().add(loItemCabecera);
            } //FIN de Validacion a nivel superior header Fallida
        }else{//Autenticacion fallida
            lbProcessParadigm = false;
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
            String       lsMess = "Credenciales no permitidas, verifique Username y Password";  
            ListaMensaje laLmes = new ListaMensaje();
            laLmes.setIdError("0");
            laLmes.setDescripcion("[" + lsFieldParent + "] " + lsMess + " [" + lsFieldValidate + "]");
            loItemRespuesta.getListaMensaje().add(laLmes);
            loItemCabecera.getItemRespuesta().add(loItemRespuesta);
            System.out.println("ADD 08");
            loCodRespuesta.getItemCabecera().add(loItemCabecera);
        }//FIN de Autenticacion fallida 
        
        RecibirDatosExternosResult loRecibirDatosExternosResult = new RecibirDatosExternosResult();
        loRecibirDatosExternosResult.setCodRespuesta(loCodRespuesta);
        loResponse.setRecibirDatosExternosResult(loRecibirDatosExternosResult);
        //###############################################################################################
        try{
            String lsIndProcessRes = 
                loEntityMappedDao.getGeneralParameterID("FinishResponseNeptuno", "PROCESS_INTEGRATION");
            EvetvIntServiceBitacoraTab toEvetvIntServiceBitacoraTabRes = new EvetvIntServiceBitacoraTab();
            toEvetvIntServiceBitacoraTabRes.setLsIdLogServices(lsIdService);
            toEvetvIntServiceBitacoraTabRes.setLsIdService(lsIdService);
            toEvetvIntServiceBitacoraTabRes.setLsIndProcess(lsIndProcessRes);
            toEvetvIntServiceBitacoraTabRes.setLsNumEvtbProcessId("0");
            toEvetvIntServiceBitacoraTabRes.setLsNumPgmProcessId("0");
            toEvetvIntServiceBitacoraTabRes.setLsIndEvento("Respuesta a Cliente para Cancelacion de Pases");
            loEntityMappedDao.insertBitacoraWs(toEvetvIntServiceBitacoraTabRes);
            
            lsNomFile = "Preemtion-" + getId() + ".xml";
            lsIndFileType = "RESPONSE";
            lsIndServiceType = "WsPreemtion";
            lsIndEstatus = "A";
            lsNomUserName = "neptuno";        
            ByteArrayOutputStream loBaosRes = new ByteArrayOutputStream();
            JAXB.marshal(loResponse, loBaosRes);
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
        }catch(Exception loEx){
            System.out.println("Archivo Response no generado");
        }
        //###############################################################################################
        if(laOrdLinesModulosTabMail.size() > 0){
            try{
                String         lsIndProcessRes = 
                    loEntityMappedDao.getGeneralParameterID("SendEmail", "PROCESS_INTEGRATION");
                EvetvIntServiceBitacoraTab toEvetvIntServiceBitacoraTabRes = new EvetvIntServiceBitacoraTab();
                toEvetvIntServiceBitacoraTabRes.setLsIdLogServices(lsIdService);
                toEvetvIntServiceBitacoraTabRes.setLsIdService(lsIdService);
                toEvetvIntServiceBitacoraTabRes.setLsIndProcess(lsIndProcessRes);
                toEvetvIntServiceBitacoraTabRes.setLsNumEvtbProcessId("0");
                toEvetvIntServiceBitacoraTabRes.setLsNumPgmProcessId("0");
                toEvetvIntServiceBitacoraTabRes.setLsIndEvento("Enviando email del Procesamiento de Cancelacion Preemtion");
                loEntityMappedDao.insertBitacoraWs(toEvetvIntServiceBitacoraTabRes);
                String         lsSubject = 
                    loEntityMappedDao.getGeneralParameter("SubjectPreemption", "INTEGRATION_EMAIL");
                String         lsTypeMail = "MAIL_OK";
                List<EmailDestinationAddress> loEmailDestinationAddress = 
                    loEntityMappedDao.getDestinationAddress(lsIdService, lsTypeMail);
                MailManagement loMailManagement = new MailManagement();
                loMailManagement.sendEmailVentaModulos(lsSubject, 
                                                       loEmailDestinationAddress, 
                                                       laOrdLinesModulosTabMail
                                                       );
                
            }catch(Exception loEx){
                System.out.println("No es Posible enviar correo");
            }
        }
        //###############################################################################################
        return loResponse;
    }
        
    /**
     * Ejecuta validaciones por cada campo ingresado en el request xml del cliente
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
     * Procesa linea, valida e inserta en base de datos 
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdLinesModulosTab
     * @return ResponseBean
     */
    private ResponseBean processLinesParadigm(OrdLinesModulosTab toOrdLinesModulosTab){
        ResponseBean    loResponse = new ResponseBean();
        boolean         lbFalgPrcess = false;
        VentaModulosDao loVtaModulosDao = new VentaModulosDao();
        //ResponseBean    loRb = loVtaModulosDao.validateExistLines(toOrdLinesModulosTab);
        Integer         liExe = 0;
        //if(loRb.getLsResponse().equalsIgnoreCase("OK")){// No existe en bd
        if(true){
            //Validar tipo de ACTION
            //if(toOrdLinesModulosTab.getLsAction().equalsIgnoreCase("C")){
                lbFalgPrcess = true;
            //}else{
              //  loResponse.setLsResponse("ERROR");
                //loResponse.setLsType("IntegrityProcess");
//                loResponse.setLsMessageResponse("Elemento Action debe ser C - Cancelacion");
  //              lbFalgPrcess = false;
    //        }
        }
        if(lbFalgPrcess){   
            UtilsIntegrationService loUtilsInt = new UtilsIntegrationService();
            if(loUtilsInt.isInCc(toOrdLinesModulosTab.getLsAction())){
                System.out.println("Si ha pasado la validacion de ACTION");
                liExe = loVtaModulosDao.insertLinesPreemptionTab(toOrdLinesModulosTab);
            }
            if(liExe == 0){
                loResponse.setLsResponse("ERROR");
                loResponse.setLsType("NotInsert");
                loResponse.setLsMessageResponse("La Linea de la Orden No fue Procesada");
            }else{
                //Invocar SP por spot...........
                ResponseUpdDao loResponseDao = new ResponseUpdDao();
                try {
                    System.out.println("Llamando a callProcedurePreemption: ");
                    Integer liSptmstidPgm = Integer.parseInt(toOrdLinesModulosTab.getLsSptmstidEvetv());
                    loResponseDao = loVtaModulosDao.callProcedurePreemption(liSptmstidPgm);
                    if(loResponseDao.getLsResponse().equalsIgnoreCase("OK")){
                        loResponse.setLsResponse("OK");
                        loResponse.setLsType("InsertOKProcess");
                        loResponse.setLsMessageResponse("Linea-SpotId Procesado Correctamente [" +
                                                        liSptmstidPgm +
                                                        "][callProcedurePreemption-OK]");
                    }else{
                        loResponse.setLsResponse("ERROR");
                        loResponse.setLsType("ExeSp");
                        loResponse.setLsMessageResponse(loResponseDao.getLsMessage());
                    }
                } catch (SQLException loExLin) {
                    System.out.println("ERROR en callProcedurePreemption: " + loExLin.getMessage());
                    loResponse.setLsResponse("ERROR");
                    loResponse.setLsType("NotInsert");
                    loResponse.setLsMessageResponse("La Linea-SpotId No fue Procesada "+loExLin.getMessage());
                }                                
                
            }
        }
        return loResponse;
    }
       
    /**
     * Genera en base al momento en tiempo una clave de identificacion
     * @autor Jorge Luis Bautista Santiago
     * @return String
     */
    private String getId(){
        String     lsResponse = null;
        DateFormat loDf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        lsResponse = loDf.format(new java.util.Date(System.currentTimeMillis()));
        return lsResponse;
    }
    
}
