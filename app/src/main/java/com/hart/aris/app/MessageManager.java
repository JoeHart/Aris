package com.hart.aris.app;

import java.util.AbstractQueue;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by joehart on 12/04/2014.
 */
public class MessageManager {

    LinkedList<String[]> messageQueue;

    public MessageManager(){
        messageQueue = new LinkedList<String[]>();
    }

    public String[] getMessage(){
        if(messageQueue.size()>0){
            return messageQueue.poll();
        } else {
            return null;
        }
    }

    public boolean addMessage(String[] s){
        return messageQueue.add(s);
    }

    public boolean addMessage(String s1, String s2, String s3){
        String[] s = {s1,s2,s3};
        return addMessage(s);

    }

    public boolean isEmpty(){
        return messageQueue.isEmpty();
    }
}
