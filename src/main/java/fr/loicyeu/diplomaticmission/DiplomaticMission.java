package fr.loicyeu.diplomaticmission;

import fr.loicyeu.diplomaticmission.command.DMWorldCommand;
import fr.loicyeu.diplomaticmission.command.RoleCommand;
import fr.loicyeu.diplomaticmission.command.StartCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class DiplomaticMission extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Plugin fonctionnel");
        try {
            getServer().getPluginCommand("start").setExecutor(new StartCommand(this));
            getServer().getPluginCommand("role").setExecutor(new RoleCommand());
            getServer().getPluginCommand("dmworld").setExecutor(new DMWorldCommand());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            getLogger().log(Level.SEVERE, "Le plugin " + getName() + " ne fonctionne pas !");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
