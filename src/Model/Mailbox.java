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
public class Mailbox implements IProducer, IReceiver{
    
    private IMessageQueue messageQueue;
    private ArrayList<IProducer> producers;
    private ArrayList<IReceiver> receivers;
    

    public Mailbox(int queueSize, QueueType queueType) {
        if(queueType == QueueType.FIFO){
            messageQueue = new FIFOQueue(queueSize);
        }
        else if(queueType == QueueType.PRIORITY){
            //hacer
        }
        
        producers = new ArrayList<>();
        receivers = new ArrayList<>();
    }
    
    /*
    @Override
    public void run() {
        try {
            while(true){
                //funcion del receiver
                if(producers.isEmpty() != false){
                    for(int i=0; i<producers.size(); i++){
                        IProducer producer = producers.get(i);
                        
                        if(producer.getMessageQueue().getQueue().isEmpty()){ //hacer validacion si se cumple condicion sleep(1)
                        //Message m4 = new Message(MessageType.FIFO, 2, 1, 10, "Soy un pura mierda 4");
                        //producer.getMessageQueue().addMessage(m4);
                        sleep(1);
                        }
                        else{
                            getProducerMessage();
                        }
                    }
                }
                //funcion del producer
                putMessage();             
            }
        } catch (InterruptedException e) {
        }
    }
    */
    
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
        
    }

    @Override
    public Message getMessage(IReceiver receiver) throws InterruptedException {
        return null;
    }

    @Override
    public IMessageQueue getMessageQueue() {
        return messageQueue;
    }
    
    //RECEIVER

    @Override
    public void getProducerMessage() throws InterruptedException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void receiveMessage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SynchronizationType getSynchronizationType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setAllowReceive(boolean allowReceive) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setWaitReceive(boolean waitReceive) {
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
        if(messageQueue.addMessage(message) == false){
            System.out.println("No se pueden agregar más procesos, la cola está llena");
        }
    }

    @Override
    public void start() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
