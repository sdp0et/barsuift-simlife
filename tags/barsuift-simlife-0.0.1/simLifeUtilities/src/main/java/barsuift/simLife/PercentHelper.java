/**
 * barsuift-simlife is a life simulator programm
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


public final class PercentHelper {

    private PercentHelper() {
        // private constructor to enforce static access
    }

    /**
     * Returns a BigDecimal value between 0 and 1 from the given value, which must be comprised between 0 and 100
     * 
     * @param value a value between 0 and 100
     * @return a decimal value between 0 and 1
     */
    public static BigDecimal getDecimalValue(int value) {
        if (value < 0 || value > 100) {
            throw new IllegalArgumentException("Value is not between 0 and 100 but is " + value);
        }
        BigDecimal tmpValue = new BigDecimal(value);
        return tmpValue.movePointLeft(2);
    }

}
