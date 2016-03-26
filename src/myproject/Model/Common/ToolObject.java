/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import myproject.Model.Logger.Log;

/**
 *
 * @author BotNaEasyEnv
 */
public class ToolObject {
    
    public static final String TEMP_DIRECTORY =  "D:\\ServerRatTemp\\";
    
    public static String getCurrentTimestampText(){
        SimpleDateFormat simpleDateThere = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        return simpleDateThere.format(new Date());
    }
    
    public static String getCurrentDateText(){
        SimpleDateFormat simpleDateThere = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateThere.format(new Date());
    }
    
    public static File getTempDirectory(){
        return new File(TEMP_DIRECTORY);
    }
    public static String getStringTempDirectory(){
        return TEMP_DIRECTORY;
    }
    
    public static boolean isFile(String couldFile){
        try {
            String[] temp = couldFile.split("\\.");
            if(temp[1].length()<=4){
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }
    
    public static void saveFile(byte[] fileContent, File file, String reg){
         try {
                File tempDir = getTempDirectory();
                if(!tempDir.exists()){
                    Files.createDirectory(tempDir.toPath());
                }
                String temp = getStringTempDirectory();
                temp+=reg+"_"+getCurrentDateText()+"_"+file.getName();
                File destination = new File(temp);
                Files.write(destination.toPath(), fileContent);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
    }
}
