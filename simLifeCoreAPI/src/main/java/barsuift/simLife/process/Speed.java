package barsuift.simLife.process;


public enum Speed {

    /**
     * Normal speed of 1
     */
    NORMAL(1),

    /**
     * Fast speed of 5
     */
    FAST(5),

    /**
     * Very fast speed of 20
     */
    VERY_FAST(20);

    private final int speed;


    private Speed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public String toString() {
        return "speed " + speed;
    }

}
