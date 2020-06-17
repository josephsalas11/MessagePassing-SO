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
public class Producer extends Thread implements IProducer{
    private IMessageQueue messageQueue;
    private SynchronizationType synchronizationType;
    private IReceiver receiver;

    public Producer(int queueSize, SynchronizationType synchronizationType, QueueType queueType) {
        if(queueType == QueueType.FIFO)
            messageQueue = new FIFOQueue(queueSize);
        else if(queueType == QueueType.PRIORITY){
            messageQueue = new QueuePriority(queueSize);
        }
        this.synchronizationType = synchronizationType;
    }

    @Override
    public void run(){
        try {
            while(true){
                putMessage();
                sleep(1000);
            }
            } catch (InterruptedException  e) {
        }
    }

    @Override
    public void putMessage() throws InterruptedException {
        if(synchronizationType == SynchronizationType.BLOCKING){ //&& no se envia a mailbox
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
    
    
    //lo sustituye create message en process

    private synchronized void putMessageBlocking() throws InterruptedException{
        while(messageQueue.getQueueSize() == messageQueue.getSize()){

            wait();
        }
        //messageQueue.addMessage(message); ver como manejar mensajes
        wait(); //para esperar que el otro hilo responda

        if(receiver != null && receiver.getSynchronizationType() == SynchronizationType.BLOCKING){
            receiver.receiveMessage();
            receiver = null;
        }
        notify();
    }
    

    private synchronized void putMessageNonblocking() throws InterruptedException{
        while(messageQueue.getQueueSize() == messageQueue.getSize()){

            wait();
        }
        //messageQueue.addElement(message); ver como manejar mensajes

        if(receiver != null && receiver.getSynchronizationType() == SynchronizationType.BLOCKING){
            receiver.receiveMessage();
            receiver = null;
        }
        //notify();
    }
    
    @Override
    public synchronized Message getMessage(IReceiver receiver) throws InterruptedException{
        this.receiver = receiver;
        sleep(1000);
        
        //si el tipo de sincronizacion del producer es blocking
        //se puede cambiar por sendMessage
        if(synchronizationType == SynchronizationType.BLOCKING){
            notify();
        }
        else if(synchronizationType == SynchronizationType.NONBLOCKING){

        }
        
        //si el tipo de sincronizacion del receiver es blocking
        /*if(receiver.getSynchronizationType() == SynchronizationType.BLOCKING){
            receiver.receiveMessage(); //para que se desbloquee el hilo del receiver
        }*/
        
        while(messageQueue.isQueueEmpty())
            sleep(5000);
        Message message = messageQueue.getMessage(); 
        messageQueue.remove(message);
        return message;
    }

    public IMessageQueue getMessageQueue() {
        return messageQueue;
    }

    @Override
    public void sendMessage() {
        if(synchronizationType == SynchronizationType.BLOCKING){
            notify();
        }
    }
    
    
    
}
