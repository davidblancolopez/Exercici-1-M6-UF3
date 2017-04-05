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
     * Metodo para traducir una etiqueta a otro idioma.
     */
    public void traduirElement(String[] abans, String[] despres) {
        try {
            
            //Se inicializa la expresion xq.
            xqe = con.createExpression();
            
            //Utilizamos un bucle para recorrer todos los elementos a traducir.
            for (int i = 0; i < abans.length; i++) {
                /*Creamos la sentencia inidicandole que debe renombrar el nombre que tenia antes (array abans[])
                per el nou nom (despres[]).*/
                String xq = "update rename doc('/Exercici-1-M6-UF3/plantes.xml')//PLANT/" + abans[i] + "as'" + despres[i] + "'";
                xqe.executeCommand(xq);
            }
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Metodo para modificar el precio. Quita el simbolo $ de los precios de
     * todas las plantas.
     */
    public void modificarPreu() {
        try {
            
            //Se inicializa la expresion xq.
            xqe = con.createExpression();

            /*Creamos la sentencia indicandole que recorra todas las plantas y compruebe
            que en la etiqueta PRICE el precio comience por el simbolo $ y entonces 
            actualice el valor sin el $*/
            String xq = "for $b in doc('/Exercici-1-M6-UF3/plantes.xml')"
                    + "//CATALOG/PLANT/PRICE where starts-with($b, '$')"
                    + "return update value $b with substring($b, 2)";
            
            //Ejecutamos la sentencia.
            xqe.executeCommand(xq);

        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Metodo para obtener todas las plantas.
     *
     * @return
     */
    public List<Node> obtenirPlantes() {
        
        //Creamos una lista para almacenar las plantas.
        List<Node> plantes = new ArrayList<>();
        try {
            //Se inicializa la expresion xq.
            xqe = con.createExpression();
            
            /*Creamos la sentencia indicandole que recorra todo el archivo y nos
            retorne las plantas. */
            String xq = "for $b in doc ('/Exercici-1-M6-UF3/plantes.xml')//PLANT return $b";

            //Ejecutamos la sentencia.
            XQResultSequence rs = xqe.executeQuery(xq);
            
            //Bucle que insertara los resultados en la lista que hemos creado para guardarlos.
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
     *
     * @param nom
     * @return
     */
    public Node cercarNom(String nom) {
        //Es crea un Node.
        Node planta = null;
        try {
            
            //Se inicializa la expresion xq.
            xqe = con.createExpression();
            
            /*Creamos la sentencia indicandole que recorra todo el archivo y compruebe que en la 
            etiqueta COMMON el nombre que hay coincide con el que se busca.*/
            String xq = "for $b in doc('/Exercici-1-M6-UF3/plantes.xml')"
                    + "//PLANT where every $a in $b/COMMON satisfies ($a = '" + nom + "') return $b";

            
            //Ejecutamos la sentencia.
            XQResultSequence rs = xqe.executeQuery(xq);
            
            
            rs.next();
            
            //Asiganmos al Node el que nos ha devuelto la consulta.
            planta = rs.getItem().getNode();
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
        return planta;
    }

    /**
     * Metodo para añadir una planta.
     *
     * @param common
     * @param botanical
     * @param zone
     * @param light
     * @param price
     * @param availability
     */
    public void afegirPlanta(String common, String botanical, String zone, String light, double price, int availability) {
        try {
            
            //Se inicializa la expresion xq.
            xqe = con.createExpression();
            
            /*Creamos la sentencia indicandole que cree una nueva planta con los datos indicados, que
            seran los que le llega por parametro al metodo.*/
            String xq = "update insert "
                    + "    <PLANT>"
                    + "        <COMMON>" + common + "</COMMON>"
                    + "        <BOTANICAL>" + botanical + "</BOTANICAL>"
                    + "        <ZONE>" + zone + "</ZONE>"
                    + "        <LIGHT>" + light + "</LIGHT>"
                    + "        <PRICE>" + price + "</PRICE>"
                    + "        <AVAILABILITY>" + availability + "</AVAILABILITY>"
                    + "    </PLANT>\n"
                    + "preceding doc('/Exercici-1-M6-UF3/plantes.xml";

            //Ejecutamos la sentencia.
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Metodo para añadir un atributo a las plantas.
     *
     * @param atributo
     * @param valor
     */
    public void afegirAtribut(String atributo, String valor) {
        try {
            
            //Se inicializa la expresion xq.
            xqe = con.createExpression();
            
            /*Creamos la sentencia indicandole que inserte un nuevo atributo con el nombre y valor indicados
            por parametros.*/ 
            String xq = "update insert attribute " + atributo + " {'" + valor + "'} into doc('/Exercici-1-M6-UF3/plantes.xml')//PLANT";
            
            //Ejecutamos la sentencia.
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Metode per afegir una etiqueta.
     *
     * @param etiqueta
     * @param valor
     * @param zona
     */
    public void afegirEtiqueta(String etiqueta, String valor, String zona) {
        try {
            
            //Se inicializa la expresion xq.
            xqe = con.createExpression();
            
            /*Creamos la sentencia indicandole que inserte la etiqueta con el valor indicado.*/
            String xq = "update insert <" + etiqueta + ">'" + valor + "'</" + etiqueta + "> into doc('/Exercici-1-M6-UF3/plantes.xml')//libro";
            
            //Ejecutamos la sentencia.
            xqe.executeCommand(xq);

        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Metode per a cercar per preu.
     *
     * @param preuMinim
     * @param preuMaxim
     */
    public void cercarPerRangPreus(double preuMinim, double preuMaxim) {
        try {
            
            //Se inicializa la expresion xq.
            xqe = con.createExpression();
            
            /*Creamos la sentencia indicandole que recorra todo el archivo y en PRICE
            si coincide el precio entre el rango indicado lo devuelve.*/
            String xq = "for $b in doc ('/Exercici-1-M6-UF3/plantes.xml')//PLANT where every $a in $b/PRICE satisfies($a >= '" + preuMinim + "' and $a <= '" + preuMaxim + "') return $b";
            
            //Ejecutamos la sentencia.
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Metode per cercar plantes per zona.
     *
     * @param zona
     */
    public void cercarPerZona(String zona) {
        try {
            
            //Se inicializa la expresion xq.
            xqe = con.createExpression();
            
            /*Creamos la sentencia indicandole que recorra todo el archivo y en ZONE
            si coincide con la zona indicada lo devuelve*/
            String xq = "for $b in doc('/Exercici-1-M6-UF3/plantes.xml')"
                    + "//PLANT where every $a in $b/COMMON/ZONE satisfies ($a = '" + zona + "') return $b";
            
            //Ejecutamos la sentencia.
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Metode per modificar preu de una planta.
     *
     * @param nom
     * @param preu
     */
    public void modificarPreuPlanta(String nom, double preu) {

        try {
            
            //Se inicializa la expresion xq.
            xqe = con.createExpression();
            
            /*Creamos la sentencia indicandole que recorra todo el archivo y en COMMON
            si coincide con el nombre que le hemos pasado entonces se actualiza el precio.*/
            String xq = "for $b in doc('/Exercici-1-M6-UF3/plantes.xml')"
                    + "//PLANT where every $a in $b/COMMON satisfies ($a = '" + nom + "') "
                    + "return update value $b/PRICE with substring(" + preu + ")";
            
            //Ejecutamos la sentencia.
            xqe.executeCommand(xq);
            
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Metode per eliminar una planta.
     *
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
