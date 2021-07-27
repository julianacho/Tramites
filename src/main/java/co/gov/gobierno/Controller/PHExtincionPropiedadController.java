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
public class PHExtincionPropiedadController implements Serializable
{
    
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(PHExtincionPropiedadController.class);
    
    
    private ThirdDTO objThirdDTO;
    private List<PropertieDTO> listPropertieDTOs;
    private List<PropertieDTO> listPHPropertieDTOs;
    
    private String expedientePropiedad;
    private String nitPropiedad;
    private boolean oathCheckBox;
    
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
    
    private Map<String, Object> parametros;
    
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
            this.nitPropiedad = indexController.getRealStateRegistration();
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
                        counter++;
                    }
                    String responseFile5 = phes.CreateAppend(radNumber, uploadCertificateOfTraditionAndFreedom, listPropertieDTOs, PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs,"certificadoDeTradiciónYLibertadExt"));
                    if(responseFile5 == null)
                    {
                    	log.error("Error en la respuesta de crear Anexo #2....");
                        this.setAlertShow(true);
                        this.setAlertStyle(false);
                            this.setAlertMessage("Ocurrió un error en la respuesta de crear Anexos en el sistema contacte al administrador del sistema.");
                            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
                        counter++;
                    }
                    
                    String responseFile4 = phes.CreateAppend(radNumber, uploadIdDocumentCopy, listPropertieDTOs, PropertiesHandler.PropertieValueOfIdKeyFromDB(listPHPropertieDTOs, "118"));
                    if(responseFile4 == null)
                    {
                    	log.error("Error en la respuesta de crear Anexo #3....");
                        this.setAlertShow(true);
                        this.setAlertStyle(false);
                            this.setAlertMessage("Ocurrió un error en la respuesta de crear Anexos en el sistema contacte al administrador del sistema.");
                            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
                        counter++;
                    }
                    String responseFile10 = phes.CreateAppend(radNumber, uploadDocumentOfFiscaReviewer, listPropertieDTOs, PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs,"certificadoRevisorFiscal"));
                    if(responseFile10 == null)
                    {
                 	   log.error("Error en la respuesta de crear Anexo #10 Opcional....");
                        this.setAlertShow(true);
                        this.setAlertStyle(false);
                            this.setAlertMessage("Ocurrió un error en la respuesta de crear Anexos en el sistema contacte al administrador del sistema.");
                            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
                        //counter++;
                    }
                    if(counter == 0)
                    {
                        WebServiceDTO objWebServiceDTO = phes.ExtinguirPH(objThirdDTO, listPropertieDTOs,
                                listPHPropertieDTOs, expedientePropiedad, radNumber, nitPropiedad);
                        if(objWebServiceDTO.isRespuesta() && !objWebServiceDTO.getXmlFile().isEmpty())
                        {
                            parametros = new HashMap<>();
                            parametros.put("radicado", radNumber);
                            parametros.put("fecha", day);
                            parametros.put("tramite", "Creación de Extinción de Propiedad Horizontal");
                            String doc = ReporteHandler.reporteRadicado(parametros);

                            if (doc != null || !doc.equals(""))
                            {
                            	log.info("Se va a Adjuntar el Reporte del radicado");
                                boolean resp = ws.appendDocumentRadicado(listPropertieDTOs, listPHPropertieDTOs, doc, radNumber);

                                if (resp)
                                {
                                	log.info("El Documento se Adjunto Correctamente");  
                                } else {
                                	log.error("Ocurrio un error al adjuntar el reporte"); 
                                }
                            } else {
                            	log.error("El Reporte no se Construyo Correctamente"); 
                            }
                            this.setAlertShow(true);
                            //String emailResponse = EmailHandler.EnviarMensaje(this.objThirdDTO.getEmail(), "Creación de Extinción de Propiedad Horizontal",
                            //        "Estimado(a) Ciudadano(a) <br><br> Su solicitud de Extinci&oacute;n de Propiedad Horizontal ha sido radicada exitosamente, su n&uacute;mero de radicado es el No " + radNumber + 
                            //            ", su solicitud ser&aacute; atendida durante los pr&oacute;ximos 15 d&iacute;as h&aacute;biles. "
                            //            + "<br><br><br><p style='font-size:10px'>\"Este correo es de tipo informativo; por favor no responda a este mensaje.</p>", this.listPropertieDTOs);
                            //if(!emailResponse.contains("Error"))
                            //{
                                indexController.setAlertStylePH(true);
                                indexController.setResultProcessPH("Señor ciudadano su solicitud ha sido radicada exitosamente su" 
                                            + " número de radicado es el No " + radNumber + ", su solicitud será resuelta en un plazo maximo de 15 días hábiles");
                                this.setAlertStyle(true);
                                this.setAlertMessage("Señor ciudadano su solicitud ha sido radicada exitosamente su" 
                                        + " número de radicado es el No " + radNumber + ", su solicitud será resuelta en un plazo maximo de 15 días hábiles");
                                return "phconsultinit";
                            //}
                            //else
                            //{
                            //    this.setAlertStyle(false);
                            //    this.setAlertMessage("Error al enviar el email.");
                            //}
                        }
                    }
                }
                else
                {
                	log.error("numero del anexo vacio o nulo.");
                    this.setAlertShow(true);
                    this.setAlertStyle(false);
                    this.setAlertMessage("Ocurrió un error numero del anexo vacio contacte al administrador del sistema.");
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

    public Map<String, Object> getParametros()
    {
        return parametros;
    }

    public void setParametros(Map<String, Object> parametros)
    {
        this.parametros = parametros;
    }    
    
    
}
