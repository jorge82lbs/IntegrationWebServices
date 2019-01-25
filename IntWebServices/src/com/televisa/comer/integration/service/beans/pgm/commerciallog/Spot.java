
package com.televisa.comer.integration.service.beans.pgm.commerciallog;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Spot complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="Spot">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Action" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ParadigmOrderID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ParadigmSpotID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Duration" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SpotFormatID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SpotComments" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MediaID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ProductID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BookingPos" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Warning" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NumOrden" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SpotPrice" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Spot", propOrder = {
         "action", "paradigmOrderID", "paradigmSpotID", "duration", "spotFormatID", "spotComments", "mediaID",
         "productID", "bookingPos", "warning", "numOrden", "spotPrice"
    })
public class Spot {

    @XmlElement(name = "Action", required = true)
    protected String action;
    @XmlElement(name = "ParadigmOrderID", required = true)
    protected String paradigmOrderID;
    @XmlElement(name = "ParadigmSpotID", required = true)
    protected String paradigmSpotID;
    @XmlElement(name = "Duration", required = true)
    protected String duration;
    @XmlElement(name = "SpotFormatID", required = true)
    protected String spotFormatID;
    @XmlElement(name = "SpotComments", required = true)
    protected String spotComments;
    @XmlElement(name = "MediaID", required = true)
    protected String mediaID;
    @XmlElement(name = "ProductID", required = true)
    protected String productID;
    @XmlElement(name = "BookingPos", required = true)
    protected String bookingPos;
    @XmlElement(name = "Warning", required = true)
    protected String warning;
    @XmlElement(name = "NumOrden", required = true)
    protected String numOrden;
    @XmlElement(name = "SpotPrice", required = true)
    protected String spotPrice;

    /**
     * Gets the value of the action property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAction() {
        return action;
    }

    /**
     * Sets the value of the action property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAction(String value) {
        this.action = value;
    }

    /**
     * Gets the value of the paradigmOrderID property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getParadigmOrderID() {
        return paradigmOrderID;
    }

    /**
     * Sets the value of the paradigmOrderID property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setParadigmOrderID(String value) {
        this.paradigmOrderID = value;
    }

    /**
     * Gets the value of the paradigmSpotID property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getParadigmSpotID() {
        return paradigmSpotID;
    }

    /**
     * Sets the value of the paradigmSpotID property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setParadigmSpotID(String value) {
        this.paradigmSpotID = value;
    }

    /**
     * Gets the value of the duration property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDuration() {
        return duration;
    }

    /**
     * Sets the value of the duration property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDuration(String value) {
        this.duration = value;
    }

    /**
     * Gets the value of the spotFormatID property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSpotFormatID() {
        return spotFormatID;
    }

    /**
     * Sets the value of the spotFormatID property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSpotFormatID(String value) {
        this.spotFormatID = value;
    }

    /**
     * Gets the value of the spotComments property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSpotComments() {
        return spotComments;
    }

    /**
     * Sets the value of the spotComments property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSpotComments(String value) {
        this.spotComments = value;
    }

    /**
     * Gets the value of the mediaID property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getMediaID() {
        return mediaID;
    }

    /**
     * Sets the value of the mediaID property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setMediaID(String value) {
        this.mediaID = value;
    }

    /**
     * Gets the value of the productID property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getProductID() {
        return productID;
    }

    /**
     * Sets the value of the productID property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setProductID(String value) {
        this.productID = value;
    }

    /**
     * Gets the value of the bookingPos property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getBookingPos() {
        return bookingPos;
    }

    /**
     * Sets the value of the bookingPos property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setBookingPos(String value) {
        this.bookingPos = value;
    }

    /**
     * Gets the value of the warning property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getWarning() {
        return warning;
    }

    /**
     * Sets the value of the warning property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setWarning(String value) {
        this.warning = value;
    }

    /**
     * Gets the value of the numOrden property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getNumOrden() {
        return numOrden;
    }

    /**
     * Sets the value of the numOrden property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setNumOrden(String value) {
        this.numOrden = value;
    }

    /**
     * Gets the value of the spotPrice property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSpotPrice() {
        return spotPrice;
    }

    /**
     * Sets the value of the spotPrice property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSpotPrice(String value) {
        this.spotPrice = value;
    }

}
