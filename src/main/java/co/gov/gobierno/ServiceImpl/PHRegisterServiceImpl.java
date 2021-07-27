/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.ServiceImpl;

import co.gov.gobierno.DTO.LegalAgentDTO;
import co.gov.gobierno.DTO.LogAuditoriaDTO;
import co.gov.gobierno.DTO.PHRequestDTO;
import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.DTO.ThirdDTO;
import co.gov.gobierno.DTO.WebServiceDTO;
import co.gov.gobierno.Service.LogAuditoriaService;
import co.gov.gobierno.Service.PHRegisterService;
import co.gov.gobierno.Service.WebService;
import co.gov.gobierno.Util.Base64Handler;
import co.gov.gobierno.Util.JsonHandler;
import co.gov.gobierno.Util.PropertiesHandler;
import co.gov.gobierno.Util.XmlHandler;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.primefaces.model.UploadedFile;
import org.slf4j.LoggerFactory;

/**
 *
 * @author DELL
 */
@Stateless
public class PHRegisterServiceImpl implements PHRegisterService
{
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(PHRegisterServiceImpl.class);
    @EJB
    WebService ws;
    
    @EJB
    LogAuditoriaService logService;
    
    private LogAuditoriaDTO logAuditoriaDTO = new LogAuditoriaDTO(0, "NA", "IP", "SO", "NA", "PHRegisterServiceImpl", "NA", "NA", "NA", null);

    
    @Override
    public String GetRadicadoNumber(ThirdDTO objThirdDTO, List<PropertieDTO> listPropertieDTOs)
    {
        String radNumber = null;
        try
        {
            String tipoEndpointOrfeo = (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "TipoEndpointOrfeo", "5") == null ? "2" : PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "TipoEndpointOrfeo", "5"));  	
            if (tipoEndpointOrfeo.equals("2")) {
            	tipoEndpointOrfeo = "         <file xsi:type=\"xsd:base64Binary\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "file", "1")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "file", "1")) + "</file>\n";
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
                            "         <destinatarioOrg xsi:type=\"xsd:string\">" + "0||" + objThirdDTO.getIdNumber() + "||0||" + objThirdDTO.getFirstName()+ "||" + objThirdDTO.getSecondName()+ "||" + objThirdDTO.getLastName()+ "||" + objThirdDTO.getSecondLastName()+ "||" + objThirdDTO.getCellphone()+ "||" + objThirdDTO.getAddress().replace(" ","") + " " + objThirdDTO.getRuralAddress() + "||" + objThirdDTO.getEmail() + "||1||170||11||1" + "</destinatarioOrg>\n" +
                            "         <predioOrg xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "predioOrg", "1")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "predioOrg", "1")) + "</predioOrg>\n" +
                            "         <espOrg xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "espOrg", "1")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "espOrg", "1")) + "</espOrg>\n" +
                            "         <asu xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "asu", "1")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "asu", "1")) + "</asu>\n" +
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
            String responseMensaje = "";
            if (objWebServiceDTO.getXmlFile().contains("codigoError")) {
            	log.info("Orfeo TagError");
            	String response = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "codigoError");
            	if(response != null && response.contains("0")) {
            		responseMensaje = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "mensaje");
            		objWebServiceDTO.setRespuesta(false);
                    this.logAuditoriaDTO.setAuMensajeError("getRadicadoNumber " + "Se va a consumir el servicio de radicarDocumentoP de ORFEO... " + responseMensaje);
                    this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
                    this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
                    this.logAuditoriaDTO.setAuLogUsuario(objThirdDTO.getIdNumber());
                    this.logAuditoriaDTO.setAuAplicacion("ORFEO");
                    logService.AddLogAuditoria(logAuditoriaDTO);
            		return radNumber;
            	}
            }
            this.logAuditoriaDTO.setAuMensajeError("getRadicadoNumber " + "Se va a consumir el servicio de radicarDocumentoP de ORFEO... " + responseMensaje);
            this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
            this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
            this.logAuditoriaDTO.setAuLogUsuario(objThirdDTO.getIdNumber());
            this.logAuditoriaDTO.setAuAplicacion("ORFEO");
            logService.AddLogAuditoria(logAuditoriaDTO);
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
            log.error("Error en PHRegisterServiceImpl en el metodo de obtener el numero de radicado: " + e.toString());
        }
        return null;
    }

    @Override
    public String GetExpedientNumber(String radNumber, List<PropertieDTO> listPropertieDTOs)
    {
        String expNumber = null;
        try
        {
            Date now = new Date();
            SimpleDateFormat formatDay = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
            String day = formatDay.format(now);
            String year = formatYear.format(now);
            String params = "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:org.gobierno.webserviceorfeo\">\n" +
                            "   <soapenv:Header/>\n" +
                            "   <soapenv:Body>\n" +
                            "      <urn:crearExpediente soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                            "         <nurad xsi:type=\"xsd:string\">" + radNumber + "</nurad>\n" +
                            "         <usuario xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "usuario", "2")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "usuario", "2")) + "</usuario>\n" +
                            "         <anoExp xsi:type=\"xsd:string\">" + year + "</anoExp>\n" +
                            "         <fechaExp xsi:type=\"xsd:string\">" + day + "</fechaExp>\n" +
                            "         <codiSRD xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "codiSRD", "2")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "codiSRD", "2")) + "</codiSRD>\n" +
                            "         <codiSBRD xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "codiSBRD", "2")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "codiSBRD", "2")) + "</codiSBRD>\n" +
                            "         <codiProc xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "codiProc", "2")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "codiProc", "2")) + "</codiProc>\n" +
                            "         <descripcion xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "descripcion", "2")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "descripcion", "2")) + "</descripcion>\n" +
                            "      </urn:crearExpediente>\n" +
                            "   </soapenv:Body>\n" +
                            "</soapenv:Envelope>";
            WebServiceDTO objWebServiceDTO = new WebServiceDTO();
            objWebServiceDTO.setParams(params);
            objWebServiceDTO.setUrl(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "urlORFEO") );
            objWebServiceDTO.getHeader().put("Content-Type", "text/xml;charset=UTF-8");
            objWebServiceDTO.setMethod("POST");
            log.info("Se va a consumir el servicio de crear expediente de ORFEO...");
            objWebServiceDTO = ws.SoapWithParam(objWebServiceDTO);
            String responseMensaje = "";
            if (objWebServiceDTO.getXmlFile().contains("codigoError")) {
                log.info("Orfeo TagError");
                String responseError = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "codigoError");
                if(responseError != null && responseError.contains("0")) {
                	responseMensaje = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "mensaje");
                	objWebServiceDTO.setRespuesta(false);
                    this.logAuditoriaDTO.setAuMensajeError("GetExpedientNumber " + "Se va a consumir el servicio de crear expediente de ORFEO... " + responseMensaje);
                    this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
                    this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
                    this.logAuditoriaDTO.setAuLogUsuario(radNumber);
                    this.logAuditoriaDTO.setAuAplicacion("ORFEO");
                    logService.AddLogAuditoria(logAuditoriaDTO);
                	return radNumber;
                }
            }
            this.logAuditoriaDTO.setAuMensajeError("GetExpedientNumber " + "Se va a consumir el servicio de crear expediente de ORFEO... " + responseMensaje);
            this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
            this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
            this.logAuditoriaDTO.setAuLogUsuario(radNumber);
            this.logAuditoriaDTO.setAuAplicacion("ORFEO");
            logService.AddLogAuditoria(logAuditoriaDTO);
            if(objWebServiceDTO.isRespuesta())
            {
                if(objWebServiceDTO.getXmlFile()!=null && !objWebServiceDTO.getXmlFile().equals(""))
                {
                    expNumber = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "return");
                    if(expNumber!=null && !expNumber.equals(""))
                    {
                    	if (expNumber.charAt(expNumber.length() - 1) == '1') {
                    		expNumber = expNumber.substring(0, expNumber.length() - 1);
                    	}
                        log.info("Se consumio correctamente este servicio web.");
                        return expNumber;
                    }
                    else
                    {
                        log.error("El numero de expediente es nulo o esta vacio.");
                        return null;
                    }
                }
                else
                {
                    log.error("El xml que retorno el servicio web esta vacio o es nulo.");
                    return null;
                }
            }
            else
            {
                log.error("Error al consumir el servicio web de crearExpediente de ORFEO.");
                return null;
            }
        }
        catch(Exception e)
        {
            log.error("Error en PHRegisterServiceImpl en el metodo de obtener el numero de expediente: " + e.toString());
            return null;
        }
    }

    @Override
    public String AppendExpedient(String radNumber, String expNumber, List<PropertieDTO> listPropertieDTOs)
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
            String urlA = PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "urlORFEO");
            objWebServiceDTO.setUrl(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "urlORFEO") );
            objWebServiceDTO.getHeader().put("Content-Type", "text/xml;charset=UTF-8");
            objWebServiceDTO.setMethod("POST");
            log.info("Se va a consumir el servicio de anexar expediente de ORFEO...");
            objWebServiceDTO = ws.SoapWithParam(objWebServiceDTO);
            String responseMensaje = "";
            if (objWebServiceDTO.getXmlFile().contains("codigoError")) {
                log.info("Orfeo TagError");
                String responseError = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "codigoError");
                if(responseError != null && responseError.contains("0")) {
                	responseMensaje = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "mensaje");
                	objWebServiceDTO.setRespuesta(false);
                    this.logAuditoriaDTO.setAuMensajeError("AppendExpedient " + "Se va a consumir el servicio de anexar expediente de ORFEO... " + responseMensaje);
                    this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
                    this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
                    this.logAuditoriaDTO.setAuLogUsuario(radNumber);
                    this.logAuditoriaDTO.setAuAplicacion("ORFEO");
                    logService.AddLogAuditoria(logAuditoriaDTO);
                	return radNumber;
                }
            }
            this.logAuditoriaDTO.setAuMensajeError("AppendExpedient " + "Se va a consumir el servicio de anexar expediente de ORFEO... " + responseMensaje);
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
                        return appendNumber;
                    }
                    else
                    {
                        log.error("El numero del anexo es nulo o esta vacio.");
                    }
                }
                else
                {
                    log.error("El xml que retorno el servicio web esta vacio o es nulo.");
                }
            }
            else
            {
                log.error("Error al consumir el servicio web de Anexar Expediente de ORFEO.");
                return null;
            }
        }
        catch(Exception e)
        {
            log.error("Error en PHRegisterServiceImpl en el metodo de obtener el numero del anexo: " + e.toString());
        }
        return null;
    }

    @Override
    public String CreateAppend(String radNumber, UploadedFile objUploadedFile, List<PropertieDTO> listPropertieDTOs, String numberDocument)
    {
        String response = null;
        try
        {
            String base64 = null;
            if(objUploadedFile!=null)
            {
                log.info("Se va a convertir el archivo a base64...");
                base64 = (Base64Handler.convertFileToBase64Binary(objUploadedFile.getInputstream()));
                log.info("Archivo convertido correctamente.");
                
                String tipoEndpointOrfeo = (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "TipoEndpointOrfeo", "1") == null ? "2" : PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "TipoEndpointOrfeo", "1"));  	
                if (tipoEndpointOrfeo.equals("2")) {
                	tipoEndpointOrfeo = "         <file xsi:type=\"xsd:base64Binary\">" + base64 + "</file>\n";
                }else {
                	tipoEndpointOrfeo = "         <file xsi:type=\"xsd:string\">" + base64 + "</file>\n";
                }
            
                String params = "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:org.gobierno.webserviceorfeo\">\n" +
                                "   <soapenv:Header/>\n" +
                                "   <soapenv:Body>\n" +
                                "      <urn:crearAnexo soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                                "         <radiNume xsi:type=\"xsd:string\">" + radNumber + "</radiNume>\n" +
                                tipoEndpointOrfeo + 
                                //"         <file xsi:type=\"xsd:string\">" + base64 + "</file>\n" +
                                "         <filename xsi:type=\"xsd:string\">" + objUploadedFile.getFileName() + "</filename>\n" +
                                "         <descripcion xsi:type=\"xsd:string\">" + numberDocument + objUploadedFile.getFileName() + "</descripcion>\n" +
                                "         <usuaDoc xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "usuaDoc", "4")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "usuaDoc", "4")) + "</usuaDoc>\n" +
                                "         <usuaLogin xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "usuaLogin", "4")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "usuaLogin", "4")) + "</usuaLogin>\n" +
                                "      </urn:crearAnexo>\n" +
                                "   </soapenv:Body>\n" +
                                "</soapenv:Envelope>";
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
                	}
                }
                this.logAuditoriaDTO.setAuMensajeError("AppendExpedient " + "Se va a consumir el servicio de crear anexo de ORFEO... " + responseMensaje);
                this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
                this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
                this.logAuditoriaDTO.setAuLogUsuario(radNumber);
                this.logAuditoriaDTO.setAuAplicacion("ORFEO");
                this.logAuditoriaDTO.setAuUserArchivo(objUploadedFile.getFileName());
                logService.AddLogAuditoria(logAuditoriaDTO);
                if(objWebServiceDTO.isRespuesta())
                {
                    if(objWebServiceDTO.getXmlFile()!=null && !objWebServiceDTO.getXmlFile().equals(""))
                    {
                        response = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "return");
                        if(response!=null && !response.equals(""))
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
        catch(IOException e)
        {
            log.error("Error en PHRegisterServiceImpl en el metodo de crear anexo: " + e.toString());
        }
        return null;
    }

    @Override
    public WebServiceDTO RegisterPH(ThirdDTO objThirdDTO, List<PropertieDTO> listPropertieDTOs, List<PropertieDTO> listPHPropertieDTOs, String expNumber,
            String radNumber, String name, String state, String nit, String registerDate, String publicScriptNumber,
            String publicScriptdate, String type1, String type2, String type3, String locate, String neighborhood, String neighborhoodName,
            String realStateRegistration, String folio, String address, String typeDireccionPH, String email, String stratum, String regimen,
            String typeNroPrivateUnits, String nroPrivateUnits, String commonArea, String nroCommonArea,
            String presentationType, String academicFormation, String nroResidents, String nroBloques,
            String nroEtapas, String nroLocales, String contactPerson, String contactPhone, String contactEmail,
            String notaria, String emergencyPlan, String personType, String idTypeLegalAgent, String idNumberLegalAgent,
            String firstNameLegalAgent, String lastNameLegalAgent, String secondNameLegalAgent,
            String secondLastNameLegalAgent, String birthDateLegalAgent, String nationalityLegalAgent,
            String cellphoneLegalAgent, String phoneLegalAgent, String addressLegalAgent, String typeDireccionLegalAgent, String emailLegalAgent,
            String locationLegalAgent, String stratumLegalAgent, String specialConditionLegalAgent,
            String occupationLegalAgent, String idGenreLegalAgent, String idStatusLegalAgent, String dateAgent,
            String startAgent, String endAgent, String numberActAgent, String dateActAgent, String nameAgentJuridico,
            String idNumberAgentJuridico, String idTypeAgentJuridico, String idTypeRepLegalAgent, String razonSocialLegalAgent, String rut, List<PHRequestDTO> listUnidadesPHRequestDTO)
    {
        String representante = "";
        String representanteTemp = "";
        String tagTypeNroPrivateUnits = "";
        String tagCommonArea = "";
        String strInfoComplementaria ="";
        String strArea ="";
   
        //Captura de info complemetaria
        for (PHRequestDTO objPHRequestDTO : listUnidadesPHRequestDTO) 
        {
           if (objPHRequestDTO.getStrCodeTipoArea().equals("C"))
            {
                strArea = "<TipodeAreaComun businessKey=\"Codigo='"+objPHRequestDTO.getStrArea()+"'\"/>\n"; 
            }
           if (objPHRequestDTO.getStrCodeTipoArea().equals("P"))
            {
                strArea = "<TipodeUnidadPrivada businessKey=\"Codigo='"+objPHRequestDTO.getStrPrivada()+"'\"/>\n";
            }
            
        strInfoComplementaria= strInfoComplementaria+"<AreasdelaPropiedadHorizo>\n" +
                               "<Area businessKey=\"Codigo='"+objPHRequestDTO.getStrCodeTipoArea()+"'\"/>\n" +
                               "<Cantidad>"+objPHRequestDTO.getStrCantidad()+"</Cantidad>\n" +
                               strArea +
                               "</AreasdelaPropiedadHorizo>\n";
        }
        
        String header = "<XRepresentantesLegales>";
        String footer = "</XRepresentantesLegales>";
        for(LegalAgentDTO item  : objThirdDTO.getListLegalAgentDTOs())
        {
        	idNumberLegalAgent = item.getIdNumber();
        	firstNameLegalAgent = item.getFirstName().toUpperCase();
        	secondNameLegalAgent = item.getSecondName().toUpperCase();
        	lastNameLegalAgent = item.getLastName().toUpperCase();
        	secondLastNameLegalAgent = item.getSecondLastName().toUpperCase();
        	idGenreLegalAgent = item.getGender();
        	startAgent = item.getBeginDate();
        	endAgent = item.getFinishDate();
        	phoneLegalAgent = item.getPhone();
        	addressLegalAgent = item.getAddress().toUpperCase();
        	emailLegalAgent = item.getEmail();
        	idTypeRepLegalAgent = item.getIdTypeRepLegalAgent();
        	dateActAgent = item.getDateActAgent();
        	razonSocialLegalAgent = item.getName().toUpperCase();
        	idTypeAgentJuridico = item.getIdTypeAgentJuridico();
        	idNumberAgentJuridico = item.getIdNumberAgentJuridico();
        	nameAgentJuridico = item.getNameAgentJuridico().toUpperCase();
        	idStatusLegalAgent = item.getIdStatusLegalAgent();
        	if(item.getIdTypeAgentJuridico() == null) //Natural 
        	{
        		idTypeLegalAgent = item.getIdType();
        		personType = "PN";

        		representanteTemp += "<M_PH_RepresentanteLegal> \n" +
                        "<IdTipoPersona>\n" +
                        "<SCodigo>"+personType+"</SCodigo>\n" +
                        "</IdTipoPersona>\n" +
                        "<IdTipoIdentificacion>\n" +
                        "<Codigo>"+idTypeLegalAgent+"</Codigo>\n" +
                        "</IdTipoIdentificacion>\n" +
                        "<NumerodeIdentificacion>"+idNumberLegalAgent+"</NumerodeIdentificacion>\n" +
                        "<SNombreCompleto>"+firstNameLegalAgent + " " + secondNameLegalAgent + " " + lastNameLegalAgent + " " + secondLastNameLegalAgent+"</SNombreCompleto>\n" +
                        "<SPrimerApellido>" + lastNameLegalAgent + "</SPrimerApellido>\n" +
                        "<SSegundoApellido>" + secondLastNameLegalAgent+"</SSegundoApellido>\n" +
                        "<SPrimerNombre>"+firstNameLegalAgent + "</SPrimerNombre>\n" +
                        "<SSegundoNombre>" + secondNameLegalAgent + "</SSegundoNombre>\n" +
                        "<EstadoCivil businessKey=\"SCodigo='"+idStatusLegalAgent+"'\"/>\n" +
                        "<idGenero businessKey=\"SCodigo='"+ idGenreLegalAgent +"'\"/>\n" +
                        "<DFechaInicio>"+startAgent+"</DFechaInicio>\n" +
                        "<DFechaFin>"+endAgent+"</DFechaFin>\n" +
                        "<STelefonoRepLegal>"+phoneLegalAgent+"</STelefonoRepLegal>\n" +
                        "<IdTipodedirecionRL businessKey=\"SCodigo='"+typeDireccionLegalAgent+"'\"/>\n"+
                        "<SDireccion>"+addressLegalAgent.replace(" ","")+"</SDireccion>\n" +
                        "<SCorreoRepresentanteLegal>"+emailLegalAgent+"</SCorreoRepresentanteLegal>\n" +
                        "<IdTipoRepresentacion businessKey=\"SCodigo='"+idTypeRepLegalAgent+"'\"/>\n" +
                        "<DFechaActaAsamblea>"+dateActAgent+"</DFechaActaAsamblea>\n" +
                        "<SNumActadeAsamblea>"+numberActAgent+"</SNumActadeAsamblea>\n" +
                        "</M_PH_RepresentanteLegal>\n";
        	}
        	else
        	{
        		//Juridico
        		personType = "PJ";
        		idTypeLegalAgent = item.getIdType();
        		representanteTemp += "<M_PH_RepresentanteLegal> \n" +
                        "<IdTipoPersona>\n" +
                        "<SCodigo>"+personType+"</SCodigo>\n" +
                        "</IdTipoPersona>\n" +
                        "<IdTipoIdentificacion>\n" +
                        "<Codigo>"+idTypeLegalAgent+"</Codigo>\n" +
                        "</IdTipoIdentificacion>\n" +
                        "<NumerodeIdentificacion>"+idNumberLegalAgent+"</NumerodeIdentificacion>\n" +
                        "<SNombreCompleto>"+razonSocialLegalAgent+"</SNombreCompleto>\n" +
                        "<DFechaInicio>"+startAgent+"</DFechaInicio>\n" +
                        "<DFechaFin>"+endAgent+"</DFechaFin>\n" +
                        "<STelefonoRepLegal>"+phoneLegalAgent+"</STelefonoRepLegal>\n" +
                        "<IdTipodedirecionRL businessKey=\"SCodigo='"+typeDireccionLegalAgent+"'\"/>\n"+
                        "<SDireccion>"+addressLegalAgent.replace(" ","")+"</SDireccion>\n" +
                        "<SCorreoRepresentanteLegal>"+emailLegalAgent+"</SCorreoRepresentanteLegal>\n" +
                        "<IdTipoRepresentacion businessKey=\"SCodigo='"+idTypeRepLegalAgent+"'\"/>\n" +
                        "<DFechaActaAsamblea>"+dateActAgent+"</DFechaActaAsamblea>\n" +
                        "<SNumActadeAsamblea>"+numberActAgent+"</SNumActadeAsamblea>\n" +
                        "<idTipodeIdentificacionRL>\n" +
                        "<Codigo>"+idTypeAgentJuridico+"</Codigo>\n" +
                        "</idTipodeIdentificacionRL>\n" +
                        "<NumerodeIdentificacionRL>"+idNumberAgentJuridico+"</NumerodeIdentificacionRL>\n" +
                        "<SNombreRepresentante>"+nameAgentJuridico+"</SNombreRepresentante>\n" +
                        "</M_PH_RepresentanteLegal>\n";
        	}
        }
        
        representante += header + representanteTemp + footer;
        
        /*if (personType.equals("PN"))
        {
            representante = "<XRepresentantesLegales>\n" +
                            "<M_PH_RepresentanteLegal> \n" +
                            "<IdTipoPersona>\n" +
                            "<SCodigo>"+personType+"</SCodigo>\n" +
                            "</IdTipoPersona>\n" +
                            "<IdTipoIdentificacion>\n" +
                            "<Codigo>"+idTypeLegalAgent+"</Codigo>\n" +
                            "</IdTipoIdentificacion>\n" +
                            "<NumerodeIdentificacion>"+idNumberLegalAgent+"</NumerodeIdentificacion>\n" +
                            "<SNombreCompleto>"+firstNameLegalAgent + " " + secondNameLegalAgent + " " + lastNameLegalAgent + " " + secondLastNameLegalAgent+"</SNombreCompleto>\n" +
                            "<SPrimerApellido>" + lastNameLegalAgent + "</SPrimerApellido>\n" +
                            "<SSegundoApellido>" + secondLastNameLegalAgent+"</SSegundoApellido>\n" +
                            "<SPrimerNombre>"+firstNameLegalAgent + "</SPrimerNombre>\n" +
                            "<SSegundoNombre>" + secondNameLegalAgent + "</SSegundoNombre>\n" +
                            "<EstadoCivil businessKey=\"SCodigo='"+idStatusLegalAgent+"'\"/>\n" +
                            "<idGenero businessKey=\"SCodigo='"+ idGenreLegalAgent +"'\"/>\n" +
                            "<DFechaInicio>"+startAgent+"</DFechaInicio>\n" +
                            "<DFechaFin>"+endAgent+"</DFechaFin>\n" +
                            "<STelefonoRepLegal>"+phoneLegalAgent+"</STelefonoRepLegal>\n" +
                            "<IdTipodedirecionRL businessKey=\"SCodigo='"+typeDireccionLegalAgent+"'\"/>\n"+
                            "<SDireccion>"+addressLegalAgent.replace(" ","")+"</SDireccion>\n" +
                            "<SCorreoRepresentanteLegal>"+emailLegalAgent+"</SCorreoRepresentanteLegal>\n" +
                            "<IdTipoRepresentacion businessKey=\"SCodigo='"+idTypeRepLegalAgent+"'\"/>\n" +
                            "<DFechaActaAsamblea>"+dateActAgent+"</DFechaActaAsamblea>\n" +
                            "<SNumActadeAsamblea>"+numberActAgent+"</SNumActadeAsamblea>\n" +
                            "</M_PH_RepresentanteLegal>\n" +
                            "</XRepresentantesLegales>" ;
        }
        if (personType.equals("PJ"))
        {
            representante = "<XRepresentantesLegales>\n" +
                            "<M_PH_RepresentanteLegal> \n" +
                            "<IdTipoPersona>\n" +
                            "<SCodigo>"+personType+"</SCodigo>\n" +
                            "</IdTipoPersona>\n" +
                            "<IdTipoIdentificacion>\n" +
                            "<Codigo>"+idTypeLegalAgent+"</Codigo>\n" +
                            "</IdTipoIdentificacion>\n" +
                            "<NumerodeIdentificacion>"+idNumberLegalAgent+"</NumerodeIdentificacion>\n" +
                            "<SNombreCompleto>"+razonSocialLegalAgent+"</SNombreCompleto>\n" +
                            "<DFechaInicio>"+startAgent+"</DFechaInicio>\n" +
                            "<DFechaFin>"+endAgent+"</DFechaFin>\n" +
                            "<STelefonoRepLegal>"+phoneLegalAgent+"</STelefonoRepLegal>\n" +
                            "<IdTipodedirecionRL businessKey=\"SCodigo='"+typeDireccionLegalAgent+"'\"/>\n"+
                            "<SDireccion>"+addressLegalAgent.replace(" ","")+"</SDireccion>\n" +
                            "<SCorreoRepresentanteLegal>"+emailLegalAgent+"</SCorreoRepresentanteLegal>\n" +
                            "<IdTipoRepresentacion businessKey=\"SCodigo='"+idTypeRepLegalAgent+"'\"/>\n" +
                            "<DFechaActaAsamblea>"+dateActAgent+"</DFechaActaAsamblea>\n" +
                            "<SNumActadeAsamblea>"+numberActAgent+"</SNumActadeAsamblea>\n" +
                            "<idTipodeIdentificacionRL>\n" +
                            "<Codigo>"+idTypeAgentJuridico+"</Codigo>\n" +
                            "</idTipodeIdentificacionRL>\n" +
                            "<NumerodeIdentificacionRL>"+idNumberAgentJuridico+"</NumerodeIdentificacionRL>\n" +
                            "<SNombreRepresentante>"+nameAgentJuridico+"</SNombreRepresentante>\n" +
                            "</M_PH_RepresentanteLegal>\n" +
                            "</XRepresentantesLegales>" ;
        }*/
        if (!typeNroPrivateUnits.equals("0"))
        {
            tagTypeNroPrivateUnits = "<TipodeUnidadesPrivadas businessKey=\"Codigo='"+typeNroPrivateUnits+"'\"/>\n";
        }
        if (!commonArea.equals("0"))
        {
            tagCommonArea = "<TipodeAreasComunes businessKey=\"Codigo='"+commonArea+"'\"/>\n";
        }
        Date now = new Date();
        SimpleDateFormat formatDay = new SimpleDateFormat("yyyy-MM-dd");
        String day = formatDay.format(now);
        String params = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n" +
                        "	<soapenv:Header/>\n" +
                        "	<soapenv:Body>\n" +
                        "		<tem:createCases>\n" +
                        "			<tem:casesInfo>\n" +
                        "				<BizAgiWSParam>\n" +
                        "					<domain>domain</domain>\n" +
                        "					<userName>admon</userName>\n" +
                        "					<Cases>\n" +
                        "						<Case>\n" +
                        "							<Process>InscripcionPropHoriz</Process>\n" +
                        "							<Entities>\n" +
                        "								<M_IPH_Solicitud>\n" +
                        "									<SNumeroRadicado>" + radNumber + "</SNumeroRadicado >\n" +
                        "									<IdPropiedadHorizontal>\n" +
                        "										<SNombrePropiedad>" + name + "</SNombrePropiedad>\n" +
                        "										<SNIT>" + nit + "</SNIT>\n" +
                        "										<RUT>" + rut + "</RUT>\n" +
                        "										<SNoEscrituraPublica>" + publicScriptNumber + "</SNoEscrituraPublica>\n" +
                        "										<SMatriculainmobiliaria>" + realStateRegistration + "</SMatriculainmobiliaria>\n" +
                        "										<DFechaMatricula>" + registerDate + "T12:12:12</DFechaMatricula>\n" +
                        "										<DFechaEscrituraPublica>" + publicScriptdate + "T12:12:12</DFechaEscrituraPublica>\n" +
                        "										<INoFolios>" + folio + "</INoFolios>\n" +
                        "										<IdTipoPropiedad businessKey=\"SCodigo='" + type3 + "'\"/>\n" +
                        "										<NumeroNotaria>"+notaria+"</NumeroNotaria>\n" +
                        "										<SBarrio>"+neighborhood+"</SBarrio>\n" +
                        "                                                                               <SNombredelBarrio>"+neighborhoodName+"</SNombredelBarrio>\n"+
                        "                                                                               <IdTipodedirecion businessKey=\"SCodigo='"+typeDireccionPH+"'\"/>\n"+
                        "										<SDireccion>" + address.replace(" ","") + "</SDireccion>\n" +
                        "										<SCorreoElectronico>" + email + "</SCorreoElectronico>\n" +
                        "										<IEstrato>" + stratum + "</IEstrato>\n" +
                        "										<IdRegimen businessKey=\"SCodigo='"+regimen+"'\"/>\n" +
                        "  										<IdLocalidad businessKey=\"Codigo='"+locate+"'\"/>\n" +
                        "										<NumerodeExpediente>" + expNumber + "</NumerodeExpediente>\n" +
                        "										<SNumerodeBloques>" + nroBloques + "</SNumerodeBloques>\n" +
                        "										<SNumerodeetapas>" + nroEtapas + "</SNumerodeetapas>\n" +
                        "										<SNumerolocalescomerciales>" + nroLocales + "</SNumerolocalescomerciales>\n" +
                        "										<IdInfoComplementaria>\n" +
                        "											<INoResidentes>" + nroResidents + "</INoResidentes>\n" +
                        "											<SNombrePersonaContacto>" + contactPerson + "</SNombrePersonaContacto>\n" +
                        "											<BPHtTenePlanEmergencias>" + emergencyPlan + "</BPHtTenePlanEmergencias>\n" +
                        "											<STelContacto>" + contactPhone + "</STelContacto>																					\n" +
                        "											<SCorreoElectronicoContacto>" + contactEmail + "</SCorreoElectronicoContacto>\n" +
                        "                                                                                       <Areas>\n"+
                                                                                                                 strInfoComplementaria    +
                        "                                                                                       </Areas>\n"+
                        "										</IdInfoComplementaria>\n" +
                                                                                                        representante +
                        "  									</IdPropiedadHorizontal>\n" +
                        "  									<IdDatosGenereales>\n" +
                        "  										<SNumeroRadicado>" + radNumber + "</SNumeroRadicado>\n" +
                        "  										<DFechaRadicado>"+day+"</DFechaRadicado>\n" +
                        "  										<IdLocalidad businessKey=\"Codigo='"+locate+"'\"/>\n" +
                        " 										<IdTipoTramite>\n" +
                        " 											<Codigo>"+(PropertiesHandler.PropertieValueOfKeyFromDB( listPHPropertieDTOs , "IdTipoTramite")) +"</Codigo>\n" +
                        "										</IdTipoTramite>\n" +
                        "									<Solicitante>" + objThirdDTO.getUser() + "</Solicitante>\n" +
                        "  										<SCorreoSolicitante>" + objThirdDTO.getEmail() + "</SCorreoSolicitante>\n" +
                        "									</IdDatosGenereales>\n" +
                        "								</M_IPH_Solicitud>\n" +
                        "							</Entities>\n" +
                        "						</Case>\n" +
                        "					</Cases>\n" +
                        "				</BizAgiWSParam>\n" +
                        "			</tem:casesInfo>\n" +
                        "		</tem:createCases>\n" +
                        "	</soapenv:Body>\n" +
                        "</soapenv:Envelope>";
        WebServiceDTO objWebServiceDTO = new WebServiceDTO();
        objWebServiceDTO.setParams(params);
        
        objWebServiceDTO.setUrl(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "urlBIZAGY") + "workflowenginesoa.asmx");
        objWebServiceDTO.setMethod("POST");
        objWebServiceDTO.setSoapAction("http://tempuri.org/createCases");
        objWebServiceDTO.getHeader().put("Content-Type", "text/xml;charset=utf-8");
        try 
        {
            log.info("Voy a consumir el servicio web de Registrar PH de BIZAGY. Número Radicado -->"+radNumber);
            log.info(params);
            objWebServiceDTO = ws.SoapWithParam(objWebServiceDTO);
	        String responseMensaje = "";
            if (objWebServiceDTO.getXmlFile().contains("errorCode") || objWebServiceDTO.getXmlFile().contains("errorCode")) {
            	log.info("Bizagi TagError");
            	String xmlFileTemp = objWebServiceDTO.getXmlFile().replace("&gt;", ">").replace("&lt;", "<");//.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
            	String response = XmlHandler.ReadXmlFileGetTag(xmlFileTemp, "errorCode");
            	if(response != null && !response.isEmpty()) {
            		responseMensaje = XmlHandler.ReadXmlFileGetTag(xmlFileTemp, "errorMessage");
            		responseMensaje = XmlHandler.ReadXmlFileGetTag(xmlFileTemp, "ErrorMessage");
            		objWebServiceDTO.setRespuesta(false);
            	}
            }
            log.info("Se consumió el servicio web de Registrar PH de BIZAGY correctamente. Obteniendo la siguiente Respuesta");
            this.logAuditoriaDTO.setAuMensajeError("AppendExpedient " + "Se consumió el servicio web de Registrar PH de BIZAGY correctamente. Obteniendo la siguiente Respuesta.. " + responseMensaje);
            this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
            this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
            this.logAuditoriaDTO.setAuLogUsuario(radNumber);
            this.logAuditoriaDTO.setAuAplicacion("BIZAGI");

            logService.AddLogAuditoria(logAuditoriaDTO);
            if (log.isDebugEnabled()) {	
            	log.info(objWebServiceDTO.getXmlFile());
            }
        }
        catch (Exception ex) 
        {
            log.info("Error al Registrar la PH BIZAGY: " + ex.toString());
            return null;
        }
        if(objWebServiceDTO.isRespuesta())
        {
            if(!objWebServiceDTO.getXmlFile().isEmpty())
            {
                return objWebServiceDTO;
            }
            else
            {
                return null;
            }
        }
        else
        {
            return null;
        }
    }
}
