package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.BoardCoord;
import it.polimi.ingsw.model.LobbyPlayer;
import it.polimi.ingsw.model.Property;
import it.polimi.ingsw.model.Tile;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGeneratorFactory;

import static org.junit.jupiter.api.Assertions.*;

class GameServerControllerTest {

    @Test
    void makeMove() {
        final var randomFactory = RandomGeneratorFactory.getDefault();
        final long seed1 = randomFactory.create().nextLong();
        final var game = LobbyServerController.createGame(0, randomFactory.create(seed1),
                List.of(new LobbyPlayer("example_player_1"),
                        new LobbyPlayer("example_player_2")));
        GameServerController gsc = new GameServerController(new LockProtected<>(game));
        /*
         * valid positions for 2 players board
         * ****0**1**2**3**4**5**6**7**8
         * 0 { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
         * 1 { 0, 0, 0, 1, 1, 0, 0, 0, 0 },
         * 2 { 0, 0, 0, 1, 1, 1, 0, 0, 0 },
         * 3 { 0, 0, 1, 1, 1, 1, 1, 1, 0 },
         * 4 { 0, 1, 1, 1, 1, 1, 1, 1, 0 },
         * 5 { 0, 1, 1, 1, 1, 1, 1, 0, 0 },
         * 6 { 0, 0, 0, 1, 1, 1, 0, 0, 0 },
         * 7 { 0, 0, 0, 0, 1, 1, 0, 0, 0 },
         * 8 { 0, 0, 0, 0, 0, 0, 0, 0, 0 }
         */

        //expects throw of IllegalArgumentException("Invalid move") if selected tiles are invalid
        List<BoardCoord> selectedWrong = new ArrayList<>();
        selectedWrong.add(new BoardCoord(0, 0));
        selectedWrong.add(new BoardCoord(0, 1));

        assertThrows(IllegalArgumentException.class, () -> gsc.makeMove(game.getStartingPlayer(),
                selectedWrong, 0));

        List<BoardCoord> selected = new ArrayList<>();
        selected.add(new BoardCoord(1, 3));
        selected.add(new BoardCoord(1, 4));

        //temporary memo of extracted tiles from board
        Property<Tile> tileProp0 = game.getBoard().tile(selected.get(0).row(), selected.get(0).col());
        Property<Tile> tileProp1 = game.getBoard().tile(selected.get(1).row(), selected.get(1).col());

        gsc.makeMove(game.getStartingPlayer(), selected, 0);

        //expected tiles in position (1,3) and (1,4) to be removed from board and set to null
        assertNull(game.getBoard().tile(selected.get(0).row(), selected.get(0).col()).get());
        assertNull(game.getBoard().tile(selected.get(1).row(), selected.get(1).col()).get());
        //expected tiles in shelfie in column 0, to be !=null and to be equal to the ones extracted from boards,
        //following extraction order
        assertEquals(tileProp0.get(), game.getStartingPlayer().getShelfie().tile(0, 0).get());
        assertEquals(tileProp1.get(), game.getStartingPlayer().getShelfie().tile(1, 0).get());
        //after make move expected IllegalArgumentException("It's not this player turn") if the player who made
        //the move attempts another move
        List<BoardCoord> selected1 = new ArrayList<>();
        selected1.add(new BoardCoord(2, 3));
        selected1.add(new BoardCoord(2, 4));
        assertThrows(IllegalArgumentException.class, () -> gsc.makeMove(game.getStartingPlayer(), selected1, 0),
                "It's not this player turn");

    }
}