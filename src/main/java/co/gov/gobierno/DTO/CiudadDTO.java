package co.gov.gobierno.DTO;

import java.io.Serializable;

public class CiudadDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer identificador;
	private String codigoCiudad;
	private String nombreCiudad;
	
	public CiudadDTO(){}
	
	
	
	public CiudadDTO(Integer identificador, String codigoCiudad, String nombreCiudad) {
		super();
		this.identificador = identificador;
		this.codigoCiudad = codigoCiudad;
		this.nombreCiudad = nombreCiudad;
	}



	public Integer getIdentificador() {
		return identificador;
	}
	public void setIdentificador(Integer identificador) {
		this.identificador = identificador;
	}
	public String getCodigoCiudad() {
		return codigoCiudad;
	}
	public void setCodigoCiudad(String codigoCiudad) {
		this.codigoCiudad = codigoCiudad;
	}
	public String getNombreCiudad() {
		return nombreCiudad;
	}
	public void setNombreCiudad(String nombreCiudad) {
		this.nombreCiudad = nombreCiudad;
	}
}
