/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Message.Client2ServerMessages;

import myproject.Model.Common.FileManager.TreeModels.FileTreeNode;

/**
 *
 * @author BotNaEasyEnv
 */
public class FillDeletedFilesInfoMessage extends AbstractC2SMessage{

    public FillDeletedFilesInfoMessage(Object[][] args) {
        super("FillDeletedFilesInfoMessage", args);
    }

    @Override
    public String[] setMethod() {
        return new String[]{
            "removeNode"
        };
    }

    @Override
    public Class[][] setClassArguments() {
        return new Class[][]{
            {FileTreeNode.class}  
        };
    }
    
}
