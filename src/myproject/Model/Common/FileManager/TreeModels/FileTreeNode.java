/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Common.FileManager.TreeModels;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
import javax.swing.tree.TreeNode;

/**
 *
 * @author BotNaEasyEnv
 */
public class FileTreeNode implements TreeNode{

    private File value;
    private FileTreeNode parent;
    private List<FileTreeNode> childs;
    
    public FileTreeNode(String f){
        this(new File(f));
    }
    
    public FileTreeNode(File f){
        super();
        value = f;
        childs = new ArrayList<FileTreeNode>();
    }
    
    public boolean addNode(FileTreeNode node){
        boolean result = false;
        if(getAllowsChildren()){
            childs.add(node);
            result = true;
        }
        return result;
    }
    
    @Override
    public TreeNode getChildAt(int i) {
        if(i<0||i>=childs.size()){
            return null;
        }
        return childs.get(i);
    }

    @Override
    public int getChildCount() {
        return childs.size();
    }

    @Override
    public TreeNode getParent() {
        return parent;
    }

    @Override
    public int getIndex(TreeNode tn) {
        return childs.indexOf(tn);
    }

    @Override
    public boolean getAllowsChildren() {
        return !isLeaf();
    }

    @Override
    public boolean isLeaf() {
        return value.isFile();
    }

    @Override
    public Enumeration children() {
        Vector<TreeNode> vector = new Vector<TreeNode>();
        for(int i=0;i<childs.size();i++){
            vector.add(childs.get(i));
        }
        return vector.elements();
    }

    @Override
    public String toString() {
        return value.getName();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.value);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FileTreeNode other = (FileTreeNode) obj;
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        return true;
    }
    
    
}
