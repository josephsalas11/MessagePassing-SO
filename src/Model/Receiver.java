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
    private Mailbox currentMailbox = null;
    private boolean waitReceive = true; //creo que se va por nuevas condiciones
    
    private IMessageQueue messageQueue;
    private int currentId = -1;
    private int idProcess;

    public Receiver(IProducer producer, SynchronizationType synchronizationType, QueueType queueType, int queueSize, int idProcess) {
        this.producer = producer;
        this.synchronizationType = synchronizationType;
        this.idProcess = idProcess;
        
        if(queueType == QueueType.FIFO)
            messageQueue = new FIFOQueue(queueSize);
        else if(queueType == QueueType.PRIORITY){
            messageQueue = new QueuePriority(queueSize);
        }
    }
    
    @Override
    public synchronized void run(){
        try {
            while(true){
                sleep(100);
                if(synchronizationType == SynchronizationType.BLOCKING){
                    wait();
                }
                if(allowReceive){
                    //if(messageQueue.isQueueEmpty()){
                    //    sleep(1000);
                    //}
                    //else{
                    if(currentMailbox != null)
                        retreiveMessageMailbox();
                    else
                        retreiveMessage(); 
                    //}     
                }
            }
        } catch (InterruptedException e) {
        }
    }
    
    public void retreiveMessage(){
        Message messageTmp = searchMessage();
        
        //se hace Log
        System.out.println("El mensaje fue recibido por el proceso "+messageTmp.getDestinyID());
        
        //desbloqueo de producer
        if(messageTmp.getSource().getProducer().getSynchronizationType() == SynchronizationType.BLOCKING){ //si producer es blocking
            messageTmp.getSource().getProducer().freeProducer(); //se desbloquea
        }
        Log.getInstance().addLog(messageTmp.getDestinyID(), "Proceso: "+messageTmp.getDestinyID()+ " ha recibido el mensaje '"+messageTmp.getContent()+"' del proceso: "+messageTmp.getSourceID(), false);
        System.out.println(messageTmp.getContent());
        allowReceive = false;
    }
    
    public void retreiveMessageMailbox(){
        Message messsageTmp = currentMailbox.retreiveMessage(this);
        if(messsageTmp != null){
            //System.out.println(messsageTmp.getContent()+" :)");
            if(messsageTmp.getDestiny() == null){
                Log.getInstance().addLog(idProcess, "El proceso "+idProcess+"  ha recibido el mensaje '"+messsageTmp.getContent()+"' del proceso "+messsageTmp.getSourceID(), waitReceive);
            }
            else
                Log.getInstance().addLog(messsageTmp.getDestinyID(), "El proceso "+messsageTmp.getDestinyID()+" ha recibido el mensaje '"+messsageTmp.getContent()+"' del proceso "+messsageTmp.getSourceID(), waitReceive); //se esta guardando el id del producer
        }
    }
    
    private Message searchMessage(){
        Message messageTmp = null;
        if(currentId == -1){//para ver si es implicito o explicito
            messageTmp = messageQueue.poll();
        }
        else{
            messageTmp = messageQueue.getMessageProducer(currentId);
        }
        currentId = -1; //para volver a estado anterior
        //notify();
        return messageTmp;
    }
    
    /*
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
    */
    
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
    public synchronized void setAllowReceive(boolean allowReceive) {
        this.allowReceive = allowReceive;
        //System.out.println(producer.getSynchronizationType());
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

    public Mailbox getCurrentMailbox() {
        return currentMailbox;
    }

    @Override
    public void setCurrentMailbox(Mailbox currentMailbox) {
        this.currentMailbox = currentMailbox;
    }

    @Override
    public int getIdProcess() {
        return idProcess;
    }
    
    
    
}
