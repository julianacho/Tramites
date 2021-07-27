/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.Service;

import java.util.List;

import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.DTO.RequestDTO;
import co.gov.gobierno.DTO.ThirdDTO;
import co.gov.gobierno.DTO.WebServiceDTO;

/**
 *
 * @author DELL
 */

public interface CertificateConsultService 
{
    /*
    *Este método consume el servicio web de BIZAGI de consultar las solicitudes de certificado de residencia.
    *Retorna el objeto WebServiceDTO con el XML resultante.
    */
    public WebServiceDTO getRequest(ThirdDTO objThirdDTO, List<PropertieDTO> listPropertieDTOs);
    
    /*
    *Este método recibe una cadena con formato XMl, y dependiendo si es menor de edad o no, se lee el XML
    *y se llena una lista de RequestDTO, que son las filas de la tabla a mostrar.
    */
    public List<RequestDTO> readConsult(String xml, String menorEdad);
    
    /*
    *Consume el servicio web de ADAX para obtener el base64 del certificado.
    */
    public WebServiceDTO GetDownloadRequest(ThirdDTO objThirdDTO, List<PropertieDTO> listPropertieDTOs, RequestDTO objRequestDTO);
    
    /*
    *Este método consume el servicio web de ORFEO de consultarAnexo, y retorna un 
    *WebServiceDTO con el xml resultante que tendría el base64 del certificado.
    */
    public WebServiceDTO DownloadORFEO(ThirdDTO objThirdDTO, List<PropertieDTO> listPropertieDTOs, RequestDTO objRequestDTO);
}
