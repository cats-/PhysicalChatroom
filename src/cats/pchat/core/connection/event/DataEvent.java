package cats.pchat.core.connection.event;

import cats.pchat.core.connection.Connection;
import cats.pchat.core.connection.data.Data;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 6:09 PM
 */
public class DataEvent extends ConnectionEvent{

    private final Data data;

    public DataEvent(final Connection connection, final Data data){
        super(connection);
        this.data = data;
    }

    public Data data(){
        return data;
    }

    public boolean reply(final Data data){
        return connection.send(data);
    }
}
