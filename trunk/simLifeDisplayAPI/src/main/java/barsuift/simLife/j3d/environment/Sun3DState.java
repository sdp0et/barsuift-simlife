package barsuift.simLife.j3d.environment;

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.State;
import barsuift.simLife.j3d.BoundingBoxState;

@XmlRootElement
public class Sun3DState implements State {

    private BoundingBoxState bounds;

    /**
     * The latitude should be between 0 and Pi/2
     */
    private float latitude;

    /**
     * The ecliptic obliquity should be between 0 and Pi/2
     */
    private float eclipticObliquity;

    public Sun3DState() {
        this.bounds = new BoundingBoxState();
        this.latitude = 0;
        this.eclipticObliquity = 0;
    }

    public Sun3DState(BoundingBoxState bounds, float latitude, float eclipticObliquity) {
        this.bounds = bounds;
        this.latitude = latitude;
        this.eclipticObliquity = eclipticObliquity;
    }

    public BoundingBoxState getBounds() {
        return bounds;
    }

    public void setBounds(BoundingBoxState bounds) {
        this.bounds = bounds;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getEclipticObliquity() {
        return eclipticObliquity;
    }

    public void setEclipticObliquity(float eclipticObliquity) {
        this.eclipticObliquity = eclipticObliquity;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bounds == null) ? 0 : bounds.hashCode());
        result = prime * result + Float.floatToIntBits(latitude);
        result = prime * result + Float.floatToIntBits(eclipticObliquity);
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
        Sun3DState other = (Sun3DState) obj;
        if (bounds == null) {
            if (other.bounds != null)
                return false;
        } else
            if (!bounds.equals(other.bounds))
                return false;
        if (Float.floatToIntBits(latitude) != Float.floatToIntBits(other.latitude))
            return false;
        if (Float.floatToIntBits(eclipticObliquity) != Float.floatToIntBits(other.eclipticObliquity))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Sun3DState [bounds=" + bounds + ", latitude=" + latitude + ", eclipticObliquity=" + eclipticObliquity
                + "]";
    }

}
