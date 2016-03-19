/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Common;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import myproject.Model.Logger.Log;

/**
 *
 * @author BotNaEasyEnv
 */
public class ProcessExecutor {
    public static String execute(String... commands) throws IOException{
        return properExecute(null, commands);
    }
    public static String execute(File file, String... commands) throws IOException{
        return properExecute(file, commands);
    }
    
    private static String properExecute(File file, String... commands) throws IOException{
        ProcessBuilder builder = new ProcessBuilder(commands);
        if(file!=null){
            builder.directory(file);
        }
        Process process = builder.start();
        String input = streamToString(process.getInputStream());
        String error = streamToString(process.getErrorStream());
        Log.log(error);

        return input;
    }
    
    private static String streamToString(InputStream is) throws IOException{
        return "";
    }
    
}
