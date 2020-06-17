/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Command;

import View.MainPage;
import java.awt.List;
import java.util.ArrayList;

/**
 *
 * @author Paulo
 */
public class ReceiveMessageCommand implements ICommand{
    
    private int sourceId;
    private int destinyId;

    public ReceiveMessageCommand(int destinyId, int sourceId) {
        this.sourceId = sourceId;
        this.destinyId = destinyId;
    }
    

    @Override
    public void execute(ArrayList params) {
        MainPage mainPage = (MainPage)params.get(0);
        mainPage.receiveMessage(destinyId, sourceId);
    }

    @Override
    public String getCommandText() {
        String commandText = "receive destinyId= "+destinyId+" sourceId= "+sourceId;
        return commandText;
    }

    public int getSourceId() {
        return sourceId;
    }

    public int getDestinyId() {
        return destinyId;
    }
    
    
}
