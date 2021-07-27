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

/**
 *
 * @author DELL
 */

public interface ExtinctionPHService
{
    /*
     * Este método retorna un nuevo número de radicado consumiendo el servicio web de ORFEO de RadicarDocumentoP.
     */
    public String GetRadicadoNumber(ThirdDTO objThirdDTO, List<PropertieDTO> listPropertieDTOs, String location, String neightborhood);
    
    /*
     * Este método retorna un nuevo número de expediente consumiendo el servicio web de ORFEO de CrearExpediente.
     */
    public String GetExpedientNumber(String radNumber, List<PropertieDTO> listPropertieDTOs);
    
    /*
     * Este método anexa o asocia el número de expediente con el número de radicado que se reciben por parámetro.
     * Se consume el servicio web de AnexarExpediente de ORFEO.
     */
    public String AppendExpedient(String radNumber, String expNumber, List<PropertieDTO> listPropertieDTOs);
    
    /*
     * Este método recibe un archivo y lo envía al servicio web de CrearAnexo asociado al número de radicado que se recibe.
     */
    public String CreateAppend(String radNumber, UploadedFile objUploadedFile, List<PropertieDTO> listPropertieDTOs);
}
