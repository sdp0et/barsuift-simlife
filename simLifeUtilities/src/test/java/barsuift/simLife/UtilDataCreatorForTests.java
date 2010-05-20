package barsuift.simLife;

import java.math.BigDecimal;


public final class UtilDataCreatorForTests {

    private UtilDataCreatorForTests() {
        // private constructor to enforce static access
    }

    public static PercentState createPercentState() {
        return new PercentState(new BigDecimal(Math.random()));
    }

}
