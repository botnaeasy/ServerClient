/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Message.CommonMessages;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import myproject.Model.Client.AbstractClient;
import myproject.Model.Message.AbstractMessage;
import myproject.Model.Message.Client2ServerMessages.SendFileMessage;

/**
 *
 * @author BotNaEasyEnv
 */
public class RequestFileSendMessage extends AbstractMessage{

    private File toDownload;
    public RequestFileSendMessage(File file){
        super("RequestFileSendMessage");
        this.toDownload= file;
    }
    
    @Override
    public void executeMessage(AbstractClient client) throws IOException {
        byte[] fileContent = Files.readAllBytes(toDownload.toPath());
        
        Object[][] args = new Object[][]{
            {fileContent, toDownload}
        };
        
        AbstractMessage message = new SendFileMessage(args);
        client.sendMessage(message);
    }
    
}
