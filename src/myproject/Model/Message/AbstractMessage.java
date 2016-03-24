/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Message;

import java.io.Serializable;
import myproject.Model.Client.AbstractClient;

/**
 *
 * @author BotNaEasyEnv
 */
public abstract class AbstractMessage implements Serializable{
    protected String message;
    
    public AbstractMessage(){
    }
    
    public AbstractMessage(String message){
        this.message = message;
    }
    
    @Override
    public String toString(){
        return message;
    }
    
    public void showMessage(){
        System.out.println(message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Class getSendableClass(){
        return getClass();
    }
    public String getSendableClassName(){
        return getClass().getSimpleName();
    }
    public abstract void executeMessage(AbstractClient client) throws Throwable;
}
