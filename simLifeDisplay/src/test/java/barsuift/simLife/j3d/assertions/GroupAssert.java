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
package barsuift.simLife.j3d.assertions;

import java.util.Enumeration;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Group;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransformGroup;

import org.fest.assertions.Assertions;
import org.fest.assertions.GenericAssert;

import com.sun.j3d.utils.geometry.Primitive;


public class GroupAssert extends GenericAssert<GroupAssert, Group> {

    /**
     * Creates a new </code>{@link GroupAssert}</code> to make assertions on actual Group.
     * 
     * @param actual the Group we want to make assertions on.
     */
    public GroupAssert(Group actual) {
        super(GroupAssert.class, actual);
    }

    /**
     * An entry group for GroupAssert to follow Fest standard <code>assertThat()</code> statements.<br>
     * With a static import, one's can write directly :
     * <code>assertThat(group).hasExactlyOneBranchGroup(anotherGroup);</code>
     * 
     * @param actual the Group we want to make assertions on.
     * @return a new </code>{@link GroupAssert}</code>
     */
    public static GroupAssert assertThat(Group actual) {
        return new GroupAssert(actual);
    }

    /**
     * Verifies that the actual group has exactly one child, and this child is of the given class.
     * 
     * @return this assertion object.
     * @throws AssertionError - if the actual group does not have exactly one child of the given class.
     */
    @SuppressWarnings("rawtypes")
    public GroupAssert hasExactlyOne(Class<?> type) {
        // check that actual Group we want to make assertions on is not null.
        isNotNull();

        Enumeration children = actual.getAllChildren();
        Assertions.assertThat(children.hasMoreElements()).isTrue();
        Assertions.assertThat(children.nextElement()).isInstanceOf(type);
        Assertions.assertThat(children.hasMoreElements()).isFalse();

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual group has exactly one Group.
     * 
     * @return this assertion object.
     * @throws AssertionError - if the actual group does not have exactly one Group.
     */
    public GroupAssert hasExactlyOneGroup() {
        return hasExactlyOne(Group.class);
    }

    /**
     * Verifies that the actual group has exactly one BranchGroup.
     * 
     * @return this assertion object.
     * @throws AssertionError - if the actual group does not have exactly one BranchGroup.
     */
    public GroupAssert hasExactlyOneBranchGroup() {
        return hasExactlyOne(BranchGroup.class);
    }

    /**
     * Verifies that the actual group has exactly one TransformGroup.
     * 
     * @return this assertion object.
     * @throws AssertionError - if the actual group does not have exactly one TransformGroup.
     */
    public GroupAssert hasExactlyOneTransformGroup() {
        return hasExactlyOne(TransformGroup.class);
    }

    /**
     * Verifies that the actual group has exactly one Shape3D.
     * 
     * @return this assertion object.
     * @throws AssertionError - if the actual group does not have exactly one Shape3D.
     */
    public GroupAssert hasExactlyOneShape3D() {
        return hasExactlyOne(Shape3D.class);
    }

    /**
     * Verifies that the actual group has exactly one Primitive.
     * 
     * @return this assertion object.
     * @throws AssertionError - if the actual group does not have exactly one Primitive.
     */
    public GroupAssert hasExactlyOnePrimitive() {
        return hasExactlyOne(Primitive.class);
    }

}
