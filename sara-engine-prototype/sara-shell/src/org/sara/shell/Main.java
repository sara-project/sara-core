package org.sara.shell;

public class Main {

    public static void main(String[] args) {
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
