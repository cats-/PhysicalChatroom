package cats.phat.server.handler;

import cats.pchat.core.connection.data.type.impl.ChangeNameData;
import cats.pchat.core.connection.data.type.impl.Message;
import cats.phat.server.Server;
import java.awt.Color;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 9:15 PM
 */
public class ChangeNameHandler extends AbstractHandler<ChangeNameData>{

    public void handle(final ChangeNameData data){
        if(Server.profiles().stream().filter(p -> p.name().get().equalsIgnoreCase(data.value())).findFirst().orElse(null) != null){
            Server.profiles().profile(data.uid()).send(new Message("That name is already taken", Color.RED));
            return;
        }
        Server.profiles().profile(data.uid()).name().set(data.value());
    }
}
