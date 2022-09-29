package module.support;

/**
 * Represents a propulsion.
 * 
 * @author Emir Yuksel
 * @version 1.0
 */
public class Propulsion implements Support {
    private static final String NAME = "PROPULSION";
    private static final String ACTION = "PROPEL";
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

        return this.isDestroyed;
    }

    @Override
    public String getAction() {
        return ACTION;
    }

    @Override
    public boolean equals(Object object) {
        return false;
    }
}
