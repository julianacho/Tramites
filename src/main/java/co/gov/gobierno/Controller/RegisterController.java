/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.Controller;

import co.gov.gobierno.DTO.AyudaDTO;
import co.gov.gobierno.DTO.LocationDTO;
import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.DTO.WebServiceDTO;
import co.gov.gobierno.Service.RegisterService;
import co.gov.gobierno.Service.WebService;
import co.gov.gobierno.Util.EmailHandler;
import co.gov.gobierno.Util.JsonHandler;
import co.gov.gobierno.Util.PropertiesHandler;
import co.gov.gobierno.Util.ValidUtility;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.slf4j.LoggerFactory;

/**
 *
 * @author DELL
 */
@ManagedBean
@RequestScoped
public class RegisterController implements Serializable
{
    private static long serialVersionUID = 1L;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(RegisterController.class);
    private List<PropertieDTO> listPropertieDTOs;
    private List<AyudaDTO> listAyudaDTO;
    
    private boolean alertMessage = false;
    private boolean registerSuccess = false;
    private String result;
    
    private String firstName ="";
    private String lastName="";
    private String idNumber="";
    private String secondName="";
    private String secondLastName="";
    private String birthDate;
    private String gender="";
    private String sexo = "";
    private String sexualOrient = "";
    private String nationality="";
    private String cellphone="";
    private String phone="";
    private String address="";
    private String addressCompl="";
    private String ruralAddress="";
    private String neightborhood="";
    private String residenceCity="";
    private String email="";
    private String idType="";
    private String location="";
    private String stratum="";
    private String specialCondition="";
    private String mainStreet="";
    private String occupation="";
    private String numberMainStreet="";
    private String firstNumberAddress="";
    private String secondNumberAddress="";
    private String firstAddressLetter="";
    private String secondAddressLetter="";
    private String thirdAddressLetter="";
    private String fourthAddressLetter="";
    private boolean bisAddress = false;
    private boolean secondBisAddress = false;
    private String firstStreetGeneratingPath="";
    private String SecondStreetGeneratingPath="";
    private String firstAddressComplement="";
    private String secondAddressComplement="";
    private String thirdAddressComplement="";
    private String firstNumberComplement="";
    private String secondNumberComplement="";
    private String thirdNumberComplement="";
    private String user="";
    private String pass="";
    private String passConfirmation="";
    private String typeDireccion = "1";
    private boolean thermsAndConditions = false;
    private String etnia;
    private String cualEtnia = "";
    
    
    
    private Map<String, String> valuesIdType;
    private List<LocationDTO> valuesLocation;
    private Map<String, String> valuesStratum;
    private Map<String, String> valuesSpecialCondition;
    private Map<String, String> valuesMainStreet;
    private Map<String, String> valuesStreetGeneratingPath;
    private Map<String, String> valuesOccupation;
    private Map<String, String> valuesGender;
    private List<LocationDTO> valuesNeightborhood;
    private List<LocationDTO> valuesResidenceCity;
    private Map<String, String> valuesNationality;
    private Map<String, String> valuesSexualOrient;
    private Map<String, String> valuesSex;
    private Map<String,String> MapAyudaDB;
    private Map<String,String> valuesEtnia;
    
    private String UrlVideoRegistro;
    
    @ManagedProperty(value="#{loginController}")
    private LoginController loginController;
    
    @ManagedProperty(value="#{indexController}")
    private IndexController indexController;
    
    @EJB
    WebService ws;
    @EJB
    RegisterService rs;
    
    public String Register()
    {
        try
        {
            if((this.pass.equals(this.passConfirmation)))
            {
            	//Validar Tipo Identificacion
            	if(!ValidUtility.validateIdentificacion(idNumber, idType)) {
            		setAlertMessage(true);
                    setRegisterSuccess(false);
                    setResult("El campo número de Idenfificacion contiene letras y no esta permitido para el tipo de documento seleccionado.");
                    return null;
            		
            	}
            	
                WebServiceDTO objWebServiceDTO = new WebServiceDTO();
                WebServiceDTO objWebServiceDTO2 = new WebServiceDTO();

                
                if (this.birthDate.isEmpty() || this.birthDate == null)
                {
                	setAlertMessage(true);
                    setRegisterSuccess(false);
                    setResult("Faltan datos obligatorios (Fecha Nacimiento).");
                    return null;
                }
                
                //objWebServiceDTO = rs.CreateUser(this.firstName, this.lastName, this.idNumber, this.pass, this.listPropertieDTOs);
                objWebServiceDTO.setRespuesta(true);
                if(objWebServiceDTO.isRespuesta())
                {
                    objWebServiceDTO2 = ws.CreateThird(this.idType, this.idNumber, this.firstName, this.secondName, 
                        this.lastName, this.secondLastName, this.birthDate, this.gender, this.sexo, this.sexualOrient, this.nationality, this.cellphone, 
                        this.phone, this.address, this.addressCompl, this.ruralAddress, this.location, this.neightborhood, this.residenceCity, 
                        this.occupation, this.specialCondition, this.stratum, this.email, this.idNumber, this.etnia, this.cualEtnia, this.listPropertieDTOs, this.pass);
                    if(objWebServiceDTO2.isRespuesta())
                    {
                        String emailResult = EmailHandler.EnviarMensaje(this.email, "Registro Exitoso","<html><meta charset=\"UTF-8\"/><div style='text-align:left;'>Estimado (a) ciudadano (a) :<br>" +
                                                "<br>" +
                                                "Le informamos que se cre&oacute; satisfactoriamente el usuario " + this.idNumber + " en la aplicaci&oacute;n de Tr&aacute;mites y Servicios de la Secretar&iacute;a Distrital de Gobierno.<br>" +
                                                //"Le informamos que se cre&oacute; satisfactoriamente el usuario " + this.idNumber /*+ " con la contrase&ntilde;a " + this.pass */+ " en la aplicaci&oacute;n de Tr&aacute;mites y Servicios de la Secretar&iacute;a Distrital de Gobierno.<br>" +
                                                "<br><br><br><p style='font-size:10px'>\"Este correo es de tipo informativo; por favor no responda a este mensaje. Para contactarnos puede hacerlo a trav&eacute;s de la p&aacute;gina de la Secretar&iacute;a Distrital de Gobierno:<br> <a href='http://www.gobiernobogota.gov.co' target='_blank'>http://www.gobiernobogota.gov.co</a>\"</p><br></div></html>", this.listPropertieDTOs);
                        log.info(emailResult + "estoy en el managed bean registerController");
                        if(!emailResult.contains("Error"))
                        {
                            loginController.setLoginMessage(true);
                            loginController.setAlertStyle(true);
                            //loginController.setResult("Su registro fue exitoso. El usuario y contraseña fueron enviados al correo: " + this.getEmail());
                            loginController.setResult("Su registro fue exitoso. El usuario fue enviado al correo: " + this.getEmail());
                            return "success";
                        }
                        else
                        {
                            setAlertMessage(true);
                            setRegisterSuccess(false);
                            setResult("Error al enviar el email.");
                        }
                    }
                    else
                    {
                        setAlertMessage(true);
                        setRegisterSuccess(false);
                        setResult("Error al crear la persona.");
                    }
                }
                else
                {
                    if(objWebServiceDTO.getToken().contains("registrado"))
                    {
                        setAlertMessage(true);
                        setRegisterSuccess(false);
                        setResult("Este nombre de usuario ya se encuentra registrado en el sistema por favor intente con un nombre de usuario diferente.");
                    }
                    else
                    {
                        setAlertMessage(true);
                        setRegisterSuccess(false);
                        setResult("Error al crear el usuario");
                    }
                }
            }
            else
            {
                setAlertMessage(true);
                setResult("La validacion de la contraseña es incorrecta.");
            }
            return null;
        }
        catch(Exception e)
        {
            setAlertMessage(true);
            setRegisterSuccess(false);
            setResult("Ha ocurrido un error en el sistema por favor comuníquese con el administrador del sistema.");
            log.info("Error en pagina de registro: " + e.toString());
            return null;
        }
    }
    
    public String Back()
    {
        return "success";
    }
    
    public void FillIdTypeValues()
    {
        indexController.FillIdTypeValues();
        setValuesIdType(indexController.getValuesIdType());
        valuesIdType.remove("TARJETA DE IDENTIDAD");
        valuesIdType.remove("REGISTRO CIVIL DE NACIMIENTO");
        valuesIdType.remove("REGISTRO CIVIL DE NACIMIENTO NUIP");
        valuesIdType.remove("NIT");
        valuesIdType.remove("VISA");
        valuesIdType.remove("MATRICULA");
        valuesIdType.remove("NUMERO UNICO DE IDENTIFICACION PERSONAL");
        valuesIdType.remove("NUMERO UNICO DE IDENTIFICACION PERSONAL ");
        valuesIdType.remove("TARJETA DE EXTRANJERIA");
         
    }
    
   
    
    @PostConstruct
    public void init() 
    {
        try 
        {
            this.listPropertieDTOs = PropertiesHandler.GetPropertiesListFromDB();
            this.listAyudaDTO = PropertiesHandler.GetAyudaDB();
            this.registerSuccess = false;
            this.alertMessage = false;
            FillResidenceCityValues();
            FillIdTypeValues();
            FillLocationValues();
            FillStratumValues();
            FillSpecialConditionValues();
            FillMainStreetValues();
            FillStreetGeneratingPathValues();
            FillOccupationValues();
            FillGenderValues();
            FillNationalityValues();
            FillNeightborhoodValues();
            FillSexValues();
            FillSexualOrientValues();
            FillValuesEtnias();
            this.MapAyudaDB = PropertiesHandler.AyudaDB(listAyudaDTO, "Registro");
            if(MapAyudaDB.get("vTipo").equals("1"))
            {
                          this.setUrlVideoRegistro("<iframe width='600' height='315'  src='"+MapAyudaDB.get("vUrl")+"' frameborder='0' allow='accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture' allowfullscreen></iframe>");
                          
            }                                     
            if(MapAyudaDB.get("vTipo").equals("2"))
            {
                          this.setUrlVideoRegistro("<img width='600' height='315' src='"+MapAyudaDB.get("vUrl")+"'/>");
            }
        


  
        }
        catch (Exception e) 
        {
            setAlertMessage(true);
            setRegisterSuccess(false);
            setResult("Ha ocurrido un error en el sistema por favor comuníquese con el administrador del sistema.");
            log.info("Error en pagina de registro: " + e.toString());
        }
    }
    
    public void FillValuesEtnias()
    {
        setValuesEtnia(indexController.getValuesEtnias());
    }

     public void FillLocationValues()
    {
        setValuesLocation(indexController.getValuesLocation());
    }
    
    public void FillStratumValues()
    {
        setValuesStratum(indexController.getValuesStratum());
    }
    
    public void FillSpecialConditionValues()
    {
        setValuesSpecialCondition(indexController.getValuesSpecialCondition());
    }
    
    public void FillMainStreetValues()
    {
        setValuesMainStreet(indexController.getValuesMainStreet());
    }
    
    public void FillStreetGeneratingPathValues()
    {
        setValuesStreetGeneratingPath(indexController.getValuesStreetGeneratingPath());
    }
    
    public void FillOccupationValues()
    {
        setValuesOccupation(indexController.getValuesOccupation());
    }
    
    public void FillNationalityValues()
    {
        setValuesNationality(indexController.getValuesNationality());
    }
    
    public void FillGenderValues()
    {
        setValuesGender(indexController.getValuesGender());
    }
    
    public void FillSexValues()
    {
        setValuesSex(indexController.getValuesSex());
    }
        
    public void FillSexualOrientValues()
    {
        setValuesSexualOrient(indexController.getValuesSexualOrient());
    }
    
    public void FillNeightborhoodValues()
    {
        setValuesNeightborhood(indexController.getValuesNeightborhood());
    }
    
    public void FillResidenceCityValues()
    {
        setValuesResidenceCity(indexController.getValuesResidenceCity());
    }
    
    public void setLoginController(LoginController loginController)
    {
        this.loginController = loginController;
    }
    
    public boolean isAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(boolean alertMessage) {
        this.alertMessage = alertMessage;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    
    public boolean isSecondBisAddress() {
        return secondBisAddress;
    }

    public void setSecondBisAddress(boolean secondBisAddress) {
        this.secondBisAddress = secondBisAddress;
    }

    public String getThirdAddressLetter() {
        return thirdAddressLetter;
    }

    public void setThirdAddressLetter(String thirdAddressLetter) {
        this.thirdAddressLetter = thirdAddressLetter;
    }

    public String getFourthAddressLetter() {
        return fourthAddressLetter;
    }

    public void setFourthAddressLetter(String fourthAddressLetter) {
        this.fourthAddressLetter = fourthAddressLetter;
    }

    public String getNumberMainStreet() {
        return numberMainStreet;
    }

    public void setNumberMainStreet(String numberMainStreet) {
        this.numberMainStreet = numberMainStreet;
    }

    public String getFirstNumberAddress() {
        return firstNumberAddress;
    }

    public void setFirstNumberAddress(String firstNumberAddress) {
        this.firstNumberAddress = firstNumberAddress;
    }

    public String getSecondNumberAddress() {
        return secondNumberAddress;
    }

    public void setSecondNumberAddress(String secondNumberAddress) {
        this.secondNumberAddress = secondNumberAddress;
    }

    public String getFirstAddressLetter() {
        return firstAddressLetter;
    }

    public void setFirstAddressLetter(String firstAddressLetter) {
        this.firstAddressLetter = firstAddressLetter;
    }

    public String getSecondAddressLetter() {
        return secondAddressLetter;
    }

    public void setSecondAddressLetter(String secondAddressLetter) {
        this.secondAddressLetter = secondAddressLetter;
    }

    public boolean isBisAddress() {
        return bisAddress;
    }

    public void setBisAddress(boolean bisAddress) {
        this.bisAddress = bisAddress;
    }

    public String getSecondStreetGeneratingPath() {
        return SecondStreetGeneratingPath;
    }

    public void setSecondStreetGeneratingPath(String SecondStreetGeneratingPath) {
        this.SecondStreetGeneratingPath = SecondStreetGeneratingPath;
    }

    public String getFirstAddressComplement() {
        return firstAddressComplement;
    }

    public void setFirstAddressComplement(String firstAddressComplement) {
        this.firstAddressComplement = firstAddressComplement;
    }

    public String getSecondAddressComplement() {
        return secondAddressComplement;
    }

    public void setSecondAddressComplement(String secondAddressComplement) {
        this.secondAddressComplement = secondAddressComplement;
    }

    public String getThirdAddressComplement() {
        return thirdAddressComplement;
    }

    public void setThirdAddressComplement(String thirdAddressComplement) {
        this.thirdAddressComplement = thirdAddressComplement;
    }

    public String getFirstNumberComplement() {
        return firstNumberComplement;
    }

    public void setFirstNumberComplement(String firstNumberComplement) {
        this.firstNumberComplement = firstNumberComplement;
    }

    public String getSecondNumberComplement() {
        return secondNumberComplement;
    }

    public void setSecondNumberComplement(String secondNumberComplement) {
        this.secondNumberComplement = secondNumberComplement;
    }

    public String getThirdNumberComplement() {
        return thirdNumberComplement;
    }

    public void setThirdNumberComplement(String thirdNumberComplement) {
        this.thirdNumberComplement = thirdNumberComplement;
    }

    public String getPassConfirmation() {
        return passConfirmation;
    }

    public void setPassConfirmation(String passConfirmation) {
        this.passConfirmation = passConfirmation;
    }

    public boolean isThermsAndConditions() {
        return thermsAndConditions;
    }

    public void setThermsAndConditions(boolean thermsAndConditions) {
        this.thermsAndConditions = thermsAndConditions;
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

    public boolean isRegisterSuccess() {
        return registerSuccess;
    }

    public void setRegisterSuccess(boolean registerSuccess) {
        this.registerSuccess = registerSuccess;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public Map<String, String> getValuesIdType() {
        return valuesIdType;
    }

    public void setValuesIdType(Map<String, String> valuesIdType) {
        this.valuesIdType = valuesIdType;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStratum(String stratum) {
        this.stratum = stratum;
    }

    public Map<String, String> getValuesStratum() {
        return valuesStratum;
    }

    public void setValuesStratum(Map<String, String> valuesStratum) {
        this.valuesStratum = valuesStratum;
    }
    public String getSpecialCondition() {
        return specialCondition;
    }

    public void setSpecialCondition(String specialCondition) {
        this.specialCondition = specialCondition;
    }

    public Map<String, String> getValuesSpecialCondition() {
        return valuesSpecialCondition;
    }

    public void setValuesSpecialCondition(Map<String, String> valuesSpecialCondition) {
        this.valuesSpecialCondition = valuesSpecialCondition;
    }
    public String getMainStreet() {
        return mainStreet;
    }

    public void setMainStreet(String mainStreet) {
        this.mainStreet = mainStreet;
    }

    public String getFirstStreetGeneratingPath() {
        return firstStreetGeneratingPath;
    }

    public void setFirstStreetGeneratingPath(String firstStreetGeneratingPath) {
        this.firstStreetGeneratingPath = firstStreetGeneratingPath;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Map<String, String> getValuesMainStreet() {
        return valuesMainStreet;
    }

    public void setValuesMainStreet(Map<String, String> valuesMainStreet) {
        this.valuesMainStreet = valuesMainStreet;
    }

    public Map<String, String> getValuesStreetGeneratingPath() {
        return valuesStreetGeneratingPath;
    }

    public void setValuesStreetGeneratingPath(Map<String, String> valuesStreetGeneratingPath) {
        this.valuesStreetGeneratingPath = valuesStreetGeneratingPath;
    }

    public Map<String, String> getValuesOccupation() {
        return valuesOccupation;
    }

    public void setValuesOccupation(Map<String, String> valuesOccupation) {
        this.valuesOccupation = valuesOccupation;
    }
    
    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Map<String, String> getValuesGender() {
        return valuesGender;
    }

    public void setValuesGender(Map<String, String> valuesGender) {
        this.valuesGender = valuesGender;
    }

    public Map<String, String> getValuesNationality() {
        return valuesNationality;
    }

    public void setValuesNationality(Map<String, String> valuesNationality) {
        this.valuesNationality = valuesNationality;
    }

    public List<PropertieDTO> getListPropertieDTOs()
    {
        return listPropertieDTOs;
    }

    public void setListPropertieDTOs(List<PropertieDTO> listPropertieDTOs)
    {
        this.listPropertieDTOs = listPropertieDTOs;
    }

    public String getTypeDireccion()
    {
        return typeDireccion;
    }

    public void setTypeDireccion(String typeDireccion)
    {
        this.typeDireccion = typeDireccion;
    }

    public Map<String, String> getValuesSexualOrient()
    {
        return valuesSexualOrient;
    }

    public void setValuesSexualOrient(Map<String, String> valuesSexualOrient)
    {
        this.valuesSexualOrient = valuesSexualOrient;
    }

    public Map<String, String> getValuesSex()
    {
        return valuesSex;
    }

    public void setValuesSex(Map<String, String> valuesSex)
    {
        this.valuesSex = valuesSex;
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

    public void setIndexController(IndexController indexController)
    {
        this.indexController = indexController;
    }

    public List<LocationDTO> getValuesLocation()
    {
        return valuesLocation;
    }

    public void setValuesLocation(List<LocationDTO> valuesLocation)
    {
        this.valuesLocation = valuesLocation;
    }

    public List<LocationDTO> getValuesNeightborhood()
    {
        return valuesNeightborhood;
    }

    public void setValuesNeightborhood(List<LocationDTO> valuesNeightborhood)
    {
        this.valuesNeightborhood = valuesNeightborhood;
    }

    public List<LocationDTO> getValuesResidenceCity()
    {
        return valuesResidenceCity;
    }

    public void setValuesResidenceCity(List<LocationDTO> valuesResidenceCity)
    {
        this.valuesResidenceCity = valuesResidenceCity;
    }

    public String getStratum()
    {
        return stratum;
    }
    
     public String getUrlVideoRegistro()
    {
        return UrlVideoRegistro;
    }

    public void setUrlVideoRegistro(String UrlVideoRegistro)
    {
        this.UrlVideoRegistro = UrlVideoRegistro;
    }

    public List<AyudaDTO> getListAyudaDTO()
    {
        return listAyudaDTO;
    }

    public void setListAyudaDTO(List<AyudaDTO> listAyudaDTO)
    {
        this.listAyudaDTO = listAyudaDTO;
    }

    public Map<String, String> getMapAyudaDB()
    {
        return MapAyudaDB;
    }

    public void setMapAyudaDB(Map<String, String> MapAyudaDB)
    {
        this.MapAyudaDB = MapAyudaDB;
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

    public Map<String, String> getValuesEtnia()
    {
        return valuesEtnia;
    }

    public void setValuesEtnia(Map<String, String> valuesEtnia)
    {
        this.valuesEtnia = valuesEtnia;
    }
    
    
    
    
}
