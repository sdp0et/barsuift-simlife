package barsuift.simLife.universe;

import barsuift.simLife.Randomizer;

public class BasicUniverseFactory {

    /**
     * Create a random universe with between 1 and 4 trees
     * 
     * @return a new universe instance
     */
    public Universe createRandom() {
        UniverseStateFactory envStateFactory = new UniverseStateFactory();
        int nbTrees = Randomizer.randomBetween(1, 4);
        UniverseState envState = envStateFactory.createRandomUniverseState(nbTrees);
        Universe environment = new BasicUniverse(envState);
        return environment;
    }

    /**
     * Create an empty universe with no trees
     * 
     * @return a new universe instance
     */
    public Universe createEmpty() {
        UniverseStateFactory envStateFactory = new UniverseStateFactory();
        UniverseState envState = envStateFactory.createEmptyUniverseState();
        Universe environment = new BasicUniverse(envState);
        return environment;
    }

}
