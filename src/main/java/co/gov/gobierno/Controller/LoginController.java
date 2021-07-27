/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.Controller;

import co.gov.gobierno.DTO.LogAuditoriaDTO;
import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.DTO.ThirdDTO;
import co.gov.gobierno.DTO.WebServiceDTO;
import co.gov.gobierno.Service.LogAuditoriaService;
import co.gov.gobierno.Service.LoginService;
import co.gov.gobierno.Service.TerceroService;
import co.gov.gobierno.Service.WebService;
import co.gov.gobierno.ServiceImpl.LogAuditoriaServiceImpl;
import co.gov.gobierno.Util.EmailHandler;
import co.gov.gobierno.Util.EncryptHandler;
import co.gov.gobierno.Util.JsonHandler;
import co.gov.gobierno.Util.PropertiesHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.List;
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
public class LoginController implements Serializable
{
    //private static long serialVersionUID = 1L;
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(LoginController.class);
    
    
    private boolean loginMessage = false;
    private boolean alertStyle = false;
    private String result;
    
    private List<PropertieDTO> listPropertieDTOs;
    private List<PropertieDTO> listPHPropertieDTOs;
    
    private String user;
    private String pass;
    private String urlAyuda;
    private String UrlImgCR;
    private String UrlImgPH;
    
    
    private String email;
    
    @ManagedProperty(value="#{indexController}")
    private IndexController indexController;
    
    @EJB
    LoginService ls;
    
    @EJB
    WebService ws;
    
    @EJB
    LogAuditoriaService logService;
    
    @EJB
    TerceroService ts;
    
    private LogAuditoriaDTO logAuditoriaDTO = null;
    
    public String Authenticate()
    {
        try
        {
        	//logger.er("debugging");
            WebServiceDTO objWebServiceDTO = new WebServiceDTO();
            String token = ts.Login(this.user, this.pass);
            objWebServiceDTO = ls.Authenticate(this.user, this.pass, this.listPropertieDTOs);
            if (token == null) {
            	objWebServiceDTO.setRespuesta(false);
            }
            if(null != objWebServiceDTO)
            {
                //setResult(objWebServiceDTO.getToken());
                setResult(token);
                if (objWebServiceDTO.isRespuesta())
                {
                    indexController.setObjThirdDTO(new ThirdDTO());
                    indexController.getObjThirdDTO().setUser(this.user);
                    indexController.getObjThirdDTO().setPass(this.pass);
                    indexController.setAuthenticationToken(objWebServiceDTO.getToken());
                    indexController.setListPropertieDTOs(this.listPropertieDTOs);
                    indexController.setListPHPropertieDTOs(this.listPHPropertieDTOs);
                    String aux = indexController.ConsultThird();
                    if (!"error".equals(aux) && aux!=null) 
                    {
                        indexController.FillResidenceCityValues();
                        indexController.FillIdTypeValues();
                        indexController.FillLocationValues();
                        indexController.FillStratumValues();
                        indexController.FillSpecialConditionValues();
                        indexController.FillMainStreetValues();
                        indexController.FillStreetGeneratingPathValues();
                        indexController.FillOccupationValues();
                        indexController.FillGenderValues();
                        indexController.FillNationalityValues();
                        indexController.FillNeightborhoodValues();
                        indexController.FillSexValues();
                        indexController.FillSexualOrientValues();
                        indexController.FillPrivateUnitsValues();
                        indexController.FillCommonAreasValues();
                        indexController.FillRegimenValues();
                        indexController.FillPresentationTypeValues();
                        indexController.FillAcademicFormationValues();
                        indexController.FillStatusLegalAgent();
                        indexController.FillTypeProperty();
                        indexController.FillTypeRepLegalAgent();
                        indexController.FillRelatedValues();
                        indexController.FillProcedureTypeValues();
                        indexController.FillEtniasValues();
                        return aux;
                    }
                    else 
                    {
                        setLoginMessage(true);
                        setAlertStyle(false);
                        setResult("Error al consultar la información de la persona.");
                        return null;
                    }
                }
                else 
                {
                    setLoginMessage(true);
                    setAlertStyle(false);
                    setResult("El No. De identificación o contraseña no son correctos o el usuario no está registrado.");
                    return null;
                }
            }
            else
            {
                setLoginMessage(true);
                setAlertStyle(false);
                setResult("El No. De identificación o contraseña no son correctos o el usuario no está registrado.");
                return null;
            }
        }
        catch(Exception e)
        {
            setLoginMessage(true);
            setAlertStyle(false);
            setResult("Ha ocurrido un error en el sistema por favor comuníquese con el administrador del sistema.");
            log.error("Error: Ha ocurrido un error en el sistema por favor comuníquese con el administrador del sistema. " + e.toString());
            this.logAuditoriaDTO.setAuMensajeError("Authenticate " + e.toString());
            logService.AddLogAuditoria(logAuditoriaDTO);
            return null;
        }
    }
    public String Register()
    {
        indexController.setListPropertieDTOs(listPropertieDTOs);
        indexController.FillResidenceCityValues();
        indexController.FillIdTypeValues();
        indexController.FillLocationValues();
        indexController.FillStratumValues();
        indexController.FillSpecialConditionValues();
        indexController.FillMainStreetValues();
        indexController.FillStreetGeneratingPathValues();
        indexController.FillOccupationValues();
        indexController.FillGenderValues();
        indexController.FillNationalityValues();
        indexController.FillNeightborhoodValues();
        indexController.FillSexValues();
        indexController.FillSexualOrientValues();
        indexController.FillPrivateUnitsValues();
        indexController.FillCommonAreasValues();
        indexController.FillRegimenValues();
        indexController.FillPresentationTypeValues();
        indexController.FillAcademicFormationValues();
        indexController.FillStatusLegalAgent();
        indexController.FillTypeProperty();
        indexController.FillTypeRepLegalAgent();
        indexController.FillEtniasValues();
        return "ok";
    }
    
    public String show()
    {
        this.user = "";
        this.pass = "";
        this.loginMessage = false;
        this.indexController.setObjThirdDTO(null);
        return "login";
    }
    
    @PostConstruct
    public void Init()
    {
        try
        {
            this.listPropertieDTOs = PropertiesHandler.GetPropertiesListFromDB();
            this.listPHPropertieDTOs = PropertiesHandler.GetPHPropertiesListFromDB();
            
            /*for(PropertieDTO item : this.listPHPropertieDTOs) {
            	if (item.getKey() == "urlSeguridad") {
            		item.setValue("http://129.213.157.233:7002/ProviderAuthentication/resources");
            	}
            	
            	if (item.getKey() == "urlTerceros") {
            		item.setValue("http://129.213.157.233:7002/ProviderAuthentication/resources");
            	}
            }*/
            
            this.setUrlAyuda(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "urlayuda"));
            this.setUrlImgCR(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "UrlImgCR"));
            this.setUrlImgPH(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "UrlImgPH"));
            this.setLoginMessage(indexController.isViewMessage());
            this.setAlertStyle(indexController.isAlertStyleM());
            this.setResult(indexController.getResultChangePass());
            
            this.logAuditoriaDTO = new LogAuditoriaDTO(0, this.user, "IP", "SO", "NA", "LoginContoller", "NA", "NA", "NA", null);
        }
        catch(Exception e)
        {
            setLoginMessage(true);
            setAlertStyle(false);
            setResult("Ha ocurrido un error en el sistema por favor comuníquese con el administrador del sistema.");
            this.logAuditoriaDTO.setAuMensajeError("Init " + e.toString());
            logService.AddLogAuditoria(logAuditoriaDTO);
            log.error("Error: Ha ocurrido un error en el sistema por favor comuníquese con el administrador del sistema. " + e.toString());
        }
    }
    
    public String ForgotPassword()
    {
        return "success";
    }

    public void setIndexController(IndexController indexController)
    {
        this.indexController = indexController;
    }
    
     public boolean isLoginMessage() {
        return loginMessage;
    }

    public void setLoginMessage(boolean loginMessage) {
        this.loginMessage = loginMessage;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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
    
    public boolean isAlertStyle() {
        return alertStyle;
    }

    public void setAlertStyle(boolean alertStyle) {
        this.alertStyle = alertStyle;
    }

    public List<PropertieDTO> getListPropertieDTOs()
    {
        return listPropertieDTOs;
    }

    public void setListPropertieDTOs(List<PropertieDTO> listPropertieDTOs)
    {
        this.listPropertieDTOs = listPropertieDTOs;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getUrlAyuda()
    {
        return urlAyuda;
    }

    public void setUrlAyuda(String urlAyuda)
    {
        this.urlAyuda = urlAyuda;
    }

    public List<PropertieDTO> getListPHPropertieDTOs()
    {
        return listPHPropertieDTOs;
    }

    public void setListPHPropertieDTOs(List<PropertieDTO> listPHPropertieDTOs)
    {
        this.listPHPropertieDTOs = listPHPropertieDTOs;
    }

    public String getUrlImgCR()
    {
        return UrlImgCR;
    }

    public void setUrlImgCR(String UrlImgCR)
    {
        this.UrlImgCR = UrlImgCR;
    }

    public String getUrlImgPH()
    {
        return UrlImgPH;
    }

    public void setUrlImgPH(String UrlImgPH)
    {
        this.UrlImgPH = UrlImgPH;
    }

   

 
    
    

}
