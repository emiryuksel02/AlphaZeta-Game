package system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import ai.AI;
import ai.Position;
import ai.occupier.Cover;
import ai.occupier.Occupier;
import ai.occupier.spaceship.Attackership;
import ai.occupier.spaceship.Spaceship;
import errorCheck.ErrorCheck;
import module.Modulus;

/**
 * System of the game.
 * 
 * @author Emir Yuksel
 * @version 1.0
 *
 */
public class AlphaZeta {
    private static final String RAILGUN = "RAILGUN";
    private static final String SWORD = "SWORD";
    private static final String ENGINE = "ENGINE";
    private static final String PROPULSION = "PROPULSION";
    private static final String SENSOR = "SENSOR";
    private static final String RAMM = "RAMM";

    private static final int BOUND = 6;

    private Random random;

    private Map<String, List<String>> playables = new HashMap<>();
    private Occupier[][] matchField;
    private AI alpha;
    private AI zeta;

    private AI playing;
    private AI defender;

    private int turn = 0;

    /**
     * Initializes the game depending of the input.
     * 
     * @param matchField board of the game
     * @param alpha      Alpha AI
     * @param zeta       Zeta AI
     * @param seed       Seed defined in the beginning
     */
    public void init(Occupier[][] matchField, AI alpha, AI zeta, int seed) {
        this.matchField = matchField;
        this.alpha = alpha;
        this.zeta = zeta;
        this.random = new Random(seed);
    }

    /**
     * Gets the AI Alpha
     * 
     * @return the alpha
     */
    public AI getAlpha() {
        return alpha;
    }

    /**
     * Gets the AI Zeta
     * 
     * @return the zeta
     */
    public AI getZeta() {
        return zeta;
    }

    /**
     * Gets the attacker AI.
     * 
     * @return playing
     */
    public AI getPlaying() {
        return playing;
    }

    /**
     * Gets the defender AI
     * 
     * @return defender
     */
    public AI getDefender() {
        return defender;
    }

    /**
     * Removes a module from a spaceship.
     * 
     * @param module Module to remove
     * @param ship   Spaceship to remove a module
     */
    public void removeModule(Modulus module, Spaceship ship) {
        ErrorCheck errorCheck = new ErrorCheck();
        if (errorCheck.isAttacking(ship, this.playing)) {
            for (Spaceship damagedShip : this.playing.getFleet().getShips()) {
                if (damagedShip.getName().equals(ship.getName())) {
                    this.playing.removeModule(ship, module);
                    return;
                }
            }
        } else {
            for (Spaceship damagedShip : this.defender.getFleet().getShips()) {
                if (damagedShip.getName().equals(ship.getName())) {
                    this.defender.removeModule(ship, module);
                    return;
                }
            }
        }

    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Cover;
    }

    /**
     * Calculates cover value of a tile. Only direct neighbors are relevant for the
     * calculation.
     * 
     * @param current Position of attacker.
     * @param target  Position of defender.
     * @return cover value
     */
    public int calculateCover(Position current, Position target) {

        int currentY = current.getY();
        int targetY = target.getY();

        int currentX = current.getX();
        int targetX = target.getX();
        List<Integer> coverValues = new ArrayList<Integer>();

        if (currentY < targetY) {
            if (equals(this.matchField[targetY - 1][targetX])) {

                Cover north = (Cover) this.matchField[targetY - 1][targetX];
                coverValues.add(north.getValue());
            }
        }

        if (currentY > targetY) {
            if (equals(this.matchField[targetY + 1][targetX])) {

                Cover south = (Cover) this.matchField[targetY + 1][targetX];
                coverValues.add(south.getValue());
            }
        }

        if (currentX < targetX) {
            if (equals(this.matchField[targetY][targetX - 1])) {

                Cover west = (Cover) this.matchField[targetY][targetX - 1];
                coverValues.add(west.getValue());
            }
        }

        if (currentX > targetX) {
            if (equals(this.matchField[targetY][targetX + 1])) {

                Cover east = (Cover) this.matchField[targetY][targetX + 1];
                coverValues.add(east.getValue());
            }
        }
        if (coverValues.isEmpty()) {

            return 0;
        }
        return Collections.max(coverValues);
    }

    /**
     * Rolls a dice.
     * 
     * @return dice
     */
    public int roll() {
        return random.nextInt(BOUND) + 1;
    }

    /**
     * Starts a new turn and resets playables. After that it initializes playables
     * depending on current attacker.
     */
    public void startNewTurn() {
        this.playables.clear();
        if (turn == 1) {
            this.playing = zeta;
            this.defender = alpha;
            initPlayables();

        }

        if (turn == 0) {
            this.playing = alpha;
            this.defender = zeta;
            initPlayables();
        }
    }

    /**
     * Changes the turn. Markings will be cleared for every change. It starts a new
     * turn at the end.
     */
    public void changeTurn() {
        this.playing.clearMark();
        this.defender.clearMark();
        if (this.turn == 1) {
            zeta = this.playing;
            alpha = this.defender;
            turn--;
            startNewTurn();
            return;

        }
        alpha = this.playing;
        zeta = this.defender;
        turn++;
        startNewTurn();

    }

    /**
     * Initializes playable modules. Only intact ships and modules will be added.
     */
    private void initPlayables() {
        for (Spaceship ship : this.playing.getFleet().getShips()) {
            if (!ship.isDestroyed()) {
                List<String> moduleNames = new ArrayList<String>();
                for (Modulus module : ship.getModules()) {

                    moduleNames.add(module.getName());
                }
                moduleNames.add(RAMM);
                playables.put(ship.getName(), moduleNames);
            }
        }
    }

    /**
     * Removes a module from playables.
     * 
     * @param playable Module to remove.
     * @param key      Holds the name of the spaceship
     */
    private void removePlayable(String playable, String key) {
        List<String> values = this.playables.get(key);
        int index = values.indexOf(playable);
        values.remove(index);
        playables.put(key, values);
    }

    /**
     * Changes the position of a spaceship and updates the current match field
     * depending on it.
     * 
     * @param ship Spaceship to move.
     * @param y    Coordinate on y-axis
     * @param x    Coordinate on x-axis
     */
    public void move(Spaceship ship, int y, int x) {

        Position position = ship.getPosition();
        this.matchField[position.getY()][position.getX()] = null;
        this.playing.move(ship, y, x);
        this.matchField[y][x] = ship;
        removePlayable(ENGINE, ship.getName());

    }

    /**
     * Changes the position of a spaceship and updates the current match field
     * depending on it.
     * 
     * @param ship Spaceship to move.
     * @param y    Coordinate on y-axis
     * @param x    Coordinate on x-axis
     */
    public void propel(Spaceship ship, int y, int x) {

        Position position = ship.getPosition();
        this.matchField[position.getY()][position.getX()] = null;
        this.playing.move(ship, y, x);
        this.matchField[y][x] = ship;
        removePlayable(PROPULSION, ship.getName());

    }

    /**
     * Removes ramm from playables for current turn.
     * 
     * @param ship Spaceship which uses the module for this action.
     */
    public void ramm(Spaceship ship) {
        removePlayable(RAMM, ship.getName());
    }

    /**
     * Removes rail gun module from playables for current turn.
     * 
     * @param ship Spaceship which uses the module for this action.
     */
    public void longShot(Spaceship ship) {
        removePlayable(RAILGUN, ship.getName());
    }

    /**
     * Removes sword module from playables for current turn.
     * 
     * @param ship Spaceship which uses the module for this action.
     */
    public void strike(Spaceship ship) {
        removePlayable(SWORD, ship.getName());
    }

    /**
     * Removes sensor module from playables for current turn.
     * 
     * @param ship     Spaceship which is marked.
     * @param attacker Spaceship which uses the module for this action.
     */
    public void mark(Attackership ship, Spaceship attacker) {
        removePlayable(SENSOR, attacker.getName());
        this.defender.mark(ship);

    }

    /**
     * Checks if the game is finished.
     * 
     * @return true if an AI has lost, false otherwise.
     */
    public boolean gameOver() {
        if (this.defender.hasLost() || this.playing.hasLost()) {
            return true;
        }
        return false;
    }

    /**
     * Gets the winner of the game.
     * 
     * @return winner AI
     */
    public AI getWinner() {
        if (this.defender.hasLost()) {
            return this.playing;
        }

        return this.defender;

    }

    /**
     * Gets the match field.
     * 
     * @return game board.
     */
    public Occupier[][] getField() {
        return this.matchField;
    }

    /**
     * Gets the map which contains playable ships and modules.
     * 
     * @return playables
     */
    public Map<String, List<String>> getPlayables() {
        return this.playables;
    }

    /**
     * Returns a spaceship depending on the given name.
     * 
     * @param name Name to search
     * @return Spaceship which has the given name.
     */
    public Spaceship getShipByName(String name) {
        List<Spaceship> allShips = new ArrayList<Spaceship>();

        allShips.addAll(this.alpha.getFleet().getShips());
        allShips.addAll(this.zeta.getFleet().getShips());
        Spaceship searched = null;
        for (Spaceship ship : allShips) {
            if (ship.getName().equals(name)) {
                searched = ship;
                break;
            }
        }
        return searched;
    }

    /**
     * Destroys a ship.
     * 
     * @param ship Spaceship to destroy.
     */
    public void destroyShip(Spaceship ship) {
        Position position = ship.getPosition();
        this.matchField[position.getY()][position.getX()] = null;
        ErrorCheck check = new ErrorCheck();
        if (check.isAttacking(ship, this.playing)) {
            this.playing.destroyShip(ship);
            return;
        }

        this.defender.destroyShip(ship);
    }

}