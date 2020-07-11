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
    private boolean waitReceive = true; //creo que se va por nuevas condiciones
    
    private IMessageQueue messageQueue;
    private int currentId = -1;

    public Receiver(IProducer producer, SynchronizationType synchronizationType, QueueType queueType, int queueSize) {
        this.producer = producer;
        this.synchronizationType = synchronizationType;
        
        if(queueType == QueueType.FIFO)
            messageQueue = new FIFOQueue(queueSize);
        else if(queueType == QueueType.PRIORITY){
            messageQueue = new QueuePriority(queueSize);
        }
    }
    
    @Override
    public void run(){
        try {
            while(true){
                if(producer != null && allowReceive){
                    //System.out.println("asdasdasda");
                    if(messageQueue.isQueueEmpty()){
                        sleep(1000);
                    }
                    else{
                        retreiveMessage();
                    }
                }
                else if(synchronizationType == SynchronizationType.BLOCKING){
                    wait();
                }
            }
        } catch (InterruptedException e) {
        }
    }
    
    public void retreiveMessage(){
        Message messageTmp = searchMessage();
        System.out.println(messageTmp.getContent()); //eliminar
        
        if(synchronizationType == SynchronizationType.BLOCKING && producer.getClass() != Mailbox.class){ //revisar if de mailbox
            
        }
        //se hace Log
        System.out.println("El mensaje fue recibido por el proceso "+messageTmp.getDestinyID());
        
        //desbloqueo de producer
        if(producer.getSynchronizationType() == SynchronizationType.BLOCKING){
            producer.notify();
        }
    }
    
    private Message searchMessage(){
        Message messageTmp = null;
        if(currentId == -1){//para ver si es implicito o explicito
            messageTmp = messageQueue.poll();
        }
        else{
            messageTmp = messageQueue.getMessage(currentId);
        }
        currentId = -1; //para volver a estado anterior
        //notify();
        return messageTmp;
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
        if(synchronizationType == SynchronizationType.BLOCKING){
            notify();
        }
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
        if(synchronizationType == SynchronizationType.BLOCKING){
            notify();
        }
    }

    public boolean isWaitReceive() {
        return waitReceive;
    }

    @Override
    public void setWaitReceive(boolean waitReceive) {
        this.waitReceive = waitReceive;
    }

    @Override
    public void setProducer(IProducer producer) {
        this.producer = producer;
    }

    @Override
    public IProducer getProducer() {
        return producer;
    }   

    @Override
    public boolean addMessage(Message message) {
        if(messageQueue.addMessage(message) == false){ //aqui se agrega
            System.out.println("No se pueden agregar más procesos, la cola está llena");
            return false;
        }
        return true;
    }

    @Override
    public void setCurrentId(int id) {
        currentId = id;
    }
    
}
