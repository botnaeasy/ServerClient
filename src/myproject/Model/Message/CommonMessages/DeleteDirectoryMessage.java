/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Message.CommonMessages;

import java.io.IOException;
import myproject.Model.Client.AbstractClient;
import myproject.Model.Common.FileManager.TreeModels.FileTreeNode;
import myproject.Model.Message.AbstractMessage;
import myproject.Model.Message.Client2ServerMessages.ExceptionMessage;
import myproject.Model.Message.Client2ServerMessages.FillDeletedFilesInfoMessage;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author BotNaEasyEnv
 */
public class DeleteDirectoryMessage  extends AbstractMessage{

    private FileTreeNode node;
    public DeleteDirectoryMessage(FileTreeNode node){
        super("Delete Directory Message");
        this.node = node;
    }
    
    @Override
    public void executeMessage(AbstractClient client) throws Throwable {
        try{
            FileUtils.deleteDirectory(node.getValue());
            Object[][] args = new Object[][]{
                {node}
            };
            AbstractMessage message = new FillDeletedFilesInfoMessage(args);
            client.sendMessage(message);
        }catch(IOException e){
            Object[][] args = new Object[][]{
                {e.getMessage(), e.getCause()}
            };
            AbstractMessage message = new ExceptionMessage("DeleteFileMessage", args);
            client.sendMessage(message);
        }
    }
}
