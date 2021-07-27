package co.gov.gobierno.DTO;

import java.io.Serializable;

public class DatosAdicionalesDTO implements Serializable {
	
	public Integer getIdentificador() {
		return identificador;
	}
	public void setIdentificador(Integer identificador) {
		this.identificador = identificador;
	}
	public String getIdentificacionTercero() {
		return identificacionTercero;
	}
	public void setIdentificacionTercero(String identificacionTercero) {
		this.identificacionTercero = identificacionTercero;
	}
	public TipoDatoAdicionalDTO getTipoDatoAdicional() {
		return tipoDatoAdicional;
	}
	public void setTipoDatoAdicional(TipoDatoAdicionalDTO tipoDatoAdicional) {
		this.tipoDatoAdicional = tipoDatoAdicional;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	private static final long serialVersionUID = 1L;

	private Integer identificador;
	private String identificacionTercero;
	private TipoDatoAdicionalDTO tipoDatoAdicional;
	private String valor;
	private String estado;
}
