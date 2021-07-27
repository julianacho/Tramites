/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.ServiceImpl;

import co.gov.gobierno.DAO.PropertiesPHDAO;
import co.gov.gobierno.DTO.LogAuditoriaDTO;
import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.DTO.RequestDTO;
import co.gov.gobierno.DTO.ThirdDTO;
import co.gov.gobierno.DTO.WebServiceDTO;
import co.gov.gobierno.Service.CertificateConsultService;
import co.gov.gobierno.Service.LogAuditoriaService;
import co.gov.gobierno.Service.WebService;
import co.gov.gobierno.Util.PropertiesHandler;
import co.gov.gobierno.Util.XmlHandler;
import static co.gov.gobierno.Util.XmlHandler.convertStringToXmlDocument;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author DELL
 */
@Stateless
public class CertificateConsultServiceImpl implements CertificateConsultService
{
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(CertificateConsultServiceImpl.class);
	
    @EJB
    WebService ws;
    
    @EJB
    LogAuditoriaService logService;
    
    private LogAuditoriaDTO logAuditoriaDTO = new LogAuditoriaDTO(0, "NA", "IP", "SO", "NA", "CertificateConsultServiceImpl", "NA", "NA", "NA", null);

    @Override
    public WebServiceDTO getRequest(ThirdDTO objThirdDTO, List<PropertieDTO> listPropertieDTOs) 
    {
        WebServiceDTO objWebServiceDTO = new WebServiceDTO();
        String params = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n" +
"   <soapenv:Header/>\n" +
"   <soapenv:Body>\n" +
"      <tem:getEntitiesUsingSchema>\n" +
"         <!--Optional:-->\n" +
"         <tem:entitiesInfo>\n" +
"          <BizAgiWSParam><EntityData><EntityName>SolicitudCertificado</EntityName><Filters><![CDATA[IdentificacionCiudadano = '" + objThirdDTO.getIdNumber() + "']]></Filters></EntityData></BizAgiWSParam>\n" +
"         </tem:entitiesInfo>\n" +
"         <!--Optional:-->\n" +
"         <tem:schema>\n" +
"          \n" +
"<xs:schema attributeFormDefault=\"qualified\" elementFormDefault=\"qualified\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n" +
"\n" +
"  <xs:element name=\"SolicitudCertificado\">\n" +
"\n" +
"    <xs:complexType>\n" +
"\n" +
"      <xs:sequence>\n" +
"\n" +
"	  <!--Aqui va lo agregado por Francisco-->\n" +
"      <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"RadicadoSalida\" type=\"xs:string\" />\n" +
"	<!--Finde Aqui va lo agregado por Francisco-->\n" +
"        <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"TipoTramite\">\n" +
"\n" +
"          <xs:complexType>\n" +
"\n" +
"            <xs:sequence>\n" +
"\n" +
"              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"TipoDeTramite\" type=\"xs:string\" />\n" +
"\n" +
"              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Codigo\" type=\"xs:string\" />\n" +
"\n" +
"            </xs:sequence>\n" +
"\n" +
"            <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n" +
"\n" +
"          </xs:complexType>\n" +
"\n" +
"        </xs:element>\n" +
"\n" +
"        <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"CausaRechazo\">\n" +
"\n" +
"          <xs:complexType>\n" +
"\n" +
"            <xs:sequence>\n" +
"\n" +
"              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"CausaDeRechazo\" type=\"xs:string\" />\n" +
"\n" +
"              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Codigo\" type=\"xs:string\" />\n" +
"\n" +
"            </xs:sequence>\n" +
"\n" +
"            <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n" +
"\n" +
"          </xs:complexType>\n" +
"\n" +
"        </xs:element>\n" +
"\n" +
"        <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"FechaAtencion\" type=\"xs:dateTime\" />\n" +
"\n" +
"        <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Fecha\" type=\"xs:dateTime\" />\n" +
"\n" +
"        <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"IdentificacionCiudadano\" type=\"xs:string\" />\n" +
"\n" +
"        <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"NumRadicado\" type=\"xs:string\" />\n" +
"\n" +
"        <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"IdCaso\" type=\"xs:string\" />\n" +
"\n" +
        "<xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"RequiereApostillaje\" type=\"xs:boolean\" />\n" +
"\n" +                
"        <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Ciudadano\">\n" +
"\n" +
"          <xs:complexType>\n" +
"\n" +
"            <xs:sequence>\n" +
"\n" +
"              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"NumeroIdentificacion\" type=\"xs:string\" />\n" +
"\n" +
"              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"NombreCompleto\" type=\"xs:string\" />\n" +
"\n" +
"              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"PrimerApellido\" type=\"xs:string\" />\n" +
"\n" +
"              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"DatosMenor\">\n" +
"\n" +
"                <xs:complexType>\n" +
"\n" +
"                  <xs:sequence>\n" +
"\n" +
"                    <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"NumeroDocumento\" type=\"xs:string\" />\n" +
"\n" +
"                    <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"NombreCompleto\" type=\"xs:string\" />\n" +
"\n" +
"                    <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Direccion\" type=\"xs:string\" />\n" +
"\n" +
"                    <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"TipoIdentificacion\">\n" +
"\n" +
"                      <xs:complexType>\n" +
"\n" +
"                        <xs:sequence>\n" +
"\n" +
"                          <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"TipoIdentificacion\" type=\"xs:string\" />\n" +
"\n" +
"                          <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Codigo\" type=\"xs:string\" />\n" +
"\n" +
"                        </xs:sequence>\n" +
"\n" +
"                        <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n" +
"\n" +
"                      </xs:complexType>\n" +
"\n" +
"                    </xs:element>\n" +
"\n" +
"                  </xs:sequence>\n" +
"\n" +
"                  <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n" +
"\n" +
"                </xs:complexType>\n" +
"\n" +
"              </xs:element>\n" +
"\n" +
"              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"PrimerNombre\" type=\"xs:string\" />\n" +
"\n" +
"              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SegundoApellido\" type=\"xs:string\" />\n" +
"\n" +
"              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Segundonombre\" type=\"xs:string\" />\n" +
"\n" +
"            </xs:sequence>\n" +
"\n" +
"            <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n" +
"\n" +
"          </xs:complexType>\n" +
"\n" +
"        </xs:element>\n" +
"\n"+                
"        <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"IdDatosSolicitantePriv\">\n"+
"\n"+
"          <xs:complexType>\n"+
"\n"+
"            <xs:sequence>\n"+
"\n"+
"              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SNumerodeIdentificacion\" type=\"xs:string\" />\n"+
"\n"+
"              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SNombreCompleto\" type=\"xs:string\" />\n"+
"\n"+
"              <xs:sequence>\n"+
"\n"+
"                <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"IdTipodeIdentificacion\" type=\"xs:string\" />\n"+
"\n"+
"                <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Codigo\" type=\"xs:string\" />\n"+
"\n"+
"              </xs:sequence>\n"+
"\n"+
"            </xs:sequence>\n"+
"\n"+
"          </xs:complexType>\n"+
"\n"+
"        </xs:element>\n"+              
"\n" +
"        <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Radicado\" type=\"xs:string\" />\n" +
"\n" +
"        <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"EstadoSolicitud\">\n" +
"\n" +
"          <xs:complexType>\n" +
"\n" +
"            <xs:sequence>\n" +
"\n" +
"              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"EstadodeSolicitud\" type=\"xs:string\" />\n" +
"\n" +
"              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Codigo\" type=\"xs:string\" />\n" +
"\n" +
"            </xs:sequence>\n" +
"\n" +
"            <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n" +
"\n" +
"          </xs:complexType>\n" +
"\n" +
"        </xs:element>\n" +
"\n" +
"        <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"MenorEdad\" type=\"xs:boolean\" />\n" +
"\n" +
"        <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Parentesco\">\n" +
"\n" +
"          <xs:complexType>\n" +
"\n" +
"            <xs:sequence>\n" +
"\n" +
"              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"TipoParentesco\" type=\"xs:string\" />\n" +
"\n" +
"              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Codigo\" type=\"xs:string\" />\n" +
"\n" +
"            </xs:sequence>\n" +
"\n" +
"            <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n" +
"\n" +
"          </xs:complexType>\n" +
"\n" +
"        </xs:element>\n" +
"\n" +
"      </xs:sequence>\n" +
"\n" +
"    </xs:complexType>\n" +
"\n" +
"  </xs:element>\n" +
"\n" +
"</xs:schema>\n" +
"         </tem:schema>\n" +
"      </tem:getEntitiesUsingSchema>\n" +
"   </soapenv:Body>\n" +
"</soapenv:Envelope>";
        objWebServiceDTO.setUrl(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "urlBIZAGY") + "entitymanagersoa.asmx");
        objWebServiceDTO.setMethod("POST");
        objWebServiceDTO.getHeader().put("Content-Type", "text/xml;charset=utf-8");
        objWebServiceDTO.setSoapAction("http://tempuri.org/getEntitiesUsingSchema");
        objWebServiceDTO.setParams(params);
        try 
        {
            log.info("Voy a consumir el servicio web de Consultar Certificados de BIZAGI.");
            objWebServiceDTO = ws.SoapWithParam(objWebServiceDTO);
            
            if (objWebServiceDTO.getXmlFile().contains("errorCode")) {
                String responseMensaje = "";
                log.info("Bizagi TagError");
                String xmlFileTemp = objWebServiceDTO.getXmlFile().replace("&gt;", ">").replace("&lt;", "<").replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
                String responseError = XmlHandler.ReadXmlFileGetTag(xmlFileTemp, "errorCode");
                if(responseError != null && !responseError.isEmpty()) {
                	responseMensaje = XmlHandler.ReadXmlFileGetTag(xmlFileTemp, "errorMessage");
                	objWebServiceDTO.setRespuesta(false);
                }
            }
            log.info("Se consumió el servicio web de Consultar Certificados de BIZAGI correctamente.");
            this.logAuditoriaDTO.setAuMensajeError("getRequest " + "Voy a consumir el servicio web de Consultar Certificados de BIZAGI. ");
            this.logAuditoriaDTO.setAuRequest(params);
            this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile());
            this.logAuditoriaDTO.setAuLogUsuario(objThirdDTO.getUser());
            this.logAuditoriaDTO.setAuAplicacion("BIZAGI");
            logService.AddLogAuditoria(logAuditoriaDTO);
        }
        catch (Exception ex) 
        {
            log.info("Error al consultar los certificados BIZAGI: " + ex.toString());
            this.logAuditoriaDTO.setAuMensajeError("getRequest " + "Error al consultar los certificados BIZAGI. " + ex.toString());
            this.logAuditoriaDTO.setAuRequest(params);
            this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile());
            this.logAuditoriaDTO.setAuLogUsuario(objThirdDTO.getUser());
            this.logAuditoriaDTO.setAuAplicacion("BIZAGI");
            logService.AddLogAuditoria(logAuditoriaDTO);
            return null;
        }
        if(objWebServiceDTO.isRespuesta())
            return objWebServiceDTO;
        else
            return null;
    }

    @Override
    public List<RequestDTO> readConsult(String xml, String menorEdad) 
    {
        log.info("Estoy leyendo las consultas de BIZAGI.");
        List<RequestDTO> listRequestDTOs = new ArrayList<>();
        Document document;
        try 
        {
            log.info("Transformando la cadena en documento XML.");
            document = convertStringToXmlDocument(xml);
        }
        catch (Exception ex) 
        {
            log.info("Error al convertir la cadena en un documento Xml.");
            return null;
        }
        String externalRelation = "";
        NodeList nodeLst = document.getElementsByTagName("SolicitudCertificado");
        int count=0;
        for(int i=0; i<nodeLst.getLength(); i++) 
        {
            Element err = (Element) nodeLst.item(i);
            String aux = err.getElementsByTagName("MenorEdad").item(0).getTextContent();
            
            if(aux.equals(menorEdad))
            {
                count++;
                String date="";
                String dateAtention="";
                String idNumber="";
                String requestType="";
                String name="";
                String state="";
                String reject="";
                String radNumber = "";
                String younger="";
                String related="";
                String certificateKey="";
                String certificateKey2="";
                String idRequestType="";
                String itemId="";
                String thirdPartyId="";
                String listId="";
                String downloadRadNumber = "";
                externalRelation = err.getElementsByTagName("RequiereApostillaje").item(0).getTextContent();
                if(menorEdad.equals("True"))
                {
                    idNumber = err.getElementsByTagName("NumeroDocumento").item(0).getTextContent();
                    NodeList nodeLstName = err.getElementsByTagName("DatosMenor");
                    Element errName = (Element) nodeLstName.item(0);
                    NodeList nodeNombreC = errName.getElementsByTagName("NombreCompleto");
                    Node auxx = nodeNombreC.item(0);
                    name = auxx.getTextContent();
                    younger="1";
                    NodeList nodeLstRelated = err.getElementsByTagName("Parentesco");
                    Element errRelated = (Element) nodeLstRelated.item(0);
                    related = errRelated.getElementsByTagName("Codigo").item(0).getTextContent();
                }
                else
                {
                    idNumber = err.getElementsByTagName("IdentificacionCiudadano").item(0).getTextContent();
                    name = err.getElementsByTagName("PrimerNombre").item(0).getTextContent() + " " + 
                        err.getElementsByTagName("Segundonombre").item(0).getTextContent() + " " + 
                        err.getElementsByTagName("PrimerApellido").item(0).getTextContent() + " " + 
                        err.getElementsByTagName("SegundoApellido").item(0).getTextContent();
                    younger="0";
                }
                certificateKey = err.getElementsByTagName("IdCaso").item(0).getTextContent();
                certificateKey2 = err.getAttributes().getNamedItem("key").getTextContent();
                date = err.getElementsByTagName("Fecha").item(0).getTextContent();
                if(!date.equals(""))
                    date = date.substring(0, 10);
                requestType = err.getElementsByTagName("TipoDeTramite").item(0).getTextContent();
                NodeList nodeLstRequest = err.getElementsByTagName("TipoTramite");
                Element errRequest = (Element) nodeLstRequest.item(0);
                idRequestType = errRequest.getElementsByTagName("Codigo").item(0).getTextContent();
                dateAtention = err.getElementsByTagName("FechaAtencion").item(0).getTextContent();
                if(!dateAtention.equals(""))
                    dateAtention = dateAtention.substring(0, 10);
                state = err.getElementsByTagName("EstadoSolicitud").item(0).getTextContent();
                if(state.equals(""))
                    state = "En Tramite";
                else
                    state = err.getElementsByTagName("EstadodeSolicitud").item(0).getTextContent();
                
                reject = err.getElementsByTagName("CausaRechazo").item(0).getTextContent();
                if(!reject.equals(""))
                    reject = err.getElementsByTagName("CausaDeRechazo").item(0).getTextContent();
                radNumber = err.getElementsByTagName("Radicado").item(0).getTextContent();
                if(err.getElementsByTagName("ItemId").item(0)!=null)
                {
                    itemId = err.getElementsByTagName("ItemId").item(0).getTextContent();
                }
                if(err.getElementsByTagName("ListId").item(0)!=null)
                {
                    listId = err.getElementsByTagName("ListId").item(0).getTextContent();
                }
                if(err.getElementsByTagName("ThirdPartyId").item(0)!=null)
                {
                    thirdPartyId = err.getElementsByTagName("ThirdPartyId").item(0).getTextContent();
                }
                String privado = err.getElementsByTagName("IdDatosSolicitantePriv").item(0).getTextContent();
                String cedulaPrivado = "";
                if (!privado.equals(""))
                {
                    NodeList nodeLstPrivado = err.getElementsByTagName("IdDatosSolicitantePriv");
                    Element errPrivado = (Element) nodeLstPrivado.item(0);
                    cedulaPrivado = errPrivado.getElementsByTagName("SNumerodeIdentificacion").item(0).getTextContent();
                }
                log.info("Voy a crear un nuevo objeto RequestDTO para añadir a la tabla");
                RequestDTO objRequestDTO = new RequestDTO(false, younger, cedulaPrivado, certificateKey,certificateKey2, related, radNumber,
                        idNumber, name, idRequestType, requestType, date, dateAtention, state, reject, externalRelation,
                        itemId, listId, thirdPartyId, "", false, false);
                if(state.equals("Aprobado"))
                {
                    if(err.getElementsByTagName("RadicadoSalida").item(0)!=null)
                    {
                        downloadRadNumber = err.getElementsByTagName("RadicadoSalida").item(0).getTextContent();
                        objRequestDTO.setDownloadRadNumber(downloadRadNumber);
                    }
                    objRequestDTO.setShowCorrect(false);
                    objRequestDTO.setShowDownload(true);
                }
                else if(state.equals("Rechazado"))
                {
                    objRequestDTO.setShowCorrect(true);
                    objRequestDTO.setShowDownload(false);
                }
                listRequestDTOs.add(objRequestDTO);
                log.info("añadido correctamente");
            }
        }
        if(count>0)
            return listRequestDTOs;
        else
            return new ArrayList<>();
    }

    @Override
    public WebServiceDTO GetDownloadRequest(ThirdDTO objThirdDTO, List<PropertieDTO> listPropertieDTOs, RequestDTO objRequestDTO)
    {   
        WebServiceDTO objWebServiceDTO = new WebServiceDTO();
        try 
        {
            String params = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n" +
                            "   <soapenv:Header/>\n" +
                            "   <soapenv:Body>\n" +
                            "      <tem:GetRadicado>\n" +
                            "         <!--Optional:-->\n" +
                            "         <tem:listId>" + objRequestDTO.getListId() + "</tem:listId>\n" +
                            "         <!--Optional:-->\n" +
                            "         <tem:itemId>" + objRequestDTO.getItemId() + "</tem:itemId>\n" +
                            "         <!--Optional:-->\n" +
                            "         <tem:thirdPartyId>" + objRequestDTO.getThirdPartyId() + "</tem:thirdPartyId>\n" +
                            "      </tem:GetRadicado>\n" +
                            "   </soapenv:Body>\n" +
                            "</soapenv:Envelope>";

            objWebServiceDTO.setParams(params);
            objWebServiceDTO.setUrl(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "urlADAX"));
            objWebServiceDTO.setMethod("POST");
            objWebServiceDTO.getHeader().put("Content-Type", "text/xml;charset=utf-8");
            objWebServiceDTO.setSoapAction("http://tempuri.org/IIntegrationPOXTA/GetRadicado");
            log.info("Voy a consumir el servicio web de obtener el archivo para descargar el certificado.");
            objWebServiceDTO = ws.SoapWithParam(objWebServiceDTO);
            String responseMensaje = "";
            if (objWebServiceDTO.getXmlFile().contains("codigoError")) {
                log.info("Orfeo TagError");
                String responseError = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "codigoError");
                if(responseError != null && responseError.contains("0")) {
                	responseMensaje = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "mensaje");
                	objWebServiceDTO.setRespuesta(false);
                }
            }
            log.info("Se consumió el servicio web de obtener el archivo para descargar el certificado correctamente.");
            this.logAuditoriaDTO.setAuMensajeError("GetDownloadRequest " + "Se consumió el servicio web de obtener el archivo para descargar el certificado correctamente. " + responseMensaje);
            this.logAuditoriaDTO.setAuRequest(params);
            this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile());
            this.logAuditoriaDTO.setAuAplicacion("ORFEO");
            this.logAuditoriaDTO.setAuLogUsuario(objThirdDTO.getUser());
        }
        catch (Exception ex) 
        {
            log.info("Error al obtener el archivo para descargar el certificado: " + ex.toString());
            this.logAuditoriaDTO.setAuMensajeError("GetDownloadRequest " + "Error al obtener el archivo para descargar el certificado: " + ex.toString());
            this.logAuditoriaDTO.setAuRequest("NA");
            this.logAuditoriaDTO.setAuResponse("NA");
            this.logAuditoriaDTO.setAuAplicacion("ORFEO");
            this.logAuditoriaDTO.setAuLogUsuario(objThirdDTO.getUser());
            return null;
        }
        if(objWebServiceDTO.isRespuesta())
            return objWebServiceDTO;
        else
            return null;
    }
    
    @Override
    public WebServiceDTO DownloadORFEO(ThirdDTO objThirdDTO, List<PropertieDTO> listPropertieDTOs, RequestDTO objRequestDTO)
    {
        String tipoEndpointOrfeo = (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "TipoEndpointOrfeo", "5") == null ? "2" : PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "TipoEndpointOrfeo", "5"));  	
        String tipoEndpointOrfeo1 = "";
        String tipoEndpointOrfeo2 = "";
        String tipoEndpointOrfeo3 = "";
        if (tipoEndpointOrfeo.equals("2")) {
        	tipoEndpointOrfeo = "      <ser:consultaRadicado soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n";
        	tipoEndpointOrfeo1 = "      </ser:consultaRadicado>\n";
        	tipoEndpointOrfeo2 = "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"" + PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "urlORFEOCorto") + "/\">";
        	tipoEndpointOrfeo3 = "         <numeroRadicado xsi:type=\"xsd:string\">" + objRequestDTO.getDownloadRadNumber() + "</numeroRadicado>\n";
        }else {
        	tipoEndpointOrfeo = "      <urn:consultaRadicado soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n";
        	tipoEndpointOrfeo1 = "      </urn:consultaRadicado>\n";
        	tipoEndpointOrfeo2 = "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:co.gobierno.orfeo.prooveedor\">\n";
        	tipoEndpointOrfeo3 = "         <numRadicado xsi:type=\"xsd:string\">" + objRequestDTO.getDownloadRadNumber() + "</numRadicado>\n";
        }
        String params = tipoEndpointOrfeo2 + //"<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:co.gobierno.orfeo.prooveedor\">\n" +
                        "   <soapenv:Header/>\n" +
                        "   <soapenv:Body>\n" +
                        tipoEndpointOrfeo +
                        //"      <urn:consultaRadicado soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                        //"         <numRadicado xsi:type=\"xsd:string\">" + objRequestDTO.getDownloadRadNumber() + "</numRadicado>\n" +
                        tipoEndpointOrfeo3 + 
                        "         <estado xsi:type=\"xsd:string\">0</estado>\n" +
                        tipoEndpointOrfeo1 +
                        "   </soapenv:Body>\n" +
                        "</soapenv:Envelope>";
        WebServiceDTO objWebServiceDTO = new WebServiceDTO();
        objWebServiceDTO.setUrl(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "urlORFEO"));
        objWebServiceDTO.setMethod("POST");
        objWebServiceDTO.getHeader().put("Content-Type", "text/xml;charset=ISO-8859-1");
        objWebServiceDTO.setParams(params);
        try 
        {
            log.info("Voy a consumir el servicio web de Consultar Certificados de ORFEO.");
            objWebServiceDTO = ws.SoapWithParam(objWebServiceDTO);            
            String responseMensaje = "";
            if (objWebServiceDTO.getXmlFile().contains("codigoError")) {
                log.info("Orfeo TagError");
                String responseError = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "codigoError");
                if(responseError != null && responseError.contains("0")) {
                	responseMensaje = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "mensaje");
                	objWebServiceDTO.setRespuesta(false);
                }
            }
            
            this.logAuditoriaDTO.setAuMensajeError("DownloadORFEO " + "Voy a consumir el servicio web de Consultar Certificados de ORFEO. " + responseMensaje);
            this.logAuditoriaDTO.setAuRequest(params);
            this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile());
            this.logAuditoriaDTO.setAuAplicacion("ORFEO");
            this.logAuditoriaDTO.setAuLogUsuario(objThirdDTO.getUser());
        }
        catch (Exception ex) 
        {
            log.info("Error al consultar los certificados de ORFEO: " + ex.toString());
            this.logAuditoriaDTO.setAuMensajeError("DownloadORFEO " + "Voy a consumir el servicio web de Consultar Certificados de ORFEO. " + ex.toString());
            this.logAuditoriaDTO.setAuRequest(params);
            this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile());
            this.logAuditoriaDTO.setAuLogUsuario(objThirdDTO.getUser());
            this.logAuditoriaDTO.setAuAplicacion("ORFEO");
            return null;
        }
        if(objWebServiceDTO.isRespuesta())
        {
            log.info("Se consumió el servicio web de Consultar Certificados de ORFEO correctamente.");
            return objWebServiceDTO;
        }
        else
        {
            return null;
        }
    }
}
