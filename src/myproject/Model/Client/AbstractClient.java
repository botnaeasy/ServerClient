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
    ///server info
    protected int PORT = 6000;
    protected String HOST = "83.30.127.135";
    protected Socket socket;
    ///streams
    protected BufferedReader socketReader;
    protected PrintWriter socketWriter;
    protected ObjectOutputStream outMessage;
    protected ObjectInputStream inMessage;
    /////
    protected AbstractClient client;
    ///client ino
    protected boolean isConnected;
    protected int clientID;
    protected String clientIP;
    protected String hostName;
    protected String clientOS;
    protected String javaVersion;
    protected String clientLanguage;
    /////logger
    protected Log logger;
    
    public AbstractClient(){
        this.client = this;
        isConnected = false;
        setFullClientInfo();
    }
    
    private void setFullClientInfo(){
        try {
            setHostName();
            setIP();
            setOS();
            setJavaVersion();
            setClientLanguage();
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
    }
    
    private void setClientLanguage(){
        setClientLanguage(System.getProperty("user.language")+"- "+System.getProperty("user.country"));
    }
    
    private void setJavaVersion(){
        setJavaVersion(System.getProperty("java.version"));
    }
    
    private void setOS(){
        setClientOS(System.getProperty("os.name"));
    }
    
    private void setHostName() throws UnknownHostException{
            this.hostName = InetAddress.getLocalHost().getHostName();
    }
    
        private void setIP() throws UnknownHostException{
            this.clientIP = InetAddress.getLocalHost().getHostAddress();
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

    public String getHostName() {
        return hostName;
    }

    public String getClientOS() {
        return clientOS;
    }

    public void setClientOS(String clientOS) {
        this.clientOS = clientOS;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public void setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
    }

    public String getClientLanguage() {
        return clientLanguage;
    }

    public void setClientLanguage(String clientLanguage) {
        this.clientLanguage = clientLanguage;
    }
    
    
    
}
