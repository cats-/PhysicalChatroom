package cats.pchat.client.handler;

import cats.pchat.client.Client;
import cats.pchat.core.connection.data.type.impl.JoinData;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 10:12 PM
 */
public class JoinHandler extends AbstractHandler<JoinData>{

    public void handle(final JoinData data){
        Client.profiles().add(data.value());
        Client.profileList().update();
    }
}
