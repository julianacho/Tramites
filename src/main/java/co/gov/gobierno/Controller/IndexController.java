/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.Controller;

import co.gov.gobierno.DTO.LocationDTO;
import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.DTO.RequestDTO;
import co.gov.gobierno.DTO.ThirdDTO;
import co.gov.gobierno.DTO.WebServiceDTO;
import co.gov.gobierno.Service.IndexService;
import co.gov.gobierno.Service.TerceroService;
import co.gov.gobierno.Service.WebService;
import co.gov.gobierno.Util.JsonHandler;
import co.gov.gobierno.Util.PropertiesHandler;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.slf4j.LoggerFactory;

/**
 *
 * @author DELL
 */
@ManagedBean
@SessionScoped
public class IndexController implements Serializable
{
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(IndexController.class);
    private ThirdDTO objThirdDTO;
    private String authenticationToken="";
    private List<PropertieDTO> listPropertieDTOs;
    private List<PropertieDTO> listPHPropertieDTOs;
    private List<RequestDTO> listRequestDTOs;
    private RequestDTO objRequestDTO;
    private WebServiceDTO objPropiedadHorizontal;
    
    
    private String expedientPHExtincion;
    private String nitPHExtincion;
    private String nitNewPH;
    private String localidadPH;
    private String parametrosSubsanacionExtincion;
    private String casoSubsanacionExtincion;
    private String parametrosSubsanacionPropiedad;
    private String realStateRegistration;
    
    /*Parametros de Mensajes Informativos en diferentes modulos*/
    private String resultChangePass;
    private boolean alertStyleM;
    private boolean viewMessage;
    
    private String resultProcessPH;
    private boolean alertStylePH;
    private boolean viewMessagePH;
    
    
    private List<LocationDTO> valuesNeightborhood;
    private List<LocationDTO> valuesResidenceCity;
    private List<LocationDTO> valuesLocation;
    private Map<String, String> valuesIdType;
    private Map<String, String> valuesStratum;
    private Map<String, String> valuesSpecialCondition;
    private Map<String, String> valuesMainStreet;
    private Map<String, String> valuesStreetGeneratingPath;
    private Map<String, String> valuesOccupation;
    private Map<String, String> valuesGender;
    private Map<String, String> valuesNationality;
    private Map<String, String> valuesSexualOrient;
    private Map<String, String> valuesSex;
    private Map<String, String> valuesEstado;
    private Map<String, String> valuesTypeNroPrivateUnits;
    private Map<String, String> valuesCommonAreas;
    private Map<String, String> valuesRegimen;
    private Map<String, String> valuesAcademicFormation;
    private Map<String, String> valuesPresentationType;
    private Map<String, String> valuesidStatusLegalAgent;
    private Map<String, String> valuesidTypeProperty;
    private Map<String, String> valuesidTypeRepLegalAgent;
    private Map<String, String> valuesProcedureType;
    private Map<String, String> valuesRelated;
    private Map<String, String> valuesEtnias;

	private Map<String, String> valuesMatriculaSelect1;
    private Map<String, String> valuesMatriculaSelect2;
    
    @EJB
    WebService ws;
    @EJB
    IndexService is;
    @EJB
    TerceroService ts;
    
    public String ConsultThird()
    {
        try
        {
            this.objThirdDTO = is.ThirdConsult(this.objThirdDTO, this.listPropertieDTOs);
            if(this.objThirdDTO != null)
            {
                setObjThirdDTO(this.objThirdDTO);
                return "success";
            }
            else
            {
                return null;
            }
        }
        catch(Exception e)
        {
            log.error("Error en el index, al consultar el tercero:"+e.getMessage());
            return null;
        }
    }
    
    public void init()
    {
        if (objThirdDTO == null)
        {
            try
            {
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                externalContext.redirect(externalContext.getRequestContextPath() + "/login.xhtml");
            }
            catch (IOException ex)
            {
            	log.error("Error al intentar redirigir la vista a la pagina de login.");
            }
        }
    }
    
    public void FillMatriculaSelect1() {
        
    	setValuesMatriculaSelect1(ts.ConsultarParametro("TIPO_MATRICULA_1", 0));
        valuesMatriculaSelect1 = new LinkedHashMap<>();
        if (!valuesMatriculaSelect1.containsKey("50")) {        
        	valuesMatriculaSelect1.put("50", "50");
        	setValuesMatriculaSelect1(valuesMatriculaSelect1);
        }
    	
    }
    
    public void FillMatriculaSelect2() {
        
    	setValuesMatriculaSelect2(ts.ConsultarParametro("TIPO_MATRICULA_2", 0));
        valuesMatriculaSelect2 = new LinkedHashMap<>();
        if (!valuesMatriculaSelect2.containsKey("N")) {        	
        	valuesMatriculaSelect2.put("N", "N");
        	setValuesMatriculaSelect2(valuesMatriculaSelect2);
        }  
        
        if (!valuesMatriculaSelect2.containsKey("C") ) {
        	valuesMatriculaSelect2.put("C", "C");	
        	setValuesMatriculaSelect2(valuesMatriculaSelect2);        	
        }
        
        if (!valuesMatriculaSelect2.containsKey("S")) {
        	valuesMatriculaSelect2.put("S", "S");
        	setValuesMatriculaSelect2(valuesMatriculaSelect2);        	
        }  
    }
    
    public void FillResidenceCityValues()
    {
        setValuesResidenceCity(ts.ConsultarParametroLocation("CIUDAD", 0));
    }
    
    public void FillLocationValues()
    {
       setValuesLocation(ts.ConsultarParametroLocation("LOCALIDAD", 0));
    }
    
    
    public void FillNeightborhoodValues()
    {
        setValuesNeightborhood(ts.ConsultarParametroLocation("BARRIO", 0));
    }
    
    
    public void FillIdTypeValues()
    {
        setValuesIdType(ts.ConsultarParametro("TIPO DOCUMENTO", 0));
        
    }
    
    public void FillStratumValues()
    {
       setValuesStratum(ts.ConsultarParametro("ESTRATO", 0));
    }
    
    public void FillSpecialConditionValues()
    {
       setValuesSpecialCondition(ts.ConsultarParametro("COND_ESPECIAL", 0));
    }
    
    public void FillMainStreetValues()
    {
        setValuesMainStreet(ts.ConsultarParametro("TIP_VIA_PRIN", 0));
    }
    
    public void FillStreetGeneratingPathValues()
    {
        setValuesStreetGeneratingPath(ts.ConsultarParametro("CUAD_VIA_GEN", 0));
    }
    
    public void FillOccupationValues()
    {
        setValuesOccupation(ts.ConsultarParametro("OCUPACION", 0));
    }
    
    public void FillNationalityValues()
    {
        setValuesNationality(ts.ConsultarPais());
    }
    
    public void FillGenderValues()
    {
        setValuesGender(ts.ConsultarParametro("GENERO", 0));
    }
    
    public void FillSexValues()
    {
        setValuesSex(ts.ConsultarParametro("SEXO", 0));
    }
    
    public void FillEtniasValues()
    {
        setValuesEtnias(ts.ConsultarParametro("ETNIA", 0));
    }
        
    public void FillSexualOrientValues()
    {
        setValuesSexualOrient(ts.ConsultarParametro("ORIENTACION SEXUAL", 0));
    }
    
    public void FillTypeRepLegalAgent()
    {
        setValuesidTypeRepLegalAgent(ts.ConsultarParametro("TIPO_REP_LEGAL", 0));
    }

    public void FillTypeProperty()
    {
       setValuesidTypeProperty(ts.ConsultarParametro("TIPO_PROPIEDAD", 0));
    }


    public void FillStatusLegalAgent()
    {
    	setValuesidStatusLegalAgent(ts.ConsultarParametro("ESTADO_CIVIL", 0));
    }

    
    public void FillAcademicFormationValues()
    {
        setValuesAcademicFormation(ts.ConsultarParametro("FORMACION_ACADEMICA", 0));
    }    

    public void FillPresentationTypeValues()
    {
    	setValuesPresentationType(ts.ConsultarParametro("TIPO_REP_LEGAL", 0));
    }


    public void FillRegimenValues()
    {
       setValuesRegimen(ts.ConsultarParametro("REGIMEN", 0));
    }    
    
    public void FillCommonAreasValues()
    {
    	setValuesCommonAreas(ts.ConsultarParametro("AREAS_COMUNES", 0));
    }
    
    public void FillPrivateUnitsValues()
    {
    	setValuesTypeNroPrivateUnits(ts.ConsultarParametro("UNIDADES_PRIVADAS", 0));
    }
    
    public void FillProcedureTypeValues() {
    	
    	setValuesProcedureType(ts.ConsultarParametro("TIPO_TRAMITE", 0));
    }

    public void FillRelatedValues() {
        setValuesRelated(ts.ConsultarParametro("PARENTESCO", 0));
    }

    public ThirdDTO getObjThirdDTO() {
        return objThirdDTO;
    }

    public void setObjThirdDTO(ThirdDTO objThirdDTO) {
        this.objThirdDTO = objThirdDTO;
    }

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

    public List<PropertieDTO> getListPropertieDTOs()
    {
        return listPropertieDTOs;
    }

    public void setListPropertieDTOs(List<PropertieDTO> listPropertieDTOs)
    {
        this.listPropertieDTOs = listPropertieDTOs;
    }

    public RequestDTO getObjRequestDTO()
    {
        return objRequestDTO;
    }

    public void setObjRequestDTO(RequestDTO objRequestDTO)
    {
        this.objRequestDTO = objRequestDTO;
    }

    public List<PropertieDTO> getListPHPropertieDTOs()
    {
        return listPHPropertieDTOs;
    }

    public void setListPHPropertieDTOs(List<PropertieDTO> listPHPropertieDTOs)
    {
        this.listPHPropertieDTOs = listPHPropertieDTOs;
    }

    public String getExpedientPHExtincion()
    {
        return expedientPHExtincion;
    }

    public void setExpedientPHExtincion(String expedientPHExtincion)
    {
        this.expedientPHExtincion = expedientPHExtincion;
    }

    public String getNitPHExtincion()
    {
        return nitPHExtincion;
    }

    public void setNitPHExtincion(String nitPHExtincion)
    {
        this.nitPHExtincion = nitPHExtincion;
    }

    public String getParametrosSubsanacionExtincion()
    {
        return parametrosSubsanacionExtincion;
    }

    public void setParametrosSubsanacionExtincion(String parametrosSubsanacionExtincion)
    {
        this.parametrosSubsanacionExtincion = parametrosSubsanacionExtincion;
    }

    public String getCasoSubsanacionExtincion()
    {
        return casoSubsanacionExtincion;
    }

    public void setCasoSubsanacionExtincion(String casoSubsanacionExtincion)
    {
        this.casoSubsanacionExtincion = casoSubsanacionExtincion;
    }

    public WebServiceDTO getObjPropiedadHorizontal()
    {
        return objPropiedadHorizontal;
    }

    public void setObjPropiedadHorizontal(WebServiceDTO objPropiedadHorizontal)
    {
        this.objPropiedadHorizontal = objPropiedadHorizontal;
    }

    public String getParametrosSubsanacionPropiedad()
    {
        return parametrosSubsanacionPropiedad;
    }

    public void setParametrosSubsanacionPropiedad(String parametrosSubsanacionPropiedad)
    {
        this.parametrosSubsanacionPropiedad = parametrosSubsanacionPropiedad;
    }

    public String getNitNewPH()
    {
        return nitNewPH;
    }

    public void setNitNewPH(String nitNewPH)
    {
        this.nitNewPH = nitNewPH;
    }

    public String getLocalidadPH()
    {
        return localidadPH;
    }

    public void setLocalidadPH(String localidadPH)
    {
        this.localidadPH = localidadPH;
    }

    public List<RequestDTO> getListRequestDTOs()
    {
        return listRequestDTOs;
    }

    public void setListRequestDTOs(List<RequestDTO> listRequestDTOs)
    {
        this.listRequestDTOs = listRequestDTOs;
    }

    public String getResultChangePass()
    {
        return resultChangePass;
    }

    public void setResultChangePass(String resultChangePass)
    {
        this.resultChangePass = resultChangePass;
    }

    public boolean isAlertStyleM()
    {
        return alertStyleM;
    }

    public void setAlertStyleM(boolean alertStyleM)
    {
        this.alertStyleM = alertStyleM;
    }

    public boolean isViewMessage()
    {
        return viewMessage;
    }

    public void setViewMessage(boolean viewMessage)
    {
        this.viewMessage = viewMessage;
    }

    public String getResultProcessPH()
    {
        return resultProcessPH;
    }

    public void setResultProcessPH(String resultProcessPH)
    {
        this.resultProcessPH = resultProcessPH;
    }

    public boolean isAlertStylePH()
    {
        return alertStylePH;
    }

    public void setAlertStylePH(boolean alertStylePH)
    {
        this.alertStylePH = alertStylePH;
    }

    public boolean isViewMessagePH()
    {
        return viewMessagePH;
    }

    public void setViewMessagePH(boolean viewMessagePH)
    {
        this.viewMessagePH = viewMessagePH;
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

    public List<LocationDTO> getValuesLocation()
    {
        return valuesLocation;
    }

    public void setValuesLocation(List<LocationDTO> valuesLocation)
    {
        this.valuesLocation = valuesLocation;
    }

    public Map<String, String> getValuesEstado()
    {
        return valuesEstado;
    }

    public void setValuesEstado(Map<String, String> valuesEstado)
    {
        this.valuesEstado = valuesEstado;
    }

    public Map<String, String> getValuesTypeNroPrivateUnits()
    {
        return valuesTypeNroPrivateUnits;
    }

    public void setValuesTypeNroPrivateUnits(Map<String, String> valuesTypeNroPrivateUnits)
    {
        this.valuesTypeNroPrivateUnits = valuesTypeNroPrivateUnits;
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

    public Map<String, String> getValuesidTypeRepLegalAgent()
    {
        return valuesidTypeRepLegalAgent;
    }

    public void setValuesidTypeRepLegalAgent(Map<String, String> valuesidTypeRepLegalAgent)
    {
        this.valuesidTypeRepLegalAgent = valuesidTypeRepLegalAgent;
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

    public Map<String, String> getValuesEtnias()
    {
        return valuesEtnias;
    }

    public void setValuesEtnias(Map<String, String> valuesEtnias)
    {
        this.valuesEtnias = valuesEtnias;
    }


    public String getRealStateRegistration()
    {
        return realStateRegistration;
    }

    public void setRealStateRegistration(String realStateRegistration)
    {
        this.realStateRegistration = realStateRegistration;
    }

    public Map<String, String> getValuesMatriculaSelect1() {
		return valuesMatriculaSelect1;
	}

	public void setValuesMatriculaSelect1(Map<String, String> valuesMatriculaSelect1) {
		this.valuesMatriculaSelect1 = valuesMatriculaSelect1;
	}

	public Map<String, String> getValuesMatriculaSelect2() {
		return valuesMatriculaSelect2;
	}

	public void setValuesMatriculaSelect2(Map<String, String> valuesMatriculaSelect2) {
		this.valuesMatriculaSelect2 = valuesMatriculaSelect2;
	}    
    
    
    
    }
