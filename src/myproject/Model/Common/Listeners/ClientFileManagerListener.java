/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Common.Listeners;

import java.awt.event.ActionEvent;

/**
 *
 * @author BotNaEasyEnv
 */
public interface ClientFileManagerListener {
    
    public void onDownloadFinish(ActionEvent e);
    public void onAddChilds(ActionEvent e);
}
