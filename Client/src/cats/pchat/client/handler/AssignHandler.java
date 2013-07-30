package cats.pchat.client.handler;

import cats.pchat.client.Client;
import cats.pchat.core.connection.data.type.impl.AssignData;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 10:16 PM
 */
public class AssignHandler extends AbstractHandler<AssignData>{

    public void handle(final AssignData data){
        Client.profile().uid().set(data.value());
        Client.game().setup();
    }
}
