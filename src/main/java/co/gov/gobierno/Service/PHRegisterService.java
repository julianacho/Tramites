/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.Service;

import java.util.List;

import org.primefaces.model.UploadedFile;

import co.gov.gobierno.DTO.PHRequestDTO;
import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.DTO.ThirdDTO;
import co.gov.gobierno.DTO.WebServiceDTO;

/**
 *
 * @author DELL
 */

public interface PHRegisterService
{


    /*
     * Este método retorna un nuevo número de radicado consumiendo el servicio web de ORFEO de RadicarDocumentoP.
     */
    public String GetRadicadoNumber(ThirdDTO objThirdDTO, List<PropertieDTO> listPropertieDTOs);

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
    public String CreateAppend(String radNumber, UploadedFile objUploadedFile, List<PropertieDTO> listPropertieDTOs, String numberDocument);

    public WebServiceDTO RegisterPH(ThirdDTO objThirdDTO, List<PropertieDTO> listPropertieDTOs, List<PropertieDTO> listPHPropertieDTOs, String expNumber,
            String radNumber, String name, String state, String nit, String registerDate, String publicScriptNumber,
            String publicScriptdate, String type1, String type2, String type3, String locate, String neighborhood, String neighborhoodName,
            String realStateRegistration, String folio, String address, String typeDireccionPH, String email, String stratum, String regimen,
            String typeNroPrivateUnits, String nroPrivateUnits, String commonArea, String nroCommonArea,
            String presentationType, String academicFormation, String nroResidents, String nroBloques,
            String nroEtapas, String nroLocales, String contactPerson, String contactPhone, String contactEmail,
            String notaria, String emergencyPlan, String personType, String idTypeLegalAgent, String idNumberLegalAgent,
            String firstNameLegalAgent, String lastNameLegalAgent, String secondNameLegalAgent,
            String secondLastNameLegalAgent, String birthDateLegalAgent, String nationalityLegalAgent,
            String cellphoneLegalAgent, String phoneLegalAgent, String addressLegalAgent, String typeDireccionLegalAgent,String emailLegalAgent,
            String locationLegalAgent, String stratumLegalAgent, String specialConditionLegalAgent,
            String occupationLegalAgent, String idGenreLegalAgent, String idStatusLegalAgent, String dateAgent,
            String startAgent, String endAgent, String numberActAgent, String dateActAgent,
            String nameAgentJuridico, String idNumberAgentJuridico, String idTypeAgentJuridico,
            String idTypeRepLegalAgent, String razonSocialLegalAgent, String rut,List<PHRequestDTO> listUnidadesPHRequestDTO);
    
}
