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
    private ArrayList<TextView> arisText;
    private ArrayList<Animation> textIn;
    private int counter;

    @Override
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

        //Start the Aris glowing
        ImageView glow = (ImageView) findViewById(R.id.glowImageView);
        glow.startAnimation(arisGlow);

        //counter for Aris Text
        counter=0;


    }

    public void initializeArisText(){
        arisText = new ArrayList<TextView>();

        //put them into the array list for future manipulation
        arisText.add((TextView) findViewById(R.id.arisTextView1));
        arisText.add((TextView) findViewById(R.id.arisTextView2));
        arisText.add((TextView) findViewById(R.id.arisTextView3));
        for(TextView t: arisText){
            t.setVisibility(TextView.INVISIBLE);
        }
    }

    public void setArisText(String line1, String line2, String line3){
        arisText.get(0).setText(line1);
        arisText.get(1).setText(line2);
        arisText.get(2).setText(line3);
    }

    public void onResume(){
        super.onResume();
        final Handler handler=new Handler();
        handler.post(new Runnable(){
            public void run() {
                if(counter<arisText.size()) {
                    arisText.get(counter).startAnimation(textIn.get(counter));
                    counter++;
                }
                handler.postDelayed(this,1000); // set time here to refresh textView
            }
        });
    }
}
