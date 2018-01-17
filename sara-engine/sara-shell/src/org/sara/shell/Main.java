package org.sara.shell;

import java.io.IOException;
import java.util.Date;
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
        
        int availableProcessors = Runtime.getRuntime().availableProcessors();

        String osName = System.getProperty("os.name");
        String jsonFile = args[0];
        Date startDate, endDate;
        startDate = new Date();
        
        System.out.println();
        System.out.println("----------------------------------------- ");
        System.out.println("System Information");
        System.out.println();
        System.out.println("- Operational System: " + osName);
        System.out.println("- Available Processors: " + availableProcessors);
        
        System.out.println();
        System.out.println("----------------------------------------- ");
        System.out.println("Initializing the Core");
        System.out.println();
        Core.initialize();
        
        System.out.println();
        System.out.println("----------------------------------------- ");
        System.out.println("Models Information");
        System.out.println();

        System.out.println("- Starting loading models...");

        //This block will load the models
        {
            try {
                JSONHandler handler = new JSONHandler(jsonFile);
                Core.getInstance().getModelController().setSchedules(handler.getSchedulesHash());
                Core.getInstance().getModelController().setSlots(handler.getSlotsHash());
                Core.getInstance().getModelController().setSchoolClass(handler.getClassesHash());
                Core.getInstance().getModelController().setRooms(handler.getRoomsHash());
                Core.getInstance().getModelController().setGaConfiguration(handler.getGAConfiguration());

            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }//Releases the json handler after this block executes
        
        System.out.println("  ...loading completed successfully!");
        System.out.println();
        
        System.out.println(" - " + Core.getInstance().getModelController().getSchedules().size() + " schedules;");
        System.out.println(" - " + Core.getInstance().getModelController().getSlots().size() + " slots;");
        System.out.println(" - " + Core.getInstance().getModelController().getSchoolClass().size() + " classes;");
        System.out.println(" - " + Core.getInstance().getModelController().getRooms().size() + " rooms.");
        System.out.println();
        System.out.println(" - Genetic Algorithm Configuration");
        System.out.println(Core.getInstance().getModelController().getGaConfiguration());
        System.out.println();
        
        System.out.println();
        System.out.println("----------------------------------------- ");
        System.out.println("Manager Plugins");
        System.out.println();
        
        try {
            System.out.print("- Starting copying of plugins... ");
            if (osName.toUpperCase().contains("LINUX") || osName.toUpperCase().contains("MAC"))
                Runtime.getRuntime().exec("sh refresh_plugins.sh");
            else
                throw new Exception("The script for windows is not implemented.");
            
            System.out.println("  ... the script was successful!");
        } catch (Exception ex) {
            System.err.println("Failed to execute the script that copies the plug-in.");
            System.err.println(ex.getMessage());
        }

        Core.initPlugins();
        
        System.out.println();
        System.out.println("----------------------------------------- ");
        System.out.println("Initialize System");
        System.out.println();
        
        Core.getInstance().getProjectController().getGAEngine().startGA();

        System.out.println("Progress Bar");
        System.out.print("[");
        for(int i = 0; i < 100; i++) {
            if(i < 20)    
                System.out.print("#");
            else
                System.out.print(".");
        }

        System.out.print("] " + "20%");
        System.out.println();
        
        endDate = new Date();
        System.out.println("Start Date: " + startDate);
        System.out.println("End Date: " + endDate);
    }
}
