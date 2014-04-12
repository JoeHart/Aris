package com.hart.aris.app;

import java.util.AbstractQueue;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by joehart on 12/04/2014.
 */
public class MessageManager {

    LinkedList<String> messageQueue;

    public MessageManager(){
        messageQueue = new LinkedList<String>();
    }

    public String getMessage(){
        if(messageQueue.size()>0){
            return messageQueue.getFirst();
        } else {
            return null;
        }
    }

    public boolean addMessage(String s){
        return messageQueue.add(s);
    }

    public boolean isEmpty(){
        return messageQueue.isEmpty();
    }
}
