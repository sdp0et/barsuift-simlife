/**
 * barsuift-simlife is a life simulator program
 * 
 * Copyright (C) 2010 Cyrille GACHOT
 * 
 * This file is part of barsuift-simlife.
 * 
 * barsuift-simlife is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * barsuift-simlife is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with barsuift-simlife. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package barsuift.simLife.time;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.regex.Pattern;

import barsuift.simLife.Persistent;
import barsuift.simLife.message.BasicPublisher;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;

//FIXME there seem to be a bug when relaoding a calendar
public class SimLifeCalendar extends Calendar implements Persistent<SimLifeCalendarState>, Publisher {

    private static final long serialVersionUID = -2239086430259505817L;

    private static final MessageFormat STRING_FORMAT = new MessageFormat(
            "{0,number,00}:{1,number,00}:{2,number,000} {3} {4,number,00} {5} {6,number,0000}");

    private static final Pattern spacePattern = Pattern.compile(" ");

    private static final Pattern colonPattern = Pattern.compile(":");

    private static final int EPOCH_MILLIS = 0;

    static final int CURRENT_ERA = 0;

    /**
     * Value of the {@link #MONTH} field indicating the first month of the year in the SimLife calendar.
     */
    static final int WIM = Month.WIM.getIndex();

    /**
     * Value of the {@link #MONTH} field indicating the second month of the year in the SimLife calendar.
     */
    static final int SPRIM = Month.SPRIM.getIndex();

    /**
     * Value of the {@link #MONTH} field indicating the third month of the year in the SimLife calendar.
     */
    static final int SUM = Month.SUM.getIndex();

    /**
     * Value of the {@link #MONTH} field indicating the fourth month of the year in the SimLife calendar.
     */
    static final int TOM = Month.TOM.getIndex();

    /**
     * Value of the {@link #DAY_OF_WEEK} field indicating Nosday.
     */
    public final static int NOSDAY = Day.NOSDAY.getIndex();

    /**
     * Value of the {@link #DAY_OF_WEEK} field indicating Watsday.
     */
    public final static int WATSDAY = Day.WATSDAY.getIndex();

    /**
     * Value of the {@link #DAY_OF_WEEK} field indicating Stooday.
     */
    public final static int STOODAY = Day.STOODAY.getIndex();

    /**
     * Value of the {@link #DAY_OF_WEEK} field indicating Firday.
     */
    public final static int FIRDAY = Day.FIRDAY.getIndex();

    /**
     * Value of the {@link #DAY_OF_WEEK} field indicating Thunsday.
     */
    public final static int THUNSDAY = Day.THUNSDAY.getIndex();

    /**
     * Value of the {@link #DAY_OF_WEEK} field indicating Winday.
     */
    public final static int WINDAY = Day.WINDAY.getIndex();

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
    private static final int ONE_SECOND = MS_PER_SECOND;

    private static final int ONE_MINUTE = SECOND_PER_MINUTE * ONE_SECOND;

    private static final long ONE_DAY = MINUTE_PER_DAY * ONE_MINUTE;

    private static final long ONE_WEEK = DAY_PER_WEEK * ONE_DAY;

    private static final long ONE_MONTH = WEEK_PER_MONTH * ONE_WEEK;

    private static final long ONE_YEAR = MONTH_PER_YEAR * ONE_MONTH;

    static final int MIN_VALUES[] = { CURRENT_ERA, // ERA
            1, // YEAR
            WIM, // MONTH
            1, // WEEK_OF_YEAR
            1, // WEEK_OF_MONTH
            1, // DAY_OF_MONTH
            1, // DAY_OF_YEAR
            NOSDAY, // DAY_OF_WEEK
            1, // DAY_OF_WEEK_IN_MONTH
            AM, // AM_PM
            0, // HOUR
            0, // HOUR_OF_DAY
            0, // MINUTE
            0, // SECOND
            0, // MILLISECOND
            0, // ZONE_OFFSET (UNIX compatibility)
            0 // DST_OFFSET
    };

    static final int MAX_VALUES[] = { CURRENT_ERA, // ERA
            292269054, // YEAR
            TOM, // MONTH
            WEEK_PER_YEAR, // WEEK_OF_YEAR
            WEEK_PER_MONTH, // WEEK_OF_MONTH
            DAY_PER_MONTH, // DAY_OF_MONTH
            DAY_PER_YEAR, // DAY_OF_YEAR
            WINDAY, // DAY_OF_WEEK
            WEEK_PER_MONTH, // DAY_OF_WEEK_IN_MONTH
            AM, // AM_PM
            0, // HOUR
            0, // HOUR_OF_DAY
            MINUTE_PER_DAY - 1, // MINUTE
            SECOND_PER_MINUTE - 1, // SECOND
            MS_PER_SECOND - 1, // MILLISECOND
            0, // ZONE_OFFSET
            0 // DST_OFFSET (historical least maximum)
    };


    private final SimLifeCalendarState state;

    private final BasicPublisher publisher = new BasicPublisher(this);

    public SimLifeCalendar() {
        time = 0;
        set(YEAR, 1);
        set(MONTH, WIM);
        set(DAY_OF_MONTH, 1);
        set(MINUTE, 0);
        set(SECOND, 0);
        this.state = new SimLifeCalendarState(getTimeInMillis());
    }

    public SimLifeCalendar(String date) {
        setTime(date);
        this.state = new SimLifeCalendarState(getTimeInMillis());
    }

    public SimLifeCalendar(long millis) {
        setTimeInMillis(millis);
        this.state = new SimLifeCalendarState(millis);
    }

    public SimLifeCalendar(SimLifeCalendar copy) {
        long timeInMillis = copy.getTimeInMillis();
        setTimeInMillis(timeInMillis);
        this.state = new SimLifeCalendarState(timeInMillis);
    }

    public SimLifeCalendar(SimLifeCalendarState calendarState) {
        if (calendarState == null) {
            throw new IllegalArgumentException("calendar state is null");
        }
        setTimeInMillis(calendarState.getValue());
        this.state = calendarState;
    }

    public SimLifeCalendarState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        state.setValue(getTimeInMillis());
    }

    @Override
    protected void computeTime() {
        long result = computeTimeInDays();
        result = result * MINUTE_PER_DAY + internalGet(MINUTE); // time in minute
        result = result * SECOND_PER_MINUTE + internalGet(SECOND); // time in seconds
        result = result * MS_PER_SECOND + internalGet(MILLISECOND); // time in milliseconds
        time = result;
    }

    private long computeTimeInDays() {
        long result = internalGet(YEAR) - 1; // time in years
        if (isSet(MONTH)) {
            result = result * MONTH_PER_YEAR + internalGet(MONTH) - 1; // time in months
            if (isSet(DAY_OF_MONTH)) {
                return result * DAY_PER_MONTH + internalGet(DAY_OF_MONTH) - 1; // time in days
            } else {
                result *= WEEK_PER_MONTH;// time in weeks
                if (isSet(WEEK_OF_MONTH)) {
                    result += internalGet(WEEK_OF_MONTH) - 1;
                } else {
                    if (isSet(DAY_OF_WEEK_IN_MONTH)) {
                        result += internalGet(DAY_OF_WEEK_IN_MONTH) - 1;
                    }
                }
                result *= DAY_PER_WEEK;// time in days
                if (isSet(DAY_OF_WEEK)) {
                    result += internalGet(DAY_OF_WEEK) - 1;
                }
                return result;
            }
        } else {
            if (isSet(DAY_OF_YEAR)) {
                return result * DAY_PER_YEAR + internalGet(DAY_OF_YEAR) - 1; // time in days
            } else {
                result *= WEEK_PER_YEAR; // time in weeks
                if (isSet(WEEK_OF_YEAR)) {
                    result += internalGet(WEEK_OF_YEAR) - 1;
                }
                result *= DAY_PER_WEEK;// time in days
                if (isSet(DAY_OF_WEEK)) {
                    result += internalGet(DAY_OF_WEEK) - 1;
                }
                return result;
            }
        }
    }

    @Override
    protected void computeFields() {
        long relativeTime = time - EPOCH_MILLIS;
        long absDay = relativeTime / ONE_DAY;
        int dayOfMonth = (int) (absDay % DAY_PER_MONTH); // starting at 0
        fields[ERA] = CURRENT_ERA;
        fields[YEAR] = (int) (absDay / DAY_PER_YEAR) + 1;
        fields[MONTH] = (int) ((absDay / DAY_PER_MONTH) % MONTH_PER_YEAR) + 1;
        fields[WEEK_OF_YEAR] = (int) ((absDay / DAY_PER_WEEK) % WEEK_PER_YEAR) + 1;
        fields[WEEK_OF_MONTH] = (int) (dayOfMonth / DAY_PER_WEEK) + 1;
        fields[DAY_OF_MONTH] = dayOfMonth + 1;
        fields[DAY_OF_YEAR] = (int) (absDay % DAY_PER_YEAR) + 1;
        fields[DAY_OF_WEEK] = (int) (dayOfMonth % DAY_PER_WEEK) + 1;
        fields[DAY_OF_WEEK_IN_MONTH] = (int) (dayOfMonth / DAY_PER_WEEK) + 1;
        fields[AM_PM] = AM;
        fields[HOUR] = 0;
        fields[HOUR_OF_DAY] = 0;
        fields[MINUTE] = (int) (relativeTime % ONE_DAY) / ONE_MINUTE;
        fields[SECOND] = (int) (relativeTime % ONE_MINUTE) / ONE_SECOND;
        fields[MILLISECOND] = (int) (relativeTime % ONE_SECOND);
    }

    @Override
    public void add(int field, int amount) {
        if (amount == 0) {
            return; // Do nothing!
        }
        if (field < 0 || field >= ZONE_OFFSET) {
            throw new IllegalArgumentException("Invalid field " + field);
        }
        complete();
        long delta = amount;
        switch (field) {
        case ERA:
            // only one era, so nothing to do
            return;
        case YEAR:
            delta *= ONE_YEAR;
            break;
        case MONTH:
            delta *= ONE_MONTH;
            break;
        case WEEK_OF_YEAR:
            delta *= ONE_WEEK;
            break;
        case WEEK_OF_MONTH:
            delta *= ONE_WEEK;
            break;
        case DAY_OF_MONTH:
            delta *= ONE_DAY;
            break;
        case DAY_OF_YEAR:
            delta *= ONE_DAY;
            break;
        case DAY_OF_WEEK:
            delta *= ONE_DAY;
            break;
        case DAY_OF_WEEK_IN_MONTH:
            delta *= ONE_WEEK;
            break;
        case AM_PM:
            // no AM_PM, so nothing to do
            return;
        case HOUR:
            // no hour, so nothing to do
            return;
        case HOUR_OF_DAY:
            // no hour, so nothing to do
            return;
        case MINUTE:
            delta *= ONE_MINUTE;
            break;
        case SECOND:
            delta *= ONE_SECOND;
            break;
        case MILLISECOND:
            // nothing to do, as it is already in milliseconds
            break;
        }
        setTimeInMillis(time + delta);
        return;

    }

    @Override
    public void roll(int field, boolean up) {
        roll(field, up ? +1 : -1);
    }

    public void roll(int field, int amount) {
        if (amount == 0) {
            return; // Do nothing!
        }
        if (field < 0 || field >= ZONE_OFFSET) {
            throw new IllegalArgumentException("Invalid field " + field);
        }
        complete();

        int min = getMinimum(field);
        int max = getMaximum(field);

        int range = max - min + 1;
        amount %= range;
        int rolledValue = internalGet(field) + amount;
        if (rolledValue > max) {
            rolledValue -= range;
        } else {
            if (rolledValue < min) {
                rolledValue += range;
            }
        }
        set(field, rolledValue);
    }

    @Override
    public int getMinimum(int field) {
        return MIN_VALUES[field];
    }

    @Override
    public int getMaximum(int field) {
        return MAX_VALUES[field];
    }

    @Override
    public int getGreatestMinimum(int field) {
        return MIN_VALUES[field];
    }

    @Override
    public int getLeastMaximum(int field) {
        return MAX_VALUES[field];
    }

    public boolean equals(Object obj) {
        return obj instanceof SimLifeCalendar && super.equals(obj);
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
        complete();
        return STRING_FORMAT.format(new Object[] { internalGet(Calendar.MINUTE), internalGet(Calendar.SECOND),
                internalGet(Calendar.MILLISECOND), Day.values()[internalGet(Calendar.DAY_OF_WEEK) - 1],
                internalGet(Calendar.DAY_OF_MONTH), Month.values()[internalGet(Calendar.MONTH) - 1],
                internalGet(Calendar.YEAR) });
    }

    /**
     * Parse the given date and set the given values to this calendar instance. The date must be formatted as the
     * {@link #formatDate()} method would generate.
     * 
     * @param date the date to parse
     */
    public void setTime(String date) {
        String[] elements = spacePattern.split(date);
        String minSecMillisec = elements[0];
        String[] minSecElements = colonPattern.split(minSecMillisec);
        fields[YEAR] = Integer.parseInt(elements[4]);
        set(MONTH, Month.valueOf(elements[3].toUpperCase()).getIndex());
        set(DAY_OF_MONTH, Integer.parseInt(elements[2]));
        fields[DAY_OF_WEEK] = Day.valueOf(elements[1].toUpperCase()).getIndex();
        fields[MINUTE] = Integer.parseInt(minSecElements[0]);
        fields[SECOND] = Integer.parseInt(minSecElements[1]);
        fields[MILLISECOND] = Integer.parseInt(minSecElements[2]);
        computeTime();
        setChanged();
        notifySubscribers();
    }

    @Override
    public void setTimeInMillis(long millis) {
        super.setTimeInMillis(millis);
        setChanged();
        notifySubscribers();
    }

    @Override
    public void set(int field, int value) {
        super.set(field, value);
        setChanged();
        notifySubscribers();
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

    public int hashCode() {
        return publisher.hashCode();
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
