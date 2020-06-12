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
public class Consumer extends Thread{
    private Producer producer;
    private SynchronizationType synchronizationType;

    public Consumer(Producer producer, SynchronizationType synchronizationType) {
        this.producer = producer;
        this.synchronizationType = synchronizationType;
    }
    
    @Override
    public void run(){
        try {
            while(true){
            if(producer.getMessageQueue().getQueue().size() == 0){
                Message m4 = new Message(MessageType.FIFO, 2, 1, 10, "Soy un pura mierda 4");
                producer.getMessageQueue().addMessage(m4);
            }
            Message message = producer.getMessage();
            System.out.println(message.getContent());
            sleep(2000);
        }
        } catch (InterruptedException e) {
        }
    }

}
