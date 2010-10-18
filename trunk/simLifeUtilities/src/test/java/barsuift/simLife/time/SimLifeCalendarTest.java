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

import java.util.Calendar;

import junit.framework.TestCase;
import barsuift.simLife.message.PublisherTestHelper;


public class SimLifeCalendarTest extends TestCase {

    public void testConstructorString() {
        SimLifeCalendar calendar = new SimLifeCalendar("19:59:999 Firday 04 Sprim 0003");
        assertEquals("19:59:999 Firday 04 Sprim 0003", calendar.formatDate());
        // = 999 + 2*86 400 000 + 1*21 600 000 + 3*1 200 000 + 19*60 000 + 59*1000
        // = 999 + 172 800 000 + 21 600 000 + 3 600 000 + 1 140 000 + 59 000
        // = 199 199 999
        assertEquals(199199999, calendar.getTimeInMillis());

        calendar = new SimLifeCalendar("06:13:158 Winday 12 Tom 0001");
        assertEquals("06:13:158 Winday 12 Tom 0001", calendar.formatDate());
        // = 158 + 3*21 600 000 + 11*1 200 000 + 6*60 000 + 13*1000
        // = 158 + 64 800 000 + 13 200 000 + 360 000 + 13 000
        // = 78 373 158
        assertEquals(78373158, calendar.getTimeInMillis());
    }

    public void testConstructorCopy() {
        SimLifeCalendar calendar = new SimLifeCalendar();
        calendar.setTimeInMillis(199199158);
        SimLifeCalendar cal2 = new SimLifeCalendar(calendar);
        assertEquals(calendar, cal2);
        assertEquals(199199158, cal2.getTimeInMillis());
    }

    public void testConstructorLong() {
        SimLifeCalendar calendar = new SimLifeCalendar(199199000);
        assertEquals(199199000, calendar.getTimeInMillis());
        assertEquals("19:59:000 Firday 04 Sprim 0003", calendar.formatDate());

        // same with 1 additional second and 1 millisecond
        calendar = new SimLifeCalendar(199200001);
        assertEquals(199200001, calendar.getTimeInMillis());
        assertEquals("00:00:001 Thunsday 05 Sprim 0003", calendar.formatDate());

        calendar = new SimLifeCalendar(78373000);
        assertEquals(78373000, calendar.getTimeInMillis());
        assertEquals("06:13:000 Winday 12 Tom 0001", calendar.formatDate());
    }

    public void testConstructorState() {
        SimLifeCalendarState calendarState = new SimLifeCalendarState();
        calendarState.setValue(199199000);
        SimLifeCalendar calendar = new SimLifeCalendar(calendarState);
        assertEquals(199199000, calendar.getTimeInMillis());
        try {
            new SimLifeCalendar((SimLifeCalendarState) null);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    public void testAddWithBasicConstructor() {
        internalTestAdd(new SimLifeCalendar());
    }

    public void testAddWithLongConstructor() {
        internalTestAdd(new SimLifeCalendar(0));
    }

    public void testAddWithCalendarConstructor() {
        SimLifeCalendar calendar = new SimLifeCalendar();
        internalTestAdd(new SimLifeCalendar(calendar));
    }

    public void testAddWithStateConstructor() {
        SimLifeCalendarState calendarState = new SimLifeCalendarState();
        internalTestAdd(new SimLifeCalendar(calendarState));
    }

    public void testAddWithStringConstructor() {
        internalTestAdd(new SimLifeCalendar("00:00:000 Nosday 01 Wim 0001"));
    }

    public void internalTestAdd(Calendar calendar) {
        assertEquals(0, calendar.getTimeInMillis());
        assertEquals(0, calendar.get(Calendar.MILLISECOND));
        assertEquals(0, calendar.get(Calendar.SECOND));

        calendar.add(Calendar.MILLISECOND, 1);
        assertEquals(1, calendar.getTimeInMillis());
        assertEquals(1, calendar.get(Calendar.MILLISECOND));
        assertEquals(0, calendar.get(Calendar.SECOND));

        calendar.add(Calendar.MILLISECOND, 499);
        assertEquals(500, calendar.getTimeInMillis());
        assertEquals(500, calendar.get(Calendar.MILLISECOND));
        assertEquals(0, calendar.get(Calendar.SECOND));

        calendar.add(Calendar.MILLISECOND, 500);
        assertEquals(1000, calendar.getTimeInMillis());
        assertEquals(0, calendar.get(Calendar.MILLISECOND));
        assertEquals(1, calendar.get(Calendar.SECOND));

        calendar.add(Calendar.SECOND, 19);
        assertEquals(20000, calendar.getTimeInMillis());
        assertEquals(0, calendar.get(Calendar.MILLISECOND));
        assertEquals(20, calendar.get(Calendar.SECOND));

        calendar.add(Calendar.SECOND, 40);
        assertEquals(60000, calendar.getTimeInMillis());
        assertEquals(0, calendar.get(Calendar.MILLISECOND));
        assertEquals(0, calendar.get(Calendar.SECOND));
        assertEquals(1, calendar.get(Calendar.MINUTE));

        calendar.add(Calendar.MINUTE, 10);
        assertEquals(660000, calendar.getTimeInMillis());
        assertEquals(0, calendar.get(Calendar.MILLISECOND));
        assertEquals(0, calendar.get(Calendar.SECOND));
        assertEquals(11, calendar.get(Calendar.MINUTE));
        assertEquals(1, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(SimLifeCalendar.NOSDAY, calendar.get(Calendar.DAY_OF_WEEK));
        assertEquals(1, calendar.get(Calendar.DAY_OF_YEAR));

        calendar.add(Calendar.MINUTE, 19);
        assertEquals(1800000, calendar.getTimeInMillis());
        assertEquals(0, calendar.get(Calendar.MILLISECOND));
        assertEquals(0, calendar.get(Calendar.SECOND));
        assertEquals(10, calendar.get(Calendar.MINUTE));
        assertEquals(2, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(SimLifeCalendar.WATSDAY, calendar.get(Calendar.DAY_OF_WEEK));
        assertEquals(2, calendar.get(Calendar.DAY_OF_YEAR));

        calendar.add(Calendar.HOUR, 1);
        // no change
        assertEquals(1800000, calendar.getTimeInMillis());

        calendar.add(Calendar.HOUR_OF_DAY, 1);
        // no change
        assertEquals(1800000, calendar.getTimeInMillis());

        calendar.add(Calendar.DAY_OF_WEEK, 1);
        assertEquals(3000000, calendar.getTimeInMillis());
        assertEquals(0, calendar.get(Calendar.MILLISECOND));
        assertEquals(0, calendar.get(Calendar.SECOND));
        assertEquals(10, calendar.get(Calendar.MINUTE));
        assertEquals(3, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(SimLifeCalendar.STOODAY, calendar.get(Calendar.DAY_OF_WEEK));
        assertEquals(3, calendar.get(Calendar.DAY_OF_YEAR));

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        assertEquals(4200000, calendar.getTimeInMillis());
        assertEquals(0, calendar.get(Calendar.MILLISECOND));
        assertEquals(0, calendar.get(Calendar.SECOND));
        assertEquals(10, calendar.get(Calendar.MINUTE));
        assertEquals(4, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(SimLifeCalendar.FIRDAY, calendar.get(Calendar.DAY_OF_WEEK));
        assertEquals(4, calendar.get(Calendar.DAY_OF_YEAR));

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        assertEquals(5400000, calendar.getTimeInMillis());
        assertEquals(0, calendar.get(Calendar.MILLISECOND));
        assertEquals(0, calendar.get(Calendar.SECOND));
        assertEquals(10, calendar.get(Calendar.MINUTE));
        assertEquals(5, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(SimLifeCalendar.THUNSDAY, calendar.get(Calendar.DAY_OF_WEEK));
        assertEquals(5, calendar.get(Calendar.DAY_OF_YEAR));

        calendar.add(Calendar.DAY_OF_WEEK, 1);
        assertEquals(6600000, calendar.getTimeInMillis());
        assertEquals(SimLifeCalendar.WINDAY, calendar.get(Calendar.DAY_OF_WEEK));
        assertEquals(1, calendar.get(Calendar.WEEK_OF_MONTH));
        assertEquals(1, calendar.get(Calendar.WEEK_OF_YEAR));

        calendar.add(Calendar.DAY_OF_WEEK, 1);
        assertEquals(7800000, calendar.getTimeInMillis());
        assertEquals(SimLifeCalendar.NOSDAY, calendar.get(Calendar.DAY_OF_WEEK));
        assertEquals(2, calendar.get(Calendar.WEEK_OF_MONTH));
        assertEquals(2, calendar.get(Calendar.WEEK_OF_YEAR));

        calendar.add(Calendar.WEEK_OF_MONTH, 1);
        assertEquals(15000000, calendar.getTimeInMillis());
        assertEquals(SimLifeCalendar.NOSDAY, calendar.get(Calendar.DAY_OF_WEEK));
        assertEquals(3, calendar.get(Calendar.WEEK_OF_MONTH));
        assertEquals(3, calendar.get(Calendar.WEEK_OF_YEAR));
        assertEquals(1, calendar.get(Calendar.MONTH));

        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        assertEquals(22200000, calendar.getTimeInMillis());
        assertEquals(SimLifeCalendar.NOSDAY, calendar.get(Calendar.DAY_OF_WEEK));
        assertEquals(1, calendar.get(Calendar.WEEK_OF_MONTH));
        assertEquals(4, calendar.get(Calendar.WEEK_OF_YEAR));
        assertEquals(2, calendar.get(Calendar.MONTH));

        calendar.add(Calendar.MONTH, 1);
        assertEquals(43800000, calendar.getTimeInMillis());
        assertEquals(SimLifeCalendar.NOSDAY, calendar.get(Calendar.DAY_OF_WEEK));
        assertEquals(1, calendar.get(Calendar.WEEK_OF_MONTH));
        assertEquals(7, calendar.get(Calendar.WEEK_OF_YEAR));
        assertEquals(3, calendar.get(Calendar.MONTH));
        assertEquals(1, calendar.get(Calendar.YEAR));

        calendar.add(Calendar.MONTH, 2);
        assertEquals(87000000, calendar.getTimeInMillis());
        assertEquals(1, calendar.get(Calendar.WEEK_OF_MONTH));
        assertEquals(1, calendar.get(Calendar.WEEK_OF_YEAR));
        assertEquals(1, calendar.get(Calendar.MONTH));
        assertEquals(2, calendar.get(Calendar.YEAR));
        assertEquals(SimLifeCalendar.CURRENT_ERA, calendar.get(Calendar.ERA));

        calendar.add(Calendar.YEAR, 1);
        assertEquals(173400000, calendar.getTimeInMillis());
        assertEquals(1, calendar.get(Calendar.WEEK_OF_MONTH));
        assertEquals(1, calendar.get(Calendar.WEEK_OF_YEAR));
        assertEquals(1, calendar.get(Calendar.MONTH));
        assertEquals(3, calendar.get(Calendar.YEAR));
        assertEquals(SimLifeCalendar.CURRENT_ERA, calendar.get(Calendar.ERA));

        calendar.add(Calendar.ERA, 1);
        // no change
        assertEquals(3, calendar.get(Calendar.YEAR));
        assertEquals(SimLifeCalendar.CURRENT_ERA, calendar.get(Calendar.ERA));
    }

    public void testRollWithBasicConstructor() {
        internalTestRoll(new SimLifeCalendar());
    }

    public void testRollWithLongConstructor() {
        internalTestRoll(new SimLifeCalendar(0));
    }

    public void testRollWithCalendarConstructor() {
        SimLifeCalendar calendar = new SimLifeCalendar();
        internalTestRoll(new SimLifeCalendar(calendar));
    }

    public void testRollWithStateConstructor() {
        SimLifeCalendarState calendarState = new SimLifeCalendarState();
        internalTestRoll(new SimLifeCalendar(calendarState));
    }

    public void testRollWithStringConstructor() {
        internalTestRoll(new SimLifeCalendar("00:00:000 Nosday 01 Wim 0001"));
    }

    public void internalTestRoll(Calendar calendar) {
        assertEquals(0, calendar.getTimeInMillis());
        assertEquals(0, calendar.get(Calendar.MILLISECOND));
        assertEquals(0, calendar.get(Calendar.SECOND));

        calendar.roll(Calendar.MILLISECOND, 1);
        assertEquals(1, calendar.getTimeInMillis());
        assertEquals(1, calendar.get(Calendar.MILLISECOND));
        assertEquals(0, calendar.get(Calendar.SECOND));

        calendar.roll(Calendar.MILLISECOND, 499);
        assertEquals(500, calendar.getTimeInMillis());
        assertEquals(500, calendar.get(Calendar.MILLISECOND));
        assertEquals(0, calendar.get(Calendar.SECOND));

        calendar.roll(Calendar.MILLISECOND, 500);
        assertEquals(0, calendar.getTimeInMillis());
        assertEquals(0, calendar.get(Calendar.MILLISECOND));
        assertEquals(0, calendar.get(Calendar.SECOND));

        calendar.roll(Calendar.SECOND, 20);
        assertEquals(20000, calendar.getTimeInMillis());
        assertEquals(0, calendar.get(Calendar.MILLISECOND));
        assertEquals(20, calendar.get(Calendar.SECOND));

        calendar.roll(Calendar.SECOND, 40);
        assertEquals(0, calendar.getTimeInMillis());
        assertEquals(0, calendar.get(Calendar.MILLISECOND));
        assertEquals(0, calendar.get(Calendar.SECOND));
        assertEquals(0, calendar.get(Calendar.MINUTE));

        calendar.roll(Calendar.MINUTE, 10);
        assertEquals(600000, calendar.getTimeInMillis());
        assertEquals(0, calendar.get(Calendar.SECOND));
        assertEquals(10, calendar.get(Calendar.MINUTE));
        assertEquals(1, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(SimLifeCalendar.NOSDAY, calendar.get(Calendar.DAY_OF_WEEK));
        assertEquals(1, calendar.get(Calendar.DAY_OF_YEAR));

        calendar.roll(Calendar.MINUTE, 10);
        assertEquals(0, calendar.getTimeInMillis());
        assertEquals(0, calendar.get(Calendar.SECOND));
        assertEquals(0, calendar.get(Calendar.MINUTE));
        assertEquals(1, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(SimLifeCalendar.NOSDAY, calendar.get(Calendar.DAY_OF_WEEK));
        assertEquals(1, calendar.get(Calendar.DAY_OF_YEAR));

        calendar.roll(Calendar.HOUR, 1);
        // no change
        assertEquals(0, calendar.getTimeInMillis());

        calendar.roll(Calendar.HOUR_OF_DAY, 1);
        // no change
        assertEquals(0, calendar.getTimeInMillis());


        calendar = new SimLifeCalendar();
        calendar.clear(Calendar.MONTH);
        calendar.clear(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.WEEK_OF_YEAR, 1);
        calendar.set(Calendar.DAY_OF_WEEK, 1);
        calendar.roll(Calendar.DAY_OF_WEEK, 1);
        assertEquals(1200000, calendar.getTimeInMillis());
        assertEquals(0, calendar.get(Calendar.MINUTE));
        assertEquals(2, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(SimLifeCalendar.WATSDAY, calendar.get(Calendar.DAY_OF_WEEK));
        assertEquals(2, calendar.get(Calendar.DAY_OF_YEAR));

        calendar = new SimLifeCalendar();
        calendar.roll(Calendar.DAY_OF_MONTH, 1);
        assertEquals(1200000, calendar.getTimeInMillis());
        assertEquals(0, calendar.get(Calendar.MINUTE));
        assertEquals(2, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(SimLifeCalendar.WATSDAY, calendar.get(Calendar.DAY_OF_WEEK));
        assertEquals(2, calendar.get(Calendar.DAY_OF_YEAR));

        calendar = new SimLifeCalendar();
        calendar.clear(Calendar.MONTH);
        calendar.clear(Calendar.DAY_OF_MONTH);
        calendar.roll(Calendar.DAY_OF_YEAR, 1);
        assertEquals(1200000, calendar.getTimeInMillis());
        assertEquals(0, calendar.get(Calendar.MINUTE));
        assertEquals(2, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(SimLifeCalendar.WATSDAY, calendar.get(Calendar.DAY_OF_WEEK));
        assertEquals(2, calendar.get(Calendar.DAY_OF_YEAR));


        calendar = new SimLifeCalendar();
        calendar.clear(Calendar.MONTH);
        calendar.clear(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.WEEK_OF_YEAR, 1);
        calendar.roll(Calendar.DAY_OF_WEEK, 1);

        calendar.roll(Calendar.DAY_OF_WEEK, 1);
        assertEquals(2400000, calendar.getTimeInMillis());
        assertEquals(SimLifeCalendar.STOODAY, calendar.get(Calendar.DAY_OF_WEEK));
        assertEquals(1, calendar.get(Calendar.WEEK_OF_MONTH));
        assertEquals(1, calendar.get(Calendar.WEEK_OF_YEAR));

        calendar.roll(Calendar.DAY_OF_WEEK, 1);
        assertEquals(3600000, calendar.getTimeInMillis());
        assertEquals(SimLifeCalendar.FIRDAY, calendar.get(Calendar.DAY_OF_WEEK));
        assertEquals(1, calendar.get(Calendar.WEEK_OF_MONTH));
        assertEquals(1, calendar.get(Calendar.WEEK_OF_YEAR));

        calendar.roll(Calendar.DAY_OF_WEEK, 1);
        assertEquals(4800000, calendar.getTimeInMillis());
        assertEquals(SimLifeCalendar.THUNSDAY, calendar.get(Calendar.DAY_OF_WEEK));
        assertEquals(1, calendar.get(Calendar.WEEK_OF_MONTH));
        assertEquals(1, calendar.get(Calendar.WEEK_OF_YEAR));

        calendar.roll(Calendar.DAY_OF_WEEK, 1);
        assertEquals(6000000, calendar.getTimeInMillis());
        assertEquals(SimLifeCalendar.WINDAY, calendar.get(Calendar.DAY_OF_WEEK));
        assertEquals(1, calendar.get(Calendar.WEEK_OF_MONTH));
        assertEquals(1, calendar.get(Calendar.WEEK_OF_YEAR));

        calendar.roll(Calendar.DAY_OF_WEEK, 1);
        assertEquals(0, calendar.getTimeInMillis());
        assertEquals(SimLifeCalendar.NOSDAY, calendar.get(Calendar.DAY_OF_WEEK));
        assertEquals(1, calendar.get(Calendar.WEEK_OF_MONTH));
        assertEquals(1, calendar.get(Calendar.WEEK_OF_YEAR));


        calendar = new SimLifeCalendar();
        calendar.clear(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_WEEK, SimLifeCalendar.NOSDAY);
        calendar.roll(Calendar.WEEK_OF_MONTH, 1);
        assertEquals(7200000, calendar.getTimeInMillis());
        assertEquals(SimLifeCalendar.NOSDAY, calendar.get(Calendar.DAY_OF_WEEK));
        assertEquals(2, calendar.get(Calendar.WEEK_OF_MONTH));
        assertEquals(2, calendar.get(Calendar.WEEK_OF_YEAR));
        assertEquals(1, calendar.get(Calendar.MONTH));

        calendar = new SimLifeCalendar();
        calendar.clear(Calendar.MONTH);
        calendar.roll(Calendar.WEEK_OF_YEAR, 1);
        assertEquals(7200000, calendar.getTimeInMillis());
        assertEquals(SimLifeCalendar.NOSDAY, calendar.get(Calendar.DAY_OF_WEEK));
        assertEquals(2, calendar.get(Calendar.WEEK_OF_MONTH));
        assertEquals(2, calendar.get(Calendar.WEEK_OF_YEAR));
        assertEquals(1, calendar.get(Calendar.MONTH));

        calendar = new SimLifeCalendar();
        calendar.clear(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_WEEK, SimLifeCalendar.NOSDAY);
        calendar.roll(Calendar.WEEK_OF_MONTH, 1);
        calendar.roll(Calendar.WEEK_OF_MONTH, 1);
        assertEquals(14400000, calendar.getTimeInMillis());
        assertEquals(SimLifeCalendar.NOSDAY, calendar.get(Calendar.DAY_OF_WEEK));
        assertEquals(3, calendar.get(Calendar.WEEK_OF_MONTH));
        assertEquals(3, calendar.get(Calendar.WEEK_OF_YEAR));
        assertEquals(1, calendar.get(Calendar.MONTH));

        calendar.roll(Calendar.WEEK_OF_MONTH, 1);
        assertEquals(0, calendar.getTimeInMillis());
        assertEquals(SimLifeCalendar.NOSDAY, calendar.get(Calendar.DAY_OF_WEEK));
        assertEquals(1, calendar.get(Calendar.WEEK_OF_MONTH));
        assertEquals(1, calendar.get(Calendar.WEEK_OF_YEAR));
        assertEquals(1, calendar.get(Calendar.MONTH));


        calendar = new SimLifeCalendar();
        calendar.roll(Calendar.MONTH, 1);
        assertEquals(21600000, calendar.getTimeInMillis());
        assertEquals(SimLifeCalendar.NOSDAY, calendar.get(Calendar.DAY_OF_WEEK));
        assertEquals(1, calendar.get(Calendar.WEEK_OF_MONTH));
        assertEquals(4, calendar.get(Calendar.WEEK_OF_YEAR));
        assertEquals(2, calendar.get(Calendar.MONTH));
        assertEquals(1, calendar.get(Calendar.YEAR));

        calendar.roll(Calendar.MONTH, 3);
        assertEquals(0, calendar.getTimeInMillis());
        assertEquals(1, calendar.get(Calendar.WEEK_OF_MONTH));
        assertEquals(1, calendar.get(Calendar.WEEK_OF_YEAR));
        assertEquals(1, calendar.get(Calendar.MONTH));
        assertEquals(1, calendar.get(Calendar.YEAR));
        assertEquals(SimLifeCalendar.CURRENT_ERA, calendar.get(Calendar.ERA));


        calendar = new SimLifeCalendar();
        calendar.roll(Calendar.YEAR, 1);
        assertEquals(86400000, calendar.getTimeInMillis());
        assertEquals(1, calendar.get(Calendar.WEEK_OF_MONTH));
        assertEquals(1, calendar.get(Calendar.WEEK_OF_YEAR));
        assertEquals(1, calendar.get(Calendar.MONTH));
        assertEquals(2, calendar.get(Calendar.YEAR));
        assertEquals(SimLifeCalendar.CURRENT_ERA, calendar.get(Calendar.ERA));

        calendar.roll(Calendar.ERA, 1);
        // no change
        assertEquals(2, calendar.get(Calendar.YEAR));
        assertEquals(SimLifeCalendar.CURRENT_ERA, calendar.get(Calendar.ERA));
    }

    public void testGetMinimum() {
        SimLifeCalendar calendar = new SimLifeCalendar();
        assertEquals(SimLifeCalendar.CURRENT_ERA, calendar.getMinimum(SimLifeCalendar.ERA));
        assertEquals(1, calendar.getMinimum(SimLifeCalendar.YEAR));
        assertEquals(SimLifeCalendar.WIM, calendar.getMinimum(SimLifeCalendar.MONTH));
        assertEquals(1, calendar.getMinimum(SimLifeCalendar.WEEK_OF_YEAR));
        assertEquals(1, calendar.getMinimum(SimLifeCalendar.WEEK_OF_MONTH));
        assertEquals(1, calendar.getMinimum(SimLifeCalendar.DAY_OF_MONTH));
        assertEquals(1, calendar.getMinimum(SimLifeCalendar.DAY_OF_YEAR));
        assertEquals(SimLifeCalendar.NOSDAY, calendar.getMinimum(SimLifeCalendar.DAY_OF_WEEK));
        assertEquals(1, calendar.getMinimum(SimLifeCalendar.DAY_OF_WEEK_IN_MONTH));
        assertEquals(Calendar.AM, calendar.getMinimum(SimLifeCalendar.AM_PM));
        assertEquals(0, calendar.getMinimum(SimLifeCalendar.HOUR));
        assertEquals(0, calendar.getMinimum(SimLifeCalendar.HOUR_OF_DAY));
        assertEquals(0, calendar.getMinimum(SimLifeCalendar.MINUTE));
        assertEquals(0, calendar.getMinimum(SimLifeCalendar.SECOND));
        assertEquals(0, calendar.getMinimum(SimLifeCalendar.MILLISECOND));
        assertEquals(0, calendar.getMinimum(SimLifeCalendar.ZONE_OFFSET));
        assertEquals(0, calendar.getMinimum(SimLifeCalendar.DST_OFFSET));
    }

    public void testGetMaximum() {
        SimLifeCalendar calendar = new SimLifeCalendar();
        assertEquals(SimLifeCalendar.CURRENT_ERA, calendar.getMaximum(SimLifeCalendar.ERA));
        assertEquals(292269054, calendar.getMaximum(SimLifeCalendar.YEAR));
        assertEquals(SimLifeCalendar.TOM, calendar.getMaximum(SimLifeCalendar.MONTH));
        assertEquals(SimLifeCalendar.WEEK_PER_YEAR, calendar.getMaximum(SimLifeCalendar.WEEK_OF_YEAR));
        assertEquals(SimLifeCalendar.WEEK_PER_MONTH, calendar.getMaximum(SimLifeCalendar.WEEK_OF_MONTH));
        assertEquals(SimLifeCalendar.DAY_PER_MONTH, calendar.getMaximum(SimLifeCalendar.DAY_OF_MONTH));
        assertEquals(SimLifeCalendar.DAY_PER_YEAR, calendar.getMaximum(SimLifeCalendar.DAY_OF_YEAR));
        assertEquals(SimLifeCalendar.WINDAY, calendar.getMaximum(SimLifeCalendar.DAY_OF_WEEK));
        assertEquals(SimLifeCalendar.WEEK_PER_MONTH, calendar.getMaximum(SimLifeCalendar.DAY_OF_WEEK_IN_MONTH));
        assertEquals(Calendar.AM, calendar.getMaximum(SimLifeCalendar.AM_PM));
        assertEquals(0, calendar.getMaximum(SimLifeCalendar.HOUR));
        assertEquals(0, calendar.getMaximum(SimLifeCalendar.HOUR_OF_DAY));
        assertEquals(SimLifeCalendar.MINUTE_PER_DAY - 1, calendar.getMaximum(SimLifeCalendar.MINUTE));
        assertEquals(SimLifeCalendar.SECOND_PER_MINUTE - 1, calendar.getMaximum(SimLifeCalendar.SECOND));
        assertEquals(SimLifeCalendar.MS_PER_SECOND - 1, calendar.getMaximum(SimLifeCalendar.MILLISECOND));
        assertEquals(0, calendar.getMaximum(SimLifeCalendar.ZONE_OFFSET));
        assertEquals(0, calendar.getMaximum(SimLifeCalendar.DST_OFFSET));
    }

    public void testGetGreatestMinimum() {
        SimLifeCalendar calendar = new SimLifeCalendar();
        assertEquals(SimLifeCalendar.CURRENT_ERA, calendar.getGreatestMinimum(SimLifeCalendar.ERA));
        assertEquals(1, calendar.getGreatestMinimum(SimLifeCalendar.YEAR));
        assertEquals(SimLifeCalendar.WIM, calendar.getGreatestMinimum(SimLifeCalendar.MONTH));
        assertEquals(1, calendar.getGreatestMinimum(SimLifeCalendar.WEEK_OF_YEAR));
        assertEquals(1, calendar.getGreatestMinimum(SimLifeCalendar.WEEK_OF_MONTH));
        assertEquals(1, calendar.getGreatestMinimum(SimLifeCalendar.DAY_OF_MONTH));
        assertEquals(1, calendar.getGreatestMinimum(SimLifeCalendar.DAY_OF_YEAR));
        assertEquals(SimLifeCalendar.NOSDAY, calendar.getGreatestMinimum(SimLifeCalendar.DAY_OF_WEEK));
        assertEquals(1, calendar.getGreatestMinimum(SimLifeCalendar.DAY_OF_WEEK_IN_MONTH));
        assertEquals(Calendar.AM, calendar.getGreatestMinimum(SimLifeCalendar.AM_PM));
        assertEquals(0, calendar.getGreatestMinimum(SimLifeCalendar.HOUR));
        assertEquals(0, calendar.getGreatestMinimum(SimLifeCalendar.HOUR_OF_DAY));
        assertEquals(0, calendar.getGreatestMinimum(SimLifeCalendar.MINUTE));
        assertEquals(0, calendar.getGreatestMinimum(SimLifeCalendar.SECOND));
        assertEquals(0, calendar.getGreatestMinimum(SimLifeCalendar.MILLISECOND));
        assertEquals(0, calendar.getGreatestMinimum(SimLifeCalendar.ZONE_OFFSET));
        assertEquals(0, calendar.getGreatestMinimum(SimLifeCalendar.DST_OFFSET));
    }

    public void testGetLeastMaximum() {
        SimLifeCalendar calendar = new SimLifeCalendar();
        assertEquals(SimLifeCalendar.CURRENT_ERA, calendar.getLeastMaximum(SimLifeCalendar.ERA));
        assertEquals(292269054, calendar.getLeastMaximum(SimLifeCalendar.YEAR));
        assertEquals(SimLifeCalendar.TOM, calendar.getLeastMaximum(SimLifeCalendar.MONTH));
        assertEquals(SimLifeCalendar.WEEK_PER_YEAR, calendar.getLeastMaximum(SimLifeCalendar.WEEK_OF_YEAR));
        assertEquals(SimLifeCalendar.WEEK_PER_MONTH, calendar.getLeastMaximum(SimLifeCalendar.WEEK_OF_MONTH));
        assertEquals(SimLifeCalendar.DAY_PER_MONTH, calendar.getLeastMaximum(SimLifeCalendar.DAY_OF_MONTH));
        assertEquals(SimLifeCalendar.DAY_PER_YEAR, calendar.getLeastMaximum(SimLifeCalendar.DAY_OF_YEAR));
        assertEquals(SimLifeCalendar.WINDAY, calendar.getLeastMaximum(SimLifeCalendar.DAY_OF_WEEK));
        assertEquals(SimLifeCalendar.WEEK_PER_MONTH, calendar.getLeastMaximum(SimLifeCalendar.DAY_OF_WEEK_IN_MONTH));
        assertEquals(Calendar.AM, calendar.getLeastMaximum(SimLifeCalendar.AM_PM));
        assertEquals(0, calendar.getLeastMaximum(SimLifeCalendar.HOUR));
        assertEquals(0, calendar.getLeastMaximum(SimLifeCalendar.HOUR_OF_DAY));
        assertEquals(SimLifeCalendar.MINUTE_PER_DAY - 1, calendar.getLeastMaximum(SimLifeCalendar.MINUTE));
        assertEquals(SimLifeCalendar.SECOND_PER_MINUTE - 1, calendar.getLeastMaximum(SimLifeCalendar.SECOND));
        assertEquals(SimLifeCalendar.MS_PER_SECOND - 1, calendar.getLeastMaximum(SimLifeCalendar.MILLISECOND));
        assertEquals(0, calendar.getLeastMaximum(SimLifeCalendar.ZONE_OFFSET));
        assertEquals(0, calendar.getLeastMaximum(SimLifeCalendar.DST_OFFSET));
    }

    public void testFormatDate() {
        SimLifeCalendar calendar = new SimLifeCalendar();
        assertEquals("00:00:000 Nosday 01 Wim 0001", calendar.formatDate());

        calendar.set(Calendar.MILLISECOND, 999);
        assertEquals("00:00:999 Nosday 01 Wim 0001", calendar.formatDate());

        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 19);
        calendar.clear(Calendar.MONTH);
        calendar.clear(Calendar.DAY_OF_YEAR);
        calendar.set(Calendar.DAY_OF_WEEK, SimLifeCalendar.FIRDAY);
        assertEquals("19:59:999 Firday 04 Wim 0001", calendar.formatDate());

        calendar = new SimLifeCalendar();
        calendar.set(Calendar.DAY_OF_WEEK, SimLifeCalendar.STOODAY);
        calendar.set(Calendar.DAY_OF_MONTH, 15);
        assertEquals("00:00:000 Stooday 15 Wim 0001", calendar.formatDate());

        calendar.set(Calendar.MONTH, SimLifeCalendar.TOM);
        assertEquals("00:00:000 Stooday 15 Tom 0001", calendar.formatDate());

        calendar.set(Calendar.YEAR, 12);
        assertEquals("00:00:000 Stooday 15 Tom 0012", calendar.formatDate());

        calendar.set(Calendar.YEAR, 499);
        assertEquals("00:00:000 Stooday 15 Tom 0499", calendar.formatDate());

        calendar.set(Calendar.YEAR, 12345);
        assertEquals("00:00:000 Stooday 15 Tom 12345", calendar.formatDate());

        calendar.set(Calendar.YEAR, 123456789);
        assertEquals("00:00:000 Stooday 15 Tom 123456789", calendar.formatDate());

        calendar = new SimLifeCalendar();
        calendar.clear(Calendar.DAY_OF_MONTH);
        calendar.clear(Calendar.DAY_OF_YEAR);
        calendar.set(Calendar.DAY_OF_WEEK, SimLifeCalendar.WINDAY);
        assertEquals("00:00:000 Winday 06 Wim 0001", calendar.formatDate());
    }

    public void testSetTime() {
        SimLifeCalendar calendar = new SimLifeCalendar();
        calendar.setTime("19:59:000 Firday 04 Sprim 0003");
        assertEquals("19:59:000 Firday 04 Sprim 0003", calendar.formatDate());
        // = 2*86 400 000 + 1*21 600 000 + 3*1 200 000 + 19*60 000 + 59*1000
        // = 172 800 000 + 21 600 000 + 3 600 000 + 1 140 000 + 59 000
        // = 199 199 000
        assertEquals(199199000, calendar.getTimeInMillis());

        calendar.setTime("06:13:001 Winday 12 Tom 0001");
        assertEquals("06:13:001 Winday 12 Tom 0001", calendar.formatDate());
        // = 1 + 3*21 600 000 + 11*1 200 000 + 6*60 000 + 13*1000
        // = 1 + 64 800 000 + 13 200 000 + 360 000 + 13 000
        // = 78 373 001
        assertEquals(78373001, calendar.getTimeInMillis());
    }

    public void testGetState() {
        SimLifeCalendarState state = new SimLifeCalendarState(123);
        SimLifeCalendar calendar = new SimLifeCalendar(state);
        assertEquals(state, calendar.getState());
        assertSame(state, calendar.getState());
        assertEquals(123, calendar.getState().getValue());
        calendar.add(Calendar.MILLISECOND, 1);
        assertEquals(state, calendar.getState());
        assertSame(state, calendar.getState());
        assertEquals(124, calendar.getState().getValue());
    }

    public void testPublisherAdd() {
        SimLifeCalendar calendar = new SimLifeCalendar();
        PublisherTestHelper publisherHelper = new PublisherTestHelper();
        publisherHelper.addSubscriber(calendar);
        calendar.add(Calendar.MILLISECOND, 1);
        assertEquals(1, publisherHelper.nbUpdated());
        assertEquals(1, publisherHelper.getUpdateObjects().size());
        assertEquals(null, publisherHelper.getUpdateObjects().get(0));

        publisherHelper.reset();
        calendar.add(Calendar.MILLISECOND, 1);
        assertEquals(1, publisherHelper.nbUpdated());
        assertEquals(1, publisherHelper.getUpdateObjects().size());
        assertEquals(null, publisherHelper.getUpdateObjects().get(0));

        publisherHelper.reset();
        calendar.add(Calendar.SECOND, 53);
        assertEquals(1, publisherHelper.nbUpdated());
        assertEquals(1, publisherHelper.getUpdateObjects().size());
        assertEquals(null, publisherHelper.getUpdateObjects().get(0));

        publisherHelper.reset();
        calendar.getTimeInMillis();
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());
    }

    public void testPublisherRoll() {
        SimLifeCalendar calendar = new SimLifeCalendar();
        PublisherTestHelper publisherHelper = new PublisherTestHelper();
        publisherHelper.addSubscriber(calendar);
        calendar.roll(Calendar.MILLISECOND, 1);
        assertEquals(1, publisherHelper.nbUpdated());
        assertEquals(1, publisherHelper.getUpdateObjects().size());
        assertEquals(null, publisherHelper.getUpdateObjects().get(0));

        publisherHelper.reset();
        calendar.roll(Calendar.MILLISECOND, 1);
        assertEquals(1, publisherHelper.nbUpdated());
        assertEquals(1, publisherHelper.getUpdateObjects().size());
        assertEquals(null, publisherHelper.getUpdateObjects().get(0));

        publisherHelper.reset();
        calendar.roll(Calendar.SECOND, 53);
        assertEquals(1, publisherHelper.nbUpdated());
        assertEquals(1, publisherHelper.getUpdateObjects().size());
        assertEquals(null, publisherHelper.getUpdateObjects().get(0));

        publisherHelper.reset();
        calendar.getTimeInMillis();
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());
    }

    public void testPublisherSet() {
        SimLifeCalendar calendar = new SimLifeCalendar();
        PublisherTestHelper publisherHelper = new PublisherTestHelper();
        publisherHelper.addSubscriber(calendar);
        calendar.set(Calendar.MILLISECOND, 1);
        assertEquals(1, publisherHelper.nbUpdated());
        assertEquals(1, publisherHelper.getUpdateObjects().size());
        assertEquals(null, publisherHelper.getUpdateObjects().get(0));

        publisherHelper.reset();
        calendar.set(Calendar.MILLISECOND, 1);
        assertEquals(1, publisherHelper.nbUpdated());
        assertEquals(1, publisherHelper.getUpdateObjects().size());
        assertEquals(null, publisherHelper.getUpdateObjects().get(0));

        publisherHelper.reset();
        calendar.set(Calendar.SECOND, 53);
        assertEquals(1, publisherHelper.nbUpdated());
        assertEquals(1, publisherHelper.getUpdateObjects().size());
        assertEquals(null, publisherHelper.getUpdateObjects().get(0));

        publisherHelper.reset();
        calendar.getTimeInMillis();
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());
    }

}
