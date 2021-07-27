/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.Util;

import com.sun.jersey.core.util.Base64;

import co.gov.gobierno.ServiceImpl.PHRegisterServiceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;

/**
 *
 * @author DELL
 */
public class Base64Handler 
{
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(Base64Handler.class);
    public static String convertFileToBase64Binary(InputStream is) throws IOException 
    {
        byte[] bytes = loadFile(is);
        byte[] encoded = Base64.encode(bytes);
        String encodedString = new String(encoded);
        return encodedString;   
    }
    
    private static byte[] loadFile(InputStream is) throws IOException 
    {
        byte[] bytes = null;
        try
        {
            bytes = IOUtils.toByteArray(is);
            int offset = 0;
            int numRead = 0;
            numRead=is.read(bytes, offset, bytes.length-offset);
            int tam = bytes.length;
            while ((offset < tam) && numRead >= 0) 
            {
                offset += numRead;
            }   
            if (offset < tam) 
            {
                log.info("Could not completely read file ");
            }   
        }
        catch(IOException e)
        {
            log.error("Error al convertir el archivo a base 64: " + e.toString());
            return null;
        }
        return bytes;
    } 
    
    public static byte[] convertBase64BinaryToFile(String base64)
    {
        try
        {
            byte[] bytes = Base64.decode(base64);
            return bytes;
            
        }
        catch (Exception e)
        {
            log.error("Error al convertir el base 64 a un archivo: " + e.toString());
            return null;
        }
    }
}
