package cats.pchat.client.handler;

import cats.pchat.client.Client;
import cats.pchat.core.connection.data.type.impl.ChangeColorData;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 10:12 PM
 */
public class ChangeColorHandler extends AbstractHandler<ChangeColorData>{

    public void handle(final ChangeColorData data){
        if(Client.profile().equals(data.uid()))
            Client.profile().color().set(data.value());
        else
            Client.profiles().profile(data.uid()).color().set(data.value());
        Client.profileList().update();
    }
}
