package module.support;

/**
 * Represents a shield.
 * 
 * @author Emir Yuksel
 * @version 1.0
 *
 */
public class Shield implements Support {
    private static final String NAME = "SHIELD";
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
        return null;
    }

    @Override
    public boolean equals(Object object) {
        return false;
    }
}
