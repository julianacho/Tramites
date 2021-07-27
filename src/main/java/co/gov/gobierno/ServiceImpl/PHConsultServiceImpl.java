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
import co.gov.gobierno.Service.ExtinctionPHService;
import co.gov.gobierno.Service.LogAuditoriaService;
import co.gov.gobierno.Service.PHConsultService;
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
public class PHConsultServiceImpl implements PHConsultService
{
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(PHConsultServiceImpl.class);
    @EJB
    WebService ws;
    
    @EJB
    LogAuditoriaService logService;
    
    private LogAuditoriaDTO logAuditoriaDTO = new LogAuditoriaDTO(0, "NA", "IP", "SO", "NA", "PHConsultServiceImpl", "NA", "NA", "NA", null);


    @Override
    public WebServiceDTO consultAppends(ThirdDTO objThirdDTO, List<PropertieDTO> listPropertieDTOs, String numRad)
    {
        String params = "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:co.gobierno.orfeo.prooveedor\">\n" +
                        "   <soapenv:Header/>\n" +
                        "   <soapenv:Body>\n" +
                        "      <urn:consultaAnexo soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                        "         <numRadicado xsi:type=\"xsd:string\">" + numRad + "</numRadicado>\n" +
                        "         <estado xsi:type=\"xsd:string\">0</estado>\n" +
                        "      </urn:consultaAnexo>\n" +
                        "   </soapenv:Body>\n" +
                        "</soapenv:Envelope>";
        WebServiceDTO objWebServiceDTO = new WebServiceDTO();
        objWebServiceDTO.setUrl(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "urlORFEO"));
        objWebServiceDTO.setMethod("POST");
        objWebServiceDTO.getHeader().put("Content-Type", "text/xml;charset=utf-8");
        objWebServiceDTO.setParams(params);
        try 
        {
            log.info("Voy a consumir el servicio web de Consultar Certificados de ORFEO.");
            objWebServiceDTO = ws.SoapWithParam(objWebServiceDTO);
            log.info("Se consumió el servicio web de Consultar Certificados de ORFEO correctamente.");
            this.logAuditoriaDTO.setAuMensajeError("consultAppends " + "Se consumió el servicio web de Consultar Certificados de ORFEO correctamente. ");
            this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
            this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
            this.logAuditoriaDTO.setAuLogUsuario(objThirdDTO.getIdNumber());
            this.logAuditoriaDTO.setAuAplicacion("ORFEO");
            logService.AddLogAuditoria(logAuditoriaDTO);
        }
        catch (Exception ex) 
        {
            log.info("Error al consultar los certificados ORFEO: " + ex.toString());
            return null;
        }
        if(objWebServiceDTO.isRespuesta())
            return objWebServiceDTO;
        else
            return null;
    }

}
