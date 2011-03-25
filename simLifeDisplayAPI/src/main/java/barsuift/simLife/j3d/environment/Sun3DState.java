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

    /**
     * The earth rotation angle, in radian, between 0 and 2*Pi
     */
    private float earthRotation;

    /**
     * The earth revolution angle, in radian, between 0 and 2*Pi
     */
    private float earthRevolution;


    public Sun3DState() {
        this.bounds = new BoundingBoxState();
        this.latitude = 0;
        this.eclipticObliquity = 0;
        this.earthRotation = 0;
        this.earthRevolution = 0;
    }

    public Sun3DState(BoundingBoxState bounds, float latitude, float eclipticObliquity, float earthRotation,
            float earthRevolution) {
        this.bounds = bounds;
        this.latitude = latitude;
        this.eclipticObliquity = eclipticObliquity;
        this.earthRotation = earthRotation;
        this.earthRevolution = earthRevolution;
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

    public float getEarthRotation() {
        return earthRotation;
    }

    public void setEarthRotation(float earthRotation) {
        this.earthRotation = earthRotation;
    }

    public float getEarthRevolution() {
        return earthRevolution;
    }

    public void setEarthRevolution(float earthRevolution) {
        this.earthRevolution = earthRevolution;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bounds == null) ? 0 : bounds.hashCode());
        result = prime * result + Float.floatToIntBits(latitude);
        result = prime * result + Float.floatToIntBits(eclipticObliquity);
        result = prime * result + Float.floatToIntBits(earthRotation);
        result = prime * result + Float.floatToIntBits(earthRevolution);
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
        if (Float.floatToIntBits(earthRotation) != Float.floatToIntBits(other.earthRotation))
            return false;
        if (Float.floatToIntBits(earthRevolution) != Float.floatToIntBits(other.earthRevolution))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Sun3DState [bounds=" + bounds + ", latitude=" + latitude + ", eclipticObliquity=" + eclipticObliquity
                + earthRotation + ", earthRevolution=" + earthRevolution + "]";
    }

}
