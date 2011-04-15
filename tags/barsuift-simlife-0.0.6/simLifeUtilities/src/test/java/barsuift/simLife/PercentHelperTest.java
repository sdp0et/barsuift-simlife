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

import junit.framework.TestCase;


public class PercentHelperTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetDecimalValue() {
        BigDecimal value = PercentHelper.getDecimalValue(12);
        assertEquals(12, value.multiply(new BigDecimal(100)).intValueExact());
        try {
            PercentHelper.getDecimalValue(-1);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            PercentHelper.getDecimalValue(101);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    public void testGetIntValue() {
        assertEquals(25, PercentHelper.getIntValue(new BigDecimal("0.25")));
        assertEquals(99, PercentHelper.getIntValue(new BigDecimal("0.99")));
    }

    public void testGetStringValueInt() {
        assertEquals("25.00%", PercentHelper.getStringValue(25));
        assertEquals("100.00%", PercentHelper.getStringValue(100));
        assertEquals("0.00%", PercentHelper.getStringValue(0));
    }

    public void testGetStringValueBigDecimal() {
        assertEquals("25.00%", PercentHelper.getStringValue(new BigDecimal("0.25")));
        assertEquals("100.00%", PercentHelper.getStringValue(new BigDecimal("1")));
        assertEquals("0.00%", PercentHelper.getStringValue(new BigDecimal("0")));
        assertEquals("25.12%", PercentHelper.getStringValue(new BigDecimal("0.25123")));
    }

}
