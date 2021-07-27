package co.gov.gobierno.ServiceImpl;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.NamingException;

import org.slf4j.LoggerFactory;

import co.gov.gobierno.DAO.TercerosDAO;
import co.gov.gobierno.DTO.DatosAdicionalesDTO;
import co.gov.gobierno.DTO.LocationDTO;
import co.gov.gobierno.DTO.LogAuditoriaDTO;
import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.DTO.TerceroDTO;
import co.gov.gobierno.DTO.ThirdDTO;
import co.gov.gobierno.Service.LogAuditoriaService;
import co.gov.gobierno.Service.TerceroService;

@Stateless
public class TerceroServiceImp implements TerceroService{
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(TerceroServiceImp.class);

	@EJB
    LogAuditoriaService logService;
    
    private LogAuditoriaDTO logAuditoriaDTO = new LogAuditoriaDTO(0, "NA", "IP", "SO", "NA", "TerceroServiceImp", "NA", "NA", "NA", null);
    
	@Override
	public String Login(String Usuario, String Password) {
		TercerosDAO terceroDao;
		String token = null;
		try {
			String pass = Password;

			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			byte hashBytes[] = messageDigest.digest(pass.getBytes(StandardCharsets.UTF_8));
			BigInteger noHash = new BigInteger(1, hashBytes);
			String hashStr = noHash.toString(16);
			terceroDao = new TercerosDAO();
			token = terceroDao.Login(Usuario, hashStr);
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return token;
	}

	@Override
	public String ModificarPassword(String Usuario, String Password, String PasswordAnterior) {
		TercerosDAO terceroDao;
		String token = "";
		try {
			String pass = Password;

			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			byte hashBytes[] = messageDigest.digest(pass.getBytes(StandardCharsets.UTF_8));
			byte hashBytesPa[] = messageDigest.digest(PasswordAnterior.getBytes(StandardCharsets.UTF_8));
			BigInteger noHash = new BigInteger(1, hashBytes);
			BigInteger noHashPa = new BigInteger(1, hashBytesPa);
			String hashStr = noHash.toString(16);
			String hashStrPa = noHashPa.toString(16);
			terceroDao = new TercerosDAO();
			token = terceroDao.ModificarPassword(Usuario, hashStr, hashStrPa);
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return token;
	}


	@Override
	public List<TerceroDTO> ConsultarTercero(String identificacionTercero) {
		TercerosDAO terceroDao;
		List<TerceroDTO> listTerceroDtos = new ArrayList();
		try {
			terceroDao = new TercerosDAO();
			listTerceroDtos = terceroDao.ConsultarTercero(identificacionTercero);
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listTerceroDtos;
	}

	@Override
	public String ConsultarDatosTercero(String identificacionTercero) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String ConsultarCiudad(String CodigoPais) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> ConsultarPais() {
		Map<String, String> map = new LinkedHashMap<>();
		try {
			List<PropertieDTO> listPropertieDtos = new ArrayList();
			TercerosDAO terceroDao = new TercerosDAO();
			listPropertieDtos = terceroDao.ConsultarPais();
			for(PropertieDTO item : listPropertieDtos) {
				map.put(item.getValue(), item.getIdService());
			}
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public List<TerceroDTO>  ConsultarTerceroUsuario(String nombreUsuario) {
		TercerosDAO terceroDao;
		List<TerceroDTO> listTerceroDtos = new ArrayList();
		try {
			terceroDao = new TercerosDAO();
			listTerceroDtos = terceroDao.ConsultarTerceroUsuario(nombreUsuario);
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listTerceroDtos;
	}

	@Override
	public List<DatosAdicionalesDTO> BuscarDatosAdicionales(String identificacionTercero, String sistema) {
		List<DatosAdicionalesDTO> listDatosAdicionalesDtos= new ArrayList();
		try {
			TercerosDAO terceroDao;			
			terceroDao = new TercerosDAO();
			listDatosAdicionalesDtos = terceroDao.BuscarDatosAdicionales(identificacionTercero, sistema);
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listDatosAdicionalesDtos;
	}

	@Override
	public ThirdDTO ConsultaUbicacionTercero(String identificacionTercero) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> ConsultarParametro(String tipoParametro, int skParent) {
		Map<String, String> map = new LinkedHashMap<>();
		try {
			List<PropertieDTO> listPropertieDtos = new ArrayList();
			TercerosDAO terceroDao = new TercerosDAO();
			listPropertieDtos = terceroDao.ConsultarParametro(tipoParametro, skParent);
			for(PropertieDTO item : listPropertieDtos) {
				map.put(item.getValue(), item.getIdService().split(":")[0]);
			}
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public String ConsultarTipoDatosAdicional(String Sistema) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void RegistrarDatosAdicionalTercero(List<DatosAdicionalesDTO> datos) {
		try {
			TercerosDAO terceroDao = new TercerosDAO();
			terceroDao.RegistrarDatosAdicionales(datos);
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String RegistrarDatosTercero(TerceroDTO terceroDTO) {
		try {
			TercerosDAO terceroDao = new TercerosDAO();
			
			String pass = terceroDTO.getPassword();

			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			byte hashBytes[] = messageDigest.digest(pass.getBytes(StandardCharsets.UTF_8));
			BigInteger noHash = new BigInteger(1, hashBytes);
			String hashStr = noHash.toString(16);
			
			terceroDTO.setPassword(hashStr);
			return terceroDao.RegistrarDatosTercero(terceroDTO);
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String RegistrarDatosUbicacionTercero(TerceroDTO terceroDTO) {
		try {
			TercerosDAO terceroDao = new TercerosDAO();
			return terceroDao.RegistrarDatosUbicacionTercero(terceroDTO);
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<LocationDTO> ConsultarParametroLocation(String tipoParametro, int skParent) {
		List<LocationDTO> lists = new ArrayList();
		try {
			List<PropertieDTO> listPropertieDtos = new ArrayList();
			TercerosDAO terceroDao = new TercerosDAO();
			listPropertieDtos = terceroDao.ConsultarParametro(tipoParametro, skParent);
			for(PropertieDTO item : listPropertieDtos) {
				LocationDTO l = new LocationDTO(item.getValue(), item.getIdkey(), item.getIdService().split(":")[0], item.getIdService().split(":")[1]);
				lists.add(l);
			}
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lists;
	}

}
