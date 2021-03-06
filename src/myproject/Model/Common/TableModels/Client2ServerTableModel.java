/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Common.TableModels;

import myproject.Model.Common.TableModels.UniversalTableModel;
import java.util.List;
import myproject.Model.Server.Client2Server;

/**
 *
 * @author BotNaEasyEnv
 */
public class Client2ServerTableModel extends UniversalTableModel<Client2Server>  {

    public Client2ServerTableModel(List<Client2Server> data) {
        super(data);
    }

    @Override
    public Object[] createColumns() {
        return new Object[]{
            "ID",
            "IP",
            "Hostname",
            "OS",
            "Architecture",
            "Language",
            "Java version"
        };
    }

    @Override
    public Object[] createRow(Client2Server row) {
         return new Object[]{
            row.getClientID(),
            row.getClientIP(),
            row.getClientHostName(),
            row.getClientOS(),
            row.getClientArchitecture(),
            row.getClientLanguage(),
            row.getJavaVersion()
         };
    }
}
