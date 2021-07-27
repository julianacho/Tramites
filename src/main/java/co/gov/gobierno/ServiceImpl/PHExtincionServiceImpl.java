/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.ServiceImpl;

import co.gov.gobierno.DTO.LogAuditoriaDTO;
import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.DTO.ThirdDTO;
import co.gov.gobierno.DTO.WebServiceDTO;
import co.gov.gobierno.Service.LogAuditoriaService;
import co.gov.gobierno.Service.PHExtincionService;
import co.gov.gobierno.Service.WebService;
import co.gov.gobierno.Util.Base64Handler;
import co.gov.gobierno.Util.PropertiesHandler;
import co.gov.gobierno.Util.XmlHandler;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.primefaces.model.UploadedFile;
import org.slf4j.LoggerFactory;

/**
 *
 * @author DELL
 */
@Stateless
public class PHExtincionServiceImpl implements PHExtincionService
{
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(PHExtincionServiceImpl.class);
    
    @EJB
    WebService ws;
    
    @EJB
    LogAuditoriaService logService;
    
    private LogAuditoriaDTO logAuditoriaDTO = new LogAuditoriaDTO(0, "NA", "IP", "SO", "NA", "PHExtincionServiceImpl", "NA", "NA", "NA", null);

    

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
                            "         <destinatarioOrg xsi:type=\"xsd:string\">" + "0||" + objThirdDTO.getIdNumber() + "||0||" + objThirdDTO.getFirstName()+ "||" + objThirdDTO.getSecondName()+ "||" + objThirdDTO.getLastName()+ "||" + objThirdDTO.getSecondLastName()+ "||" + objThirdDTO.getCellphone()+ "||" + objThirdDTO.getAddress().replace(" ","") +" "+ objThirdDTO.getRuralAddress() + "||" + objThirdDTO.getEmail() + "||1||170||11||1" + "</destinatarioOrg>\n" +
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
            String responseMensaje = "";
            if (objWebServiceDTO.getXmlFile().contains("codigoError")) {
            	log.info("Orfeo TagError");
            	String response = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "codigoError");
            	if(response != null && response.contains("0")) {
            		responseMensaje = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "mensaje");
            		objWebServiceDTO.setRespuesta(false);
                    this.logAuditoriaDTO.setAuMensajeError("AppendExpedient " + "Se va a consumir el servicio de anexar expediente de ORFEO... " + responseMensaje);
                    this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
                    this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
                    this.logAuditoriaDTO.setAuLogUsuario(radNumber);
                    this.logAuditoriaDTO.setAuAplicacion("ORFEO");
                    logService.AddLogAuditoria(logAuditoriaDTO);
            		return appendNumber;
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
                        this.logAuditoriaDTO.setAuMensajeError("CreateAppend " + "Se va a consumir el servicio de crear anexo de ORFEO... " + responseMensaje);
                        this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
                        this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
                        this.logAuditoriaDTO.setAuLogUsuario(numberDocument);
                        this.logAuditoriaDTO.setAuAplicacion("ORFEO");
                        this.logAuditoriaDTO.setAuUserArchivo(objUploadedFile.getFileName());
                		return response;
                	}
                }
                this.logAuditoriaDTO.setAuMensajeError("CreateAppend " + "Se va a consumir el servicio de crear anexo de ORFEO... " + responseMensaje);
                this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
                this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
                this.logAuditoriaDTO.setAuLogUsuario(numberDocument);
                this.logAuditoriaDTO.setAuAplicacion("ORFEO");
                this.logAuditoriaDTO.setAuUserArchivo(objUploadedFile.getFileName());
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
    public WebServiceDTO ExtinguirPH(ThirdDTO objThirdDTO, List<PropertieDTO> listPropertieDTOs, List<PropertieDTO> listPHPropertieDTOs, String expNumber,
            String radNumber,String nitPropiedad)
    {
        
        Date now = new Date();
        SimpleDateFormat formatDay = new SimpleDateFormat("yyyy-MM-dd");
        String day = formatDay.format(now);
        String params = " <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\"> \n"+
                        " 	<soapenv:Header/> \n"+
                        " 	<soapenv:Body> \n"+
                        " 		<tem:createCases> \n"+
                        " 			<tem:casesInfo> \n"+
                        " 				<BizAgiWSParam> \n"+
                        " 					<domain>domain</domain> \n"+
                        " 					<userName>admon</userName> \n"+
                        " 					<Cases> \n"+
                        " 						<Case> \n"+
                        " 							<Process>ExtincionDeLaPropiedadHori</Process> \n"+
                        " 							<Entities> \n"+
                        " 								<ExtincionPropiedadHorizo> \n"+
                        " 									<MatriculaPropiedad>"+nitPropiedad+"</MatriculaPropiedad> \n"+
                        " 									<DatosGenerales> \n"+
                        " 										<SNumeroRadicado>"+radNumber+"</SNumeroRadicado> \n"+
                        " 										<DFechaRadicado>"+day+"</DFechaRadicado> \n"+
                        " 										<IdLocalidad businessKey=\"Codigo='"+objThirdDTO.getLocation()+"'\"/> \n"+
                        " 										<IdTipoTramite> \n"+
                        " 											<Codigo>"+(PropertiesHandler.PropertieValueOfKeyFromDB( listPHPropertieDTOs , "IdTipoTramiteExt")) +"</Codigo> \n"+
                        " 										</IdTipoTramite> \n"+
                        " 										<SCorreoSolicitante>"+objThirdDTO.getEmail()+"</SCorreoSolicitante> \n"+
                        "                                                                               <Solicitante>"+ objThirdDTO.getUser() +"</Solicitante>\n"+
                        " 									</DatosGenerales> \n"+
                        " 								</ExtincionPropiedadHorizo> \n"+
                        " 							</Entities> \n"+
                        " 						</Case> \n"+
                        " 					</Cases> \n"+
                        " 				</BizAgiWSParam> \n"+
                        " 			</tem:casesInfo> \n"+
                        " 		</tem:createCases> \n"+
                        " 	</soapenv:Body> \n"+
                        " </soapenv:Envelope>";
        
        WebServiceDTO objWebServiceDTO = new WebServiceDTO();
        objWebServiceDTO.setParams(params);
        objWebServiceDTO.setUrl(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "urlBIZAGY") + "workflowenginesoa.asmx");
        objWebServiceDTO.setMethod("POST");
        objWebServiceDTO.setSoapAction("http://tempuri.org/createCases");
        objWebServiceDTO.getHeader().put("Content-Type", "text/xml;charset=utf-8");
        try 
        {
            log.info("Voy a consumir el servicio web de Registrar Extincion PH de BIZAGY.");
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
            log.info("Se consumió el servicio web de Registrar Extincion PH de BIZAGY correctamente.");
            this.logAuditoriaDTO.setAuMensajeError("ExtinguirPH " + "Se consumió el servicio web de Registrar Extincion PH de BIZAGY correctamente. " + responseMensaje);
            this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
            this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
            this.logAuditoriaDTO.setAuLogUsuario(nitPropiedad);
            this.logAuditoriaDTO.setAuAplicacion("BIZAGI");
        }
        catch (Exception ex) 
        {
            log.info("Error al Registrar la Extencion PH BIZAGY: " + ex.toString());
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
    
    @Override
    public WebServiceDTO SubsanarExtincionPH(ThirdDTO objThirdDTO, List<PropertieDTO> listPropertieDTOs, List<PropertieDTO> listPHPropertieDTOs, String expNumber,
            String radNumber,String nitPropiedad, String casoPropiedad)
    {
        
        Date now = new Date();
        SimpleDateFormat formatDay = new SimpleDateFormat("yyyy-MM-dd");
        String day = formatDay.format(now);
        String params = " <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\"> \n"+
                        "    <soapenv:Header/> \n"+
                        "    <soapenv:Body> \n"+
                        "       <tem:setEvent> \n"+
                        "          <tem:eventInfo> \n"+
                        "             <BizAgiWSParam> \n"+
                        "                <domain>domain</domain> \n"+
                        "                <userName>admon</userName> \n"+
                        "                <Events> \n"+
                        "                   <Event> \n"+
                        "                      <EventData> \n"+
                        "                         <radNumber>"+casoPropiedad+"</radNumber> \n"+
                        "                         <eventName>"+ PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs, "eventNameExt") + "</eventName> \n"+
                        "                      </EventData> \n"+
                        "                      <Entities> \n"+
                        "                         <ExtincionPropiedadHorizo> \n"+
                        "                           <MatriculaPropiedad>"+nitPropiedad+"</MatriculaPropiedad> \n"+
                        "                     <DatosGenerales> \n"+
                        "                         <SNumeroRadicado>"+radNumber+"</SNumeroRadicado> \n"+
                        "                         <DFechaRadicado>"+day+"</DFechaRadicado> \n"+
                        "                     </DatosGenerales> \n"+
                        "             </ExtincionPropiedadHorizo> \n"+
                        "                      </Entities> \n"+
                        "                   </Event> \n"+
                        "                </Events> \n"+
                        "             </BizAgiWSParam> \n"+
                        "          </tem:eventInfo> \n"+
                        "       </tem:setEvent> \n"+
                        "    </soapenv:Body> \n"+
                        " </soapenv:Envelope>";
        
        WebServiceDTO objWebServiceDTO = new WebServiceDTO();
        objWebServiceDTO.setParams(params);
        objWebServiceDTO.setUrl(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "urlBIZAGY") + "workflowenginesoa.asmx");
        objWebServiceDTO.setMethod("POST");
        objWebServiceDTO.setSoapAction("http://tempuri.org/setEvent");
        objWebServiceDTO.getHeader().put("Content-Type", "text/xml;charset=utf-8");
        try 
        {
            log.info("Voy a consumir el servicio web de Registrar Extincion PH de BIZAGY.");
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
            log.info("Se consumió el servicio web de Registrar Extincion PH de BIZAGY correctamente.");
            this.logAuditoriaDTO.setAuMensajeError("SubsanarExtincionPH " + "Se consumió el servicio web de Registrar Extincion PH de BIZAGY correctamente. " + responseMensaje);
            this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
            this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
            this.logAuditoriaDTO.setAuLogUsuario(nitPropiedad);
            this.logAuditoriaDTO.setAuAplicacion("BIZAGI");
        }
        catch (Exception ex) 
        {
            log.info("Error al Registrar la Extencion PH BIZAGY: " + ex.toString());
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
