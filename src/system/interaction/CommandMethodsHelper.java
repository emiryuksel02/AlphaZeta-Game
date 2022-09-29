package system.interaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import ai.Position;
import ai.occupier.spaceship.Spaceship;
import errorCheck.ErrorCheck;
import module.Container;
import module.Modulus;
import module.assault.RailGun;
import module.assault.Sword;
import module.support.Propulsion;
import module.support.Sensor;
import module.support.Shield;
import system.AlphaZeta;

/**
 * Provides helper methods for command methods.
 * 
 * @author Emir Yuksel
 * @version 1.0
 *
 */
public class CommandMethodsHelper {
    private static final String ERROR_COLLECTOR = "collectors cannot be marked.";
    private static final String ERROR_FALSE_TURN = "the AI of this ship is not attacking at the moment.";
    private static final String ERROR_COMMAND_NOT_EXECUTABLE = "this command is not executable.";
    private static final String ERROR_NOT_IN_RANGE = "not in range.";
    private static final String ERROR_TILE_NOT_EMPTY = "target tile is not empty.";
    private static final String ERROR_SHIP_DESTROYED = "this ship is destroyed.";
    private static final String ERROR_RAMM = "you cannot ramm a collector.";
    private static final String ERROR_SAME_FLEET = "you cannot attack a ship from same fleet";
    private static final String ACTIONS_OF = "Available actions of ";
    private static final String NO_ACTIONS = "No available actions for ";
    private static final String ERROR_MODULE = "Error, please select a valid module.";
    private static final String ERROR_SHIP_NOT_FOUND = "ship not found.";
    private static final String ERROR_INVALID_INPUT = "invalid input.";
    private static final String MESSAGE_MUST_SELECT = " must select ";
    private static final String ONE = "1 of ";
    private static final String TWO = "2 of ";
    private static final String MODULE_SELECT = "s module(s) (separated by comma):";

    /**
     * Checks if mark command is executable.
     * 
     * @param attacker  Attacker ship
     * @param defender  Defender ship
     * @param alphaZeta The instance of a system to be manipulated
     */
    protected void markError(Spaceship attacker, Spaceship defender, AlphaZeta alphaZeta) throws InputException {
        if (defender.equals(defender)) {
            throw new InputException(ERROR_COLLECTOR);
        }
        ErrorCheck errorCheck = new ErrorCheck();

        if (!errorCheck.isAttacking(attacker, alphaZeta.getPlaying())) {
            throw new InputException(ERROR_FALSE_TURN);
        }

        if (!errorCheck.canBePlayed(attacker.getName(), "SENSOR", alphaZeta.getPlayables())) {
            throw new InputException(ERROR_COMMAND_NOT_EXECUTABLE);
        }
    }

    /**
     * Checks if strike command is executable.
     * 
     * @param attacker  Attacker ship
     * @param defender  Defender ship
     * @param alphaZeta The instance of a system to be manipulated
     */

    protected void strikeError(Spaceship attacker, Spaceship defender, AlphaZeta alphaZeta) throws InputException {
        ErrorCheck errorCheck = new ErrorCheck();

        if (attacker.isDestroyed() || defender.isDestroyed()) {
            throw new InputException(ERROR_SHIP_DESTROYED);
        }

        if (!errorCheck.isAttacking(attacker, alphaZeta.getPlaying())) {
            throw new InputException(ERROR_FALSE_TURN);
        }
        if (errorCheck.isAttacking(defender, alphaZeta.getPlaying())) {
            throw new InputException(ERROR_SAME_FLEET);
        }

        if (!errorCheck.canBePlayed(attacker.getName(), "SWORD", alphaZeta.getPlayables())) {
            throw new InputException(ERROR_COMMAND_NOT_EXECUTABLE);
        }
        if (!errorCheck.strikeInRange(defender.getPosition(), attacker.getPosition())) {
            throw new InputException(ERROR_NOT_IN_RANGE);
        }
    }

    /**
     * Checks if longshot command is executable.
     * 
     * @param attacker  Attacker ship
     * @param defender  Defender ship
     * @param alphaZeta The instance of a system to be manipulated
     */
    protected void longShotError(Spaceship attacker, Spaceship defender, AlphaZeta alphaZeta) throws InputException {
        ErrorCheck errorCheck = new ErrorCheck();
        if (attacker.isDestroyed() || defender.isDestroyed()) {
            throw new InputException(ERROR_SHIP_DESTROYED);
        }

        if (!errorCheck.isAttacking(attacker, alphaZeta.getPlaying())) {
            throw new InputException(ERROR_FALSE_TURN);
        }

        if (errorCheck.isAttacking(defender, alphaZeta.getPlaying())) {
            throw new InputException(ERROR_SAME_FLEET);
        }

        if (!errorCheck.canBePlayed(attacker.getName(), "RAILGUN", alphaZeta.getPlayables())) {
            throw new InputException(ERROR_COMMAND_NOT_EXECUTABLE);
        }

        if (!errorCheck.longShotInRange(defender.getPosition(), attacker.getPosition())) {
            throw new InputException(ERROR_NOT_IN_RANGE);
        }
    }

    /**
     * Checks if move command is executable.
     * 
     * @param toMove    Spaceship to move
     * @param alphaZeta The instance of a system to be manipulated
     * @param target    Target position
     * @param current   Current position of a spaceship
     */
    protected void moveError(Spaceship toMove, AlphaZeta alphaZeta, Position target, Position current)
            throws InputException {
        ErrorCheck errorCheck = new ErrorCheck();
        if (!errorCheck.isAttacking(toMove, alphaZeta.getPlaying())) {
            throw new InputException(ERROR_FALSE_TURN);
        }
        if (!errorCheck.canBePlayed(toMove.getName(), "ENGINE", alphaZeta.getPlayables())) {
            throw new InputException(ERROR_COMMAND_NOT_EXECUTABLE);
        }
        if (!errorCheck.moveInRange(target, current)) {
            throw new InputException(ERROR_NOT_IN_RANGE);
        }

        if (toMove.isDestroyed()) {
            throw new InputException(ERROR_SHIP_DESTROYED);
        }

        if (errorCheck.isOccupied(alphaZeta.getField(), target.getX(), target.getY())) {
            throw new InputException(ERROR_TILE_NOT_EMPTY);
        }
    }

    /**
     * Checks if move command is executable.
     * 
     * @param toMove    Spaceship to move
     * @param alphaZeta The instance of a system to be manipulated
     * @param target    Target position
     * @param current   Current position of a spaceship
     */
    protected void propelError(Spaceship toMove, AlphaZeta alphaZeta, Position target, Position current)
            throws InputException {
        ErrorCheck errorCheck = new ErrorCheck();

        if (!errorCheck.isAttacking(toMove, alphaZeta.getPlaying())) {
            throw new InputException(ERROR_FALSE_TURN);
        }
        if (!errorCheck.canBePlayed(toMove.getName(), "PROPULSION", alphaZeta.getPlayables())) {
            throw new InputException(ERROR_COMMAND_NOT_EXECUTABLE);
        }
        if (!errorCheck.moveInRange(target, current)) {
            throw new InputException(ERROR_NOT_IN_RANGE);
        }

        if (toMove.isDestroyed()) {
            throw new InputException(ERROR_SHIP_DESTROYED);
        }

        if (errorCheck.isOccupied(alphaZeta.getField(), target.getX(), target.getY())) {
            throw new InputException(ERROR_TILE_NOT_EMPTY);
        }
    }

    /**
     * Checks if ramm command is executable.
     * 
     * @param attacker  Attacker ship
     * @param defender  Defender ship
     * @param alphaZeta The instance of a system to be manipulated
     */
    protected void rammError(Spaceship attacker, Spaceship defender, AlphaZeta alphaZeta) throws InputException {
        ErrorCheck errorCheck = new ErrorCheck();
        if (!errorCheck.isAttacking(attacker, alphaZeta.getPlaying())) {
            throw new InputException(ERROR_FALSE_TURN);
        }
        if (attacker.isDestroyed() || defender.isDestroyed()) {
            throw new InputException(ERROR_SHIP_DESTROYED);
        }
        if (errorCheck.isAttacking(defender, alphaZeta.getPlaying())) {
            throw new InputException(ERROR_SAME_FLEET);
        }
        if (!alphaZeta.getPlayables().get(attacker.getName()).contains("RAMM")) {
            throw new InputException(ERROR_COMMAND_NOT_EXECUTABLE);
        }

        if (!errorCheck.rammInRange(defender.getPosition(), attacker.getPosition())) {
            throw new InputException(ERROR_NOT_IN_RANGE);
        }

        if (defender.equals(defender)) {
            throw new InputException(ERROR_RAMM);
        }

        if (!attacker.hasNoWeapon()) {
            throw new InputException(ERROR_COMMAND_NOT_EXECUTABLE);
        }
    }

    /**
     * Converts a list of modules to a single line string.
     * 
     * @param modules List of modules
     * @return list as a string
     */
    protected static String toString(List<Modulus> modules) {

        List<String> moduleNames = new ArrayList<String>();
        for (Modulus module : modules) {
            moduleNames.add(module.getName());
        }
        Collections.sort(moduleNames);

        return moduleNames.stream().map(Object::toString).collect(Collectors.joining(", "));
    }

    /**
     * Make a ship to lose two of it's modules. If it already has 2 modules, it will
     * destroy the ship.
     * 
     * @param loser     Spaceship to destroy modules
     * @param alphaZeta The instance of a system to be manipulated
     */
    protected static void loseTwoModules(Spaceship loser, AlphaZeta alphaZeta) throws InputException {
        List<Modulus> modules = loser.getModules();
        if (modules.size() <= 2) {
            destroyShip(loser, alphaZeta);
            return;
        }
        if (modules.size() == 3) {
            for (Modulus module : modules) {
                if (!module.getName().equals("ENGINE")) {
                    System.out.println(loser.getName() + " looses " + module.getName());
                    alphaZeta.removeModule(module, loser);
                }
            }
            return;
        }

        chooseModuleToRemove(loser, alphaZeta);
        chooseModuleToRemove(loser, alphaZeta);
    }

    /**
     * Make a ship to lose one of it's modules. If it already has 1 module, it will
     * destroy the ship.
     * 
     * @param loser     Spaceship to destroy modules
     * @param alphaZeta The instance of a system to be manipulated
     */
    protected static void loseOneModule(Spaceship loser, AlphaZeta alphaZeta) throws InputException {
        List<Modulus> modules = loser.getModules();
        if (modules.size() <= 1) {
            destroyShip(loser, alphaZeta);
            return;
        }
        if (modules.size() == 2) {
            for (Modulus module : modules) {
                if (!module.getName().equals("ENGINE")) {
                    System.out.println(loser.getName() + " looses " + module.getName());
                    alphaZeta.removeModule(module, loser);
                }
            }
            return;
        }

        chooseModuleToRemove(loser, alphaZeta);
    }

    /**
     * Prints the critical hit message (For lost of 1 module).
     * 
     * @param defender  Defender ship
     * @param alphaZeta The instance of a system to be manipulated
     */
    protected static void criticalHitOne(Spaceship defender, AlphaZeta alphaZeta) throws InputException {
        List<Modulus> modules = formatModules(defender.getModules());
        System.out.println(alphaZeta.getPlaying().getName() + MESSAGE_MUST_SELECT + ONE + defender.getName()
                + MODULE_SELECT + toString(modules));

    }

    /**
     * Prints the critical hit message (For lost of 2 modules).
     * 
     * @param defender  Defender ship
     * @param alphaZeta The instance of a system to be manipulated
     */
    protected static void criticalHitTwo(Spaceship defender, AlphaZeta alphaZeta) throws InputException {
        List<Modulus> modules = formatModules(defender.getModules());
        System.out.println(alphaZeta.getPlaying().getName() + MESSAGE_MUST_SELECT + TWO + MODULE_SELECT
                + defender.getName() + toString(modules));

    }

    /**
     * Applies the bonus to a dice.
     * 
     * @param old      Rolled dice without bonus.
     * @param game     The instance of a system to be manipulated
     * @param attacker Attacker ship
     * @param defender Defender ship
     * @return bonus applied value
     */
    protected static int applyBonus(int old, AlphaZeta game, Spaceship attacker, Spaceship defender) {
        int bonus = game.calculateCover(attacker.getPosition(), defender.getPosition());
        if (defender.containsShield()) {
            bonus++;
        }
        System.out.println("Roll " + old);
        System.out.println("Bonus " + -bonus);

        int applied = old - bonus;

        return applied;
    }

    /**
     * Destroys a ship
     * 
     * @param ship Spaceship to be destroyed
     * @param game The instance of a system to be manipulated
     */
    protected static void destroyShip(Spaceship ship, AlphaZeta game) {
        for (Modulus module : ship.getModules()) {
            System.out.println(ship.getName() + " looses " + module.getName());
        }
        game.destroyShip(ship);

        System.out.println(ship.getName() + " was destroyed");
    }

    /**
     * Formats a list of modules. It removes the engine from the list.
     * 
     * @param defender List of modules of the defender.
     * @return formatted list
     */
    protected static List<Modulus> formatModules(List<Modulus> defender) {
        List<Modulus> modules = defender;
        for (int i = 0; i < modules.size(); i++) {
            if (modules.get(i).getName().equals("ENGINE")) {
                modules.remove(i);
                break;
            }
        }
        return modules;
    }

    /**
     * Allows user to choose which modules will be destroyed.
     * 
     * @param ship      Spaceship whose modules will be destroyed.
     * @param alphaZeta The instance of a system to be manipulated
     */
    protected static void chooseModuleToRemove(Spaceship ship, AlphaZeta alphaZeta) throws InputException {
        /*
         * If the ship is a collector, it destroys container without user input.
         */
        if (ship.equals(ship)) {
            alphaZeta.removeModule(new Container(), ship);
            System.out.println(ship.getName() + " looses CONTAINER");
            return;
        }
        /*
         * If the spaceship has only one type of module except engine, it destroys it
         * without asking user.
         */
        ErrorCheck errorCheck = new ErrorCheck();
        if (errorCheck.hasOnlyOneModule(ship)) {
            Modulus moduleToRemove = ship.getModules().get(1);
            alphaZeta.removeModule(moduleToRemove, ship);
            System.out.println(ship.getName() + " looses " + moduleToRemove.getName());
            return;
        }
        /*
         * Here it asks the user which modules will be removed.
         */
        boolean check = false;
        Modulus module = null;
        do {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine().toUpperCase();
            try {
                module = getModule(input);
                if (!ship.hasModule(module)) {
                    throw new InputException("");
                }
                alphaZeta.removeModule(module, ship);
                check = true;
            } catch (InputException exception) {
                System.out.println(ERROR_MODULE);
            }

        } while (!check);
        System.out.println(ship.getName() + " looses " + module.getName());

    }

    /**
     * Checks if a spaceship is null.
     * 
     * @param ship Spaceship to check.
     */
    protected static void nullCheck(Spaceship ship) throws InputException {
        if (ship == null) {
            throw new InputException(ERROR_SHIP_NOT_FOUND);
        }
    }

    /**
     * Gets a module depending on it's name.
     * 
     * @param name
     * @return Module with the searched name.
     */
    protected static Modulus getModule(String name) throws InputException {
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
     * Returns a list of actions from module list.
     * 
     * @param modules
     * @return list of actions
     */
    protected static List<String> extractActions(List<Modulus> modules) {
        List<String> toGet = new ArrayList<String>();
        for (Modulus module : modules) {
            toGet.add(module.getAction());
        }
        return toGet;
    }

    /**
     * Returns actions of a spaceship.
     * 
     * @param actions
     * @param ship
     * @return actions of a spaceship.
     */
    protected static final String availableActions(List<String> actions, Spaceship ship) {
        if (actions.isEmpty()) {
            return NO_ACTIONS + ship.getName();
        }

        return ACTIONS_OF + ship.getName() + ": "
                + actions.stream().map(Object::toString).collect(Collectors.joining(", "));
    }
}