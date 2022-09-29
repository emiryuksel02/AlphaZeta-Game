package module.assault;

/**
 * Represents a rail gun.
 * 
 * @author Emir Yuksel
 * @version 1.0
 *
 */
public class RailGun implements Weapon {
    private static final String NAME = "RAILGUN";
    private static final String ACTION = "LONGSHOT";
    private boolean isDestroyed = false;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void destroy() {
        isDestroyed = true;
    }

    @Override
    public boolean isDestroyed() {
        return isDestroyed;
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
