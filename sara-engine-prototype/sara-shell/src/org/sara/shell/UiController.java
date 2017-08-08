package org.sara.shell;

import org.sara.interfaces.IUiController;
import javax.swing.JFrame;
import javax.swing.JMenuItem;

public class UiController implements IUiController {

    public UiController() {
        mainWindow = new MainWindow();
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                mainWindow.setVisible(true);
            }
        });
    }

    @Override
    public JMenuItem addMenuItem(String menuText, JMenuItem item) {
        return mainWindow.addMenuItem(menuText, item);
    }

    @Override
    public void showWindow(JFrame window, Boolean visible) {
        window.setVisible(visible);
    }

    @Override
    public void refreshPlugins() {
        mainWindow.cleanMenuBar();
    }

    @Override
    public void repaintWindow() {
        mainWindow.revalidate();
        mainWindow.repaint();
        mainWindow.revalidate();
    }

    @Override
    public void removeMenu(JMenuItem item) {
        mainWindow.remove(item);
    }

    private MainWindow mainWindow;
}
