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
        //Inicializamos la coleccion.
        this.coleccio = conf.conexion();
        buscarCollectionManagement();
    }

    /**
     * Tornar el nom de la col·lecció actual.
     */
    public void nomColeccioActual() {
        try {
            //Recuperamos el nombre la coleccion con este metodo.
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
            //Recuperamos la colecion padre y miramos su nombre.
            System.out.println("Col·lecció Pare: " + coleccio.getParentCollection().getName());
        } catch (XMLDBException ex) {
            Logger.getLogger(ColeccionsConsultes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Tornar noms de col·leccions filles.
     */
    public void nomColeccionsFilles() {
        //Inicializamos el array.
        String[] col = null;
        try {
            //Rellenamos el array con el nombre de las colecciones hijas.
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

            //Eliminamos la coleccion utilizando el nombre pasado por parametro.
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
        //Comprovamos que no se ha introducido mal el nombre.
        if (col.length() > 0) {
            //Asignamos a coleccio la coleccion que buscamos con el nombre.
            coleccio = DatabaseManager.getCollection("xmldb:exist://localhost:8080/exist/xmlrpc/db/" + col, "admin", "");
        }

        System.out.println("Recurs: " + coleccio.getResource(id).getId());
    }

    /**
     * Metode per inicialitzar la variable cms.
     */
    public void buscarCollectionManagement() {
        try {
            //Obtenemos el servicio.
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
     * @param fitxer
     * @throws XMLDBException
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException 
     */
    public void afegirRecurs(String fitxer) throws XMLDBException, SAXException, IOException, ParserConfigurationException {
        XMLResource xml = null;
        File arxiu = new File(fitxer);

        //Inicializamos el objeto xml y le metemos el recurso que creamos.
        xml = (XMLResource) coleccio.createResource(fitxer, XMLResource.RESOURCE_TYPE);
        //Creamos un objeto document y utlizamos el metodo cargarXML para obtener el contenido y meterlo en el objeto document.
        Document document = cargarXML(arxiu);
        
        //Introducimos el objeto document en el XML con este metodo.
        xml.setContentAsDOM(document);
        //Añadimos el recurso a la coleccion.
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
            //Obtenemos el recurso utilizando el nombre pasado por parametro.
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
        
       //Inicializamos el objeto XML utilizando el metodo anterior que sirve para obtener un recurso con el nombre pasado por parametro.
       XMLResource xml = obtenirRecurs(recurs);
        try{
            //Eliminamos el recurso que hemos obtenido antes y hemos metido en xml.
            coleccio.removeResource(xml);
        }catch(XMLDBException ex){
            Logger.getLogger(ColeccionsConsultes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Metode per afegir un fitxer a la BBDD.
     * @param ruta
     * @throws XMLDBException 
     */
    public void afegir(String ruta) throws XMLDBException{
        
        
        BinaryResource br = null;
        
        //Inicializamos el objeto BinaryResource utilizando este metodo al que se le pasa la ruta pasada por parametro 
        // y se utiliza el metodo RESOURCE_TYPE.
        br = (BinaryResource) coleccio.createResource(ruta, BinaryResource.RESOURCE_TYPE);
        
        //Creamos un archivo FIle con la ruta de la imagen especificada.
        File imatge = new File(ruta);
        
        //Le metemos el objeto File al objeto BinaryResource.
        br.setContent(imatge);
        
        coleccio.storeResource(br);
    }
    
    /**
     * Metode per descarregar un fitxer.
     * @param ruta
     * @throws XMLDBException
     * @throws IOException 
     */
    public void descarregar(String ruta) throws XMLDBException, IOException{

        //Inicializamos el objeto Binary Resource con este metodo que obtiene el recurso
        //con el nombre pasado por parametro.
        BinaryResource br = (BinaryResource) coleccio.getResource(ruta);
        
        //Creamos un array de byte y introducimos el recurso binario con este metodo.
        byte[] lista = (byte[]) br.getContent();
        
        //Creamos un Path con la ruta pasada por parametro.
        Path p = Paths.get(ruta);
        
        //
        Files.write(p, lista);
        
        //Guardamos el recurso.
        coleccio.storeResource(br);
    }
    
    
    
}
