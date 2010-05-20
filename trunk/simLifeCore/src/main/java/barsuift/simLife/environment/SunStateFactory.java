package barsuift.simLife.environment;

import java.math.BigDecimal;

import barsuift.simLife.PercentState;


public class SunStateFactory {

    /**
     * Creates a default sun state with following values :
     * <ul>
     * <li>luminosity = 100%</li>
     * <li>riseAngle = 25%</li>
     * <li>zenithAngle = 50%</li>
     * </ul>
     */
    public SunState createSunState() {
        PercentState luminosity = new PercentState(new BigDecimal("1.00"));
        PercentState riseAngle = new PercentState(new BigDecimal("0.25"));
        PercentState zenithAngle = new PercentState(new BigDecimal("0.50"));
        return new SunState(luminosity, riseAngle, zenithAngle);
    }
}
