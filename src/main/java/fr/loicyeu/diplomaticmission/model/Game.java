package fr.loicyeu.diplomaticmission.model;

import java.util.ArrayList;
import java.util.Collection;

public final class Game {

    private static Game instance;
    private GameMap map;
    private boolean ended;

    private Game() {
        this.map = new GameMap(.25);
        this.ended = false;
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    /**
     * End the game and return the list of the winners.
     * Switch all the players on spectator mode.
     *
     * @return The list of the winners
     */
    public Collection<PlayerData> endGame(final boolean ambassadorWins) {
        this.ended = true;
        final Players players = Players.getInstance();
        final Collection<PlayerData> winners = new ArrayList<>();
        players.forEach(playerData -> {
            if (ambassadorWins) {
                switch (playerData.getRole()) {
                    case AMBASSADOR:
                    case GUARD:
                    case AMBITIOUS_GUARD:
                        winners.add(playerData);
                }
            } else {
                if (playerData.getRole() == Role.MURDERER) {
                    winners.add(playerData);
                }
            }
        });
        return winners;
    }

    public GameMap getMap() {
        return map;
    }

}
