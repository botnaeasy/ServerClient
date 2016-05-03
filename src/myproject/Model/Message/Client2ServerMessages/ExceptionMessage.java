/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Message.Client2ServerMessages;

/**
 *
 * @author BotNaEasyEnv
 */
public class ExceptionMessage extends AbstractC2SMessage{

    public ExceptionMessage(String message, Object[][] args) {
        super("Exception message "+message, args);
    }

    @Override
    public String[] setMethod() {
        return new String[]{
            "showException"
        };
    }

    @Override
    public Class[][] setClassArguments() {
        return new Class[][]{
            {String.class, String.class}  
        };
    }
    
}
