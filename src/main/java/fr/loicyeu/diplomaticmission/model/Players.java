package fr.loicyeu.diplomaticmission.model;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class Players implements Iterable<PlayerData> {

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

    public Collection<PlayerData> getAll() {
        return this.playerDataMap.values();
    }

    public void addPlayer(Player player, Role role) {
        this.playerDataMap.put(player, new PlayerData(player, role));
    }

    public PlayerData getData(Player player) {
        return this.playerDataMap.get(player);
    }

    /**
     * Returns an iterator over elements of type {@code PlayerData}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<PlayerData> iterator() {
        return playerDataMap.values().iterator();
    }
}
