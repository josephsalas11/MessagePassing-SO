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
                        sleep(1000);
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
        Message message = producer.getMessage(this);
        if(message != null){
            if(synchronizationType == SynchronizationType.BLOCKING && producer.getClass() != Mailbox.class){
                //while se obtiene el mensaje: wait
                wait();
                System.out.println(message.getContent());
                sleep(1000);
            }
            else { //if(synchronizationType == SynchronizationType.NONBLOCKING)
                System.out.println(message.getContent());
                sleep(1000);
            }

        }
        else{
            System.out.println("El receiver no está autorizado para acceder a este recurso");
        }
        if(waitReceive)
            allowReceive = false; //para esperar comando de receive()
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
