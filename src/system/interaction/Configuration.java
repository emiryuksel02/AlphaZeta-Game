package system.interaction;

import java.util.Scanner;

/**
 * This class configures ships and layout.
 * 
 * @author Emir Yuksel
 * @version 1.0
 */
public class Configuration {
    private static final String ERROR_MESSAGE_PREFIX = "Error, ";
    private static final String CHOOSE_MODULE = "Choose upto 3 modules for ship ";
    private static final String SEPARATED_BY = " (separated by comma):\n";
    private static final String SELECTABLE_MODULES = "PROPULSION, RAILGUN, SENSOR, SHIELD, SWORD";
    private static final String ZETA = "Zeta ";
    private static final String CONFIGURATION = "configures its ships\n";
    private static final String ASK_LAYOUT = "Enter the Board layout as string:\n"
            + "Use . for free tiles; 1,2 for cover; and A,B,C,D,Z,W,X,Y for ships";

    /**
     * Configuration of Spaceship B
     * 
     * @param start InitGame instance to configure game settings.
     */
    public void configureB(InitGame start) {

        do {

            Scanner scannerB = new Scanner(System.in);
            try {
                start.configureB(scannerB.nextLine().toUpperCase());
            } catch (InputException exception) {
                System.out.println(ERROR_MESSAGE_PREFIX + exception.getMessage());

            }

        } while (!start.isbConfigured());
    }

    /**
     * Configuration of Spaceship C
     * 
     * @param start InitGame instance to configure game settings.
     */
    public void configureC(InitGame start) {

        System.out.println(CHOOSE_MODULE + "C" + SEPARATED_BY + SELECTABLE_MODULES);
        do {
            Scanner scannerC = new Scanner(System.in);
            try {
                start.configureC(scannerC.nextLine().toUpperCase());
            } catch (InputException exception) {
                System.out.println(ERROR_MESSAGE_PREFIX + exception.getMessage());

            }

        } while (!start.iscConfigured());
    }

    /**
     * Configuration of Spaceship D
     * 
     * @param start InitGame instance to configure game settings.
     */
    public void configureD(InitGame start) {
        System.out.println(CHOOSE_MODULE + "D" + SEPARATED_BY + SELECTABLE_MODULES);
        do {
            Scanner scannerD = new Scanner(System.in);
            try {
                start.configureD(scannerD.nextLine().toUpperCase());
            } catch (InputException exception) {
                System.out.println(ERROR_MESSAGE_PREFIX + exception.getMessage());

            }

        } while (!start.isdConfigured());
    }

    /**
     * Configuration of Spaceship X
     * 
     * @param start InitGame instance to configure game settings.
     */
    public void configureX(InitGame start) {
        System.out.println(CHOOSE_MODULE + "X" + SEPARATED_BY + SELECTABLE_MODULES);
        do {
            Scanner scannerX = new Scanner(System.in);
            try {
                start.configureX(scannerX.nextLine().toUpperCase());
            } catch (InputException exception) {
                System.out.println(ERROR_MESSAGE_PREFIX + exception.getMessage());

            }

        } while (!start.isxConfigured());
    }

    /**
     * Configuration of Spaceship Y
     * 
     * @param start InitGame instance to configure game settings.
     */
    public void configureY(InitGame start) {
        System.out.println(CHOOSE_MODULE + "Y" + SEPARATED_BY + SELECTABLE_MODULES);
        do {
            Scanner scannerY = new Scanner(System.in);
            try {
                start.configureY(scannerY.nextLine().toUpperCase());
            } catch (InputException exception) {
                System.out.println(ERROR_MESSAGE_PREFIX + exception.getMessage());

            }

        } while (!start.isyConfigured());
    }

    /**
     * Configuration of Spaceship W
     * 
     * @param start InitGame instance to configure game settings.
     */
    public void configureW(InitGame start) {
        System.out.println(ZETA + CONFIGURATION + CHOOSE_MODULE + "W" + SEPARATED_BY + SELECTABLE_MODULES);
        do {
            Scanner scannerW = new Scanner(System.in);
            try {
                start.configureW(scannerW.nextLine().toUpperCase());
            } catch (InputException exception) {
                System.out.println(ERROR_MESSAGE_PREFIX + exception.getMessage());

            }

        } while (!start.iswConfigured());
    }

    /**
     * Configuration of game board layout
     * 
     * @param start  InitGame instance to configure game settings.
     * @param length length of the game board
     */
    public void configureLayout(InitGame start, int length) {

        System.out.println(ASK_LAYOUT);

        do {
            Scanner scannerLayout = new Scanner(System.in);

            try {
                String layout = scannerLayout.nextLine().toUpperCase();
                start.configureLayout(layout, length);
            } catch (InputException exception) {

                System.out.println(ERROR_MESSAGE_PREFIX + exception.getMessage());
            }
        } while (!start.isMatchFieldConfigured());
    }
}
