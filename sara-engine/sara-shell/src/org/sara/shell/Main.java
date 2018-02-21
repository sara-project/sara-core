package org.sara.shell;

import org.sara.shell.controller.ProjectController;
import org.sara.shell.jsonhandler.JSONReader;
import org.sara.shell.jsonhandler.JSONWriter;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;
import org.sara.interfaces.ICore;
import org.sara.interfaces.algorithms.ga.operator.IGALightSwitch;

public class Main {

    public static void main( String[] args ) {

        if (!Main.validateArgs( args )) {
            System.exit( 1 );
        }

        int availableProcessors = Runtime.getRuntime().availableProcessors();

        String osName = System.getProperty( "os.name" );
        String jsonFile = args[0];
        Date startDate, endDate;
        startDate = new Date();

        System.out.println();
        System.out.println( "----------------------------------------- " );
        System.out.println( "System Information" );
        System.out.println();
        System.out.println( "- Operational System: " + osName );
        System.out.println( "- Available Processors: " + availableProcessors );

        System.out.println();
        System.out.println( "----------------------------------------- " );
        System.out.println( "Manager Libraries and Plugins" );
        System.out.println();

        try {
            System.out.print( "- Starting copy ... " );
            if (osName.toUpperCase().contains( "LINUX" ) || osName.toUpperCase().contains( "MAC" )) {
                Runtime.getRuntime().exec( "sh scripts/get_libs.sh" );
                Runtime.getRuntime().exec( "sh scripts/refresh_plugins.sh" );
            } else {
                throw new Exception( "The script for windows is not implemented." );
            }

            System.out.println( "  ... copy was completed successful." );
        } catch (Exception ex) {
            System.err.println( "Failed to execute the scripts ..." );
            System.err.println( ex.getMessage() );
        }
        
        System.out.println( "\n----------------------------------------- " );
        System.out.println( "Initializing the Core\n" );
        Core.initialize();

     
        System.out.println( "\n----------------------------------------- " );
        System.out.println( "Models Information\n" );

        System.out.println( "- Starting loading models..." );

        //This block will load the models
        {
            try {
                JSONReader handler = new JSONReader( jsonFile );
                Core.getInstance().getModelController().setSchedules( handler.getSchedulesHash() );
                Core.getInstance().getModelController().setSlots( handler.getSlotsHash() );
                Core.getInstance().getModelController().setSchoolClass( handler.getClassesHash() );
                Core.getInstance().getModelController().setClassSchedule( handler.getClassSchedulesHash() );
                Core.getInstance().getModelController().setRooms( handler.getRoomsHash() );
                Core.getInstance().getModelController().setGaConfiguration( handler.getGAConfiguration() );

            } catch (IOException | ParseException ex) {
                Logger.getLogger( Main.class.getName() ).log( Level.SEVERE, null, ex );
            } catch (Exception ex) {
                Logger.getLogger( Main.class.getName() ).log( Level.SEVERE, null, ex );
            }
        }//Releases the json handler after this block executes

        System.out.println( "  ...loading completed successfully!" );
        System.out.println();

        int size;
        System.out.println( " - " + ( size = Core.getInstance().getModelController().getSchedules().size() )
                + " schedule" + ( size > 1 ? "s;" : ";" ) );
        System.out.println( " - " + ( size = Core.getInstance().getModelController().getSlots().size() )
                + " slot" + ( size > 1 ? "s;" : ";" ) );
        System.out.println( " - " + ( size = Core.getInstance().getModelController().getSchoolClass().size() )
                + " class" + ( size > 1 ? "es;" : ";" ) );
        System.out.println( " - " + ( size = Core.getInstance().getModelController().getClassSchedule().size() )
                + " class schedule" + ( size > 1 ? "s;" : ";" ) );
        System.out.println( " - " + ( size = Core.getInstance().getModelController().getRooms().size() )
                + " room" + ( size > 1 ? "s;" : ";" ) );

        System.out.println( "\n - Genetic Algorithm Configuration" );
        System.out.println( Core.getInstance().getModelController().getGaConfiguration() + "\n" );
        
        
        System.out.println( "\n----------------------------------------- " );
        System.out.println( "Initializing Plugins \n" );
        Core.initPlugins();


        System.out.println( "\n----------------------------------------- " );
        System.out.println( "Initialize System\n" );

        System.out.println( "The evolutionary cyle was started." );
        {
            IGALightSwitch result = Core.getInstance().getProjectController().getGAEngine().startGA();
            
            try {
                new JSONWriter().writeSolution( "saida.json", result.getBestSolution(), result.getFitnessTimeLine().toArray(new Float[0]));
            } catch (ParseException ex) {
                Logger.getLogger( Main.class.getName() ).log( Level.SEVERE, null, ex );
            } catch (Exception ex) {
                Logger.getLogger( Main.class.getName() ).log( Level.SEVERE, null, ex );
            }
        }
        System.out.println();

        endDate = new Date();
        System.out.println( "Start Date: " + startDate );
        System.out.println( "End Date: " + endDate );
        
        System.out.println();
        ICore.getInstance().getUiController().printMemoryInfo();
    }

    private static boolean validateArgs( String[] args ) {
        String msg = "";
        String syntax = "Please type: sara-core \"filename\" [-d or debug] to execute rigth.";
        if (args.length == 0) {
            msg = "No parameters found. "+ syntax;
        }
        
        if(args.length == 2) {
            if(!args[1].toLowerCase().equals( "-d") &&  !args[1].toLowerCase().equals( "-debug")) {
               msg = "Wrong parameters. "+ syntax;
               ProjectController.DEBUG_INFO_AG = false; 
            }
            else
                ProjectController.DEBUG_INFO_AG = true; 
        }

        if (!msg.isEmpty()) {
            System.err.println( msg );
        }

        return msg.isEmpty();
    }
}
