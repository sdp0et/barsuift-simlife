package barsuift.simLife.environment;

import java.util.Observable;

import barsuift.simLife.Percent;
import barsuift.simLife.j3d.universe.environment.BasicSun3D;
import barsuift.simLife.j3d.universe.environment.Sun3D;

/**
 * Class representing the sun.
 * <p>
 * Its only property is its luminosity, as a Percent instance.
 * </p>
 * 
 */
public class BasicSun extends Observable implements Sun {

    private Percent luminosity;

    private Percent riseAngle;

    private Percent zenithAngle;

    private final Sun3D sun3D;

    /**
     * Creates a Sun instance with given state
     * 
     * @throws IllegalArgumentException if the given sun state is null
     */
    public BasicSun(SunState state) throws IllegalArgumentException {
        if (state == null) {
            throw new IllegalArgumentException("Null sun state");
        }
        luminosity = new Percent(state.getLuminosity());
        riseAngle = new Percent(state.getRiseAngle());
        zenithAngle = new Percent(state.getZenithAngle());
        sun3D = new BasicSun3D(this);
    }

    public Percent getLuminosity() {
        return luminosity;
    }

    public void setLuminosity(Percent luminosity) throws IllegalArgumentException {
        if (luminosity == null) {
            throw new IllegalArgumentException("Sun luminosity can not be null");
        }
        if (!this.luminosity.equals(luminosity)) {
            this.luminosity = luminosity;
            setChanged();
            notifyObservers(SunUpdateCode.luminosity);
        }
    }

    public Percent getRiseAngle() {
        return riseAngle;
    }


    public void setRiseAngle(Percent riseAngle) {
        if (riseAngle == null) {
            throw new IllegalArgumentException("Sun rise angle can not be null");
        }
        if (!this.riseAngle.equals(riseAngle)) {
            this.riseAngle = riseAngle;
            setChanged();
            notifyObservers(SunUpdateCode.riseAngle);
        }
    }

    public Percent getZenithAngle() {
        return zenithAngle;
    }

    public void setZenithAngle(Percent zenithAngle) {
        if (zenithAngle == null) {
            throw new IllegalArgumentException("Sun zenith angle can not be null");
        }
        if (!this.zenithAngle.equals(zenithAngle)) {
            this.zenithAngle = zenithAngle;
            setChanged();
            notifyObservers(SunUpdateCode.zenithAngle);
        }
    }

    @Override
    public SunState getState() {
        return new SunState(luminosity.getState(), riseAngle.getState(), zenithAngle.getState());
    }

    @Override
    public Sun3D getSun3D() {
        return sun3D;
    }

    @Override
    public String toString() {
        return "BasicSun [luminosity=" + luminosity + ", riseAngle=" + riseAngle + ", zenithAngle=" + zenithAngle + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((luminosity == null) ? 0 : luminosity.hashCode());
        result = prime * result + ((riseAngle == null) ? 0 : riseAngle.hashCode());
        result = prime * result + ((zenithAngle == null) ? 0 : zenithAngle.hashCode());
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
        BasicSun other = (BasicSun) obj;
        if (luminosity == null) {
            if (other.luminosity != null)
                return false;
        } else
            if (!luminosity.equals(other.luminosity))
                return false;
        if (riseAngle == null) {
            if (other.riseAngle != null)
                return false;
        } else
            if (!riseAngle.equals(other.riseAngle))
                return false;
        if (zenithAngle == null) {
            if (other.zenithAngle != null)
                return false;
        } else
            if (!zenithAngle.equals(other.zenithAngle))
                return false;
        return true;
    }

}
