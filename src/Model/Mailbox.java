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
    public void putMessage() throws InterruptedException {
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
                    receiver.receiveMessage(); //CAMBIAR
                    System.out.println("asd");
                }
            }
        }
    }
    
    /*
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
            
            //LOG
            Log.getInstance().addLog(message.getDestinyID(), "El proceso "+message.getDestinyID()+" ha recibido un mensaje del proceso "+message.getSourceID()+
                    " con el mensaje "+message.getContent());
            return message;
        }
        return null;
    }
    */
    
    @Override
    public void sendMessage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Message retreiveMessage(IReceiver receiver){
        Message messageTmp = null;
        if(isReceiverAllowed(receiver)){
            messageTmp = messageQueue.poll();
        }
        else{
            System.out.println("El proceso no tiene permitido acceder a este meilbox");
        }
        
        if(messageTmp != null){
            //desbloqueo de producer
            if(messageTmp.getSource().getProducer().getSynchronizationType() == SynchronizationType.BLOCKING){
                messageTmp.getSource().getProducer().freeProducer();
            }
            System.out.println(messageTmp.getContent());
        }
        else{
            System.out.println("La cola de mensaje está vacia");
        }
        return messageTmp;
    }
    
    //no usar?
    private Message searchMessage(int receiverId){
        Message messageTmp = messageQueue.getMessageReceiver(receiverId);
        return messageTmp;
    }
    
    //MAILBOX
    
    public void addProducer(Process producer){
        producers.add(producer.getProducer());
        
        //LOG
        Log.getInstance().addLog(producer.getId(), "El proceso "+producer.getId()+" ha sido agregado a la lista del mailbox "+id);
    }
    
    public void addReceiver(Process receiver){
        receivers.add(receiver.getReceiver());
        
        //LOG
        Log.getInstance().addLog(receiver.getId(), "El proceso "+receiver.getId()+" ha sido agregado a la lista del mailbox "+id);
    }
    
    @Override
    public boolean addMessage(Message message){
        if(messageQueue.addMessage(message) == false){ //aqui se agrega
            System.out.println("No se pueden agregar más procesos, la cola está llena");
            return false;
        }
        return true;
    }
    
    public boolean isReceiverAllowed(IReceiver receiver){
        for(int i=0; i<receivers.size(); i++){
            if(receivers.get(i).equals(receiver)){
                return true;
            }
        }
        return false;
    }
    
    public boolean isProducerAllowed(IProducer producer){
        for(int i=0; i<producers.size(); i++){
            if(producers.get(i).equals(producer)){
                return true;
            }
        }
        return false;
    }

    public int getId() {
        return id;
    }

    @Override
    public SynchronizationType getSynchronizationType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized void freeProducer() {
        notify();
    }
    
    
}
