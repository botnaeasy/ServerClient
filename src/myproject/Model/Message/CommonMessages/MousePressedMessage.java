/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Message.CommonMessages;

import java.awt.Robot;
import myproject.Model.Client.AbstractClient;
import myproject.Model.Message.AbstractMessage;
import myproject.Model.Message.Client2ServerMessages.ExceptionMessage;

/**
 *
 * @author BotNaEasyEnv
 */
public class MousePressedMessage extends AbstractMessage{

     private int key;
    
    public MousePressedMessage(int key){
        super("MousePressedMessage");
        this.key = key;
    }
    
    @Override
    public void executeMessage(AbstractClient client) throws Throwable {
         Robot r = client.getRobot();
        try{
            r.mousePress(key);
        }catch(Exception e){
            Object[][] args = new Object[][]{
                {e.getMessage(), e.getCause()}
            };
            AbstractMessage message = new ExceptionMessage("check robot?", args);
            client.sendMessage(message);
        }
    }
    
}
