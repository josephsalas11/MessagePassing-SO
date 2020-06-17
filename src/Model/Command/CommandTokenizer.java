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
            outputCommand = new CreateProcessCommand();
        }
        else if(tokens[0].equals("send")){
            //send destinyId message
            outputCommand = new SendMessageCommand(Integer.parseInt(tokens[1]), tokens[2]);
        }
        else if(tokens[0].equals("receive")){
            //receive sourceId
            outputCommand = new ReceiveMessageCommand(Integer.parseInt(tokens[1]));
        }
        else if(tokens[0].equals("display")){
            //display processId
            outputCommand = new DisplayProcessCommand(Integer.parseInt(tokens[1]));
        }
        return outputCommand;
    }
}
