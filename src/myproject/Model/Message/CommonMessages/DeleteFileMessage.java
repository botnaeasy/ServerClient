/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Message.CommonMessages;

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
public class DeleteFileMessage extends AbstractMessage {

    private FileTreeNode node;
    public DeleteFileMessage(FileTreeNode node){
        super("DeleteFileMessage");
        this.node = node;
    }
    
    @Override
    public void executeMessage(AbstractClient client) throws Throwable {
        if(FileUtils.deleteQuietly(node.getValue())){
            Object[][] args = new Object[][]{
                {node}
            };
            AbstractMessage message = new FillDeletedFilesInfoMessage(args);
            client.sendMessage(message);
        }else{
            Object[][] args = new Object[][]{
                {"Can't delete", "Problem occured while removing file"+node.getValue().getName()}
            };
            AbstractMessage message = new ExceptionMessage("DeleteFileMessage", args);
            client.sendMessage(message);
        }
    }
    
}
