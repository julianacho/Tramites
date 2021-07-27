/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.Service;

import java.util.List;

import org.primefaces.model.UploadedFile;

import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.DTO.ThirdDTO;
import co.gov.gobierno.DTO.WebServiceDTO;
/**
 *
 * @author DELL
 */

public interface PHExtincionService
{
    
    public String GetRadicadoNumber(ThirdDTO objThirdDTO, List<PropertieDTO> listPropertieDTOs);
    
    public String AppendExpedient(String radNumber, String expNumber, List<PropertieDTO> listPropertieDTOs);
    
    public String CreateAppend(String radNumber, UploadedFile objUploadedFile, List<PropertieDTO> listPropertieDTOs, String numberDocument);
    
    public WebServiceDTO ExtinguirPH(ThirdDTO objThirdDTO, List<PropertieDTO> listPropertieDTOs, List<PropertieDTO> listPHPropertieDTOs, String expNumber,
            String radNumber, String nitPropiedad);
    
    public WebServiceDTO SubsanarExtincionPH(ThirdDTO objThirdDTO, List<PropertieDTO> listPropertieDTOs, List<PropertieDTO> listPHPropertieDTOs, String expNumber,
            String radNumber, String nitPropiedad, String casoPropiedad);
}
