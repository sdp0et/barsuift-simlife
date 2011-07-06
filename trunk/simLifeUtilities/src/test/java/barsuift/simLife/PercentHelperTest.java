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

import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;


public class PercentHelperTest {

    @Test
    public void getDecimalValue() {
        BigDecimal value = PercentHelper.getDecimalValue(12);
        assertThat(value.multiply(new BigDecimal(100)).intValueExact()).isEqualTo(12);
    }

    @Test(expectedExceptions = { IllegalArgumentException.class })
    public void getDecimalValue_exceptionLow() {
        PercentHelper.getDecimalValue(-1);
    }

    @Test(expectedExceptions = { IllegalArgumentException.class })
    public void getDecimalValue_exceptionHigh() {
        PercentHelper.getDecimalValue(101);
    }

    @Test
    public void getIntValue() {
        assertThat(PercentHelper.getIntValue(new BigDecimal("0.25"))).isEqualTo(25);
        assertThat(PercentHelper.getIntValue(new BigDecimal("0.99"))).isEqualTo(99);
    }

    @Test
    public void getStringValueInt() {
        assertThat(PercentHelper.getStringValue(25)).isEqualTo("25.00%");
        assertThat(PercentHelper.getStringValue(100)).isEqualTo("100.00%");
        assertThat(PercentHelper.getStringValue(0)).isEqualTo("0.00%");
    }

    @Test
    public void getStringValueBigDecimal() {
        assertThat(PercentHelper.getStringValue(new BigDecimal("0.25"))).isEqualTo("25.00%");
        assertThat(PercentHelper.getStringValue(new BigDecimal("1"))).isEqualTo("100.00%");
        assertThat(PercentHelper.getStringValue(new BigDecimal("0"))).isEqualTo("0.00%");
        assertThat(PercentHelper.getStringValue(new BigDecimal("0.25123"))).isEqualTo("25.12%");
    }

}
