/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Main;

import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import myproject.Model.Server.MultiThreadServer;

/**
 *
 * @author BotNaEasyEnv
 */
public class Serwer {
    public static void main(String [] args) throws InterruptedException{
        try {
            MultiThreadServer server = new MultiThreadServer();
            server.connect();
            //sleep(10000);
            //server.sendTo(new StopClientMessage(), 0);
        } catch (IOException ex) {
            Logger.getLogger(Serwer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
