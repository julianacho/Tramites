/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.DTO;

import java.io.Serializable;

/**
 *
 * @author DELL
 */
public class RequestDTO implements Serializable
{
    private boolean select;
    private String younger;
    private String certificateKey;
    private String certificateKey2;
    private String related;
    private String radNumber;
    private String radNumberSolicitud;
    private String idNumber;
    private String name;
    private String idRequestType;
    private String requestType;
    private String requestDate;
    private String atentionDate;
    private String state;
    private String rejectCauses;
    private String externalRelation;
    private String itemId;
    private String listId;
    private String thirdPartyId;
    private String downloadRadNumber;
    private String tipoTramite;
    private String paramSubsanacion;
    private String caso;
    private String identificacionPrivado;
    private boolean showDownload;
    private boolean showCorrect;
    private boolean showSubsanacionExtincion;
    private boolean showSubsanacionPropiedad;
    private boolean showSubsanacionActualizacionRepresentante;
    private boolean showTramite;
    private boolean showAprobado;
    private boolean showAprobadoD = false;

    public RequestDTO() 
    {
        
    }

    public RequestDTO(String radNumber, String name, String state, String tipoTramite, String parametroSubsanacion, String caso,
                        boolean showSubsanacion, String radNumberSolicitud, String Ultimo)
    {
        this.radNumber = radNumber;
        this.radNumberSolicitud = radNumberSolicitud;
        this.name = name;
        this.state = state;
        this.tipoTramite = tipoTramite;
        this.paramSubsanacion = parametroSubsanacion;
        this.caso = caso;
        if (state.equals("T"))
        {
            this.state = "Trámite";
            this.showAprobado = false;
            this.showSubsanacionExtincion = false;
            this.showSubsanacionActualizacionRepresentante = false;
            this.showTramite = true;
            this.showSubsanacionPropiedad = false;
        }
        if (state.equals("S") && tipoTramite.equals("3"))
        {
            this.state = "Subsanación";
            this.showAprobado = false;
            this.showSubsanacionExtincion = showSubsanacion;
            this.showSubsanacionActualizacionRepresentante = false;
            this.showTramite = false;
            this.showSubsanacionPropiedad = false;
        }
        if (state.equals("S") && tipoTramite.equals("2"))
        {
            this.state = "Subsanación";
            this.showAprobado = false;
            this.showSubsanacionActualizacionRepresentante = showSubsanacion;
            this.showSubsanacionExtincion = false;
            this.showTramite = false;
            this.showSubsanacionPropiedad = false;
        }
        if (state.equals("S") && tipoTramite.equals("1"))
        {
            this.state = "Subsanación";
            this.showAprobado = false;
            this.showSubsanacionExtincion = false;
            this.showTramite = false;
            this.showSubsanacionPropiedad = showSubsanacion;
            this.showSubsanacionActualizacionRepresentante = false;
        }
        if (state.equals("A"))
        {
            this.state = "Aprobado";
            this.showAprobadoD = Ultimo.equals("S");
            this.showAprobado = true;
            this.showSubsanacionExtincion = false;
            this.showTramite = false;
            this.showSubsanacionPropiedad = false;
            this.showSubsanacionActualizacionRepresentante = false;
        }
        if (state.equals("C"))
        {
            this.state = "Cerrado";
            this.showAprobado = false;
            this.showSubsanacionExtincion = false;
            this.showTramite = true;
            this.showSubsanacionPropiedad = false;
            this.showSubsanacionActualizacionRepresentante = false;
        }
    }
    
    public RequestDTO(boolean select, String younger, String privado, String certificateKey,String certificateKey2, String related,
            String radNumber, String idNumber, String name, String idRequestType, String requestType,
            String requestDate, String atentionDate, String state, String rejectCauses, String externalRelation,
            String itemId, String listId, String thirdPartyId, String downloadRadNumber, boolean showDownload,
            boolean showCorrect)
    {
        this.select = select;
        this.younger = younger;
        this.certificateKey = certificateKey;
        this.certificateKey2 = certificateKey2;
        this.related = related;
        this.radNumber = radNumber;
        this.idNumber = idNumber;
        this.name = name;
        this.idRequestType = idRequestType;
        this.requestType = requestType;
        this.requestDate = requestDate;
        this.atentionDate = atentionDate;
        this.state = state;
        this.rejectCauses = rejectCauses;
        this.externalRelation = externalRelation;
        this.itemId = itemId;
        this.listId = listId;
        this.thirdPartyId = thirdPartyId;
        this.downloadRadNumber = downloadRadNumber;
        this.showDownload = showDownload;
        this.showCorrect = showCorrect;
        this.identificacionPrivado = privado;
    }

    
    
    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getRadNumber() {
        return radNumber;
    }

    public void setRadNumber(String radNumber) {
        this.radNumber = radNumber;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getAtentionDate() {
        return atentionDate;
    }

    public void setAtentionDate(String atentionDate) {
        this.atentionDate = atentionDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRejectCauses() {
        return rejectCauses;
    }

    public void setRejectCauses(String rejectCauses) {
        this.rejectCauses = rejectCauses;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isShowDownload() {
        return showDownload;
    }

    public void setShowDownload(boolean showDownload) {
        this.showDownload = showDownload;
    }

    public boolean isShowCorrect() {
        return showCorrect;
    }

    public void setShowCorrect(boolean showCorrect) {
        this.showCorrect = showCorrect;
    }

    public String getYounger() {
        return younger;
    }

    public void setYounger(String younger) {
        this.younger = younger;
    }

    public String getCertificateKey() {
        return certificateKey;
    }

    public void setCertificateKey(String certificateKey) {
        this.certificateKey = certificateKey;
    }

    public String getRelated() {
        return related;
    }

    public void setRelated(String related) {
        this.related = related;
    }

    public String getIdRequestType() {
        return idRequestType;
    }

    public void setIdRequestType(String idRequestType) {
        this.idRequestType = idRequestType;
    }

    public String getExternalRelation() {
        return externalRelation;
    }

    public void setExternalRelation(String externalRelation) {
        this.externalRelation = externalRelation;
    }
    
    public String getItemId()
    {
        return itemId;
    }

    public void setItemId(String itemId)
    {
        this.itemId = itemId;
    }

    public String getListId()
    {
        return listId;
    }

    public void setListId(String listId)
    {
        this.listId = listId;
    }

    public String getThirdPartyId()
    {
        return thirdPartyId;
    }

    public void setThirdPartyId(String thirdPartyId)
    {
        this.thirdPartyId = thirdPartyId;
    }

    public String getDownloadRadNumber()
    {
        return downloadRadNumber;
    }

    public void setDownloadRadNumber(String downloadRadNumber)
    {
        this.downloadRadNumber = downloadRadNumber;
    }

    public String getCertificateKey2()
    {
        return certificateKey2;
    }

    public void setCertificateKey2(String certificateKey2)
    {
        this.certificateKey2 = certificateKey2;
    }

    public String getTipoTramite()
    {
        return tipoTramite;
    }

    public void setTipoTramite(String tipoTramite)
    {
        this.tipoTramite = tipoTramite;
    }

    public boolean isShowSubsanacionExtincion()
    {
        return showSubsanacionExtincion;
    }

    public void setShowSubsanacionExtincion(boolean showSubsanacionExtincion)
    {
        this.showSubsanacionExtincion = showSubsanacionExtincion;
    }

    public boolean isShowSubsanacionPropiedad()
    {
        return showSubsanacionPropiedad;
    }

    public void setShowSubsanacionPropiedad(boolean showSubsanacionPropiedad)
    {
        this.showSubsanacionPropiedad = showSubsanacionPropiedad;
    }

    public boolean isShowTramite()
    {
        return showTramite;
    }

    public void setShowTramite(boolean showTramite)
    {
        this.showTramite = showTramite;
    }

    public boolean isShowAprobado()
    {
        return showAprobado;
    }

    public void setShowAprobado(boolean showAprobado)
    {
        this.showAprobado = showAprobado;
    }

    public String getParamSubsanacion()
    {
        return paramSubsanacion;
    }

    public void setParamSubsanacion(String paramSubsanacion)
    {
        this.paramSubsanacion = paramSubsanacion;
    }

    public String getCaso()
    {
        return caso;
    }

    public void setCaso(String caso)
    {
        this.caso = caso;
    }

    public boolean isShowSubsanacionActualizacionRepresentante()
    {
        return showSubsanacionActualizacionRepresentante;
    }

    public void setShowSubsanacionActualizacionRepresentante(boolean showSubsanacionActualizacionRepresentante)
    {
        this.showSubsanacionActualizacionRepresentante = showSubsanacionActualizacionRepresentante;
    }

    public String getRadNumberSolicitud()
    {
        return radNumberSolicitud;
    }

    public void setRadNumberSolicitud(String radNumberSolicitud)
    {
        this.radNumberSolicitud = radNumberSolicitud;
    }

    public boolean isShowAprobadoD()
    {
        return showAprobadoD;
    }

    public void setShowAprobadoD(boolean showAprobadoD)
    {
        this.showAprobadoD = showAprobadoD;
    }

    public String getIdentificacionPrivado()
    {
        return identificacionPrivado;
    }

    public void setIdentificacionPrivado(String identificacionPrivado)
    {
        this.identificacionPrivado = identificacionPrivado;
    }
    
    
}
