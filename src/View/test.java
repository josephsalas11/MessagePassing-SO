/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Consumer;
import Model.Message;
import Model.MessageType;
import Model.Producer;
import Model.QueueType;
import Model.SynchronizationType;

/**
 *
 * @author Paulo
 */
public class test{
    public static void main(String [] args) throws InterruptedException
	{
            Message m1 = new Message(MessageType.FIFO, 2, 1, 10, "Soy un pura mierda 1");
            Message m2 = new Message(MessageType.FIFO, 2, 1, 10, "Soy un pura mierda 2");
            Message m3 = new Message(MessageType.FIFO, 2, 1, 10, "Soy un pura mierda 3");

            Producer p = new Producer(5, SynchronizationType.BLOCKING, QueueType.FIFO);
            p.getMessageQueue().addMessage(m1);
            p.getMessageQueue().addMessage(m2);
            p.getMessageQueue().addMessage(m3);

            p.start();
            Consumer c = new Consumer(p, SynchronizationType.NONBLOCKING);
            c.start();
        }
        
}

