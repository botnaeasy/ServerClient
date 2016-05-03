/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Message.CommonMessages;

import java.io.File;
import myproject.Model.Client.AbstractClient;
import myproject.Model.Common.ProcessExecutor;
import myproject.Model.Message.AbstractMessage;
import myproject.Model.Message.Client2ServerMessages.CommandAnswerMessage;
import myproject.Model.Message.Client2ServerMessages.ExceptionMessage;

/**
 *
 * @author BotNaEasyEnv
 */
public class ExecuteCommandMessage extends AbstractMessage{

    private String[] commands;
    private File file;
    public ExecuteCommandMessage(String... str){
        this(null, str);
    }
    
    public ExecuteCommandMessage(File file, String... str){
        super("ExecuteCommandMessage");
        this.file = file;
        this.commands = str;
    }
    
    @Override
    public void executeMessage(AbstractClient client) throws Throwable {
        try{
            String result = ProcessExecutor.execute(file, commands);
            Object[][] arg = new Object[][]{
                {result}
            };
            AbstractMessage message = new CommandAnswerMessage(arg);
            client.sendMessage(message);
        }catch(Exception e){
            Object[][] args = new Object[][]{
                {e.getMessage(), e.getCause()}
            };
            AbstractMessage message = new ExceptionMessage("ExecuteCommandMessage", args);
            client.sendMessage(message);
        }
    }
}
