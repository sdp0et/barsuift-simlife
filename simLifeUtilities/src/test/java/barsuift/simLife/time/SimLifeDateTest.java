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

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import barsuift.simLife.message.PublisherTestHelper;

import static org.fest.assertions.Assertions.assertThat;


public class SimLifeDateTest {

    @Test
    public void constructorString() throws Exception {
        SimLifeDate date = new SimLifeDate("19:59:999 Firday 04 Sprim 0003");
        assertThat( date.formatDate()).isEqualTo("19:59:999 Firday 04 Sprim 0003");
        // = 999 + 2*86 400 000 + 1*21 600 000 + 3*1 200 000 + 19*60 000 + 59*1000
        // = 999 + 172 800 000 + 21 600 000 + 3 600 000 + 1 140 000 + 59 000
        // = 199 199 999
        assertThat( date.getTimeInMillis()).isEqualTo(199199999);

        date = new SimLifeDate("06:13:158 Winday 12 Tom 0001");
        assertThat( date.formatDate()).isEqualTo("06:13:158 Winday 12 Tom 0001");
        // = 158 + 3*21 600 000 + 11*1 200 000 + 6*60 000 + 13*1000
        // = 158 + 64 800 000 + 13 200 000 + 360 000 + 13 000
        // = 78 373 158
        assertThat( date.getTimeInMillis()).isEqualTo(78373158);
    }

    @Test
    public void constructorCopy() {
        SimLifeDate date = new SimLifeDate();
        date.setTimeInMillis(199199158);
        SimLifeDate cal2 = new SimLifeDate(date);
        assertThat( cal2).isEqualTo(date);
        assertThat( cal2.getTimeInMillis()).isEqualTo(199199158);
    }

    @Test
    public void constructorLong() {
        SimLifeDate date = new SimLifeDate(199199000);
        assertThat( date.getTimeInMillis()).isEqualTo(199199000);
        assertThat( date.formatDate()).isEqualTo("19:59:000 Firday 04 Sprim 0003");

        // same with 1 additional second and 1 millisecond
        date = new SimLifeDate(199200001);
        assertThat( date.getTimeInMillis()).isEqualTo(199200001);
        assertThat( date.formatDate()).isEqualTo("00:00:001 Thunsday 05 Sprim 0003");

        date = new SimLifeDate(78373000);
        assertThat( date.getTimeInMillis()).isEqualTo(78373000);
        assertThat( date.formatDate()).isEqualTo("06:13:000 Winday 12 Tom 0001");
    }

    @Test
    public void constructorState() {
        SimLifeDateState dateState = new SimLifeDateState();
        dateState.setValue(199199000);
        SimLifeDate date = new SimLifeDate(dateState);
        assertThat( date.getTimeInMillis()).isEqualTo(199199000);
        try {
            new SimLifeDate((SimLifeDateState) null);
            Assert.fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    @Test
    public void addWithBasicConstructor() {
        internalTestAdd(new SimLifeDate());
    }

    @Test
    public void addWithLongConstructor() {
        internalTestAdd(new SimLifeDate(0));
    }

    @Test
    public void addWithSimLifeDateConstructor() {
        SimLifeDate date = new SimLifeDate();
        internalTestAdd(new SimLifeDate(date));
    }

    @Test
    public void addWithStateConstructor() {
        SimLifeDateState dateState = new SimLifeDateState();
        internalTestAdd(new SimLifeDate(dateState));
    }

    @Test
    public void addWithStringConstructor() throws Exception {
        internalTestAdd(new SimLifeDate("00:00:000 Nosday 01 Wim 0001"));
    }

    public void internalTestAdd(SimLifeDate date) {
        assertThat( date.getTimeInMillis()).isEqualTo(0);
        assertThat( date.getMillisOfSecond()).isEqualTo(0);
        assertThat( date.getSecondOfMinute()).isEqualTo(0);

        date.addMillis(1);
        assertThat( date.getTimeInMillis()).isEqualTo(1);
        assertThat( date.getMillisOfSecond()).isEqualTo(1);
        assertThat( date.getSecondOfMinute()).isEqualTo(0);

        date.addMillis(499);
        assertThat( date.getTimeInMillis()).isEqualTo(500);
        assertThat( date.getMillisOfSecond()).isEqualTo(500);
        assertThat( date.getSecondOfMinute()).isEqualTo(0);

        date.addMillis(500);
        assertThat( date.getTimeInMillis()).isEqualTo(1000);
        assertThat( date.getMillisOfSecond()).isEqualTo(0);
        assertThat( date.getSecondOfMinute()).isEqualTo(1);

        date.addSeconds(19);
        assertThat( date.getTimeInMillis()).isEqualTo(20000);
        assertThat( date.getMillisOfSecond()).isEqualTo(0);
        assertThat( date.getSecondOfMinute()).isEqualTo(20);

        date.addSeconds(40);
        assertThat( date.getTimeInMillis()).isEqualTo(60000);
        assertThat( date.getMillisOfSecond()).isEqualTo(0);
        assertThat( date.getSecondOfMinute()).isEqualTo(0);
        assertThat( date.getMinuteOfDay()).isEqualTo(1);

        date.addMinutes(10);
        assertThat( date.getTimeInMillis()).isEqualTo(660000);
        assertThat( date.getMillisOfSecond()).isEqualTo(0);
        assertThat( date.getSecondOfMinute()).isEqualTo(0);
        assertThat( date.getMinuteOfDay()).isEqualTo(11);
        assertThat( date.getDayOfMonth()).isEqualTo(1);
        assertThat( date.getDayOfWeek()).isEqualTo(Day.NOSDAY);

        date.addMinutes(19);
        assertThat( date.getTimeInMillis()).isEqualTo(1800000);
        assertThat( date.getMillisOfSecond()).isEqualTo(0);
        assertThat( date.getSecondOfMinute()).isEqualTo(0);
        assertThat( date.getMinuteOfDay()).isEqualTo(10);
        assertThat( date.getDayOfMonth()).isEqualTo(2);
        assertThat( date.getDayOfWeek()).isEqualTo(Day.WATSDAY);

        date.addDays(1);
        assertThat( date.getTimeInMillis()).isEqualTo(3000000);
        assertThat( date.getMillisOfSecond()).isEqualTo(0);
        assertThat( date.getSecondOfMinute()).isEqualTo(0);
        assertThat( date.getMinuteOfDay()).isEqualTo(10);
        assertThat( date.getDayOfMonth()).isEqualTo(3);
        assertThat( date.getDayOfWeek()).isEqualTo(Day.STOODAY);

        date.addDays(1);
        assertThat( date.getTimeInMillis()).isEqualTo(4200000);
        assertThat( date.getMillisOfSecond()).isEqualTo(0);
        assertThat( date.getSecondOfMinute()).isEqualTo(0);
        assertThat( date.getMinuteOfDay()).isEqualTo(10);
        assertThat( date.getDayOfMonth()).isEqualTo(4);
        assertThat( date.getDayOfWeek()).isEqualTo(Day.FIRDAY);

        date.addDays(1);
        assertThat( date.getTimeInMillis()).isEqualTo(5400000);
        assertThat( date.getMillisOfSecond()).isEqualTo(0);
        assertThat( date.getSecondOfMinute()).isEqualTo(0);
        assertThat( date.getMinuteOfDay()).isEqualTo(10);
        assertThat( date.getDayOfMonth()).isEqualTo(5);
        assertThat( date.getDayOfWeek()).isEqualTo(Day.THUNSDAY);

        date.addDays(1);
        assertThat( date.getTimeInMillis()).isEqualTo(6600000);
        assertThat( date.getDayOfWeek()).isEqualTo(Day.WINDAY);
        assertThat( date.getWeekOfMonth()).isEqualTo(1);

        date.addDays(1);
        assertThat( date.getTimeInMillis()).isEqualTo(7800000);
        assertThat( date.getDayOfWeek()).isEqualTo(Day.NOSDAY);
        assertThat( date.getWeekOfMonth()).isEqualTo(2);

        date.addWeeks(1);
        assertThat( date.getTimeInMillis()).isEqualTo(15000000);
        assertThat( date.getDayOfWeek()).isEqualTo(Day.NOSDAY);
        assertThat( date.getWeekOfMonth()).isEqualTo(3);
        assertThat( date.getMonthOfYear()).isEqualTo(Month.WIM);

        date.addWeeks(1);
        assertThat( date.getTimeInMillis()).isEqualTo(22200000);
        assertThat( date.getDayOfWeek()).isEqualTo(Day.NOSDAY);
        assertThat( date.getWeekOfMonth()).isEqualTo(1);
        assertThat( date.getMonthOfYear()).isEqualTo(Month.SPRIM);

        date.addMonths(1);
        assertThat( date.getTimeInMillis()).isEqualTo(43800000);
        assertThat( date.getDayOfWeek()).isEqualTo(Day.NOSDAY);
        assertThat( date.getWeekOfMonth()).isEqualTo(1);
        assertThat( date.getMonthOfYear()).isEqualTo(Month.SUM);
        assertThat( date.getYear()).isEqualTo(1);

        date.addMonths(2);
        assertThat( date.getTimeInMillis()).isEqualTo(87000000);
        assertThat( date.getWeekOfMonth()).isEqualTo(1);
        assertThat( date.getMonthOfYear()).isEqualTo(Month.WIM);
        assertThat( date.getYear()).isEqualTo(2);

        date.addYears(1);
        assertThat( date.getTimeInMillis()).isEqualTo(173400000);
        assertThat( date.getWeekOfMonth()).isEqualTo(1);
        assertThat( date.getMonthOfYear()).isEqualTo(Month.WIM);
        assertThat( date.getYear()).isEqualTo(3);
    }

    @Test
    public void formatDate() {
        SimLifeDate date = new SimLifeDate();
        assertThat( date.formatDate()).isEqualTo("00:00:000 Nosday 01 Wim 0001");

        date.setMillisOfSecond(999);
        assertThat( date.formatDate()).isEqualTo("00:00:999 Nosday 01 Wim 0001");

        date.setSecondOfMinute(59);
        date.setMinuteOfDay(19);
        date.setDayOfWeek(Day.FIRDAY);
        assertThat( date.formatDate()).isEqualTo("19:59:999 Firday 04 Wim 0001");

        date = new SimLifeDate();
        date.setDayOfMonth(15);
        assertThat( date.formatDate()).isEqualTo("00:00:000 Stooday 15 Wim 0001");

        date.setMonthOfYear(Month.TOM);
        assertThat( date.formatDate()).isEqualTo("00:00:000 Stooday 15 Tom 0001");

        date.setYear(12);
        assertThat( date.formatDate()).isEqualTo("00:00:000 Stooday 15 Tom 0012");

        date.setYear(499);
        assertThat( date.formatDate()).isEqualTo("00:00:000 Stooday 15 Tom 0499");

        date.setYear(12345);
        assertThat( date.formatDate()).isEqualTo("00:00:000 Stooday 15 Tom 12345");

        date.setYear(123456789);
        assertThat( date.formatDate()).isEqualTo("00:00:000 Stooday 15 Tom 123456789");

        date = new SimLifeDate();
        date.setDayOfWeek(Day.WINDAY);
        assertThat( date.formatDate()).isEqualTo("00:00:000 Winday 06 Wim 0001");
    }

    @Test
    public void dayOfMonth() {
        SimLifeDate date = new SimLifeDate();
        date.setDayOfMonth(1);
        assertThat( date.getDayOfMonth()).isEqualTo(1);
        date.setDayOfMonth(2);
        assertThat( date.getDayOfMonth()).isEqualTo(2);
        date.setDayOfMonth(3);
        assertThat( date.getDayOfMonth()).isEqualTo(3);
        date.setDayOfMonth(4);
        assertThat( date.getDayOfMonth()).isEqualTo(4);
        date.setDayOfMonth(5);
        assertThat( date.getDayOfMonth()).isEqualTo(5);
        date.setDayOfMonth(6);
        assertThat( date.getDayOfMonth()).isEqualTo(6);
        date.setDayOfMonth(7);
        assertThat( date.getDayOfMonth()).isEqualTo(7);
        date.setDayOfMonth(8);
        assertThat( date.getDayOfMonth()).isEqualTo(8);
        date.setDayOfMonth(12);
        assertThat( date.getDayOfMonth()).isEqualTo(12);
        date.setDayOfMonth(13);
        assertThat( date.getDayOfMonth()).isEqualTo(13);
        date.setDayOfMonth(17);
        assertThat( date.getDayOfMonth()).isEqualTo(17);
        date.setDayOfMonth(18);
        assertThat( date.getDayOfMonth()).isEqualTo(18);
    }

    @Test
    public void setTime() throws Exception {
        SimLifeDate date = new SimLifeDate();
        date.setTime("19:59:000 Firday 04 Sprim 0003");
        assertThat( date.formatDate()).isEqualTo("19:59:000 Firday 04 Sprim 0003");
        // = 2*86 400 000 + 1*21 600 000 + 3*1 200 000 + 19*60 000 + 59*1000
        // = 172 800 000 + 21 600 000 + 3 600 000 + 1 140 000 + 59 000
        // = 199 199 000
        assertThat( date.getTimeInMillis()).isEqualTo(199199000);

        date.setTime("06:13:001 Winday 12 Tom 0001");
        assertThat( date.formatDate()).isEqualTo("06:13:001 Winday 12 Tom 0001");
        // = 1 + 3*21 600 000 + 11*1 200 000 + 6*60 000 + 13*1000
        // = 1 + 64 800 000 + 13 200 000 + 360 000 + 13 000
        // = 78 373 001
        assertThat( date.getTimeInMillis()).isEqualTo(78373001);
    }

    @Test
    public void getState() {
        SimLifeDateState state = new SimLifeDateState(123);
        SimLifeDate date = new SimLifeDate(state);
        assertThat( date.getState()).isEqualTo(state);
        assertThat(date.getState()).isSameAs(state);
        assertThat( date.getState().getValue()).isEqualTo(123);
        date.addMillis(1);
        assertThat( date.getState()).isEqualTo(state);
        assertThat(date.getState()).isSameAs(state);
        assertThat( date.getState().getValue()).isEqualTo(124);
    }

    @Test
    public void publisherAdd() {
        SimLifeDate date = new SimLifeDate();
        PublisherTestHelper publisherHelper = new PublisherTestHelper();
        publisherHelper.addSubscriberTo(date);

        date.addMillis(1);
        assertThat( publisherHelper.nbUpdated()).isEqualTo(1);
        assertThat( publisherHelper.getUpdateObjects().size()).isEqualTo(1);
        assertThat( publisherHelper.getUpdateObjects().get(0)).isEqualTo(null);

        publisherHelper.reset();
        date.addMillis(1);
        assertThat( publisherHelper.nbUpdated()).isEqualTo(1);
        assertThat( publisherHelper.getUpdateObjects().size()).isEqualTo(1);
        assertThat( publisherHelper.getUpdateObjects().get(0)).isEqualTo(null);

        publisherHelper.reset();
        date.addSeconds(53);
        assertThat( publisherHelper.nbUpdated()).isEqualTo(1);
        assertThat( publisherHelper.getUpdateObjects().size()).isEqualTo(1);
        assertThat( publisherHelper.getUpdateObjects().get(0)).isEqualTo(null);

        publisherHelper.reset();
        date.getTimeInMillis();
        assertThat( publisherHelper.nbUpdated()).isEqualTo(0);
        assertThat(publisherHelper.getUpdateObjects()).isEmpty();

        publisherHelper.reset();
        date.formatDate();
        assertThat( publisherHelper.nbUpdated()).isEqualTo(0);
        assertThat(publisherHelper.getUpdateObjects()).isEmpty();
    }

    @Test
    public void publisherSet() {
        SimLifeDate date = new SimLifeDate();
        PublisherTestHelper publisherHelper = new PublisherTestHelper();
        publisherHelper.addSubscriberTo(date);

        date.setMillisOfSecond(1);
        assertThat( publisherHelper.nbUpdated()).isEqualTo(1);
        assertThat( publisherHelper.getUpdateObjects().size()).isEqualTo(1);
        assertThat( publisherHelper.getUpdateObjects().get(0)).isEqualTo(null);

        publisherHelper.reset();
        date.setMillisOfSecond(1);
        assertThat( publisherHelper.nbUpdated()).isEqualTo(1);
        assertThat( publisherHelper.getUpdateObjects().size()).isEqualTo(1);
        assertThat( publisherHelper.getUpdateObjects().get(0)).isEqualTo(null);

        publisherHelper.reset();
        date.setSecondOfMinute(53);
        assertThat( publisherHelper.nbUpdated()).isEqualTo(1);
        assertThat( publisherHelper.getUpdateObjects().size()).isEqualTo(1);
        assertThat( publisherHelper.getUpdateObjects().get(0)).isEqualTo(null);

        publisherHelper.reset();
        date.set(999, 19, 59, Day.NOSDAY, 3, Month.TOM, 99);
        assertThat( publisherHelper.nbUpdated()).isEqualTo(1);
        assertThat( publisherHelper.getUpdateObjects().size()).isEqualTo(1);
        assertThat( publisherHelper.getUpdateObjects().get(0)).isEqualTo(null);

        publisherHelper.reset();
        date.getTimeInMillis();
        assertThat( publisherHelper.nbUpdated()).isEqualTo(0);
        assertThat(publisherHelper.getUpdateObjects()).isEmpty();

        publisherHelper.reset();
        date.formatDate();
        assertThat( publisherHelper.nbUpdated()).isEqualTo(0);
        assertThat(publisherHelper.getUpdateObjects()).isEmpty();
    }

}
