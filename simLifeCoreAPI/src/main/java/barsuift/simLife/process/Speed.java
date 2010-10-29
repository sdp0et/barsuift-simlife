package barsuift.simLife.process;


public enum Speed {

    NORMAL(1),
    FAST(5),
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
