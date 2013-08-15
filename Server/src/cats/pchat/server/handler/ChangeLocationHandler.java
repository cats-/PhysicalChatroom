package cats.pchat.server.handler;

import cats.pchat.core.GameConstants;
import cats.pchat.core.connection.Connection;
import cats.pchat.core.connection.packet.Packet;
import cats.pchat.server.Server;
import cats.pchat.server.cache.Cache;
import cats.pchat.server.profile.ServerProfile;
import java.awt.Point;

/**
 * Josh
 * 14/08/13
 * 9:28 PM
 */
public class ChangeLocationHandler extends AbstractHandler implements GameConstants{

    public void handle(final Connection connection, final Packet packet){
        final Point location = packet.get(Point.class, 1);
        if(!GAME_BOUNDS.contains(location))
            return;
        final ServerProfile profile = Server.profiles().get(connection);
        profile.location = location;
        Server.profiles().forEach(p -> p.send(new Packet(CHANGE_LOCATION, profile.uid(), location)));
        Cache.writeProfiles();
    }

}
