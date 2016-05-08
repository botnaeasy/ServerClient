/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.View.Server;

import java.io.IOException;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import myproject.Model.Common.TableModels.Client2ServerTableModel;
import myproject.Model.Common.ExtendedComponents.ExtendedJTable;
import myproject.Model.Common.RemoteDesktop.RemoteDesktopManager;
import myproject.Model.Message.AbstractMessage;
import myproject.Model.Message.CommonMessages.StopSendingDesktopMessage;
import myproject.Model.Server.Client2Server;
import myproject.Model.Server.MultiThreadServer;
import myproject.View.FileManager.FileManagerPanel;
import myproject.View.Common.UniversalMainFrame;
import myproject.View.Console.ConsolePanel;
import myproject.View.RemoteDesktop.RemoteDesktopPanel;

/**
 *
 * @author BotNaEasyEnv
 */
public class ServerPanel extends javax.swing.JPanel {

    private MultiThreadServer server;
    private ExtendedJTable clientsTable = new ExtendedJTable();
    private Client2ServerTableModel clientsModel;
    private RemoteDesktopManager remoteDesktopManager;
    /**
     * Creates new form ServerPanel
     */
    public ServerPanel() {
        initComponents();
        initServer();
        initialize();
        reload();
    }
    
    private void initialize(){
        tablePanel.add(new JScrollPane(clientsTable));
        reload();
    }
    
    private void reload(){
        clientsModel = new Client2ServerTableModel(server.getConnectedClients());
        clientsTable.setModel(clientsModel);
    }
    
    
    public void updateForMessage(String message){
        reload();
    }
    /*
    private void send(){
        String message = messageField.getText();
        try{
            if(message.contains("exe")){
                server.sendExeTo(new ShowErrorMessage(message), 0);
            }
            server.sendTo(message, 0);
            messageField.setText("");
            chatTA.append("\n"+message);
        }catch(Exception e){
            chatTA.append("\nConnection error");
        }
    }*/
    
    private void initServer(){
        try {
            server = new MultiThreadServer(this);
            server.connect();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        testButton = new javax.swing.JButton();
        consoleButton = new javax.swing.JButton();
        remoteDesktopButton = new javax.swing.JButton();
        tablePanel = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.GridBagLayout());

        testButton.setText("File manager");
        testButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testButtonActionPerformed(evt);
            }
        });
        jPanel1.add(testButton, new java.awt.GridBagConstraints());

        consoleButton.setText("Console");
        consoleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consoleButtonActionPerformed(evt);
            }
        });
        jPanel1.add(consoleButton, new java.awt.GridBagConstraints());

        remoteDesktopButton.setText("Remote desktop");
        remoteDesktopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                remoteDesktopButtonActionPerformed(evt);
            }
        });
        jPanel1.add(remoteDesktopButton, new java.awt.GridBagConstraints());

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        tablePanel.setLayout(new java.awt.BorderLayout());
        add(tablePanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void testButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testButtonActionPerformed
            //clientsTable.getSelectedObjects();
            //UniversalMainFrame.main.showInInternalFrame("test");
            UniversalMainFrame.main.showInInternalFrame("File manager", new FileManagerPanel(clientsTable.getSelectedObject()), true);
    }//GEN-LAST:event_testButtonActionPerformed

    private void consoleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consoleButtonActionPerformed
        UniversalMainFrame.main.showInInternalFrame("Console manager", new ConsolePanel(clientsTable.getSelectedObject()), true);
    }//GEN-LAST:event_consoleButtonActionPerformed

    private void remoteDesktopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_remoteDesktopButtonActionPerformed
        Client2Server c2s = clientsTable.getSelectedObject();
        RemoteDesktopPanel panel = new RemoteDesktopPanel();
        remoteDesktopManager = new RemoteDesktopManager(c2s, panel);
        JInternalFrame frame = UniversalMainFrame.main.showMaximizedInInternalFrame("Remote Desktop - "+c2s.getClientHostName(),panel , true);
        setRemoteDesktopListener(frame);
        remoteDesktopManager.startRemoteDesktop();
    }//GEN-LAST:event_remoteDesktopButtonActionPerformed

    private void setRemoteDesktopListener(JInternalFrame frame){
        frame.addInternalFrameListener(new InternalFrameListener() {
            @Override
            public void internalFrameOpened(InternalFrameEvent ife) {
            }

            @Override
            public void internalFrameClosing(InternalFrameEvent ife) {
            }

            @Override
            public void internalFrameClosed(InternalFrameEvent ife) {
                remoteDesktopManager.stopRemoteDesktop();
            }

            @Override
            public void internalFrameIconified(InternalFrameEvent ife) {
            }

            @Override
            public void internalFrameDeiconified(InternalFrameEvent ife) {
            }

            @Override
            public void internalFrameActivated(InternalFrameEvent ife) {
            }

            @Override
            public void internalFrameDeactivated(InternalFrameEvent ife) {
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton consoleButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton remoteDesktopButton;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JButton testButton;
    // End of variables declaration//GEN-END:variables
}
