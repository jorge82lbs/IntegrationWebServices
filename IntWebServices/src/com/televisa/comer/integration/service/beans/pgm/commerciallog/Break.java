
package com.televisa.comer.integration.service.beans.pgm.commerciallog;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Break complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="Break">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BreakID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BreakDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BreakHour" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BreakOverlay" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SpotList" type="{http://com/televisa/comer/integration/service/beans/pgm/commerciallog}SpotList"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Break", propOrder = { "breakID", "breakDescription", "breakHour", "breakOverlay", "spotList" })
public class Break {

    @XmlElement(name = "BreakID", required = true)
    protected String breakID;
    @XmlElement(name = "BreakDescription", required = true)
    protected String breakDescription;
    @XmlElement(name = "BreakHour", required = true)
    protected String breakHour;
    @XmlElement(name = "BreakOverlay", required = true)
    protected String breakOverlay;
    @XmlElement(name = "SpotList", required = true)
    protected SpotList spotList;

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
     * Gets the value of the breakDescription property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getBreakDescription() {
        return breakDescription;
    }

    /**
     * Sets the value of the breakDescription property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setBreakDescription(String value) {
        this.breakDescription = value;
    }

    /**
     * Gets the value of the breakHour property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getBreakHour() {
        return breakHour;
    }

    /**
     * Sets the value of the breakHour property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setBreakHour(String value) {
        this.breakHour = value;
    }

    /**
     * Gets the value of the breakOverlay property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getBreakOverlay() {
        return breakOverlay;
    }

    /**
     * Sets the value of the breakOverlay property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setBreakOverlay(String value) {
        this.breakOverlay = value;
    }

    /**
     * Gets the value of the spotList property.
     *
     * @return
     *     possible object is
     *     {@link SpotList }
     *
     */
    public SpotList getSpotList() {
        return spotList;
    }

    /**
     * Sets the value of the spotList property.
     *
     * @param value
     *     allowed object is
     *     {@link SpotList }
     *
     */
    public void setSpotList(SpotList value) {
        this.spotList = value;
    }

}
