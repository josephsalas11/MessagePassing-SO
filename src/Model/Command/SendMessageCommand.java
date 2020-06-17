/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Command;

import View.MainPage;
import java.util.ArrayList;

/**
 *
 * @author Paulo
 */
public class SendMessageCommand implements ICommand{
    
    private int sourceId;
    private int destinyId;
    private String message;
    private int priority = 0;

    public SendMessageCommand(int sourceId, int destinyId, String message) {
        this.sourceId = sourceId;
        this.destinyId = destinyId;
        this.message = message;
    }

    public SendMessageCommand(int sourceId, int destinyId, String message, int priority) {
        this.sourceId = sourceId;
        this.destinyId = destinyId;
        this.message = message;
        this.priority = priority;
    }

    @Override
    public void execute(ArrayList params) {
        MainPage mainPage = (MainPage)params.get(0);
        //mainPage.sendMessage(sourceId, destinyId, message, priority);
    }

    @Override
    public String getCommandText() {
        String commandText = "send sourceId: "+sourceId+" destinyId: "+destinyId+" message: "+message+" priority: "+priority;
        return commandText;
    }

    public int getSourceId() {
        return sourceId;
    }

    public int getDestinyId() {
        return destinyId;
    }

    public String getMessage() {
        return message;
    }

    public int getPriority() {
        return priority;
    }    
    
}
