package barsuift.simLife.environment;

import barsuift.simLife.IObservable;
import barsuift.simLife.Percent;
import barsuift.simLife.j3d.universe.environment.Sun3D;

/**
 * Class representing the sun.
 */
public interface Sun extends IObservable {

    public Sun3D getSun3D();

    public Percent getLuminosity();

    public void setLuminosity(Percent luminosity) throws IllegalArgumentException;

    /**
     * Rise angle, in percent.
     * <p>
     * <ul>
     * <li>0 means sun is full east, enlighting only along the X axis</li>
     * <li>50 means sun is at its zenith position (neither east nor west). no X direction</li>
     * <li>100 means sun is full west, enlightin only along the reverted X axis</li>
     * </ul>
     * </p>
     */
    public Percent getRiseAngle();

    public void setRiseAngle(Percent riseAngle);

    /**
     * Zenith angle, in percent.
     * <p>
     * <ul>
     * <li>0 means sun is always at the horizon</li>
     * <li>50 means sun is at 45° (Pi/4)</li>
     * <li>100 means sun iis at its zenith position. no Z direction</li>
     * </ul>
     * </p>
     */
    public Percent getZenithAngle();

    public void setZenithAngle(Percent zenithAngle);

    public SunState getState();

}
