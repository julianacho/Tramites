package co.gov.gobierno.Service;

import java.util.List;

import co.gov.gobierno.DTO.LogAuditoriaDTO;


public interface LogAuditoriaService {
	public List<LogAuditoriaDTO> findAll();
	public Boolean AddLogAuditoria(LogAuditoriaDTO logAuditoriaDTO);
}
