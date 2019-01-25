
package com.televisa.comer.integration.service.beans.pgm.preemption;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Campaign complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="Campaign">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Action" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="OrderID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="OrdIdPgm" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AgencyID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AdvertirserID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="InitialDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MasterContract" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RateCard" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TargetID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Channel" type="{http://com/televisa/comer/integration/service/beans/pgm/preemption}Channel" maxOccurs="unbounded"/>
 *         &lt;element name="Spot" type="{http://com/televisa/comer/integration/service/beans/pgm/preemption}Spot" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Campaign", propOrder = {
         "action", "orderID", "ordIdPgm", "agencyID", "advertirserID", "initialDate", "endDate", "masterContract",
         "rateCard", "targetID", "channel", "spot"
    })
public class Campaign {

    @XmlElement(name = "Action", required = true)
    protected String action;
    @XmlElement(name = "OrderID", required = true)
    protected String orderID;
    @XmlElement(name = "OrdIdPgm", required = true)
    protected String ordIdPgm;
    @XmlElement(name = "AgencyID", required = true)
    protected String agencyID;
    @XmlElement(name = "AdvertirserID", required = true)
    protected String advertirserID;
    @XmlElement(name = "InitialDate", required = true)
    protected String initialDate;
    @XmlElement(name = "EndDate", required = true)
    protected String endDate;
    @XmlElement(name = "MasterContract", required = true)
    protected String masterContract;
    @XmlElement(name = "RateCard", required = true)
    protected String rateCard;
    @XmlElement(name = "TargetID", required = true)
    protected String targetID;
    @XmlElement(name = "Channel", required = true)
    protected List<Channel> channel;
    @XmlElement(name = "Spot", required = true)
    protected List<Spot> spot;

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
     * Gets the value of the orderID property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getOrderID() {
        return orderID;
    }

    /**
     * Sets the value of the orderID property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setOrderID(String value) {
        this.orderID = value;
    }

    /**
     * Gets the value of the ordIdPgm property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getOrdIdPgm() {
        return ordIdPgm;
    }

    /**
     * Sets the value of the ordIdPgm property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setOrdIdPgm(String value) {
        this.ordIdPgm = value;
    }

    /**
     * Gets the value of the agencyID property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAgencyID() {
        return agencyID;
    }

    /**
     * Sets the value of the agencyID property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAgencyID(String value) {
        this.agencyID = value;
    }

    /**
     * Gets the value of the advertirserID property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAdvertirserID() {
        return advertirserID;
    }

    /**
     * Sets the value of the advertirserID property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAdvertirserID(String value) {
        this.advertirserID = value;
    }

    /**
     * Gets the value of the initialDate property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getInitialDate() {
        return initialDate;
    }

    /**
     * Sets the value of the initialDate property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setInitialDate(String value) {
        this.initialDate = value;
    }

    /**
     * Gets the value of the endDate property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setEndDate(String value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the masterContract property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getMasterContract() {
        return masterContract;
    }

    /**
     * Sets the value of the masterContract property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setMasterContract(String value) {
        this.masterContract = value;
    }

    /**
     * Gets the value of the rateCard property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getRateCard() {
        return rateCard;
    }

    /**
     * Sets the value of the rateCard property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setRateCard(String value) {
        this.rateCard = value;
    }

    /**
     * Gets the value of the targetID property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTargetID() {
        return targetID;
    }

    /**
     * Sets the value of the targetID property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTargetID(String value) {
        this.targetID = value;
    }

    /**
     * Gets the value of the channel property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the channel property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getChannel().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Channel }
     *
     *
     */
    public List<Channel> getChannel() {
        if (channel == null) {
            channel = new ArrayList<Channel>();
        }
        return this.channel;
    }

    /**
     * Gets the value of the spot property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the spot property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSpot().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Spot }
     *
     *
     */
    public List<Spot> getSpot() {
        if (spot == null) {
            spot = new ArrayList<Spot>();
        }
        return this.spot;
    }

}
