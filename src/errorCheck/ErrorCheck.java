package errorCheck;

import java.util.List;
import java.util.Map;

import ai.AI;
import ai.Position;
import ai.occupier.Occupier;
import ai.occupier.spaceship.Spaceship;
import module.Modulus;

/**
 * Provides methods for error handling.
 * 
 * @author Emir Yuksel
 * @version 1.0
 *
 */
public class ErrorCheck {
    private static final int MOVE_MIN = 1;
    private static final int MOVE_MAX = 3;
    private static final int RAMM_RANGE = 1;
    private static final int STRIKE_RANGE = 1;
    private static final int LONGSHOT_MIN = 5;
    private static final int LONGSHOT_MAX = 6;

    /**
     * Calculates distance.
     * 
     * @param target  Position of target tile.
     * @param current Position of current tile.
     * @return distance
     */
    private int distance(Position target, Position current) {
        return Math.abs(target.getX() - current.getX()) + Math.abs(target.getY() - current.getY());
    }

    /**
     * Checks if the module number is acceptable. User can select maximum 3 modules.
     * 
     * @param modules Modules to add
     * @return true if number is valid, false otherwise.
     */
    public boolean moduleNumberValid(List<String> modules) {
        if (modules.size() > 3) {
            return false;
        }
        return true;
    }

    /**
     * Checks if a tile is empty.
     * 
     * @param board match field
     * @param x     Coordinate of x-axis
     * @param y     Coordinate of y-axis
     * @return true if tile is not empty, false otherwise.
     */
    public boolean isOccupied(Occupier[][] board, int x, int y) {
        if (board[y][x] != null) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the target position is in range for move.
     * 
     * @param target  Target position.
     * @param current Current position
     * @return true if it's in range, false otherwise.
     */
    public boolean moveInRange(Position target, Position current) {
        int distance = distance(target, current);

        if (distance <= MOVE_MAX && distance >= MOVE_MIN) {
            return true;
        }

        return false;
    }

    /**
     * Checks if the target position is in range for ramm.
     * 
     * @param target  Target position.
     * @param current Current position
     * @return true if it's in range, false otherwise.
     */
    public boolean rammInRange(Position target, Position current) {
        if (distance(target, current) == RAMM_RANGE) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the target position is in range for strike.
     * 
     * @param target  Target position.
     * @param current Current position
     * @return true if it's in range, false otherwise.
     */
    public boolean strikeInRange(Position target, Position current) {
        if (distance(target, current) == STRIKE_RANGE) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the target position is in range for longshot.
     * 
     * @param target  Target position.
     * @param current Current position
     * @return true if it's in range, false otherwise.
     */
    public boolean longShotInRange(Position target, Position current) {
        int distance = distance(target, current);
        if (distance >= LONGSHOT_MIN && distance <= LONGSHOT_MAX) {
            return true;
        }
        return false;
    }

    /**
     * Checks if an action is playable.
     * 
     * @param name      Ship name
     * @param module    Module name
     * @param playables a map that contains attacker AI ships and its playable
     *                  modules.
     * @return true if the ship has corresponding module and not used it in current
     *         turn.
     */
    public boolean canBePlayed(String name, String module, Map<String, List<String>> playables) {
        if (playables.get(name).contains(module)) {
            return true;
        }

        return false;
    }

    /**
     * Checks if a ship is attacking.
     * 
     * @param ship     Spaceship to check
     * @param attacker Attacker AI
     * @return true if the ship is attacking, false otherwise.
     */
    public boolean isAttacking(Spaceship ship, AI attacker) {
        for (Spaceship attacking : attacker.getFleet().getShips()) {
            if (ship.getName().equals(attacking.getName())) {
                return true;
            }
        }

        return false;

    }

    /**
     * Checks if a ship has only one type of module except engine.
     * 
     * @param ship Spaceship to check
     * @return true if the ship has only one module, false otherwise.
     */
    public boolean hasOnlyOneModule(Spaceship ship) {
        Spaceship check = ship;
        int i = 0;
        List<Modulus> modules = check.getModules();
        modules.remove(0);
        for (Modulus module : modules) {
            if (module.getName().equals(modules.get(0).getName())) {
                i++;
            }
        }
        if (modules.size() == i) {
            return true;
        }
        return false;

    }
}
