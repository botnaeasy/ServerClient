/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.View.RemoteDesktop;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

/**
 *
 * @author BotNaEasyEnv
 */
public class RemoteDesktopPanel extends javax.swing.JPanel {

    /**
     * Creates new form RemoteDesktopPanel
     */
    public RemoteDesktopPanel() {
        initComponents();
    }
    
    public void drawDesktop(ImageIcon imageIcon){
        Image image = imageIcon.getImage();
        image = image.getScaledInstance(getWidth(), getHeight(), 2);
       
        Graphics graphics = getGraphics();
        if(graphics!=null){
            graphics.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }
    
    public void drawDesktop(BufferedImage buff){
        Image image = buff;
        image = image.getScaledInstance(getWidth(), getHeight(), 2);
       
        Graphics graphics = getGraphics();
        if(graphics!=null){
            graphics.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
