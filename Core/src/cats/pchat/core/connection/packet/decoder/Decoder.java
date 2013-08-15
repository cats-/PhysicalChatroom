package cats.pchat.core.connection.packet.decoder;

import java.nio.ByteBuffer;

/**
 * Josh
 * 14/08/13
 * 10:09 AM
 */
public interface Decoder<T> {

    public T decode(final ByteBuffer buffer);
}
