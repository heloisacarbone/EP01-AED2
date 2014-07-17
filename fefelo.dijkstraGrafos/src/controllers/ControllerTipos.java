/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import file.Text;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author Favoretti
 */
public class ControllerTipos {
    public static String verificaTipoTxt(String file){
    	try {
	    	FileReader readArq = new FileReader(file);
			BufferedReader reader = new BufferedReader(readArq);
			String line = "";
	                String tipo = "";
	                int contaVazia = 0;
	        try {
	            while(contaVazia <= 3){
	            while (!(line = reader.readLine()).equals("")) {
	                   if(contaVazia == 3)
	                    break;
	                
	            }
	            System.out.println("Vazia");
	            contaVazia++;
	            }
	            tipo = line;
	            System.out.println("Tipo: "+tipo);
	           
	        } catch (IOException ex) {
	            Logger.getLogger(ControllerTipos.class.getName()).log(Level.SEVERE, null, ex);
	        }
	     return tipo;
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		return null;
    	}
    
    }

    public static String verificaTipoXml(String file){
       	String tipo = null;
        Document doc = null;
        SAXBuilder builder = new SAXBuilder();
        try {
            doc = builder.build(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
         Element dijkstra = doc.getRootElement();
         tipo = dijkstra.getAttributeValue("tipo");
         return tipo;
    }
    
}
