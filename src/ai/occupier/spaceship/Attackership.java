package ai.occupier.spaceship;

import java.util.ArrayList;
import java.util.List;

import ai.Position;
import module.Engine;
import module.Modulus;

/**
 * Represents an attackership.
 * 
 * @author Emir Yuksel
 * @version 1.0
 *
 */
public class Attackership implements Spaceship {

    private Position position;
    private final String name;
    private Engine engine = new Engine();
    private List<Modulus> modules;
    private boolean isMarked = false;
    private boolean isDestroyed = false;

    /**
     * Creates an attackership.
     * 
     * @param name    Name of an attackership.
     * @param modules Modules of an attackership.
     */
    public Attackership(String name, List<Modulus> modules) {
        this.name = name;
        this.modules = modules;

    }

    @Override
    public String getName() {

        return this.name;
    }

    @Override
    public void setPosition(int y, int x) {
        this.position = new Position(y, x);

    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public List<Modulus> getModules() {
        List<Modulus> allModules = new ArrayList<Modulus>();
        if (!this.engine.isDestroyed()) {
            allModules.add(engine);
        }
        for (Modulus module : this.modules) {
            if (!module.isDestroyed()) {
                allModules.add(module);
            }
        }

        return allModules;
    }

    @Override
    public void removeModule(Modulus module) {
        if (module.getName().equals("ENGINE")) {
            this.engine.destroy();
            return;
        }
        for (Modulus moduleToRemove : this.modules) {
            if (module.getName().equals(moduleToRemove.getName()) && !moduleToRemove.isDestroyed()) {
                moduleToRemove.destroy();
                return;
            }
        }
    }

    @Override
    public boolean isDestroyed() {
        if (!getModules().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isMarked() {
        return isMarked;

    }

    @Override
    public void mark() {
        this.isMarked = true;
    }

    @Override
    public void clearMark() {
        this.isMarked = false;
    }

    @Override
    public boolean containsShield() {
        for (Modulus module : this.modules) {
            if (module.getName().equals("SHIELD")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {
        this.modules.clear();
        this.engine.destroy();
        isDestroyed = true;

    }

    @Override
    public boolean equals(Object object) {

        return false;
    }

    @Override
    public boolean hasModule(Modulus module) {
        for (Modulus toCheck : getModules()) {
            if (toCheck.getName().equals(module.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasNoWeapon() {
        for (Modulus module : getModules()) {
            if (module.equals(module)) {
                return false;
            }
        }
        return true;
    }

}
