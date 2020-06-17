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
public class AddReceiverMailboxCommand implements ICommand{
    
    private int receiverId;

    public AddReceiverMailboxCommand(int receiverId) {
        this.receiverId = receiverId;
    }    
    
    @Override
    public void execute(ArrayList params) {
        MainPage mainPage = (MainPage)params.get(0);
        //mainPage.addReceiverMailbox(receiverId);
    }

    @Override
    public String getCommandText() {
        String commandText = "create-mailbox receiverId= "+receiverId;
        return commandText;
    }
    
}
