package cats.pchat.client.handler;

import cats.pchat.client.Client;
import cats.pchat.core.connection.data.type.impl.LeaveData;
import cats.pchat.core.profile.Profile;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 10:13 PM
 */
public class LeaveHandler extends AbstractHandler<LeaveData>{

    public void handle(final LeaveData data){
        final Profile profile = Client.profiles().profile(data.value());
        Client.profiles().remove(profile);
        Client.profileList().update();
    }
}
