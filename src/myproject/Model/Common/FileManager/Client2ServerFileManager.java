/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Common.FileManager;

import javax.swing.tree.TreeNode;
import myproject.Model.Common.FileManager.TreeModels.FileTreeNode;
import myproject.Model.Logger.Log;
import myproject.Model.Message.AbstractMessage;
import myproject.Model.Message.CommonMessages.RequestDiscInfoMessage;
import myproject.Model.Server.Client2Server;

/**
 *
 * @author BotNaEasyEnv
 */
public class Client2ServerFileManager<T extends Client2Server> {
    
    private T c2s;
    
    public Client2ServerFileManager(T c2s){
        this.c2s = c2s;
    }
    

    public TreeNode getRoot(){
        if(c2s.getClientFiles()==null){
            c2s.setClientFiles(new FileTreeNode(c2s.getClientHostName()));
            sendDiscsInfoRequest();
        }
        return c2s.getClientFiles();
    }
    
    private void sendDiscsInfoRequest(){
        try {
            AbstractMessage message = new RequestDiscInfoMessage();
         c2s.sendMessage(message);
        } catch (Exception e) {
            Log.errorLog(e.getMessage(), e);
        }
    }
    
    public void sendFilesInfoRequest(FileTreeNode node){
        if(node.getChildCount()>0){
            return;
        }
        if(node.getValue().isFile()){
            downloadFileRequest();
        }else{
            filesInfoRequest();
        }
    }
    
    private void filesInfoRequest(){
        
    }
    
    private void downloadFileRequest(){
        
    }
    
    
}
