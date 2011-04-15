package barsuift.simLife.j3d.environment;

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.State;
import barsuift.simLife.j3d.BoundingBoxState;
import barsuift.simLife.process.SplitConditionalTaskState;

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

    private SplitConditionalTaskState earthRotationTask;

    private boolean earthRotationTaskAutomatic;

    /**
     * The earth revolution angle, in radian, between 0 and 2*Pi
     */
    private float earthRevolution;

    private SplitConditionalTaskState earthRevolutionTask;

    private boolean earthRevolutionTaskAutomatic;


    public Sun3DState() {
        this.bounds = new BoundingBoxState();
        this.latitude = 0;
        this.eclipticObliquity = 0;

        this.earthRotation = 0;
        this.earthRotationTask = new SplitConditionalTaskState();
        this.earthRotationTaskAutomatic = true;

        this.earthRevolution = 0;
        this.earthRevolutionTask = new SplitConditionalTaskState();
        this.earthRevolutionTaskAutomatic = true;
    }

    public Sun3DState(BoundingBoxState bounds, float latitude, float eclipticObliquity, float earthRotation,
            SplitConditionalTaskState earthRotationTask, boolean earthRotationTaskAutomatic, float earthRevolution,
            SplitConditionalTaskState earthRevolutionTask, boolean earthRevolutionTaskAutomatic) {
        this.bounds = bounds;
        this.latitude = latitude;
        this.eclipticObliquity = eclipticObliquity;

        this.earthRotation = earthRotation;
        this.earthRotationTask = earthRotationTask;
        this.earthRotationTaskAutomatic = earthRotationTaskAutomatic;

        this.earthRevolution = earthRevolution;
        this.earthRevolutionTask = earthRotationTask;
        this.earthRevolutionTaskAutomatic = earthRevolutionTaskAutomatic;
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

    public SplitConditionalTaskState getEarthRotationTask() {
        return earthRotationTask;
    }

    public void setEarthRotationTask(SplitConditionalTaskState earthRotationTask) {
        this.earthRotationTask = earthRotationTask;
    }

    public boolean isEarthRotationTaskAutomatic() {
        return earthRotationTaskAutomatic;
    }

    public void setEarthRotationTaskAutomatic(boolean earthRotationTaskAutomatic) {
        this.earthRotationTaskAutomatic = earthRotationTaskAutomatic;
    }



    public float getEarthRevolution() {
        return earthRevolution;
    }

    public void setEarthRevolution(float earthRevolution) {
        this.earthRevolution = earthRevolution;
    }

    public SplitConditionalTaskState getEarthRevolutionTask() {
        return earthRevolutionTask;
    }

    public void setEarthRevolutionTask(SplitConditionalTaskState earthRevolutionTask) {
        this.earthRevolutionTask = earthRevolutionTask;
    }

    public boolean isEarthRevolutionTaskAutomatic() {
        return earthRevolutionTaskAutomatic;
    }

    public void setEarthRevolutionTaskAutomatic(boolean earthRevolutionTaskAutomatic) {
        this.earthRevolutionTaskAutomatic = earthRevolutionTaskAutomatic;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bounds == null) ? 0 : bounds.hashCode());
        result = prime * result + Float.floatToIntBits(earthRevolution);
        result = prime * result + ((earthRevolutionTask == null) ? 0 : earthRevolutionTask.hashCode());
        result = prime * result + (earthRevolutionTaskAutomatic ? 1231 : 1237);
        result = prime * result + Float.floatToIntBits(earthRotation);
        result = prime * result + ((earthRotationTask == null) ? 0 : earthRotationTask.hashCode());
        result = prime * result + (earthRotationTaskAutomatic ? 1231 : 1237);
        result = prime * result + Float.floatToIntBits(eclipticObliquity);
        result = prime * result + Float.floatToIntBits(latitude);
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
        if (Float.floatToIntBits(earthRevolution) != Float.floatToIntBits(other.earthRevolution))
            return false;
        if (earthRevolutionTask == null) {
            if (other.earthRevolutionTask != null)
                return false;
        } else
            if (!earthRevolutionTask.equals(other.earthRevolutionTask))
                return false;
        if (earthRevolutionTaskAutomatic != other.earthRevolutionTaskAutomatic)
            return false;
        if (Float.floatToIntBits(earthRotation) != Float.floatToIntBits(other.earthRotation))
            return false;
        if (earthRotationTask == null) {
            if (other.earthRotationTask != null)
                return false;
        } else
            if (!earthRotationTask.equals(other.earthRotationTask))
                return false;
        if (earthRotationTaskAutomatic != other.earthRotationTaskAutomatic)
            return false;
        if (Float.floatToIntBits(eclipticObliquity) != Float.floatToIntBits(other.eclipticObliquity))
            return false;
        if (Float.floatToIntBits(latitude) != Float.floatToIntBits(other.latitude))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Sun3DState [bounds=" + bounds + ", latitude=" + latitude + ", eclipticObliquity=" + eclipticObliquity
                + ", earthRotation=" + earthRotation + ", earthRotationTask=" + earthRotationTask
                + ", earthRotationTaskAutomatic=" + earthRotationTaskAutomatic + ", earthRevolution=" + earthRevolution
                + ", earthRevolutionTask=" + earthRevolutionTask + ", earthRevolutionTaskAutomatic="
                + earthRevolutionTaskAutomatic + "]";
    }

}
