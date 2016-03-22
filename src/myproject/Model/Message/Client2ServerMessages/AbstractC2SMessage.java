/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Message.Client2ServerMessages;

import java.lang.reflect.Method;
import myproject.Model.Client.AbstractClient;
import myproject.Model.Logger.Log;
import myproject.Model.Message.AbstractMessage;
import myproject.Model.Server.Client2Server;

/**
 *
 * @author BotNaEasyEnv
 */
public abstract class AbstractC2SMessage extends AbstractMessage{

    String[] methodToExecute;
    Class[][] types4arguments;
    Object[][] arguments4method;
    
    public AbstractC2SMessage(String message, Object[][] args){
        super(message);
        this.arguments4method = args;
    }
    @Override
    public void executeMessage(AbstractClient client) {
    }
    
    public void executeOnC2S(Client2Server c2s){
        try{
            setMethodToExecute();
            setClassArguments4Method();
            
            Class cl = Class.forName("myproject.Model.Server.Client2Server");
            
            for(int i=0;i<methodToExecute.length;i++){
                  //Method[] methods = cl.getDeclaredMethods();
                  Method method = cl.getDeclaredMethod(methodToExecute[i], types4arguments[i]);
                  method.invoke(c2s,  arguments4method[i]);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.errorLog(ex, ex.getCause(), AbstractC2SMessage.class);
        }
    }

    private void setMethodToExecute() {
        this.methodToExecute = setMethod();
    }
    
    private void setClassArguments4Method(){
        this.types4arguments = setClassArguments();
    }
    
    public abstract String[] setMethod();
    public abstract Class[][] setClassArguments();
}
