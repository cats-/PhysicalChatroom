package cats.pchat.core.connection.data.type.impl;

import cats.pchat.core.connection.data.type.ValueData;
import java.io.Serializable;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 7:04 PM
 */
public class LeaveData extends ValueData<Byte> implements Serializable{

    public LeaveData(final byte uid){
        super(LEAVE, uid);
    }

}
