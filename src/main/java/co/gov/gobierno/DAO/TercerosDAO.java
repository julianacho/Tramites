package co.gov.gobierno.DAO;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

import org.apache.lucene.search.FieldCache.IntParser;
import org.slf4j.LoggerFactory;

import co.gov.gobierno.DTO.CiudadDTO;
import co.gov.gobierno.DTO.DatosAdicionalesDTO;
import co.gov.gobierno.DTO.DatosBasicosDTO;
import co.gov.gobierno.DTO.LogAuditoriaDTO;
import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.DTO.TerceroDTO;
import co.gov.gobierno.DTO.TipoDatoAdicionalDTO;
import co.gov.gobierno.DTO.UbicacionDTO;
import co.gov.gobierno.Util.PropertiesHandler;
import oracle.jdbc.OracleTypes;


@Stateless
public class TercerosDAO {
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(TercerosDAO.class);
	private final String terceros_DS_CTX;
	
    private Connection conn;
    private Properties prop;
    
    public TercerosDAO() throws NamingException, SQLException
    {
        this.prop = PropertiesHandler.getPropertiesFile();
        this.terceros_DS_CTX = prop.getProperty("jndiOracleTerceros");
        AbreConexion();
    }

    private void AbreConexion()
    {
        try
        {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup(terceros_DS_CTX);
            conn = ds.getConnection();
        }
        catch (SQLException | NamingException ex)
        {
            log.error("Error al conectarse al DataSource Terceros: " + ex.toString());
        }

    }
    
    public String Login(String Usuario, String Password) 
    {
        CallableStatement c = null;
        try
        {
            if (conn.isClosed())
            {
                AbreConexion();
            }
            String execRPC = "{call TERCEROS.PA_LOGIN_USUARIO(?,?,?)}";
            c = conn.prepareCall(execRPC);
            c.registerOutParameter(1, OracleTypes.CURSOR);
            c.setString(2, Usuario);
            c.setString(3, Password);
            c.execute();
            ResultSet rs;
            rs = (ResultSet) c.getObject(1);
            int Existe = -1;
            while (rs.next())
            {
            	Existe = rs.getInt(1);
            }
            c.close();
            conn.close();
            String token = null;
            if (Existe > 0)
            {
            	token = UUID.randomUUID().toString().toUpperCase() + "|userid|" + Usuario;
            }
            return token;
        }
        catch (SQLException ex)
        {
            log.error("Error al leer el procedimiento almacenado de obtener los paises: " + ex.toString());
            return null;
        }
        catch (Exception ex)
        {
            log.error("Error al leer el procedimiento almacenado de obtener los paises: " + ex.toString());
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
                    log.error("Error al leer el procedimiento almacenado de obtener las propiedades: " + ex.toString());
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
                    log.error("Error al leer el procedimiento almacenado de obtener las propiedades: " + ex.toString());
                }
            }
        }
    }
    
    public String ModificarPassword(String Usuario, String Password, String PasswordAnterior)
    {
        CallableStatement c = null;
        try
        {
            if (conn.isClosed())
            {
                AbreConexion();
            }
            String execRPC = "{call TERCEROS.PA_MODIFICAR_CLAVE(?,?,?,?)}";
            c = conn.prepareCall(execRPC);
            c.registerOutParameter(1, OracleTypes.NUMBER);
            c.setString(2, Usuario);
            c.setString(3, Password);
            c.setString(4, PasswordAnterior);
            c.execute();
            BigDecimal rs;
            rs = (BigDecimal) c.getObject(1);
            int Existe = rs.intValue();
            c.close();
            conn.close();
            String token = "";
            if (Existe > 0)
            {
            	token = UUID.randomUUID().toString().toUpperCase() + "|userid|" + Usuario;
            }
            return token;
        }
        catch (SQLException ex)
        {
            log.error("Error al leer el procedimiento almacenado de modificar contraseña: " + ex.toString());
            return null;
        }
        catch (Exception ex)
        {
            log.error("Error al leer el procedimiento almacenado de modificar contraseña: " + ex.toString());
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
                    log.error("Error al leer el procedimiento almacenado de modificar contraseña: " + ex.toString());
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
                    log.error("Error al leer el procedimiento almacenado de modificar contraseña: " + ex.toString());
                }
            }
        }
    }
 
    public void RegistrarDatosAdicionales(List<DatosAdicionalesDTO> datos) {
    	CallableStatement c = null;
		for (DatosAdicionalesDTO datoIterado : datos) {
	        try
	        {

	            if (conn.isClosed())
	            {
	                AbreConexion();
	            }
	            
	            String execRPC = "{call TERCEROS.PA_REGISTRAR_DATOS_ADICIONALES(?,?,?,?)}";
	            c = conn.prepareCall(execRPC);
	            
	            int i = 1;
	            
				c.setString(i++, datoIterado.getIdentificacionTercero() != null ? datoIterado.getIdentificacionTercero() : "");
				c.setObject(i++, datoIterado.getTipoDatoAdicional().getIdentificador() != null ? datoIterado.getTipoDatoAdicional().getIdentificador() :  null);
				c.setString(i++, datoIterado.getValor() != null ? datoIterado.getValor() : "");
				c.setString(i++, datoIterado.getEstado() != null ? datoIterado.getEstado() : "");

	            c.execute();
	        }
	        catch (SQLException ex)
	        {
	            log.error("Error al leer el procedimiento almacenado de PA_REGISTRAR_DATOS_TERCERO: " + ex.toString());
	        }
	        catch (Exception ex)
	        {
	            log.error("Error al leer el procedimiento almacenado de PA_REGISTRAR_DATOS_TERCERO: " + ex.toString());
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
	                    log.error("Error al leer el procedimiento almacenado de PA_REGISTRAR_DATOS_TERCERO: " + ex.toString());
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
	                    log.error("Error al leer el procedimiento almacenado de PA_REGISTRAR_DATOS_TERCERO: " + ex.toString());
	                }
	            }
	        }
			
		}
    }
    
    public List<TerceroDTO> ConsultarTercero(String identificadorUsuario) //"PA_CONSULTAR_TERCERO"
    {
        CallableStatement c = null;
        try
        {
            List<TerceroDTO> listTerceroDtos = new ArrayList();
            if (conn.isClosed())
            {
                AbreConexion();
            }
            String execRPC = "{call TERCEROS.PA_CONSULTAR_TERCERO(?,?)}";
            c = conn.prepareCall(execRPC);
            c.registerOutParameter(1, OracleTypes.CURSOR);
            c.setString(2, identificadorUsuario);
            c.execute();
            ResultSet rs;
            rs = (ResultSet) c.getObject(1);
            while (rs.next())
            {
            	TerceroDTO objTerceroDTO = new TerceroDTO();
            	CiudadDTO objCiudadDto = new CiudadDTO();
            	

            	
            	DatosBasicosDTO objDatosBasicoDTO = new DatosBasicosDTO();
            	UbicacionDTO objUbicacionDTO = new UbicacionDTO();
            	objTerceroDTO.setFechaActualizacion(rs.getDate("FECHA_ULT_ACTUALIZACION"));
            	objTerceroDTO.setFechaCreacion(rs.getDate("FECHA_CREACION"));
            	objTerceroDTO.setIdentificacionTercero(rs.getString("NUM_ID"));
            	objTerceroDTO.setIdentificador(rs.getInt("SK_TERCERO"));
            	objDatosBasicoDTO.setEstado(rs.getString("ESTADO"));
            	objDatosBasicoDTO.setEtnia(rs.getInt("ETNIA"));
            	objDatosBasicoDTO.setFechaCreacion(rs.getDate("FECHA_CREACION"));
            	objDatosBasicoDTO.setFechaFinVigencia(rs.getDate("FECHA_FIN_VIGENCIA"));
            	objDatosBasicoDTO.setFechaNacimiento(rs.getDate("FEC_NAC"));
            	objDatosBasicoDTO.setGenero(rs.getString("GENERO"));
            	objDatosBasicoDTO.setIdentificador(rs.getInt("SK_DATOS"));
            	objDatosBasicoDTO.setNumeroIdentificacion(rs.getString("NUM_ID"));
            	objDatosBasicoDTO.setPrimerApellido(rs.getString("APELLIDO_1"));
            	objDatosBasicoDTO.setSegundoApellido(rs.getString("APELLIDO_2"));
            	objDatosBasicoDTO.setPrimerNombre(rs.getString("NOMBRE_1"));
            	objDatosBasicoDTO.setSegundoNombre(rs.getString("NOMBRE_2"));
            	objDatosBasicoDTO.setRazonSocial(rs.getString("RAZON_SOCIAL"));
            	objDatosBasicoDTO.setSexo(rs.getString("SEXO"));
            	objDatosBasicoDTO.setTipoIdentificacion(rs.getString("TIP_ID"));
            	objDatosBasicoDTO.setTipoTercero(rs.getString("TIPO_TERCERO"));
            	objDatosBasicoDTO.setUsuario(rs.getString("USUARIO"));
            	objTerceroDTO.setDatosBasicos(objDatosBasicoDTO);
            	objUbicacionDTO.setBarrio(rs.getString("BARRIO"));
            	for(CiudadDTO item :  ConsultarCiudad("170"))
            	{
            		if (item.getIdentificador() == rs.getInt("SK_CIUDAD")) 
            		{
            			objCiudadDto.setIdentificador(rs.getInt("SK_CIUDAD")); 
            			objCiudadDto.setCodigoCiudad(item.getCodigoCiudad());
            			objCiudadDto.setNombreCiudad(item.getNombreCiudad());
            		}
            	}
            	for(CiudadDTO item :  ConsultarCiudad("1"))
            	{
            		if (item.getIdentificador() == rs.getInt("SK_CIUDAD")) 
            		{
            			objCiudadDto.setIdentificador(rs.getInt("SK_CIUDAD")); 
            			objCiudadDto.setCodigoCiudad(item.getCodigoCiudad());
            			objCiudadDto.setNombreCiudad(item.getNombreCiudad());
            		}
            	}

            	objUbicacionDTO.setCiudad(objCiudadDto);
            	objUbicacionDTO.setComplemento(rs.getString("COMPLEMENTO"));
            	objUbicacionDTO.setCorreoElectronico(rs.getString("CORREO_ELECTRONICO"));
            	objUbicacionDTO.setDireccion(rs.getString("DIRECCION"));
            	objUbicacionDTO.setDireccionCompleta(rs.getString("DIRECCION_COMPLETA"));
            	objUbicacionDTO.setDireccionCompleta(rs.getString("DIRECCION_COMPLETA"));
            	objUbicacionDTO.setEstado(rs.getString("ESTADO"));
            	objUbicacionDTO.setFechaCreacion(rs.getDate("FECHA_CREACION_UBICACION"));
            	objUbicacionDTO.setFechaFinVigencia(rs.getDate("FECHA_FIN_VIGENCIA_UBICACION"));
            	objUbicacionDTO.setIdentificador(rs.getInt("SK_UBICACION"));
            	objUbicacionDTO.setLocalidad(rs.getInt("LOCALIDAD"));
            	objUbicacionDTO.setTelefonoCelular(rs.getString("TELEFONO_CEL"));
            	objUbicacionDTO.setTelefonoFijo(rs.getString("TELEFONO_FIJO"));
            	objUbicacionDTO.setTipoZona(rs.getInt("TIPO_ZONA"));
            	objTerceroDTO.setUbicacion(objUbicacionDTO);
                listTerceroDtos.add(objTerceroDTO);
            }
            c.close();
            conn.close();
            return listTerceroDtos;
        }
        catch (SQLException ex)
        {
            log.error("Error al leer el procedimiento almacenado de obtener de PA_CONSULTAR_TERCERO_USR: " + ex.toString());
            return null;
        }
        catch (Exception ex)
        {
            log.error("Error al leer el procedimiento almacenado de obtener de PA_CONSULTAR_TERCERO_USR: " + ex.toString());
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
                    log.error("Error al leer el procedimiento almacenado de PA_CONSULTAR_TERCERO_USR: " + ex.toString());
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
                    log.error("Error al leer el procedimiento almacenado de PA_CONSULTAR_TERCERO_USR: " + ex.toString());
                }
            }
        }
    }
    
    public List<CiudadDTO>  ConsultarCiudad(String CodigoPais) //"PA_CONSULTAR_CIUDAD"
    {
        CallableStatement c = null;
        try
        {
            List<CiudadDTO> listCiudadDtos = new ArrayList();
            if (conn.isClosed())
            {
                AbreConexion();
            }
            String execRPC = "{call TERCEROS.PA_CONSULTAR_CIUDAD(?,?)}";
            c = conn.prepareCall(execRPC);
            c.registerOutParameter(1, OracleTypes.CURSOR);
            c.setString(2, CodigoPais);
            c.execute();
            ResultSet rs;
            rs = (ResultSet) c.getObject(1);
            while (rs.next())
            {
            	CiudadDTO objCiudadDTO = new CiudadDTO(rs.getInt(1),rs.getString(2), rs.getString(3));
                listCiudadDtos.add(objCiudadDTO);
            }
            //c.close();
            //conn.close();
            return listCiudadDtos;
        }
        catch (SQLException ex)
        {
            log.error("Error al leer el procedimiento almacenado de obtener las ciudades: " + ex.toString());
            return null;
        }
        catch (Exception ex)
        {
            log.error("Error al leer el procedimiento almacenado de obtener las ciudades: " + ex.toString());
            return null;
        }
        finally
        {
            /*if (c != null)
            {
                try
                {
                    c.close();
                }
                catch (SQLException ex)
                {
                    log.error("Error al leer el procedimiento almacenado de obtener las ciudades: " + ex.toString());
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
                    log.error("Error al leer el procedimiento almacenado de obtener las ciudades: " + ex.toString());
                }
            }*/
        }
    }
    
    public List<PropertieDTO> ConsultarPais() //"PA_CONSULTAR_PAIS"
    {
        CallableStatement c = null;
        try
        {
            List<PropertieDTO> listPropertieDtos = new ArrayList();
            if (conn.isClosed())
            {
                AbreConexion();
            }
            String execRPC = "{call TERCEROS.PA_CONSULTAR_PAIS(?)}";
            c = conn.prepareCall(execRPC);
            c.registerOutParameter(1, OracleTypes.CURSOR);
            c.execute();
            ResultSet rs;
            rs = (ResultSet) c.getObject(1);
            while (rs.next())
            {
                PropertieDTO objPropertieDTO = new PropertieDTO(rs.getString(1),rs.getString(2), rs.getString(3), rs.getString(2));
                listPropertieDtos.add(objPropertieDTO);
            }
            c.close();
            conn.close();
            return listPropertieDtos;
        }
        catch (SQLException ex)
        {
            log.error("Error al leer el procedimiento almacenado de obtener los paises: " + ex.toString());
            return null;
        }
        catch (Exception ex)
        {
            log.error("Error al leer el procedimiento almacenado de obtener los paises: " + ex.toString());
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
                    log.error("Error al leer el procedimiento almacenado de obtener los paises: " + ex.toString());
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
                    log.error("Error al leer el procedimiento almacenado de obtener los paises: " + ex.toString());
                }
            }
        }
    }
    
    public List<TerceroDTO> ConsultarTerceroUsuario(String nombreUsuario) //"PA_CONSULTAR_TERCERO_USR"
    {
        CallableStatement c = null;
        try
        {
            List<TerceroDTO> listTerceroDtos = new ArrayList();
            if (conn.isClosed())
            {
                AbreConexion();
            }
            String execRPC = "{call TERCEROS.PA_CONSULTAR_TERCERO_USR(?,?)}";
            c = conn.prepareCall(execRPC);
            c.registerOutParameter(1, OracleTypes.CURSOR);
            c.setString(2, nombreUsuario);
            c.execute();
            ResultSet rs;
            rs = (ResultSet) c.getObject(1);
            while (rs.next())
            {
            	TerceroDTO objTerceroDTO = new TerceroDTO();
            	CiudadDTO objCiudadDto = new CiudadDTO();
            	

            	
            	DatosBasicosDTO objDatosBasicoDTO = new DatosBasicosDTO();
            	UbicacionDTO objUbicacionDTO = new UbicacionDTO();
            	objTerceroDTO.setFechaActualizacion(rs.getDate("FECHA_ULT_ACTUALIZACION"));
            	objTerceroDTO.setFechaCreacion(rs.getDate("FECHA_CREACION"));
            	objTerceroDTO.setIdentificacionTercero(rs.getString("NUM_ID"));
            	objTerceroDTO.setIdentificador(rs.getInt("SK_TERCERO"));
            	objDatosBasicoDTO.setEstado(rs.getString("ESTADO"));
            	objDatosBasicoDTO.setEtnia(rs.getInt("ETNIA"));
            	objDatosBasicoDTO.setFechaCreacion(rs.getDate("FECHA_CREACION"));
            	objDatosBasicoDTO.setFechaFinVigencia(rs.getDate("FECHA_FIN_VIGENCIA"));
            	objDatosBasicoDTO.setFechaNacimiento(rs.getDate("FEC_NAC"));
            	objDatosBasicoDTO.setGenero(rs.getString("GENERO"));
            	objDatosBasicoDTO.setIdentificador(rs.getInt("SK_DATOS"));
            	objDatosBasicoDTO.setNumeroIdentificacion(rs.getString("NUM_ID"));
            	objDatosBasicoDTO.setPrimerApellido(rs.getString("APELLIDO_1"));
            	objDatosBasicoDTO.setSegundoApellido(rs.getString("APELLIDO_2"));
            	objDatosBasicoDTO.setPrimerNombre(rs.getString("NOMBRE_1"));
            	objDatosBasicoDTO.setSegundoNombre(rs.getString("NOMBRE_2"));
            	objDatosBasicoDTO.setSexo(rs.getString("SEXO"));
            	objDatosBasicoDTO.setTipoIdentificacion(rs.getString("TIP_ID"));
            	objDatosBasicoDTO.setTipoTercero(rs.getString("TIPO_TERCERO"));
            	objDatosBasicoDTO.setUsuario(rs.getString("USUARIO"));
            	objTerceroDTO.setDatosBasicos(objDatosBasicoDTO);
            	objUbicacionDTO.setBarrio(rs.getString("BARRIO"));
            	for(CiudadDTO item :  ConsultarCiudad("170"))
            	{
            		if (item.getIdentificador() == rs.getInt("SK_CIUDAD")) 
            		{
            			objCiudadDto.setIdentificador(rs.getInt("SK_CIUDAD")); 
            			objCiudadDto.setCodigoCiudad(item.getCodigoCiudad());
            			objCiudadDto.setNombreCiudad(item.getNombreCiudad());
            		}
            	}
            	for(CiudadDTO item :  ConsultarCiudad("1"))
            	{
            		if (item.getIdentificador() == rs.getInt("SK_CIUDAD")) 
            		{
            			objCiudadDto.setIdentificador(rs.getInt("SK_CIUDAD")); 
            			objCiudadDto.setCodigoCiudad(item.getCodigoCiudad());
            			objCiudadDto.setNombreCiudad(item.getNombreCiudad());
            		}
            	}

            	objUbicacionDTO.setCiudad(objCiudadDto);
            	objUbicacionDTO.setComplemento(rs.getString("COMPLEMENTO"));
            	objUbicacionDTO.setCorreoElectronico(rs.getString("CORREO_ELECTRONICO"));
            	objUbicacionDTO.setDireccion(rs.getString("DIRECCION_UBICACION"));
            	objUbicacionDTO.setDireccionCompleta(rs.getString("DIRECCION_COMPLETA"));
            	objUbicacionDTO.setDireccionRural(rs.getString("DIRECCION_RURAL"));
            	objUbicacionDTO.setEstado(rs.getString("ESTADO"));
            	objUbicacionDTO.setFechaCreacion(rs.getDate("FECHA_CREACION_UBICACION"));
            	objUbicacionDTO.setFechaFinVigencia(rs.getDate("FECHA_FIN_VIGENCIA_UBICACION"));
            	objUbicacionDTO.setIdentificador(rs.getInt("SK_UBICACION"));
            	objUbicacionDTO.setLocalidad(rs.getInt("LOCALIDAD"));
            	objUbicacionDTO.setTelefonoCelular(rs.getString("TELEFONO_CEL"));
            	objUbicacionDTO.setTelefonoFijo(rs.getString("TELEFONO_FIJO"));
            	objUbicacionDTO.setTipoZona(rs.getInt("TIPO_ZONA"));
            	objTerceroDTO.setUbicacion(objUbicacionDTO);
                listTerceroDtos.add(objTerceroDTO);
            }
            c.close();
            conn.close();
            return listTerceroDtos;
        }
        catch (SQLException ex)
        {
            log.error("Error al leer el procedimiento almacenado de obtener de PA_CONSULTAR_TERCERO_USR: " + ex.toString());
            return null;
        }
        catch (Exception ex)
        {
            log.error("Error al leer el procedimiento almacenado de obtener de PA_CONSULTAR_TERCERO_USR: " + ex.toString());
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
                    log.error("Error al leer el procedimiento almacenado de PA_CONSULTAR_TERCERO_USR: " + ex.toString());
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
                    log.error("Error al leer el procedimiento almacenado de PA_CONSULTAR_TERCERO_USR: " + ex.toString());
                }
            }
        }
    }
    
    public List<DatosAdicionalesDTO> BuscarDatosAdicionales(String identifcadorUsuario, String origen) //"PA_CONSULTAR_DATOS_ADICIONALES"
    {
        CallableStatement c = null;
        try
        {
            List<DatosAdicionalesDTO> listDatosAdicionalesDtos = new ArrayList();
            if (conn.isClosed())
            {
                AbreConexion();
            }
            String execRPC = "{call TERCEROS.PA_CONSULTAR_DATOS_ADICIONALES(?,?,?)}";
            c = conn.prepareCall(execRPC);
            c.registerOutParameter(1, OracleTypes.CURSOR);
            c.setString(2, identifcadorUsuario);
            c.setString(3, origen);
            c.execute();
            ResultSet rs;
            rs = (ResultSet) c.getObject(1);
            while (rs.next())
            {
            	//rs.getString(1),rs.getString(2), rs.getString(3), rs.getString(2)
            	DatosAdicionalesDTO objDatosAdicionalesDTO = new DatosAdicionalesDTO();
            	objDatosAdicionalesDTO.setEstado(rs.getString("ESTADO"));
            	objDatosAdicionalesDTO.setIdentificacionTercero(rs.getString("ID_TERCERO"));
            	objDatosAdicionalesDTO.setValor(rs.getString("VALOR"));
            	objDatosAdicionalesDTO.setIdentificador(rs.getInt("SK_DATOS_AD"));
            	TipoDatoAdicionalDTO objTipoDatoAdicionalDTO = new TipoDatoAdicionalDTO(); 
            	objTipoDatoAdicionalDTO.setAtributo(rs.getString("ATRIBUTO"));
            	objTipoDatoAdicionalDTO.setIdentificador(rs.getInt("SK_DATO_ADICIONAL"));
            	objTipoDatoAdicionalDTO.setSistemaOrigen(rs.getString("SISTEMA_ORIGEN"));
            	objTipoDatoAdicionalDTO.setTipoDatos(rs.getString("TIPO_DATOS"));
            	objDatosAdicionalesDTO.setTipoDatoAdicional(objTipoDatoAdicionalDTO);
            	listDatosAdicionalesDtos.add(objDatosAdicionalesDTO);
            }
            c.close();
            conn.close();
            return listDatosAdicionalesDtos;
        }
        catch (SQLException ex)
        {
            log.error("Error al leer el procedimiento almacenado de obtener los datos adicionales: " + ex.toString());
            return null;
        }
        catch (Exception ex)
        {
            log.error("Error al leer el procedimiento almacenado de obtener los datos adicionales: " + ex.toString());
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
                    log.error("Error al leer el procedimiento almacenado de obtener los datos adicionales: " + ex.toString());
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
                    log.error("Error al leer el procedimiento almacenado de obtener los datos adicionales: " + ex.toString());
                }
            }
        }
    }
    
    public List<PropertieDTO> ConsultarParametro(String tipoParametro, int skParent) //"PA_CONSULTAR_PARAMETRO"
    {
        CallableStatement c = null;
        try
        {
            List<PropertieDTO> listPropertieDtos = new ArrayList();
            if (conn.isClosed())
            {
                AbreConexion();
            }
            String execRPC = "{call TERCEROS.PA_CONSULTAR_PARAMETRO(?,?,?)}";
            c = conn.prepareCall(execRPC);
            c.registerOutParameter(1, OracleTypes.CURSOR);
            c.setString(2, tipoParametro);
            c.setInt(3, skParent);
            c.execute();
            ResultSet rs;
            rs = (ResultSet) c.getObject(1);
            if (tipoParametro.equals("TIPO_TRAMITE") || tipoParametro.equals("REGIMEN") || tipoParametro.equals("ESTADO_CIVIL") || tipoParametro.equals("TIPO_REP_LEGAL")) {
                while (rs.next())
                {
                    PropertieDTO objPropertieDTO = new PropertieDTO(rs.getString(1),rs.getString(2), rs.getString(3), rs.getString(1) + ":" + rs.getString(5));
                    listPropertieDtos.add(objPropertieDTO);
                }
            }
            if(tipoParametro.equals("CUAD_VIA_GEN") ) {
            	while (rs.next())
            	{
            		PropertieDTO objPropertieDTO = new PropertieDTO(rs.getString(1),rs.getString(3), rs.getString(3), rs.getString(4) + ":" + rs.getString(5));
            		listPropertieDtos.add(objPropertieDTO);
            	}
            }
            else {
            	
            	while (rs.next())
            	{
            		PropertieDTO objPropertieDTO = new PropertieDTO(rs.getString(1),rs.getString(2), rs.getString(3), rs.getString(4) + ":" + rs.getString(5));
            		listPropertieDtos.add(objPropertieDTO);
            	}
            }
            c.close();
            conn.close();
            return listPropertieDtos;
        }
        catch (SQLException ex)
        {
            log.error("Error al leer el procedimiento almacenado de obtener las propiedades: " + ex.toString());
            return null;
        }
        catch (Exception ex)
        {
            log.error("Error al leer el procedimiento almacenado de obtener las propiedades: " + ex.toString());
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
                    log.error("Error al leer el procedimiento almacenado de obtener las propiedades: " + ex.toString());
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
                    log.error("Error al leer el procedimiento almacenado de obtener las propiedades: " + ex.toString());
                }
            }
        }
    }
   
    public String RegistrarDatosTercero(TerceroDTO terceroDTO) //"PA_REGISTRAR_DATOS_TERCERO"
    {
        CallableStatement c = null;
        try
        {
            if (conn.isClosed())
            {
                AbreConexion();
            }
            
            String execRPC = "{call TERCEROS.PA_REGISTRAR_DATOS_TERCERO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            c = conn.prepareCall(execRPC);
            int i = 1;
            
			c.setString(i++, terceroDTO.getIdentificacionTercero() != null ? terceroDTO.getIdentificacionTercero() : "");
			c.setString(i++, terceroDTO.getDatosBasicos().getTipoTercero() != null ? terceroDTO.getDatosBasicos().getTipoTercero() : "");
			c.setString(i++, terceroDTO.getDatosBasicos().getPrimerNombre() != null ? terceroDTO.getDatosBasicos().getPrimerNombre() : "");
			c.setString(i++, terceroDTO.getDatosBasicos().getSegundoNombre() != null ? terceroDTO.getDatosBasicos().getSegundoNombre() : "");
			c.setString(i++, terceroDTO.getDatosBasicos().getPrimerApellido() != null ? terceroDTO.getDatosBasicos().getPrimerApellido() : "");
			c.setString(i++, terceroDTO.getDatosBasicos().getSegundoApellido() != null ? terceroDTO.getDatosBasicos().getSegundoApellido() : "");
			c.setString(i++, terceroDTO.getDatosBasicos().getTipoIdentificacion() != null ? terceroDTO.getDatosBasicos().getTipoIdentificacion() : "");
			c.setString(i++, terceroDTO.getDatosBasicos().getNumeroIdentificacion() != null ? terceroDTO.getDatosBasicos().getNumeroIdentificacion() : "");
			c.setString(i++, terceroDTO.getDatosBasicos().getSexo() != null ? terceroDTO.getDatosBasicos().getSexo() : "");
			c.setDate(i++, terceroDTO.getDatosBasicos().getFechaNacimiento() != null
					? new java.sql.Date(terceroDTO.getDatosBasicos().getFechaNacimiento().getTime()) : null);
			c.setObject(i++, terceroDTO.getDatosBasicos().getEtnia() != null ? terceroDTO.getDatosBasicos().getEtnia() : null);
			c.setString(i++, terceroDTO.getDatosBasicos().getCualEtnia() != null ? terceroDTO.getDatosBasicos().getCualEtnia() : "");
			c.setString(i++, terceroDTO.getDatosBasicos().getGenero() != null ? terceroDTO.getDatosBasicos().getGenero() : "");
			c.setString(i++, terceroDTO.getDatosBasicos().getCualGenero() != null ? terceroDTO.getDatosBasicos().getCualGenero() : "");
			c.setObject(i++, terceroDTO.getDatosBasicos().getOrientacionSexual() != null ? terceroDTO.getDatosBasicos().getOrientacionSexual() : null);
			c.setString(i++, terceroDTO.getDatosBasicos().getCualOrientacionSexual() != null ? terceroDTO.getDatosBasicos().getCualOrientacionSexual() : "");
			c.setString(i++, terceroDTO.getDatosBasicos().getRazonSocial() != null ? terceroDTO.getDatosBasicos().getRazonSocial() : "");
			c.setObject(i++, terceroDTO.getDatosBasicos().getNit() != null ? terceroDTO.getDatosBasicos().getNit() : null);
			c.setString(i++, terceroDTO.getDatosBasicos().getDireccion() != null ? terceroDTO.getDatosBasicos().getDireccion() : "");
			c.setString(i++, terceroDTO.getDatosBasicos().getRepresentanteLegal() != null ? terceroDTO.getDatosBasicos().getRepresentanteLegal() : "");
			c.setDate(i++, terceroDTO.getDatosBasicos().getFechaFinVigencia() != null
					? new java.sql.Date(terceroDTO.getDatosBasicos().getFechaFinVigencia().getTime()) : null);
			c.setString(i++, terceroDTO.getDatosBasicos().getEstado() != null ? terceroDTO.getDatosBasicos().getEstado() : "");
			c.setString(i++, terceroDTO.getDatosBasicos().getUsuario() != null ? terceroDTO.getDatosBasicos().getUsuario() : "");
			c.setString(i++, terceroDTO.getDireccionIP() != null ? terceroDTO.getDireccionIP() : "");
			c.setString(i++, terceroDTO.getUsuarioModificacion() != null ? terceroDTO.getUsuarioModificacion() : "");
			c.setString(i++, terceroDTO.getPassword() != null ? terceroDTO.getPassword() : "");

            c.execute();
            /*ResultSet rs;
            rs = (ResultSet) c.getObject(1);
            while (rs.next())
            {
                PropertieDTO objPropertieDTO = new PropertieDTO(rs.getString(1),rs.getString(2), rs.getString(3), rs.getString(4) + ":" + rs.getString(5));
                listPropertieDtos.add(objPropertieDTO);
            }*/
            c.close();
            conn.close();
            return "0";
        }
        catch (SQLException ex)
        {
            log.error("Error al leer el procedimiento almacenado de PA_REGISTRAR_DATOS_TERCERO: " + ex.toString());
            return null;
        }
        catch (Exception ex)
        {
            log.error("Error al leer el procedimiento almacenado de PA_REGISTRAR_DATOS_TERCERO: " + ex.toString());
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
                    log.error("Error al leer el procedimiento almacenado de PA_REGISTRAR_DATOS_TERCERO: " + ex.toString());
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
                    log.error("Error al leer el procedimiento almacenado de PA_REGISTRAR_DATOS_TERCERO: " + ex.toString());
                }
            }
        }
    }
    
    public String RegistrarDatosUbicacionTercero(TerceroDTO terceroDTO) //"PA_REGISTRAR_UBICACION_TERCERO"
    {
    	
        CallableStatement c = null;
        try
        {
            if (conn.isClosed())
            {
                AbreConexion();
            }
            
            String execRPC = "{call TERCEROS.PA_REGISTRAR_UBICACION_TERCERO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            c = conn.prepareCall(execRPC);
            
            int i = 1;
            
            c.setString(i++, terceroDTO.getIdentificacionTercero() != null ? terceroDTO.getIdentificacionTercero() : "" );
            c.setObject(i++, terceroDTO.getUbicacion().getTipoZona() != null ? terceroDTO.getUbicacion().getTipoZona() : null);
            c.setString(i++, terceroDTO.getUbicacion().getDireccionRural() != null ? terceroDTO.getUbicacion().getDireccionRural() : "");
            c.setString(i++, terceroDTO.getUbicacion().getTipoViaPrincipal() != null ? terceroDTO.getUbicacion().getTipoViaPrincipal() : "");
            c.setObject(i++, terceroDTO.getUbicacion().getNumeroViaPrincipal() != null ? terceroDTO.getUbicacion().getNumeroViaPrincipal() : null);
            c.setString(i++, terceroDTO.getUbicacion().getLetraViaPrincipal() != null ? terceroDTO.getUbicacion().getLetraViaPrincipal() : "");
            c.setString(i++, terceroDTO.getUbicacion().getBis() != null ? terceroDTO.getUbicacion().getBis() : "");
            c.setString(i++, terceroDTO.getUbicacion().getLetraBis() != null ? terceroDTO.getUbicacion().getLetraBis() : "");
            c.setString(i++, terceroDTO.getUbicacion().getCuadViaPrincipal() != null ? terceroDTO.getUbicacion().getCuadViaPrincipal() : "");
            c.setObject(i++, terceroDTO.getUbicacion().getNumeroViaGeneral() != null ? terceroDTO.getUbicacion().getNumeroViaGeneral() : null);
            c.setString(i++, terceroDTO.getUbicacion().getLetraViaGeneral() != null ? terceroDTO.getUbicacion().getLetraViaGeneral() : "");
            c.setString(i++, terceroDTO.getUbicacion().getNumeroPlaca() != null ? terceroDTO.getUbicacion().getNumeroPlaca() : "");
            c.setString(i++, terceroDTO.getUbicacion().getCuadViaGen() != null ? terceroDTO.getUbicacion().getCuadViaGen() : "");
            c.setString(i++, terceroDTO.getUbicacion().getComplemento() != null ? terceroDTO.getUbicacion().getComplemento() : "");
            c.setString(i++, terceroDTO.getUbicacion().getBarrio() != null ? terceroDTO.getUbicacion().getBarrio() : "");
            c.setObject(i++, terceroDTO.getUbicacion().getLocalidad() != null ? terceroDTO.getUbicacion().getLocalidad() : null);
            c.setObject(i++, terceroDTO.getUbicacion().getCiudad() != null
    				? terceroDTO.getUbicacion().getCiudad().getIdentificador() : null);
            c.setString(i++, terceroDTO.getUbicacion().getTelefonoFijo()!= null ? terceroDTO.getUbicacion().getTelefonoFijo() : "");
            c.setString(i++, terceroDTO.getUbicacion().getTelefonoCelular() != null ? terceroDTO.getUbicacion().getTelefonoCelular() : "");
            c.setString(i++, terceroDTO.getUbicacion().getCorreoElectronico() != null ? terceroDTO.getUbicacion().getCorreoElectronico()  : "");
            c.setString(i++, terceroDTO.getUbicacion().getDireccion() != null ? terceroDTO.getUbicacion().getDireccion() : "");
            c.setDate(i++, terceroDTO.getUbicacion().getFechaFinVigencia() != null
    				? new java.sql.Date(terceroDTO.getUbicacion().getFechaFinVigencia().getTime()) : null);
            c.setString(i++, terceroDTO.getUbicacion().getEstado() != null ? terceroDTO.getUbicacion().getEstado() : "");
            c.setString(i++, terceroDTO.getUbicacion().getDireccionCompleta() != null ? terceroDTO.getUbicacion().getDireccionCompleta() : "");
            c.setString(i++, terceroDTO.getUsuarioModificacion() != null ? terceroDTO.getUsuarioModificacion() : "");
            c.setString(i++, terceroDTO.getDireccionIP() != null ?  terceroDTO.getDireccionIP() : "");

            c.execute();
            /*ResultSet rs;
            rs = (ResultSet) c.getObject(1);
            while (rs.next())
            {
                PropertieDTO objPropertieDTO = new PropertieDTO(rs.getString(1),rs.getString(2), rs.getString(3), rs.getString(4) + ":" + rs.getString(5));
                listPropertieDtos.add(objPropertieDTO);
            }*/
            c.close();
            conn.close();
            return "0";
        }
        catch (SQLException ex)
        {
            log.error("Error al leer el procedimiento almacenado de PA_REGISTRAR_UBICACION_TERCERO: " + ex.toString());
            return null;
        }
        catch (Exception ex)
        {
            log.error("Error al leer el procedimiento almacenado de PA_REGISTRAR_UBICACION_TERCERO: " + ex.toString());
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
                    log.error("Error al leer el procedimiento almacenado de PA_REGISTRAR_UBICACION_TERCERO: " + ex.toString());
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
                    log.error("Error al leer el procedimiento almacenado de PA_REGISTRAR_UBICACION_TERCERO: " + ex.toString());
                }
            }
        }
    }
    
}
