/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.Controller;

import co.gov.gobierno.DTO.AyudaDTO;
import co.gov.gobierno.DTO.LegalAgentDTO;
import co.gov.gobierno.DTO.LocationDTO;
import co.gov.gobierno.DTO.PHRequestDTO;
import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.DTO.RequestDTO;
import co.gov.gobierno.DTO.ThirdDTO;
import co.gov.gobierno.DTO.WebServiceDTO;
import co.gov.gobierno.DTO.YoungerDTO;
import co.gov.gobierno.Service.PHRegisterService;
import co.gov.gobierno.Service.WebService;
import co.gov.gobierno.Util.EmailHandler;
import co.gov.gobierno.Util.JsonHandler;
import co.gov.gobierno.Util.PropertiesHandler;
import co.gov.gobierno.Util.ReporteHandler;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;
import org.slf4j.LoggerFactory;

/**
 *
 * @author DELL
 */
@ManagedBean
@ViewScoped
public class PHRegisterController implements Serializable
{

    private ThirdDTO objThirdDTO;
    private ThirdDTO objThirdDTOLA;
    private List<PropertieDTO> listPropertieDTOs;
    private List<PropertieDTO> listPHPropertieDTOs;
    private List<AyudaDTO> listAyudaDTO;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(PHRegisterController.class);
        
    private String name;
    private String state;
    private String nit;
    private String rut;
    private String registerDate;
    private String publicScriptNumber;
    private String publicScriptdate;
    private String type1;
    private String type2;
    private String type3;
    private String locate;
    private String neighborhood;
    private String realStateRegistration;
    private String folio;
    private String address;
    private String email;
    private String stratum;
    private String regimen;
    private String typeNroPrivateUnits;
    private String nroPrivateUnits;
    private String commonArea;
    private String nroCommonArea;
    private String presentationType;
    private String academicFormation;
    private String nroResidents;
    private String nroBloques;
    private String nroEtapas;
    private String nroLocales;
    private String contactPerson;
    private String contactPhone;
    private String contactEmail;
    private String notaria;
    private String emergencyPlan;
    private boolean oathCheckBox;
    private String typeDireccionPH = "1";

    private Map<String, String> valuesIdType;
    private List<LocationDTO> valuesLocation;
    private List<LocationDTO> valuesNeightborhood;
    private Map<String, String> valuesStratum;
    private Map<String, String> valuesMainStreet;
    private Map<String, String> valuesStreetGeneratingPath;
    private Map<String, String> valuesSpecialCondition;
    private Map<String, String> valuesOccupation;
    private Map<String, String> valuesGender;
    private Map<String, String> valuesResidenceCity;
    private Map<String, String> valuesNationality;
    private Map<String, String> valuesEstado;
    private Map<String, String> valuesTypeNroPrivateUnits;
    private Map<String, String> valuesCommonAreas;
    private Map<String, String> valuesRegimen;
    private Map<String, String> valuesAcademicFormation;
    private Map<String, String> valuesPresentationType;
    private Map<String, String> valuesidStatusLegalAgent;
    private Map<String, String> valuesidTypeProperty;
    private Map<String, String> valuesidTypeRepLegalAgent;
    private Map<String, Object> parametros;
    private Map<String,String> MapAyudaDB;
    private List<PHRequestDTO> listUnidadesPHRequestDTO = new ArrayList<PHRequestDTO>();
    
    
    private String UrlVideoRegistro;

    

    private boolean alertShow;
    private boolean alertStyle;
    private String alertMessage;

    //ATRIBUTOS PARA EL REPRESENTANTE LEGAL-------------------------------------
    private String personType = "";
    private String idTypeLegalAgent;
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
    private String addressComplLegalAgent = "";
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
    private String requireNaturalLegalAgent = "requeridoRepresentante";
    private String requireGeneralLegalAgent = "requeridoRepresentante";
    private String showNaturalLegalAgent = "";
    private String typeDireccionLegalAgent = "1";
    
    private String requireJuridicLegalAgent = "";
    private String showJuridicLegalAgent = "none";
    
    private String readOnlyNatural = "";
    private String readOnlyJuridic = "";
    private String readOnlyGeneral = "";
    private String nitSearch;
    private String etnia = "";
    private String cualEtnia = "";
    private Map<String,String> valuesEtnia;

    private String legalAgentSize;

    private boolean modalAlertShow;
    private boolean modalAlertStyle;
    private String modalAlertMessage;

    private List<LegalAgentDTO> listLegalAgentDTOs;
    
    //INFORMACION COMPLEMENTARIA
    private String strTipoArea;
    private String strArea;
    private String strCantidad;
    private String strDescripcionC;
    private String strDescripcionP;

    //AQUI TERMINAN LOS ATRIBUTOS PARA EL REPRESENTANTE LEGAL-------------------
    private UploadedFile uploadConference;
    private UploadedFile uploadConferenceRecord;
    private UploadedFile uploadPublicDocument;
    private UploadedFile uploadAceptDocument;
    private UploadedFile uploadIdDocumentCopy;
    private UploadedFile uploadCertificateOfTraditionAndFreedom;
    private UploadedFile uploadCertificateOfChamberOfCommerce;
	private UploadedFile uploadAdministrativeAct;
    private UploadedFile uploadCatastral;
    private UploadedFile uploadDocumentOfFiscaReviewer;

    @ManagedProperty(value = "#{indexController}")
    private IndexController indexController;
    
    
    

    @EJB
    WebService ws;
    @EJB
    PHRegisterService phrs;
  

    
    public String Request()
    {
    	if (objThirdDTO.getListLegalAgentDTOs().size() <= 0) {
        	log.error("Debe ingresar al menos un Representante");
            this.setAlertShow(true);
            this.setAlertStyle(false);
            this.setAlertMessage("Para Generar la Solicitud, debe ingresar al menos un Representante Legal.");
            return null;
    	}
        Date now = new Date();
        SimpleDateFormat formatDay = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String day = formatDay.format(now);
        String datos = "";
        for (int i = 0;i < valuesNeightborhood.size(); i++) 
        {
            if (valuesNeightborhood.get(i).getValueLocation().equals(neighborhood))
            {
                datos = valuesNeightborhood.get(i).getNameLocation();
            }
        }
        
        
        try
        {
            String radNumber = phrs.GetRadicadoNumber(objThirdDTO, listPropertieDTOs);
            if(radNumber!=null && !radNumber.equals(""))
            {
                String expNumber = phrs.GetExpedientNumber(radNumber, listPropertieDTOs);
                if(expNumber!=null && !expNumber.equals(""))
                {
                    String appendNumber = phrs.AppendExpedient(radNumber, expNumber, listPropertieDTOs);
                    if(appendNumber!=null && !appendNumber.equals(""))
                    {
                        String responseFile1 = phrs.CreateAppend(radNumber, uploadConferenceRecord, listPropertieDTOs, PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs,"actaAsambleaOConsejo"));
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
                        String responseFile2 = phrs.CreateAppend(radNumber, uploadPublicDocument, listPropertieDTOs, PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs,"escrituraPública"));
                        if(responseFile2 == null)
                        {
                        	log.error("Error en la respuesta de crear Anexo #2....");
                            this.setAlertShow(true);
                            this.setAlertStyle(false);
                                                        this.setAlertMessage("Ocurrió un error en la respuesta de crear Anexos en el sistema contacte al administrador del sistema.");
                            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
                            counter++;
                        }
                        String responseFile3 = phrs.CreateAppend(radNumber, uploadAceptDocument, listPropertieDTOs, PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs,"documentoDeAceptación"));
                        if(responseFile3 == null)
                        {
                        	log.error("Error en la respuesta de crear Anexo #3....");
                            this.setAlertShow(true);
                            this.setAlertStyle(false);
                                                        this.setAlertMessage("Ocurrió un error en la respuesta de crear Anexos en el sistema contacte al administrador del sistema.");
                            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
                            counter++;
                        }
                        String responseFile4 = phrs.CreateAppend(radNumber, uploadIdDocumentCopy, listPropertieDTOs, PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs,"documentoDeIdentificación"));
                        if(responseFile4 == null)
                        {
                        	log.error("Error en la respuesta de crear Anexo #4....");
                            this.setAlertShow(true);
                            this.setAlertStyle(false);
                                                        this.setAlertMessage("Ocurrió un error en la respuesta de crear Anexos en el sistema contacte al administrador del sistema.");
                            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
                            counter++;
                        }
                        String responseFile5 = phrs.CreateAppend(radNumber, uploadCertificateOfTraditionAndFreedom, listPropertieDTOs, PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs,"certificadoDeTradiciónYLibertad"));
                        if(responseFile5 == null)
                        {
                        	log.error("Error en la respuesta de crear Anexo #5....");
                            this.setAlertShow(true);
                            this.setAlertStyle(false);
                                                        this.setAlertMessage("Ocurrió un error en la respuesta de crear Anexos en el sistema contacte al administrador del sistema.");
                            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
                            counter++;
                        }
                        String responseFile6 = phrs.CreateAppend(radNumber, uploadConference, listPropertieDTOs, PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs,"actaConsejo"));
                        if(responseFile6 == null)
                        {
                        	log.error("Error en la respuesta de crear Anexo #6 Opcional....");
                            this.setAlertShow(true);
                            this.setAlertStyle(false);
                                                        this.setAlertMessage("Ocurrió un error en la respuesta de crear Anexos en el sistema contacte al administrador del sistema.");
                            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
                        }
                        String responseFile7 = phrs.CreateAppend(radNumber, uploadAdministrativeAct, listPropertieDTOs, PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs,"actoAdministrativo"));
                        if(responseFile7 == null)
                        {
                        	log.error("Error en la respuesta de crear Anexo #7 Opcional....");
                            this.setAlertShow(true);
                            this.setAlertStyle(false);
                                                        this.setAlertMessage("Ocurrió un error en la respuesta de crear Anexos en el sistema contacte al administrador del sistema.");
                            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
                        }                                                                                                                                                         
                         String responseFile8 = phrs.CreateAppend(radNumber, uploadCatastral, listPropertieDTOs, PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs,"certificadoCatastral"));
                        if(responseFile8 == null)
                        {
                        	log.error("Error en la respuesta de crear Anexo #8 Opcional....");
                            this.setAlertShow(true);
                            this.setAlertStyle(false);
                                                        this.setAlertMessage("Ocurrió un error en la respuesta de crear Anexos en el sistema contacte al administrador del sistema.");
                            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
                            counter++;
                        }
                        String responseFile9 = phrs.CreateAppend(radNumber, uploadCertificateOfChamberOfCommerce, listPropertieDTOs, PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs,"certificadoCamaraComercio"));
                       if(responseFile9 == null)
                       {
                    	   log.error("Error en la respuesta de crear Anexo #9 Opcional....");
                           this.setAlertShow(true);
                           this.setAlertStyle(false);
                                                       this.setAlertMessage("Ocurrió un error en la respuesta de crear Anexos en el sistema contacte al administrador del sistema.");
                            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
                           counter++;
                       }
                       
                       String responseFile10 = phrs.CreateAppend(radNumber, uploadDocumentOfFiscaReviewer, listPropertieDTOs, PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs,"certificadoRevisorFiscal"));
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
                            
                            WebServiceDTO objWebServiceDTO = phrs.RegisterPH(objThirdDTO, listPropertieDTOs,
                                    listPHPropertieDTOs, expNumber, radNumber, name.toUpperCase(), state, nit, registerDate,
                                    publicScriptNumber.toUpperCase(), publicScriptdate, type1, type2, type3, locate, neighborhood, datos,
                                    realStateRegistration, folio, address, typeDireccionPH, email, stratum, regimen, typeNroPrivateUnits,
                                    nroPrivateUnits, commonArea, nroCommonArea, presentationType, academicFormation,
                                    nroResidents, nroBloques, nroEtapas, nroLocales, contactPerson, contactPhone,
                                    contactEmail, notaria, emergencyPlan, personType, idTypeLegalAgent, idNumberLegalAgent,
                                    firstNameLegalAgent, lastNameLegalAgent, secondNameLegalAgent, secondLastNameLegalAgent,
                                    birthDateLegalAgent, nationalityLegalAgent, cellphoneLegalAgent, phoneLegalAgent,
                                    addressLegalAgent+addressComplLegalAgent, typeDireccionLegalAgent, emailLegalAgent, locationLegalAgent, stratumLegalAgent, 
                                    specialConditionLegalAgent, occupationLegalAgent, genderLegalAgent, idStatusLegalAgent,
                                    dateAgent, startAgent, endAgent, numberActAgent, dateActAgent, nameAgentJuridico.toUpperCase(),
                                    idNumberAgentJuridico, idTypeAgentJuridico, idTypeRepLegalAgent, razonSocialLegalAgent.toUpperCase(), rut, listUnidadesPHRequestDTO);
                            if(objWebServiceDTO.isRespuesta() && !objWebServiceDTO.getXmlFile().isEmpty())
                            {
                                
                                this.setAlertShow(true);
                                //Señor ciudadano, gracias por utilizar nuestra plataforma para solicitar el Certificado de Propiedad Horizontal.   Su solicitud ha sido recibida y se remitira a la dependecia competente,  su número de radicado es el No xxxxxxxxxxxxxx,  será resuelta en un plazo maximo de 15 días hábiles
                                /*String emailResponse = EmailHandler.EnviarMensaje(this.objThirdDTO.getEmail(), "Creación de Solicitud Propiedad Horizontal",
                                        "Gracias por utilizar nuestra plataforma para solicitar el Certificado de Propiedad Horizontal. <br><br> Su solicitud ha sido recibida y se remitira a la dependecia competente, su n&uacute;mero de radicado es el No " + radNumber + 
                                        ", será resuelta en un plazo m&aacuteximo de 15 d&iacute;as h&aacute;biles. "
                                        + "<br><br><br><p style='font-size:10px'>\"Este correo es de tipo informativo; por favor no responda a este mensaje.</p>", this.listPropertieDTOs);
                                
                                String emailResponse1 = EmailHandler.EnviarMensaje(email, "Creación de Solicitud Propiedad Horizontal",
                                        "Gracias por utilizar nuestra plataforma para solicitar el Certificado de Propiedad Horizontal. <br><br> Su solicitud ha sido recibida y se remitira a la dependecia competente, su n&uacute;mero de radicado es el No " + radNumber + 
                                        ", será resuelta en un plazo m&aacuteximo de 15 d&iacute;as h&aacute;biles. "
                                        + "<br><br><br><p style='font-size:10px'>\"Este correo es de tipo informativo; por favor no responda a este mensaje.</p>", this.listPropertieDTOs);
                                */
                                
                                parametros = new HashMap<>();
                                parametros.put("radicado", radNumber);
                                parametros.put("fecha", day);
                                parametros.put("tramite", "Creación de Solicitud Propiedad Horizontal");
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
                                
                                //if(!emailResponse.contains("Error") || !emailResponse1.contains("Error"))
                                //{
                                    indexController.setAlertStylePH(true);
                                    indexController.setResultProcessPH(
                                            "Gracias por utilizar nuestra plataforma para solicitar el Certificado de Propiedad Horizontal. Su solicitud ha sido recibida y se remitira a la dependecia competente, su número de radicado es el No " + radNumber + 
                                            ", será resuelta en un plazo máximo de 15 días hábiles. "
                                            );
                                    this.setAlertStyle(true);
                                    this.setAlertMessage(
                                            "Gracias por utilizar nuestra plataforma para solicitar el Certificado de Propiedad Horizontal."
                                            + "Su solicitud ha sido recibida y se remitira a la dependecia competente, su número de radicado es el No " + radNumber + 
                                            ", será resuelta en un plazo máximo de 15 días hábiles. "
                                            );
                                    return "phconsultinit";
                                //}
                                
                                //else
                                //{
                                //    this.setAlertStyle(false);
                                //    this.setAlertMessage("Error al enviar el email.");
                                //}
                            }else {
                         	   log.error("Error en la creación del caso en Bizagi....");
                               this.setAlertShow(true);
                               this.setAlertStyle(false);
                               this.setAlertMessage("Ocurrió un error al colsutar los datos en el sistema contacte al administrador del sistema.");
                            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");

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
                	log.error("numero de expediente vacio o nulo.");
                    this.setAlertShow(true);
                    this.setAlertStyle(false);
                    this.setAlertMessage("Ocurrió un error numero de expediente contacte al administrador del sistema.");
                    // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
                }
            }
            else
            {
            	log.error("numero de radicado vacio o nulo.");
                this.setAlertShow(true);
                this.setAlertStyle(false);
                this.setAlertMessage("Ocurrió un error numero de radicado contacte al administrador del sistema.");
                // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
            }
            return null;
        }
        catch(Exception e)
        {
        	log.error("Error en PHRegisterController: " + e.toString());
            this.setAlertShow(true);
            this.setAlertStyle(false);
            this.setAlertMessage("Ocurrió un error al registrar el formulario contacte al administrador del sistema.");
            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
            return null;
        }
    }
    
    public void RegisterLegalAgent()
    {
        Map<String, String> datos = new HashMap<String, String>();
        for (Map.Entry<String, String> entry : valuesidTypeRepLegalAgent.entrySet()) 
        {
            if (entry.getValue().equals(idTypeRepLegalAgent))
            {
                datos.put(entry.getValue(), entry.getKey());
            }
        }
        try
        {
            WebServiceDTO objWebServiceDTO = ws.RegisterLegalAgent(objThirdDTO, listPropertieDTOs, listPHPropertieDTOs,
                    nit, idTypeLegalAgent, idNumberLegalAgent, firstNameLegalAgent, lastNameLegalAgent,
                    secondNameLegalAgent, secondLastNameLegalAgent, cellphoneLegalAgent, phoneLegalAgent, birthDateLegalAgent,
                    addressLegalAgent, addressComplLegalAgent, neightborhoodLegalAgent, emailLegalAgent, dateAgent, startAgent, personType, endAgent,
                    nameAgentJuridico, idNumberAgentJuridico, idTypeAgentJuridico, razonSocialLegalAgent, dateActAgent, numberActAgent, genderLegalAgent, 
                    typeDireccionLegalAgent, etnia, cualEtnia);
            if (objWebServiceDTO.isRespuesta())
            {
                //Aca se le van a asignar todos los valores(uno por uno) enviados al servicio web a un nuevo LegalAgentDTO.
                int count = 0;
                for (LegalAgentDTO objLegalAgentDTO : listLegalAgentDTOs)
                {
                    if (objLegalAgentDTO.getIdNumber().equals(this.idNumberLegalAgent))
                    {
                        count++;
                        objLegalAgentDTO.setAddress(this.addressLegalAgent);
                        objLegalAgentDTO.setAddressCompl(this.addressComplLegalAgent);
                        objLegalAgentDTO.setName(this.firstNameLegalAgent + " " + this.secondNameLegalAgent + " " + this.lastNameLegalAgent + " "
                                + this.secondLastNameLegalAgent);
                        objLegalAgentDTO.setFirstName(this.firstNameLegalAgent);
                        objLegalAgentDTO.setSecondName(this.secondNameLegalAgent);
                        objLegalAgentDTO.setLastName(this.lastNameLegalAgent);
                        objLegalAgentDTO.setSecondLastName(this.secondLastNameLegalAgent);
                        objLegalAgentDTO.setIdType(this.idTypeLegalAgent);
                        objLegalAgentDTO.setIdNumber(this.idNumberLegalAgent);
                        objLegalAgentDTO.setBirthDate(this.birthDateLegalAgent);
                        objLegalAgentDTO.setNeightborhood(this.neightborhoodLegalAgent);
                        objLegalAgentDTO.setEmail(this.emailLegalAgent);
                        objLegalAgentDTO.setBeginDate(startAgent);
                        objLegalAgentDTO.setFinishDate(endAgent);
                        objLegalAgentDTO.setGender(genderLegalAgent);
                        objLegalAgentDTO.setIdTypeRepLegalAgent(idTypeRepLegalAgent);
                        objLegalAgentDTO.setDescTypeRepLegalAgent(datos.get(idTypeRepLegalAgent));
                        objLegalAgentDTO.setRazonSocialLegalAgent(razonSocialLegalAgent);
                        objLegalAgentDTO.setIdNumberAgentJuridico(idNumberAgentJuridico);
                        objLegalAgentDTO.setNameAgentJuridico(nameAgentJuridico);
                        objLegalAgentDTO.setIdStatusLegalAgent(idStatusLegalAgent);
                        objLegalAgentDTO.setDateActAgent(dateActAgent);
                    }
                }
                if (count == 0)
                {
                    String nameLegalAgent = "";
                    if (this.personType.equals("PN"))
                    {
                        nameLegalAgent = firstNameLegalAgent + " " + secondNameLegalAgent + " " + lastNameLegalAgent + " " + secondLastNameLegalAgent;
                    }
                    if (this.personType.equals("PJ"))
                    {
                        nameLegalAgent = razonSocialLegalAgent;
                    }
                    
                    LegalAgentDTO objLegalAgentDTO = new LegalAgentDTO(false, nameLegalAgent, "tipo1", startAgent, endAgent, "estado",
                            idTypeLegalAgent, idNumberLegalAgent, addressLegalAgent, firstNameLegalAgent, lastNameLegalAgent,
                            secondNameLegalAgent, secondLastNameLegalAgent, birthDateLegalAgent, genderLegalAgent, 
                            nationalityLegalAgent, cellphoneLegalAgent, phoneLegalAgent, addressComplLegalAgent, ruralAddressLegalAgent,
                            neightborhoodLegalAgent, residenceCityLegalAgent, emailLegalAgent, locationLegalAgent,
                            stratumLegalAgent, specialConditionLegalAgent, occupationLegalAgent, idTypeRepLegalAgent, datos.get(idTypeRepLegalAgent), razonSocialLegalAgent, 
                            nameAgentJuridico, idTypeAgentJuridico, idNumberAgentJuridico, etnia, cualEtnia, idStatusLegalAgent, numberActAgent, dateActAgent);
                    //this.objThirdDTO.getListLegalAgentDTOs().add(objLegalAgentDTO);
                    listLegalAgentDTOs.add(objLegalAgentDTO);
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
            setModalAlertMessage("Ha ocurrido un error en el sistema, contacte al administrador del sistema.");
            log.error("Error al registrar un nuevo representante legal: " + e.toString());
        }
    }
    

    @PostConstruct
    public void Init()
    {
        try
        {
            this.objThirdDTO = indexController.getObjThirdDTO();
            this.listPropertieDTOs = indexController.getListPropertieDTOs();
            this.listPHPropertieDTOs = indexController.getListPHPropertieDTOs();
            this.setEmergencyPlan("false");
            this.setPersonType("PN");
            this.objThirdDTO.getListLegalAgentDTOs().clear();
            this.setListLegalAgentDTOs(this.objThirdDTO.getListLegalAgentDTOs());
            this.setRealStateRegistration(indexController.getRealStateRegistration());
            FillIdTypeValues();
            FillLocationValues();
            FillStratumValues();
            FillMainStreetValues();
            FillStreetGeneratingPathValues();
            FillNeightborhoodValues();
            FillSpecialConditionValues();
            FillOccupationValues();
            FillGenderValues();
            FillNationalityValues();
            FillPrivateUnitsValues();
            FillCommonAreasValues();
            FillRegimenValues();
            FillPresentationTypeValues();
            FillAcademicFormationValues();
            FillStatusLegalAgent();
            FillTypeProperty();
            FillTypeRepLegalAgent();
            FillValuesEtnias();
            
            this.listAyudaDTO = PropertiesHandler.GetAyudaDB();
            this.MapAyudaDB = PropertiesHandler.AyudaDB(listAyudaDTO, "PhRepresentanteLegal");
            if(MapAyudaDB.get("vTipo").equals("1"))
            {
                           this.setUrlVideoRegistro("<iframe width='600' height='315'  src='"+MapAyudaDB.get("vUrl")+"' frameborder='0' allow='accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture' allowfullscreen></iframe>");
            }
            if(MapAyudaDB.get("vTipo").equals("2"))
            {
                          this.setUrlVideoRegistro("<img width='600' height='315' src='"+MapAyudaDB.get("vUrl")+"'/>");
            }
            this.strTipoArea = "1";
        }
        catch (Exception e)
        {
            setAlertShow(true);
            setAlertStyle(false);
            setAlertMessage("Ha ocurrido un error en el sistema por favor comuníquese con el administrador del sistema.");
            log.error("Error al cargar la pagina de registro de PH: " + e.toString());
        }
    }

    
    
    public void updateLegalAgent()
    {
        try
        {
            Locale locale = new Locale(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idnumber"));
            String select = locale.toString();
//            for (LegalAgentDTO objLegalAgentDTO : this.listLegalAgentDTOs)
//            {
//                if (objLegalAgentDTO.getIdNumber().equals(select))
//                {
//                    this.setIdTypeLegalAgent(objLegalAgentDTO.getIdType());
//                    this.setIdNumberLegalAgent(objLegalAgentDTO.getIdNumber());
//                    this.setFirstNameLegalAgent(objLegalAgentDTO.getFirstName());
//                    this.setSecondNameLegalAgent(objLegalAgentDTO.getSecondName());
//                    this.setLastNameLegalAgent(objLegalAgentDTO.getLastName());
//                    this.setSecondLastNameLegalAgent(objLegalAgentDTO.getSecondLastName());
//                    this.setBirthDateLegalAgent(objLegalAgentDTO.getBirthDate().substring(0, 10));
//                    this.setGenderLegalAgent(objLegalAgentDTO.getGender());
//                    this.setNationalityLegalAgent(objLegalAgentDTO.getNationality());
//                    this.setCellphoneLegalAgent(objLegalAgentDTO.getCellphone());
//                    this.setPhoneLegalAgent(objLegalAgentDTO.getPhone());
//                    this.setAddressLegalAgent(objLegalAgentDTO.getAddress());
//                    this.setRuralAddressLegalAgent(objLegalAgentDTO.getRuralAddress());
//                    this.setLocationLegalAgent(objLegalAgentDTO.getLocation());
//                    this.setNeightborhoodLegalAgent(objLegalAgentDTO.getNeightborhood());
//                    this.setResidenceCityLegalAgent(objLegalAgentDTO.getResidenceCity());
//                    this.setOccupationLegalAgent(objLegalAgentDTO.getOccupation());
//                    this.setSpecialConditionLegalAgent(objLegalAgentDTO.getSpecialCondition());
//                    this.setStratumLegalAgent(objLegalAgentDTO.getStratum());
//                    this.setEmailLegalAgent(objLegalAgentDTO.getEmail());
//                    this.disableIdsLegalAgent = true;
//                    break;
//                }
//            }
            listLegalAgentDTOs.clear();
        }
        catch (Exception e)
        {
            setAlertShow(true);
            setAlertStyle(false);
            setAlertMessage("Ha ocurrido un error inesperado. Contacte al administrador del sistema e intentelo más tarde.");
            log.error("Error: " + e.toString());
        }
    }
    
    public void FindRepresentanteLegal()
    {
    	try
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
                setEtnia(objLegalAgentDTO.get(0).getEtnia());
                setCualEtnia(objLegalAgentDTO.get(0).getCualEtnia());
                if (!objLegalAgentDTO.get(0).getAddress().equals(""))
                {
                    setAddressLegalAgent(objLegalAgentDTO.get(0).getAddress());
                    setAddressComplLegalAgent(objLegalAgentDTO.get(0).getAddressCompl());
                    setTypeDireccionLegalAgent("1");
                }
                if (!objLegalAgentDTO.get(0).getRuralAddress().equals(""))
                {
                    setAddressLegalAgent(objLegalAgentDTO.get(0).getRuralAddress());
                    setTypeDireccionLegalAgent("2");
                }
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
    	catch (Exception e)
        {
            setAlertShow(true);
            setAlertStyle(false);
            setAlertMessage("No se encontro ningún registro en la busqueda del representante legal. Complete el Registro.");
            log.error("Error: " + e.toString());
        }
    	

    }
    
    public void infoComplementaria()
    {
        PHRequestDTO objPHRequestDTO = new PHRequestDTO(strTipoArea, commonArea,typeNroPrivateUnits,  strCantidad, Integer.toString(listUnidadesPHRequestDTO.size()), strDescripcionC, strDescripcionP);
        listUnidadesPHRequestDTO.add(objPHRequestDTO);
        this.strTipoArea = "1";
        this.commonArea = "0";
        this.typeNroPrivateUnits = "0";
        this.strCantidad = "";
    }
    
    public void eliminaUnidad(PHRequestDTO objPHRequestDTO)
    {
        listUnidadesPHRequestDTO.remove(objPHRequestDTO);
    }
    
    public void FillValuesEtnias()
    {
        setValuesEtnia(indexController.getValuesEtnias());
    }
    
    public void FillTypeRepLegalAgent()
    {
        setValuesidTypeRepLegalAgent(indexController.getValuesidTypeRepLegalAgent());
    }

    public void FillTypeProperty()
    {
        setValuesidTypeProperty(indexController.getValuesidTypeProperty());
    }


    public void FillStatusLegalAgent()
    {
        setValuesidStatusLegalAgent(indexController.getValuesidStatusLegalAgent());
    }

    
    public void FillAcademicFormationValues()
    {
        setValuesAcademicFormation(indexController.getValuesAcademicFormation());
    }    

    public void FillPresentationTypeValues()
    {
        setValuesPresentationType(indexController.getValuesPresentationType());
    }


    public void FillRegimenValues()
    {
        setValuesRegimen(indexController.getValuesRegimen());
    }    
    
    public void FillCommonAreasValues()
    {
        setValuesCommonAreas(indexController.getValuesCommonAreas());
    }
    
    public void FillPrivateUnitsValues()
    {
        setValuesTypeNroPrivateUnits(indexController.getValuesTypeNroPrivateUnits());
    }
     

    public void FillIdTypeValues()
    {
        indexController.FillIdTypeValues();
        this.setValuesIdType(indexController.getValuesIdType());
        
        if (!this.valuesIdType.containsKey("MATRICULA")) {        	
        	this.valuesIdType.put("MATRICULA", "MATR");
        }
        this.valuesIdType.remove("TARJETA DE IDENTIDAD");
        this.valuesIdType.remove("REGISTRO CIVIL DE NACIMIENTO");
    }

    public void FillLocationValues()
    {
        setValuesLocation(indexController.getValuesLocation());
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

    public void FillNeightborhoodValues()
    {
        setValuesNeightborhood(indexController.getValuesNeightborhood());
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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getNit()
    {
        return nit;
    }

    public void setNit(String nit)
    {
        this.nit = nit;
    }

    public String getRegisterDate()
    {
        return registerDate;
    }

    public void setRegisterDate(String registerDate)
    {
        this.registerDate = registerDate;
    }

    public String getPublicScriptNumber()
    {
        return publicScriptNumber;
    }

    public void setPublicScriptNumber(String publicScriptNumber)
    {
        this.publicScriptNumber = publicScriptNumber;
    }

    public String getPublicScriptdate()
    {
        return publicScriptdate;
    }

    public void setPublicScriptdate(String publicScriptdate)
    {
        this.publicScriptdate = publicScriptdate;
    }

    public String getType1()
    {
        return type1;
    }

    public void setType1(String type1)
    {
        this.type1 = type1;
    }

    public String getLocate()
    {
        return locate;
    }

    public void setLocate(String locate)
    {
        this.locate = locate;
    }

    public String getNeighborhood()
    {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood)
    {
        this.neighborhood = neighborhood;
    }

    public String getRealStateRegistration()
    {
        return realStateRegistration;
    }

    public void setRealStateRegistration(String realStateRegistration)
    {
        this.realStateRegistration = realStateRegistration;
    }

    public String getFolio()
    {
        return folio;
    }

    public void setFolio(String folio)
    {
        this.folio = folio;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
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

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getStratum()
    {
        return stratum;
    }

    public void setStratum(String stratum)
    {
        this.stratum = stratum;
    }

    public String getRegimen()
    {
        return regimen;
    }

    public void setRegimen(String regimen)
    {
        this.regimen = regimen;
    }

    public String getType2()
    {
        return type2;
    }

    public void setType2(String type2)
    {
        this.type2 = type2;
    }

    public String getType3()
    {
        return type3;
    }

    public void setType3(String type3)
    {
        this.type3 = type3;
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

    public Map<String, String> getValuesResidenceCity()
    {
        return valuesResidenceCity;
    }

    public void setValuesResidenceCity(Map<String, String> valuesResidenceCity)
    {
        this.valuesResidenceCity = valuesResidenceCity;
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

    public boolean isOathCheckBox()
    {
        return oathCheckBox;
    }

    public void setOathCheckBox(boolean oathCheckBox)
    {
        this.oathCheckBox = oathCheckBox;
    }

    public List<PropertieDTO> getListPropertieDTOs()
    {
        return listPropertieDTOs;
    }

    public void setListPropertieDTOs(List<PropertieDTO> listPropertieDTOs)
    {
        this.listPropertieDTOs = listPropertieDTOs;
    }
    
    public Map<String, String> getValuesEstado()
    {
        return valuesEstado;
    }

    public void setValuesEstado(Map<String, String> valuesEstado)
    {
        this.valuesEstado = valuesEstado;
    }

    public String getTypeNroPrivateUnits()
    {
        return typeNroPrivateUnits;
    }

    public void setTypeNroPrivateUnits(String typeNroPrivateUnits)
    {
        this.typeNroPrivateUnits = typeNroPrivateUnits;
    }

    public String getNroPrivateUnits()
    {
        return nroPrivateUnits;
    }

    public void setNroPrivateUnits(String nroPrivateUnits)
    {
        this.nroPrivateUnits = nroPrivateUnits;
    }

    public Map<String, String> getValuesTypeNroPrivateUnits()
    {
        return valuesTypeNroPrivateUnits;
    }

    public void setValuesTypeNroPrivateUnits(Map<String, String> valuesTypeNroPrivateUnits)
    {
        this.valuesTypeNroPrivateUnits = valuesTypeNroPrivateUnits;
    }

    public String getCommonArea()
    {
        return commonArea;
    }

    public void setCommonArea(String commonArea)
    {
        this.commonArea = commonArea;
    }

    public String getNroCommonArea()
    {
        return nroCommonArea;
    }

    public void setNroCommonArea(String nroCommonArea)
    {
        this.nroCommonArea = nroCommonArea;
    }

    public Map<String, String> getValuesCommonAreas()
    {
        return valuesCommonAreas;
    }

    public void setValuesCommonAreas(Map<String, String> valuesCommonAreas)
    {
        this.valuesCommonAreas = valuesCommonAreas;
    }

    public Map<String, String> getValuesRegimen()
    {
        return valuesRegimen;
    }

    public void setValuesRegimen(Map<String, String> valuesRegimen)
    {
        this.valuesRegimen = valuesRegimen;
    }

    public String getPresentationType()
    {
        return presentationType;
    }

    public void setPresentationType(String presentationType)
    {
        this.presentationType = presentationType;
    }

    public String getAcademicFormation()
    {
        return academicFormation;
    }

    public void setAcademicFormation(String academicFormation)
    {
        this.academicFormation = academicFormation;
    }

    public Map<String, String> getValuesAcademicFormation()
    {
        return valuesAcademicFormation;
    }

    public void setValuesAcademicFormation(Map<String, String> valuesAcademicFormation)
    {
        this.valuesAcademicFormation = valuesAcademicFormation;
    }

    public Map<String, String> getValuesPresentationType()
    {
        return valuesPresentationType;
    }

    public void setValuesPresentationType(Map<String, String> valuesPresentationType)
    {
        this.valuesPresentationType = valuesPresentationType;
    }

    public String getNroResidents()
    {
        return nroResidents;
    }

    public void setNroResidents(String nroResidents)
    {
        this.nroResidents = nroResidents;
    }

    public String getContactPerson()
    {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson)
    {
        this.contactPerson = contactPerson;
    }

    public String getContactPhone()
    {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone)
    {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail()
    {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail)
    {
        this.contactEmail = contactEmail;
    }

    public String getNotaria()
    {
        return notaria;
    }

    public void setNotaria(String notaria)
    {
        this.notaria = notaria;
    }

    public String getNroBloques()
    {
        return nroBloques;
    }

    public void setNroBloques(String nroBloques)
    {
        this.nroBloques = nroBloques;
    }

    public String getNroEtapas()
    {
        return nroEtapas;
    }

    public void setNroEtapas(String nroEtapas)
    {
        this.nroEtapas = nroEtapas;
    }

    public String getNroLocales()
    {
        return nroLocales;
    }

    public void setNroLocales(String nroLocales)
    {
        this.nroLocales = nroLocales;
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

    public Map<String, String> getValuesidTypeProperty()
    {
        return valuesidTypeProperty;
    }

    public void setValuesidTypeProperty(Map<String, String> valuesidTypeProperty)
    {
        this.valuesidTypeProperty = valuesidTypeProperty;
    }

    public String getEmergencyPlan()
    {
        return emergencyPlan;
    }

    public void setEmergencyPlan(String emergencyPlan)
    {
        this.emergencyPlan = emergencyPlan;
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

    public String getNitSearch()
    {
        return nitSearch;
    }

    public void setNitSearch(String nitSearch)
    {
        this.nitSearch = nitSearch;
    }

    public String getRequireNaturalLegalAgent()
    {
        return requireNaturalLegalAgent;
    }

    public void setRequireNaturalLegalAgent(String requireNaturalLegalAgent)
    {
        this.requireNaturalLegalAgent = requireNaturalLegalAgent;
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

    public String getRequireGeneralLegalAgent()
    {
        return requireGeneralLegalAgent;
    }

    public void setRequireGeneralLegalAgent(String requireGeneralLegalAgent)
    {
        this.requireGeneralLegalAgent = requireGeneralLegalAgent;
    }

    public ThirdDTO getObjThirdDTOLA()
    {
        return objThirdDTOLA;
    }

    public void setObjThirdDTOLA(ThirdDTO objThirdDTOLA)
    {
        this.objThirdDTOLA = objThirdDTOLA;
    }

    public Map<String, Object> getParametros()
    {
        return parametros;
    }

    public void setParametros(Map<String, Object> parametros)
    {
        this.parametros = parametros;
    }

    public String getTypeDireccionPH()
    {
        return typeDireccionPH;
    }

    public void setTypeDireccionPH(String typeDireccionPH)
    {
        this.typeDireccionPH = typeDireccionPH;
    }

    public String getTypeDireccionLegalAgent()
    {
        return typeDireccionLegalAgent;
    }

    public void setTypeDireccionLegalAgent(String typeDireccionLegalAgent)
    {
        this.typeDireccionLegalAgent = typeDireccionLegalAgent;
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

    public UploadedFile getUploadConference()
    {
        return uploadConference;
    }

    public void setUploadConference(UploadedFile uploadConference)
    {
        this.uploadConference = uploadConference;
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

    public String getAddressComplLegalAgent()
    {
        return addressComplLegalAgent;
    }

    public void setAddressComplLegalAgent(String addressComplLegalAgent)
    {
        this.addressComplLegalAgent = addressComplLegalAgent;
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

    public UploadedFile getUploadAdministrativeAct()
    {
        return uploadAdministrativeAct;
    }

    public void setUploadAdministrativeAct(UploadedFile uploadAdministrativeAct)
    {
        this.uploadAdministrativeAct = uploadAdministrativeAct;
    }

    public String getRut()
    {
        return rut;
    }

    public void setRut(String rut)
    {
        this.rut = rut;
    }        

    public String getStrTipoArea()
    {
        return strTipoArea;
    }

    public void setStrTipoArea(String strTipoArea)
    {
        this.strTipoArea = strTipoArea;
    }

    public String getStrArea()
    {
        return strArea;
    }

    public void setStrArea(String strArea)
    {
        this.strArea = strArea;
    }

    public String getStrCantidad()
    {
        return strCantidad;
    }

    public void setStrCantidad(String strCantidad)
    {
        this.strCantidad = strCantidad;
    }

    public List<PHRequestDTO> getListUnidadesPHRequestDTO()
    {
        return listUnidadesPHRequestDTO;
    }

    public void setListUnidadesPHRequestDTO(List<PHRequestDTO> listUnidadesPHRequestDTO)
    {
        this.listUnidadesPHRequestDTO = listUnidadesPHRequestDTO;
    }

    public String getStrDescripcionC()
    {
        return strDescripcionC;
    }

    public void setStrDescripcionC(String strDescripcionC)
    {
        this.strDescripcionC = strDescripcionC;
    }

    public String getStrDescripcionP()
    {
        return strDescripcionP;
    }

    public void setStrDescripcionP(String strDescripcionP)
    {
        this.strDescripcionP = strDescripcionP;
    }

    public UploadedFile getUploadCatastral()
    {
        return uploadCatastral;
    }

    public void setUploadCatastral(UploadedFile uploadCatastral)
    {
        this.uploadCatastral = uploadCatastral;
    }
    
    public UploadedFile getUploadDocumentOfFiscaReviewer() {
		return uploadDocumentOfFiscaReviewer;
	}

	public void setUploadDocumentOfFiscaReviewer(UploadedFile uploadDocumentOfFiscaReviewer) {
		this.uploadDocumentOfFiscaReviewer = uploadDocumentOfFiscaReviewer;
	}

	public UploadedFile getUploadCertificateOfChamberOfCommerce() {
		return uploadCertificateOfChamberOfCommerce;
	}

	public void setUploadCertificateOfChamberOfCommerce(UploadedFile uploadCertificateOfChamberOfCommerce) {
		this.uploadCertificateOfChamberOfCommerce = uploadCertificateOfChamberOfCommerce;
	}
}
