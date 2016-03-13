/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Server;

import java.io.IOException;
import myproject.Model.Exception.ClientLogoutException;

/**
 *
 * @author BotNaEasyEnv
 */
public abstract class DuplexServer extends AbstractServer {
    
     public void newReceptionThread(Client2Server c2s) throws IOException{
        Thread reception = new Thread(){
          @Override
          public void run(){
                  while(c2s.isIsReception()){
                      try {
                          c2s.showMessage();
                      } catch (ClientLogoutException ex) {
                          clientLogout(c2s);
                          c2s.setIsReception(false);
                          c2s.setIsSending(false);
                      }
                  }
            }  
        };
        reception.setName("Reception thread");
        reception.start();
    }
     
     protected abstract void clientLogout(Client2Server c2s);
    
    public void newSendingThread(Client2Server c2s){
        Thread sending = new Thread(){
            @Override
            public void run(){
                try {
                    while(c2s.isIsSending()){
                        sleep(10000);
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        };
        sending.setName("Sending thread");
        sending.start();
    }
}
