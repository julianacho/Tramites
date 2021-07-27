/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.Controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;

import co.gov.gobierno.DTO.AyudaDTO;
import co.gov.gobierno.DTO.LogAuditoriaDTO;
import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.DTO.RequestDTO;
import co.gov.gobierno.DTO.ThirdDTO;
import co.gov.gobierno.DTO.WebServiceDTO;
import co.gov.gobierno.Service.CertificateConsultService;
import co.gov.gobierno.Service.LogAuditoriaService;
import co.gov.gobierno.Util.Base64Handler;
import co.gov.gobierno.Util.PropertiesHandler;
import co.gov.gobierno.Util.XmlHandler;

/**
 *
 * @author DELL
 */
@ManagedBean
@RequestScoped
public class CertificateConsultController implements Serializable
{

    private static long serialVersionUID = 1L;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(CertificateConsultController.class);
    private ThirdDTO objThirdDTO;
    private List<PropertieDTO> listPropertieDTOs;

    private String xmlConsult = "";

    private boolean showidAndName = true;
    private boolean resultTable = false;
    private boolean showTable;

    private boolean alertShow = false;
    private boolean alertStyle = false;
    private String alertMessage = "";

    private List<AyudaDTO> listAyudaDTO;
    private Map<String, String> MapAyudaDB;
    private String UrlVideoRegistro;

    private transient List<RequestDTO> listRequestDTO;
    private transient List<RequestDTO> listRequestDTOYounger;

    @ManagedProperty(value = "#{indexController}")
    private IndexController indexController;

    @EJB
    CertificateConsultService ccs;
    
    @EJB
    LogAuditoriaService logService;
    
    private LogAuditoriaDTO logAuditoriaDTO = null;

    public String CorrectRequest()
    {
        try
        {
            Locale locale = new Locale(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("radNumber"));
            String select = locale.toString();
            for (RequestDTO objRequestDTO : listRequestDTO)
            {
                if (objRequestDTO.getRadNumber().equals(select))
                {
                    indexController.setObjRequestDTO(objRequestDTO);
                    return "success";
                }
            }
        } catch (Exception e)
        {
            setAlertShow(true);
            setAlertStyle(false);
            setAlertMessage("Ha ocurrido un error inesperado. Contacte al administrador del sistema e intentelo más tarde.");
            log.error("Error: " + e.toString());
            this.logAuditoriaDTO.setAuMensajeError("Correcto Request " + "Ha ocurrido un error inesperado. Contacte al administrador del sistema e intentelo más tarde. " + e.toString());
            logService.AddLogAuditoria(logAuditoriaDTO);
            
        }
        return null;
    }

    public String CorrectYoungerRequest()
    {
        try
        {
            Locale locale = new Locale(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("radNumber"));
            String select = locale.toString();
            for (RequestDTO objRequestDTO : listRequestDTOYounger)
            {
                if (objRequestDTO.getRadNumber().equals(select))
                {
                    indexController.setObjRequestDTO(objRequestDTO);
                    return "success";
                }
            }
        } catch (Exception e)
        {
            setAlertShow(true);
            setAlertStyle(false);
            setAlertMessage("Ha ocurrido un error inesperado. Al cargar la tabla menor de edad.");
            // setAlertMessage("Ha ocurrido un error inesperado. Al cargar la tabla.");
            log.error("Error: " + e.toString());
            this.logAuditoriaDTO.setAuMensajeError("CorrectYoungerRequest " + "Ha ocurrido un error inesperado. Contacte al administrador del sistema e intentelo más tarde. " + e.toString());
            logService.AddLogAuditoria(logAuditoriaDTO);
        }
        return null;
    }

    public String DownloadRequest()
    {
        try
        {
            Locale locale = new Locale(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("radNumber"));
            String select = locale.toString();
            WebServiceDTO objWebServiceDTO = new WebServiceDTO();
            for (RequestDTO objRequestDTO : listRequestDTO)
            {
                if (objRequestDTO.getRadNumber().equals(select))
                {
                    if (Boolean.parseBoolean(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "executeADAX")))
                    {
                        objWebServiceDTO = ccs.GetDownloadRequest(this.objThirdDTO, this.listPropertieDTOs, objRequestDTO);
                    } else
                    {
                        objWebServiceDTO = ccs.DownloadORFEO(objThirdDTO, listPropertieDTOs, objRequestDTO);
                    }
                }
            }
            for (RequestDTO objRequestDTO : listRequestDTOYounger)
            {
                if (objRequestDTO.getRadNumber().equals(select))
                {
                    if (Boolean.parseBoolean(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "executeADAX")))
                    {
                        objWebServiceDTO = ccs.GetDownloadRequest(this.objThirdDTO, this.listPropertieDTOs, objRequestDTO);
                    } else
                    {
                        objWebServiceDTO = ccs.DownloadORFEO(objThirdDTO, listPropertieDTOs, objRequestDTO);
                    }
                }
            }
            if (objWebServiceDTO.isRespuesta())
            {
                String fileName = "";
                String base64 = "";
                if (Boolean.parseBoolean(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "executeADAX")))
                {
                    fileName = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "a:FileName");
                    base64 = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "a:StringBase64");
                } else
                {
                    String response = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "return");
                    String aux[] = response.split("\\|\\|");
                    fileName = aux[3];
                    base64 = aux[4];
                }
                byte[] pdfData = Base64Handler.convertBase64BinaryToFile(base64);
                FacesContext facesContext = FacesContext.getCurrentInstance();
                ExternalContext externalContext = facesContext.getExternalContext();
                HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

                // Initialize response.
                response.reset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
                response.setContentType("application/pdf"); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ServletContext#getMimeType() for auto-detection based on filename.
                response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + ".pdf\""); // The Save As popup magic is done here. You can give it any filename you want, this only won't work in MSIE, it will use current request URL as filename instead.
                try (OutputStream output = response.getOutputStream())
                {
                    output.write(pdfData);
                }
                // Inform JSF to not take the response in hands.
                facesContext.responseComplete();
            } else
            {
                this.setAlertShow(true);
                this.setAlertStyle(false);
                this.setAlertMessage("El certificado no se puede descargar, intentelo de nuevo mas tarde.");
                this.logAuditoriaDTO.setAuMensajeError("Download Request " + "El certificado no se puede descargar, intentelo de nuevo mas tarde. ");
                logService.AddLogAuditoria(logAuditoriaDTO);
            }
            return "success";
        } catch (IOException e)
        {
            this.setAlertShow(true);
            this.setAlertStyle(false);
            // this.setAlertMessage("El certificado no se puede descargar, intentelo de nuevo mas tarde.");
            this.setAlertMessage("El certificado no se puede descargar, Error al descargar el archivo: ");
            log.error("Error al descargar el archivo: " + e.toString());
            this.logAuditoriaDTO.setAuMensajeError("Download Request " + "El certificado no se puede descargar, intentelo de nuevo mas tarde. " + e.toString());
            logService.AddLogAuditoria(logAuditoriaDTO);
        } catch (Exception e)
        {
            this.setAlertShow(true);
            this.setAlertStyle(false);
            this.setAlertMessage("El certificado no se puede descargar, intentelo de nuevo mas tarde.");
            log.error("Error al descargar el archivo: " + e.toString());
            this.logAuditoriaDTO.setAuMensajeError("Download Request " + "El certificado no se puede descargar, intentelo de nuevo mas tarde. " + e.toString());
            logService.AddLogAuditoria(logAuditoriaDTO);
        }
        return null;
    }

    @PostConstruct
    public void Init()
    {
        try
        {
        	this.logAuditoriaDTO = new LogAuditoriaDTO(0, "NA", "IP", "SO", "NA", "CertificateContoller", "NA", "NA", "NA", null);
            this.showTable = false;
            this.objThirdDTO = indexController.getObjThirdDTO();
            this.listPropertieDTOs = indexController.getListPropertieDTOs();
            this.listRequestDTO = new ArrayList<>();
            this.listRequestDTOYounger = new ArrayList<>();
            WebServiceDTO objWebServiceDTO = ccs.getRequest(this.objThirdDTO, this.listPropertieDTOs);
            if (objWebServiceDTO != null)
            {
                try
                {
                    this.listRequestDTO = ccs.readConsult(objWebServiceDTO.getXmlFile(), "False");
                    this.listRequestDTOYounger = ccs.readConsult(objWebServiceDTO.getXmlFile(), "True");
                    this.xmlConsult = objWebServiceDTO.getXmlFile();
                    
                    this.listAyudaDTO = PropertiesHandler.GetAyudaDB();
                    this.MapAyudaDB = PropertiesHandler.AyudaDB(listAyudaDTO, "ConsultaCertificado");
                    if (MapAyudaDB.get("vTipo").equals("1"))
                    {
                        this.setUrlVideoRegistro("<iframe width='600' height='315'  src='"+MapAyudaDB.get("vUrl")+"' frameborder='0' allow='accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture' allowfullscreen></iframe>");
                    }
                    if (MapAyudaDB.get("vTipo").equals("2"))
                    {
                        this.setUrlVideoRegistro("<img width='600' height='315' src='" + MapAyudaDB.get("vUrl") + "'/>");
                    }
                } catch (Exception e)
                {
                    this.showTable = false;
                    this.alertShow = true;
                    this.alertStyle = false;
                    this.alertMessage = "Error al leer las consultas de los certificados.";
                    this.logAuditoriaDTO.setAuMensajeError("Init " + "Error al leer las consultas de los certificados. " + e.toString());
                    logService.AddLogAuditoria(logAuditoriaDTO);
                    log.error("Error en la consulta de certificados: " + e.toString());
                    
                }
            } else
            {
                this.showTable = false;
                this.listRequestDTO.clear();
                this.alertShow = true;
                this.alertStyle = false;
                this.alertMessage = "Error al buscar las consultas de los certificados.";
                this.logAuditoriaDTO.setAuMensajeError("Init " + "Error al buscar las consultas de los certificados.");
                logService.AddLogAuditoria(logAuditoriaDTO);
            }
            
        } catch (Exception e)
        {
            setAlertShow(true);
            setAlertStyle(false);
            setAlertMessage("Error al buscar las consultas de los certificados. Contacte al administrador del sistema e intentelo más tarde.");
            // setAlertMessage("Ha ocurrido un error inesperado. Contacte al administrador del sistema e intentelo más tarde.");
            log.error("Error: " + e.toString());
            this.logAuditoriaDTO.setAuMensajeError("Init " + "Error al buscar las consultas de los certificados. Contacte al administrador del sistema e intentelo más tarde. " + e.toString());
            // this.logAuditoriaDTO.setAuMensajeError("Init " + "Ha ocurrido un error inesperado. Contacte al administrador del sistema e intentelo más tarde. " + e.toString());
            logService.AddLogAuditoria(logAuditoriaDTO);
        }
    }

    public ThirdDTO getObjThirdDTO()
    {
        return objThirdDTO;
    }

    public void setObjThirdDTO(ThirdDTO objThirdDTO)
    {
        this.objThirdDTO = objThirdDTO;
    }

    public List<RequestDTO> getListRequestDTO()
    {
        return listRequestDTO;
    }

    public void setListRequestDTO(List<RequestDTO> listRequestDTO)
    {
        this.listRequestDTO = listRequestDTO;
    }

    public void setIndexController(IndexController indexController)
    {
        this.indexController = indexController;
    }

    public boolean isShowidAndName()
    {
        return showidAndName;
    }

    public void setShowidAndName(boolean showidAndName)
    {
        this.showidAndName = showidAndName;
    }

    public boolean isResultTable()
    {
        return resultTable;
    }

    public void setResultTable(boolean resultTable)
    {
        this.resultTable = resultTable;
    }

    public List<RequestDTO> getListRequestDTOYounger()
    {
        return listRequestDTOYounger;
    }

    public void setListRequestDTOYounger(List<RequestDTO> listRequestDTOYounger)
    {
        this.listRequestDTOYounger = listRequestDTOYounger;
    }

    public boolean isShowTable()
    {
        return showTable;
    }

    public void setShowTable(boolean showTable)
    {
        this.showTable = showTable;
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

    public String getXmlConsult()
    {
        return xmlConsult;
    }

    public void setXmlConsult(String xmlConsult)
    {
        this.xmlConsult = xmlConsult;
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
