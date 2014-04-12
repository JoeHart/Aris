package com.hart.aris.app;

import android.app.Activity;
import android.os.Bundle;
import android.opengl.GLSurfaceView;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.view.animation.Animation;
import android.widget.TextView;
import android.view.MotionEvent;
import android.os.Handler;
import android.content.Intent;
import java.util.Random;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private Animation arisGlow;
    private ArrayList<TextView> arisTextViews;
    private String[] currentText;
    private ArrayList<Animation> textIn;
    private int counter;
    public MessageManager msgManager;
    public ArisTextManager arisTextManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Load dumb GUI
        setContentView(R.layout.activity_main);

        //load Animations
        arisGlow = AnimationUtils.loadAnimation(this,R.anim.aris_glow);
        textIn = new ArrayList<Animation>();
        textIn.add(AnimationUtils.loadAnimation(this,R.anim.text_in));
        textIn.add(AnimationUtils.loadAnimation(this,R.anim.text_in));
        textIn.add(AnimationUtils.loadAnimation(this,R.anim.text_in));

        //Initialize the 3 lines of ArisText
        initializeArisText();

        //check for a message
        msgManager = new MessageManager();



        //Start the Aris glowing
        ImageView glow = (ImageView) findViewById(R.id.glowImageView);
        glow.startAnimation(arisGlow);

        //counter for Aris Text
        counter=0;


    }

    public void initializeArisText(){
        arisTextViews = new ArrayList<TextView>();
        arisTextManager = new ArisTextManager();
        //put them into the array list for future manipulation
        arisTextViews.add((TextView) findViewById(R.id.arisTextView1));
        arisTextViews.add((TextView) findViewById(R.id.arisTextView2));
        arisTextViews.add((TextView) findViewById(R.id.arisTextView3));
        hideArisText();
    }

    public void hideArisText(){
        for(TextView t: arisTextViews){
            t.setVisibility(TextView.INVISIBLE);
            t.setAlpha(0.0f);
        }
    }

    public void setArisText(String line1, String line2, String line3){
        arisTextViews.get(0).setText(line1);
        arisTextViews.get(1).setText(line2);
        arisTextViews.get(2).setText(line3);
    }

    public void setArisText(String[] s){
        setArisText(s[0],s[1],s[2]);
    }

    public void onResume(){
        super.onResume();
        counter=0;
        //check message manager for messages
        if(msgManager.isEmpty()){
            //display default message
            setArisText("Hello There!","Is there anything I can help you with?","");
        } else {
            setArisText(msgManager.getMessage());
        }


        final Handler handler=new Handler();
        handler.post(new Runnable(){
            public void run() {
                if(counter<arisTextViews.size()) {
                    arisTextViews.get(counter).setAlpha(1.0f);
                    arisTextViews.get(counter).startAnimation(textIn.get(counter));
                    counter++;
                }
                handler.postDelayed(this,1000); // set time here to refresh textView
            }
        });
    }

    public void onPause(){
        super.onPause();
        hideArisText();
    }
}
