package it.polimi.ingsw.server.rmi;

import it.polimi.ingsw.rmi.*;
import it.polimi.ingsw.server.controller.ServerController;
import org.jetbrains.annotations.VisibleForTesting;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RmiConnectionServerController implements RmiConnectionController {

    private final ServerController controller;

    public static void bind(ServerController controller) throws RemoteException {
        bind(RmiConnectionController.REMOTE_NAME, controller);
    }

    @VisibleForTesting
    public static void bind(String remoteName,
                            ServerController controller)
            throws RemoteException {
        LocateRegistry.createRegistry(1099).rebind(
                remoteName,
                UnicastRemoteObjects.export(new RmiConnectionServerController(controller), 0));
    }

    private RmiConnectionServerController(ServerController controller) {
        this.controller = controller;
    }

    @Override
    public void joinGame(String nick,
                         RmiHeartbeatHandler handler,
                         RmiLobbyUpdaterFactory updaterFactory)
            throws RemoteException {
        controller.joinGame(
                nick,
                new RmiHeartbeatHandler.Adapter(handler),
                new RmiLobbyUpdaterFactory.Adapter(updaterFactory),
                () -> {
                    try {
                        return new RmiGameController.Adapter(
                                UnicastRemoteObjects.export(new RmiGameServerController(), 0));
                    } catch (RemoteException e) {
                        throw new IllegalStateException("Unexpectedly failed to export RmiGameServerController", e);
                    }
                });
    }
}