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
import co.gov.gobierno.Service.ChangePasswordService;
import co.gov.gobierno.Service.IndexService;
import co.gov.gobierno.Service.LoginService;
import co.gov.gobierno.Service.RememberPasswordService;
import co.gov.gobierno.Service.WebService;
import co.gov.gobierno.Util.EmailHandler;
import co.gov.gobierno.Util.EncryptHandler;
import co.gov.gobierno.Util.PropertiesHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.slf4j.LoggerFactory;

/**
 *
 * @author DELL
 */
@ManagedBean
@ViewScoped
public class ForgotPasswordController implements Serializable
{
    
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(ForgotPasswordController.class);
    private boolean alertShow;
    private boolean alertStyle;
    private String alertMessage;
    private List<PropertieDTO> listPropertieDTOs;
    private List<AyudaDTO> listAyudaDTO;
    private Map<String,String> MapAyudaDB;
    private String UrlVideoRegistro;
    
    private String user;
    private String email;
    private String url;
    
    @ManagedProperty(value="#{indexController}")
    private IndexController indexController;
    
    public String ForgotPassword()
    {
        indexController.setObjThirdDTO(new ThirdDTO());
        indexController.getObjThirdDTO().setUser(this.getUser());
        indexController.setListPropertieDTOs(this.getListPropertieDTOs());
        indexController.ConsultThird();
        this.setUrl(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "urlLogin"));
        if (indexController.getObjThirdDTO() != null)
        {
            this.setEmail(indexController.getObjThirdDTO().getEmail());
            if(getEmail()!=null && !email.equals(""))
            {
                String key = "Bar12345Bar12345"; // 128 bit key
                String initVector = "RandomInitVector"; // 16 bytes IV

                String enc64 = EncryptHandler.encrypt(key, initVector, user);
                log.info(enc64);
                //String link = "h" + "ttp://localhost:7001/TRAMITES/faces/rememberpassword.xhtml?user=" + enc64;
                String link = url+"faces/rememberpassword.xhtml?user=" + enc64;
                String emailResponse = EmailHandler.EnviarMensaje(this.getEmail(), "Recuperación contraseña", "<html><meta charset=\"UTF-8\"/><div style='text-align:left;'>Estimado (a) ciudadano (a)<br>"
                        + "Para Cambiar su contrase&ntilde;a, siga el siguiente Enlace: <br> <a href=" + link+" target='_blank'>Haga clic aqui para cambiar su contrase&ntilde;a</a><br><br><br>"
                                + "<br><br><br><br><p style='font-size:10px'>\"Este correo es de tipo informativo; por favor no responda a este mensaje. Para contactarnos puede hacerlo a trav&eacute;s de la p&aacute;gina de la Secretar&iacute;a Distrital de Gobierno: <a href='http://www.gobiernobogota.gov.co' target='_blank'>http://www.gobiernobogota.gov.co</a>\"</p><br></div></html>", this.getListPropertieDTOs());
                if(!emailResponse.contains("Error"))
                {
                    this.alertShow = true;
                    this.setAlertStyle(true);
                    this.setAlertMessage("Se ha enviado un correo electrónico a: " + this.getEmail() + " con la recuperación de la contraseña.");
                }
                else
                {
                    this.alertShow = true;
                    this.setAlertStyle(false);
                    this.setAlertMessage("Error enviando el email");
                }
            }
            this.setEmail("");
            return null;
        } else {
            this.alertShow = true;
            this.setAlertStyle(false);
            this.setAlertMessage("El Usuario Ingresado no Existe");
            this.setEmail("");
            return null;
        }
    }
    
    public String Back()
    {
        return"back";
    }
    
    @PostConstruct
    public void Init()
    {
        try
        {
            this.listPropertieDTOs = PropertiesHandler.GetPropertiesListFromDB();
            this.listAyudaDTO = PropertiesHandler.GetAyudaDB();
            this.MapAyudaDB = PropertiesHandler.AyudaDB(listAyudaDTO, "RecuperarContrasena");
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
            setAlertMessage("Ha ocurrido un error inesperado, contacte al administrador del sistema.");
        }
    }

    public boolean isAlertShow()
    {
        return alertShow;
    }

    public void setAlertShow(boolean alertShow)
    {
        this.alertShow = alertShow;
    }

    public boolean isAlertStyle()
    {
        return alertStyle;
    }

    public void setAlertStyle(boolean alertStyle)
    {
        this.alertStyle = alertStyle;
    }

    public String getAlertMessage()
    {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage)
    {
        this.alertMessage = alertMessage;
    }

    public List<PropertieDTO> getListPropertieDTOs()
    {
        return listPropertieDTOs;
    }

    public void setListPropertieDTOs(List<PropertieDTO> listPropertieDTOs)
    {
        this.listPropertieDTOs = listPropertieDTOs;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setIndexController(IndexController indexController)
    {
        this.indexController = indexController;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
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