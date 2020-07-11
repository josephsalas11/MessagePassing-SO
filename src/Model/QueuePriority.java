/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
/**
 *
 * @author Joseph Salas
 */
public class QueuePriority implements IMessageQueue{
   
    private PriorityQueue<Message> priorityQueue;
    private int size;

    public QueuePriority(int size) {
        this.size = size;
        priorityQueue = new PriorityQueue<Message>();
    }


    public void setQueue(PriorityQueue<Message> queue) {
        this.priorityQueue = queue;
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
        if(priorityQueue.size() == size){
            return false;
        }
        else{
            priorityQueue.add(message);
            return true;
        }
    }

    @Override
    public Message getMessage() {
        return priorityQueue.element();
    }

    @Override
    public void remove(Message message) {
        priorityQueue.remove(message);
    }

    @Override
    public int getQueueSize() {
        return priorityQueue.size();
    }

    @Override
    public boolean isQueueEmpty() {
       if(priorityQueue.isEmpty())
       {
           return true;
       }
       return false;
    }
    
    @Override
    public Message poll() {
        return priorityQueue.poll();
    }
    
    @Override
    public Message getMessage(int sourceId) {
        Message message = null;
        for(Message m:priorityQueue){
            if(m.getSourceID() == sourceId){
                message = m;
                priorityQueue.remove(m);
                break;
            }
        }
        return message;
    }
    
}
