package cats.pchat.client.handler;

import cats.pchat.client.Client;
import cats.pchat.client.auth.AuthWindow;
import cats.pchat.client.profile.ClientProfile;
import cats.pchat.core.connection.packet.Packet;
import java.awt.Color;
import java.awt.Point;

/**
 * Josh
 * 14/08/13
 * 11:26 PM
 */
public class AssignHandler extends AbstractHandler{

    public void handle(final Packet packet){
        final long uid = packet.get(Long.class, 0);
        final String name = packet.get(String.class, 1);
        final Color color = packet.get(Color.class, 2);
        final Point location = packet.get(Point.class, 3);
        Client.self = new ClientProfile(uid);
        Client.self.name = name;
        Client.self.color = color;
        Client.self.location = location;
        Client.inputArea.update();
        AuthWindow.INSTANCE.dispose();
    }
}
