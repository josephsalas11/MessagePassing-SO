/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import static java.lang.Thread.sleep;

/**
 *
 * @author Paulo
 */
public class Receiver extends Thread implements IReceiver{
    private IProducer producer;
    private SynchronizationType synchronizationType;
    private boolean allowReceive = false;
    private boolean waitReceive = true;

    public Receiver(IProducer producer, SynchronizationType synchronizationType) {
        this.producer = producer;
        this.synchronizationType = synchronizationType;
    }
    
    @Override
    public void run(){
        try {
            while(true){
                if(producer != null && allowReceive){
                    if(producer.getMessageQueue().isQueueEmpty()){ //hacer validacion si se cumple condicion sleep(1)
                        //Message m4 = new Message(MessageType.FIFO, 2, 1, 10, "Soy un pura mierda 4");
                        //producer.getMessageQueue().addMessage(m4);
                        sleep(1);
                    }
                    else{
                        getProducerMessage();
                    }
                }
            }
        } catch (InterruptedException e) {
        }
    }
    
    @Override
    public synchronized void getProducerMessage() throws InterruptedException{
        if(synchronizationType == SynchronizationType.BLOCKING){
            //while se obtiene el mensaje: wait
            Message message = producer.getMessage(this);
            wait();
            System.out.println(message.getContent());
            sleep(1000);
        }
        else if(synchronizationType == SynchronizationType.NONBLOCKING){
            Message message = producer.getMessage(this);
            System.out.println(message.getContent());
            sleep(1000);
        }
        if(waitReceive)
            allowReceive = false; //para esperar comando de receive()
        //System.out.println(allowReceive);
    }
    
    @Override
    public synchronized void receiveMessage(){
        notify();
    }

    @Override
    public SynchronizationType getSynchronizationType() {
        return synchronizationType;
    }

    public boolean isAllowReceive() {
        return allowReceive;
    }

    @Override
    public void setAllowReceive(boolean allowReceive) {
        this.allowReceive = allowReceive;
    }

    public boolean isWaitReceive() {
        return waitReceive;
    }

    @Override
    public void setWaitReceive(boolean waitReceive) {
        this.waitReceive = waitReceive;
    }
    
    
    
    
}
