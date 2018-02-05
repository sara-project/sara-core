package org.sara.shell;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;
import org.sara.interfaces.model.Slot;

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

        //Workaround to ensure that previous script has completed
        try {
            Thread.sleep( 500 );
        } catch (InterruptedException ex) {
            System.out.println( "Erro: " + ex.getMessage() );
        }

        System.out.println( "\n----------------------------------------- " );
        System.out.println( "Initializing the Core\n" );
        Core.initialize();
        Core.initPlugins();

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
        System.out.println( "Initialize System\n" );

        System.out.println( "The evolutionary cyle was started." );
        List<Slot> solucion = Core.getInstance().getProjectController().getGAEngine().startGA();
        System.out.println();

        endDate = new Date();
        System.out.println( "Start Date: " + startDate );
        System.out.println( "End Date: " + endDate );
    }

    private static boolean validateArgs( String[] args ) {
        String msg = "";
        if (args.length == 0) {
            msg = "No parameters found. Please type: sara-core \"filename\" to execute rigth.";
        }

        if (!msg.isEmpty()) {
            System.err.println( msg );
        }

        return msg.isEmpty();
    }
}
