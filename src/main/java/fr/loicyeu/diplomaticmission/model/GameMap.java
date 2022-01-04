package fr.loicyeu.diplomaticmission.model;

import fr.loicyeu.diplomaticmission.exception.DeleteMapException;
import fr.loicyeu.diplomaticmission.exception.GameMapException;
import fr.loicyeu.diplomaticmission.exception.PlayerOnMapException;
import fr.loicyeu.diplomaticmission.exception.UnloadMapException;
import org.bukkit.*;

public class GameMap {

    private World world;
    private Location center;
    private int radius;
    private double safeZonePercent;

    public GameMap(double safeZonePercent) {
        this.world = null;
        this.safeZonePercent = safeZonePercent;
    }

    public World getWorld() {
        return world;
    }

    public Location getCenter() {
        return center;
    }

    public int getRadius() {
        return radius;
    }

    public double getSafeZonePercent() {
        return safeZonePercent;
    }

    public Location setCenter(Location location) {
        center = location;
        return center;
    }

    /* not used yet
    public Location setCenter(double x, double y, double z) {
        center = new Location(world, x, y, z);
        return center;
    }
    */

    public void setRadius(int radius) {
        this.radius = radius;
    }

    /**
     * Generate a new world for the game
     *
     * @return True if the world is well created, False instead or if the world was already existing.
     */
    public boolean generateWorld() {
        if (world == null) {
            WorldCreator wc = new WorldCreator("DiplomaticMission");
            wc.environment(World.Environment.NORMAL);
            wc.type(WorldType.NORMAL);
            world = wc.createWorld();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Unload and delete world files.
     *
     * @return True if the world is deleted.
     * @throws PlayerOnMapException If players are still on the world.
     * @throws UnloadMapException   If the world can't be unloaded.
     * @throws DeleteMapException   If the files can't be deleted.
     * @throws GameMapException     If another error occurred.
     */
    public boolean destroyWorld() throws GameMapException {
        if (world != null) {
            if (!world.getPlayers().isEmpty()) {
                throw new PlayerOnMapException("Players a still on the world. (" + world.getName() + ")");
            }
            if (!Bukkit.unloadWorld(world, false)) {
                throw new UnloadMapException("Unable to unload the world " + world.getName());
            }
            if (!world.getWorldFolder().delete()) {
                throw new DeleteMapException("Map unloaded but files not deleted (" + world.getName() + ")");
            }
            return true;
        }
        return false;
    }
}
