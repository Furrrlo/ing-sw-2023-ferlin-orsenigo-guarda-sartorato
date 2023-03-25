package it.polimi.ingsw.server.socket;

import it.polimi.ingsw.DisconnectedException;
import it.polimi.ingsw.model.Lobby;
import it.polimi.ingsw.socket.SocketLobbyUpdaterFactory;
import it.polimi.ingsw.socket.SocketManager;
import it.polimi.ingsw.socket.packets.*;
import it.polimi.ingsw.updater.LobbyUpdater;

import java.io.IOException;

public class SocketLobbyServerUpdaterFactory implements SocketLobbyUpdaterFactory {

    private final ServerSocketManager socketManager;
    private final SocketManager.PacketReplyContext<C2SAckPacket, S2CAckPacket, JoinGamePacket> ctx;

    public SocketLobbyServerUpdaterFactory(ServerSocketManager socketManager,
                                           SocketManager.PacketReplyContext<C2SAckPacket, S2CAckPacket, JoinGamePacket> ctx) {
        this.socketManager = socketManager;
        this.ctx = ctx;
    }

    @Override
    public LobbyUpdater create(Lobby lobby) throws DisconnectedException {
        try {
            ctx.reply(new LobbyPacket(lobby), C2SAckPacket.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new SocketLobbyServerUpdater(socketManager);
    }
}
