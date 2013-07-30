package cats.pchat.core.connection.data.type;

import java.io.Serializable;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 6:31 PM
 */
public class UidValueData<T> extends ValueData<T> implements Serializable {

    private final byte uid;

    public UidValueData(final byte opcode, final byte uid, final T value){
        super(opcode, value);
        this.uid = uid;
    }

    public byte uid(){
        return uid;
    }
}
