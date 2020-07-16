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
import java.util.Queue;


/**
 *
 * @author Paulo
 */
public class FIFOQueue implements IMessageQueue{
    private Queue<Message> queue;
    private int size;
    
    private ArrayList<String> log = new ArrayList<>();

    public FIFOQueue(int size) {
        this.size = size;
        queue = new LinkedList<Message>();
    }
    
    /*
    Display informativo:
    ID del proceso
    Tamaño de la cola: 
    */

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
            log.add("No se pueden agregar más mensajes, la cola está llena");
            return false;
        }
        else{
            queue.add(message);
            log.add(makeLog(message, "Agrega"));
            return true;
        }
    }

    @Override
    public Message getMessage() {
        Message message = queue.element();
        log.add(makeLog(message, "Extrae"));
        return message;
    }

    @Override
    public void remove(Message message) {
        queue.remove(message);
    }

    @Override
    public int getQueueSize() {
        return queue.size();
    }

    @Override
    public boolean isQueueEmpty() {
        if(queue.isEmpty())
        {
            return true;
        }
        return false;
    }

    @Override
    public Message poll() {
        return queue.poll();
    }

    @Override
    public Message getMessageProducer(int sourceId) {
        Message message = null;
        //System.out.println(queue.size());
        for(Message m:queue){
            //System.out.println(m.getContent());
            if(m.getSourceID() == sourceId){
                message = m;
                queue.remove(m);
                break;
            }
        }
        return message;
    }
    
    @Override
    public Message getMessageReceiver(int destinyId) {
        Message message = null;
        //System.out.println(queue.size());
        for(Message m:queue){
            //System.out.println(m.getContent());
            if(m.getDestinyID() == destinyId){
                message = m;
                queue.remove(m);
                break;
            }
        }
        return message;
    }
    
    @Override
    public String toString(){
        String result = "";
        for(Message m:queue){
            result = result.concat(m.toString())+"\n";
        }
        return result;
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
        
        String result = dtf.format(now)+"\nTamaño: "+queue.size()+"\nTipo: FIFO\nLog:\n";
        for (int i = 0; i < log.size(); i++) {
            result = result.concat(log.get(i)+"\n");
        }
        return result;
    }
}
