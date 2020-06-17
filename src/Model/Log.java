/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author Paulo
 */
public class Log {
    private ArrayList<LogMessage> logs;
    private static Log singleton = null;

    private Log() {
        logs = new ArrayList<>();
    }
    
    public static Log getInstance(){
        if(singleton == null){
            singleton = new Log();
        }
        return singleton;
    }
    
    public void addLog(int processId, String message){
        LogMessage logMessage = new LogMessage(processId, message);
        logs.add(logMessage);
        System.out.println(message);
    }

    public ArrayList getLogs() {
        return logs;
    }
    
    public ArrayList getProcessLog(int idProcess){
        ArrayList result = new ArrayList<>();
        for(int i=0; i<logs.size(); i++){
            if(logs.get(i).getAssociatedProcessId() == idProcess)
                result.add(logs.get(i));
        }
        return result;
    }
}
