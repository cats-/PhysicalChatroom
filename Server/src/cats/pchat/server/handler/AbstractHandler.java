package cats.pchat.server.handler;

import cats.pchat.core.connection.Connection;
import cats.pchat.core.connection.packet.Opcodes;
import cats.pchat.core.connection.packet.Packet;

/**
 * Josh
 * 14/08/13
 * 4:48 PM
 */
public abstract class AbstractHandler implements Opcodes{

    public abstract void handle(final Connection connection, final Packet packet);
}
