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

import junit.framework.TestCase;
import barsuift.simLife.message.PublisherTestHelper;


public class SimLifeDateTest extends TestCase {

    public void testConstructorString() throws Exception {
        SimLifeDate date = new SimLifeDate("19:59:999 Firday 04 Sprim 0003");
        assertEquals("19:59:999 Firday 04 Sprim 0003", date.formatDate());
        // = 999 + 2*86 400 000 + 1*21 600 000 + 3*1 200 000 + 19*60 000 + 59*1000
        // = 999 + 172 800 000 + 21 600 000 + 3 600 000 + 1 140 000 + 59 000
        // = 199 199 999
        assertEquals(199199999, date.getTimeInMillis());

        date = new SimLifeDate("06:13:158 Winday 12 Tom 0001");
        assertEquals("06:13:158 Winday 12 Tom 0001", date.formatDate());
        // = 158 + 3*21 600 000 + 11*1 200 000 + 6*60 000 + 13*1000
        // = 158 + 64 800 000 + 13 200 000 + 360 000 + 13 000
        // = 78 373 158
        assertEquals(78373158, date.getTimeInMillis());
    }

    public void testConstructorCopy() {
        SimLifeDate date = new SimLifeDate();
        date.setTimeInMillis(199199158);
        SimLifeDate cal2 = new SimLifeDate(date);
        assertEquals(date, cal2);
        assertEquals(199199158, cal2.getTimeInMillis());
    }

    public void testConstructorLong() {
        SimLifeDate date = new SimLifeDate(199199000);
        assertEquals(199199000, date.getTimeInMillis());
        assertEquals("19:59:000 Firday 04 Sprim 0003", date.formatDate());

        // same with 1 additional second and 1 millisecond
        date = new SimLifeDate(199200001);
        assertEquals(199200001, date.getTimeInMillis());
        assertEquals("00:00:001 Thunsday 05 Sprim 0003", date.formatDate());

        date = new SimLifeDate(78373000);
        assertEquals(78373000, date.getTimeInMillis());
        assertEquals("06:13:000 Winday 12 Tom 0001", date.formatDate());
    }

    public void testConstructorState() {
        SimLifeDateState dateState = new SimLifeDateState();
        dateState.setValue(199199000);
        SimLifeDate date = new SimLifeDate(dateState);
        assertEquals(199199000, date.getTimeInMillis());
        try {
            new SimLifeDate((SimLifeDateState) null);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    public void testAddWithBasicConstructor() {
        internalTestAdd(new SimLifeDate());
    }

    public void testAddWithLongConstructor() {
        internalTestAdd(new SimLifeDate(0));
    }

    public void testAddWithSimLifeDateConstructor() {
        SimLifeDate date = new SimLifeDate();
        internalTestAdd(new SimLifeDate(date));
    }

    public void testAddWithStateConstructor() {
        SimLifeDateState dateState = new SimLifeDateState();
        internalTestAdd(new SimLifeDate(dateState));
    }

    public void testAddWithStringConstructor() throws Exception {
        internalTestAdd(new SimLifeDate("00:00:000 Nosday 01 Wim 0001"));
    }

    public void internalTestAdd(SimLifeDate date) {
        assertEquals(0, date.getTimeInMillis());
        assertEquals(0, date.getMillisOfSecond());
        assertEquals(0, date.getSecondOfMinute());

        date.addMillis(1);
        assertEquals(1, date.getTimeInMillis());
        assertEquals(1, date.getMillisOfSecond());
        assertEquals(0, date.getSecondOfMinute());

        date.addMillis(499);
        assertEquals(500, date.getTimeInMillis());
        assertEquals(500, date.getMillisOfSecond());
        assertEquals(0, date.getSecondOfMinute());

        date.addMillis(500);
        assertEquals(1000, date.getTimeInMillis());
        assertEquals(0, date.getMillisOfSecond());
        assertEquals(1, date.getSecondOfMinute());

        date.addSeconds(19);
        assertEquals(20000, date.getTimeInMillis());
        assertEquals(0, date.getMillisOfSecond());
        assertEquals(20, date.getSecondOfMinute());

        date.addSeconds(40);
        assertEquals(60000, date.getTimeInMillis());
        assertEquals(0, date.getMillisOfSecond());
        assertEquals(0, date.getSecondOfMinute());
        assertEquals(1, date.getMinuteOfDay());

        date.addMinutes(10);
        assertEquals(660000, date.getTimeInMillis());
        assertEquals(0, date.getMillisOfSecond());
        assertEquals(0, date.getSecondOfMinute());
        assertEquals(11, date.getMinuteOfDay());
        assertEquals(1, date.getDayOfMonth());
        assertEquals(Day.NOSDAY, date.getDayOfWeek());

        date.addMinutes(19);
        assertEquals(1800000, date.getTimeInMillis());
        assertEquals(0, date.getMillisOfSecond());
        assertEquals(0, date.getSecondOfMinute());
        assertEquals(10, date.getMinuteOfDay());
        assertEquals(2, date.getDayOfMonth());
        assertEquals(Day.WATSDAY, date.getDayOfWeek());

        date.addDays(1);
        assertEquals(3000000, date.getTimeInMillis());
        assertEquals(0, date.getMillisOfSecond());
        assertEquals(0, date.getSecondOfMinute());
        assertEquals(10, date.getMinuteOfDay());
        assertEquals(3, date.getDayOfMonth());
        assertEquals(Day.STOODAY, date.getDayOfWeek());

        date.addDays(1);
        assertEquals(4200000, date.getTimeInMillis());
        assertEquals(0, date.getMillisOfSecond());
        assertEquals(0, date.getSecondOfMinute());
        assertEquals(10, date.getMinuteOfDay());
        assertEquals(4, date.getDayOfMonth());
        assertEquals(Day.FIRDAY, date.getDayOfWeek());

        date.addDays(1);
        assertEquals(5400000, date.getTimeInMillis());
        assertEquals(0, date.getMillisOfSecond());
        assertEquals(0, date.getSecondOfMinute());
        assertEquals(10, date.getMinuteOfDay());
        assertEquals(5, date.getDayOfMonth());
        assertEquals(Day.THUNSDAY, date.getDayOfWeek());

        date.addDays(1);
        assertEquals(6600000, date.getTimeInMillis());
        assertEquals(Day.WINDAY, date.getDayOfWeek());
        assertEquals(1, date.getWeekOfMonth());

        date.addDays(1);
        assertEquals(7800000, date.getTimeInMillis());
        assertEquals(Day.NOSDAY, date.getDayOfWeek());
        assertEquals(2, date.getWeekOfMonth());

        date.addWeeks(1);
        assertEquals(15000000, date.getTimeInMillis());
        assertEquals(Day.NOSDAY, date.getDayOfWeek());
        assertEquals(3, date.getWeekOfMonth());
        assertEquals(Month.WIM, date.getMonthOfYear());

        date.addWeeks(1);
        assertEquals(22200000, date.getTimeInMillis());
        assertEquals(Day.NOSDAY, date.getDayOfWeek());
        assertEquals(1, date.getWeekOfMonth());
        assertEquals(Month.SPRIM, date.getMonthOfYear());

        date.addMonths(1);
        assertEquals(43800000, date.getTimeInMillis());
        assertEquals(Day.NOSDAY, date.getDayOfWeek());
        assertEquals(1, date.getWeekOfMonth());
        assertEquals(Month.SUM, date.getMonthOfYear());
        assertEquals(1, date.getYear());

        date.addMonths(2);
        assertEquals(87000000, date.getTimeInMillis());
        assertEquals(1, date.getWeekOfMonth());
        assertEquals(Month.WIM, date.getMonthOfYear());
        assertEquals(2, date.getYear());

        date.addYears(1);
        assertEquals(173400000, date.getTimeInMillis());
        assertEquals(1, date.getWeekOfMonth());
        assertEquals(Month.WIM, date.getMonthOfYear());
        assertEquals(3, date.getYear());
    }

    public void testFormatDate() {
        SimLifeDate date = new SimLifeDate();
        assertEquals("00:00:000 Nosday 01 Wim 0001", date.formatDate());

        date.setMillisOfSecond(999);
        assertEquals("00:00:999 Nosday 01 Wim 0001", date.formatDate());

        date.setSecondOfMinute(59);
        date.setMinuteOfDay(19);
        date.setDayOfWeek(Day.FIRDAY);
        assertEquals("19:59:999 Firday 04 Wim 0001", date.formatDate());

        date = new SimLifeDate();
        date.setDayOfMonth(15);
        assertEquals("00:00:000 Stooday 15 Wim 0001", date.formatDate());

        date.setMonthOfYear(Month.TOM);
        assertEquals("00:00:000 Stooday 15 Tom 0001", date.formatDate());

        date.setYear(12);
        assertEquals("00:00:000 Stooday 15 Tom 0012", date.formatDate());

        date.setYear(499);
        assertEquals("00:00:000 Stooday 15 Tom 0499", date.formatDate());

        date.setYear(12345);
        assertEquals("00:00:000 Stooday 15 Tom 12345", date.formatDate());

        date.setYear(123456789);
        assertEquals("00:00:000 Stooday 15 Tom 123456789", date.formatDate());

        date = new SimLifeDate();
        date.setDayOfWeek(Day.WINDAY);
        assertEquals("00:00:000 Winday 06 Wim 0001", date.formatDate());
    }

    public void testDayOfMonth() {
        SimLifeDate date = new SimLifeDate();
        date.setDayOfMonth(1);
        assertEquals(1, date.getDayOfMonth());
        date.setDayOfMonth(2);
        assertEquals(2, date.getDayOfMonth());
        date.setDayOfMonth(3);
        assertEquals(3, date.getDayOfMonth());
        date.setDayOfMonth(4);
        assertEquals(4, date.getDayOfMonth());
        date.setDayOfMonth(5);
        assertEquals(5, date.getDayOfMonth());
        date.setDayOfMonth(6);
        assertEquals(6, date.getDayOfMonth());
        date.setDayOfMonth(7);
        assertEquals(7, date.getDayOfMonth());
        date.setDayOfMonth(8);
        assertEquals(8, date.getDayOfMonth());
        date.setDayOfMonth(12);
        assertEquals(12, date.getDayOfMonth());
        date.setDayOfMonth(13);
        assertEquals(13, date.getDayOfMonth());
        date.setDayOfMonth(17);
        assertEquals(17, date.getDayOfMonth());
        date.setDayOfMonth(18);
        assertEquals(18, date.getDayOfMonth());
    }

    public void testSetTime() throws Exception {
        SimLifeDate date = new SimLifeDate();
        date.setTime("19:59:000 Firday 04 Sprim 0003");
        assertEquals("19:59:000 Firday 04 Sprim 0003", date.formatDate());
        // = 2*86 400 000 + 1*21 600 000 + 3*1 200 000 + 19*60 000 + 59*1000
        // = 172 800 000 + 21 600 000 + 3 600 000 + 1 140 000 + 59 000
        // = 199 199 000
        assertEquals(199199000, date.getTimeInMillis());

        date.setTime("06:13:001 Winday 12 Tom 0001");
        assertEquals("06:13:001 Winday 12 Tom 0001", date.formatDate());
        // = 1 + 3*21 600 000 + 11*1 200 000 + 6*60 000 + 13*1000
        // = 1 + 64 800 000 + 13 200 000 + 360 000 + 13 000
        // = 78 373 001
        assertEquals(78373001, date.getTimeInMillis());
    }

    public void testGetState() {
        SimLifeDateState state = new SimLifeDateState(123);
        SimLifeDate date = new SimLifeDate(state);
        assertEquals(state, date.getState());
        assertSame(state, date.getState());
        assertEquals(123, date.getState().getValue());
        date.addMillis(1);
        assertEquals(state, date.getState());
        assertSame(state, date.getState());
        assertEquals(124, date.getState().getValue());
    }

    public void testPublisherAdd() {
        SimLifeDate date = new SimLifeDate();
        PublisherTestHelper publisherHelper = new PublisherTestHelper();
        publisherHelper.addSubscriberTo(date);

        date.addMillis(1);
        assertEquals(1, publisherHelper.nbUpdated());
        assertEquals(1, publisherHelper.getUpdateObjects().size());
        assertEquals(null, publisherHelper.getUpdateObjects().get(0));

        publisherHelper.reset();
        date.addMillis(1);
        assertEquals(1, publisherHelper.nbUpdated());
        assertEquals(1, publisherHelper.getUpdateObjects().size());
        assertEquals(null, publisherHelper.getUpdateObjects().get(0));

        publisherHelper.reset();
        date.addSeconds(53);
        assertEquals(1, publisherHelper.nbUpdated());
        assertEquals(1, publisherHelper.getUpdateObjects().size());
        assertEquals(null, publisherHelper.getUpdateObjects().get(0));

        publisherHelper.reset();
        date.getTimeInMillis();
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());

        publisherHelper.reset();
        date.formatDate();
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());
    }

    public void testPublisherSet() {
        SimLifeDate date = new SimLifeDate();
        PublisherTestHelper publisherHelper = new PublisherTestHelper();
        publisherHelper.addSubscriberTo(date);

        date.setMillisOfSecond(1);
        assertEquals(1, publisherHelper.nbUpdated());
        assertEquals(1, publisherHelper.getUpdateObjects().size());
        assertEquals(null, publisherHelper.getUpdateObjects().get(0));

        publisherHelper.reset();
        date.setMillisOfSecond(1);
        assertEquals(1, publisherHelper.nbUpdated());
        assertEquals(1, publisherHelper.getUpdateObjects().size());
        assertEquals(null, publisherHelper.getUpdateObjects().get(0));

        publisherHelper.reset();
        date.setSecondOfMinute(53);
        assertEquals(1, publisherHelper.nbUpdated());
        assertEquals(1, publisherHelper.getUpdateObjects().size());
        assertEquals(null, publisherHelper.getUpdateObjects().get(0));

        publisherHelper.reset();
        date.set(999, 19, 59, Day.NOSDAY, 3, Month.TOM, 99);
        assertEquals(1, publisherHelper.nbUpdated());
        assertEquals(1, publisherHelper.getUpdateObjects().size());
        assertEquals(null, publisherHelper.getUpdateObjects().get(0));

        publisherHelper.reset();
        date.getTimeInMillis();
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());

        publisherHelper.reset();
        date.formatDate();
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());
    }

}
