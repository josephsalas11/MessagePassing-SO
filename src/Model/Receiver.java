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
    
    private IMessageQueue messageQueue;
    private int currentId = -1;
    private int idProcess;
    
    private boolean blocked = false;

    public Receiver(IProducer producer, SynchronizationType synchronizationType, QueueType queueType, int queueSize, int idProcess) {
        this.producer = producer;
        this.synchronizationType = synchronizationType;
        this.idProcess = idProcess;
        
        if(queueType == QueueType.FIFO)
            messageQueue = new FIFOQueue(queueSize);
        else if(queueType == QueueType.PRIORITY){
            messageQueue = new QueuePriority(queueSize);
        }
        
        //PRUEBA DE LLEGADA
        if(synchronizationType.equals(SynchronizationType.PRUEBA_LLEGADA))
           this.allowReceive = true; 
    }
    
    @Override
    public synchronized void run(){
        try {
            while(true){
                sleep(100);
                if(synchronizationType == SynchronizationType.BLOCKING){
                    //blocked = true;
                }
                if(synchronizationType != SynchronizationType.PRUEBA_LLEGADA)
                    wait();

                //if(allowReceive){                    
                    if(messageQueue.isQueueEmpty() && currentMailbox == null){
                        sleep(100);
                    }
                    else{
                        if(currentMailbox != null){
                            retreiveMessageMailbox();
                            System.out.println("entro");
                        }
                        else
                            retreiveMessage(); 
                    }     
                //}
            }
        } catch (InterruptedException e) {
        }
    }
    
    public void retreiveMessage(){
        Message messageTmp = searchMessage();
        
        //desbloqueo de producer
        if(messageTmp.getSource().getProducer().getSynchronizationType() == SynchronizationType.BLOCKING){ //si producer es blocking
            messageTmp.getSource().getProducer().freeProducer(); //se desbloquea
        }
        Log.getInstance().addLog(messageTmp.getDestinyID(), "Proceso: "+messageTmp.getDestinyID()+ " ha recibido el mensaje '"+messageTmp.getContent()+"' del proceso: "+messageTmp.getSourceID(), false);
        System.out.println(messageTmp.getContent());
        if(!synchronizationType.equals(SynchronizationType.PRUEBA_LLEGADA))
            allowReceive = false;
    }
    
    public void retreiveMessageMailbox(){
        Message messsageTmp = currentMailbox.retreiveMessage(this);
        //System.out.println(messsageTmp.getContent());
        if(messsageTmp != null){
            if(messsageTmp.getDestiny() == null){
                Log.getInstance().addLog(idProcess, "El proceso "+idProcess+"  ha recibido el mensaje '"+messsageTmp.getContent()+"' del proceso "+messsageTmp.getSourceID(), true);
            }
            else
                Log.getInstance().addLog(messsageTmp.getDestinyID(), "El proceso "+messsageTmp.getDestinyID()+" ha recibido el mensaje '"+messsageTmp.getContent()+"' del proceso "+messsageTmp.getSourceID(), true); //se esta guardando el id del producer
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
    
    @Override
    public synchronized void receiveMessage(){
        if(synchronizationType == SynchronizationType.BLOCKING){
            blocked = false;
        }
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
    public synchronized void setAllowReceive(boolean allowReceive) {
        if(!synchronizationType.equals(SynchronizationType.PRUEBA_LLEGADA)){
            this.allowReceive = allowReceive;
        }

        if(synchronizationType == SynchronizationType.BLOCKING){
            blocked = false;
        }
        notify();
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
        if(blocked){
            System.out.println("El proceso está bloqueado, no puede recibir mensajes");
            return false;
        }
        else if(messageQueue.addMessage(message) == false){ //aqui se agrega
            System.out.println("No se pueden agregar más procesos, la cola está llena");
            return false;
        }
        else{
            if(synchronizationType == SynchronizationType.BLOCKING)
                blocked = true;
            return true;
        }
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

    @Override
    public boolean isBlocked() {
        return blocked;
    }
    
    @Override
    public String stateToString() {
        String result = "";
        if(blocked){
            result = "Bloqueado";
        }
        else{
            result = "Desbloqueado";
        }
        return result;
    }
    
    @Override
    public int getQueueSize() {
        return messageQueue.getQueueSize();
    }

    @Override
    public String getQueueMessages() {
        return messageQueue.toString();
    }  

    @Override
    public String getQueueLog() {
        return messageQueue.getLogToString();
    }
}
