/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Command;

/**
 *
 * @author Paulo
 */
public class CreateProcessCommand implements ICommand{

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getCommandText() {
        String commandText = "create";
        return commandText;
    }
    
}
