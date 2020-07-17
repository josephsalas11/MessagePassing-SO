/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    
    private boolean dynamic;
    

    public Mailbox(int id, int queueSize, QueueType queueType, boolean dynamic) {
        this.id = id;
        this.dynamic = dynamic;
        if(queueType == QueueType.FIFO){
            messageQueue = new FIFOQueue(queueSize);
        }
        else if(queueType == QueueType.PRIORITY){
            messageQueue = new QueuePriority(queueSize);
        }
        
        producers = new ArrayList<>();
        receivers = new ArrayList<>();
        //LOG
        Log.getInstance().addLog(id, "El mailbox "+id+" ha sido creado exitosamente", false);
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
            Log.getInstance().addLog(id, "El mailbox "+id+" ha denegado el acceso para obtener un mensaje", false);
            return messageTmp;
        }
        
        if(messageTmp != null){
            //desbloqueo de producer
            if(messageTmp.getSource().getProducer().getSynchronizationType() == SynchronizationType.BLOCKING){
                messageTmp.getSource().getProducer().freeProducer();
                Log.getInstance().addLog(messageTmp.getSourceID(), "El proceso "+messageTmp.getSourceID()+" se ha desbloqueado exitosamente", true);
            }
            Log.getInstance().addLog(id, "El mailbox "+id+" ha enviado el mensaje '"+messageTmp.getContent()+"' del proceso "+messageTmp.getSourceID(), false);
        }
        else{
            Log.getInstance().addLog(id, "La cola de mensajes del mailbox "+id+" está vacia", false);
        }
        return messageTmp;
    }
    
    //no usar?
    private Message searchMessage(int receiverId){
        Message messageTmp = messageQueue.getMessageReceiver(receiverId);
        return messageTmp;
    }
    
    //MAILBOX
    //Prueba
    
    public void addProducer(Process producer){
        producers.add(producer.getProducer());

        //LOG
        Log.getInstance().addLog(id, "El proceso "+producer.getId()+" ha sido agregado a la lista del mailbox "+id, false);
    }
    
    public void addReceiver(Process receiver){
        receivers.add(receiver.getReceiver());
        
        //LOG
        Log.getInstance().addLog(id, "El proceso "+receiver.getId()+" ha sido agregado a la lista del mailbox "+id, false);
    }
    
    @Override
    public boolean addMessage(Message message){
        if(isProducerAllowed(message.getSource().getProducer())){
            if(messageQueue.addMessage(message) == false){ //aqui se agrega
                Log.getInstance().addLog(id, "La cola de mensajes del mailbox "+id+" está llena, no se pueden agregar más mensajes", false);
                return false;
            }
            if(message.getDestinyID() == -1)
                Log.getInstance().addLog(id, "El mailbox "+id+" ha recibido el mensaje '"+message.getContent()+" del proceso "+message.getSourceID(), false);
            else
                Log.getInstance().addLog(id, "El mailbox "+id+" ha recibido el mensaje '"+message.getContent()+"' del proceso "+message.getSourceID()+" para el proceso "+message.getDestinyID(), false);

            return true;
        }
        else{
            Log.getInstance().addLog(id, "El mailbox "+id+" ha denegado el acceso para recibir un mensaje", false);
            return false;
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
    
    public boolean isProducerAllowed(IProducer producer){
        if(dynamic){
            for(int i=0; i<producers.size(); i++){
                if(producers.get(i).equals(producer)){
                    System.out.println("true");
                    return true;
                }
            }
            return false;
        }
        return true;
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

    @Override
    public boolean isBlocked() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String stateToString() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getQueueSize() {
        return messageQueue.getQueueSize();
    }

    @Override
    public String getQueueMessages() {
        return messageQueue.toString(); //tal vez cambiar por el receiver
    }

    @Override
    public String getQueueLog() {
        return messageQueue.getLogToString();
    }
    
    @Override
    public String toString(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();  
        
        String result = dtf.format(now)+"\n"+
                        "ID del mailbox: "+id+"\n"+
                        "Tamaño de la cola: "+getQueueSize()+"\n"+
                        "Mensajes:\n"+
                        getQueueMessages()+"\n\n";
        
        return result;
    }
}
