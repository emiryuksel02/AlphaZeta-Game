package system.interaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ai.occupier.Cover;
import ai.occupier.Occupier;
import errorCheck.ErrorCheck;
import module.Modulus;
import module.assault.RailGun;
import module.assault.Sword;
import module.support.Propulsion;
import module.support.Sensor;
import module.support.Shield;

/**
 * Provides useful methods to apply rules to game.
 * 
 * @author Emir Yuksel
 * @version 1.0
 *
 */
public class InitGameCheck {
    private static final String MODULE_SEPARATOR = ",";
    private static final String MAX_MODULE_NUMBER = "You can select maximum 3 modules.";
    private static final String ERROR_INVALID_INPUT = "invalid input.";
    private static final int NUM_INVALID_NEIGHBOR = 4;
    private static final int NEIGHBORS = 4;

    /**
     * Checks if a tile with only covers as it's direct neighbors exists on the
     * gameboard.
     * 
     * @param board
     * @return true if it exists, false otherwise.
     */
    protected boolean occupiedByCovers(Occupier[][] board) {
        int length = board.length;

        int offSetX[] = { 0, 1, 0, -1 };
        int offSetY[] = { -1, 0, 1, 0 };

        for (int y = 0; y < length; y++) {
            for (int x = 0; x < length; x++) {
                if (!equals(board[y][x])) {
                    int check = 0;
                    check = checkNeighbors(x, y, offSetX, offSetY, board, check);
                    if (check == NUM_INVALID_NEIGHBOR) {
                        return true;
                    }
                }
            }
        }

        return false;

    }

    /**
     * Checks direct neighbors to find it a tile has only cover-neighbors.
     * 
     * @param x       Coordinate on x-axis
     * @param y       Coordinate on y-axis
     * @param offSetX neighbor calculation array for x
     * @param offSetY neighbor calculation array for y
     * @param board   game board
     * @param check   number of cover neighbors(when the index is out of bounds, it
     *                will count as a cover neighbor)
     * @return check
     */
    public int checkNeighbors(int x, int y, int[] offSetX, int[] offSetY, Occupier[][] board, int check) {
        int count = check;
        int length = board.length;
        for (int i = 0; i < NEIGHBORS; i++) {
            int nx = x + offSetX[i];
            int ny = y + offSetY[i];
            if (nx < 0 || ny >= length || ny < 0 || nx >= length || equals(board[ny][nx])) {
                count++;

            }

        }
        return count;
    }

    /**
     * Returns a list from a string.
     * 
     * @param toExtract String to separate
     * @param separator Common separator
     * @return an ArrayList
     */
    protected ArrayList<String> extractList(String toExtract, String separator) {
        return new ArrayList<String>(Arrays.asList(toExtract.split(separator)));
    }

    /**
     * Helper method for configuring Spaceships.
     * 
     * @param input
     * @return List of modules to add.
     */
    protected List<Modulus> configureInput(String input) throws InputException {
        ErrorCheck check = new ErrorCheck();
        List<String> modules = extractList(input.toUpperCase(), MODULE_SEPARATOR);
        if (!check.moduleNumberValid(modules)) {
            throw new InputException(MAX_MODULE_NUMBER);
        }
        if (!frequencyValid(modules)) {
            throw new InputException(ERROR_INVALID_INPUT);
        }

        List<Modulus> modulesToAdd = new ArrayList<Modulus>();
        for (String name : modules) {
            modulesToAdd.add(getModuleByName(name));
        }
        return modulesToAdd;
    }

    /**
     * These rules are applied to the game:
     * <p>
     * Number of weapon modules per spaceship ≤ 2.
     * <p>
     * Maximum 1 Rail Gun Module per spaceship .
     * <p>
     * Maximum 1 Propulsion Module per spaceship.
     * <p>
     * Maximum 1 shield module per spaceship
     * <p>
     * Per fleet: Maximum 1 sensor module
     * 
     * @param modules Modules to add
     * @return true if these rules are not broken, false otherwise.
     */
    protected boolean frequencyValid(List<String> modules) throws InputException {
        int weaponModule = 0;
        for (String name : modules) {
            if (getModuleByName(name).equals(name)) {
                weaponModule++;
            }
        }
        if (Collections.frequency(modules, "RAILGUN") > 1 || weaponModule > 2
                || Collections.frequency(modules, "PROPULSION") > 1 || Collections.frequency(modules, "SHIELD") > 1
                || Collections.frequency(modules, "SENSOR") > 1) {
            return false;
        }

        return true;
    }

    /**
     * Gets a module depending on it's name.
     * 
     * @param name Module name to get.
     * @return Module
     */
    protected Modulus getModuleByName(String name) throws InputException {
        Modulus module = null;
        switch (name) {
        case "PROPULSION":
            module = new Propulsion();
            break;
        case "RAILGUN":
            module = new RailGun();
            break;
        case "SENSOR":
            module = new Sensor();
            break;
        case "SHIELD":
            module = new Shield();
            break;
        case "SWORD":
            module = new Sword();
            break;
        default:
            throw new InputException(ERROR_INVALID_INPUT);
        }
        return module;
    }

    /**
     * Checks if an Occupier-Matrix (Here game board) symmetrical in terms of
     * south-west and north-east axis. Only covers are relevant for symmetry. Their
     * values should be also the same.
     * 
     * @param board
     * @return true if it's symmetrical, false otherwise.
     */
    protected boolean isSymmetrical(Occupier[][] board) {
        int column = board.length - 1;
        int row = board[0].length - 1;
        for (int y = 0; y < board.length; y++) {
            int check = 0;
            for (int x = 0; x < board[y].length; x++) {

                int cur = column - check;
                Occupier currentOccupier = board[y][x];
                Occupier toCompare = board[cur][row];
                if (equals(currentOccupier)) {
                    if (!equals(toCompare)) {
                        return false;
                    }
                    Cover first = (Cover) currentOccupier;
                    Cover second = (Cover) toCompare;
                    if (first.getValue() != second.getValue()) {
                        return false;
                    }

                }

                check++;
            }
            row--;
        }

        return true;

    }
}
