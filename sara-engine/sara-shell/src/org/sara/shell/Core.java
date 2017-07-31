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

public class Core extends ICore {

    public static void initialize() {
        if (instance == null) {
            instance = new Core();
        }
    }

    public IUiController getUiController() {
        return uiController;
    }

    public IProjectController getProjectController() {
        return projectController;
    }

    protected Core() {
        uiController = new UiController();
        projectController = new ProjectController();
    }

    public static void initPlugins() {
        initGeneralPlugins();
    }
    public static void initGeneralPlugins() {

        File currentDir = new File("./plugins");
        
        if(!currentDir.exists())
            currentDir.mkdir();
        
        String[] plugins = currentDir.list();
        int i;
        URL[] jars = new URL[plugins.length];
        for (i = 0; i < plugins.length; i++) {

            System.out.println(i + 1 + " - " + plugins[i].split("\\.")[0]);

            try {
                jars[i] = (new File("./plugins/" + plugins[i])).toURL();
            } catch (MalformedURLException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
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
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
            getInstance().getProjectController().addNameActivePlugin(tempName);
        }

        new Thread() {

            @Override
            public void run() {
                try {
                    this.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Core.class.getName()).log(Level.SEVERE, null, ex);
                }
                Core.getInstance().getUiController().repaintWindow();
            }
        }.start();
    }

    private UiController uiController;
    private ProjectController projectController;
}
