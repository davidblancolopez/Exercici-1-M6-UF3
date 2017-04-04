package exercici_1_2;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Service;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;

public class Coleccions {

    Collection coleccio;
    ConfigConnexio conf = new ConfigConnexio();
    Service[] serveis;
    CollectionManagementService cms;

    public Coleccions() {
        this.coleccio = conf.conexion();
//        buscarCollectionManagement(); 
    }

    /**
     * Tornar el nom de la col·lecció actual.
     */
    public void nomColeccioActual() {
        try {
            System.out.println("Col·lecció actual: " + coleccio.getName());
        } catch (XMLDBException ex) {
            Logger.getLogger(Coleccions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Tornar el nom de la col·lecció pare.
     */
    public void nomColeccioPare() {
        try {
            System.out.println("Col·lecció Pare: " + coleccio.getParentCollection().getName());
        } catch (XMLDBException ex) {
            Logger.getLogger(Coleccions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Tornar noms de col·leccions filles.
     */
    private void nomColeccionsFilles(){
        String [] col = null;
        try{
            col = coleccio.listChildCollections();
        }catch(XMLDBException ex){
            Logger.getLogger(Coleccions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Crear col·lecció amb nom.
     * @param nom 
     */
    public void crearColeccio(String nom){
        try{
            cms.createCollection(nom);
        }catch(XMLDBException ex){
            Logger.getLogger(Coleccions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Eliminar una col·lecció.
     * @param nom 
     */
    public void eliminarColeccio(String nom){
        try{
            cms.removeCollection(nom);
        }catch(XMLDBException ex){
            Logger.getLogger(Coleccions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Comprovar si existeix un recurs en una col·lecció.
     * @param col
     * @param id
     * @throws XMLDBException 
     */
    public void comprovarRecursColeccio(String col, String id) throws XMLDBException{
        if(col.length() > 0) {
            coleccio = DatabaseManager.getCollection("xmldb:exist://localhost:8080/exist/xmlrpc/db/" + col, "admin", "");
        }
        
        System.out.println("Recurs: " + coleccio.getResource(id).getId());
    }
    
    
}
