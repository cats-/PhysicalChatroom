package cats.pchat.core.connection.packet;

/**
 * Josh
 * 14/08/13
 * 10:13 AM
 */
public interface Opcodes {

    byte REGISTER = 0x0;
    byte LOGIN = 0x1;

    byte POPUP_MESSAGE = 0x2;

    byte ASSIGN = 0x3;

    byte JOIN = 0x4;
    byte LEAVE = 0x5;

    byte CHANGE_COLOR = 0x6;
    byte CHANGE_LOCATION = 0x7;

    byte MESSAGE = 0x8;
}
