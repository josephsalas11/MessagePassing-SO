
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
    
    

    public void createDirectProcess(int processCounter,SynchronizationType STP,QueueType queueType, int queueSizeType, SynchronizationType STR)
    {
         processList.put(processCounter, new Process(processCounter, STP,
                queueType, queueSizeType,STR));
    }
    
    /*public void createIndirectProcess(int processCounter,SynchronizationType STP,QueueType queueType, int queueSizeType, SynchronizationType STR, int ID_MB)
    {
        Mailbox mailbox = mailboxList.get(ID_MB);
        processList.put(processCounter, new Process(processCounter, STP,
                queueType, queueSizeType,STR, mailbox));
    }
    */
    
    
    public void createStaticProcess(int processCounter,SynchronizationType STP,QueueType queueType, int queueSizeType, SynchronizationType STR)
    {
        createMailbox(mailboxList.size()+1,queueSizeType,queueType, false);
        processList.put(processCounter, new Process(processCounter, STP,
                queueType, queueSizeType,STR));
        addReceiverMailbox(mailboxList.size(),processCounter);
    }
    
        
        
    public void createDynamicProcess(int processCounter,SynchronizationType STP,QueueType queueType, int queueSizeType, SynchronizationType STR)
    {
        //createMailbox(mailboxList.size()+1,queueSizeType,queueType);
        processList.put(processCounter, new Process(processCounter, STP,
                queueType, queueSizeType,STR));
    }
    
    public void resetSystem()
    {
        for (int i = 1; i <= processList.size(); i++) {
            processList.get(i).stopProcess();    
        }
        processList.clear();
        mailboxList.clear();
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
        
            if(priority == 0)
                message = source.createMessage(destination, messageContent, messageType, messageLength, processList.get(idSourceProcess), false);
            else
                message = source.createMessagePriority(destination, messageContent, messageType, messageLength, priority, processList.get(idSourceProcess), false);

            source.send(destination, message);
            return true;
        }
    }
    
    public boolean sendIndirectProcess(int idSourceProcess, int idDestinationProcess, MessageType messageType, int messageLength, String messageContent, int priority) throws InterruptedException
    {        
        Process source = processList.get(idSourceProcess);
        Process destination = null;
        Mailbox mailbox = mailboxList.get(idDestinationProcess);
        
        //validacion para determinar que el producer, el receiver y el mailbox existan en el controlador
        if(source == null || mailbox == null){
            System.out.println("Operacion invalida" +idSourceProcess+" "+idDestinationProcess); //hacer log de error
            return false;
        }
        
        else{
            Message message = null;
        
            if(priority == 0)
                message = source.createMessage(destination, messageContent, messageType, messageLength, processList.get(idSourceProcess), true);
            else
                message = source.createMessagePriority(destination, messageContent, messageType, messageLength, priority, processList.get(idSourceProcess), true);

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
    
    public void receiveImplicitMessage(int idDestinationProcess)
    {
        Process destination = processList.get(idDestinationProcess);
        destination.receive();
    }
    
    public void receiveIndirectMessage(int mailboxId, int idDestinationProcess)
    {
        Mailbox mailbox = mailboxList.get(mailboxId);
        Process destination = processList.get(idDestinationProcess);
        destination.receiveMailbox(mailbox);
    }
    
    public void createMailbox(int ID, int queueSize, QueueType queueType, boolean dynamic)
    {
        mailboxList.put(ID, new Mailbox(ID,queueSize, queueType, dynamic));
    } 
    
    public void addReceiverMailbox(int mailboxId,int receiverId)
    {   
        Mailbox mailbox = mailboxList.get(mailboxId);
        Process receiver = processList.get(receiverId);
        mailbox.addReceiver(receiver);
        //LOG
    }
       
    public void addProducerMailbox(int mailboxId,int producerId)
    {   
        //Añadir excepción si no encuentra el producer o sino encuentra el mailbox
        Mailbox mailbox = mailboxList.get(mailboxId);
        Process producer = processList.get(producerId);
        mailbox.addProducer(producer);
        //LOG
    }
    
    
    public Process getProcess(int processID)
    {
        Process displayProcess = processList.get(processID);
        return displayProcess;
    }
    
    public Mailbox getMailbox(int mailboxID){
        Mailbox mailbox = mailboxList.get(mailboxID);
        return mailbox;
    }
    
    public Hashtable<Integer, Process> getProcessList() {
        return processList;
    }

    public void setProcessList(Hashtable<Integer, Process> processList) {
        this.processList = processList;
        
    }

    public Hashtable<Integer, Mailbox> getMailboxList() {
        return mailboxList;
    }

    public void setMailboxList(Hashtable<Integer, Mailbox> mailboxList) {
        this.mailboxList = mailboxList;
    }
    
    
    
    
}
