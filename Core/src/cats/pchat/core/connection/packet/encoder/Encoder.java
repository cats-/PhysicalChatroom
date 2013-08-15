package cats.pchat.core.connection.packet.encoder;

import java.nio.ByteBuffer;

/**
 * Josh
 * 14/08/13
 * 10:08 AM
 */
public interface Encoder<T> {

    public void encode(final ByteBuffer buffer, final T object);
}
