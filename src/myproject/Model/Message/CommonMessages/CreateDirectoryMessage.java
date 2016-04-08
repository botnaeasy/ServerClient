/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Message.CommonMessages;

import java.io.File;
import javax.swing.tree.TreePath;
import myproject.Model.Client.AbstractClient;
import myproject.Model.Message.AbstractMessage;
import myproject.Model.Message.Client2ServerMessages.FillCatalogInfoMessage;

/**
 *
 * @author BotNaEasyEnv
 */
public class CreateDirectoryMessage extends AbstractMessage{

    private File directory;
    private TreePath path;
    public CreateDirectoryMessage(File directory, TreePath path){
        super("CreateDirectoryMessage");
        this.directory = directory;
        this.path = path;
    }
    @Override
    public void executeMessage(AbstractClient client) throws Throwable {
        if(!directory.exists()){
            directory.mkdirs();
            if(directory.exists()){
                Object[][] obj = new Object[][]{
                    {new File[]{directory},path}
                };
                AbstractMessage message = new FillCatalogInfoMessage(obj);
                client.sendMessage(message);
            }
        }
    }
}
