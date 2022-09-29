package system.interaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.MatchResult;

import ai.AI;
import ai.Fleet;
import ai.Position;
import ai.occupier.Cover;
import ai.occupier.CoverCheck;
import ai.occupier.Occupier;
import ai.occupier.spaceship.Attackership;
import ai.occupier.spaceship.Spaceship;
import errorCheck.ErrorCheck;
import module.Modulus;
import system.AlphaZeta;

/**
 * This class is for shorten the main command class and provides methods for
 * corresponding commands.
 * 
 * @author Emir Yuksel
 * @version 1.0
 *
 */
public class CommandMethods extends CommandMethodsHelper {
    private static final String TURN_CHANGE = "'s turn";
    private static final String HELP_OUTPUT = "HELP - shows this text\r\n" + "QUIT - quits the game\n"
            + "BOARD - shows the whole board\r\n" + "FLEET - shows details of all ships of the AIs\n"
            + "ACTIONS [NAME] - shows available actions of ship [NAME]\n" + "ENDTURN - ends the current player's turn\n"
            + "[SHIP] [ACTION] [X] [Y] - let [SHIP] perform [ACTION] on field (X,Y)\n"
            + "[SHIP] [ACTION] [TARGET] - let [SHIP] perform [ACTION] on [TARGET]";
    private static final String FLEET_ALPHA = "Alpha's fleet";
    private static final String FLEET_ZETA = "Zeta's fleet";
    private static final String MARKER_COLLECTOR = "<C> ";
    private static final String MARKER_INTACT = "<-> ";
    private static final String MARKER_DESTROYED = "~X~ ";

    private static final int ACTION_PARAMETER_ATTACKER = 1;
    private static final int ACTION_PARAMETER_DEFENDER = 3;

    private static final String MESSAGE_MUST_SELECT = " must select ";
    private static final String ONE = "1 of ";
    private static final String TWO = "2 of ";
    private static final String MODULE_SELECT = "s module(s) (separated by comma):\n";
    private static final String MISSED = "missed";
    private static final String ERROR_SHIP_DESTROYED = "this ship is destroyed.";

    private static final int MOVE_PARAMETER_SHIP = 1;
    private static final int MOVE_PARAMETER_X = 3;
    private static final int MOVE_PARAMETER_Y = 4;

    private static final int ACTION_PARAMETER_SHIP = 2;

    private static final int LONGSHOT_CRITICAL_MIN = 4;
    private static final int LONGSHOT_CRITICAL_MAX = 6;
    private static final int LONGSHOT_NORMAL_MIN = 2;
    private static final int LONGSHOT_NORMAL_MAX = 3;

    private static final int STRIKE_NORMAL_MIN = 2;
    private static final int STRIKE_NORMAL_MAX = 4;

    /**
     * Command to end a turn.
     * 
     * @param matcher   The regex matcher
     * @param alphaZeta The instance of a system to be manipulated
     */
    protected void endTurn(MatchResult matcher, AlphaZeta alphaZeta) throws InputException {
        alphaZeta.changeTurn();
        System.out.println(alphaZeta.getPlaying().getName() + TURN_CHANGE);
    }

    /**
     * Command to help a player.
     * 
     * @param matcher   The regex matcher
     * @param alphaZeta The instance of a system to be manipulated
     */
    protected void help(MatchResult matcher, AlphaZeta alphaZeta) throws InputException {
        System.out.println(HELP_OUTPUT);

    }

    /**
     * Command to print fleets of AI's.
     * 
     * @param matcher   The regex matcher
     * @param alphaZeta The instance of a system to be manipulated
     */
    protected void fleet(MatchResult matcher, AlphaZeta alphaZeta) throws InputException {
        AI[] ai = { alphaZeta.getPlaying(), alphaZeta.getDefender() };

        Fleet fleetAlpha = null;
        Fleet fleetZeta = null;

        for (AI toPrint : ai) {
            if (toPrint.getName().equals("Alpha")) {
                fleetAlpha = toPrint.getFleet();
            }
            if (toPrint.getName().equals("Zeta")) {
                fleetZeta = toPrint.getFleet();
            }
        }
        System.out.println(FLEET_ALPHA);
        printFleet(fleetAlpha);

        System.out.println(FLEET_ZETA);
        printFleet(fleetZeta);

    }

    /**
     * Command to mark a ship. Every attack to a marked ship is critical hit.
     * 
     * @param matcher   The regex matcher
     * @param alphaZeta The instance of a system to be manipulated
     */
    protected void mark(MatchResult matcher, AlphaZeta alphaZeta) throws InputException {

        Spaceship attacker = alphaZeta.getShipByName(matcher.group(ACTION_PARAMETER_ATTACKER).toUpperCase());
        Spaceship defender = alphaZeta.getShipByName(matcher.group(ACTION_PARAMETER_DEFENDER).toUpperCase());

        nullCheck(attacker);
        nullCheck(defender);
        markError(attacker, defender, alphaZeta);
        alphaZeta.mark((Attackership) defender, attacker);

    }

    /**
     * Command to move a ship.
     * 
     * @param matcher   The regex matcher
     * @param alphaZeta The instance of a system to be manipulated
     */
    protected void move(MatchResult matcher, AlphaZeta alphaZeta) throws InputException {

        String ship = matcher.group(MOVE_PARAMETER_SHIP);
        String x = matcher.group(MOVE_PARAMETER_X);
        String y = matcher.group(MOVE_PARAMETER_Y);

        Spaceship toMove = alphaZeta.getShipByName(ship.toUpperCase());
        nullCheck(toMove);

        Position current = toMove.getPosition();
        Position target = new Position(Integer.valueOf(y), Integer.valueOf(x));
        moveError(toMove, alphaZeta, target, current);
        alphaZeta.move(toMove, target.getY(), target.getX());

    }

    /**
     * Command to use propulsion for extra move.
     * 
     * @param matcher   The regex matcher
     * @param alphaZeta The instance of a system to be manipulated
     */
    protected void propulsion(MatchResult matcher, AlphaZeta alphaZeta) throws InputException {

        String ship = matcher.group(MOVE_PARAMETER_SHIP);
        String x = matcher.group(MOVE_PARAMETER_X);
        String y = matcher.group(MOVE_PARAMETER_Y);

        Spaceship toMove = alphaZeta.getShipByName(ship.toUpperCase());
        nullCheck(toMove);

        Position current = toMove.getPosition();
        Position target = new Position(Integer.valueOf(y), Integer.valueOf(x));
        propelError(toMove, alphaZeta, target, current);
        alphaZeta.propel(toMove, target.getY(), target.getX());
    }

    /**
     * Command to ramm a ship.
     * 
     * @param matcher   The regex matcher
     * @param alphaZeta The instance of a system to be manipulated
     */
    protected void ramm(MatchResult matcher, AlphaZeta alphaZeta) throws InputException {
        Spaceship attacker = alphaZeta.getShipByName(matcher.group(ACTION_PARAMETER_ATTACKER).toUpperCase());
        Spaceship defender = alphaZeta.getShipByName(matcher.group(ACTION_PARAMETER_DEFENDER).toUpperCase());

        nullCheck(attacker);
        nullCheck(defender);
        rammError(attacker, defender, alphaZeta);
        int dice = alphaZeta.roll();
        dice = applyBonus(dice, alphaZeta, attacker, defender);

        if (dice <= 1) {
            if (attacker.getModules().size() > 2) {
                System.out.println(alphaZeta.getPlaying().getName() + MESSAGE_MUST_SELECT + ONE + attacker.getName()
                        + MODULE_SELECT + toString(formatModules(attacker.getModules())));
            }
            loseOneModule(attacker, alphaZeta);
        } else if (dice >= 5) {
            if (defender.getModules().size() > 2) {
                System.out.println(alphaZeta.getDefender().getName() + MESSAGE_MUST_SELECT + ONE + defender.getName()
                        + MODULE_SELECT + toString(formatModules(defender.getModules())));
            }
            loseOneModule(defender, alphaZeta);
        } else {
            System.out.println(MISSED);
        }
        alphaZeta.ramm(attacker);

    }

    /**
     * Command to print available actions of a ship.
     * 
     * @param matcher   The regex matcher
     * @param alphaZeta The instance of a system to be manipulated
     */
    protected void actions(MatchResult matcher, AlphaZeta alphaZeta) throws InputException {
        ErrorCheck error = new ErrorCheck();

        Spaceship ship = alphaZeta.getShipByName(matcher.group(ACTION_PARAMETER_SHIP).toUpperCase());
        if (ship.isDestroyed()) {
            throw new InputException(ERROR_SHIP_DESTROYED);
        }
        List<String> actions = new ArrayList<String>();
        if (!error.isAttacking(ship, alphaZeta.getPlaying())) {
            List<Modulus> modules = ship.getModules();
            modules.removeIf(m -> (m.getName().equals("CONTAINER") || m.getName().equals("SHIELD")));
            actions = extractActions(modules);
            actions.add("RAMM");

        } else {
            List<String> modules = alphaZeta.getPlayables().get(ship.getName());
            modules.removeIf(n -> (n.equals("SHIELD") || n.equals("CONTAINER")));
            for (String module : modules) {
                if (module.equals("ENGINE")) {
                    actions.add("MOVE");
                } else if (module.equals("RAMM")) {
                    actions.add("RAMM");
                } else {
                    actions.add(getModule(module).getAction());
                }
            }
        }
        Collections.sort(actions);
        System.out.println(availableActions(actions, ship));

    }

    /**
     * Command to print the current situation of the game board.
     * 
     * @param matcher   The regex matcher
     * @param alphaZeta The instance of a system to be manipulated
     */
    protected void board(MatchResult matcher, AlphaZeta alphaZeta) throws InputException {
        CoverCheck check = new CoverCheck();
        for (Occupier[] row : alphaZeta.getField()) {

            for (Occupier occupier : row) {
                if (occupier == null) {
                    System.out.print(".");
                } else if (check.equals(occupier)) {
                    Cover cover = (Cover) occupier;
                    System.out.print(cover.getValue());
                } else {
                    Spaceship ship = (Spaceship) occupier;
                    System.out.print(ship.getName());
                }
            }
            System.out.println();
        }
    }

    /**
     * Command to use a long range missile of a ship.
     * 
     * @param matcher   The regex matcher
     * @param alphaZeta The instance of a system to be manipulated
     */
    protected void longshot(MatchResult matcher, AlphaZeta alphaZeta) throws InputException {
        Spaceship attacker = alphaZeta.getShipByName(matcher.group(ACTION_PARAMETER_ATTACKER).toUpperCase());
        Spaceship defender = alphaZeta.getShipByName(matcher.group(ACTION_PARAMETER_DEFENDER).toUpperCase());

        nullCheck(attacker);
        nullCheck(defender);
        ErrorCheck errorCheck = new ErrorCheck();
        longShotError(attacker, defender, alphaZeta);

        int dice = alphaZeta.roll();
        dice = applyBonus(dice, alphaZeta, attacker, defender);
        List<Modulus> modules = defender.getModules();
        if (defender.isMarked()) {
            if (modules.size() > 3 && !errorCheck.hasOnlyOneModule(defender)) {
                criticalHitTwo(defender, alphaZeta);
            }
            loseTwoModules(defender, alphaZeta);
            return;
        }
        if (dice >= LONGSHOT_NORMAL_MIN && dice <= LONGSHOT_NORMAL_MAX) {
            if (modules.size() > 2 && !errorCheck.hasOnlyOneModule(defender)) {
                System.out.println(alphaZeta.getDefender().getName() + MESSAGE_MUST_SELECT + ONE + defender.getName()
                        + MODULE_SELECT + toString(formatModules(modules)));
            }
            loseOneModule(defender, alphaZeta);
        } else if (dice >= LONGSHOT_CRITICAL_MIN && dice < LONGSHOT_CRITICAL_MAX) {
            if (modules.size() > 3 && !errorCheck.hasOnlyOneModule(defender)) {
                System.out.println(alphaZeta.getDefender().getName() + MESSAGE_MUST_SELECT + TWO + defender.getName()
                        + MODULE_SELECT + toString(formatModules(modules)));
            }
            loseTwoModules(defender, alphaZeta);
        } else if (dice >= LONGSHOT_CRITICAL_MAX) {
            if (modules.size() > 3 && !errorCheck.hasOnlyOneModule(defender)) {
                criticalHitTwo(defender, alphaZeta);
            }
            loseTwoModules(defender, alphaZeta);
        } else {
            System.out.println(MISSED);

        }
        alphaZeta.longShot(attacker);

    }

    /**
     * Command to strike to a ship.
     * 
     * @param matcher   The regex matcher
     * @param alphaZeta The instance of a system to be manipulated
     */
    protected void strike(MatchResult matcher, AlphaZeta alphaZeta) throws InputException {
        Spaceship attacker = alphaZeta.getShipByName(matcher.group(ACTION_PARAMETER_ATTACKER).toUpperCase());
        Spaceship defender = alphaZeta.getShipByName(matcher.group(ACTION_PARAMETER_DEFENDER).toUpperCase());

        nullCheck(attacker);
        nullCheck(defender);

        ErrorCheck errorCheck = new ErrorCheck();

        strikeError(attacker, defender, alphaZeta);

        int dice = alphaZeta.roll();
        dice = applyBonus(dice, alphaZeta, attacker, defender);
        List<Modulus> modules = defender.getModules();
        if (defender.isMarked()) {
            if (modules.size() > 2 && !errorCheck.hasOnlyOneModule(defender)) {
                criticalHitOne(defender, alphaZeta);
            }
            loseOneModule(defender, alphaZeta);
            return;
        } else if (dice >= STRIKE_NORMAL_MIN && dice < STRIKE_NORMAL_MAX) {
            if (modules.size() > 2 && !errorCheck.hasOnlyOneModule(defender)) {
                System.out.println(alphaZeta.getDefender().getName() + MESSAGE_MUST_SELECT + ONE + defender.getName()
                        + MODULE_SELECT + toString(formatModules(modules)));
            }
            loseOneModule(defender, alphaZeta);
        } else if (dice >= STRIKE_NORMAL_MAX) {
            if (modules.size() > 2 && !errorCheck.hasOnlyOneModule(defender)) {
                criticalHitOne(defender, alphaZeta);
            }
            loseOneModule(defender, alphaZeta);
        } else {
            System.out.println(MISSED);

        }
        alphaZeta.strike(attacker);

    }

    /**
     * Command to print fleets of AI's.
     * 
     * @param fleet Fleet to be printed.
     */
    private static void printFleet(Fleet fleet) {
        List<Spaceship> destroyedShipsZeta = new ArrayList<Spaceship>();
        for (Spaceship zeta : fleet.getShips()) {

            if (!zeta.isDestroyed()) {
                List<Modulus> modules = zeta.getModules();
                String name = zeta.getName();

                if (name.equals("Z") || name.equals("A")) {

                    System.out.println(MARKER_COLLECTOR + name + ":" + toString(modules));

                } else {
                    System.out.println(MARKER_INTACT + name + ":" + toString(modules));
                }
            } else {
                destroyedShipsZeta.add(zeta);
            }
        }
        for (Spaceship destroyed : destroyedShipsZeta) {
            System.out.println(MARKER_DESTROYED + destroyed.getName());
        }
    }

}
