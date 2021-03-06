package cats.pchat.client.handler;

import cats.pchat.client.Client;
import cats.pchat.client.profile.ClientProfile;
import cats.pchat.core.connection.packet.Packet;
import java.awt.Color;

/**
 * Josh
 * 14/08/13
 * 11:47 PM
 */
public class ChangeColorHandler extends AbstractHandler{

    public void handle(final Packet packet){
        final long uid = packet.get(Long.class, 0);
        final Color color = packet.get(Color.class, 1);
        if(Client.self.equals(uid)){
            Client.self.color = color;
            Client.inputArea.update();
            return;
        }
        final ClientProfile profile = Client.profiles().get(uid);
        profile.color = color;
        Client.profileList.update(profile);
    }
}
