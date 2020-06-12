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
    
    private synchronized void putMessageBlocking() throws InterruptedException{
        while(messageQueue.getQueue().size() == messageQueue.getSize()){
            wait();
        }
        //messageQueue.addElement(message); ver como manejar mensajes
        wait(); //para esperar que el otro hilo responda
        notify();
    }
    
    private void putMessageNonblocking() throws InterruptedException{
        while(messageQueue.getQueue().size() == messageQueue.getSize()){
            wait();
        }
        //messageQueue.addElement(message); ver como manejar mensajes
        notify();
    }
    
    public synchronized Message getMessage() throws InterruptedException{
        if(synchronizationType == SynchronizationType.BLOCKING){
            notify();
        }
        else if(synchronizationType == SynchronizationType.NONBLOCKING){
            
        }
        while(messageQueue.getQueue().size() == 0)
            sleep(1);
        //notify(); //si la cola tiene elementos
        Message message = messageQueue.getMessage();
        
        //dependiendo del tipo de cola se elimina el mensaje, implementado en interfaz
        messageQueue.remove(message);
        return message;
    }

    public IMessageQueue getMessageQueue() {
        return messageQueue;
    }
    
    
    
}
