
package exercici.pkg1.m6.uf3;

import java.util.List;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;


public class Exercici_1_M6_UF3 {

    public static void imprimirLibros(List<Node> ns) {
        for (Node n : ns) {
            System.out.println(n.getTextContent());
        }
    }

    private static void imprimiLibro(Node libro) {
        if (libro != null) {
            NamedNodeMap attributes = libro.getAttributes();

            for (int i = 0; i < attributes.getLength(); i++) {
                System.out.println(attributes.item(i).getNodeValue());
            }

            System.out.println(libro.getTextContent());
        }
    }

    public static void main(String[] args) {

//        ConfigConnexio cc = new ConfigConnexio();
//        Consultes cs = new Consultes(cc.getCon());
//
//        //Llibre
//        String codigo = "16-205";
//        String categoria = "BBDD";
//        String fecha_pub = "2017-03-19";
//        String titulo = "BBDD XML con eXist";
//        String ventas = "7";
//
//        String codigo2 = "16-041";
//        String precio = "50€";
//        String etiqueta = "preu";
//        String atributo = "disponible";
//        String valor = "S";
//        String valor1 = "0€";
//
////        cs.afegirLlibre(codigo, categoria, fecha_pub, titulo, ventas);
////        cs.afegirAtribut(atributo, valor);
////
////        cs.afegirEtiqueta(etiqueta, valor1);
////        cs.modificarPreuNode(codigo2, precio);
//        //LLISTAR LLIBRES
//        //imprimirLibros(cs.obtenirLlibres());
//        //CERCAR PER TITOL
//        imprimiLibro(cs.cercarNom("instant html"));
//
////        cs.eliminarEtiqueta(etiqueta);
////        cs.eliminarAtribut(atributo);
//        cs.eliminarLlibre(codigo);
//        imprimirLibros(cs.obtenirPlantes());

    }

}
