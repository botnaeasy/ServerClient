/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Common.FileManager.TreeModels;

import java.io.File;
import java.util.Enumeration;
import java.util.List;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author BotNaEasyEnv
 */
public class FileTreeModel extends DefaultTreeModel{

         public FileTreeModel(TreeNode tn) {
            super(tn);
         }
        
         public FileTreeNode getProperNode(FileTreeNode start, FileTreeNode toFind){
             if(start.getValue().equals(toFind.getValue())){
                 return start;
             }
             List<FileTreeNode> childs = start.getChilds();
             if(childs==null||childs.isEmpty()){
                 return null;
             }
             for(FileTreeNode n : childs){
                  FileTreeNode result = getProperNode(n, toFind);
                  if(result !=null){
                      return result;
                  }
             }
             return null;
          }
         
        public void removeNode(FileTreeNode node){
                
                FileTreeNode proper = getProperNode((FileTreeNode) this.getRoot(), node);
                if(proper!=null){
                    this.removeNodeFromParent((MutableTreeNode) proper);
                    //((FileTreeNode)nodes[nodes.length-1]).removeFromParent();
                }
        }
}
