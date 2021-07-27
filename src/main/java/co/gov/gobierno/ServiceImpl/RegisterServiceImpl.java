/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.ServiceImpl;

import co.gov.gobierno.DTO.LogAuditoriaDTO;
import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.DTO.WebServiceDTO;
import co.gov.gobierno.Service.LogAuditoriaService;
import co.gov.gobierno.Service.RegisterService;
import co.gov.gobierno.Service.WebService;
import co.gov.gobierno.Util.PropertiesHandler;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.LoggerFactory;

/*import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;*/
/**
 *
 * @author DELL
 */
@Stateless
public class RegisterServiceImpl implements RegisterService {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(RegisterServiceImpl.class);
    @EJB
    WebService ws;
    
    @EJB
    LogAuditoriaService logService;
    
    private LogAuditoriaDTO logAuditoriaDTO = new LogAuditoriaDTO(0, "NA", "IP", "SO", "NA", "RegisterServiceImpl", "NA", "NA", "NA", null);
   
    

    @Override
    public WebServiceDTO CreateUser(String firstName, String lastName, String user, String pass, List<PropertieDTO> listPropertieDTOs) 
    {
        String params = "{\n\"nombre\":\""
                    + firstName + "\",\n\"apellido\":\""
                    + lastName + "\",\n\"usuario\":\""
                    + user + "\",\n\"password\":\""
                    + pass + "\"\n}";
        WebServiceDTO objWebServiceDTO = new WebServiceDTO();
        objWebServiceDTO.getHeader().put("Content-Type", "application/json");
        objWebServiceDTO.setMethod("POST");
        objWebServiceDTO.setParams(params);
        objWebServiceDTO.setUrl(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "urlSeguridad") + "/user/create");
        log.info("Voy a consumir el servicio web de Crear Usuario de seguridad.");
        objWebServiceDTO = ws.postServiceWithParam(objWebServiceDTO);
        log.info("Se consumió el servicio web de Crear Usuario de seguridad correctamente.");
        this.logAuditoriaDTO.setAuMensajeError("CreateUser " + "Se consumió el servicio web de Crear Usuario de seguridad correctamente. ");
        this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
        this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
        this.logAuditoriaDTO.setAuLogUsuario(user);
        this.logAuditoriaDTO.setAuAplicacion("SEGURIDAD");
        logService.AddLogAuditoria(logAuditoriaDTO);
        return objWebServiceDTO;
    }


}
