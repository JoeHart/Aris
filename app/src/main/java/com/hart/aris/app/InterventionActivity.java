package com.hart.aris.app;

import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.util.Log;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.content.SharedPreferences;
import android.speech.tts.TextToSpeech;
import java.util.Locale;
import android.os.AsyncTask;

public class InterventionActivity extends FragmentActivity implements ButtonAnswerFragment.OnFragmentInteractionListener, TextToSpeech.OnInitListener {
    private TextView arisText;
    protected TextToSpeech tts;
    protected boolean readyToTalk;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_intervention);
        tts = new TextToSpeech(this, this);
        readyToTalk=false;
        //get user name etc
        initializeArisText();

        ArisTriangleFragment arisTriangleFragment = new ArisTriangleFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.arisTriangleContainer, arisTriangleFragment).commit();
    }

    public void setMoodHappy(){

    }

    public void setMoodNeutral(){

    }

    public void setMoodSad(){

    }


    public void onPause(){
        super.onPause();
        finish();
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
        speak(s);
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
        tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);

    }

    public void speak(){
        tts.speak(getArisText(), TextToSpeech.QUEUE_FLUSH, null);
    }
}
