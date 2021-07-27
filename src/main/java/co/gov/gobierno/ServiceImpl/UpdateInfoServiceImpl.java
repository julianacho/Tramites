/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.ServiceImpl;

import co.gov.gobierno.DTO.LogAuditoriaDTO;
import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.DTO.WebServiceDTO;
import co.gov.gobierno.Service.LogAuditoriaService;
import co.gov.gobierno.Service.UpdateInfoService;
import co.gov.gobierno.Service.WebService;
import co.gov.gobierno.Util.PropertiesHandler;
import java.util.List;
import java.util.Properties;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.LoggerFactory;

/**
 *
 * @author DELL
 */
@Stateless
public class UpdateInfoServiceImpl implements UpdateInfoService
{
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(UpdateInfoServiceImpl.class);
    @EJB
    WebService ws;
    
    @EJB
    LogAuditoriaService logService;
    
    private LogAuditoriaDTO logAuditoriaDTO = new LogAuditoriaDTO(0, "NA", "IP", "SO", "NA", "UpdateInfoServiceImpl", "NA", "NA", "NA", null);
   

    @Override
    public WebServiceDTO CreateThird(String idType, String idNumber, String firstName, String secondName, String lastName, 
            String secondLastName, String birthDate, String gender, String sexo, String sexualOrient, String nationality, String cellphone, String phone, 
            String address, String ruralAddress, String location, String neightborhood, String residenceCity, String occupation, 
            String specialCondition, String stratum, String email, String user, List<PropertieDTO> listPropertieDTOs) 
    {
        //Actualizar tercero-------------------------------------------------------------------
        String params = "{\"datosBasicos\": {"
                + "\n\"fechaFinVigencia\" : \"" + PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "fechaFinVigencia") + "\","
                + "\n\"numeroIdentificacion\": \"" + idNumber + "\","
                + "\n\"fechaNacimiento\": \"" + birthDate + "T09:09:09Z\","
                + "\n\"primerApellido\": \"" + lastName + "\","
                + "\n\"primerNombre\": \"" + firstName + "\","
                + "\n\"segundoApellido\": \"" + secondLastName + "\","
                + "\n\"segundoNombre\": \"" + secondName + "\","
                + "\n\"sexo\": \"" + sexo + "\","
                + "\n\"genero\": \"" + gender + "\","
                + "\n\"tipoIdentificacion\": \"" + idType + "\","
                + "\n\"tipoTercero\": \"" + PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "tipoTercero") + "\","
                + "\n\"usuario\": \"" + user + "\""
                + "},"
                + "\n\"fechaActualizacion\": \"" + PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "fechaActualizacion") + "\","
                + "\n\"usuarioModificacion\": \"" + user + "\","
                + "\n\"direccionIP\": \"" + PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "direccionIP") + "\","
                + "\n\"identificacionTercero\": \"" + idNumber.replaceAll("[^0-9]", "") + "\","
                + "\n\"ubicacion\":"
                + "\n{"
                + "\n\"barrio\" : \"" + neightborhood + "\","
                + "\n\"ciudad\": {"
                + "\n\"codigoCiudad\": \"23001\","
                + "\n\"identificador\": \"" + residenceCity + "\","
                + "\n\"nombreCiudad\": \"\""
                + "\n},"
                + "\n\"correoElectronico\" : \"" + email + "\","
                + "\n\"direccion\" : \"" + address.replace(" ","") + "\","
                + "\n\"direccionRural\" : \"" + ruralAddress + "\","
                + "\n\"estado\" : \"1\","
                + "\n\"fechaFinVigencia\" : \"" + PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "fechaFinVigencia2") + "\","
                + "\n\"localidad\" : \"" + location + "\","
                + "\n\"telefonoCelular\" : \"" + cellphone + "\","
                + "\n\"telefonoFijo\" : \"" + phone + "\","
                + "\n\"tipoZona\" : \"" + PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "tipoZona") + "\""
                + "\n}"
                + "\n}";
        WebServiceDTO objWebServiceDTO = new WebServiceDTO();
        objWebServiceDTO.getHeader().put("Content-Type", "application/json");
        objWebServiceDTO.setMethod("POST");
        objWebServiceDTO.setParams(params);
        objWebServiceDTO.setUrl(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "urlTerceros") + "/tercero/registrar");
        log.info("Voy a consumir el servicio web de Crear tercero para actualizar.");
        objWebServiceDTO = ws.postServiceWithParam(objWebServiceDTO);
        log.info("Se consumió el servicio web de Crear tercero para actualizar correctamente.");
        //Fin Actualización tercero-------------------------------------------------------------------
        
        //Envío Listas Adicionales--------------------------------------------------------------
        this.logAuditoriaDTO.setAuMensajeError("CreateThird " + "Se consumió el servicio web de Crear tercero para actualizar correctamente. ");
        this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
        this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
        this.logAuditoriaDTO.setAuLogUsuario(idNumber);
        this.logAuditoriaDTO.setAuAplicacion("TERCERO");
        logService.AddLogAuditoria(logAuditoriaDTO);
        if(objWebServiceDTO.isRespuesta())
        {
            String params2 = " [{\n" +
                "    \"estado\": \"5\",\n" +
                "    \"identificacionTercero\": \"" + idNumber + "\",\n" +
                "    \"tipoDatoAdicional\": {\n" +
                "      \"identificador\": \"4\"\n" +
                "    },\n" +
                "    \"valor\": \"" + occupation + "\"\n" +
                "  }, {\n" +
                "    \"estado\": \"1\",\n" +
                "    \"identificacionTercero\": \"" + idNumber + "\",\n" +
                "    \"tipoDatoAdicional\": {\n" +
                "      \"identificador\": \"3\"\n" +
                "    },\n" +
                "    \"valor\": \"" + nationality + "\"\n" +
                "  }, {\n" +
                "    \"estado\": \"1\",\n" +
                "    \"identificacionTercero\": \"" + idNumber + "\",\n" +
                "    \"tipoDatoAdicional\": {\n" +
                "      \"identificador\": \"5\"\n" +
                "    },\n" +
                "    \"valor\": \"" + stratum + "\"\n" +
                "  }, {\n" +
                "    \"estado\": \"1\",\n" +
                "    \"identificacionTercero\": \"" + idNumber + "\",\n" +
                "    \"tipoDatoAdicional\": {\n" +
                "      \"identificador\": \"6\"\n" +
                "    },\n" +
                "    \"valor\": \"" + specialCondition + "\"\n" +
                "  }, {\n" +
                "    \"estado\": \"1\",\n" +
                "    \"identificacionTercero\": \"" + idNumber + "\",\n" +
                "    \"tipoDatoAdicional\": {\n" +
                "      \"identificador\": \"7\"\n" +
                "    },\n" +
                "    \"valor\": \"" + gender + "\"\n" +
                "  }, {\n" +
                "    \"estado\": \"1\",\n" +
                "    \"identificacionTercero\": \"" + idNumber + "\",\n" +
                "    \"tipoDatoAdicional\": {\n" +
                "      \"identificador\": \"8\"\n" +
                "    },\n" +
                "    \"valor\": \"" + sexo + "\"\n" +
                "  }, {\n" +
                "    \"estado\": \"1\",\n" +
                "    \"identificacionTercero\": \"" + idNumber + "\",\n" +
                "    \"tipoDatoAdicional\": {\n" +
                "      \"identificador\": \"9\"\n" +
                "    },\n" +
                "    \"valor\": \"" + sexualOrient + "\"\n" +
                "  }]\n";
            WebServiceDTO objWebServiceDTO2 = new WebServiceDTO();
            objWebServiceDTO2.setMethod("POST");
            objWebServiceDTO2.getHeader().put("Content-Type", "application/json");
            objWebServiceDTO2.setParams(params2);
            objWebServiceDTO2.setUrl(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "urlTerceros") + "/tercero/datosadicionales/registrar");
            log.info("Voy a consumir el servicio web de crear las listas adicionales de tercero.");
            objWebServiceDTO2 = ws.postServiceWithParam(objWebServiceDTO2);
            log.info("Se consumió el servicio web de crear las listas adicionales de tercero correctamente.");
            this.logAuditoriaDTO.setAuMensajeError("CreateThird " + "Se consumió el servicio web de crear las listas adicionales de tercero correctamente. ");
            this.logAuditoriaDTO.setAuRequest(objWebServiceDTO2.getParams());
            this.logAuditoriaDTO.setAuResponse(objWebServiceDTO2.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
            this.logAuditoriaDTO.setAuLogUsuario(idNumber);
            this.logAuditoriaDTO.setAuAplicacion("TERCERO");
            logService.AddLogAuditoria(logAuditoriaDTO);
            //Acá debe retornar el objeto objWebServiceDTO #2, esto apenas corrijan el servicio de crear las listas adicionales.
            return objWebServiceDTO;
        }
        else
        {
            return null;
        }
    }
    
}
