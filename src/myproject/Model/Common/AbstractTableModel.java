/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Common;

import java.util.List;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author BotNaEasyEnv
 */
public abstract class AbstractTableModel<T> implements TableModel{
    
    private List<T> data;
    private Object[] columns;
    private Object[][] rows;
     
    public AbstractTableModel(List<T> data){
        this.data = data;
        setData();
        setColumns();
    }
    public abstract Object[] createRow(T row);
    public abstract Object[] createColumns();

    public void setData(){
        Object[][] result = new Object[data.size()][createColumns().length];
        for(int i=0;i<data.size();i++){
            result[i]=createRow(data.get(i));
        }
        rows = result;
    }
    
    public void setColumns(){
        columns = createColumns();
    }
    
    @Override
    public int getRowCount() {
       return data.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int i) {
       return columns[i].toString();
    }

    @Override
    public Class<?> getColumnClass(int i) {
        return rows[0][i].getClass();
    }

    @Override
    public boolean isCellEditable(int i, int i1) {
        return false;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        return rows[i][i1];
    }

    @Override
    public void setValueAt(Object o, int i, int i1) {
        rows[i][i1] = o;
    }
    
     @Override
    public void addTableModelListener(TableModelListener tl) {
       
    }

    @Override
    public void removeTableModelListener(TableModelListener tl) {
       
    }

    public List<T> getData() {
        return data;
    }

    public Object[] getColumns() {
        return columns;
    }

    public Object[][] getRows() {
        return rows;
    }
    
}
