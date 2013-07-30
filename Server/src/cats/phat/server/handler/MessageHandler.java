package cats.phat.server.handler;

import cats.pchat.core.connection.data.type.impl.Message;
import cats.pchat.core.profile.Profile;
import cats.phat.server.Server;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 8:39 PM
 */
public class MessageHandler extends AbstractHandler<Message>{

    public void handle(final Message message){
        final Profile profile = Server.profiles().profile(message.uid());
        final Message msg = new Message(String.format("%s: %s", profile.name().get(), message.value()), profile.color().get());
        Server.send(msg);
    }
}
