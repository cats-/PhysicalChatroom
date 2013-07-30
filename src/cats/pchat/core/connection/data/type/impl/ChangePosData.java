package cats.pchat.core.connection.data.type.impl;

import cats.pchat.core.connection.data.type.UidValueData;
import java.awt.Point;
import java.io.Serializable;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 7:06 PM
 */
public class ChangePosData extends UidValueData<Point> implements Serializable{

    public ChangePosData(final byte uid, final Point pos){
        super(CHANGE_POS, uid, pos);
    }

}
