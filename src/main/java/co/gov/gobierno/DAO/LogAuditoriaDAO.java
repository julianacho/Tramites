package co.gov.gobierno.DAO;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

import org.slf4j.LoggerFactory;

import co.gov.gobierno.DTO.LogAuditoriaDTO;
import co.gov.gobierno.Util.PropertiesHandler;


@Stateless
public class LogAuditoriaDAO {
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(LogAuditoriaDAO.class);
	private final String tramites_DS_CTX;
	
    private Connection conn;
    private Properties prop;
    
    public LogAuditoriaDAO() throws NamingException, SQLException
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
    
    public Boolean AgregarLogAuditoria(LogAuditoriaDTO logAuditoriaDTO) {
    	PreparedStatement preparedStmt = null;
    	try {
            if (conn.isClosed())
            {
                AbreConexion();
            }
            
            if (log.isDebugEnabled()) {            	
            	log.error("Agregando Log a SQL - Usuario:  " + logAuditoriaDTO.getAuLogUsuario());
            	log.error("Agregando Log a SQL - Origen:  " + logAuditoriaDTO.getAuAplicacion());
            	log.error("Agregando Log a SQL - Request:  " + logAuditoriaDTO.getAuRequest().trim());
            	log.error("Agregando Log a SQL - Response:  " + logAuditoriaDTO.getAuResponse().trim());
            	log.error("Agregando Log a SQL - Mensaje:  " + logAuditoriaDTO.getAuMensajeError());
            }
            
            Statement stmt= conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT AU_LOG_SEQ.NEXTVAL FROM dual");

            long idSequence = 0L;
            if ( rs!=null && rs.next() ) {
            	idSequence = rs.getInt(1);
            }
            
            
            Calendar calendar = Calendar.getInstance();
            java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());
            StringBuilder queryBuilder = new StringBuilder();
            int sizeRequest1 = logAuditoriaDTO.getAuRequest().trim().length();
            int sizeResponse1 = logAuditoriaDTO.getAuResponse().trim().length();
            int sizeRequest2 = logAuditoriaDTO.getAuRequest().trim().length();
            int sizeResponse2 = logAuditoriaDTO.getAuResponse().trim().length();

            
            logAuditoriaDTO.setAuRequest(logAuditoriaDTO.getAuRequest().replace("'", "**"));
            logAuditoriaDTO.setAuResponse(logAuditoriaDTO.getAuResponse().replace("'", "**"));
            logAuditoriaDTO.setAuMensajeError(logAuditoriaDTO.getAuMensajeError().replace("'", "**"));
           
            if (sizeRequest1 > 4000) {  
            	sizeRequest1 = 4000;
            }
            
            
            if (sizeResponse1 > 4000) {  
            	sizeResponse1 = 4000;
            }
            queryBuilder.append(" INSERT INTO au_log (\r\n" );
            queryBuilder.append("    au_log_id,\r\n"); 
            queryBuilder.append("    au_log_usuario,\r\n"); 
            queryBuilder.append("    au_user_archivo,\r\n"); 
            queryBuilder.append("    au_aplicacion,\r\n"); 
            queryBuilder.append("    au_request,\r\n" ); 
            queryBuilder.append("    au_response,\r\n"); 
            queryBuilder.append("    au_mensaje_error\r\n");
            queryBuilder.append(") ");
            
            queryBuilder.append("VALUES (" + idSequence + ",");
            queryBuilder.append("'"  + logAuditoriaDTO.getAuLogUsuario() + "'"  + ","); 
            queryBuilder.append("'"  + logAuditoriaDTO.getAuUserArchivo() + "'" + ","); 
            queryBuilder.append("'"  + logAuditoriaDTO.getAuAplicacion() + "'" + ","); 
            queryBuilder.append("'"  + logAuditoriaDTO.getAuRequest().substring(0, sizeRequest1) + "'" + ","); 
            queryBuilder.append("'"  + logAuditoriaDTO.getAuResponse().trim().substring(0, sizeResponse1) + "'" + ","); 
            queryBuilder.append("'"  + logAuditoriaDTO.getAuMensajeError() + "'" + ")");
            
            preparedStmt = conn.prepareStatement(queryBuilder.toString());

            int  result = preparedStmt.executeUpdate();

            preparedStmt.close();
            conn.close();
            if (result > 0)
            	return true;
            else
            	return false;
		}
    	catch (SQLException ex)
        {
    		log.error("Error al Agregar de LogAuditoria: " + ex.toString());
            return false;
        }
    	catch (Exception e) {
			log.error("Error al Agregar de LogAuditoria: " + e.toString());
            return false;
		}        
    	finally
        {
            if (preparedStmt != null)
            {
                try
                {
                	preparedStmt.close();
                }
                catch (SQLException ex)
                {
                    log.error("Error al Agregar de LogAuditoria: " + ex.toString());
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
                    log.error("Error al Agregar de LogAuditoria: " + ex.toString());
                }
            }
        }    	
    }
    
    public List<LogAuditoriaDTO> findAll(){
    	Statement stmt = null;
    	List<LogAuditoriaDTO> lstLogAuditoria = new ArrayList<LogAuditoriaDTO>();
    	try {
            if (conn.isClosed())
            {
                AbreConexion();
            }
            String query = "SELECT au_log_id, au_log_usuario,au_ip_terminal,au_user_so, au_user_archivo,au_aplicacion,au_request, au_response,au_mensaje_error, au_fecha FROM  au_log;";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
            	LogAuditoriaDTO item = new LogAuditoriaDTO(
            			rs.getLong("au_log_id"),
            			rs.getString("au_log_usuario"),
            			rs.getString("au_ip_terminal"),
            			rs.getString("au_user_so"),
            			rs.getString("au_user_archivo"),
            			rs.getString("au_aplicacion"),
            			rs.getString("au_request"),
            			rs.getString("au_response"),
            			rs.getString("au_mensaje_error"),
            			rs.getDate("au_ip_terminal"));
            	
            	lstLogAuditoria.add(item);
            	
            }
            stmt.close();
            conn.close();
		}
    	catch (SQLException ex)
        {
            log.error("Error al Ejecutar Consulta de LogAuditoria: " + ex.toString());
            return null;
        }
    	catch (Exception e) {
			log.error("Error al Ejecutar Consulta de LogAuditoria: " + e.toString());
            return null;
		}        
    	finally
        {
            if (stmt != null)
            {
                try
                {
                	stmt.close();
                }
                catch (SQLException ex)
                {
                    log.error("Error al Ejecutar Consulta de LogAuditoria: " + ex.toString());
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
                    log.error("Error al Ejecutar Consulta de LogAuditoria: " + ex.toString());
                }
            }
        }
		return lstLogAuditoria;			
    }
}
