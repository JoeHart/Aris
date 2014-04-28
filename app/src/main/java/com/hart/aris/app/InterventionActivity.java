package com.hart.aris.app;

import android.app.Notification;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.util.Log;
import android.speech.tts.TextToSpeech;

import java.util.Calendar;
import java.util.Locale;
import java.util.HashMap;
import android.speech.tts.UtteranceProgressListener;
import android.os.Handler;
import android.os.Message;
import java.util.Date;
import android.content.Intent;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.NotificationManager;

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
                //TODO: SOrt this shit out
                addStudyPromise(v,d,"study",PastPaperCheckActivity.class);
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

    public void nextStudyCheck(String studyType, Class activity){
        clearAnswer();
        DateAnswerFragment date = DateAnswerFragment.newInstance("dateCheck");
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer,date).commit();
    }

    public void addStudyPromise(View v, Date d,String studyType,Class activity){


        /*Intent myIntent = new Intent(this , NotificationService.class);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        pendingIntent = PendingIntent.getService(ThisApp.this, 0, myIntent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        long time = d.getTime();

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, 24*60*60*1000 , pendingIntent);  //set repeating every 24 hours*/
        addPromise(studyType,activity,d);
        setArisMoodHappy();
        setArisText("I'll catch up with you " + lang.getTimePhrase(d));
        clearAnswer();


        ButtonAnswerFragment thanks = ButtonAnswerFragment.newInstance("Bye Aris!","closeAris","","","","");
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer,thanks).commit();
    }

    public void addPromise(String StudyType,Class activity,Date d){
        // get a Calendar object with current time
        Calendar cal = Calendar.getInstance();
        // add 5 minutes to the calendar object
        cal.add(Calendar.MINUTE, 1);
        Calendar deadline = Calendar.getInstance();
        deadline.setTime(d);
        int day= deadline.get(Calendar.DAY_OF_MONTH);
        int month = deadline.get(Calendar.MONTH);
        int year = deadline.get(Calendar.YEAR);
        Calendar deadlineTime = Calendar.getInstance();
        deadlineTime.set(year,month,day,15,0,0);
        Intent intent = new Intent(this, CheckReceiver.class);
        intent.putExtra("activity_start", activity.toString());
        intent.putExtra("study_type", StudyType);
        intent.putExtra("name",user.getName());
        // In reality, you would want to have a static variable for the request code instead of 192837
        PendingIntent sender = PendingIntent.getBroadcast(this, 192837, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get the AlarmManager service
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, deadlineTime.getTimeInMillis(), sender);

    }

    public void howManyHours(View view){
        setArisText("How many hours did you " + user.getProjectVerb() + " today?");
        HoursAnswerFragment hours = HoursAnswerFragment.newInstance("hoursReaction");

        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer,hours).commit();
    }

    public void goodbye(View v){
        clearAnswer();
        float userMood = user.getUserMood();
        if(userMood>0.5){
            setArisMoodHappy();
            setArisText("You're doing really well " + user.getName() + ". Keep up the good work! Goodbye.");
        } else{
            if(userMood>0){
                setArisMoodNeutral();
                setArisText("With a bit more hard work, you'll be fine " + user.getName() +". Goodbye");
            } else{
                if(lang.getDaysUntilInt(user.getDeadline())<7){
                    setArisMoodHappy();
                    setArisText("You've got less than a week left. Give it one last push and you'll be fine.");
                }
                setArisText("I know you can do better. If you focus I'm sure you'll succeed");
            }
        }


        ButtonAnswerFragment thanks = ButtonAnswerFragment.newInstance("Bye Aris!","closeAris","","","","");
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer,thanks).commit();
    }

    public void closeAris(View v){
        finish();
    }

    public void hoursReaction(View v, int hours){
        clearAnswer();
        if(hours>5){
            setArisMoodHappy();
            setArisText(hours + " hours! Good Job!");
            ButtonAnswerFragment thanks = ButtonAnswerFragment.newInstance("Thanks Aris!","goodbye","","","","");
                    getSupportFragmentManager().beginTransaction().add(R.id.answerContainer,thanks).commit();
            addPromise("dissertation",PastPaperCheckActivity.class,new Date());
            user.addMood(1.0f);

        } else{
            if(hours>2){
                setArisMoodHappy();
                int tomorrow = hours+1;
                setArisText(hours + " hours, thats ok. Try and aim for " + tomorrow + " hours tomorrow.");
                user.addMood(0.4f);
                ButtonAnswerFragment thanks = ButtonAnswerFragment.newInstance("I'll try!","goodbye","","","","");
                addPromise("dissertation",PastPaperCheckActivity.class,new Date());
                getSupportFragmentManager().beginTransaction().add(R.id.answerContainer,thanks).commit();
            } else{
                setArisMoodWorried();
                user.addMood(-1.0f);
                setArisText("That's not a lot, will you try and do more tomorrow?");

                ButtonAnswerFragment thanks = ButtonAnswerFragment.newInstance("I'll try!","goodbye","","","","");
                addPromise("dissertation",PastPaperCheckActivity.class,new Date());
                getSupportFragmentManager().beginTransaction().add(R.id.answerContainer,thanks).commit();
            }
        }


        storeRevisionHours(hours);
    }

    public void storeRevisionHours(int hours){
        Date today = new Date();
        long todayLong = today.getTime();
    }


    public void createNotification(View view,Class activity) {
        // Prepare intent which is triggered if the
        // notification is selected
        Intent intent = new Intent(this, activity);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,  PendingIntent.FLAG_UPDATE_CURRENT);

        // Build notification
        // Actions are just fake
        Notification noti = new Notification.Builder(this)
                .setContentTitle("Hi " + user.getName() + ". Can we chat?")
                .setContentText("It'll only take a few moments!").setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(pIntent).build();


        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags = Notification.DEFAULT_LIGHTS | Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, noti);
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
