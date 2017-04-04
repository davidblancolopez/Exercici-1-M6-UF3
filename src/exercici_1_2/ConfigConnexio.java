
package exercici_1_2;

import exercici.pkg1.m6.uf3.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import net.xqj.exist.ExistXQDataSource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;


public class ConfigConnexio {

    private Database dbDriver;
    private Collection coll;

    public ConfigConnexio() {
        conexion();
    }
    
    public Collection conexion(){        
        try {
            dbDriver = (Database) Class.forName("org.exist.xmldb.DatabaseImpl").newInstance();
            DatabaseManager.registerDatabase(dbDriver);
            coll = DatabaseManager.getCollection("xmldb:exist://localhost:8080/exist/xmlrpc/db", "admin", "");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Put.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Put.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Put.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLDBException ex) {
            Logger.getLogger(Put.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return coll;
    }
    
    
}
