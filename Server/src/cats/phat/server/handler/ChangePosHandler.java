package cats.phat.server.handler;

import cats.pchat.core.connection.data.type.impl.ChangePosData;
import cats.phat.server.Server;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 9:16 PM
 */
public class ChangePosHandler extends AbstractHandler<ChangePosData>{

    public void handle(final ChangePosData data){
        Server.profiles().profile(data.uid()).pos().set(data.value());
    }
}
