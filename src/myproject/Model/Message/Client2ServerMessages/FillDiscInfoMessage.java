/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Message.Client2ServerMessages;

import java.io.File;
import java.util.List;

/**
 *
 * @author BotNaEasyEnv
 */
public class FillDiscInfoMessage extends AbstractC2SMessage{

    public FillDiscInfoMessage(Object[][] args) {
        super("FillDiscInfoMessage", args);
    }

    @Override
    public String[] setMethod() {
        return new String[]{
            "addRootChilds"
        };
    }


    @Override
    public Class[][] setClassArguments() {
          return new Class[][]{
              {File[].class}
          };
    }
    
}
