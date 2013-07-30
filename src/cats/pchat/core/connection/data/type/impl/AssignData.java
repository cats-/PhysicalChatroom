package cats.pchat.core.connection.data.type.impl;

import cats.pchat.core.connection.data.type.ValueData;
import java.io.Serializable;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 8:16 PM
 */
public class AssignData extends ValueData<Byte> implements Serializable{

    public AssignData(final Byte uid){
        super(ASSIGN, uid);
    }
}
