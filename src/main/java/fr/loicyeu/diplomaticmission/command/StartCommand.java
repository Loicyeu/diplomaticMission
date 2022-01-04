package fr.loicyeu.diplomaticmission.command;

import fr.loicyeu.diplomaticmission.DiplomaticMission;
import fr.loicyeu.diplomaticmission.exception.NoMapException;
import fr.loicyeu.diplomaticmission.model.*;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;


public final class StartCommand implements CommandExecutor {

    private final DiplomaticMission main;

    public StartCommand(DiplomaticMission main) {
        this.main = main;
    }

    /**
     * Executes the given command, returning its success.
     * <br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Collection<? extends Player> onlinePlayers = main.getServer().getOnlinePlayers();
            Player player = (Player) sender;
            if (onlinePlayers.size() < 3 || onlinePlayers.size() > 5) {
                player.sendMessage(C.ERROR + "Vous devez être au moins 3 et au plus 5 pour pouvoir jouer");
                return true;
            }
            Players players = Players.getInstance();
            List<Role> roles = Role.getRandList(onlinePlayers.size());

            onlinePlayers.forEach(p -> {
                Role role = roles.remove(0);
                players.addPlayer(p, role);
//                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(role.getRoleName()));
                p.sendTitle(role.getRoleName(), role.getWords(), -1, 100, -1);
            });
            try {
                setPlayersSpawns();
            } catch (NoMapException e) {
                main.getServer().broadcastMessage(C.ERROR + "Aucun monde DM trouvé");
            }
            try {
                Thread.sleep(5000);
                main.getServer().broadcastMessage(C.DEFAULT + "3");
                Thread.sleep(1000);
                main.getServer().broadcastMessage(C.DEFAULT + "2");
                Thread.sleep(1000);
                main.getServer().broadcastMessage(C.DEFAULT + "1");
                Thread.sleep(1000);
                main.getServer().broadcastMessage(C.DEFAULT + "La partie commence ! Bon courage et bonne chance a tous !");
                return true;
            } catch (Exception e) {
                main.getServer().broadcastMessage(C.DEFAULT + "La partie commence ! Bon courage et bonne chance a tous !");
                return true;
            }
        }
        return false;
    }

    /**
     * Gives all the players a random spawn
     * Except for the Ambassador who spawns on the chest
     * Everyone else spawns away from the center
     *
     * @throws NoMapException
     */
    private void setPlayersSpawns() throws NoMapException {
        GameMap map = Game.getInstance().getMap();
        World world = map.getWorld();
        if (world == null) {
            throw new NoMapException();
        }

        for (PlayerData p : Players.getInstance().getAll()) {
            if (p.getRole() == Role.AMBASSADOR) {
                p.setSpawn(new Location(map.getWorld(), map.getCenter().getX(), map.getCenter().getY() + 1, map.getCenter().getZ()));
            } else {
                p.setSpawn(generateSpawn());
            }
        }
    }

    /**
     * Generates a random location away from the center
     *
     * @return The new Location
     */
    private Location generateSpawn() {
        GameMap map = Game.getInstance().getMap();
        Location newSpawn = new Location(map.getWorld(), 0, 0, 0);
        double spawnZoneRadius = map.getRadius() - map.getRadius() * map.getSafeZonePercent();
        double plusOrMinus = Math.random() - 0.5;
        newSpawn.setX(map.getCenter().getX() + (plusOrMinus / Math.abs(plusOrMinus)) * (map.getRadius() * map.getSafeZonePercent() + Math.random() * spawnZoneRadius));
        newSpawn.setZ(map.getCenter().getZ() + (plusOrMinus / Math.abs(plusOrMinus)) * (map.getRadius() * map.getSafeZonePercent() + Math.random() * spawnZoneRadius));
        newSpawn.setY(map.getWorld().getHighestBlockYAt(newSpawn));
        return newSpawn;
    }
}
