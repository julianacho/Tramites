/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.Controller;

import co.gov.gobierno.DTO.AyudaDTO;
import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.DTO.ThirdDTO;
import co.gov.gobierno.DTO.WebServiceDTO;
import co.gov.gobierno.Service.CertificateRequestService;
import co.gov.gobierno.Service.ChangePasswordService;
import co.gov.gobierno.Service.TerceroService;
import co.gov.gobierno.Service.WebService;
import co.gov.gobierno.Util.EmailHandler;
import co.gov.gobierno.Util.PropertiesHandler;
import java.io.Serializable;
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
public class ChangePasswordController implements Serializable
{
    private static long serialVersionUID = 1L;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ChangePasswordController.class);
    
    private boolean alertShow = false;
    private boolean alertStyle = false;
    private String alertMessage = "";
    private List<PropertieDTO> listPropertieDTOs;
    
    private ThirdDTO objThirdDTO;
    
    private String email = "";
    private String passConfirm = "";
    private String pass = "";
    
    private List<AyudaDTO> listAyudaDTO;
    private Map<String,String> MapAyudaDB;
    private String UrlVideoRegistro;

    
    @ManagedProperty(value="#{indexController}")
    private IndexController indexController;

    @EJB
    WebService ws;
    @EJB
    ChangePasswordService cps;
    
    @EJB
    TerceroService ts;
    
    public void changePassword()
    {
        try
        {
            if(this.pass.equals(this.passConfirm))
            {
                if(this.objThirdDTO.getEmail().equals(this.email))
                {
                    WebServiceDTO objWebServiceDTO = new WebServiceDTO();
                    String token = ts.ModificarPassword(indexController.getObjThirdDTO().getUser(), this.passConfirm, indexController.getObjThirdDTO().getPass());
                    objWebServiceDTO = cps.ChangePassword(this.objThirdDTO, this.pass, indexController.getAuthenticationToken(), this.listPropertieDTOs);
                    if (token.isEmpty()) {
                    	objWebServiceDTO.setRespuesta(false);
                    }
                    setAlertShow(true);
                    if(objWebServiceDTO.isRespuesta())
                    {
                    	///alonso
                    	 String pass = "";
                        /*String emailResult = EmailHandler.EnviarMensaje(this.email, "Cambio de contraseña exitoso", "Estimado (a) ciudadano (a)" +
                                "<br><br>" +
                                "Se actualiz&oacute; satisfactoriamente el usuario " + this.objThirdDTO.getUser() + " y Contrase&ntilde;a " + this.pass + " en la aplicaci&oacute;n.<br>" +
                                "<br>Para continuar con su solicitud <br><a href='" + PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "urlLogin") + "' target='_blank'>" +
                                "haga clic aqui</a><br><br><br><br><br><p style='font-size:10px'>\"Este correo es de tipo informativo; por favor no responda a este mensaje. Para contactarnos puede hacerlo a trav&eacute;s de la p&aacute;gina de la Secretar&iacute;a Distrital de Gobierno: <a href='http://www.gobiernobogota.gov.co' target='_blank'>http://www.gobiernobogota.gov.co</a>\"</p><br>", this.listPropertieDTOs);*/

                        String emailResult = EmailHandler.EnviarMensaje(this.email, "Cambio de contraseña exitoso", "Estimado (a) ciudadano (a)" +
                                                        "<br><br>" +
                                                        "Se actualiz&oacute; satisfactoriamente el usuario " + this.objThirdDTO.getUser() + " " /*+ this.pass + */ +" .<br>" +
                                                        "<br>Para continuar con su solicitud<br><a href='" + PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "urlLogin") + "' target='_blank'>" +
                                                        "haga clic aqui</a><br><br><br><br><br><p style='font-size:10px'>\"Este correo es de tipo informativo; por favor no responda a este mensaje. Para contactarnos puede hacerlo a trav&eacute;s de la p&aacute;gina de la Secretar&iacute;a Distrital de Gobierno: <a href='http://www.gobiernobogota.gov.co' target='_blank'>http://www.gobiernobogota.gov.co</a>\"</p><br>", this.listPropertieDTOs);
                        if(!emailResult.contains("Error"))
                        {
                            setAlertShow(true);
                            setAlertStyle(true);
                            setAlertMessage("El cambio de contraseña fue exitoso, el usuario y la nueva contraseña fueron "
                                    + "enviados al correo " + this.getEmail());
                        }
                        else
                        {
                            setAlertShow(true);
                            setAlertStyle(false);
                            setAlertMessage("Error al enviar el email.");
                        }
                        
                    }
                    else
                    {
                        setAlertStyle(false);
                        setAlertMessage("Ha ocurrido un error en el sistema por favor comuníquese con el administrador del sistema.");
                    }
                    this.email = "";
                }
                else
                {
                    setAlertShow(true);
                    setAlertStyle(false);
                    setAlertMessage("El correo electrónico no se encuentra registrado");
                }
            }
            else
            {
                setAlertShow(true);
                setAlertStyle(false);
                setAlertMessage("La validacion de la contraseña es incorrecta.");
            }
        }
        catch(Exception e)
        {
            setAlertShow(true);
            setAlertStyle(false);
            setAlertMessage("Ha ocurrido un error al enviar el email. Contacte al administrador del sistema e intentelo más tarde.");
            // setAlertMessage("Ha ocurrido un error inesperado. Contacte al administrador del sistema e intentelo más tarde.");
            log.error("Error: " + e.toString());
        }
    }
    
    @PostConstruct
    public void Init()
    {
        try
        {
            this.objThirdDTO = indexController.getObjThirdDTO();
            this.listPropertieDTOs = indexController.getListPropertieDTOs();
            this.listAyudaDTO = PropertiesHandler.GetAyudaDB();
            this.MapAyudaDB = PropertiesHandler.AyudaDB(listAyudaDTO, "CambioContrasena");
            if(MapAyudaDB.get("vTipo").equals("1"))
            {
                           this.setUrlVideoRegistro("<iframe width='600' height='315'  src='"+MapAyudaDB.get("vUrl")+"' frameborder='0' allow='accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture' allowfullscreen></iframe>");
            }
            if(MapAyudaDB.get("vTipo").equals("2"))
            {
                          this.setUrlVideoRegistro("<img width='600' height='315' src='"+MapAyudaDB.get("vUrl")+"'/>");
            }
        }
        catch(Exception e)
        {
            setAlertShow(true);
            setAlertStyle(false);
            setAlertMessage("Ha ocurrido un error inesperado. Contacte al administrador del sistema e intentelo más tarde.");
            setAlertMessage("Ha ocurrido un error al enviar el email. Contacte al administrador del sistema e intentelo más tarde.");
            log.error("Error: " + e.toString());
        }
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

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassConfirm() {
        return passConfirm;
    }

    public void setPassConfirm(String passConfirm) {
        this.passConfirm = passConfirm;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public ThirdDTO getObjThirdDTO() {
        return objThirdDTO;
    }

    public void setObjThirdDTO(ThirdDTO objThirdDTO) {
        this.objThirdDTO = objThirdDTO;
    }

    public void setIndexController(IndexController indexController) {
        this.indexController = indexController;
    }

    public List<PropertieDTO> getListPropertieDTOs()
    {
        return listPropertieDTOs;
    }

    public void setListPropertieDTOs(List<PropertieDTO> listPropertieDTOs)
    {
        this.listPropertieDTOs = listPropertieDTOs;
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
}
