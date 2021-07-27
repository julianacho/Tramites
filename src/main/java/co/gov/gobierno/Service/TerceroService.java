package co.gov.gobierno.Service;

import java.util.List;
import java.util.Map;

import co.gov.gobierno.DTO.DatosAdicionalesDTO;
import co.gov.gobierno.DTO.LocationDTO;
import co.gov.gobierno.DTO.TerceroDTO;
import co.gov.gobierno.DTO.ThirdDTO;

public interface TerceroService 
{
	String Login(String Usuario, String Password);
	String ModificarPassword(String Usuario, String Password, String PasswordAnterior);
	List<TerceroDTO> ConsultarTercero(String identificacionTercero);
	String ConsultarDatosTercero(String identificacionTercero);//TODO Revisar el retorno de este metodo
	String ConsultarCiudad(String CodigoPais);//TODO Revisar el retorno de este metodo
	Map<String, String> ConsultarPais();
	List<TerceroDTO> ConsultarTerceroUsuario(String nombreUsuario);
	List<DatosAdicionalesDTO> BuscarDatosAdicionales(String identificacionTercero, String sistem);
	ThirdDTO ConsultaUbicacionTercero(String identificacionTercero);//TODO Revisar el retorno de este metodo
	Map<String, String> ConsultarParametro(String tipoParametro, int skParent);
	String ConsultarTipoDatosAdicional(String Sistema); //TODO Revisar el retorno de este metodo
	void RegistrarDatosAdicionalTercero(List<DatosAdicionalesDTO> datos);
	String RegistrarDatosTercero(TerceroDTO terceroDTO);
	String RegistrarDatosUbicacionTercero(TerceroDTO terceroDTO);
	List<LocationDTO> ConsultarParametroLocation(String tipoParametro, int skParent);
}
