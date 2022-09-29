package module.support;

/**
 * Represents a sensor.
 * 
 * @author Emir Yuksel
 * @version 1.0
 *
 */
public class Sensor implements Support {
    private static final String NAME = "SENSOR";
    private static final String ACTION = "MARK";
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
