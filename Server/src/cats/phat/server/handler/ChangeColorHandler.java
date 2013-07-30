package cats.phat.server.handler;

import cats.pchat.core.connection.data.type.impl.ChangeColorData;
import cats.phat.server.Server;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 9:15 PM
 */
public class ChangeColorHandler extends AbstractHandler<ChangeColorData>{

    public void handle(final ChangeColorData data){
        Server.profiles().profile(data.uid()).color().set(data.value());
    }
}
