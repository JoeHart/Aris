package com.hart.aris.app;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Joe on 27/04/2014.
 */
public class LanguageHelper {
    User user;

    public LanguageHelper(User u) {
        this.user = u;
    }

    public String getActivityNoun() {
        String projectType = user.getProjectType();
        if (projectType.equals("exam")) {
            return "revision";
        } else {
            if (projectType.equals("dissertation")) {
                return "dissertation";
            } else {
                return "coursework";
            }
        }
    }

    public int getDaysUntilInt(Date deadline) {
        Date date = new Date();
        long days = 0;
        try {
            Date date1 = deadline;
            Date date2 = date;
            long diff = date1.getTime() - date2.getTime();
            days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
        }
        return safeLongToInt(days) + 1;
    }

    public String getTimeUntilString(Date deadline) {
       int daysLeft = getDaysUntilInt(deadline);
       return getTimeUntilString(daysLeft);
    }

    public String getTimeUntilString(int daysLeft) {

        if (daysLeft == 1) {
            return "1 day";
        } else {
            if (daysLeft < 7) {
                return daysLeft + " days";
            } else {
                if (daysLeft < 30) {
                    int weeks = daysLeft / 7;
                    if(weeks==1){
                        return weeks + " week";
                    } else {
                        return weeks + " weeks";
                    }
                } else {
                    int months = daysLeft / 30;
                    if(months==1){
                        return months + " month";
                    } else {
                        return months + " months";
                    }
                }
            }
        }
    }

    public String getTimeReaction(Date deadline) {
        int daysLeft = getDaysUntilInt(deadline);

        if (daysLeft < 7) {
            return "That's not very long only " + getTimeUntilString(daysLeft) + " to go!";
        } else {
            if (daysLeft < 30) {
                return "That's ok we've got " + getTimeUntilString(daysLeft) + " left.";
            } else {
                return "That's ages! We've got " + getTimeUntilString(daysLeft) + " left.";
            }
        }
    }

    public static int safeLongToInt(long l) {
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException
                    (l + " cannot be cast to int without changing its value.");
        }
        return (int) l;
    }

    public String getTimePhrase(Date d) {
        int daysLeft = getDaysUntilInt(d);
        if(isToday(d)){
            return "later today";
        } else {
            if (daysLeft == 1) {
                return "tomorrow";
            } else {
                if (daysLeft < 7) {
                    return "in a few days";
                } else {
                    if (daysLeft < 14) {
                        return "next week";
                    }
                }

            }
        }
        return null;
    }

    public boolean isSameDay(Date d1,Date d2){
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(d1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(d2);
        int day1 = cal1.get(Calendar.DAY_OF_MONTH);
        int day2 = cal2.get(Calendar.DAY_OF_MONTH);
        Log.e("DAY1:", Integer.toString(day1));
        Log.e("DAY2:", Integer.toString(day2));
        if(day1==day2){
            return true;
        } else {
            return false;
        }
    }

    public boolean isToday(Date d){
        Date d2 = new Date();
        return isSameDay(d,d2);
    }
}





