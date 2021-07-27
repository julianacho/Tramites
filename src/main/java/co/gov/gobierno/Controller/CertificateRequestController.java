/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.Controller;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.LoggerFactory;

import co.gov.gobierno.DTO.AyudaDTO;
import co.gov.gobierno.DTO.LegalAgentDTO;
import co.gov.gobierno.DTO.LocationDTO;
import co.gov.gobierno.DTO.LocationExpedientDTO;
import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.DTO.ThirdDTO;
import co.gov.gobierno.DTO.WebServiceDTO;
import co.gov.gobierno.DTO.YoungerDTO;
import co.gov.gobierno.Service.CertificateRequestService;
import co.gov.gobierno.Service.WebService;
import co.gov.gobierno.Util.EmailHandler;
import co.gov.gobierno.Util.LocationExpedientHandler;
import co.gov.gobierno.Util.PropertiesHandler;
import co.gov.gobierno.Util.ReporteHandler;
import co.gov.gobierno.Util.ValidUtility;

/**
 *
 * @author DELL
 */
@ManagedBean
@RequestScoped
public class CertificateRequestController implements Serializable
{

    private static long serialVersionUID = 1L;
    
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(CertificateRequestController.class);
    private ThirdDTO objThirdDTO;
    private ThirdDTO objThirdDTOPL;
    private List<PropertieDTO> listPropertieDTOs;
    private List<LocationExpedientDTO> listLocationExpedientDTOs;
    private List<YoungerDTO> listYoungerDto;
    private List<LegalAgentDTO> listPrivadoLibertadDTOs;
    private ReporteHandler rep = new ReporteHandler();

    private UploadedFile uploadedFileAttendant;
    private UploadedFile uploadedFilePublicService;
    private UploadedFile uploadedFileYounger;
    private UploadedFile uploadedFileCustody;

    private String oldRadNumber;
    private String certificateKey;
    private String certificateKey2;
    private String atentionDate;
    private boolean correct;
    private boolean disableYounger;
    private boolean disableProcedureType;
    private boolean disableExternalRelation;

    private String idType = "";
    private String firstName = "";
    private String lastName = "";
    private String idNumber = "";
    private String secondName = "";
    private String secondLastName = "";
    private String address = "";
    private String email = "";
    private String younger;
    private String procedureType;
    private String related;
    private String externalRelation;
    private String typeDireccion;
    private boolean disableRelated;
    private boolean disablePublicBill;
    private boolean disableYoungerDocument;
    private boolean disableCustody;
    private boolean disableDocumentIdentity;
    private boolean oathCheckBox;
    private boolean alertStyle;
    private boolean alertMessage;
    private String alertText;
    private File document;
    private Map<String, String> valuesProcedureType;
    private Map<String, String> valuesRelated;

    //Atributos para el menor de edad.
    private String idTypeYounger = "";
    private String idNumberYounger = "";
    private String firstNameYounger = "";
    private String lastNameYounger = "";
    private String secondNameYounger = "";
    private String secondLastNameYounger = "";
    private String birthDateYounger;
    private String genderYounger = "";
    private String sexoYounger = "";
    private String sexualOrientYounger = "";
    private String nationalityYounger = "";
    private String cellphoneYounger = "";
    private String phoneYounger = "";
    private String addressYounger = "";
    private String addressComplYounger = "";
    private String ruralAddressYounger = "";
    private String neightborhoodYounger = "";
    private String residenceCityYounger = "";
    private String emailYounger = "";
    private String locationYounger = "";
    private String stratumYounger = "";
    private String specialConditionYounger = "";
    private String occupationYounger = "";
    private boolean disableIdsYounger;
    private String etnia;
    private String cualEtnia;

    private String youngerSize = "";
    
    //ATRIBUTOS PARA EL PRIVADO DE LIBERTAD
    private String personType = "";
    private String idTypePrivado;
    private String idNumberPrivado = "";
    private String firstNamePrivado = "";
    private String lastNamePrivado = "";
    private String secondNamePrivado = "";
    private String secondLastNamePrivado = "";
    private String birthDatePrivado = "";
    private String genderPrivado = "";
    private String nationalityPrivado = "";
    private String cellphonePrivado = "";
    private String phonePrivado = "";
    private String addressPrivado = "";
    private String addressComplPrivado = "";
    private String ruralAddressPrivado = "";
    private String neightborhoodPrivado = "";
    private String residenceCityPrivado = "";
    private String emailPrivado = "";
    private String locationPrivado = "";
    private String stratumPrivado = "";
    private String specialConditionPrivado = "";
    private String occupationPrivado = "";
    private String idGenrePrivado = "";
    private String idStatusPrivado = "";
    private String dateAgent = "";
    private String startAgent = "";
    private String endAgent = "";
    private String numberActAgent = "";
    private String dateActAgent = "";
    private String nameAgentJuridico="";
    private String idNumberAgentJuridico="";
    private String idTypeAgentJuridico="";
    private String razonSocialPrivado = "";
    private String idTypeRepPrivado = "";
    private String requireNaturalPrivado = "requeridoRepresentante";
    private String requireGeneralPrivado = "requeridoRepresentante";
    private String showNaturalPrivado = "";
    private String typeDireccionPrivado = "1";
    private String nitSearch = "";
    private String etniaPrivado;
    private String cualEtniaPrivado;

    
    private String readOnlyGeneral = "";
    private boolean modalAlertShowPrivado = false;
    private boolean modalAlertStylePrivado = false;
    private String modalAlertMessagePrivado = "";
    
    private boolean modalAlertShow = false;
    private boolean modalAlertStyle = false;
    private String modalAlertMessage = "";
    private boolean infoPanelShow = false;
    private String infoPanelMessage = "";

    private List<LocationDTO> valuesLocation;
    private Map<String, String> valuesStratum;
    private Map<String, String> valuesSpecialCondition;
    private Map<String, String> valuesMainStreet;
    private Map<String, String> valuesStreetGeneratingPath;
    private Map<String, String> valuesOccupation;
    private Map<String, String> valuesGender;
    private List<LocationDTO> valuesNeightborhood;
    private List<LocationDTO> valuesResidenceCity;
    private Map<String, String> valuesNationality;
    private Map<String, String> valuesIdTypeYounger;
    private Map<String, String> valuesSexualOrient;
    private Map<String, String> valuesSex;
    private Map<String, Object> parametros;
    private Map<String, String> valuesEtnias;
    
    private Map<String, String> valuesIdTypePrivado;
    private Map<String, String> valuesidStatusPrivado;

    private List<AyudaDTO> listAyudaDTO;
    private Map<String, String> MapAyudaDB;
    private String UrlVideoRegistro;
    private String UrlVideoRegistro2;

    @ManagedProperty(value = "#{indexController}")
    private IndexController indexController;

    @EJB
    WebService ws;
    @EJB
    CertificateRequestService crs;

    @PostConstruct
    public void Init()
    {
        try
        {
            this.objThirdDTO = indexController.getObjThirdDTO();
            this.listYoungerDto = objThirdDTO.getListYoungerDTO();
            this.listPrivadoLibertadDTOs = objThirdDTO.getListLegalAgentDTOs();
            this.setYoungerSize(Integer.toString(this.listYoungerDto.size()));
            this.listPropertieDTOs = indexController.getListPropertieDTOs();
            this.listLocationExpedientDTOs = LocationExpedientHandler.GetLocationExpedientListFromDB();
            this.setIdType(this.objThirdDTO.getIdType());
            this.setIdNumber(this.objThirdDTO.getIdNumber());
            this.setFirstName(this.objThirdDTO.getFirstName());
            this.setSecondName(this.objThirdDTO.getSecondName());
            this.setLastName(this.objThirdDTO.getLastName());
            this.setSecondLastName(this.objThirdDTO.getSecondLastName());
            if (this.objThirdDTO.getAddress().equals(""))
            {
                this.setAddress(this.objThirdDTO.getRuralAddress());
            } else
            {
                this.setAddress(this.objThirdDTO.getAddress() +" "+ this.objThirdDTO.getAddressCompl());
            }
            this.setEmail(this.objThirdDTO.getEmail());
            this.disableRelated = true;
            this.disablePublicBill = true;
            this.disableYoungerDocument = true;
            this.disableCustody = true;
            this.disableIdsYounger = false;
            this.certificateKey = "";
            this.typeDireccion = "1";
            FillRelatedValues();
            FillProcedureTypeValues();
            FillIdTypeYoungerValues();
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
            FillStatusPrivado();
            FillIdTypePrivadoValues();
            FillValuesEtnias();

            this.listAyudaDTO = PropertiesHandler.GetAyudaDB();
            this.MapAyudaDB = PropertiesHandler.AyudaDB(listAyudaDTO, "CrMenorEdad");
            if (MapAyudaDB.get("vTipo").equals("1"))
            {
                 this.setUrlVideoRegistro("<iframe width='600' height='315'  src='"+MapAyudaDB.get("vUrl")+"' frameborder='0' allow='accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture' allowfullscreen></iframe>");
            }
            if (MapAyudaDB.get("vTipo").equals("2"))
            {
                this.setUrlVideoRegistro("<img width='600' height='315' src='" + MapAyudaDB.get("vUrl") + "'/>");
            }
            
             this.MapAyudaDB = PropertiesHandler.AyudaDB(listAyudaDTO, "CertificadoResidencia");
            if (MapAyudaDB.get("vTipo").equals("1"))
            {
                 this.setUrlVideoRegistro2("<iframe width='600' height='315'  src='"+MapAyudaDB.get("vUrl")+"' frameborder='0' allow='accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture' allowfullscreen></iframe>");
            }
            if (MapAyudaDB.get("vTipo").equals("2"))
            {
                this.setUrlVideoRegistro2("<img width='600' height='315' src='" + MapAyudaDB.get("vUrl") + "'/>");
            }
        } catch (Exception ex)
        {
            setAlertMessage(true);
            setAlertStyle(false);
            setAlertText("Ha ocurrido un error inesperado. Contacte al administrador del sistema e intentelo más tarde.");
            log.error("Error al construir esta clase: " + ex.toString());
            //Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, ex.toString(), ex);
        }
    }
    
    /*@PreDestroy
    public void finish()
    {
        this.oldRadNumber=null;
    }*/
    public void Request() throws IOException
    {

        Date now = new Date();
        SimpleDateFormat formatDay = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String day = formatDay.format(now);
        boolean isExtensionPermited = true;
        if(!procedureType.equals("391"))
        {
            listPrivadoLibertadDTOs.clear();
        }
        
		/*
		 * if(this.getUploadedFileAttendant() != null &&
		 * !this.getUploadedFileAttendant().getFileName().equals("")) { String extension
		 * = this.getUploadedFileAttendant().getFileName().split(".")[1];
		 * if(extension.equals("jpeg")) { isExtensionPermited = false; } }
		 * 
		 * if(this.getUploadedFileCustody() != null &&
		 * !this.getUploadedFileCustody().getFileName().equals("")) { String extension =
		 * this.getUploadedFileCustody().getFileName().split(".")[1];
		 * if(extension.equals("jpeg")) { isExtensionPermited = false; } }
		 * 
		 * if(this.getUploadedFilePublicService() != null &&
		 * !this.getUploadedFilePublicService().getFileName().equals("")) { String
		 * extension = this.getUploadedFilePublicService().getFileName().split(".")[1];
		 * if(extension.equals("jpeg")) { isExtensionPermited = false; } }
		 * 
		 * if(this.getUploadedFileYounger() != null &&
		 * !this.getUploadedFileYounger().getFileName().equals("")) { String extension =
		 * this.getUploadedFileYounger().getFileName().split(".")[1];
		 * if(extension.equals("jpeg")) { isExtensionPermited = false; } }
		 */
        
        boolean errorYouger=false;
        for (YoungerDTO objYoungerDTO : this.listYoungerDto)
        {
            if (objYoungerDTO!=null)
            {
                if(!ValidUtility.validateIdentificacion(objYoungerDTO.getIdNumber(), objYoungerDTO.getIdType())) {
                	errorYouger=true;
                }
            }
        }
        if(errorYouger) {
        	setAlertMessage(true);
            setAlertStyle(false);
            setAlertText("El campo número de Idenfificacion contiene letras y no esta permitido para el tipo de documento seleccionado para el menor de edad registrado");
            return;
        }
        
        
        boolean errorPrivadoLibertad=false;
        for (LegalAgentDTO legalAgentDTO : this.listPrivadoLibertadDTOs)
        {
            if (legalAgentDTO!=null)
            {
                if(!ValidUtility.validateIdentificacion(legalAgentDTO.getIdNumber(), legalAgentDTO.getIdType())) {
                	errorPrivadoLibertad=true;
                }
            }
        }
        if(errorPrivadoLibertad) {
        	setAlertMessage(true);
            setAlertStyle(false);
            setAlertText("El campo número de Idenfificacion contiene letras y no esta permitido para el tipo de documento seleccionado para el privado de la libertdad registrado");
            return;
        }
        
        
        
        
        if (isExtensionPermited)
        {
            try
            {
                WebServiceDTO objWebServiceDTO = new WebServiceDTO();

                int count = 0;
                for (YoungerDTO objYoungerDTO : this.listYoungerDto)
                {
                    if (objYoungerDTO.isSelect())
                    {
                        count++;
                    }
                }
                if ((this.younger.equals("1") && count == 1) || this.younger.equals("0"))
                {
                    boolean executeAdax = Boolean.parseBoolean(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "executeADAX"));
                    SimpleDateFormat today = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    if (this.correct == false)
                    {
                        String radicadoNumber = null;
                        if (executeAdax)
                        {
                            radicadoNumber = crs.getRadicadoNumber(objWebServiceDTO, this.listPropertieDTOs);
                        } else
                        {
                            radicadoNumber = crs.getRadicadoNumberOrfeo(objThirdDTO, listPropertieDTOs);
                        }
                        if (radicadoNumber != null && !radicadoNumber.equals(""))
                        {
                            if (executeAdax)
                            {
                                objWebServiceDTO = crs.Request(objWebServiceDTO, this.objThirdDTO, radicadoNumber, this.listPropertieDTOs, this.uploadedFileAttendant,
                                        this.uploadedFilePublicService, this.uploadedFileYounger, this.uploadedFileCustody);
                            } else
                            {
                                String exp = LocationExpedientHandler.getExpedientValueFromLocationList(listLocationExpedientDTOs, this.objThirdDTO.getLocation());
                                objWebServiceDTO = crs.AppendExpedient(radicadoNumber, exp, listPropertieDTOs);
                            }
                            if (objWebServiceDTO.isRespuesta())
                            {
                                int counter = 0;
                                WebServiceDTO objWebServiceDTO2 = new WebServiceDTO();
                                if (executeAdax)
                                {
                                    String tags[] = crs.getFilesTag(objWebServiceDTO.getXmlFile());
                                    objWebServiceDTO2 = crs.SecondRequest(objWebServiceDTO2, this.objThirdDTO, radicadoNumber, this.procedureType,
                                            this.externalRelation, this.listPropertieDTOs, this.related, this.younger, tags[0], tags[1], this.uploadedFileAttendant,
                                            this.uploadedFilePublicService, this.uploadedFileYounger, this.uploadedFileCustody, this.externalRelation, this.listPrivadoLibertadDTOs);
                                } else
                                {
                                    objWebServiceDTO2 = crs.SecondRequest(objWebServiceDTO2, this.objThirdDTO, radicadoNumber, this.procedureType,
                                            this.externalRelation, this.listPropertieDTOs, this.related, this.younger, "", "", this.uploadedFileAttendant,
                                            this.uploadedFilePublicService, this.uploadedFileYounger, this.uploadedFileCustody, this.externalRelation, this.listPrivadoLibertadDTOs);
                                    String responseFile1 = crs.CreateAppend(radicadoNumber, uploadedFileAttendant, listPropertieDTOs, PropertiesHandler.PropertieValueOfIdKeyFromDB(listPropertieDTOs, "81"), "DocIdentSol");
                                    if (responseFile1 == null)
                                    {
                                        System.err.println("Error en la respuesta de crear Anexo #1....");
                                        this.setAlertMessage(true);
                                        this.setAlertStyle(false);
                                        this.setAlertText(responseFile1);
                                        counter++;
                                    }
                                    String responseFile2 = crs.CreateAppend(radicadoNumber, uploadedFilePublicService, listPropertieDTOs, PropertiesHandler.PropertieValueOfIdKeyFromDB(listPropertieDTOs, "82"), "RecServPub");
                                    if (responseFile2 == null)
                                    {
                                        System.err.println("Error en la respuesta de crear Anexo #2....");
                                        this.setAlertMessage(true);
                                        this.setAlertStyle(false);
                                        this.setAlertText(responseFile2);
                                        counter++;
                                    }
                                    String responseFile3 = crs.CreateAppend(radicadoNumber, uploadedFileYounger, listPropertieDTOs, PropertiesHandler.PropertieValueOfIdKeyFromDB(listPropertieDTOs, "83"), "DocIdentMen");
                                    if (responseFile3 == null)
                                    {
                                        System.err.println("Error en la respuesta de crear Anexo #3....");
                                        this.setAlertMessage(true);
                                        this.setAlertStyle(false);
                                        this.setAlertText(responseFile3);
                                        counter++;
                                    }
                                    //Si es Menor de edad y PERSONA CON PATRIA POTESTAD=3
                                    if(younger.equals("1") && related.equals("3")) {
                                    	String responseFile4 = crs.CreateAppend(radicadoNumber, uploadedFileCustody, listPropertieDTOs, PropertiesHandler.PropertieValueOfIdKeyFromDB(listPropertieDTOs, "84"), "DocPatPost");
                                        if (responseFile4 == null)
                                        {
                                            System.err.println("Error en la respuesta de crear Anexo #4....");
                                            this.setAlertMessage(true);
                                            this.setAlertStyle(false);
                                            this.setAlertText(responseFile4);
                                            counter++;
                                        }
                                    	
                                    }
                                    
                                }
                                if (objWebServiceDTO2.isRespuesta() && counter < 3)
                                {
                                    setAlertMessage(true);
                                    setAlertStyle(true);
                                    setAlertText("El certificado se ha solicitado con éxito.");

                                    parametros = new HashMap<>();
                                    parametros.put("radicado", radicadoNumber);
                                    parametros.put("fecha", day);
                                    parametros.put("tramite", "Creación de Solicitud de Certificado de Residencia");
                                    String doc = rep.reporteRadicado(parametros);

                                    if (doc != null || !doc.equals(""))
                                    {
                                        log.info("Se va a Adjuntar el Reporte del radicado");
                                        boolean resp = ws.appendDocumentRadicado(listPropertieDTOs, listPropertieDTOs, doc, radicadoNumber);

                                        if (resp)
                                        {
                                        	log.info("El Documento se Adjunto Correctamente");
                                        } else
                                        {
                                        	log.info("Ocurrio un error al adjuntar el reporte");
                                        }
                                    } else
                                    {
                                    	log.info("El Reporte no se Construyo Correctamente");
                                    }

                                    if (this.externalRelation.equals("False"))
                                    {
                                        String fec = today.format(now);
                                        //String emailResponse = EmailHandler.EnviarMensaje(this.email, "Creación de Solicitud Certificado de Residencia",
                                        //        "Gracias por utilizar nuestra plataforma para solicitar el Certificado de Residencia. <br><br> Su solicitud ha sido recibida y se remitira a la dependecia competente, su n&uacute;mero de radicado es el No " + radicadoNumber + 
                                        //        ", será resuelta en un plazo m&aacuteximo de 15 d&iacute;as h&aacute;biles. "
                                        //        + "<br><br><br><p style='font-size:10px'>\"Este correo es de tipo informativo; por favor no responda a este mensaje.</p>", this.listPropertieDTOs);
                                        //if (!emailResponse.contains("Error"))
                                        //{
                                            setInfoPanelShow(true);
                                            setInfoPanelMessage(

                                                    "Gracias por utilizar nuestra plataforma para solicitar el Certificado de Residencia. "
                                                    + "Su solicitud ha sido recibida y se remitirá a la dependencia competente, su número de radicado es el No " + radicadoNumber + 
                                                    ", será resuelta en un plazo máximo de 24 horas hábiles "
                                                    );

                                        //} else
                                        //{
                                        //    this.setAlertMessage(true);
                                        //    this.setAlertStyle(false);
                                        //    this.setAlertText("Error enviando el email");
                                        //}

                                    } else if (this.externalRelation.equals("True"))
                                    {
                                        //String emailResponse = EmailHandler.EnviarMensaje(this.email, "Creación de Solicitud Certificado de Residencial",
                                        //        "Gracias por utilizar nuestra plataforma para solicitar el Certificado de Residencia. <br><br> Su solicitud ha sido recibida y se remitira a la dependecia competente, su n&uacute;mero de radicado es el No " + radicadoNumber + 
                                        //        ", será resuelta en un plazo m&aacuteximo de 15 d&iacute;as h&aacute;biles. "
                                        //        + "<br><br><br><p style='font-size:10px'>\"Este correo es de tipo informativo; por favor no responda a este mensaje.</p>", this.listPropertieDTOs);
                                        //if (!emailResponse.contains("Error"))
                                        //{
                                            setInfoPanelShow(true);
                                            setInfoPanelMessage(
                                            		"Gracias por utilizar nuestra plataforma para solicitar el Certificado de Residencia. "
                                                     + "Su solicitud ha sido recibida y se remitirá a la dependencia competente, su número de radicado es el No " + radicadoNumber + 
                                                     ", será resuelta en un plazo máximo de 24 horas hábiles "
                                                    );
                                            //} else
                                            //{
                                            //this.setAlertMessage(true);
                                            //this.setAlertStyle(false);
                                            //this.setAlertText("Error enviando el email");
                                            //}
                                    } else
                                    {
                                        setInfoPanelShow(false);
                                        setInfoPanelMessage("");
                                    }

                                } else
                                {
                                    setAlertMessage(true);
                                    setAlertStyle(false);
                                    setAlertText("Ha ocurrido un error inesperado. Contacte al administrador del sistema e intentelo más tarde.");
                                }
                            } else
                            {
                                setAlertMessage(true);
                                setAlertStyle(false);
                                setAlertText("Ha ocurrido un error al solicitar el certificado.");
                            }
                        } else
                        {
                            setAlertMessage(true);
                            setAlertStyle(false);
                            setAlertText("Ha ocurrido un error al solicitar el número de radicado.");
                        }
                    } else
                    {
//                        if(procedureType.equals("391"))
//                        {
//                            if (listPrivadoLibertadDTOs.size() == 0) {
//                                setAlertMessage(true);
//                                setAlertStyle(false);
//                                setAlertText("No hay datos para el Privado de Libertad");
//                            }
//                        }
                        String radicadoNumber = null;
                        if (executeAdax)
                        {
                            objWebServiceDTO = crs.Request(objWebServiceDTO, this.objThirdDTO, oldRadNumber, this.listPropertieDTOs, this.uploadedFileAttendant,
                                    this.uploadedFilePublicService, this.uploadedFileYounger, this.uploadedFileCustody);
                        } else
                        {
                            radicadoNumber = crs.getRadicadoNumberOrfeo(objThirdDTO, listPropertieDTOs);
                            String exp = LocationExpedientHandler.getExpedientValueFromLocationList(listLocationExpedientDTOs, this.objThirdDTO.getLocation());
                            objWebServiceDTO = crs.AppendExpedient(radicadoNumber, exp, listPropertieDTOs);
                        }
                        if (objWebServiceDTO.isRespuesta() && radicadoNumber != null)
                        {
                            int counter = 0;
                            WebServiceDTO objWebServiceDTO2 = new WebServiceDTO();
                            if (executeAdax)
                            {
                                String tags[] = crs.getFilesTag(objWebServiceDTO.getXmlFile());

                                objWebServiceDTO2 = crs.CorrectRequest(objWebServiceDTO2, this.objThirdDTO, this.procedureType,
                                        this.externalRelation, this.listPropertieDTOs, this.related, this.younger, "", this.getOldRadNumber(), this.getCertificateKey(), this.getCertificateKey2(),
                                        this.getAtentionDate(), tags[0], tags[1], this.uploadedFileAttendant, this.uploadedFilePublicService,
                                        this.uploadedFileYounger, this.uploadedFileCustody, listPrivadoLibertadDTOs);
                            } else
                            {
                                objWebServiceDTO2 = crs.CorrectRequest(objWebServiceDTO2, this.objThirdDTO, this.procedureType,
                                        this.externalRelation, this.listPropertieDTOs, this.related, this.younger, "", radicadoNumber, this.getCertificateKey(), this.getCertificateKey2(),
                                        this.getAtentionDate(), "", "", this.uploadedFileAttendant, this.uploadedFilePublicService,
                                        this.uploadedFileYounger, this.uploadedFileCustody, listPrivadoLibertadDTOs);
                                String responseFile1 = crs.CreateAppend(radicadoNumber, uploadedFileAttendant, listPropertieDTOs, PropertiesHandler.PropertieValueOfIdKeyFromDB(listPropertieDTOs, "81"), "DocIdentSol");
                                if (responseFile1 == null)
                                {
                                	log.error("Error en la respuesta de crear Anexo #1....");
                                    this.setAlertMessage(true);
                                    this.setAlertStyle(false);
                                    this.setAlertText(responseFile1);
                                    counter++;
                                }
                                String responseFile2 = crs.CreateAppend(radicadoNumber, uploadedFilePublicService, listPropertieDTOs, PropertiesHandler.PropertieValueOfIdKeyFromDB(listPropertieDTOs, "82"), "RecServPub");
                                if (responseFile2 == null)
                                {
                                	log.error("Error en la respuesta de crear Anexo #2....");
                                    this.setAlertMessage(true);
                                    this.setAlertStyle(false);
                                    this.setAlertText(responseFile2);
                                    counter++;
                                }
                                String responseFile3 = crs.CreateAppend(radicadoNumber, uploadedFileYounger, listPropertieDTOs, PropertiesHandler.PropertieValueOfIdKeyFromDB(listPropertieDTOs, "83"), "DocIdentMen");
                                if (responseFile3 == null)
                                {
                                	log.error("Error en la respuesta de crear Anexo #3....");
                                    this.setAlertMessage(true);
                                    this.setAlertStyle(false);
                                    this.setAlertText(responseFile3);
                                    counter++;
                                }
								// Si es Menor de edad y PERSONA CON PATRIA POTESTAD=3
								if (younger.equals("1") && related.equals("3")) {
									String responseFile4 = crs.CreateAppend(radicadoNumber, uploadedFileCustody,
											listPropertieDTOs,
											PropertiesHandler.PropertieValueOfIdKeyFromDB(listPropertieDTOs, "84"),
											"DocPatPost");
									if (responseFile4 == null) {
										log.error("Error en la respuesta de crear Anexo #4....");
										this.setAlertMessage(true);
										this.setAlertStyle(false);
										this.setAlertText(responseFile4);
										counter++;
									}
								}
                            }
                            if (objWebServiceDTO2.isRespuesta() && counter < 3)
                            {
                                setAlertMessage(true);
                                setAlertStyle(true);
                                setAlertText("El certificado se ha solicitado con éxito.");

                                parametros = new HashMap<>();
                                parametros.put("radicado", radicadoNumber);
                                parametros.put("fecha", day);
                                parametros.put("tramite", "Creación de Solicitud de Certificado de Residencia");
                                String doc = rep.reporteRadicado(parametros);

                                if (doc != null || !doc.equals(""))
                                {
                                	log.info("Se va a Adjuntar el Reporte del radicado");
                                    boolean resp = ws.appendDocumentRadicado(listPropertieDTOs, listPropertieDTOs, doc, radicadoNumber);

                                    if (resp)
                                    {
                                    	log.info("El Documento se Adjunto Correctamente");
                                    } else
                                    {
                                    	log.info("Ocurrio un error al adjuntar el reporte");
                                    }
                                } else
                                {
                                	log.info("El Reporte no se Construyo Correctamente");
                                }

                                if (this.externalRelation.equals("False"))
                                {
                                    String fec = today.format(now);
                                    //String emailResponse = EmailHandler.EnviarMensaje(this.email, "Creación de Solicitud", "<html><meta charset=\"UTF-8\"/>Estimado (a) Ciudadano (a) <br><br>"
                                    //        + "Gracias por utilizar nuestra plataforma para solicitar el certificado de residencia.<br><br>"
                                    //        + "Le informamos que su solicitud ser&aacute; atendida a m&aacute;s tardar en el siguiente d&iacute;a h&aacute;bil su solicitud  ser&aacute tramitada y "
                                    //        + "se generar&aacute; notificaci&oacute;n al correo electr&oacute;nico: <br>" + this.email + ".<br>" + "Su solicitud es la No:" + radicadoNumber + "<br><br>"
                                    //        + "<br><br><br><br><p style='font-size:10px'>\"Este correo es de tipo informativo; por favor no responda a este mensaje. Para contactarnos puede hacerlo a trav&eacute;s de la p&aacute;gina de la Secretar&iacute;a Distrital de Gobierno: <a href='http://www.gobiernobogota.gov.co' target='_blank'>http://www.gobiernobogota.gov.co</a>\"</p></html>", this.listPropertieDTOs);
                                    //if (!emailResponse.contains("Error"))
                                    //{
                                        setInfoPanelShow(true);
                                        setInfoPanelMessage(
                                        		"Gracias por utilizar nuestra plataforma para solicitar el Certificado de Residencia. "
                                                + "Su solicitud ha sido recibida y se remitirá a la dependencia competente, su número de radicado es el No " + radicadoNumber + 
                                                ", será resuelta en un plazo máximo de 24 horas hábiles "
                                                );
                                        //} else
                                        //{
                                        //this.setAlertMessage(true);
                                        //this.setAlertStyle(false);
                                        //this.setAlertText("Error enviando el email");
                                        //}

                                } else if (this.externalRelation.equals("True"))
                                {
                                    //String emailResponse = EmailHandler.EnviarMensaje(this.email, "Creación de Solicitud", "<html><meta charset=\"UTF-8\"/>Estimado (a) Ciudadano (a) <br><br>"
                                    //        + "Gracias por utilizar nuestra plataforma para solicitar el certificado de residencia.<br><br>"
                                    //        + "Le informamos que su solicitud ser&aacute; tramitada en 5 d&iacute;as h&aacute;biles siguientes a la radicaci&oacute;n y se generar&aacute; notificaci&oacute;n al "
                                    //        + "correo electr&oacute;nico: <br>" + this.email + " una vez se encuentre disponible el certificado.<br>"
                                    //        + "Su solicitud es la No:" + radicadoNumber + "<br><br>"
                                    //        + "<br><br><br><br><p style='font-size:10px'>\"Este correo es de tipo informativo; por favor no responda a este mensaje. Para contactarnos puede hacerlo a trav&eacute;s de la p&aacute;gina de la Secretar&iacute;a Distrital de Gobierno: <a href='http://www.gobiernobogota.gov.co' target='_blank'>http://www.gobiernobogota.gov.co</a>\"</p><br></html>", this.listPropertieDTOs);
                                    //if (!emailResponse.contains("Error"))
                                    //{
                                        setInfoPanelShow(true);
                                        setInfoPanelMessage(
                                        		"Gracias por utilizar nuestra plataforma para solicitar el Certificado de Residencia. "
                                                 + "Su solicitud ha sido recibida y se remitirá a la dependencia competente, su número de radicado es el No " + radicadoNumber + 
                                                 ", será resuelta en un plazo máximo de 24 horas hábiles "
                                                );
                                        //} else
                                        //{
                                        //this.setAlertMessage(true);
                                        //this.setAlertStyle(false);
                                        //this.setAlertText("Error enviando el email");
                                        //}
                                } else
                                {
                                    setInfoPanelShow(false);
                                    setInfoPanelMessage("");
                                }

                            } else
                            {
                                setAlertMessage(true);
                                setAlertStyle(false);
                                //setAlertText(objWebServiceDTO2.getToken());
                                setAlertText("Ha ocurrido un error inesperado. Contacte al administrador del sistema e intentelo más tarde.");
                            }
                        } else
                        {
                            setAlertMessage(true);
                            setAlertStyle(false);
                            setAlertText("Ha ocurrido un error al solicitar el certificado.");
                        }
                    }
                } else if (count == 0)
                {
                    setAlertMessage(true);
                    setAlertStyle(false);
                    setAlertText("Debe escoger uno de los menores registrados.");
                } else
                {
                    setAlertMessage(true);
                    setAlertStyle(false);
                    setAlertText("Debe escoger solo un menor de edad.");
                }
            } catch (Exception e)
            {
                setAlertMessage(true);
                setAlertStyle(false);
                setAlertText("Error en la respuesta de crear Anexos. Contacte al administrador del sistema e intentelo más tarde.");
                // setAlertText("Ha ocurrido un error inesperado. Contacte al administrador del sistema e intentelo más tarde.");
                log.error("Error: " + e.toString());
            }        	
        }
        else {
            setAlertMessage(true);
            setAlertStyle(false);
            setAlertText("Extensión de archivo no permitido.");
        }
    }

    public void RegisterYounger()
    {
        try
        {
            WebServiceDTO objWebServiceDTO = new WebServiceDTO();

            
            objWebServiceDTO = ws.CreateThird(this.idTypeYounger, this.idNumberYounger, this.firstNameYounger, this.secondNameYounger,
                    this.lastNameYounger, this.secondLastNameYounger, this.birthDateYounger, this.genderYounger, this.sexoYounger, this.sexualOrientYounger, this.nationalityYounger,
                    this.cellphoneYounger, this.phoneYounger, this.addressYounger, this.addressComplYounger, this.ruralAddressYounger, this.locationYounger,
                    this.neightborhoodYounger, this.residenceCityYounger, this.occupationYounger, this.specialConditionYounger,
                    this.stratumYounger, this.emailYounger, this.objThirdDTO.getUser(), this.etnia, this.cualEtnia, this.listPropertieDTOs, this.objThirdDTO.getPass());
            if (objWebServiceDTO.isRespuesta())
            {
                int count = 0;
                for (YoungerDTO objYoungerDTO2 : listYoungerDto)
                {
                    if (this.idNumberYounger.equals(objYoungerDTO2.getIdNumber()))
                    {
                        count++;
                        if (this.addressYounger != null)
                        {
                            objYoungerDTO2.setAddress(this.addressYounger);
                        }
                        if (this.addressComplYounger != null)
                        {
                            objYoungerDTO2.setAddressCompl(this.addressComplYounger);
                        }
                        objYoungerDTO2.setName(this.firstNameYounger + " " + this.secondNameYounger + " " + this.lastNameYounger + " "
                                + this.secondLastNameYounger);
                        objYoungerDTO2.setFirstName(this.firstNameYounger);
                        objYoungerDTO2.setSecondName(this.secondNameYounger);
                        objYoungerDTO2.setLastName(this.lastNameYounger);
                        objYoungerDTO2.setSecondLastName(this.secondLastNameYounger);
                        objYoungerDTO2.setIdType(this.idTypeYounger);
                        objYoungerDTO2.setIdNumber(this.idNumberYounger);
                        objYoungerDTO2.setGender(this.genderYounger);
                        objYoungerDTO2.setSexo(this.sexoYounger);
                        objYoungerDTO2.setSexualOrient(this.sexualOrientYounger);
                        objYoungerDTO2.setBirthDate(this.birthDateYounger);
                        objYoungerDTO2.setNeightborhood(this.neightborhoodYounger);
                        objYoungerDTO2.setEmail(this.emailYounger);
                        if (this.ruralAddressYounger != null)
                        {
                            objYoungerDTO2.setRuralAddress(this.ruralAddressYounger);
                        }
                        objYoungerDTO2.setLocation(this.locationYounger);
                        objYoungerDTO2.setCellphone(this.cellphoneYounger);
                        objYoungerDTO2.setPhone(this.phoneYounger);
                        objYoungerDTO2.setResidenceCity(this.residenceCityYounger);
                        objYoungerDTO2.setNationality(this.nationalityYounger);
                        objYoungerDTO2.setStratum(this.stratumYounger);
                        objYoungerDTO2.setSpecialCondition(this.specialConditionYounger);
                        objYoungerDTO2.setOccupation(this.occupationYounger);
                        objYoungerDTO2.setTypeDireccion(typeDireccion);
                        objYoungerDTO2.setEtnia(this.etnia);
                        if (!this.etnia.equals("8"))
                        {
                            objYoungerDTO2.setCualEtnia(cualEtnia);
                        } else {
                            objYoungerDTO2.setCualEtnia("");
                        }
                    }
                }
                if (count == 0)
                {
                    YoungerDTO objYoungerDTO = new YoungerDTO();
                    if (this.addressYounger != null)
                    {
                        objYoungerDTO.setAddress(this.addressYounger);
                    }
                    if (this.addressComplYounger != null)
                    {
                        objYoungerDTO.setAddressCompl(this.addressComplYounger);
                    }
                    
                    objYoungerDTO.setName(this.firstNameYounger + " " + this.secondNameYounger + " " + this.lastNameYounger + " "
                            + this.secondLastNameYounger);
                    objYoungerDTO.setFirstName(this.firstNameYounger);
                    objYoungerDTO.setSecondName(this.secondNameYounger);
                    objYoungerDTO.setLastName(this.lastNameYounger);
                    objYoungerDTO.setSecondLastName(this.secondLastNameYounger);
                    objYoungerDTO.setIdType(this.idTypeYounger);
                    objYoungerDTO.setIdNumber(this.idNumberYounger);
                    objYoungerDTO.setGender(this.genderYounger);
                    objYoungerDTO.setSexo(this.sexoYounger);
                    objYoungerDTO.setSexualOrient(this.sexualOrientYounger);
                    objYoungerDTO.setBirthDate(this.birthDateYounger);
                    objYoungerDTO.setNeightborhood(this.neightborhoodYounger);
                    objYoungerDTO.setEmail(this.emailYounger);
                    if (this.ruralAddressYounger != null)
                    {
                        objYoungerDTO.setRuralAddress(this.ruralAddressYounger);
                    }
                    objYoungerDTO.setLocation(this.locationYounger);
                    objYoungerDTO.setCellphone(this.cellphoneYounger);
                    objYoungerDTO.setPhone(this.phoneYounger);
                    objYoungerDTO.setResidenceCity(this.residenceCityYounger);
                    objYoungerDTO.setNationality(this.nationalityYounger);
                    objYoungerDTO.setStratum(this.stratumYounger);
                    objYoungerDTO.setSpecialCondition(this.specialConditionYounger);
                    objYoungerDTO.setOccupation(this.occupationYounger);
                    objYoungerDTO.setTypeDireccion(typeDireccion);
                    objYoungerDTO.setEtnia(this.etnia);
                    if (this.etnia.equals("8"))
                    {
                        objYoungerDTO.setCualEtnia(cualEtnia);
                    } else {
                        objYoungerDTO.setCualEtnia("");
                    }
                    //listYoungerDto.add(objYoungerDTO);
                    this.objThirdDTO.getListYoungerDTO().add(objYoungerDTO);
                }
                setModalAlertShow(true);
                setModalAlertStyle(true);
                setModalAlertMessage("El menor ha sido asociado correctamente.");
            } else
            {
                setModalAlertShow(true);
                setModalAlertStyle(false);
                setModalAlertMessage("Error al tratar de asociar el menor.");
            }
        } catch (Exception ex)
        {
            setModalAlertShow(true);
            setModalAlertStyle(false);
            setModalAlertMessage("Error en la respuesta de crear Anexo, al tratar cargar los datos del menor.");
            // setModalAlertMessage("Ha ocurrido un error en el sistema, al tratar cargar los datos del menor.");
            log.error("Error CertificateRequestController: " + ex.toString());
        }
    }
    
    public void RegisterPrivado()
    {
        try
        {
        	// Validar que si no es Nit no posea valores lafanumericos
        	
//        	if(!idTypePrivado.equals("CE")) {
//        		 boolean isNumeric =  nitSearch.matches("[+-]?\\d*(\\.\\d+)?");
//        		 if(!isNumeric) {
//        			 setModalAlertShow(true);
//                     setModalAlertStyle(false);
//                     setModalAlertMessage("El tipo de identificación seleccionado no puede tener letras.");
//                     return;
//        		 }
//        	}
        	
            idNumberPrivado = nitSearch;
            WebServiceDTO objWebServiceDTO = ws.RegisterLegalAgent(objThirdDTOPL, listPropertieDTOs, indexController.getListPHPropertieDTOs(),
                    idNumberPrivado, idTypePrivado, idNumberPrivado, firstNamePrivado, lastNamePrivado,
                    secondNamePrivado, secondLastNamePrivado, cellphonePrivado, phonePrivado, birthDatePrivado,
                    addressPrivado, addressComplPrivado, neightborhoodPrivado, emailPrivado, dateAgent, startAgent, "PL", endAgent,
                    nameAgentJuridico, idNumberAgentJuridico, idTypeAgentJuridico, razonSocialPrivado, dateActAgent, numberActAgent, genderPrivado, 
                    typeDireccionPrivado, etniaPrivado, cualEtniaPrivado);
            if (objWebServiceDTO.isRespuesta())
            {
                //Aca se le van a asignar todos los valores(uno por uno) enviados al servicio web a un nuevo PrivadoDTO.
                int count = 0;
                for (LegalAgentDTO objPrivadoDTO : listPrivadoLibertadDTOs)
                {
                    if (objPrivadoDTO.getIdNumber().equals(this.idNumberPrivado))
                    {
                        count++;
                        objPrivadoDTO.setAddress(this.addressPrivado);
                        objPrivadoDTO.setAddressCompl(this.addressComplPrivado);
                        objPrivadoDTO.setName(this.firstNamePrivado + " " + this.secondNamePrivado + " " + this.lastNamePrivado + " "
                                + this.secondLastNamePrivado);
                        objPrivadoDTO.setFirstName(this.firstNamePrivado);
                        objPrivadoDTO.setSecondName(this.secondNamePrivado);
                        objPrivadoDTO.setLastName(this.lastNamePrivado);
                        objPrivadoDTO.setSecondLastName(this.secondLastNamePrivado);
                        objPrivadoDTO.setIdType(this.idTypePrivado);
                        objPrivadoDTO.setIdNumber(this.idNumberPrivado);
                        objPrivadoDTO.setBirthDate(this.birthDatePrivado);
                        objPrivadoDTO.setNeightborhood(this.neightborhoodPrivado);
                        objPrivadoDTO.setEmail(this.emailPrivado);
                        objPrivadoDTO.setBeginDate(startAgent);
                        objPrivadoDTO.setFinishDate(endAgent);
                        objPrivadoDTO.setGender(genderPrivado);
                        objPrivadoDTO.setIdNumberAgentJuridico(idNumberAgentJuridico);
                        objPrivadoDTO.setNameAgentJuridico(nameAgentJuridico);
                        
                    }
                }
                if (count == 0)
                {
                    String namePrivado = "";
                    namePrivado = firstNamePrivado + " " + secondNamePrivado + " " + lastNamePrivado + " " + secondLastNamePrivado;
                    
                    LegalAgentDTO objPrivadoDTO = new LegalAgentDTO(false, namePrivado, "tipo1", startAgent, endAgent, "estado",
                            idTypePrivado, idNumberPrivado, addressPrivado, firstNamePrivado, lastNamePrivado,
                            secondNamePrivado, secondLastNamePrivado, birthDatePrivado, genderPrivado, 
                            nationalityPrivado, cellphonePrivado, phonePrivado, addressComplPrivado, ruralAddressPrivado,
                            neightborhoodPrivado, residenceCityPrivado, emailPrivado, locationPrivado,
                            stratumPrivado, specialConditionPrivado, occupationPrivado, idTypeRepPrivado, "", razonSocialPrivado, 
                            nameAgentJuridico, idTypeAgentJuridico, idNumberAgentJuridico, etniaPrivado, cualEtniaPrivado,"", numberActAgent, dateActAgent);
                    //this.objThirdDTO.getListPrivadoDTOs().add(objPrivadoDTO);
                    listPrivadoLibertadDTOs.add(objPrivadoDTO);
                }
                setModalAlertShow(true);
                setModalAlertStyle(true);
                setModalAlertMessage("El representante legal ha sido asociado correctamente.");
            }
            else
            {
                setModalAlertShow(true);
                setModalAlertStyle(false);
                setModalAlertMessage("No se pudo asociar el representante legal correctamente.");
            }
            
        }
        catch (Exception e)
        {
            setModalAlertShow(true);
            setModalAlertStyle(false);
            setModalAlertMessage("Ha ocurrido un error en el sistema, al registrar un nuevo representante legal.");
            // setModalAlertMessage("Ha ocurrido un error en el sistema, al registrar un nuevo representante legal.");
            log.error("Error al registrar un nuevo representante legal: " + e.toString());
        }
    }
    
    public void updatePrivado()
    {
        listPrivadoLibertadDTOs.clear();
    }

    public void UpdateYounger()
    {
        try
        {
            Locale locale = new Locale(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idnumber"));
            String select = locale.toString().toUpperCase();
            this.listYoungerDto = objThirdDTO.getListYoungerDTO();
            for (YoungerDTO objYoungerDTO : this.listYoungerDto)
            {
                if (objYoungerDTO.getIdNumber().toUpperCase().equals(select))
                {
                    this.setIdTypeYounger(objYoungerDTO.getIdType());
                    this.setIdNumberYounger(objYoungerDTO.getIdNumber());
                    this.setFirstNameYounger(objYoungerDTO.getFirstName());
                    this.setSecondNameYounger(objYoungerDTO.getSecondName());
                    this.setLastNameYounger(objYoungerDTO.getLastName());
                    this.setSecondLastNameYounger(objYoungerDTO.getSecondLastName());
                    this.setBirthDateYounger(objYoungerDTO.getBirthDate().substring(0, 10));
                    this.setGenderYounger(objYoungerDTO.getGender());
                    this.setSexoYounger(objYoungerDTO.getSexo());
                    this.setSexualOrientYounger(objYoungerDTO.getSexualOrient());
                    this.setNationalityYounger(objYoungerDTO.getNationality());
                    this.setCellphoneYounger(objYoungerDTO.getCellphone());
                    this.setPhoneYounger(objYoungerDTO.getPhone());
                    this.setAddressYounger(objYoungerDTO.getAddress());
                    this.setAddressComplYounger(objYoungerDTO.getAddressCompl());
                    this.setRuralAddressYounger(objYoungerDTO.getRuralAddress());
                    this.setLocationYounger(objYoungerDTO.getLocation());
                    this.setNeightborhoodYounger(objYoungerDTO.getNeightborhood());
                    this.setResidenceCityYounger(objYoungerDTO.getResidenceCity());
                    this.setOccupationYounger(objYoungerDTO.getOccupation());
                    this.setSpecialConditionYounger(objYoungerDTO.getSpecialCondition());
                    this.setStratumYounger(objYoungerDTO.getStratum());
                    this.setEmailYounger(objYoungerDTO.getEmail());
                    this.setTypeDireccion(objYoungerDTO.getTypeDireccion());
                    this.setEtnia(objYoungerDTO.getEtnia());
                    this.setCualEtnia(objYoungerDTO.getCualEtnia());
                    this.disableIdsYounger = true;
                    break;
                }
            }
        } catch (Exception e)
        {
            setAlertMessage(true);
            setAlertStyle(false);
            setAlertText("Error en la respuesta de crear Anexo. Contacte al administrador del sistema e intentelo más tarde.");
            // setAlertText("Ha ocurrido un error inesperado. Contacte al administrador del sistema e intentelo más tarde.");
            log.error("Error: " + e.toString());
        }
    }

    public String Back()
    {
        return "success";
    }
    
    public void FindPrivadoLibertad()
    {
        objThirdDTOPL = ws.FindRepresentanteLegal(listPropertieDTOs, getNitSearch(), listPrivadoLibertadDTOs);
        if (objThirdDTOPL != null)
        {  
            List<LegalAgentDTO> objLegalAgentDTO = objThirdDTOPL.getListLegalAgentDTOs();
            
            setFirstNamePrivado(objLegalAgentDTO.get(0).getFirstName());
            setLastNamePrivado(objLegalAgentDTO.get(0).getLastName());
            setSecondNamePrivado(objLegalAgentDTO.get(0).getSecondName());
            setSecondLastNamePrivado(objLegalAgentDTO.get(0).getSecondLastName());
            setIdTypePrivado(objLegalAgentDTO.get(0).getIdType());
            setIdNumberPrivado(objLegalAgentDTO.get(0).getIdNumber());
            setGenderPrivado(objLegalAgentDTO.get(0).getGender());
            setIdStatusPrivado(objLegalAgentDTO.get(0).getState());
            setIdTypeAgentJuridico(objLegalAgentDTO.get(0).getIdTypeAgentJuridico());
            setIdNumberAgentJuridico(objLegalAgentDTO.get(0).getIdNumberAgentJuridico());
            setNameAgentJuridico(objLegalAgentDTO.get(0).getNameAgentJuridico());
            if (!objLegalAgentDTO.get(0).getAddress().equals(""))
            {
                setAddressPrivado(objLegalAgentDTO.get(0).getAddress());
                setAddressComplPrivado(objLegalAgentDTO.get(0).getAddressCompl());
                setTypeDireccionPrivado("1");
            }
            if (!objLegalAgentDTO.get(0).getRuralAddress().equals(""))
            {
                setAddressPrivado(objLegalAgentDTO.get(0).getRuralAddress());
                setTypeDireccionPrivado("2");
            }
            setPhonePrivado(objLegalAgentDTO.get(0).getPhone());
            setEmailPrivado(objLegalAgentDTO.get(0).getEmail());
            setEtniaPrivado(objLegalAgentDTO.get(0).getEtnia());
            setCualEtniaPrivado(objLegalAgentDTO.get(0).getCualEtnia());
            setModalAlertStylePrivado(true);
            setModalAlertShowPrivado(true);
            setModalAlertMessagePrivado("Se ha encontrado un usuario");
        } else {
            this.readOnlyGeneral = "false";
            setIdTypePrivado("");
            setRazonSocialPrivado("");
            setFirstNamePrivado("");
            setLastNamePrivado("");
            setSecondNamePrivado("");
            setSecondLastNamePrivado("");
            setIdNumberPrivado(getNitSearch());
            setGenderPrivado("");
            setIdStatusPrivado("");
            setIdTypeAgentJuridico("");
            setIdNumberAgentJuridico("");
            setNameAgentJuridico("");
            setAddressPrivado("");
            setPhonePrivado("");
            setEmailPrivado("");
            setModalAlertShowPrivado(true);
            setModalAlertStylePrivado(false);
            setModalAlertMessagePrivado("No se encontro ningún registro en la busqueda. Complete el Registro");
        }
    }

    public void FillIdTypeYoungerValues()
    {
        indexController.FillIdTypeValues();
        setValuesIdTypeYounger(indexController.getValuesIdType());
        this.valuesIdTypeYounger.remove("CEDULA DE CIUDADANIA");
        //this.valuesIdTypeYounger.remove("CEDULA DE EXTRANJERIA");
        this.valuesIdTypeYounger.remove("NUMERO UNICO DE IDENTIFICACION PERSONAL ");
        this.valuesIdTypeYounger.remove("TARJETA DE EXTRANJERIA");
        this.valuesIdTypeYounger.remove("NIT");
        this.valuesIdTypeYounger.remove("MATRICULA");
    }
    
    public void FillIdTypePrivadoValues()
    {
        indexController.FillIdTypeValues();        
        setValuesIdTypePrivado(indexController.getValuesIdType());
        this.valuesIdTypePrivado.remove("VISA");
        this.valuesIdTypePrivado.remove("NIT");
        this.valuesIdTypePrivado.remove("REGISTRO CIVIL DE NACIMIENTO NUIP");
        this.valuesIdTypePrivado.remove("NUMERO UNICO DE IDENTIFICACION PERSONAL ");
        this.valuesIdTypePrivado.remove("NUMERO UNICO DE IDENTIFICACION PERSONAL");
        this.valuesIdTypePrivado.remove("TARJETA DE EXTRANJERIA");        
        this.valuesIdTypePrivado.remove("MATRICULA");
        /**
         * VISA: ocultar o eliminar, pero mantener en la base de datos
NIT: ocultar o eliminar, pero mantener en la base de datos
CEDULA DE CIUDADANÍA: se mantiene. En este campo se subirá la contraseña.
CEDULA DE EXTRANJERÍA: se mantiene.
TARJETA DE IDENTIDAD: se mantiene.
REGISTRO CIVIL DE NACIMIENTO: ocultar o eliminar, pero mantener en la base de datos
NUMERO ÚNICO DE IDENTIFICACIÓN: ocultar o eliminar, pero mantener en la base de datos
PASAPORTE: ocultar o eliminar, pero mantener en la base de datos
TARJETA DE EXTRANJERÍA: ocultar o eliminar, pero mantener en la base de datos
MATRICULA: ocultar o eliminar, pero mantener en la base de datos
PERMISO ESPECIAL DE PERMANENCIA PEP: agregar
         */
    }
    
    public void FillStatusPrivado()
    {
        setValuesidStatusPrivado(indexController.getValuesidStatusLegalAgent());
    }
    
    public void FillValuesEtnias()
    {
        setValuesEtnias(indexController.getValuesEtnias());
    }

    public void FillProcedureTypeValues()
    {
        setValuesProcedureType(indexController.getValuesProcedureType());
    }

    public void FillRelatedValues()
    {
        setValuesRelated(indexController.getValuesRelated());
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

    public void FillLocationValues()
    {
        setValuesLocation(indexController.getValuesLocation());
    }

    public String getIdType()
    {
        return idType;
    }

    public void setIdType(String idType)
    {
        this.idType = idType;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getIdNumber()
    {
        return idNumber;
    }

    public void setIdNumber(String idNumber)
    {
        this.idNumber = idNumber;
    }

    public String getSecondName()
    {
        return secondName;
    }

    public void setSecondName(String secondName)
    {
        this.secondName = secondName;
    }

    public String getSecondLastName()
    {
        return secondLastName;
    }

    public void setSecondLastName(String secondLastName)
    {
        this.secondLastName = secondLastName;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getRelated()
    {
        return related;
    }

    public void setRelated(String related)
    {
        this.related = related;
    }

    public String getExternalRelation()
    {
        return externalRelation;
    }

    public void setExternalRelation(String externalRelation)
    {
        this.externalRelation = externalRelation;
    }

    public boolean isOathCheckBox()
    {
        return oathCheckBox;
    }

    public void setOathCheckBox(boolean oathCheckBox)
    {
        this.oathCheckBox = oathCheckBox;
    }

    public String getProcedureType()
    {
        return procedureType;
    }

    public void setProcedureType(String procedureType)
    {
        this.procedureType = procedureType;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getYounger()
    {
        return younger;
    }

    public void setYounger(String younger)
    {
        this.younger = younger;
    }

    public Map<String, String> getValuesProcedureType()
    {
        return valuesProcedureType;
    }

    public void setValuesProcedureType(Map<String, String> valuesProcedureType)
    {
        this.valuesProcedureType = valuesProcedureType;
    }

    public Map<String, String> getValuesRelated()
    {
        return valuesRelated;
    }

    public void setValuesRelated(Map<String, String> valuesRelated)
    {
        this.valuesRelated = valuesRelated;
    }

    public boolean isDisableRelated()
    {
        return disableRelated;
    }

    public void setDisableRelated(boolean disableRelated)
    {
        this.disableRelated = disableRelated;
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

    public File getDocument()
    {
        return document;
    }

    public void setDocument(File document)
    {
        this.document = document;
    }

    public boolean isAlertStyle()
    {
        return alertStyle;
    }

    public void setAlertStyle(boolean alertStyle)
    {
        this.alertStyle = alertStyle;
    }

    public boolean isAlertMessage()
    {
        return alertMessage;
    }

    public void setAlertMessage(boolean alertMessage)
    {
        this.alertMessage = alertMessage;
    }

    public String getAlertText()
    {
        return alertText;
    }

    public void setAlertText(String alertText)
    {
        this.alertText = alertText;
    }

    public boolean isDisablePublicBill()
    {
        return disablePublicBill;
    }

    public void setDisablePublicBill(boolean disablePublicBill)
    {
        this.disablePublicBill = disablePublicBill;
    }

    public boolean isDisableYoungerDocument()
    {
        return disableYoungerDocument;
    }

    public void setDisableYoungerDocument(boolean disableYoungerDocument)
    {
        this.disableYoungerDocument = disableYoungerDocument;
    }

    public boolean isDisableCustody()
    {
        return disableCustody;
    }

    public void setDisableCustody(boolean disableCustody)
    {
        this.disableCustody = disableCustody;
    }

    public boolean isDisableDocumentIdentity()
    {
        return disableDocumentIdentity;
    }

    public void setDisableDocumentIdentity(boolean disableDocumentIdentity)
    {
        this.disableDocumentIdentity = disableDocumentIdentity;
    }

    public String getIdTypeYounger()
    {
        return idTypeYounger;
    }

    public void setIdTypeYounger(String idTypeYounger)
    {
        this.idTypeYounger = idTypeYounger;
    }

    public String getIdNumberYounger()
    {
        return idNumberYounger;
    }

    public void setIdNumberYounger(String idNumberYounger)
    {
        this.idNumberYounger = idNumberYounger;
    }

    public String getFirstNameYounger()
    {
        return firstNameYounger;
    }

    public void setFirstNameYounger(String firstNameYounger)
    {
        this.firstNameYounger = firstNameYounger;
    }

    public Map<String, String> getValuesIdTypeYounger()
    {
        return valuesIdTypeYounger;
    }

    public void setValuesIdTypeYounger(Map<String, String> valuesIdTypeYounger)
    {
        this.valuesIdTypeYounger = valuesIdTypeYounger;
    }

    public UploadedFile getUploadedFileAttendant()
    {
        return uploadedFileAttendant;
    }

    public void setUploadedFileAttendant(UploadedFile uploadedFileAttendant)
    {
        this.uploadedFileAttendant = uploadedFileAttendant;
    }

    public String getLastNameYounger()
    {
        return lastNameYounger;
    }

    public void setLastNameYounger(String lastNameYounger)
    {
        this.lastNameYounger = lastNameYounger;
    }

    public String getSecondNameYounger()
    {
        return secondNameYounger;
    }

    public void setSecondNameYounger(String secondNameYounger)
    {
        this.secondNameYounger = secondNameYounger;
    }

    public String getSecondLastNameYounger()
    {
        return secondLastNameYounger;
    }

    public void setSecondLastNameYounger(String secondLastNameYounger)
    {
        this.secondLastNameYounger = secondLastNameYounger;
    }

    public String getBirthDateYounger()
    {
        return birthDateYounger;
    }

    public void setBirthDateYounger(String birthDateYounger)
    {
        this.birthDateYounger = birthDateYounger;
    }

    public String getGenderYounger()
    {
        return genderYounger;
    }

    public void setGenderYounger(String genderYounger)
    {
        this.genderYounger = genderYounger;
    }

    public String getNationalityYounger()
    {
        return nationalityYounger;
    }

    public void setNationalityYounger(String nationalityYounger)
    {
        this.nationalityYounger = nationalityYounger;
    }

    public String getCellphoneYounger()
    {
        return cellphoneYounger;
    }

    public void setCellphoneYounger(String cellphoneYounger)
    {
        this.cellphoneYounger = cellphoneYounger;
    }

    public String getPhoneYounger()
    {
        return phoneYounger;
    }

    public void setPhoneYounger(String phoneYounger)
    {
        this.phoneYounger = phoneYounger;
    }

    public String getAddressYounger()
    {
        return addressYounger;
    }

    public void setAddressYounger(String addressYounger)
    {
        this.addressYounger = addressYounger;
    }

    public String getRuralAddressYounger()
    {
        return ruralAddressYounger;
    }

    public void setRuralAddressYounger(String ruralAddressYounger)
    {
        this.ruralAddressYounger = ruralAddressYounger;
    }

    public String getNeightborhoodYounger()
    {
        return neightborhoodYounger;
    }

    public void setNeightborhoodYounger(String neightborhoodYounger)
    {
        this.neightborhoodYounger = neightborhoodYounger;
    }

    public String getResidenceCityYounger()
    {
        return residenceCityYounger;
    }

    public void setResidenceCityYounger(String residenceCityYounger)
    {
        this.residenceCityYounger = residenceCityYounger;
    }

    public String getEmailYounger()
    {
        return emailYounger;
    }

    public void setEmailYounger(String emailYounger)
    {
        this.emailYounger = emailYounger;
    }

    public String getLocationYounger()
    {
        return locationYounger;
    }

    public void setLocationYounger(String locationYounger)
    {
        this.locationYounger = locationYounger;
    }

    public String getStratumYounger()
    {
        return stratumYounger;
    }

    public void setStratumYounger(String stratumYounger)
    {
        this.stratumYounger = stratumYounger;
    }

    public String getSpecialConditionYounger()
    {
        return specialConditionYounger;
    }

    public void setSpecialConditionYounger(String specialConditionYounger)
    {
        this.specialConditionYounger = specialConditionYounger;
    }

    public String getOccupationYounger()
    {
        return occupationYounger;
    }

    public void setOccupationYounger(String occupationYounger)
    {
        this.occupationYounger = occupationYounger;
    }

    public Map<String, String> getValuesStratum()
    {
        return valuesStratum;
    }

    public void setValuesStratum(Map<String, String> valuesStratum)
    {
        this.valuesStratum = valuesStratum;
    }

    public Map<String, String> getValuesSpecialCondition()
    {
        return valuesSpecialCondition;
    }

    public void setValuesSpecialCondition(Map<String, String> valuesSpecialCondition)
    {
        this.valuesSpecialCondition = valuesSpecialCondition;
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

    public List<YoungerDTO> getListYoungerDto()
    {
        return listYoungerDto;
    }

    public void setListYoungerDto(List<YoungerDTO> listYoungerDto)
    {
        this.listYoungerDto = listYoungerDto;
    }

    public String getYoungerSize()
    {
        return youngerSize;
    }

    public void setYoungerSize(String youngerSize)
    {
        this.youngerSize = youngerSize;
    }

    public UploadedFile getUploadedFilePublicService()
    {
        return uploadedFilePublicService;
    }

    public void setUploadedFilePublicService(UploadedFile uploadedFilePublicService)
    {
        this.uploadedFilePublicService = uploadedFilePublicService;
    }

    public UploadedFile getUploadedFileYounger()
    {
        return uploadedFileYounger;
    }

    public void setUploadedFileYounger(UploadedFile uploadedFileYounger)
    {
        this.uploadedFileYounger = uploadedFileYounger;
    }

    public UploadedFile getUploadedFileCustody()
    {
        return uploadedFileCustody;
    }

    public void setUploadedFileCustody(UploadedFile uploadedFileCustody)
    {
        this.uploadedFileCustody = uploadedFileCustody;
    }

    public boolean isDisableIdsYounger()
    {
        return disableIdsYounger;
    }

    public void setDisableIdsYounger(boolean disableIdsYounger)
    {
        this.disableIdsYounger = disableIdsYounger;
    }

    public String getOldRadNumber()
    {
        return oldRadNumber;
    }

    public void setOldRadNumber(String oldRadNumber)
    {
        this.oldRadNumber = oldRadNumber;
    }

    public String getCertificateKey()
    {
        return certificateKey;
    }

    public void setCertificateKey(String certificateKey)
    {
        this.certificateKey = certificateKey;
    }

    public String getAtentionDate()
    {
        return atentionDate;
    }

    public void setAtentionDate(String atentionDate)
    {
        this.atentionDate = atentionDate;
    }

    public boolean isCorrect()
    {
        return correct;
    }

    public void setCorrect(boolean correct)
    {
        this.correct = correct;
    }

    public boolean isInfoPanelShow()
    {
        return infoPanelShow;
    }

    public void setInfoPanelShow(boolean infoPanelShow)
    {
        this.infoPanelShow = infoPanelShow;
    }

    public String getInfoPanelMessage()
    {
        return infoPanelMessage;
    }

    public void setInfoPanelMessage(String infoPanelMessage)
    {
        this.infoPanelMessage = infoPanelMessage;
    }

    public boolean isDisableYounger()
    {
        return disableYounger;
    }

    public void setDisableYounger(boolean disableYounger)
    {
        this.disableYounger = disableYounger;
    }

    public boolean isDisableProcedureType()
    {
        return disableProcedureType;
    }

    public void setDisableProcedureType(boolean disableProcedureType)
    {
        this.disableProcedureType = disableProcedureType;
    }

    public boolean isDisableExternalRelation()
    {
        return disableExternalRelation;
    }

    public void setDisableExternalRelation(boolean disableExternalRelation)
    {
        this.disableExternalRelation = disableExternalRelation;
    }

    public List<PropertieDTO> getListPropertieDTOs()
    {
        return listPropertieDTOs;
    }

    public void setListPropertieDTOs(List<PropertieDTO> listPropertieDTOs)
    {
        this.listPropertieDTOs = listPropertieDTOs;
    }

    public List<LocationExpedientDTO> getListLocationExpedientDTOs()
    {
        return listLocationExpedientDTOs;
    }

    public void setListLocationExpedientDTOs(List<LocationExpedientDTO> listLocationExpedientDTOs)
    {
        this.listLocationExpedientDTOs = listLocationExpedientDTOs;
    }

    public String getCertificateKey2()
    {
        return certificateKey2;
    }

    public void setCertificateKey2(String certificateKey2)
    {
        this.certificateKey2 = certificateKey2;
    }

    public String getSexoYounger()
    {
        return sexoYounger;
    }

    public void setSexoYounger(String sexoYounger)
    {
        this.sexoYounger = sexoYounger;
    }

    public String getSexualOrientYounger()
    {
        return sexualOrientYounger;
    }

    public void setSexualOrientYounger(String sexualOrientYounger)
    {
        this.sexualOrientYounger = sexualOrientYounger;
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

    public String getTypeDireccion()
    {
        return typeDireccion;
    }

    public void setTypeDireccion(String typeDireccion)
    {
        this.typeDireccion = typeDireccion;
    }

    public Map<String, Object> getParametros()
    {
        return parametros;
    }

    public void setParametros(Map<String, Object> parametros)
    {
        this.parametros = parametros;
    }

    public List<LocationDTO> getValuesLocation()
    {
        return valuesLocation;
    }

    public void setValuesLocation(List<LocationDTO> valuesLocation)
    {
        this.valuesLocation = valuesLocation;
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

    public String getUrlVideoRegistro2()
    {
        return UrlVideoRegistro2;
    }

    public void setUrlVideoRegistro2(String UrlVideoRegistro2)
    {
        this.UrlVideoRegistro2 = UrlVideoRegistro2;
    }

    public String getAddressComplYounger()
    {
        return addressComplYounger;
    }

    public void setAddressComplYounger(String addressComplYounger)
    {
        this.addressComplYounger = addressComplYounger;
    }

    public ReporteHandler getRep()
    {
        return rep;
    }

    public void setRep(ReporteHandler rep)
    {
        this.rep = rep;
    }

    public String getPersonType()
    {
        return personType;
    }

    public void setPersonType(String personType)
    {
        this.personType = personType;
    }

    public String getIdTypePrivado()
    {
        return idTypePrivado;
    }

    public void setIdTypePrivado(String idTypePrivado)
    {
        this.idTypePrivado = idTypePrivado;
    }

    public String getIdNumberPrivado()
    {
        return idNumberPrivado;
    }

    public void setIdNumberPrivado(String idNumberPrivado)
    {
        this.idNumberPrivado = idNumberPrivado;
    }

    public String getFirstNamePrivado()
    {
        return firstNamePrivado;
    }

    public void setFirstNamePrivado(String firstNamePrivado)
    {
        this.firstNamePrivado = firstNamePrivado;
    }

    public String getLastNamePrivado()
    {
        return lastNamePrivado;
    }

    public void setLastNamePrivado(String lastNamePrivado)
    {
        this.lastNamePrivado = lastNamePrivado;
    }

    public String getSecondNamePrivado()
    {
        return secondNamePrivado;
    }

    public void setSecondNamePrivado(String secondNamePrivado)
    {
        this.secondNamePrivado = secondNamePrivado;
    }

    public String getSecondLastNamePrivado()
    {
        return secondLastNamePrivado;
    }

    public void setSecondLastNamePrivado(String secondLastNamePrivado)
    {
        this.secondLastNamePrivado = secondLastNamePrivado;
    }

    public String getBirthDatePrivado()
    {
        return birthDatePrivado;
    }

    public void setBirthDatePrivado(String birthDatePrivado)
    {
        this.birthDatePrivado = birthDatePrivado;
    }

    public String getGenderPrivado()
    {
        return genderPrivado;
    }

    public void setGenderPrivado(String genderPrivado)
    {
        this.genderPrivado = genderPrivado;
    }

    public String getNationalityPrivado()
    {
        return nationalityPrivado;
    }

    public void setNationalityPrivado(String nationalityPrivado)
    {
        this.nationalityPrivado = nationalityPrivado;
    }

    public String getCellphonePrivado()
    {
        return cellphonePrivado;
    }

    public void setCellphonePrivado(String cellphonePrivado)
    {
        this.cellphonePrivado = cellphonePrivado;
    }

    public String getPhonePrivado()
    {
        return phonePrivado;
    }

    public void setPhonePrivado(String phonePrivado)
    {
        this.phonePrivado = phonePrivado;
    }

    public String getAddressPrivado()
    {
        return addressPrivado;
    }

    public void setAddressPrivado(String addressPrivado)
    {
        this.addressPrivado = addressPrivado;
    }

    public String getAddressComplPrivado()
    {
        return addressComplPrivado;
    }

    public void setAddressComplPrivado(String addressComplPrivado)
    {
        this.addressComplPrivado = addressComplPrivado;
    }

    public String getRuralAddressPrivado()
    {
        return ruralAddressPrivado;
    }

    public void setRuralAddressPrivado(String ruralAddressPrivado)
    {
        this.ruralAddressPrivado = ruralAddressPrivado;
    }

    public String getNeightborhoodPrivado()
    {
        return neightborhoodPrivado;
    }

    public void setNeightborhoodPrivado(String neightborhoodPrivado)
    {
        this.neightborhoodPrivado = neightborhoodPrivado;
    }

    public String getResidenceCityPrivado()
    {
        return residenceCityPrivado;
    }

    public void setResidenceCityPrivado(String residenceCityPrivado)
    {
        this.residenceCityPrivado = residenceCityPrivado;
    }

    public String getEmailPrivado()
    {
        return emailPrivado;
    }

    public void setEmailPrivado(String emailPrivado)
    {
        this.emailPrivado = emailPrivado;
    }

    public String getLocationPrivado()
    {
        return locationPrivado;
    }

    public void setLocationPrivado(String locationPrivado)
    {
        this.locationPrivado = locationPrivado;
    }

    public String getStratumPrivado()
    {
        return stratumPrivado;
    }

    public void setStratumPrivado(String stratumPrivado)
    {
        this.stratumPrivado = stratumPrivado;
    }

    public String getSpecialConditionPrivado()
    {
        return specialConditionPrivado;
    }

    public void setSpecialConditionPrivado(String specialConditionPrivado)
    {
        this.specialConditionPrivado = specialConditionPrivado;
    }

    public String getOccupationPrivado()
    {
        return occupationPrivado;
    }

    public void setOccupationPrivado(String occupationPrivado)
    {
        this.occupationPrivado = occupationPrivado;
    }

    public String getIdGenrePrivado()
    {
        return idGenrePrivado;
    }

    public void setIdGenrePrivado(String idGenrePrivado)
    {
        this.idGenrePrivado = idGenrePrivado;
    }

    public String getIdStatusPrivado()
    {
        return idStatusPrivado;
    }

    public void setIdStatusPrivado(String idStatusPrivado)
    {
        this.idStatusPrivado = idStatusPrivado;
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

    public String getRazonSocialPrivado()
    {
        return razonSocialPrivado;
    }

    public void setRazonSocialPrivado(String razonSocialPrivado)
    {
        this.razonSocialPrivado = razonSocialPrivado;
    }

    public String getIdTypeRepPrivado()
    {
        return idTypeRepPrivado;
    }

    public void setIdTypeRepPrivado(String idTypeRepPrivado)
    {
        this.idTypeRepPrivado = idTypeRepPrivado;
    }

    public String getRequireNaturalPrivado()
    {
        return requireNaturalPrivado;
    }

    public void setRequireNaturalPrivado(String requireNaturalPrivado)
    {
        this.requireNaturalPrivado = requireNaturalPrivado;
    }

    public String getRequireGeneralPrivado()
    {
        return requireGeneralPrivado;
    }

    public void setRequireGeneralPrivado(String requireGeneralPrivado)
    {
        this.requireGeneralPrivado = requireGeneralPrivado;
    }

    public String getShowNaturalPrivado()
    {
        return showNaturalPrivado;
    }

    public void setShowNaturalPrivado(String showNaturalPrivado)
    {
        this.showNaturalPrivado = showNaturalPrivado;
    }

    public String getTypeDireccionPrivado()
    {
        return typeDireccionPrivado;
    }

    public void setTypeDireccionPrivado(String typeDireccionPrivado)
    {
        this.typeDireccionPrivado = typeDireccionPrivado;
    }

    public boolean isModalAlertShowPrivado()
    {
        return modalAlertShowPrivado;
    }

    public void setModalAlertShowPrivado(boolean modalAlertShowPrivado)
    {
        this.modalAlertShowPrivado = modalAlertShowPrivado;
    }

    public boolean isModalAlertStylePrivado()
    {
        return modalAlertStylePrivado;
    }

    public void setModalAlertStylePrivado(boolean modalAlertStylePrivado)
    {
        this.modalAlertStylePrivado = modalAlertStylePrivado;
    }

    public String getModalAlertMessagePrivado()
    {
        return modalAlertMessagePrivado;
    }

    public void setModalAlertMessagePrivado(String modalAlertMessagePrivado)
    {
        this.modalAlertMessagePrivado = modalAlertMessagePrivado;
    }

    public String getNitSearch()
    {
        return nitSearch;
    }

    public void setNitSearch(String nitSearch)
    {
        this.nitSearch = nitSearch;
    }

    public String getReadOnlyGeneral()
    {
        return readOnlyGeneral;
    }

    public void setReadOnlyGeneral(String readOnlyGeneral)
    {
        this.readOnlyGeneral = readOnlyGeneral;
    }

    public ThirdDTO getObjThirdDTOPL()
    {
        return objThirdDTOPL;
    }

    public void setObjThirdDTOPL(ThirdDTO objThirdDTOPL)
    {
        this.objThirdDTOPL = objThirdDTOPL;
    }

    public List<LegalAgentDTO> getListPrivadoLibertadDTOs()
    {
        return listPrivadoLibertadDTOs;
    }

    public void setListPrivadoLibertadDTOs(List<LegalAgentDTO> listPrivadoLibertadDTOs)
    {
        this.listPrivadoLibertadDTOs = listPrivadoLibertadDTOs;
    }

    public Map<String, String> getValuesIdTypePrivado()
    {
        return valuesIdTypePrivado;
    }

    public void setValuesIdTypePrivado(Map<String, String> valuesIdTypePrivado)
    {
        this.valuesIdTypePrivado = valuesIdTypePrivado;
    }

    public Map<String, String> getValuesidStatusPrivado()
    {
        return valuesidStatusPrivado;
    }

    public void setValuesidStatusPrivado(Map<String, String> valuesidStatusPrivado)
    {
        this.valuesidStatusPrivado = valuesidStatusPrivado;
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

    public Map<String, String> getValuesEtnias()
    {
        return valuesEtnias;
    }

    public void setValuesEtnias(Map<String, String> valuesEtnias)
    {
        this.valuesEtnias = valuesEtnias;
    }

    public String getEtniaPrivado()
    {
        return etniaPrivado;
    }

    public void setEtniaPrivado(String etniaPrivado)
    {
        this.etniaPrivado = etniaPrivado;
    }

    public String getCualEtniaPrivado()
    {
        return cualEtniaPrivado;
    }

    public void setCualEtniaPrivado(String cualEtniaPrivado)
    {
        this.cualEtniaPrivado = cualEtniaPrivado;
    }
    
    
    
    
    
}
