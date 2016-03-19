/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Common.TreeModels;

import java.io.File;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author BotNaEasyEnv
 */
public class FileTreeModel implements TreeModel{

    private File root;
    
    public FileTreeModel(){
        root = null;
    }
    
    public FileTreeModel(String f){
        root = new File(f);
    }
    
    public FileTreeModel(File f){
        root = f;
    }
    
    @Override
    public Object getRoot() {
        return root;
    }

    @Override
    public Object getChild(Object parent, int index) {
       String[] children = ((File)parent).list();
       if ((children == null) || (index >= children.length)) {
           return null;
       }
       return new File((File) parent, children[index]);
    }

    @Override
    public int getChildCount(Object parent) {
        String[] children = ((File)parent).list();
        if (children == null) return 0;
        return children.length;
    }

    @Override
    public boolean isLeaf(Object node) {
       return ((File)node).isFile();
    }

    @Override
    public void valueForPathChanged(TreePath tp, Object o) {
        
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
       String[] children = ((File)parent).list();
        if (children == null) {
            return -1;
        }
        String childname = ((File)child).getName();
        for(int i = 0; i < children.length; i++) {
            if (childname.equals(children[i])){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void addTreeModelListener(TreeModelListener tl) {
       
    }

    @Override
    public void removeTreeModelListener(TreeModelListener tl) {
       
    }
    
}
