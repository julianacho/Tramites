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
 * @author DELL
 */
public class PropertieDTO implements Serializable
{
    private String idkey;
    private String key;
    private String value;
    private String idService;

    public PropertieDTO(String idkey, String key, String value, String idService)
    {
        this.idkey = idkey;
        this.key = key;
        this.value = value;
        this.idService = idService;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getIdService()
    {
        return idService;
    }

    public void setIdService(String idService)
    {
        this.idService = idService;
    }

    public String getIdkey()
    {
        return idkey;
    }

    public void setIdkey(String idkey)
    {
        this.idkey = idkey;
    }

    

    
}
