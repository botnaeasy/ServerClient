/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Common.RemoteDesktop;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
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
    private RemoteDesktopInputManager inputManager;
    
    public RemoteDesktopManager(Client2Server c2s, RemoteDesktopPanel panel, boolean input){
        this.c2s = c2s;
        this.panel = panel;
        listener();
        if(input){
            initInput();
        }
    }
    
    private void initInput(){
        inputManager = new RemoteDesktopInputManager(c2s, panel);
    }
    
    private void listener(){
        c2s.addClientRemoteDesktopListener(new ClientRemoteDesktopListener() {
            @Override
            public void onReceivedScreen(ActionEvent e) {
                  if(e.getSource() instanceof ImageIcon){
                    panel.drawDesktop((ImageIcon) e.getSource());
                  }else{
                      InputStream in = new ByteArrayInputStream((byte[]) e.getSource());
                      try {
                          BufferedImage image = ImageIO.read(in);
                          panel.drawDesktop(image);
                      } catch (IOException ex) {
                          ex.printStackTrace();
                      }
                  }
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
