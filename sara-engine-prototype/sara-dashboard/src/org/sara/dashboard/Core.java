package org.sara.dashboard;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.sara.interfaces.IUiController;

public class Core {

    public Core() {
        uiController = new UiController();
    }
    public static Core getInstance() {
            return instance;
    }
    
    public static void initialize() {
        if (instance == null) {
            instance = new Core();

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
    }

    public IUiController getUiController() {
        return uiController;
    }

    protected static Core instance = null;
    private UiController uiController;
}
