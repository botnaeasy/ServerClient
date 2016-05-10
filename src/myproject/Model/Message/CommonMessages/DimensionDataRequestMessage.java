/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Message.CommonMessages;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import myproject.Model.Client.AbstractClient;
import myproject.Model.Message.AbstractMessage;
import myproject.Model.Message.Client2ServerMessages.FillDimensionDataMessage;

/**
 *
 * @author BotNaEasyEnv
 */
public class DimensionDataRequestMessage extends AbstractMessage{

    public DimensionDataRequestMessage(){
        super("DimensionDataRequestMessage");
    }
    
    @Override
    public void executeMessage(AbstractClient client) throws Throwable {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle rectangle = new Rectangle(dim);
        
        Object[][] args = new Object[][]{
            {rectangle}
        };
        
        AbstractMessage message = new FillDimensionDataMessage(args);
        client.sendMessage(message);
    }
}
