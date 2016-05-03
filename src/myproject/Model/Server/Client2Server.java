/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import myproject.Model.Common.FileManager.TreeModels.FileTreeModel;
import myproject.Model.Common.FileManager.TreeModels.FileTreeNode;
import myproject.Model.Common.Listeners.ClientFileManagerListener;
import myproject.Model.Common.ToolObject;
import myproject.Model.Exception.ClientLogoutException;
import myproject.Model.Message.AbstractMessage;
import myproject.Model.Message.Client2ServerMessages.AbstractC2SMessage;
import myproject.Model.Message.Client2ServerMessages.FillClientInfoMessage;
import myproject.Model.Message.CommonMessages.StopClientMessage;
import myproject.View.Common.UniversalMainFrame;
import myproject.View.Server.ServerPanel;

/**
 *
 * @author BotNaEasyEnv
 */
public class Client2Server{
    
        private Socket socket;
        private BufferedReader socketReader;
        private PrintWriter socketWriter;
        private ObjectInputStream inMessage;
        private ObjectOutputStream outMessage;
        private int clientID;
        private String clientIP;
        private String clientHostName;
        private String clientOS;
        private String clientLanguage;
        private String javaVersion;
        private String clientArchitecture;
        
        private boolean isReception = true;
        private boolean isSending = true;
        
        private FileTreeModel model;
        
        private ServerPanel panel; 
        
        private ClientFileManagerListener listener;
     
        public Client2Server(Socket socket) throws IOException{
            this(socket, new Random().nextInt());
        }
        
        public void setPanel(ServerPanel panel){
            this.panel = panel;
        }
        
        public Client2Server(Socket socket, int id) throws IOException{
            this.socket = socket;
            this.clientID = id;
            socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            socketWriter = new PrintWriter(socket.getOutputStream());
            inMessage = new ObjectInputStream(socket.getInputStream());
            outMessage = new ObjectOutputStream(socket.getOutputStream());
        }
        
        public void close() throws IOException{
            socketWriter.close();
            socketReader.close();
            inMessage.close();
            outMessage.close();
            socket.close();
        }
        
        public void sendMessage(AbstractMessage mes) throws IOException{
            outMessage.writeObject(mes);
            outMessage.flush();
        }
        
        public void addRootChilds(File[] files){
            FileTreeNode root = (FileTreeNode) model.getRoot();
            for(int i=0;i<files.length;i++){
                model.insertNodeInto(new FileTreeNode(files[i], root), root, i);
            }
            model.reload();
            if(listener!=null){
                listener.onAddChilds(new ActionEvent(this, 0, "end of reloading"));
            }
        }
        
        public void addChildsToNode(File[] files, TreePath path){
            if(files==null){
                UniversalMainFrame.main.showErrorDialog("Directory blocked or not exists!");
                return;
            }
            FileTreeNode parent = ((FileTreeNode)model.getRoot()).findLastFromPath(path);
            for(int i=0;i<files.length;i++){
                model.insertNodeInto(new FileTreeNode(files[i], parent), parent, i);
            }
            model.reload();
            if(listener!=null){
                listener.onAddChilds(new ActionEvent(this, 0, "end of reloading"));
            }
        }  
        public void sendCloseClientMessage() throws IOException{
             sendMessage(new StopClientMessage());
        }
        
        public void removeNode(FileTreeNode node){
            model.removeNode(node);
            model.reload();
            if(listener!=null){
                listener.onDeleteChilds(new ActionEvent(this, 0, "remove child"));
            }
        }
        
        public void DeleteFileMessage(String ex, String cause){
            UniversalMainFrame.main.showErrorDialog(clientHostName+": "+ex +" cause: "+cause);
        }
        
        public void showMessage() throws  ClientLogoutException{
            try{
                AbstractMessage message = (AbstractMessage) inMessage.readObject();
                System.out.println("Serwer odebrał ("+getClientID()+"): "+ message.toString());
                checkClient2ServerMessage(message);
                if(message instanceof FillClientInfoMessage) {
                        panelMethod(message);
                }
            }catch(Exception e){
                throw new ClientLogoutException();
            }
        }
        
        private void checkClient2ServerMessage(AbstractMessage message){
            if(message instanceof AbstractC2SMessage){
                executeC2SMessage((AbstractC2SMessage) message);
            }
        }
        
        private void executeC2SMessage(AbstractC2SMessage message){
            message.executeOnC2S(this);
        }

        public int getClientID() {
            return clientID;
        }

        private void panelMethod(AbstractMessage message){
            if(panel==null)
                return;

            panel.updateForMessage(message.toString());
        }

        public String getClientIP() {
            return clientIP;
        }

        public void setClientIP(String clientIP) {
            this.clientIP = clientIP;
        }

        public boolean isIsReception() {
            return isReception;
        }

        public void setIsReception(boolean isReception) {
            this.isReception = isReception;
        }

        public boolean isIsSending() {
            return isSending;
        }

        public void setIsSending(boolean isSending) {
            this.isSending = isSending;
        }

    public String getClientHostName() {
        return clientHostName;
    }

    public void setClientHostName(String clientHostName) {
        this.clientHostName = clientHostName;
    }

    public String getClientOS() {
        return clientOS;
    }

    public void setClientOS(String clientOS) {
        this.clientOS = clientOS;
    }

    public String getClientLanguage() {
        return clientLanguage;
    }

    public void setClientLanguage(String clientLanguage) {
        this.clientLanguage = clientLanguage;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public void setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
    }

    public DefaultTreeModel getModel() {
        return model;
    }

    public void setModel(FileTreeModel model) {
        this.model =  model;
    }
    
    public void saveFile(byte[] fileContent, File file, boolean open, String directory){
         String regAdd = getClientHostName();
         ToolObject.saveFileTemp(fileContent, file, regAdd, open, directory);
         listener.onDownloadFinish(new ActionEvent(this, 0, "download file finished"));
    }


    public void addClientFileManagerListener(ClientFileManagerListener listener) {
        this.listener = listener;
    }
    
    public void setClientArchitecture(String arch){
        this.clientArchitecture = arch;
    }
    
    public String getClientArchitecture(){
        return clientArchitecture;
    }
}
