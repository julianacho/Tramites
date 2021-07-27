/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.Service;

import java.util.List;
import java.util.Map;

import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.DTO.RequestDTO;
import co.gov.gobierno.DTO.ThirdDTO;
import co.gov.gobierno.DTO.WebServiceDTO;

/**
 *
 * @author DELL
 */

public interface PHConsultInitService
{
    public WebServiceDTO FindPH(List<PropertieDTO> listPropertieDTOs, String nit, String localidad, String nombre);
    
    public Map<String,String> readXmlConsultPH(String xml, String tipoconsulta);
    
    public List<RequestDTO> getDataTablePH(String xml, List<RequestDTO> objRequestDTOs, String userlog);
    
    public WebServiceDTO createCurrentCertificate(String realStateRegistration,String radNumber,ThirdDTO objThirdDTO, List<PropertieDTO> listPropertieDTOs);
    
    public WebServiceDTO DownloadORFEO(ThirdDTO objThirdDTO, List<PropertieDTO> listPropertieDTOs, String radicado);
}
