
package com.televisa.comer.integration.service.beans.pgm.commerciallog;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Detalle complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="Detalle">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ItemRespuestaValidate" type="{http://com/televisa/comer/integration/service/beans/pgm/commerciallog}ItemRespuestaValidate" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Detalle", propOrder = { "itemRespuestaValidate" })
public class Detalle {

    @XmlElement(name = "ItemRespuestaValidate", required = true)
    protected List<ItemRespuestaValidate> itemRespuestaValidate;

    /**
     * Gets the value of the itemRespuestaValidate property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the itemRespuestaValidate property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItemRespuestaValidate().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ItemRespuestaValidate }
     *
     *
     */
    public List<ItemRespuestaValidate> getItemRespuestaValidate() {
        if (itemRespuestaValidate == null) {
            itemRespuestaValidate = new ArrayList<ItemRespuestaValidate>();
        }
        return this.itemRespuestaValidate;
    }

}
