/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.Util;

import co.gov.gobierno.DTO.LegalAgentDTO;
import co.gov.gobierno.DTO.LocationDTO;
import co.gov.gobierno.DTO.ThirdDTO;
import co.gov.gobierno.DTO.YoungerDTO;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;

/**
 *
 * @author DELL
 */
public class JsonHandler 
{
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(JsonHandler.class);
    public static Map<String, String> ReadSimpleJsonStringTwoValues(String json, String listName, String param1, String param2) 
    {
        Map<String, String> map = new LinkedHashMap<>();
        JsonElement jelement = new JsonParser().parse(json);
//        JsonObject jobject = jelement.getAsJsonObject();
        JsonArray jarray = jelement.getAsJsonArray();
        for ( int i = 0; i < jarray.size(); i++) {
            JsonObject obj = jarray.get(i).getAsJsonObject();
            JsonElement jDesc = obj.get(param1);
            JsonElement jValor = obj.get(param2);
            map.put(jDesc.getAsString(), jValor.getAsString());
            
        }
        return map;
    }
    
    public static List<LocationDTO> ReadSimpleJsonStringThreeValues(String json, String valorParametro, String descParametro, String identificador, String padre) 
    {
         
        List<LocationDTO> map = new ArrayList<>();
        JsonElement jelement = new JsonParser().parse(json);
        JsonArray jarray = jelement.getAsJsonArray();
        for ( int i = 0; i < jarray.size(); i++) {
            JsonObject obj = jarray.get(i).getAsJsonObject();
            JsonElement jDesc = obj.get(descParametro);
            JsonElement jValor = obj.get(valorParametro);
            JsonElement jIdentificador = obj.get(identificador);
            JsonElement jPadre = obj.get(padre);
            LocationDTO objLocationDTO = new LocationDTO(jDesc.getAsString(), jIdentificador.getAsString(), jValor.getAsString(), jPadre.getAsString());
            //map.put(jDesc.getAsString(), jValor.getAsString()+"|"+jValor2.getAsString());
            map.add(objLocationDTO);
            
        }
        return map;
    }

    public static ThirdDTO ReadThirdJsonString(String json, ThirdDTO objThirdDTO) 
    {
        int count=0, size=0;
        List<YoungerDTO> listYoungerDTO = new ArrayList<>();
        List<LegalAgentDTO> listLegalAgentDTOs = new ArrayList<>();
        try
        {
            JsonElement jelement = new JsonParser().parse(json);
            //JsonObject jobject = jelement.getAsJsonObject();
            JsonArray thirds = new JsonArray();
            try
            {
                thirds = jelement.getAsJsonArray();
                size = thirds.size();
            }
            catch(Exception e)
            {
                size=1;
            }
            for(int i=0;i<size;i++)
            {
                JsonObject firstO = new JsonObject();
                if(size==1)
                {
                    firstO = thirds.get(0).getAsJsonObject();
                }
                else
                {
                    firstO = thirds.get(i).getAsJsonObject();
                }
                JsonObject secondO = firstO.getAsJsonObject("datosBasicos");
                JsonObject thirdO = firstO.getAsJsonObject("ubicacion");
                if (secondO.get("tipoIdentificacion") != null)
                {
                    String idTypeAux = secondO.get("tipoIdentificacion").getAsString();
                    String numId=secondO.get("numeroIdentificacion").getAsString();
                    String numIdLogin;
                    if(objThirdDTO.getPass()==null) {
                    	numIdLogin=numId;
                    }else {
                    	numIdLogin=objThirdDTO.getUser();
                    } 
                    if((!idTypeAux.equals("TI") && !idTypeAux.equals("RC") && !idTypeAux.equals("VI")) && (numId.equals(numIdLogin)))
                    {
                        String thirdType = secondO.get("tipoTercero").getAsString();
                        if(thirdType.equals("T"))
                        {
                            //Datos basicos del tercero.
                            objThirdDTO.setFirstName(secondO.get("primerNombre").getAsString());
                            objThirdDTO.setLastName(secondO.get("primerApellido").getAsString());
                            if (secondO.get("segundoNombre") != null) {
                                objThirdDTO.setSecondName(secondO.get("segundoNombre").getAsString());
                            } else {
                                objThirdDTO.setSecondName("");
                            }
                            if (secondO.get("segundoApellido") != null) {
                                objThirdDTO.setSecondLastName(secondO.get("segundoApellido").getAsString());
                            } else {
                                objThirdDTO.setSecondLastName("");
                            }
                            objThirdDTO.setGender(secondO.get("genero").getAsString());
                            objThirdDTO.setIdType(secondO.get("tipoIdentificacion").getAsString());
                            objThirdDTO.setIdNumber(secondO.get("numeroIdentificacion").getAsString());
                            objThirdDTO.setBirthDate(secondO.get("fechaNacimiento").getAsString());

                            //Datos de ubicación
                            objThirdDTO.setNeightborhood(thirdO.get("barrio")!=null ? thirdO.get("barrio").getAsString():"");
                            objThirdDTO.setEmail(thirdO.get("correoElectronico") !=null ? thirdO.get("correoElectronico").getAsString():"");
                            //objThirdDTO.setAddress(thirdO.get("direccion").getAsString());
                            if (thirdO.get("direccion") != null) {
                                objThirdDTO.setAddress(thirdO.get("direccion").getAsString());
                            } else {
                                objThirdDTO.setAddress("");
                            }
                            
                            if (secondO.get("etnia") != null) {
                                objThirdDTO.setEtnia(secondO.get("etnia").getAsString());
                            } else {
                                objThirdDTO.setEtnia("");
                            }
                            
                            if (secondO.get("cualEtnia") != null) {
                                objThirdDTO.setCualEtnia(secondO.get("cualEtnia").getAsString());
                            } else {
                                objThirdDTO.setCualEtnia("");
                            }
                            
                            if (thirdO.get("complemento") != null) {
                                objThirdDTO.setAddressCompl(thirdO.get("complemento").getAsString());
                            } else {
                                objThirdDTO.setAddressCompl("");
                            }
                            //TODO Revisar direccion completa
                            /*if (thirdO.get("direccionCompleta") != null) {
                                objThirdDTO.setAddress(thirdO.get("direccionCompleta").getAsString());
                            } else {
                                objThirdDTO.setAddress("");
                            }*/
                            
                            
                            if (thirdO.get("direccionRural") != null) {
                                objThirdDTO.setRuralAddress(thirdO.get("direccionRural").getAsString());
                            } else {
                                objThirdDTO.setRuralAddress("");
                            }
                            objThirdDTO.setLocation(thirdO.get("localidad").getAsString());
                               if(thirdO.get("telefonoCelular") == null)
                                {   
                                    objThirdDTO.setCellphone("");
                                }
                            else
                                {
                                    objThirdDTO.setCellphone(thirdO.get("telefonoCelular").getAsString());
                                }
                            
                             if(thirdO.get("telefonoFijo")== null)
                                {   
                                    objThirdDTO.setPhone("");
                                }
                            else
                                {
                                    objThirdDTO.setPhone(thirdO.get("telefonoFijo").getAsString());
                                }
                            
                            JsonObject fourthO = thirdO.getAsJsonObject("ciudad");
                            objThirdDTO.setResidenceCity(fourthO.get("identificador").getAsString());
                            count++;
                        }
                        else if(thirdType.equals("R"))
                        {
                            LegalAgentDTO objLegalAgentDTO = new LegalAgentDTO();
                            String name = "";
                            name = name.concat(secondO.get("primerNombre").getAsString()).concat(" ");
                            if (secondO.get("segundoNombre") != null) {
                                name = name.concat(secondO.get("segundoNombre").getAsString()).concat(" ");
                            }
                            name = name.concat(secondO.get("primerApellido").getAsString()).concat(" ");
                            if (secondO.get("segundoApellido") != null) {
                                name = name.concat(secondO.get("segundoApellido").getAsString());
                            }
                            objLegalAgentDTO.setName(name);
                            objLegalAgentDTO.setIdType(secondO.get("tipoIdentificacion").getAsString());
                            objLegalAgentDTO.setIdNumber(secondO.get("numeroIdentificacion").getAsString());
                            
                            if (thirdO.get("direccion") != null) {
                                objLegalAgentDTO.setAddress(thirdO.get("direccion").getAsString());
                            } else {
                                objLegalAgentDTO.setAddress("");
                            }
                            
                            if (thirdO.get("direccionCompleta") != null) {
                                objThirdDTO.setAddress(thirdO.get("direccionCompleta").getAsString());
                            } else {
                                objThirdDTO.setAddress("");
                            }
                            
                            if (thirdO.get("complemento") != null) {
                                objLegalAgentDTO.setAddressCompl(thirdO.get("complemento").getAsString());
                            } else {
                                objLegalAgentDTO.setAddressCompl("");
                            }

                            objLegalAgentDTO.setFirstName(secondO.get("primerNombre").getAsString());
                            objLegalAgentDTO.setLastName(secondO.get("primerApellido").getAsString());
                            if (secondO.get("segundoNombre") != null) {
                                objLegalAgentDTO.setSecondName(secondO.get("segundoNombre").getAsString());
                            }
                            if (secondO.get("segundoApellido") != null) {
                                objLegalAgentDTO.setSecondLastName(secondO.get("segundoApellido").getAsString());
                            }
                            objLegalAgentDTO.setGender(secondO.get("genero")!=null ?secondO.get("genero").getAsString():"");
                            objLegalAgentDTO.setBirthDate(secondO.get("fechaNacimiento") !=null ? secondO.get("fechaNacimiento").getAsString():"");

                            //Datos de ubicación
                            objLegalAgentDTO.setNeightborhood(thirdO.get("barrio")!=null ? thirdO.get("barrio").getAsString():"");
                            objLegalAgentDTO.setEmail(thirdO.get("correoElectronico") !=null ? thirdO.get("correoElectronico").getAsString():"");
                            if (thirdO.get("direccionRural") != null) {
                                objLegalAgentDTO.setRuralAddress(thirdO.get("direccionRural").getAsString());
                            }
                            objLegalAgentDTO.setLocation(thirdO.get("localidad").getAsString());
                            
                            if(thirdO.get("telefonoCelular") == null)
                                {   
                                    objLegalAgentDTO.setCellphone("");
                                }
                            else
                                {
                                    objLegalAgentDTO.setCellphone(thirdO.get("telefonoCelular").getAsString());
                                }
                            
                             if(thirdO.get("telefonoFijo") == null)
                                {   
                                    objLegalAgentDTO.setPhone("");
                                }
                            else
                                {
                                    objLegalAgentDTO.setPhone(thirdO.get("telefonoFijo").getAsString());
                                }
                           
                            JsonObject fourthO = thirdO.getAsJsonObject("ciudad");
                            objLegalAgentDTO.setResidenceCity(fourthO.get("identificador").getAsString());
                            
                            listLegalAgentDTOs.add(objLegalAgentDTO);
                        }
                    }
                    else if(!idTypeAux.equals("null"))
                    {
                        YoungerDTO objYoungerDTO = new YoungerDTO();
                        String name = "";
                        name = name.concat(secondO.get("primerNombre").getAsString()).concat(" ");
                        if (secondO.get("segundoNombre") != null) {
                            name = name.concat(secondO.get("segundoNombre").getAsString()).concat(" ");
                        }
                        name = name.concat(secondO.get("primerApellido").getAsString()).concat(" ");
                        if (secondO.get("segundoApellido") != null) {
                            name = name.concat(secondO.get("segundoApellido").getAsString());
                        }
                        objYoungerDTO.setName(name);
                        objYoungerDTO.setIdType(secondO.get("tipoIdentificacion").getAsString());
                        objYoungerDTO.setIdNumber(secondO.get("numeroIdentificacion").getAsString());
                        
                        if (thirdO.get("direccion") != null) {
                            objYoungerDTO.setAddress(thirdO.get("direccion").getAsString());
                            objYoungerDTO.setTypeDireccion("1");
                        }
                        
                        if (secondO.get("etnia") != null) {
                            objYoungerDTO.setEtnia(secondO.get("etnia").getAsString());
                        } else {
                            objYoungerDTO.setEtnia("");
                        }

                        if (secondO.get("cualEtnia") != null) {
                            objYoungerDTO.setCualEtnia(secondO.get("cualEtnia").getAsString());
                        } else {
                            objYoungerDTO.setCualEtnia("");
                        }
                        
                        if (thirdO.get("complemento") != null) {
                            objYoungerDTO.setAddressCompl(thirdO.get("complemento").getAsString());
                        }
                        
                        if (thirdO.get("direccionCompleta") != null) {
                            objThirdDTO.setAddress(thirdO.get("direccionCompleta").getAsString());
                        } else {
                            objThirdDTO.setAddress("");
                        }
                        
                        objYoungerDTO.setFirstName(secondO.get("primerNombre").getAsString());
                        objYoungerDTO.setLastName(secondO.get("primerApellido").getAsString());
                        if (secondO.get("segundoNombre") != null) {
                            objYoungerDTO.setSecondName(secondO.get("segundoNombre").getAsString());
                        }
                        if (secondO.get("segundoApellido") != null) {
                            objYoungerDTO.setSecondLastName(secondO.get("segundoApellido").getAsString());
                        }
                        objYoungerDTO.setGender(secondO.get("genero")!=null ? secondO.get("genero").getAsString():"");
                        objYoungerDTO.setBirthDate(secondO.get("fechaNacimiento") !=null ?secondO.get("fechaNacimiento").getAsString():"");

                        //Datos de ubicación
                        objYoungerDTO.setNeightborhood(thirdO.get("barrio")!=null ? thirdO.get("barrio").getAsString():"");
                        objYoungerDTO.setEmail(thirdO.get("correoElectronico")!=null ? thirdO.get("correoElectronico").getAsString():"");
                        if (thirdO.get("direccionRural") != null) {
                            objYoungerDTO.setRuralAddress(thirdO.get("direccionRural").getAsString());
                            objYoungerDTO.setTypeDireccion("2");
                        }
                        objYoungerDTO.setLocation(thirdO.get("localidad").getAsString());
                        
                              if(thirdO.get("telefonoCelular") == null)
                                {   
                                    objYoungerDTO.setCellphone("");
                                }
                            else
                                {
                                    objYoungerDTO.setCellphone(thirdO.get("telefonoCelular").getAsString());
                                }
                            
                             if(thirdO.get("telefonoFijo")== null)
                                {   
                                    objYoungerDTO.setPhone("");
                                }
                            else
                                {
                                    objYoungerDTO.setPhone(thirdO.get("telefonoFijo").getAsString());
                                }
                        JsonObject fourthO = thirdO.getAsJsonObject("ciudad");
                        objYoungerDTO.setResidenceCity(fourthO.get("identificador").getAsString());
                        
                        //objYoungerDTO = ReadAdicionalDataYounger(objYoungerDTO, json);
                        
                        listYoungerDTO.add(objYoungerDTO);
                    }
                    else
                    {
                        return null;
                    }
                }
            }
        }
        catch(NullPointerException e)
        {
            System.out.println("JsonHandler: ReadThird: Hay un valor nulo.");
            return null;
        }
        if(count>0)
        {
            objThirdDTO.setListYoungerDTO(listYoungerDTO);
            objThirdDTO.setListLegalAgentDTOs(listLegalAgentDTOs);
            return objThirdDTO;
        }
        else
            return null;
    }
    
    
    public static ThirdDTO ReadLegalAgentJsonString(String json, ThirdDTO objThirdDTO) 
    {
        int count=0, size=0;
        List<LegalAgentDTO> listLegalAgentDTOs = new ArrayList<>();
        LegalAgentDTO objLegalAgentDTO = new LegalAgentDTO();
        try
        {
            JsonElement jelement = new JsonParser().parse(json);
            //JsonObject jobject = jelement.getAsJsonObject();
            JsonArray thirds = new JsonArray();
            try
            {
            	thirds = jelement.getAsJsonArray();
                //thirds = jobject.getAsJsonArray();
                size = thirds.size();
            }
            catch(Exception e)
            {
                size=1;
            }
            for(int i=0;i<size;i++)
            {
                JsonObject firstO = new JsonObject();
                
                //JsonObject firstO = new JsonObject();
                if(size==1)
                {
                    firstO = thirds.get(0).getAsJsonObject();
                }
                else
                {
                    firstO = thirds.get(i).getAsJsonObject();
                }
                
                /*if(size==1)
                {
                    firstO = jobject.getAsJsonObject("terceroDTO");
                }
                else
                {
                    firstO = thirds.get(i).getAsJsonObject();
                } */
                JsonObject secondO = firstO.getAsJsonObject("datosBasicos");
                JsonObject thirdO = firstO.getAsJsonObject("ubicacion");
                if (secondO.get("tipoIdentificacion") != null)
                {
                    
                        
                    String name = "";
                    if (secondO.get("primerNombre") == null)
                    {
                        objLegalAgentDTO.setRazonSocialLegalAgent(secondO.get("razonSocial").getAsString());
                        objLegalAgentDTO.setPersonType("PJ");
                    } else {
                        objLegalAgentDTO.setFirstName(secondO.get("primerNombre").getAsString());
                        name = name.concat(secondO.get("primerNombre").getAsString()).concat(" ");
                        if (secondO.get("segundoNombre") != null) {
                            objLegalAgentDTO.setSecondName(secondO.get("segundoNombre").getAsString());
                            name = name.concat(secondO.get("segundoNombre").getAsString()).concat(" ");
                        }
                        objLegalAgentDTO.setLastName(secondO.get("primerApellido").getAsString());
                        name = name.concat(secondO.get("primerApellido").getAsString()).concat(" ");
                        if (secondO.get("segundoApellido") != null) {
                            objLegalAgentDTO.setSecondLastName(secondO.get("segundoApellido").getAsString());
                            name = name.concat(secondO.get("segundoApellido").getAsString());
                        }
                        objLegalAgentDTO.setPersonType("PN");
                        if (secondO.get("genero") != null) {
                            objLegalAgentDTO.setGender(secondO.get("genero").getAsString());
                        }
                    }
                    if (secondO.get("etnia") != null) {
                        objLegalAgentDTO.setEtnia(secondO.get("etnia").getAsString());
                    } else {
                        objLegalAgentDTO.setEtnia("");
                    }

                    if (secondO.get("cualEtnia") != null) {
                        objLegalAgentDTO.setCualEtnia(secondO.get("cualEtnia").getAsString());
                    } else {
                        objLegalAgentDTO.setCualEtnia("");
                    }
                    objLegalAgentDTO.setName(name);
                    objLegalAgentDTO.setIdType(secondO.get("tipoIdentificacion").getAsString());
                    objLegalAgentDTO.setIdNumber(secondO.get("numeroIdentificacion").getAsString());
                    if (thirdO.get("direccion") != null)
                    { 
                        objLegalAgentDTO.setAddress(thirdO.get("direccion").getAsString());
                    }
                    if (thirdO.get("complemento") != null) {
                        objLegalAgentDTO.setAddressCompl(thirdO.get("complemento").getAsString());
                    } else {
                        objLegalAgentDTO.setAddressCompl("");
                    }
                    if (thirdO.get("direccionRural") != null)
                    { 
                        objLegalAgentDTO.setRuralAddress(thirdO.get("direccionRural").getAsString());
                    }
                    if (thirdO.get("direccionCompleta") != null) {
                    	objLegalAgentDTO.setAddress(thirdO.get("direccionCompleta").getAsString());
                    } else {
                    	objLegalAgentDTO.setAddress("");
                    }
                    
                    if (thirdO.get("telefonoFijo") != null)
                    { 
                        objLegalAgentDTO.setPhone(thirdO.get("telefonoFijo").getAsString());
                    }
                    if (thirdO.get("correoElectronico") != null)
                    { 
                        objLegalAgentDTO.setEmail(thirdO.get("correoElectronico").getAsString());
                    }
                    
                    listLegalAgentDTOs.add(objLegalAgentDTO);
                    count++;   
                }
            }
        }
        catch(NullPointerException e)
        {
            System.out.println("JsonHandler: ReadThird: Hay un valor nulo.");
            return null;
        }
        if(count>0)
        {
            objThirdDTO.setListLegalAgentDTOs(listLegalAgentDTOs);
            return objThirdDTO;
        }
        else
            return null;
    }
    
    public static ThirdDTO ReadAdicionalData(ThirdDTO objThirdDTO, String json)
    {
        int size=0;
        try
        {
            JsonElement jelement = new JsonParser().parse(json);
            //JsonObject jobject = jelement.getAsJsonObject();
            JsonArray thirds = new JsonArray();
            try
            {
                thirds = jelement.getAsJsonArray();
                size = thirds.size();
            }
            catch(Exception e)
            {
                size=1;
            }
            for(int i=0;i<size;i++)
            {
                JsonObject firstO = new JsonObject();
                firstO = thirds.get(i).getAsJsonObject();
                JsonObject secondO = firstO.getAsJsonObject("tipoDatoAdicional");
                String id = secondO.get("identificador").getAsString();
                switch(id)
                {
                    case "3":
                    {
                        objThirdDTO.setNationality(firstO.get("valor")!= null ? firstO.get("valor").getAsString() : "");
                        break;
                    }
                    case "4":
                    {
                        objThirdDTO.setOccupation(firstO.get("valor") != null ? firstO.get("valor").getAsString() : "");
                        break;
                    }
                    case "5":
                    {
                        objThirdDTO.setStratum(firstO.get("valor") != null ? firstO.get("valor").getAsString() : "");
                        break;
                    }
                    case "6":
                    {
                        objThirdDTO.setSpecialCondition(firstO.get("valor") != null ? firstO.get("valor").getAsString() : "");
                        break;
                    }
                    
                    case "8":
                    {
                        objThirdDTO.setSexo(firstO.get("valor") != null ? firstO.get("valor").getAsString() : "");
                        break;
                    }
                    case "9":
                    {
                        objThirdDTO.setSexualOrient(firstO.get("valor") != null ? firstO.get("valor").getAsString() : "");
                        break;
                    }
                }
            }
            return objThirdDTO;
        }
        catch(JsonSyntaxException e)
        {
            log.error("Error al leer los datos adicionales del tercero: " + e.toString());
            return null;
        }
        catch(Exception e)
        {
            log.error("Error al leer los datos adicionales del tercero: " + e.toString());
            return null;
        }
    }
    
    public static YoungerDTO ReadAdicionalDataYounger(YoungerDTO objYoungerDTO, String json)
    {
        int size=0;
        try
        {
            JsonElement jelement = new JsonParser().parse(json);
            //JsonObject jobject = jelement.getAsJsonObject();
            JsonArray thirds = new JsonArray();
            try
            {
                thirds = jelement.getAsJsonArray();
                size = thirds.size();
            }
            catch(Exception e)
            {
                size=1;
            }
            for(int i=0;i<size;i++)
            {
                JsonObject firstO = new JsonObject();
                firstO = thirds.get(i).getAsJsonObject();
                JsonObject secondO = firstO.getAsJsonObject("tipoDatoAdicional");
                String id = secondO.get("identificador").getAsString();
                switch(id)
                {
                    case "3":
                    {
                        objYoungerDTO.setNationality(firstO.get("valor").getAsString());
                        break;
                    }
                    case "4":
                    {
                        objYoungerDTO.setOccupation(firstO.get("valor").getAsString());
                        break;
                    }
                    case "5":
                    {
                        objYoungerDTO.setStratum(firstO.get("valor").getAsString());
                        break;
                    }
                    case "6":
                    {
                        objYoungerDTO.setSpecialCondition(firstO.get("valor").getAsString());
                        break;
                    }
                    case "8":
                    {
                        objYoungerDTO.setSexo(firstO.get("valor").getAsString());
                        break;
                    }
                    case "9":
                    {
                        objYoungerDTO.setSexualOrient(firstO.get("valor").getAsString());
                        break;
                    }
                }
            }
            return objYoungerDTO;
        }
        catch(JsonSyntaxException e)
        {
            log.error("Error al leer los datos adicionales del tercero: " + e.toString());
            return null;
        }
        catch(Exception e)
        {
            log.error("Error al leer los datos adicionales del tercero: " + e.toString());
            return null;
        }
    }
}
