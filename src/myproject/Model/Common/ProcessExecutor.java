/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Common;

import java.io.File;
import java.io.IOException;
import myproject.Model.Logger.Log;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author BotNaEasyEnv
 */
public class ProcessExecutor {
    public static String execute(String... commands){
        return properExecute(null, commands);
    }
    public static String execute(File file, String... commands){
        return properExecute(file, commands);
    }
    
    private static String properExecute(File file, String... commands){
        try {
            ProcessBuilder builder = new ProcessBuilder(commands);
            if(file!=null){
                builder.directory(file);
            }
            Process process = builder.start();
            String input = IOUtils.toString(process.getInputStream());
            String error = IOUtils.toString(process.getErrorStream());
            process.waitFor();
            if(!error.isEmpty()){
                Log.errorLog(error,ProcessExecutor.class);
            }
            return input;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.errorLog(ex,ex.getCause(),ProcessExecutor.class);
        }
        return null;
    }  
}
