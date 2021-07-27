package co.gov.gobierno.Util;

public class ValidUtility {
	
	/**
	 * Metodo que realiza la validacion del tipo de identificacion y retorna true si esta bn o false si esta mal
	 * @param numeroIdentificacion
	 * @param typoIdentificacion
	 * @return
	 */
	public static boolean validateIdentificacion(String numeroIdentificacion, String typoIdentificacion) {
		if(!typoIdentificacion.equals("CE") &&  !typoIdentificacion.equals("VI")) {
			return numeroIdentificacion.matches("[+-]?\\d*(\\.\\d+)?");
		}else {
			return true;
		}
        
	}

}
