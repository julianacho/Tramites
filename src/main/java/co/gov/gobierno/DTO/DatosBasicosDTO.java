package co.gov.gobierno.DTO;

import java.io.Serializable;
import java.util.Date;

public class DatosBasicosDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer identificador;
	private String tipoTercero;
	private String tipoPersona;
	private String primerNombre;
	private String segundoNombre;
	private String primerApellido;
	private String segundoApellido;
	private String tipoIdentificacion;
	private String numeroIdentificacion;
	private String sexo;
	private Date fechaNacimiento;
	private Integer etnia;
	private String cualEtnia;
	private String genero;
	private String cualGenero;
	private Integer orientacionSexual;
	private String cualOrientacionSexual;
	private String razonSocial;
	private Integer nit;
	private String direccion;
	private String representanteLegal;
	private Date fechaCreacion;
	private Date fechaFinVigencia;
	private String estado;
	private String usuario;
	private String direccion_dos;
	
	public DatosBasicosDTO() {
	}

	

	



	public DatosBasicosDTO(Integer identificador, String tipoTercero, String primerNombre, String segundoNombre,
			String primerApellido, String segundoApellido, String tipoIdentificacion, String numeroIdentificacion,
			String sexo, Date fechaNacimiento, Integer etnia, String cualEtnia, String genero, String cualGenero,
			Integer orientacionSexual, String cualOrientacionSexual, String razonSocial, Integer nit, String direccion,
			String representanteLegal, Date fechaCreacion, Date fechaFinVigencia, String estado, String usuario ) {
		super();
		this.identificador = identificador;
		this.tipoTercero = tipoTercero;
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.tipoIdentificacion = tipoIdentificacion;
		this.numeroIdentificacion = numeroIdentificacion;
		this.sexo = sexo;
		this.fechaNacimiento = fechaNacimiento;
		this.etnia = etnia;
		this.cualEtnia = cualEtnia;
		this.genero = genero;
		this.cualGenero = cualGenero;
		this.orientacionSexual = orientacionSexual;
		this.cualOrientacionSexual = cualOrientacionSexual;
		this.razonSocial = razonSocial;
		this.nit = nit;
		this.direccion = direccion;
		this.representanteLegal = representanteLegal;
		this.fechaCreacion = fechaCreacion;
		this.fechaFinVigencia = fechaFinVigencia;
		this.estado = estado;
		this.usuario = usuario;
		//this.direccion_dos = direccion_dos;
	}







	public String getTipoPersona() {
		return tipoPersona;
	}







	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}







	public Integer getIdentificador() {
		return identificador;
	}

	public void setIdentificador(Integer identificador) {
		this.identificador = identificador;
	}

	public String getTipoTercero() {
		return tipoTercero;
	}

	public void setTipoTercero(String tipoTercero) {
		this.tipoTercero = tipoTercero;
	}

	public String getPrimerNombre() {
		return primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	public String getSegundoNombre() {
		return segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Integer getEtnia() {
		return etnia;
	}

	public void setEtnia(Integer etnia) {
		this.etnia = etnia;
	}

	public String getCualEtnia() {
		return cualEtnia;
	}

	public void setCualEtnia(String cualEtnia) {
		this.cualEtnia = cualEtnia;
	}

	public Integer getOrientacionSexual() {
		return orientacionSexual;
	}

	public void setOrientacionSexual(Integer orientacionSexual) {
		this.orientacionSexual = orientacionSexual;
	}

	public String getCualOrientacionSexual() {
		return cualOrientacionSexual;
	}

	public void setCualOrientacionSexual(String cualOrientacionSexual) {
		this.cualOrientacionSexual = cualOrientacionSexual;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public Integer getNit() {
		return nit;
	}

	public void setNit(Integer nit) {
		this.nit = nit;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getRepresentanteLegal() {
		return representanteLegal;
	}

	public void setRepresentanteLegal(String representanteLegal) {
		this.representanteLegal = representanteLegal;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaFinVigencia() {
		return fechaFinVigencia;
	}

	public void setFechaFinVigencia(Date fechaFinVigencia) {
		this.fechaFinVigencia = fechaFinVigencia;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getCualGenero() {
		return cualGenero;
	}

	public void setCualGenero(String cualGenero) {
		this.cualGenero = cualGenero;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}







/*	public String getDireccion_dos() {
		return direccion_dos;
	}







	public void setDireccion_dos(String direccion_dos) {
		this.direccion_dos = direccion_dos;
	}*/
}
