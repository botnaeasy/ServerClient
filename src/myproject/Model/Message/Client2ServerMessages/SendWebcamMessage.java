/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Message.Client2ServerMessages;

import javax.swing.ImageIcon;

/**
 *
 * @author BotNaEasyEnv
 */
public class SendWebcamMessage extends AbstractC2SMessage{

    public SendWebcamMessage(Object[][] args) {
        super("SendWebcamMessage", args);
    }

    @Override
    public String[] setMethod() {
        return new String[]{
          "transferWebcamImage"  
        };
    }

    @Override
    public Class[][] setClassArguments() {
        return new Class[][]{
            {ImageIcon.class}
        };
    }
    
}
