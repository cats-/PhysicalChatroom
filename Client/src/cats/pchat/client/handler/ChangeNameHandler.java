package cats.pchat.client.handler;

import cats.pchat.client.Client;
import cats.pchat.core.connection.data.type.impl.ChangeNameData;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 10:11 PM
 */
public class ChangeNameHandler extends AbstractHandler<ChangeNameData>{

    public void handle(final ChangeNameData data){
        if(Client.profile().equals(data.uid()))
            Client.profile().name().set(data.value());
        else
            Client.profiles().profile(data.uid()).name().set(data.value());
        Client.profileList().update();
    }
}
