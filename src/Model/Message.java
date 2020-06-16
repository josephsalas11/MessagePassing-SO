/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;

/**
 *
 * @author Paulo
 */
public class Message implements Serializable, Comparable<Message>{
    private MessageType messageType;
    private int destinyID;
    private int sourceID;
    private int length;
    private String content;
    private int priorityFlag;

    public Message(MessageType messageType, int destinyID, int sourceID, int length, String content) {
        this.messageType = messageType;
        this.destinyID = destinyID;
        this.sourceID = sourceID;
        this.length = length;
        this.content = content;
    }
    
        public Message(MessageType messageType, int destinyID, int sourceID, int length, String content, int priorityFlag) {
        this.messageType = messageType;
        this.destinyID = destinyID;
        this.sourceID = sourceID;
        this.length = length;
        this.content = content;
        this.priorityFlag = priorityFlag;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public int getDestinyID() {
        return destinyID;
    }

    public void setDestinyID(int destinyID) {
        this.destinyID = destinyID;
    }

    public int getSourceID() {
        return sourceID;
    }

    public void setSourceID(int sourceID) {
        this.sourceID = sourceID;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPriorityFlag() {
        return priorityFlag;
    }

    public void setPriorityFlag(int priorityFlag) {
        this.priorityFlag = priorityFlag;
    }

    @Override
    public int compareTo(Message message) {
        if(this.getPriorityFlag() > message.getPriorityFlag()) {
            return 1;
        } 
        else if (this.getPriorityFlag() < message.getPriorityFlag()) 
        {
            return -1;
        } else {
            return 0;
        }
    }
    
}
