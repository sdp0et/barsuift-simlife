/**
 * barsuift-simlife is a life simulator program
 * 
 * Copyright (C) 2010 Cyrille GACHOT
 * 
 * This file is part of barsuift-simlife.
 * 
 * barsuift-simlife is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * barsuift-simlife is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with barsuift-simlife. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package barsuift.simLife.j3d.landscape;

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.State;
import barsuift.simLife.j3d.BoundingBoxState;
import barsuift.simLife.j3d.Tuple3fState;

@XmlRootElement
public class NavigatorState implements State {

    private Tuple3fState originalTranslation;

    private Tuple3fState translation;

    private double rotationX;

    private double rotationY;

    private NavigationMode navigationMode;

    private BoundingBoxState bounds;

    public NavigatorState() {
        super();
        this.originalTranslation = new Tuple3fState();
        this.translation = new Tuple3fState();
        this.rotationX = 0;
        this.rotationY = 0;
        this.navigationMode = NavigationMode.DEFAULT;
        this.bounds = new BoundingBoxState();
    }

    public NavigatorState(Tuple3fState originalTranslation, Tuple3fState translation, double rotationX,
            double rotationY, NavigationMode navigationMode, BoundingBoxState bounds) {
        super();
        this.originalTranslation = originalTranslation;
        this.translation = translation;
        this.rotationX = rotationX;
        this.rotationY = rotationY;
        this.navigationMode = navigationMode;
        this.bounds = bounds;
    }

    /**
     * The original viewer position. Be careful that the Y coordinate may not fit with the landscape. Please adjust the
     * height to the landscape when using this constant.
     */
    public Tuple3fState getOriginalTranslation() {
        return originalTranslation;
    }

    public void setOriginalTranslation(Tuple3fState originalTranslation) {
        this.originalTranslation = originalTranslation;
    }

    public Tuple3fState getTranslation() {
        return translation;
    }

    public void setTranslation(Tuple3fState translation) {
        this.translation = translation;
    }

    public double getRotationX() {
        return rotationX;
    }

    public void setRotationX(double rotationX) {
        this.rotationX = rotationX;
    }

    public double getRotationY() {
        return rotationY;
    }

    public void setRotationY(double rotationY) {
        this.rotationY = rotationY;
    }

    public NavigationMode getNavigationMode() {
        return navigationMode;
    }

    public void setNavigationMode(NavigationMode navigationMode) {
        this.navigationMode = navigationMode;
    }

    public BoundingBoxState getBounds() {
        return bounds;
    }

    public void setBounds(BoundingBoxState bounds) {
        this.bounds = bounds;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bounds == null) ? 0 : bounds.hashCode());
        result = prime * result + ((navigationMode == null) ? 0 : navigationMode.hashCode());
        long temp;
        temp = Double.doubleToLongBits(rotationX);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(rotationY);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((originalTranslation == null) ? 0 : originalTranslation.hashCode());
        result = prime * result + ((translation == null) ? 0 : translation.hashCode());
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
        NavigatorState other = (NavigatorState) obj;
        if (bounds == null) {
            if (other.bounds != null)
                return false;
        } else
            if (!bounds.equals(other.bounds))
                return false;
        if (navigationMode != other.navigationMode)
            return false;
        if (Double.doubleToLongBits(rotationX) != Double.doubleToLongBits(other.rotationX))
            return false;
        if (Double.doubleToLongBits(rotationY) != Double.doubleToLongBits(other.rotationY))
            return false;
        if (originalTranslation == null) {
            if (other.originalTranslation != null)
                return false;
        } else
            if (!originalTranslation.equals(other.originalTranslation))
                return false;
        if (translation == null) {
            if (other.translation != null)
                return false;
        } else
            if (!translation.equals(other.translation))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "NavigatorState [originalTranslation=" + originalTranslation + ", translation=" + translation
                + ", rotationX=" + rotationX + ", rotationY=" + rotationY + ", navigationMode=" + navigationMode
                + ", bounds=" + bounds + "]";
    }

}
