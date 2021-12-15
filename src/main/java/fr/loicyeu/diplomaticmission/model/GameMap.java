package fr.loicyeu.diplomaticmission.model;

import fr.loicyeu.diplomaticmission.exception.DeleteMapException;
import fr.loicyeu.diplomaticmission.exception.GameMapException;
import fr.loicyeu.diplomaticmission.exception.PlayerOnMapException;
import fr.loicyeu.diplomaticmission.exception.UnloadMapException;
import org.bukkit.*;

public final class GameMap {

    private static GameMap instance;
    private final Location pos1;
    private final Location pos2;

    public World getWorld() {
        return world;
    }

    private World world;

    private GameMap() {
        this.pos1 = new Location(null, 1d, 1d, 1d);
        this.pos2 = new Location(null, 1d, 1d, 1d);
        this.world = null;
    }

    public static GameMap getInstance() {
        if (instance == null) {
            instance = new GameMap();
        }
        return instance;
    }

    /**
     * Generate a new world for the game
     *
     * @return True if the world is well created, False instead or if the world was already existing.
     */
    public boolean generateWorld() {
        if (world == null) {
            WorldCreator wc = new WorldCreator("Diplomatic Mission");
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
