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

    public Producer(int queueSize, enum synchronizationType, enum queueType) {
        if(queueType == QueueType.FIFO)
            messageQueue = new FIFOQueue(queueSize);
        else if(queueType == QueueType.PRIORITY)
            //messageQueue = new PriorityQueue(queueSize);
        this.synchronizationType = synchronizationType;
    }

    @Override
    public void run(){
        while(true){
            if(synchronizationType == SynchronizationType.BLOCKING){
                putMessageBlocking();
                sleep(1000);
            }
            else if(synchronizationType == SynchronizationType.NONBLOCKING){
                putMessageNonblocking();
            }
        }
    }
    
    private synchronized void putMessageBlocking(){
        while(messageQueue.size() == messageQueue.getSize()){
            wait();
        }
        //messageQueue.addQueue(message); ver como manejar mensajes
        notify();
    }
    
    private void putMessageNonblocking(){
        while(messageQueue.)
    }
    
}
