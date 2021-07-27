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
import co.gov.gobierno.Service.TerceroService;
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
import javax.crypto.Cipher;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.slf4j.LoggerFactory;

/**
 *
 * @author DELL
 */
@ManagedBean
@ViewScoped
public class RememberPasswordController implements Serializable
{
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(RememberPasswordController.class);
    private boolean alertShow;
    private boolean alertStyle;
    private String alertMessage;
    private List<PropertieDTO> listPropertieDTOs;
    
    private String user;
    private String pass;
    private String passConfirm;
    private ThirdDTO objThirdDTO;
    
    private List<AyudaDTO> listAyudaDTO;
    private Map<String,String> MapAyudaDB;
    private String UrlVideoRegistro;
    
    @ManagedProperty(value="#{indexController}")
    private IndexController indexController;
    
    @EJB
    IndexService is;
    @EJB
    RememberPasswordService rps;
    
    @EJB
    TerceroService ts;
    
    public String ChangePassword()
    {
        try
        {
            if(pass.equals(passConfirm))
            {
                WebServiceDTO objWebServiceDTO = new WebServiceDTO();
                String token = ts.ModificarPassword(indexController.getObjThirdDTO().getUser(), this.passConfirm, indexController.getObjThirdDTO().getPass() != null ? indexController.getObjThirdDTO().getPass() : "");
                objWebServiceDTO = rps.ChangePassword(this.user, this.pass, PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "tokenAutenticacion"), this.listPropertieDTOs);
                if (token.isEmpty()) {
                	objWebServiceDTO.setRespuesta(false);
                }
                this.objThirdDTO = new ThirdDTO();
                this.objThirdDTO.setUser(this.user);
                this.objThirdDTO = is.ThirdConsult(objThirdDTO, listPropertieDTOs);
                if(objWebServiceDTO.isRespuesta())
                {
                	//alonso
                	String pass = "";
                   /* String emailResult = EmailHandler.EnviarMensaje(this.objThirdDTO.getEmail(), "Cambio de contraseña exitoso", "<html><meta charset=\"UTF-8\"/>Estimado (a) ciudadano (a)<br>" +
                                                    "" +
                                                    "Se actualizó satisfactoriamente el usuario " + this.getObjThirdDTO().getUser() + " y Contraseña " + this.pass + " en la aplicación.<br><br>" +
                                                    "\n" +
                                                    "</html>", this.listPropertieDTOs);*/
                    String emailResult = EmailHandler.EnviarMensaje(this.objThirdDTO.getEmail(), "", "<html><meta charset=\"UTF-8\"/>Estimado (a) ciudadano (a)<br>" +
                            "" +
                            "Se actualiz&oacute; satisfactoriamente el usuario " + this.objThirdDTO.getUser() + " " /*+ this.pass + */ +" .<br>" +
                            "\n" +
                            "</html>", this.listPropertieDTOs);
                    if(!emailResult.contains("Error"))
                    {
                        indexController.setResultChangePass("Se ha cambiado su contraseña exitosamente.");
                        indexController.setAlertStyleM(true);
                        indexController.setViewMessage(true);
                        return "success";
                        
                    }
                    else
                    {
                        setAlertShow(true);
                        setAlertStyle(false);
                        setAlertMessage("Error al enviar el email.");
                        return null;
                    }
                }
                else
                {
                    setAlertShow(true);
                    setAlertStyle(false);
                    setAlertMessage("Ha ocurrido un error inesperado, contacte al administrador del sistema.");
                    return null;
                }
            }
            else
            {
                setAlertShow(true);
                setAlertStyle(false);
                setAlertMessage("La contraseña no coincide con la confirmación de contraseña");
                return null;
            }
        }
        catch(Exception e)
        {
            setAlertShow(true);
            setAlertStyle(false);
            setAlertMessage("Ha ocurrido un Error en recordar contrasenia:, contacte al administrador del sistema.");
            // setAlertMessage("Ha ocurrido un error inesperado, contacte al administrador del sistema.");
            log.error("Error en recordar contrasenia: " + e.toString());
            return null;
        }
    }
    
    @PostConstruct
    public void Init()
    {
        try
        {
            FacesContext context = FacesContext.getCurrentInstance();
            Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
            String enc64 = paramMap.get("user");
            enc64 = enc64.replace(" ", "+");
            String key = "Bar12345Bar12345"; // 128 bit key
            String initVector = "RandomInitVector"; // 16 bytes IV
            this.user = EncryptHandler.decrypt(key, initVector,enc64);
            this.setListPropertieDTOs(PropertiesHandler.GetPropertiesListFromDB());
            
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

    public String getPass()
    {
        return pass;
    }

    public void setPass(String pass)
    {
        this.pass = pass;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public ThirdDTO getObjThirdDTO()
    {
        return objThirdDTO;
    }

    public void setObjThirdDTO(ThirdDTO objThirdDTO)
    {
        this.objThirdDTO = objThirdDTO;
    }

    public String getPassConfirm()
    {
        return passConfirm;
    }

    public void setPassConfirm(String passConfirm)
    {
        this.passConfirm = passConfirm;
    }

    public void setIndexController(IndexController indexController)
    {
        this.indexController = indexController;
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