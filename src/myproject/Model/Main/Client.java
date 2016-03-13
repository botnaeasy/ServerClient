/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Main;

import myproject.Model.Client.DuplexClient;

/**
 *
 * @author BotNaEasyEnv
 */
public class Client {
    public static void main(String [] args){
        DuplexClient client = new DuplexClient();
        client.setHOST("192.168.1.10");
        client.tryToConnect();
    }
}
