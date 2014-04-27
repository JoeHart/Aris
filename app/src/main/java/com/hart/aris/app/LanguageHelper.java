package com.hart.aris.app;
import android.util.Log;

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

    public String getActivity() {
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

        if (daysLeft == 1) {
            return "1 day";
        } else {
            if (daysLeft < 7) {
                return daysLeft + " days";
            } else {
                if (daysLeft < 30) {
                    int weeks = daysLeft / 7;
                    return weeks + " weeks";
                } else {
                    int months = daysLeft / 30;
                    return months + " months";
                }
            }
        }
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
                    return weeks + " weeks";
                } else {
                    int months = daysLeft / 30;
                    return months + " months";
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
        return null;
    }
}





