/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.philgooch;

import java.io.*;
import java.util.*;
import java.net.URL;

/**
 *
 * @author philipgooch
 */
public class ConfigParser {

    private URL configURL;
    private HashMap<String, String> options;

    public URL getConfigURL() {
        return configURL;
    }

    public HashMap<String, String> getOptions() {
        return options;
    }

    public void setConfigURL(URL configURL) {
        this.configURL = configURL;
    }

    public ConfigParser() {
        this.options = new HashMap<String, String>();
    }

    /**
     *
     * @param configURL - path to configuration file
     */
    public ConfigParser(URL configURL) {
        this();
        this.configURL = configURL;
    }

    /**
     *
     * @param prop - name of package properties file that describes the
     *                k/v pairs expected in the config file.
     *                Config file will be in the form
     *                   TableName = BillOfMaterial
     *                   EntryName = Product
     *                   Order = row
     *                   CellType = String -> Item
     *                   CellType = Number -> Quantity
     *                   CellType = Money -> Amount
     *                   CellType = Money -> Total
     *
     *                  but this can be configured via the properties file
     * @return true if options populated successfully
     */
    public boolean populateOptions(String prop) {

        BufferedReader in = null;
        String inputLine = null;

        ResourceBundle props = null;
        String kVSplit = null;
        String whitespace = null;
        try {
            props = ResourceBundle.getBundle(prop);
            whitespace = props.getString("whitespace");
            kVSplit = props.getString("kVSplit");
        } catch (MissingResourceException me) {
            return false;
        }

        try {
            in = new BufferedReader(
                    new InputStreamReader(
                    configURL.openStream()));

            while ((inputLine = in.readLine()) != null) {
                if (!inputLine.equals("") && !inputLine.startsWith("#")) {
                    inputLine = inputLine.replaceAll(whitespace, "");
                    String[] opt = inputLine.split(kVSplit);
                    if (opt.length == 2) {
                       options.put(opt[0], opt[1]);
                    } else {
                        // malformed mapping file
                        throw new IOException("Malformed configuration file: " + configURL);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException ie) {
            return false;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException iee) {
                    return false;
                }
            }
        }

        return true;
    }
}
