package com.hart.aris.app;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class InterventionActivity extends FragmentActivity implements ButtonAnswerFragment.OnFragmentInteractionListener, TextToSpeech.OnInitListener {
    final Handler showHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            showAnswer();
        }
    };
    public User user;
    public LanguageHelper lang;
    public ArrayList<Date> interventionDates;
    protected TextToSpeech tts;
    protected boolean readyToTalk;
    HashMap<String, String> map;
    ArisTriangleFragment arisFace;
    private TextView arisText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_intervention);
        user = new User(this);
        map = new HashMap<String, String>();
        tts = new TextToSpeech(this, this);
        lang = new LanguageHelper(user);
        interventionDates = new ArrayList<Date>();
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
        readyToTalk = false;
        //get user name etc
        initializeArisText();

        arisFace = new ArisTriangleFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.arisTriangleContainer, arisFace).commit();
    }

    public void setArisMoodHappy() {
        arisFace.smile();
        tts.setPitch(1.2f);
    }

    public void setArisMoodNeutral() {
        arisFace.neutral();
        tts.setPitch(1.0f);
    }

    public void setArisMoodWorried() {
        arisFace.worry();
        tts.setPitch(0.85f);
    }

    public void onPause() {
        super.onPause();
        finish();
    }

    public boolean dateCheck(View v, Date d, String studyType, Class activity) {
        if (lang.getDaysUntilInt(user.getDeadline()) > lang.getDaysUntilInt(d)) {
            if (lang.getDaysUntilInt(d) < 14) {
                addStudyPromise(v, d, studyType, activity);
                return true;
            } else {
                clearAnswer();
                setArisText("That's not very soon! You've only got " + lang.getTimeUntilString(user.getDeadline()));
                nextStudyCheck(studyType, activity);
                return false;
            }
        } else {
            setArisText("Don't be silly " + user.getName() + ". That's after your " + user.getStringDeadline() + ". When will you study next?");
            nextStudyCheck(studyType, activity);
            return false;
        }
    }


    public void nextStudyCheck(String studyType, Class activity) {
        clearAnswer();
        DateAnswerFragment date = DateAnswerFragment.newInstance("dateCheck", studyType, activity);
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, date).commit();
    }

    public Date setTime(int hour, Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        return cal.getTime();
    }

    public void addStudyPromise(View v, Date d, String studyType, Class activity) {
        Date dNew = setTime(15, d);
        Log.e("AddStudyPromisDin", d.toString());
        Log.e("AddStudyPromiseDnew", dNew.toString());
        if (lang.isToday(d)) {
            Calendar cal = Calendar.getInstance();
            int currentHour = cal.get(Calendar.HOUR_OF_DAY);
            dNew = setTime(currentHour + 3, d);
        }

        Log.e("AddStudyPromiseDnewFinal", dNew.toString());
        addPromise(studyType, activity, dNew);
        setArisMoodHappy();
        setArisText("I'll catch up with you " + lang.getTimePhrase(d));
        clearAnswer();
        ButtonAnswerFragment thanks = ButtonAnswerFragment.newInstance("Bye Aris!", "closeAris", "", "", "", "");
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, thanks).commit();
    }

    public void storeDate(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        SharedPreferences pref = getSharedPreferences("appData", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(Integer.toString(cal.get(Calendar.DATE)), "yes");
        edit.commit();
    }

    public boolean getDate(int date) {
        SharedPreferences pref = getSharedPreferences("appData", MODE_PRIVATE);
        String dateString = pref.getString(Integer.toString(date), "");
        return !dateString.isEmpty();
    }


    public void addPromise(String StudyType, Class activity, Date d) {

        Log.e("date", d.toString());
        storeDate(d);
        Intent intent = new Intent(this, CheckReceiver.class);
        intent.putExtra("activity_type", activity.toString());
        intent.putExtra("study_type", StudyType);
        intent.putExtra("name", user.getName());
        // In reality, you would want to have a static variable for the request code instead of 192837
        PendingIntent sender = PendingIntent.getBroadcast(this, 192837, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Get the AlarmManager service
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, d.getTime(), sender);

    }

    public void howManyHours(View view) {
        clearAnswer();
        setArisText("How many hours did you " + user.getProjectVerb() + " today?");
        HoursAnswerFragment hours = HoursAnswerFragment.newInstance("hoursReaction");

        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, hours).commit();
    }

    public void goodbye(View v) {
        clearAnswer();
        float userMood = user.getUserMood();
        if (userMood > 0.5) {
            setArisMoodHappy();
            setArisText("You're doing really well " + user.getName() + ". Keep up the good work! Goodbye.");
        } else {
            if (userMood > 0) {
                setArisMoodNeutral();
                setArisText("With a bit more hard work, you'll be fine " + user.getName() + ". Goodbye");
            } else {
                if (lang.getDaysUntilInt(user.getDeadline()) < 7) {
                    setArisMoodHappy();
                    setArisText("You've got less than a week left. Give it one last push and you'll be fine.");
                }
                setArisText("I know you can do better. If you focus I'm sure you'll succeed");
            }
        }
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        int tomorrowDate = cal.get(Calendar.DATE);
        if (getDate(tomorrowDate)) {
            //do nothing because there is already an interaction
        } else {
            //add interaction tomorrow at random time between 10am and 3pm

            addPromise(lang.getActivityNoun(), ProjectCheck.class, cal.getTime());
            Log.e("Added promise", cal.getTime().toString());
        }


        ButtonAnswerFragment thanks = ButtonAnswerFragment.newInstance("Bye Aris!", "closeAris", "", "", "", "");
        getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, thanks).commit();
    }

    public void closeAris(View v) {
        finish();
    }

    public void hoursReaction(View v, int hours) {
        clearAnswer();
        int oldHours = user.getHours();
        user.storeHours(hours);
        user.addToTotalHours(hours);

        if (hours > oldHours) {
            setArisMoodHappy();
            setArisText(hours + " hours! That's " + (hours - oldHours) + " more that last time!");
            ButtonAnswerFragment thanks = ButtonAnswerFragment.newInstance("Thanks Aris!", "goodbye", "", "", "", "");
            getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, thanks).commit();
            user.addMood(1.0f);

        } else {
            if (hours > 5) {
                setArisMoodHappy();
                setArisText(hours + " hours! Good Job!");
                ButtonAnswerFragment thanks = ButtonAnswerFragment.newInstance("Thanks Aris!", "goodbye", "", "", "", "");
                getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, thanks).commit();
                user.addMood(1.0f);

            } else {
                if (hours > 2) {
                    setArisMoodHappy();
                    int tomorrow = hours + 1;
                    setArisText(hours + " hours, thats ok. Try and aim for " + tomorrow + " hours tomorrow.");
                    user.addMood(0.4f);
                    ButtonAnswerFragment thanks = ButtonAnswerFragment.newInstance("I'll try!", "goodbye", "", "", "", "");
                    getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, thanks).commit();
                } else {
                    setArisMoodWorried();
                    user.addMood(-1.0f);
                    setArisText("That's not a lot, will you try and do more tomorrow?");

                    ButtonAnswerFragment thanks = ButtonAnswerFragment.newInstance("I'll try!", "goodbye", "", "", "", "");
                    getSupportFragmentManager().beginTransaction().add(R.id.answerContainer, thanks).commit();
                }
            }
        }
    }



    public void initializeArisText() {
        arisText = (TextView) findViewById(R.id.arisTextView);
    }

    public void hideArisText() {
        arisText.setVisibility(TextView.INVISIBLE);
        arisText.setAlpha(0.0f);
    }

    public void hideAnswer() {
        try {
            View container = findViewById(R.id.answerContainer);
            container.setVisibility(View.INVISIBLE);
            container.setAlpha(0.0f);
        } catch (Exception e) {

        }
    }

    public void showAnswer() {
        View container = findViewById(R.id.answerContainer);
        container.setVisibility(View.VISIBLE);
        container.setAlpha(1.0f);


    }

    public void clearAnswer() {
        getSupportFragmentManager().beginTransaction().
                remove(getSupportFragmentManager().findFragmentById(R.id.answerContainer)).commit();
    }

    public void onFragmentInteraction(Uri uri) {
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

    public void showArisText() {
        arisText.setVisibility(TextView.VISIBLE);
        arisText.setAlpha(1);
    }

    public String getArisText() {
        TextView text = (TextView) findViewById(R.id.arisTextView);
        return text.getText().toString();
    }

    public void setArisText(String s) {
        arisText.setText(s);
        hideAnswer();
        speak(s);
    }

    public void speak(String s) {
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");
        tts.speak(s, TextToSpeech.QUEUE_FLUSH, map);

    }

    public void speak() {

        speak(getArisText());
    }
}
