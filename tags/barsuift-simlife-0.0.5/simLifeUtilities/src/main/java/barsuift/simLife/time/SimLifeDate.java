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

    public SimLifeDate(String date) throws ParseException {
        setTime(date);
        this.state = new SimLifeDateState(getTimeInMillis());
    }

    public SimLifeDate(long millis) {
        setTimeInMillis(millis);
        computeFields();
        this.state = new SimLifeDateState(millis);
    }

    public SimLifeDate(SimLifeDate copy) {
        this(copy.getTimeInMillis());
    }

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

    public long getTimeInMillis() {
        return timeInMillis;
    }

    public int getMillisOfSecond() {
        return millisOfSecond;
    }

    public int getSecondOfMinute() {
        return secondOfMinute;
    }

    public int getMinuteOfDay() {
        return minuteOfDay;
    }

    public Day getDayOfWeek() {
        return dayOfWeek;
    }

    public int getDayOfMonth() {
        return (dayOfWeek.getIndex()) + ((weekOfMonth - 1) * DAY_PER_WEEK);
    }

    public int getWeekOfMonth() {
        return weekOfMonth;
    }

    public Month getMonthOfYear() {
        return monthOfYear;
    }

    public int getYear() {
        return year;
    }

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

    public synchronized void setTimeInMillis(long millis) {
        timeInMillis = millis;
        computeFields();
    }

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

    public synchronized void setMillisOfSecond(int millis) {
        millisOfSecond = millis;
        computeTime();
    }

    public synchronized void setSecondOfMinute(int seconds) {
        secondOfMinute = seconds;
        computeTime();
    }

    public synchronized void setMinuteOfDay(int minutes) {
        minuteOfDay = minutes;
        computeTime();
    }

    public synchronized void setDayOfWeek(Day day) {
        dayOfWeek = day;
        computeTime();
    }

    public synchronized void setDayOfMonth(int dayOfMonth) {
        weekOfMonth = (int) ((dayOfMonth - 1) / DAY_PER_WEEK) + 1;
        dayOfWeek = Day.values()[(dayOfMonth - 1) % DAY_PER_WEEK];
        computeTime();
    }

    public synchronized void setWeekOfMonth(int weeks) {
        weekOfMonth = weeks;
        computeTime();
    }

    public synchronized void setMonthOfYear(Month month) {
        monthOfYear = month;
        computeTime();
    }

    public synchronized void setYear(int years) {
        year = years;
        computeTime();
    }

    public synchronized void addMillis(long millis) {
        setTimeInMillis(timeInMillis + millis);
    }

    public synchronized void addSeconds(long seconds) {
        setTimeInMillis(timeInMillis + (seconds * MS_FOR_ONE_SECOND));
    }

    public synchronized void addMinutes(long minutes) {
        setTimeInMillis(timeInMillis + (minutes * MS_FOR_ONE_MINUTE));
    }

    public synchronized void addDays(long days) {
        setTimeInMillis(timeInMillis + (days * MS_FOR_ONE_DAY));
    }

    public synchronized void addWeeks(long weeks) {
        setTimeInMillis(timeInMillis + (weeks * MS_FOR_ONE_WEEK));
    }

    public synchronized void addMonths(long months) {
        setTimeInMillis(timeInMillis + (months * MS_FOR_ONE_MONTH));
    }

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

    @Override
    public String toString() {
        return formatDate();
    }





    public void addSubscriber(Subscriber subscriber) {
        publisher.addSubscriber(subscriber);
    }

    public void deleteSubscriber(Subscriber subscriber) {
        publisher.deleteSubscriber(subscriber);
    }

    public void notifySubscribers() {
        publisher.notifySubscribers();
    }

    public void notifySubscribers(Object arg) {
        publisher.notifySubscribers(arg);
    }

    public void deleteSubscribers() {
        publisher.deleteSubscribers();
    }

    public void setChanged() {
        publisher.setChanged();
    }

    public void clearChanged() {
        publisher.clearChanged();
    }

    public boolean hasChanged() {
        return publisher.hasChanged();
    }

    public int countSubscribers() {
        return publisher.countSubscribers();
    }

}
