package it.polimi.ingsw.model;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @implNote Java Serialization automatically takes care of keeping multiple references to a single object
 *           correct on deserialization:
 *           "Multiple references to a single object are encoded using a reference sharing mechanism so that
 *           graphs of objects can be restored to the same shape as when the original was written."
 *           Using invalidTile's reference as a marker should be deserialized correctly.
 * @see java.io.ObjectOutputStream
 */
public class Board implements BoardView {

    private final Property<@Nullable Tile>[][] board;
    private final Property<Tile> invalidTile;

    @SuppressWarnings("unchecked")
    public Board(int numOfPlayers) {
        this.board = Arrays.stream(generateBasedOnPlayers(numOfPlayers))
                .map(row -> Arrays.stream(row).map(SerializableProperty::nullableProperty).toArray(Property[]::new))
                .toArray(Property[][]::new);
        invalidTile = new SerializableProperty<>(new Tile(Color.GREEN));
    }

    private static @Nullable Tile[][] generateBasedOnPlayers(int numOfPlayers) {
        return new Tile[20][20]; // TODO: generate based on number of players
    }

    @Override
    public int getRows() {
        return board.length;
    }

    @Override
    public int getCols() {
        return board.length == 0 ? 0 : board[0].length;
    }

    @Override
    public Property<@Nullable Tile> tile(int r, int c) {
        if (board[r][c] == invalidTile)
            throw new IndexOutOfBoundsException("Invalid Position selected");
        return board[r][c];
    }

    @Override
    public Stream<TileAndCoords<Property<@Nullable Tile>>> tiles() {
        return IntStream.range(0, getRows()).boxed().flatMap(row -> IntStream.range(0, getCols()).boxed()
                .filter(col -> board[row][col] != invalidTile)
                .map(col -> new TileAndCoords<>(board[row][col], row, col)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Board that))
            return false;
        return IntStream.range(0, getRows()).boxed()
                .allMatch(row -> IntStream.range(0, getCols()).boxed()
                        .allMatch(col -> Objects.equals(board[row][col].get(), that.board[row][col].get())));
    }

    @Override
    public int hashCode() {
        return Arrays.stream(board)
                .mapToInt(row -> Arrays.stream(row)
                        .mapToInt(tile -> Objects.hashCode(tile.get()))
                        .reduce(1, (a, b) -> 31 * a + b))
                .reduce(1, (a, b) -> 31 * a + b);
    }

    @Override
    public String toString() {
        return "Board{" +
                "board=" + Arrays.toString(board) +
                ", invalidTile=" + invalidTile +
                '}';
    }
}