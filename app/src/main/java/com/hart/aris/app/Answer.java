package com.hart.aris.app;

import java.util.ArrayList;

/**
 * Created by joehart on 12/04/2014.
 */
public class Answer {
    public int id;
    AnswerType type;
    ArrayList<String> buttons;

    public Answer(int id, AnswerType type){
        this.id =id;
        this.type=type;
    }

}
