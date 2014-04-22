package com.hart.aris.app;

import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation;
import android.widget.TextView;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
//import android.app.Fragment;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {
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
        textIn = new ArrayList<Animation>();
        textIn.add(AnimationUtils.loadAnimation(this,R.anim.text_in));
        textIn.add(AnimationUtils.loadAnimation(this,R.anim.text_in));
        textIn.add(AnimationUtils.loadAnimation(this,R.anim.text_in));

        //Initialize the 3 lines of ArisText
        initializeArisText();

        //check for a message
        msgManager = new MessageManager();
        msgManager.addMessage("This is a test message.","I really hope it works","If not I'm fucked");



        //counter for Aris Text
        counter=0;


        ///fragment shizzle
// Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.answerContainer) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            HoursAnswerFragment firstFragment = new HoursAnswerFragment();


            //Aris Fragment
            ArisTriangleFragment arisTriangleFragment = new ArisTriangleFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
           // firstFragment.setArguments(getIntent().getExtras());
            //getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, firstFragment);
            getSupportFragmentManager().beginTransaction().add(R.id.answerContainer,firstFragment).commit();

            getSupportFragmentManager().beginTransaction().add(R.id.arisTriangleContainer, arisTriangleFragment).commit();
        }



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
