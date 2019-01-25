
package com.televisa.comer.integration.service.beans.pgm.commerciallog;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CommercialList complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="CommercialList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ChannelID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Validate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Breaks" type="{http://com/televisa/comer/integration/service/beans/pgm/commerciallog}Breaks"/>
 *         &lt;element name="ListBreaks" type="{http://com/televisa/comer/integration/service/beans/pgm/commerciallog}ListBreaks"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CommercialList", propOrder = { "channelID", "date", "validate", "breaks", "listBreaks" })
public class CommercialList {

    @XmlElement(name = "ChannelID", required = true)
    protected String channelID;
    @XmlElement(name = "Date", required = true)
    protected String date;
    @XmlElement(name = "Validate", required = true)
    protected String validate;
    @XmlElement(name = "Breaks", required = true)
    protected Breaks breaks;
    @XmlElement(name = "ListBreaks", required = true)
    protected ListBreaks listBreaks;

    /**
     * Gets the value of the channelID property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getChannelID() {
        return channelID;
    }

    /**
     * Sets the value of the channelID property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setChannelID(String value) {
        this.channelID = value;
    }

    /**
     * Gets the value of the date property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDate(String value) {
        this.date = value;
    }

    /**
     * Gets the value of the validate property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getValidate() {
        return validate;
    }

    /**
     * Sets the value of the validate property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setValidate(String value) {
        this.validate = value;
    }

    /**
     * Gets the value of the breaks property.
     *
     * @return
     *     possible object is
     *     {@link Breaks }
     *
     */
    public Breaks getBreaks() {
        return breaks;
    }

    /**
     * Sets the value of the breaks property.
     *
     * @param value
     *     allowed object is
     *     {@link Breaks }
     *
     */
    public void setBreaks(Breaks value) {
        this.breaks = value;
    }

    /**
     * Gets the value of the listBreaks property.
     *
     * @return
     *     possible object is
     *     {@link ListBreaks }
     *
     */
    public ListBreaks getListBreaks() {
        return listBreaks;
    }

    /**
     * Sets the value of the listBreaks property.
     *
     * @param value
     *     allowed object is
     *     {@link ListBreaks }
     *
     */
    public void setListBreaks(ListBreaks value) {
        this.listBreaks = value;
    }

}
