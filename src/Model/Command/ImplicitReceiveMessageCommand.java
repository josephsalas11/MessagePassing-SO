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
 * @author Joseph Salas
 */
public class ImplicitReceiveMessageCommand implements ICommand{
   
    private int destinyId;

    public ImplicitReceiveMessageCommand(int destinyId) {
        this.destinyId = destinyId;

    }
    

    @Override
    public void execute(ArrayList params) {
        MainPage mainPage = (MainPage)params.get(0);
        mainPage.receiveMessage(destinyId,1);
    }

    @Override
    public String getCommandText() {
        String commandText = "receive destinyId= "+destinyId;
        return commandText;
    }



    public int getDestinyId() {
        return destinyId;
    }
    
}
