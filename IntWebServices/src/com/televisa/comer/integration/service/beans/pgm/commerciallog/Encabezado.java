
package com.televisa.comer.integration.service.beans.pgm.commerciallog;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Encabezado complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="Encabezado">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ProcessID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Canal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Dia" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Tipo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Mensaje" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Detalle" type="{http://com/televisa/comer/integration/service/beans/pgm/commerciallog}Detalle"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Encabezado", propOrder = { "processID", "canal", "dia", "tipo", "mensaje", "detalle" })
public class Encabezado {

    @XmlElement(name = "ProcessID", required = true)
    protected String processID;
    @XmlElement(name = "Canal", required = true)
    protected String canal;
    @XmlElement(name = "Dia", required = true)
    protected String dia;
    @XmlElement(name = "Tipo", required = true)
    protected String tipo;
    @XmlElement(name = "Mensaje", required = true)
    protected String mensaje;
    @XmlElement(name = "Detalle", required = true)
    protected Detalle detalle;

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
     * Gets the value of the canal property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCanal() {
        return canal;
    }

    /**
     * Sets the value of the canal property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCanal(String value) {
        this.canal = value;
    }

    /**
     * Gets the value of the dia property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDia() {
        return dia;
    }

    /**
     * Sets the value of the dia property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDia(String value) {
        this.dia = value;
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

    /**
     * Gets the value of the detalle property.
     *
     * @return
     *     possible object is
     *     {@link Detalle }
     *
     */
    public Detalle getDetalle() {
        return detalle;
    }

    /**
     * Sets the value of the detalle property.
     *
     * @param value
     *     allowed object is
     *     {@link Detalle }
     *
     */
    public void setDetalle(Detalle value) {
        this.detalle = value;
    }

}
