/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Common.Webcam;

import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.ImageIcon;
import myproject.Model.Common.Listeners.ClientWebcamListener;
import myproject.Model.Logger.Log;
import myproject.Model.Message.AbstractMessage;
import myproject.Model.Message.CommonMessages.StartSendingWebcamMessage;
import myproject.Model.Message.CommonMessages.StopSendingWebcamMessage;
import myproject.Model.Server.Client2Server;
import myproject.View.RemoteDesktop.RemoteDesktopPanel;

/**
 *
 * @author BotNaEasyEnv
 */
public class WebcamManager {
    
    private Client2Server c2s;
    private RemoteDesktopPanel panel;
    
    public WebcamManager(Client2Server c2s,RemoteDesktopPanel panel){
        this.c2s = c2s;
        this.panel = panel;
        listener();
    }
    
    private void listener(){
        c2s.addClientWebcamListener(new ClientWebcamListener(){

            @Override
            public void onWebcamReceived(ActionEvent e) {
                panel.drawDesktop((ImageIcon) e.getSource());
            }
        });
    }
    
    public void startWebcamWatching(){
        try{
            AbstractMessage message = new StartSendingWebcamMessage();
            c2s.sendMessage(message);
        } catch (IOException ex) {
            Log.errorLog(ex.getMessage(), ex.getCause(),WebcamManager.class);
        }
    }
    
    public void stopWebcamWatching(){
        try{
            AbstractMessage message = new StopSendingWebcamMessage();
            c2s.sendMessage(message);
        } catch (IOException ex) {
            Log.errorLog(ex.getMessage(), ex.getCause(),WebcamManager.class);
        }
    }
    
    
}
