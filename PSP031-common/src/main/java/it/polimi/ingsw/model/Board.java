package it.polimi.ingsw.model;

import java.util.Arrays;

public class Board implements BoardView {

    private final Property<Tile>[][] board;
    private final Property<Tile> invalidTile;

    @SuppressWarnings("unchecked")
    public Board(int numOfPlayers) {
        this.board = Arrays.stream(generateBasedOnPlayers(numOfPlayers))
                .map(row -> Arrays.stream(row).map(PropertyImpl::new).toArray(Property[]::new))
                .toArray(Property[][]::new);
        invalidTile = new PropertyImpl<>(new Tile(Color.GREEN));
    }

    private static Tile[][] generateBasedOnPlayers(int numOfPlayers) {
        return new Tile[20][20]; // TODO: generate based on number of players
    }

    @Override
    public Property<Tile> tile(int r, int c) {
        if (board[r][c] == invalidTile)
            throw new IndexOutOfBoundsException("Invalid Position selected");
        else return board[r][c];
    }
}