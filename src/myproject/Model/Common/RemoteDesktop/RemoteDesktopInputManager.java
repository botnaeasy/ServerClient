/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Common.RemoteDesktop;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import myproject.Model.Common.Listeners.ClientRemoteDesktopDimensionDataReceivedListener;
import myproject.Model.Logger.Log;
import myproject.Model.Message.AbstractMessage;
import myproject.Model.Message.CommonMessages.DimensionDataRequestMessage;
import myproject.Model.Message.CommonMessages.KeyPressedMessage;
import myproject.Model.Message.CommonMessages.KeyReleasedMessage;
import myproject.Model.Message.CommonMessages.MouseMoveMessage;
import myproject.Model.Message.CommonMessages.MousePressedMessage;
import myproject.Model.Message.CommonMessages.MouseReleasedMessage;
import myproject.Model.Message.CommonMessages.MouseWheelMessage;
import myproject.Model.Server.Client2Server;
import myproject.View.RemoteDesktop.RemoteDesktopPanel;

/**
 *
 * @author BotNaEasyEnv
 */
public class RemoteDesktopInputManager implements KeyListener, MouseMotionListener, MouseListener, MouseWheelListener{
    private double scaleX;
    private double scaleY;
    
    private Client2Server c2s;
    private RemoteDesktopPanel panel;
    
    
    public RemoteDesktopInputManager(Client2Server c2s, RemoteDesktopPanel panel){
        this.c2s = c2s;
        this.panel = panel;
        addListeners();
        sendDimensionDataRequest();
    }
    
    private void addListeners(){
        c2s.addClientRemoteDesktopDimensionDataReceivedListener(new ClientRemoteDesktopDimensionDataReceivedListener() {
            @Override
            public void dimensionDataReceived(ActionEvent e) {
                Rectangle clientScreen = (Rectangle) e.getSource();
                scaleX = clientScreen.getWidth() / panel.getWidth();
                scaleY = clientScreen.getHeight() / panel.getHeight();
            }
        });
        panel.addKeyListener(this);
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);
        panel.addMouseWheelListener(this);
    }
    
    private void sendDimensionDataRequest(){
        try{
            AbstractMessage message = new DimensionDataRequestMessage();
            c2s.sendMessage(message);
        }catch(IOException e){
            Log.errorLog(e.getMessage(), e.getCause(), RemoteDesktopInputManager.class);
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
            keyPressedMessage(ke.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent ke) {
            keyReleasedMessage(ke.getKeyCode());
    }

    @Override
    public void mouseDragged(MouseEvent me) {
    }

    @Override
    public void mouseMoved(MouseEvent me) {
            int x = (int) (me.getX() * scaleX);
            int y = (int) (me.getY() * scaleY);
            mouseMoveMessage(x,y);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
            int button = me.getButton();
            int toSendButton = 16;
            if(button == 3){
                toSendButton = 4;
            }
            mousePressedMessage(toSendButton);
    }

    @Override
    public void mouseReleased(MouseEvent me) {
            int button = me.getButton();
            int toSendButton = 16;
            if(button == 3){
                toSendButton = 4;
            }
            mouseReleasedMessage(toSendButton);
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
    
    
    private void mouseReleasedMessage(int button){
        try{
            AbstractMessage message = new MouseReleasedMessage(button);
            c2s.sendMessage(message);
        }catch(IOException e){
            Log.errorLog(e.getMessage(), e.getCause(), RemoteDesktopInputManager.class);
        }
    }
    
    private void mousePressedMessage(int button){
         try{
            AbstractMessage message = new MousePressedMessage(button);
            c2s.sendMessage(message);
        }catch(IOException e){
            Log.errorLog(e.getMessage(), e.getCause(), RemoteDesktopInputManager.class);
        }
    }
    
    private void mouseMoveMessage(int x, int y){
         try{
            AbstractMessage message = new MouseMoveMessage(x, y);
            c2s.sendMessage(message);
        }catch(IOException e){
            Log.errorLog(e.getMessage(), e.getCause(), RemoteDesktopInputManager.class);
        }
    }
    
    private void keyReleasedMessage(int key){
        try{
            AbstractMessage message = new KeyReleasedMessage(key);
            c2s.sendMessage(message);
        }catch(IOException e){
            Log.errorLog(e.getMessage(), e.getCause(), RemoteDesktopInputManager.class);
        }
    }
    private void keyPressedMessage(int key){
        try{
            AbstractMessage message = new KeyPressedMessage(key);
            c2s.sendMessage(message);
        }catch(IOException e){
            Log.errorLog(e.getMessage(), e.getCause(), RemoteDesktopInputManager.class);
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mwe) {
        try{
            AbstractMessage message = new MouseWheelMessage(mwe.getScrollAmount());
            c2s.sendMessage(message);
        }catch(IOException e){
            Log.errorLog(e.getMessage(), e.getCause(), RemoteDesktopInputManager.class);
        }
    }
}
