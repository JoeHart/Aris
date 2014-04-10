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
    Animation arisGlow;
    Animation textIn;
    Animation textIn2;
    Animation textIn3;
    ArrayList<TextView> arisText;


    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Load dumb GUI
        setContentView(R.layout.activity_main);

        //load Animations
        arisGlow = AnimationUtils.loadAnimation(this,R.anim.aris_glow);
        textIn = AnimationUtils.loadAnimation(this,R.anim.text_in);
        textIn2 = AnimationUtils.loadAnimation(this,R.anim.text_in);
        textIn3 = AnimationUtils.loadAnimation(this,R.anim.text_in);

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

            @Override
            public void run() {
                    if(counter<arisText.size()) {
                        //TextView currentText = arisText.get(counter);
                        //currentText.clearAnimation();


                       // currentText.setVisibility(TextView.VISIBLE);
                        //currentText.startAnimation(textIn);
                        if(counter==0){
                            TextView text1 = (TextView) findViewById(R.id.arisTextView1);
                            text1.startAnimation(textIn);
                        }
                        if(counter==1){
                            TextView text2 = (TextView) findViewById(R.id.arisTextView2);
                            text2.startAnimation(textIn2);
                        }
                        if(counter==2){
                            TextView text3 = (TextView) findViewById(R.id.arisTextView3);
                            text3.startAnimation(textIn3);
                        }

                        counter++;

                    }



                handler.postDelayed(this,1000); // set time here to refresh textView

            }

        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.



        return true;
    }
}
