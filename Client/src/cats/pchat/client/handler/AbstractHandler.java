package cats.pchat.client.handler;

import cats.pchat.core.connection.packet.Packet;

/**
 * Josh
 * 14/08/13
 * 11:26 PM
 */
public abstract class AbstractHandler {

    public abstract void handle(final Packet packet);
}
