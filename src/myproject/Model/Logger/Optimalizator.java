/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Logger;

/**
 *
 * @author BotNaEasyEnv
 */
public class Optimalizator {
    
        private long start;
        private long stop;
        private String indicator;
        public void start(){
            start = System.nanoTime();
        }
        
        public void stop(){
            stop = System.nanoTime();
        }
        
        public long getResult(){
            return stop - start;
        }
        
        public void setIndicator(String indicator){
            this.indicator = indicator;
        }
        
        public String getIndicator(){
            return indicator;
        }
        
        public double getMillisResult(){
            return getResult()/1000000;
        }
        
        public double getSecondsResult(){
            return getMillisResult()/1000;
        }
        
        public void showLongResult(){
            System.out.println(getIndicator()+": "+getResult()+" nanos");
        }
        
        public void showMillisResult(){
            System.out.println(getIndicator()+": "+getMillisResult()+" millis");
        }
        
        public void showSecondResult(){
            System.out.println(getIndicator()+": "+getSecondsResult()+" seconds");
        }
        
}
