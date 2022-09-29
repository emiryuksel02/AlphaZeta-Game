package ai.occupier.spaceship;

import java.util.ArrayList;
import java.util.List;

import ai.Position;
import module.Container;
import module.Engine;
import module.Modulus;

/**
 * Represents a collector.
 * 
 * @author Emir Yuksel
 * @version 1.0
 *
 */
public class Collector implements Spaceship {

    private Position position;
    private final String name;
    private Engine engine = new Engine();
    private boolean isDestroyed = false;
    private List<Container> containers = new ArrayList<Container>();

    /**
     * Creates a collector.
     * 
     * @param total Total number of containers.
     * @param name  Name of the collector.
     */
    public Collector(int total, String name) {
        this.name = name;
        while (containers.size() < total) {
            containers.add(new Container());
        }

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
        for (Container container : this.containers) {
            if (!container.isDestroyed()) {
                allModules.add(container);
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
        this.containers.remove(containers.size() - 1);

    }

    @Override
    public boolean isDestroyed() {
        if (this.engine.isDestroyed() && this.containers.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public void mark() {
        return;
    }

    @Override
    public boolean containsShield() {
        return false;
    }

    @Override
    public void destroy() {
        this.containers.clear();
        this.engine.destroy();
        isDestroyed = true;
    }

    @Override
    public boolean equals(Object object) {

        return true;
    }

    @Override
    public boolean isMarked() {
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
    public void clearMark() {
        return;
    }

    @Override
    public boolean hasNoWeapon() {
        return true;
    }

}
