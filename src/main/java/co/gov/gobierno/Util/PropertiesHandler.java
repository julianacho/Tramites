/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.Util;

import co.gov.gobierno.DAO.AyudaDAO;
import co.gov.gobierno.DAO.PropertiesDAO;
import co.gov.gobierno.DAO.PropertiesPHDAO;
import co.gov.gobierno.DTO.AyudaDTO;
import co.gov.gobierno.DTO.PropertieDTO;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
public class PropertiesHandler 
{
	
    public static Properties getPropertiesFile()
    {
        Properties prop = new Properties();
        InputStream input = null;

        try {

            String filename = "config.properties";
            input =  PropertiesHandler.class.getClassLoader().getResourceAsStream(filename);
            if (input == null) 
            {
                System.out.println("No se encontro el archivo de propiedades con nombre: " + filename);
                return null;
            }
            else
            {
                prop.load(input);
                return prop;
            }
        }
        catch (IOException ex) 
        {
            System.out.println("Error al leer el archivo de propiedades: " + ex.toString());
        }
        finally 
        {
            if (input != null) 
            {
                try 
                {
                    input.close();
                }
                catch (IOException e) 
                {
                    System.out.println("Error al cerrar el flujo de lectura en la pagina del login: " + e.toString());
                }
            }
        }
        return null;
    }
    
    public static String PropertieValueOfKeyFromDB(List<PropertieDTO> listPropertieDTOs, String key)
    {
        try
        {
            for(PropertieDTO objPropertieDTO : listPropertieDTOs)
            {
                if(key.equals(objPropertieDTO.getKey()))
                {
                	System.out.println("Valor :"+objPropertieDTO.getValue());
                	return objPropertieDTO.getValue();
                }
            }
        }
        catch (Exception ex)
        {
            System.err.println("Error al conectarse al DataSource: " + ex.toString());
            return null;
        }
        return null;
    }
    
    public static String PropertieValueOfKeyAndIdServiceFromDB(List<PropertieDTO> listPropertieDTOs, String key, String idService)
    {
        try
        {
            for(PropertieDTO objPropertieDTO : listPropertieDTOs)
            {
                if(key.equals(objPropertieDTO.getKey()) && idService.equals(objPropertieDTO.getIdService()))
                {
                    return objPropertieDTO.getValue();
                }
            }
        }
        catch (Exception ex)
        {
            System.err.println("Error al conectarse al DataSource: " + ex.toString());
            return null;
        }
        return null;
    }
    
    public static String PropertieValueOfIdKeyFromDB(List<PropertieDTO> listPropertieDTOs, String idkey)
    {
        try
        {
            for(PropertieDTO objPropertieDTO : listPropertieDTOs)
            {
                if(idkey.equals(objPropertieDTO.getIdkey()))
                {
                    return objPropertieDTO.getValue();
                }
            }
        }
        catch (Exception ex)
        {
            System.err.println("Error al conectarse al DataSource: " + ex.toString());
            return null;
        }
        return null;
    }
    
    public static List<PropertieDTO> GetPropertiesListFromDB()
    {
        try
        {
            PropertiesDAO objPropertiesDAO = new PropertiesDAO();
            List<PropertieDTO> listPropertieDTOs = objPropertiesDAO.ConsultPropertiesDB();
            return listPropertieDTOs;
        }
        catch (NamingException | SQLException ex)
        {
            System.err.println("Error al conectarse al DataSource: " + ex.toString());
            return null;
        }
    }
    
    public static List<PropertieDTO> GetPHPropertiesListFromDB()
    {
        try
        {
            PropertiesPHDAO objPropertiesPHDAO = new PropertiesPHDAO();
            List<PropertieDTO> listPropertieDTOs = objPropertiesPHDAO.ConsultPropertiesDB();
            return listPropertieDTOs;
        }
        catch (NamingException | SQLException ex)
        {
            System.err.println("Error al conectarse al DataSource: " + ex.toString());
            return null;
        }
    }
    
    public static Map<String,String> AyudaDB(List<AyudaDTO> listAyudaDTO, String key)
    {
        Map<String,String> MapAyudaDB = new HashMap<String, String>();
        try
        {
            for(AyudaDTO objAyudaDTO : listAyudaDTO)
            {
                if(key.equals(objAyudaDTO.getIdFormulario()))
                {
                    MapAyudaDB.put("vTipo", objAyudaDTO.getTipo());
                    MapAyudaDB.put("vUrl", objAyudaDTO.getUrl());
                    return MapAyudaDB;
                }
            }
        }
        catch (Exception ex)
        {
            System.err.println("Error al conectarse al DataSource: " + ex.toString());
            return null;
        }
        return null;
    }
    
    
    public static List<AyudaDTO> GetAyudaDB()
    {
        try
        {
            AyudaDAO objAyudaDAO = new AyudaDAO();
            List<AyudaDTO> listAyudaDTO = objAyudaDAO.ConsultPropertiesAyuda();
            return listAyudaDTO;
        }
        catch (NamingException | SQLException ex)
        {
            System.err.println("Error al conectarse al DataSource: " + ex.toString());
            return null;
        }
    }
}
