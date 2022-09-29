package ai;

/**
 * Represents a position
 * 
 * @author Emir Yuksel
 * @version 1.0
 *
 */
public class Position {
    private int x;
    private int y;

    /**
     * Creates a position.
     * 
     * @param y Coordinate on y-axis
     * @param x Coordinate on x-axis.
     */
    public Position(int y, int x) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the y-coordinate.
     * 
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the x-coordinate.
     * 
     * @return the x
     */
    public int getX() {
        return x;
    }
}
