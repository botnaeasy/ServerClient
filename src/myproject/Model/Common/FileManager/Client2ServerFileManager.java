/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Common.FileManager;

import java.io.File;
import java.io.IOException;
import javax.swing.JProgressBar;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import myproject.Model.Common.FileManager.TreeModels.FileTreeModel;
import myproject.Model.Common.FileManager.TreeModels.FileTreeNode;
import myproject.Model.Common.ToolObject;
import myproject.Model.Logger.Log;
import myproject.Model.Message.AbstractMessage;
import myproject.Model.Message.CommonMessages.CreateDirectoryMessage;
import myproject.Model.Message.CommonMessages.DeleteDirectoryMessage;
import myproject.Model.Message.CommonMessages.RequestCatalogInfoMessage;
import myproject.Model.Message.CommonMessages.RequestDiscInfoMessage;
import myproject.Model.Message.CommonMessages.RequestFileSendMessage;
import myproject.Model.Message.CommonMessages.SendFileMessage;
import myproject.Model.Server.Client2Server;

/**
 *
 * @author BotNaEasyEnv
 * @param <T>
 */
public class Client2ServerFileManager<T extends Client2Server> {
    
    private T c2s;
    private boolean isDownloading = false;
    private JProgressBar progressField;
    
    public Client2ServerFileManager(T c2s){
        this.c2s = c2s;
        c2s.setModel(new FileTreeModel(new FileTreeNode(c2s.getClientHostName())));
        sendDiscsInfoRequest();
    }
    
    public void setProgressBar(JProgressBar bar){
        progressField = bar;
    }
    
    public DefaultTreeModel getModel(){
        return c2s.getModel();
    }
    
    public T getClient(){
        return c2s;
    }
    
    public TreeNode getRoot(){
        return (TreeNode) c2s.getModel().getRoot();
    }
    
    private void sendDiscsInfoRequest(){
        try {
            AbstractMessage message = new RequestDiscInfoMessage();
            c2s.sendMessage(message);
        } catch (Exception e) {
            Log.errorLog(e.getMessage(), e);
        }
    }
    
    public void sendFilesInfoRequest(TreePath path){
        FileTreeNode node = (FileTreeNode) path.getLastPathComponent();
        if(node.getChildCount()>0){
            return;
        }
        if(node.getValue().isDirectory()){
            catalogInfoRequest(path);
            return;
        }
        if(node.getValue().isFile()||ToolObject.isFile(node.getValue().getName())){
            downloadFileRequest(((FileTreeNode)path.getLastPathComponent()).getValue(), true, null);
            return;
        }
         catalogInfoRequest(path);
    }
    
    public void downloadAllFilesInDirectory(FileTreeNode node){
        for(File f : node.getChildsValue()){
            if(f.isFile()||ToolObject.isFile(f.getName())){
                downloadFileRequest(f, false, node.getValue().getName());
            }
        }
    }
    
    private void catalogInfoRequest(TreePath path){
        try {
            AbstractMessage message = new RequestCatalogInfoMessage(path);
            c2s.sendMessage(message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void sendFileMessage(byte[] fileBytes, File toDirectory, File properFile, TreePath path){
        try {
            AbstractMessage message = new SendFileMessage(fileBytes, toDirectory, properFile, path);
            c2s.sendMessage(message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void downloadFileRequest(File file, boolean open, String directory){
        try{
            startProgressThread();
            AbstractMessage message = new RequestFileSendMessage(file, open, directory);
            c2s.sendMessage(message);
        }catch(Throwable ex){
            ex.printStackTrace();
        }
    }
    
     public void removeFile(FileTreeNode node) {
         
     }

    public void removeDirectory(FileTreeNode node) {
        try{
            AbstractMessage message = new DeleteDirectoryMessage(node);
            c2s.sendMessage(message);
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    public void createDirectoryMessage(File directory, TreePath path){
        try {
            AbstractMessage message = new CreateDirectoryMessage(directory, path);
            c2s.sendMessage(message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void startProgressThread(){
        isDownloading = true;
        Thread t = new Thread(){
            @Override
            public void run(){
               int i =1;
                while(isDownloading){
                    try {
                        if(i>=100){
                            i=1;
                        }
                        progressField.setValue(i);
                        sleep(10);
                        i++;
                    } catch (InterruptedException ex) {
                    }
                }
                progressField.setValue(0);
            }
        };
        t.setName("Downloading thread");
        t.start();
    }
    
    public void stopDownloading(){
        isDownloading = false;
    }
}
