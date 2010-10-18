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

import javax.swing.JLabel;

import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;
import barsuift.simLife.time.SimLifeCalendar;

public class CalendarDisplay extends JLabel implements Subscriber {

    private static final long serialVersionUID = 6381218933947453660L;

    private SimLifeCalendar calendar;

    public CalendarDisplay(SimLifeCalendar calendar) {
        this.calendar = calendar;
        calendar.addSubscriber(this);
        setText(calendar.formatDate());
        setSize(200, 40);
    }

    @Override
    public void update(Publisher publisher, Object arg) {
        setText(calendar.formatDate());
    }

}
