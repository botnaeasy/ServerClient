/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Common.RemoteDesktop;

import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.ImageIcon;
import myproject.Model.Common.Listeners.ClientRemoteDesktopListener;
import myproject.Model.Logger.Log;
import myproject.Model.Message.AbstractMessage;
import myproject.Model.Message.CommonMessages.StartSendingDesktopMessage;
import myproject.Model.Message.CommonMessages.StopSendingDesktopMessage;
import myproject.Model.Server.Client2Server;
import myproject.View.RemoteDesktop.RemoteDesktopPanel;

/**
 *
 * @author BotNaEasyEnv
 */
public class RemoteDesktopManager {
    
    private Client2Server c2s;
    private RemoteDesktopPanel panel;
    
    public RemoteDesktopManager(Client2Server c2s, RemoteDesktopPanel panel){
        this.c2s = c2s;
        this.panel = panel;
        listener();
    }
    
    private void listener(){
        c2s.addClientRemoteDesktopListener(new ClientRemoteDesktopListener() {
            @Override
            public void onReceivedScreen(ActionEvent e) {
                  panel.drawDesktop((ImageIcon) e.getSource());
            }
        });
    }
    
    public void startRemoteDesktop(){
        try{
            AbstractMessage message = new StartSendingDesktopMessage();
            c2s.sendMessage(message);
        } catch (IOException ex) {
            Log.errorLog(ex.getMessage(), ex.getCause(),RemoteDesktopManager.class);
        }
    }
    
    public void stopRemoteDesktop(){
        try {
            AbstractMessage message = new StopSendingDesktopMessage();
            c2s.sendMessage(message);
        } catch (IOException ex) {
            Log.errorLog(ex.getMessage(), ex.getCause(),RemoteDesktopManager.class);
        }
    }
}
