/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Command;

import Model.IProducer;
import Model.Process;
import Model.QueueType;
import Model.SynchronizationType;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 *
 * @author Paulo
 */
public class CreateProcessCommand implements ICommand{

    @Override
    public void execute(ArrayList params) {
        Process process = null;
        if((Integer)params.get(0) == 0){ //explicito
            process = new Process((Integer)params.get(1), (SynchronizationType)params.get(2), (QueueType)params.get(3), (Integer)params.get(4), (SynchronizationType)params.get(5), (Process)params.get(6));
        }
        else if((Integer)params.get(0) == 1){ //implicito
            process = new Process((Integer)params.get(1), (SynchronizationType)params.get(2), (QueueType)params.get(3), (Integer)params.get(4), (SynchronizationType)params.get(5));
        }
        else if((Integer)params.get(0) == 2){ //indirecto
            process = new Process((Integer)params.get(1), (SynchronizationType)params.get(2), (QueueType)params.get(3), (Integer)params.get(4), (SynchronizationType)params.get(5), (IProducer)params.get(6));
        }
        Hashtable hash = (Hashtable)params.get(params.size()-1);
        if(process != null)
            hash.put((Integer)params.get(1), process);
    }

    @Override
    public String getCommandText() {
        String commandText = "create";
        return commandText;
    }
    
}
