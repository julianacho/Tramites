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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;

import co.gov.gobierno.DTO.AyudaDTO;
import co.gov.gobierno.DTO.LegalAgentDTO;
import co.gov.gobierno.DTO.LocationDTO;
import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.DTO.RequestDTO;
import co.gov.gobierno.DTO.ThirdDTO;
import co.gov.gobierno.DTO.WebServiceDTO;
import co.gov.gobierno.Service.PHConsultInitService;
import co.gov.gobierno.Service.PHExtincionService;
import co.gov.gobierno.Util.Base64Handler;
import co.gov.gobierno.Util.PropertiesHandler;
import co.gov.gobierno.Util.XmlHandler;

/**
 *
 * @author DELL
 */
@ManagedBean
@ViewScoped
public class PHConsultInitController implements Serializable
{

    private static long serialVersionUID = 1L;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(PHConsultInitController.class);

    private ThirdDTO objThirdDTO;
    private List<PropertieDTO> listPropertieDTOs;
    private List<PropertieDTO> listPHPropertieDTOs;

    private boolean alertShow = false;
    private boolean alertStyle = false;
    private String alertMessage = "";

    private String nit;
    private String localidad;
    private String nombre;
    private String tBusqueda = "1";
    private String verNit = "";
    private String verNombre = "none";
    private String labelBusqueda = "Matricula inmobiliaria";
    private String helpMessage = "Ingrese la matricula inmobiliaria de la Propiedad Horizontal, No use puntos, guiones ni espacios";
    private boolean disableLocation = true;
    private String namePH;
    private String addressPH;
    private String legalAgentPH;
    private String dateInitPH;
    private String dateFinalPH;
    private String consultErrorMessage;
    private String messageTramitePropiedad;
    private String expedientPropiedad;
    private String localidadPropiedad;
    private boolean noResult;
    private boolean result;
    private boolean msjTramite;
    private boolean updatePropiedadHorizontal;
    private boolean updateRepresentantePropiedadHorizontal;
    private boolean extincionPropiedadHorizontal;
    private String showButtonNew;
    private String showButtonNewClosed;
    
    private Map<String,String> MapAyudaDB;
	private List<SelectItem>  valuesMatriculaSelect1;
    private List<SelectItem>  valuesMatriculaSelect2;
    private List<AyudaDTO> listAyudaDTO;
    private String UrlVideoRegistro;

    private List<LocationDTO> valuesLocation;

    private List<RequestDTO> listRequestDTOs;
    private LegalAgentDTO objLegalAgentDTOsLA;

    @ManagedProperty(value = "#{indexController}")
    private IndexController indexController;

    @EJB
    PHConsultInitService phcis;
    
    @EJB
    PHExtincionService phExtIs;

    public void FindPH()
    {
        this.setAlertShow(false);
        this.setAlertStyle(false);
        this.setAlertMessage("");

        indexController.setAlertStylePH(false);
        indexController.setResultProcessPH("");

        WebServiceDTO objWebServiceDTO = phcis.FindPH(listPropertieDTOs, getNit(), this.localidad, getNombre());
        objLegalAgentDTOsLA = new LegalAgentDTO();
        if (objWebServiceDTO != null)
        {
            Map<String, String> infoPH = phcis.readXmlConsultPH(objWebServiceDTO.getXmlFile(), "");
            if (infoPH != null)
            {
                try
                {
                    String[] initD = infoPH.get("dateInitPH").split("T");
                    String[] endtD = infoPH.get("dateFinalPH").split("T");

                    initD = initD[0].split("-");
                    endtD = endtD[0].split("-");
                    this.namePH = infoPH.get("namePH");
                    this.addressPH = infoPH.get("addressPH");
                    this.legalAgentPH = infoPH.get("legalAgentPH");
                    this.dateInitPH = initD[2] + "-" + initD[1] + "-" + initD[0];
                    this.dateFinalPH = endtD[2] + "-" + endtD[1] + "-" + endtD[0];
                    if (infoPH.get("stateFinal").equals("S"))
                    {
                        objLegalAgentDTOsLA.setAddress(infoPH.get("SDireccion"));
                        objLegalAgentDTOsLA.setTypeDireccionLegalAgent(infoPH.get("IdTipodedirecionRL"));
                        objLegalAgentDTOsLA.setEmail(infoPH.get("SCorreoRepresentanteLegal"));
                        objLegalAgentDTOsLA.setFirstName(infoPH.get("SPrimerNombre"));
                        objLegalAgentDTOsLA.setSecondName(infoPH.get("SSegundoNombre"));
                        objLegalAgentDTOsLA.setLastName(infoPH.get("SPrimerApellido"));
                        objLegalAgentDTOsLA.setSecondLastName(infoPH.get("SSegundoApellido"));
                        objLegalAgentDTOsLA.setPhone(infoPH.get("STelefonoRepLegal"));
                        objLegalAgentDTOsLA.setBeginDate(infoPH.get("DFechaInicio"));
                        objLegalAgentDTOsLA.setFinishDate(infoPH.get("DFechaFin"));
                        objLegalAgentDTOsLA.setRepresentType(infoPH.get("IdTipoRepresentacion"));
                        objLegalAgentDTOsLA.setPersonType(infoPH.get("IdTipoPersona"));
                        objLegalAgentDTOsLA.setName(infoPH.get("SNombreCompleto"));
                        objLegalAgentDTOsLA.setRazonSocialLegalAgent(infoPH.get("SNombreCompleto"));
                        objLegalAgentDTOsLA.setState(infoPH.get("EstadoCivil"));
                        objLegalAgentDTOsLA.setNeightborhood(infoPH.get("SBarrio"));
                        objLegalAgentDTOsLA.setLocation(infoPH.get("IdLocalidad"));
                        objLegalAgentDTOsLA.setGender(infoPH.get("idGenero"));
                        objLegalAgentDTOsLA.setIdTypeAgentJuridico(infoPH.get("idTipodeIdentificacionRL"));
                        objLegalAgentDTOsLA.setIdType(infoPH.get("IdTipoIdentificacion"));
                        objLegalAgentDTOsLA.setNameAgentJuridico(infoPH.get("SNombreRepresentante"));
                        objLegalAgentDTOsLA.setIdNumberAgentJuridico(infoPH.get("NumerodeIdentificacionRL"));
                        objLegalAgentDTOsLA.setIdNumber(infoPH.get("NumerodeIdentificacion"));
                        objLegalAgentDTOsLA.setActNumber(infoPH.get("SNumActadeAsamblea"));
                        objLegalAgentDTOsLA.setDateActNumber(infoPH.get("DFechaActaAsamblea"));
                        List<LegalAgentDTO> listObjLegalAgentDTOsLA = new ArrayList<LegalAgentDTO>();
                        listObjLegalAgentDTOsLA.add(objLegalAgentDTOsLA);
                        objThirdDTO.setListLegalAgentDTOs(listObjLegalAgentDTOsLA);
                        indexController.setObjThirdDTO(objThirdDTO);
                    }
                    setConsultErrorMessage("");
                    setResult(true);
                    setNoResult(false);
                    setMessageTramitePropiedad("");
                    if (infoPH.get("stateFinal").equals("T"))
                    {
                        setMsjTramite(true);
                        setUpdatePropiedadHorizontal(false);
                        setExtincionPropiedadHorizontal(false);
                        setUpdateRepresentantePropiedadHorizontal(false);
                        setMessageTramitePropiedad("La propiedad actualmente tiene un Tramite en proceso ante la Secretaria Distrital de Gobierno");
                        setShowButtonNewClosed("none");
                    }
                    if (infoPH.get("stateFinal").equals("A"))
                    {
                        setMsjTramite(false);
                        setUpdatePropiedadHorizontal(false);
                        setExtincionPropiedadHorizontal(true);
                        setUpdateRepresentantePropiedadHorizontal(true);
                        setShowButtonNewClosed("");

                    }
                    if (infoPH.get("stateFinal").equals("CL"))
                    {
                        setMsjTramite(false);
                        setUpdatePropiedadHorizontal(false);
                        setExtincionPropiedadHorizontal(false);
                        setUpdateRepresentantePropiedadHorizontal(false);
                        setShowButtonNewClosed("");
                    }
                    if (infoPH.get("stateFinal").equals("C"))
                    {
                        setMsjTramite(false);
                        setUpdatePropiedadHorizontal(false);
                        setExtincionPropiedadHorizontal(true);
                        setUpdateRepresentantePropiedadHorizontal(true);
                        setShowButtonNewClosed("none");
                    }
                    if (infoPH.get("stateFinal").equals("S"))
                    {
                        setMsjTramite(false);
                        setUpdatePropiedadHorizontal(false);
                        setExtincionPropiedadHorizontal(false);
                        setUpdateRepresentantePropiedadHorizontal(false);
                        setShowButtonNewClosed("none");
                        }
                    if (infoPH.get("stateFinal").equals("F"))
                    {
                        setMsjTramite(false);
                        setUpdatePropiedadHorizontal(false);
                        setExtincionPropiedadHorizontal(false);
                        setUpdateRepresentantePropiedadHorizontal(false);
                        setShowButtonNewClosed("none");
                    }
                   
                    this.setAlertShow(false);
                    this.expedientPropiedad = infoPH.get("expediente");
                    this.localidadPropiedad = infoPH.get("localidad");
                    this.listRequestDTOs.clear();
                    this.listRequestDTOs = phcis.getDataTablePH(objWebServiceDTO.getXmlFile(), listRequestDTOs, objThirdDTO.getUser());
              
                    if (tBusqueda.equals("2"))
                    {
                        this.verNombre = "";
                        this.verNit = "none";
                        this.labelBusqueda = "Nombre de la Propiedad";
                        this.helpMessage = "Ingrese la Localidad donde se encuentra ubicada la propiedad horizontal y el nombre exacto con el que está registrada recuerde que el sistema difiere entre mayúsculas y minúsculas";
                        setShowButtonNew("none");
                        this.disableLocation = false;
                        this.listRequestDTOs.get((listRequestDTOs.size() - 1)).setShowSubsanacionPropiedad(false);
                        setUpdatePropiedadHorizontal(false);
                        setExtincionPropiedadHorizontal(false);
                        setUpdateRepresentantePropiedadHorizontal(false);

                    }
                    if (tBusqueda.equals("1"))
                    {
                        this.verNombre = "none";
                        this.verNit = "";
                        this.labelBusqueda = "Matricula inmobiliaria";
                        this.helpMessage = "Ingrese la matricula inmobiliaria de la Propiedad Horizontal, No use puntos, guiones ni espacios";
                        this.disableLocation = true;
                        setShowButtonNew("");
                    }
                } catch (Exception e)
                {
                    setConsultErrorMessage("Su Registro esta Incompleto, Comuniquese con el Administrador.");
                    setResult(false);
                    setNoResult(true);
                    setShowButtonNew("none");
                    setShowButtonNewClosed("");
                    if (tBusqueda.equals("2"))
                    {
                        this.verNombre = "";
                        this.verNit = "none";
                        this.labelBusqueda = "Nombre de la Propiedad";
                        this.helpMessage = "Ingrese la Localidad donde se encuentra ubicada la propiedad horizontal y el nombre exacto con el que está registrada recuerde que el sistema difiere entre mayúsculas y minúsculas";
                        this.disableLocation = false;
                    }
                    if (tBusqueda.equals("1"))
                    {
                        this.verNombre = "none";
                        this.verNit = "";
                        this.labelBusqueda = "Matricula inmobiliaria";
                        this.helpMessage = "Ingrese la matricula inmobiliaria de la Propiedad Horizontal sin el dígito de verificación, No use puntos, guiones ni espacios";
                        this.disableLocation = true;
                    }
                }
            } else
            {
                setConsultErrorMessage("No se encontro ningún registro.");
                setResult(false);
                setNoResult(true);
                setShowButtonNew("");
                setShowButtonNewClosed("");
                if (tBusqueda.equals("2"))
                {
                    this.verNombre = "";
                    this.verNit = "none";
                    this.labelBusqueda = "Nombre de la Propiedad";
                    this.helpMessage = "Ingrese la Localidad donde se encuentra ubicada la propiedad horizontal y el nombre exacto con el que está registrada recuerde que el sistema difiere entre mayúsculas y minúsculas";
                    setShowButtonNew("none");
                    this.disableLocation = false;
                }
                if (tBusqueda.equals("1"))
                {
                    this.verNombre = "none";
                    this.verNit = "";
                    this.labelBusqueda = "Matricula inmobiliaria";
                    this.helpMessage = "Ingrese la matricula inmobiliaria de la Propiedad Horizontal, No use puntos, guiones ni espacios";
                    setShowButtonNew("");
                    this.disableLocation = true;
                }
            }
            indexController.setObjPropiedadHorizontal(objWebServiceDTO);
        } else
        {
            setConsultErrorMessage("No se encontro ningún registro.");
            setResult(false);
            setNoResult(true);
            setShowButtonNew("");
            if (tBusqueda.equals("2"))
            {
                this.verNombre = "";
                this.verNit = "none";
                this.labelBusqueda = "Nombre de la Propiedad";
                this.helpMessage = "Ingrese la Localidad donde se encuentra ubicada la propiedad horizontal y el nombre exacto con el que está registrada recuerde que el sistema difiere entre mayúsculas y minúsculas";
                setShowButtonNew("none");
                this.disableLocation = false;
            }
            if (tBusqueda.equals("1"))
            {
                this.verNombre = "none";
                this.verNit = "";
                this.labelBusqueda = "Matricula inmobiliaria";
                this.helpMessage = "Ingrese matricula inmobiliaria de la Propiedad Horizontal, No use puntos, guiones ni espacios";
                setShowButtonNew("");
                this.disableLocation = true;
            }
        }
    }

    public String downloadDocument()
    {
            
        String radicadoSalida="";
        try
        {
            String Radicado = phExtIs.GetRadicadoNumber ( objThirdDTO, listPropertieDTOs);
            phExtIs. AppendExpedient(Radicado, expedientPropiedad, listPropertieDTOs);
            WebServiceDTO CreateCurrent = phcis.createCurrentCertificate(this.nit,Radicado, objThirdDTO,listPropertieDTOs);
            
            if (CreateCurrent == null) {
                this.setAlertShow(true);
                this.setAlertStyle(false);
                this.setAlertMessage("El certificado no se puede descargar, intentelo de nuevo mas tarde.");
                
                return null;
            }
            
            Thread.sleep(5000);
            
             WebServiceDTO objWebServiceDTO = phcis.FindPH(listPropertieDTOs, getNit(), this.localidad, getNombre());
            
            if (objWebServiceDTO != null)
            {
                Map<String, String> infoRd = phcis.readXmlConsultPH(objWebServiceDTO.getXmlFile(), "Certificate");
                if (infoRd != null)
                {
                    radicadoSalida = infoRd.get("certificadoRadicado");
                }
            }   
            
            
//            Locale locale = new Locale(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("radicado"));
//            WebServiceDTO objWebServiceDTO = new WebServiceDTO();
            objWebServiceDTO = phcis.DownloadORFEO(objThirdDTO, listPropertieDTOs, radicadoSalida);
//            for (RequestDTO objRequestDTO : listRequestDTOs)
//            {
//                if (objRequestDTO.getRadNumber().equals(radicado))
//                {
//                    objWebServiceDTO = phcis.DownloadORFEO(objThirdDTO, listPropertieDTOs, objRequestDTO);
//                }
//            }
            if (objWebServiceDTO.isRespuesta())
            {
                String fileName = "";
                String base64 = "";

                String responseService = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "return");
                String aux[] = responseService.split("\\|\\|");
                fileName = aux[3];
                base64 = aux[4];

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
            }
            return "success";
        } catch (IOException e)
        {
            this.setAlertShow(true);
            this.setAlertStyle(false);
            this.setAlertMessage("El certificado no se puede descargar, intentelo de nuevo mas tarde.");
            log.error("Error al descargar el archivo: " + e.toString());
        } catch (Exception e)
        {
            this.setAlertShow(true);
            this.setAlertStyle(false);
            this.setAlertMessage("El certificado no se puede descargar, intentelo de nuevo mas tarde.");
            log.error("Error al descargar el archivo: " + e.toString());
        }
        return null;
    }

    public String registerPH()
    {
        indexController.setRealStateRegistration(this.nit);
        return "phregister";
    }

    public String extincionPH()
    {
        indexController.setExpedientPHExtincion(expedientPropiedad);
        indexController.setRealStateRegistration(this.nit);
        return "extinctionph";
    }

    public String updateLegalAgent()
    {
        indexController.setExpedientPHExtincion(expedientPropiedad);
        indexController.setRealStateRegistration(this.nit);
        indexController.setLocalidadPH(localidadPropiedad);
        return "updaterepresentante";
    }

    public String subsanacionLegalAgent()
    {
        indexController.setExpedientPHExtincion(expedientPropiedad);
        indexController.setRealStateRegistration(this.nit);
        Locale locale = new Locale(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("Documentos"));
        String parametros = locale.toString();
        Locale locale2 = new Locale(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("caso"));
        String caso = locale2.toString();
        indexController.setParametrosSubsanacionExtincion(parametros);
        indexController.setCasoSubsanacionExtincion(caso);
        return "phsubsanacionlegalagent";
    }

    public String subsanacionExtincionPH()
    {
        indexController.setExpedientPHExtincion(expedientPropiedad);
        indexController.setRealStateRegistration(this.nit);
        Locale locale = new Locale(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("Documentos"));
        String parametros = locale.toString();
        Locale locale2 = new Locale(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("caso"));
        String caso = locale2.toString();
        indexController.setParametrosSubsanacionExtincion(parametros);
        indexController.setCasoSubsanacionExtincion(caso);
        return "subsanacionextinctionph";
    }

    public String subsanacionInscripcionPH()
    {
        indexController.setExpedientPHExtincion(expedientPropiedad);
        indexController.setRealStateRegistration(this.nit);
        Locale locale = new Locale(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("Documentos"));
        String parametros = locale.toString();
        indexController.setParametrosSubsanacionPropiedad(parametros);
        return "phupdatepropiedad";
    }

    @PostConstruct
    public void Init()
    {
        this.objThirdDTO = indexController.getObjThirdDTO();
        this.listPropertieDTOs = indexController.getListPropertieDTOs();
        this.listPHPropertieDTOs = indexController.getListPHPropertieDTOs();
        this.listRequestDTOs = new ArrayList<>();
        this.setAlertShow(indexController.isAlertStylePH());
        this.setAlertStyle(indexController.isAlertStylePH());
        this.setAlertMessage(indexController.getResultProcessPH());
        indexController.setAlertStylePH(false);
        indexController.setResultProcessPH("");
        this.noResult = false;
        this.result = false;
        this.msjTramite = false;
        FillLocationValues();

        this.listPropertieDTOs = PropertiesHandler.GetPropertiesListFromDB();
        this.listAyudaDTO = PropertiesHandler.GetAyudaDB();
        this.MapAyudaDB = PropertiesHandler.AyudaDB(listAyudaDTO, "PhConsulta");
        if (MapAyudaDB.get("vTipo").equals("1"))
        {
            this.setUrlVideoRegistro("<iframe width='600' height='315'  src='"+MapAyudaDB.get("vUrl")+"' frameborder='0' allow='accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture' allowfullscreen></iframe>");
        }
        if (MapAyudaDB.get("vTipo").equals("2"))
        {
            this.setUrlVideoRegistro("<img width='600' height='315' src='" + MapAyudaDB.get("vUrl") + "'/>");
        }
        
        indexController.FillMatriculaSelect1();
        valuesMatriculaSelect1 = new ArrayList<SelectItem>();
        for(Map.Entry<String, String> entry : indexController.getValuesMatriculaSelect1().entrySet()) {
        	valuesMatriculaSelect1.add(new SelectItem(entry.getKey(), entry.getValue()));        	
        }
        
        indexController.FillMatriculaSelect2();
        valuesMatriculaSelect2 = new ArrayList<SelectItem>();
        for(Map.Entry<String, String> entry : indexController.getValuesMatriculaSelect2().entrySet()) {
        	valuesMatriculaSelect2.add(new SelectItem(entry.getKey(), entry.getValue()));        	
        }
        
        
        this.setValuesMatriculaSelect1(valuesMatriculaSelect1);
        this.setValuesMatriculaSelect2(valuesMatriculaSelect2);
        //
    }
    

    public void FillLocationValues()
    {
        setValuesLocation(indexController.getValuesLocation());
    }

    public ThirdDTO getObjThirdDTO()
    {
        return objThirdDTO;
    }

    public void setObjThirdDTO(ThirdDTO objThirdDTO)
    {
        this.objThirdDTO = objThirdDTO;
    }

    public void setIndexController(IndexController indexController)
    {
        this.indexController = indexController;
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

    public String getNit()
    {
        return nit;
    }

    public void setNit(String nit)
    {
        this.nit = nit;
    }

    public String getNamePH()
    {
        return namePH;
    }

    public void setNamePH(String namePH)
    {
        this.namePH = namePH;
    }

    public String getAddressPH()
    {
        return addressPH;
    }

    public void setAddressPH(String addressPH)
    {
        this.addressPH = addressPH;
    }

    public String getLegalAgentPH()
    {
        return legalAgentPH;
    }

    public void setLegalAgentPH(String legalAgentPH)
    {
        this.legalAgentPH = legalAgentPH;
    }

    public String getDateInitPH()
    {
        return dateInitPH;
    }

    public void setDateInitPH(String dateInitPH)
    {
        this.dateInitPH = dateInitPH;
    }

    public String getDateFinalPH()
    {
        return dateFinalPH;
    }

    public void setDateFinalPH(String dateFinalPH)
    {
        this.dateFinalPH = dateFinalPH;
    }

    public String getConsultErrorMessage()
    {
        return consultErrorMessage;
    }

    public void setConsultErrorMessage(String consultErrorMessage)
    {
        this.consultErrorMessage= consultErrorMessage;
    }

    public boolean isNoResult()
    {
        return noResult;
    }

    public void setNoResult(boolean noResult)
    {
        this.noResult = noResult;
    }

    public boolean isResult()
    {
        return result;
    }

    public void setResult(boolean result)
    {
        this.result = result;
    }

    public List<RequestDTO> getListRequestDTOs()
    {
        return listRequestDTOs;
    }

    public void setListRequestDTOs(List<RequestDTO> listRequestDTOs)
    {
        this.listRequestDTOs = listRequestDTOs;
    }

    public List<PropertieDTO> getListPHPropertieDTOs()
    {
        return listPHPropertieDTOs;
    }

    public void setListPHPropertieDTOs(List<PropertieDTO> listPHPropertieDTOs)
    {
        this.listPHPropertieDTOs = listPHPropertieDTOs;
    }

    public boolean isMsjTramite()
    {
        return msjTramite;
    }

    public void setMsjTramite(boolean msjTramite)
    {
        this.msjTramite = msjTramite;
    }

    public String getMessageTramitePropiedad()
    {
        return messageTramitePropiedad;
    }

    public void setMessageTramitePropiedad(String messageTramitePropiedad)
    {
        this.messageTramitePropiedad = messageTramitePropiedad;
    }

    public boolean isUpdatePropiedadHorizontal()
    {
        return updatePropiedadHorizontal;
    }

    public void setUpdatePropiedadHorizontal(boolean updatePropiedadHorizontal)
    {
        this.updatePropiedadHorizontal = updatePropiedadHorizontal;
    }

    public boolean isUpdateRepresentantePropiedadHorizontal()
    {
        return updateRepresentantePropiedadHorizontal;
    }

    public void setUpdateRepresentantePropiedadHorizontal(boolean updateRepresentantePropiedadHorizontal)
    {
        this.updateRepresentantePropiedadHorizontal = updateRepresentantePropiedadHorizontal;
    }

    public boolean isExtincionPropiedadHorizontal()
    {
        return extincionPropiedadHorizontal;
    }

    public void setExtincionPropiedadHorizontal(boolean extincionPropiedadHorizontal)
    {
        this.extincionPropiedadHorizontal = extincionPropiedadHorizontal;
    }

    public String getExpedientPropiedad()
    {
        return expedientPropiedad;
    }

    public void setExpedientPropiedad(String expedientPropiedad)
    {
        this.expedientPropiedad = expedientPropiedad;
    }

    public String getShowButtonNew()
    {
        return showButtonNew;
    }

    public void setShowButtonNew(String showButtonNew)
    {
        this.showButtonNew = showButtonNew;
    }

    public String getLocalidadPropiedad()
    {
        return localidadPropiedad;
    }

    public void setLocalidadPropiedad(String localidadPropiedad)
    {
        this.localidadPropiedad = localidadPropiedad;
    }

    public String getShowButtonNewClosed()
    {
        return showButtonNewClosed;
    }

    public void setShowButtonNewClosed(String showButtonNewClosed)
    {
        this.showButtonNewClosed = showButtonNewClosed;
    }

    public String getLocalidad()
    {
        return localidad;
    }

    public void setLocalidad(String localidad)
    {
        this.localidad = localidad;
    }

    public List<LocationDTO> getValuesLocation()
    {
        return valuesLocation;
    }

    public void setValuesLocation(List<LocationDTO> valuesLocation)
    {
        this.valuesLocation = valuesLocation;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String gettBusqueda()
    {
        return tBusqueda;
    }

    public void settBusqueda(String tBusqueda)
    {
        this.tBusqueda = tBusqueda;
    }

    public String getVerNit()
    {
        return verNit;
    }

    public void setVerNit(String verNit)
    {
        this.verNit = verNit;
    }

    public String getVerNombre()
    {
        return verNombre;
    }

    public void setVerNombre(String verNombre)
    {
        this.verNombre = verNombre;
    }

    public String getLabelBusqueda()
    {
        return labelBusqueda;
    }

    public void setLabelBusqueda(String labelBusqueda)
    {
        this.labelBusqueda = labelBusqueda;
    }

    public boolean isDisableLocation()
    {
        return disableLocation;
    }

    public void setDisableLocation(boolean disableLocation)
    {
        this.disableLocation = disableLocation;
    }

    public String getHelpMessage()
    {
        return helpMessage;
    }

    public void setHelpMessage(String helpMessage)
    {
        this.helpMessage = helpMessage;
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
    
    public List<SelectItem>  getValuesMatriculaSelect1() {
		return valuesMatriculaSelect1;
	}

	public void setValuesMatriculaSelect1(List<SelectItem> valuesMatriculaSelect1) {
		this.valuesMatriculaSelect1 = valuesMatriculaSelect1;
	}

	public List<SelectItem>  getValuesMatriculaSelect2() {
		return valuesMatriculaSelect2;
	}

	public void setValuesMatriculaSelect2(List<SelectItem>  valuesMatriculaSelect2) {
		this.valuesMatriculaSelect2 = valuesMatriculaSelect2;
	}

}
