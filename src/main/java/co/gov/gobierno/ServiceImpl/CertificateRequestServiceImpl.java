/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.ServiceImpl;

import co.gov.gobierno.DTO.LegalAgentDTO;
import co.gov.gobierno.DTO.LogAuditoriaDTO;
import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.DTO.ThirdDTO;
import co.gov.gobierno.DTO.WebServiceDTO;
import co.gov.gobierno.DTO.YoungerDTO;
import co.gov.gobierno.Service.CertificateRequestService;
import co.gov.gobierno.Service.LogAuditoriaService;
import co.gov.gobierno.Service.WebService;
import co.gov.gobierno.Util.Base64Handler;
import co.gov.gobierno.Util.PropertiesHandler;
import co.gov.gobierno.Util.XmlHandler;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.primefaces.model.UploadedFile;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author DELL
 */
@Stateless
public class CertificateRequestServiceImpl implements CertificateRequestService
{
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(CertificateRequestServiceImpl.class);
    @EJB
    WebService ws;
    
    @EJB
    LogAuditoriaService logService;
    
    private LogAuditoriaDTO logAuditoriaDTO = new LogAuditoriaDTO(0, "NA", "IP", "SO", "NA", "CertificateRequestServiceImpl", "NA", "NA", "NA", null);


    @Override
    public String getRadicadoNumber(WebServiceDTO objWebServiceDTO, List<PropertieDTO> listPropertieDTOs)
    {
        try
        {
            objWebServiceDTO.setUrl(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "urlADAX"));
        
            objWebServiceDTO.setParams("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n" +
    "   <soapenv:Header/>\n" +
    "   <soapenv:Body>\n" +
    "      <tem:GetRadicadoNumber>\n" +
    "         <!--Optional:-->\n" +
    "         <tem:documentKindLabelType>" + PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "documentKindLabelType") + "</tem:documentKindLabelType>\n" +
    "         <!--Optional:-->\n" +
    "         <tem:userLoginName>" + PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "userLoginName") + "</tem:userLoginName>\n" +
    "         <!--Optional:-->\n" +
    "         <tem:thirdPartyId>" + PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "ThirdPartyId") + "</tem:thirdPartyId>\n" +
    "      </tem:GetRadicadoNumber>\n" +
    "   </soapenv:Body>\n" +
    "</soapenv:Envelope>");

            objWebServiceDTO.setMethod("POST");
            objWebServiceDTO.getHeader().put("Content-Type", "text/xml;charset=UTF-8");
            objWebServiceDTO.setSoapAction("http://tempuri.org/IIntegrationPOXTA/GetRadicadoNumber");
            log.info("Voy a consumir el servicio web de obtener numero de radicado de ADAX.");
            objWebServiceDTO = ws.SoapWithParam(objWebServiceDTO);
            log.info("Se consumió el servicio web de obtener numero de radicado de ADAX correctamente.");
            objWebServiceDTO.setToken(XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "a:Message"));
            log.info("Se leyo correctamente el tag a:Message que contiene el numero de radicado del XML resultante.");
            this.logAuditoriaDTO.setAuMensajeError("getRadicadoNumber " + "Se leyo correctamente el tag a:Message que contiene el numero de radicado del XML resultante.. ");
            this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
            this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
            this.logAuditoriaDTO.setAuLogUsuario("NA");
            this.logAuditoriaDTO.setAuAplicacion("ADAX");
            logService.AddLogAuditoria(logAuditoriaDTO);
            return objWebServiceDTO.getToken();
        }
        catch(Exception e)
        {
            log.info("Error al obtener el numero de radicado: " + e.toString());
            return null;
        }
    }

    @Override
    public WebServiceDTO Request(WebServiceDTO objWebServiceDTO, ThirdDTO objThirdDTO, String radicado, List<PropertieDTO> listPropertieDTOs, 
            UploadedFile uploadedFileAttendant, UploadedFile uploadedFilePublicService, UploadedFile uploadedFileYounger,
            UploadedFile uploadedFileCustody) 
    {
        objWebServiceDTO.setUrl(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "urlADAX"));
        String base64Younger = "";
        String base64Custody = "";
        String base64Attendant = "";
        String base64PublicService = "";
        try 
        {
            log.info("Se van a convertir los documentos adjuntos a base64");
            base64Attendant = (Base64Handler.convertFileToBase64Binary(uploadedFileAttendant.getInputstream()));
            base64PublicService = (Base64Handler.convertFileToBase64Binary(uploadedFilePublicService.getInputstream()));
            if(uploadedFileYounger!=null)
                base64Younger = (Base64Handler.convertFileToBase64Binary(uploadedFileYounger.getInputstream()));
            if(uploadedFileCustody!=null)
                base64Custody = (Base64Handler.convertFileToBase64Binary(uploadedFileCustody.getInputstream()));
            log.info("Archivos convertidos correctamente.");
        }
        catch (IOException ex) 
        {
            log.info("Error en la conversión de los archivos a base64: " + ex.toString());
            this.logAuditoriaDTO.setAuMensajeError("Request " + "Error en la conversión de los archivos a base64: " + ex.toString());
            this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
            this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
            this.logAuditoriaDTO.setAuLogUsuario(objThirdDTO.getIdNumber());
            this.logAuditoriaDTO.setAuAplicacion("ADAX");
            logService.AddLogAuditoria(logAuditoriaDTO);
        }
        
        String params = ("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\" xmlns:ms=\"http://schemas.datacontract.org/2004/07/MS.InformationManagement.DAL.DTOs.PoxtaIntegration\">\n" +
"   <soapenv:Header/>\n" +
"   <soapenv:Body>\n" +
"      <tem:CreateRadicadoEntradaDigitalPersona>\n" +
"         <!--Optional:-->\n" +
"         <tem:person>\n" +
"            <!--Optional:-->\n" +
"            <ms:Address>" + objThirdDTO.getAddress().replace(" ","") + "</ms:Address>\n" +
"            <ms:CityTaxonomyId>" + PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "CityTaxonomyId") + "</ms:CityTaxonomyId>\n" +
"            <ms:EMail>" + objThirdDTO.getEmail() + "</ms:EMail>\n" +
"            <ms:FirstSurname>" + objThirdDTO.getLastName() + "</ms:FirstSurname>\n" +
"            <ms:IdentificationNumber>" + objThirdDTO.getIdNumber() + "</ms:IdentificationNumber>\n" +
"            <ms:Name>" + objThirdDTO.getFirstName() + "</ms:Name>\n" +
"         </tem:person>\n" +
"         <!--Optional:-->\n" +
"         <tem:mainDigitalFile>\n" +
"            <ms:FileName>Solicitante " + objThirdDTO.getIdNumber() + "</ms:FileName>\n" +
"            <ms:StringBase64>" + base64Attendant + "</ms:StringBase64>\n" +
"             <ms:FileName>Recibo Publico " + objThirdDTO.getIdNumber() + "</ms:FileName>\n" +
"            <ms:StringBase64>" + base64PublicService + "</ms:StringBase64>\n" +
(uploadedFileYounger!=null? "             <ms:FileName>Identidad Menor " + objThirdDTO.getIdNumber() + "</ms:FileName>\n" +
"            <ms:StringBase64>" + base64Younger + "</ms:StringBase64>\n":"") +
(uploadedFileCustody!=null? "             <ms:FileName>Patria Potestad " + objThirdDTO.getIdNumber() + "</ms:FileName>\n" +
"            <ms:StringBase64>" + base64Custody + "</ms:StringBase64>\n":"") +
"         </tem:mainDigitalFile>\n" +
"         <tem:radicadoValues>\n" +
"          <ms:AssignedUserNameWithDomain>" + PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "AssignedUserNameWithDomain") + "</ms:AssignedUserNameWithDomain>\n" +
"            <!--Optional:-->\n" +
"            <ms:AuthorizeSendResponse>" + PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "AuthorizeSendResponse") + "</ms:AuthorizeSendResponse>\n" +
"            <!--Optional:-->\n" +
"            <ms:CertifiedCourier>" + PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "CertifiedCourier") + "</ms:CertifiedCourier>\n" +
"            <ms:CurrentUserNameWithDomain>" + PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "CurrentUserNameWithDomain") + "</ms:CurrentUserNameWithDomain>\n" +
"            <!--Optional:-->\n" +
"            <ms:DocumentId>" + radicado + "</ms:DocumentId>\n" +
"            <ms:DocumentNature>" + PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "DocumentNature") + "</ms:DocumentNature>\n" +
"            <!--Optional:-->\n" +
"            <ms:OfficeType>" + PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "OfficeType") + "</ms:OfficeType>\n" +
"            <!--Optional:-->\n" +
"            <ms:OfficeTypeBranch>" + PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "OfficeTypeBranch") + "</ms:OfficeTypeBranch>\n" +
"            <!--Optional:-->\n" +
"            <ms:SectionId>" + PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "SectionId") + "</ms:SectionId>\n" +
"            <ms:Subject>" + PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "Subject") + "</ms:Subject>\n" +
"            <ms:ThirdPartyId>" + PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "ThirdPartyId", "8") + "</ms:ThirdPartyId>\n" +
"         </tem:radicadoValues>\n" +
"      </tem:CreateRadicadoEntradaDigitalPersona>\n" +
"   </soapenv:Body>\n" +
"</soapenv:Envelope>");
        
        objWebServiceDTO.setParams(params.trim());
        objWebServiceDTO.setMethod("POST");
        objWebServiceDTO.getHeader().put("Content-Type", "text/xml;charset=UTF-8");
        objWebServiceDTO.setSoapAction("http://tempuri.org/IIntegrationPOXTA/CreateRadicadoEntradaDigitalPersona");
        log.info("Se va a consumir el servicio de crear solicitud de ADAX.");
        objWebServiceDTO = ws.SoapWithParam(objWebServiceDTO);
        log.info("Se consumio el servicio de crear solicitud de ADAX correctamente.");
        this.logAuditoriaDTO.setAuMensajeError("Request " + "Se va a consumir el servicio de crear solicitud de ADAX. " );
        this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
        this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
        this.logAuditoriaDTO.setAuLogUsuario(objThirdDTO.getIdNumber());
        this.logAuditoriaDTO.setAuAplicacion("ADAX");
        logService.AddLogAuditoria(logAuditoriaDTO);
        return objWebServiceDTO;
    }
    
    @Override
    public WebServiceDTO SecondRequest(WebServiceDTO objWebServiceDTO, ThirdDTO objThirdDTO, String radicado, String procedureType, 
            String externalRelation, List<PropertieDTO> listPropertieDTOs, String related, String younger, String itemId, String listId,
            UploadedFile uploadedFileAttendant, UploadedFile uploadedFilePublicService, UploadedFile uploadedFileYounger,
            UploadedFile uploadedFileCustody, String apostille, List<LegalAgentDTO> privadoLibertad) 
    {
    	try {
	        Date now = new Date();
	        SimpleDateFormat formatDay = new SimpleDateFormat("yyyy-MM-dd");
	        SimpleDateFormat formatHour = new SimpleDateFormat("hh:mm:ss");
	        String day = formatDay.format(now);
	        String hour = formatHour.format(now);
	        String youngerData = "";
	        String youngerTag = "";
	        String privado = "";
	        if(younger.equals("1"))
	        {
	            for(YoungerDTO y : objThirdDTO.getListYoungerDTO())
	            {
	                if(y.isSelect())
	                {
	                    youngerData =("						<DatosMenor>\n" +
	                                "							<NumeroDocumento>" + y.getIdNumber() + "</NumeroDocumento>\n" +
	                                "							<NombreCompleto>" + y.getName() + "</NombreCompleto>\n" +
	                                "							<Direccion>" + y.getAddress().replace(" ","")+ "</Direccion>\n" +
	                                "							<TipoIdentificacion>\n" +
	                                "                                                       <Codigo>" + y.getIdType()+ "</Codigo>\n" +
	                                "                                                   </TipoIdentificacion>\n" +
	                                "						</DatosMenor>\n");
	                    youngerTag = ("<MenorEdad>" + younger + "</MenorEdad>\n" +
	"					<Parentesco>\n" +
	"						<Codigo>" + related + "</Codigo>\n" +
	"					</Parentesco>");
	                    break;
	                }
	            }
	        }
	        else
	        {
	            youngerTag = ("<MenorEdad>" + younger + "</MenorEdad>\n");
	        }
	        
	        if (privadoLibertad.size()>0)
	        {
	            privado = "                                       <IdDatosSolicitantePriv>\n"+
	"                                               <IdTipodeIdentificacion>\n"+
	"                                                       <Codigo>"+privadoLibertad.get(0).getIdType()+"</Codigo>\n"+
	"                                               </IdTipodeIdentificacion>\n"+
	"                                               <SNombreCompleto>"+privadoLibertad.get(0).getName()+"</SNombreCompleto>\n"+
	"                                               <SNumerodeIdentificacion>"+privadoLibertad.get(0).getIdNumber()+"</SNumerodeIdentificacion>\n"+
	"                                       </IdDatosSolicitantePriv>\n";
	        }
	        else
	        {
	            privado = " <IdDatosSolicitantePriv>  </IdDatosSolicitantePriv>\n";
	        }
	        objWebServiceDTO.setUrl(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "urlBIZAGY") + "workflowenginesoa.asmx");
	        
	        objWebServiceDTO.setParams("<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:tem=\"http://tempuri.org/\">\n" +
	"   <soap:Header/>\n" +
	"   <soap:Body>\n" +
	"      <tem:createCasesAsString>\n" +
	"         <!--Optional:-->\n" +
	"         <tem:casesInfo>\n" + 
	"           <![CDATA[<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"+
	"          <BizAgiWSParam>\n" +
	"	<domain>" + PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "domain", "7") + "</domain>\n" +
	"	<userName>" + PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "userName", "7") + "</userName>\n" +
	"	<Cases>\n" +
	"		<Case>\n" +
	"			<Process>" + PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "Process", "7") + "</Process>\n" +
	"			<Entities>\n" +
	"				<SolicitudCertificado>\n" +
	"                                       <Localidad businessKey=\"Codigo='" + objThirdDTO.getLocation() + "'\"/>\n" +        
	"					<TipoTramite>\n" +
	"						<Codigo>" + procedureType + "</Codigo>\n" +
	"					</TipoTramite>\n" +privado+
	"					<Ciudadano>\n" +
	"						<NumeroIdentificacion>" + objThirdDTO.getIdNumber() + "</NumeroIdentificacion>\n" +
	"						<TipoIdentificacion>\n" +
	"							<Codigo>" + objThirdDTO.getIdType() + "</Codigo>\n" +
	"						</TipoIdentificacion>\n" +
	"						<Direccion>" + objThirdDTO.getAddress().replace(" ","")  +" "+ objThirdDTO.getAddressCompl() +" "+ objThirdDTO.getRuralAddress() + "</Direccion>\n" +
	"						<CorreoElectronico>" + objThirdDTO.getEmail() + "</CorreoElectronico>\n" +
	"						<PrimerNombre>" + objThirdDTO.getFirstName() + "</PrimerNombre>\n" +
	"						<SegundoApellido>" + objThirdDTO.getSecondLastName() + "</SegundoApellido>\n" +
	"						<PrimerApellido>" + objThirdDTO.getLastName() + "</PrimerApellido>\n" +
	"						<Segundonombre>" + objThirdDTO.getSecondName() + "</Segundonombre>\n" +
	youngerData + 
	"					</Ciudadano>\n" +
	youngerTag +
	"					<RequiereApostillaje>" + apostille + "</RequiereApostillaje>\n" +
	"					<Radicado>" + radicado + "</Radicado>\n" +
	"					<IdentificacionCiudadano>" + objThirdDTO.getIdNumber() + "</IdentificacionCiudadano>\n" +
	"					<Fecha>" + day + "T" + hour + ".0Z" + "</Fecha>\n" +
	"					<DocumentosAdjuntos>\n" +
	"						<DocumentosAdjuntos>\n" +
	"                                                   <TipoDocumento>\n" +
	"                                                       <Codigo>" + PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "TipoDocumentoSolicitante", "7") + "</Codigo>\n" +
	"                                                   </TipoDocumento>\n" +
	"                                                   <NombreDocumento>Solicitante " + objThirdDTO.getIdNumber() + "</NombreDocumento>\n" +
	"                                                   <ItemId>" + itemId + "</ItemId>\n" +
	"                                                   <ThirdPartyId>" + PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "ThirdPartyId", "7") + "</ThirdPartyId>\n" +
	"                                                   <ListId>" + listId + "</ListId>\n" +
	"						</DocumentosAdjuntos>\n" +
	"						<DocumentosAdjuntos>\n" +
	"                                                   <TipoDocumento>\n" +
	"                                                       <Codigo>" + PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "TipoDocumentoRecibo", "7") + "</Codigo>\n" +
	"                                                   </TipoDocumento>\n" +
	"                                                   <NombreDocumento>Recibo Publico " + objThirdDTO.getIdNumber() + "</NombreDocumento>\n" +
	"                                                   <ItemId>" + itemId + "</ItemId>\n" +
	"                                                   <ThirdPartyId>" + PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "ThirdPartyId", "7") + "</ThirdPartyId>\n" +
	"                                                   <ListId>" + listId + "</ListId>\n" +
	"						</DocumentosAdjuntos>\n" +
	(uploadedFileYounger!=null? "						<DocumentosAdjuntos>\n" +
	"                                                   <TipoDocumento>\n" +
	"                                                       <Codigo>" + PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "TipoDocumentoMenor", "7") + "</Codigo>\n" +
	"                                                   </TipoDocumento>\n" +
	"                                                   <NombreDocumento>Identidad Menor " + objThirdDTO.getIdNumber() + "</NombreDocumento>\n" +
	"                                                   <ItemId>" + itemId + "</ItemId>\n" +
	"                                                   <ThirdPartyId>" + PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "ThirdPartyId", "7") + "</ThirdPartyId>\n" +
	"                                                   <ListId>" + listId + "</ListId>\n" +
	"						</DocumentosAdjuntos>\n":"") +
	(uploadedFileCustody!=null? "						<DocumentosAdjuntos>\n" +
	"                                                   <TipoDocumento>\n" +
	"                                                       <Codigo>" + PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "TipoDocumentoCustodia", "7") + "</Codigo>\n" +
	"                                                   </TipoDocumento>\n" +
	"                                                   <NombreDocumento>Patria Potestad " + objThirdDTO.getIdNumber() + "</NombreDocumento>\n" +
	"                                                   <ItemId>" + itemId + "</ItemId>\n" +
	"                                                   <ThirdPartyId>" + PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "ThirdPartyId", "7") + "</ThirdPartyId>\n" +
	"                                                   <ListId>" + listId + "</ListId>\n" +
	"						</DocumentosAdjuntos>\n":"") +
	"					</DocumentosAdjuntos>\n" +
	"				</SolicitudCertificado>\n" +
	"			</Entities>\n" +
	"		</Case>\n" +
	"	</Cases>\n" +
	"</BizAgiWSParam>]]>\n" +
	"         </tem:casesInfo>\n" +
	"      </tem:createCasesAsString>\n" +
	"   </soap:Body>\n" +
	"</soap:Envelope>");
	        
	        objWebServiceDTO.setMethod("POST");
	        objWebServiceDTO.getHeader().put("Content-Type", "application/soap+xml;charset=UTF-8");
	        objWebServiceDTO.setSoapAction("http://tempuri.org/createCases");
	        if (log.isDebugEnabled()) {	        	
	        	log.info("Voy a consumir el servicio web de crear certificado de BIZAGI.\n\n\n");
	        	log.info("\n\n\nEste es el XML de Parametros enviado:\n --------INICIO XML----------\n\n\n");
	        	log.info(objWebServiceDTO.getParams());
	        	log.info("\n --------FIN XML----------\n\n\n");
	        }
	        objWebServiceDTO = ws.SoapWithParam(objWebServiceDTO);
	        String responseMensaje = "";
            if (objWebServiceDTO.getXmlFile().contains("errorCode") || objWebServiceDTO.getXmlFile().contains("errorCode")) {
            	log.info("Bizagi TagError");
            	String xmlFileTemp = objWebServiceDTO.getXmlFile().replace("&gt;", ">").replace("&lt;", "<").replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
            	String response = XmlHandler.ReadXmlFileGetTag(xmlFileTemp, "errorCode");
            	if(response != null && !response.isEmpty()) {
            		responseMensaje = XmlHandler.ReadXmlFileGetTag(xmlFileTemp, "errorMessage"); 
            		objWebServiceDTO.setRespuesta(false);
            	}
            }
	        this.logAuditoriaDTO.setAuMensajeError("SecondRequest " + "Voy a consumir el servicio web de crear certificado de BIZAGI. " + responseMensaje);
	        this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
	        this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
	        this.logAuditoriaDTO.setAuLogUsuario(objThirdDTO.getIdNumber());
	        this.logAuditoriaDTO.setAuAplicacion("BIZAGI");
	        logService.AddLogAuditoria(logAuditoriaDTO);
	        /*log.info("Se va a leer el XML resultante de BIZAGI.");
	        String process = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "processId");
	        log.info("Se consumió el servicio web de crear certificado de BIZAGI correctamente.");
	        if(process.equals("0"))
	        {
	            objWebServiceDTO.setRespuesta(false);
	            String errorMessage= XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "ErrorMessage");
	            objWebServiceDTO.setToken(errorMessage);
	        }*/
	        return objWebServiceDTO;
    	} catch (Throwable t) {
    		log.error(t.getMessage(), t);
    	}
    	return null;
    }
    
    @Override
    public WebServiceDTO CreateThird(String idType, String idNumber, String     firstName, String secondName, String lastName,
            String secondLastName, String birthDate, String gender, String sexo, String sexualOrient, String nationality, String cellphone, String phone, 
            String address, String ruralAddress, String location, String neightborhood, String residenceCity, String occupation, 
            String specialCondition, String stratum, String email, String user, List<PropertieDTO> listPropertieDTOs) 
    {
        String params = "{\"datosBasicos\": {"
                + "\n\"fechaFinVigencia\" : \"" + PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "fechaFinVigencia") + "\","
                + "\n\"numeroIdentificacion\": \"" + idNumber + "\","
                + "\n\"fechaNacimiento\": \"" + birthDate + "T09:09:09Z\","
                + "\n\"primerApellido\": \"" + lastName + "\","
                + "\n\"primerNombre\": \"" + firstName + "\","
                + "\n\"segundoApellido\": \"" + secondLastName + "\","
                + "\n\"segundoNombre\": \"" + secondName + "\","
                + "\n\"sexo\": \"" + sexo + "\","
                + "\n\"genero\": \"" + gender + "\","
                + "\n\"tipoIdentificacion\": \"" + idType + "\","
                + "\n\"tipoTercero\": \"" + PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "tipoTercero") + "\","
                + "\n\"usuario\": \"" + user + "\""
                + "},"
                + "\n\"fechaActualizacion\": \"" + PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "fechaActualizacion") + "\","
                + "\n\"usuarioModificacion\": \"" + user + "\","
                + "\n\"direccionIP\": \"" + PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "direccionIP") + "\","
                + "\n\"identificacionTercero\": \"" + idNumber.replaceAll("[^0-9]", "") + "\","
                + "\n\"ubicacion\":"
                + "\n{"
                + "\n\"barrio\" : \"" + neightborhood + "\","
                + "\n\"ciudad\": {"
                + "\n\"identificador\": \"" + residenceCity + "\","
                + "\n},"
                + "\n\"correoElectronico\" : \"" + email + "\","
                + "\n\"direccion\" : \"" + address.replace(" ","") + "\","
                + "\n\"direccionRural\" : \"" + ruralAddress + "\","
                + "\n\"estado\" : \"1\","
                + "\n\"fechaFinVigencia\" : \"" + PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "fechaFinVigencia2") + "\","
                + "\n\"localidad\" : \"" + location + "\","
                + "\n\"telefonoCelular\" : \"" + cellphone + "\","
                + "\n\"telefonoFijo\" : \"" + phone + "\","
                + "\n\"tipoZona\" : \"" + PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "tipoZona") + "\""
                + "\n}"
                + "\n}";
        WebServiceDTO objWebServiceDTO = new WebServiceDTO();
        objWebServiceDTO.getHeader().put("Content-Type", "application/json");
        objWebServiceDTO.setMethod("POST");
        objWebServiceDTO.setParams(params);
        objWebServiceDTO.setUrl(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "urlTerceros") + "/tercero/registrar");
        log.info("Voy a consumir el servicio web de crear tercero para el menor de edad.");
        objWebServiceDTO = ws.postServiceWithParam(objWebServiceDTO);
        log.info("Se consumió el servicio web de crear tercero para el menor de edad correctamente.");
        this.logAuditoriaDTO.setAuMensajeError("CreateThird " + "Se consumió el servicio web de crear tercero para el menor de edad correctamente. " );
        this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
        this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
        this.logAuditoriaDTO.setAuLogUsuario(idNumber);
        this.logAuditoriaDTO.setAuAplicacion("TERCEROS");
        logService.AddLogAuditoria(logAuditoriaDTO);
        if(objWebServiceDTO.isRespuesta())
        {
            String params2 = "{\n" +
                "  \"datosAdicionalesDTO\": [{\n" +
                "    \"estado\": \"5\",\n" +
                "    \"identificacionTercero\": \"" + idNumber + "\",\n" +
                "    \"tipoDatoAdicional\": {\n" +
                "      \"identificador\": \"4\"\n" +
                "    },\n" +
                "    \"valor\": \"" + occupation + "\"\n" +
                "  }, {\n" +
                "    \"estado\": \"1\",\n" +
                "    \"identificacionTercero\": \"" + idNumber + "\",\n" +
                "    \"tipoDatoAdicional\": {\n" +
                "      \"identificador\": \"3\"\n" +
                "    },\n" +
                "    \"valor\": \"" + nationality + "\"\n" +
                "  }, {\n" +
                "    \"estado\": \"1\",\n" +
                "    \"identificacionTercero\": \"" + idNumber + "\",\n" +
                "    \"tipoDatoAdicional\": {\n" +
                "      \"identificador\": \"5\"\n" +
                "    },\n" +
                "    \"valor\": \"" + stratum + "\"\n" +
                "  }, {\n" +
                "    \"estado\": \"1\",\n" +
                "    \"identificacionTercero\": \"" + idNumber + "\",\n" +
                "    \"tipoDatoAdicional\": {\n" +
                "      \"identificador\": \"6\"\n" +
                "    },\n" +
                "    \"valor\": \"" + specialCondition + "\"\n" +
                "  }, {\n" +
                "    \"estado\": \"1\",\n" +
                "    \"identificacionTercero\": \"" + idNumber + "\",\n" +
                "    \"tipoDatoAdicional\": {\n" +
                "      \"identificador\": \"7\"\n" +
                "    },\n" +
                "    \"valor\": \"" + gender + "\"\n" +
                "  }, {\n" +
                "    \"estado\": \"1\",\n" +
                "    \"identificacionTercero\": \"" + idNumber + "\",\n" +
                "    \"tipoDatoAdicional\": {\n" +
                "      \"identificador\": \"8\"\n" +
                "    },\n" +
                "    \"valor\": \"" + sexo + "\"\n" +
                "  }, {\n" +
                "    \"estado\": \"1\",\n" +
                "    \"identificacionTercero\": \"" + idNumber + "\",\n" +
                "    \"tipoDatoAdicional\": {\n" +
                "      \"identificador\": \"9\"\n" +
                "    },\n" +
                "    \"valor\": \"" + sexualOrient + "\"\n" +
                "  }]\n" +
                "}";
            WebServiceDTO objWebServiceDTO2 = new WebServiceDTO();
            objWebServiceDTO2.setMethod("POST");
            objWebServiceDTO2.setParams(params2);
            objWebServiceDTO2.getHeader().put("Content-Type", "application/json");
            objWebServiceDTO2.setUrl(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "urlTerceros") + "/tercero/datosadicionales/registrar");
            log.info("Voy a consumir el servicio web de crear las listas adicionales de tercero.");
            objWebServiceDTO2 = ws.postServiceWithParam(objWebServiceDTO2);
            log.info("Se consumió el servicio web de crear las listas adicionales de tercero correctamente.");
            this.logAuditoriaDTO.setAuMensajeError("CreateThird " + "Se consumió el servicio web de crear las listas adicionales de tercero correctamente. " );
            this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
            this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
            this.logAuditoriaDTO.setAuLogUsuario(idNumber);
            this.logAuditoriaDTO.setAuAplicacion("TERCEROS");
            logService.AddLogAuditoria(logAuditoriaDTO);
        }
        return objWebServiceDTO;
    }

    @Override
    public WebServiceDTO CorrectRequest(WebServiceDTO objWebServiceDTO, ThirdDTO objThirdDTO, 
            String procedureType, String externalRelation, List<PropertieDTO> listPropertieDTOs, String related, String younger, String radNumber,
            String oldRadNumber, String certificateKey, String certificateKey2, String atentionDate, String itemId, String listId, UploadedFile uploadedFileAttendant, 
            UploadedFile uploadedFilePublicService, UploadedFile uploadedFileYounger, UploadedFile uploadedFileCustody, List<LegalAgentDTO> privadoLibertad) 
    {
        Date now = new Date();
        SimpleDateFormat formatDay = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatHour = new SimpleDateFormat("hh:mm:ss");
        String day = formatDay.format(now);
        String hour = formatHour.format(now);
        String youngerData = "";
        String youngerTag = "";
        String privado ="";
        if(younger.equals("1"))
        {
            for(YoungerDTO y : objThirdDTO.getListYoungerDTO())
            {
                if(y.isSelect())
                {
                    youngerData =("						<DatosMenor>\n" +
                                "							<NumeroDocumento>" + y.getIdNumber() + "</NumeroDocumento>\n" +
                                "							<NombreCompleto>" + y.getName() + "</NombreCompleto>\n" +
                                "							<Direccion>" + y.getAddress().replace(" ","")+ "</Direccion>\n" +
                                "							<TipoIdentificacion>\n" +
                                "                                                       <Codigo>" + y.getIdType()+ "</Codigo>\n" +
                                "                                                   </TipoIdentificacion>\n" +
                                "						</DatosMenor>\n");
                    youngerTag = ("             <MenorEdad>true</MenorEdad>\n" +
"					<Parentesco>\n" +
"						<Codigo>" + related + "</Codigo>\n" +
"					</Parentesco>");
                    break;
                }
            }
        }
        else
        {
            youngerTag = ("<MenorEdad>false</MenorEdad>\n");
        }
        
        if(privadoLibertad.size()>0)
        {
            privado ="                     <IdDatosSolicitantePriv>\n"+
"                           <IdTipodeIdentificacion>\n"+
"                               <Codigo>"+privadoLibertad.get(0).getIdType()+"</Codigo>\n"+
"                           </IdTipodeIdentificacion>\n"+
"                           <SNombreCompleto>"+privadoLibertad.get(0).getName()+"</SNombreCompleto>\n"+
"                           <SNumerodeIdentificacion>"+privadoLibertad.get(0).getIdNumber()+"</SNumerodeIdentificacion>\n"+
"                     </IdDatosSolicitantePriv>\n";
        }
        else
        {
            privado ="                     <IdDatosSolicitantePriv> </IdDatosSolicitantePriv>\n";
        }
               
        objWebServiceDTO.setParams("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n" +
"   <soapenv:Header/>\n" +
"   <soapenv:Body>\n" +
"      <tem:setEventAsString>\n" +
"         <!--Optional:-->\n" +
"         <tem:eventInfo>\n" + 
"       <![CDATA[<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"+
"<BizAgiWSParam>\n" +
"    <domain>domain</domain>\n" +
"    <userName>admon</userName>\n" +
"    <Events>\n" +
"        <Event>\n" +
"          <EventData>\n" +
"              <idCase>" + certificateKey + "</idCase>\n" +
"              <idTask>" + PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "idTask", "11") + "</idTask>\n" +
"          </EventData>\n" +
"          <Entities>\n" +
"		 <SolicitudCertificado key=\"" + certificateKey2 + "\">\n" +
"                     <FechaAtencion>" + atentionDate + "T14:45:49</FechaAtencion>\n" +
"                     <Fecha>" + day + "T" + hour + "</Fecha>\n" +
"                     <IdentificacionCiudadano>" + objThirdDTO.getIdNumber() + "</IdentificacionCiudadano>\n" +
"                     <NumRadicado>" + certificateKey + "</NumRadicado>\n" +
"                     <IdCaso>" + certificateKey + "</IdCaso>\n" +
"                     <Radicado>" + radNumber + "</Radicado>\n" +
youngerTag +
"                     <CausaRechazo/>\n" +
"                     <Ciudadano entityName=\"Ciudadano\" >\n" +
"                        <NumeroIdentificacion>" + objThirdDTO.getIdNumber() + "</NumeroIdentificacion>\n" +
"                        <NombreCompleto>" + objThirdDTO.getFirstName() + " " + objThirdDTO.getSecondName() + " " + objThirdDTO.getLastName() + " " +
                objThirdDTO.getSecondLastName() + "</NombreCompleto>\n" +
"                        <PrimerApellido>" + objThirdDTO.getLastName() + "</PrimerApellido>\n" +
"                        <PrimerNombre>" + objThirdDTO.getFirstName()+ "</PrimerNombre>\n" +
"                        <SegundoApellido>" + objThirdDTO.getSecondLastName()+ "</SegundoApellido>\n" +
"                        <Segundonombre>" + objThirdDTO.getSecondName()+ "</Segundonombre>\n" +
"   				     <Direccion>" + objThirdDTO.getAddress().replace(" ","")  +" "+ objThirdDTO.getAddressCompl() +" "+ objThirdDTO.getRuralAddress() + "</Direccion>\n" +
"						 <CorreoElectronico>" + objThirdDTO.getEmail() + "</CorreoElectronico>\n" +
youngerData +
"                     </Ciudadano>\n" +privado+      
"                     <Parentesco/>\n" +
"					<DocumentosAdjuntos>\n" +
"						<DocumentosAdjuntos>\n" +
"                                                   <TipoDocumento>\n" +
    "                                                       <Codigo>" + PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "TipoDocumentoSolicitante", "11") + "</Codigo>\n" +
"                                                   </TipoDocumento>\n" +
"                                                   <NombreDocumento>Solicitante " + objThirdDTO.getIdNumber() + "</NombreDocumento>\n" +
"                                                   <ItemId>" + itemId + "</ItemId>\n" +
"                                                   <ThirdPartyId>" + PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "ThirdPartyId", "7") + "</ThirdPartyId>\n" +
"                                                   <ListId>" + listId + "</ListId>\n" +
"						</DocumentosAdjuntos>\n" +
"						<DocumentosAdjuntos>\n" +
"                                                   <TipoDocumento>\n" +
"                                                       <Codigo>" + PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "TipoDocumentoRecibo", "11") + "</Codigo>\n" +
"                                                   </TipoDocumento>\n" +
"                                                   <NombreDocumento>Recibo Publico " + objThirdDTO.getIdNumber() + "</NombreDocumento>\n" +
"                                                   <ItemId>" + itemId + "</ItemId>\n" +
"                                                   <ThirdPartyId>" + PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "ThirdPartyId", "7") + "</ThirdPartyId>\n" +
"                                                   <ListId>" + listId + "</ListId>\n" +
"						</DocumentosAdjuntos>\n" +
(uploadedFileYounger!=null? "						<DocumentosAdjuntos>\n" +
"                                                   <TipoDocumento>\n" +
"                                                       <Codigo>" + PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "TipoDocumentoMenor", "11") + "</Codigo>\n" +
"                                                   </TipoDocumento>\n" +
"                                                   <NombreDocumento>Identidad Menor " + objThirdDTO.getIdNumber() + "</NombreDocumento>\n" +
"                                                   <ItemId>" + itemId + "</ItemId>\n" +
"                                                   <ThirdPartyId>" + PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "ThirdPartyId", "7") + "</ThirdPartyId>\n" +
"                                                   <ListId>" + listId + "</ListId>\n" +
"						</DocumentosAdjuntos>\n":"") +
(uploadedFileCustody!=null? "						<DocumentosAdjuntos>\n" +
"                                                   <TipoDocumento>\n" +
"                                                       <Codigo>" + PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "TipoDocumentoCustodia", "11") + "</Codigo>\n" +
"                                                   </TipoDocumento>\n" +
"                                                   <NombreDocumento>Patria Potestad " + objThirdDTO.getIdNumber() + "</NombreDocumento>\n" +
"                                                   <ItemId>" + itemId + "</ItemId>\n" +
"                                                   <ThirdPartyId>" + PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "ThirdPartyId", "7") + "</ThirdPartyId>\n" +
"                                                   <ListId>" + listId + "</ListId>\n" +
"						</DocumentosAdjuntos>\n":"") +
"					</DocumentosAdjuntos>\n" +
"                  </SolicitudCertificado>\n" +
"          </Entities>\n" +
"        </Event>\n" +
"    </Events>\n" +
"  </BizAgiWSParam>]]>\n" +
"</tem:eventInfo>\n" +
"      </tem:setEventAsString>\n" +
"   </soapenv:Body>\n" +
"</soapenv:Envelope>");
        
        objWebServiceDTO.setUrl(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "urlBIZAGY") + "workflowenginesoa.asmx");
        objWebServiceDTO.setMethod("POST");
        objWebServiceDTO.getHeader().put("Content-Type", "text/xml;charset=utf-8");
        objWebServiceDTO.setSoapAction("http://tempuri.org/setEventAsString");
        log.info("Voy a consumir el servicio web de Corregir Certificados de BIZAGI.");
        objWebServiceDTO = ws.SoapWithParam(objWebServiceDTO);
        String responseMensaje = "";
        if (objWebServiceDTO.getXmlFile().contains("errorCode") || objWebServiceDTO.getXmlFile().contains("errorCode")) {
        	log.info("Bizagi TagError");
        	String xmlFileTemp = objWebServiceDTO.getXmlFile().replace("&gt;", ">").replace("&lt;", "<").replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
        	String response = XmlHandler.ReadXmlFileGetTag(xmlFileTemp, "errorCode");
        	if(response != null && !response.isEmpty()) {
        		responseMensaje = XmlHandler.ReadXmlFileGetTag(xmlFileTemp, "errorMessage");
        		objWebServiceDTO.setRespuesta(false);
        	}
        }
        log.info("Se consumió el servicio web de Corregir Certificados de BIZAGI correctamente.");
        this.logAuditoriaDTO.setAuMensajeError("CorrectRequest " + "Se consumió el servicio web de Corregir Certificados de BIZAGI correctamente. " + responseMensaje);
        this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
        this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
        this.logAuditoriaDTO.setAuLogUsuario(objThirdDTO.getIdNumber());
        this.logAuditoriaDTO.setAuAplicacion("BIZAGI");
        logService.AddLogAuditoria(logAuditoriaDTO);
        /*String process = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "processId");
        if(process!=null)
        {
            if(process.equals("0"))
            {
                objWebServiceDTO.setRespuesta(false);
                String errorMessage= XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "ErrorMessage");
                objWebServiceDTO.setToken(errorMessage);
            }
        }*/
        return objWebServiceDTO;
    }

    @Override
    public String[] getFilesTag(String xml) 
    {
        String tags[] = new String[2];
        log.info("Se van a leer los tags de los archivos de ADAX para enviar a BIZAGI.");
        tags[0] = XmlHandler.ReadXmlFileGetTag(xml, "a:ItemId");
        tags[1] = XmlHandler.ReadXmlFileGetTag(xml, "a:ListId");
        log.info("Se leyeron los tags de los archivos de ADAX para enviar a BIZAGI correctamente.");
        return tags;
    }

    @Override
    public String getRadicadoNumberOrfeo(ThirdDTO objThirdDTO, List<PropertieDTO> listPropertieDTOs)
    {
        String radNumber = null;
        try
        {
            String tipoEndpointOrfeo = (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "TipoEndpointOrfeo", "5") == null ? "2" : PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "TipoEndpointOrfeo", "5"));  	
            if (tipoEndpointOrfeo.equals("2")) {
            	tipoEndpointOrfeo = "<file xsi:type=\"xsd:base64Binary\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "file", "1")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "file", "1")) + "</file>\n";
            }else {
            	tipoEndpointOrfeo = "         <file xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "file", "1")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "file", "1")) + "</file>\n";
            }
            String params = "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:co.gobierno.siactua.proovedor\">\n" +
                            "   <soapenv:Header/>\n" +
                            "   <soapenv:Body>\n" +
                            "      <urn:radicarDocumentoP soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                            tipoEndpointOrfeo + 
                            //"         <file xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "file", "1")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "file", "1")) + "</file>\n" +
                            "         <fileName xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "filename", "1")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "filename", "1")) + "</fileName>\n" +
                            "         <login xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "login", "1")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "login", "1")) + "</login>\n" +
                            "         <cc xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "cc", "1")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "cc", "1")) + "</cc>\n" +
                            "         <destinatarioOrg xsi:type=\"xsd:string\">" + "0||" + objThirdDTO.getIdNumber() + "||0||" + objThirdDTO.getFirstName()+ "||" + objThirdDTO.getSecondName()+ "||" + objThirdDTO.getLastName()+ "||" + objThirdDTO.getSecondLastName()+ "||" + objThirdDTO.getCellphone()+ "||" + objThirdDTO.getAddress().replace(" ","") +" "+ objThirdDTO.getAddressCompl() + " " + objThirdDTO.getRuralAddress() + "||" + objThirdDTO.getEmail() + "||1||170||11||1" + "</destinatarioOrg>\n" +
                            "         <predioOrg xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "predioOrg", "1")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "predioOrg", "1")) + "</predioOrg>\n" +
                            "         <espOrg xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "espOrg", "1")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "espOrg", "1")) + "</espOrg>\n" +
                            "         <asu xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "asuCR", "1")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "asuCR", "1")) + "</asu>\n" +
                            "         <med xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "med", "1")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "med", "1")) + "</med>\n" +
                            "         <ane xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "ane", "1")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "ane", "1")) + "</ane>\n" +
                            "         <coddepe xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "coddepe", "1")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "coddepe", "1")) + "</coddepe>\n" +
                            "         <tpRadicado xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "tpRadicado", "1")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "tpRadicado", "1")) + "</tpRadicado>\n" +
                            "         <cuentai xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "cuentai", "1")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "cuentai", "1")) + "</cuentai>\n" +
                            "         <radi_usua_actu xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "radi_usua_actu", "1")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "radi_usua_actu", "1")) + "</radi_usua_actu>\n" +
                            "         <numeroFolios xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "numeroFolios", "1")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "numeroFolios", "1")) + "</numeroFolios>\n" +
                            "         <tdoc xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "tdoc", "1")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "tdoc", "1")) + "</tdoc>\n" +
                            "         <tip_doc xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "tip_doc", "1")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "tip_doc", "1")) + "</tip_doc>\n" +
                            "         <carp_codi xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "carp_codi", "1")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "carp_codi", "1")) + "</carp_codi>\n" +
                            "         <carp_per xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "carp_per", "1")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "carp_per", "1")) + "</carp_per>\n" +
                            "      </urn:radicarDocumentoP>\n" +
                            "   </soapenv:Body>\n" +
                            "</soapenv:Envelope>";
            WebServiceDTO objWebServiceDTO = new WebServiceDTO();
            objWebServiceDTO.setParams(params);
            objWebServiceDTO.setUrl(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "urlORFEO") );
            objWebServiceDTO.getHeader().put("Content-Type", "text/xml;charset=UTF-8");
            objWebServiceDTO.setMethod("POST");
            objWebServiceDTO.setSoapAction("urn:co.gobierno.siactua.proovedor#radicarDocumentoP");
            log.info("Se va a consumir el servicio de radicarDocumentoP de ORFEO...");
            objWebServiceDTO = ws.SoapWithParam(objWebServiceDTO);
            this.logAuditoriaDTO.setAuMensajeError("getRadicadoNumberOrfeo " + "Se va a consumir el servicio de radicarDocumentoP de ORFEO... " );
            this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
            this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
            this.logAuditoriaDTO.setAuLogUsuario(objThirdDTO.getIdNumber());
            this.logAuditoriaDTO.setAuAplicacion("ORFEO");
            logService.AddLogAuditoria(logAuditoriaDTO);
          //TODO:Validar TagError
            String responseMensaje = "";
            if (objWebServiceDTO.getXmlFile().contains("codigoError")) {
            	log.info("Orfeo TagError");
            	String response = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "codigoError");
            	if(response != null && response.contains("0")) {
            		responseMensaje = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "mensaje");
            		objWebServiceDTO.setRespuesta(false);
                    this.logAuditoriaDTO.setAuMensajeError("getRadicadoNumberOrfeo " + "Se va a consumir el servicio de radicarDocumentoP de ORFEO... " );
                    this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
                    this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
                    this.logAuditoriaDTO.setAuLogUsuario(objThirdDTO.getIdNumber());
                    this.logAuditoriaDTO.setAuAplicacion("ORFEO");
                    logService.AddLogAuditoria(logAuditoriaDTO);
            		return radNumber;
            	}
            }
            if(objWebServiceDTO.isRespuesta())
            {
                if(objWebServiceDTO.getXmlFile()!=null && !objWebServiceDTO.getXmlFile().equals(""))
                {
                    radNumber = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "return");
                    if(radNumber!=null && !radNumber.equals(""))
                    {
                        log.info("Se consumio correctamente este servicio web.");
                        return radNumber;
                    }
                    else
                    {
                        log.error("El numero de radicado es nulo o esta vacio.");
                    }
                }
                else
                {
                    log.error("El xml que retorno el servicio web esta vacio o es nulo.");
                }
            }
            else
            {
                log.error("Error al consumir el servicio web de radicarDocumentoP de ORFEO.");
                return null;
            }
        }
        catch(Exception e)
        {
            log.error("Error en CertificateRequestServiceImpl en el metodo de obtener el numero de radicado: " + e.toString());
        }
        return null;
    }

    @Override
    public WebServiceDTO AppendExpedient(String radNumber, String expNumber, List<PropertieDTO> listPropertieDTOs)
    {
        String appendNumber = null;
        try
        {
            String params = "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:org.gobierno.webserviceorfeo\">\n" +
                            "   <soapenv:Header/>\n" +
                            "   <soapenv:Body>\n" +
                            "      <urn:anexarExpediente soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                            "         <numRadicado xsi:type=\"xsd:string\">" + radNumber + "</numRadicado>\n" +
                            "         <numExpediente xsi:type=\"xsd:string\">" + expNumber + "</numExpediente>\n" +
                            "         <usuaLogin xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "usuaLogin", "3")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "usuaLogin", "3")) + "</usuaLogin>\n" +
                            "         <observa xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "observa", "3")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "observa", "3")) + "</observa>\n" +
                            "      </urn:anexarExpediente>\n" +
                            "   </soapenv:Body>\n" +
                            "</soapenv:Envelope>";
            WebServiceDTO objWebServiceDTO = new WebServiceDTO();
            objWebServiceDTO.setParams(params);
            objWebServiceDTO.setUrl(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "urlORFEO") );
            objWebServiceDTO.getHeader().put("Content-Type", "text/xml;charset=UTF-8");
            objWebServiceDTO.setMethod("POST");
            log.info("Se va a consumir el servicio de anexar expediente de ORFEO...");
            objWebServiceDTO = ws.SoapWithParam(objWebServiceDTO);
          //TODO:Validar TagError
            String responseMensaje = "";
            if (objWebServiceDTO.getXmlFile().contains("codigoError")) {
            	log.info("Orfeo TagError");
            	String response = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "codigoError");
            	if(response != null && response.contains("0")) {
            		responseMensaje = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "mensaje");
            		objWebServiceDTO.setRespuesta(false);
            	}
            }
            this.logAuditoriaDTO.setAuMensajeError("AppendExpedient " + "Se va a consumir el servicio de anexar expediente de ORFEO.... " + responseMensaje );
            this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
            this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
            this.logAuditoriaDTO.setAuLogUsuario(radNumber);
            this.logAuditoriaDTO.setAuAplicacion("ORFEO");
            logService.AddLogAuditoria(logAuditoriaDTO);
            if(objWebServiceDTO.isRespuesta())
            {
                if(objWebServiceDTO.getXmlFile()!=null && !objWebServiceDTO.getXmlFile().equals(""))
                {
                    appendNumber = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "return");
                    if(appendNumber!=null && !appendNumber.equals(""))
                    {
                        log.info("Se consumio correctamente este servicio web.");
                        return objWebServiceDTO;
                    }
                    else
                    {
                        log.error("El numero del anexo es nulo o esta vacio.");
                        objWebServiceDTO.setRespuesta(false);
                        return objWebServiceDTO;
                    }
                }
                else
                {
                    log.error("El xml que retorno el servicio web esta vacio o es nulo.");
                    objWebServiceDTO.setRespuesta(false);
                    return objWebServiceDTO;
                }
            }
            else
            {
                log.error("Error al consumir el servicio web de Anexar Expediente de ORFEO.");
                objWebServiceDTO.setRespuesta(false);
                return objWebServiceDTO;
            }
        }
        catch(Exception e)
        {
            log.error("Error en PHRegisterServiceImpl en el metodo de obtener el numero del anexo: " + e.toString());
            return null;
        }
    }

    @Override
    public String CreateAppend(String radNumber, UploadedFile objUploadedFile, List<PropertieDTO> listPropertieDTOs, String idFile, String fileName)
    {
        String response = null;
        try
        {
            String base64 = null;
            if (objUploadedFile != null)
            {
                log.info("Se va a convertir el archivo a base64...");
                base64 = (Base64Handler.convertFileToBase64Binary(objUploadedFile.getInputstream()));
                String ext = objUploadedFile.getFileName().substring(objUploadedFile.getFileName().lastIndexOf('.'));
                log.info("Archivo convertido correctamente.");
                String tipoEndpointOrfeo = (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "TipoEndpointOrfeo", "5") == null ? "2" : PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "TipoEndpointOrfeo", "5"));  	
                if (tipoEndpointOrfeo.equals("2")) {
                	tipoEndpointOrfeo = "<file xsi:type=\"xsd:base64Binary\">" + base64 + "</file>\n";
                }else {
                	tipoEndpointOrfeo = "         <file xsi:type=\"xsd:string\">" + base64 + "</file>\n";
                }
                String params = "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:org.gobierno.webserviceorfeo\">\n"
                        + "   <soapenv:Header/>\n"
                        + "   <soapenv:Body>\n"
                        + "      <urn:crearAnexo soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n"
                        + "         <radiNume xsi:type=\"xsd:string\">" + radNumber + "</radiNume>\n"
                        //+ "         <file xsi:type=\"xsd:string\">" + base64 + "</file>\n"
                        +           tipoEndpointOrfeo
                        + "         <filename xsi:type=\"xsd:string\">" + fileName + ext +"</filename>\n"
                        + "         <descripcion xsi:type=\"xsd:string\">" + idFile + fileName + ext +"</descripcion>\n"
                        + "         <usuaDoc xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "usuaDoc", "4") == null ? "" : PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "usuaDoc", "4")) + "</usuaDoc>\n"
                        + "         <usuaLogin xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "usuaLogin", "4") == null ? "" : PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "usuaLogin", "4")) + "</usuaLogin>\n"
                        + "      </urn:crearAnexo>\n"
                        + "   </soapenv:Body>\n"
                        + "</soapenv:Envelope>";
                WebServiceDTO objWebServiceDTO = new WebServiceDTO();
                objWebServiceDTO.setParams(params);
                objWebServiceDTO.setUrl(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "urlORFEO") );
                objWebServiceDTO.getHeader().put("Content-Type", "text/xml;charset=UTF-8");
                objWebServiceDTO.setMethod("POST");
                log.info("Se va a consumir el servicio de crear anexo de ORFEO...");
                objWebServiceDTO = ws.SoapWithParam(objWebServiceDTO);
                String responseMensaje = "";
                if (objWebServiceDTO.getXmlFile().contains("codigoError")) {
                	log.info("Orfeo TagError");
                	String responseError = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "codigoError");
                	if(responseError != null && responseError.contains("0")) {
                		responseMensaje = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "mensaje");
                		objWebServiceDTO.setRespuesta(false);
                        this.logAuditoriaDTO.setAuMensajeError("CreateAppend " + "Se va a consumir el servicio de crear anexo de ORFEO.... " + responseMensaje);
                        this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
                        this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
                        this.logAuditoriaDTO.setAuLogUsuario(radNumber);
                        this.logAuditoriaDTO.setAuAplicacion("ORFEO");
                        this.logAuditoriaDTO.setAuUserArchivo(objUploadedFile.getFileName());
                        logService.AddLogAuditoria(logAuditoriaDTO);
                		return response;
                	}
                }
                this.logAuditoriaDTO.setAuMensajeError("CreateAppend " + "Se va a consumir el servicio de crear anexo de ORFEO.... " + responseMensaje);
                this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
                this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
                this.logAuditoriaDTO.setAuLogUsuario(radNumber);
                this.logAuditoriaDTO.setAuAplicacion("ORFEO");
                this.logAuditoriaDTO.setAuUserArchivo(objUploadedFile.getFileName());
                logService.AddLogAuditoria(logAuditoriaDTO);
                if (objWebServiceDTO.isRespuesta())
                {
                    if (objWebServiceDTO.getXmlFile() != null && !objWebServiceDTO.getXmlFile().equals(""))
                    {
                        response = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "return");
                        if (response != null && !response.equals(""))
                        {
                            log.info("Se consumio correctamente este servicio web.");
                            return response;
                        }
                        else
                        {
                            log.error("El retorno es nulo o esta vacio.");
                        }
                    }
                    else
                    {
                        log.error("El xml que retorno el servicio web esta vacio o es nulo.");
                    }
                }
                else
                {
                    log.error("Error al consumir el servicio web de crear anexo de ORFEO.");
                    return null;
                }
            }
            else
            {
                log.error("El archivo cargado es nulo.");
                return null;
            }
        }
        catch (IOException e)
        {
            log.error("Error en PHRegisterServiceImpl en el metodo de crear anexo: " + e.toString());
        }
        return null;
    }
}
