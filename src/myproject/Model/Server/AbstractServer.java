/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Server;

import java.io.IOException;
import java.net.ServerSocket;
import myproject.Model.Logger.Log;
import myproject.Model.Message.AbstractMessage;

/**
 *
 * @author BotNaEasyEnv
 */
public abstract class AbstractServer {
    protected int PORT = 6000;
    protected ServerSocket serverSocket;
    protected Log logger;

    public void connect(int port) throws IOException{
        this.PORT = port;
        connect();
    }
    
    public void connect() throws IOException{
        serverSocket = new ServerSocket(PORT);
        startLogging();
        startListening();
    }
    
    private void startLogging(){
        logger = new Log(this);
        logger.newLoggerThread();
    }
    
    protected abstract void startListening() throws IOException;
    protected abstract void startRecepting() throws IOException;
    protected abstract void startSending() throws IOException;
    
    public void disconnect() throws IOException {
        serverSocket.close();
        logger.setIsLogging(false);
    }

    public int getPORT() {
        return PORT;
    }

    public void setPORT(int PORT) {
        this.PORT = PORT;
    }  
    
    public abstract void sendTo(String mes, int position) throws IOException;
    public abstract void sendExeTo(AbstractMessage message, int position) throws IOException;
}
