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
public class CreateMailboxCommand implements ICommand{

    @Override
    public void execute(ArrayList params) {
        MainPage mainPage = (MainPage)params.get(0);
        //mainPage.createMailbox(destinyId, sourceId);
    }

    @Override
    public String getCommandText() {
        String commandText = "create-mailbox";
        return commandText;
    }
    
}
