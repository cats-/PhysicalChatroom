package cats.pchat.server.handler;

import cats.pchat.core.connection.Connection;
import cats.pchat.core.connection.packet.Packet;
import cats.pchat.server.Server;
import cats.pchat.server.cache.Cache;
import cats.pchat.server.profile.ServerProfile;
import java.awt.Color;

/**
 * Josh
 * 14/08/13
 * 9:26 PM
 */
public class ChangeColorHandler extends AbstractHandler{

    public void handle(final Connection connection, final Packet packet){
        final Color color = packet.get(Color.class, 1);
        final ServerProfile profile = Server.profiles().get(connection);
        profile.color = color;
        Server.profiles().forEach(p -> p.send(new Packet(CHANGE_COLOR, profile.uid(), color)));
        Cache.writeProfiles();
    }
}
