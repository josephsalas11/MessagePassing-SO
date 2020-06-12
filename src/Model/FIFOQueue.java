/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import sun.misc.Queue;

/**
 *
 * @author Paulo
 */
public class FIFOQueue implements IMessageQueue{
    private Queue<Message> queue;
    private int size;

    public FIFOQueue(int size) {
        this.size = size;
        queue = new Queue<>();
    }

    public Queue<Message> getQueue() {
        return queue;
    }

    public void setQueue(Queue<Message> queue) {
        this.queue = queue;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    
    @Override
    public void addMessage(Message message){
        queue.enqueue(message);
    }
    
    
}
