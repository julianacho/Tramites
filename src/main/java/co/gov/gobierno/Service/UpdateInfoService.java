/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.Service;

import java.util.List;

import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.DTO.WebServiceDTO;

/**
 *
 * @author DELL
 */

public interface UpdateInfoService 
{
    /*
     * Crea un tercero con los datos que recibe por parámetro. 
     */
    public WebServiceDTO CreateThird(String idType, String idNumber, String firstName, String secondName, String lastName, 
            String secondLastName, String birthDate, String gender, String sexo, String sexualOrient, String nationality, String cellphone, String phone,
            String address, String ruralAddress, String location, String neightborhood, String residenceCity, String occupation,
            String specialCondition, String stratum, String email, String user, List<PropertieDTO> listpPropertieDTOs);
}
