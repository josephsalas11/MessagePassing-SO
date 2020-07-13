
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
    Hashtable<Integer, Mailbox> mailboxList;
    
    public FunctionManager() {
        processList = new Hashtable<Integer, Process>();
        mailboxList = new Hashtable<Integer, Mailbox>();

    }
    
    
    public void createExplicitProcess(int processCounter,SynchronizationType STP,QueueType queueType, int queueSizeType, SynchronizationType STR, int ID_SP)
    {
        //System.out.println(ID_SP);
        processList.put(processCounter, new Process(processCounter, STP, queueType, queueSizeType, STR));

    }
    
    public void createImplicitProcess(int processCounter,SynchronizationType STP,QueueType queueType, int queueSizeType, SynchronizationType STR)
    {
         processList.put(processCounter, new Process(processCounter, STP,
                queueType, queueSizeType,STR));
    }
    
    public void createIndirectProcess(int processCounter,SynchronizationType STP,QueueType queueType, int queueSizeType, SynchronizationType STR, int ID_MB)
    {
        Mailbox mailbox = mailboxList.get(ID_MB);
        processList.put(processCounter, new Process(processCounter, STP,
                queueType, queueSizeType,STR, mailbox));
    }
    
    public boolean sendDirectProcess(int idSourceProcess, int idDestinationProcess, MessageType messageType, int messageLength, String messageContent, int priority)
    { 
        Process source = processList.get(idSourceProcess);
        Process destination = processList.get(idDestinationProcess);
        
        //validacion para determinar que el producer y el receiver existan en el controlador
        if(source == null || destination == null){
            System.out.println("Operacion invalida"); //hacer log de error
            return false;
        }
        
        else{
            Message message = null;
        
            if(priority != -1)
                message = source.createMessage(destination, messageContent, messageType, messageLength);
            else
                message = source.createMessagePriority(destination, messageContent, messageType, messageLength, priority);

            source.send(destination, message);
            return true;
        }
    }
    
public boolean sendIndirectProcess(int idSourceProcess, int idDestinationProcess, MessageType messageType, int messageLength, String messageContent, int priority) throws InterruptedException
    {        
        Process source = processList.get(idSourceProcess);
        Process destination = processList.get(idDestinationProcess);
        Mailbox mailbox = mailboxList.get(idDestinationProcess);
        
        //validacion para determinar que el producer, el receiver y el mailbox existan en el controlador
        if(source == null || destination == null || mailbox == null){
            System.out.println("Operacion invalida"); //hacer log de error
            return false;
        }
        
        else{
            Message message = null;
        
            if(priority != -1)
                message = source.createMessage(destination, messageContent, messageType, messageLength);
            else
                message = source.createMessagePriority(destination, messageContent, messageType, messageLength, priority);

            source.sendMailbox(mailbox, message);
            return true;
        } 
    }
    
    public void receiveMessage(int idSourceProcess, int idDestinationProcess)
    {
        Process source = processList.get(idSourceProcess);
        Process destination = processList.get(idDestinationProcess);
        destination.receive(source);
    }
    
    public void createMailbox(int ID, int queueSize, QueueType queueType)
    {
        mailboxList.put(ID, new Mailbox(ID,queueSize, queueType));
    } 
    
    public void addReceiverMailbox(int mailboxId,int receiverId)
    {
        
        Mailbox mailbox = mailboxList.get(mailboxId);
        Process receiver = processList.get(receiverId);
        mailbox.addReceiver(receiver);
    }
    
    public Process display(int processID)
    {
        Process displayProcess = processList.get(processID);
        return displayProcess;
    }
    
    public Hashtable<Integer, Process> getProcessList() {
        return processList;
    }

    public void setProcessList(Hashtable<Integer, Process> processList) {
        this.processList = processList;
    }
    
    
}
