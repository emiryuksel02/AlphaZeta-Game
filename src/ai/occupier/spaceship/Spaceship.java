package ai.occupier.spaceship;

import java.util.List;

import ai.Position;
import ai.occupier.Occupier;
import module.Modulus;

/**
 * This interface provides useful methods to manage spaceships.
 * 
 * @author Emir Yuksel
 * @version 1.0
 *
 */
public interface Spaceship extends Occupier {
    /**
     * Gets the name of a spaceship.
     * 
     * @return name
     */
    String getName();

    /**
     * Sets the position of a spaceship.
     * 
     * @param y Coordinate on y-axis
     * @param x Coordinate on x-axis
     */
    void setPosition(int y, int x);

    /**
     * Gets the position of a spaceship.
     * 
     * @return position
     */
    Position getPosition();

    /**
     * Gets the list of intact modules of a spaceship.
     * 
     * @return list of modules
     */
    List<Modulus> getModules();

    /**
     * Removes a module from a spaceship.
     * 
     * @param module Module to remove
     */
    void removeModule(Modulus module);

    /**
     * Checks if a ship is destroyed.
     * 
     * @return true if all modules including engine are destroyed, false otherwise
     */
    boolean isDestroyed();

    /**
     * Destroys a ship.
     */
    void destroy();

    /**
     * Marks a ship
     */
    void mark();

    /**
     * Checks if a ship is marked.
     * 
     * @return true if the ship is marked, false otherwise.
     */
    boolean isMarked();

    /**
     * Removes the marking from a ship.
     */
    void clearMark();

    /**
     * Checks if a ship has SHIELD module.
     * 
     * @return true if shield exists, false otherwise.
     */
    boolean containsShield();

    /**
     * Checks if a ship is instance of Collector.
     * 
     * @param object
     * @return true if a ship is collector, false otherwise.
     */
    boolean equals(Object object);

    /**
     * Checks if a ship has the given module.
     * 
     * @param module Module to search
     * @return true if the searched module exists, false otherwise.
     */
    boolean hasModule(Modulus module);

    /**
     * Checks if a ship has a weapon module.
     * 
     * @return true if the ship has no weapon, false otherwise.
     */
    boolean hasNoWeapon();

}
