/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Common.FileManager;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import myproject.Model.Server.Client2Server;

/**
 *
 * @author BotNaEasyEnv
 */
public class Client2ServerFileManager<T extends Client2Server> {
    
    private T c2s;
    private TreeNode root;
    
    public Client2ServerFileManager(T c2s){
        this.c2s = c2s;
        setRoot();
    }
    
    public void setRoot(){
        root = new DefaultMutableTreeNode(c2s.getClientHostName());
    }
    
    public TreeNode getRootNode(){
        return root;
    }
    
    public void addNode(TreeNode tn, String add){
        ((DefaultMutableTreeNode) tn).add(new DefaultMutableTreeNode(add));
    }
}
