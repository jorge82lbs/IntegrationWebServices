
package com.televisa.comer.integration.service.beans.pgm.commerciallog;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SpotList complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="SpotList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Spot" type="{http://com/televisa/comer/integration/service/beans/pgm/commerciallog}Spot" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SpotList", propOrder = { "spot" })
public class SpotList {

    @XmlElement(name = "Spot", required = true)
    protected List<Spot> spot;

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
