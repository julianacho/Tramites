/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.Util;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;

/**
 *
 * @author DELL
 */
public class DateHandler
{
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(DateHandler.class);
    public String ConvertDateToString(Date date, String format) 
    {
        Format formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }
    public String ConvertStringToDate(String date, String format)
    {
        try 
        {
            Date aux =new SimpleDateFormat(format).parse(date);
            return aux.toString();
        }
        catch (ParseException ex)
        {
            log.error("ConvertStringToDate in DateHandler: Error en la conversion de cadena a fecha." + ex.toString());
            return null;
        }
    }
}
