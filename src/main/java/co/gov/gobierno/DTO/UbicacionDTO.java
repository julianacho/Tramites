package co.gov.gobierno.DTO;

import java.io.Serializable;
import java.util.Date;

public class UbicacionDTO implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer identificador;
	private Integer tipoZona;
	private String direccionRural;
	private String tipoViaPrincipal;
	private Integer numeroViaPrincipal;
	private String letraViaPrincipal;
	private String bis;
	private String letraBis;
	private String cuadViaPrincipal;
	private Integer numeroViaGeneral;
	private String LetraViaGeneral;
	private String numeroPlaca;
	private String cuadViaGen;
	private String complemento;
	private String barrio;
	private Integer localidad;
	private CiudadDTO ciudad;
	private String telefonoFijo;
	private String telefonoCelular;
	private String correoElectronico;
	private String direccion;
	private String direccionCompleta;
	private Date fechaCreacion;
	private Date fechaFinVigencia;
	private String estado;
	
	public UbicacionDTO() {
	}

	

	public UbicacionDTO(Integer identificador, Integer tipoZona, String direccionRural, String tipoViaPrincipal,
			Integer numeroViaPrincipal, String letraViaPrincipal, String bis, String letraBis, String cuadViaPrincipal,
			Integer numeroViaGeneral, String letraViaGeneral, String numeroPlaca, String cuadViaGen, String complemento,
			String barrio, Integer localidad, CiudadDTO ciudad, String telefonoFijo, String telefonoCelular,
			String correoElectronico, String direccion, String direccionCompleta, Date fechaCreacion,
			Date fechaFinVigencia, String estado) {
		super();
		this.identificador = identificador;
		this.tipoZona = tipoZona;
		this.direccionRural = direccionRural;
		this.tipoViaPrincipal = tipoViaPrincipal;
		this.numeroViaPrincipal = numeroViaPrincipal;
		this.letraViaPrincipal = letraViaPrincipal;
		this.bis = bis;
		this.letraBis = letraBis;
		this.cuadViaPrincipal = cuadViaPrincipal;
		this.numeroViaGeneral = numeroViaGeneral;
		LetraViaGeneral = letraViaGeneral;
		this.numeroPlaca = numeroPlaca;
		this.cuadViaGen = cuadViaGen;
		this.complemento = complemento;
		this.barrio = barrio;
		this.localidad = localidad;
		this.ciudad = ciudad;
		this.telefonoFijo = telefonoFijo;
		this.telefonoCelular = telefonoCelular;
		this.correoElectronico = correoElectronico;
		this.direccion = direccion;
		this.direccionCompleta = direccionCompleta;
		this.fechaCreacion = fechaCreacion;
		this.fechaFinVigencia = fechaFinVigencia;
		this.estado = estado;
	}



	public Integer getIdentificador() {
		return identificador;
	}

	public void setIdentificador(Integer identificador) {
		this.identificador = identificador;
	}

	public Integer getTipoZona() {
		return tipoZona;
	}

	public void setTipoZona(Integer tipoZona) {
		this.tipoZona = tipoZona;
	}

	public String getDireccionRural() {
		return direccionRural;
	}

	public void setDireccionRural(String direccionRural) {
		this.direccionRural = direccionRural;
	}

	public String getTipoViaPrincipal() {
		return tipoViaPrincipal;
	}

	public void setTipoViaPrincipal(String tipoViaPrincipal) {
		this.tipoViaPrincipal = tipoViaPrincipal;
	}

	public Integer getNumeroViaPrincipal() {
		return numeroViaPrincipal;
	}

	public void setNumeroViaPrincipal(Integer numeroViaPrincipal) {
		this.numeroViaPrincipal = numeroViaPrincipal;
	}

	public String getLetraViaPrincipal() {
		return letraViaPrincipal;
	}

	public void setLetraViaPrincipal(String letraViaPrincipal) {
		this.letraViaPrincipal = letraViaPrincipal;
	}

	public String getBis() {
		return bis;
	}

	public void setBis(String bis) {
		this.bis = bis;
	}

	public String getLetraBis() {
		return letraBis;
	}

	public void setLetraBis(String letraBis) {
		this.letraBis = letraBis;
	}

	public String getCuadViaPrincipal() {
		return cuadViaPrincipal;
	}

	public void setCuadViaPrincipal(String cuadViaPrincipal) {
		this.cuadViaPrincipal = cuadViaPrincipal;
	}

	public Integer getNumeroViaGeneral() {
		return numeroViaGeneral;
	}

	public void setNumeroViaGeneral(Integer numeroViaGeneral) {
		this.numeroViaGeneral = numeroViaGeneral;
	}

	public String getLetraViaGeneral() {
		return LetraViaGeneral;
	}

	public void setLetraViaGeneral(String letraViaGeneral) {
		LetraViaGeneral = letraViaGeneral;
	}

	public String getNumeroPlaca() {
		return numeroPlaca;
	}

	public void setNumeroPlaca(String numeroPlaca) {
		this.numeroPlaca = numeroPlaca;
	}

	public String getCuadViaGen() {
		return cuadViaGen;
	}

	public void setCuadViaGen(String cuadViaGen) {
		this.cuadViaGen = cuadViaGen;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBarrio() {
		return barrio;
	}

	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}

	public Integer getLocalidad() {
		return localidad;
	}

	public void setLocalidad(Integer localidad) {
		this.localidad = localidad;
	}

	public String getTelefonoFijo() {
		return telefonoFijo;
	}

	public void setTelefonoFijo(String telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
	}

	public String getTelefonoCelular() {
		return telefonoCelular;
	}

	public void setTelefonoCelular(String telefonoCelular) {
		this.telefonoCelular = telefonoCelular;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
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

	public String getDireccionCompleta() {
		return direccionCompleta;
	}

	public void setDireccionCompleta(String direccionCompleta) {
		this.direccionCompleta = direccionCompleta;
	}

	public CiudadDTO getCiudad() {
		return ciudad;
	}

	public void setCiudad(CiudadDTO ciudad) {
		this.ciudad = ciudad;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}
