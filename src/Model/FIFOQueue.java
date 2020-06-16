/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.LinkedList;
import java.util.Queue;


/**
 *
 * @author Paulo
 */
public class FIFOQueue implements IMessageQueue{
    private Queue<Message> queue;
    private int size;

    public FIFOQueue(int size) {
        this.size = size;
        queue = new LinkedList<Message>();
    }

    @Override
    public Queue<Message> getQueue() {
        return queue;
    }

    public void setQueue(Queue<Message> queue) {
        this.queue = queue;
    }

    @Override
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    
    @Override
    public boolean addMessage(Message message){
        if(queue.size() == size){ //no se pueden meter mas mensajes
            return false;
        }
        else{
            queue.add(message);
            return true;
        }
    }

    @Override
    public Message getMessage() {
        return queue.element();
    }

    @Override
    public void remove(Message message) {
        queue.remove(message);
    }
    
    
}
