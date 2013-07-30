package cats.pchat.core.connection.data.type.impl;

import cats.pchat.core.connection.data.type.UidValueData;
import java.awt.Color;
import java.io.Serializable;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 7:07 PM
 */
public class ChangeColorData extends UidValueData<Color> implements Serializable{

    public ChangeColorData(final byte uid, final Color color){
        super(CHANGE_COLOR, uid, color);
    }
}
