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
package barsuift.simLife.j3d.environment;

import javax.vecmath.Point3f;

import org.testng.annotations.Test;

import static barsuift.simLife.j3d.assertions.Point3fAssert.assertThat;


public class SunSphere3DTest {

    @Test
    public void testUpdateForEclipticShift1() {
        SunSphere3D sphere = new SunSphere3D(0, (float) Math.PI / 2, 0);
        assertThat(sphere.getSunCenter()).isEqualTo(new Point3f(0, 0, -0.15f));

        sphere.updateForEclipticShift(0);
        assertThat(sphere.getSunCenter()).isEqualTo(new Point3f(0, 0, -0.15f));

        sphere.updateForEclipticShift((float) Math.PI / 2);
        assertThat(sphere.getSunCenter()).isEqualTo(new Point3f(0, -0.15f, 0));

        sphere.updateForEclipticShift((float) Math.PI);
        assertThat(sphere.getSunCenter()).isEqualTo(new Point3f(0, 0, 0.15f));

        sphere.updateForEclipticShift(3 * (float) Math.PI / 2);
        assertThat(sphere.getSunCenter()).isEqualTo(new Point3f(0, -0.15f, 0));
    }

    @Test
    public void testUpdateForEclipticShift2() {
        SunSphere3D sphere = new SunSphere3D((float) Math.PI / 4, (float) Math.PI / 2, 0);
        assertThat(sphere.getSunCenter()).isEqualTo(
                new Point3f(0, -0.15f * (float) Math.sqrt(2) / 2, -0.15f * (float) Math.sqrt(2) / 2));

        sphere.updateForEclipticShift(0);
        assertThat(sphere.getSunCenter()).isEqualTo(
                new Point3f(0, -0.15f * (float) Math.sqrt(2) / 2, -0.15f * (float) Math.sqrt(2) / 2));

        sphere.updateForEclipticShift((float) Math.PI / 2);
        assertThat(sphere.getSunCenter()).isEqualTo(
                new Point3f(0, -0.15f * (float) Math.sqrt(2) / 2, 0.15f * (float) Math.sqrt(2) / 2));

        sphere.updateForEclipticShift((float) Math.PI);
        assertThat(sphere.getSunCenter()).isEqualTo(
                new Point3f(0, 0.15f * (float) Math.sqrt(2) / 2, 0.15f * (float) Math.sqrt(2) / 2));

        sphere.updateForEclipticShift(3 * (float) Math.PI / 2);
        assertThat(sphere.getSunCenter()).isEqualTo(
                new Point3f(0, -0.15f * (float) Math.sqrt(2) / 2, 0.15f * (float) Math.sqrt(2) / 2));
    }

    @Test
    public void testUpdateForEclipticShift3() {
        SunSphere3D sphere = new SunSphere3D((float) Math.PI / 2, (float) Math.PI / 2, 0);
        assertThat(sphere.getSunCenter()).isEqualTo(new Point3f(0, -0.15f, 0));

        sphere.updateForEclipticShift(0);
        assertThat(sphere.getSunCenter()).isEqualTo(new Point3f(0, -0.15f, 0));

        sphere.updateForEclipticShift((float) Math.PI / 2);
        assertThat(sphere.getSunCenter()).isEqualTo(new Point3f(0, 0, 0.15f));

        sphere.updateForEclipticShift((float) Math.PI);
        assertThat(sphere.getSunCenter()).isEqualTo(new Point3f(0, 0.15f, 0));

        sphere.updateForEclipticShift(3 * (float) Math.PI / 2);
        assertThat(sphere.getSunCenter()).isEqualTo(new Point3f(0, 0, 0.15f));
    }

}
