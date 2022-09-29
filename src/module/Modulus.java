package module;

/**
 * Interface for managing modules.
 * 
 * @author Emir Yuksel
 * @version 1.0
 *
 */
public interface Modulus {

    /**
     * Gets the name of a module
     * 
     * @return name
     */
    String getName();

    /**
     * Destroys a module.
     */
    void destroy();

    /**
     * Checks if a module is destroyed.
     * 
     * @return true if it's destroyed, false otherwise.
     */
    boolean isDestroyed();

    /**
     * Gets the corresponding action of a module.
     * 
     * @return action
     */
    String getAction();

    /**
     * Checks if a module is weapon.
     * 
     * @param object
     * @return true if it's a weapon module, false otherwise
     */
    boolean equals(Object object);
}
