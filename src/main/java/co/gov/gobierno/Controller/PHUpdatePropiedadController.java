/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.Controller;

import co.gov.gobierno.DTO.LegalAgentDTO;
import co.gov.gobierno.DTO.LocationDTO;
import co.gov.gobierno.DTO.PHRequestDTO;
import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.DTO.ThirdDTO;
import co.gov.gobierno.DTO.WebServiceDTO;
import co.gov.gobierno.DTO.YoungerDTO;
import co.gov.gobierno.Service.PHRegisterService;
import co.gov.gobierno.Service.PHUpdateService;
import co.gov.gobierno.Service.WebService;
import co.gov.gobierno.Util.EmailHandler;
import co.gov.gobierno.Util.JsonHandler;
import co.gov.gobierno.Util.PropertiesHandler;
import co.gov.gobierno.Util.ReporteHandler;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class PHUpdatePropiedadController implements Serializable
{
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(PHUpdatePropiedadController.class);

    private ThirdDTO objThirdDTO;
    private ThirdDTO objThirdDTOLA;
    private List<PropertieDTO> listPropertieDTOs;
    private List<PropertieDTO> listPHPropertieDTOs;
    private WebServiceDTO objInfoPropiedad;

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
    private String radicadopropiedad;
    private boolean oathCheckBox;
    private String typeDireccionPH;

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
    private Map<String, Object> parameters;

    

    private boolean alertShow;
    private boolean alertStyle;
    private String alertMessage;
    
    private String parametros;
    private String expedientePropiedad;
    private boolean showConference = true;
    private boolean showConferenceRecord = true;
    private boolean showPublicDocument = true;
    private boolean showAceptDocument = true;
    private boolean showIdDocumentCopy = true;
    private boolean showCertificateOfTraditionAndFreedom = true;
    private boolean showCertificateOfChamberOfCommercem = true;
    private String requireConference = "";
    private String requireConferenceRecord = "";
    private String requirePublicDocument = "";
    private String requireAceptDocument = "";
    private String requireIdDocumentCopy = "";
    private String requireCertificateOfTraditionAndFreedom = "";
    private String requireCertificateOfChamberOfCommercem = "";

	//ATRIBUTOS PARA EL REPRESENTANTE LEGAL-------------------------------------
    private String personType;
    private String idTypeLegalAgent;
    private String idNumberLegalAgent;
    private String firstNameLegalAgent;
    private String lastNameLegalAgent;
    private String secondNameLegalAgent;
    private String secondLastNameLegalAgent;
    private String birthDateLegalAgent;
    private String genderLegalAgent;
    private String nationalityLegalAgent;
    private String cellphoneLegalAgent;
    private String phoneLegalAgent;
    private String addressLegalAgent;
    private String addressComplLegalAgent;
    private String ruralAddressLegalAgent;
    private String neightborhoodLegalAgent;
    private String residenceCityLegalAgent;
    private String emailLegalAgent;
    private String locationLegalAgent;
    private String stratumLegalAgent;
    private String specialConditionLegalAgent;
    private String occupationLegalAgent;
    private String idGenreLegalAgent;
    private String idStatusLegalAgent;
    private String dateAgent;
    private String startAgent;
    private String endAgent;
    private String numberActAgent;
    private String dateActAgent;
    private String nameAgentJuridico;
    private String idNumberAgentJuridico;
    private String idTypeAgentJuridico;
    private String key;
    private String razonSocialLegalAgent = "";
    private String idTypeRepLegalAgent = "";
    private String completeNameLegalAgent = "";
    private String descTypeLegalAgent = "";
    private boolean disableIdsLegalAgent;
    private String typeDireccionLegalAgent;
    
    private String requireNaturalLegalAgent = "requeridoRepresentante";
    private String requireGeneralLegalAgent = "requeridoRepresentante";
    private String showNaturalLegalAgent = "";
    
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
    private List<PHRequestDTO> listUnidadesPHRequestDTO = new ArrayList<PHRequestDTO>();

    //AQUI TERMINAN LOS ATRIBUTOS PARA EL REPRESENTANTE LEGAL-------------------
    private UploadedFile uploadConference;
    private UploadedFile uploadConferenceRecord;
    private UploadedFile uploadPublicDocument;
    private UploadedFile uploadAceptDocument;
    private UploadedFile uploadIdDocumentCopy;
    private UploadedFile uploadCertificateOfTraditionAndFreedom;
    private UploadedFile uploadCertificateOfChamberOfCommerce;
	private UploadedFile uploadAdministrativeAct;
    private UploadedFile uploadCertificateCatastral;
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
    PHUpdateService phus;
    

    @PostConstruct
    public void Init()
    {
        try
        {
            this.listPropertieDTOs = indexController.getListPropertieDTOs();
            this.listPHPropertieDTOs = indexController.getListPHPropertieDTOs();
            this.expedientePropiedad = indexController.getExpedientPHExtincion();
            this.objThirdDTO = indexController.getObjThirdDTO();
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
            this.setObjInfoPropiedad(indexController.getObjPropiedadHorizontal());
            Map<String,String> infoPH = phus.readXmlConsultPH(objInfoPropiedad.getXmlFile());
            listUnidadesPHRequestDTO = phus.readUnidadesAreas(objInfoPropiedad.getXmlFile());
            this.strTipoArea = "1";
            this.radicadopropiedad = infoPH.get("radicado");
            this.name = infoPH.get("nombre");
            this.realStateRegistration = infoPH.get("matricula");
            this.registerDate = infoPH.get("fechaMatricula").split("T")[0];
            this.nit = infoPH.get("nit");
            this.rut = infoPH.get("rut");
            this.publicScriptNumber = infoPH.get("nroEscrituraPublica");
            this.publicScriptdate = infoPH.get("fechaEscrituraPublica").split("T")[0];
            this.folio = infoPH.get("folios");
            this.notaria = infoPH.get("notaria");
            this.address = infoPH.get("direccion");
            this.commonArea = infoPH.get("nroAreasComunes");
            this.type3 = infoPH.get("tipoPropiedad");
            this.locate = infoPH.get("localidad");
            this.regimen = infoPH.get("regimen");
            this.stratum = infoPH.get("estrato");
            this.neighborhood = infoPH.get("barrio");
            this.email = infoPH.get("correo");
            this.nroPrivateUnits = infoPH.get("nroUnidadesPrivadas");
            this.nroBloques = infoPH.get("nroBloques");
            this.nroCommonArea = infoPH.get("nroAreasComunes");
            this.nroEtapas = infoPH.get("nroEtapas");
            this.nroLocales = infoPH.get("nroLocalesComerciales");
            this.nroResidents = infoPH.get("nroResidentes");
            this.typeNroPrivateUnits = infoPH.get("typeUnidadesPrivadas");
            this.commonArea = infoPH.get("typeAreasComunes");
            this.emergencyPlan = infoPH.get("planEmergencia");
            this.contactPerson = infoPH.get("personaContacto");
            this.contactEmail = infoPH.get("correoContacto");
            this.contactPhone = infoPH.get("telefonoContacto");
            this.firstNameLegalAgent = infoPH.get("PrimerNombre");
            this.secondNameLegalAgent = infoPH.get("SegundoNombre");
            this.lastNameLegalAgent = infoPH.get("PrimerApellido");
            this.secondLastNameLegalAgent = infoPH.get("SegundoApellido");
            this.startAgent = infoPH.get("FechaInicio").split("T")[0];
            this.endAgent = infoPH.get("FechaFin").split("T")[0];
            this.numberActAgent = infoPH.get("SNumActadeAsamblea");
            this.emailLegalAgent = infoPH.get("SCorreoRepresentanteLegal");
            this.phoneLegalAgent = infoPH.get("STelefonoRepLegal");
            this.dateActAgent = infoPH.get("DFechaActaAsamblea").split("T")[0];
            this.addressLegalAgent = infoPH.get("SDireccion");
            this.idNumberLegalAgent = infoPH.get("NumerodeIdentificacion");
            this.nameAgentJuridico = infoPH.get("SNombreRepresentante");
            if (infoPH.get("IdTipoPersona").equals("PJ"))
            {
                this.razonSocialLegalAgent = infoPH.get("SNombreCompleto");
            } else {
                this.razonSocialLegalAgent = "";
            }
            this.completeNameLegalAgent = infoPH.get("SNombreCompleto");
            this.neightborhoodLegalAgent = infoPH.get("SBarrio");
            this.idNumberAgentJuridico = infoPH.get("NumerodeIdentificacionRL");
            this.idTypeRepLegalAgent = infoPH.get("IdTipoRepresentacion");
            this.idTypeAgentJuridico = infoPH.get("idTipodeIdentificacionRL");
            this.genderLegalAgent = infoPH.get("idGenero");
            this.idTypeLegalAgent = infoPH.get("IdTipoIdentificacion");
            this.personType = infoPH.get("IdTipoPersona");
            this.idStatusLegalAgent = infoPH.get("EstadoCivil");
            this.locationLegalAgent = infoPH.get("IdLocalidad");
            this.key = infoPH.get("key");
            this.typeDireccionLegalAgent = infoPH.get("IdTipodedirecionRL");
            this.typeDireccionPH = infoPH.get("IdTipodedirecion");
            this.parametros = indexController.getParametrosSubsanacionPropiedad();
            String[] params = parametros.split("-");
            
            for (String param : params)
            {
                if (param.equals("01"))
                {
                    this.showIdDocumentCopy = false;
                    this.requireIdDocumentCopy = "requerido";
                }
                if (param.equals("10"))
                {
                    this.showConferenceRecord = false;
                    this.requireConferenceRecord = "requerido";
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
                if (param.equals("15"))
                {
                    this.showAceptDocument = false;
                    this.requireAceptDocument = "requerido";
                }
                if (param.equals("69"))
                {
                    this.showConference = false;
                    this.requireConference = "requerido";
                }
                if (param.equals("19"))
                {
                    this.showCertificateOfChamberOfCommercem = false;
                    this.requireCertificateOfChamberOfCommercem = "requerido";
                }
            }
            
            String nameLegalAgent = "";
            if (this.personType.equals("PN"))
            {
                nameLegalAgent = firstNameLegalAgent + " " + secondNameLegalAgent + " " + lastNameLegalAgent + " " + secondLastNameLegalAgent;
                this.requireNaturalLegalAgent = "requeridoRepresentante";
                this.showNaturalLegalAgent = "";
                this.requireJuridicLegalAgent = "";
                this.requireGeneralLegalAgent = "";
                this.showJuridicLegalAgent = "none";
                this.readOnlyNatural = "true";
                this.readOnlyJuridic = "";
                this.readOnlyGeneral = "true";
            }
            if (this.personType.equals("PJ"))
            {
                nameLegalAgent = razonSocialLegalAgent;
                this.requireNaturalLegalAgent = "";
                this.showNaturalLegalAgent = "none";
                this.requireJuridicLegalAgent = "requeridoRepresentante";
                this.showJuridicLegalAgent = "";
                this.requireGeneralLegalAgent = "";
                this.readOnlyNatural = "";
                this.readOnlyJuridic = "true";
                this.readOnlyGeneral = "true";
            }
            
            Map<String, String> datos = new HashMap<String, String>();
            for (Map.Entry<String, String> entry : valuesidTypeRepLegalAgent.entrySet()) 
            {
                if (entry.getValue().equals(idTypeRepLegalAgent))
                {
                    datos.put(entry.getValue(), entry.getKey());
                }
            }
            
            this.descTypeLegalAgent = datos.get(idTypeRepLegalAgent);
            LegalAgentDTO objLegalAgentDTO = new LegalAgentDTO(false, nameLegalAgent, "tipo1", startAgent, endAgent, "estado",
                            idTypeLegalAgent, idNumberLegalAgent, addressLegalAgent, firstNameLegalAgent, lastNameLegalAgent,
                            secondNameLegalAgent, secondLastNameLegalAgent, birthDateLegalAgent, genderLegalAgent, addressComplLegalAgent,
                            nationalityLegalAgent, cellphoneLegalAgent, phoneLegalAgent, ruralAddressLegalAgent,
                            neightborhoodLegalAgent, residenceCityLegalAgent, emailLegalAgent, locationLegalAgent,
                            stratumLegalAgent, specialConditionLegalAgent, occupationLegalAgent, idTypeRepLegalAgent, datos.get(idTypeRepLegalAgent), razonSocialLegalAgent, 
                            nameAgentJuridico, idTypeAgentJuridico, idNumberAgentJuridico, etnia, cualEtnia, idStatusLegalAgent, numberActAgent, dateActAgent);
        }
        catch (Exception e)
        {
            setAlertShow(true);
            setAlertStyle(false);
            setAlertMessage("Ha ocurrido un error en el sistema por favor comuníquese con el administrador del sistema.");
            log.info("Error al cargar la pagina de registro de PH: " + e.toString());
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
                
                String nameLegalAgent = "";
                if (this.personType.equals("PN"))
                {
                    nameLegalAgent = firstNameLegalAgent + " " + secondNameLegalAgent + " " + lastNameLegalAgent + " " + secondLastNameLegalAgent;
                    this.completeNameLegalAgent = nameLegalAgent;
                    this.requireNaturalLegalAgent = "requeridoRepresentante";
                    this.showNaturalLegalAgent = "";
                    this.requireJuridicLegalAgent = "";
                    this.requireGeneralLegalAgent = "";
                    this.showJuridicLegalAgent = "none";
                    this.readOnlyNatural = "true";
                    this.readOnlyJuridic = "";
                    this.readOnlyGeneral = "true";
                }
                if (this.personType.equals("PJ"))
                {
                    nameLegalAgent = razonSocialLegalAgent;
                    this.completeNameLegalAgent = nameLegalAgent;
                    this.requireNaturalLegalAgent = "";
                    this.showNaturalLegalAgent = "none";
                    this.requireJuridicLegalAgent = "requeridoRepresentante";
                    this.showJuridicLegalAgent = "";
                    this.requireGeneralLegalAgent = "";
                    this.readOnlyNatural = "";
                    this.readOnlyJuridic = "true";
                    this.readOnlyGeneral = "true";
                }
                

                LegalAgentDTO objLegalAgentDTO = new LegalAgentDTO(false, nameLegalAgent, "tipo1", startAgent, endAgent, "estado",
                        idTypeLegalAgent, idNumberLegalAgent, addressLegalAgent, firstNameLegalAgent, lastNameLegalAgent,
                        secondNameLegalAgent, secondLastNameLegalAgent, birthDateLegalAgent, genderLegalAgent, addressComplLegalAgent,
                        nationalityLegalAgent, cellphoneLegalAgent, phoneLegalAgent, ruralAddressLegalAgent,
                        neightborhoodLegalAgent, residenceCityLegalAgent, emailLegalAgent, locationLegalAgent,
                        stratumLegalAgent, specialConditionLegalAgent, occupationLegalAgent, idTypeRepLegalAgent, datos.get(idTypeRepLegalAgent), razonSocialLegalAgent, 
                        nameAgentJuridico, idTypeAgentJuridico, idNumberAgentJuridico, etnia, cualEtnia, idStatusLegalAgent, numberActAgent, dateActAgent);
                this.setIdTypeLegalAgent(objLegalAgentDTO.getIdType());
                this.setIdNumberLegalAgent(objLegalAgentDTO.getIdNumber());
                this.setFirstNameLegalAgent(objLegalAgentDTO.getFirstName());
                this.setSecondNameLegalAgent(objLegalAgentDTO.getSecondName());
                this.setLastNameLegalAgent(objLegalAgentDTO.getLastName());
                this.setSecondLastNameLegalAgent(objLegalAgentDTO.getSecondLastName());
                this.setBirthDateLegalAgent(objLegalAgentDTO.getBirthDate());
                this.setGenderLegalAgent(objLegalAgentDTO.getGender());
                this.setNationalityLegalAgent(objLegalAgentDTO.getNationality());
                this.setCellphoneLegalAgent(objLegalAgentDTO.getCellphone());
                this.setPhoneLegalAgent(objLegalAgentDTO.getPhone());
                this.setAddressLegalAgent(objLegalAgentDTO.getAddress());
                this.setRuralAddressLegalAgent(objLegalAgentDTO.getRuralAddress());
                this.setLocationLegalAgent(objLegalAgentDTO.getLocation());
                this.setNeightborhoodLegalAgent(objLegalAgentDTO.getNeightborhood());
                this.setResidenceCityLegalAgent(objLegalAgentDTO.getResidenceCity());
                this.setOccupationLegalAgent(objLegalAgentDTO.getOccupation());
                this.setSpecialConditionLegalAgent(objLegalAgentDTO.getSpecialCondition());
                this.setStratumLegalAgent(objLegalAgentDTO.getStratum());
                this.setEmailLegalAgent(objLegalAgentDTO.getEmail());
                this.setEtnia(objLegalAgentDTO.getEtnia());
                this.setCualEtnia(objLegalAgentDTO.getCualEtnia());
                this.setStartAgent(objLegalAgentDTO.getBeginDate());
                this.setEndAgent(objLegalAgentDTO.getFinishDate());
                this.setDateActAgent(objLegalAgentDTO.getDateActAgent());
                this.setDescTypeLegalAgent(datos.get(objLegalAgentDTO.getIdTypeRepLegalAgent()));
                this.setIdStatusLegalAgent(objLegalAgentDTO.getIdStatusLegalAgent());
                setModalAlertStyle(true);
                setModalAlertShow(true);
                setModalAlertMessage("Se ha Actualizado el representante Legal");
                //this.objThirdDTO.getListLegalAgentDTOs().add(objLegalAgentDTO);
                //listLegalAgentDTOs.add(objLegalAgentDTO);
            }
            
        }
        catch (Exception e)
        {
            setModalAlertShow(true);
            setModalAlertStyle(false);
            setModalAlertMessage("Ha ocurrido un error en el sistema, contacte al administrador del sistema.");
            log.info("Error al registrar un nuevo representante legal: " + e.toString());
        }
    }
    
    public void updateLegalAgent()
    {
        try
        {
            Locale locale = new Locale(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idnumber"));
            String select = locale.toString();
            for (LegalAgentDTO objLegalAgentDTO : this.listLegalAgentDTOs)
            {
                if (objLegalAgentDTO.getIdNumber().equals(select))
                {
                    this.setIdTypeLegalAgent(objLegalAgentDTO.getIdType());
                    this.setIdNumberLegalAgent(objLegalAgentDTO.getIdNumber());
                    this.setFirstNameLegalAgent(objLegalAgentDTO.getFirstName());
                    this.setSecondNameLegalAgent(objLegalAgentDTO.getSecondName());
                    this.setLastNameLegalAgent(objLegalAgentDTO.getLastName());
                    this.setSecondLastNameLegalAgent(objLegalAgentDTO.getSecondLastName());
                    this.setBirthDateLegalAgent(objLegalAgentDTO.getBirthDate().substring(0, 10));
                    this.setGenderLegalAgent(objLegalAgentDTO.getGender());
                    this.setNationalityLegalAgent(objLegalAgentDTO.getNationality());
                    this.setCellphoneLegalAgent(objLegalAgentDTO.getCellphone());
                    this.setPhoneLegalAgent(objLegalAgentDTO.getPhone());
                    this.setAddressLegalAgent(objLegalAgentDTO.getAddress());
                    this.setRuralAddressLegalAgent(objLegalAgentDTO.getRuralAddress());
                    this.setLocationLegalAgent(objLegalAgentDTO.getLocation());
                    this.setNeightborhoodLegalAgent(objLegalAgentDTO.getNeightborhood());
                    this.setResidenceCityLegalAgent(objLegalAgentDTO.getResidenceCity());
                    this.setOccupationLegalAgent(objLegalAgentDTO.getOccupation());
                    this.setSpecialConditionLegalAgent(objLegalAgentDTO.getSpecialCondition());
                    this.setStratumLegalAgent(objLegalAgentDTO.getStratum());
                    this.setEmailLegalAgent(objLegalAgentDTO.getEmail());
                    this.setEtnia(objLegalAgentDTO.getEtnia());
                    this.setCualEtnia(objLegalAgentDTO.getCualEtnia());
                    this.disableIdsLegalAgent = true;
                    break;
                }
            }
        }
        catch (Exception e)
        {
            setAlertShow(true);
            setAlertStyle(false);
            setAlertMessage("Ha ocurrido un error inesperado. Contacte al administrador del sistema e intentelo más tarde.");
            log.info("Error: " + e.toString());
        }
    }
    
    public String Request()
    {
        Date now = new Date();
        SimpleDateFormat formatDay = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String day = formatDay.format(now);
        listPropertieDTOs.add(new PropertieDTO("USER", "USUARIO", this.objThirdDTO.getIdNumber(), "NA"));
        try
        {
            String radNumber = phus.GetRadicadoNumber(objThirdDTO, listPropertieDTOs);
            if(radNumber!=null && !radNumber.equals(""))
            {
                String appendNumber = phus.AppendExpedient(radNumber, expedientePropiedad, listPropertieDTOs);
                if(appendNumber!=null && !appendNumber.equals(""))
                {
                    String responseFile1 = phus.CreateAppend(radNumber, uploadConferenceRecord, listPropertieDTOs, PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs,"actaAsambleaOConsejo"));
                    int counter=0;
                    if(responseFile1 == null)
                    {
                        log.error("Error en la respuesta de crear Anexo #1....");
                        this.setAlertShow(true);
                        this.setAlertStyle(false);
                                                    this.setAlertMessage("Ocurrió un error en la respuesta de crear Anexos en el sistema contacte al administrador del sistema.");
                            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
                        
                    }
                    String responseFile2 = phus.CreateAppend(radNumber, uploadPublicDocument, listPropertieDTOs, PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs,"escrituraPública"));
                    if(responseFile2 == null)
                    {
                        log.error("Error en la respuesta de crear Anexo #2....");
                        this.setAlertShow(true);
                        this.setAlertStyle(false);
                                                    this.setAlertMessage("Ocurrió un error en la respuesta de crear Anexos en el sistema contacte al administrador del sistema.");
                            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
                        
                    }
                    String responseFile3 = phus.CreateAppend(radNumber, uploadAceptDocument, listPropertieDTOs, PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs,"documentoDeAceptación"));
                    if(responseFile3 == null)
                    {
                        log.error("Error en la respuesta de crear Anexo #3....");
                        this.setAlertShow(true);
                        this.setAlertStyle(false);
                                                    this.setAlertMessage("Ocurrió un error en la respuesta de crear Anexos en el sistema contacte al administrador del sistema.");
                            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
                        
                    }
                    String responseFile4 = phus.CreateAppend(radNumber, uploadIdDocumentCopy, listPropertieDTOs, PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs,"documentoDeIdentificación"));
                    if(responseFile4 == null)
                    {
                        log.error("Error en la respuesta de crear Anexo #4....");
                        this.setAlertShow(true);
                        this.setAlertStyle(false);
                                                    this.setAlertMessage("Ocurrió un error en la respuesta de crear Anexos en el sistema contacte al administrador del sistema.");
                            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
                        
                    }
                    String responseFile5 = phus.CreateAppend(radNumber, uploadCertificateOfTraditionAndFreedom, listPropertieDTOs, PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs,"certificadoDeTradiciónYLibertad"));
                    if(responseFile5 == null)
                    {
                        log.error("Error en la respuesta de crear Anexo #5....");
                        this.setAlertShow(true);
                        this.setAlertStyle(false);
                                                    this.setAlertMessage("Ocurrió un error en la respuesta de crear Anexos en el sistema contacte al administrador del sistema.");
                            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
                        
                    }
                    String responseFile6 = phus.CreateAppend(radNumber, uploadConference, listPropertieDTOs, PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs,"actaConsejo"));
                    if(responseFile6 == null)
                    {
                        log.error("Error en la respuesta de crear Anexo #6 Opcional....");
                        this.setAlertShow(true);
                        this.setAlertStyle(false);
                                                    this.setAlertMessage("Ocurrió un error en la respuesta de crear Anexos en el sistema contacte al administrador del sistema.");
                            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
                    }
                     String responseFile7 = phus.CreateAppend(radNumber, uploadAdministrativeAct, listPropertieDTOs, PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs,"actoAdministrativo"));
                    if(responseFile7 == null)
                    {
                        log.error("Error en la respuesta de crear Anexo #7 Opcional....");
                        this.setAlertShow(true);
                        this.setAlertStyle(false);
                                                    this.setAlertMessage("Ocurrió un error en la respuesta de crear Anexos en el sistema contacte al administrador del sistema.");
                            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
                    }
                     String responseFile8 = phus.CreateAppend(radNumber, uploadCertificateCatastral, listPropertieDTOs, PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs,"certificadoCatastral"));
                    if(responseFile8 == null)
                    {
                        log.error("Error en la respuesta de crear Anexo #8 Opcional....");
                        this.setAlertShow(true);
                        this.setAlertStyle(false);
                                                    this.setAlertMessage("Ocurrió un error en la respuesta de crear Anexos en el sistema contacte al administrador del sistema.");
                            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
                    }
                    String responseFile9 = phus.CreateAppend(radNumber, uploadCertificateOfChamberOfCommerce, listPropertieDTOs, PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs,"certificadoCamaraComercio"));
                   if(responseFile9 == null)
                   {
                       log.error("Error en la respuesta de crear Anexo #9 Opcional....");
                       this.setAlertShow(true);
                       this.setAlertStyle(false);
                                                   this.setAlertMessage("Ocurrió un error en la respuesta de crear Anexos en el sistema contacte al administrador del sistema.");
                            // this.setAlertMessage("Ocurrió un error inesperado en el sistema contacte al administrador del sistema.");
                   } 
                   String responseFile10 = phus.CreateAppend(radNumber, uploadDocumentOfFiscaReviewer, listPropertieDTOs, PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs,"certificadoRevisorFiscal"));
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
                        WebServiceDTO objWebServiceDTO = phus.RegisterSubsanacionPH(objThirdDTO, listPropertieDTOs,
                                listPHPropertieDTOs, expedientePropiedad, radNumber, name, state, nit, registerDate,
                                publicScriptNumber, publicScriptdate, type1, type2, type3, locate, neighborhood,
                                realStateRegistration, folio, address, email, stratum, regimen, typeNroPrivateUnits,
                                nroPrivateUnits, commonArea, nroCommonArea, presentationType, academicFormation,
                                nroResidents, nroBloques, nroEtapas, nroLocales, contactPerson, contactPhone,
                                contactEmail, notaria, emergencyPlan, personType, idTypeLegalAgent, idNumberLegalAgent,
                                firstNameLegalAgent, lastNameLegalAgent, secondNameLegalAgent, secondLastNameLegalAgent,
                                birthDateLegalAgent, nationalityLegalAgent, cellphoneLegalAgent, phoneLegalAgent,
                                addressLegalAgent+addressComplLegalAgent, emailLegalAgent, locationLegalAgent, stratumLegalAgent, 
                                specialConditionLegalAgent, occupationLegalAgent, genderLegalAgent, idStatusLegalAgent,
                                dateAgent, startAgent, endAgent, numberActAgent, dateActAgent, nameAgentJuridico,
                                idNumberAgentJuridico, idTypeAgentJuridico, idTypeRepLegalAgent, razonSocialLegalAgent, radicadopropiedad,
                                key,typeDireccionPH,typeDireccionLegalAgent, rut, listUnidadesPHRequestDTO);
                        if(objWebServiceDTO.isRespuesta() && !objWebServiceDTO.getXmlFile().isEmpty())
                        {
                            parameters = new HashMap<>();
                            parameters.put("radicado", radNumber);
                            parameters.put("fecha", day);
                            parameters.put("tramite", "Subsanación de Solicitud Propiedad Horizontal");
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
                            /*String emailResponse = EmailHandler.EnviarMensaje(this.objThirdDTO.getEmail(), "Subsanación de Solicitud Propiedad Horizontal",
                                    "Estimado(a) Ciudadano(a) <br><br> Su solicitud de Subsanaci&oacute;n de Propiedad Horizontal ha sido radicada exitosamente, su n&uacute;mero de radicado es el No " + radNumber + 
                                        ", su solicitud ser&aacute; atendida durante los pr&oacute;ximos 15 d&iacute;as h&aacute;biles. "
                                        + "<br><br><br><p style='font-size:10px'>\"Este correo es de tipo informativo; por favor no responda a este mensaje.</p>", this.listPropertieDTOs);

                            String emailResponse1 = EmailHandler.EnviarMensaje(email, "Subsanación de Solicitud Propiedad Horizontal",
                                    "Estimado(a) Ciudadano(a) <br><br> Su solicitud de Subsacaci&oacute;n de Propiedad Horizontal ha sido radicada exitosamente, su n&uacute;mero de radicado es el No " + radNumber + 
                                        ", su solicitud ser&aacute; atendida durante los pr&oacute;ximos 15 d&iacute;as h&aacute;biles. "
                                        + "<br><br><br><p style='font-size:10px'>\"Este correo es de tipo informativo; por favor no responda a este mensaje.</p>", this.listPropertieDTOs);
                            */
                            //if(!emailResponse.contains("Error") || !emailResponse1.contains("Error"))
                            //{
                                indexController.setAlertStylePH(true);
                                indexController.setResultProcessPH("Señor ciudadano su solicitud ha sido radicada exitosamente su" 
                                            + " número de radicado es el No " + radNumber + ", su solicitud será resuelta en un plazo maximo de 15 días hábiles");
                                this.setAlertStyle(true);
                                this.setAlertMessage("Señor ciudadano su solicitud ha sido radicada exitosamente su" 
                                        + " número de radicado es el No " + radNumber + ", su solicitud será será resuelta en un plazo maximo de 15 días hábiles");
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
        setValuesIdType(indexController.getValuesIdType());
        valuesIdType.remove("TARJETA DE IDENTIDAD");
        valuesIdType.remove("REGISTRO CIVIL DE NACIMIENTO");
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

    public WebServiceDTO getObjInfoPropiedad()
    {
        return objInfoPropiedad;
    }

    public void setObjInfoPropiedad(WebServiceDTO objInfoPropiedad)
    {
        this.objInfoPropiedad = objInfoPropiedad;
    }

    public List<PropertieDTO> getListPHPropertieDTOs()
    {
        return listPHPropertieDTOs;
    }

    public void setListPHPropertieDTOs(List<PropertieDTO> listPHPropertieDTOs)
    {
        this.listPHPropertieDTOs = listPHPropertieDTOs;
    }

    public String getPersonType()
    {
        return personType;
    }

    public void setPersonType(String personType)
    {
        this.personType = personType;
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

    public boolean isShowCertificateOfChamberOfCommercem() {
		return showCertificateOfChamberOfCommercem;
	}

	public void setShowCertificateOfChamberOfCommercem(boolean showCertificateOfChamberOfCommercem) {
		this.showCertificateOfChamberOfCommercem = showCertificateOfChamberOfCommercem;
	}

	public void setShowCertificateOfTraditionAndFreedom(boolean showCertificateOfTraditionAndFreedom)
    {
        this.showCertificateOfTraditionAndFreedom = showCertificateOfTraditionAndFreedom;
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

    public String getRazonSocialLegalAgent()
    {
        return razonSocialLegalAgent;
    }

    public void setRazonSocialLegalAgent(String razonSocialLegalAgent)
    {
        this.razonSocialLegalAgent = razonSocialLegalAgent;
    }

    public String getIdTypeRepLegalAgent()
    {
        return idTypeRepLegalAgent;
    }

    public void setIdTypeRepLegalAgent(String idTypeRepLegalAgent)
    {
        this.idTypeRepLegalAgent = idTypeRepLegalAgent;
    }

    public Map<String, String> getValuesidTypeRepLegalAgent()
    {
        return valuesidTypeRepLegalAgent;
    }

    public void setValuesidTypeRepLegalAgent(Map<String, String> valuesidTypeRepLegalAgent)
    {
        this.valuesidTypeRepLegalAgent = valuesidTypeRepLegalAgent;
    }

    public String getExpedientePropiedad()
    {
        return expedientePropiedad;
    }

    public void setExpedientePropiedad(String expedientePropiedad)
    {
        this.expedientePropiedad = expedientePropiedad;
    }

    public String getRadicadopropiedad()
    {
        return radicadopropiedad;
    }

    public void setRadicadopropiedad(String radicadopropiedad)
    {
        this.radicadopropiedad = radicadopropiedad;
    }

    public String getCompleteNameLegalAgent()
    {
        return completeNameLegalAgent;
    }

    public void setCompleteNameLegalAgent(String completeNameLegalAgent)
    {
        this.completeNameLegalAgent = completeNameLegalAgent;
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

    public Map<String, Object> getParameters()
    {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters)
    {
        this.parameters = parameters;
    }

    public String getDescTypeLegalAgent()
    {
        return descTypeLegalAgent;
    }

    public void setDescTypeLegalAgent(String descTypeLegalAgent)
    {
        this.descTypeLegalAgent = descTypeLegalAgent;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
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

    public boolean isShowConference()
    {
        return showConference;
    }

    public void setShowConference(boolean showConference)
    {
        this.showConference = showConference;
    }

    public String getRequireConference()
    {
        return requireConference;
    }

    public void setRequireConference(String requireConference)
    {
        this.requireConference = requireConference;
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

    public UploadedFile getUploadCertificateCatastral()
    {
        return uploadCertificateCatastral;
    }

    public void setUploadCertificateCatastral(UploadedFile uploadCertificateCatastral)
    {
        this.uploadCertificateCatastral = uploadCertificateCatastral;
    }
    
    public UploadedFile getUploadCertificateOfChamberOfCommerce() {
		return uploadCertificateOfChamberOfCommerce;
	}

	public void setUploadCertificateOfChamberOfCommerce(UploadedFile uploadCertificateOfChamberOfCommerce) {
		this.uploadCertificateOfChamberOfCommerce = uploadCertificateOfChamberOfCommerce;
	}
	
    public String getRequireCertificateOfChamberOfCommercem() {
		return requireCertificateOfChamberOfCommercem;
	}

	public void setRequireCertificateOfChamberOfCommercem(String requireCertificateOfChamberOfCommercem) {
		this.requireCertificateOfChamberOfCommercem = requireCertificateOfChamberOfCommercem;
	}

}
