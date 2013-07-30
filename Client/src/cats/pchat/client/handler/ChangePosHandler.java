package cats.pchat.client.handler;

import cats.pchat.client.Client;
import cats.pchat.core.connection.data.type.impl.ChangePosData;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 10:13 PM
 */
public class ChangePosHandler extends AbstractHandler<ChangePosData>{

    public void handle(final ChangePosData data){
        if(Client.profile().equals(data.uid()))
            Client.profile().pos().set(data.value());
        else
            Client.profiles().profile(data.uid()).pos().set(data.value());
        Client.profileList().update();
    }
}
