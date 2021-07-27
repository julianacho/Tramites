/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.Service;

import java.util.List;

import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.DTO.ThirdDTO;

/**
 *
 * @author DELL
 */

public interface IndexService 
{
    /*
    *Este método consulta la información del tercero y la retorna en el objeto ThirdDTO.
    */
    public ThirdDTO ThirdConsult(ThirdDTO objThirdDTO, List<PropertieDTO> listPropertieDTOs);
}
