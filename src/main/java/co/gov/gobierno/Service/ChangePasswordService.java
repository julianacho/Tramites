/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.Service;

import java.util.List;

import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.DTO.ThirdDTO;
import co.gov.gobierno.DTO.WebServiceDTO;

/**
 *
 * @author DELL
 */

public interface ChangePasswordService 
{
    /*
    *Este método cambia la contraseña del usuaio logueado, consumiendo el servicio web de Actualización de seguridad.
    *@param token: Este es el token que se obtiene al autenticarse a la aplicación.
    */
    public WebServiceDTO ChangePassword(ThirdDTO objThirdDTO, String pass, String token, List<PropertieDTO> listPropertieDTOs);
}
