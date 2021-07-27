/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.Controller;

import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.DTO.ThirdDTO;
import co.gov.gobierno.DTO.WebServiceDTO;
import co.gov.gobierno.Service.UpdateLegalAgentService;
import co.gov.gobierno.Service.WebService;
import co.gov.gobierno.Util.EmailHandler;
import co.gov.gobierno.Util.JsonHandler;
import co.gov.gobierno.Util.PropertiesHandler;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.UploadedFile;
import org.slf4j.LoggerFactory;

/**
 *
 * @author DELL
 */
@ManagedBean
@ViewScoped
public class UpdateLegalAgentController implements Serializable
{
    private ThirdDTO objThirdDTO;
    private List<PropertieDTO> listPropertieDTOs;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(UpdateLegalAgentController.class);
    
    private String location;
    private String neightborhood;
    private UploadedFile uploadConferenceRecord;
    private UploadedFile uploadAcceptDocument;
    private UploadedFile uploadUpdateDocument;
    private UploadedFile uploadIdDocumentCopy;
    private boolean oathCheckBox;
    
    private Map<String, String> valuesLocation;
    private Map<String, String> valuesNeightborhood;
    
    private boolean alertShow;
    private boolean alertStyle;
    private String alertMessage;
    
    @ManagedProperty(value = "#{indexController}")
    private IndexController indexController;
    
    @EJB
    WebService ws;
    @EJB
    UpdateLegalAgentService ulas;
    
    public void Request()
    {
        try
        {
            String radNumber = ulas.GetRadicadoNumber(objThirdDTO, listPropertieDTOs, location, neightborhood);
            if(radNumber!=null && !radNumber.equals(""))
            {
                String expNumber = ulas.GetExpedientNumber(radNumber, listPropertieDTOs);
                if(expNumber!=null && !expNumber.equals(""))
                {
                    String appendNumber = ulas.AppendExpedient(radNumber, expNumber, listPropertieDTOs);
                    if(appendNumber!=null && !appendNumber.equals(""))
                    {
                        String responseFile1 = ulas.CreateAppend(radNumber, uploadConferenceRecord, listPropertieDTOs);
                        int counter=0;
                        if(responseFile1 == null)
                        {
                            log.error("Error en la respuesta de crear Anexo #1....");
                            this.setAlertShow(true);
                            this.setAlertStyle(false);
                                                        this.setAlertMessage("Ocurrió un error en la respuesta de crear Anexos en el sistema contacte al administrador del sistema.");
                            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
                            counter++;
                        }
                        String responseFile2 = ulas.CreateAppend(radNumber, uploadAcceptDocument, listPropertieDTOs);
                        if(responseFile2 == null)
                        {
                            log.error("Error en la respuesta de crear Anexo #2....");
                            this.setAlertShow(true);
                            this.setAlertStyle(false);
                                                        this.setAlertMessage("Ocurrió un error en la respuesta de crear Anexos en el sistema contacte al administrador del sistema.");
                            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
                            counter++;
                        }
                        String responseFile3 = ulas.CreateAppend(radNumber, uploadUpdateDocument, listPropertieDTOs);
                        if(responseFile3 == null)
                        {
                            log.error("Error en la respuesta de crear Anexo #3....");
                            this.setAlertShow(true);
                            this.setAlertStyle(false);
                                                        this.setAlertMessage("Ocurrió un error en la respuesta de crear Anexos en el sistema contacte al administrador del sistema.");
                            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
                            counter++;
                        }
                        String responseFile4 = ulas.CreateAppend(radNumber, uploadIdDocumentCopy, listPropertieDTOs);
                        if(responseFile4 == null)
                        {
                            log.error("Error en la respuesta de crear Anexo #4....");
                            this.setAlertShow(true);
                            this.setAlertStyle(false);
                                                        this.setAlertMessage("Ocurrió un error en la respuesta de crear Anexos en el sistema contacte al administrador del sistema.");
                            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
                            counter++;
                        }
                        if(counter == 0)
                        {
                            this.setAlertShow(true);
                            String emailResponse = EmailHandler.EnviarMensaje(this.objThirdDTO.getEmail(), "Acualización de Representate legal",
                                    "Estimado (a) ciudadano su solicitud ha sido radicada exitosamente su" + " n&uacute;mero de radicado es el No " + radNumber + 
                                            ", su solicitud ser&acute; atendida durante los pr&ocute;ximos 15 d&icute;as h&acute;biles", this.listPropertieDTOs);
                            if(!emailResponse.contains("Error"))
                            {
                                this.setAlertStyle(true);
                                this.setAlertMessage("Señor ciudadano su solicitud ha sido radicada exitosamente su" 
                                        + " número de radicado es el No " + radNumber + ", su solicitud será atendida durante los próximos 15 días hábiles");
                            }
                            else
                            {
                                this.setAlertStyle(false);
                                this.setAlertMessage("Error al enviar el email.");
                            }
                        }
                    }
                    else
                    {
                        log.error("numero del anexo vacio o nulo.");
                        this.setAlertShow(true);
                        this.setAlertStyle(false);
                        this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
                        // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
                    }
                }
                else
                {
                    log.error("numero de expediente vacio o nulo.");
                    this.setAlertShow(true);
                    this.setAlertStyle(false);
                    this.setAlertMessage("Ocurrió un error numero de expediente vacio contacte al administrador del sistema.");
                    // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
                }
            }
            else
            {
                log.error("numero de radicado vacio o nulo.");
                this.setAlertShow(true);
                this.setAlertStyle(false);
                this.setAlertMessage("Ocurrió un error numero de radicado vacio contacte al administrador del sistema.");
                // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
            }
        }
        catch(Exception e)
        {
            log.error("Error en PHRegisterController: " + e.toString());
            this.setAlertShow(true);
            this.setAlertStyle(false);
            this.setAlertMessage("Ocurrió un error al registrar el formulario contacte al administrador del sistema.");
            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
        }
    }
    
    @PostConstruct
    public void Init()
    {
        try
        {
            this.objThirdDTO = indexController.getObjThirdDTO();
            this.listPropertieDTOs = indexController.getListPropertieDTOs();
            FillLocationValues();
            FillNeightborhoodValues();
        }
        catch (Exception e)
        {
            setAlertShow(true);
            setAlertStyle(false);
            setAlertMessage("Ha ocurrido un error en el sistema por favor comuníquese con el administrador del sistema.");
            log.info("Error al cargar la pagina de registro de PH: " + e.toString());
        }
    }

    public void FillLocationValues()
    {
        WebServiceDTO objWebServiceDTO = new WebServiceDTO();
        objWebServiceDTO.setMethod("GET");
        objWebServiceDTO.setUrl(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "urlTerceros") + "/parametros?tipoParametro=LOCALIDAD");
        objWebServiceDTO = ws.getService(objWebServiceDTO);
        setValuesLocation(JsonHandler.ReadSimpleJsonStringTwoValues(objWebServiceDTO.getJsonFile(), "parametrosDTO", "descParametro", "valorParametro"));
    }
    
    public void FillNeightborhoodValues()
    {
        WebServiceDTO objWebServiceDTO = new WebServiceDTO();
        objWebServiceDTO.setMethod("GET");
        objWebServiceDTO.setUrl(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "urlTerceros") + "/parametros?tipoParametro=UPZ");
        objWebServiceDTO = ws.getService(objWebServiceDTO);
        setValuesNeightborhood(JsonHandler.ReadSimpleJsonStringTwoValues(objWebServiceDTO.getJsonFile(), "parametrosDTO", "descParametro", "valorParametro"));
    }
    
    public ThirdDTO getObjThirdDTO()
    {
        return objThirdDTO;
    }

    public void setObjThirdDTO(ThirdDTO objThirdDTO)
    {
        this.objThirdDTO = objThirdDTO;
    }

    public List<PropertieDTO> getListPropertieDTOs()
    {
        return listPropertieDTOs;
    }

    public void setListPropertieDTOs(List<PropertieDTO> listPropertieDTOs)
    {
        this.listPropertieDTOs = listPropertieDTOs;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getNeightborhood()
    {
        return neightborhood;
    }

    public void setNeightborhood(String neightborhood)
    {
        this.neightborhood = neightborhood;
    }

    public UploadedFile getUploadConferenceRecord()
    {
        return uploadConferenceRecord;
    }

    public void setUploadConferenceRecord(UploadedFile uploadConferenceRecord)
    {
        this.uploadConferenceRecord = uploadConferenceRecord;
    }

    public UploadedFile getUploadAcceptDocument()
    {
        return uploadAcceptDocument;
    }

    public void setUploadAcceptDocument(UploadedFile uploadAcceptDocument)
    {
        this.uploadAcceptDocument = uploadAcceptDocument;
    }

    public UploadedFile getUploadUpdateDocument()
    {
        return uploadUpdateDocument;
    }

    public void setUploadUpdateDocument(UploadedFile uploadUpdateDocument)
    {
        this.uploadUpdateDocument = uploadUpdateDocument;
    }

    public UploadedFile getUploadIdDocumentCopy()
    {
        return uploadIdDocumentCopy;
    }

    public void setUploadIdDocumentCopy(UploadedFile uploadIdDocumentCopy)
    {
        this.uploadIdDocumentCopy = uploadIdDocumentCopy;
    }

    public Map<String, String> getValuesLocation()
    {
        return valuesLocation;
    }

    public void setValuesLocation(Map<String, String> valuesLocation)
    {
        this.valuesLocation = valuesLocation;
    }

    public Map<String, String> getValuesNeightborhood()
    {
        return valuesNeightborhood;
    }

    public void setValuesNeightborhood(Map<String, String> valuesNeightborhood)
    {
        this.valuesNeightborhood = valuesNeightborhood;
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

    public boolean isOathCheckBox()
    {
        return oathCheckBox;
    }

    public void setOathCheckBox(boolean oathCheckBox)
    {
        this.oathCheckBox = oathCheckBox;
    }

    public void setIndexController(IndexController indexController)
    {
        this.indexController = indexController;
    }
    
    
}
