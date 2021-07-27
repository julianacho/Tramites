/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class ThirdDTO implements Serializable
{
    private String firstName;
    private String lastName;
    private String idNumber;
    private String secondName;
    private String secondLastName;
    private String birthDate;
    private String gender;
    private String sexo;
    private String sexualOrient;
    private String nationality;
    private String cellphone;
    private String phone;
    private String address;
    private String addressCompl;
    private String ruralAddress;
    private String neightborhood;
    private String residenceCity;
    private String email;
    private String idType;
    private String location;
    private String stratum;
    private String specialCondition;
    private String occupation;
    private String user;
    private String pass;
    private String etnia;
    private String cualEtnia;
    private List<YoungerDTO> listYoungerDTO;
    private List<LegalAgentDTO> listLegalAgentDTOs;

    public ThirdDTO()
    {
         this.listYoungerDTO = new ArrayList<>();
         this.listLegalAgentDTOs = new ArrayList<>();
    }

    public ThirdDTO(String firstName, String lastName, String idNumber, String secondName, String secondLastName,
            String birthDate, String gender, String sexo, String sexualOrient, String nationality, String cellphone, String phone, String address, String addressCompl,
            String ruralAddress, String neightborhood, String residenceCity, String email, String idType,
            String location, String stratum, String specialCondition, String occupation, String user, String pass, String etnia, String cualEtnia) 
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.secondName = secondName;
        this.secondLastName = secondLastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.sexo = sexo;
        this.sexualOrient = sexualOrient;
        this.nationality = nationality;
        this.cellphone = cellphone;
        this.phone = phone;
        this.address = address;
        this.addressCompl = addressCompl;
        this.ruralAddress = ruralAddress;
        this.neightborhood = neightborhood;
        this.residenceCity = residenceCity;
        this.email = email;
        this.idType = idType;
        this.location = location;
        this.stratum = stratum;
        this.specialCondition = specialCondition;
        this.occupation = occupation;
        this.user = user;
        this.pass = pass;
        this.etnia = etnia;
        this.cualEtnia = cualEtnia;
        this.listYoungerDTO = new ArrayList<>();
        this.listLegalAgentDTOs = new ArrayList<>();
    }
    
    
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRuralAddress() {
        return ruralAddress;
    }

    public void setRuralAddress(String ruralAddress) {
        this.ruralAddress = ruralAddress;
    }

    public String getNeightborhood() {
        return neightborhood;
    }

    public void setNeightborhood(String neightborhood) {
        this.neightborhood = neightborhood;
    }

    public String getResidenceCity() {
        return residenceCity;
    }

    public void setResidenceCity(String residenceCity) {
        this.residenceCity = residenceCity;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStratum() {
        return stratum;
    }

    public void setStratum(String stratum) {
        this.stratum = stratum;
    }

    public String getSpecialCondition() {
        return specialCondition;
    }

    public void setSpecialCondition(String specialCondition) {
        this.specialCondition = specialCondition;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public List<YoungerDTO> getListYoungerDTO() {
        return listYoungerDTO;
    }

    public void setListYoungerDTO(List<YoungerDTO> listYoungerDTO) {
        this.listYoungerDTO = listYoungerDTO;
    }

    public List<LegalAgentDTO> getListLegalAgentDTOs() {
        return listLegalAgentDTOs;
    }

    public void setListLegalAgentDTOs(List<LegalAgentDTO> listLegalAgentDTOs) {
        this.listLegalAgentDTOs = listLegalAgentDTOs;
    }

    public String getSexo()
    {
        return sexo;
    }

    public void setSexo(String sexo)
    {
        this.sexo = sexo;
    }

    public String getSexualOrient()
    {
        return sexualOrient;
    }

    public void setSexualOrient(String sexualOrient)
    {
        this.sexualOrient = sexualOrient;
    }

    public String getAddressCompl()
    {
        return addressCompl;
    }

    public void setAddressCompl(String addressCompl)
    {
        this.addressCompl = addressCompl;
    }

    public String getEtnia()
    {
        return etnia;
    }

    public void setEtnia(String etnia)
    {
        this.etnia = etnia;
    }

    public String getCualEtnia()
    {
        return cualEtnia;
    }

    public void setCualEtnia(String cualEtnia)
    {
        this.cualEtnia = cualEtnia;
    }
    
    
    
}
