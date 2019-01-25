
package com.televisa.comer.integration.secman.grupos.types;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the com.televisa.comer.integration.secman.grupos.types package.
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

    private final static QName _ObtenerUsuariosGrupo_QNAME =
        new QName("http://webServices.view.secman.televisa.com.mx/", "obtenerUsuariosGrupo");
    private final static QName _ObtenerUsuariosGrupoResponse_QNAME =
        new QName("http://webServices.view.secman.televisa.com.mx/", "obtenerUsuariosGrupoResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.televisa.comer.integration.secman.grupos.types
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UsuariosCollection }
     *
     */
    public UsuariosCollection createUsuariosCollection() {
        return new UsuariosCollection();
    }

    /**
     * Create an instance of {@link UsuariosGrupoInputParameters }
     *
     */
    public UsuariosGrupoInputParameters createUsuariosGrupoInputParameters() {
        return new UsuariosGrupoInputParameters();
    }

    /**
     * Create an instance of {@link GrupoUsuarios }
     *
     */
    public GrupoUsuarios createGrupoUsuarios() {
        return new GrupoUsuarios();
    }

    /**
     * Create an instance of {@link ObtenerUsuariosGrupoResponse }
     *
     */
    public ObtenerUsuariosGrupoResponse createObtenerUsuariosGrupoResponse() {
        return new ObtenerUsuariosGrupoResponse();
    }

    /**
     * Create an instance of {@link ObtenerUsuariosGrupo }
     *
     */
    public ObtenerUsuariosGrupo createObtenerUsuariosGrupo() {
        return new ObtenerUsuariosGrupo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerUsuariosGrupo }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webServices.view.secman.televisa.com.mx/", name = "obtenerUsuariosGrupo")
    public JAXBElement<ObtenerUsuariosGrupo> createObtenerUsuariosGrupo(ObtenerUsuariosGrupo value) {
        return new JAXBElement<ObtenerUsuariosGrupo>(_ObtenerUsuariosGrupo_QNAME, ObtenerUsuariosGrupo.class, null,
                                                     value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerUsuariosGrupoResponse }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webServices.view.secman.televisa.com.mx/",
                    name = "obtenerUsuariosGrupoResponse")
    public JAXBElement<ObtenerUsuariosGrupoResponse> createObtenerUsuariosGrupoResponse(ObtenerUsuariosGrupoResponse value) {
        return new JAXBElement<ObtenerUsuariosGrupoResponse>(_ObtenerUsuariosGrupoResponse_QNAME,
                                                             ObtenerUsuariosGrupoResponse.class, null, value);
    }

}
