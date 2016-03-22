/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Common.ExtendedComponents;

import java.util.Enumeration;
import javax.swing.JTree;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author BotNaEasyEnv
 */
public class ExtendedJTree extends JTree {

    public ExtendedJTree(TreeNode root) {
        super(root);
    }
    public TreeNode getSelectedNode(){
         return (TreeNode) getSelectionPath().getLastPathComponent();
     }
    
    /*////////////////kod ku pamięci głupoty!!!nie kasować!!!!
    public TreeNode getSelectedNode(){
        Object[] path = getSelectionPath().getPath();
        if(path==null||path.length==0){
            return null;
        }
        if(path.length==1){
            return root;
        }
        return getSelectedNode(1, path, root.children());
    }
    
    private TreeNode getSelectedNode(int level, Object[] path, Enumeration childs){
        if(level==path.length-1){
            while(childs.hasMoreElements()){
                TreeNode node = (TreeNode) childs.nextElement();
                if(node.toString().equals(path[level-1].toString())){
                    return node;
                }
            }
            return null;
        }else{
            while(childs.hasMoreElements()){
                TreeNode node = (TreeNode) childs.nextElement();
                if(node.toString().equals(path[level-1].toString())){
                    return getSelectedNode(++level, path, node.children());
                }
            }
            return null;
        }
    }  */
}
