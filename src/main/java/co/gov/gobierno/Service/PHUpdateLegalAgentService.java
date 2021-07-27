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

public interface PHUpdateLegalAgentService
{

    public WebServiceDTO updateLegalAgentPH(ThirdDTO objThirdDTO, List<PropertieDTO> listPropertieDTOs, List<PropertieDTO> listPHPropertieDTOs, String expNumber,
            String radNumber, String nitPropiedad,String personType, String idTypeLegalAgent, String idNumberLegalAgent,
            String firstNameLegalAgent, String lastNameLegalAgent, String secondNameLegalAgent,
            String secondLastNameLegalAgent, String phoneLegalAgent, String addressLegalAgent, String emailLegalAgent,
            String locationLegalAgent, String idGenreLegalAgent, String idStatusLegalAgent, 
            String startAgent, String endAgent, String numberActAgent, String dateActAgent, String nameAgentJuridico,
            String idNumberAgentJuridico, String idTypeAgentJuridico, String idTypeRepLegalAgent, String razonSocialLegalAgent,
            String typeDireccionLegalAgent);

    
}
