package barsuift.simLife.j3d.universe.environment;

import java.util.Observable;

import javax.media.j3d.DirectionalLight;

import barsuift.simLife.Percent;


public class MockSun3D extends Observable implements Sun3D {

    private Percent whiteFactor = new Percent(100);

    private DirectionalLight light = new DirectionalLight();

    @Override
    public Percent getWhiteFactor() {
        return whiteFactor;
    }

    public void setWhiteFactor(Percent whiteFactor) {
        this.whiteFactor = whiteFactor;
    }

    @Override
    public DirectionalLight getLight() {
        return light;
    }

    public void setLight(DirectionalLight light) {
        this.light = light;
    }

}
