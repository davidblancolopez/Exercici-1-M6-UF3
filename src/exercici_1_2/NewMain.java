/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exercici_1_2;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

/**
 *
 * @author ALUMNEDAM
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws XMLDBException, SAXException, IOException, ParserConfigurationException {
        ColeccionsConsultes cc = new ColeccionsConsultes();
//        
//        cc.afegirRecurs("envio.xml");
//        
//        XMLResource xml = cc.obtenirRecurs("envio.xml");
//        
//        System.out.println(xml.getContentAsDOM().getFirstChild().getTextContent());
//        
//        cc.eliminarRecurs("envio.xml");

//        cc.afegir("ficha.png");

cc.descarregar("ficha.png");
    }
    
}
