package cats.pchat.core.connection.event;

import cats.pchat.core.connection.Connection;

/**
 * Josh
 * 14/08/13
 * 10:54 AM
 */
public interface ConnectionListener {

    public void connectionClosed(final Connection connection);
}
