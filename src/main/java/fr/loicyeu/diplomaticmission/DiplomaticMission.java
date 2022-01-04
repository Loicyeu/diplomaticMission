package fr.loicyeu.diplomaticmission;

import fr.loicyeu.diplomaticmission.command.DMWorldCommand;
import fr.loicyeu.diplomaticmission.command.RoleCommand;
import fr.loicyeu.diplomaticmission.command.StartCommand;
import fr.loicyeu.diplomaticmission.listener.PlayerDeathListener;
import fr.loicyeu.diplomaticmission.model.C;
import fr.loicyeu.diplomaticmission.model.PlayerData;
import fr.loicyeu.diplomaticmission.model.Players;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class DiplomaticMission extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Plugin fonctionnel");
        try {
            getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
            getServer().getPluginCommand("start").setExecutor(new StartCommand(this));
            getServer().getPluginCommand("role").setExecutor(new RoleCommand());
            getServer().getPluginCommand("dmworld").setExecutor(new DMWorldCommand());
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            getLogger().log(Level.SEVERE, "Le plugin " + getName() + " ne fonctionne pas !");
            getServer().broadcastMessage(C.FATAL + "Le plugin n'est pas fonctionnel.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            getLogger().log(Level.SEVERE, "Le plugin " + getName() + " ne fonctionne pas ! Et c'est vrm pas normal");
            getServer().broadcastMessage(C.FATAL + "Le plugin n'est pas fonctionnel. Et c'est vrm pas normal.");
        }

        Runnable runnable = () -> Players.getInstance().getAll().forEach(PlayerData::showRole);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, runnable, 1, 1);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
