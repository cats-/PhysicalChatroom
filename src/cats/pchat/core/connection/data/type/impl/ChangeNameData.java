package cats.pchat.core.connection.data.type.impl;

import cats.pchat.core.connection.data.type.UidValueData;
import java.io.Serializable;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 7:05 PM
 */
public class ChangeNameData extends UidValueData<String> implements Serializable{

    public ChangeNameData(final byte uid, final String name){
        super(CHANGE_NAME, uid, name);
    }
}
