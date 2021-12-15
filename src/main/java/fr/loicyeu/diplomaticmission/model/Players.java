package fr.loicyeu.diplomaticmission.model;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public final class Players {

    private static Players players;

    private final Map<Player, PlayerData> playerDataMap;

    private Players() {
        this.playerDataMap = new HashMap<>();
    }

    public static Players getInstance() {
        if (players == null) {
            players = new Players();
        }
        return players;
    }

    public void addPlayer(Player player, Role role) {
        this.playerDataMap.put(player, new PlayerData(player, role));
    }

    public PlayerData getData(Player player) {
        return this.playerDataMap.get(player);
    }
}
