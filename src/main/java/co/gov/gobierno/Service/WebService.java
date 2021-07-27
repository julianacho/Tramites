/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.Service;

import java.util.List;

import co.gov.gobierno.DTO.LegalAgentDTO;
import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.DTO.ThirdDTO;
import co.gov.gobierno.DTO.WebServiceDTO;

/**
 *
 * @author DELL
 */

public interface WebService
{   
    /**
     * Esta función consume un servicio web de tipo REST con el método GET,
     * usando los atributos del objeto de tipo WebServiceDTO que recibe por parámetro.
     * @param wsd
     * @return 
     */
    public WebServiceDTO getService(WebServiceDTO wsd) ;
    
    /**
     * Esta función consume un servicio web de tipo REST con el método POST,
     * usando los atributos del objeto de tipo WebServiceDTO que recibe por parámetro.
     * @param wsd
     * @return 
     */
    public WebServiceDTO postServiceWithParam(WebServiceDTO wsd) ;
    
    /**
     * Esta función consume un servicio web de tipo SOAP,
     * usando los atributos del objeto de tipo WebServiceDTO que recibe por parámetro.
     * @param wsd
     * @return 
     */
    public WebServiceDTO SoapWithParam(WebServiceDTO wsd) ;
    
    public boolean appendDocumentRadicado(List<PropertieDTO> listPropertieDTOs, List<PropertieDTO> listPHPropertieDTOs, String doc, String radNumber);
    
    public WebServiceDTO CreateThird(String idType, String idNumber, String firstName, String secondName, String lastName, 
            String secondLastName, String birthDate, String gender, String sexo, String sexualOrient, String nationality, String cellphone, String phone,
            String address, String addressCompl, String ruralAddress, String location, String neightborhood, String residenceCity, String occupation,
            String specialCondition, String stratum, String email, String user, String etnia, String cualEtnia, List<PropertieDTO> listPropertieDTOs, String Password);  
    
    public WebServiceDTO RegisterLegalAgent(ThirdDTO objThirdDTO, List<PropertieDTO> listPropertieDTOs, List<PropertieDTO> listPHPropertieDTOs,
            String nit, String idTypeLegalAgent, String idNumberLegalAgent, String firstNameLegalAgent, String lastNameLegalAgent,
            String secondNameLegalAgent, String secondLastNameLegalAgent, String cellphoneLegalAgent, String phoneLegalAgent, String birthDateLegalAgent,
            String addressLegalAgent, String addressComplLegalAgent, String neightborhoodLegalAgent, String emailLegalAgent, String dateAgent, String startAgent, String personType,
            String endAgent, String nameAgentJuridico, String idNumberAgentJuridico, String idTypeAgentJuridico, String razonSocialLegalAgent, String dateActAgent, 
            String numberActAgent, String genderLegalAgent, String typeDireccion, String etnia, String cualEtnia);
    
    public ThirdDTO FindRepresentanteLegal(List<PropertieDTO> listPropertieDTOs, String nit, List<LegalAgentDTO> listLegalAgentDTOs);
}
