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
public class Message implements Serializable{
    private MessageType messageType;
    private int destinyID;
    private int sourceID;
    private int length;
    private String content;

    public Message(MessageType messageType, int destinyID, int sourceID, int length, String content) {
        this.messageType = messageType;
        this.destinyID = destinyID;
        this.sourceID = sourceID;
        this.length = length;
        this.content = content;
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
    
    
}
