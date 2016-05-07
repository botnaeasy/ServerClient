/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Common.Console;

import java.util.Arrays;
import java.util.stream.Stream;
import myproject.Model.Message.AbstractMessage;
import myproject.Model.Message.CommonMessages.ExecuteCommandMessage;
import myproject.Model.Server.Client2Server;

/**
 *
 * @author BotNaEasyEnv
 */
public class ConsoleManager< T extends Client2Server> {
    private T c2s;
    
    public ConsoleManager(T c2s){
        this.c2s = c2s;
    }
    
    public T getC2S(){
        return c2s;
    }
    
    public void sendExecuteRequest(String... commands){
        try{
            String[] args = {"cmd", "/c"};
            String fullCommand[] = Stream.concat(Arrays.stream(args), Arrays.stream(commands)).toArray(String[]::new);
            AbstractMessage message = new ExecuteCommandMessage(fullCommand);
            c2s.sendMessage(message);
        }catch(Throwable e){
            e.printStackTrace();
        }
    }
    
}
