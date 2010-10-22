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

import java.text.ParseException;

import barsuift.simLife.message.BasicPublisher;

/**
 * Mock empty class
 * 
 */
public class MockSimLifeDate extends BasicPublisher implements SimLifeDate {

    public MockSimLifeDate() {
        super(null);
    }

    @Override
    public SimLifeDateState getState() {
        return null;
    }

    @Override
    public void synchronize() {
    }

    @Override
    public long getTimeInMillis() {
        return 0;
    }

    @Override
    public int getMillisOfSecond() {
        return 0;
    }

    @Override
    public int getSecondOfMinute() {
        return 0;
    }

    @Override
    public int getMinuteOfDay() {
        return 0;
    }

    @Override
    public Day getDayOfWeek() {
        return null;
    }

    @Override
    public int getDayOfMonth() {
        return 0;
    }

    @Override
    public int getWeekOfMonth() {
        return 0;
    }

    @Override
    public Month getMonthOfYear() {
        return null;
    }

    @Override
    public int getYear() {
        return 0;
    }

    @Override
    public void setTimeInMillis(long millis) {
    }

    @Override
    public void set(int millisOfSecond, int secondOfMinute, int minuteOfDay, Day dayOfWeek, int weekOfMonth,
            Month monthOfYear, int year) {
    }

    @Override
    public void setMillisOfSecond(int millis) {
    }

    @Override
    public void setSecondOfMinute(int seconds) {
    }

    @Override
    public void setMinuteOfDay(int minutes) {
    }

    @Override
    public void setDayOfWeek(Day day) {
    }

    @Override
    public void setDayOfMonth(int dayOfMonth) {
    }

    @Override
    public void setWeekOfMonth(int weeks) {
    }

    @Override
    public void setMonthOfYear(Month month) {
    }

    @Override
    public void setYear(int years) {
    }

    @Override
    public void addMillis(long millis) {
    }

    @Override
    public void addSeconds(long seconds) {
    }

    @Override
    public void addMinutes(long minutes) {
    }

    @Override
    public void addDays(long days) {
    }

    @Override
    public void addWeeks(long weeks) {
    }

    @Override
    public void addMonths(long months) {
    }

    @Override
    public void addYears(long years) {
    }

    @Override
    public String formatDate() {
        return null;
    }

    @Override
    public void setTime(String date) throws ParseException {
    }

}
