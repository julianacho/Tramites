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

public interface RegisterService 
{
    /*
     * Este método crea un usuario consumiendo el servicio web de CREATE de seguridad.
     */
    public WebServiceDTO CreateUser(String firstName, String lastName, String user, String pass, List<PropertieDTO> listPropertieDTOs);   
    
}
