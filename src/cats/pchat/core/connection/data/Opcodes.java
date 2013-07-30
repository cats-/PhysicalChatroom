package cats.pchat.core.connection.data;

import java.io.Serializable;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 6:10 PM
 */
public interface Opcodes extends Serializable{

    byte MESSAGE = 0x0;

    byte CHANGE_NAME = 0x1;
    byte CHANGE_COLOR = 0x2;
    byte CHANGE_POS = 0x3;

    byte JOIN = 0x4;
    byte LEAVE = 0x5;

    byte ASSIGN = 0x6;
}
