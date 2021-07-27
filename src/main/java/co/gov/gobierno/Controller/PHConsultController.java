/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.Controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.slf4j.LoggerFactory;

import co.gov.gobierno.DTO.PHRequestDTO;
import co.gov.gobierno.DTO.PropertieDTO;
import co.gov.gobierno.DTO.ThirdDTO;
import co.gov.gobierno.Service.PHConsultInitService;

/**
 *
 * @author DELL
 */
@ManagedBean
@RequestScoped
public class PHConsultController implements Serializable
{
    private static long serialVersionUID = 1L;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(PHConsultController.class);
    
    private ThirdDTO objThirdDTO;
    private List<PropertieDTO> listPropertieDTOs;
    
    private boolean alertShow=false;
    private boolean alertStyle=false;
    private String alertMessage="";
    
    private String nit;
    
    private List<PHRequestDTO> listPHRequestDTOs;
    
    @ManagedProperty(value="#{indexController}")
    private IndexController indexController;
    

    @EJB
    PHConsultInitService phcis;

    public void DownloadRequest()
    {
        
    }
    
    @PostConstruct
    public void Init()
    {
        this.objThirdDTO = indexController.getObjThirdDTO();
        this.listPropertieDTOs = indexController.getListPropertieDTOs();
        this.listPHRequestDTOs = new ArrayList<>();
        
    }

    public ThirdDTO getObjThirdDTO() {
        return objThirdDTO;
    }

    public void setObjThirdDTO(ThirdDTO objThirdDTO) {
        this.objThirdDTO = objThirdDTO;
    }

    public void setIndexController(IndexController indexController) {
        this.indexController = indexController;
    }

    public boolean isAlertShow() {
        return alertShow;
    }

    public void setAlertShow(boolean alertShow) {
        this.alertShow = alertShow;
    }

    public boolean isAlertStyle() {
        return alertStyle;
    }

    public void setAlertStyle(boolean alertStyle) {
        this.alertStyle = alertStyle;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    public List<PropertieDTO> getListPropertieDTOs()
    {
        return listPropertieDTOs;
    }

    public void setListPropertieDTOs(List<PropertieDTO> listPropertieDTOs)
    {
        this.listPropertieDTOs = listPropertieDTOs;
    }

    public List<PHRequestDTO> getListPHRequestDTOs()
    {
        return listPHRequestDTOs;
    }

    public void setListPHRequestDTOs(List<PHRequestDTO> listPHRequestDTOs)
    {
        this.listPHRequestDTOs = listPHRequestDTOs;
    }

    public String getNit()
    {
        return nit;
    }

    public void setNit(String nit)
    {
        this.nit = nit;
    }

}
