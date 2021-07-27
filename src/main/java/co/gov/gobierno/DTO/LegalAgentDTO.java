/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.DTO;

import java.io.Serializable;

/**
 *
 * @author DELL
 */
public class LegalAgentDTO implements Serializable {
	private boolean select;
	private String name = "";
	private String representType = "";
	private String beginDate = "";
	private String finishDate = "";
	private String dateActAgent = "";

	private String state = "";

	private String idType = "";
	private String idNumber = "";
	private String address = "";
	private String addressCompl = "";
	private String firstName = "";
	private String lastName = "";
	private String secondName = "";
	private String secondLastName = "";
	private String birthDate = "";
	private String gender = "";
	private String nationality = "";
	private String cellphone = "";
	private String phone = "";
	private String ruralAddress = "";
	private String neightborhood = "";
	private String residenceCity = "";
	private String email = "";
	private String location = "";
	private String stratum = "";
	private String specialCondition = "";
	private String occupation = "";
	private String idTypeRepLegalAgent = "";
	private String descTypeRepLegalAgent = "";
	private String razonSocialLegalAgent = "";
	private String nameAgentJuridico = "";
	private String idTypeAgentJuridico = "";
	private String idNumberAgentJuridico = "";
	private String personType = "";
	private String actNumber = "";
	private String dateActNumber = "";
	private String typeDireccionLegalAgent = "";
	private String etnia = "";
	private String cualEtnia = "";
	private String idStatusLegalAgent = "";

	public String getIdStatusLegalAgent() {
		return idStatusLegalAgent;
	}

	public void setIdStatusLegalAgent(String idStatusLegalAgent) {
		this.idStatusLegalAgent = idStatusLegalAgent;
	}

	public LegalAgentDTO(boolean select, String name, String representType, String beginDate, String finishDate,
			String state, String idType, String idNumber, String address, String firstName, String lastName,
			String secondName, String secondLastName, String birthDate, String gender, String nationality,
			String cellphone, String phone, String addressCompl, String ruralAddress, String neightborhood,
			String residenceCity, String email, String location, String stratum, String specialCondition,
			String occupation, String idTypeRepLegalAgent, String descTypeRepLegalAgent, String razonSocialLegalAgent,
			String nameAgentJuridico, String idTypeAgentJuridico, String idNumberAgentJuridico, String etnia,
			String cualEtnia, String idStatusLegalAgent, String actNumber, String dateActAgent) {
		this.select = select;
		this.name = name;
		this.representType = idTypeRepLegalAgent;
		this.descTypeRepLegalAgent = descTypeRepLegalAgent;
		this.beginDate = beginDate;
		this.finishDate = finishDate;
		this.state = state;
		this.idType = idType;
		this.idNumber = idNumber;
		this.address = address;
		this.addressCompl = addressCompl;
		this.firstName = firstName;
		this.lastName = lastName;
		this.secondName = secondName;
		this.secondLastName = secondLastName;
		this.birthDate = birthDate;
		this.gender = gender;
		this.nationality = nationality;
		this.cellphone = cellphone;
		this.phone = phone;
		this.ruralAddress = ruralAddress;
		this.neightborhood = neightborhood;
		this.residenceCity = residenceCity;
		this.email = email;
		this.location = location;
		this.stratum = stratum;
		this.specialCondition = specialCondition;
		this.occupation = occupation;
		this.idTypeRepLegalAgent = idTypeRepLegalAgent;
		this.razonSocialLegalAgent = razonSocialLegalAgent;
		this.nameAgentJuridico = nameAgentJuridico;
		this.idTypeAgentJuridico = idTypeAgentJuridico;
		this.idNumberAgentJuridico = idNumberAgentJuridico;
		this.etnia = etnia;
		this.cualEtnia = cualEtnia;
		this.idStatusLegalAgent = idStatusLegalAgent;
		this.actNumber = actNumber;
		this.dateActAgent = dateActAgent;
	}

	public LegalAgentDTO() {
	}

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getRepresentType() {
		return representType;
	}

	public void setRepresentType(String representType) {
		this.representType = representType;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIdTypeRepLegalAgent() {
		return idTypeRepLegalAgent;
	}

	public void setIdTypeRepLegalAgent(String idTypeRepLegalAgent) {
		this.idTypeRepLegalAgent = idTypeRepLegalAgent;
	}

	public String getRazonSocialLegalAgent() {
		return razonSocialLegalAgent;
	}

	public void setRazonSocialLegalAgent(String razonSocialLegalAgent) {
		this.razonSocialLegalAgent = razonSocialLegalAgent;
	}

	public String getNameAgentJuridico() {
		return nameAgentJuridico;
	}

	public void setNameAgentJuridico(String nameAgentJuridico) {
		this.nameAgentJuridico = nameAgentJuridico;
	}

	public String getIdTypeAgentJuridico() {
		return idTypeAgentJuridico;
	}

	public void setIdTypeAgentJuridico(String idTypeAgentJuridico) {
		this.idTypeAgentJuridico = idTypeAgentJuridico;
	}

	public String getIdNumberAgentJuridico() {
		return idNumberAgentJuridico;
	}

	public void setIdNumberAgentJuridico(String idNumberAgentJuridico) {
		this.idNumberAgentJuridico = idNumberAgentJuridico;
	}

	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

	public String getActNumber() {
		return actNumber;
	}

	public void setActNumber(String actNumber) {
		this.actNumber = actNumber;
	}

	public String getDateActNumber() {
		return dateActNumber;
	}

	public void setDateActNumber(String dateActNumber) {
		this.dateActNumber = dateActNumber;
	}

	public String getDescTypeRepLegalAgent() {
		return descTypeRepLegalAgent;
	}

	public void setDescTypeRepLegalAgent(String descTypeRepLegalAgent) {
		this.descTypeRepLegalAgent = descTypeRepLegalAgent;
	}

	public String getTypeDireccionLegalAgent() {
		return typeDireccionLegalAgent;
	}

	public void setTypeDireccionLegalAgent(String typeDireccionLegalAgent) {
		this.typeDireccionLegalAgent = typeDireccionLegalAgent;
	}

	public String getAddressCompl() {
		return addressCompl;
	}

	public void setAddressCompl(String addressCompl) {
		this.addressCompl = addressCompl;
	}

	public String getEtnia() {
		return etnia;
	}

	public void setEtnia(String etnia) {
		this.etnia = etnia;
	}

	public String getCualEtnia() {
		return cualEtnia;
	}

	public void setCualEtnia(String cualEtnia) {
		this.cualEtnia = cualEtnia;
	}
	
	public String getDateActAgent() {
		return dateActAgent;
	}

	public void setDateActAgent(String dateActAgent) {
		this.dateActAgent = dateActAgent;
	}

}
