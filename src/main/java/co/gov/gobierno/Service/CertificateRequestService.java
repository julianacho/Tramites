/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.Service;

import java.util.List;

import org.primefaces.model.UploadedFile;

import co.gov.gobierno.DTO.LegalAgentDTO;
import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.DTO.ThirdDTO;
import co.gov.gobierno.DTO.WebServiceDTO;

/**
 *
 * @author DELL
 */
public interface CertificateRequestService
{
    /*
    *Este método crea y retorna un nuevo número de radicado consumiendo el servicio web correpondiente de ADAX.
    */
    public String getRadicadoNumber(WebServiceDTO objWebServiceDTO, List<PropertieDTO> listPropertieDTOs);
    
    /*
    *Este método realiza todo el registro de la solicitud, consumiendo el servicio web correpondiente de ADAX.
    */
    public WebServiceDTO Request(WebServiceDTO objWebServiceDTO, ThirdDTO objThirdDTO, String radicado, List<PropertieDTO> listPropertieDTOs, 
            UploadedFile uploadedFileAttendant, UploadedFile uploadedFilePublicService, UploadedFile uploadedFileYounger,
            UploadedFile uploadedFileCustody);
    
    /*
    *Este método realiza todo el registro de la solicitud, consumiendo el servicio web correpondiente de BIZAGI.
    */
    public WebServiceDTO SecondRequest(WebServiceDTO objWebServiceDTO, ThirdDTO objThirdDTO, String radicado, String procedureType, 
            String externalRelation, List<PropertieDTO> listPropertieDTOs, String related, String younger, String itemId, String listId,
            UploadedFile uploadedFileAttendant, UploadedFile uploadedFilePublicService, UploadedFile uploadedFileYounger,
            UploadedFile uploadedFileCustody, String apostille, List<LegalAgentDTO> privadoLibertad);
    
    /*
    *Este método crea un tercero, en este método se usa para crear un menor de edad y asociarlo al usuario logueado.
    */
    public WebServiceDTO CreateThird(String idType, String idNumber, String firstName, String secondName, String lastName, 
            String secondLastName, String birthDate, String gender, String sexo, String sexualOrient, String nationality, String cellphone, String phone,
            String address, String ruralAddress, String location, String neightborhood, String residenceCity, String occupation,
            String specialCondition, String stratum, String email, String user, List<PropertieDTO> listPropertieDTOs);
    
    /*
    *Este método permite la corrección de la solicitud del certificado de residencia, asociando el número de radicado viejo con uno nuevo.
    *Se consume el servicio web de BIZAGI.
    */
    public WebServiceDTO CorrectRequest(WebServiceDTO objWebServiceDTO, ThirdDTO objThirdDTO, String procedureType, 
            String externalRelation, List<PropertieDTO> listPropertieDTOs, String related, String younger, String radNumber, String oldRadNumber, String certificateKey,String certificateKey2, String atentionDate,
            String itemId, String listId, UploadedFile uploadedFileAttendant, UploadedFile uploadedFilePublicService, UploadedFile uploadedFileYounger,
            UploadedFile uploadedFileCustody, List<LegalAgentDTO> privadoLibertad);
    
    /*
    *Este método retorna un arreglo de cadenas que se obtienen al leer la cadena con formato XML.
    */
    public String[] getFilesTag(String xml);
    
    /*
     * Este método retorna un nuevo número de radicado consumiendo el servicio web de ORFEO de RadicarDocumentoP.
     */
    public String getRadicadoNumberOrfeo(ThirdDTO objThirdDTO, List<PropertieDTO> listPropertieDTOs);
    
    /*
     * Este método anexa o asocia el número de expediente con el número de radicado que se reciben por parámetro.
     * Se consume el servicio web de AnexarExpediente de ORFEO.
     */
    public WebServiceDTO AppendExpedient(String radNumber, String expNumber, List<PropertieDTO> listPropertieDTOs);
    
    /*
     * Este método recibe un archivo y lo envía al servicio web de CrearAnexo asociado al número de radicado que se recibe.
    *Se consume el servicio web de ORFEO.
     */
    public String CreateAppend(String radNumber, UploadedFile objUploadedFile, List<PropertieDTO> listPropertieDTOs, String idFile, String fileName);
}
