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

public interface PHConsultService
{
    /*
    *Este metodo consulta los anexos creados en ORFEO.
    */
    public WebServiceDTO consultAppends(ThirdDTO objThirdDTO, List<PropertieDTO> listPropertieDTOs, String numRad);
}
