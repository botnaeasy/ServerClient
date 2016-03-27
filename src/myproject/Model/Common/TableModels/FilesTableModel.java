/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Common.TableModels;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

/**
 *
 * @author BotNaEasyEnv
 */
public class FilesTableModel extends UniversalTableModel<File>{

    
    public FilesTableModel(List<File> data) {
        super(data);
    }

    @Override
    public Object[] createRow(File row) {
        return new Object[]{
            row.getName(),
            new DecimalFormat("#.##").format((double)row.length()/1024/1024),
            row.isDirectory(),
            row.isFile(),
            row.isHidden(),
            row.canWrite(),
            row.canRead(),
            row.canExecute()
        };
    }

    @Override
    public Object[] createColumns() {
        return new Object[]{
            "Name",
            "Size(MB)",
            "Directory",
            "File",
            "Hidden",
            "Can write",
            "Can read",
            "Execute"
        };
    }
}
