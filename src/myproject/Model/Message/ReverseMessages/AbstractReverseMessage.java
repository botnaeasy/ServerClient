/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Message.ReverseMessages;

import java.lang.reflect.Method;
import java.util.List;
import myproject.Model.Client.AbstractClient;
import myproject.Model.Informator.Log;
import myproject.Model.Message.AbstractMessage;

/**
 *
 * @author BotNaEasyEnv
 */
public abstract class AbstractReverseMessage extends AbstractMessage {
    private String clazz;
    private List<String> methodToExecute;
    
    public AbstractReverseMessage(String message){
        super(message);
    }
    public void reverseExecute(){
        try {
            seterClazz();
            setetMethod();
            
            Class cl = Class.forName(clazz);
            Object ob = cl.newInstance();
            for(int i=0;i<methodToExecute.size();i++){
                  Method method = cl.getMethod(methodToExecute.get(i));
                  method.invoke(ob);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.errorLog(ex, ex.getCause(), AbstractReverseMessage.class);
        } 
        
    }
    

    @Override
    public void executeMessage(AbstractClient client){
        
    }

    public String getClazz() {
        return clazz;
    }

    public List<String> getMethodToExecute() {
        return methodToExecute;
    }
    
    public void seterClazz(){
        this.clazz = getClazz();
    }
    
    public void setetMethod(){
        this.methodToExecute = setMethods();
    }
    
    public abstract String setClazz();
    public abstract List<String> setMethods();

}
