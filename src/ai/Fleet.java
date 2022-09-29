package ai;

import java.util.ArrayList;
import java.util.List;

import ai.occupier.spaceship.Collector;
import ai.occupier.spaceship.Spaceship;
import module.Modulus;

/**
 * Represents a fleet.
 * 
 * @author Emir Yuksel
 * @version 1.0
 *
 */
public class Fleet {
    private Collector collector;
    private List<Spaceship> attackers;

    /**
     * Creates a fleet.
     * 
     * @param attackers Attacker ships
     * @param collector Collector of a fleet
     */
    public Fleet(List<Spaceship> attackers, Collector collector) {
        this.attackers = attackers;
        this.collector = collector;
    }

    /**
     * Removes all markings from the fleet.
     */
    public void clearMark() {
        for (Spaceship toClear : attackers) {
            toClear.clearMark();
        }
    }

    /**
     * Changes the position of a spaceship.
     * 
     * @param spaceship Spaceship to move.
     * @param y         Coordinate on y-axis.
     * @param x         Coordinate on x-axis.
     */
    public void move(Spaceship spaceship, int y, int x) {
        String nameToMove = spaceship.getName();
        if (nameToMove.equals(collector.getName())) {
            collector.setPosition(y, x);
            return;
        }
        for (Spaceship ship : this.attackers) {
            if (nameToMove.equals(ship.getName())) {
                ship.setPosition(y, x);
            }
        }
    }

    /**
     * Gets list of ships in a fleet
     * 
     * @return all ships.
     */
    public List<Spaceship> getShips() {
        List<Spaceship> allShips = new ArrayList<Spaceship>();
        allShips.add(collector);
        allShips.addAll(attackers);

        return allShips;
    }

    /**
     * Removes a module from a spaceship.
     * 
     * @param spaceship Spaceship whose module will be removed.
     * @param module    Module to remove.
     */
    public void removeModule(Spaceship spaceship, Modulus module) {
        if (spaceship.getName().equals(this.collector.getName())) {
            this.collector.removeModule(module);
            return;
        }

        for (Spaceship shipToRemove : this.attackers) {
            if (spaceship.getName().equals(shipToRemove.getName())) {
                shipToRemove.removeModule(module);
                return;
            }
        }

    }

    /**
     * Marks a spaceship.
     * 
     * @param ship Spaceship to mark.
     */
    public void mark(Spaceship ship) {
        for (Spaceship markedShip : this.attackers) {
            if (markedShip.getName().equals(ship.getName())) {
                markedShip.mark();
            }
        }
    }

    /**
     * Checks if a fleet is destroyed.
     * 
     * @return true if the collector is destroyed, false otherwise.
     */
    public boolean isDestroyed() {
        if (this.collector.isDestroyed()) {
            return true;
        }
        return false;
    }

    /**
     * Destroys a ship.
     * 
     * @param toDestroy Spaceship to destroy.
     */
    public void destroyShip(Spaceship toDestroy) {
        if (toDestroy.equals(toDestroy)) {
            this.collector.destroy();
            return;
        }
        for (Spaceship ship : this.attackers) {
            if (ship.getName().equals(toDestroy.getName())) {
                ship.destroy();
            }
        }
    }

}
