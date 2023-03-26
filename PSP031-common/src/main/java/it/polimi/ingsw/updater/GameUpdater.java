package it.polimi.ingsw.updater;

import it.polimi.ingsw.DisconnectedException;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Type;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface GameUpdater {

    void updateBoardTile(int row, int col, @Nullable Tile tile) throws DisconnectedException;

    void updatePlayerShelfieTile(String nick, int row, int col, @Nullable Tile tile) throws DisconnectedException;

    void updateCurrentTurn(String nick) throws DisconnectedException;

    void updateFirstFinisher(String nick) throws DisconnectedException;

    void updateAchievedCommonGoal(Type commonGoalType, List<String> playersAchieved) throws DisconnectedException;
}