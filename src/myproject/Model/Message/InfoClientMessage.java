/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Message;

import myproject.Model.Client.AbstractClient;

/**
 *
 * @author BotNaEasyEnv
 */
public class InfoClientMessage extends AbstractMessage {

    private String IP;
    
    public InfoClientMessage(String ip){
        super("Client info message");
        this.IP = ip;
    }
    
    @Override
    public void executeMessage(AbstractClient client) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getIP() {
        return IP;
    }
    
    
    
}
