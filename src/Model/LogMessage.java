/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Paulo
 */
public class LogMessage {
    private int associatedProcessId;
    private String message;

    public LogMessage(int associatedProcessId, String message) {
        this.associatedProcessId = associatedProcessId;
        this.message = message;
    }

    public int getAssociatedProcessId() {
        return associatedProcessId;
    }

    public String getMessage() {
        return message;
    }
    
}
