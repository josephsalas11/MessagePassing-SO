/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author Paulo
 */
public class Mailbox implements IProducer{
    
    private int id;
    private IMessageQueue messageQueue;
    private ArrayList<IProducer> producers;
    private ArrayList<IReceiver> receivers;
    private int lastMessageCounter = 0;
    

    public Mailbox(int id, int queueSize, QueueType queueType) {
        this.id = id;
        if(queueType == QueueType.FIFO){
            messageQueue = new FIFOQueue(queueSize);
        }
        else if(queueType == QueueType.PRIORITY){
            messageQueue = new QueuePriority(queueSize);
        }
        
        producers = new ArrayList<>();
        receivers = new ArrayList<>();
        //LOG
        Log.getInstance().addLog(-1, "El mailbox "+id+" ha sido creado exitosamente");
    }
    
    //PRODUCER

    @Override
    public synchronized void putMessage() throws InterruptedException {
        /*while(messageQueue.getQueue().size() == messageQueue.getSize()){
            wait();
        }
        for(int i=0; i<receivers.size(); i++){
            IReceiver receiver = receivers.get(i);
            
            if(receiver != null && receiver.getSynchronizationType() == SynchronizationType.BLOCKING){
            receiver.receiveMessage();
            }
        }*/
        if(messageQueue.getQueueSize() < messageQueue.getSize()){
            //System.out.println(receivers.size());
            for(int i=0; i<receivers.size(); i++){
                IReceiver receiver = receivers.get(i);
                
                if(receiver != null && receiver.getSynchronizationType() == SynchronizationType.BLOCKING){
                    receiver.receiveMessage();
                    System.out.println("asd");
                }
            }
        }
    }

    @Override
    public Message getMessage(IReceiver receiver) throws InterruptedException {
        //aqui habria que poner el sendMessage del producer correspondiente
        
        if(isReceiverAllowed(receiver)){
            Message message = messageQueue.getMessage();
            lastMessageCounter++;
            //receiver.receiveMessage();

            if(lastMessageCounter == receivers.size()){
                messageQueue.poll();
                lastMessageCounter = 0;
                //putMessage();
            }
            return message;
        }
        return null;
    }

    @Override
    public IMessageQueue getMessageQueue() {
        return messageQueue;
    }
    
    @Override
    public void sendMessage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    //MAILBOX
    
    public void addProducer(Process producer){
        producers.add(producer.getProducer());
    }
    
    public void addReceiver(Process receiver){
        receivers.add(receiver.getReceiver());
        
        //LOG
        Log.getInstance().addLog(receiver.getId(), "El proceso "+receiver.getId()+" ha sido agregado a la lista del mailbox "+id);
    }
    
    public void addMessage(Message message){
        if(messageQueue.addMessage(message) == false){ //aqui se agrega
            System.out.println("No se pueden agregar más procesos, la cola está llena");
        }
    }
    
    public boolean isReceiverAllowed(IReceiver receiver){
        for(int i=0; i<receivers.size(); i++){
            if(receivers.get(i).equals(receiver)){
                return true;
            }
        }
        return false;
    }

    public int getId() {
        return id;
    }
    
    
}
