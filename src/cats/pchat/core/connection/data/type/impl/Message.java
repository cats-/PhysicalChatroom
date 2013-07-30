package cats.pchat.core.connection.data.type.impl;

import cats.pchat.core.connection.data.type.UidValueData;
import java.awt.Color;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 6:34 PM
 */
public class Message extends UidValueData<String> implements Serializable{

    private static final HashMap<Byte, Color> MAP = new HashMap<>();

    static{
        MAP.put(JOIN, Color.GREEN);
        MAP.put(LEAVE, Color.RED);
        MAP.put(CHANGE_NAME, Color.MAGENTA);
        MAP.put(CHANGE_COLOR, Color.ORANGE);
    }

    private final Color color;

    public Message(final byte uid, final String message, final Color color){
        super(MESSAGE, uid, message);
        this.color = color;
    }

    public Message(final byte uid, final String message){
        this(uid, message, MAP.getOrDefault(uid, Color.BLACK));
    }

    public Message(final String message, final Color color){
        this((byte)-1, message, color);
    }

    public Color color(){
        return color;
    }

    public String formatted(){
        return String.format("[%s] %s", timestamp(), value);
    }
}
