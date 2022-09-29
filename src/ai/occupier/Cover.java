package ai.occupier;

import ai.Position;

/**
 * Represents a cover.
 * 
 * @author Emir Yuksel
 * @version 1.0
 *
 */
public class Cover implements Occupier {
    private final int value;
    private final Position position;

    /**
     * Creates a new cover
     * 
     * @param value    Cover value(1 or 2)
     * @param position Position of the cover
     */
    public Cover(int value, Position position) {
        this.value = value;
        this.position = position;
    }

    /**
     * Gets the value of a cover.
     * 
     * @return the value
     */
    public int getValue() {
        return value;
    }
}
