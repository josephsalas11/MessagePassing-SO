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
public class DisplayProcessCommand implements ICommand{
    
    private int processId;

    public DisplayProcessCommand(int processId) {
        this.processId = processId;
    }

    @Override
    public void execute(ArrayList params) {
        MainPage mainPage = (MainPage)params.get(0);
        mainPage.displayProcess(processId);
    }

    @Override
    public String getCommandText() {
        String commandText = "display processId= "+processId;
        return commandText;
    }

    public int getProcessId() {
        return processId;
    }


    
    
}
