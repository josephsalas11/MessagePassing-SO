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
public interface IProducer {
    void putMessage() throws InterruptedException;
    Message getMessage(IReceiver receiver) throws InterruptedException;
    IMessageQueue getMessageQueue();
    void sendMessage();
}
