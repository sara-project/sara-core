package org.sara.shell;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;
import org.sara.interfaces.model.Room;
import org.sara.interfaces.model.Schedule;
import org.sara.interfaces.model.SchoolClass;
import org.sara.interfaces.model.Slot;

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
        System.out.println("Models Information");
        System.out.println();

        System.out.println("- Starting loading models...");

        HashMap<String, Schedule> schedulesHash = new HashMap<>();
        HashMap<String, Slot> slotsHash = new HashMap<>();
        HashMap<String, SchoolClass> classesHash = new HashMap<>();
        HashMap<String, Room> roomsHash = new HashMap<>();

        //This block will load the models
        {
            try {
                JSONHandler handler = new JSONHandler(jsonFile);
                schedulesHash = handler.getSchedulesHash();
                slotsHash = handler.getSlotsHash();
                classesHash = handler.getClassesHash();
                roomsHash = handler.getRoomsHash();

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
        
        System.out.println(" - " + schedulesHash.size() + " schedules;");
        System.out.println(" - " + slotsHash.size() + " slots;");
        System.out.println(" - " + classesHash.size() + " classes;");
        System.out.println(" - " + roomsHash.size() + " rooms.");
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
        
        System.out.println();
        System.out.print("- ");
        Core.initialize();
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
