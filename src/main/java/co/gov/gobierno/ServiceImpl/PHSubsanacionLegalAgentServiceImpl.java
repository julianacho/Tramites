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
import co.gov.gobierno.Service.LogAuditoriaService;
import co.gov.gobierno.Service.PHSubsanacionLegalAgentService;
import javax.ejb.Stateless;

import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import co.gov.gobierno.Service.WebService;
import co.gov.gobierno.Util.PropertiesHandler;
import co.gov.gobierno.Util.XmlHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author DELL
 */
@Stateless
public class PHSubsanacionLegalAgentServiceImpl implements PHSubsanacionLegalAgentService
{
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(PHSubsanacionLegalAgentServiceImpl.class);
	
    @EJB
    WebService ws;
    
    @EJB
    LogAuditoriaService logService;
    
    private LogAuditoriaDTO logAuditoriaDTO = new LogAuditoriaDTO(0, "NA", "IP", "SO", "NA", "PHSubsanacionLegalAgentServiceImpl", "NA", "NA", "NA", null);

    
    @Override
    public WebServiceDTO subsanacionLegalAgentPH(ThirdDTO objThirdDTO, List<PropertieDTO> listPropertieDTOs, List<PropertieDTO> listPHPropertieDTOs, String expNumber,
            String radNumber,String nitPropiedad, String personType, String idTypeLegalAgent, String idNumberLegalAgent,
            String firstNameLegalAgent, String lastNameLegalAgent, String secondNameLegalAgent,
            String secondLastNameLegalAgent, String phoneLegalAgent, String addressLegalAgent, String emailLegalAgent,
            String locationLegalAgent, String idGenreLegalAgent, String idStatusLegalAgent, 
            String startAgent, String endAgent, String numberActAgent, String dateActAgent, String nameAgentJuridico,
            String idNumberAgentJuridico, String idTypeAgentJuridico, String idTypeRepLegalAgent, String razonSocialLegalAgent,
            String idCaso, String typeDireccionLegalAgent)
    {
        
        Date now = new Date();
        SimpleDateFormat formatDay = new SimpleDateFormat("yyyy-MM-dd");
        String day = formatDay.format(now);
        String representante = "";
        String representanteTemp = "";
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
        	numberActAgent = item.getActNumber();
        	typeDireccionLegalAgent = "1";//item.getTypeDireccionLegalAgent(); TODO Validar luego de donde viene este campo
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
        	}else
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
        String params = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n"+
                        "   <soapenv:Header/>\n"+
                        "   <soapenv:Body>\n"+
                        "      <tem:setEvent>\n"+
                        "         <tem:eventInfo>\n"+
                        "            <BizAgiWSParam>\n"+
                        "               <domain>"+ PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs, "domainRlC") + "</domain>\n"+
                        "               <userName>"+ PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs, "userNameRlC") + "</userName>\n"+
                        "               <Events>\n"+
                        "                  <Event>\n"+
                        "                     <EventData>\n"+
                        "                        <radNumber>"+ idCaso +"</radNumber>\n"+
                        "                        <eventName>"+ PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs, "eventNameRlC") + "</eventName>\n"+
                        "                     </EventData>\n"+
                        "                     <Entities>\n"+
                        "                        <ActualizacionRepresentan>\n"+
                        "                                <MatriculaPropiedad>"+ nitPropiedad +"</MatriculaPropiedad>\n"+
                        "                                <NumerodeRadicado>"+ radNumber +"</NumerodeRadicado>\n"+
                                                                 representante +
                        "                                        <DatosGeneralesPH>\n"+
                        "                                                <SNumeroRadicado>"+ radNumber +"</SNumeroRadicado>\n"+
                        "                                                <DFechaRadicado>"+ day +"</DFechaRadicado>\n"+
                        "                                        </DatosGeneralesPH>\n"+
                        "                        </ActualizacionRepresentan>\n"+
                        "                     </Entities>\n"+
                        "                  </Event>\n"+
                        "               </Events>\n"+
                        "            </BizAgiWSParam>\n"+
                        "         </tem:eventInfo>\n"+
                        "      </tem:setEvent>\n"+
                        "   </soapenv:Body>\n"+
                        "</soapenv:Envelope>";
        
        WebServiceDTO objWebServiceDTO = new WebServiceDTO();
        objWebServiceDTO.setParams(params);
        objWebServiceDTO.setUrl(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "urlBIZAGY") + "workflowenginesoa.asmx");
        objWebServiceDTO.setMethod("POST");
        objWebServiceDTO.setSoapAction("http://tempuri.org/setEvent");
        objWebServiceDTO.getHeader().put("Content-Type", "text/xml;charset=utf-8");
        try 
        {
            log.info("Voy a consumir el servicio web de Subsanar Representante PH de BIZAGY.");
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
            this.logAuditoriaDTO.setAuMensajeError("subsanacionLegalAgentPH " + "Se consumió el servicio web de Actualizar Subsanar PH de BIZAGY correctamente. " + responseMensaje);
            this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
            this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
            this.logAuditoriaDTO.setAuLogUsuario(objThirdDTO.getIdNumber());
            this.logAuditoriaDTO.setAuAplicacion("BIZAGI");
            logService.AddLogAuditoria(logAuditoriaDTO);
            log.info("Se consumió el servicio web de Actualizar Subsanar PH de BIZAGY correctamente.");
        }
        catch (Exception ex) 
        {
            log.error("Error al Subsanar la Representante PH BIZAGY: " + ex.toString());
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