
package Controller;
import Model.Mailbox;
import Model.Message;
import Model.MessageType;
import Model.Producer;
import Model.QueueType;
import Model.Receiver;
import Model.SynchronizationType;
import Model.Process;
import static java.lang.Thread.sleep;
import java.util.*; 
import Model.Command.*;
import Model.IProducer;



public class FunctionManager {

    Hashtable<Integer, Process> processList;
    
    public FunctionManager() {
        processList = new Hashtable<Integer, Process>();
    }
    
    
    public void createExplicitProcess(int processCounter,SynchronizationType STP,QueueType queueType, int queueSizeType,SynchronizationType STR, int ID_SP)
    {
        Process SP = processList.get(ID_SP);
        processList.put(processCounter, new Process(processCounter, STP,
                queueType,queueSizeType,STR, SP));

    }
    
    public void createImplicitProcess(int processCounter,SynchronizationType STP,QueueType queueType, int queueSizeType,SynchronizationType STR)
    {
         processList.put(processCounter, new Process(processCounter, STP,
                queueType,queueSizeType,STR));
    }
    
    public void createIndirectProcess(int processCounter,SynchronizationType STP,QueueType queueType, int queueSizeType,SynchronizationType STR, Mailbox mailbox)
    {
        processList.put(processCounter, new Process(processCounter, STP,
                queueType,queueSizeType,STR, mailbox));
    }
    
    public void sendDirectProcess(int idSourceProcess, int idDestinationProcess, MessageType messageType, int messageLength, String messageContent, int priority)
    {
        Process source = processList.get(idSourceProcess);
        Process destination = processList.get(idDestinationProcess);
        Message message = null;
        
        if(priority != -1)
            message = source.createMessage(destination, messageContent, messageType, messageLength);
        else
            message = source.createMessagePriority(destination, messageContent, messageType, messageLength, priority);
        
        source.send(destination, message);
        
    }
    
    public void sendIndirectProcess(int idSourceProcess, int idDestinationProcess, MessageType messageType, int messageLength, String messageContent, int priority) throws InterruptedException
    {
        Process source = processList.get(idSourceProcess);
        Process destination = processList.get(idDestinationProcess);
        Message message = null;
        
        if(priority != -1)
            message = source.createMessage(destination, messageContent, messageType, messageLength);
        else
            message = source.createMessagePriority(destination, messageContent, messageType, messageLength, priority);
        
        source.sendMailbox((Mailbox)(IProducer)destination.getProducer(), message);
        
    }
    
    
    public Hashtable<Integer, Process> getProcessList() {
        return processList;
    }

    public void setProcessList(Hashtable<Integer, Process> processList) {
        this.processList = processList;
    }
    
    
}
