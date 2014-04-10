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
    Animation glowAnim;
    Animation fadeAnim;
    String[] words;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        glowAnim = AnimationUtils.loadAnimation(this,R.anim.aris_glow);
        fadeAnim = AnimationUtils.loadAnimation(this,R.anim.abc_fade_in);
        TextView text = (TextView) findViewById(R.id.arisTextView);
        String textRaw = text.getText().toString();
        words = textRaw.split(" ");
        text.setText("");
        i=0;
    }
    public void onResume(){
        super.onResume();
        final Handler handler=new Handler();
        handler.post(new Runnable(){

            @Override
            public void run() {

                if(i<words.length){
                    ImageView glow = (ImageView) findViewById(R.id.glowImageView);
                    glow.startAnimation(glowAnim);
                    TextView text = (TextView) findViewById(R.id.arisTextView);

                    text.append(words[i]+" ");
                    i++;}
                Random ranGen = new Random();
                int ranInt = ranGen.nextInt(100);


                handler.postDelayed(this,200+ranInt); // set time here to refresh textView

            }

        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.
        Intent intent = getIntent();
        finish();
        startActivity(intent);


        return true;
    }
}
