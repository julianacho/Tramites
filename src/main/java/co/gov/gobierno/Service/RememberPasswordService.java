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

public interface RememberPasswordService 
{
    /**
     * Este método permite cambiar la contraseña del usuario mediante los datos que recibe por parámetro.
     * @param user
     * @param pass
     * @param token: Este es el token que se obtiene al autenticarse en la aplicación.
     * @param listPropertieDTOs
     * @return 
     */
    public WebServiceDTO ChangePassword(String user, String pass, String token, List<PropertieDTO> listPropertieDTOs);
}
