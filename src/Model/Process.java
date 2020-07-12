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
        this.receiver = new Receiver(null, synchronizationTypeReceiver, queueType, queueSize);
        
        //LOG
        Log.getInstance().addLog(id, "El proceso "+id+" de tipo "+synchronizationTypeProducer.toString()+"-"+synchronizationTypeReceiver.toString()+" directo implicito ha sido creado exitosamente");

        producer.start();
        receiver.start();
    }
    
    //para direccionamiento indirecto
    public Process(int id, SynchronizationType synchronizationTypeProducer, QueueType queueType, int queueSize, SynchronizationType synchronizationTypeReceiver, IProducer mailbox){
        this.id = id;
        this.producer = new Producer(synchronizationTypeProducer, queueType, queueSize);
        this.receiver = new Receiver(mailbox, synchronizationTypeReceiver, queueType, queueSize);
        
        //LOG
        Log.getInstance().addLog(id, "El proceso "+id+" de tipo "+synchronizationTypeProducer.toString()+"-"+synchronizationTypeReceiver.toString()+" indirecto ha sido creado exitosamente");
        
        producer.start();
        receiver.start();
    }
    
    public Message createMessage(Process destination, String messageContent, MessageType messageType, int messageLength){
        Message message = new Message(messageType, destination.id, id, messageLength, messageContent);
        return message;
    }
    
    public Message createMessagePriority(Process destination, String messageContent, MessageType messageType, int messageLength, int priority){
        Message message = new Message(messageType, destination.id, id, messageLength, messageContent, priority);
        return message;
    }
    
    public void send(Process destination, Message message){
        //producer.setReceiver(destination.getReceiver());
        //destination.setProducer(producer); //cambiar
        if(producer.addMessage(message) == false){
            System.out.println("No se pudo ingresar el mensaje porque la cola esta llena");
        }
        //LOG
        Log.getInstance().addLog(id, "El proceso "+id+" ha enviado el comando para enviar un mensaje al proceso "+destination.id);
    }
    
    public void sendMailbox(Mailbox mailbox, Message message) throws InterruptedException{
        mailbox.addMessage(message);
        //LOG
        Log.getInstance().addLog(id, "El proceso "+message.getSourceID()+" ha enviado el comando para enviar un mensaje al proceso "+message.getDestinyID()+ " a través del mailbox "+mailbox.getId());
        //mailbox.putMessage();
    }
    
    //implicito
    public void receive(){
        receiver.setAllowReceive(true);         
        Log.getInstance().addLog(id, "El proceso "+id+" ha enviado el comando para recibir un mensaje");
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
        Log.getInstance().addLog(id, "El proceso "+id+" ha enviado el comando para recibir un mensaje del proceso "+source.id);
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
