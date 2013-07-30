package cats.pchat.core.connection.event;

import cats.pchat.core.Constants;
import cats.pchat.core.connection.Connection;
import java.util.Date;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 6:01 PM
 */
public class ConnectionEvent implements Constants{

    protected final Date time;
    protected final Connection connection;

    public ConnectionEvent(final Connection connection){
        this.connection = connection;

        time = new Date();
    }

    public Connection connection(){
        return connection;
    }

    public Date time(){
        return time;
    }

    public String timestamp(){
        return TIME_FORMAT.format(time);
    }
}
