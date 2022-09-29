package system.interaction;

import java.util.Scanner;

import system.AlphaZeta;

/**
 * This program provides a system for literature and citation management.
 * 
 * @author Emir Yuksel
 * @version 1.0
 */
public final class Main {

    private static final String ERROR_UTILITY_CLASS_INSTANTIATION = "Utility class cannot be instantiated.";
    private static final String ERROR_MESSAGE_PREFIX = "Error, ";
    private static final String FIRST_TURN = "Alpha's turn";
    private static final String PARAMETER_NOT_VALID = "given parameters are not valid.";

    /**
     * Constructs a new instance of Main.
     *
     * @throws IllegalStateException if constructor is called because Main is a
     *                               utility class.
     */
    private Main() {
        throw new IllegalStateException(ERROR_UTILITY_CLASS_INSTANTIATION);
    }

    /**
     * Main entry point to the app.
     * 
     * @param args The arguments that are passed to the program at launch as array.
     * @throws InputException
     */
    public static void main(String[] args) throws InputException {
        int seed = Integer.valueOf(args[0]);
        int length = Integer.valueOf(args[1]);
        int container = Integer.valueOf(args[2]);
        if (length < 5 || length % 2 == 0 || container < 0 || container > 3) {
            System.out.println(ERROR_MESSAGE_PREFIX + PARAMETER_NOT_VALID);
            return;
        }

        AlphaZeta alphaZeta = new AlphaZeta();
        InitGame start = new InitGame(alphaZeta, seed);
        System.out.println(start.welcomeMessage(container));

        Configuration configuration = new Configuration();
        configuration.configureB(start);
        configuration.configureC(start);
        configuration.configureD(start);
        configuration.configureW(start);
        configuration.configureX(start);
        configuration.configureY(start);
        configuration.configureLayout(start, length);
        start.initGame();
        alphaZeta = start.getGame();
        alphaZeta.startNewTurn();
        System.out.println(FIRST_TURN);
        Command command = null;
        do {
            Scanner scanner = new Scanner(System.in);
            try {
                String input = scanner.nextLine();
                command = Command.executeMatching(input, alphaZeta);

            } catch (InputException exception) {
                System.out.println(ERROR_MESSAGE_PREFIX + exception.getMessage());
            }
        } while (command == null || command.isRunning());

    }

}