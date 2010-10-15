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
package barsuift.simLife.universe;

import java.util.ArrayList;
import java.util.List;

import barsuift.simLife.LivingPart;
import barsuift.simLife.environment.Environment;
import barsuift.simLife.environment.MockEnvironment;
import barsuift.simLife.j3d.universe.MockUniverse3D;
import barsuift.simLife.j3d.universe.Universe3D;
import barsuift.simLife.time.FpsCounter;
import barsuift.simLife.time.SimLifeCalendar;
import barsuift.simLife.time.SimLifeCalendarState;
import barsuift.simLife.tree.Tree;
import barsuift.simLife.tree.TreeLeaf;


public class MockUniverse implements Universe {

    private List<LivingPart> livingParts = new ArrayList<LivingPart>();

    private List<Tree> trees = new ArrayList<Tree>();

    private List<TreeLeaf> fallenLeaves = new ArrayList<TreeLeaf>();

    private int age = 0;

    private int timeSpent = 0;

    private Environment environment = new MockEnvironment();

    private Universe3D universe3D = new MockUniverse3D();

    private UniverseState state = new UniverseState();

    private SimLifeCalendar calendar = new SimLifeCalendar(new SimLifeCalendarState());

    private FpsCounter fpsCounter;

    private int synchronizedCalled;

    private boolean fpsShowing = false;

    @Override
    public List<LivingPart> getLivingParts() {
        return livingParts;
    }

    public void addLivingPart(LivingPart livingPart) {
        livingParts.add(livingPart);
    }

    public void removeLivingPart(LivingPart livingPart) {
        livingParts.remove(livingPart);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public void spendTime() {
        timeSpent++;
    }

    public int getNbTimeSpent() {
        return timeSpent;
    }

    public void resetNbTimeSpent() {
        timeSpent = 0;
    }

    @Override
    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public List<Tree> getTrees() {
        return trees;
    }

    @Override
    public void addTree(Tree tree) {
        trees.add(tree);
    }

    public void removeTree(Tree tree) {
        trees.remove(tree);
    }

    @Override
    public List<TreeLeaf> getFallenLeaves() {
        return fallenLeaves;
    }

    public void addFallenLeaf(TreeLeaf treeLeaf) {
        fallenLeaves.add(treeLeaf);
    }

    public void removeFallenLeaf(TreeLeaf treeLeaf) {
        fallenLeaves.remove(treeLeaf);
    }

    @Override
    public Universe3D getUniverse3D() {
        return universe3D;
    }

    public void setUniverse3D(Universe3D universe3D) {
        this.universe3D = universe3D;
    }

    @Override
    public UniverseState getState() {
        return state;
    }

    public void setState(UniverseState state) {
        this.state = state;
    }

    @Override
    public SimLifeCalendar getCalendar() {
        return calendar;
    }

    public void setCalendar(SimLifeCalendar calendar) {
        this.calendar = calendar;
    }

    @Override
    public FpsCounter getFpsCounter() {
        return fpsCounter;
    }

    public void setFpsCounter(FpsCounter fpsCounter) {
        this.fpsCounter = fpsCounter;
    }

    @Override
    public void synchronize() {
        this.synchronizedCalled++;
    }

    public int getNbSynchronize() {
        return synchronizedCalled;
    }

    @Override
    public void setFpsShowing(boolean fpsShowing) {
        this.fpsShowing = fpsShowing;
    }

    public boolean isFpsShowing() {
        return fpsShowing;
    }

}
