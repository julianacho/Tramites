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
public class PHRequestDTO implements Serializable
{

    private String strTipoArea;
    private String strCodeTipoArea;
    private String strArea;
    private String strAreaDescripcion;
    private String strPrivada;
    private String strCantidad;
    private String strDescripcionC;
    private String strDescripcionP;
    private String indice;
   

    public PHRequestDTO(String strTipoArea, String strArea, String strPrivada, String strCantidad, String indice, String strDescripcionC, String strDescripcionP)
    {
        if(strTipoArea.equals("1") || strTipoArea.equals("C"))
        {
            this.strTipoArea = "Común";
            this.strCodeTipoArea = "C";
            this.strPrivada = "";
            this.strArea = strArea;
            this.strDescripcionC  = strDescripcionC;
        }
        if(strTipoArea.equals("2") || strTipoArea.equals("P"))
        {
            this.strTipoArea = "Privada";
            this.strCodeTipoArea = "P";
            this.strArea = "";
            this.strPrivada = strPrivada;
            this.strDescripcionP = strDescripcionP;
        }
 
        this.strCantidad = strCantidad;
        this.indice =  indice;
    }

    public PHRequestDTO()
    {
    }

    public String getStrTipoArea()
    {
        return strTipoArea;
    }

    public void setStrTipoArea(String strTipoArea)
    {
        this.strTipoArea = strTipoArea;
    }

    public String getStrArea()
    {
        return strArea;
    }

    public void setStrArea(String strArea)
    {
        this.strArea = strArea;
    }

    public String getStrPrivada()
    {
        return strPrivada;
    }

    public void setStrPrivada(String strPrivada)
    {
        this.strPrivada = strPrivada;
    }

    public String getStrCantidad()
    {
        return strCantidad;
    }

    public void setStrCantidad(String strCantidad)
    {
        this.strCantidad = strCantidad;
    }

    public String getIndice()
    {
        return indice;
    }

    public void setIndice(String indice)
    {
        this.indice = indice;
    }

    public String getStrCodeTipoArea()
    {
        return strCodeTipoArea;
    }

    public void setStrCodeTipoArea(String strCodeTipoArea)
    {
        this.strCodeTipoArea = strCodeTipoArea;
    }

    public String getStrAreaDescripcion()
    {
        return strAreaDescripcion;
    }

    public void setStrAreaDescripcion(String strAreaDescripcion)
    {
        this.strAreaDescripcion = strAreaDescripcion;
    }

    public String getStrDescripcionC()
    {
        return strDescripcionC;
    }

    public void setStrDescripcionC(String strDescripcionC)
    {
        this.strDescripcionC = strDescripcionC;
    }

    public String getStrDescripcionP()
    {
        return strDescripcionP;
    }

    public void setStrDescripcionP(String strDescripcionP)
    {
        this.strDescripcionP = strDescripcionP;
    }


    
    
}
