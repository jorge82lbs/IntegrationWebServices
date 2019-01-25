/**
* Project: Integraton Paradigm - EveTV
*
* File: IntegrationBsVentaModulos.java
*
* Created on: Septiembre 23, 2017 at 11:00
*
* Copyright (c) - OMW - 2017
*/
package com.televisa.comer.integration.service.implementation;

import com.televisa.comer.integration.model.beans.ResponseBean;
import com.televisa.comer.integration.model.beans.pgm.EvetvIntServiceBitacoraTab;
import com.televisa.comer.integration.model.beans.pgm.OrdHeaderModulosTab;
import com.televisa.comer.integration.model.beans.pgm.OrdLinesModulosTab;
import com.televisa.comer.integration.model.beans.pgm.OrderModulosBean;
import com.televisa.comer.integration.model.daos.EntityMappedDao;
import com.televisa.comer.integration.model.daos.VentaModulosDao;
import com.televisa.comer.integration.service.beans.pgm.ventamodulos.Campaign;
import com.televisa.comer.integration.service.beans.pgm.ventamodulos.Channel;
import com.televisa.comer.integration.service.beans.pgm.ventamodulos.CodRespuesta;
import com.televisa.comer.integration.service.beans.pgm.ventamodulos.CommercialList;
import com.televisa.comer.integration.service.beans.pgm.ventamodulos.ItemCabecera;
import com.televisa.comer.integration.service.beans.pgm.ventamodulos.ItemRespuesta;
import com.televisa.comer.integration.service.beans.pgm.ventamodulos.ListaMensaje;
import com.televisa.comer.integration.service.beans.pgm.ventamodulos.RecibirDatosExternosResponse;
import com.televisa.comer.integration.service.beans.pgm.ventamodulos.RecibirDatosExternosResult;
import com.televisa.comer.integration.service.beans.pgm.ventamodulos.Spot;
import com.televisa.comer.integration.service.beans.types.EmailDestinationAddress;
import com.televisa.comer.integration.service.beans.types.EvetvIntConfigParamTabBean;
import com.televisa.comer.integration.service.beans.types.EvetvIntServicesLogBean;
import com.televisa.comer.integration.service.email.MailManagement;
import com.televisa.comer.integration.service.interfaces.VentaModulosInterface;
import com.televisa.comer.integration.service.jobs.VentaModulosCopys;
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

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/** Servicio Web expuesto para Venta Por Modulos
 * en la capa de servicio
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Septiembre 23, 2017, 12:00 pm
 */
@WebService
public class IntegrationBsVentaModulos implements VentaModulosInterface{

    @Resource 
    private WebServiceContext loWsc;
    
    /**
     * Ejecuta actualizacion y carga de Venta de Modulos de Neptuno a Paradigm
     * @autor Jorge Luis Bautista Santiago
     * @param toCommercialList
     * @return RecibirDatosExternosResponse
     */
    @Override
    @WebMethod
    
    public RecibirDatosExternosResponse actualizarCargaeVeTV(@WebParam(name = "commercialList") 
                                                             CommercialList toCommercialList) {
        //################################################
        boolean                      lbSecurityFalg = false;
        boolean                      lbInitFalg = true;
        EntityMappedDao              loEntityMappedDao = new EntityMappedDao();
        String                       lsNomFile = "VtaModulos-" + getId() + ".xml";
        String                       lsIndFileType = "REQUEST";
        String                       lsIndServiceType = "WsVentaModulos";
        String                       lsIndEstatus = "A";
        String                       lsNomUserName = "neptuno";
        Integer                      liIdRequest = loEntityMappedDao.getMaxIdParadigm("RstRequest") + 1; 
        String                       lsIdService = loEntityMappedDao.getWsParadigmOrigin("WsVentaModulos");
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
        List<OrdLinesModulosTab> laOrdLinesModulosTabMail = new ArrayList<OrdLinesModulosTab>();
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
                                                   "WsVentaModulos", 
                                                   "neptuno"
                                                   );
        EvetvIntServicesLogBean loEvetvIntServicesLogBean = new EvetvIntServicesLogBean();
        loEvetvIntServicesLogBean.setLiIdLogServices(liIdRequest);
        loEvetvIntServicesLogBean.setLiIdService(Integer.parseInt(lsIdService));
        loEvetvIntServicesLogBean.setLiIndProcess(0);
        loEvetvIntServicesLogBean.setLsIndResponse("N");
        loEvetvIntServicesLogBean.setLsIndEstatus("A");
        loEvetvIntServicesLogBean.setLsAttribute9("WsVentaModulos");
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
        
        toEvetvIntServiceBitacoraTabR.setLsIndEvento("Solicitud Recibida para Servicio de Venta Por Modulos");
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
                    boolean lbActionCamp = loUtIn.isInIUD(loCapaign.getAction());  
                    boolean lbActionCampSize = loUtIn.validateLength(loCapaign.getAction(),1);
                    boolean lbActionCampReq = loUtIn.isRequired(loCapaign.getAction(),"S");
                    boolean lbOrderSize = loUtIn.validateLength(loCapaign.getOrderID(),64);                
                    boolean lbOrderReq = loUtIn.isRequired(loCapaign.getOrderID(),"S");                                    
                    boolean lbAgencySize = loUtIn.validateLength(loCapaign.getAgencyID(),64);   
                    boolean lbAdvertirserSize = loUtIn.validateLength(loCapaign.getAdvertirserID(),64);                
                    boolean lbAdvertirserReq = loUtIn.isRequired(loCapaign.getAdvertirserID(),"S");
                    boolean lbInitialDateSize = loUtIn.validateLength(loCapaign.getInitialDate(),10);
                    boolean lbInitialDateReq = loUtIn.isRequired(loCapaign.getInitialDate(),"S");                      
                    boolean lbEndDateSize = loUtIn.validateLength(loCapaign.getEndDate(),10);
                    boolean lbEndDateReq = loUtIn.isRequired(loCapaign.getEndDate(),"S");                      
                    boolean lbMcSize = loUtIn.validateLength(loCapaign.getMasterContract(),64);
                    boolean lbMcReq = loUtIn.isRequired(loCapaign.getMasterContract(),"S");                      
                    boolean lbRateCardSize = loUtIn.validateLength(loCapaign.getRateCard(),64);
                    boolean lbRateCardReq = loUtIn.isRequired(loCapaign.getRateCard(),"S");                      
                    boolean lbTargetSize = loUtIn.validateLength(loCapaign.getTargetID(),64);
                    boolean lbTargetReq = loUtIn.isRequired(loCapaign.getTargetID(),"S");  
                    
                    if(lbActionCamp && lbActionCampSize && lbActionCampReq && lbOrderSize && lbOrderReq && lbAgencySize &&
                        lbAdvertirserSize && lbAdvertirserReq && lbInitialDateSize && lbInitialDateReq && lbEndDateSize &&
                        lbEndDateReq && lbMcSize && lbMcReq && lbRateCardSize && lbRateCardReq && lbTargetSize && 
                       lbTargetReq
                    ){
                        OrdHeaderModulosTab loOrdHeaderModulosTab = new OrdHeaderModulosTab();
                        String lsActionCamp = loCapaign.getAction();   
                        lsMainAction = loCapaign.getAction();   
                        String lsOrderID = loCapaign.getOrderID();
                        String lsAgencyID = loCapaign.getAgencyID();
                        String lsAdvertirserID = loCapaign.getAdvertirserID();
                        String lsInitialDate = loCapaign.getInitialDate().replace("-", "/");
                        String lsEndDate = loCapaign.getEndDate().replace("-", "/");
                        String lsMasterContract = loCapaign.getMasterContract();
                        String lsRateCard = loCapaign.getRateCard();
                        String lsTargetID = loCapaign.getTargetID();
                        
                        loOrdHeaderModulosTab.setLsOrdidEvetv(lsOrderID);//ORDID_EVETV
                        //ARRIBA EL ORDERID QUE VIENE EN EL REQUEST
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
                        ResponseBean loRes = processHeaderParadigm(loOrdHeaderModulosTab);
                        if(loRes.getLsResponse().equalsIgnoreCase("OK")){
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
                                boolean lbAction = loUtIn.isInIUD(loSpot.getAction());  
                                boolean lbActionSize = loUtIn.validateLength(loSpot.getAction(),1);
                                boolean lbActionReq = loUtIn.isRequired(loSpot.getAction(),"S");                        
                                boolean lbSpotIdSize = loUtIn.validateLength(loSpot.getSpotID(),64);
                                boolean lbSpotIdReq = loUtIn.isRequired(loSpot.getSpotID(),"S");                        
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
                                boolean lbRaEndTime = loUtIn.validateLength(loSpot.getRangeAllowedEndTime(),8);
                                
                                
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
                                   && lbRaStartTime &&lbRaEndTime
                                ){
                                    OrdLinesModulosTab loOrdLinesModulosTab = new OrdLinesModulosTab();                  
                                    String lsAction = loSpot.getAction();
                                    String lsSpotID = loSpot.getSpotID();
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
                                    //loOrdLinesModulosTab.setLsOrdid(lsOrderID);//ORDID NULL
                                    //loOrdLinesModulosTab.setLsOrdlnnum(null);//ORDLNNUM.- No puedo mapear, no se cual es, NULL
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
                                        String lsSptmstidParadigm = 
                                            loVtaModulosDao.getSpotIdParadigm(loOrdLinesModulosTab.getLsOrdidEvetv(), 
                                                                              loOrdLinesModulosTab.getLsSptmstidEvetv());
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
    //EN "D" Y "U" NO INVOCAR SP DE COPYS
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
                            } // Fin de Ciclo de lectura de Spots 
                            loOrderModulosBean.setLaOrdLinesModulosTab(laOrdLinesModulosTab);
                            //########### Invocar Procedimientos de Jacobo #################
                            System.out.println("########### Invocar Procedimientos de Jacobo ("+lbProcessOrder+") ################# ["+new Date()+"]");
                            if(lbProcessOrder){
                                
                                //#############################################
                                String lsIndProcessCtrl = 
                                    loEntityMappedDao.getGeneralParameterID("InsertCtrlTable", "PROCESS_INTEGRATION");
                                EvetvIntServiceBitacoraTab toEvetvIntServiceBitacoraTabCtrl = new EvetvIntServiceBitacoraTab();
                                toEvetvIntServiceBitacoraTabCtrl.setLsIdLogServices(lsIdService);
                                toEvetvIntServiceBitacoraTabCtrl.setLsIdService(lsIdService);
                                toEvetvIntServiceBitacoraTabCtrl.setLsIndProcess(lsIndProcessCtrl); //Tipo de Proceso
                                toEvetvIntServiceBitacoraTabCtrl.setLsNumEvtbProcessId("0");
                                toEvetvIntServiceBitacoraTabCtrl.setLsNumPgmProcessId("0");
                                toEvetvIntServiceBitacoraTabCtrl.setLsIndEvento("Tablas de Control Actualizadas para Venta Por Modulos");
                                loEntityMappedDao.insertBitacoraWs(toEvetvIntServiceBitacoraTabCtrl);
                                //#############################################
                                boolean lbSpPgm = true;
                                String lsMessSpPgm = "";
                                VentaModulosDao loVtaModulosDao = new VentaModulosDao();                                                                                                            
                                
                                lsIdServiceCopys = lsIdService;
                                String lsIndProcess = 
                                    loEntityMappedDao.getGeneralParameterID("callProcedureHeader", "PROCESS_INTEGRATION");
                                
                                //####### Encabezado ########## 
                                String lsOrderIdNeptuno = loOrderModulosBean.getLoOrdHeaderModulosTab().getLsOrdidEvetv();
                                try {
                                    System.out.println("Lllamando acallProcedureHeader ");
                                    loVtaModulosDao.callProcedureHeader(loOrderModulosBean.getLoOrdHeaderModulosTab());
                                    lsMessageBitacora = " llamando a callProcedureHeader para orden " +
                                                            lsOrderIdNeptuno + 
                                                            " de EveTV";
                                } catch (SQLException loExEn) {
                                    System.out.println("ERROR en callProcedureHeader: " + loExEn.getMessage());
                                    lsMessageBitacora = "ERROR: " + loExEn.getMessage();
                                    lsMessSpPgm += loExEn.getMessage();
                                    lbSpPgm = false;
                                }
                                EvetvIntServiceBitacoraTab toEvetvIntServiceBitacoraTab = new EvetvIntServiceBitacoraTab();
                                toEvetvIntServiceBitacoraTab.setLsIdLogServices(lsIdService);
                                toEvetvIntServiceBitacoraTab.setLsIdService(lsIdService);
                                toEvetvIntServiceBitacoraTab.setLsIndProcess(lsIndProcess); //Tipo de Proceso
                                toEvetvIntServiceBitacoraTab.setLsNumEvtbProcessId("0");
                                toEvetvIntServiceBitacoraTab.setLsNumPgmProcessId("0");
                                
                                toEvetvIntServiceBitacoraTab.setLsIndEvento(lsMessageBitacora);
                                loEntityMappedDao.insertBitacoraWs(toEvetvIntServiceBitacoraTab);
                                
                                //####### Lineas(al finalizar, responder a evetv ##########                 
                                Integer liOrderId = 
                                    Integer.parseInt(lsOrderIdNeptuno);
                                //####### Respuesta a eVeTV ########## 
                                try {
                                    System.out.println("Llamando a callProcedureLines: ");
                                    loVtaModulosDao.callProcedureLines(liOrderId);
                                    lsMessageBitacora = " llamando a callProcedureLines para orden " +
                                                        loOrderModulosBean.getLoOrdHeaderModulosTab().getLsOrdidEvetv() + 
                                                        " de EveTV";
                                } catch (SQLException loExLin) {
                                    System.out.println("ERROR en callProcedureLines: " + loExLin.getMessage());
                                    lsMessageBitacora = "ERROR: " + loExLin.getMessage();
                                    lsMessSpPgm += loExLin.getMessage();
                                    lbSpPgm = false;
                                }
                                lsIndProcess = loEntityMappedDao.getGeneralParameterID("callProcedureLines", "PROCESS_INTEGRATION");
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
                                    if(lsMainAction.equalsIgnoreCase("I")){
                                        loVtaModulosDao.rollbackOrdLinesModulosTab(lsOrderID);
                                        loVtaModulosDao.rollbackOrdHeaderModulosTab(lsOrderID);
                                    }
                                    
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
                            }
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
                            laLmes.setDescripcion("[" + lsFieldParent + "] " + loRes.getLsMessageResponse() +
                                                  " [" + lsFieldValidate + "]");
                            itemRespuesta.getListaMensaje().add(laLmes);
                            
                            loItemCabecera.getItemRespuesta().add(itemRespuesta);
                            //JLBSloCodRespuesta.getItemCabecera().add(loItemCabecera);
                        }
                        //loCodRespuesta.getItemCabecera().add(loItemCabecera);                    
                        
                    }else{//Validacion a nivel Spot
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
                        OrdHeaderModulosTab loOrdHeaderModulosTabRes = 
                            loVentaModulosDao.getHeaderByOrdIdEvetv(loOrdHeaderModulosTab);
                        //ItemCabecera loItemCabecera = new ItemCabecera();        
                        loItemCabecera.setProcessID("OrdIdEveTv: " + loCapaign.getOrderID()+ 
                                                  " OrdIdParadigm: " + loOrdHeaderModulosTabRes.getLsOrdid());
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
                                loVentaModulosDao.getOrdLineByKeyIdExist(loOrdLinesModulosTab, lsLineCondition);
                            System.out.print("Action["+loSpot.getAction()+"] Procesado??["+liRes+"] ");
                            //System.out.print("Action["+loLineOk.getLsAction()+"] Procesado??["+liRes+"] ");
                            if(liRes > 0){
                                //System.out.print(" obtiene loOrdLinesModulosTabRes ");
                                OrdLinesModulosTab loOrdLinesModulosTabRes = 
                                    loVentaModulosDao.getOrdLineByKeyId(loOrdLinesModulosTab);
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
            toEvetvIntServiceBitacoraTabRes.setLsIndEvento("Respuesta a Cliente para Venta Por Modulos");
            loEntityMappedDao.insertBitacoraWs(toEvetvIntServiceBitacoraTabRes);
            
            lsNomFile = "VtaModulos-" + getId() + ".xml";
            lsIndFileType = "RESPONSE";
            lsIndServiceType = "WsVentaModulos";
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
                toEvetvIntServiceBitacoraTabRes.setLsIndEvento("Enviando email del Procesamiento de Venta de Modulos");
                loEntityMappedDao.insertBitacoraWs(toEvetvIntServiceBitacoraTabRes);
                String         lsSubject = 
                    loEntityMappedDao.getGeneralParameter("SubjectVentaModulos", "INTEGRATION_EMAIL");
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
     * Procesa encabezado, valida e inserta en base de datos 
     * @autor Jorge Luis Bautista Santiago
     * @param toOrdHeaderModulosTab
     * @return ResponseBean
     */        
    private ResponseBean processHeaderParadigm(OrdHeaderModulosTab toOrdHeaderModulosTab){
        ResponseBean    loResponse = new ResponseBean();
        boolean         lbFalgPrcess = false;
        VentaModulosDao loVtaModulosDao = new VentaModulosDao();
        Integer         loRb = loVtaModulosDao.getHeaderCountByOrdIdEvetv(toOrdHeaderModulosTab);
        Integer         liExe = 0;
        if(loRb == 0){// No existe en bd
            //Validar tipo de ACTION
            if(toOrdHeaderModulosTab.getLsAction().equalsIgnoreCase("I")){
                lbFalgPrcess = true;
            }else{
                loResponse.setLsResponse("ERROR");
                loResponse.setLsType("IntegrityProcess");
                loResponse.setLsMessageResponse("Imposible Realizar Operacion debido a que la Orden No existe");
                lbFalgPrcess = false;
            }
        }else{
            //Validar tipo de ACTION
            if(toOrdHeaderModulosTab.getLsAction().equalsIgnoreCase("I")){
                String lsOrderId = loVtaModulosDao.getOrderIdParadigm(toOrdHeaderModulosTab.getLsOrdidEvetv());
                loResponse.setLsResponse("ERROR");
                loResponse.setLsType("IntegrityProcess");
                loResponse.setLsMessageResponse("Imposible Realizar Operacion Insertar debido a que la Orden " +
                    "Ya existe [" + lsOrderId + "]");
                lbFalgPrcess = false;
            }else{
                lbFalgPrcess = true;
            }
        }
        if(lbFalgPrcess){
            if(toOrdHeaderModulosTab.getLsAction().equalsIgnoreCase("I")){
                liExe = loVtaModulosDao.insertOrdHeaderModulosTab(toOrdHeaderModulosTab);    
            }else{
                liExe = loVtaModulosDao.updateOrdHeaderModulosTab(toOrdHeaderModulosTab);
            }
            if(liExe == 0){
                loResponse.setLsResponse("ERROR");
                loResponse.setLsType("NotInsert");
                loResponse.setLsMessageResponse("El Encabezado de la Orden No fue Insertado");
            }else{
                loResponse.setLsResponse("OK");
                loResponse.setLsType("InsertOKProcess");
                loResponse.setLsMessageResponse("Encabezado Insertado Correctamente [" +
                                                toOrdHeaderModulosTab.getLsOrdidEvetv() + "]");
            }
        }
        return loResponse;
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
        ResponseBean    loRb = loVtaModulosDao.validateExistLines(toOrdLinesModulosTab);
        Integer         liExe = 0;
        if(loRb.getLsResponse().equalsIgnoreCase("OK")){// No existe en bd
            //Validar tipo de ACTION
            if(toOrdLinesModulosTab.getLsAction().equalsIgnoreCase("I")){
                lbFalgPrcess = true;
            }else{
                loResponse.setLsResponse("ERROR");
                loResponse.setLsType("IntegrityProcess");
                loResponse.setLsMessageResponse("Imposible Realizar Operacion debido a que la Orden-Linea No existe");
                lbFalgPrcess = false;
            }
        }else{//La linea si esxiste en bd
            //Validar tipo de ACTION
            if(toOrdLinesModulosTab.getLsAction().equalsIgnoreCase("I")){
                loResponse.setLsResponse("ERROR");
                loResponse.setLsType("IntegrityProcess");
                loResponse.setLsMessageResponse("Imposible Realizar Operacion Insertar debido a que la Orden-Linea " +
                    "Ya existe");
                lbFalgPrcess = false;
            }else{
                lbFalgPrcess = true;
            }
        }
        if(lbFalgPrcess){         
            if(toOrdLinesModulosTab.getLsAction().equalsIgnoreCase("I")){
                liExe = loVtaModulosDao.insertOrdLinesModulosTab(toOrdLinesModulosTab);
            }else{
                liExe = loVtaModulosDao.updateOrdLinesModulosTab(toOrdLinesModulosTab);
            }
            if(liExe == 0){
                loResponse.setLsResponse("ERROR");
                loResponse.setLsType("NotInsert");
                loResponse.setLsMessageResponse("La Linea de la Orden No fue Procesada");
            }else{
                loResponse.setLsResponse("OK");
                loResponse.setLsType("InsertOKProcess");
                loResponse.setLsMessageResponse("Linea Procesada Correctamente [" +
                                                toOrdLinesModulosTab.getLsOrdidEvetv() +
                                                "][" + toOrdLinesModulosTab.getLsSptmstidEvetv() + "]");
            }
        }
        return loResponse;
    }
    
    /**
     * Invoca a proceso en segundo plano para procesar los copys
     * @autor Jorge Luis Bautista Santiago
     * @param tsOrderId
     * @param tsIdServiceCopys
     * @return void
     */
    private void executeCopysProcedure(String tsOrderId, String tsIdServiceCopys, String tsOrdId){
        Scheduler loScheduler;
        String    lsCopyId = "vmcopys-"+tsOrdId;
        System.out.println("Ejecutando cron copy: ["+lsCopyId+"]");
        try {
            loScheduler = new StdSchedulerFactory().getScheduler();
            JobDetail loJob = 
                JobBuilder.newJob(VentaModulosCopys.class).build();
            Trigger   loTrigger = 
                TriggerBuilder.newTrigger().withIdentity(lsCopyId).build();                                
            JobDataMap loJobDataMap=  loJob.getJobDataMap();
            loJobDataMap.put("lsOrderId", tsOrderId); 
            loJobDataMap.put("lsIdService", tsIdServiceCopys);                         
            loScheduler.scheduleJob(loJob, loTrigger);
            System.out.println("Ejecutando cron copy: ["+lsCopyId+"].......... start");
            loScheduler.start();
            
        } catch (Exception loEx) {
            System.out.println("Error al inicializar tareas " + loEx.getMessage());
           
        }
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
