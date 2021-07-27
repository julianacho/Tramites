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
import co.gov.gobierno.Service.LoginService;
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
public class LoginServiceImpl implements LoginService
{
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

    @EJB
    WebService ws;
    
    @EJB
    LogAuditoriaService logService;
    
    private LogAuditoriaDTO logAuditoriaDTO = new LogAuditoriaDTO(0, "NA", "IP", "SO", "NA", "LoginServiceImpl", "NA", "NA", "NA", null);

   
    @Override
    public WebServiceDTO Authenticate(String user, String pass, List<PropertieDTO> listPropertieDTOs) 
    {
		
        WebServiceDTO objWebServiceDTO = new WebServiceDTO();
        objWebServiceDTO.setRespuesta(true);
		
        return objWebServiceDTO;
    }

    
}
