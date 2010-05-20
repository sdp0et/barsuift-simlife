package barsuift.simLife;

public interface LivingPart {

    /**
     * Make the living part spend some time.
     * <p>
     * Depending on the living part, the consequences of this method may differ widely.
     * </p>
     * <p>
     * Usually, it means :
     * <ul>
     * <li>the living part is older than before</li>
     * <li>the living part gains 1 "action point", allowing it to do some stuff</li>
     * </ul>
     * </p>
     * 
     */
    public void spendTime();

    public Long getId();

    public int getAge();

}
