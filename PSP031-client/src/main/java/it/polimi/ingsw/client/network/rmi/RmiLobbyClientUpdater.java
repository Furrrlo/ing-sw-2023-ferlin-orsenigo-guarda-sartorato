package it.polimi.ingsw.client.network.rmi;

import it.polimi.ingsw.GameAndController;
import it.polimi.ingsw.client.updater.LobbyClientUpdater;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Lobby;
import it.polimi.ingsw.rmi.RmiGameUpdater;
import it.polimi.ingsw.rmi.RmiLobbyUpdater;
import it.polimi.ingsw.rmi.UnicastRemoteObjects;
import it.polimi.ingsw.updater.GameUpdater;

import java.rmi.RemoteException;

class RmiLobbyClientUpdater extends LobbyClientUpdater implements RmiLobbyUpdater {

    public RmiLobbyClientUpdater(Lobby lobby) {
        super(lobby);
    }

    Lobby getGameCreationState() {
        return lobby;
    }

    @Override
    protected GameUpdater createGameUpdater(GameAndController<Game> gameAndController) {
        try {
            return new RmiGameUpdater.Adapter(UnicastRemoteObjects.export(
                    new RmiGameClientUpdater(gameAndController.game()), 0));
        } catch (RemoteException e) {
            throw new IllegalStateException("Unexpectedly failed to export RmiGameClientUpdater", e);
        }
    }
}