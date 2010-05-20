package barsuift.simLife;

import java.math.BigDecimal;


public class PercentStateTest extends JaxbTestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    protected String getPackage() {
        return "barsuift.simLife";
    }

    public void testJaxb() throws Exception {
        PercentState p = new PercentState(new BigDecimal("0.34"));
        write(p);
        PercentState p2 = (PercentState) read();
        assertEquals(p, p2);
    }

}
