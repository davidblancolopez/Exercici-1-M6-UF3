/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exercici.pkg1.m6.uf3;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import net.xqj.exist.ExistXQDataSource;

/**
 *
 * @author ALUMNEDAM
 */
public class ConfigConnexio {

    private XQDataSource xqs;
    private XQConnection con;

    public ConfigConnexio() {
        configConnexio();
    }

    private void configConnexio() {
        try {
            xqs = new ExistXQDataSource();
            xqs.setProperty("serverName", "localhost");
            xqs.setProperty("port", "8080");
            con = xqs.getConnection("admin", "admin");
        } catch (XQException e) {
            System.out.println(e.getMessage());
        }
    }

    public XQConnection getCon() {
        return con;
    }

    public void propietatsBD() {
        String propietats[] = xqs.getSupportedPropertyNames();
        System.out.println("Propietats de XQData" + Arrays.toString(propietats));
        try {
            System.out.println("IsClosed: " + con.isClosed());
            for (String propietat : propietats) {
                System.out.println(propietat + "=\t" + xqs.getProperty(propietat));
            }
        } catch (XQException ex) {
            Logger.getLogger(ConfigConnexio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
