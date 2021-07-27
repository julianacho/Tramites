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
import co.gov.gobierno.Service.ChangePasswordService;
import co.gov.gobierno.Service.LogAuditoriaService;
import co.gov.gobierno.Service.WebService;
import co.gov.gobierno.Util.PropertiesHandler;
import java.util.List;
import java.util.Properties;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.LoggerFactory;

/**
 *
 * @author DELL
 */
@Stateless
public class ChangePasswordServiceImpl implements ChangePasswordService
{
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(ChangePasswordServiceImpl.class);
    @EJB
    WebService ws;
    
    @EJB
    LogAuditoriaService logService;
    
    private LogAuditoriaDTO logAuditoriaDTO = new LogAuditoriaDTO(0, "NA", "IP", "SO", "NA", "ChangePasswordServiceImpl", "NA", "NA", "NA", null);


    @Override
    public WebServiceDTO ChangePassword(ThirdDTO objThirdDTO, String pass, String token, List<PropertieDTO> listPropertieDTOs) 
    {
		/*
		 * String params = "{\n\"nombre\":\"" + objThirdDTO.getFirstName() +
		 * "\",\n\"apellido\":\"" + objThirdDTO.getLastName() + "\",\n\"usuario\":\"" +
		 * objThirdDTO.getUser() + "\",\n\"password\":\"" + pass + "\"\n}";
		 */
        WebServiceDTO objWebServiceDTO = new WebServiceDTO();
		/*
		 * objWebServiceDTO.getHeader().put("Content-Type", "application/json");
		 * objWebServiceDTO.getHeader().put("id", objThirdDTO.getUser());
		 * objWebServiceDTO.getHeader().put("x-access-token", token);
		 * objWebServiceDTO.setMethod("POST"); objWebServiceDTO.setParams(params);
		 * objWebServiceDTO.setUrl(PropertiesHandler.PropertieValueOfKeyFromDB(
		 * listPropertieDTOs, "urlSeguridad") + "/user/update");
		 * log.info("Voy a consumir el servicio web de Cambiar Contrasenia.");
		 * objWebServiceDTO = ws.postServiceWithParam(objWebServiceDTO);
		 * log.info("Se consumió el servicio web de Cambiar Contrasenia correctamente."
		 * ); this.logAuditoriaDTO.setAuMensajeError("ChangePassword " +
		 * "Se consumió el servicio web de Cambiar Contrasenia correctamente. ");
		 * this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
		 * this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " +
		 * objWebServiceDTO.getJsonFile());
		 * this.logAuditoriaDTO.setAuLogUsuario(objThirdDTO.getUser());
		 * this.logAuditoriaDTO.setAuAplicacion("SEGURIDAD");
		 * logService.AddLogAuditoria(logAuditoriaDTO);
		 */
        
        objWebServiceDTO.setRespuesta(true);
        return objWebServiceDTO;
    }
    
}
