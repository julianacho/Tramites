/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.Util;

import com.sun.jersey.core.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

/**
 *
 * @author DELL
 */
public class ReporteHandler
{
    public static List<String> dataSource = new ArrayList<>();
    
    public static String reporteRadicado(Map<String, Object> parametros) throws IOException
    {
        dataSource.clear();
        URL res = FacesContext.getCurrentInstance().getExternalContext().getResource("/resources/reporteRadicado.jasper");
        //String relativa = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reporteRadicado.jasper");
        File archivo = new File(res.getFile());
        
        dataSource.add("Reporte de Radicado");
        
        try
        {
            JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(dataSource,false);  

            JasperPrint print = JasperFillManager.fillReport(archivo.getPath(),parametros, source);
            
            byte[] bytes = JasperExportManager.exportReportToPdf(print);
            
            byte[] encoded = Base64.encode(bytes);
            
            String encodedString = new String(encoded);
            
            return encodedString;   
        }
        catch (JRException e)
        {
            System.err.println("Error Generando El Reporte de Radicado ->"+ e );
            return null;
        }
        
        
        
    }

    public static List<String> getDataSource()
    {
        return dataSource;
    }

    public static void setDataSource(List<String> dataSource)
    {
        ReporteHandler.dataSource = dataSource;
    }
    
    
    
    
}
