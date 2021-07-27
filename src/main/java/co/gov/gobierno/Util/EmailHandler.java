/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.Util;

import co.gov.aressoluciones.EnvioEmailAres;
import co.gov.aressoluciones.dto.MensajeDto;
import co.gov.aressoluciones.dto.PropiedadesServerDto;
import co.gov.gobierno.Controller.PHRegisterController;
import co.gov.gobierno.DTO.PropertieDTO;
import java.util.List;

import org.slf4j.LoggerFactory;

/**
 *
 * @author DELL
 */
public class EmailHandler 
{
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(EmailHandler.class);
	
    public EmailHandler(String a, String asunto, String mensaje, List<PropertieDTO> listPropertieDTOs)
    {
        EnviarMensaje(a, asunto, mensaje, listPropertieDTOs);
    }
    
    public static String EnviarMensaje(String a, String asunto, String mensaje, List<PropertieDTO> listPropertieDTOs)
    {
        MensajeDto objMensajeDto = new MensajeDto();
        objMensajeDto.setA(a);
        objMensajeDto.setAsunto(asunto);
        objMensajeDto.setMensaje(mensaje);
        objMensajeDto.setDe(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "correoDe"));
        
        PropiedadesServerDto objPropiedadesServerDto = new PropiedadesServerDto();
        objPropiedadesServerDto.setHost(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "Host"));
        objPropiedadesServerDto.setPuerto(Integer.parseInt(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "Puerto")));
        objPropiedadesServerDto.setRemitente(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "Remitente"));
        objPropiedadesServerDto.setTls(Boolean.parseBoolean(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "Tls")));
        objPropiedadesServerDto.setUsuario(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "Usuario"));
        objPropiedadesServerDto.setVerLog(Boolean.parseBoolean(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "VerLog")));
        objPropiedadesServerDto.setContrasena(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "Contraseña"));
        objPropiedadesServerDto.setAutenticacion(Boolean.parseBoolean(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "Autenticacion")));
        
        EnvioEmailAres objEnvioEmailAres = new EnvioEmailAres();
        String result = objEnvioEmailAres.EnvioEmail(objMensajeDto, objPropiedadesServerDto);
        log.info("Enviando Correo: " + result);
        return result;
    }
            
    
    
    
}
