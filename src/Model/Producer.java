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
    private SynchronizationType synchronizationType;
    private IReceiver receiver;
    private IMessageQueue messageQueue;
    
    private boolean blocked = false;

    public Producer(SynchronizationType synchronizationType, QueueType queueType, int queueSize) {
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
                if(messageQueue.isQueueEmpty()){
                        sleep(1000);
                }
                else{
                    putMessage();
                    sleep(1000);
                }

            }
            } catch (InterruptedException  e) {
        }
    }

    @Override
    public void putMessage() throws InterruptedException {
        
        /*Si es nonblocking, se envia mensaje a cola de mensaje del receiver
        Si es blocking, y el receiver esta bloqueado, espera para meterlo en la cola de mensajes*/
        
        
        if(synchronizationType == SynchronizationType.BLOCKING){ //&& no se envia a mailbox
            //System.out.println("entro b");
            putMessageBlocking();
            //sleep(1000);
            //getProducer
        }                
        else if(synchronizationType == SynchronizationType.NONBLOCKING){
            //System.out.println("entro nb");
            putMessageNonblocking();
            //sleep(1000);    
        }
    }
    
    
    //lo sustituye create message en process

    private synchronized void putMessageBlocking() throws InterruptedException{
        Message messageTmp = messageQueue.poll();
        if(messageTmp != null){
            boolean sentFlag = false;
            while(sentFlag == false){ //constantemente esta tratando de meter el mensaje en la cola del receiver
                if(messageTmp.getDestiny().getReceiver().addMessage(messageTmp) == false){
                    sleep(1000);
                }
                else{
                    sentFlag = true;
                }
            }
            blocked = true;
            wait(); //para esperar que el receiver ecplicitamente reciba el mensaje
        }
    }
    

    private synchronized void putMessageNonblocking() throws InterruptedException{
        Message messageTmp = messageQueue.poll();
        if(messageTmp != null){
            boolean sentFlag = false;
            while(sentFlag == false){ //constantemente esta tratando de meter el mensaje en la cola del receiver
                //Aqui se debe sacar el receiver de la lista de procesos para poder agregar el mensaje posteriormente
                if(messageTmp.getDestiny().getReceiver().addMessage(messageTmp) == false){
                    sleep(1000);
                }
                else{
                    sentFlag = true;
                }
            }
        }
    }
    
    @Override
    public synchronized void freeProducer(){
        blocked = false;
        notify();
    }

    @Override
    public synchronized void sendMessage() {
        if(synchronizationType == SynchronizationType.BLOCKING){
            notify();
        }
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
    public SynchronizationType getSynchronizationType() {
        return synchronizationType;
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
