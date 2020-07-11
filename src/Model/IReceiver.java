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
public interface IReceiver{
    void getProducerMessage() throws InterruptedException;
    void receiveMessage();
    SynchronizationType getSynchronizationType();
    void start();
   
    void setAllowReceive(boolean allowReceive);
    void setWaitReceive(boolean waitReceive);
    void setProducer(IProducer producer);
    IProducer getProducer();
    boolean addMessage(Message message);
    void setCurrentId(int id);
}
