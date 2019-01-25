
package com.televisa.comer.integration.secman.email.types;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the com.televisa.comer.integration.secman.email.types package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 *
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _EnviarCorreoResponse_QNAME = new QName("http://tempuri.org/", "EnviarCorreoResponse");
    private final static QName _EnviarCorreo_QNAME = new QName("http://tempuri.org/", "EnviarCorreo");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.televisa.comer.integration.secman.email.types
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EnviarCorreo }
     *
     */
    public EnviarCorreo createEnviarCorreo() {
        return new EnviarCorreo();
    }

    /**
     * Create an instance of {@link EnviarCorreoResponse }
     *
     */
    public EnviarCorreoResponse createEnviarCorreoResponse() {
        return new EnviarCorreoResponse();
    }

    /**
     * Create an instance of {@link AttachmentCollection }
     *
     */
    public AttachmentCollection createAttachmentCollection() {
        return new AttachmentCollection();
    }

    /**
     * Create an instance of {@link Attachment }
     *
     */
    public Attachment createAttachment() {
        return new Attachment();
    }

    /**
     * Create an instance of {@link MailAddress }
     *
     */
    public MailAddress createMailAddress() {
        return new MailAddress();
    }

    /**
     * Create an instance of {@link MailBody }
     *
     */
    public MailBody createMailBody() {
        return new MailBody();
    }

    /**
     * Create an instance of {@link MailHeader }
     *
     */
    public MailHeader createMailHeader() {
        return new MailHeader();
    }

    /**
     * Create an instance of {@link Mail }
     *
     */
    public Mail createMail() {
        return new Mail();
    }

    /**
     * Create an instance of {@link MailAddressCollection }
     *
     */
    public MailAddressCollection createMailAddressCollection() {
        return new MailAddressCollection();
    }

    /**
     * Create an instance of {@link ProcessResponse }
     *
     */
    public ProcessResponse createProcessResponse() {
        return new ProcessResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnviarCorreoResponse }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "EnviarCorreoResponse")
    public JAXBElement<EnviarCorreoResponse> createEnviarCorreoResponse(EnviarCorreoResponse value) {
        return new JAXBElement<EnviarCorreoResponse>(_EnviarCorreoResponse_QNAME, EnviarCorreoResponse.class, null,
                                                     value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnviarCorreo }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "EnviarCorreo")
    public JAXBElement<EnviarCorreo> createEnviarCorreo(EnviarCorreo value) {
        return new JAXBElement<EnviarCorreo>(_EnviarCorreo_QNAME, EnviarCorreo.class, null, value);
    }

}
