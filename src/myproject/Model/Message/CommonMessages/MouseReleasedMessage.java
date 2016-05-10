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
public class MouseReleasedMessage extends AbstractMessage{

    private int key;
    
    public MouseReleasedMessage(int key){
        super("MouseReleasedMessage");
        this.key = key;
    }
    
    @Override
    public void executeMessage(AbstractClient client) throws Throwable {
        Robot r = client.getRobot();
        try{
            r.mouseRelease(key);
        }catch(Exception e){
            Object[][] args = new Object[][]{
                {e.getMessage(), e.getCause()}
            };
            AbstractMessage message = new ExceptionMessage("check robot?", args);
            client.sendMessage(message);
        }
    }
    
}
