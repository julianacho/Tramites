/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.ServiceImpl;

import co.gov.gobierno.DTO.LogAuditoriaDTO;
import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.DTO.RequestDTO;
import co.gov.gobierno.DTO.ThirdDTO;
import co.gov.gobierno.DTO.WebServiceDTO;
import co.gov.gobierno.Service.ExtinctionPHService;
import co.gov.gobierno.Service.LogAuditoriaService;
import co.gov.gobierno.Service.PHConsultInitService;
import co.gov.gobierno.Service.PHConsultService;
import co.gov.gobierno.Service.WebService;
import co.gov.gobierno.Util.Base64Handler;
import co.gov.gobierno.Util.PropertiesHandler;
import co.gov.gobierno.Util.XmlHandler;
import static co.gov.gobierno.Util.XmlHandler.convertStringToXmlDocument;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.primefaces.model.UploadedFile;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author DELL
 */
@Stateless
public class PHConsultInitServiceImpl implements PHConsultInitService
{
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(PHConsultInitServiceImpl.class);
    @EJB
    WebService ws;
    
    @EJB
    LogAuditoriaService logService;
    
    private LogAuditoriaDTO logAuditoriaDTO = new LogAuditoriaDTO(0, "NA", "IP", "SO", "NA", "PHConsultInitServiceImpl", "NA", "NA", "NA", null);


    @Override
    public WebServiceDTO FindPH(List<PropertieDTO> listPropertieDTOs, String nit, String localidad, String nombre)
    {
        String params = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n" +
                        "   <soapenv:Header/>\n" +
                        "   <soapenv:Body>\n" +
                        "      <tem:getEntitiesUsingSchema>\n" +
                        "         <!--Optional:-->\n" +
                        "         <tem:entitiesInfo>\n" +
                        "          <BizAgiWSParam><EntityData><EntityName>InformacionConsulta</EntityName><Filters><![CDATA[SMatriculainmobiliaria='" + nit + "' OR (CodigoLocalidad='"+ localidad +"' AND NombrePropiedad='" + nombre + "')]]></Filters></EntityData></BizAgiWSParam>\n" +
                        "         </tem:entitiesInfo>\n" +
                        "         <!--Optional:-->\n" +
                        "         <tem:schema>\n" +
                        "       \n" +
                        "<xs:schema attributeFormDefault=\"qualified\" elementFormDefault=\"qualified\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n" +
                        "  <xs:element name=\"InformacionConsulta\">\n" +
                        "    <xs:complexType>\n" +
                        "      <xs:sequence>\n" +
                        "        <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Solicitante\" type=\"xs:string\" />\n" +
                        "        <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"BUltimoregistro\" type=\"xs:string\" />"+
                        "        <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"UltimoCertificado\" type=\"xs:string\" />"+
                        "        <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Proceso\" type=\"xs:string\" />\n" +
                        "        <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"PropiedadHorizontal\">\n" +
                        "          <xs:complexType>\n" +
                        "            <xs:sequence>\n" +
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SMatriculainmobiliaria\" type=\"xs:string\" />\n" +
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SDireccion\" type=\"xs:string\" />\n" +
                        "            <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"IdTipodedirecion\">\n"+
                        "             <xs:complexType>\n"+
                        "               <xs:sequence>\n"+
                        "                 <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SCodigo\" type=\"xs:string\" />\n"+
                        "               </xs:sequence>\n"+
                        "               <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n"+
                        "             </xs:complexType>\n"+
                        "           </xs:element>\n"+
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"IdInfoComplementaria\">\n" +
                        "                <xs:complexType>\n" +
                        "                  <xs:sequence>\n" +
                        "                    <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"INoUnidadesPrivadas\" type=\"xs:integer\" />\n" +
                        "                    <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"INoAreasComunes\" type=\"xs:integer\" />\n" +
                        "                    <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"TipodeUnidadesPrivadas\">\n" +
                        "                      <xs:complexType>\n" +
                        "                        <xs:sequence>\n" +
                        "                          <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Codigo\" type=\"xs:string\" />\n" +
                        "                        </xs:sequence>\n" +
                        "                        <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n" +
                        "                      </xs:complexType>\n" +
                        "                    </xs:element>\n" +
                        "                    <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SNombrePersonaContacto\" type=\"xs:string\" />\n" +
                        "                    <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"STelContacto\" type=\"xs:string\" />\n" +
                        "                    <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"BPHtTenePlanEmergencias\" type=\"xs:boolean\" />\n" +
                        "                    <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"INoResidentes\" type=\"xs:integer\" />\n" +
                        "                    <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"TipodeAreasComunes\">\n" +
                        "                      <xs:complexType>\n" +
                        "                        <xs:sequence>\n" +
                        "                          <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Codigo\" type=\"xs:string\" />\n" +
                        "                        </xs:sequence>\n" +
                        "                        <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n" +
                        "                      </xs:complexType>\n" +
                        "                    </xs:element>\n" +
                        "                    <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SCorreoElectronicoContacto\" type=\"xs:string\" />\n" +
                        "                    <xs:element minOccurs=\"0\" maxOccurs=\"unbounded\" name=\"Areas\">\n" +
                        "                      <xs:complexType>\n" +
                        "                        <xs:sequence>\n" +
                        "                          <xs:element minOccurs=\"0\" maxOccurs=\"unbounded\" name=\"AreasdelaPropiedadHorizo\">\n" +
                        "                            <xs:complexType>\n" +
                        "                              <xs:sequence>\n" +
                        "                                <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"TipodeUnidadPrivada\">\n" +
                        "                                  <xs:complexType>\n" +
                        "                                    <xs:sequence>\n" +
                        "                                      <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Descripcion\" type=\"xs:string\" />\n" +
                        "                                      <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Codigo\" type=\"xs:string\" />\n" +
                        "                                    </xs:sequence>\n" +
                        "                                    <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n" +
                        "                                  </xs:complexType>\n" +
                        "                                </xs:element>\n" +
                        "                                <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"TipodeAreaComun\">\n" +
                        "                                  <xs:complexType>\n" +
                        "                                    <xs:sequence>\n" +
                        "                                      <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Descripcion\" type=\"xs:string\" />\n" +
                        "                                      <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Codigo\" type=\"xs:string\" />\n" +
                        "                                    </xs:sequence>\n" +
                        "                                    <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n" +
                        "                                  </xs:complexType>\n" +
                        "                                </xs:element>\n" +
                        "                                <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Cantidad\" type=\"xs:integer\" />\n" +
                        "                                <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Area\">\n" +
                        "                                  <xs:complexType>\n" +
                        "                                    <xs:sequence>\n" +
                        "                                      <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Descripcion\" type=\"xs:string\" />\n" +
                        "                                      <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Codigo\" type=\"xs:string\" />\n" +
                        "                                    </xs:sequence>\n" +
                        "                                    <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n" +
                        "                                  </xs:complexType>\n" +
                        "                                </xs:element>\n" +
                        "                              </xs:sequence>\n" +
                        "                            </xs:complexType>\n" +
                        "                          </xs:element>\n" +
                        "                        </xs:sequence>\n" +
                        "                      </xs:complexType>\n" +
                        "                    </xs:element>" +
                        "                  </xs:sequence>\n" +
                        "                  <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n" +
                        "                </xs:complexType>\n" +
                        "              </xs:element>\n" +
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SNumerolocalescomerciales\" type=\"xs:integer\" />\n" +
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SBarrio\" type=\"xs:string\" />\n" +
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"DFechaMatricula\" type=\"xs:dateTime\" />\n" +
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"NumeroNotaria\" type=\"xs:integer\" />\n" +
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"DFechaActaAsamblea\" type=\"xs:dateTime\" />\n" +
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"IdRegimen\">\n" +
                        "                <xs:complexType>\n" +
                        "                  <xs:sequence>\n" +
                        "                    <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SCodigo\" type=\"xs:string\" />\n" +
                        "                  </xs:sequence>\n" +
                        "                  <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n" +
                        "                </xs:complexType>\n" +
                        "              </xs:element>\n" +
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"NumerodeExpediente\" type=\"xs:string\" />\n" +
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SNumerodeetapas\" type=\"xs:integer\" />\n" +
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"DFechaEscrituraPublica\" type=\"xs:dateTime\" />\n" +
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"IdTipoPropiedad\">\n" +
                        "                <xs:complexType>\n" +
                        "                  <xs:sequence>\n" +
                        "                    <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SCodigo\" type=\"xs:string\" />\n" +
                        "                  </xs:sequence>\n" +
                        "                  <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n" +
                        "                </xs:complexType>\n" +
                        "              </xs:element>\n" +
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SNoEscrituraPublica\" type=\"xs:string\" />\n" +
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SNombrePropiedad\" type=\"xs:string\" />\n" +
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"IEstrato\" type=\"xs:integer\" />\n" +
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"IdLocalidad\">\n" +
                        "                <xs:complexType>\n" +
                        "                  <xs:sequence>\n" +
                        "                    <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Codigo\" type=\"xs:string\" />\n" +
                        "                  </xs:sequence>\n" +
                        "                  <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n" +
                        "                </xs:complexType>\n" +
                        "              </xs:element>\n" +
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SCorreoElectronico\" type=\"xs:string\" />\n" +
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"INoFolios\" type=\"xs:integer\" />\n" +
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"DFechaInscripcionPropiedad\" type=\"xs:dateTime\" />\n" +
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SNIT\" type=\"xs:string\" />\n" +
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"RUT\" type=\"xs:string\" />\n"+
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SNumerodeBloques\" type=\"xs:integer\" />\n" +
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"NoRadicadoCertificado\" type=\"xs:string\" />\n" +
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"unbounded\" name=\"XRepresentantesLegales\">\n" +
                        "                <xs:complexType>\n" +
                        "                  <xs:sequence>\n" +
                        "                    <xs:element minOccurs=\"0\" maxOccurs=\"unbounded\" name=\"M_PH_RepresentanteLegal\">\n" +
                        "                      <xs:complexType>\n" +
                        "                        <xs:sequence>\n" +
                        "                          <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"idTipodeIdentificacionRL\">\n" +
                        "                            <xs:complexType>\n" +
                        "                              <xs:sequence>\n" +
                        "                                <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Codigo\" type=\"xs:string\" />\n" +
                        "                              </xs:sequence>\n" +
                        "                              <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n" +
                        "                            </xs:complexType>\n" +
                        "                          </xs:element>\n" +
                        "                          <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"idGenero\">\n" +
                        "                            <xs:complexType>\n" +
                        "                              <xs:sequence>\n" +
                        "                                <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SCodigo\" type=\"xs:string\" />\n" +
                        "                              </xs:sequence>\n" +
                        "                              <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n" +
                        "                            </xs:complexType>\n" +
                        "                          </xs:element>\n" +
                        "                          <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"IdTipoPersona\">\n" +
                        "                            <xs:complexType>\n" +
                        "                              <xs:sequence>\n" +
                        "                                <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SCodigo\" type=\"xs:string\" />\n" +
                        "                              </xs:sequence>\n" +
                        "                              <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n" +
                        "                            </xs:complexType>\n" +
                        "                          </xs:element>\n" +
                        "                          <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Activo\" type=\"xs:boolean\" />\n" +
                        "                          <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"EstadoCivil\">\n" +
                        "                            <xs:complexType>\n" +
                        "                              <xs:sequence>\n" +
                        "                                <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SCodigo\" type=\"xs:string\" />\n" +
                        "                              </xs:sequence>\n" +
                        "                              <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n" +
                        "                            </xs:complexType>\n" +
                        "                          </xs:element>\n" +
                        "                          <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SBarrio\" type=\"xs:string\" />\n" +
                        "                          <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"DFechaInicio\" type=\"xs:dateTime\" />\n" +
                        "                          <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"NumerodeIdentificacionRL\" type=\"xs:string\" />\n" +
                        "                          <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"DFechaActaAsamblea\" type=\"xs:dateTime\" />\n" +
                        "                          <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SNombreCompleto\" type=\"xs:string\" />\n" +
                        "                          <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"IdFormacionAcademica\">\n" +
                        "                            <xs:complexType>\n" +
                        "                              <xs:sequence>\n" +
                        "                                <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SCodigo\" type=\"xs:string\" />\n" +
                        "                              </xs:sequence>\n" +
                        "                              <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n" +
                        "                            </xs:complexType>\n" +
                        "                          </xs:element>\n" +
                        "                          <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SPrimerApellido\" type=\"xs:string\" />\n" +
                        "                          <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"DFechaFin\" type=\"xs:dateTime\" />\n" +
                        "                          <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"IdTipoIdentificacion\">\n" +
                        "                            <xs:complexType>\n" +
                        "                              <xs:sequence>\n" +
                        "                                <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Codigo\" type=\"xs:string\" />\n" +
                        "                              </xs:sequence>\n" +
                        "                              <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n" +
                        "                            </xs:complexType>\n" +
                        "                          </xs:element>\n" +
                        "                           <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"IdTipodedirecionRL\">\n"+
                        "                               <xs:complexType>\n"+
                        "                                   <xs:sequence>\n"+
                        "                                       <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SCodigo\" type=\"xs:string\" />\n"+
                        "                                   </xs:sequence>\n"+
                        "                               <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n"+
                        "                               </xs:complexType>\n"+
                        "                           </xs:element>\n"+
                        "                          <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SDireccion\" type=\"xs:string\" />\n" +
                        "                          <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"IdLocalidad\">\n" +
                        "                            <xs:complexType>\n" +
                        "                              <xs:sequence>\n" +
                        "                                <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Codigo\" type=\"xs:string\" />\n" +
                        "                              </xs:sequence>\n" +
                        "                              <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n" +
                        "                            </xs:complexType>\n" +
                        "                          </xs:element>\n" +
                        "                          <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SSegundoApellido\" type=\"xs:string\" />\n" +
                        "                          <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"IdTipoRepresentacion\">\n" +
                        "                            <xs:complexType>\n" +
                        "                              <xs:sequence>\n" +
                        "                                <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SCodigo\" type=\"xs:string\" />\n" +
                        "                              </xs:sequence>\n" +
                        "                              <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n" +
                        "                            </xs:complexType>\n" +
                        "                          </xs:element>\n" +
                        "                          <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SPrimerNombre\" type=\"xs:string\" />\n" +
                        "                          <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SSegundoNombre\" type=\"xs:string\" />\n" +
                        "                          <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"NumerodeIdentificacion\" type=\"xs:string\" />\n" +
                        "                          <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SCorreoRepresentanteLegal\" type=\"xs:string\" />\n" +
                        "                          <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"STelefonoRepLegal\" type=\"xs:string\" />\n" +
                        "                          <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SNombreRepresentante\" type=\"xs:string\" />\n" +
                        "                          <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"DFechaNacimiento\" type=\"xs:dateTime\" />\n" +
                        "                          <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SNumActadeAsamblea\" type=\"xs:string\" />\n" +
                        "                        </xs:sequence>\n" +
                        "                      </xs:complexType>\n" +
                        "                    </xs:element>\n" +
                        "                  </xs:sequence>\n" +
                        "                </xs:complexType>\n" +
                        "              </xs:element>\n" +
                        "            </xs:sequence>\n" +
                        "            <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n" +
                        "          </xs:complexType>\n" +
                        "        </xs:element>\n" +
                        "        <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"NumeroRadicado\" type=\"xs:string\" />\n" +
                        "        <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"NumeroRadSolicitud\" type=\"xs:string\" />\n"+
                        "<xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"ActuallizacionRepresentant\">\n"+
                        "          <xs:complexType>\n"+
                        "            <xs:sequence>\n"+
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SNumActadeAsamblea\" type=\"xs:string\" />\n"+
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Activo\" type=\"xs:boolean\" />\n"+
                        "               <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"IdTipodedirecionRL\">\n"+
                        "                     <xs:complexType>\n"+
                        "                       <xs:sequence>\n"+
                        "                           <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SCodigo\" type=\"xs:string\" />\n"+
                        "                       </xs:sequence>\n"+
                        "                       <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n"+
                        "                    </xs:complexType>\n"+
                        "              </xs:element>\n"+
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"IdTipoRepresentacion\">\n"+
                        "                <xs:complexType>\n"+
                        "                  <xs:sequence>\n"+
                        "                    <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SCodigo\" type=\"xs:string\" />\n"+
                        "                  </xs:sequence>\n"+
                        "                  <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n"+
                        "                </xs:complexType>\n"+
                        "              </xs:element>\n"+
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"idTipodeIdentificacionRL\">\n"+
                        "                <xs:complexType>\n"+
                        "                  <xs:sequence>\n"+
                        "                    <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Codigo\" type=\"xs:string\" />\n"+
                        "                  </xs:sequence>\n"+
                        "                  <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n"+
                        "                </xs:complexType>\n"+
                        "              </xs:element>\n"+
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"idGenero\">\n"+
                        "                <xs:complexType>\n"+
                        "                  <xs:sequence>\n"+
                        "                    <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SCodigo\" type=\"xs:string\" />\n"+
                        "                  </xs:sequence>\n"+
                        "                  <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n"+
                        "                </xs:complexType>\n"+
                        "              </xs:element>\n"+
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SCorreoRepresentanteLegal\" type=\"xs:string\" />\n"+
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SPrimerNombre\" type=\"xs:string\" />\n"+
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"DFechaFin\" type=\"xs:dateTime\" />\n"+
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"STelefonoRepLegal\" type=\"xs:string\" />\n"+
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"DFechaActaAsamblea\" type=\"xs:dateTime\" />\n"+
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"IdTipoIdentificacion\">\n"+
                        "                <xs:complexType>\n"+
                        "                  <xs:sequence>\n"+
                        "                    <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Codigo\" type=\"xs:string\" />\n"+
                        "                  </xs:sequence>\n"+
                        "                  <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n"+
                        "                </xs:complexType>\n"+
                        "              </xs:element>\n"+
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SDireccion\" type=\"xs:string\" />\n"+
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"NumerodeIdentificacion\" type=\"xs:string\" />\n"+
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"IdTipoPersona\">\n"+
                        "                <xs:complexType>\n"+
                        "                  <xs:sequence>\n"+
                        "                    <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SCodigo\" type=\"xs:string\" />\n"+
                        "                  </xs:sequence>\n"+
                        "                  <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n"+
                        "                </xs:complexType>\n"+
                        "              </xs:element>\n"+
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"EstadoCivil\">\n"+
                        "                <xs:complexType>\n"+
                        "                  <xs:sequence>\n"+
                        "                    <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SCodigo\" type=\"xs:string\" />\n"+
                        "                  </xs:sequence>\n"+
                        "                  <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n"+
                        "                </xs:complexType>\n"+
                        "              </xs:element>\n"+
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SNombreRepresentante\" type=\"xs:string\" />\n"+
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"IdLocalidad\">\n"+
                        "                <xs:complexType>\n"+
                        "                  <xs:sequence>\n"+
                        "                    <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Codigo\" type=\"xs:string\" />\n"+
                        "                  </xs:sequence>\n"+
                        "                  <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n"+
                        "                </xs:complexType>\n"+
                        "              </xs:element>\n"+
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"DFechaInicio\" type=\"xs:dateTime\" />\n"+
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SNombreCompleto\" type=\"xs:string\" />\n"+
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SBarrio\" type=\"xs:string\" />\n"+
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SPrimerApellido\" type=\"xs:string\" />\n"+
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"DFechaNacimiento\" type=\"xs:dateTime\" />\n"+
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SSegundoNombre\" type=\"xs:string\" />\n"+
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"NumerodeIdentificacionRL\" type=\"xs:string\" />\n"+
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"SSegundoApellido\" type=\"xs:string\" />\n"+
                        "            </xs:sequence>\n"+
                        "            <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n"+
                        "          </xs:complexType>\n"+
                                "</xs:element>\n"+
                        "        <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Activo\" type=\"xs:boolean\" />\n" +
                        "<xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"TipoTramite\">\n" +
                            "<xs:complexType>\n" +
                              "<xs:sequence>\n" +
                                "<xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Codigo\" type=\"xs:string\" />\n" +
                              "</xs:sequence>\n" +
                              "<xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n" +
                            "</xs:complexType>\n" +
                        "</xs:element>\n" +
                        "        <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Estado\">\n" +
                        "          <xs:complexType>\n" +
                        "            <xs:sequence>\n" +
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Codigo\" type=\"xs:string\" />\n" +
                        "            </xs:sequence>\n" +
                        "            <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n" +
                        "          </xs:complexType>\n" +
                        "        </xs:element>\n" +
                        "        <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"FechaLimiteSubsanacion\" type=\"xs:dateTime\" />\n" +
                        "        <xs:element minOccurs=\"0\" maxOccurs=\"unbounded\" name=\"Documentos\">\n" +
                        "          <xs:complexType>\n" +
                        "            <xs:sequence>\n" +
                        "              <xs:element minOccurs=\"0\" maxOccurs=\"unbounded\" name=\"M_PH_Documentos\">\n" +
                        "                <xs:complexType>\n" +
                        "                  <xs:sequence>\n" +
                        "                    <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"IdTipoDocumento\">\n" +
                        "                      <xs:complexType>\n" +
                        "                        <xs:sequence>\n" +
                        "                          <xs:element minOccurs=\"0\" maxOccurs=\"1\" name=\"Codigo\" type=\"xs:string\" />\n" +
                        "                        </xs:sequence>\n" +
                        "                        <xs:attribute form=\"unqualified\" name=\"entityName\" type=\"xs:string\" />\n" +
                        "                      </xs:complexType>\n" +
                        "                    </xs:element>\n" +
                        "                  </xs:sequence>\n" +
                        "                </xs:complexType>\n" +
                        "              </xs:element>\n" +
                        "            </xs:sequence>\n" +
                        "          </xs:complexType>\n" +
                        "        </xs:element>\n" +
                        "      </xs:sequence>\n" +
                        "    </xs:complexType>\n" +
                        "  </xs:element>\n" +
                        "</xs:schema>\n" +
                        "         </tem:schema>\n" +
                        "      </tem:getEntitiesUsingSchema>\n" +
                        "   </soapenv:Body>\n" +
                        "</soapenv:Envelope>";
        WebServiceDTO objWebServiceDTO = new WebServiceDTO();
        objWebServiceDTO.setUrl(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "urlBIZAGY") + "entitymanagersoa.asmx");
        objWebServiceDTO.setMethod("POST");
        objWebServiceDTO.setSoapAction("http://tempuri.org/getEntitiesUsingSchema");
        objWebServiceDTO.getHeader().put("Content-Type", "text/xml;charset=utf-8");
        objWebServiceDTO.setParams(params);
        try {  	        	     
        	
        	log.info("url antes : "+objWebServiceDTO.getUrl());
            log.info("metodo  antes: "+objWebServiceDTO.getMethod() );
            log.info("param xml antes  : "+objWebServiceDTO.getParams() );
            
            objWebServiceDTO = ws.SoapWithParam(objWebServiceDTO);
            
            
        	log.info("url : "+objWebServiceDTO.getUrl());
            log.info("metodo : "+objWebServiceDTO.getMethod() );
            log.info("param xml : "+objWebServiceDTO.getParams() );
        

            String responseMensaje = "";
            if (objWebServiceDTO.getXmlFile().contains("errorCode") || objWebServiceDTO.getXmlFile().contains("errorCode")) {
            	log.info("Bizagi TagError");
            	String response = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "errorCode");
            	if(response != null && !response.isEmpty()) {
            		responseMensaje = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "errorMessage");
            		objWebServiceDTO.setRespuesta(false);
            	}
            }
            log.info("Se consumió el servicio web de Consultar PH de BIZAGY correctamente.");
            this.logAuditoriaDTO.setAuMensajeError("WebServiceDTO " + "Se consumió el servicio web de Consultar PH de BIZAGY correctamente. " + responseMensaje);
            this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
            this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
            this.logAuditoriaDTO.setAuLogUsuario(nit);
            this.logAuditoriaDTO.setAuAplicacion("BIZAGI");
            logService.AddLogAuditoria(logAuditoriaDTO);
        }
        catch (Exception ex) 
        {
            log.info("Error al consultar las PH BIZAGY: " + ex.toString());
            return null;
        }
        if(objWebServiceDTO.isRespuesta())
        {
            if(!objWebServiceDTO.getXmlFile().isEmpty())
            {
                return objWebServiceDTO;
            }
            else
            {
                return null;
            }
        }
        else
        {
            return null;
        }
    }

    @Override
    public Map<String,String> readXmlConsultPH(String xml, String tipoConsulta)
    {
        String stateFinal= "";
        String expediente = "";
        String localidad = "";
        String representante = "";
        String proceso = "";
        String stateUltimo="";
        String processUltimo="";
        String certificadoRadicado="";
        try
        {
            log.info("Estoy leyendo las consultas de BIZAGI. de Información");
            List<RequestDTO> listRequestDTOs = new ArrayList<>();
            Document document;
            try 
            {
                log.info("Transformando la cadena en documento XML.");
                document = convertStringToXmlDocument(xml);
            }
            catch (Exception ex) 
            {
                log.info("Error al convertir la cadena en un documento Xml.");
                return null;
            }
            NodeList nodeLst = document.getElementsByTagName("InformacionConsulta");
            int count=0;
            Map<String,String> infoPH = new HashMap<String, String>();
            Map<String,String> infoRd = new HashMap<String, String>();
            for(int i=0; i<nodeLst.getLength(); i++) 
            {
                
                Element err = (Element) nodeLst.item(i);
                String state = err.getElementsByTagName("Estado").item(0).getTextContent();
                String ultimo = err.getElementsByTagName("BUltimoregistro").item(0).getTextContent();
                String ultimoCertificado = err.getElementsByTagName("UltimoCertificado").item(0).getTextContent();
                String process = err.getElementsByTagName("TipoTramite").item(0).getTextContent();
                
                 //Validación para identificar procesos para descargar el ultimo certificado
                if(tipoConsulta.equals("Certificate"))
                {
                    if((process.equals("4") && (state.equals("A")) && ultimoCertificado.equals("True") ))
                        {
                            Element err4 = (Element) nodeLst.item(i);
                            certificadoRadicado = err4.getElementsByTagName("NoRadicadoCertificado").item(0).getTextContent();
                            infoRd.put("certificadoRadicado",certificadoRadicado);
                            return infoRd;
                        }
                }
                
                if((process.equals("1") && (state.equals("A") || state.equals("T") || state.equals("S") || state.equals("C"))) || (process.equals("2") && (state.equals("S") || state.equals("A"))))
                {
                    
                    infoPH.clear();
                    NodeList nodeLst2 = document.getElementsByTagName("PropiedadHorizontal");
                    Element err2 = (Element) nodeLst2.item(0);
                    String namePH = err2.getElementsByTagName("SNombrePropiedad").item(0).getTextContent();
                    String addressPH = err2.getElementsByTagName("SDireccion").item(0).getTextContent();
                    
                    expediente = err2.getElementsByTagName("NumerodeExpediente").item(0).getTextContent();
                    String legalAgentPH = "";
                    String dateInitPH = "";
                    String dateFinalPH = "";
                    String radNumber = err2.getElementsByTagName("NoRadicadoCertificado").item(0).getTextContent();
                    NodeList nodeLst3 = document.getElementsByTagName("M_PH_RepresentanteLegal");
                    
                    for(int j=0; j<nodeLst3.getLength(); j++)
                    {
                        Element err3 = (Element) nodeLst3.item(j);
                        String t = err3.getElementsByTagName("IdTipoPersona").item(0).getTextContent();
                        if(err3.getElementsByTagName("Activo").item(0).getTextContent().equals("True") || state.equals("C"))
                        {
                            stateFinal = state;
                            if(err3.getElementsByTagName("IdTipoPersona").item(0).getTextContent().equals("PN"))
                            {
                                String fName = err3.getElementsByTagName("SPrimerNombre").item(0).getTextContent();
                                String sName = err3.getElementsByTagName("SSegundoNombre").item(0).getTextContent();
                                String fLastName = err3.getElementsByTagName("SPrimerApellido").item(0).getTextContent();
                                String sLastName = err3.getElementsByTagName("SSegundoApellido").item(0).getTextContent();
                                legalAgentPH = fName + " " + sName + " " + fLastName + " " + sLastName;
                            }
                            else if(err3.getElementsByTagName("IdTipoPersona").item(0).getTextContent().equals("PJ"))
                            {
                                legalAgentPH = err3.getElementsByTagName("SNombreCompleto").item(0).getTextContent();
                            }
                            legalAgentPH = err3.getElementsByTagName("SNombreCompleto").item(0).getTextContent();
                            dateInitPH = err3.getElementsByTagName("DFechaInicio").item(0).getTextContent();
                            dateFinalPH = err3.getElementsByTagName("DFechaFin").item(0).getTextContent();
                        }
                    }
                    
                    representante = state;
                    proceso = process;
                    if (ultimo.equals("True"))
                    {
                        stateUltimo = representante;
                        processUltimo = proceso;
                    }
                    infoPH.put("namePH",namePH);
                    infoPH.put("addressPH",addressPH);
                    infoPH.put("legalAgentPH",legalAgentPH);
                    infoPH.put("dateInitPH",dateInitPH);
                    infoPH.put("dateFinalPH",dateFinalPH);
                }
            }
            for (int i = 0; i < nodeLst.getLength(); i++)
            {
                Element err = (Element) nodeLst.item(i);
                String activo = err.getElementsByTagName("Activo").item(0).getTextContent();
                String tTramite = err.getElementsByTagName("TipoTramite").item(0).getTextContent();
                String state = err.getElementsByTagName("Estado").item(0).getTextContent();
                localidad = err.getElementsByTagName("IdLocalidad").item(0).getTextContent();
                if (activo.equals("True") || (((i+1) == nodeLst.getLength()) && ((tTramite.equals("1")||tTramite.equals("2")) && state.equals("A"))))
                {
                    stateFinal = err.getElementsByTagName("Estado").item(0).getTextContent();
                    break;
                }
                if (activo.equals("False") && state.equals("A") && tTramite.equals("3"))
                {
                    stateFinal = "F";
                    break;
                }
            }
            if (representante.equals("C") && !proceso.equals("1"))
            {
                stateFinal = "C";
            }
            if (representante.equals("C") && proceso.equals("1"))
            {
                stateFinal = "CL";
            }
            infoPH.put("stateFinal",stateFinal);
            infoPH.put("expediente",expediente);
            infoPH.put("localidad",localidad);
            if (stateUltimo.equals("S") && processUltimo.equals("2"))
            {
                String SNumActadeAsamblea = "";
                String SCorreoRepresentanteLegal = "";
                String SPrimerNombre = "";
                String DFechaFin = "";
                String STelefonoRepLegal = "";
                String DFechaActaAsamblea = "";
                String SDireccion = "";
                String NumerodeIdentificacion = "";
                String SNombreRepresentante = "";
                String DFechaInicio = "";
                String SNombreCompleto = "";
                String SBarrio = "";
                String SPrimerApellido = "";
                String SSegundoNombre = "";
                String NumerodeIdentificacionRL = "";
                String SSegundoApellido = "";
                String IdTipoRepresentacion = "";
                String idTipodeIdentificacionRL = "";
                String idGenero = "";
                String IdTipoIdentificacion = "";
                String IdTipoPersona = "";
                String EstadoCivil = "";
                String IdLocalidad = "";
                String IdTipodedirecion = "";
                String IdTipodedirecionRL = "";
                
                for (int i = 0; i < nodeLst.getLength(); i++)
                {
                    Element errr = (Element) nodeLst.item(i);
                    String activo = errr.getElementsByTagName("Activo").item(0).getTextContent();
                    String ultimo2 = errr.getElementsByTagName("BUltimoregistro").item(0).getTextContent();
                    String tTramite = errr.getElementsByTagName("TipoTramite").item(0).getTextContent();
                    String state = errr.getElementsByTagName("Estado").item(0).getTextContent();
                    localidad = errr.getElementsByTagName("IdLocalidad").item(0).getTextContent();
                    
                    if (activo.equals("True") && tTramite.equals("2") && ultimo2.equals("True"))
                    {
                        NodeList nodeLst8 = document.getElementsByTagName("ActuallizacionRepresentant");
                        Element err8 = (Element) nodeLst8.item(i);
                        SNumActadeAsamblea = err8.getElementsByTagName("SNumActadeAsamblea").item(0).getTextContent();
                        SCorreoRepresentanteLegal = err8.getElementsByTagName("SCorreoRepresentanteLegal").item(0).getTextContent();
                        SPrimerNombre = err8.getElementsByTagName("SPrimerNombre").item(0).getTextContent();
                        DFechaFin = err8.getElementsByTagName("DFechaFin").item(0).getTextContent();
                        STelefonoRepLegal = err8.getElementsByTagName("STelefonoRepLegal").item(0).getTextContent();
                        DFechaActaAsamblea = err8.getElementsByTagName("DFechaActaAsamblea").item(0).getTextContent();
                        SDireccion = err8.getElementsByTagName("SDireccion").item(0).getTextContent();
                        NumerodeIdentificacion = err8.getElementsByTagName("NumerodeIdentificacion").item(0).getTextContent();
                        SNombreRepresentante = err8.getElementsByTagName("SNombreRepresentante").item(0).getTextContent();
                        DFechaInicio = err8.getElementsByTagName("DFechaInicio").item(0).getTextContent();
                        SNombreCompleto = err8.getElementsByTagName("SNombreCompleto").item(0).getTextContent();
                        SBarrio = err8.getElementsByTagName("SBarrio").item(0).getTextContent();
                        SPrimerApellido = err8.getElementsByTagName("SPrimerApellido").item(0).getTextContent();
                        SSegundoNombre = err8.getElementsByTagName("SSegundoNombre").item(0).getTextContent();
                        NumerodeIdentificacionRL = err8.getElementsByTagName("NumerodeIdentificacionRL").item(0).getTextContent();
                        SSegundoApellido = err8.getElementsByTagName("SSegundoApellido").item(0).getTextContent();
                        IdTipoRepresentacion = err8.getElementsByTagName("IdTipoRepresentacion").item(0).getTextContent();
                        idTipodeIdentificacionRL = err8.getElementsByTagName("idTipodeIdentificacionRL").item(0).getTextContent();
                        IdTipodedirecionRL = err8.getElementsByTagName("IdTipodedirecionRL").item(0).getTextContent();
                        idGenero = err8.getElementsByTagName("idGenero").item(0).getTextContent();
                        IdTipoIdentificacion = err8.getElementsByTagName("IdTipoIdentificacion").item(0).getTextContent();
                        IdTipoPersona = err8.getElementsByTagName("IdTipoPersona").item(0).getTextContent();
                        EstadoCivil = err8.getElementsByTagName("EstadoCivil").item(0).getTextContent();
                        IdLocalidad = err8.getElementsByTagName("IdLocalidad").item(0).getTextContent();
                    }
                }
                infoPH.put("SNumActadeAsamblea",SNumActadeAsamblea);
                infoPH.put("SCorreoRepresentanteLegal",SCorreoRepresentanteLegal);
                infoPH.put("SPrimerNombre",SPrimerNombre);
                infoPH.put("DFechaFin",DFechaFin);
                infoPH.put("STelefonoRepLegal",STelefonoRepLegal);
                infoPH.put("DFechaActaAsamblea",DFechaActaAsamblea);
                infoPH.put("SDireccion",SDireccion);
                infoPH.put("IdTipodedirecion", IdTipodedirecion);
                infoPH.put("NumerodeIdentificacion",NumerodeIdentificacion);
                infoPH.put("SNombreRepresentante",SNombreRepresentante);
                infoPH.put("DFechaInicio",DFechaInicio);
                infoPH.put("SNombreCompleto",SNombreCompleto);
                infoPH.put("SBarrio",SBarrio);
                infoPH.put("SPrimerApellido",SPrimerApellido);
                infoPH.put("SSegundoNombre",SSegundoNombre);
                infoPH.put("NumerodeIdentificacionRL",NumerodeIdentificacionRL);
                infoPH.put("SSegundoApellido",SSegundoApellido);
                infoPH.put("IdTipoRepresentacion",IdTipoRepresentacion);
                infoPH.put("idTipodeIdentificacionRL",idTipodeIdentificacionRL);
                infoPH.put("IdTipodedirecionRL", IdTipodedirecionRL);
                infoPH.put("idGenero",idGenero);
                infoPH.put("IdTipoIdentificacion",IdTipoIdentificacion);
                infoPH.put("IdTipoPersona",IdTipoPersona);
                infoPH.put("EstadoCivil",EstadoCivil);
                infoPH.put("IdLocalidad",IdLocalidad);
                return infoPH;
            } else {
                
                if(infoPH.size() == 8)
                {
                    return infoPH;
                }
                else
                {
                    log.error("El Xml no contenia los 5 datos necesarios.");
                    return null;
                }
            }
        }
        catch(DOMException e)
        {
            log.error("Error al leer el xml de la consulta de PH: " + e);
            return null;
        }
        catch(Exception e)
        {
            log.error("Error al leer el xml de la consulta de PH: " + e);
            return null;
        }
    }

    @Override
    public List<RequestDTO> getDataTablePH(String xml, List<RequestDTO> objRequestDTOs, String userlog)
    {
        boolean subsana = true;
        String parametro = "", caso = "";
        try
        {
            log.info("Estoy leyendo las consultas de BIZAGI. de Tabla");
            Document document;
            try 
            {
                log.info("Transformando la cadena en documento XML.");
                document = convertStringToXmlDocument(xml);
            }
            catch (Exception ex) 
            {
                log.info("Error al convertir la cadena en un documento Xml.");
                return null;
            }
            NodeList nodeLst = document.getElementsByTagName("InformacionConsulta");
            String[] status = new String[nodeLst.getLength()];
            for (int i = 0; i < nodeLst.getLength(); i++)
            {
                Element err = (Element) nodeLst.item(i);
                status[i] = err.getElementsByTagName("Estado").item(0).getTextContent();
            }
            for (int i = 0; i < status.length; i++)
            {
                String aux = status[i];
                for (int j = i+1; j < status.length; j++)
                {
                    if (aux.equals(status[j]))
                    {
                        status[i] = "";
                    }
                }
            }
            int count=0;
            for(int i=0; i < nodeLst.getLength(); i++) 
            {
                Element err = (Element) nodeLst.item(i);
                subsana = err.getElementsByTagName("Solicitante").item(0).getTextContent().equals(userlog);
                String process = err.getElementsByTagName("Proceso").item(0).getTextContent();
                String state = err.getElementsByTagName("Estado").item(0).getTextContent();
                String tipoTramite = err.getElementsByTagName("TipoTramite").item(0).getTextContent();
                String ultimo2 = err.getElementsByTagName("BUltimoregistro").item(0).getTextContent();
                NodeList nodeLst2 = document.getElementsByTagName("PropiedadHorizontal");
                Element err2 = (Element) nodeLst2.item(0);
                String radNumber = err2.getElementsByTagName("NoRadicadoCertificado").item(0).getTextContent();
                
                if (tipoTramite.equals("1") || tipoTramite.equals("2") || tipoTramite.equals("3"))//Valida que solo se capturen para llenar la tabla los tramites 1,2,3
                    {
                    if (tipoTramite.equals("3"))
                    {
                        caso = err.getElementsByTagName("NumeroRadicado").item(0).getTextContent();
                        NodeList nodeLst3 = document.getElementsByTagName("M_PH_Documentos");
                        for (int j = 0; j < nodeLst3.getLength(); j++)
                        {
                            Element err3 = (Element) nodeLst3.item(j);
                            parametro = parametro + "-" + err3.getElementsByTagName("IdTipoDocumento").item(0).getTextContent();
                        }
                    }
                    if ((tipoTramite.equals("1") || tipoTramite.equals("2")) && state.equals("S"))
                    {
                        caso = err.getElementsByTagName("NumeroRadicado").item(0).getTextContent();
                        NodeList nodeLst3 = document.getElementsByTagName("M_PH_Documentos");
                        for (int j = 0; j < nodeLst3.getLength(); j++)
                        {
                            Element err3 = (Element) nodeLst3.item(j);
                            parametro = parametro + "-" + err3.getElementsByTagName("IdTipoDocumento").item(0).getTextContent();
                        }
                    }
                    String radNumberSolicitud = err.getElementsByTagName("NumeroRadSolicitud").item(0).getTextContent();
                    String ultimo = "";
                    if (ultimo2.equals("True") && state.equals("A"))
                    {
                        ultimo = "S";
                    }
                    RequestDTO aux = new RequestDTO(radNumber, process, state,tipoTramite,parametro,caso,subsana,radNumberSolicitud,ultimo);
                    objRequestDTOs.add(aux);
                }
            }
            
            if(objRequestDTOs.size() > 0)
            {
                return objRequestDTOs;
            }
            else
            {
                log.error("El Xml no contenia los datos necesarios.");
                return null;
            }
        }
        catch(DOMException e)
        {
            log.error("Error al leer el xml de la consulta de PH: " + e);
            return null;
        }
        catch(Exception e)
        {
            log.error("Error al leer el xml de la consulta de PH: " + e);
            return null;
        }
    }
    
    //Descarga de certificado actual
    @Override 
    public WebServiceDTO createCurrentCertificate(String realStateRegistration,String radNumber,ThirdDTO objThirdDTO, List<PropertieDTO> listPropertieDTOs)
    {
        Date now = new Date();
        SimpleDateFormat formatDay = new SimpleDateFormat("yyyy-MM-dd");
        String day = formatDay.format(now);
        Date dateNow = new Date();
        String params = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n" +
                        "	<soapenv:Header/>\n" +
                        "	<soapenv:Body>\n" +
                        "		<tem:createCases>\n" +
                        "			<tem:casesInfo>\n" +
                        "				<BizAgiWSParam>\n" +
                        "					<domain>domain</domain>\n" +
                        "					<userName>admon</userName>\n" +
                        "					<Cases>\n" +
                        "						<Case>\n" +
                        "							<Process>SolicitudCertificadoPropie</Process>\n" +
                        "							<Entities>\n" +
                        "                                                        <SolicitudCertificadoProp>\n"+ 
 			"                                                           <MatriculaaConsultar>"+realStateRegistration+"</MatriculaaConsultar>\n" +
			"                                                           <DatosGenerales>\n"+
 			"                                                           	<SNumeroRadicado>"+ radNumber +"</SNumeroRadicado>\n" + 
 			"                                                       	<DFechaRadicado>"+day+"</DFechaRadicado>\n"+
 			"                                                               <IdLocalidad businessKey=\"Codigo='"+objThirdDTO.getLocation()+"'\"/>\n" +
                        " 								<IdTipoTramite>\n" +
                        " 									<Codigo>4</Codigo>\n" +
                        "								</IdTipoTramite>\n" +
 			"                                                               <SCorreoSolicitante>"+objThirdDTO.getEmail()+"</SCorreoSolicitante>\n" +
                        "                                                        <Solicitante>"+ objThirdDTO.getUser()+"</Solicitante>\n"+
                        "                                                           </DatosGenerales>\n"+
 			"                                                        </SolicitudCertificadoProp>\n"+ 
                        "							</Entities>\n" +
                        "						</Case>\n" +
                        "					</Cases>\n" +
                        "				</BizAgiWSParam>\n" +
                        "			</tem:casesInfo>\n" +
                        "		</tem:createCases>\n" +
                        "	</soapenv:Body>\n" +
                        "</soapenv:Envelope>";
        
        WebServiceDTO objWebServiceDTO = new WebServiceDTO();
        objWebServiceDTO.setParams(params);
        objWebServiceDTO.setUrl(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "urlBIZAGY") + "workflowenginesoa.asmx");
        objWebServiceDTO.setMethod("POST");
        objWebServiceDTO.setSoapAction("http://tempuri.org/createCases");
        objWebServiceDTO.getHeader().put("Content-Type", "text/xml;charset=utf-8");
        try 
        {
            log.info("Voy a consumir el servicio web de solicitar certificado de BIZAGY.");
            objWebServiceDTO = ws.SoapWithParam(objWebServiceDTO);
            String responseMensaje = "";
            if (objWebServiceDTO.getXmlFile().contains("errorCode") || objWebServiceDTO.getXmlFile().contains("errorCode")) {
            	log.info("Bizagi TagError");
            	String xmlFileTemp = objWebServiceDTO.getXmlFile().replace("&gt;", ">").replace("&lt;", "<").replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
            	String response = XmlHandler.ReadXmlFileGetTag(xmlFileTemp, "errorCode");
            	if(response != null && !response.isEmpty()) {
            		responseMensaje = XmlHandler.ReadXmlFileGetTag(xmlFileTemp, "errorMessage");
            		objWebServiceDTO.setRespuesta(false);
            	}
            }
            log.info("Se consumió el servicio web de solicitar certificado de BIZAGY.");
            log.info("Se consumió el servicio web de Consultar PH de BIZAGY correctamente.");
            this.logAuditoriaDTO.setAuMensajeError("createCurrentCertificate " + "Se consumió el servicio web de Consultar PH de BIZAGY correctamente. " + responseMensaje);
            this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
            this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
            this.logAuditoriaDTO.setAuLogUsuario(objThirdDTO.getIdNumber());
            this.logAuditoriaDTO.setAuAplicacion("BIZAGI");
            logService.AddLogAuditoria(logAuditoriaDTO);
        }
        catch (Exception ex) 
        {
            log.info("Error al solicitar certificado de BIZAGY: " + ex.toString());
            return null;
        }
        if(objWebServiceDTO.isRespuesta())
        {
            if(!objWebServiceDTO.getXmlFile().isEmpty())
            {
                return objWebServiceDTO;
            }
            else
            {
                return null;
            }
        }
        else
        {
            return null;
        }
    }
    
    
    @Override
    public WebServiceDTO DownloadORFEO(ThirdDTO objThirdDTO, List<PropertieDTO> listPropertieDTOs, String radicado)
    {
        String tipoEndpointOrfeo = (PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "TipoEndpointOrfeo", "5") == null ? "2" : PropertiesHandler.PropertieValueOfKeyAndIdServiceFromDB(listPropertieDTOs, "TipoEndpointOrfeo", "5"));  	
        String tipoEndpointOrfeo1 = "";
        String tipoEndpointOrfeo2 = "";
        String tipoEndpointOrfeo3 = "";
        if (tipoEndpointOrfeo.equals("2")) {
        	tipoEndpointOrfeo = "      <ser:consultaRadicado soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n";
        	tipoEndpointOrfeo1 = "      </ser:consultaRadicado>\n";
        	tipoEndpointOrfeo2 = "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"" + PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "urlORFEOCorto") + "/\">";
        	tipoEndpointOrfeo3 = "         <numeroRadicado xsi:type=\"xsd:string\">" + radicado + "</numeroRadicado>\n";
        }else {
        	tipoEndpointOrfeo = "      <urn:consultaRadicado soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n";
        	tipoEndpointOrfeo1 = "      </urn:consultaRadicado>\n";
        	tipoEndpointOrfeo2 = "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:co.gobierno.orfeo.prooveedor\">\n";
        	tipoEndpointOrfeo3 = "         <numRadicado xsi:type=\"xsd:string\">" + radicado + "</numRadicado>\n";
        }
        String params = tipoEndpointOrfeo2 +
                        "   <soapenv:Header/>\n" +
                        "   <soapenv:Body>\n" +
                        tipoEndpointOrfeo + //"      <urn:consultaRadicado soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                        tipoEndpointOrfeo3 + //"         <numRadicado xsi:type=\"xsd:string\">" + radicado + "</numRadicado>\n" +
                        "         <estado xsi:type=\"xsd:string\">?</estado>\n" +
                        tipoEndpointOrfeo1 + //"      </urn:consultaRadicado>\n" +
                        "   </soapenv:Body>\n" +
                        "</soapenv:Envelope>";
        WebServiceDTO objWebServiceDTO = new WebServiceDTO(); //
        objWebServiceDTO.setUrl(PropertiesHandler.PropertieValueOfKeyFromDB(listPropertieDTOs, "urlORFEO"));
        objWebServiceDTO.setMethod("POST");
        objWebServiceDTO.getHeader().put("Content-Type", "text/xml;charset=ISO-8859-1");
        objWebServiceDTO.setParams(params);
        try 
        {
            log.info("Voy a consumir el servicio web de Consultar Certificados de ORFEO.");
            objWebServiceDTO = ws.SoapWithParam(objWebServiceDTO);
            String responseMensaje = "";
            if (objWebServiceDTO.getXmlFile().contains("codigoError")) {
            	log.info("Orfeo TagError");
            	String response = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "codigoError");
            	if(response != null && response.contains("0")) {
            		responseMensaje = XmlHandler.ReadXmlFileGetTag(objWebServiceDTO.getXmlFile(), "mensaje");
            		objWebServiceDTO.setRespuesta(false);
            	}
            }
            this.logAuditoriaDTO.setAuMensajeError("DownloadORFEO " + "Voy a consumir el servicio web de Consultar Certificados de ORFEO. ");
            this.logAuditoriaDTO.setAuRequest(objWebServiceDTO.getParams());
            this.logAuditoriaDTO.setAuResponse(objWebServiceDTO.getXmlFile() + " " + objWebServiceDTO.getJsonFile());
            this.logAuditoriaDTO.setAuLogUsuario(objThirdDTO.getIdNumber());
            this.logAuditoriaDTO.setAuAplicacion("ORFEO");
            logService.AddLogAuditoria(logAuditoriaDTO);
        }
        catch (Exception ex) 
        {
            log.info("Error al consultar los certificados de ORFEO: " + ex.toString());
            return null;
        }
        if(objWebServiceDTO.isRespuesta())
        {
            log.info("Se consumió el servicio web de Consultar Certificados de ORFEO correctamente.");
            return objWebServiceDTO;
        }
        else
        {
            return null;
        }
    }    
}
