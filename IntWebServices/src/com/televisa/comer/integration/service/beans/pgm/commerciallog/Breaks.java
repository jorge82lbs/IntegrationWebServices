
package com.televisa.comer.integration.service.beans.pgm.commerciallog;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Breaks complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="Breaks">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Break" type="{http://com/televisa/comer/integration/service/beans/pgm/commerciallog}Break" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Breaks", propOrder = { "_break" })
public class Breaks {

    @XmlElement(name = "Break", required = true)
    protected List<Break> _break;

    /**
     * Gets the value of the break property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the break property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBreak().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Break }
     *
     *
     */
    public List<Break> getBreak() {
        if (_break == null) {
            _break = new ArrayList<Break>();
        }
        return this._break;
    }

}
