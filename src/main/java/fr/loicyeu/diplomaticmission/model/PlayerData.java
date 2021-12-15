package fr.loicyeu.diplomaticmission.model;

import org.bukkit.entity.Player;

public final class PlayerData {
    private final Player player;
    private final Role role;
    private boolean revealed;
    private boolean dead;

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

    public boolean isRevealed() {
        return revealed;
    }

    public boolean isDead() {
        return dead;
    }

    public void reveal() {
        this.revealed = true;
    }

    public void killed() {
        this.dead = true;
    }
}
