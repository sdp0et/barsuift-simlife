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
import java.math.RoundingMode;


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

    /**
     * Returns an integer value between 0 and 100 from the given value, which must be comprised between 0 and 1
     * 
     * @return an approximated int value, between 0 and 100 (rounded down)
     */
    public static int getIntValue(BigDecimal value) {
        BigDecimal tmpValue = value.movePointRight(2);
        return tmpValue.setScale(0, RoundingMode.HALF_DOWN).intValueExact();
    }

}
