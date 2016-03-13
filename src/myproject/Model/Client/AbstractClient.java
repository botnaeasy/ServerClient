/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import myproject.Model.Exception.ServerLogoutException;
import myproject.Model.Informator.Log;
import myproject.Model.Message.AbstractMessage;
import myproject.Model.Message.CommonMessages.TextMessage;

/**
 *
 * @author BotNaEasyEnv
 */
public abstract class AbstractClient implements Serializable {
    
    public static final int SLEEP_TIME = 5000;
    
    protected int PORT = 6000;
    protected String HOST = "83.30.127.135";
    protected Socket socket;
    
    protected BufferedReader socketReader;
    protected PrintWriter socketWriter;
    protected ObjectOutputStream outMessage;
    protected ObjectInputStream inMessage;
    
    protected AbstractClient client;
    
    protected boolean isConnected;
    protected int clientID;
    protected String clientIP;
    
    protected Log logger;
    
    public AbstractClient(){
        this.client = this;
        isConnected = false;
        setIP();
    }
    
    public void setServer(String host, int port){
        setHOST(host);
        setPORT(port);
    }
    public void setServer(String host){
        setHOST(host);
    }
    public void setServer(int port){
        setPORT(port);
    }
    
    private void connect(String host, int port) throws IOException{
        this.PORT = port;
        this.HOST = host;
        connect();
        isConnected = true;
    }
    
    protected void reconnect(){
        try {
            Log.log("Reconnecting...");
            reconnecting();
            softDisconnect();
            newConnectThread();
        } catch (IOException ex) {
            Log.errorLog(ex, ex.getCause(),AbstractClient.class);
        }
    }
    
    protected abstract void reconnecting();
    
    public void tryToConnect(){
        startLogging();
        newConnectThread();
        //setIp();
    }
    
    private void startLogging(){
        logger = new Log(this);
        logger.newLoggerThread();
    }
    
    private void newConnectThread(){
        Thread t = new Thread(){
            @Override
            public void run(){
                boolean isConnected = false;
                while(!isConnected){
                    try {
                        Thread.sleep(SLEEP_TIME);
                        connect(HOST, PORT);
                        isConnected = true;
                    } catch (IOException ex) {
                        Log.errorLog(ex, ex.getCause(), AbstractClient.class);
                    } catch (InterruptedException ex) {
                        Log.errorLog(ex, ex.getCause(),AbstractClient.class);
                    }
                }
            }
        };
        t.setName("Connect thread");
        t.start();
    }
    
    private void connect() throws IOException{
        socket = new Socket(HOST, PORT);
        
        socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        socketWriter = new PrintWriter(socket.getOutputStream());
        
        outMessage = new ObjectOutputStream(socket.getOutputStream());
        inMessage = new ObjectInputStream(socket.getInputStream());
        
        startSending();
        startRecepting();
    }
    
    private void setIP(){
        try {
            this.clientIP = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
    }
    
    private void softDisconnect() throws IOException{
        isConnected = false;
        socketWriter.close();
        socketReader.close();

        inMessage.close();
        outMessage.close();
        socket.close();
    }
    
        public void disconnect() throws IOException{
            softDisconnect();
           logger.setIsLogging(false);
    }
    
    public void sendMessage(AbstractMessage mes){
        try {
            outMessage.writeObject(mes);
            outMessage.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    } 
    
    public void sendMessage(String message){
        sendMessage(new TextMessage(message));
    }
      
     public AbstractMessage getMessage() throws ServerLogoutException{
        try {
            AbstractMessage message = (AbstractMessage) inMessage.readObject();
            return message;
        } catch (Exception ex) {
            throw new ServerLogoutException();
        } 
    } 
     
   public int getPORT() {
        return PORT;
    }

    public void setPORT(int PORT) {
        this.PORT = PORT;
    }

    public String getHOST() {
        return HOST;
    }

    public void setHOST(String HOST) {
        this.HOST = HOST;
    }  
     
    protected abstract void startSending();
    protected abstract void startRecepting();

    public boolean isIsConnected() {
        return isConnected;
    }   

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getClientIP() {
        return clientIP;
    }
 
}
