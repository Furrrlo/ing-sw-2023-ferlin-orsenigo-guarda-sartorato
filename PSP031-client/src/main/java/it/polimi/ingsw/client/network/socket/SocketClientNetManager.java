package it.polimi.ingsw.client.network.socket;

import it.polimi.ingsw.client.network.ClientNetManager;
import it.polimi.ingsw.model.Lobby;
import it.polimi.ingsw.model.LobbyView;
import it.polimi.ingsw.socket.packets.*;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;

public class SocketClientNetManager implements ClientNetManager {
    private final ClientSocketManager socketManager;

    public SocketClientNetManager(SocketAddress address) throws IOException {
        final Socket socket = new Socket();
        socket.connect(address);
        System.out.println("Connected to : " + address);
        socketManager = new ClientSocketManagerImpl(socket);
    }

    @Override
    public LobbyView joinGame(String nick) throws IOException {
        socketManager.setNick(nick);
        Lobby lobby;
        try (var lobbyCtx = socketManager.send(new JoinGamePacket(nick), LobbyPacket.class)) {
            lobby = lobbyCtx.getPacket().lobby();
            new Thread(new SocketLobbyClientUpdater(lobby, socketManager)).start();
        }
        return lobby;
    }
}
