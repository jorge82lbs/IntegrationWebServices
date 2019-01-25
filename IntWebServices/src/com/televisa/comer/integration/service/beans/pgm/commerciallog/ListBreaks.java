
package com.televisa.comer.integration.service.beans.pgm.commerciallog;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListBreaks complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ListBreaks">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ActiveBreak" type="{http://com/televisa/comer/integration/service/beans/pgm/commerciallog}ActiveBreak" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListBreaks", propOrder = { "activeBreak" })
public class ListBreaks {

    @XmlElement(name = "ActiveBreak", required = true)
    protected List<ActiveBreak> activeBreak;

    /**
     * Gets the value of the activeBreak property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the activeBreak property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getActiveBreak().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ActiveBreak }
     *
     *
     */
    public List<ActiveBreak> getActiveBreak() {
        if (activeBreak == null) {
            activeBreak = new ArrayList<ActiveBreak>();
        }
        return this.activeBreak;
    }

}
