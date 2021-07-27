package co.gov.gobierno.ServiceImpl;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.NamingException;

import org.slf4j.LoggerFactory;

import co.gov.gobierno.DAO.LogAuditoriaDAO;
import co.gov.gobierno.DTO.LogAuditoriaDTO;
import co.gov.gobierno.Service.LogAuditoriaService;

@Stateless
public class LogAuditoriaServiceImpl implements LogAuditoriaService {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(LogAuditoriaServiceImpl.class);
	//@EJB LogAuditoriaDAO
	
	@Override
	public List<LogAuditoriaDTO> findAll(){
		LogAuditoriaDAO lDao = null;
		try {
			lDao = new LogAuditoriaDAO();
		} catch (NamingException e) {
			log.error("Error al Consultar de LogAuditoriaServiceImpl: " + e.toString());
		} catch (SQLException e) {
			log.error("Error al Consultar de LogAuditoriaServiceImpl: " + e.toString());
		}
		return lDao.findAll();
	}
	
	@Override
	public Boolean AddLogAuditoria(LogAuditoriaDTO logAuditoriaDTO){
		try {
			return new LogAuditoriaDAO().AgregarLogAuditoria(logAuditoriaDTO);
		} catch (NamingException e) {
			log.error("Error al Agregar de LogAuditoriaServiceImpl: " + e.toString());
		} catch (SQLException e) {
			log.error("Error al Agregar de LogAuditoriaServiceImpl: " + e.toString());
		}
		return false;
	}

}
