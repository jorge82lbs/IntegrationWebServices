
package com.televisa.comer.integration.service.beans.pgm.preemption;

import java.util.ArrayList;
import java.util.List;

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
 *         &lt;element name="Mode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Campaign" type="{http://com/televisa/comer/integration/service/beans/pgm/preemption}Campaign" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CommercialList", propOrder = { "mode", "campaign" })
public class CommercialList {

    @XmlElement(name = "Mode", required = true)
    protected String mode;
    @XmlElement(name = "Campaign", required = true)
    protected List<Campaign> campaign;

    /**
     * Gets the value of the mode property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getMode() {
        return mode;
    }

    /**
     * Sets the value of the mode property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setMode(String value) {
        this.mode = value;
    }

    /**
     * Gets the value of the campaign property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the campaign property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCampaign().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Campaign }
     *
     *
     */
    public List<Campaign> getCampaign() {
        if (campaign == null) {
            campaign = new ArrayList<Campaign>();
        }
        return this.campaign;
    }

}
