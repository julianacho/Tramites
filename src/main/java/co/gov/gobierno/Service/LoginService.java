/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.Service;

import java.util.List;

import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.DTO.WebServiceDTO;

/**
 *
 * @author DELL
 */

public interface LoginService 
{
    /*
    *Este método permite autenticarse en la aplicación. Consume el servicio web de Authenticate de seguridad.
    */
    public WebServiceDTO Authenticate(String user, String pass, List<PropertieDTO> listPropertieDTOs);
}
