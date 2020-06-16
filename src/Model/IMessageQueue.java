/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.PriorityQueue;
import java.util.Queue;


/**
 *
 * @author Paulo
 */
public interface IMessageQueue {
    boolean addMessage(Message message);
    int getSize();
    int getQueueSize();
    boolean isQueueEmpty();
    Message getMessage();
    void remove(Message message);
    void poll();
    
}
