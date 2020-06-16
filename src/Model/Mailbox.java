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
    
    private IMessageQueue messageQueue;
    private ArrayList<IProducer> producers;
    private ArrayList<IReceiver> receivers;
    

    public Mailbox(int queueSize, QueueType queueType) {
        if(queueType == QueueType.FIFO){
            messageQueue = new FIFOQueue(queueSize);
        }
        else if(queueType == QueueType.PRIORITY){
            messageQueue = new QueuePriority(queueSize);
        }
        
        producers = new ArrayList<>();
        receivers = new ArrayList<>();
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
            for(int i=0; i<receivers.size(); i++){
                IReceiver receiver = receivers.get(i);
            
                if(receiver != null && receiver.getSynchronizationType() == SynchronizationType.BLOCKING){
                    receiver.receiveMessage();
                }
            }
        }
    }

    @Override
    public Message getMessage(IReceiver receiver) throws InterruptedException {
        //aqui habria que poner el sendMessage del producer correspondiente
        System.out.println("Entro");
        Message message = messageQueue.getMessage(); 
        //messageQueue.remove(message);
        return message;
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
    
    public void addProducer(IProducer producer){
        producers.add(producer);
    }
    
    public void addReceiver(IReceiver receiver){
        receivers.add(receiver);
    }
    
    public void addMessage(Message message){
        if(messageQueue.addMessage(message) == false){ //aqui se agrega
            System.out.println("No se pueden agregar más procesos, la cola está llena");
        }
    }
    
}
