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
    public static String getStringTempDirectory(String directory){
        if(directory == null){
         return TEMP_DIRECTORY;
        }else{
            return TEMP_DIRECTORY+"\\"+directory+"\\";
        }
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
    
    public static void saveFileTemp(byte[] fileContent, File file, String directory){
        saveFileTemp(fileContent, file, null, false, directory);
    }
    
    public static void saveAndOpenFileTemp(byte[] fileContent, File file, String regex,String directory){
        saveFileTemp(fileContent, file, regex, true, directory);
    }
    public static void saveAndOpenFileTemp(byte[] fileContent, File file, String directory){
        saveFileTemp(fileContent, file, null, true, directory);
    }
    
    public static void saveFile(byte[] fileContent, File toDirectory, File file, boolean open){
        try{
            String allPath = toDirectory.getAbsolutePath()+"/"+file.getName();
            File destination = new File(allPath);
            Files.write(destination.toPath(), fileContent);
            
            if(open){
                openFile(destination);
            }
        }catch(Exception ex){
            Log.errorLog(ex, ex.getCause(), ToolObject.class);
        }
    }
    
    public static void saveFileTemp(byte[] fileContent, File file, String regex, boolean open, String directory){
         try {
                File tempDir = getTempDirectory();
                if(!tempDir.exists()){
                    Files.createDirectory(tempDir.toPath());
                }
                String temp = getStringTempDirectory(directory);
                if(regex==null){
                    temp+=file.getName();
                }else{
                    temp+=regex+"_"+getCurrentDateText()+"_"+file.getName();
                }
                File destination = new File(temp);
                Files.write(destination.toPath(), fileContent);
                
                if(open){
                    openFile(destination);
                }
                
            } catch (IOException ex) {
                Log.errorLog(ex, ex.getCause(), ToolObject.class);
            }
    }
    public static void openFile(String path){
        ProcessExecutor.execute("rundll32","url.dll","FileProtocolHandler", path);
    }
    public static void openFile(File file){
        ProcessExecutor.execute("rundll32","url.dll","FileProtocolHandler", file.getPath());
    }
}
