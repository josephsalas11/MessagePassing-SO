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
public class Producer extends Thread{
    private IMessageQueue messageQueue;
    private SynchronizationType synchronizationType;
    private Receiver receiver;

    public Producer(int queueSize, SynchronizationType synchronizationType, QueueType queueType) {
        if(queueType == QueueType.FIFO)
            messageQueue = new FIFOQueue(queueSize);
        else if(queueType == QueueType.PRIORITY){
            //messageQueue = new PriorityQueue(queueSize);
        }
        this.synchronizationType = synchronizationType;
    }

    @Override
    public void run(){
        try {
            while(true){
            if(synchronizationType == SynchronizationType.BLOCKING){
                System.out.println("entro b");
                putMessageBlocking();
                sleep(1000);
                //getProducer
            }
            else if(synchronizationType == SynchronizationType.NONBLOCKING){
                System.out.println("entro nb");
                putMessageNonblocking();
                sleep(1000);
                }
            }
            } catch (InterruptedException  e) {
        }
    }
    
    //lo sustituye create message en process
    private synchronized void putMessageBlocking() throws InterruptedException{
        while(messageQueue.getQueue().size() == messageQueue.getSize()){
            wait();
        }
        //messageQueue.addMessage(message); ver como manejar mensajes
        wait(); //para esperar que el otro hilo responda
        //receiver.receiveMessage();
        if(receiver != null && receiver.getSynchronizationType() == SynchronizationType.BLOCKING){
            receiver.receiveMessage();
            receiver = null;
        }
        notify();
    }
    
    private synchronized void putMessageNonblocking() throws InterruptedException{
        while(messageQueue.getQueue().size() == messageQueue.getSize()){
            wait();
        }
        //messageQueue.addElement(message); ver como manejar mensajes
        //receiver.receiveMessage();
        if(receiver != null && receiver.getSynchronizationType() == SynchronizationType.BLOCKING){
            receiver.receiveMessage();
            receiver = null;
        }
        //notify();
    }
    
    public synchronized Message getMessage(Receiver receiver) throws InterruptedException{
        this.receiver = receiver;
        sleep(1000);
        
        //si el tipo de sincronizacion del producer es blocking
        if(synchronizationType == SynchronizationType.BLOCKING){
            notify();
        }
        else if(synchronizationType == SynchronizationType.NONBLOCKING){
            
        }
        
        //si el tipo de sincronizacion del receiver es blocking
        /*if(receiver.getSynchronizationType() == SynchronizationType.BLOCKING){
            receiver.receiveMessage(); //para que se desbloquee el hilo del receiver
        }*/
        
        while(messageQueue.getQueue().isEmpty())
            sleep(5000);
        Message message = messageQueue.getMessage(); 
        //dependiendo del tipo de cola se elimina el mensaje, implementado en interfaz
        messageQueue.remove(message);
        return message;
    }

    public IMessageQueue getMessageQueue() {
        return messageQueue;
    }
    
}
