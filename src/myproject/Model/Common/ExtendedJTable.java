/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Common;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 *
 * @author BotNaEasyEnv
 */
public class ExtendedJTable extends JTable{
    
     AbstractTableModel model;
    
    public ExtendedJTable(){
        super();
        
    }

    @Override
    public void setModel(TableModel tm) {
        super.setModel(tm);
        if(tm instanceof AbstractTableModel){
             this.model = (AbstractTableModel) tm;
        }
    }

    public <T> T getSelectedObject(){
        T result = null;
        List<T> tempList = model.getData();
        
        int selected = getSelectedRow();
        
        for(int i =0; i<tempList.size();i++){
            if(selected == i){
                result = tempList.get(i);
                break;
            }
        }
        return result;
    }
    
    public <T> List<T> getSelectedObjects(){
      List<T> resultList = new ArrayList<T>();
      List<T> tempList = model.getData();
      
      int[] selected = getSelectedRows();
      
      for(int i=0;i<selected.length;i++){
          for(int j=0;j<tempList.size();j++){
              if(selected[i]==j){
                  resultList.add(tempList.get(j));
                  break;
              }
          }
      }
      return resultList; 
    } 

}
