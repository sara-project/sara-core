package org.sara.shell;

import org.sara.interfaces.IUiController;

public class UiController implements IUiController {

    @Override
    public void printProgressBar( int currentValue, int maxValue ) {
        String progressBar = "";
        double percent = ( currentValue * 1.0 ) / ( maxValue * 1.0 );

        for (int i = 0; i < 20; i++)
            if (( (int) ( 20 * percent ) ) > i) {
                progressBar += "#";
            } else {
                progressBar += " ";
            }

        progressBar = "[" + progressBar + "] " + ( (int) ( percent * 100 ) ) + "%";
        progressBar += " - (" + currentValue + "/" + maxValue + ")";
        System.out.print( progressBar + "\r" );
        
        amountMemory += (Runtime.getRuntime().maxMemory() - Runtime.getRuntime().freeMemory());
        count++;
    }
    
    @Override
    public void printMemoryInfo() {
        System.out.println( "Total Used Memory: " + maxUnit(Runtime.getRuntime().totalMemory()));
        System.out.println( "Average Used Memory: " + maxUnit(amountMemory / count));
    }
    
    private String maxUnit(double size) {
        String[] units  = {"B", "kB", "MB", "GB", "TB", "PB"} ;
        int unit = 0;
        
        while(size > 1024) {
            size = size / 1024;
            unit++;
        }
        return String.format("%.2f %s",  size, units[unit]);
    }
    
    private long amountMemory = 0;
    private long count = 1;
}