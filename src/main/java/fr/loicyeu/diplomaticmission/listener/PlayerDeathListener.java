package fr.loicyeu.diplomaticmission.listener;

import fr.loicyeu.diplomaticmission.DiplomaticMission;
import fr.loicyeu.diplomaticmission.model.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Collection;

public class PlayerDeathListener implements Listener {

    private final DiplomaticMission main;

    public PlayerDeathListener(DiplomaticMission main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        Player killer = getKiller(player);
        if (killer == null) {
            player.getServer().broadcastMessage(C.DEFAULT + "Un joueur est mort accidentellement");
            return;
        }
        PlayerData playerData = Players.getInstance().getData(player);
        PlayerData killerData = Players.getInstance().getData(killer);
        if (killerData.getRole() == Role.THIEF) {
            player.getServer().broadcastMessage(C.DEFAULT + "Un joueur est mort accidentellement");
            killerData.changeRole(playerData.getRole());
            playerData.changeRole(Role.THIEF);
            playerData.respawn();
        } else {
            player.getServer().broadcastMessage(C.DEFAULT + "Un joueur a été trouvé mort...");
            switch (playerData.getRole()) {
                case THIEF:
                    playerData.respawn();
                case MURDERER:
                    playerData.setDead();
                    murdererKilled();
                    break;
                case AMBASSADOR:
                    playerData.setDead();
                    ambassadorKilled();
                    //TODO send end message
                default:
                    playerData.setDead();
            }

        }
    }

    private Player getKiller(Player player) {
        if (player.isDead()) {
            return player.getKiller();
        }
        return null;
    }

    private void murdererKilled() {
        for (PlayerData pd : Players.getInstance().getAll()) {
            if (pd.getRole() == Role.MURDERER && !pd.isDead()) {
                Game.getInstance().endGame(true);
            }
        }
    }

    private void ambassadorKilled() {
        Game.getInstance().endGame(false);
    }

    /**
     * Show the results for all the players.
     *
     * @param winners List of the winners.
     */
    private void showResults(Collection<PlayerData> winners) {
        winners.forEach(playerData ->
                main.getServer().broadcastMessage(C.DEFAULT + playerData.getPlayer().getName()
                        + " a gagné en tant que " + playerData.getRole())
        );
    }
}
