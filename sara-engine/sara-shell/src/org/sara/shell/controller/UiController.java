package org.sara.shell.controller;

import org.sara.interfaces.IUiController;
import org.sara.shell.Utils;

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
    }
    
    @Override
    public void printMemoryInfo() {
        System.out.println( "Used Memory: " + Utils.maxUnit(Runtime.getRuntime().totalMemory()));
    }
}