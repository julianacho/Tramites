/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.Controller;

import co.gov.gobierno.DTO.AyudaDTO;
import co.gov.gobierno.DTO.LocationDTO;
import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.DTO.ThirdDTO;
import co.gov.gobierno.DTO.WebServiceDTO;
import co.gov.gobierno.Service.RegisterService;
import co.gov.gobierno.Service.UpdateInfoService;
import co.gov.gobierno.Service.WebService;
import co.gov.gobierno.Util.JsonHandler;
import co.gov.gobierno.Util.PropertiesHandler;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class UpdateInfoController implements Serializable
{
    private static final long serialVersionUID = 1L;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(UpdateInfoController.class);
    private List<PropertieDTO> listPropertieDTOs;
    private ThirdDTO objThirdDTO;
    
    private boolean alertShow=false;
    private boolean alertStyle=false;
    private String alertMessage="";
    
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
    private String typeDireccion = "";
    private boolean thermsAndConditions = false;
    private String etnia;
    private String cualEtnia;
    
    private List<AyudaDTO> listAyudaDTO;
    private Map<String,String> MapAyudaDB;
    private String UrlVideoRegistro;
    
    
    private List<LocationDTO> valuesNeightborhood;
    private List<LocationDTO> valuesResidenceCity;
    private List<LocationDTO> valuesLocation;
    private Map<String, String> valuesIdType;
    private Map<String, String> valuesStratum;
    private Map<String, String> valuesSpecialCondition;
    private Map<String, String> valuesMainStreet;
    private Map<String, String> valuesStreetGeneratingPath;
    private Map<String, String> valuesOccupation;
    private Map<String, String> valuesGender;
    private Map<String, String> valuesNationality;
    private Map<String, String> valuesSexualOrient;
    private Map<String, String> valuesSex;
    private Map<String, String> valuesEtnia;
    
    @ManagedProperty(value="#{indexController}")
    private IndexController indexController;
    
    @ManagedProperty(value="#{loginController}")
    private LoginController loginController;
    
    @EJB
    WebService ws;
    @EJB
    UpdateInfoService uis;
    
    public String UpdateInfo()
    {
        if (!etnia.equals("8"))
        {
            this.cualEtnia = "";
        }
        try {
            WebServiceDTO objWebServiceDTO = new WebServiceDTO();

            objWebServiceDTO = ws.CreateThird(this.idType, this.idNumber, this.firstName, this.secondName,
                    this.lastName, this.secondLastName, this.birthDate, this.gender, this.sexo, this.sexualOrient, this.nationality, this.cellphone,
                    this.phone, this.address, this.addressCompl, this.ruralAddress, this.location, this.neightborhood, this.residenceCity,
                    this.occupation, this.specialCondition, this.stratum, this.email, this.objThirdDTO.getUser(), this.etnia, this.cualEtnia, this.listPropertieDTOs, this.objThirdDTO.getPass());
            if(objWebServiceDTO.isRespuesta())
            {
                loginController.setLoginMessage(true);
                loginController.setAlertStyle(true);
                loginController.setResult("Se han actualizado sus datos correctamente. Por favor ingrese nuevamente.");
                return "success";
            }
            else
            {
                setAlertShow(true);
                setAlertStyle(false);
                setAlertMessage("Error al actualizar los datos.");
            }
        } 
        catch (Exception ex) 
        {
            setAlertShow(true);
            setAlertStyle(false);
            setAlertMessage("Ha ocurrido un error en el sistema, contacte al administrador del sistema.");
            log.info("Error UpdateInfoController: " + ex.toString());
        }
        return null;
    }
    
    @PostConstruct
    public void Init() 
    {
        try 
        {
            this.objThirdDTO = indexController.getObjThirdDTO();
            this.listPropertieDTOs = indexController.getListPropertieDTOs();
            
            this.setIdType(this.objThirdDTO.getIdType());
            this.setIdNumber(this.objThirdDTO.getIdNumber());
            this.setFirstName(this.objThirdDTO.getFirstName());
            this.setSecondName(this.objThirdDTO.getSecondName());
            this.setLastName(this.objThirdDTO.getLastName());
            this.setSecondLastName(this.objThirdDTO.getSecondLastName());
            this.setBirthDate(this.objThirdDTO.getBirthDate());
            this.setGender(this.objThirdDTO.getGender());
            this.setSexo(this.objThirdDTO.getSexo());
            this.setSexualOrient(this.objThirdDTO.getSexualOrient());
            this.setNationality(this.objThirdDTO.getNationality());
            this.setCellphone(this.objThirdDTO.getCellphone());
            this.setPhone(this.objThirdDTO.getPhone());
            this.setAddress(this.objThirdDTO.getAddress());
            this.setAddressCompl(this.objThirdDTO.getAddressCompl());
            this.setRuralAddress(this.objThirdDTO.getRuralAddress());
            this.setLocation(this.objThirdDTO.getLocation());
            this.setNeightborhood(this.objThirdDTO.getNeightborhood());
            this.setResidenceCity(this.objThirdDTO.getResidenceCity());
            this.setOccupation(this.objThirdDTO.getOccupation());
            this.setSpecialCondition(this.objThirdDTO.getSpecialCondition());
            this.setStratum(this.objThirdDTO.getStratum());
            this.setEmail(this.objThirdDTO.getEmail());
            this.setEtnia(this.objThirdDTO.getEtnia());
            this.setCualEtnia(this.objThirdDTO.getCualEtnia());
            if (this.objThirdDTO.getAddress().equals(""))
            {
                this.setTypeDireccion("2");
                this.setAddress("");
            } else {
                this.setTypeDireccion("1");
                this.setRuralAddress("");
            }
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
            FillResidenceCityValues();
            FillSexValues();
            FillSexualOrientValues();
            FillValuesEtnias();
            
            this.listAyudaDTO = PropertiesHandler.GetAyudaDB();
            this.MapAyudaDB = PropertiesHandler.AyudaDB(listAyudaDTO, "ActualizarDatos");
            if(MapAyudaDB.get("vTipo").equals("1"))
            {
                           this.setUrlVideoRegistro("<iframe width='600' height='315'  src='"+MapAyudaDB.get("vUrl")+"' frameborder='0' allow='accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture' allowfullscreen></iframe>");
            }
            if(MapAyudaDB.get("vTipo").equals("2"))
            {
                          this.setUrlVideoRegistro("<img width='600' height='315' src='"+MapAyudaDB.get("vUrl")+"'/>");
            }
        }
        catch (Exception ex) 
        {
            setAlertShow(true);
            setAlertStyle(false);
            setAlertMessage("Ha ocurrido un error en el sistema, contacte al administrador del sistema.");
            log.info("Error UpdateInfoController: " + ex.toString());
            //Logger.getLogger(UpdateInfoController.class.getName()).log(Level.SEVERE, ex.toString(), ex);
        }
    }
    
    public void FillIdTypeValues()
    {
        indexController.FillIdTypeValues();
        setValuesIdType(indexController.getValuesIdType());
        valuesIdType.remove("TARJETA DE IDENTIDAD");
        valuesIdType.remove("REGISTRO CIVIL DE NACIMIENTO");
        valuesIdType.remove("NIT");
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

    public String getMainStreet() {
        return mainStreet;
    }

    public void setMainStreet(String mainStreet) {
        this.mainStreet = mainStreet;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
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

    public boolean isBisAddress() {
        return bisAddress;
    }

    public void setBisAddress(boolean bisAddress) {
        this.bisAddress = bisAddress;
    }

    public boolean isSecondBisAddress() {
        return secondBisAddress;
    }

    public void setSecondBisAddress(boolean secondBisAddress) {
        this.secondBisAddress = secondBisAddress;
    }

    public String getFirstStreetGeneratingPath() {
        return firstStreetGeneratingPath;
    }

    public void setFirstStreetGeneratingPath(String firstStreetGeneratingPath) {
        this.firstStreetGeneratingPath = firstStreetGeneratingPath;
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

    public boolean isThermsAndConditions() {
        return thermsAndConditions;
    }

    public void setThermsAndConditions(boolean thermsAndConditions) {
        this.thermsAndConditions = thermsAndConditions;
    }

    public Map<String, String> getValuesIdType() {
        return valuesIdType;
    }

    public void setValuesIdType(Map<String, String> valuesIdType) {
        this.valuesIdType = valuesIdType;
    }


    public Map<String, String> getValuesStratum() {
        return valuesStratum;
    }

    public void setValuesStratum(Map<String, String> valuesStratum) {
        this.valuesStratum = valuesStratum;
    }

    public Map<String, String> getValuesSpecialCondition() {
        return valuesSpecialCondition;
    }

    public void setValuesSpecialCondition(Map<String, String> valuesSpecialCondition) {
        this.valuesSpecialCondition = valuesSpecialCondition;
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

    public ThirdDTO getObjThirdDTO() {
        return objThirdDTO;
    }

    public void setObjThirdDTO(ThirdDTO objThirdDTO) {
        this.objThirdDTO = objThirdDTO;
    }

    public boolean isAlertShow() {
        return alertShow;
    }

    public void setAlertShow(boolean alertShow) {
        this.alertShow = alertShow;
    }

    public boolean isAlertStyle() {
        return alertStyle;
    }

    public void setAlertStyle(boolean alertStyle) {
        this.alertStyle = alertStyle;
    }

    public void setIndexController(IndexController indexController) {
        this.indexController = indexController;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
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

    public List<LocationDTO> getValuesLocation()
    {
        return valuesLocation;
    }

    public void setValuesLocation(List<LocationDTO> valuesLocation)
    {
        this.valuesLocation = valuesLocation;
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
