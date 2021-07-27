/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.ServiceImpl;

import static co.gov.gobierno.Util.XmlHandler.convertStringToXmlDocument;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.primefaces.model.UploadedFile;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import co.gov.gobierno.DTO.LogAuditoriaDTO;
import co.gov.gobierno.DTO.PHRequestDTO;
import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.DTO.RequestDTO;
import co.gov.gobierno.DTO.ThirdDTO;
import co.gov.gobierno.DTO.WebServiceDTO;
import co.gov.gobierno.Service.LogAuditoriaService;
import co.gov.gobierno.Service.PHUpdateService;
import co.gov.gobierno.Service.WebService;
import co.gov.gobierno.Util.Base64Handler;
import co.gov.gobierno.Util.PropertiesHandler;
import co.gov.gobierno.Util.XmlHandler;

/**
 *
 * @author DELL
 */
@Stateless
public class PHUpdateServiceImpl implements PHUpdateService
{
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(PHUpdateServiceImpl.class);
    @EJB
    WebService ws;
    
    @EJB
    LogAuditoriaService logService;
    
    private LogAuditoriaDTO logAuditoriaDTO = new LogAuditoriaDTO(0, "NA", "IP", "SO", "NA", "PHUpdateServiceImpl", "NA", "NA", "NA", null);
   
    
    @Override
    public Map<String,String> readXmlConsultPH(String xml)
    {
        String stateFinal= "";
        String expediente = "";
        try
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
            NodeList nodeLst = document.getElementsByTagName("InformacionConsulta");
            int count=0;
            Map<String,String> infoPH = new HashMap<String, String>();
            for(int i=0; i<nodeLst.getLength(); i++) 
            {
                
                Element err = (Element) nodeLst.item(i);
                String state = err.getElementsByTagName("Estado").item(0).getTextContent();
                String process = err.getElementsByTagName("TipoTramite").item(0).getTextContent();
                //infoPH.put("radicado", err.getElementsByTagName("NumeroRadicado").item(0).getTextContent());
                if(process.equals("1"))
                {
                    NodeList nodeLst2 = document.getElementsByTagName("PropiedadHorizontal");
                    Element err2 = (Element) nodeLst2.item(0);
                    
                    
                    
                    infoPH.put("nombre",err2.getElementsByTagName("SNombrePropiedad").item(0).getTextContent());
                    infoPH.put("matricula", err2.getElementsByTagName("SMatriculainmobiliaria").item(0).getTextContent());
                    infoPH.put("direccion", err2.getElementsByTagName("SDireccion").item(0).getTextContent());
                    infoPH.put("IdTipodedirecion", err2.getElementsByTagName("IdTipodedirecion").item(0).getTextContent());
                    infoPH.put("fechaMatricula", err2.getElementsByTagName("DFechaMatricula").item(0).getTextContent());
                    infoPH.put("nroLocalesComerciales", err2.getElementsByTagName("SNumerolocalescomerciales").item(0).getTextContent());
                    infoPH.put("nroBloques", err2.getElementsByTagName("SNumerodeBloques").item(0).getTextContent());
                    infoPH.put("barrio", err2.getElementsByTagName("SBarrio").item(0).getTextContent());
                    infoPH.put("notaria", err2.getElementsByTagName("NumeroNotaria").item(0).getTextContent());
                    infoPH.put("expediente", err2.getElementsByTagName("NumerodeExpediente").item(0).getTextContent());
                    infoPH.put("nroEtapas", err2.getElementsByTagName("SNumerodeetapas").item(0).getTextContent());
                    infoPH.put("fechaEscrituraPublica", err2.getElementsByTagName("DFechaEscrituraPublica").item(0).getTextContent());
                    infoPH.put("nroEscrituraPublica", err2.getElementsByTagName("SNoEscrituraPublica").item(0).getTextContent());
                    infoPH.put("estrato", err2.getElementsByTagName("IEstrato").item(0).getTextContent());
                    infoPH.put("correo", err2.getElementsByTagName("SCorreoElectronico").item(0).getTextContent());
                    infoPH.put("folios", err2.getElementsByTagName("INoFolios").item(0).getTextContent());
                    infoPH.put("nit", err2.getElementsByTagName("SNIT").item(0).getTextContent());
                    infoPH.put("rut", err2.getElementsByTagName("RUT").item(0).getTextContent());
                    infoPH.put("localidad", err2.getElementsByTagName("IdLocalidad").item(0).getTextContent());
                    infoPH.put("regimen", err2.getElementsByTagName("IdRegimen").item(0).getTextContent());
                    infoPH.put("tipoPropiedad", err2.getElementsByTagName("IdTipoPropiedad").item(0).getTextContent());
                    
                    
                    NodeList nodeLst4 = document.getElementsByTagName("IdInfoComplementaria");                    
                    for (int k = 0; k < nodeLst4.getLength(); k++)
                    {
                        Element err4 = (Element) nodeLst4.item(k);
                        infoPH.put("nroUnidadesPrivadas", err4.getElementsByTagName("INoUnidadesPrivadas").item(0).getTextContent());
                        infoPH.put("nroAreasComunes", err4.getElementsByTagName("INoAreasComunes").item(0).getTextContent());
                        infoPH.put("personaContacto", err4.getElementsByTagName("SNombrePersonaContacto").item(0).getTextContent());
                        infoPH.put("telefonoContacto", err4.getElementsByTagName("STelContacto").item(0).getTextContent());
                        infoPH.put("planEmergencia", err4.getElementsByTagName("BPHtTenePlanEmergencias").item(0).getTextContent().toLowerCase());
                        infoPH.put("nroResidentes", err4.getElementsByTagName("INoResidentes").item(0).getTextContent());
                        infoPH.put("correoContacto", err4.getElementsByTagName("SCorreoElectronicoContacto").item(0).getTextContent());
                        infoPH.put("typeUnidadesPrivadas", err4.getElementsByTagName("TipodeUnidadesPrivadas").item(0).getTextContent());
                        infoPH.put("typeAreasComunes", err4.getElementsByTagName("TipodeAreasComunes").item(0).getTextContent());
                    }
                    
                    
                    
                                    
                    NodeList nodeLst3 = document.getElementsByTagName("M_PH_RepresentanteLegal");
                    for(int j=0; j<nodeLst3.getLength(); j++)
                    {
                        Element err3 = (Element) nodeLst3.item(j);
                        if(err3.getElementsByTagName("Activo").item(0).getTextContent().equals("True"))
                        {
                            infoPH.put("PrimerNombre", err3.getElementsByTagName("SPrimerNombre").item(0).getTextContent());
                            infoPH.put("SegundoNombre", err3.getElementsByTagName("SSegundoNombre").item(0).getTextContent());
                            infoPH.put("PrimerApellido", err3.getElementsByTagName("SPrimerApellido").item(0).getTextContent());
                            infoPH.put("SegundoApellido", err3.getElementsByTagName("SSegundoApellido").item(0).getTextContent());
                            infoPH.put("NombreCompleto", err3.getElementsByTagName("SNombreCompleto").item(0).getTextContent());
                            infoPH.put("FechaInicio", err3.getElementsByTagName("DFechaInicio").item(0).getTextContent());
                            infoPH.put("FechaFin", err3.getElementsByTagName("DFechaFin").item(0).getTextContent());
                            
                            infoPH.put( "SNumActadeAsamblea" , err3.getElementsByTagName("SNumActadeAsamblea").item(0).getTextContent()); 
                            infoPH.put( "SCorreoRepresentanteLegal" , err3.getElementsByTagName("SCorreoRepresentanteLegal").item(0).getTextContent());  
                            infoPH.put( "STelefonoRepLegal" , err3.getElementsByTagName("STelefonoRepLegal").item(0).getTextContent());   
                            infoPH.put( "DFechaActaAsamblea" , err3.getElementsByTagName("DFechaActaAsamblea").item(0).getTextContent());   
                            infoPH.put( "SDireccion" , err3.getElementsByTagName("SDireccion").item(0).getTextContent());   
                            infoPH.put( "NumerodeIdentificacion" , err3.getElementsByTagName("NumerodeIdentificacion").item(0).getTextContent());   
                            infoPH.put( "SNombreRepresentante" , err3.getElementsByTagName("SNombreRepresentante").item(0).getTextContent());   
                            infoPH.put( "SNombreCompleto" , err3.getElementsByTagName("SNombreCompleto").item(0).getTextContent());   
                            infoPH.put( "SBarrio" , err3.getElementsByTagName("SBarrio").item(0).getTextContent());     
                            infoPH.put( "DFechaNacimiento" , err3.getElementsByTagName("DFechaNacimiento").item(0).getTextContent());   
                            infoPH.put( "NumerodeIdentificacionRL" , err3.getElementsByTagName("NumerodeIdentificacionRL").item(0).getTextContent()); 
                            infoPH.put( "IdTipoRepresentacion" , err3.getElementsByTagName("IdTipoRepresentacion").item(0).getTextContent());     
                            infoPH.put( "idTipodeIdentificacionRL" , err3.getElementsByTagName("idTipodeIdentificacionRL").item(0).getTextContent());   
                            infoPH.put( "idGenero" , err3.getElementsByTagName("idGenero").item(0).getTextContent());    
                            infoPH.put( "IdTipoIdentificacion" , err3.getElementsByTagName("IdTipoIdentificacion").item(0).getTextContent());   
                            infoPH.put( "IdTipoPersona" , err3.getElementsByTagName("IdTipoPersona").item(0).getTextContent());     
                            infoPH.put( "EstadoCivil" , err3.getElementsByTagName("EstadoCivil").item(0).getTextContent());    
                            infoPH.put( "IdLocalidad" , err3.getElementsByTagName("IdLocalidad").item(0).getTextContent());   
                            infoPH.put( "key" , err3.getAttributes().getNamedItem("key").getTextContent());
                            infoPH.put("IdTipodedirecionRL", err3.getElementsByTagName("IdTipodedirecionRL").item(0).getTextContent());
                            break;
                        }
                    }
                    break;
                }
                
            }
            for (int i = 0; i < nodeLst.getLength(); i++)
            {
                Element err = (Element) nodeLst.item(i);
                String activo = err.getElementsByTagName("Activo").item(0).getTextContent();
                String tTramite = err.getElementsByTagName("TipoTramite").item(0).getTextContent();
                String state = err.getElementsByTagName("Estado").item(0).getTextContent();
                if (activo.equals("True") || (((i+1) == nodeLst.getLength()) && (tTramite.equals("1") && state.equals("A"))))
                {
                	stateFinal = err.getElementsByTagName("Estado").item(0).getTextContent();
                	infoPH.put("radicado", err.getElementsByTagName("NumeroRadicado").item(0).getTextContent());
                    break;
                }
            }
            return infoPH;
        }
        catch(DOMException e)
        {
            log.error("Error al leer el xml de la consulta de PH: " + e);
            return null;
        }
        catch(Exception e)
        {
            log.error("Error al leer el xml de la consulta de PH: " + e);
            return null;
        }
    }
    
    @Override
    public List<PHRequestDTO> readUnidadesAreas(String xml)
    {
        List<PHRequestDTO> listObjPHRequestDto = new ArrayList<>();
        try
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
            
            NodeList nodeLstAreas = document.getElementsByTagName("AreasdelaPropiedadHorizo");

            for (int k = 0; k < nodeLstAreas.getLength(); k++)
            {
                Element elAreas = (Element) nodeLstAreas.item(k);
                String strCantidad = elAreas.getElementsByTagName("Cantidad").item(0).getTextContent();
                String strTipoArea = "";
                String strArea = "";
                String strPrivada = "";
                String strDescripcionC = "";
                String strDescripcionP = "";
                String indice = Integer.toString(k);
                
                
                NodeList nodeLstUnidadPrivada = elAreas.getElementsByTagName("TipodeUnidadPrivada");
                
                for (int i = 0; i < nodeLstUnidadPrivada.getLength(); i++)
                {
                    Element elAreaPrivada = (Element) nodeLstUnidadPrivada.item(i);
                    if (elAreaPrivada.getElementsByTagName("Codigo").item(0) != null)
                    {
                        strPrivada = elAreaPrivada.getElementsByTagName("Codigo").item(0).getTextContent();
                        strDescripcionP = elAreaPrivada.getElementsByTagName("Descripcion").item(0).getTextContent();
                    }
                }
                
                NodeList nodeLstAreaComun = elAreas.getElementsByTagName("TipodeAreaComun");
                
                for (int i = 0; i < nodeLstAreaComun.getLength(); i++)
                {
                    Element elAreaComun = (Element) nodeLstAreaComun.item(i);
                    if (elAreaComun.getElementsByTagName("Codigo").item(0) != null)
                    {
                        strArea = elAreaComun.getElementsByTagName("Codigo").item(0).getTextContent();
                        strDescripcionC = elAreaComun.getElementsByTagName("Descripcion").item(0).getTextContent();
                    }
                }
                
                NodeList nodeArea = elAreas.getElementsByTagName("Area");
                
                for (int i = 0; i < nodeArea.getLength(); i++)
                {
                    Element elArea = (Element) nodeArea.item(i);
                    strTipoArea = elArea.getElementsByTagName("Codigo").item(0).getTextContent();
                }
                PHRequestDTO objPHRequestDTO = new PHRequestDTO(strTipoArea, strArea, strPrivada, strCantidad, indice, strDescripcionC, strDescripcionP);
                listObjPHRequestDto.add(objPHRequestDTO);
            }
            return listObjPHRequestDto;
        } 
        catch(DOMException e)
        {
            log.error("Error al leer el xml de la consulta de PH: " + e);
            return null;
        }
        catch(Exception e)
        {
            log.error("Error al leer el xml de la consulta de PH: " + e);
            return null;
        }
    }
    
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
                            //"         <file xsi:type=\"xsd:string\">" + (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "file", "1")==null? "":PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "file", "1")) + "</file>\n" +
                            tipoEndpointOrfeo + 
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
                    this.logAuditoriaDTO.setAuMensajeError("GetRadicadoNumber " + "Se va a consumir el servicio de radicarDocumentoP de ORFEO... " +responseMensaje);
                    this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
                    this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
                    this.logAuditoriaDTO.setAuLogUsuario(objThirdDTO.getIdNumber());
                    this.logAuditoriaDTO.setAuAplicacion("ORFEO");
                    logService.AddLogAuditoria(logAuditoriaDTO);
            		return radNumber;
            	}
            }
            this.logAuditoriaDTO.setAuMensajeError("GetRadicadoNumber " + "Se va a consumir el servicio de radicarDocumentoP de ORFEO... " +responseMensaje);
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
            	String xmlFileTemp = objWebServiceDTO.getXmlFile().replace("&gt;", ">").replace("&lt;", "<").replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
            	String response = XmlHandler.ReadXmlFileGetTag(xmlFileTemp, "codigoError");
            	if(response != null && response.contains("0")) {
            		responseMensaje = XmlHandler.ReadXmlFileGetTag(xmlFileTemp, "mensaje");
            		objWebServiceDTO.setRespuesta(false);
                    this.logAuditoriaDTO.setAuMensajeError("AppendExpedient " + "Se va a consumir el servicio de anexar expediente de ORFEO... " + responseMensaje);
                    this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
                    this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
                    this.logAuditoriaDTO.setAuLogUsuario(PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "USUARIO", "NA"));
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
                this.logAuditoriaDTO.setAuMensajeError("CreateAppend " + "Se va a consumir el servicio de crear anexo de ORFEO... ");
                this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
                this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
                this.logAuditoriaDTO.setAuLogUsuario(radNumber);
                this.logAuditoriaDTO.setAuAplicacion("ORFEO");
                this.logAuditoriaDTO.setAuUserArchivo(objUploadedFile.getFileName());
                logService.AddLogAuditoria(logAuditoriaDTO);
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
                        this.logAuditoriaDTO.setAuLogUsuario(radNumber);
                        this.logAuditoriaDTO.setAuAplicacion("ORFEO");
                		return response;
                	}
                }
                
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
    public WebServiceDTO RegisterSubsanacionPH(ThirdDTO objThirdDTO, List<PropertieDTO> listPropertieDTOs, List<PropertieDTO> listPHPropertieDTOs, String expNumber,
            String radNumber, String name, String state, String nit, String registerDate, String publicScriptNumber,
            String publicScriptdate, String type1, String type2, String type3, String locate, String neighborhood,
            String realStateRegistration, String folio, String address, String email, String stratum, String regimen,
            String typeNroPrivateUnits, String nroPrivateUnits, String commonArea, String nroCommonArea,
            String presentationType, String academicFormation, String nroResidents, String nroBloques,
            String nroEtapas, String nroLocales, String contactPerson, String contactPhone, String contactEmail,
            String notaria, String emergencyPlan, String personType, String idTypeLegalAgent, String idNumberLegalAgent,
            String firstNameLegalAgent, String lastNameLegalAgent, String secondNameLegalAgent,
            String secondLastNameLegalAgent, String birthDateLegalAgent, String nationalityLegalAgent,
            String cellphoneLegalAgent, String phoneLegalAgent, String addressLegalAgent, String emailLegalAgent,
            String locationLegalAgent, String stratumLegalAgent, String specialConditionLegalAgent,
            String occupationLegalAgent, String idGenreLegalAgent, String idStatusLegalAgent, String dateAgent,
            String startAgent, String endAgent, String numberActAgent, String dateActAgent, String nameAgentJuridico,
            String idNumberAgentJuridico, String idTypeAgentJuridico, String idTypeRepLegalAgent, String razonSocialLegalAgent, String radicadoPropiedad,
            String key, String typeDireccionPH, String typeDireccionLegalAgent, String rut, List<PHRequestDTO> listUnidadesPHRequestDTO)
    {
        String representante = "";
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
        if (personType.equals("PN"))
        {
            representante = "<XRepresentantesLegales>\n" +
                            "<M_PH_RepresentanteLegal  businessKey=\"idM_PH_RepresentanteLegal="+key+"\"> \n" +
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
                            "<idGenero businessKey=\"SCodigo='"+idGenreLegalAgent+"'\"/>\n" +
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
                            "<M_PH_RepresentanteLegal  businessKey=\"idM_PH_RepresentanteLegal="+key+"\"> \n" +
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
        }
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
        String params = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\"> \n"+
                        "   <soapenv:Header/> \n"+
                        "   <soapenv:Body> \n"+
                        "      <tem:setEvent> \n"+
                        "         <tem:eventInfo> \n"+
                        "            <BizAgiWSParam> \n"+
                        "               <domain>domain</domain> \n"+
                        "               <userName>admon</userName> \n"+
                        "               <Events> \n"+
                        "                  <Event> \n"+
                        "                     <EventData> \n"+
                        "                        <radNumber>"+ radicadoPropiedad +"</radNumber> \n"+
                        "                        <eventName>RecibirRespuestaSolicitant</eventName> \n"+
                        "                     </EventData> \n"+
                        "                     <Entities> \n"+
                        "			<M_IPH_Solicitud> \n"+
                        
                        "                           <IdPropiedadHorizontal>\n" +
                        "				<SNombrePropiedad>" + name + "</SNombrePropiedad>\n" +
                        "				<SNIT>" + nit + "</SNIT>\n" +
                        "                               <RUT>"+rut+"</RUT>\n"+
                        "				<SNoEscrituraPublica>" + publicScriptNumber + "</SNoEscrituraPublica>\n" +
                        "				<SMatriculainmobiliaria>" + realStateRegistration + "</SMatriculainmobiliaria>\n" +
                        "				<DFechaMatricula>" + registerDate + "T12:12:12</DFechaMatricula>\n" +
                        "				<DFechaEscrituraPublica>" + publicScriptdate + "T12:12:12</DFechaEscrituraPublica>\n" +
                        "				<INoFolios>" + folio + "</INoFolios>\n" +
                        "				<IdTipoPropiedad businessKey=\"SCodigo='" + type3 + "'\"/>\n" +
                        "				<NumeroNotaria>"+notaria+"</NumeroNotaria>\n" +
                        "				<SBarrio>"+neighborhood+"</SBarrio>\n" +
                        "                               <IdTipodedirecion businessKey=\"SCodigo='"+typeDireccionPH+"'\"/>\n"+
                        "				<SDireccion>" + address.replace(" ","") + "</SDireccion>\n" +
                        "				<SCorreoElectronico>" + email + "</SCorreoElectronico>\n" +
                        "				<IEstrato>" + stratum + "</IEstrato>\n" +
                        "				<IdRegimen businessKey=\"SCodigo='"+regimen+"'\"/>\n" +
                        "  				<IdLocalidad businessKey=\"Codigo='"+locate+"'\"/>\n" +
                        "				<NumerodeExpediente>" + expNumber + "</NumerodeExpediente>\n" +
                        "				<SNumerodeBloques>" + nroBloques + "</SNumerodeBloques>\n" +
                        "				<SNumerodeetapas>" + nroEtapas + "</SNumerodeetapas>\n" +
                        "				<SNumerolocalescomerciales>" + nroLocales + "</SNumerolocalescomerciales>\n" +
                        "				<IdInfoComplementaria>\n" +
                        "                                   <INoUnidadesPrivadas>" + nroPrivateUnits + "</INoUnidadesPrivadas>\n" +
                                                            tagTypeNroPrivateUnits +
                        "                                   <INoAreasComunes>" + nroCommonArea + "</INoAreasComunes>\n" +
                                                            tagCommonArea +
                        "                                   <INoResidentes>" + nroResidents + "</INoResidentes>\n" +
                        "                                   <SNombrePersonaContacto>" + contactPerson + "</SNombrePersonaContacto>\n" +
                        "                                   <BPHtTenePlanEmergencias>" + emergencyPlan + "</BPHtTenePlanEmergencias>\n" +
                        "                                   <STelContacto>" + contactPhone + "</STelContacto>																					\n" +
                        "                                   <SCorreoElectronicoContacto>" + contactEmail + "</SCorreoElectronicoContacto>\n" +
                        "                                   <Areas>\n"+
                                                                strInfoComplementaria+
                        "                                   </Areas>\n"+
                        "                               </IdInfoComplementaria>\n" +
                                                        representante +
                        "  			</IdPropiedadHorizontal>\n" +
                        "			<IdDatosGenereales> \n"+
                        "				<SNumeroRadicado>" + radNumber + "</SNumeroRadicado>\n" +
                        "  				<DFechaRadicado>"+day+"</DFechaRadicado>\n" +
                        "  				<IdLocalidad businessKey=\"Codigo='"+locate+"'\"/>\n" +
                        "			</IdDatosGenereales> \n"+
                        "			</M_IPH_Solicitud> \n"+
                        "                     </Entities> \n"+
                        "                  </Event> \n"+
                        "               </Events> \n"+
                        "            </BizAgiWSParam> \n"+
                        "         </tem:eventInfo> \n"+
                        "      </tem:setEvent> \n"+
                        "   </soapenv:Body> \n"+
                        "</soapenv:Envelope> ";
        
        WebServiceDTO objWebServiceDTO = new WebServiceDTO();
        objWebServiceDTO.setParams(params);
        objWebServiceDTO.setUrl(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "urlBIZAGY") + "workflowenginesoa.asmx");
        objWebServiceDTO.setMethod("POST");
        objWebServiceDTO.setSoapAction("http://tempuri.org/setEvent");
        objWebServiceDTO.getHeader().put("Content-Type", "text/xml;charset=utf-8");
        try 
        {
            log.info("Voy a consumir el servicio web de Actualizar PH de BIZAGY.");
            objWebServiceDTO = ws.SoapWithParam(objWebServiceDTO);
            String responseMensaje = "";
            if (objWebServiceDTO.getXmlFile().contains("errorCode") || objWebServiceDTO.getXmlFile().contains("errorCode")) {
            	log.info("Bizagi TagError");
            	String response = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "errorCode");
            	if(!response.isEmpty()) {
            		responseMensaje = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "errorMessage");
            		objWebServiceDTO.setRespuesta(false);
            	}
            }
            log.info("Se consumió el servicio web de Actualizar PH de BIZAGY correctamente.");
            this.logAuditoriaDTO.setAuMensajeError("RegisterSubsanacionPH " + "Se consumió el servicio web de Actualizar PH de BIZAGY correctamente. " + responseMensaje);
            this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
            this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
            this.logAuditoriaDTO.setAuLogUsuario(radNumber);
            this.logAuditoriaDTO.setAuAplicacion("BIZAGI");
            logService.AddLogAuditoria(logAuditoriaDTO);
        }
        catch (Exception ex) 
        {
            log.info("Error al Actualizar la PH BIZAGY: " + ex.toString());
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
