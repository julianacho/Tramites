/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.ServiceImpl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.primefaces.model.UploadedFile;
import org.slf4j.LoggerFactory;

import co.gov.gobierno.DTO.LogAuditoriaDTO;
import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.DTO.ThirdDTO;
import co.gov.gobierno.DTO.WebServiceDTO;
import co.gov.gobierno.Service.LogAuditoriaService;
import co.gov.gobierno.Service.UpdateLegalAgentService;
import co.gov.gobierno.Service.WebService;
import co.gov.gobierno.Util.Base64Handler;
import co.gov.gobierno.Util.PropertiesHandler;
import co.gov.gobierno.Util.XmlHandler;

/**
 *
 * @author DELL
 */
@Stateless
public class UpdateLegalAgentServiceImpl implements UpdateLegalAgentService
{
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(UpdateLegalAgentServiceImpl.class);
    @EJB
    WebService ws;
    
    @EJB
    LogAuditoriaService logService;
    
    private LogAuditoriaDTO logAuditoriaDTO = new LogAuditoriaDTO(0, "NA", "IP", "SO", "NA", "UpdateLegalAgentServiceImpl", "NA", "NA", "NA", null);
   
    
    @Override
    public String GetRadicadoNumber(ThirdDTO objThirdDTO, List<PropertieDTO> listPropertieDTOs, String location, String neightborhood)
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
                            "         <destinatarioOrg xsi:type=\"xsd:string\">" + objThirdDTO.getIdNumber() + "||" + objThirdDTO.getFirstName()+ "||" + objThirdDTO.getSecondName()+ "||" + objThirdDTO.getLastName()+ "||" + objThirdDTO.getSecondLastName()+ "||" + objThirdDTO.getCellphone()+ "||" + objThirdDTO.getIdType()+ "||" + objThirdDTO.getAddress().replace(" ","") + " " + objThirdDTO.getRuralAddress() + "||" + objThirdDTO.getEmail() + "||" + location + "||" + neightborhood + "</destinatarioOrg>\n" +
                            "         <predioOrg xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "predioOrg", "1")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "predioOrg", "1")) + "</predioOrg>\n" +
                            "         <espOrg xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "espOrg", "1")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "espOrg", "1")) + "</espOrg>\n" +
                            "         <asu xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "asuRl", "1")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "asuRl", "1")) + "</asu>\n" +
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
                    this.logAuditoriaDTO.setAuMensajeError("GetRadicadoNumber " + "Se va a consumir el servicio de radicarDocumentoP de ORFEO... " + responseMensaje);
                    this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
                    this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
                    this.logAuditoriaDTO.setAuLogUsuario(objThirdDTO.getIdNumber());
                    this.logAuditoriaDTO.setAuAplicacion("ORFEO");
                    logService.AddLogAuditoria(logAuditoriaDTO);
            		return radNumber;
            	}
            }
            this.logAuditoriaDTO.setAuMensajeError("GetRadicadoNumber " + "Se va a consumir el servicio de radicarDocumentoP de ORFEO... " + responseMensaje);
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
            	String response = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "codigoError");
            	if(response != null && response.contains("0")) {
            		responseMensaje = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "mensaje");
            		objWebServiceDTO.setRespuesta(false);
                    this.logAuditoriaDTO.setAuMensajeError("GetExpedientNumber " + "Se va a consumir el servicio de crear expediente de ORFEO... " + expNumber);
                    this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
                    this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
                    this.logAuditoriaDTO.setAuLogUsuario(radNumber);
                    this.logAuditoriaDTO.setAuAplicacion("ORFEO");
                    logService.AddLogAuditoria(logAuditoriaDTO);
            		return expNumber;
            	}
            }
            this.logAuditoriaDTO.setAuMensajeError("GetExpedientNumber " + "Se va a consumir el servicio de crear expediente de ORFEO... " + expNumber);
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
            objWebServiceDTO.setUrl(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "urlORFEO") );
            objWebServiceDTO.getHeader().put("Content-Type", "text/xml;charset=UTF-8");
            objWebServiceDTO.setMethod("POST");
            log.info("Se va a consumir el servicio de anexar expediente de ORFEO...");
            objWebServiceDTO = ws.SoapWithParam(objWebServiceDTO);
            this.logAuditoriaDTO.setAuMensajeError("AppendExpedient " + "Se va a consumir el servicio de anexar expediente de ORFEO... ");
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
    public String CreateAppend(String radNumber, UploadedFile objUploadedFile, List<PropertieDTO> listPropertieDTOs)
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
                String tipoEndpointOrfeo = (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "TipoEndpointOrfeo", "5") == null ? "2" : PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "TipoEndpointOrfeo", "5"));  	
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
                                "         <descripcion xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "descripcion", "4")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "descripcion", "4")) + "</descripcion>\n" +
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
                		return response;
                	}
                }
                this.logAuditoriaDTO.setAuMensajeError("CreateAppend " + "Se va a consumir el servicio de crear anexo de ORFEO... " + responseMensaje); 
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
                        	if (response.charAt(response.length() - 1) == '1') {
                        		response = response.substring(0, response.length() - 1);
                        	}
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
}
