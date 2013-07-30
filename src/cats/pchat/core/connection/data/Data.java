package cats.pchat.core.connection.data;

import cats.pchat.core.Constants;
import java.io.Serializable;
import java.util.Date;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 6:09 PM
 */
public class Data implements Serializable, Constants, Opcodes{

    protected final Date time;
    protected final byte opcode;

    public Data(final byte opcode){
        this.opcode = opcode;

        time = new Date();
    }

    public byte opcode(){
        return opcode;
    }

    public Date time(){
        return time;
    }

    public String timestamp(){
        return TIME_FORMAT.format(time);
    }
}
