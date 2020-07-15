/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Paulo
 */
public class Log {
    private ArrayList<LogMessage> logs;
    private static Log singleton = null;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  

    private Log() {
        logs = new ArrayList<>();
    }
    
    public static Log getInstance(){
        if(singleton == null){
            singleton = new Log();
        }
        return singleton;
    }
    
    public void addLog(int associatedId, String detail, boolean isProcess){ 
        
        LocalDateTime now = LocalDateTime.now();  
        String finalDetail = dtf.format(now)+": "+detail;
        LogMessage logMessage = new LogMessage(associatedId, finalDetail, now, isProcess);
        logs.add(logMessage);
        System.out.println(detail);
    }

    public ArrayList<LogMessage> getLogs() {
        return logs;
    }
    
    public ArrayList getProcessLog(int idProcess){
        ArrayList result = new ArrayList<>();
        for(int i=0; i<logs.size(); i++){
            if(logs.get(i).getAssociatedId() == idProcess){
                result.add(logs.get(i));
                System.out.println(logs.get(i).getDetail());
            }
        }
        return result;
    }
    
}
