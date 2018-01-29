package org.sara.shell;

import org.sara.interfaces.ICore;
import org.sara.interfaces.IPlugin;
import org.sara.interfaces.IProjectController;
import org.sara.interfaces.IUiController;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sara.interfaces.IModelController;

public class Core extends ICore {

    public static void initialize() {
        if (instance == null) {
            instance = new Core();
        }
    }

    @Override
    public IUiController getUiController() {
        return uiController;
    }

    @Override
    public IProjectController getProjectController() {
        return projectController;
    }
    
    @Override
    public IModelController getModelController() {
        return modelcontroller;
    }

    protected Core() {
        uiController = new UiController();
        projectController = new ProjectController();
        modelcontroller =  new ModelController();
        pluginsActived = false;
    }

    public static void initPlugins() {
        if(!pluginsActived)
            initGeneralPlugins();
    }
    
    private static void initGeneralPlugins() {

        File currentDir = new File("./plugins");
        
        if(!currentDir.exists())
            currentDir.mkdir();
        
        String[] plugins = currentDir.list();
        int i;
        URL[] jars = new URL[plugins.length];
        System.out.println("Loading plugins...");
        for (i = 0; i < plugins.length; i++) {

            System.out.println("\t" + i + 1 + " - " + plugins[i].split("\\.")[0]);

            try {
                jars[i] = (new File("./plugins/" + plugins[i])).toURL();
            } catch (MalformedURLException ex) {
                System.err.println(ex.getMessage());
            }
        }
        URLClassLoader ulc = new URLClassLoader(jars);
        getInstance().getProjectController().setDataJars(ulc);
        for (i = 0; i < plugins.length; i++) {
            String factoryName = plugins[i].split("\\.")[0];
            String tempName = "";
            try {
                tempName = "org.sara." + factoryName.toLowerCase() + "." + factoryName;
                IPlugin plugin = (IPlugin) Class.forName(tempName, true, ulc).newInstance();
                plugin.initialize();
                projectController.addNameActivePlugin(factoryName);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                 System.err.println(ex.getMessage());
            }
            getInstance().getProjectController().addNameActivePlugin(tempName);
        }
        System.out.println("Load completed...");
        new Thread() {

            @Override
            public void run() {
                try {
                    this.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Core.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
    }
    
    private static boolean pluginsActived;
    private static UiController uiController;
    private static ProjectController projectController;
    private static ModelController modelcontroller;
}
