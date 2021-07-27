package co.gov.gobierno.DTO;

import java.io.Serializable;
import java.util.Date;

public class TerceroDTO implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer identificador;
	private String identificacionTercero;
	private Date fechaCreacion;
	private Date fechaActualizacion;
	private UbicacionDTO ubicacion;
	private DatosBasicosDTO datosBasicos;
	private String usuarioModificacion;
	private String direccionIP;
	private String Password;
	
	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public TerceroDTO() {
	}

	public TerceroDTO(Integer identificador) {
		this.identificador = identificador;
	}

	public TerceroDTO(Integer identificador, String identificacionTercero, Date fechaCreacion, Date fechaActualizacion,
			UbicacionDTO ubicacion, DatosBasicosDTO datosBasicos) {
		super();
		this.identificador = identificador;
		this.identificacionTercero = identificacionTercero;
		this.fechaCreacion = fechaCreacion;
		this.fechaActualizacion = fechaActualizacion;
		this.ubicacion = ubicacion;
		this.datosBasicos = datosBasicos;
	}

	public TerceroDTO(Integer identificador, String identificacionTercero, Date fechaCreacion, Date fechaActualizacion,
			UbicacionDTO ubicacion, DatosBasicosDTO datosBasicos, String usuarioModificacion, String direccionIP) {
		super();
		this.identificador = identificador;
		this.identificacionTercero = identificacionTercero;
		this.fechaCreacion = fechaCreacion;
		this.fechaActualizacion = fechaActualizacion;
		this.ubicacion = ubicacion;
		this.datosBasicos = datosBasicos;
		this.usuarioModificacion = usuarioModificacion;
		this.direccionIP = direccionIP;
	}

	public Integer getIdentificador() {
		return identificador;
	}

	public void setIdentificador(Integer identificador) {
		this.identificador = identificador;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public String getIdentificacionTercero() {
		return identificacionTercero;
	}

	public void setIdentificacionTercero(String identificacionTercero) {
		this.identificacionTercero = identificacionTercero;
	}

	public UbicacionDTO getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(UbicacionDTO ubicacion) {
		this.ubicacion = ubicacion;
	}

	public DatosBasicosDTO getDatosBasicos() {
		return datosBasicos;
	}

	public void setDatosBasicos(DatosBasicosDTO datosBasicos) {
		this.datosBasicos = datosBasicos;
	}

	public String getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(String usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public String getDireccionIP() {
		return direccionIP;
	}

	public void setDireccionIP(String direccionIP) {
		this.direccionIP = direccionIP;
	}
}
