/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Random;
import javax.swing.tree.TreeNode;
import myproject.Model.Common.FileManager.TreeModels.FileTreeNode;
import myproject.Model.Exception.ClientLogoutException;
import myproject.Model.Message.AbstractMessage;
import myproject.Model.Message.Client2ServerMessages.AbstractC2SMessage;
import myproject.Model.Message.CommonMessages.StopClientMessage;
import myproject.View.Server.ServerPanel;

/**
 *
 * @author BotNaEasyEnv
 */
public class Client2Server{
    
        private static final long serialVersionUID = 1L;
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
        
        private boolean isReception = true;
        private boolean isSending = true;
        
        private FileTreeNode clientFiles;
        
        private ServerPanel panel; 
        
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
        
        
        public void addRootChilds(Object[] files){
            File[] ff = new File[files.length];
            for(int i=0;i<files.length;i++){
                ff[i] = (File) files[i];
            }
            clientFiles.addNodes(ff);
        }
        
        public void sendCloseClientMessage() throws IOException{
             sendMessage(new StopClientMessage());
        }
        
        
        public void showMessage() throws  ClientLogoutException{
            try{
                AbstractMessage message = (AbstractMessage) inMessage.readObject();
                System.out.println("Serwer odebrał ("+getClientID()+"): "+ message.toString());
                checkClient2ServerMessage(message);
                panelMethod(message);
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

    public FileTreeNode getClientFiles() {
        return clientFiles;
    }

    public void setClientFiles(FileTreeNode clientFiles) {
        this.clientFiles = clientFiles;
    }
}
