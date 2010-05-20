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
