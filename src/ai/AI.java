package ai;

import ai.occupier.spaceship.Attackership;
import ai.occupier.spaceship.Spaceship;
import module.Modulus;

/**
 * Represents an AI
 * 
 * @author Emir Yuksel
 * @version 1.0
 *
 */
public class AI {
    private Fleet fleet;
    private String name;

    /**
     * Creates an AI.
     * 
     * @param name  Name of an AI : Alpha or Zeta
     * @param fleet Fleet of an AI
     */
    public AI(String name, Fleet fleet) {
        this.fleet = fleet;
        this.name = name;
    }

    /**
     * Gets the fleet of an AI.
     * 
     * @return the fleet
     */
    public Fleet getFleet() {
        return fleet;
    }

    /**
     * Runs move() method from Fleet.
     * 
     * @param ship Spaceship to move.
     * @param y    Coordinate of y-axis
     * @param x    Coordinate of x-axis
     */
    public void move(Spaceship ship, int y, int x) {
        this.fleet.move(ship, y, x);
    }

    /**
     * Runs clearMark() method from Fleet.
     */
    public void clearMark() {
        this.fleet.clearMark();
    }

    /**
     * Runs removeModule() method from Fleet.
     * 
     * @param ship   Spaceship whose module will be removed.
     * @param module Module to remove.
     */
    public void removeModule(Spaceship ship, Modulus module) {
        this.fleet.removeModule(ship, module);
    }

    /**
     * Runs mark() method from Fleet.
     * 
     * @param ship Ship to mark.
     */
    public void mark(Attackership ship) {
        this.fleet.mark(ship);
    }

    /**
     * Runs the isDestroyed() method from Fleet. An AI loses the game if its
     * collector is destroyed.
     * 
     * @return true if the collector is destroyed,false otherwise.
     */
    public boolean hasLost() {
        return this.fleet.isDestroyed();
    }

    /**
     * Destroys a ship.
     * 
     * @param toDestroy Ship to destroy.
     */
    public void destroyShip(Spaceship toDestroy) {
        for (Spaceship ship : this.fleet.getShips()) {
            if (ship.getName().equals(toDestroy.getName())) {
                ship.destroy();
            }
        }
    }

    /**
     * Gets the name of an AI.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

}