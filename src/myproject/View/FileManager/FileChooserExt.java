/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.View.FileManager;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author BotNaEasyEnv
 */
public class FileChooserExt extends JFileChooser {
    private FileNameExtensionFilter filter;
    private JPanel parent;
    public FileChooserExt(JPanel panel, String title){
        this(panel, title, null);
        
    }
    
    public FileChooserExt(JPanel panel, String title, String filterName, String... filters){
        super();
        this.parent = panel;
        setDialogTitle(title);
        setFilter(filterName, filters);
        setVisible(true);
    }
    private void setFilter(String filterName, String... filters){
        if(filterName!=null){
            filter= new FileNameExtensionFilter(filterName, filters);
            setFileFilter(filter);
        }
    }
    public File getChoosenFile(){
        int result = showOpenDialog(parent);
        if(result == JFileChooser.APPROVE_OPTION){
            return getSelectedFile();
        }
        return null;
    }
    public File[] getChoosenFiles(){
        int result = showOpenDialog(parent);
        if(result == JFileChooser.APPROVE_OPTION){
            return getSelectedFiles();
        }
        return null;
    }
}
