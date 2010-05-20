package barsuift.simLife;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Immutable percent number.
 */
public class Percent implements Comparable<Percent> {

    private static NumberFormat percentFormat;

    static {
        resetPercentFormat(Locale.US);
    }

    static NumberFormat getPercentFormat() {
        return percentFormat;
    }

    static void resetPercentFormat(Locale locale) {
        percentFormat = NumberFormat.getPercentInstance(locale);
        percentFormat.setMinimumFractionDigits(2);
    }

    private final BigDecimal value;

    /**
     * Creates a random percentage value
     */
    public Percent() {
        this.value = new BigDecimal(Math.random());
    }

    /**
     * Creates a percent which is a copy of the given percent
     * 
     * @param percent the percent to copy value from
     * @throws NullPointerException if given percent is null
     */
    public Percent(Percent percent) {
        this.value = percent.getValue();
    }

    /**
     * Creates a percent from given PercentState value
     * 
     * @param percentState must be between 0 and 1 (and not null)
     * @throws IllegalArgumentException if the given value is not between 0 and 1 (or is null)
     */
    public Percent(PercentState percentState) {
        if (percentState == null || percentState.getValue() == null || percentState.getValue().doubleValue() < 0
                || percentState.getValue().doubleValue() > 1) {
            throw new IllegalArgumentException("percent state is not between 0 and 1 (or is null)");
        }
        this.value = percentState.getValue();
    }

    /**
     * Creates a percent from given int value
     * 
     * @param value must be between 0 and 100
     * @throws IllegalArgumentException if the given value is not between 0 and 100
     */
    public Percent(int value) {
        this.value = PercentHelper.getDecimalValue(value);
    }

    /**
     * Creates a percent from given BigDecimal value
     * 
     * @param value must be between 0 and 1 (and not null)
     * @throws IllegalArgumentException if the given value is not between 0 and 1 (or is null)
     */
    public Percent(BigDecimal value) {
        if (value == null || value.doubleValue() < 0 || value.doubleValue() > 1) {
            throw new IllegalArgumentException("Value is not between 0 and 1 (or is null) but is " + value);
        }
        this.value = value;
    }

    /**
     * 
     * @return the BigDecimal value of the percentage
     */
    public BigDecimal getValue() {
        return value;
    }

    /**
     * 
     * @return an approximated int value, between 0 and 100 (rounded down)
     */
    public int getIntValue() {
        BigDecimal tmpValue = value.movePointRight(2);
        return tmpValue.setScale(0, RoundingMode.HALF_DOWN).intValueExact();
        // return tmpValue.intValue();
    }

    public PercentState getState() {
        return new PercentState(value);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + value.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Percent other = (Percent) obj;
        if (value.compareTo(other.value) != 0)
            return false;
        return true;
    }

    /**
     * Return a formatted string representing the percent value, in the form xx.00%
     * 
     * @return xx.00%
     */
    @Override
    public String toString() {
        return percentFormat.format(getValue());
    }

    public int compareTo(Percent o) {
        return value.compareTo(o.value);
    }

}
