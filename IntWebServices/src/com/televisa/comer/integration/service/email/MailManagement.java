/**
* Project: Integraton Paradigm - EveTV
*
* File: MailManagement.java
*
* Created on: Septiembre 23, 2017 at 11:00
*
* Copyright (c) - OMW - 2017
*/
package com.televisa.comer.integration.service.email;

import com.televisa.comer.integration.model.beans.pgm.EvetvLogComercialTab;
import com.televisa.comer.integration.model.beans.pgm.OrdLinesModulosTab;
import com.televisa.comer.integration.model.daos.EntityMappedDao;
import com.televisa.comer.integration.secman.email.SecmanDasEnviarCorreo;
import com.televisa.comer.integration.secman.email.SecmanDasEnviarCorreo_Service;
import com.televisa.comer.integration.secman.email.types.Mail;
import com.televisa.comer.integration.secman.email.types.MailAddress;
import com.televisa.comer.integration.secman.email.types.MailAddressCollection;
import com.televisa.comer.integration.secman.email.types.MailBody;
import com.televisa.comer.integration.secman.email.types.MailHeader;
import com.televisa.comer.integration.service.beans.types.EmailDestinationAddress;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceRef;

import weblogic.wsee.security.unt.ClientUNTCredentialProvider;

import weblogic.xml.crypto.wss.WSSecurityContext;
import weblogic.xml.crypto.wss.provider.CredentialProvider;

/** Clase administradora de envio de correos para Venta por Modulos y Log Comercial
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Septiembre 23, 2017, 12:00 pm
 */
public class MailManagement {
    public MailManagement() {
        super();
    }
    
    @WebServiceRef
    private static SecmanDasEnviarCorreo_Service loSecmanDasEnviarCorreo;
    
    /**
     * Obtiene dinamicamente la url del servicio web de correo
     * @autor Jorge Luis Bautista Santiago     
     * @return SecmanDasEnviarCorreo
     */
    public SecmanDasEnviarCorreo getDynaWebServiceURLEmail(){
        SecmanDasEnviarCorreo loSecmanDasSendMail = null;
        loSecmanDasEnviarCorreo =
                new SecmanDasEnviarCorreo_Service();
        QName                 loQname = loSecmanDasEnviarCorreo.getServiceName();
        URL                   loWsdlDocLocation;
        EntityMappedDao       loEntityMappedDao = new EntityMappedDao();
        String                lsName = "WsSecmanMail";
        String                lsUsedBy = "SECMAN_WS";
        String                lsDynaUrl = loEntityMappedDao.getGeneralParameter(lsName, lsUsedBy);
        if(!lsDynaUrl.equalsIgnoreCase("")){
            try{
                loWsdlDocLocation = new URL(lsDynaUrl);
                loSecmanDasEnviarCorreo.create(loWsdlDocLocation, loQname);
                loSecmanDasSendMail = loSecmanDasEnviarCorreo.getSecmanDasEnviarCorreoSoap12HttpPort();    
            } catch (MalformedURLException loEx) {
                System.out.println("01" + loEx.getMessage());
            }catch(Exception loEx){
                System.out.println("02" + loEx.getMessage());
            }
        }else{
            System.out.println("No se obtuvo WSDL del Servicio de Correo");
        }        
        return loSecmanDasSendMail;
    }
    
    /**
     * Envia correo correspondiente al resultado de Venta por Modulos
     * @autor Jorge Luis Bautista Santiago
     * @param tsSubject
     * @param toEmailDestinationAddress
     * @param toOrderModulosBean
     * @return boolean
     */
    public boolean sendEmailVentaModulosCopys(String tsSubject, 
                                         List<EmailDestinationAddress> toEmailDestinationAddress
                                        ) {
        boolean lbResponse = false;
        try{
            String lsHtml =
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/" +
                "xhtml1-strict.dtd\">\n" + 
                "	<html xmlns=\"http://www.w3.org/1999/xhtml\" dir=\"ltr\" lang=\"es-ES\">\n" + 
                "		<head>\n" + 
                "            <title>Venta de M&oacute;dulos</title>\n" + 
                "            <meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\" />\n" + 
                "            <meta name=\"language\" content=\"es\" />\n" + 
                "        </head>\n" + 
                "        <body>\n" + 
                "            <div style='background-color:#0F2F62; height:25px;background:linear-gradient(#040545," +
                "#0172AE);background:-webkit-linear-gradient(#040545, #0172AE);background: -ms-linear-background" +
                "(#040545, #0172AE);filter:progid:DXImageTransform.Microsoft.gradient(startColorStr='#040545'," +
                "EndColorStr='#0172AE');'></div>\n" + 
                "            <div style='background-color:#fff'>             \n" + 
                "            <br/>\n" + 
                "            <label style='font-family:Arial, Helvetica, sans-serif; font-size:16px; " +
                "font-weight:bold '>El Proceso de Integraci&oacute;n Para COPYS en Venta de M&oacutedulos ha " +
                "Finalizado</label><p/>\n" + 
                "            <br/>\n" + 
                "            <label style='font-family:Arial, Helvetica, sans-serif; font-size:12px;" +
            "font-weight:bold '>Integraci&oacute;n Paradigm-Neptuno, 2017 Televisa S.A. de C.V</label>  \n" + 
                "            <br/>\n" + 
                
                "            <div style='float:left; background-color:#F58220;width:7%;height:25px;background:-" +
                "moz-linear-gradient(#F1A345, #E17521); background:-webkit-linear-gradient(#F1A345, #E17521);" +
            "background: -ms-linear-background(#F1A345, #E17521);filter:progid:DXImageTransform.Microsoft.gradient(" +
                "startColorStr='#F1A345', EndColorStr='#E17521');'>\n" + 
                "			</div>             \n" + 
                "            <div style='float:left; ;width:86%;height:25px;background:linear-gradient(#040545," +
                "#0172AE);background:-webkit-linear-gradient(#040545, #0172AE);background: -ms-linear-background(" +
                "#040545, #0172AE);filter:progid:DXImageTransform.Microsoft.gradient(startColorStr='#040545'," +
                "EndColorStr='#0172AE');'>\n" + 
                "			</div>\n" + 
                "            <div style='float:left; background-color:#F58220;width:7%;height:25px;background:" +
                "-moz-linear-gradient(#F1A345, #E17521);background:-webkit-linear-gradient(#F1A345, #E17521);" +
            "background: -ms-linear-background(#F1A345, #E17521);filter:progid:DXImageTransform.Microsoft.gradient" +
                "(startColorStr='#F1A345', EndColorStr='#E17521');'></div>             \n" + 
                "		</body>\n" + 
                "	</html>";            
            SecmanDasEnviarCorreo loSecmanDasSendMail = getDynaWebServiceURLEmail();
            if(loSecmanDasSendMail != null){
                try{
                    Map<String, Object>   loRequestContext =
                        ((BindingProvider)loSecmanDasSendMail).getRequestContext();                    
                    setPortCredentialProviderList(loRequestContext);                    
                    Mail                  loEmail = new Mail();                    
                    MailHeader            loEmailHeader = new MailHeader();
                    MailAddress           loEmailAddresFrom = new MailAddress();                    
                    loEmailAddresFrom.setUserNameAdress("Paradigm-Neptuno Integration");
                    loEmailAddresFrom.setAddress("service_integration@televisa.com.mx");
                    loEmailHeader.setAddressFrom(loEmailAddresFrom);
                    MailAddressCollection loMailAddressCollection =
                        new MailAddressCollection();
                    for(EmailDestinationAddress loDest: toEmailDestinationAddress){                        
                        MailAddress           loEmailAdd = new MailAddress();
                        loEmailAdd.setUserNameAdress(loDest.getLsNameTo());
                        loEmailAdd.setAddress(loDest.getLsAddressTo());    
                        loMailAddressCollection.getMailAddress().add(loEmailAdd);
                    }
                    loEmailHeader.setTo(loMailAddressCollection);
                    loEmailHeader.setSubject(tsSubject);
                    loEmail.setMailHeader(loEmailHeader);
                    MailBody              loMailBody = new MailBody();
                    loMailBody.setBodyType("HTML");
                    loMailBody.setMessage(lsHtml);
                    loEmail.setMailBody(loMailBody);
                    loSecmanDasSendMail.enviarCorreo(loEmail);
                    lbResponse = true;
                } catch (Exception loException) {
                    lbResponse = false;
                }
            }
        } catch (Exception loException) {
            lbResponse = false;
        }
        return lbResponse;
    }
    
    /**
     * Envia correo correspondiente al resultado de Venta por Modulos
     * @autor Jorge Luis Bautista Santiago
     * @param tsSubject
     * @param toEmailDestinationAddress
     * @param toOrderModulosBean
     * @return boolean
     */
    public boolean sendEmailVentaModulos(String tsSubject, 
                                         List<EmailDestinationAddress> toEmailDestinationAddress,
                                         List<OrdLinesModulosTab> taOrdLinesModulosTabMail
                                        ) {
        boolean lbResponse = false;
        try{
            String lsHtml =
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/" +
                "xhtml1-strict.dtd\">\n" + 
                "       <html xmlns=\"http://www.w3.org/1999/xhtml\" dir=\"ltr\" lang=\"es-ES\">\n" + 
                "               <head>\n" + 
                "            <title>Venta de M&oacute;dulos</title>\n" + 
                "            <meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\" />\n" + 
                "            <meta name=\"language\" content=\"es\" />\n" + 
                "        </head>\n" + 
                "        <body>\n" + 
                "            <div style='background-color:#0F2F62; height:25px;background:linear-gradient(#040545," +
                " #0172AE);background:-webkit-linear-gradient(#040545, #0172AE);background: -ms-linear-background" +
                "(#040545, #0172AE);filter:progid:DXImageTransform.Microsoft.gradient(startColorStr='#040545'," +
                "EndColorStr='#0172AE');'></div>\n" + 
                "            <div style='background-color:#fff'>             \n" + 
                "            <br/>\n" + 
                "            <label style='font-family:Arial, Helvetica, sans-serif; font-size:16px; " +
                "font-weight:bold '>El Proceso de Integraci&oacute;n para Venta de M&oacutedulos ha Finalizado" +
                "</label><p/>\n" + 
                "                       <label style='font-family:Arial, Helvetica, sans-serif; font-size:12px;" +
            "font-weight:bold '>A continuaci&oacuten se encuentra el detalle del Resultado</label><p/>\n" + 
                "            <table cellpadding='0' cellspacing='0'  style='border-bottom-color:#F79646;" +
            "border-bottom-width:3px;border-bottom-style:solid;border-top-color:#F79646;border-top-style:solid;" +
            "border-top-width:3px; '>             \n" + 
                "                               <tr>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>OrdidEvetv</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>SptmstidEvetv</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Ordid</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Ordlnnum</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Bcstdt</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Buyuntid</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Brkdtid</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Hour</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Duration</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Stnid</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Usrchr</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Autoid</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Brnd</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Posesp</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Sptrt</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Revsts</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Priority</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Digital</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Paga</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Sptmstid</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Status</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Ordlnid</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C; " +
                "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Action</th>\n" + 
                
/*CH*/            "                                       <th height='10px' style='font-size:12px; background:#276A7C; " +
/*CH*/            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>RangeAllowedStartTime</th>\n" + 
/*CH*/            "                                       <th height='10px' style='font-size:12px; background:#276A7C; " +
/*CH*/            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>RangeAllowedEndTime</th>\n" + 
                
                "                               </tr>\n";
            for(OrdLinesModulosTab loOrdLinesModulosTab : taOrdLinesModulosTabMail){            
                lsHtml += 
                "                               <tr>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsOrdidEvetv() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsSptmstidEvetv() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsOrdid() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsOrdlnnum() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsBcstdt() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsBuyuntid() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsBrkdtid() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsHour() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsDuration() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsStnid() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsUsrchr() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsAutoid() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsBrnd() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsPosesp() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsSptrt() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsRevsts() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsPriority() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsDigital() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsPaga() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" +
                loOrdLinesModulosTab.getLsSptmstid() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsStatus() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsOrdlnid() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsAction() + "</td>\n" + 
/*CH*/                "                                       <td style='font-size:12px; background:#B6DDE8;" +
/*CH*/                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
/*CH*/                loOrdLinesModulosTab.getLsRangeAllowedStartTime() + "</td>\n" + 
/*CH*/                "                                       <td style='font-size:12px; background:#B6DDE8;" +
/*CH*/                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
/*CH*/                loOrdLinesModulosTab.getLsRangeAllowedEndTime() + "</td>\n" + 
                "                               </tr>\n";
            }
                lsHtml += "            </table>             \n" + 
                "            <br/>           \n" + 
                "            <br/>\n" + 
                "            <label style='font-family:Arial, Helvetica, sans-serif; font-size:12px; " +
                    "font-weight:bold '>Integraci&oacute;n Paradigm-Neptuno, 2017 Televisa S.A. de C.V</label>\n" + 
                "            <br/>\n" + 
                "            </div>\n" + 
                "            <div style='float:left; background-color:#F58220;width:7%;height:25px;background:" +
                          "-moz-linear-gradient(#F1A345, #E17521); background:-webkit-linear-gradient(#F1A345, " +
                    "#E17521);background: -ms-linear-background(#F1A345, #E17521);filter:progid:DXImageTransform." +
                    "Microsoft.gradient(startColorStr='#F1A345', EndColorStr='#E17521');'>\n" + 
                "                       </div>             \n" + 
                "            <div style='float:left; ;width:86%;height:25px;background:linear-gradient(#040545, " +
                    "#0172AE);background:-webkit-linear-gradient(#040545, #0172AE);background: " +
                          "-ms-linear-background(#040545, #0172AE);filter:progid:DXImageTransform.Microsoft." +
                    "gradient(startColorStr='#040545', EndColorStr='#0172AE');'>\n" + 
                "                       </div>\n" + 
                "            <div style='float:left; background-color:#F58220;width:7%;height:25px;background:-" +
                    "moz-linear-gradient(#F1A345, #E17521);background:-webkit-linear-gradient(#F1A345, #E17521);" +
            "background: -ms-linear-background(#F1A345, #E17521);filter:progid:DXImageTransform.Microsoft.gradient(" +
                    "startColorStr='#F1A345', EndColorStr='#E17521');'></div>             \n" + 
                "               </body>\n" + 
                "       </html>";            
            SecmanDasEnviarCorreo loSecmanDasSendMail = getDynaWebServiceURLEmail();
            if(loSecmanDasSendMail != null){
                try{
                    Map<String, Object>   loRequestContext =
                        ((BindingProvider)loSecmanDasSendMail).getRequestContext();                    
                    setPortCredentialProviderList(loRequestContext);                    
                    Mail                  loEmail = new Mail();                    
                    MailHeader            loEmailHeader = new MailHeader();
                    MailAddress           loEmailAddresFrom = new MailAddress();                    
                    loEmailAddresFrom.setUserNameAdress("Paradigm-Neptuno Integration");
                    loEmailAddresFrom.setAddress("service_integration@televisa.com.mx");
                    loEmailHeader.setAddressFrom(loEmailAddresFrom);
                    MailAddressCollection loMailAddressCollection =
                        new MailAddressCollection();
                    for(EmailDestinationAddress loDest: toEmailDestinationAddress){                        
                        MailAddress           loEmailAdd = new MailAddress();
                        loEmailAdd.setUserNameAdress(loDest.getLsNameTo());
                        loEmailAdd.setAddress(loDest.getLsAddressTo());    
                        loMailAddressCollection.getMailAddress().add(loEmailAdd);
                    }
                    loEmailHeader.setTo(loMailAddressCollection);
                    loEmailHeader.setSubject(tsSubject);
                    loEmail.setMailHeader(loEmailHeader);
                    MailBody              loMailBody = new MailBody();
                    loMailBody.setBodyType("HTML");
                    loMailBody.setMessage(lsHtml);
                    loEmail.setMailBody(loMailBody);
                    loSecmanDasSendMail.enviarCorreo(loEmail);
                    lbResponse = true;
                } catch (Exception loException) {
                    lbResponse = false;
                }
            }
        } catch (Exception loException) {
            lbResponse = false;
        }
        return lbResponse;
    }
    
    /**
     * Envia correo correspondiente al resultado de Log Comercial
     * @autor Jorge Luis Bautista Santiago
     * @param tsSubject
     * @param toEmailDestinationAddress
     * @param taLogsComercialTab
     * @return boolean
     */
    public boolean sendEmailLogComercial(String tsSubject, 
                                         List<EmailDestinationAddress> toEmailDestinationAddress,
                                         List<EvetvLogComercialTab>    taLogsComercialTab,
                                         String tsMessageHeader
                                        ) {
        boolean lbResponse = false;
        try{
            String lsHtml =
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/" +
                "xhtml1-strict.dtd\">\n" + 
                "       <html xmlns=\"http://www.w3.org/1999/xhtml\" dir=\"ltr\" lang=\"es-ES\">\n" + 
                "               <head>\n" + 
                "            <title>Venta de M&oacute;dulos</title>\n" + 
                "            <meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\" />\n" + 
                "            <meta name=\"language\" content=\"es\" />\n" + 
                "        </head>\n" + 
                "        <body>\n" + 
                "            <div style='background-color:#0F2F62; height:25px;background:linear-gradient(#040545," +
                "#0172AE);background:-webkit-linear-gradient(#040545, #0172AE);background: -ms-linear-background" +
                "(#040545, #0172AE);filter:progid:DXImageTransform.Microsoft.gradient(startColorStr='#040545', " +
                "EndColorStr='#0172AE');'></div>\n" + 
                "            <div style='background-color:#fff'>             \n" + 
                "            <br/>\n" + 
                "            <label style='font-family:Arial, Helvetica, sans-serif; font-size:16px; " +
                "font-weight:bold '>Proceso de Integraci&oacute;n para Log Comercial Finalizado</label><br>\n";
                if(tsMessageHeader.length() > 0){                                    
                    lsHtml += "<br><label style='font-family:Arial, Helvetica, sans-serif; font-size:20px;" +
                    "font-color:red; font-weight:bold '>" + tsMessageHeader + "</label><br>\n";
                }
                if(tsMessageHeader.length() == 0){
                lsHtml += "                       <label style='font-family:Arial, Helvetica, sans-serif; " +
                    "font-size:12px; font-weight:bold '>A continuaci&oacuten se encuentra el detalle del Resultado" +
                          "</label><p/>\n" + 
                "            <table cellpadding='0' cellspacing='0'  style='border-bottom-color:#F79646;" +
                "border-bottom-width:3px;border-bottom-style:solid;border-top-color:#F79646;border-top-style:solid;" +
                "border-top-width:3px; '>             \n" + 
                "                               <tr>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
                "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Stnid</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C; " +
                    "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Bcstdt</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
                "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Brkdtid</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
                "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Desccorte</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C; " +
                    "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Acttim</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
                "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Overlay</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
                "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Action</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C; " +
                    "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Ordid</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
                "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Sptmstid</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C; " +
                    "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Sptlen</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
                "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Spotformatid</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
                "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Spotcomments</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
                "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Brnd</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C; " +
                    "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Autoid</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
                "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Bookingpos</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C; " +
                    "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Warning</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
                "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Untnum</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C; " +
                    "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Sptrt</th>\n" + 
                "                               </tr>\n";
            
            for(EvetvLogComercialTab loOrdLogComercialTab : taLogsComercialTab){            
                lsHtml += 
                "                               <tr>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" +
                loOrdLogComercialTab.getLsStnid() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" +
                loOrdLogComercialTab.getLsBcstdt() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" +
                loOrdLogComercialTab.getLsBrkdtid() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" +
                loOrdLogComercialTab.getLsDesccorte() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" +
                loOrdLogComercialTab.getLsActtim() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" +
                loOrdLogComercialTab.getLsOverlay() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" +
                loOrdLogComercialTab.getLsAction() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" +
                loOrdLogComercialTab.getLsOrdid() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" +
                loOrdLogComercialTab.getLsSptmstid() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" +
                loOrdLogComercialTab.getLsSptlen() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>"+
                loOrdLogComercialTab.getLsSpotformatid() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" +
                loOrdLogComercialTab.getLsSpotcomments() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" +
                loOrdLogComercialTab.getLsBrnd() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" +
                loOrdLogComercialTab.getLsAutoid() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" +
                loOrdLogComercialTab.getLsBookingpos() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" +
                loOrdLogComercialTab.getLsWarning() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" +
                loOrdLogComercialTab.getLsUntnum() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>"+
                loOrdLogComercialTab.getLsSptrt() + "</td>\n" + 
                "                               </tr>\n";
                }//for
                lsHtml += "            </table>             \n" + 
                "            <br/>           \n";
            }//tsMessageHeader.length() == 0
                lsHtml +=  "            <br/>\n" + 
                "            <label style='font-family:Arial, Helvetica, sans-serif; font-size:12px; " +
                    "font-weight:bold '>Integraci&oacute;n Paradigm-Neptuno, 2017 Televisa S.A. de C.V</label> \n" + 
                "            <br/>\n" + 
                "            </div>\n" + 
                "            <div style='float:left; background-color:#F58220;width:7%;height:25px;" +
            "background:-moz-linear-gradient(#F1A345, #E17521); background:-webkit-linear-gradient(#F1A345, " +
                    "#E17521);background: -ms-linear-background(#F1A345, #E17521);filter:progid:DXImageTransform." +
                    "Microsoft.gradient(startColorStr='#F1A345', EndColorStr='#E17521');'>\n" + 
                "                       </div>             \n" + 
                "            <div style='float:left; ;width:86%;height:25px;background:linear-gradient" +
                "(#040545, #0172AE);background:-webkit-linear-gradient(#040545, #0172AE);background: " +
                           "-ms-linear-background(#040545, #0172AE);filter:progid:DXImageTransform.Microsoft." +
                    "gradient(startColorStr='#040545', EndColorStr='#0172AE');'>\n" + 
                "                       </div>\n" + 
                "            <div style='float:left; background-color:#F58220;width:7%;height:25px;" +
            "background:-moz-linear-gradient(#F1A345, #E17521);background:-webkit-linear-gradient(#F1A345, " +
                    "#E17521);background: -ms-linear-background(#F1A345, #E17521);filter:progid:DXImageTransform." +
                    "Microsoft.gradient(startColorStr='#F1A345', EndColorStr='#E17521');'></div>   \n" + 
                "               </body>\n" + 
                "       </html>";    
            SecmanDasEnviarCorreo loSecmanDasSendMail = getDynaWebServiceURLEmail();
            if(loSecmanDasSendMail != null){
                try{
                    Map<String, Object>   loRequestContext =
                        ((BindingProvider)loSecmanDasSendMail).getRequestContext();                    
                    setPortCredentialProviderList(loRequestContext);                    
                    Mail                  loEmail = new Mail();                    
                    MailHeader            loEmailHeader = new MailHeader();
                    MailAddress           loEmailAddresFrom = new MailAddress();                    
                    loEmailAddresFrom.setUserNameAdress("Paradigm-Neptuno Integration");
                    loEmailAddresFrom.setAddress("service_integration@televisa.com.mx");
                    loEmailHeader.setAddressFrom(loEmailAddresFrom);
                    MailAddressCollection loMailAddressCollection =
                        new MailAddressCollection();
                    for(EmailDestinationAddress loDest: toEmailDestinationAddress){                        
                        MailAddress           loEmailAdd = new MailAddress();
                        loEmailAdd.setUserNameAdress(loDest.getLsNameTo());
                        loEmailAdd.setAddress(loDest.getLsAddressTo());    
                        loMailAddressCollection.getMailAddress().add(loEmailAdd);
                    }
                    loEmailHeader.setTo(loMailAddressCollection);
                    loEmailHeader.setSubject(tsSubject);
                    loEmail.setMailHeader(loEmailHeader);
                    MailBody              loMailBody = new MailBody();
                    loMailBody.setBodyType("HTML");
                    loMailBody.setMessage(lsHtml);
                    loEmail.setMailBody(loMailBody);
                    loSecmanDasSendMail.enviarCorreo(loEmail);
                    lbResponse = true;
                    System.out.println("OK (envCorreo)");
                } catch (Exception loException) {
                    lbResponse = false;
                    System.out.println("ERROR 01: "+loException.getMessage());
                }
            }
        } catch (Exception loException) {
            lbResponse = false;
            System.out.println("ERROR 02: "+loException.getMessage());
        }
        System.out.println("Fin de Enviar correo "+lbResponse);
        return lbResponse;
    }
    
    /**
     * Metodo que settea credenciales requeridas en secman
     * @autor Jorge Luis Bautista Santiago
     * @param tsRequestContext
     * @return void
     */
    @Generated("Oracle JDeveloper")
    public static void setPortCredentialProviderList(Map<String, Object> tsRequestContext) 
    throws Exception {    
        String                   lsUsername = "osbusrkey";
        String                   lsPassword = "password1";
        List<CredentialProvider> laCredList = 
            new ArrayList<CredentialProvider>();
        laCredList.add(getUNTCredentialProvider(lsUsername, lsPassword));
        tsRequestContext.put(WSSecurityContext.CREDENTIAL_PROVIDER_LIST, 
                             laCredList);
    }
    
    /**
     * Agrega al provider el usuario y contraseña requeridas en secman
     * @autor Jorge Luis Bautista Santiago
     * @param tsUsername
     * @param tsPassword
     * @return CredentialProvider
     */
    @Generated("Oracle JDeveloper")
    public static CredentialProvider getUNTCredentialProvider(String tsUsername,
                                                              String tsPassword) {
        return new ClientUNTCredentialProvider(tsUsername.getBytes(), 
                                               tsPassword.getBytes());
    }
    
    /**
     * Envia correo correspondiente al resultado de Venta por Modulos
     * @autor Jorge Luis Bautista Santiago
     * @param tsSubject
     * @param toEmailDestinationAddress
     * @param toOrderModulosBean
     * @return boolean
     */
    public boolean sendEmailPreemption(String tsSubject, 
                                         List<EmailDestinationAddress> toEmailDestinationAddress,
                                         List<OrdLinesModulosTab> taOrdLinesModulosTabMail
                                        ) {
        boolean lbResponse = false;
        try{
            String lsHtml =
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/" +
                "xhtml1-strict.dtd\">\n" + 
                "       <html xmlns=\"http://www.w3.org/1999/xhtml\" dir=\"ltr\" lang=\"es-ES\">\n" + 
                "               <head>\n" + 
                "            <title>Venta de M&oacute;dulos</title>\n" + 
                "            <meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\" />\n" + 
                "            <meta name=\"language\" content=\"es\" />\n" + 
                "        </head>\n" + 
                "        <body>\n" + 
                "            <div style='background-color:#0F2F62; height:25px;background:linear-gradient(#040545," +
                " #0172AE);background:-webkit-linear-gradient(#040545, #0172AE);background: -ms-linear-background" +
                "(#040545, #0172AE);filter:progid:DXImageTransform.Microsoft.gradient(startColorStr='#040545'," +
                "EndColorStr='#0172AE');'></div>\n" + 
                "            <div style='background-color:#fff'>             \n" + 
                "            <br/>\n" + 
                "            <label style='font-family:Arial, Helvetica, sans-serif; font-size:16px; " +
                "font-weight:bold '>El Proceso de Integraci&oacute;n Cancelaci&oacute;n de Pases ha Finalizado" +
                "</label><p/>\n" + 
                "                       <label style='font-family:Arial, Helvetica, sans-serif; font-size:12px;" +
            "font-weight:bold '>A continuaci&oacuten se encuentra el detalle del Resultado</label><p/>\n" + 
                "            <table cellpadding='0' cellspacing='0'  style='border-bottom-color:#F79646;" +
            "border-bottom-width:3px;border-bottom-style:solid;border-top-color:#F79646;border-top-style:solid;" +
            "border-top-width:3px; '>             \n" + 
                "                               <tr>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>OrdidEvetv</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>SptmstidEvetv</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Ordid</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Ordlnnum</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Bcstdt</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Buyuntid</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Brkdtid</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Hour</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Duration</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Stnid</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Usrchr</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Autoid</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Brnd</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Posesp</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Sptrt</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Revsts</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Priority</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Digital</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Paga</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Sptmstid</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Status</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C;" +
            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Ordlnid</th>\n" + 
                "                                       <th height='10px' style='font-size:12px; background:#276A7C; " +
                "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Action</th>\n" + 
                
    /*CH*/            "                                       <th height='10px' style='font-size:12px; background:#276A7C; " +
    /*CH*/            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>RangeAllowedStartTime</th>\n" +
    /*CH*/            "                                       <th height='10px' style='font-size:12px; background:#276A7C; " +
    /*CH*/            "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>RangeAllowedEndTime</th>\n" +
                
                "                               </tr>\n";
            for(OrdLinesModulosTab loOrdLinesModulosTab : taOrdLinesModulosTabMail){            
                lsHtml += 
                "                               <tr>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsOrdidEvetv() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsSptmstidEvetv() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsOrdid() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsOrdlnnum() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsBcstdt() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsBuyuntid() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsBrkdtid() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsHour() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsDuration() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsStnid() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsUsrchr() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsAutoid() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsBrnd() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsPosesp() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsSptrt() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsRevsts() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsPriority() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsDigital() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsPaga() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" +
                loOrdLinesModulosTab.getLsSptmstid() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsStatus() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsOrdlnid() + "</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;" +
                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" + 
                loOrdLinesModulosTab.getLsAction() + "</td>\n" + 
    /*CH*/                "                                       <td style='font-size:12px; background:#B6DDE8;" +
    /*CH*/                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" +
    /*CH*/                loOrdLinesModulosTab.getLsRangeAllowedStartTime() + "</td>\n" +
    /*CH*/                "                                       <td style='font-size:12px; background:#B6DDE8;" +
    /*CH*/                "font-family:Arial, Helvetica, sans-serif; padding:3px;'>" +
    /*CH*/                loOrdLinesModulosTab.getLsRangeAllowedEndTime() + "</td>\n" +
                "                               </tr>\n";
            }
                lsHtml += "            </table>             \n" + 
                "            <br/>           \n" + 
                "            <br/>\n" + 
                "            <label style='font-family:Arial, Helvetica, sans-serif; font-size:12px; " +
                    "font-weight:bold '>Integraci&oacute;n Paradigm-Neptuno, 2017 Televisa S.A. de C.V</label>\n" + 
                "            <br/>\n" + 
                "            </div>\n" + 
                "            <div style='float:left; background-color:#F58220;width:7%;height:25px;background:" +
                          "-moz-linear-gradient(#F1A345, #E17521); background:-webkit-linear-gradient(#F1A345, " +
                    "#E17521);background: -ms-linear-background(#F1A345, #E17521);filter:progid:DXImageTransform." +
                    "Microsoft.gradient(startColorStr='#F1A345', EndColorStr='#E17521');'>\n" + 
                "                       </div>             \n" + 
                "            <div style='float:left; ;width:86%;height:25px;background:linear-gradient(#040545, " +
                    "#0172AE);background:-webkit-linear-gradient(#040545, #0172AE);background: " +
                          "-ms-linear-background(#040545, #0172AE);filter:progid:DXImageTransform.Microsoft." +
                    "gradient(startColorStr='#040545', EndColorStr='#0172AE');'>\n" + 
                "                       </div>\n" + 
                "            <div style='float:left; background-color:#F58220;width:7%;height:25px;background:-" +
                    "moz-linear-gradient(#F1A345, #E17521);background:-webkit-linear-gradient(#F1A345, #E17521);" +
            "background: -ms-linear-background(#F1A345, #E17521);filter:progid:DXImageTransform.Microsoft.gradient(" +
                    "startColorStr='#F1A345', EndColorStr='#E17521');'></div>             \n" + 
                "               </body>\n" + 
                "       </html>";            
            SecmanDasEnviarCorreo loSecmanDasSendMail = getDynaWebServiceURLEmail();
            if(loSecmanDasSendMail != null){
                try{
                    Map<String, Object>   loRequestContext =
                        ((BindingProvider)loSecmanDasSendMail).getRequestContext();                    
                    setPortCredentialProviderList(loRequestContext);                    
                    Mail                  loEmail = new Mail();                    
                    MailHeader            loEmailHeader = new MailHeader();
                    MailAddress           loEmailAddresFrom = new MailAddress();                    
                    loEmailAddresFrom.setUserNameAdress("Paradigm-Neptuno Integration");
                    loEmailAddresFrom.setAddress("service_integration@televisa.com.mx");
                    loEmailHeader.setAddressFrom(loEmailAddresFrom);
                    MailAddressCollection loMailAddressCollection =
                        new MailAddressCollection();
                    for(EmailDestinationAddress loDest: toEmailDestinationAddress){                        
                        MailAddress           loEmailAdd = new MailAddress();
                        loEmailAdd.setUserNameAdress(loDest.getLsNameTo());
                        loEmailAdd.setAddress(loDest.getLsAddressTo());    
                        loMailAddressCollection.getMailAddress().add(loEmailAdd);
                    }
                    loEmailHeader.setTo(loMailAddressCollection);
                    loEmailHeader.setSubject(tsSubject);
                    loEmail.setMailHeader(loEmailHeader);
                    MailBody              loMailBody = new MailBody();
                    loMailBody.setBodyType("HTML");
                    loMailBody.setMessage(lsHtml);
                    loEmail.setMailBody(loMailBody);
                    loSecmanDasSendMail.enviarCorreo(loEmail);
                    lbResponse = true;
                } catch (Exception loException) {
                    lbResponse = false;
                }
            }
        } catch (Exception loException) {
            lbResponse = false;
        }
        return lbResponse;
    }
    
}
