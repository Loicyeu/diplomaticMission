package fr.loicyeu.diplomaticmission.model;

import fr.loicyeu.diplomaticmission.exception.DeleteMapException;
import fr.loicyeu.diplomaticmission.exception.GameMapException;
import fr.loicyeu.diplomaticmission.exception.PlayerOnMapException;
import fr.loicyeu.diplomaticmission.exception.UnloadMapException;
import org.bukkit.*;

import java.util.ArrayList;
import java.util.Collection;

public final class Game {

    private static Game instance;
    private World world;
    private boolean ended;

    private Game() {
        this.world = null;
        this.ended = false;
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    /**
     * End the game and return the list of the winners.
     * Switch all the players on spectator mode.
     *
     * @return The list of the winners
     */
    public Collection<PlayerData> endGame(final boolean ambassadorWins) {
        this.ended = true;
        final Players players = Players.getInstance();
        final Collection<PlayerData> winners = new ArrayList<>();
        players.forEach(playerData -> {
            if (ambassadorWins) {
                switch (playerData.getRole()) {
                    case AMBASSADOR:
                    case GUARD:
                    case AMBITIOUS_GUARD:
                        winners.add(playerData);
                }
            } else {
                if (playerData.getRole() == Role.MURDERER) {
                    winners.add(playerData);
                }
            }
        });
        return winners;
    }

    public World getWorld() {
        return world;
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
