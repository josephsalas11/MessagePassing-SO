/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Queue;


/**
 *
 * @author Paulo
 */
public interface IMessageQueue {
    void addMessage(Message message);
    int getSize();
    Queue<Message> getQueue();
    Message getMessage();
    void remove(Message message);
}
