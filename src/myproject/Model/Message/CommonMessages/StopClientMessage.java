/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Message.CommonMessages;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import myproject.Model.Client.AbstractClient;
import myproject.Model.Message.AbstractMessage;

/**
 *
 * @author BotNaEasyEnv
 */
public class StopClientMessage extends AbstractMessage{
    public StopClientMessage(){
        super("Stop client");
    }

    @Override
    public void executeMessage(AbstractClient client) {
        try {
            client.disconnect();
        } catch (IOException ex) {
            Logger.getLogger(StopClientMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
