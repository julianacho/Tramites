/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.Util;

import co.gov.gobierno.DAO.LocationExpedientDAO;
import co.gov.gobierno.DAO.PropertiesDAO;
import co.gov.gobierno.DTO.LocationExpedientDTO;
import co.gov.gobierno.DTO.PropertieDTO;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;

import org.slf4j.LoggerFactory;

/**
 *
 * @author DELL
 */
public class LocationExpedientHandler
{
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(LocationExpedientHandler.class);
    public static List<LocationExpedientDTO> GetLocationExpedientListFromDB()
    {
        try
        {
            LocationExpedientDAO onjLocationExpedientDAO = new LocationExpedientDAO();
            List<LocationExpedientDTO> listLocationExpedientDTOs = onjLocationExpedientDAO.getLocationExpedientFromDB();
            return listLocationExpedientDTOs;
        }
        catch (NamingException | SQLException ex)
        {
            log.error("Error al conectarse al DataSource: " + ex.toString());
            return null;
        }
    }
    
    public static String getExpedientValueFromLocationList(List<LocationExpedientDTO> listLocationExpedientDTOs, String locationValue)
    {
        for(LocationExpedientDTO objLocationExpedientDTO : listLocationExpedientDTOs)
        {
            if(locationValue.equals(objLocationExpedientDTO.getLocationValue()))
            {
                return objLocationExpedientDTO.getExp();
            }
        }
        return null;
    }
}
