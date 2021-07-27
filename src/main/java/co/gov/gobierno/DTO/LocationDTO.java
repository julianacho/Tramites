/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.DTO;

/**
 *
 * @author DELL
 */
public class LocationDTO
{
    private String nameLocation;
    private String idLocation;
    private String valueLocation;
    private String padreId;

    public LocationDTO(String nameLocation, String idLocation, String valueLocation, String padreId)
    {
        this.nameLocation = nameLocation;
        this.idLocation = idLocation;
        this.valueLocation = valueLocation;
        this.padreId = padreId;
    }

    public String getNameLocation()
    {
        return nameLocation;
    }

    public void setNameLocation(String nameLocation)
    {
        this.nameLocation = nameLocation;
    }

    public String getIdLocation()
    {
        return idLocation;
    }

    public void setIdLocation(String idLocation)
    {
        this.idLocation = idLocation;
    }

    public String getValueLocation()
    {
        return valueLocation;
    }

    public void setValueLocation(String valueLocation)
    {
        this.valueLocation = valueLocation;
    }

    public String getPadreId()
    {
        return padreId;
    }

    public void setPadreId(String padreId)
    {
        this.padreId = padreId;
    }
    
    
}
