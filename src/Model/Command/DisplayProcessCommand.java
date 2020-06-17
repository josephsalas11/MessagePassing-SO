/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Command;

import java.util.ArrayList;


/**
 *
 * @author Paulo
 */
public class DisplayProcessCommand implements ICommand{
    
    private int processId;

    public DisplayProcessCommand(int processId) {
        this.processId = processId;
    }

    @Override
    public void execute(ArrayList params) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getCommandText() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getProcessId() {
        return processId;
    }
    
    
    
}
