/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Common.FileManager.TreeModels;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author BotNaEasyEnv
 */
public class FileTreeNode implements MutableTreeNode, Serializable{

    private File value;
    private FileTreeNode parent;
    private List<FileTreeNode> childs;
    
    public FileTreeNode(String f, FileTreeNode parent){
        this(new File(f), parent);
    }
    
    public FileTreeNode(File f, FileTreeNode parent){
        super();
        value = f;
        childs = new ArrayList<FileTreeNode>();
        this.parent = parent;
    }
    
    
    public FileTreeNode(String f){
        this(new File(f));
    }
    
    public FileTreeNode(File f){
        super();
        value = f;
        childs = new ArrayList<FileTreeNode>();
        parent = null;
    }
    
    public boolean addNodesAtLast(File[] files, TreePath path){
        FileTreeNode node = findLastFromPath(path);
        if(node!=null){
            node.addNodes(files);
            return true;
        }
        return false;
    }
    
    public FileTreeNode findLastFromPath(TreePath treePath){
        Object[] path = treePath.getPath();
        if(path==null||path.length==0){
            return null;
        }
        if(path.length==1){
            return this;
        }
        return getFoundNode(1,path, this.children());
    }
    
    private FileTreeNode getFoundNode(int level,Object[] path, Enumeration childs ){
        if(level==path.length-1){
            while(childs.hasMoreElements()){
                FileTreeNode node = (FileTreeNode) childs.nextElement();
                if(node.toString().equals(path[level].toString())){
                    return node;
                }
            }
            return null;
        }else{
            while(childs.hasMoreElements()){
                TreeNode node = (TreeNode) childs.nextElement();
                if(node.toString().equals(path[level].toString())){
                    return getFoundNode(++level, path, node.children());
                }
            }
            return null;
        }
    }
    
    public boolean addNodes(File[] files){
        boolean result = false;
         if(getAllowsChildren()){
            for(File f : files){
                childs.add(new FileTreeNode(f, this));
            }
            result = true;
         }
        return result;
    }
    
    public boolean addNode(String f){
         boolean result = false;
         if(getAllowsChildren()){
            childs.add(new FileTreeNode(f, this));
            result = true;
         }
        return result;
    }
    
    public boolean addNode(File f){
        boolean result = false;
         if(getAllowsChildren()){
            childs.add(new FileTreeNode(f, this));
            result = true;
         }
        return result;
    }
    
    public void setParent(FileTreeNode parent){
        this.parent = parent;
    }
    
    public boolean addNode(FileTreeNode node){
        boolean result = false;
        if(getAllowsChildren()){
            node.setParent(this);
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
        return value.getName().equals("")||value.getName()==null?value.getPath():value.getName();
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

    @Override
    public void insert(MutableTreeNode mtn, int i) {
        childs.add((FileTreeNode) mtn);
    }

    @Override
    public void remove(int i) {
        childs.remove(i);
    }

    @Override
    public void remove(MutableTreeNode mtn) {
        childs.remove(mtn);
    }

    @Override
    public void setUserObject(Object o) {
        value = (File) o;
    }

    @Override
    public void removeFromParent() {
        parent.getChilds().remove(this);
    }

    @Override
    public void setParent(MutableTreeNode mtn) {
        parent = (FileTreeNode) mtn;
    }
    
    public List<FileTreeNode> getChilds(){
        return childs;
    }

    public File getValue() {
        return value;
    }

    public void setValue(File value) {
        this.value = value;
    }

}
