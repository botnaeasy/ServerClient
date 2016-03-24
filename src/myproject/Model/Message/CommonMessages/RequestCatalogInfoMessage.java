/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Message.CommonMessages;

import java.io.File;
import java.util.List;
import javax.swing.tree.TreePath;
import myproject.Model.Client.AbstractClient;
import myproject.Model.Common.FileManager.TreeModels.FileTreeNode;
import myproject.Model.Message.AbstractMessage;
import myproject.Model.Message.Client2ServerMessages.FillCatalogInfoMessage;

/**
 *
 * @author BotNaEasyEnv
 */
public class RequestCatalogInfoMessage extends AbstractMessage{

    private TreePath path;
    
    public RequestCatalogInfoMessage(TreePath path){
        super("RequestCatalogInfoMessage");
        this.path = path;
    }

    @Override
    public void executeMessage(AbstractClient client) {
        File[] files = ((FileTreeNode)path.getLastPathComponent()).getValue().listFiles();
        
        Object[][] args = new Object[][]{
            {files, path}
        };
        
        AbstractMessage message = new FillCatalogInfoMessage(args);
        client.sendMessage(message);
    }
}
