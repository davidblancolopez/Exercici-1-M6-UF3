
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

    public List<Node> obtenirPlantes() {
        List<Node> plantes = new ArrayList<>();
        try {
            xqe = con.createExpression();
            String xq = "for $b in doc ('/Exercici-1-M6-UF3/plantes.xml')//planta return $b/titulo";

            XQResultSequence rs = xqe.executeQuery(xq);
            while (rs.next()) {
                plantes.add(rs.getItem().getNode());
            }
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
        return plantes;
    }

    
    public Node cercarNom(String nom) {
        Node libro = null;
        try {
            xqe = con.createExpression();
            String xq = "for $b in doc('/Exercici-1-M6-UF3/plantes.xml')"
                    + "//libro where every $a in $b/titulo satisfies ($a = '" + nom + "') return $b";

            XQResultSequence rs = xqe.executeQuery(xq);
            rs.next();
            libro = rs.getItem().getNode();
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
        return libro;
    }

    public void afegirPlanta(String common, String botanical, String zone, String light, double price, int availability) {
        try {
            xqe = con.createExpression();
            String xq = "update insert "
                    + "    <PLANT>"
                    +          "<COMMON>" + common + "</COMMON>"
                    + "        <BOTANICAL>" + botanical + "</BOTANICAL>"
                    + "        <ZONE>" + zone + "</ZONE>"
                    + "        <LIGHT>" + light + "</LIGHT>"
                    + "        <PRICE>" + price + "</PRICE>"
                    + "        <AVAILABILITY>" + availability + "</AVAILABILITY>"
                    + "    </PLANT>\n"
                    + "into doc('/Exercici-1-M6-UF3/plantes.xml";

            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void afegirAtribut(String atributo, String valor) {
        try {
            xqe = con.createExpression();
            String xq = "update insert attribute " + atributo + " {'" + valor + "'} into doc('/m06uf3/libros.xml')//libro";
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void afegirEtiqueta(String etiqueta, String valor) {
        try {
            xqe = con.createExpression();
            String xq = "update insert <" + etiqueta + ">'" + valor + "'</" + etiqueta + "> into doc('/m06uf3/libros.xml')//libro";
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

    public void eliminarLlibre(String codigo) {

        try {
            xqe = con.createExpression();
            String xq = "update delete doc('/m06uf3/libros.xml')//libro[@codigo='" + codigo + "']";
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

}
