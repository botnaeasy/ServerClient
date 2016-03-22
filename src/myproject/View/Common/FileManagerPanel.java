/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.View.Common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import myproject.Model.Common.ExtendedComponents.ExtendedJTree;
import myproject.Model.Common.FileManager.TreeModels.FileTreeModel;
import myproject.Model.Common.FileManager.Client2ServerFileManager;
import myproject.Model.Common.FileManager.TreeModels.FileTreeNode;
import myproject.Model.Server.Client2Server;

/**
 *
 * @author BotNaEasyEnv
 */
public class FileManagerPanel extends javax.swing.JPanel {

    /**
     * Creates new form FileManagerPanel
     */
    private ExtendedJTree tree;
    private Client2ServerFileManager manager;
    
    public FileManagerPanel(Client2Server c2s) {
        initComponents();
        initialize(c2s);
        listener();
    }
    
    private void initialize(Client2Server c2s){
         manager = new Client2ServerFileManager(c2s);
         tree = new ExtendedJTree(manager.getRoot());
         treePanel.add(new JScrollPane(tree));
    }
    
    private void listener(){
        tree.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if(me.getClickCount()>=2){
                    //tree.updateUI();
                    manager.sendFilesInfoRequest((FileTreeNode) tree.getSelectedNode());
                }
            }

            @Override
            public void mousePressed(MouseEvent me) {
                
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                
            }

            @Override
            public void mouseEntered(MouseEvent me) {
               
            }

            @Override
            public void mouseExited(MouseEvent me) {
                
            }
        });
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        filesPanel = new javax.swing.JPanel();
        treePanel = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        filesPanel.setLayout(new java.awt.BorderLayout());
        jSplitPane1.setRightComponent(filesPanel);

        treePanel.setLayout(new java.awt.BorderLayout());
        jSplitPane1.setLeftComponent(treePanel);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel filesPanel;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JPanel treePanel;
    // End of variables declaration//GEN-END:variables
}
