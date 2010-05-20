package barsuift.simLife.j3d.universe.environment;

import javax.media.j3d.DirectionalLight;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import junit.framework.TestCase;
import barsuift.simLife.Percent;
import barsuift.simLife.environment.MockSun;
import barsuift.simLife.environment.SunUpdateCode;
import barsuift.simLife.j3d.helper.CompilerHelper;
import barsuift.simLife.j3d.helper.VectorTestHelper;
import barsuift.simLife.time.ObservableTestHelper;


public class BasicSun3DTest extends TestCase {

    private MockSun mockSun;

    private BasicSun3D sun3D;

    private DirectionalLight sunLight;

    private ObservableTestHelper observerHelper;

    protected void setUp() throws Exception {
        super.setUp();
        mockSun = new MockSun();
        sun3D = new BasicSun3D(mockSun);
        sunLight = sun3D.getLight();
        CompilerHelper.compile(sunLight);
        observerHelper = new ObservableTestHelper();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        mockSun = null;
        sun3D = null;
        sunLight = null;
        observerHelper = null;
    }

    /**
     * Test that the sun3D observes the sun
     */
    public void testObservers() {
        assertEquals(1, mockSun.countObservers());
        // check the observer is the sunLight
        mockSun.deleteObserver(sun3D);
        assertEquals(0, mockSun.countObservers());
    }

    /**
     * Test that the sun3D is observable : it notifies its observers when the color change
     */
    public void testObservable() {
        observerHelper.addObserver(sun3D);
        // force computation of angles in the sun, and so for color in sun3D
        mockSun.setZenithAngle(new Percent(100));
        sun3D.update(mockSun, SunUpdateCode.zenithAngle);

        assertEquals(1, observerHelper.nbUpdated());
        assertEquals(SunUpdateCode.color, observerHelper.getUpdateObjects().get(0));
    }

    public void testUpdateLuminosity() {
        mockSun.setRiseAngle(new Percent(50));
        mockSun.setZenithAngle(new Percent(100));
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        sun3D.update(mockSun, SunUpdateCode.zenithAngle);

        Color3f actualSunColor = new Color3f();
        sunLight.getColor(actualSunColor);
        assertEquals(new Color3f(1, 1, 1), actualSunColor);

        mockSun.setLuminosity(new Percent(40));
        sun3D.update(mockSun, SunUpdateCode.luminosity);
        actualSunColor = new Color3f();
        sunLight.getColor(actualSunColor);
        assertEquals(new Color3f(0.4f, 0.4f, 0.4f), actualSunColor);

        mockSun.setLuminosity(new Percent(70));
        sun3D.update(mockSun, SunUpdateCode.luminosity);
        actualSunColor = new Color3f();
        sunLight.getColor(actualSunColor);
        assertEquals(new Color3f(0.7f, 0.7f, 0.7f), actualSunColor);
    }


    public void testUpdateColor() {
        Color3f actualSunColor = new Color3f();
        sunLight.getColor(actualSunColor);
        assertEquals(new Color3f(1f, (float) Math.sqrt(2) / 2, (float) Math.sqrt(2) / 2), actualSunColor);

        mockSun.setLuminosity(new Percent(50));
        sun3D.update(mockSun, SunUpdateCode.luminosity);
        actualSunColor = new Color3f();
        sunLight.getColor(actualSunColor);
        assertEquals(new Color3f(0.5f, (float) (0.5 * Math.sqrt(2) / 2), (float) (0.5 * Math.sqrt(2) / 2)),
                actualSunColor);

        mockSun.setLuminosity(new Percent(100));
        sun3D.update(mockSun, SunUpdateCode.luminosity);
        mockSun.setRiseAngle(new Percent(0));
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        actualSunColor = new Color3f();
        sunLight.getColor(actualSunColor);
        assertEquals(new Color3f(1f, 0f, 0f), actualSunColor);

        mockSun.setRiseAngle(new Percent(50));
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        actualSunColor = new Color3f();
        sunLight.getColor(actualSunColor);
        assertEquals(new Color3f(1f, (float) Math.sqrt(Math.sqrt(2) / 2), (float) Math.sqrt(Math.sqrt(2) / 2)),
                actualSunColor);

        mockSun.setZenithAngle(new Percent(100));
        sun3D.update(mockSun, SunUpdateCode.zenithAngle);
        actualSunColor = new Color3f();
        sunLight.getColor(actualSunColor);
        assertEquals(new Color3f(1f, 1f, 1f), actualSunColor);
    }

    public void testUpdateRiseAngle1() {
        // rise = Pi/4
        // zenith = Pi/4
        assertEquals(new Percent(25), mockSun.getRiseAngle());
        assertEquals(new Percent(50), mockSun.getZenithAngle());
        Vector3f actualDirection = new Vector3f();
        sunLight.getDirection(actualDirection);
        Vector3f expectedDirection = new Vector3f((float) Math.sqrt(2) / 2, -0.5f, -0.5f);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        mockSun.setRiseAngle(new Percent(0));
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(1, 0, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        mockSun.setRiseAngle(new Percent(50));
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(0, -(float) Math.sqrt(2) / 2, -(float) Math.sqrt(2) / 2);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        mockSun.setRiseAngle(new Percent(75));
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(-(float) Math.sqrt(2) / 2, -0.5f, -0.5f);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        mockSun.setRiseAngle(new Percent(100));
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(-1, 0, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);
    }

    /**
     * Test rise angle with zenith angle set to 0
     */
    public void testUpdateRiseAngle2() {
        mockSun.setZenithAngle(new Percent(0));
        sun3D.update(mockSun, SunUpdateCode.zenithAngle);
        // Pi/4
        assertEquals(new Percent(25), mockSun.getRiseAngle());
        Vector3f actualDirection = new Vector3f();
        sunLight.getDirection(actualDirection);
        Vector3f expectedDirection = new Vector3f((float) Math.sqrt(2) / 2, 0f, (float) -Math.sqrt(2) / 2);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // 0 Pi
        mockSun.setRiseAngle(new Percent(0));
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(1, 0, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // Pi/2
        mockSun.setRiseAngle(new Percent(50));
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(0, 0, -1);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // 3Pi/4
        mockSun.setRiseAngle(new Percent(75));
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(-(float) Math.sqrt(2) / 2, 0, -(float) Math.sqrt(2) / 2);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // Pi
        mockSun.setRiseAngle(new Percent(100));
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(-1, 0, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);
    }

    /**
     * Test rise angle with zenith angle set to 100% (Pi/2)
     */
    public void testUpdateRiseAngle3() {
        mockSun.setZenithAngle(new Percent(100));
        sun3D.update(mockSun, SunUpdateCode.zenithAngle);
        // Pi/4
        assertEquals(new Percent(25), mockSun.getRiseAngle());
        Vector3f actualDirection = new Vector3f();
        sunLight.getDirection(actualDirection);
        Vector3f expectedDirection = new Vector3f((float) Math.sqrt(2) / 2, -(float) Math.sqrt(2) / 2, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // 0 Pi
        mockSun.setRiseAngle(new Percent(0));
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(1, 0, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // Pi/2
        mockSun.setRiseAngle(new Percent(50));
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(0, -1, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // 3Pi/4
        mockSun.setRiseAngle(new Percent(75));
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(-(float) Math.sqrt(2) / 2, -(float) Math.sqrt(2) / 2, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // Pi
        mockSun.setRiseAngle(new Percent(100));
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(-1, 0, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);
    }

    public void testUpdateZenithAngle() {
        assertEquals(new Percent(25), mockSun.getRiseAngle());
        assertEquals(new Percent(50), mockSun.getZenithAngle());
        Vector3f actualDirection = new Vector3f();
        sunLight.getDirection(actualDirection);
        Vector3f expectedDirection = new Vector3f((float) Math.sqrt(2) / 2, -0.5f, -0.5f);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        mockSun.setZenithAngle(new Percent(0));
        sun3D.update(mockSun, SunUpdateCode.zenithAngle);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f((float) Math.sqrt(2) / 2, 0f, (float) -Math.sqrt(2) / 2);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        mockSun.setZenithAngle(new Percent(100));
        sun3D.update(mockSun, SunUpdateCode.zenithAngle);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f((float) Math.sqrt(2) / 2, -(float) Math.sqrt(2) / 2, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);
    }

    public void testGetWhiteFactor() {
        assertEquals(new Percent(25), mockSun.getRiseAngle());
        assertEquals(new Percent(50), mockSun.getZenithAngle());
        assertEquals((float) Math.sqrt(2) / 2, sun3D.getWhiteFactor().getValue().floatValue());

        mockSun.setZenithAngle(new Percent(0));
        sun3D.update(mockSun, SunUpdateCode.zenithAngle);
        mockSun.setRiseAngle(new Percent(0));
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        assertEquals(0f, sun3D.getWhiteFactor().getValue().floatValue(), 0.001f);
        mockSun.setRiseAngle(new Percent(25));
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        assertEquals(0f, sun3D.getWhiteFactor().getValue().floatValue(), 0.001f);
        mockSun.setRiseAngle(new Percent(50));
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        assertEquals(0f, sun3D.getWhiteFactor().getValue().floatValue(), 0.001f);
        mockSun.setRiseAngle(new Percent(75));
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        assertEquals(0f, sun3D.getWhiteFactor().getValue().floatValue(), 0.001f);
        mockSun.setRiseAngle(new Percent(100));
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        assertEquals(0f, sun3D.getWhiteFactor().getValue().floatValue(), 0.001f);

        mockSun.setZenithAngle(new Percent(50));
        sun3D.update(mockSun, SunUpdateCode.zenithAngle);
        mockSun.setRiseAngle(new Percent(0));
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        assertEquals(0f, sun3D.getWhiteFactor().getValue().floatValue(), 0.001f);
        mockSun.setRiseAngle(new Percent(25));
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        assertEquals((float) Math.sqrt(2) / 2, sun3D.getWhiteFactor().getValue().floatValue(), 0.001f);
        mockSun.setRiseAngle(new Percent(50));
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        assertEquals((float) Math.sqrt(Math.sqrt(2) / 2), sun3D.getWhiteFactor().getValue().floatValue(), 0.001f);
        mockSun.setRiseAngle(new Percent(75));
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        assertEquals((float) Math.sqrt(2) / 2, sun3D.getWhiteFactor().getValue().floatValue(), 0.001f);
        mockSun.setRiseAngle(new Percent(100));
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        assertEquals(0f, sun3D.getWhiteFactor().getValue().floatValue(), 0.001f);


        mockSun.setZenithAngle(new Percent(100));
        sun3D.update(mockSun, SunUpdateCode.zenithAngle);
        mockSun.setRiseAngle(new Percent(0));
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        assertEquals(0f, sun3D.getWhiteFactor().getValue().floatValue(), 0.001f);
        mockSun.setRiseAngle(new Percent(25));
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        assertEquals((float) Math.sqrt(Math.sqrt(2) / 2), sun3D.getWhiteFactor().getValue().floatValue(), 0.001f);
        mockSun.setRiseAngle(new Percent(50));
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        assertEquals(1f, sun3D.getWhiteFactor().getValue().floatValue(), 0.001f);
        mockSun.setRiseAngle(new Percent(75));
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        assertEquals((float) Math.sqrt(Math.sqrt(2) / 2), sun3D.getWhiteFactor().getValue().floatValue(), 0.001f);
        mockSun.setRiseAngle(new Percent(100));
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        assertEquals(0f, sun3D.getWhiteFactor().getValue().floatValue(), 0.001f);
    }

}
