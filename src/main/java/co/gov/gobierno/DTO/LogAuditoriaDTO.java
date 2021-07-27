package co.gov.gobierno.DTO;

import java.util.Date;

public class LogAuditoriaDTO {
	
	
	public LogAuditoriaDTO(long auLogId, String auLogUsuario, String auIpTerminal, String auUserSO, String auUserArchivo, String auAplicacion,
			String auRequest, String auResponse, String auMensajeError, Date auFecha) {
		super();
		this.auLogId = auLogId;
		this.auLogUsuario = auLogUsuario;
		this.auIpTerminal = auIpTerminal;
		this.auUserSO = auUserSO;
		this.auUserArchivo = auUserArchivo;
		this.auAplicacion = auAplicacion;
		this.auRequest = auRequest;
		this.auResponse = auResponse;
		this.auMensajeError = auMensajeError;
		this.auFecha = auFecha;
	}
	
	private long auLogId;
    private String auLogUsuario;
    private String auIpTerminal;
    private String auUserSO;
    private String auUserArchivo;
    private String auAplicacion;
    private String auRequest;
    private String auResponse;
    private String auMensajeError;
    private Date auFecha;
	public long getAuLogId() {
		return auLogId;
	}
	public void setAuLogId(long auLogId) {
		this.auLogId = auLogId;
	}
	public String getAuLogUsuario() {
		return auLogUsuario;
	}
	public void setAuLogUsuario(String auLogUsuario) {
		this.auLogUsuario = auLogUsuario;
	}
	public String getAuIpTerminal() {
		return auIpTerminal;
	}
	public void setAuIpTerminal(String auIpTerminal) {
		this.auIpTerminal = auIpTerminal;
	}
	public String getAuUserSO() {
		return auUserSO;
	}
	public void setAuUserSO(String auUserSO) {
		this.auUserSO = auUserSO;
	}
	public String getAuAplicacion() {
		return auAplicacion;
	}
	public void setAuAplicacion(String auAplicacion) {
		this.auAplicacion = auAplicacion;
	}
	public String getAuRequest() {
		return auRequest;
	}
	public void setAuRequest(String auRequest) {
		this.auRequest = auRequest;
	}
	public String getAuResponse() {
		return auResponse;
	}
	public void setAuResponse(String auResponse) {
		this.auResponse = auResponse;
	}
	public String getAuMensajeError() {
		return auMensajeError;
	}
	public void setAuMensajeError(String auMensajeError) {
		this.auMensajeError = auMensajeError;
	}
	public Date getAuFecha() {
		return auFecha;
	}
	public void setAuFecha(Date auFecha) {
		this.auFecha = auFecha;
	}
	public String getAuUserArchivo() {
		return auUserArchivo;
	}
	public void setAuUserArchivo(String auUserArchivo) {
		this.auUserArchivo = auUserArchivo;
	}
    
    

}
