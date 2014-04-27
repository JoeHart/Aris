package com.hart.aris.app;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.util.Log;
import android.speech.tts.TextToSpeech;
import java.util.Locale;
import java.util.HashMap;
import android.speech.tts.UtteranceProgressListener;
import android.os.Handler;
import android.os.Message;
import java.util.Date;

public class InterventionActivity extends FragmentActivity implements ButtonAnswerFragment.OnFragmentInteractionListener, TextToSpeech.OnInitListener{
    private TextView arisText;
    protected TextToSpeech tts;
    HashMap<String, String> map;
    protected boolean readyToTalk;
    ArisTriangleFragment arisFace;
    public User user;
    public LanguageHelper lang;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_intervention);
        user = new User(this);
        map = new HashMap<String, String>();
        tts = new TextToSpeech(this, this);
        lang = new LanguageHelper(user);
        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String s) {
            Log.e("Started", s);
            }

            @Override
            public void onDone(String s) {
                Log.e("ended", s);
               showHandler.sendEmptyMessage(0);
            }

            @Override
            public void onError(String s) {

            }
        });
        readyToTalk=false;
        //get user name etc
        initializeArisText();

        arisFace = new ArisTriangleFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.arisTriangleContainer, arisFace).commit();
    }
    final Handler   showHandler   = new Handler() {
        @Override
        public void handleMessage(Message msg)
        {

            showAnswer();
        }
    };

    public void setArisMoodHappy(){
        arisFace.smile();
        tts.setPitch(1.2f);
    }

    public void setArisMoodNeutral(){
        arisFace.neutral();
        tts.setPitch(1.0f);
    }

    public void setArisMoodWorried(){
        arisFace.worry();
        tts.setPitch(0.9f);
    }

    public void onPause(){
        super.onPause();
        finish();
    }

    public boolean dateCheck(View v, Date d){
        if(lang.getDaysUntilInt(user.getDeadline())>lang.getDaysUntilInt(d)) {
            if (lang.getDaysUntilInt(d) < 14) {
                addStudyPromise(v,d);
                return true;
            } else {
                clearAnswer();
                setArisText("That's not very soon! You've only got " + lang.getTimeUntilString(user.getDeadline()));
                nextStudyCheck();
                return false;
            }
        }else{
            setArisText("Don't be silly " + user.getName() + ". That's after your " + user.getStringDeadline() + ". When will you study next?");
            nextStudyCheck();
            return false;
        }
    }

    public void nextStudyCheck(){
        clearAnswer();
        DateAnswerFragment date = DateAnswerFragment.newInstance("dateCheck");
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer,date).commit();
    }

    public void addStudyPromise(View v, Date d){


        clearAnswer();
        setArisMoodHappy();
        setArisText("I'll ");
    }

    public void initializeArisText(){
        arisText=(TextView) findViewById(R.id.arisTextView);
    }

    public void hideArisText(){
            arisText.setVisibility(TextView.INVISIBLE);
            arisText.setAlpha(0.0f);
    }

    public void setArisText(String s){
        arisText.setText(s);
        hideAnswer();
        speak(s);
    }
    public void hideAnswer(){
        try {
        View container = findViewById(R.id.answerContainer);
        container.setVisibility(View.INVISIBLE);
        container.setAlpha(0.0f);
    } catch (Exception e) {

    }
    }

    public void showAnswer(){
        View container = findViewById(R.id.answerContainer);
        container.setVisibility(View.VISIBLE);
        container.setAlpha(1.0f);


    }

    public void clearAnswer(){
        getSupportFragmentManager().beginTransaction().
                remove(getSupportFragmentManager().findFragmentById(R.id.answerContainer)).commit();
    }

    public void onFragmentInteraction(Uri uri){
        clearAnswer();

    }

    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.UK);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                showArisText();
                speak();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");

        }

    }

    public void showArisText(){
        arisText.setVisibility(TextView.VISIBLE);
        arisText.setAlpha(1);
    }


    public String getArisText(){
        TextView text = (TextView) findViewById(R.id.arisTextView);
        return text.getText().toString();
    }

    public void speak(String s){
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");
        tts.speak(s, TextToSpeech.QUEUE_FLUSH, map);

    }

    public void speak(){

    speak(getArisText());
    }
}
