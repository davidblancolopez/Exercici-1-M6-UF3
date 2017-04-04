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
    }

    /**
     * Tornar el nom de la col·lecció actual.
     */
    public void nomColeccioActual() {
        try {
            //Recuperem el nom de la col·lecció.
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
            //Recuperem la col·lecció pare i el seu nom.
            System.out.println("Col·lecció Pare: " + coleccio.getParentCollection().getName());
        } catch (XMLDBException ex) {
            Logger.getLogger(Coleccions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Tornar noms de col·leccions filles.
     */
    public void nomColeccionsFilles(){
        //S'inicialitza l'array.
        String [] col = null;
        try{
            //Omplim l'array amb els noms de les col·leccions filles.
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
            //Es crea la col·lecció amb el nom que han passat per parametre..
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
            //Eliminem la col·lecció utilitzant el nom que han pasat per parametre.
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
        //Comprovem que no s'ha introduit el nom de la col·lecció malament(No han posat res).
        if(col.length() > 0) {
            coleccio = DatabaseManager.getCollection("xmldb:exist://localhost:8080/exist/xmlrpc/db/" + col, "admin", "");
        }
        
        System.out.println("Recurs: " + coleccio.getResource(id).getId());
    }
    
    
}
