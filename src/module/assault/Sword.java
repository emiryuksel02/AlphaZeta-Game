package module.assault;

/**
 * Represents a sword.
 * 
 * @author Emir Yuksel
 * @version 1.0
 *
 */
public class Sword implements Weapon {
    private static final String NAME = "SWORD";
    private static final String ACTION = "STRIKE";
    private boolean isDestroyed = false;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean isDestroyed() {
        return isDestroyed;
    }

    @Override
    public void destroy() {
        isDestroyed = true;

    }

    @Override
    public String getAction() {
        return ACTION;
    }

    @Override
    public boolean equals(Object object) {
        return true;
    }
}
