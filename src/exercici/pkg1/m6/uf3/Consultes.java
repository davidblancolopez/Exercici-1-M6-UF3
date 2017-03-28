
package exercici.pkg1.m6.uf3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;
import org.w3c.dom.Node;


public class Consultes {

    private final XQConnection con;
    private XQExpression xqe;
    private XQPreparedExpression xqpe;

    public Consultes(XQConnection con) {
        this.con = con;
    }

    /**
     * 
     * @param etiqueta
     * @param valor 
     */
    public void afegirEtiqueta(String etiqueta, String valor) {
        try {
            xqe = con.createExpression();
            String xq = "update insert <" + etiqueta + ">'" + valor + "'</" + etiqueta + "> into doc('/Exercici-1-M6-UF3/plantes.xml')//libro";
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void modificarPreuNode(String codigo, String precio) {
        try {
            xqe = con.createExpression();
            String xq = "update value doc('/m06uf3/libros.xml')//libro[@codigo='" + codigo + "']/preu with '" + precio + "'";
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }



    public void eliminarEtiqueta(String etiqueta) {
        try {
            xqe = con.createExpression();
            String xq = "update delete doc('/m06uf3/libros.xml')//libro/" + etiqueta;
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }

    void eliminarAtribut(String atributo) {
        try {
            xqe = con.createExpression();
            String xq = "update delete doc('/m06uf3/libros.xml')//libro/@" + atributo;
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }

    
    
    
    
    
    
    
    
    /**
     * Metodo para traducir una etiqueta a otro idioma.
     */
    public void traduirElement(){
        try{
            xqe = con.createExpression();
            String xq = "update rename doc('/Exercici-1-M6-UF3/plantes.xml')//PLANT/COMMON as 'COMUN'";
        }catch(XQException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * Metodo para modificar el precio.
     * Quita el simbolo $ de los precios de todas las plantas.
     */
    public void modificarPreu(){
        try{
            xqe = con.createExpression();
            
            String xq = "for $b in doc('/Exercici-1-M6-UF3/plantes.xml')"
                    + "//CATALOG/PLANT/PRICE where starts-with($b, '$')"
                    + "return update value $b with substring($b, 2)";
            
            
        }catch(XQException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    
    /**
     * Metodo para obtener todas las plantas.
     * @return 
     */
    public List<Node> obtenirPlantes() {
        List<Node> plantes = new ArrayList<>();
        try {
            xqe = con.createExpression();
            String xq = "for $b in doc ('/Exercici-1-M6-UF3/plantes.xml')//return $b";

            XQResultSequence rs = xqe.executeQuery(xq);
            while (rs.next()) {
                plantes.add(rs.getItem().getNode());
            }
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
        return plantes;
    }
    
    
    /**
     * Cercar planta per el nom comú.
     * @param nom
     * @return 
     */
    public Node cercarNom(String nom) {
        Node planta = null;
        try {
            xqe = con.createExpression();
            String xq = "for $b in doc('/Exercici-1-M6-UF3/plantes.xml')"
                    + "//PLANT where every $a in $b/COMMON satisfies ($a = '" + nom + "') return $b";

            XQResultSequence rs = xqe.executeQuery(xq);
            rs.next();
            planta = rs.getItem().getNode();
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
        return planta;
    }
    
    
    /**
     * Metodo para añadir una planta.
     * @param common
     * @param botanical
     * @param zone
     * @param light
     * @param price
     * @param availability 
     */
    public void afegirPlanta(String common, String botanical, String zone, String light, double price, int availability) {
        try {
            xqe = con.createExpression();
            String xq = "update insert "
                    + "    <PLANT>"
                    + "        <COMMON>" + common + "</COMMON>"
                    + "        <BOTANICAL>" + botanical + "</BOTANICAL>"
                    + "        <ZONE>" + zone + "</ZONE>"
                    + "        <LIGHT>" + light + "</LIGHT>"
                    + "        <PRICE>" + price + "</PRICE>"
                    + "        <AVAILABILITY>" + availability + "</AVAILABILITY>"
                    + "    </PLANT>\n"
                    + "following doc('/Exercici-1-M6-UF3/plantes.xml";

            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    /**
     * Metodo para añadir un atributo a las plantas.
     * @param atributo
     * @param valor 
     */
    public void afegirAtribut(String atributo, String valor) {
        try {
            xqe = con.createExpression();
            String xq = "update insert attribute " + atributo + " {'" + valor + "'} into doc('/Exercici-1-M6-UF3/plantes.xml')//PLANT";
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    public void cercarPerRangPreus(double preuMinim, double preuMaxim){
        try{
            xqe = con.createExpression();
            String xq;
            xqe.executeCommand(xq);
        }catch(XQException ex){
            System.out.println(ex);
        }
    }
    
    /**
     * Metode per cercar plantes per zona.
     * @param zona 
     */
    public void cercarPerZona(String zona){
        try{
            xqe = con.createExpression();
            String xq = "for $b in doc('/Exercici-1-M6-UF3/plantes.xml')"
                    + "//PLANT where every $a in $b/COMMON/ZONE satisfies ($a = '" + zona + "') return $b";
            xqe.executeCommand(xq);
        }catch(XQException ex){
            System.out.println(ex);
        }
    }
    
    /**
     * Metode per afegir un node.
     */
    public void afegirNode(){
        try{
            xqe = con.createExpression();
            String xq;
            xqe.executeCommand(xq);
        }catch(XQException ex){
            System.out.println(ex);
        }
    }
    
    
    public void modificarPreuPlanta(String nom, double preu) {

        try {
            xqe = con.createExpression();
            String xq = "for $b in doc('/Exercici-1-M6-UF3/plantes.xml')"
                    + "//PLANT where every $a in $b/COMMON satisfies ($a = '" + nom + "') ";
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * Metode per eliminar una planta.
     * @param nom 
     */
    public void eliminarPlanta(String nom) {

        try {
            xqe = con.createExpression();
            String xq = "update delete doc('/Exercici-1-M6-UF3/plantes.xml')//PLANT//COMMON = " + nom + "']";
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
