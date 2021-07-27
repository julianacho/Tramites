/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HP
 */
public class AyudaDTO  implements Serializable
{
    
    private String idFormulario;
    private String tipo;
    private String url;
    private String descripcion;

    
    public AyudaDTO(String idFormulario, String Tipo, String url, String descripcion)
    {
        this.idFormulario = idFormulario;
        this.tipo = Tipo;
        this.url = url;
        this.descripcion = descripcion;
        
    }
            
    
    public String getIdFormulario()
    {
        return idFormulario;
    }

    public void setIdFormulario(String idFormulario)
    {
        this.idFormulario = idFormulario;
    }

    public String getTipo()
    {
        return tipo;
    }

    public void setTipo(String tipo)
    {
        this.tipo = tipo;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getDescripcion()
    {
        return descripcion;
    }

    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }
    
    
    
    
    
    
}
