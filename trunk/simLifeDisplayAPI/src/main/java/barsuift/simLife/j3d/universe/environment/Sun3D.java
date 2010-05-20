package barsuift.simLife.j3d.universe.environment;

import javax.media.j3d.DirectionalLight;

import barsuift.simLife.IObservable;
import barsuift.simLife.Percent;




public interface Sun3D extends IObservable {

    /**
     * Computes the white factor, based on the position (rise and zenith angles). The lower the sun is, the lower the
     * white factor.
     * <p>
     * <ul>
     * <li>When the white factor is 100%, the sun is white (Red = Green = Blue).</li>
     * <li>When the white factor is 0%, the sun is red (Red = 100%, Green = Blue = 0%).</li>
     * </ul>
     * </p>
     * <p>
     * Concretely, here is the computation :<br/>
     * <code>whiteFactor = sqrt(abs(sinus(riseAngle) * sinus(zenithAngle)))</code>
     * </p>
     * 
     * @return the white factor
     */
    public Percent getWhiteFactor();

    public DirectionalLight getLight();

}