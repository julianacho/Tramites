/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.gobierno.Util;

import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author DELL
 */
public class XmlHandler 
{
    public static String ReadXmlFileGetTag(String xml, String tag)
    {
        try
        {
            Document document = convertStringToXmlDocument(xml);
            /*NodeList nodeLst = document.getElementsByTagName("a:MessageDTO");
            String radicado="";
            if (nodeLst.getLength() > 0) 
            {
                Element err = (Element) nodeLst.item(0);
                radicado = err.getElementsByTagName("a:Message").item(0).getTextContent();
            }*/
            String radicado="";
            if(document!=null)
            {
                radicado = document.getElementsByTagName(tag).item(0).getTextContent();
                return radicado;
            }
            else
            {
                return null;
            }
        }
        catch(DOMException e)
        {
            System.err.println("Error:  " + e.toString());
        }
        catch(Exception e)
        {
            System.err.println("Error:  " + e.toString());
        }
        return null;
    }
    
    public static String ReadXmlFileGetTagInsideAnotherTag(String xml, String tagMayor, String tagMenor)
    {
        Document document = convertStringToXmlDocument(xml);
        NodeList nodeLst = document.getElementsByTagName(tagMayor);
        String result="";
        if (nodeLst.getLength() > 0) 
        {
            Element err = (Element) nodeLst.item(0);
            result = err.getElementsByTagName(tagMenor).item(0).getTextContent();
        }
        return result;
    }
    
    public static Document convertStringToXmlDocument(String xmlStr)
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder builder;  
        try  
        {  
            builder = factory.newDocumentBuilder();  
            Document doc = (Document) builder.parse( new InputSource( new StringReader( xmlStr ) ) ); 
            return doc;
        } catch (IOException | ParserConfigurationException | SAXException e) 
        {
            System.out.println("CertificateRequestServiceImpl: Error al comvertir la cadena en un documento de tipo Xml." + e.toString());
            return null;
        } 
    }
}
