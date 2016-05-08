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
public class StartSendingDesktopMessage extends AbstractMessage{

    public StartSendingDesktopMessage(){
        super("StartSendingDesktopMessage");
    }
    
    @Override
    public void executeMessage(AbstractClient client) throws Throwable {
        client.startSendingDesktop();
    }
}
