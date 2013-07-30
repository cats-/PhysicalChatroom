package cats.pchat.core.connection.data.type;

import cats.pchat.core.connection.data.Data;
import java.io.Serializable;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 6:29 PM
 */
public class ValueData<T> extends Data implements Serializable{

    protected final T value;

    public ValueData(final byte opcode, final T value){
        super(opcode);
        this.value = value;
    }

    public T value(){
        return value;
    }
}
