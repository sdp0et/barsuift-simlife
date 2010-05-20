package barsuift.simLife;

import java.math.BigDecimal;

import junit.framework.TestCase;


public class PercentHelperTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetDecimalValue() {
        BigDecimal value = PercentHelper.getDecimalValue(12);
        assertEquals(12, value.multiply(new BigDecimal(100)).intValueExact());
        try {
            PercentHelper.getDecimalValue(-1);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            PercentHelper.getDecimalValue(101);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

}
