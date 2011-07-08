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
package barsuift.simLife.j2d;

import java.text.ParseException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import barsuift.simLife.time.SimLifeDate;
import barsuift.simLife.time.SimLifeDateState;

import static org.fest.assertions.Assertions.assertThat;


public class DateDisplayTest {

    private DateDisplay display;

    private SimLifeDate date;

    @BeforeMethod
    protected void setUp() {
        date = new SimLifeDate(new SimLifeDateState());
        display = new DateDisplay(date);
    }

    @AfterMethod
    protected void tearDown() {
        date = null;
        display = null;
    }

    @Test
    public void testUpdate() throws ParseException {
        assertThat(display.getText()).isEqualTo("00:00:000 Nosday 01 Wim 0001");
        date.setTime("19:59:999 Winday 18 Tom 0455");
        assertThat(display.getText()).isEqualTo("19:59:999 Winday 18 Tom 0455");
        date.addMillis(1);
        assertThat(display.getText()).isEqualTo("00:00:000 Nosday 01 Wim 0456");
        date.addMillis(100);
        assertThat(display.getText()).isEqualTo("00:00:100 Nosday 01 Wim 0456");
        date.addSeconds(1);
        assertThat(display.getText()).isEqualTo("00:01:100 Nosday 01 Wim 0456");
    }

}
