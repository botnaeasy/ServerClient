/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Common.ExtendedComponents;

import javax.swing.JTree;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

/**
 *
 * @author BotNaEasyEnv
 */
public class ExtendedJTree extends JTree {

    public ExtendedJTree(TreeNode root) {
        super(root);
    }
    public ExtendedJTree(TreeModel model){
        super(model);
    }
    public TreeNode getSelectedNode(){
         return (TreeNode) getSelectionPath().getLastPathComponent();
    }
}
