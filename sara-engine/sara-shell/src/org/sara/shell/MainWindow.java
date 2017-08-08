package org.sara.shell;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class MainWindow extends javax.swing.JFrame {

    public MainWindow() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar = new javax.swing.JMenuBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("No Title");
        setJMenuBar(jToolBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 317, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public JMenuItem addMenuItem(String menuText, javax.swing.JMenuItem item) {
        int i = jToolBar.getMenuCount();
        for (int j = 0; j < i; j++) {
            javax.swing.JMenu currentMenu = jToolBar.getMenu(j);
            if (currentMenu.getText().equals(menuText)) {

                return currentMenu.add(item);
            }

        }

        javax.swing.JMenu novoMenu = new JMenu(menuText);

        jToolBar.add(novoMenu);

        return novoMenu.add(item);
    }

    public void cleanMenuBar() {
        int menuCount = jToolBar.getMenuCount();
        if (menuCount > 2) {
            for (int j = 2; j < menuCount; j++) {
                jToolBar.remove(j);
            }
        }

        jToolBar.removeAll();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar jToolBar;
    // End of variables declaration//GEN-END:variables
}
