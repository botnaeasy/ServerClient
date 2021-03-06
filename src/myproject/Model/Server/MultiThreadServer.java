/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Server;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import myproject.Model.Common.Listeners.ClientConnectionListener;
import myproject.Model.Common.Listeners.ClientReadyListener;
import myproject.Model.Logger.Log;
import myproject.Model.Message.AbstractMessage;
import myproject.Model.Message.CommonMessages.SetIdClientMessage;
import myproject.Model.Message.CommonMessages.TextMessage;

/**
 *
 * @author BotNaEasyEnv
 */
public class MultiThreadServer extends DuplexServer {
    private List<Client2Server> connectedClients;
    private boolean isListening = true;
    private static int currentClientID = 0;
    private ClientConnectionListener connectionListener;
    
    
    public MultiThreadServer(){
        super();
        connectedClients = new ArrayList<>();
    }
     
    @Override
    protected void startListening() throws IOException {
        startMultiThreadListening();
    }
    
    
    private void startMultiThreadListening(){
            Thread t = new Thread(){
                @Override
                public void run(){
                    while(isListening){
                        try {
                            Socket clientSocket = serverSocket.accept();
                            Client2Server c2s = new Client2Server(clientSocket, currentClientID);
                            setListeners(c2s);
                            connectedClients.add(c2s);
                            startReceptingCurrent(c2s);
                            startSendingCurrent(c2s);
                            sendTo(new SetIdClientMessage(currentClientID), connectedClients.size()-1);
                            Log.log("Connected client: "+currentClientID);
                            currentClientID++;
                        } catch (IOException ex) {
                            Log.errorLog(ex, ex.getCause(), MultiThreadServer.class);
                            ex.printStackTrace();
                        }
                    }
                }
            };
            t.setName("Listening thread");
            t.start();
    }
    
    private void setListeners(Client2Server c2s){
        c2s.addClientReadyListener(new ClientReadyListener() {
            @Override
            public void clientReady(ActionEvent e) {
                clientConnected();
            }
        });
    }
    
    public void sendBroadcast(AbstractMessage mes) throws IOException{
        for(Client2Server c2s: connectedClients){
            c2s.sendMessage(mes);
        }
    }
    
    public void sendTo(AbstractMessage mes, int position) throws IOException{
        connectedClients.get(position).sendMessage(mes);
    }
    
    private void startReceptingCurrent(Client2Server c2s) throws IOException{
        newReceptionThread(c2s);
    }
    
    private void startSendingCurrent(Client2Server c2s) throws IOException{
        newSendingThread(c2s);
    }
    
    @Override
    protected void startRecepting() throws IOException {
        for(Client2Server c2s: connectedClients){
            newReceptionThread(c2s);
        }
    }

    @Override
    protected void startSending() throws IOException {
        for(Client2Server c2s: connectedClients){
            newSendingThread(c2s);
        }
    }
    
    public boolean isIsListening() {
        return isListening;
    }

    public void setIsListening(boolean isListening) {
        this.isListening = isListening;
    }

    public List<Client2Server> getConnectedClients() {
        return connectedClients;
    }
    
    public synchronized void closeAll() throws IOException{
        for(Client2Server c2s: connectedClients){
            c2s.close();
        }
        connectedClients.clear();
    }
    
    public int numberOfConnectedClients(){
        return connectedClients.size();
    }
    
    public synchronized void close(int position, boolean isID) throws IOException{
        if(!isID){
            connectedClients.get(position).close();
            connectedClients.remove(position);
        }else{
            for(int i = 0;i<connectedClients.size();i++){
                if(connectedClients.get(i).getClientID()==position){
                    close(i,false);
                    return;
                }
            }
        }
    }
    
    @Override
    protected void clientLogout(Client2Server c2s) {
        try {
            close(c2s.getClientID(), true);
            clientDisconnected();
        } catch (IOException ex) {
            Log.errorLog(ex, ex.getCause(), MultiThreadServer.class);
        }
    }

    @Override
    public void sendTo(String mes, int position) throws IOException {
        sendTo(new TextMessage(mes), position);
    }

    @Override
    public void sendExeTo(AbstractMessage message, int position) throws IOException {
        sendTo(message, position);
    }
    
    private void clientConnected(){
        if(connectionListener!=null){
            connectionListener.clientConnected(new ActionEvent(this, 0, "client connected"));
        }
    }
    
    public void addClientConnectionListener(ClientConnectionListener listener){
        this.connectionListener = listener;
    }
    
    public void clientDisconnected(){
        if(connectionListener!=null){
            connectionListener.clientDisconnected(new ActionEvent(this, 0, "client disconnected"));
        }
    }
}
