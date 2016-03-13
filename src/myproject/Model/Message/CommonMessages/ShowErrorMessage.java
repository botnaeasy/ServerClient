/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Message.CommonMessages;

import myproject.Model.Client.AbstractClient;
import myproject.Model.Message.AbstractMessage;
import myproject.View.Common.UniversalMainFrame;

/**
 *
 * @author BotNaEasyEnv
 */
public class ShowErrorMessage extends AbstractMessage{

    public ShowErrorMessage(String message){
        super(message);
    }
    @Override
    public void executeMessage(AbstractClient client) {
        UniversalMainFrame.main.showErrorDialog(getMessage());
    }
    
}
