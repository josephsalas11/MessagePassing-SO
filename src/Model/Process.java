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
    private Receiver receiver;

    public Process(int id, SynchronizationType synchronizationTypeProducer, QueueType queueType, int queueSize, SynchronizationType synchronizationTypeReceiver) {
        this.id = id;
        this.producer = new Producer(queueSize, synchronizationTypeProducer, queueType);
        this.receiver = new Receiver(producer, synchronizationTypeReceiver);
        
        producer.start();
        receiver.start();
    }
    
    public void createMessage(int destination, String messageContent, MessageType messageType, int messageLength){
        Message message = new Message(messageType, destination, id, messageLength, messageContent);
        producer.getMessageQueue().addMessage(message);
    }
    
}
