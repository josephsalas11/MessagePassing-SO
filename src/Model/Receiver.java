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
public class Receiver extends Thread{
    private Producer producer;
    private SynchronizationType synchronizationType;

    public Receiver(Producer producer, SynchronizationType synchronizationType) {
        this.producer = producer;
        this.synchronizationType = synchronizationType;
    }
    
    @Override
    public void run(){
        try {
            while(true){ 
            if(producer.getMessageQueue().getQueue().isEmpty()){ //hacer validacion si se cumple condicion sleep(1)
                Message m4 = new Message(MessageType.FIFO, 2, 1, 10, "Soy un pura mierda 4");
                producer.getMessageQueue().addMessage(m4);
            }
            /*Message message = producer.getMessage(); //si hay mensajes espera
            System.out.println(message.getContent());
            sleep(2000);*/
            getProducerMessage();
        }
        } catch (InterruptedException e) {
        }
    }
    
    public synchronized void getProducerMessage() throws InterruptedException{
        if(synchronizationType == SynchronizationType.BLOCKING){
            //while se obtiene el mensaje: wait
            Message message = producer.getMessage(this);
            wait();
            System.out.println(message.getContent());
            sleep(2000);
        }
        else if(synchronizationType == SynchronizationType.NONBLOCKING){
            Message message = producer.getMessage(this);
            System.out.println(message.getContent());
            sleep(5000);
        }
    }
    
    public synchronized void receiveMessage(){
        notify();
    }

    public SynchronizationType getSynchronizationType() {
        return synchronizationType;
    }
    

}
