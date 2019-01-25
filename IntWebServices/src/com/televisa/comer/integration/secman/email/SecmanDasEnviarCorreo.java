
package com.televisa.comer.integration.secman.email;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import com.televisa.comer.integration.secman.email.types.Mail;
import com.televisa.comer.integration.secman.email.types.ObjectFactory;
import com.televisa.comer.integration.secman.email.types.ProcessResponse;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.10-b140319.1121
 * Generated source version: 2.2
 *
 */
@WebService(name = "SecmanDasEnviarCorreo", targetNamespace = "http://tempuri.org/")
@XmlSeeAlso({ ObjectFactory.class })
public interface SecmanDasEnviarCorreo {


    /**
     *
     * @param mail
     * @return
     *     returns com.televisa.comer.integration.secman.email.types.ProcessResponse
     */
    @WebMethod(operationName = "EnviarCorreo")
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "EnviarCorreo", targetNamespace = "http://tempuri.org/",
                    className = "com.televisa.comer.integration.secman.email.types.EnviarCorreo")
    @ResponseWrapper(localName = "EnviarCorreoResponse", targetNamespace = "http://tempuri.org/",
                     className = "com.televisa.comer.integration.secman.email.types.EnviarCorreoResponse")
    public ProcessResponse enviarCorreo(@WebParam(name = "mail", targetNamespace = "") Mail mail);

}