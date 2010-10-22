package barsuift.simLife.time;

import java.text.ParseException;

import barsuift.simLife.Persistent;
import barsuift.simLife.message.Publisher;



public interface SimLifeDate extends Persistent<SimLifeDateState>, Publisher {

    public static final int MS_PER_SECOND = 1000;

    public static final int SECOND_PER_MINUTE = 60;

    public static final int MINUTE_PER_DAY = 20;

    public static final int DAY_PER_WEEK = 6;

    public static final int WEEK_PER_MONTH = 3;

    public static final int DAY_PER_MONTH = DAY_PER_WEEK * WEEK_PER_MONTH;

    public static final int MONTH_PER_YEAR = 4;

    public static final int WEEK_PER_YEAR = MONTH_PER_YEAR * WEEK_PER_MONTH;

    public static final int DAY_PER_YEAR = MONTH_PER_YEAR * DAY_PER_MONTH;

    public abstract long getTimeInMillis();

    public abstract int getMillisOfSecond();

    public abstract int getSecondOfMinute();

    public abstract int getMinuteOfDay();

    public abstract Day getDayOfWeek();

    public abstract int getDayOfMonth();

    public abstract int getWeekOfMonth();

    public abstract Month getMonthOfYear();

    public abstract int getYear();

    public abstract void setTimeInMillis(long millis);

    public abstract void set(int millisOfSecond, int secondOfMinute, int minuteOfDay, Day dayOfWeek, int weekOfMonth,
            Month monthOfYear, int year);

    public abstract void setMillisOfSecond(int millis);

    public abstract void setSecondOfMinute(int seconds);

    public abstract void setMinuteOfDay(int minutes);

    public abstract void setDayOfWeek(Day day);

    public abstract void setDayOfMonth(int dayOfMonth);

    public abstract void setWeekOfMonth(int weeks);

    public abstract void setMonthOfYear(Month month);

    public abstract void setYear(int years);

    public abstract void addMillis(long millis);

    public abstract void addSeconds(long seconds);

    public abstract void addMinutes(long minutes);

    public abstract void addDays(long days);

    public abstract void addWeeks(long weeks);

    public abstract void addMonths(long months);

    public abstract void addYears(long years);

    /**
     * Date is formatted with the following pattern : <code>mm:ss:SSS DayInWeek DayInMonth Month Year</code>. For
     * example, it can be <code>19:59:999 Nosday 18 Tom 0455</code>
     * <p>
     * Here are the rules to be applied when formatting :
     * <ul>
     * <li>minutes are two digits</li>
     * <li>seconds are two digits</li>
     * <li>milliseconds are three digits</li>
     * <li>day in week is text, starting with an uppercase character</li>
     * <li>day in month is two digits</li>
     * <li>month is text, starting with an uppercase character</li>
     * <li>year is at least 4 digits, and can be more if needed</li>
     * </ul>
     * </p>
     * 
     * @return the formatted date
     */
    public abstract String formatDate();

    /**
     * Parse the given date and set the given values to this date instance. The date must be formatted as the
     * {@link #formatDate()} method would generate.
     * 
     * @param date the date to parse
     * @throws ParseException
     */
    public abstract void setTime(String date) throws ParseException;

}