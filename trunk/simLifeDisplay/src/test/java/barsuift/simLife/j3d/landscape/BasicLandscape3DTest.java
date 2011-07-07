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

import org.fest.assertions.Delta;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import barsuift.simLife.j3d.DisplayDataCreatorForTests;

import static org.fest.assertions.Assertions.assertThat;


public class BasicLandscape3DTest {

    private BasicLandscape3D landscape3D;

    @BeforeMethod
    protected void setUp() {
        Landscape3DState state = DisplayDataCreatorForTests.createSpecificLandscape3DState();
        landscape3D = new BasicLandscape3D(state);
    }

    @AfterMethod
    protected void tearDown() {
        landscape3D = null;
    }

    @Test
    public void testGetVertexIndix() {
        assertThat(landscape3D.getVertexIndix(0, 0)).isEqualTo(0);
        assertThat(landscape3D.getVertexIndix(1, 0)).isEqualTo(1);
        assertThat(landscape3D.getVertexIndix(0, 1)).isEqualTo(2);
        assertThat(landscape3D.getVertexIndix(1, 1)).isEqualTo(3);

        // the following points are not in landscape
        // we don't actually care about the returned values. The only assertion is that this method won't fail.
        landscape3D.getVertexIndix(-1, 0);
        landscape3D.getVertexIndix(0, -1);
        landscape3D.getVertexIndix(2, 0);
        landscape3D.getVertexIndix(0, 2);

    }

    @Test
    public void testInLandscape() {
        float epsilon = 0.00001f;

        assertThat(landscape3D.inLandscape(0, -1)).isFalse();
        assertThat(landscape3D.inLandscape(epsilon, -1)).isFalse();

        assertThat(landscape3D.inLandscape(0, -epsilon)).isFalse();
        assertThat(landscape3D.inLandscape(epsilon, -epsilon)).isFalse();
        assertThat(landscape3D.inLandscape(1 - epsilon, -epsilon)).isFalse();
        assertThat(landscape3D.inLandscape(1, -epsilon)).isFalse();

        assertThat(landscape3D.inLandscape(-1, 0)).isFalse();
        assertThat(landscape3D.inLandscape(-epsilon, 0)).isFalse();
        assertThat(landscape3D.inLandscape(0, 0)).isTrue();
        assertThat(landscape3D.inLandscape(epsilon, 0)).isTrue();
        assertThat(landscape3D.inLandscape(1 - epsilon, 0)).isTrue();
        assertThat(landscape3D.inLandscape(1, 0)).isFalse();
        assertThat(landscape3D.inLandscape(1 + epsilon, 0)).isFalse();
        assertThat(landscape3D.inLandscape(2, 0)).isFalse();

        assertThat(landscape3D.inLandscape(-1, epsilon)).isFalse();
        assertThat(landscape3D.inLandscape(-epsilon, epsilon)).isFalse();
        assertThat(landscape3D.inLandscape(0, epsilon)).isTrue();
        assertThat(landscape3D.inLandscape(epsilon, epsilon)).isTrue();
        assertThat(landscape3D.inLandscape(1 - epsilon, epsilon)).isTrue();
        assertThat(landscape3D.inLandscape(1, epsilon)).isFalse();
        assertThat(landscape3D.inLandscape(1 + epsilon, epsilon)).isFalse();
        assertThat(landscape3D.inLandscape(2, epsilon)).isFalse();

        assertThat(landscape3D.inLandscape(-1, 1 - epsilon)).isFalse();
        assertThat(landscape3D.inLandscape(-epsilon, 1 - epsilon)).isFalse();
        assertThat(landscape3D.inLandscape(0, 1 - epsilon)).isTrue();
        assertThat(landscape3D.inLandscape(epsilon, 1 - epsilon)).isTrue();
        assertThat(landscape3D.inLandscape(1 - epsilon, 1 - epsilon)).isTrue();
        assertThat(landscape3D.inLandscape(1, 1 - epsilon)).isFalse();
        assertThat(landscape3D.inLandscape(1 + epsilon, 1 - epsilon)).isFalse();
        assertThat(landscape3D.inLandscape(2, 1 - epsilon)).isFalse();

        assertThat(landscape3D.inLandscape(-1, 1)).isFalse();
        assertThat(landscape3D.inLandscape(-epsilon, 1)).isFalse();
        assertThat(landscape3D.inLandscape(0, 1)).isFalse();
        assertThat(landscape3D.inLandscape(epsilon, 1)).isFalse();
        assertThat(landscape3D.inLandscape(1 - epsilon, 1)).isFalse();
        assertThat(landscape3D.inLandscape(1, 1)).isFalse();
        assertThat(landscape3D.inLandscape(1 + epsilon, 1)).isFalse();
        assertThat(landscape3D.inLandscape(2, 1)).isFalse();

        assertThat(landscape3D.inLandscape(0, 1 + epsilon)).isFalse();
        assertThat(landscape3D.inLandscape(epsilon, 1 + epsilon)).isFalse();
        assertThat(landscape3D.inLandscape(1 - epsilon, 1 + epsilon)).isFalse();
        assertThat(landscape3D.inLandscape(1, 1 + epsilon)).isFalse();

        assertThat(landscape3D.inLandscape(0, 2)).isFalse();
    }

    @Test
    public void testGetHeight() {
        float epsilon = 0.00001f;
        float delta = 0.001f;

        assertThat(landscape3D.getHeight(0.0f, 0.0f)).isEqualTo(0.0f, Delta.delta(delta));
        assertThat(landscape3D.getHeight(0.0f, 0.0f)).isEqualTo(0.0f, Delta.delta(delta));
        assertThat(landscape3D.getHeight(0.5f, 0.0f)).isEqualTo(0.2f, Delta.delta(delta));
        assertThat(landscape3D.getHeight(1.0f - epsilon, 0.0f)).isEqualTo(0.4f, Delta.delta(delta));

        assertThat(landscape3D.getHeight(0.0f, 0.5f)).isEqualTo(0.3f, Delta.delta(delta));
        assertThat(landscape3D.getHeight(0.5f, 0.5f)).isEqualTo(0.5f, Delta.delta(delta));
        assertThat(landscape3D.getHeight(1.0f - epsilon, 0.5f)).isEqualTo(0.8f, Delta.delta(delta));

        assertThat(landscape3D.getHeight(0.0f, 1.0f - epsilon)).isEqualTo(0.6f, Delta.delta(delta));
        assertThat(landscape3D.getHeight(0.5f, 1.0f - epsilon)).isEqualTo(0.9f, Delta.delta(delta));
        assertThat(landscape3D.getHeight(1.0f - epsilon, 1.0f - epsilon)).isEqualTo(1.2f, Delta.delta(delta));

        assertThat(landscape3D.getHeight(0.4f, 0.2f)).isEqualTo(0.28f, Delta.delta(delta));
        assertThat(landscape3D.getHeight(0.2f, 0.4f)).isEqualTo(0.32f, Delta.delta(delta));

        assertThat(landscape3D.getHeight(0.8f, 0.4f)).isEqualTo(0.6f, Delta.delta(delta));
        assertThat(landscape3D.getHeight(0.4f, 0.8f)).isEqualTo(0.68f, Delta.delta(delta));


        // when the position is not in the landscape, we simply return 0
        assertThat(landscape3D.getHeight(-1, 0)).isZero();
        assertThat(landscape3D.getHeight(0, -1)).isZero();
        assertThat(landscape3D.getHeight(2, 0)).isZero();
        assertThat(landscape3D.getHeight(0, 2)).isZero();

        assertThat(landscape3D.getHeight(0, -epsilon)).isZero();
        assertThat(landscape3D.getHeight(1, -epsilon)).isZero();
        assertThat(landscape3D.getHeight(-epsilon, 0)).isZero();
        assertThat(landscape3D.getHeight(1f + epsilon, 0)).isZero();
        assertThat(landscape3D.getHeight(-epsilon, 1)).isZero();
        assertThat(landscape3D.getHeight(1 + epsilon, 1)).isZero();
        assertThat(landscape3D.getHeight(0, 1 + epsilon)).isZero();
        assertThat(landscape3D.getHeight(1, 1 + epsilon)).isZero();
    }

}
