/**
* Project: Integraton Paradigm - EveTV
*
* File: VentaModulosCopys.java
*
* Created on: Septiembre 23, 2017 at 11:00
*
* Copyright (c) - OMW - 2017
*/
package com.televisa.comer.integration.service.jobs;

import com.televisa.comer.integration.model.beans.pgm.EvetvIntServiceBitacoraTab;
import com.televisa.comer.integration.model.daos.EntityMappedDao;
import com.televisa.comer.integration.model.daos.VentaModulosDao;

import com.televisa.comer.integration.service.beans.types.EmailDestinationAddress;
import com.televisa.comer.integration.service.email.MailManagement;

import java.sql.SQLException;

import java.util.Date;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/** Clase ejecutada en segundo plano para la generacion de copys
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Septiembre 23, 2017, 12:00 pm
 */
public class VentaModulosCopys implements Job{
    
    /**
     * Ejecuta Proceso en base de datos para generar copys en segundo plano
     * @autor Jorge Luis Bautista Santiago
     * @param toJobExecutionContext
     * @return void
     */
    @Override
    public void execute(JobExecutionContext toJobExecutionContext) throws JobExecutionException {
        System.out.println("CRON- Iniciando metodo Execute para VentaModulos en Fecha y hora ["+new Date()+"]");
        JobDataMap                 loDataMap = toJobExecutionContext.getJobDetail().getJobDataMap();
        String                     lsOrderId = loDataMap.getString("lsOrderId");  
        String                     lsIdService = loDataMap.getString("lsIdService");  
        String                     lsMessageBitacora = "";
        String                     lsIndProcess = "";
        VentaModulosDao            loVmDao = new VentaModulosDao();
        EntityMappedDao            loEntityMappedDao = new EntityMappedDao();
        EvetvIntServiceBitacoraTab loEvetvIntServiceBitacoraTabLin = new EvetvIntServiceBitacoraTab();
        try {
            loVmDao.callProcedureCopys(Integer.parseInt(lsOrderId));
            lsMessageBitacora = "callProcedureCopys(" + lsOrderId + ") finalizado con exito";
        } catch (SQLException loExCop) {
            lsMessageBitacora = "ERROR: "+loExCop.getMessage();
        }
        lsIndProcess = loEntityMappedDao.getGeneralParameterID("callProcedureCopys", "PROCESS_INTEGRATION");
        loEvetvIntServiceBitacoraTabLin.setLsIdLogServices(lsIdService);
        loEvetvIntServiceBitacoraTabLin.setLsIdService(lsIdService);
        loEvetvIntServiceBitacoraTabLin.setLsIndProcess(lsIndProcess); //Tipo de Proceso
        loEvetvIntServiceBitacoraTabLin.setLsNumEvtbProcessId("0");
        loEvetvIntServiceBitacoraTabLin.setLsNumPgmProcessId("0");        
        loEvetvIntServiceBitacoraTabLin.setLsIndEvento(lsMessageBitacora);
        loEntityMappedDao.insertBitacoraWs(loEvetvIntServiceBitacoraTabLin);
        try{
            //############### Enviar por correo #########################
            String                        lsIndProcessRes = 
                loEntityMappedDao.getGeneralParameterID("SendEmail", "PROCESS_INTEGRATION");
            EvetvIntServiceBitacoraTab toEvetvIntServiceBitacoraTabRes = new EvetvIntServiceBitacoraTab();
            toEvetvIntServiceBitacoraTabRes.setLsIdLogServices(lsIdService);
            toEvetvIntServiceBitacoraTabRes.setLsIdService(lsIdService);
            toEvetvIntServiceBitacoraTabRes.setLsIndProcess(lsIndProcessRes); //Tipo de Proceso
            toEvetvIntServiceBitacoraTabRes.setLsNumEvtbProcessId("0");
            toEvetvIntServiceBitacoraTabRes.setLsNumPgmProcessId("0");
            toEvetvIntServiceBitacoraTabRes.setLsIndEvento("Enviando email (copys) del Procesamiento de Venta de Modulos");
            loEntityMappedDao.insertBitacoraWs(toEvetvIntServiceBitacoraTabRes);
            String                        lsSubject = 
                loEntityMappedDao.getGeneralParameter("SubjectVentaModulosCopys", "INTEGRATION_EMAIL");
            String                        lsTypeMail = "MAIL_OK";
            List<EmailDestinationAddress> laEmailDestinationAddress = 
                loEntityMappedDao.getDestinationAddress(lsIdService, lsTypeMail);
            MailManagement                loMailManagement = new MailManagement();
            loMailManagement.sendEmailVentaModulosCopys(lsSubject, 
                                                   laEmailDestinationAddress
                                                   );
            
            //#############################################
        }catch(Exception loEx){
            System.out.println("No es Posible enviar correo");
        }

        System.out.println("FIN callProcedureCopys ["+new Date()+"]");
    }
}
