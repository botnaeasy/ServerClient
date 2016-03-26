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
public class FillClientInfoMessage extends AbstractC2SMessage{
   
    
    public FillClientInfoMessage(Object[][] arguments) {
        super("Fill client info", arguments);
    }

    @Override
    public String[] setMethod() {
        return new String[]{
            "setClientIP",
            "setClientHostName",
            "setClientOS",
            "setClientLanguage",
            "setJavaVersion",
            "setClientArchitecture"
        };
    }


    @Override
    public Class[][] setClassArguments() {
        return new Class[][]{
            {String.class},
            {String.class},
            {String.class},
            {String.class},
            {String.class},
            {String.class}
        };
    }
    
}
