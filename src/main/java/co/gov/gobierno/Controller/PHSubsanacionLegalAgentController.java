/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.Controller;

import co.gov.gobierno.DTO.LegalAgentDTO;
import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.DTO.ThirdDTO;
import co.gov.gobierno.DTO.WebServiceDTO;
import co.gov.gobierno.Service.PHExtincionService;
import co.gov.gobierno.Service.PHRegisterService;
import co.gov.gobierno.Service.PHSubsanacionLegalAgentService;
import co.gov.gobierno.Service.WebService;
import co.gov.gobierno.Util.EmailHandler;
import co.gov.gobierno.Util.JsonHandler;
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
public class PHSubsanacionLegalAgentController implements Serializable
{

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(PHSubsanacionLegalAgentController.class);
    private ThirdDTO objThirdDTO;
    private ThirdDTO objThirdDTOLA;
    private List<PropertieDTO> listPropertieDTOs;
    private List<PropertieDTO> listPHPropertieDTOs;

    private String expedientePropiedad;
    private String nitPropiedad;
    private String localidadPropiedad;

    private Map<String, String> valuesIdType;
    private Map<String, String> valuesStratum;
    private Map<String, String> valuesMainStreet;
    private Map<String, String> valuesStreetGeneratingPath;
    private Map<String, String> valuesSpecialCondition;
    private Map<String, String> valuesOccupation;
    private Map<String, String> valuesGender;
    private Map<String, String> valuesNationality;
    private Map<String, String> valuesPresentationType;
    private Map<String, String> valuesidStatusLegalAgent;
    private Map<String, String> valuesidTypeRepLegalAgent;
    private Map<String, Object> parameters;

    

    private boolean alertShow;
    private boolean alertStyle;
    private String alertMessage;
    private boolean oathCheckBox;

    //ATRIBUTOS PARA EL REPRESENTANTE LEGAL-------------------------------------
    private String personType = "";
    private String idTypeLegalAgent = "";
    private String idNumberLegalAgent = "";
    private String firstNameLegalAgent = "";
    private String lastNameLegalAgent = "";
    private String secondNameLegalAgent = "";
    private String secondLastNameLegalAgent = "";
    private String birthDateLegalAgent = "";
    private String genderLegalAgent = "";
    private String nationalityLegalAgent = "";
    private String cellphoneLegalAgent = "";
    private String phoneLegalAgent = "";
    private String addressLegalAgent = "";
    private String ruralAddressLegalAgent = "";
    private String neightborhoodLegalAgent = "";
    private String residenceCityLegalAgent = "";
    private String emailLegalAgent = "";
    private String locationLegalAgent = "";
    private String stratumLegalAgent = "";
    private String specialConditionLegalAgent = "";
    private String occupationLegalAgent = "";
    private String idGenreLegalAgent = "";
    private String idStatusLegalAgent = "";
    private String dateAgent = "";
    private String startAgent = "";
    private String endAgent = "";
    private String numberActAgent = "";
    private String dateActAgent = "";
    private String nameAgentJuridico="";
    private String idNumberAgentJuridico="";
    private String idTypeAgentJuridico="";
    private String razonSocialLegalAgent = "";
    private String idTypeRepLegalAgent = "";
    private boolean disableIdsLegalAgent;
    private String typeDireccionLegalAgent;
    private String etnia = "";
    private String cualEtnia = "";
    private Map<String,String> valuesEtnia;

    private String requireNaturalLegalAgent = "requeridoRepresentante";
    private String requireGeneralLegalAgent = "requeridoRepresentante";
    private String showNaturalLegalAgent = "";
    
    private String requireJuridicLegalAgent = "";
    private String showJuridicLegalAgent = "none";
    
    private String readOnlyNatural = "";
    private String readOnlyJuridic = "";
    private String readOnlyGeneral = "";
    private String nitSearch;
    private String legalAgentSize;

    private boolean modalAlertShow;
    private boolean modalAlertStyle;
    private String modalAlertMessage;
    
    private String parametros;
    private String casoPropiedad;
    private boolean showConferenceRecord = true;
    private boolean showAceptDocument = true;
    private boolean showIdDocumentCopy = true; 
    private String requireConferenceRecord = "";
    private String requireAceptDocument = "";
    private String requireIdDocumentCopy = "";

    private List<LegalAgentDTO> listLegalAgentDTOs;

    //AQUI TERMINAN LOS ATRIBUTOS PARA EL REPRESENTANTE LEGAL-------------------
    private UploadedFile uploadConference;
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

	@ManagedProperty(value = "#{indexController}")
    private IndexController indexController;

    @EJB
    WebService ws;
    @EJB
    PHRegisterService phrs;
    @EJB
    PHExtincionService phes;
    @EJB
    PHSubsanacionLegalAgentService phsla;

    
    public String updateLegalAgent()
    {
        Date now = new Date();
        SimpleDateFormat formatDay = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String day = formatDay.format(now);
        try
        {
        	if (oathCheckBox == false){
            	log.error("La solicitud no puede enviarse porque aún no ha aceptado la declaración bajo gravedad de juramento");
                this.setAlertShow(true);
                this.setAlertStyle(false);
                this.setAlertMessage("La solicitud no puede enviarse porque aún no ha aceptado la declaración bajo gravedad de juramento.");
                return null; 
        	}
        	if (uploadConferenceRecord != null ) 
    		{
        		if (uploadConferenceRecord.getFileName() == "" && requireConferenceRecord == "requeridoRepresentante")
        		{
                	log.error("Debe adjuntar el archivo requerido");
                    this.setAlertShow(true);
                    this.setAlertStyle(false);
                    this.setAlertMessage("Debe adjuntar el archivo requerido, Acta de Asamblea.");
                    return null;        			        			
        		}

    		}
        	if (uploadAceptDocument != null) {
        		if (uploadAceptDocument.getFileName() == "" && requireAceptDocument == "requeridoRepresentante")
        		{
                	log.error("Debe adjuntar el archivo requerido");
                    this.setAlertShow(true);
                    this.setAlertStyle(false);
                    this.setAlertMessage("Debe adjuntar el archivo requerido, Carta de Aceptación.");
                    return null;        			        			
        		}
    		}
        	if (uploadIdDocumentCopy != null) {
        		if (uploadIdDocumentCopy.getFileName() == "" && requireIdDocumentCopy == "requeridoRepresentante")
        		{
                	log.error("Debe adjuntar el archivo requerido");
                    this.setAlertShow(true);
                    this.setAlertStyle(false);
                    this.setAlertMessage("Debe adjuntar el archivo requerido, Cedula de ciudadanía.");
                    return null;        			        			
        		}       			
    		}
        	
        	if (objThirdDTO.getListLegalAgentDTOs().size() <= 0) {
            	log.error("Debe ingresar al menos un Representante");
                this.setAlertShow(true);
                this.setAlertStyle(false);
                this.setAlertMessage("Para Generar la Solicitud, debe ingresar al menos un Representante Legal.");
                return null;
        	}
            /*WebServiceDTO objWebServiceDTO = ws.RegisterLegalAgent(objThirdDTO, listPropertieDTOs, listPHPropertieDTOs,
                    nitSearch, idTypeLegalAgent, idNumberLegalAgent, firstNameLegalAgent, lastNameLegalAgent,
                    secondNameLegalAgent, secondLastNameLegalAgent, cellphoneLegalAgent, phoneLegalAgent, birthDateLegalAgent,
                    addressLegalAgent, "", neightborhoodLegalAgent, emailLegalAgent, dateAgent, startAgent, personType, endAgent,
                    nameAgentJuridico, idNumberAgentJuridico, idTypeAgentJuridico, razonSocialLegalAgent, dateActAgent, numberActAgent, genderLegalAgent,
                    typeDireccionLegalAgent, etnia, cualEtnia);*/
            if (true)
            {
                String radNumber = phrs.GetRadicadoNumber(objThirdDTO, listPropertieDTOs);
                if(radNumber!=null && !radNumber.equals(""))
                {
                    String appendNumber = phes.AppendExpedient(radNumber, expedientePropiedad, listPropertieDTOs);
                    if(appendNumber!=null && !appendNumber.equals(""))
                    {
                        String responseFile1 = phes.CreateAppend(radNumber, uploadConferenceRecord, listPropertieDTOs, PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs,"actaAsambleaOConsejoExt"));
                        int counter=0;
                        if(responseFile1 == null)
                        {
                            log.error("Error en la respuesta de crear Anexo #1....");
                            this.setAlertShow(true);
                            this.setAlertStyle(false);
                                                        this.setAlertMessage("Ocurrió un error en la respuesta de crear Anexos en el sistema contacte al administrador del sistema.");
                            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
                            
                        }
                        String responseFile3 = phes.CreateAppend(radNumber, uploadAceptDocument, listPropertieDTOs, PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs,"documentoDeAceptaciónExt"));
                        if(responseFile3 == null)
                        {
                            log.error("Error en la respuesta de crear Anexo #3....");
                            this.setAlertShow(true);
                            this.setAlertStyle(false);
                                                        this.setAlertMessage("Ocurrió un error en la respuesta de crear Anexos en el sistema contacte al administrador del sistema.");
                            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
                            
                        }
                        String responseFile4 = phes.CreateAppend(radNumber, uploadIdDocumentCopy, listPropertieDTOs, PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs,"documentoDeIdentificaciónExt"));
                        if(responseFile4 == null)
                        {
                            log.error("Error en la respuesta de crear Anexo #4....");
                            this.setAlertShow(true);
                            this.setAlertStyle(false);
                                                        this.setAlertMessage("Ocurrió un error en la respuesta de crear Anexos en el sistema contacte al administrador del sistema.");
                            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
                            
                        }
                        String responseFile6 = phes.CreateAppend(radNumber, uploadConference, listPropertieDTOs, PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs,"actaConsejo"));
                        if(responseFile6 == null)
                        {
                            log.error("Error en la respuesta de crear Anexo #6 Opcional....");
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
                            //counter++;
                        }
                        if(counter == 0)
                        {
                            WebServiceDTO objWebServiceDTOu = phsla.subsanacionLegalAgentPH(objThirdDTO, listPropertieDTOs,
                                    listPHPropertieDTOs, expedientePropiedad, radNumber, nitPropiedad, personType, idTypeLegalAgent,
                                    idNumberLegalAgent, firstNameLegalAgent, lastNameLegalAgent, secondNameLegalAgent, secondLastNameLegalAgent,
                                    phoneLegalAgent, addressLegalAgent, emailLegalAgent, localidadPropiedad, genderLegalAgent, idStatusLegalAgent,
                                    startAgent, endAgent, numberActAgent, dateActAgent, nameAgentJuridico, idNumberAgentJuridico, idTypeAgentJuridico,
                                    idTypeRepLegalAgent, razonSocialLegalAgent, casoPropiedad, typeDireccionLegalAgent);
                            if(objWebServiceDTOu.isRespuesta() && !objWebServiceDTOu.getXmlFile().isEmpty())
                            {
                                parameters = new HashMap<>();
                                parameters.put("radicado", radNumber);
                                parameters.put("fecha", day);
                                parameters.put("tramite", "Subsanación del Representante Legal de Propiedad Horizontal");
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
                                //String emailResponse = EmailHandler.EnviarMensaje(this.objThirdDTO.getEmail(), "Subsanación de Representante Legal de Propiedad Horizontal",
                                //        "Estimado(a) Ciudadano(a) <br><br> Su solicitud de Subsanaci&oacute;n de Representante Legal de Propiedad Horizontal ha sido radicada exitosamente, su n&uacute;mero de radicado es el No " + radNumber + 
                                //        ", su solicitud ser&aacute; atendida durante los pr&oacute;ximos 15 d&iacute;as h&aacute;biles. "
                                //        + "<br><br><br><p style='font-size:10px'>\"Este correo es de tipo informativo; por favor no responda a este mensaje.</p>", this.listPropertieDTOs);
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
                            }else {
                            	setModalAlertShow(true);
                                setModalAlertStyle(false);
                                setModalAlertMessage("No se pudo asociar el representante legal correctamente.");
                            }
                        }
                    }
                    else
                    {
                        log.error("numero del anexo vacio o nulo.");
                        this.setAlertShow(true);
                        this.setAlertStyle(false);
                                                    this.setAlertMessage("Ocurrió un error numero del anexo vacio  en el sistema contacte al administrador del sistema.");
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
            else
            {
                setModalAlertShow(true);
                setModalAlertStyle(false);
                setModalAlertMessage("No se pudo asociar el representante legal correctamente.");
            }
        } catch (Exception e)
        {
            log.error("Error en PHExtincionController: " + e.toString());
            this.setAlertShow(true);
            this.setAlertStyle(false);
            this.setAlertMessage("Ocurrió un error al registrar el formulario contacte al administrador del sistema.");
            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
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
            this.listPHPropertieDTOs = indexController.getListPHPropertieDTOs();
            this.setPersonType("PN");
            this.setListLegalAgentDTOs(this.objThirdDTO.getListLegalAgentDTOs());
            this.expedientePropiedad = indexController.getExpedientPHExtincion();
            this.nitPropiedad = indexController.getRealStateRegistration();
            this.localidadPropiedad = indexController.getLocalidadPH();
            this.casoPropiedad = indexController.getCasoSubsanacionExtincion();
            FillIdTypeValues();
            FillStratumValues();
            FillMainStreetValues();
            FillStreetGeneratingPathValues();
            FillSpecialConditionValues();
            FillOccupationValues();
            FillGenderValues();
            FillNationalityValues();
            FillPresentationTypeValues();
            FillStatusLegalAgent();
            FillTypeRepLegalAgent();
            FillValuesEtnias();
            
            List<LegalAgentDTO> objLegalAgentDTO = objThirdDTO.getListLegalAgentDTOs();
            if (objLegalAgentDTO.size() > 0) {
                if (objLegalAgentDTO.get(0).getPersonType().equals("PN"))
                {
                    this.requireNaturalLegalAgent = "requeridoRepresentante";
                    this.showNaturalLegalAgent = "";
                    this.requireJuridicLegalAgent = "";
                    this.requireGeneralLegalAgent = "";
                    this.showJuridicLegalAgent = "none";
                    this.readOnlyNatural = "true";
                    this.readOnlyJuridic = "";
                    this.readOnlyGeneral = "true";
                }
                if (objLegalAgentDTO.get(0).getPersonType().equals("PJ"))
                {
                    this.requireNaturalLegalAgent = "";
                    this.showNaturalLegalAgent = "none";
                    this.requireJuridicLegalAgent = "requeridoRepresentante";
                    this.showJuridicLegalAgent = "";
                    this.requireGeneralLegalAgent = "";
                    this.readOnlyNatural = "";
                    this.readOnlyJuridic = "true";
                    this.readOnlyGeneral = "true";
                }
                setPersonType(objLegalAgentDTO.get(0).getPersonType());
                setRazonSocialLegalAgent(objLegalAgentDTO.get(0).getRazonSocialLegalAgent());
                setFirstNameLegalAgent(objLegalAgentDTO.get(0).getFirstName());
                setLastNameLegalAgent(objLegalAgentDTO.get(0).getLastName());
                setSecondNameLegalAgent(objLegalAgentDTO.get(0).getSecondName());
                setSecondLastNameLegalAgent(objLegalAgentDTO.get(0).getSecondLastName());
                setIdTypeLegalAgent(objLegalAgentDTO.get(0).getIdType());
                setIdNumberLegalAgent(objLegalAgentDTO.get(0).getIdNumber());
                setGenderLegalAgent(objLegalAgentDTO.get(0).getGender());
                setIdStatusLegalAgent(objLegalAgentDTO.get(0).getState());
                setIdTypeAgentJuridico(objLegalAgentDTO.get(0).getIdTypeAgentJuridico());
                setIdNumberAgentJuridico(objLegalAgentDTO.get(0).getIdNumberAgentJuridico());
                setNameAgentJuridico(objLegalAgentDTO.get(0).getNameAgentJuridico());
                setAddressLegalAgent(objLegalAgentDTO.get(0).getAddress());
                setPhoneLegalAgent(objLegalAgentDTO.get(0).getPhone());
                setEmailLegalAgent(objLegalAgentDTO.get(0).getEmail());
                setNumberActAgent(objLegalAgentDTO.get(0).getActNumber());
                setIdTypeRepLegalAgent(objLegalAgentDTO.get(0).getRepresentType());
                setTypeDireccionLegalAgent(objLegalAgentDTO.get(0).getTypeDireccionLegalAgent());
                String[] date; 
                date = objLegalAgentDTO.get(0).getBeginDate().split("T");
                setStartAgent(date[0]);
                date = objLegalAgentDTO.get(0).getFinishDate().split("T");
                setEndAgent(date[0]);
                date = objLegalAgentDTO.get(0).getDateActNumber().split("T");
                setDateActAgent(date[0]);            	
            	
            }

            this.parametros = indexController.getParametrosSubsanacionExtincion();
            String[] params = parametros.split("-");
            
            for (String param : params)
            {
                if (param.equals("01"))
                {
                    this.showIdDocumentCopy = false;
                    this.requireIdDocumentCopy = "requeridoRepresentante";
                }
                if (param.equals("10"))
                {
                    this.showConferenceRecord = false;
                    this.requireConferenceRecord = "requeridoRepresentante";
                }
                if (param.equals("15"))
                {
                    this.showAceptDocument = false;
                    this.requireAceptDocument = "requeridoRepresentante";
                }
            }
        }
        catch (Exception e)
        {
            setAlertShow(true);
            setAlertStyle(false);
            setAlertMessage("Ha ocurrido un error en el sistema por favor comuníquese con el administrador del sistema.");
            log.info("Error al cargar la pagina de registro de PH: " + e.toString());
        }
    }
    
    public void FindRepresentanteLegal()
    {
        objThirdDTOLA = ws.FindRepresentanteLegal(listPropertieDTOs, getNitSearch(), listLegalAgentDTOs);
        if (objThirdDTOLA != null)
        {  
            List<LegalAgentDTO> objLegalAgentDTO = objThirdDTOLA.getListLegalAgentDTOs();
            if (objLegalAgentDTO.get(0).getPersonType().equals("PN"))
            {
                this.requireNaturalLegalAgent = "requeridoRepresentante";
                this.showNaturalLegalAgent = "";
                this.requireJuridicLegalAgent = "";
                this.requireGeneralLegalAgent = "";
                this.showJuridicLegalAgent = "none";
                this.readOnlyNatural = "true";
                this.readOnlyJuridic = "";
                this.readOnlyGeneral = "true";
            }
            if (objLegalAgentDTO.get(0).getPersonType().equals("PJ"))
            {
                this.requireNaturalLegalAgent = "";
                this.showNaturalLegalAgent = "none";
                this.requireJuridicLegalAgent = "requeridoRepresentante";
                this.showJuridicLegalAgent = "";
                this.requireGeneralLegalAgent = "";
                this.readOnlyNatural = "";
                this.readOnlyJuridic = "true";
                this.readOnlyGeneral = "true";
            }
            setPersonType(objLegalAgentDTO.get(0).getPersonType());
            setRazonSocialLegalAgent(objLegalAgentDTO.get(0).getRazonSocialLegalAgent());
            setFirstNameLegalAgent(objLegalAgentDTO.get(0).getFirstName());
            setLastNameLegalAgent(objLegalAgentDTO.get(0).getLastName());
            setSecondNameLegalAgent(objLegalAgentDTO.get(0).getSecondName());
            setSecondLastNameLegalAgent(objLegalAgentDTO.get(0).getSecondLastName());
            setIdTypeLegalAgent(objLegalAgentDTO.get(0).getIdType());
            setIdNumberLegalAgent(objLegalAgentDTO.get(0).getIdNumber());
            setGenderLegalAgent(objLegalAgentDTO.get(0).getGender());
            setIdStatusLegalAgent(objLegalAgentDTO.get(0).getState());
            setIdTypeAgentJuridico(objLegalAgentDTO.get(0).getIdTypeAgentJuridico());
            setIdNumberAgentJuridico(objLegalAgentDTO.get(0).getIdNumberAgentJuridico());
            setNameAgentJuridico(objLegalAgentDTO.get(0).getNameAgentJuridico());
            setAddressLegalAgent(objLegalAgentDTO.get(0).getAddress());
            setPhoneLegalAgent(objLegalAgentDTO.get(0).getPhone());
            setEmailLegalAgent(objLegalAgentDTO.get(0).getEmail());
            setModalAlertStyle(true);
            setModalAlertShow(true);
            setModalAlertMessage("Se ha encontrado un representante Legal");
        } else {
            this.requireNaturalLegalAgent = "requeridoRepresentante";
            this.requireGeneralLegalAgent = "requeridoRepresentante";
            this.showNaturalLegalAgent = "";
            this.requireJuridicLegalAgent = "";
            this.showJuridicLegalAgent = "none";
            this.readOnlyGeneral = "false";
            this.readOnlyNatural = "";
            this.readOnlyJuridic = "";
            setPersonType("PN");
            setIdTypeLegalAgent("");
            setRazonSocialLegalAgent("");
            setFirstNameLegalAgent("");
            setLastNameLegalAgent("");
            setSecondNameLegalAgent("");
            setSecondLastNameLegalAgent("");
            setIdNumberLegalAgent(getNitSearch());
            setGenderLegalAgent("");
            setIdStatusLegalAgent("");
            setIdTypeAgentJuridico("");
            setIdNumberAgentJuridico("");
            setNameAgentJuridico("");
            setAddressLegalAgent("");
            setPhoneLegalAgent("");
            setEmailLegalAgent("");
            setModalAlertShow(true);
            setModalAlertStyle(false);
            setModalAlertMessage("No se encontro ningún registro en la busqueda del representante legal. Complete el Registro");
        }
    }

    public void FillTypeRepLegalAgent()
    {
        setValuesidTypeRepLegalAgent(indexController.getValuesidTypeRepLegalAgent());
    }

    public void FillStatusLegalAgent()
    {
        setValuesidStatusLegalAgent(indexController.getValuesidStatusLegalAgent());
    }

    public void FillPresentationTypeValues()
    {
        setValuesPresentationType(indexController.getValuesPresentationType());
    }     

    public void FillIdTypeValues()
    {
        indexController.FillIdTypeValues();
        setValuesIdType(indexController.getValuesIdType());
        valuesIdType.remove("TARJETA DE IDENTIDAD");
        valuesIdType.remove("REGISTRO CIVIL DE NACIMIENTO");
    }

    public void FillValuesEtnias()
    {
        setValuesEtnia(indexController.getValuesEtnias());
    }
    
    public void FillStratumValues()
    {
        setValuesStratum(indexController.getValuesStratum());
    }

    public void FillMainStreetValues()
    {
        setValuesMainStreet(indexController.getValuesMainStreet());
    }
    
    public void FillStreetGeneratingPathValues()
    {
        setValuesStreetGeneratingPath(indexController.getValuesStreetGeneratingPath());
    }

    
    public void FillNationalityValues()
    {
        setValuesNationality(indexController.getValuesNationality());
    }

    public void FillGenderValues()
    {
        setValuesGender(indexController.getValuesGender());
    }
    
    public void FillSpecialConditionValues()
    {
        setValuesSpecialCondition(indexController.getValuesSpecialCondition());
    }
    
    public void FillOccupationValues()
    {
        setValuesOccupation(indexController.getValuesOccupation());
    }

    public void setIndexController(IndexController indexController)
    {
        this.indexController = indexController;
    }

    public Map<String, String> getValuesIdType()
    {
        return valuesIdType;
    }

    public void setValuesIdType(Map<String, String> valuesIdType)
    {
        this.valuesIdType = valuesIdType;
    }

    public Map<String, String> getValuesStratum()
    {
        return valuesStratum;
    }

    public void setValuesStratum(Map<String, String> valuesStratum)
    {
        this.valuesStratum = valuesStratum;
    }

    public Map<String, String> getValuesMainStreet()
    {
        return valuesMainStreet;
    }

    public void setValuesMainStreet(Map<String, String> valuesMainStreet)
    {
        this.valuesMainStreet = valuesMainStreet;
    }

    public Map<String, String> getValuesStreetGeneratingPath()
    {
        return valuesStreetGeneratingPath;
    }

    public void setValuesStreetGeneratingPath(Map<String, String> valuesStreetGeneratingPath)
    {
        this.valuesStreetGeneratingPath = valuesStreetGeneratingPath;
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

    public Map<String, String> getValuesOccupation()
    {
        return valuesOccupation;
    }

    public void setValuesOccupation(Map<String, String> valuesOccupation)
    {
        this.valuesOccupation = valuesOccupation;
    }

    public Map<String, String> getValuesGender()
    {
        return valuesGender;
    }

    public void setValuesGender(Map<String, String> valuesGender)
    {
        this.valuesGender = valuesGender;
    }

    public Map<String, String> getValuesNationality()
    {
        return valuesNationality;
    }

    public void setValuesNationality(Map<String, String> valuesNationality)
    {
        this.valuesNationality = valuesNationality;
    }

    public String getIdTypeLegalAgent()
    {
        return idTypeLegalAgent;
    }

    public void setIdTypeLegalAgent(String idTypeLegalAgent)
    {
        this.idTypeLegalAgent = idTypeLegalAgent;
    }

    public String getIdNumberLegalAgent()
    {
        return idNumberLegalAgent;
    }

    public void setIdNumberLegalAgent(String idNumberLegalAgent)
    {
        this.idNumberLegalAgent = idNumberLegalAgent;
    }

    public String getFirstNameLegalAgent()
    {
        return firstNameLegalAgent;
    }

    public void setFirstNameLegalAgent(String firstNameLegalAgent)
    {
        this.firstNameLegalAgent = firstNameLegalAgent;
    }

    public String getLastNameLegalAgent()
    {
        return lastNameLegalAgent;
    }

    public void setLastNameLegalAgent(String lastNameLegalAgent)
    {
        this.lastNameLegalAgent = lastNameLegalAgent;
    }

    public String getSecondNameLegalAgent()
    {
        return secondNameLegalAgent;
    }

    public void setSecondNameLegalAgent(String secondNameLegalAgent)
    {
        this.secondNameLegalAgent = secondNameLegalAgent;
    }

    public String getSecondLastNameLegalAgent()
    {
        return secondLastNameLegalAgent;
    }

    public void setSecondLastNameLegalAgent(String secondLastNameLegalAgent)
    {
        this.secondLastNameLegalAgent = secondLastNameLegalAgent;
    }

    public String getBirthDateLegalAgent()
    {
        return birthDateLegalAgent;
    }

    public void setBirthDateLegalAgent(String birthDateLegalAgent)
    {
        this.birthDateLegalAgent = birthDateLegalAgent;
    }

    public String getGenderLegalAgent()
    {
        return genderLegalAgent;
    }

    public void setGenderLegalAgent(String genderLegalAgent)
    {
        this.genderLegalAgent = genderLegalAgent;
    }

    public String getNationalityLegalAgent()
    {
        return nationalityLegalAgent;
    }

    public void setNationalityLegalAgent(String nationalityLegalAgent)
    {
        this.nationalityLegalAgent = nationalityLegalAgent;
    }

    public String getCellphoneLegalAgent()
    {
        return cellphoneLegalAgent;
    }

    public void setCellphoneLegalAgent(String cellphoneLegalAgent)
    {
        this.cellphoneLegalAgent = cellphoneLegalAgent;
    }

    public String getPhoneLegalAgent()
    {
        return phoneLegalAgent;
    }

    public void setPhoneLegalAgent(String phoneLegalAgent)
    {
        this.phoneLegalAgent = phoneLegalAgent;
    }

    public String getAddressLegalAgent()
    {
        return addressLegalAgent;
    }

    public void setAddressLegalAgent(String addressLegalAgent)
    {
        this.addressLegalAgent = addressLegalAgent;
    }

    public String getRuralAddressLegalAgent()
    {
        return ruralAddressLegalAgent;
    }

    public void setRuralAddressLegalAgent(String ruralAddressLegalAgent)
    {
        this.ruralAddressLegalAgent = ruralAddressLegalAgent;
    }

    public String getNeightborhoodLegalAgent()
    {
        return neightborhoodLegalAgent;
    }

    public void setNeightborhoodLegalAgent(String neightborhoodLegalAgent)
    {
        this.neightborhoodLegalAgent = neightborhoodLegalAgent;
    }

    public String getResidenceCityLegalAgent()
    {
        return residenceCityLegalAgent;
    }

    public void setResidenceCityLegalAgent(String residenceCityLegalAgent)
    {
        this.residenceCityLegalAgent = residenceCityLegalAgent;
    }

    public String getEmailLegalAgent()
    {
        return emailLegalAgent;
    }

    public void setEmailLegalAgent(String emailLegalAgent)
    {
        this.emailLegalAgent = emailLegalAgent;
    }

    public String getLocationLegalAgent()
    {
        return locationLegalAgent;
    }

    public void setLocationLegalAgent(String locationLegalAgent)
    {
        this.locationLegalAgent = locationLegalAgent;
    }

    public String getStratumLegalAgent()
    {
        return stratumLegalAgent;
    }

    public void setStratumLegalAgent(String stratumLegalAgent)
    {
        this.stratumLegalAgent = stratumLegalAgent;
    }

    public String getSpecialConditionLegalAgent()
    {
        return specialConditionLegalAgent;
    }

    public void setSpecialConditionLegalAgent(String specialConditionLegalAgent)
    {
        this.specialConditionLegalAgent = specialConditionLegalAgent;
    }

    public String getOccupationLegalAgent()
    {
        return occupationLegalAgent;
    }

    public void setOccupationLegalAgent(String occupationLegalAgent)
    {
        this.occupationLegalAgent = occupationLegalAgent;
    }

    public boolean isDisableIdsLegalAgent()
    {
        return disableIdsLegalAgent;
    }

    public void setDisableIdsLegalAgent(boolean disableIdsLegalAgent)
    {
        this.disableIdsLegalAgent = disableIdsLegalAgent;
    }

    public String getLegalAgentSize()
    {
        return legalAgentSize;
    }

    public void setLegalAgentSize(String legalAgentSize)
    {
        this.legalAgentSize = legalAgentSize;
    }

    public boolean isModalAlertShow()
    {
        return modalAlertShow;
    }

    public void setModalAlertShow(boolean modalAlertShow)
    {
        this.modalAlertShow = modalAlertShow;
    }

    public boolean isModalAlertStyle()
    {
        return modalAlertStyle;
    }

    public void setModalAlertStyle(boolean modalAlertStyle)
    {
        this.modalAlertStyle = modalAlertStyle;
    }

    public String getModalAlertMessage()
    {
        return modalAlertMessage;
    }

    public void setModalAlertMessage(String modalAlertMessage)
    {
        this.modalAlertMessage = modalAlertMessage;
    }

    public Map<String, String> getValuesSpecialCondition()
    {
        return valuesSpecialCondition;
    }

    public void setValuesSpecialCondition(Map<String, String> valuesSpecialCondition)
    {
        this.valuesSpecialCondition = valuesSpecialCondition;
    }

    public List<LegalAgentDTO> getListLegalAgentDTOs()
    {
        return listLegalAgentDTOs;
    }

    public void setListLegalAgentDTOs(List<LegalAgentDTO> listLegalAgentDTOs)
    {
        this.listLegalAgentDTOs = listLegalAgentDTOs;
    }

    public ThirdDTO getObjThirdDTO()
    {
        return objThirdDTO;
    }

    public void setObjThirdDTO(ThirdDTO objThirdDTO)
    {
        this.objThirdDTO = objThirdDTO;
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

    public UploadedFile getUploadCertificateOfTraditionAndFreedom()
    {
        return uploadCertificateOfTraditionAndFreedom;
    }

    public void setUploadCertificateOfTraditionAndFreedom(UploadedFile uploadCertificateOfTraditionAndFreedom)
    {
        this.uploadCertificateOfTraditionAndFreedom = uploadCertificateOfTraditionAndFreedom;
    }

    public List<PropertieDTO> getListPropertieDTOs()
    {
        return listPropertieDTOs;
    }

    public void setListPropertieDTOs(List<PropertieDTO> listPropertieDTOs)
    {
        this.listPropertieDTOs = listPropertieDTOs;
    }

    public Map<String, String> getValuesPresentationType()
    {
        return valuesPresentationType;
    }

    public void setValuesPresentationType(Map<String, String> valuesPresentationType)
    {
        this.valuesPresentationType = valuesPresentationType;
    }

    public String getIdGenreLegalAgent()
    {
        return idGenreLegalAgent;
    }

    public void setIdGenreLegalAgent(String idGenreLegalAgent)
    {
        this.idGenreLegalAgent = idGenreLegalAgent;
    }

    public String getIdStatusLegalAgent()
    {
        return idStatusLegalAgent;
    }

    public void setIdStatusLegalAgent(String idStatusLegalAgent)
    {
        this.idStatusLegalAgent = idStatusLegalAgent;
    }

    public String getDateAgent()
    {
        return dateAgent;
    }

    public void setDateAgent(String dateAgent)
    {
        this.dateAgent = dateAgent;
    }

    public String getStartAgent()
    {
        return startAgent;
    }

    public void setStartAgent(String startAgent)
    {
        this.startAgent = startAgent;
    }

    public String getEndAgent()
    {
        return endAgent;
    }

    public void setEndAgent(String endAgent)
    {
        this.endAgent = endAgent;
    }

    public String getNumberActAgent()
    {
        return numberActAgent;
    }

    public void setNumberActAgent(String numberActAgent)
    {
        this.numberActAgent = numberActAgent;
    }

    public String getDateActAgent()
    {
        return dateActAgent;
    }

    public void setDateActAgent(String dateActAgent)
    {
        this.dateActAgent = dateActAgent;
    }

    public String getNameAgentJuridico()
    {
        return nameAgentJuridico;
    }

    public void setNameAgentJuridico(String nameAgentJuridico)
    {
        this.nameAgentJuridico = nameAgentJuridico;
    }

    public String getIdNumberAgentJuridico()
    {
        return idNumberAgentJuridico;
    }

    public void setIdNumberAgentJuridico(String idNumberAgentJuridico)
    {
        this.idNumberAgentJuridico = idNumberAgentJuridico;
    }

    public String getIdTypeAgentJuridico()
    {
        return idTypeAgentJuridico;
    }

    public void setIdTypeAgentJuridico(String idTypeAgentJuridico)
    {
        this.idTypeAgentJuridico = idTypeAgentJuridico;
    }

    public Map<String, String> getValuesidStatusLegalAgent()
    {
        return valuesidStatusLegalAgent;
    }

    public void setValuesidStatusLegalAgent(Map<String, String> valuesidStatusLegalAgent)
    {
        this.valuesidStatusLegalAgent = valuesidStatusLegalAgent;
    }

    public String getPersonType()
    {
        return personType;
    }

    public void setPersonType(String personType)
    {
        this.personType = personType;
    }

    public List<PropertieDTO> getListPHPropertieDTOs()
    {
        return listPHPropertieDTOs;
    }

    public void setListPHPropertieDTOs(List<PropertieDTO> listPHPropertieDTOs)
    {
        this.listPHPropertieDTOs = listPHPropertieDTOs;
    }

    public String getRazonSocialLegalAgent()
    {
        return razonSocialLegalAgent;
    }

    public void setRazonSocialLegalAgent(String razonSocialLegalAgent)
    {
        this.razonSocialLegalAgent = razonSocialLegalAgent;
    }

    public Map<String, String> getValuesidTypeRepLegalAgent()
    {
        return valuesidTypeRepLegalAgent;
    }

    public void setValuesidTypeRepLegalAgent(Map<String, String> valuesidTypeRepLegalAgent)
    {
        this.valuesidTypeRepLegalAgent = valuesidTypeRepLegalAgent;
    }

    public String getIdTypeRepLegalAgent()
    {
        return idTypeRepLegalAgent;
    }

    public void setIdTypeRepLegalAgent(String idTypeRepLegalAgent)
    {
        this.idTypeRepLegalAgent = idTypeRepLegalAgent;
    }

    public ThirdDTO getObjThirdDTOLA()
    {
        return objThirdDTOLA;
    }

    public void setObjThirdDTOLA(ThirdDTO objThirdDTOLA)
    {
        this.objThirdDTOLA = objThirdDTOLA;
    }

    public String getRequireNaturalLegalAgent()
    {
        return requireNaturalLegalAgent;
    }

    public void setRequireNaturalLegalAgent(String requireNaturalLegalAgent)
    {
        this.requireNaturalLegalAgent = requireNaturalLegalAgent;
    }

    public String getRequireGeneralLegalAgent()
    {
        return requireGeneralLegalAgent;
    }

    public void setRequireGeneralLegalAgent(String requireGeneralLegalAgent)
    {
        this.requireGeneralLegalAgent = requireGeneralLegalAgent;
    }

    public String getShowNaturalLegalAgent()
    {
        return showNaturalLegalAgent;
    }

    public void setShowNaturalLegalAgent(String showNaturalLegalAgent)
    {
        this.showNaturalLegalAgent = showNaturalLegalAgent;
    }

    public String getRequireJuridicLegalAgent()
    {
        return requireJuridicLegalAgent;
    }

    public void setRequireJuridicLegalAgent(String requireJuridicLegalAgent)
    {
        this.requireJuridicLegalAgent = requireJuridicLegalAgent;
    }

    public String getShowJuridicLegalAgent()
    {
        return showJuridicLegalAgent;
    }

    public void setShowJuridicLegalAgent(String showJuridicLegalAgent)
    {
        this.showJuridicLegalAgent = showJuridicLegalAgent;
    }

    public String getReadOnlyNatural()
    {
        return readOnlyNatural;
    }

    public void setReadOnlyNatural(String readOnlyNatural)
    {
        this.readOnlyNatural = readOnlyNatural;
    }

    public String getReadOnlyJuridic()
    {
        return readOnlyJuridic;
    }

    public void setReadOnlyJuridic(String readOnlyJuridic)
    {
        this.readOnlyJuridic = readOnlyJuridic;
    }

    public String getReadOnlyGeneral()
    {
        return readOnlyGeneral;
    }

    public void setReadOnlyGeneral(String readOnlyGeneral)
    {
        this.readOnlyGeneral = readOnlyGeneral;
    }

    public String getNitSearch()
    {
        return nitSearch;
    }

    public void setNitSearch(String nitSearch)
    {
        this.nitSearch = nitSearch;
    }

    public boolean isOathCheckBox()
    {
        return oathCheckBox;
    }

    public void setOathCheckBox(boolean oathCheckBox)
    {
        this.oathCheckBox = oathCheckBox;
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

    public String getLocalidadPropiedad()
    {
        return localidadPropiedad;
    }

    public void setLocalidadPropiedad(String localidadPropiedad)
    {
        this.localidadPropiedad = localidadPropiedad;
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

    public String getRequireConferenceRecord()
    {
        return requireConferenceRecord;
    }

    public void setRequireConferenceRecord(String requireConferenceRecord)
    {
        this.requireConferenceRecord = requireConferenceRecord;
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

    public String getCasoPropiedad()
    {
        return casoPropiedad;
    }

    public void setCasoPropiedad(String casoPropiedad)
    {
        this.casoPropiedad = casoPropiedad;
    }

    public Map<String, Object> getParameters()
    {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters)
    {
        this.parameters = parameters;
    }

    public String getTypeDireccionLegalAgent()
    {
        return typeDireccionLegalAgent;
    }

    public void setTypeDireccionLegalAgent(String typeDireccionLegalAgent)
    {
        this.typeDireccionLegalAgent = typeDireccionLegalAgent;
    }

    public UploadedFile getUploadConference()
    {
        return uploadConference;
    }

    public void setUploadConference(UploadedFile uploadConference)
    {
        this.uploadConference = uploadConference;
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
