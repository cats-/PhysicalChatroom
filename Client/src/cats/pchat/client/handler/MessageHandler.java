package cats.pchat.client.handler;

import cats.pchat.client.Client;
import cats.pchat.core.connection.data.type.impl.Message;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 10:08 PM
 */
public class MessageHandler extends AbstractHandler<Message>{

    public void handle(final Message message){
        Client.messageArea().push(message);
    }
}
