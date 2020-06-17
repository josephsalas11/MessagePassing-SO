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
public class CommandTokenizer {
    private static CommandTokenizer singleton = null;

    public CommandTokenizer() {
    }
    
    public static CommandTokenizer getInstance(){
        if(singleton == null){
            singleton = new CommandTokenizer();
        }
        return singleton;
    }
    
    public ICommand analyzeCommand(String commandString){
        ICommand outputCommand = null;
        String[] tokens = commandString.split(" ");
        
        if(tokens[0].equals("create")){
            if(tokens.length > 1)
                outputCommand = new CreateProcessCommand(Integer.parseInt(tokens[1]));
            else
                outputCommand = new CreateProcessCommand();
        }
        else if(tokens[0].equals("send")){
            //send destinyId message
            if(tokens.length > 4)
                outputCommand = new SendMessageCommand(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), tokens[3], Integer.parseInt(tokens[4]));
            else
                outputCommand = new SendMessageCommand(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), tokens[3]);
        }
        else if(tokens[0].equals("receive")){
            //receive sourceId
            outputCommand = new ReceiveMessageCommand(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
        }
        else if(tokens[0].equals("display")){
            //display processId
            outputCommand = new DisplayProcessCommand(Integer.parseInt(tokens[1]));
        }
        else if(tokens[0].equals("create-mailbox")){
            //create-mailbox
            outputCommand = new CreateMailboxCommand();
        }
        else if(tokens[0].equals("add-receiver-mailbox")){
            //create-mailbox
            outputCommand = new AddReceiverMailboxCommand(Integer.parseInt(tokens[1]));
        }
        return outputCommand;
    }
}
