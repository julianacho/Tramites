/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.ServiceImpl;

import co.gov.gobierno.DTO.DatosAdicionalesDTO;
import co.gov.gobierno.DTO.LogAuditoriaDTO;
import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.DTO.TerceroDTO;
import co.gov.gobierno.DTO.ThirdDTO;
import co.gov.gobierno.DTO.WebServiceDTO;
import co.gov.gobierno.DTO.YoungerDTO;
import co.gov.gobierno.Service.IndexService;
import co.gov.gobierno.Service.LogAuditoriaService;
import co.gov.gobierno.Service.TerceroService;
import co.gov.gobierno.Service.WebService;
import co.gov.gobierno.Util.JsonHandler;
import co.gov.gobierno.Util.PropertiesHandler;
import java.util.Properties;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import oracle.jdbc.*;
import oracle.jdbc.pool.OracleDataSource;

/**
 *
 * @author DELL
 */
@Stateless
public class IndexServiceImpl implements IndexService
{
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(IndexServiceImpl.class);
    @EJB
    WebService ws;
    
    @EJB
    LogAuditoriaService logService;
    
    @EJB
    TerceroService ts;
    
    private LogAuditoriaDTO logAuditoriaDTO = new LogAuditoriaDTO(0, "NA", "IP", "SO", "NA", "IndexServiceImpl", "NA", "NA", "NA", null);


    @Override
    public ThirdDTO ThirdConsult(ThirdDTO objThirdDTO, List<PropertieDTO> listPropertieDTOs)
    {
        WebServiceDTO objWebServiceDTO = new WebServiceDTO();
		
        log.info("Voy a consumir el servicio web de Consultar Tercero.");
		        
        List<TerceroDTO> listTerceroDtos = ts.ConsultarTerceroUsuario(objThirdDTO.getUser());
        Gson gson = new Gson();
        String json = gson.toJson(listTerceroDtos);
        
        if (listTerceroDtos.size() == 0 || listTerceroDtos == null)
        {
        	json = "";
        }
        
        objWebServiceDTO.setJsonFile(json);
        if(!objWebServiceDTO.getJsonFile().isEmpty())
        {
            objThirdDTO = JsonHandler.ReadThirdJsonString(objWebServiceDTO.getJsonFile(), objThirdDTO);

            WebServiceDTO objWebServiceDTO2 = new WebServiceDTO();
            log.info("Voy a consumir el servicio web de Consultar Datos Adicionales del Tercero.");
            log.info("Se consumé el servicio web de Consultar Datos Adicionales del Tercero correctamente.");
            List<DatosAdicionalesDTO> listDatosAdicionalesDtos = ts.BuscarDatosAdicionales(objThirdDTO.getIdNumber(), "Todos");
            gson = new Gson();
            json = gson.toJson(listDatosAdicionalesDtos);
            objWebServiceDTO2.setRespuesta(true);
            if (listDatosAdicionalesDtos.size() == 0 || listDatosAdicionalesDtos == null)
            {
            	json = "";
            	objWebServiceDTO2.setRespuesta(false);
            }
            if(objWebServiceDTO2.isRespuesta())
            {
                objThirdDTO = JsonHandler.ReadAdicionalData(objThirdDTO, json);
                for(YoungerDTO objYoungerDTO : objThirdDTO.getListYoungerDTO())
                {
                    WebServiceDTO objWebServiceDTO3 = new WebServiceDTO();
                    listDatosAdicionalesDtos = ts.BuscarDatosAdicionales(objYoungerDTO.getIdNumber(), "Todos");
                    json = gson.toJson(listDatosAdicionalesDtos);
                    objWebServiceDTO3.setRespuesta(true);
                    if (listDatosAdicionalesDtos.size() == 0 || listDatosAdicionalesDtos == null)
                    {
                    	json = "";
                    	objWebServiceDTO3.setRespuesta(false);
                    }
                    log.info("Voy a consumir el servicio web de Consultar Datos Adicionales del Tercero.");
                    
                    log.info("Se consumió el servicio web de Consultar Datos Adicionales del Tercero correctamente.");
                    if(objWebServiceDTO3.isRespuesta())
                    {
                        objYoungerDTO = JsonHandler.ReadAdicionalDataYounger(objYoungerDTO, json);
                    }
                }
                return objThirdDTO;
            }
            else
            {
                log.error("Error al consumir el servicio web de consultar Datos Adicionales del tercero.");
                this.logAuditoriaDTO.setAuMensajeError("ThirdConsult " + "Error al consumir el servicio web de consultar Datos Adicionales del tercero. ");
                logService.AddLogAuditoria(logAuditoriaDTO);
                return null;
            }

        }
        else
        {
            log.error("Error al consumir el servicio web de consultar tercero.");
            this.logAuditoriaDTO.setAuMensajeError("ThirdConsult " + "Error al consumir el servicio web de consultar tercero. ");
            logService.AddLogAuditoria(logAuditoriaDTO);
            return null;
        }
    }
}
    
