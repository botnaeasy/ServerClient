/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Message.CommonMessages;

import java.io.File;
import javax.swing.tree.TreePath;
import myproject.Model.Client.AbstractClient;
import myproject.Model.Common.ToolObject;
import myproject.Model.Message.AbstractMessage;
import myproject.Model.Message.Client2ServerMessages.FillCatalogInfoMessage;

/**
 *
 * @author BotNaEasyEnv
 */
public class SendFileMessage extends AbstractMessage{

    private byte[] fileContent;
    private File toDirectory;
    private TreePath path;
    private File file;
    
    public SendFileMessage(byte[] fileCont, File toDir, File file, TreePath path){
        super("SendFileMessage");
        this.file  = file;
        this.toDirectory = toDir;
        this.fileContent = fileCont;
        this.path = path;
    }
    
    @Override
    public void executeMessage(AbstractClient client) throws Throwable {
        ToolObject.saveFile(fileContent, toDirectory, file, false);
         Object[][] obj = new Object[][]{
                    {new File[]{file},path}
         };
         AbstractMessage mess = new FillCatalogInfoMessage(obj);
         client.sendMessage(mess);
    }
}
