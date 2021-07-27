/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.Controller;

import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.DTO.ThirdDTO;
import co.gov.gobierno.DTO.WebServiceDTO;
import co.gov.gobierno.Service.PHExtincionService;
import co.gov.gobierno.Service.WebService;
import co.gov.gobierno.Util.EmailHandler;
import co.gov.gobierno.Util.PropertiesHandler;
import co.gov.gobierno.Util.ReporteHandler;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
public class PHSubsanacionExtincionController implements Serializable
{
    
    
	 private static final org.slf4j.Logger log = LoggerFactory.getLogger(PHSubsanacionExtincionController.class);
    
    private ThirdDTO objThirdDTO;
    private List<PropertieDTO> listPropertieDTOs;
    private List<PropertieDTO> listPHPropertieDTOs;
    
    private String expedientePropiedad;
    private String nitPropiedad;
    private String parametros;
    private String casoPropiedad;
    private boolean oathCheckBox;
    
    private boolean showConferenceRecord = true;
    private boolean showPublicDocument = true;
    private boolean showAceptDocument = true;
    private boolean showIdDocumentCopy = true;
    private boolean showCertificateOfTraditionAndFreedom = true;    
    private String requireConferenceRecord = "";
    private String requirePublicDocument = "";
    private String requireAceptDocument = "";
    private String requireIdDocumentCopy = "";
    private String requireCertificateOfTraditionAndFreedom = "";
    
    
    private UploadedFile uploadConferenceRecord;
    private UploadedFile uploadPublicDocument;
    private UploadedFile uploadAceptDocument;
    private UploadedFile uploadIdDocumentCopy;
    private UploadedFile uploadCertificateOfTraditionAndFreedom;
    private UploadedFile uploadDocumentOfFiscaReviewer;
    
    
    public UploadedFile getUploadDocumentOfFiscaReviewer() {
		return uploadDocumentOfFiscaReviewer;
	}


	public void setUploadDocumentOfFiscaReviewer(UploadedFile uploadDocumentOfFiscaReviewer) {
		this.uploadDocumentOfFiscaReviewer = uploadDocumentOfFiscaReviewer;
	}

	private boolean alertShow;
    private boolean alertStyle;
    private String alertMessage;
    
    private Map<String, Object> parameters;
    
    @ManagedProperty(value = "#{indexController}")
    private IndexController indexController;

    @EJB
    WebService ws;
    @EJB
    PHExtincionService phes;
    
    
    public void ExtincionPropiedad()
    {
        
    }
    
    
    @PostConstruct
    public void init()
    {
        try
        {
            this.objThirdDTO = indexController.getObjThirdDTO();
            this.listPropertieDTOs = indexController.getListPropertieDTOs();
            this.listPHPropertieDTOs = indexController.getListPHPropertieDTOs();
            this.expedientePropiedad = indexController.getExpedientPHExtincion();
            this.casoPropiedad = indexController.getCasoSubsanacionExtincion();
            this.nitPropiedad = indexController.getRealStateRegistration();
            this.parametros = indexController.getParametrosSubsanacionExtincion();
            String[] params = parametros.split("-");
            
            for (String param : params)
            {
                if (param.equals("16"))
                {
                    this.showIdDocumentCopy = false;
                    this.requireIdDocumentCopy = "requerido";
                }
                if (param.equals("13"))
                {
                    this.showPublicDocument = false;
                    this.requirePublicDocument = "requerido";
                }
                if (param.equals("14"))
                {
                    this.showCertificateOfTraditionAndFreedom = false;
                    this.requireCertificateOfTraditionAndFreedom = "requerido";
                }
            }
            
        }
        catch (Exception e)
        {
            setAlertShow(true);
            setAlertStyle(false);
            setAlertMessage("Ha ocurrido un error al cargar la pagina de registro por favor comuníquese con el administrador del sistema.");
            // setAlertMessage("Ha ocurrido un error en el sistema por favor comuníquese con el administrador del sistema.");
            log.error("Error al cargar la pagina de registro de PH: " + e.toString());
        }
    }
    
    public String request()
    {
        Date now = new Date();
        SimpleDateFormat formatDay = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String day = formatDay.format(now);
        try
        {
            String radNumber = phes.GetRadicadoNumber(objThirdDTO, listPropertieDTOs);
            if(radNumber!=null && !radNumber.equals(""))
            {
                String appendNumber = phes.AppendExpedient(radNumber, expedientePropiedad, listPropertieDTOs);
                if(appendNumber!=null && !appendNumber.equals(""))
                {
                    int counter=0;
                    String responseFile2 = phes.CreateAppend(radNumber, uploadPublicDocument, listPropertieDTOs, PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs,"escrituraPúblicaExt"));
                    if(responseFile2 == null)
                    {
                    	log.error("Error en la respuesta de crear Anexo #1....");
                        this.setAlertShow(true);
                        this.setAlertStyle(false);
                                                    this.setAlertMessage("Ocurrió un error en la respuesta de crear Anexos en el sistema contacte al administrador del sistema.");
                            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
                        
                    }
                    String responseFile5 = phes.CreateAppend(radNumber, uploadCertificateOfTraditionAndFreedom, listPropertieDTOs, PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs,"certificadoDeTradiciónYLibertadExt"));
                    if(responseFile5 == null)
                    {
                    	log.error("Error en la respuesta de crear Anexo #2....");
                        this.setAlertShow(true);
                        this.setAlertStyle(false);
                                                    this.setAlertMessage("Ocurrió un error en la respuesta de crear Anexos en el sistema contacte al administrador del sistema.");
                            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
                        
                    }
                    
                    String responseFile4 = phes.CreateAppend(radNumber, uploadIdDocumentCopy, listPropertieDTOs, PropertiesHandler.PropertieValueOfIdKeyFromDB(listPHPropertieDTOs, "118"));
                    if(responseFile4 == null)
                    {
                        log.error("Error en la respuesta de crear Anexo #3....");
                        this.setAlertShow(true);
                        this.setAlertStyle(false);
                                                    this.setAlertMessage("Ocurrió un error en la respuesta de crear Anexos en el sistema contacte al administrador del sistema.");
                            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
                        
                    }
                    String responseFile10 = phes.CreateAppend(radNumber, uploadDocumentOfFiscaReviewer, listPropertieDTOs, PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs,"certificadoRevisorFiscal"));
                    if(responseFile10 == null)
                    {
                 	   log.error("Error en la respuesta de crear Anexo #10 Opcional....");
                        this.setAlertShow(true);
                        this.setAlertStyle(false);
                                                    this.setAlertMessage("Ocurrió un error en la respuesta de crear Anexos en el sistema contacte al administrador del sistema.");
                            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
                        counter++;
                    }
                    if(counter == 0)
                    {
                        WebServiceDTO objWebServiceDTO = phes.SubsanarExtincionPH(objThirdDTO, listPropertieDTOs,
                                listPHPropertieDTOs, expedientePropiedad, radNumber, nitPropiedad, casoPropiedad);
                        if(objWebServiceDTO.isRespuesta() && !objWebServiceDTO.getXmlFile().isEmpty())
                        {
                            parameters = new HashMap<>();
                            parameters.put("radicado", radNumber);
                            parameters.put("fecha", day);
                            parameters.put("tramite", "Subsanación de Extinción de Propiedad Horizontal");
                            String doc = ReporteHandler.reporteRadicado(parameters);

                            if (doc != null || !doc.equals(""))
                            {
                                log.info("Se va a Adjuntar el Reporte del radicado");
                                boolean resp = ws.appendDocumentRadicado(listPropertieDTOs, listPHPropertieDTOs, doc, radNumber);

                                if (resp)
                                {
                                    log.info("El Documento se Adjunto Correctamente");  
                                } else {
                                    log.info("Ocurrio un error al adjuntar el reporte"); 
                                }
                            } else {
                                log.info("El Reporte no se Construyo Correctamente"); 
                            }
                            this.setAlertShow(true);
                            String emailResponse = EmailHandler.EnviarMensaje(this.objThirdDTO.getEmail(), "Subsanación de Extinción Propiedad Horizontal",
                                    "Estimado(a) Ciudadano(a) <br><br> Su solicitud de Subsanaci&oacute;n Extinci&oacute;n de Propiedad Horizontal ha sido radicada exitosamente, su n&uacute;mero de radicado es el No " + radNumber + 
                                        ", su solicitud ser&aacute; atendida durante los pr&oacute;ximos 15 d&iacute;as h&aacute;biles. "
                                        + "<br><br><br><p style='font-size:10px'>\"Este correo es de tipo informativo; por favor no responda a este mensaje.</p>", this.listPropertieDTOs);
                            if(!emailResponse.contains("Error"))
                            {
                                indexController.setAlertStylePH(true);
                                indexController.setResultProcessPH("Señor ciudadano su solicitud ha sido radicada exitosamente su" 
                                        + " número de radicado es el No " + radNumber + ", su solicitud será resuelta en un plazo maximo de 15 días hábiles");
                                this.setAlertStyle(true);
                                this.setAlertMessage("Señor ciudadano su solicitud ha sido radicada exitosamente su" 
                                        + " número de radicado es el No " + radNumber + ", su solicitud será resuelta en un plazo maximo de 15 días hábiles");
                                return "phconsultinit";
                            }
                            else
                            {
                                this.setAlertStyle(false);
                                this.setAlertMessage("Error al enviar el email.");
                            }
                        }
                    }
                }
                else
                {
                    log.error("numero del anexo vacio o nulo.");
                    this.setAlertShow(true);
                    this.setAlertStyle(false);
                                                this.setAlertMessage("Ocurrió un error numero del anexo vacio en el sistema contacte al administrador del sistema.");
                            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
                }
                
            }
            else
            {
                log.error("numero de radicado vacio o nulo.");
                this.setAlertShow(true);
                this.setAlertStyle(false);
                                            this.setAlertMessage("Ocurrió un error numero de radicado vacio en el sistema contacte al administrador del sistema.");
                            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
            }
        }
        catch(Exception e)
        {
            log.error("Error en PHExtincionController: " + e.toString());
            this.setAlertShow(true);
            this.setAlertStyle(false);
            this.setAlertMessage("Ocurrió un error al registrar el formulario contacte al administrador del sistema.");
            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
        }
        return null;
    }
    
    
    

    public String getExpedientePropiedad()
    {
        return expedientePropiedad;
    }

    public void setExpedientePropiedad(String expedientePropiedad)
    {
        this.expedientePropiedad = expedientePropiedad;
    }

    public String getNitPropiedad()
    {
        return nitPropiedad;
    }

    public void setNitPropiedad(String nitPropiedad)
    {
        this.nitPropiedad = nitPropiedad;
    }

    public UploadedFile getUploadConferenceRecord()
    {
        return uploadConferenceRecord;
    }

    public void setUploadConferenceRecord(UploadedFile uploadConferenceRecord)
    {
        this.uploadConferenceRecord = uploadConferenceRecord;
    }

    public UploadedFile getUploadPublicDocument()
    {
        return uploadPublicDocument;
    }

    public void setUploadPublicDocument(UploadedFile uploadPublicDocument)
    {
        this.uploadPublicDocument = uploadPublicDocument;
    }

    public UploadedFile getUploadAceptDocument()
    {
        return uploadAceptDocument;
    }

    public void setUploadAceptDocument(UploadedFile uploadAceptDocument)
    {
        this.uploadAceptDocument = uploadAceptDocument;
    }

    public UploadedFile getUploadIdDocumentCopy()
    {
        return uploadIdDocumentCopy;
    }

    public void setUploadIdDocumentCopy(UploadedFile uploadIdDocumentCopy)
    {
        this.uploadIdDocumentCopy = uploadIdDocumentCopy;
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

    public void setIndexController(IndexController indexController)
    {
        this.indexController = indexController;
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

    public List<PropertieDTO> getListPHPropertieDTOs()
    {
        return listPHPropertieDTOs;
    }

    public void setListPHPropertieDTOs(List<PropertieDTO> listPHPropertieDTOs)
    {
        this.listPHPropertieDTOs = listPHPropertieDTOs;
    }

    public UploadedFile getUploadCertificateOfTraditionAndFreedom()
    {
        return uploadCertificateOfTraditionAndFreedom;
    }

    public void setUploadCertificateOfTraditionAndFreedom(UploadedFile uploadCertificateOfTraditionAndFreedom)
    {
        this.uploadCertificateOfTraditionAndFreedom = uploadCertificateOfTraditionAndFreedom;
    }

    public boolean isOathCheckBox()
    {
        return oathCheckBox;
    }

    public void setOathCheckBox(boolean oathCheckBox)
    {
        this.oathCheckBox = oathCheckBox;
    }

    public String getParametros()
    {
        return parametros;
    }

    public void setParametros(String parametros)
    {
        this.parametros = parametros;
    }

    public boolean isShowConferenceRecord()
    {
        return showConferenceRecord;
    }

    public void setShowConferenceRecord(boolean showConferenceRecord)
    {
        this.showConferenceRecord = showConferenceRecord;
    }

    public boolean isShowPublicDocument()
    {
        return showPublicDocument;
    }

    public void setShowPublicDocument(boolean showPublicDocument)
    {
        this.showPublicDocument = showPublicDocument;
    }

    public boolean isShowAceptDocument()
    {
        return showAceptDocument;
    }

    public void setShowAceptDocument(boolean showAceptDocument)
    {
        this.showAceptDocument = showAceptDocument;
    }

    public boolean isShowIdDocumentCopy()
    {
        return showIdDocumentCopy;
    }

    public void setShowIdDocumentCopy(boolean showIdDocumentCopy)
    {
        this.showIdDocumentCopy = showIdDocumentCopy;
    }

    public boolean isShowCertificateOfTraditionAndFreedom()
    {
        return showCertificateOfTraditionAndFreedom;
    }

    public void setShowCertificateOfTraditionAndFreedom(boolean showCertificateOfTraditionAndFreedom)
    {
        this.showCertificateOfTraditionAndFreedom = showCertificateOfTraditionAndFreedom;
    }

    public String getCasoPropiedad()
    {
        return casoPropiedad;
    }

    public void setCasoPropiedad(String casoPropiedad)
    {
        this.casoPropiedad = casoPropiedad;
    }

    public String getRequireConferenceRecord()
    {
        return requireConferenceRecord;
    }

    public void setRequireConferenceRecord(String requireConferenceRecord)
    {
        this.requireConferenceRecord = requireConferenceRecord;
    }

    public String getRequirePublicDocument()
    {
        return requirePublicDocument;
    }

    public void setRequirePublicDocument(String requirePublicDocument)
    {
        this.requirePublicDocument = requirePublicDocument;
    }

    public String getRequireAceptDocument()
    {
        return requireAceptDocument;
    }

    public void setRequireAceptDocument(String requireAceptDocument)
    {
        this.requireAceptDocument = requireAceptDocument;
    }

    public String getRequireIdDocumentCopy()
    {
        return requireIdDocumentCopy;
    }

    public void setRequireIdDocumentCopy(String requireIdDocumentCopy)
    {
        this.requireIdDocumentCopy = requireIdDocumentCopy;
    }

    public String getRequireCertificateOfTraditionAndFreedom()
    {
        return requireCertificateOfTraditionAndFreedom;
    }

    public void setRequireCertificateOfTraditionAndFreedom(String requireCertificateOfTraditionAndFreedom)
    {
        this.requireCertificateOfTraditionAndFreedom = requireCertificateOfTraditionAndFreedom;
    }

    public Map<String, Object> getParameters()
    {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters)
    {
        this.parameters = parameters;
    }
    
    
    
    
    
    
    
}
