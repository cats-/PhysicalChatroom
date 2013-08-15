package cats.pchat.client.handler;

import cats.pchat.client.Client;
import cats.pchat.client.profile.ClientProfile;
import cats.pchat.core.connection.packet.Packet;

/**
 * Josh
 * 14/08/13
 * 11:46 PM
 */
public class LeaveHandler extends AbstractHandler {

    public void handle(final Packet packet){
        final long uid = packet.get(Long.class, 0);
        final ClientProfile profile = Client.profiles().get(uid);
        Client.profiles().remove(profile);
        Client.profileList.remove(profile);
    }
}
