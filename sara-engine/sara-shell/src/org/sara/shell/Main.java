package org.sara.shell;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;

public class Main {

    public static void main(String[] args) {
        
        args = new String[1];
        args[0] = "./exemplo_request.json";
        if(args.length <= 0) {
            System.err.println("Missing args...");
            return ;
        }
        
        JSONHandler handler = new  JSONHandler();
        try {
            handler.LoadJson(args[0]);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        String osName = System.getProperty("os.name");

        try {
            System.out.print("Starting copying of plugins... ");
            if (osName.toUpperCase().contains("LINUX") || osName.toUpperCase().contains("MAC"))
                Runtime.getRuntime().exec("sh refresh_plugins.sh");
            else
                throw new Exception("The script for windows is not implemented.");
            
            System.out.println("the script was successful.");
        } catch (Exception ex) {
            System.err.println("fail to executing script that ails plugins.");
            System.err.println(ex.getMessage());
        }

        Core.initialize(availableProcessors);
        Core.initPlugins();
        
    }
}
