/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.Command.CommandTokenizer;

/**
 *
 * @author Paulo
 */
public class Process {
    private int id;
    private Producer producer;
    private IReceiver receiver;

    public Process() {
        System.out.println("Creo un proceso");
    }
    
    //para direccionamiento directo
    public Process(int id, SynchronizationType synchronizationTypeProducer, QueueType queueType, int queueSize, SynchronizationType synchronizationTypeReceiver){
        this.id = id;
        this.producer = new Producer(synchronizationTypeProducer, queueType, queueSize);
        this.receiver = new Receiver(null, synchronizationTypeReceiver, queueType, queueSize, id);
        
        //LOG
        Log.getInstance().addLog(id, "El proceso "+id+" de tipo "+synchronizationTypeProducer.toString()+"-"+synchronizationTypeReceiver.toString()+" directo implicito ha sido creado exitosamente", true);

        producer.start();
        receiver.start();
    }
    
    //para direccionamiento indirecto SE USA SOLO EL DE ARRIBA
    public Process(int id, SynchronizationType synchronizationTypeProducer, QueueType queueType, int queueSize, SynchronizationType synchronizationTypeReceiver, IProducer mailbox){
        this.id = id;
        this.producer = new Producer(synchronizationTypeProducer, queueType, queueSize);
        this.receiver = new Receiver(mailbox, synchronizationTypeReceiver, queueType, queueSize, id);
        
        //LOG
        Log.getInstance().addLog(id, "El proceso "+id+" de tipo "+synchronizationTypeProducer.toString()+"-"+synchronizationTypeReceiver.toString()+" indirecto ha sido creado exitosamente", true);
        
        producer.start();
        receiver.start();
    }
    
    public Message createMessage(Process destination, String messageContent, MessageType messageType, int messageLength, Process source, boolean isMailbox){
        Message message = null;
        if(destination == null){
            message = new Message(messageType, -1, id, messageLength, messageContent, source, destination, isMailbox);

        }
        else{
            message = new Message(messageType, destination.id, id, messageLength, messageContent, source, destination, isMailbox);
        }
        return message;
    }
    
    public Message createMessagePriority(Process destination, String messageContent, MessageType messageType, int messageLength, int priority, Process source, boolean isMailbox){
        Message message = new Message(messageType, destination.id, id, messageLength, messageContent, priority, source, destination, isMailbox);
        return message;
    }
    
    //directo
    public void send(Process destination, Message message){
        
        if(producer.addMessage(message) == false){
            System.out.println("No se pudo ingresar el mensaje porque la cola esta llena");
        }
        //LOG
        Log.getInstance().addLog(id, "El proceso "+id+" ha enviado el comando para enviar el mensaje '"+message.getContent()+"' al proceso "+destination.id, true);
    }
    
    //indirecto dinamico
    public void sendMailbox(Mailbox mailbox, Message message) throws InterruptedException{
        if(mailbox.addMessage(message) == false){
            System.out.println("No se pudo ingresar el mensaje porque la cola esta llena");
        }
        if(message.getDestinyID() == -1)
            Log.getInstance().addLog(id, "El proceso "+message.getSourceID()+" ha enviado el comando para enviar el mensaje '"+message.getContent()+"' a través del mailbox "+mailbox.getId(), true);
        else
            Log.getInstance().addLog(id, "El proceso "+message.getSourceID()+" ha enviado el comando para enviar el mensaje '"+message.getContent()+"' al proceso "+message.getDestinyID()+ " a través del mailbox "+mailbox.getId(), true);
        //mailbox.putMessage();
    }
    
    //implicito
    public void receive(){
        receiver.setAllowReceive(true);         
        Log.getInstance().addLog(id, "El proceso "+id+" ha enviado el comando para recibir un mensaje", true);
    }
    
    //explicito
    public void receive(Process source){

       //recibir mensaje
       if(CommandTokenizer.getInstance().indirect == false)
        {
           //receiver.setProducer(source.getProducer());
        }
        receiver.setCurrentId(source.getId());
        receiver.setAllowReceive(true);
        Log.getInstance().addLog(id, "El proceso "+id+" ha enviado el comando para recibir un mensaje del proceso "+source.id, true);
    }
    
    //indirecto
    public void receiveMailbox(Mailbox mailbox){
        receiver.setCurrentMailbox(mailbox);
        receiver.setAllowReceive(true);
        Log.getInstance().addLog(id, "El proceso "+id+" ha enviado el comando para recibir un mensaje del mailbox "+mailbox.getId(), true);
    }
    

    
    
    public void stopProcess()
    {
        producer.stop();
        receiver.stop();
    }
    

    public int getId() {
        return id;
    }

    public void setWaitReceive(boolean waitReceive) {
        receiver.setWaitReceive(false);
    }

    public Producer getProducer() {
        return producer;
    }

    public IReceiver getReceiver() {
        return receiver;
    }

    public void setReceiver(IReceiver receiver) {
        this.receiver = receiver;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }
    
}
