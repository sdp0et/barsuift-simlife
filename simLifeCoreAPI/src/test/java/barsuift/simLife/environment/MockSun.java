package barsuift.simLife.environment;

import java.util.Observable;

import barsuift.simLife.Percent;
import barsuift.simLife.j3d.universe.environment.MockSun3D;
import barsuift.simLife.j3d.universe.environment.Sun3D;


public class MockSun extends Observable implements Sun {

    private Percent luminosity = new Percent(100);

    private Percent riseAngle = new Percent(25);

    private Percent zenithAngle = new Percent(50);

    private Sun3D sunLight = new MockSun3D();

    private SunState state = new SunState();


    @Override
    public Percent getLuminosity() {
        return luminosity;
    }

    @Override
    public void setLuminosity(Percent luminosity) {
        this.luminosity = luminosity;
    }

    @Override
    public Percent getRiseAngle() {
        return riseAngle;
    }

    @Override
    public void setRiseAngle(Percent riseAngle) {
        this.riseAngle = riseAngle;
    }

    @Override
    public Percent getZenithAngle() {
        return zenithAngle;
    }

    @Override
    public void setZenithAngle(Percent zenithAngle) {
        this.zenithAngle = zenithAngle;
    }

    @Override
    public Sun3D getSun3D() {
        return sunLight;
    }

    public void setSunLight(Sun3D sunLight) {
        this.sunLight = sunLight;
    }

    @Override
    public SunState getState() {
        return state;
    }

    public void setSunState(SunState state) {
        this.state = state;
    }

}
