package com.overthink.mechmaid.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * A library of methods for dealing with Dates, Calendars, and all of their ugliness.
 */
public class TimeUtils {

    private static final String TAG = TimeUtils.class.getName();

    //Date format patterns
    public static final String FORMAT_DATE_TIME_LONG = "M/d/yyyy hh:mm:ss a";
    public static final String FORMAT_DATE_TIME_SHORT = "M/d/yyyy h:mm a";
    public static final String FORMAT_RSV_DATE_AND_TIME = "EEEE, MMMM d @ h:mm a";
    public static final String FORMAT_RSV_DATE = "EEEE, MMMM d";
    public static final String FORMAT_TIME_SHORT = "h:mm a";
    public static final String FORMAT_URL_TIME = "MMddyyyy";

    //Fields
    /**Minutes elapsed in current day**/
    public static final int MINUTE_OF_DAY = 1;
    /**Minutes elapsed in current hour**/
    public static final int MINUTE_OF_HOUR = 2;
    /**Hours elapsed in current day (24 hour format)**/
    public static final int HOUR_OF_DAY = 4;
    /**Days elapsed in current year**/
    public static final int DAY_OF_YEAR = 8;

    /** Second precision **/
    public static final int SECOND = 16;
    /** Minute precision **/
    public static final int MINUTE = 32;
    /** Hour precision **/
    public static final int HOUR = 64;
    /** Day precision **/
    public static final int DAY = 128;
    /** Week precision **/
    public static final int WEEK = 256;
    /** Month precision **/
    public static final int MONTH = 512;
    /** Year precision **/
    public static final int YEAR = 1024;

    //Conversions
    public static final int MINUTES_PER_HOUR = 60;
    public static final int SECONDS_PER_MINUTE = 60;
    public static final int HOURS_PER_DAY = 24;
    public static final int DAYS_PER_WEEK = 7;
    public static final int MS_PER_SECOND = 1000;
    public static final int MS_PER_MINUTE = MS_PER_SECOND * SECONDS_PER_MINUTE;
    public static final int MS_PER_HOUR = MS_PER_MINUTE * MINUTES_PER_HOUR;
    public static final int MS_PER_DAY = MS_PER_HOUR * HOURS_PER_DAY;
    public static final int MS_PER_WEEK = MS_PER_DAY * DAYS_PER_WEEK;

    public static final double SECONDS_PER_MS = 1.0 / 1000;
    public static final double MINUTES_PER_MS = SECONDS_PER_MS / (double)SECONDS_PER_MINUTE;
    public static final double HOURS_PER_MS = MINUTES_PER_MS / (double)MINUTES_PER_HOUR;
    public static final double DAY_PER_MS = HOURS_PER_MS / (double)HOURS_PER_DAY;
    public static final double WEEK_PER_MS = DAY_PER_MS / (double)HOURS_PER_DAY;


    //Error messages
    private static final String ERR_MESSAGE_INVALID_FIELD = "Invalid time field requested";
    public static final TimeZone TIME_ZONE_UTC = TimeZone.getTimeZone("UTC");

    /**
     * Returns a field of the current time as an integer.  Fields accepted: MINUTE_OF_DAY, MINUTE_OF_HOUR, HOUR_OF_DAY,
     * DAY_OF_YEAR
     *
     * @param field The field (i.e. minute, hour, etc) to retrieve
     * @return the desired field
     */
    public static int getCurrentTimeField(int field) {
        //Get calendar for current time
        Calendar now = Calendar.getInstance();
        //Select appropriate field
        switch (field) {
            case MINUTE_OF_DAY:
                return (now.get(Calendar.HOUR_OF_DAY) * MINUTES_PER_HOUR) + now.get(Calendar.MINUTE);
            case MINUTE_OF_HOUR:
                return now.get(Calendar.MINUTE);
            case HOUR_OF_DAY:
                return now.get(Calendar.HOUR_OF_DAY);
            case DAY_OF_YEAR:
                return now.get(Calendar.DAY_OF_YEAR);
            default:
                Log.e(TAG, ERR_MESSAGE_INVALID_FIELD + "(in getCurrentTimeField())");
                return -1;
        }
    }

    /**
     * Returns a field of a Calendar as an integer. Fields accepted: MINUTE_OF_DAY, MINUTE_OF_HOUR, HOUR_OF_DAY,
     * DAY_OF_YEAR
     *
     * @param field The field to retrieve
     * @param cal the Calendar
     * @return the desired field
     */
    public static int getTimeField(Calendar cal, int field) {
        //Select appropriate field
        switch (field) {
            case MINUTE_OF_DAY:
                return (cal.get(Calendar.HOUR_OF_DAY) * MINUTES_PER_HOUR) + cal.get(Calendar.MINUTE);
            case MINUTE_OF_HOUR:
                return cal.get(Calendar.MINUTE);
            case HOUR_OF_DAY:
                return cal.get(Calendar.HOUR_OF_DAY);
            case DAY_OF_YEAR:
                return cal.get(Calendar.DAY_OF_YEAR);
            default:
                Log.e(TAG, ERR_MESSAGE_INVALID_FIELD + "(in getCurrentTimeField())");
                return -1;
        }
    }

    /**
     * Returns a field of a Date as an integer. Fields accepted: MINUTE_OF_DAY, MINUTE_OF_HOUR, HOUR_OF_DAY,
     * DAY_OF_YEAR
     *
     * @param field The field (i.e. minute, hour, etc) to retrieve
     * @param date the Date
     * @return the desired field
     */
    public static int getTimeField(Date date, int field) {
        return getTimeField(dateToCalendar(date), field);
    }

    /**
     * Returns a field of the current time as a floating point number. Fields accepted: HOUR_OF_DAY, DAY_OF_YEAR.
     *
     * @param field The field (i.e. minute, hour, etc) to retrieve
     * @return the desired field
     */
    public static double getCurrentTimeFieldFractional(int field) {
        //Get calendar for current time
        Calendar now = Calendar.getInstance();
        //Select appropriate field
        switch (field) {
            case HOUR_OF_DAY:
                return now.get(Calendar.HOUR_OF_DAY) + (now.get(Calendar.MINUTE) / (double)MINUTES_PER_HOUR) ;
            case DAY_OF_YEAR:
                return now.get(Calendar.DAY_OF_YEAR) + (now.get(Calendar.HOUR_OF_DAY) / (float)HOURS_PER_DAY);
            default:
                Log.e(TAG, ERR_MESSAGE_INVALID_FIELD + "(in getCurrentTimeFieldFractional())");
                return -1;
        }
    }

    /**
     * Determines if a Calendar is set to today
     *
     * @param cal calendar to test
     * @return true if it is set to today
     */
    public static boolean isToday(Calendar cal) {
        Calendar now = Calendar.getInstance();
        return (cal.get(Calendar.YEAR) == now.get(Calendar.YEAR)) && (
                cal.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR));
    }

    /**
     * Determines if a Date is set to today
     *
     * @param date date to test
     * @return true if it is set to today
     */
    public static boolean isToday(Date date) {
        return isToday(dateToCalendar(date));
    }

    /**
     * Converts a Date to a Calendar
     *
     * @param date date to convert
     * @return a calendar
     */
    public static Calendar dateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        cal.setTimeZone(TimeZone.getDefault());
        return cal;
    }

    /**
     * Converts a Calendar to a Date
     *
     * @param cal calendar to convert
     * @return a date
     */
    public static Date calendarToDate(Calendar cal) {
        return new Date(cal.getTimeInMillis());
    }

    /**
     * Compares two Calendars chronologically
     *
     * @param cal1 first Calendar to compare
     * @param cal2 second Calendar to compare
     * @return 0 if equal, 1 if cal1 is later, and -1 if cal2 is later
     */
    public static int compareCalendars(Calendar cal1, Calendar cal2) {
        //Cal 1 is after
        if (cal1.getTimeInMillis() < cal2.getTimeInMillis()) {
            return -1;
        }
        //Both calls equal
        else if (cal1.getTimeInMillis() == cal2.getTimeInMillis()) {
            return 0;
        }
        //Cal 1 is before
        else {
            return 1;
        }
    }

    /**
     * Compares two Dates chronologically
     *
     * @param date1 first Date to compare
     * @param date2 second Date to compare
     * @return 0 if equal, 1 if date1 is later, and -1 if date2 is later
     */
    public static int compareDates(Date date1, Date date2) {
        return (compareCalendars(dateToCalendar(date1), dateToCalendar(date2)));
    }

    /**
     * Creates a date string according to a format string and a Calendar
     *
     * @param cal Calendar to convert to formatted string
     * @param formatString String specifying time format
     * @return a formatted date/time String
     */
    public static String createDateTimeString(Calendar cal, String formatString) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatString, Locale.US);
//        sdf.setTimeZone(TIME_ZONE_UTC);
        return sdf.format(calendarToDate(cal));
    }

    /**
     * Creates a date string according to a format string and a TIME_ZONE_UTC Unix milliseconds time
     *
     * @param millis UTC Unix time in milliseconds to convert to formatted string
     * @param formatString String specifying time format
     * @return a formatted date/time String
     */
    public static String createDateTimeString(long millis, String formatString) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        SimpleDateFormat sdf = new SimpleDateFormat(formatString, Locale.US);
//        sdf.setTimeZone(TIME_ZONE_UTC);
        return sdf.format(calendarToDate(cal));
    }

    /**
     * Creates a date string according to a format string and a Date
     *
     * @param date Date to convert to formatted string
     * @param formatString String specifying time format
     * @return a formatted date/time String
     */
    public static String createDateTimeString(Date date, String formatString) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatString, Locale.US);
        sdf.setTimeZone(TIME_ZONE_UTC);
        return sdf.format(date);
    }

    /**
     * Compares two Calendars for equality down to a given precision. To specify precision use one of the following
     * class constants
     *
     * @param cal1 first Calendar to compare
     * @param cal2 second Calendar to compare
     * @param field precision required for equality
     * @return true if Calendars are equal down to specified precision
     */
    public static boolean compareCalendarsToPrecision(Calendar cal1, Calendar cal2, int field) {
        switch (field) {
            case SECOND:
                if (cal1.get(Calendar.SECOND) != cal2.get(Calendar.SECOND)) {
                    return false;
                }
            case MINUTE:
                if (cal1.get(Calendar.MINUTE) != cal2.get(Calendar.MINUTE)) {
                    return false;
                }
            case HOUR:
                if (cal1.get(Calendar.HOUR_OF_DAY) != cal2.get(Calendar.HOUR_OF_DAY)) {
                    return false;
                }
            case DAY:
                if (cal1.get(Calendar.DAY_OF_YEAR) != cal2.get(Calendar.DAY_OF_YEAR)) {
                    return false;
                }
            case WEEK:
                if (cal1.get(Calendar.WEEK_OF_YEAR) != cal2.get(Calendar.WEEK_OF_YEAR)) {
                    return false;
                }
            case MONTH:
                if (cal1.get(Calendar.MONTH) != cal2.get(Calendar.MONTH)) {
                    return false;
                }
            case YEAR:
                if (cal1.get(Calendar.YEAR) != cal2.get(Calendar.YEAR)) {
                    return false;
                }
            default:
                Log.e(TAG, ERR_MESSAGE_INVALID_FIELD + "(in compareCalendarsToPrecision)");

        }
        //Everything matched up to desired precision
        return true;
    }

    /**
     * Compares two Dates for equality down to a given precision. Fields accepted: SECOND, MINUTE, HOUR, DAY, WEEK,
     * MONTH, YEAR.
     *
     * @param date1 first Date to compare
     * @param date2 second Date to compare
     * @param field precision required for equality
     * @return true if Dates are equal down to specified precision
     */
    public static boolean compareDatesToPrecision(Date date1, Date date2, int field) {
        //Call through to calendar version of method
        return compareCalendarsToPrecision(dateToCalendar(date1), dateToCalendar(date2), field);
    }

    /**
     * Parse a date/time string of the specified format into a Calendar object
     *
     * @param timeString string to be parsed
     * @param format format of string to be parsed
     * @return a Calendar based on parsed string
     */
    public static Calendar parseStringToCalendar(String timeString, String format) {
        return dateToCalendar(parseStringToDate(timeString,format));
    }

    /**
     * Parse a date/time string of the specified format into a Date object
     *
     * @param timeString string to be parsed
     * @param format format of string to be parsed
     * @return a Date based on parsed string
     */
    public static Date parseStringToDate(String timeString, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        try {
            return sdf.parse(timeString);
        } catch (ParseException e) {
            Log.e(TAG, "Could not parse date string", e);
            return null;
        }

    }

    /**
     * Converts a date from one format to another format
     *
     * @param originalFormat the format of the original String
     * @param originalDate the original String
     * @param newFormat the desired format of the new String
     * @return the new String
     */
    public static String convertDateTimeString(String originalFormat, String originalDate, String newFormat) {
        return createDateTimeString(parseStringToCalendar(originalDate, originalFormat), newFormat);
    }


}
