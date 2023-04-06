package it.polimi.ingsw.model;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.stream.Stream;

public interface PersonalGoalView extends Serializable {

    int ROWS = Shelfie.ROWS;
    int COLUMNS = Shelfie.COLUMNS;

    /**
     * Returns the tile in position r & c
     * 
     * @param r defines row of personalGoal
     * @param c defines column of personalGoal
     * @return tile of personal Goal
     */
    @Nullable
    Tile get(int r, int c);

    @Nullable
    Tile[][] getPersonalGoal();

    void printPersonalGoal();

    void printPersonalGoalOnShelfie(Shelfie shelfie);

    boolean achievedPersonalGoal(Shelfie shelfie);

    Stream<TileAndCoords<@Nullable Tile>> tiles();
}
