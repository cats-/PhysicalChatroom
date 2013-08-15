package cats.pchat.core.connection.event;

import cats.pchat.core.connection.Connection;
import cats.pchat.core.connection.packet.Packet;

/**
 * Josh
 * 14/08/13
 * 10:56 AM
 */
public interface PacketListener {

    public void packetReceived(final Connection connection, final Packet packet);
}
