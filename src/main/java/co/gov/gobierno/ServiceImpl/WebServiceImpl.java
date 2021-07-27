/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.ServiceImpl;

import co.gov.gobierno.DTO.CiudadDTO;
import co.gov.gobierno.DTO.DatosAdicionalesDTO;
import co.gov.gobierno.DTO.DatosBasicosDTO;
import co.gov.gobierno.DTO.LegalAgentDTO;
import co.gov.gobierno.DTO.LogAuditoriaDTO;
import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.DTO.TerceroDTO;
import co.gov.gobierno.DTO.ThirdDTO;
import co.gov.gobierno.DTO.TipoDatoAdicionalDTO;
import co.gov.gobierno.DTO.UbicacionDTO;
import co.gov.gobierno.DTO.WebServiceDTO;
import co.gov.gobierno.Service.LogAuditoriaService;
import co.gov.gobierno.Service.TerceroService;
import co.gov.gobierno.Service.WebService;
import co.gov.gobierno.Util.JsonHandler;
import co.gov.gobierno.Util.PropertiesHandler;
import co.gov.gobierno.Util.XmlHandler;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.lucene.search.FieldCache.IntParser;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.gson.Gson;

/**
 *
 * @author DELL
 */
@Stateless
public class WebServiceImpl implements WebService 
{
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(WebServiceImpl.class);
    @EJB
    LogAuditoriaService logService;
    
    @EJB
    TerceroService ts;
    
    private LogAuditoriaDTO logAuditoriaDTO = new LogAuditoriaDTO(0, "NA", "IP", "SO", "NA", "WebServiceImpl", "NA", "NA", "NA", null);


    @Override
    public WebServiceDTO getService(WebServiceDTO objWebServiceDTO) {
        try {
        	//Llego
            URL url = new URL(objWebServiceDTO.getUrl());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(objWebServiceDTO.getMethod());
            if (!objWebServiceDTO.getHeader().isEmpty()) 
            {
                Set<String> mapKeys = objWebServiceDTO.getHeader().keySet();

                for (String key : mapKeys) 
                {
                    con.setRequestProperty(key, objWebServiceDTO.getHeader().get(key));
                }
                con.setRequestProperty("Encoding", "UTF-8");
            }

            int responseCode = con.getResponseCode();

            StringBuilder response = new StringBuilder();
            String output ="", json = "";
            
            InputStreamReader isr = null;
            if (responseCode == HttpURLConnection.HTTP_OK) 
            {
                isr = new InputStreamReader(con.getInputStream());
                objWebServiceDTO.setRespuesta(true);
            } 
            else 
            {
                try
                {
                    isr = new InputStreamReader(con.getErrorStream());
                }
                catch(Exception e)
                {
                    log.error("Error en el servicio web de " + objWebServiceDTO.getUrl() + ": " + e.toString());
                    this.logAuditoriaDTO.setAuMensajeError("getService " + "Error en el servicio web de " + objWebServiceDTO.getUrl() + ": " + e.toString());
                    logService.AddLogAuditoria(logAuditoriaDTO);
                }
                finally
                {
                    objWebServiceDTO.setRespuesta(false);
                }
            }
            BufferedReader in = null;
            if(isr!=null)
            {
                in = new BufferedReader(isr);
                while ((output = in.readLine()) != null) 
                {
                    json += output;
                    response.append(output);
                }
            }
            if(!json.isEmpty() && !json.equals("null"))
            {
                objWebServiceDTO.setJsonFile(json);
                String answer = response.toString();
                String subtoken = "";
                if (answer.length() >= 25)
                {
                    subtoken = answer.substring(19, answer.length() - 2);
                    objWebServiceDTO.setToken(subtoken);
                }
                else 
                {
                    objWebServiceDTO.setToken(answer);
                }
            }
            else
            {
                log.error("Este servicio web de " + objWebServiceDTO.getUrl() + " no retorno ningun Json.");
                this.logAuditoriaDTO.setAuMensajeError("getService " + "Este servicio web de " + objWebServiceDTO.getUrl() + " no retorno ningun Json.");
                logService.AddLogAuditoria(logAuditoriaDTO);
                objWebServiceDTO.setRespuesta(false);
            }
            con.disconnect();
            return objWebServiceDTO;
        }
        catch (IOException e) 
        {
            objWebServiceDTO.setRespuesta(false);
            log.error("Error en el servicio web de " + objWebServiceDTO.getUrl() + ": " + e.toString());
            this.logAuditoriaDTO.setAuMensajeError("getService " + "Error en el servicio web de " + objWebServiceDTO.getUrl() + ": " + e.toString());
            logService.AddLogAuditoria(logAuditoriaDTO);
            return null;
        }
    }   

    @Override
	public WebServiceDTO postServiceWithParam(WebServiceDTO objWebServiceDTO) {
        try {
            URL url = new URL(objWebServiceDTO.getUrl());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(objWebServiceDTO.getMethod());
            if (!objWebServiceDTO.getHeader().isEmpty()) 
            {
                Set<String> mapKeys = objWebServiceDTO.getHeader().keySet();

                for (String key : mapKeys) 
                {
                    con.setRequestProperty(key, objWebServiceDTO.getHeader().get(key));
                }
            }
            if (log.isDebugEnabled()) {            	
            	log.error("Request servicio "+ objWebServiceDTO.getUrl());
            	log.error("------------ INICIO----------\n\n");
            	log.error(objWebServiceDTO.getParams());
            	log.error("\n\n------------ FIN----------\n\n");
            }
            // For POST only - START
            con.setDoOutput(true);
            try (OutputStream os = con.getOutputStream())
            {
                os.write(objWebServiceDTO.getParams().getBytes("UTF-8"));
                os.flush();
                // For POST only - END
            }
            catch(IOException e)
            {
                log.error("Error:" + e.toString());
                return null;
            }
            catch(Exception e)
            {
                log.error("Error:" + e.toString());
                this.logAuditoriaDTO.setAuMensajeError("postServiceWithParam " + "Error " + e.toString());
                logService.AddLogAuditoria(logAuditoriaDTO);
                return null;
            }
            
            int responseCode = con.getResponseCode();

            String inputLine, json = "";
            StringBuilder response = new StringBuilder();
            InputStreamReader isr = null;
            if (responseCode == HttpURLConnection.HTTP_OK) 
            {
                isr = new InputStreamReader(con.getInputStream());
                objWebServiceDTO.setRespuesta(true);
            } 
            else 
            {
                try
                {
                    isr = new InputStreamReader(con.getErrorStream());
                }
                catch(Exception e)
                {
                    log.error("Error en el servicio web: " + objWebServiceDTO.getUrl() + ": " + e.toString());
                    this.logAuditoriaDTO.setAuMensajeError("postServiceWithParam " + "Error en el servicio web de " + objWebServiceDTO.getUrl() + ": " + e.toString());
                    logService.AddLogAuditoria(logAuditoriaDTO);
                }
                finally
                {
                    con.disconnect();
                    objWebServiceDTO.setRespuesta(false);
                }
            }
            
            BufferedReader in = null;
            if(isr!=null)
            {
                in = new BufferedReader(isr);
                while ((inputLine = in.readLine()) != null) 
                {
                    json += inputLine;
                }
            }
            
            log.error("Response servicio "+ objWebServiceDTO.getUrl());
            log.error("------------ INICIO----------\n\n");
            log.error(json);
            log.error("\n\n------------ FIN----------\n\n");
            
            if(!json.isEmpty() && !json.equals("null"))
            {
                objWebServiceDTO.setJsonFile(json);
                String subtoken = "";
                if (json.contains("access-token"))
                {
                    subtoken = json.substring(19, json.length() - 2);
                    objWebServiceDTO.setToken(subtoken);
                }
                else 
                {
                    log.error("Este servicio web de " + objWebServiceDTO.getUrl() + " no retorno ningun Json.");
                    this.logAuditoriaDTO.setAuMensajeError("postServiceWithParam " + "Este servicio web de " + objWebServiceDTO.getUrl());
                    logService.AddLogAuditoria(logAuditoriaDTO);
                    objWebServiceDTO.setToken(json);
                }
            }
            else
            {
                objWebServiceDTO.setRespuesta(false);
            }
            
            return objWebServiceDTO;

        }
        catch (IOException e) 
        {
            objWebServiceDTO.setRespuesta(false);
            log.error("Error en el servicio web de " + objWebServiceDTO.getUrl() + ": " + e.toString());
            this.logAuditoriaDTO.setAuMensajeError("postServiceWithParam " + "Error en el servicio web de " + objWebServiceDTO.getUrl() + ": " + e.toString());
            logService.AddLogAuditoria(logAuditoriaDTO);
            return null;
        }
    }

    @Override
    public WebServiceDTO SoapWithParam(WebServiceDTO objWebServiceDTO) 
    {
        try 
        {
            String responseString = "";
            String outputString = "";
            URL url = new URL(objWebServiceDTO.getUrl());
            log.info("url : "+url);
            log.info("metodo : "+objWebServiceDTO.getMethod() );
            URLConnection connection = url.openConnection();
            HttpURLConnection httpConn = (HttpURLConnection) connection;
            httpConn.setRequestProperty("connection", "close");
            httpConn.setUseCaches(false);
            ByteArrayOutputStream bout = new ByteArrayOutputStream();

            byte[] buffer = new byte[objWebServiceDTO.getParams().length()];
            buffer = objWebServiceDTO.getParams().getBytes();
            bout.write(buffer);
            byte[] b = bout.toByteArray();
            httpConn.setRequestProperty("Content-Length",String.valueOf(b.length));
            if (!objWebServiceDTO.getHeader().isEmpty()) 
            {
                Set<String> mapKeys = objWebServiceDTO.getHeader().keySet();

                for (String key : mapKeys) 
                {
                    httpConn.setRequestProperty(key, objWebServiceDTO.getHeader().get(key));
                }
            }
            httpConn.setRequestProperty("SOAPAction", objWebServiceDTO.getSoapAction());
            httpConn.setRequestMethod(objWebServiceDTO.getMethod());
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            try (OutputStream out = httpConn.getOutputStream()) 
            {
                out.write(b);
                out.flush();
            }

            InputStreamReader isr = null;
            int responseCode = httpConn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) 
            {
                isr = new InputStreamReader(httpConn.getInputStream());
                objWebServiceDTO.setRespuesta(true);
            } 
            else 
            {
                isr = new InputStreamReader(httpConn.getErrorStream());
                log.error("Internal server error: " + httpConn.getErrorStream().toString());
                this.logAuditoriaDTO.setAuMensajeError("SoapWithParam " + "Internal server error: " + httpConn.getErrorStream().toString());
                logService.AddLogAuditoria(logAuditoriaDTO);
                objWebServiceDTO.setRespuesta(false);
            }

            BufferedReader in = new BufferedReader(isr);

            while ((responseString = in.readLine()) != null) 
            {
                outputString = outputString + responseString;
            }
            //Validar que no venga un tag de error en la respuesta para marcar en false si hay un error en tag
            //Segun tipo de aplicacion, para eso debemo de crear un campo en el objeto WebServiceDTO, para diferenciar la respuesta de donde viene si orfeo o bizagi u otro
            
            
            objWebServiceDTO.setXmlFile(outputString);
            return objWebServiceDTO;
            
        } 
        catch (MalformedURLException ex) 
        {
            log.error("Error en el servicio web de " + objWebServiceDTO.getUrl() + ": " + ex.toString());
            this.logAuditoriaDTO.setAuMensajeError("SoapWithParam Error en el servicio web de " + objWebServiceDTO.getUrl() + ": " + ex.toString());
            logService.AddLogAuditoria(logAuditoriaDTO);
        } 
        catch (IOException ex) 
        {
            log.error("Error en el servicio web de " + objWebServiceDTO.getUrl() + ": " + ex.toString());
            this.logAuditoriaDTO.setAuMensajeError("SoapWithParam Error en el servicio web de " + objWebServiceDTO.getUrl() + ": " + ex.toString());
            logService.AddLogAuditoria(logAuditoriaDTO);
        }
        return objWebServiceDTO;
    }
    
    @Override
    public boolean appendDocumentRadicado(List<PropertieDTO> listPropertieDTOs, List<PropertieDTO> listPHPropertieDTOs, String doc, String radNumber)
    {
        String tipoEndpointOrfeo = (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "TipoEndpointOrfeo", "5") == null ? "2" : PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "TipoEndpointOrfeo", "5"));  	
        if (tipoEndpointOrfeo.equals("2")) {
        	tipoEndpointOrfeo = "         <file xsi:type=\"xsd:base64Binary\">" + doc + "</file>\n";
        }else {
        	tipoEndpointOrfeo = "         <file xsi:type=\"xsd:string\">" + doc + "</file>\n";
        }
        String params = "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:org.gobierno.webserviceorfeo\">\n"+
                        "   <soapenv:Header/>\n"+
                        "   <soapenv:Body>\n"+
                        "      <urn:cambiarImagenRad soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n"+
                        "         <numRadicado xsi:type=\"xsd:string\">"+radNumber+"</numRadicado>\n"+
                        "         <ext xsi:type=\"xsd:string\">pdf</ext>\n"+
                        //"         <file xsi:type=\"xsd:string\">"+doc+"</file>\n"+
                        tipoEndpointOrfeo + 
                        "      </urn:cambiarImagenRad>\n"+
                        "   </soapenv:Body>\n"+
                        "</soapenv:Envelope>";
        WebServiceDTO objWebServiceDTO = new WebServiceDTO();
        objWebServiceDTO.setParams(params);
        objWebServiceDTO.setUrl(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "urlORFEO") );
        objWebServiceDTO.getHeader().put("Content-Type", "text/xml;charset=UTF-8");
        objWebServiceDTO.setMethod("POST");
        log.error("Se va a consumir el servicio de crear anexo de ORFEO...");
        objWebServiceDTO = this.SoapWithParam(objWebServiceDTO);
        logService.AddLogAuditoria(logAuditoriaDTO);
        if (objWebServiceDTO.getXmlFile().contains("codigoError")) {
        	log.info("Orfeo TagError");
        	String response = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "codigoError");
        	if(response != null && response.contains("0")) {
        		objWebServiceDTO.setRespuesta(false);
        	}
        }
        this.logAuditoriaDTO.setAuMensajeError("appendDocumentRadicado " + "Se va a consumir el servicio de crear anexo de ORFEO... ");
        this.logAuditoriaDTO.setAuRequest(params);
        this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile());
        this.logAuditoriaDTO.setAuLogUsuario(radNumber);
        logService.AddLogAuditoria(logAuditoriaDTO);
        return objWebServiceDTO.isRespuesta();
    }
    
    @Override
    public WebServiceDTO CreateThird(String idType, String idNumber, String firstName, String secondName, String lastName,
            String secondLastName, String birthDate, String gender, String sexo, String sexualOrient, String nationality, String cellphone, String phone,
            String address, String addressCompl, String ruralAddress, String location, String neightborhood, String residenceCity,
            String occupation, String specialCondition, String stratum, String email, String user, String etnia, String cualEtnia,
            List<PropertieDTO> listPropertieDTOs, String Password) 
    {
        //Crear tercero-------------------------------------------------------------------  	
    	TerceroDTO terceroDTO = new TerceroDTO();
    	terceroDTO.setPassword(Password);
    	DatosBasicosDTO datosBasicosDTO = new DatosBasicosDTO();
    	datosBasicosDTO.setEtnia(Integer.parseInt(etnia));
    	datosBasicosDTO.setCualEtnia(cualEtnia);
    	Date date1;
    	try {
			
			date1 = new SimpleDateFormat("yyyy-MM-dd").parse(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "fechaFinVigencia"));
			datosBasicosDTO.setFechaFinVigencia(date1);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		datosBasicosDTO.setNumeroIdentificacion(idNumber);
    	Date date2;
		try {
			date2 = new SimpleDateFormat("yyyy-MM-dd").parse(birthDate);
			datosBasicosDTO.setFechaNacimiento(date2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	
		datosBasicosDTO.setPrimerApellido(lastName);
		datosBasicosDTO.setPrimerNombre(firstName);
		datosBasicosDTO.setSegundoApellido(secondLastName);
		datosBasicosDTO.setSegundoNombre(secondName);
		datosBasicosDTO.setSexo(sexo);
		datosBasicosDTO.setGenero(gender);
		datosBasicosDTO.setTipoIdentificacion(idType);
		datosBasicosDTO.setTipoTercero(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "tipoTercero"));
		datosBasicosDTO.setUsuario(user);
    	terceroDTO.setDatosBasicos(datosBasicosDTO);
    	
    	Date date3;
		try {
			date3 = new SimpleDateFormat("yyyy-MM-dd").parse(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "fechaActualizacion"));
			terceroDTO.setFechaActualizacion(date3);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		terceroDTO.setUsuarioModificacion(user);
		terceroDTO.setDireccionIP(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "direccionIP"));
		if(!terceroDTO.getDatosBasicos().getTipoIdentificacion().equals("CE")) {
			terceroDTO.setIdentificacionTercero(idNumber.replaceAll("[^0-9]", ""));			
		}else {
			terceroDTO.setIdentificacionTercero(idNumber);
		}
		UbicacionDTO ubicacionDTO = new UbicacionDTO();
		ubicacionDTO.setBarrio(neightborhood);
		ubicacionDTO.setCorreoElectronico(email);
		ubicacionDTO.setDireccion(address.replace(" ",""));
		//ubicacionDTO.setDireccionCompleta(address.replace(" ","") + "" + addressCompl);
		ubicacionDTO.setDireccionRural(ruralAddress);
		ubicacionDTO.setComplemento(addressCompl);
    	Date date4;
		try {
			date4 = new SimpleDateFormat("yyyy-MM-dd").parse(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "fechaFinVigencia2"));
			ubicacionDTO.setFechaFinVigencia(date4);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		ubicacionDTO.setLocalidad(Integer.parseInt(location));
		ubicacionDTO.setTelefonoCelular(cellphone);
		ubicacionDTO.setTelefonoFijo(phone);
		ubicacionDTO.setTipoZona(Integer.parseInt(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "tipoZona")));
		ubicacionDTO.setEstado("1");
		CiudadDTO ciudadDTO = new CiudadDTO();
		ciudadDTO.setIdentificador(Integer.parseInt(residenceCity));
		ubicacionDTO.setCiudad(ciudadDTO);
		terceroDTO.setUbicacion(ubicacionDTO);
		WebServiceDTO objWebServiceDTO = new WebServiceDTO();
		objWebServiceDTO.setRespuesta(false);
		if (ts.RegistrarDatosUbicacionTercero(terceroDTO) == "0") {			
			if (ts.RegistrarDatosTercero(terceroDTO) == "0") {
				objWebServiceDTO.setRespuesta(true);
			};
		};
        //Envío Listas Adicionales--------------------------------------------------------------
        if(objWebServiceDTO.isRespuesta())
        {
        	List<DatosAdicionalesDTO> datosAdicionalesDTOs = new ArrayList();
        	
        	DatosAdicionalesDTO datosAdicionalesDTO = new DatosAdicionalesDTO();
        	datosAdicionalesDTO.setEstado("5");
        	datosAdicionalesDTO.setIdentificacionTercero(idNumber);
        	TipoDatoAdicionalDTO tipoDatoAdicionalDTO = new TipoDatoAdicionalDTO();
        	tipoDatoAdicionalDTO.setIdentificador(4);
        	datosAdicionalesDTO.setValor(occupation);
        	datosAdicionalesDTO.setTipoDatoAdicional(tipoDatoAdicionalDTO);
        	datosAdicionalesDTOs.add(datosAdicionalesDTO);
        	
        	datosAdicionalesDTO = new DatosAdicionalesDTO();
        	datosAdicionalesDTO.setEstado("1");
        	datosAdicionalesDTO.setIdentificacionTercero(idNumber);
        	tipoDatoAdicionalDTO = new TipoDatoAdicionalDTO();
        	tipoDatoAdicionalDTO.setIdentificador(3);
        	datosAdicionalesDTO.setValor(nationality);
        	datosAdicionalesDTO.setTipoDatoAdicional(tipoDatoAdicionalDTO);
        	datosAdicionalesDTOs.add(datosAdicionalesDTO);
        	
        	datosAdicionalesDTO = new DatosAdicionalesDTO();
        	datosAdicionalesDTO.setEstado("1");
        	datosAdicionalesDTO.setIdentificacionTercero(idNumber);
        	 tipoDatoAdicionalDTO = new TipoDatoAdicionalDTO();
        	tipoDatoAdicionalDTO.setIdentificador(5);
        	datosAdicionalesDTO.setValor(stratum);
        	datosAdicionalesDTO.setTipoDatoAdicional(tipoDatoAdicionalDTO);
        	datosAdicionalesDTOs.add(datosAdicionalesDTO);
        	
        	datosAdicionalesDTO = new DatosAdicionalesDTO();
        	datosAdicionalesDTO.setEstado("1");
        	datosAdicionalesDTO.setIdentificacionTercero(idNumber);
        	 tipoDatoAdicionalDTO = new TipoDatoAdicionalDTO();
        	tipoDatoAdicionalDTO.setIdentificador(6);
        	datosAdicionalesDTO.setValor(specialCondition);
        	datosAdicionalesDTO.setTipoDatoAdicional(tipoDatoAdicionalDTO);
        	datosAdicionalesDTOs.add(datosAdicionalesDTO);
        	
        	datosAdicionalesDTO = new DatosAdicionalesDTO();
        	datosAdicionalesDTO.setEstado("1");
        	datosAdicionalesDTO.setIdentificacionTercero(idNumber);
        	 tipoDatoAdicionalDTO = new TipoDatoAdicionalDTO();
        	tipoDatoAdicionalDTO.setIdentificador(7);
        	datosAdicionalesDTO.setValor(gender);
        	datosAdicionalesDTO.setTipoDatoAdicional(tipoDatoAdicionalDTO);
        	datosAdicionalesDTOs.add(datosAdicionalesDTO);
        	
        	datosAdicionalesDTO = new DatosAdicionalesDTO();
        	datosAdicionalesDTO.setEstado("1");
        	datosAdicionalesDTO.setIdentificacionTercero(idNumber);
        	 tipoDatoAdicionalDTO = new TipoDatoAdicionalDTO();
        	tipoDatoAdicionalDTO.setIdentificador(8);
        	datosAdicionalesDTO.setValor(sexo);
        	datosAdicionalesDTO.setTipoDatoAdicional(tipoDatoAdicionalDTO);
        	datosAdicionalesDTOs.add(datosAdicionalesDTO);
        	
        	datosAdicionalesDTO = new DatosAdicionalesDTO();
        	datosAdicionalesDTO.setEstado("1");
        	datosAdicionalesDTO.setIdentificacionTercero(idNumber);
        	tipoDatoAdicionalDTO = new TipoDatoAdicionalDTO();
        	tipoDatoAdicionalDTO.setIdentificador(9);
        	datosAdicionalesDTO.setValor(sexualOrient);
        	datosAdicionalesDTO.setTipoDatoAdicional(tipoDatoAdicionalDTO);
        	datosAdicionalesDTOs.add(datosAdicionalesDTO);
        	
        	ts.RegistrarDatosAdicionalTercero(datosAdicionalesDTOs);
        	return objWebServiceDTO;
        }
        else
        {
            return null;
        }
    }
    
    
    @Override
    public WebServiceDTO RegisterLegalAgent(ThirdDTO objThirdDTO, List<PropertieDTO> listPropertieDTOs, List<PropertieDTO> listPHPropertieDTOs,
            String nit, String idTypeLegalAgent, String idNumberLegalAgent, String firstNameLegalAgent, String lastNameLegalAgent,
            String secondNameLegalAgent, String secondLastNameLegalAgent, String cellphoneLegalAgent, String phoneLegalAgent, String birthDateLegalAgent,
            String addressLegalAgent, String addressComplLegalAgent, String neightborhoodLegalAgent, String emailLegalAgent, String dateAgent, String startAgent, String personType,
            String endAgent, String nameAgentJuridico, String idNumberAgentJuridico, String idTypeAgentJuridico, String razonSocialLegalAgent, String dateActAgent, 
            String numberActAgent, String genderLegalAgent, String typeDireccion, String etnia, String cualEtnia) 
    {
        Date now = new Date();
        SimpleDateFormat formatDay = new SimpleDateFormat("yyyy-MM-dd");
        String day = formatDay.format(now);
        String nameLegalAgent = firstNameLegalAgent + " " + secondNameLegalAgent + " " + lastNameLegalAgent + " " + secondLastNameLegalAgent;
        String params = "";
        String direccion = "";

    	TerceroDTO terceroDTO = new TerceroDTO();
    	if (objThirdDTO != null) {    		
    		terceroDTO.setPassword(objThirdDTO.getPass() != null ? objThirdDTO.getPass() : "");
    	}else {
    		terceroDTO.setPassword("");
    	}
    	DatosBasicosDTO datosBasicosDTO = new DatosBasicosDTO();

    	Date date1;
		try {
			date1 = new SimpleDateFormat("yyyy-MM-dd").parse(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "fechaFinVigencia"));
			datosBasicosDTO.setFechaFinVigencia(date1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  

		UbicacionDTO ubicacionDTO = new UbicacionDTO();
        
    	Date date4;
		try {
			date4 = new SimpleDateFormat("yyyy-MM-dd").parse(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "fechaFinVigencia2"));
			ubicacionDTO.setFechaFinVigencia(date4);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
        if (typeDireccion.equals("1"))
        {
            direccion = addressLegalAgent.replace(" ","") + " " +  addressComplLegalAgent;
            
            ubicacionDTO.setDireccion(direccion);
        }
        if (typeDireccion.equals("2"))
        {
            direccion =  addressLegalAgent.replace(" ","") ;
            
            ubicacionDTO.setDireccion(direccion);
        }
        if (personType.equals("PN"))
        {
        	datosBasicosDTO.setEtnia(Integer.parseInt(etnia));
        	datosBasicosDTO.setCualEtnia(cualEtnia);
        	datosBasicosDTO.setNumeroIdentificacion(idNumberLegalAgent);
        	
    		datosBasicosDTO.setPrimerApellido(lastNameLegalAgent);
    		datosBasicosDTO.setPrimerNombre(firstNameLegalAgent);
    		datosBasicosDTO.setSegundoApellido(secondLastNameLegalAgent);
    		datosBasicosDTO.setSegundoNombre(secondNameLegalAgent);
    		datosBasicosDTO.setTipoPersona(personType);
    		terceroDTO.setUsuarioModificacion(idNumberLegalAgent);
    		terceroDTO.setDireccionIP(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "direccionIP"));
    		terceroDTO.setIdentificacionTercero(idNumberLegalAgent.replaceAll("[^0-9]", ""));

    		
    		datosBasicosDTO.setTipoIdentificacion(idTypeLegalAgent);
    		datosBasicosDTO.setTipoTercero(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "tipoTercero"));
    		
        	Date date3;
    		try {
    			date3 = new SimpleDateFormat("yyyy-MM-dd").parse(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "fechaActualizacion"));
    			terceroDTO.setFechaActualizacion(date3);
    		} catch (ParseException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} 
    		
    		ubicacionDTO.setCorreoElectronico(emailLegalAgent);
    		CiudadDTO ciudadDTO = new CiudadDTO();
    		ciudadDTO.setIdentificador(Integer.parseInt(PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs, "identificadorRl")));
    		ubicacionDTO.setCiudad(ciudadDTO);
    		terceroDTO.setUbicacion(ubicacionDTO);
    		ubicacionDTO.setEstado(PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs, "estadoRl"));
    		ubicacionDTO.setTelefonoFijo(phoneLegalAgent);
    		terceroDTO.setDatosBasicos(datosBasicosDTO);
            
        }
        
        if (personType.equals("PJ"))
        {
        	datosBasicosDTO.setNumeroIdentificacion(idNumberLegalAgent);
        	datosBasicosDTO.setTipoPersona(personType);
    		terceroDTO.setUsuarioModificacion(idNumberLegalAgent);
    		terceroDTO.setDireccionIP(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "direccionIP"));
    		terceroDTO.setIdentificacionTercero(idNumberLegalAgent.replaceAll("[^0-9]", ""));
    		datosBasicosDTO.setTipoIdentificacion(idTypeLegalAgent);
    		datosBasicosDTO.setTipoTercero(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "tipoTercero"));
    		
        	Date date3;
    		try {
    			date3 = new SimpleDateFormat("yyyy-MM-dd").parse(day);
    			terceroDTO.setFechaActualizacion(date3);
    		} catch (ParseException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} 
    		
    		ubicacionDTO.setCorreoElectronico(emailLegalAgent);
    		CiudadDTO ciudadDTO = new CiudadDTO();
    		ciudadDTO.setIdentificador(Integer.parseInt(PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs, "identificadorRl")));
    		ubicacionDTO.setCiudad(ciudadDTO);
    		terceroDTO.setUbicacion(ubicacionDTO);
    		ubicacionDTO.setEstado(PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs, "estadoRl"));
    		ubicacionDTO.setTelefonoFijo(phoneLegalAgent);
    		ubicacionDTO.setTipoZona(Integer.parseInt(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "tipoZona")));
    		datosBasicosDTO.setRazonSocial(razonSocialLegalAgent);
    		datosBasicosDTO.setNit(Integer.parseInt(idNumberLegalAgent));
    		terceroDTO.setDatosBasicos(datosBasicosDTO);
        	
        }
        
        if (personType.equals("PL"))
        {
        	datosBasicosDTO.setEtnia(Integer.parseInt(etnia));
        	datosBasicosDTO.setCualEtnia(cualEtnia);
        	datosBasicosDTO.setNumeroIdentificacion(idNumberLegalAgent);
        	datosBasicosDTO.setPrimerApellido(lastNameLegalAgent);
    		datosBasicosDTO.setPrimerNombre(firstNameLegalAgent);
    		datosBasicosDTO.setSegundoApellido(secondLastNameLegalAgent);
    		datosBasicosDTO.setSegundoNombre(secondNameLegalAgent);
    		datosBasicosDTO.setGenero(genderLegalAgent);
    		datosBasicosDTO.setTipoPersona(personType);
    		terceroDTO.setUsuarioModificacion(idNumberLegalAgent);
    		terceroDTO.setDireccionIP(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "direccionIP"));
    		terceroDTO.setIdentificacionTercero(idNumberLegalAgent.replaceAll("[^0-9]", ""));
    		datosBasicosDTO.setTipoIdentificacion(idTypeLegalAgent);
    		datosBasicosDTO.setTipoTercero(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "tipoTercero"));
    		
        	Date date3;
    		try {
    			date3 = new SimpleDateFormat("yyyy-MM-dd").parse(day);
    			terceroDTO.setFechaActualizacion(date3);
    		} catch (ParseException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} 
    		
    		ubicacionDTO.setCorreoElectronico(emailLegalAgent);
    		CiudadDTO ciudadDTO = new CiudadDTO();
    		ciudadDTO.setIdentificador(Integer.parseInt(PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs, "identificadorRl")));
    		ubicacionDTO.setCiudad(ciudadDTO);
    		terceroDTO.setUbicacion(ubicacionDTO);
    		ubicacionDTO.setEstado(PropertiesHandler.PropertieValueOfKeyFromDB(listPHPropertieDTOs, "estadoRl"));
    		ubicacionDTO.setTelefonoFijo(phoneLegalAgent);
    		terceroDTO.setDatosBasicos(datosBasicosDTO);
        	
        }
        
        WebServiceDTO objWebServiceDTO = new WebServiceDTO();
        
		objWebServiceDTO.setRespuesta(false);
		if (ts.RegistrarDatosUbicacionTercero(terceroDTO) == "0") {			
			if (ts.RegistrarDatosTercero(terceroDTO) == "0") {
				objWebServiceDTO.setRespuesta(true);
			};
		};
        if(objWebServiceDTO.isRespuesta()) {
        	return objWebServiceDTO;
        }
        
        return null;
        
    }
    
    @Override
    public ThirdDTO FindRepresentanteLegal(List<PropertieDTO> listPropertieDTOs, String nit, List<LegalAgentDTO> listLegalAgentDTOs)
    {
    	try {
            ThirdDTO objThirdDTO = new ThirdDTO();
            WebServiceDTO objWebServiceDTO = new WebServiceDTO();
            log.error("Voy a consumir el servicio web de Consultar Tercero.");
            List<TerceroDTO> listTerceroDtos = ts.ConsultarTercero(nit);
            //List<TerceroDTO> listTerceroDtos = ts.ConsultarTercero(nit.replaceAll("[^0-9]", ""));
            Gson gson = new Gson();
            String json = gson.toJson(listTerceroDtos);
            
            if (listTerceroDtos.size() == 0 || listTerceroDtos == null)
            {
            	json = "Not Found";
            }
            
            objWebServiceDTO.setJsonFile(json);//this.getService(objWebServiceDTO);
            if(!objWebServiceDTO.getJsonFile().equals("Not Found"))
            {
                objThirdDTO = JsonHandler.ReadLegalAgentJsonString(objWebServiceDTO.getJsonFile(), objThirdDTO);
                listLegalAgentDTOs = objThirdDTO.getListLegalAgentDTOs(); 
                return objThirdDTO;
            }
            else
            {
                log.error("Error al consumir el servicio web de consultar tercero.");
                this.logAuditoriaDTO.setAuMensajeError("RegisterLegalAgent " + "Error al consumir el servicio web de consultar tercero. ");
                this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
                this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile());
                this.logAuditoriaDTO.setAuLogUsuario("NA");
                logService.AddLogAuditoria(logAuditoriaDTO);
                return null;
            }
    		
    	}catch (Exception e)
        {
    		log.error("Error: " + e.toString());
    		return null;
        }

    }

}
