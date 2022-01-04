package fr.loicyeu.diplomaticmission.model;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public final class PlayerData {
    private final Player player;
    private Role role;
    private boolean revealed;
    private boolean dead;
    private Location spawn;

    public PlayerData(Player player, Role role) {
        this.player = player;
        this.role = role;
        this.revealed = false;
        this.dead = false;
    }

    public Player getPlayer() {
        return player;
    }

    public Role getRole() {
        return role;
    }

    public void changeRole(Role role) {
        this.role = role;
    }

    public Location getSpawn() {
        return spawn;
    }

    public Location setSpawn(Location newSpawn) {
        spawn = newSpawn;
        return spawn;
    }

    /**
     * Show the role in the action bar.
     * Write 'MORT' if the player is dead.
     */
    public void showRole() {
        if (!this.dead) {
            this.player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(this.role.getRoleName()));
        } else {
            this.player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + "MORT"));
        }
    }

    public boolean isRevealed() {
        return revealed;
    }

    public boolean isDead() {
        return dead;
    }

    public void reveal() {
        this.revealed = true;
    }

    public void setDead() {
        this.dead = true;
    }

    public void respawn() {

    }
}
