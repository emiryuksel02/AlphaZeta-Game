package system.interaction;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ai.AI;
import system.AlphaZeta;

/**
 * This class contains commands for user interaction.
 * 
 * @author Emir Yuksel
 * @version 1.0
 *
 */
public enum Command {
    /**
     * Runs endTurn() of CommandMethods.
     */
    ENDTURN("(" + Command.REGEX_ENDTURN + ")") {
        @Override
        public void execute(MatchResult matcher, AlphaZeta alphaZeta) throws InputException {

            method.endTurn(matcher, alphaZeta);
        }

    },
    /**
     * Runs help() of CommandMethods.
     */
    HELP("(" + Command.REGEX_HELP + ")") {

        @Override
        public void execute(MatchResult matcher, AlphaZeta alphaZeta) throws InputException {
            method.help(matcher, alphaZeta);
        }

    },
    /**
     * Runs fleet() of CommandMethods.
     */
    FLEET("(" + Command.REGEX_FLEET + ")") {

        @Override
        public void execute(MatchResult matcher, AlphaZeta alphaZeta) throws InputException {
            method.fleet(matcher, alphaZeta);

        }

    },
    /**
     * Runs move() of CommandMethods.
     */
    MOVE("(" + Command.REGEX_SHIP + ") (" + Command.REGEX_MOVE + ") (" + Command.REGEX_POSITION + ") ("
            + Command.REGEX_POSITION + ")") {

        @Override
        public void execute(MatchResult matcher, AlphaZeta alphaZeta) throws InputException {
            method.move(matcher, alphaZeta);
        }

    },
    /**
     * Runs ramm() of CommandMethods.
     */
    RAMM("(" + Command.REGEX_SHIP + ") (" + Command.REGEX_RAMM + ") (" + Command.REGEX_SHIP + ")") {

        @Override
        public void execute(MatchResult matcher, AlphaZeta alphaZeta) throws InputException {
            method.ramm(matcher, alphaZeta);
            checkGameOver(alphaZeta);
        }

    },
    /**
     * Runs longshot() of CommandMethods.
     */
    LONGSHOT("(" + Command.REGEX_SHIP + ") (" + Command.REGEX_LONGSHOT + ") (" + Command.REGEX_SHIP + ")") {

        @Override
        public void execute(MatchResult matcher, AlphaZeta alphaZeta) throws InputException {
            method.longshot(matcher, alphaZeta);
            checkGameOver(alphaZeta);
        }

    },
    /**
     * Runs strike() of CommandMethods.
     */
    STRIKE("(" + Command.REGEX_SHIP + ") (" + Command.REGEX_STRIKE + ") (" + Command.REGEX_SHIP + ")") {

        @Override
        public void execute(MatchResult matcher, AlphaZeta alphaZeta) throws InputException {
            method.strike(matcher, alphaZeta);
            checkGameOver(alphaZeta);
        }
    },
    /**
     * Runs propulsion() of CommandMethods.
     */
    PROPULSION("(" + Command.REGEX_SHIP + ") (" + Command.REGEX_PROPULSION + ") (" + Command.REGEX_POSITION + ") ("
            + Command.REGEX_POSITION + ")") {
        @Override
        public void execute(MatchResult matcher, AlphaZeta alphaZeta) throws InputException {
            method.propulsion(matcher, alphaZeta);
        }
    },
    /**
     * Ends the game.
     */
    QUIT("(" + Command.REGEX_QUIT + ")") {

        @Override
        public void execute(MatchResult matcher, AlphaZeta alphaZeta) throws InputException {
            quit();

        }

    },
    /**
     * Runs board() of CommandMethods.
     */
    BOARD("(" + Command.REGEX_BOARD + ")") {

        @Override
        public void execute(MatchResult matcher, AlphaZeta alphaZeta) throws InputException {
            method.board(matcher, alphaZeta);
        }

    },
    /**
     * Runs actions() of CommandMethods.
     */
    ACTIONS("(" + Command.REGEX_ACTIONS + ") (" + Command.REGEX_SHIP + ")") {

        @Override
        public void execute(MatchResult matcher, AlphaZeta alphaZeta) throws InputException {
            method.actions(matcher, alphaZeta);

        }

    },
    /**
     * Runs mark() of CommandMethods.
     */
    MARK("(" + Command.REGEX_SHIP + ") (" + Command.REGEX_MARK + ") (" + Command.REGEX_SHIP + ")") {
        @Override
        public void execute(MatchResult matcher, AlphaZeta alphaZeta) throws InputException {
            method.mark(matcher, alphaZeta);

        }
    };

    private static final String ERROR_INVALID_COMMAND = "not a valid command";
    private static final String REGEX_MARK = "mark";
    private static final String REGEX_ENDTURN = "endturn";
    private static final String REGEX_HELP = "help";
    private static final String REGEX_FLEET = "fleet";
    private static final String REGEX_SHIP = "[abcdzwxy]";
    private static final String REGEX_MOVE = "move";
    private static final String REGEX_POSITION = "[\\d]";
    private static final String REGEX_RAMM = "ramm";
    private static final String REGEX_LONGSHOT = "longshot";
    private static final String REGEX_STRIKE = "strike";
    private static final String REGEX_PROPULSION = "propel";
    private static final String REGEX_QUIT = "quit";
    private static final String REGEX_BOARD = "board";
    private static final String REGEX_ACTIONS = "actions";
    private static final String MESSAGE_WINNER = " won!";
    private static boolean isRunning = true;
    private static CommandMethods method = new CommandMethods();
    private final Pattern pattern;

    /**
     * Constructs a new command instance.
     *
     * @param pattern The regex pattern to use for command validation and
     *                processing.
     */
    Command(String pattern) {

        this.pattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);

    }

    /**
     * Executes a command.
     * 
     * @param matcher   The regex matcher that contains the groups of user input for
     *                  the command.
     * @param alphaZeta The instance of a system to be manipulated by executing a
     *                  command.
     * @throws InputException if the command contains syntactical or semantic
     *                        errors.
     */
    public abstract void execute(MatchResult matcher, AlphaZeta alphaZeta) throws InputException;

    /**
     * Checks an input against all available commands and calls the command if one
     * is found.
     *
     * @param input     The user input.
     * @param alphaZeta The instance of a system to be manipulated by executing a
     *                  command.
     * @return The command that got executed.
     * @throws InputException if no matching command is found. Contains an error
     *                        message.
     */
    public static Command executeMatching(String input, AlphaZeta alphaZeta) throws InputException {
        for (Command command : Command.values()) {
            Matcher matcher = command.pattern.matcher(input);
            if (matcher.matches()) {
                command.execute(matcher, alphaZeta);
                return command;
            }
        }

        throw new InputException(ERROR_INVALID_COMMAND);
    }

    /**
     * To check if the program still is closed.
     * 
     * @return true if the program is still running, false otherwise.
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Exits the program.
     */
    protected static void quit() {
        isRunning = false;
    }

    /**
     * Checks if the game is over.
     * 
     * @param alphaZeta The instance of a system to be manipulated
     */
    private static void checkGameOver(AlphaZeta alphaZeta) {
        if (alphaZeta.gameOver()) {
            AI winner = alphaZeta.getWinner();
            System.out.println(winner.getName() + MESSAGE_WINNER);
            quit();
        }
    }

}
