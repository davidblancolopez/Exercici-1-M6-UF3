package exercici_1_2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.Service;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.BinaryResource;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

public class ColeccionsConsultes {

    Collection coleccio;
    ConfigConnexio conf = new ConfigConnexio();
    Service[] serveis;
    CollectionManagementService cms;

    public ColeccionsConsultes() {
        this.coleccio = conf.conexion();
        buscarCollectionManagement();
    }

    /**
     * Tornar el nom de la col·lecció actual.
     */
    public void nomColeccioActual() {
        try {
            //Recuperem el nom de la col·lecció.
            System.out.println("Col·lecció actual: " + coleccio.getName());
        } catch (XMLDBException ex) {
            Logger.getLogger(ColeccionsConsultes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Tornar el nom de la col·lecció pare.
     */
    public void nomColeccioPare() {
        try {
            //Recuperem la col·lecció pare i el seu nom.
            System.out.println("Col·lecció Pare: " + coleccio.getParentCollection().getName());
        } catch (XMLDBException ex) {
            Logger.getLogger(ColeccionsConsultes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Tornar noms de col·leccions filles.
     */
    public void nomColeccionsFilles() {
        //S'inicialitza l'array.
        String[] col = null;
        try {
            //Omplim l'array amb els noms de les col·leccions filles.
            col = coleccio.listChildCollections();
        } catch (XMLDBException ex) {
            Logger.getLogger(ColeccionsConsultes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Crear col·lecció amb nom.
     *
     * @param nom
     */
    public void crearColeccio(String nom) {
        try {
            //Es crea la col·lecció amb el nom que han passat per parametre..
            cms.createCollection(nom);
        } catch (XMLDBException ex) {
            Logger.getLogger(ColeccionsConsultes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Eliminar una col·lecció.
     *
     * @param nom
     */
    public void eliminarColeccio(String nom) {
        try {
            //Eliminem la col·lecció utilitzant el nom que han pasat per parametre.
            cms.removeCollection(nom);
        } catch (XMLDBException ex) {
            Logger.getLogger(ColeccionsConsultes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Comprovar si existeix un recurs en una col·lecció.
     *
     * @param col
     * @param id
     * @throws XMLDBException
     */
    public void comprovarRecursColeccio(String col, String id) throws XMLDBException {
        //Comprovem que no s'ha introduit el nom de la col·lecció malament(No han posat res).
        if (col.length() > 0) {
            coleccio = DatabaseManager.getCollection("xmldb:exist://localhost:8080/exist/xmlrpc/db/" + col, "admin", "");
        }

        System.out.println("Recurs: " + coleccio.getResource(id).getId());
    }

    /**
     * Metode per inicialitzar la variable cms.
     */
    public void buscarCollectionManagement() {
        try {
            serveis = coleccio.getServices();
            for (Service servei : serveis) {
                if (servei.getName().equals("CollectionManagementService")) {
                    cms = (CollectionManagementService) servei;
                }
            }
        } catch (XMLDBException ex) {
            Logger.getLogger(ColeccionsConsultes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * **************************************************************************************************************************************
     * **************************************************** RECURSOS
     * **************************************************************
     * *************************************************************************************************************************************
     */
    
    /**
     * Metode per afegir un recurs a la BBDD.
     * @param args
     * @throws XMLDBException
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException 
     */
    public void afegirRecurs(String args) throws XMLDBException, SAXException, IOException, ParserConfigurationException {
        XMLResource xml = null;
        File arxiu = new File(args);

        xml = (XMLResource) coleccio.createResource(args, XMLResource.RESOURCE_TYPE);
        Document document = cargarXML(arxiu);
        xml.setContentAsDOM(document);
        coleccio.storeResource(xml);

    }

    /**
     * Metode utilitzat per el metode afegirRecurs() per obtenir el document.
     * @param fitxerXML
     * @return
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException 
     */
    public Document cargarXML(File fitxerXML) throws SAXException, IOException, ParserConfigurationException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fitxerXML);
        doc.normalizeDocument();
        return doc;
    }

    /**
     * Metode per obtenir un recurs en concret.
     * @param recurs
     * @return 
     */
    public XMLResource obtenirRecurs(String recurs) {
        XMLResource xml = null;

        try {
            xml = (XMLResource) coleccio.getResource(recurs);
        } catch (XMLDBException ex) {
            Logger.getLogger(ColeccionsConsultes.class.getName()).log(Level.SEVERE, null, ex);
        }

        return xml;
    }

    /**
     * Metode per eliminar un recurs en concret.
     * @param recurs 
     */
    public void eliminarRecurs(String recurs){
       XMLResource xml = obtenirRecurs(recurs);
        try{
            coleccio.removeResource(xml);
        }catch(XMLDBException ex){
            Logger.getLogger(ColeccionsConsultes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void afegir(String ruta) throws XMLDBException{
        BinaryResource br = null;
        br = (BinaryResource) coleccio.createResource(ruta, BinaryResource.RESOURCE_TYPE);
        File imatge = new File(ruta);
        br.setContent(imatge);
        
        coleccio.storeResource(br);
    }
    
    public void descarregar(String ruta) throws XMLDBException, IOException{

        BinaryResource br = (BinaryResource) coleccio.getResource(ruta);
        byte[] lista = (byte[]) br.getContent();
        Path p = Paths.get(ruta);
//        File imatge = new File(ruta);
        
        Files.write(p, lista);
        coleccio.storeResource(br);
    }
    
    
    
}
