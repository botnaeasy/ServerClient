/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Message.Client2ServerMessages;

import java.io.File;

/**
 *
 * @author BotNaEasyEnv
 */
public class SendFileMessage extends AbstractC2SMessage{

    public SendFileMessage(Object[][] args) {
        super("SendFileMessage", args);
    }

    @Override
    public String[] setMethod() {
        return new String[]{
            "saveFile"
        };
    }

    @Override
    public Class[][] setClassArguments() {
        return new Class[][]{
            {byte[].class, File.class}
        };
    }
    
}
