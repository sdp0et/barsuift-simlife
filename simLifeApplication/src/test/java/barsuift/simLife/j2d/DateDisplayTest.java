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

import junit.framework.TestCase;
import barsuift.simLife.time.SimLifeDate;
import barsuift.simLife.time.SimLifeDateState;


public class DateDisplayTest extends TestCase {

    private DateDisplay display;

    private SimLifeDate date;

    protected void setUp() throws Exception {
        super.setUp();
        date = new SimLifeDate(new SimLifeDateState());
        display = new DateDisplay(date);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        date = null;
        display = null;
    }

    public void testUpdate() throws ParseException {
        assertEquals("00:00:000 Nosday 01 Wim 0001", display.getText());
        date.setTime("19:59:999 Winday 18 Tom 0455");
        assertEquals("19:59:999 Winday 18 Tom 0455", display.getText());
        date.addMillis(1);
        assertEquals("00:00:000 Nosday 01 Wim 0456", display.getText());
        date.addMillis(100);
        assertEquals("00:00:100 Nosday 01 Wim 0456", display.getText());
        date.addSeconds(1);
        assertEquals("00:01:100 Nosday 01 Wim 0456", display.getText());
    }

}
