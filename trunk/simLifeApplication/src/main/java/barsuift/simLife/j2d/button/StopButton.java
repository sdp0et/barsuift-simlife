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
package barsuift.simLife.j2d.button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;
import barsuift.simLife.process.MainSynchronizer;


public class StopButton extends JButton implements Subscriber {

    private static final long serialVersionUID = 8982250197152953973L;

    public StopButton(final MainSynchronizer synchronizer) {
        super("STOP");
        synchronizer.addSubscriber(this);
        setVisible(false);
        addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                synchronizer.stop();
            }

        });
    }

    @Override
    public void update(Publisher publisher, Object arg) {
        if (((MainSynchronizer) publisher).isRunning()) {
            setVisible(true);
        } else {
            setVisible(false);
        }
    }

}
