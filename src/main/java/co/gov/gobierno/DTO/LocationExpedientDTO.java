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
public class LocationExpedientDTO implements Serializable
{
    private String exp;
    private String locationDesc;
    private String locationValue;

    public LocationExpedientDTO(String exp, String locationDesc, String locationValue)
    {
        this.exp = exp;
        this.locationDesc = locationDesc;
        this.locationValue = locationValue;
    }

    public LocationExpedientDTO()
    {
    }

    
    
    public String getExp()
    {
        return exp;
    }

    public void setExp(String exp)
    {
        this.exp = exp;
    }

    public String getLocationDesc()
    {
        return locationDesc;
    }

    public void setLocationDesc(String locationDesc)
    {
        this.locationDesc = locationDesc;
    }

    public String getLocationValue()
    {
        return locationValue;
    }

    public void setLocationValue(String locationValue)
    {
        this.locationValue = locationValue;
    }
}
