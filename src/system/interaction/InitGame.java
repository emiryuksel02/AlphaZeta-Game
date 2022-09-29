package system.interaction;

import java.util.Arrays;
import java.util.List;

import ai.AI;
import ai.Fleet;
import ai.Position;
import ai.occupier.Cover;
import ai.occupier.Occupier;
import ai.occupier.spaceship.Attackership;
import ai.occupier.spaceship.Collector;
import ai.occupier.spaceship.Spaceship;
import module.Modulus;
import system.AlphaZeta;

/**
 * This class configures the game depending on user inputs.
 * 
 * @author Emir Yuksel
 * @version 1.0
 *
 */
public class InitGame extends InitGameCheck {

    private static final int NUMBERSHIPS = 8;
    private static final int POS_A = 0;
    private static final int POS_Z = 1;
    private static final int POS_B = 2;
    private static final int POS_C = 3;
    private static final int POS_D = 4;
    private static final int POS_W = 5;
    private static final int POS_X = 6;
    private static final int POS_Y = 7;
    private static final int MIN_LENGTH = 25;
    private static final String POINT = ".";
    private static final String ALPHA = "Alpha ";
    private static final String CONFIGURATION = "configures its ships\n";
    private static final String SELECTABLE_MODULES = "PROPULSION, RAILGUN, SENSOR, SHIELD, SWORD";
    private static final String CHOOSE_MODULE = "Choose upto 3 modules for ship ";
    private static final String SEPARATED_BY = " (separated by comma):\n";
    private static final String ERROR_INVALID_INPUT = "invalid input.";
    private static final String ERROR_SENSOR_ALREADY_ADD = "Sensor is already module of Ship ";
    private static final String START = "Welcome to AlphaZeta!\n" + ALPHA + CONFIGURATION;
    private static final String LAYOUT_LENGTH = "The string must be 25 characters long";
    private static final String NOT_SYMMETRICAL = "board should be symmetrical";
    private static final String SHIP_PLACE = "you should place each ship one time.";
    private static final String TILE_NEIGHBOR = "no empty tile can be completely surrounded by cover.";
    private static final String BOARD_SHOULD_MATCH = "the length of the board should match the "
            + "parameter for board length given in the beginning.";

    private final int seed;

    private String sensorShipAlpha = null;
    private String sensorShipZeta = null;

    private Spaceship a;

    private Spaceship b;
    private boolean bConfigured = false;

    private Spaceship c;
    private boolean cConfigured = false;

    private Spaceship d;
    private boolean dConfigured = false;

    private Spaceship w;
    private boolean wConfigured = false;
    private Spaceship x;
    private boolean xConfigured = false;

    private Spaceship y;
    private boolean yConfigured = false;

    private Spaceship z;

    private Occupier[][] matchField;
    private boolean matchFieldConfigured = false;

    private AlphaZeta alphaZeta;

    /**
     * Creates new InitGame.
     * 
     * @param alphaZeta
     * @param seed
     */
    public InitGame(AlphaZeta alphaZeta, int seed) {
        this.seed = seed;
        this.alphaZeta = alphaZeta;
    }

    /**
     * Gets the initialized game
     * 
     * @return alphaZeta
     */
    public AlphaZeta getGame() {
        return this.alphaZeta;
    }

    /**
     * Returns a welcome message and configures Collectors.
     * 
     * @param containers number of containers
     * @return welcome message
     */
    public String welcomeMessage(int containers) {

        configureA(containers);
        configureZ(containers);
        return START + CHOOSE_MODULE + "B" + SEPARATED_BY + SELECTABLE_MODULES;

    }

    /**
     * Configures Collector A
     * 
     * @param parameter number of containers
     */
    public void configureA(int parameter) {
        this.a = new Collector(parameter, "A");
    }

    /**
     * Configures Collector Z
     * 
     * @param parameter number of containers
     */
    public void configureZ(int parameter) {
        this.z = new Collector(parameter, "Z");
    }

    /**
     * Configures Attackership B
     * 
     * @param input
     */
    public void configureB(String input) throws InputException {

        List<Modulus> modulesToAdd = configureInput(input);

        if (input.contains("SENSOR")) {
            if (this.sensorShipAlpha != null) {
                throw new InputException(ERROR_SENSOR_ALREADY_ADD + this.sensorShipAlpha);
            }
            this.sensorShipAlpha = "B";
        }

        this.b = new Attackership("B", modulesToAdd);

        this.bConfigured = true;
    }

    /**
     * Configures Attackership C
     * 
     * @param input
     */
    public void configureC(String input) throws InputException {

        List<Modulus> modulesToAdd = configureInput(input);
        if (input.contains("SENSOR")) {
            if (this.sensorShipAlpha != null) {
                throw new InputException(ERROR_SENSOR_ALREADY_ADD + this.sensorShipAlpha);
            }
            this.sensorShipAlpha = "C";
        }
        this.c = new Attackership("C", modulesToAdd);
        this.cConfigured = true;

    }

    /**
     * Configures Attackership D
     * 
     * @param input
     */
    public void configureD(String input) throws InputException {
        List<Modulus> modulesToAdd = configureInput(input);
        if (input.contains("SENSOR")) {
            if (this.sensorShipAlpha != null) {
                throw new InputException(ERROR_SENSOR_ALREADY_ADD + this.sensorShipAlpha);
            }
            this.sensorShipAlpha = "D";
        }
        this.d = new Attackership("D", modulesToAdd);
        this.dConfigured = true;

    }

    /**
     * Configures Attackership W
     * 
     * @param input
     */
    public void configureW(String input) throws InputException {
        List<Modulus> modulesToAdd = configureInput(input);
        if (input.contains("SENSOR")) {
            if (this.sensorShipZeta != null) {
                throw new InputException(ERROR_SENSOR_ALREADY_ADD + this.sensorShipZeta);
            }
            this.sensorShipZeta = "W";
        }
        this.w = new Attackership("W", modulesToAdd);
        this.wConfigured = true;

    }

    /**
     * Configures Attackership X
     * 
     * @param input
     */
    public void configureX(String input) throws InputException {
        List<Modulus> modulesToAdd = configureInput(input);
        if (input.contains("SENSOR")) {
            if (this.sensorShipZeta != null) {
                throw new InputException(ERROR_SENSOR_ALREADY_ADD + this.sensorShipZeta);
            }
            this.sensorShipZeta = "X";
        }
        this.x = new Attackership("X", modulesToAdd);
        this.xConfigured = true;

    }

    /**
     * Configures Attackership Y
     * 
     * @param input
     */
    public void configureY(String input) throws InputException {
        List<Modulus> modulesToAdd = configureInput(input);
        if (input.contains("SENSOR")) {
            if (this.sensorShipZeta != null) {
                throw new InputException(ERROR_SENSOR_ALREADY_ADD + this.sensorShipZeta);
            }
            this.sensorShipZeta = "Y";
        }
        this.y = new Attackership("Y", modulesToAdd);
        this.yConfigured = true;

    }

    /**
     * Configures game board. A board must be symmetrical in terms of South-west and
     * nort-east axis. An empty tile must have at least 1 direct neighbor which is
     * not a cover.
     * 
     * @param layout layout input for board.
     * @param length Length defined by the user while starting the game.
     */
    public void configureLayout(String layout, int length) throws InputException {
        layoutInputError(layout, length);
        int seed = (int) Math.sqrt(layout.length());
        Occupier[][] board = new Occupier[seed][seed];
        String input = layout;
        int[] check = new int[NUMBERSHIPS];

        for (int y = 0; y < board.length; y++) {
            Occupier[] row = new Occupier[seed];
            for (int x = 0; x < board[y].length; x++) {
                String toAdd = String.valueOf(input.charAt(0));
                input = input.substring(1, input.length());
                switch (toAdd) {
                case "1":
                    row[x] = new Cover(1, new Position(y, x));
                    break;
                case "2":
                    row[x] = new Cover(2, new Position(y, x));
                    break;
                case "A":
                    this.a.setPosition(y, x);
                    row[x] = this.a;
                    check[POS_A]++;
                    break;
                case "Z":
                    this.z.setPosition(y, x);
                    row[x] = this.z;
                    check[POS_Z]++;
                    break;
                case POINT:
                    row[x] = null;
                    break;
                case "B":
                    this.b.setPosition(y, x);
                    row[x] = this.b;
                    check[POS_B]++;
                    break;
                case "C":
                    this.c.setPosition(y, x);
                    row[x] = this.c;
                    check[POS_C]++;
                    break;
                case "D":
                    this.d.setPosition(y, x);
                    row[x] = this.d;
                    check[POS_D]++;
                    break;
                case "W":
                    this.w.setPosition(y, x);
                    row[x] = this.w;
                    check[POS_W]++;
                    break;
                case "X":
                    this.x.setPosition(y, x);
                    row[x] = this.x;
                    check[POS_X]++;
                    break;
                case "Y":
                    this.y.setPosition(y, x);
                    row[x] = this.y;
                    check[POS_Y]++;
                    break;
                default:
                    throw new InputException(ERROR_INVALID_INPUT);
                }
            }
            board[y] = row;
        }
        layoutError(board);
        for (int i : check) {
            if (i != 1) {
                throw new InputException(SHIP_PLACE);
            }
        }
        this.matchField = board;
        this.matchFieldConfigured = true;
    }

    /**
     * Configures the game depending on the configured ships and the game board by
     * user input.
     */
    public void initGame() {
        List<Spaceship> attackersOfAlpha = Arrays.asList(new Spaceship[] { this.b, this.c, this.d });
        List<Spaceship> attackersOfZeta = Arrays.asList(new Spaceship[] { this.w, this.x, this.y });

        Fleet alphaFleet = new Fleet(attackersOfAlpha, (Collector) this.a);
        Fleet zetaFleet = new Fleet(attackersOfZeta, (Collector) this.z);

        AI alpha = new AI("Alpha", alphaFleet);
        AI zeta = new AI("Zeta", zetaFleet);

        this.alphaZeta.init(matchField, alpha, zeta, seed);
    }

    /**
     * Checks if Spaceship B is configured.
     * 
     * @return the bConfigured
     */
    public boolean isbConfigured() {
        return bConfigured;
    }

    /**
     * Checks if Spaceship C is configured.
     * 
     * @return the cConfigured
     */
    public boolean iscConfigured() {
        return cConfigured;
    }

    /**
     * Checks if Spaceship D is configured.
     * 
     * @return the dConfigured
     */
    public boolean isdConfigured() {
        return dConfigured;
    }

    /**
     * Checks if Spaceship W is configured.
     * 
     * @return the wConfigured
     */
    public boolean iswConfigured() {
        return wConfigured;
    }

    /**
     * Checks if Spaceship X is configured.
     * 
     * @return the xConfigured
     */
    public boolean isxConfigured() {
        return xConfigured;
    }

    /**
     * Checks if Spaceship Y is configured.
     * 
     * @return the yConfigured
     */
    public boolean isyConfigured() {
        return yConfigured;
    }

    /**
     * Checks if match field is configured.
     * 
     * @return the matchFieldConfigured
     */
    public boolean isMatchFieldConfigured() {
        return matchFieldConfigured;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Cover;
    }

    private void layoutInputError(String layout, int length) throws InputException {

        if (layout.length() < MIN_LENGTH) {
            throw new InputException(LAYOUT_LENGTH);
        }
        if (layout.length() != length * length) {
            throw new InputException(BOARD_SHOULD_MATCH);
        }
    }

    private void layoutError(Occupier[][] board) throws InputException {
        if (!isSymmetrical(board)) {
            throw new InputException(NOT_SYMMETRICAL);
        }

        if (occupiedByCovers(board)) {
            throw new InputException(TILE_NEIGHBOR);
        }
    }
}
