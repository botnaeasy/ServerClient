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
public class CommandAnswerMessage extends AbstractC2SMessage{

    public CommandAnswerMessage(Object[][] args) {
        super("CommandAnswerMessage", args);
    }

    @Override
    public String[] setMethod() {
        return new String[]{
            "commandAnswer"
        };
    }

    @Override
    public Class[][] setClassArguments() {
        return new Class[][]{
            {String.class}
        };
    }
    
}
