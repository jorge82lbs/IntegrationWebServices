
package com.televisa.comer.integration.service.beans.pgm.commerciallog;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ActiveBreak complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ActiveBreak">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BreakID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Hour" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TotalDuration" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ComercialDuration" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TypeBreak" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ActiveBreak", propOrder = {
         "breakID", "description", "date", "hour", "totalDuration", "comercialDuration", "typeBreak"
    })
public class ActiveBreak {

    @XmlElement(name = "BreakID", required = true)
    protected String breakID;
    @XmlElement(name = "Description", required = true)
    protected String description;
    @XmlElement(name = "Date", required = true)
    protected String date;
    @XmlElement(name = "Hour", required = true)
    protected String hour;
    @XmlElement(name = "TotalDuration", required = true)
    protected String totalDuration;
    @XmlElement(name = "ComercialDuration", required = true)
    protected String comercialDuration;
    @XmlElement(name = "TypeBreak", required = true)
    protected String typeBreak;

    /**
     * Gets the value of the breakID property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getBreakID() {
        return breakID;
    }

    /**
     * Sets the value of the breakID property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setBreakID(String value) {
        this.breakID = value;
    }

    /**
     * Gets the value of the description property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDescription(String value) {
        this.description = value;
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
     * Gets the value of the hour property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getHour() {
        return hour;
    }

    /**
     * Sets the value of the hour property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setHour(String value) {
        this.hour = value;
    }

    /**
     * Gets the value of the totalDuration property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTotalDuration() {
        return totalDuration;
    }

    /**
     * Sets the value of the totalDuration property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTotalDuration(String value) {
        this.totalDuration = value;
    }

    /**
     * Gets the value of the comercialDuration property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getComercialDuration() {
        return comercialDuration;
    }

    /**
     * Sets the value of the comercialDuration property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setComercialDuration(String value) {
        this.comercialDuration = value;
    }

    /**
     * Gets the value of the typeBreak property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTypeBreak() {
        return typeBreak;
    }

    /**
     * Sets the value of the typeBreak property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTypeBreak(String value) {
        this.typeBreak = value;
    }

}
