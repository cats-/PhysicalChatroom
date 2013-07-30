package cats.pchat.core.connection.event;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 6:17 PM
 */
public interface ConnectionListener extends Listener{

    public void onConnectionClosed(final ConnectionEvent e);
}
