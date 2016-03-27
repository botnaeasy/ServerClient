/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Logger;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import myproject.Model.Common.ToolObject;

/**
 *
 * @author BotNaEasyEnv
 * @param <T>
 */
public class Log<T> {
    public final static long SLEEP_TIME = 10000;
    private boolean isLogging = true;
    private T instanceOfClass;
    private List<Method> methods;
    
    
    public Log(T instanceOfClass){
        this.instanceOfClass = instanceOfClass;
    }
    
    public static void errorLog(Object message, Class clazz){
        System.err.println(clazz.getSimpleName()+" Error log: "+message);
    }
    
    public static void errorLog(Object message, Object cause){
        System.err.println("Error log: "+message+" \ncause: "+cause);
    }
    
    public static void log(Object message){
        System.out.println("Info log: "+message);
    }
    
    public static void errorLog(Object message){
        System.err.println("Error log: "+message);
    }
    
    public static void errorLog(Object message, Object cause, Class clazz){
        System.err.println(clazz.getSimpleName()+" Error log: "+message+" \ncause: "+cause);
    }
    
    
    public void newLoggerThread(){
        Thread t = new Thread(){
            @Override
            public void run(){
                try {
                    loadClassInfoLog();
                } catch (Exception ex) {
                    Log.errorLog(ex, ex.getCause(),Log.class);
                } 
                while(isLogging){
                    try {
                        Log.log(instanceOfClass.getClass().getSimpleName());
                        Log.log(ToolObject.getCurrentDateText());
                        showMethodResults();
                        Thread.sleep(SLEEP_TIME);
                    } catch (Exception ex) {
                        Log.errorLog(ex, ex.getCause(),Log.class);
                    }
                }
            }
        };
        t.setName("Logger thread");
        t.start();
    }
    
    public void showMethodResults() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        for(Method method : methods){
            Log.log(method.getName()+": "+method.invoke(instanceOfClass));
        }
    }
    
    private void loadClassInfoLog() throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        Properties prop = new Properties();
        
        prop.load(getClass().getClassLoader().getResourceAsStream("resources\\InfoClassProperties.properties"));
        
        List<Class> keys = new ArrayList<Class>();
        String key = instanceOfClass.getClass().getSimpleName();
        Class clazz = instanceOfClass.getClass();
        keys.add(clazz);
        while(!key.equals("Object"))
        {
           clazz = clazz.getSuperclass();
           key = clazz.getSimpleName();
           keys.add(clazz);
        }
        int classPosition = 0;
        String property = null;
        while(property == null){
            property = prop.getProperty(keys.get(classPosition).getSimpleName());
            classPosition++;
        }
        classPosition = classPosition -1;
        String[] properties = property.split("#");
        
        Method[] methodArrays = keys.get(classPosition).getDeclaredMethods();
        methods = new ArrayList<Method>();
        for(Method method : methodArrays){
            for(String props : properties){
                if(method.getName().equals(props)){
                    methods.add(method);
                }
            }
        } 
    }

    public boolean isIsLogging() {
        return isLogging;
    }

    public void setIsLogging(boolean isLogging) {
        this.isLogging = isLogging;
    }
    
    
}
