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
package barsuift.simLife;


import java.math.BigDecimal;
import java.util.Locale;

import junit.framework.TestCase;

public class PercentTest extends TestCase {

    public void testPercentRandom() {
        for (int index = 0; index < 1000; index++) {
            double value = new Percent().getValue().doubleValue();
            assertTrue(value >= 0);
            assertTrue(value <= 1);
        }
    }

    public void testPercentInt() {
        Percent percent = new Percent(12);
        assertEquals(12, percent.getValue().multiply(new BigDecimal(100)).intValueExact());
        try {
            new Percent(-1);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            new Percent(101);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    public void testPercentPercentState() {
        PercentState percentState = new PercentState();
        percentState.setValue(new BigDecimal("0.34"));
        Percent percent = new Percent(percentState);
        assertEquals(34, percent.getValue().multiply(new BigDecimal(100)).intValueExact());
        try {
            new Percent((PercentState) null);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        percentState.setValue(null);
        try {
            new Percent(percentState);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        percentState.setValue(new BigDecimal("-0.01"));
        try {
            new Percent(percentState);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        percentState.setValue(new BigDecimal("1.01"));
        try {
            new Percent(percentState);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }


    public void testPercentBigDecimal() {
        Percent percent = new Percent(new BigDecimal("0.12"));
        assertEquals(12, percent.getValue().multiply(new BigDecimal(100)).intValueExact());
        try {
            new Percent((BigDecimal) null);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            new Percent(new BigDecimal(-0.01));
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            new Percent(new BigDecimal(1.01));
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    public void testPercentPercent() {
        Percent percent1 = new Percent(12);
        Percent percent2 = new Percent(percent1);
        assertEquals(12, percent2.getValue().multiply(new BigDecimal(100)).intValueExact());
        try {
            new Percent((Percent) null);
            fail("Should throw an NullPointerException");
        } catch (NullPointerException e) {
            // OK expected exception
        }
    }

    public void testGetIntValue() {
        Percent percent1 = new Percent(12);
        Percent percent2 = new Percent(100);
        Percent percent3 = new Percent(0);
        Percent percent4 = new Percent(new BigDecimal(0.123));
        Percent percent5 = new Percent(new BigDecimal(0.159));
        assertEquals(12, percent1.getIntValue());
        assertEquals(100, percent2.getIntValue());
        assertEquals(0, percent3.getIntValue());
        assertEquals(12, percent4.getIntValue());
        assertEquals(16, percent5.getIntValue());
    }

    public void testToString() {
        // UK by default
        Percent percent = new Percent(12);
        assertEquals("12.00%", percent.toString());

        Percent.resetPercentFormat(Locale.FRENCH);
        Percent percent2 = new Percent(12);
        assertEquals("12,00 %", percent2.toString());

        Percent.resetPercentFormat(Locale.UK);
        Percent percent3 = new Percent(12);
        assertEquals("12.00%", percent3.toString());
    }

    public void testEquals() {
        Percent percent1 = new Percent(new BigDecimal("0.12"));
        Percent percent2 = new Percent(12);
        Percent percent3 = new Percent(percent2);
        // check locale has no effect on equality
        Percent.resetPercentFormat(Locale.ITALY);
        Percent percent4 = new Percent(12);
        Percent.resetPercentFormat(Locale.CHINESE);
        Percent percent5 = new Percent(12);
        assertEquals(percent1, percent1);
        assertEquals(percent1, percent2);
        assertEquals(percent1, percent3);
        assertEquals(percent2, percent1);
        assertEquals(percent2, percent2);
        assertEquals(percent2, percent3);
        assertEquals(percent3, percent1);
        assertEquals(percent3, percent2);
        assertEquals(percent3, percent3);
        assertEquals(percent2, percent4);
        assertEquals(percent2, percent5);
        assertEquals(percent4, percent5);
    }

    public void testHashcode() {
        Percent percent1 = new Percent(new BigDecimal("0.12"));
        Percent percent2 = new Percent(12);
        Percent percent2bis = new Percent(13);
        Percent percent3 = new Percent(percent2);
        // check locale has no effect on equality
        Percent.resetPercentFormat(Locale.ITALY);
        Percent percent4 = new Percent(12);
        Percent.resetPercentFormat(Locale.CHINESE);
        Percent percent5 = new Percent(12);
        assertEquals(percent1.hashCode(), percent1.hashCode());
        assertEquals(percent1.hashCode(), percent2.hashCode());
        assertEquals(percent1.hashCode(), percent3.hashCode());
        assertEquals(percent2.hashCode(), percent1.hashCode());
        assertEquals(percent2.hashCode(), percent2.hashCode());
        assertEquals(percent2.hashCode(), percent3.hashCode());
        assertEquals(percent3.hashCode(), percent1.hashCode());
        assertEquals(percent3.hashCode(), percent2.hashCode());
        assertEquals(percent3.hashCode(), percent3.hashCode());
        assertEquals(percent2.hashCode(), percent4.hashCode());
        assertEquals(percent2.hashCode(), percent5.hashCode());
        assertEquals(percent4.hashCode(), percent5.hashCode());
        assertFalse(percent2.equals(percent2bis));
        assertFalse(percent1.equals(null));
        assertFalse(percent1.equals(new BigDecimal(0.12)));
    }

}
