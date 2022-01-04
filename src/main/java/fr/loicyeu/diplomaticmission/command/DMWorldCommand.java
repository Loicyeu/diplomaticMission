package fr.loicyeu.diplomaticmission.command;

import fr.loicyeu.diplomaticmission.exception.DeleteMapException;
import fr.loicyeu.diplomaticmission.exception.NoMapException;
import fr.loicyeu.diplomaticmission.exception.PlayerOnMapException;
import fr.loicyeu.diplomaticmission.exception.UnloadMapException;
import fr.loicyeu.diplomaticmission.model.C;
import fr.loicyeu.diplomaticmission.model.Game;
import fr.loicyeu.diplomaticmission.model.GameMap;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class DMWorldCommand implements CommandExecutor {
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
            GameMap map = Game.getInstance().getMap();
            if (args.length <= 0) {
                sender.sendMessage(C.ERROR + "Mauvais usage de la commande.");
                return true;
            }
            switch (args[0]) {
                case "create":
                    sender.sendMessage(C.DEFAULT + "Génération du monde en cours...");
                    if (map.generateWorld()) {
                        sender.sendMessage(C.ERROR + "Le monde est déjà existant. \n" +
                                "Essayez " + ChatColor.GOLD + "/dmworld tp" + C.DEFAULT + " pour y aller.");
                    } else {
                        sender.sendMessage(C.DEFAULT + "Le monde a été créé avec succès.");
                    }
                    break;
                case "delete":
                    sender.sendMessage(C.DEFAULT + "Suppression du monde en cours...");
                    try {
                        if (map.destroyWorld()) {
                            sender.sendMessage(C.DEFAULT + "Le monde à bien été supprimé.");
                        } else {
                            sender.sendMessage(C.ERROR + "Le monde n'existe pas.");
                        }
                    } catch (PlayerOnMapException e) {
                        sender.sendMessage(C.ERROR + "Des joueurs sont toujours présents sur le monde.");
                    } catch (UnloadMapException e) {
                        sender.sendMessage(C.ERROR + "Impossible de décharger le monde du serveur.");
                    } catch (DeleteMapException e) {
                        sender.sendMessage(C.ERROR + "Le monde a été décharger mais les fichiers n'ont pas pu être supprimés.");
                    } catch (Exception e) {
                        sender.sendMessage(C.FATAL + "");
                        e.printStackTrace();
                    }
                    break;
                case "tp":
                case "quit":
                    //TODO
                    sender.sendMessage(C.ERROR + "Pas encore implémenté.");
                    break;
                case "setcenter":
                    try {
                        setCenter(Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]), Integer.parseInt(args[4]));
                    } catch (NumberFormatException e) {
                        sender.sendMessage(C.ERROR + "Mauvaises coordonées.");
                    } catch (NoMapException e) {
                        sender.sendMessage(C.ERROR + "Aucun monde DM trouvé");
                    }
                    break;
                default:
                    sender.sendMessage(C.ERROR + "Mauvais usage de la commande.");
                    break;
            }
        } else {
            System.out.println(C.ERROR + "You must be a player");
        }
        return true;
    }

    private void setCenter(double x, double y, double z, int radius) throws NoMapException {
        GameMap map = Game.getInstance().getMap();
        World world = map.getWorld();
        if (world == null) {
            throw new NoMapException();
        }
        Location center = new Location(world, x, y, z);
        map.setCenter(center);
        map.setRadius(radius);

        // Set the world border
        WorldBorder worldBorder = world.getWorldBorder();
        worldBorder.setCenter(center);
        worldBorder.setSize(radius * 2);

        // Set the block at the center as a chest
        Block chest = center.getBlock();
        chest.setType(Material.CHEST);
    }
}
