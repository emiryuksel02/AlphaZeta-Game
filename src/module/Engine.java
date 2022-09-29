package module;

/**
 * Represents an engine.
 * 
 * @author Emir Yuksel
 * @version 1.0
 */
public class Engine implements Modulus {
    private static final String NAME = "ENGINE";
    private static final String ACTION = "MOVE";
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
        return false;
    }

}
