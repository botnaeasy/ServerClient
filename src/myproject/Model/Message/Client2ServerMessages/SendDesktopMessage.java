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
public class SendDesktopMessage extends AbstractC2SMessage{

    private int type;
    public SendDesktopMessage(Object[][] args, int type) {
        super("SendDesktopMessage", args);
        this.type = type;
    }

    @Override
    public String[] setMethod() {
        return new String[]{
            "transferDesktopImage"
        };
    }

    @Override
    public Class[][] setClassArguments() {
        if(type==0){
            return new Class[][]{
                {ImageIcon.class}
            };
        }else{
            return new Class[][]{
                {byte[].class}
            };
        }
    }  
}
