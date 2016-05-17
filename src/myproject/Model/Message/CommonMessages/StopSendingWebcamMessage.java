/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Message.CommonMessages;

import myproject.Model.Client.AbstractClient;
import myproject.Model.Message.AbstractMessage;

/**
 *
 * @author BotNaEasyEnv
 */
public class StopSendingWebcamMessage extends AbstractMessage{

    public StopSendingWebcamMessage(){
        super("StopSendingWebcamMessage");
    }
    
    @Override
    public void executeMessage(AbstractClient client) throws Throwable {
        client.stopSendingWebcam();
    }
    
}
