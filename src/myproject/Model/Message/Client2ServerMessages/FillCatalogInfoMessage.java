/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Message.Client2ServerMessages;

import javax.swing.tree.TreePath;

/**
 *
 * @author BotNaEasyEnv
 */
public class FillCatalogInfoMessage extends AbstractC2SMessage{

    public FillCatalogInfoMessage(Object[][] args) {
        super("FillCatalogInfoMessage", args);
    }

    @Override
    public String[] setMethod() {
        return new String[]{
          "addChildsToNode"  
        };
    }

    @Override
    public Class[][] setClassArguments() {
        return new Class[][]{
            {Object[].class, TreePath.class}  
        };
    }
}
