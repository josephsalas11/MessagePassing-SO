/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    
    private ArrayList<String> log = new ArrayList<>();

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
            log.add("No se pueden agregar más mensajes, la cola está llena");
            return false;
        }
        else{
            priorityQueue.add(message);
            log.add(makeLog(message, "Agrega"));
            return true;
        }
    }

    @Override
    public Message getMessage() {
        Message message = priorityQueue.element();
        log.add(makeLog(message, "Extrae"));
        return message;
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
    public Message getMessageProducer(int sourceId) {
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
    
    @Override
    public Message getMessageReceiver(int destinyId) {
        Message message = null;
        for(Message m:priorityQueue){
            if(m.getDestinyID() == destinyId){
                message = m;
                priorityQueue.remove(m);
                break;
            }
        }
        return message;
    }
    
    private String makeLog(Message message, String action){
        String result = "";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        
        if(message != null)
            result = dtf.format(now)+" "+action+" mensaje: "+message.getContent();
        else
            result = action;
        
        return result;
    }

    @Override
    public String getLogToString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  

        String result = dtf.format(now)+"\nTamaño: "+priorityQueue.size()+"\nTipo: Prioridad\nLog\n";
        for (int i = 0; i < log.size(); i++) {
            result = result.concat(log.get(i)+"\n");
        }
        return result;
    }
}
