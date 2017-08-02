package org.sara.shell;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Runtime runTime = Runtime.getRuntime();
        System.out.println("Processor Cores: " + runTime.availableProcessors());
        Core.initialize();   
        Core.initPlugins();
    }
}
