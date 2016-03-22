/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Message.CommonMessages;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import myproject.Model.Client.AbstractClient;
import myproject.Model.Message.AbstractMessage;
import myproject.Model.Message.Client2ServerMessages.FillDiscInfoMessage;

/**
 *
 * @author BotNaEasyEnv
 */
public class RequestDiscInfoMessage extends AbstractMessage {

    public RequestDiscInfoMessage(){
        super("RequestDiscInfoMessage");
    }
    @Override
    public void executeMessage(AbstractClient client) {
        File[] file = File.listRoots();
        List<File> files = new ArrayList<File>();
        for(int i=0;i<file.length;i++){
            if(file[i].canRead()){
                files.add(file[i]);
            }
        }
        
        Object[][] args = new Object[][]{
            {files.toArray(new Object[]{})}
        };
        AbstractMessage mes = new FillDiscInfoMessage(args);
        client.sendMessage(mes);
    }
}
