package barsuift.simLife.time;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.regex.Pattern;

import barsuift.simLife.Persistent;
import barsuift.simLife.message.BasicPublisher;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;

public class SimLifeDate implements Persistent<SimLifeDateState>, Publisher {

    private static final MessageFormat STRING_FORMAT = new MessageFormat(
            "{0,number,00}:{1,number,00}:{2,number,000} {3} {4,number,00} {5} {6,number,0000}");

    private static final Pattern spacePattern = Pattern.compile(" ");

    private static final Pattern colonPattern = Pattern.compile(":");

    public static final int MS_PER_SECOND = 1000;

    public static final int SECOND_PER_MINUTE = 60;

    public static final int MINUTE_PER_DAY = 20;

    public static final int DAY_PER_WEEK = 6;

    public static final int WEEK_PER_MONTH = 3;

    public static final int DAY_PER_MONTH = DAY_PER_WEEK * WEEK_PER_MONTH;

    public static final int MONTH_PER_YEAR = 4;

    public static final int WEEK_PER_YEAR = MONTH_PER_YEAR * WEEK_PER_MONTH;

    public static final int DAY_PER_YEAR = MONTH_PER_YEAR * DAY_PER_MONTH;


    /* Useful milliseconds constants */
    private static final int MS_FOR_ONE_SECOND = MS_PER_SECOND;

    private static final int MS_FOR_ONE_MINUTE = SECOND_PER_MINUTE * MS_FOR_ONE_SECOND;

    private static final long MS_FOR_ONE_DAY = MINUTE_PER_DAY * MS_FOR_ONE_MINUTE;

    private static final long MS_FOR_ONE_WEEK = DAY_PER_WEEK * MS_FOR_ONE_DAY;

    private static final long MS_FOR_ONE_MONTH = WEEK_PER_MONTH * MS_FOR_ONE_WEEK;

    private static final long MS_FOR_ONE_YEAR = MONTH_PER_YEAR * MS_FOR_ONE_MONTH;


    private final SimLifeDateState state;

    private final BasicPublisher publisher = new BasicPublisher(this);

    /**
     * superior or equals to 0
     */
    private long timeInMillis;


    /**
     * range between 0 and 999
     */
    private int millisOfSecond;

    /**
     * range between 0 and 59
     */
    private int secondOfMinute;

    /**
     * range between 0 and 19
     */
    private int minuteOfDay;

    private Day dayOfWeek;

    /**
     * range between 1 and 3
     */
    private int weekOfMonth;

    private Month monthOfYear;

    /**
     * superior or equals to 1
     */
    private int year;

    public SimLifeDate() {
        this(0);
    }

    /**
     * Creates a new date by parsing the given string. The parsing is done by the method {@link #setTime(String)}.
     * 
     * @param date the formatted date
     * @throws ParseException if the date is not in the appropriate format
     */
    public SimLifeDate(String date) throws ParseException {
        setTime(date);
        this.state = new SimLifeDateState(getTimeInMillis());
    }

    /**
     * Creates a new date with the given milliseconds.
     * 
     * @param millis the milliseconds
     */
    public SimLifeDate(long millis) {
        setTimeInMillis(millis);
        computeFields();
        this.state = new SimLifeDateState(millis);
    }

    /**
     * Duplicated the given date instance.
     * 
     * @param copy the date to copy
     */
    public SimLifeDate(SimLifeDate copy) {
        this(copy.getTimeInMillis());
    }

    /**
     * Creates a new date from the given date state.
     * 
     * @param dateState the date state
     */
    public SimLifeDate(SimLifeDateState dateState) {
        if (dateState == null) {
            throw new IllegalArgumentException("date state is null");
        }
        setTimeInMillis(dateState.getValue());
        computeFields();
        this.state = dateState;
    }

    public SimLifeDateState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        state.setValue(getTimeInMillis());
    }

    /**
     * Returns the time in milliseconds. The milliseconds are always superior or equal to 0.
     * 
     * @return the time in milliseconds
     */
    public long getTimeInMillis() {
        return timeInMillis;
    }

    /**
     * Return the milliseconds in the current second, in the range between 0 and 999
     * 
     * @return the millisecond part of the current date
     */
    public int getMillisOfSecond() {
        return millisOfSecond;
    }

    /**
     * Return the seconds in the current minute, in the range between 0 and 59
     * 
     * @return the second part of the current date
     */
    public int getSecondOfMinute() {
        return secondOfMinute;
    }

    /**
     * Return the minutes in the current day, in the range between 0 and 19
     * 
     * @return the minute part of the current date
     */
    public int getMinuteOfDay() {
        return minuteOfDay;
    }

    /**
     * Return the day in the current week
     * 
     * @return the week day part of the current date
     */
    public Day getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * Return the day in the current month, in the range between 1 and 18
     * 
     * @return the month day part of the current date
     */
    public int getDayOfMonth() {
        return (dayOfWeek.getIndex()) + ((weekOfMonth - 1) * DAY_PER_WEEK);
    }

    /**
     * Return the week in the current month, in the range between 1 and 3
     * 
     * @return the month week part of the current date
     */
    public int getWeekOfMonth() {
        return weekOfMonth;
    }

    /**
     * Return the month in the current year
     * 
     * @return the month part of the current date
     */
    public Month getMonthOfYear() {
        return monthOfYear;
    }

    /**
     * Return the year, always superior or equal to 0.
     * 
     * @return the year part of the current date
     */
    public int getYear() {
        return year;
    }

    /**
     * Computes the {@link #timeInMillis} field from other fields.
     */
    private void computeTime() {
        long result = year - 1; // time in years
        result = result * MONTH_PER_YEAR + monthOfYear.getIndex() - 1; // time in months
        result = result * WEEK_PER_MONTH + weekOfMonth - 1; // time in weeks
        result = result * DAY_PER_WEEK + dayOfWeek.getIndex() - 1; // time in days
        result = result * MINUTE_PER_DAY + minuteOfDay; // time in minute
        result = result * SECOND_PER_MINUTE + secondOfMinute; // time in seconds
        result = result * MS_PER_SECOND + millisOfSecond; // time in milliseconds
        timeInMillis = result;
        setChanged();
        notifySubscribers();
    }

    /**
     * Computes all the other fields from the {@link #timeInMillis} field.
     */
    private void computeFields() {
        // number of complete days in this time
        long absDay = timeInMillis / MS_FOR_ONE_DAY;
        int dayOfMonth = (int) (absDay % DAY_PER_MONTH); // starting at 0
        year = (int) (absDay / DAY_PER_YEAR) + 1;
        monthOfYear = Month.values()[(int) ((absDay / DAY_PER_MONTH) % MONTH_PER_YEAR)];
        weekOfMonth = (int) (dayOfMonth / DAY_PER_WEEK) + 1;
        dayOfWeek = Day.values()[(dayOfMonth % DAY_PER_WEEK)];
        minuteOfDay = (int) (timeInMillis % MS_FOR_ONE_DAY) / MS_FOR_ONE_MINUTE;
        secondOfMinute = (int) (timeInMillis % MS_FOR_ONE_MINUTE) / MS_FOR_ONE_SECOND;
        millisOfSecond = (int) (timeInMillis % MS_FOR_ONE_SECOND);
        setChanged();
        notifySubscribers();
    }

    /**
     * Set the time from the given milliseconds. All the other fields are updated accordingly.
     * 
     * @param millis the time in milliseconds
     */
    public synchronized void setTimeInMillis(long millis) {
        timeInMillis = millis;
        computeFields();
    }

    /**
     * Set all the date fields
     * 
     * @param millisOfSecond the milliseconds in the range between 0 and 999
     * @param secondOfMinute the seconds in the range between 0 and 59
     * @param minuteOfDay the minutes in the range between 0 and 19
     * @param dayOfWeek the day of week
     * @param weekOfMonth the week of month in the range between 1 and 3
     * @param monthOfYear the month
     * @param year the year, positive or equal to 0
     */
    public synchronized void set(int millisOfSecond, int secondOfMinute, int minuteOfDay, Day dayOfWeek,
            int weekOfMonth, Month monthOfYear, int year) {
        this.millisOfSecond = millisOfSecond;
        this.secondOfMinute = secondOfMinute;
        this.minuteOfDay = minuteOfDay;
        this.dayOfWeek = dayOfWeek;
        this.weekOfMonth = weekOfMonth;
        this.monthOfYear = monthOfYear;
        this.year = year;
        computeTime();
    }

    /**
     * Set the milliseconds for the current second. All other fields remains unchanged.
     * 
     * @param millis the milliseconds in the range between 0 and 999
     */
    public synchronized void setMillisOfSecond(int millis) {
        millisOfSecond = millis;
        computeTime();
    }

    /**
     * Set the seconds for the current minute. All other fields remains unchanged.
     * 
     * @param seconds the seconds in the range between 0 and 59
     */
    public synchronized void setSecondOfMinute(int seconds) {
        secondOfMinute = seconds;
        computeTime();
    }

    /**
     * Set the minutes for the current day. All other fields remains unchanged.
     * 
     * @param minutes the minutes in the range between 0 and 19
     */
    public synchronized void setMinuteOfDay(int minutes) {
        minuteOfDay = minutes;
        computeTime();
    }

    /**
     * Set the day for the current week. All other fields remains unchanged.
     * 
     * @param day the day of the week
     */
    public synchronized void setDayOfWeek(Day day) {
        dayOfWeek = day;
        computeTime();
    }

    /**
     * Set the day for the current month. The day of week and week of month are updated. All other fields remains
     * unchanged.
     * 
     * @param dayOfMonth the day in the current month in the range between 1 and 18
     */
    public synchronized void setDayOfMonth(int dayOfMonth) {
        weekOfMonth = (int) ((dayOfMonth - 1) / DAY_PER_WEEK) + 1;
        dayOfWeek = Day.values()[(dayOfMonth - 1) % DAY_PER_WEEK];
        computeTime();
    }

    /**
     * Set the week for the current month. All other fields remains unchanged.
     * 
     * @param week the week in the range between 1 and 3
     */
    public synchronized void setWeekOfMonth(int week) {
        weekOfMonth = week;
        computeTime();
    }

    /**
     * Set the month for the current year. All other fields remains unchanged.
     * 
     * @param month the month
     */
    public synchronized void setMonthOfYear(Month month) {
        monthOfYear = month;
        computeTime();
    }

    /**
     * Set the years for the current date. All other fields remains unchanged.
     * 
     * @param years the years, positive or equal to 0
     */
    public synchronized void setYear(int years) {
        year = years;
        computeTime();
    }

    /**
     * Add the given milliseconds to the current date. All the other fields are updated accordingly.
     * 
     * @param millis the milliseconds to add
     */
    public synchronized void addMillis(long millis) {
        setTimeInMillis(timeInMillis + millis);
    }

    /**
     * Add the given seconds to the current date. All the other fields are updated accordingly.
     * 
     * @param seconds the seconds to add
     */
    public synchronized void addSeconds(long seconds) {
        setTimeInMillis(timeInMillis + (seconds * MS_FOR_ONE_SECOND));
    }

    /**
     * Add the given minutes to the current date. All the other fields are updated accordingly.
     * 
     * @param minutes the minutes to add
     */
    public synchronized void addMinutes(long minutes) {
        setTimeInMillis(timeInMillis + (minutes * MS_FOR_ONE_MINUTE));
    }

    /**
     * Add the given days to the current date. All the other fields are updated accordingly.
     * 
     * @param days the days to add
     */
    public synchronized void addDays(long days) {
        setTimeInMillis(timeInMillis + (days * MS_FOR_ONE_DAY));
    }

    /**
     * Add the given weeks to the current date. All the other fields are updated accordingly.
     * 
     * @param weeks the weeks to add
     */
    public synchronized void addWeeks(long weeks) {
        setTimeInMillis(timeInMillis + (weeks * MS_FOR_ONE_WEEK));
    }

    /**
     * Add the given months to the current date. All the other fields are updated accordingly.
     * 
     * @param months the months to add
     */
    public synchronized void addMonths(long months) {
        setTimeInMillis(timeInMillis + (months * MS_FOR_ONE_MONTH));
    }

    /**
     * Add the given years to the current date. All the other fields are updated accordingly.
     * 
     * @param years the years to add
     */
    public synchronized void addYears(long years) {
        setTimeInMillis(timeInMillis + (years * MS_FOR_ONE_YEAR));
    }


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
    public String formatDate() {
        return STRING_FORMAT.format(new Object[] { minuteOfDay, secondOfMinute, millisOfSecond, dayOfWeek,
                getDayOfMonth(), monthOfYear, year });
    }

    /**
     * Parse the given date and set the given values to this date instance. The date must be formatted as the
     * {@link #formatDate()} method would generate.
     * 
     * @param date the date to parse
     * @throws ParseException
     */
    public synchronized void setTime(String date) throws ParseException {
        String[] elements = spacePattern.split(date);
        String minSecMillisec = elements[0];
        String[] minSecElements = colonPattern.split(minSecMillisec);
        year = Integer.parseInt(elements[4]);
        monthOfYear = Month.valueOf(elements[3].toUpperCase());
        int dayOfMonth = Integer.parseInt(elements[2]);
        weekOfMonth = (int) ((dayOfMonth - 1) / DAY_PER_WEEK) + 1;
        dayOfWeek = Day.values()[(dayOfMonth - 1) % DAY_PER_WEEK];
        Day dayOfWeekFromText = Day.valueOf(elements[1].toUpperCase());
        if (dayOfWeekFromText != dayOfWeek) {
            throw new ParseException(date, minSecMillisec.length() + 1);
        }
        minuteOfDay = Integer.parseInt(minSecElements[0]);
        secondOfMinute = Integer.parseInt(minSecElements[1]);
        millisOfSecond = Integer.parseInt(minSecElements[2]);
        computeTime();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (timeInMillis ^ (timeInMillis >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SimLifeDate other = (SimLifeDate) obj;
        if (timeInMillis != other.timeInMillis)
            return false;
        return true;
    }

    /**
     * Format the date as described in the {@link #formatDate()} method.
     * 
     * @see #formatDate()
     */
    @Override
    public String toString() {
        return formatDate();
    }




    @Override
    public void addSubscriber(Subscriber subscriber) {
        publisher.addSubscriber(subscriber);
    }

    @Override
    public void deleteSubscriber(Subscriber subscriber) {
        publisher.deleteSubscriber(subscriber);
    }

    @Override
    public void notifySubscribers() {
        publisher.notifySubscribers();
    }

    @Override
    public void notifySubscribers(Object arg) {
        publisher.notifySubscribers(arg);
    }

    @Override
    public void deleteSubscribers() {
        publisher.deleteSubscribers();
    }

    @Override
    public void setChanged() {
        publisher.setChanged();
    }

    @Override
    public void clearChanged() {
        publisher.clearChanged();
    }

    @Override
    public boolean hasChanged() {
        return publisher.hasChanged();
    }

    @Override
    public int countSubscribers() {
        return publisher.countSubscribers();
    }

}
