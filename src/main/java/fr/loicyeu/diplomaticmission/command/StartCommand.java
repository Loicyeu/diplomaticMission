package fr.loicyeu.diplomaticmission.command;

import fr.loicyeu.diplomaticmission.DiplomaticMission;
import fr.loicyeu.diplomaticmission.model.C;
import fr.loicyeu.diplomaticmission.model.Players;
import fr.loicyeu.diplomaticmission.model.Role;
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
}
