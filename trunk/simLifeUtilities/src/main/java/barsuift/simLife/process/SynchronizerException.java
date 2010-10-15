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
package barsuift.simLife.process;

/**
 * This exception denotes an error while initializing the {@link Synchronizer} class. It is thrown if the synchronizer
 * is not able to instantiate the tasks to be synchronized.
 * 
 */
public class SynchronizerException extends Exception {

    private static final long serialVersionUID = 4602281594175762480L;

    public SynchronizerException() {
        super();
    }

    public SynchronizerException(String message, Throwable cause) {
        super(message, cause);
    }

    public SynchronizerException(String message) {
        super(message);
    }

    public SynchronizerException(Throwable cause) {
        super(cause);
    }

}
