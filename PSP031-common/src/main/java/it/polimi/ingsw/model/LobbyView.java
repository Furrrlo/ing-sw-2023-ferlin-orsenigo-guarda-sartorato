package it.polimi.ingsw.model;

import java.util.List;

public interface LobbyView {
    /**
     * @return number of required players
     */
    int getRequiredPlayers();

    /**
     * @return Property list of joined players
     */
    Provider<List<String>> joinedPlayers();
}
