package org.sara.interfaces;

import javax.swing.*;

public interface IUiController {

    public abstract JMenuItem addMenuItem(String menuText, javax.swing.JMenuItem item);

    public abstract void showWindow(JFrame window, Boolean visible);

    public void removeMenu(javax.swing.JMenuItem item);

    public void refreshPlugins();

    public void repaintWindow();
}
