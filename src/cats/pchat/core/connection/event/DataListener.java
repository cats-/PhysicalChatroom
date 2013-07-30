package cats.pchat.core.connection.event;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 6:17 PM
 */
public interface DataListener extends Listener{

    public void onDataReceived(final DataEvent e);
}
