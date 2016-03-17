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
   
    private String[][] args;
    
    public FillClientInfoMessage(String[][] arguments) {
        super("Fill client info");
        this.args = arguments;
    }

    @Override
    public String[] setMethod() {
        return new String[]{
            "setClientIP",
            "setClientHostName",
            "setClientOS",
            "setClientLanguage",
            "setJavaVersion"
        };
    }

    @Override
    public String[][] setArguments() {
        return args;
    }

    @Override
    public Class[][] setClassArguments() {
        return new Class[][]{
            {String.class},
            {String.class},
            {String.class},
            {String.class},
            {String.class}
        };
    }
    
}
