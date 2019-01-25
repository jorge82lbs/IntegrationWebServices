
package com.televisa.comer.integration.service.beans.pgm.commerciallog;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ItemRespuestaValidate complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ItemRespuestaValidate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ProcessID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Tipo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Mensaje" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemRespuestaValidate", propOrder = { "processID", "tipo", "mensaje" })
public class ItemRespuestaValidate {

    @XmlElement(name = "ProcessID", required = true)
    protected String processID;
    @XmlElement(name = "Tipo", required = true)
    protected String tipo;
    @XmlElement(name = "Mensaje", required = true)
    protected String mensaje;

    /**
     * Gets the value of the processID property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getProcessID() {
        return processID;
    }

    /**
     * Sets the value of the processID property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setProcessID(String value) {
        this.processID = value;
    }

    /**
     * Gets the value of the tipo property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Sets the value of the tipo property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTipo(String value) {
        this.tipo = value;
    }

    /**
     * Gets the value of the mensaje property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Sets the value of the mensaje property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setMensaje(String value) {
        this.mensaje = value;
    }

}
