/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Command;

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getCommandText() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getSourceId() {
        return sourceId;
    }

    public int getDestinyId() {
        return destinyId;
    }
    
    
}
