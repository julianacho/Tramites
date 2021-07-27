package co.gov.gobierno.DTO;

import java.io.Serializable;

public class TipoDatoAdicionalDTO implements Serializable {
	public Integer getIdentificador() {
		return identificador;
	}
	public void setIdentificador(Integer identificador) {
		this.identificador = identificador;
	}
	public String getAtributo() {
		return atributo;
	}
	public void setAtributo(String atributo) {
		this.atributo = atributo;
	}
	public String getTipoDatos() {
		return tipoDatos;
	}
	public void setTipoDatos(String tipoDatos) {
		this.tipoDatos = tipoDatos;
	}
	public String getSistemaOrigen() {
		return sistemaOrigen;
	}
	public void setSistemaOrigen(String sistemaOrigen) {
		this.sistemaOrigen = sistemaOrigen;
	}
	private static final long serialVersionUID = 1L;
	private Integer identificador;
	private String atributo;
	private String tipoDatos;
	private String sistemaOrigen;


}
