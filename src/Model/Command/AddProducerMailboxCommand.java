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
public class AddProducerMailboxCommand implements ICommand{
    private int mailboxId;
    private int producerId;

    public AddProducerMailboxCommand(int mailboxId, int producerId) {
        this.mailboxId = mailboxId;
        this.producerId = producerId;
    }

    @Override
    public void execute(ArrayList params) {
        MainPage mainPage = (MainPage)params.get(0);
        mainPage.addProducerMailbox(mailboxId, producerId);    }

    @Override
    public String getCommandText() {
        String commandText = "add-producer-mailbox producerId= "+producerId;
        return commandText;
    }
    
    
}
