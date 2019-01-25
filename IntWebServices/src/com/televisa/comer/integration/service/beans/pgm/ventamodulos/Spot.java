
package com.televisa.comer.integration.service.beans.pgm.ventamodulos;

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
 *         &lt;element name="SpotID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BuyUnitID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BreakID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Hour" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Duration" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ChannelID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SpotFormatID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SpotComments" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MediaID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ProductID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BookingPos" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SpotPrice" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Revenue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Priority" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Digital" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Paga" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RangeAllowedStartTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RangeAllowedEndTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
         "action", "spotID", "date", "buyUnitID", "breakID", "hour", "duration", "channelID", "spotFormatID",
         "spotComments", "mediaID", "productID", "bookingPos", "spotPrice", "revenue", "priority", "digital", "paga",
         "rangeAllowedStartTime", "rangeAllowedEndTime"
    })
public class Spot {

    @XmlElement(name = "Action", required = true)
    protected String action;
    @XmlElement(name = "SpotID", required = true)
    protected String spotID;
    @XmlElement(name = "Date", required = true)
    protected String date;
    @XmlElement(name = "BuyUnitID", required = true)
    protected String buyUnitID;
    @XmlElement(name = "BreakID", required = true)
    protected String breakID;
    @XmlElement(name = "Hour", required = true)
    protected String hour;
    @XmlElement(name = "Duration", required = true)
    protected String duration;
    @XmlElement(name = "ChannelID", required = true)
    protected String channelID;
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
    @XmlElement(name = "SpotPrice", required = true)
    protected String spotPrice;
    @XmlElement(name = "Revenue", required = true)
    protected String revenue;
    @XmlElement(name = "Priority", required = true)
    protected String priority;
    @XmlElement(name = "Digital", required = true)
    protected String digital;
    @XmlElement(name = "Paga", required = true)
    protected String paga;
    @XmlElement(name = "RangeAllowedStartTime", required = true)
    protected String rangeAllowedStartTime;
    @XmlElement(name = "RangeAllowedEndTime", required = true)
    protected String rangeAllowedEndTime;

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
     * Gets the value of the spotID property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSpotID() {
        return spotID;
    }

    /**
     * Sets the value of the spotID property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSpotID(String value) {
        this.spotID = value;
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
     * Gets the value of the buyUnitID property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getBuyUnitID() {
        return buyUnitID;
    }

    /**
     * Sets the value of the buyUnitID property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setBuyUnitID(String value) {
        this.buyUnitID = value;
    }

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

    /**
     * Gets the value of the revenue property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getRevenue() {
        return revenue;
    }

    /**
     * Sets the value of the revenue property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setRevenue(String value) {
        this.revenue = value;
    }

    /**
     * Gets the value of the priority property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPriority(String value) {
        this.priority = value;
    }

    /**
     * Gets the value of the digital property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDigital() {
        return digital;
    }

    /**
     * Sets the value of the digital property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDigital(String value) {
        this.digital = value;
    }

    /**
     * Gets the value of the paga property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPaga() {
        return paga;
    }

    /**
     * Sets the value of the paga property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPaga(String value) {
        this.paga = value;
    }

    /**
     * Gets the value of the rangeAllowedStartTime property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getRangeAllowedStartTime() {
        return rangeAllowedStartTime;
    }

    /**
     * Sets the value of the rangeAllowedStartTime property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setRangeAllowedStartTime(String value) {
        this.rangeAllowedStartTime = value;
    }

    /**
     * Gets the value of the rangeAllowedEndTime property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getRangeAllowedEndTime() {
        return rangeAllowedEndTime;
    }

    /**
     * Sets the value of the rangeAllowedEndTime property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setRangeAllowedEndTime(String value) {
        this.rangeAllowedEndTime = value;
    }

}
