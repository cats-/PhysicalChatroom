package cats.pchat.client.handler;

import cats.pchat.client.Client;
import cats.pchat.core.connection.packet.Packet;

/**
 * Josh
 * 14/08/13
 * 11:47 PM
 */
public class MessageHandler extends AbstractHandler{

    public void handle(final Packet packet){
        Client.messageList.append(packet);
    }
}
