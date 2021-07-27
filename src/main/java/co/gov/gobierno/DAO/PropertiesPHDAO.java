/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.DAO;
import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.Util.PropertiesHandler;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.LoggerFactory;

import oracle.jdbc.*;
import oracle.jdbc.pool.OracleDataSource;

/**
 *
 * @author DELL
 */
public class PropertiesPHDAO
{
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(PropertiesPHDAO.class);
    private final String tramites_DS_CTX;

    private Connection conn;
    private Properties prop;

    public PropertiesPHDAO() throws NamingException, SQLException
    {
        this.prop = PropertiesHandler.getPropertiesFile();
        this.tramites_DS_CTX = prop.getProperty("jndiOracle");
        AbreConexion();
    }

    private void AbreConexion()
    {
        try
        {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup(tramites_DS_CTX);
            conn = ds.getConnection();
        }
        catch (SQLException | NamingException ex)
        {
            log.error("Error al conectarse al DataSource: " + ex.toString());
        }

    }

    private static java.sql.Date convertDate(java.util.Date javaDate)
    {
        java.sql.Date sqlDate = null;
        if (javaDate != null)
        {
            sqlDate = new java.sql.Date(javaDate.getTime());
        }
        return sqlDate;
    }

    public List<PropertieDTO> ConsultPropertiesDB()
    {
        CallableStatement c = null;
        try
        {
            List<PropertieDTO> listPropertieDtos = new ArrayList();
            if (conn.isClosed())
            {
                AbreConexion();
            }
            String execRPC = "{call SP_CONSULTA_PARAMETROS_PH(?)}";
            c = conn.prepareCall(execRPC);
            c.registerOutParameter(1, OracleTypes.CURSOR);
            c.execute();
            ResultSet rs;
            rs = (ResultSet) c.getObject(1);
            while (rs.next())
            {
                PropertieDTO objPropertieDTO = new PropertieDTO(rs.getString(1),rs.getString(2), rs.getString(3), rs.getString(4));
                listPropertieDtos.add(objPropertieDTO);
            }
            c.close();
            conn.close();
            return listPropertieDtos;
        }
        catch (SQLException ex)
        {
            log.error("Error al leer el procedimiento almacenado: " + ex.toString());
            return null;
        }
        catch (Exception ex)
        {
            log.error("Error al leer el procedimiento almacenado: " + ex.toString());
            return null;
        }
        finally
        {
            if (c != null)
            {
                try
                {
                    c.close();
                }
                catch (SQLException ex)
                {
                    log.error("Error al leer el procedimiento almacenado: " + ex.toString());
                }
            }
            if (conn != null)
            {
                try
                {
                    conn.close();
                }
                catch (SQLException ex)
                {
                    log.error("Error al leer el procedimiento almacenado: " + ex.toString());
                }
            }
        }

    }
}
