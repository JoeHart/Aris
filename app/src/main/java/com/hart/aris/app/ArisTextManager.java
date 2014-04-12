package com.hart.aris.app;

import java.util.LinkedList;

/**
 * Created by joehart on 12/04/2014.
    Aris displays text in 3 lines this manages that queue
 */
public class ArisTextManager {
    String[] currentText;

    public ArisTextManager(){
        currentText = new String[3];
        this.clear();
    }

    public boolean add(String s){
        if(currentText[2]==""){
            currentText[2] = s;
            return true;
        } else {
            this.removeFirst();
            currentText[2] = s;
            return true;
        }
    }

    public void removeFirst(){
        currentText[0]=currentText[1];
        currentText[1]=currentText[2];
        currentText[2]="";
    }

    public void clear(){
        for(int i=0;i<3;i++){
            currentText[i]="";
        }
    }
}
