/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Mailbox;
import Model.Message;
import Model.MessageType;
import Model.Producer;
import Model.QueueType;
import Model.Receiver;
import Model.SynchronizationType;
import Model.Process;
import static java.lang.Thread.sleep;



/**
 *
 * @author Paulo
 */
public class test{
    public static void main(String [] args) throws InterruptedException
	{
            
            
            Message m1 = new Message(MessageType.FIFO, 2, 1, 10, "Soy un pura mierda 3",3);
            Message m2 = new Message(MessageType.FIFO, 2, 1, 10, "Soy un pura mierda 1",2);
            Message m3 = new Message(MessageType.FIFO, 2, 1, 10, "Soy un pura mierda 2",1);
            
            /*
            Process p1 = new Process(1, SynchronizationType.NONBLOCKING, QueueType.PRIORITY, 5, SynchronizationType.NONBLOCKING);
            Process p2 = new Process(2, SynchronizationType.NONBLOCKING, QueueType.PRIORITY, 5, SynchronizationType.NONBLOCKING, p1);
            p1.setWaitReceive(false);
            p2.setWaitReceive(false);
            */
            
            Process p1 = new Process(1, SynchronizationType.NONBLOCKING, QueueType.PRIORITY, 5, SynchronizationType.NONBLOCKING);
            Mailbox mailbox = new Mailbox(5, QueueType.PRIORITY);
            Process p2 = new Process(2, SynchronizationType.NONBLOCKING, QueueType.PRIORITY, 5, SynchronizationType.NONBLOCKING, mailbox);
            Process p3 = new Process(3, SynchronizationType.NONBLOCKING, QueueType.PRIORITY, 5, SynchronizationType.NONBLOCKING, mailbox);

            p1.setWaitReceive(false);
            p2.setWaitReceive(false);
            p3.setWaitReceive(false);

            p1.sendMailbox(mailbox, m1);
            p1.sendMailbox(mailbox, m2);
            p1.sendMailbox(mailbox, m3);
            
            p2.receive(p1);
            p3.receive(p1);
            mailbox.getMessageQueue().poll();
            sleep(3000);
            p2.receive(p1);
            p3.receive(p1);
            mailbox.getMessageQueue().poll();
            sleep(3000);
            p2.receive(p1);
            p3.receive(p1);
            mailbox.getMessageQueue().poll();


            
            

        }
        
}

            //Producer p = new Producer(5, SynchronizationType.NONBLOCKING, QueueType.FIFO);
            //p.getMessageQueue().addMessage(m1);
            //p.getMessageQueue().addMessage(m2);
            //p.getMessageQueue().addMessage(m3);

            //p.start();
            //Receiver r = new Receiver(p, SynchronizationType.NONBLOCKING);
            //r.start();