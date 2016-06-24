/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Common.RemoteDesktop;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import myproject.Model.Client.AbstractClient;
import myproject.Model.Logger.Log;
import myproject.Model.Logger.Optimalizator;
import myproject.Model.Message.AbstractMessage;
import myproject.Model.Message.Client2ServerMessages.SendDesktopMessage;

/**
 *
 * @author BotNaEasyEnv
 */
public class SendingDesktopThread extends Thread{

    private boolean isRunning = true;
    private Robot robot;
    private Rectangle rectangle;
    private AbstractClient client;
    
    public SendingDesktopThread(AbstractClient client){
        this.client = client;
    }
    
    @Override
    public void run() {
        init();
        //Optimalizator opt = new Optimalizator();
        while(isRunning){
            try{
                //opt.setIndicator("Screen capture");
                //opt.start();
                BufferedImage image = robot.createScreenCapture(rectangle);
                
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "jpg", baos);
                byte[] array = baos.toByteArray();
                //ImageIO.write(image, "jpg", new File("D:\\screen.jpg"));
                //ImageIcon icon = new ImageIcon(image);
                //opt.stop();
                //opt.showSecondResult();
                //opt.setIndicator("Sending message");
                //opt.start();
                Object[][] args = new Object[][]{
                    //{icon} 
                    {array}
                };
                
                AbstractMessage message = new SendDesktopMessage(args, 1);
                client.sendMessage(message);
               // opt.stop();
               // opt.showSecondResult();
                Thread.sleep(100);
            }catch(Exception e){
                Log.errorLog(e.getMessage(), e.getCause(), SendingDesktopThread.class);
            }
        }
        isRunning = false;
    }
    
    private void init(){
        try{
            GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gDev = gEnv.getDefaultScreenDevice();
            robot = new Robot(gDev);
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            rectangle = new Rectangle(dim);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void stopRunning(){
        isRunning = false;
    }
    
    public Robot getRobot(){
        if(robot != null){
            return robot;
        }
        return null;
    }
}
