package cats.pchat.client.handler;

import cats.pchat.client.Client;
import cats.pchat.client.profile.ClientProfile;
import cats.pchat.core.connection.packet.Packet;
import java.awt.Point;

/**
 * Josh
 * 14/08/13
 * 11:50 PM
 */
public class ChangeLocationHandler extends AbstractHandler{

    public void handle(final Packet packet){
        final long uid = packet.get(Long.class, 0);
        final Point location = packet.get(Point.class, 1);
        if(Client.self.equals(uid)){
            Client.self.location.setLocation(location);
            return;
        }
        final ClientProfile profile = Client.profiles().get(uid);
        profile.location.setLocation(location);
    }
}
