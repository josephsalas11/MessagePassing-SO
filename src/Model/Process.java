/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Paulo
 */
public class Process {
    private int id;
    private Producer producer;
    private IReceiver receiver;
    private Process senderProcess; //si es direccionamiento directo explícito
    
    //para direccionamiento directo explicito
    public Process(int id, SynchronizationType synchronizationTypeProducer, QueueType queueType, int queueSize, SynchronizationType synchronizationTypeReceiver, Process senderProcess){
        this.id = id;
        this.producer = new Producer(queueSize, synchronizationTypeProducer, queueType);
        this.receiver = new Receiver(senderProcess.producer, synchronizationTypeReceiver);
        this.senderProcess = senderProcess;
        
        producer.start();
        receiver.start();
    }
    
    //para direccionamiento directo implicito
    public Process(int id, SynchronizationType synchronizationTypeProducer, QueueType queueType, int queueSize, SynchronizationType synchronizationTypeReceiver){
        this.id = id;
        this.producer = new Producer(queueSize, synchronizationTypeProducer, queueType);
        this.receiver = new Receiver(null, synchronizationTypeReceiver);
        
        producer.start();
        receiver.start();
    }
    
    public Message createMessage(Process destination, String messageContent, MessageType messageType, int messageLength){
        Message message = new Message(messageType, destination.id, id, messageLength, messageContent);
        return message;
    }
    
    public void send(Process destination, Message message){
        producer.getMessageQueue().addMessage(message);
    }
    
    public void receive(Process source, Message message){
        if(senderProcess != null && source.id != senderProcess.id)
            System.out.println("El proceso que envió el mensaje no corresponde con el especificado en este proceso");
        else{
            //recibir mensaje
            receiver.setAllowReceive(true);
        }
    }

    public int getId() {
        return id;
    }

    public void setWaitReceive(boolean waitReceive) {
        receiver.setWaitReceive(false);
    }
    
    
    
    
    
}
