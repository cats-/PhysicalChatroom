package cats.pchat.core.connection.packet.decoder;

import java.awt.Color;
import java.awt.Point;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * Josh
 * 14/08/13
 * 10:22 AM
 */
public final class Decoders {

    private static final Map<Class<?>, Decoder<?>> MAP = new HashMap<>();

    static{
        MAP.put(String.class, (Decoder<String>) buffer -> {
            final int length = buffer.getInt();
            final StringBuilder builder = new StringBuilder(length);
            for(int n = 0; n < length; n++)
                builder.append((char)buffer.get());
            return builder.toString();
        });
        MAP.put(Long.class, (Decoder<Long>) ByteBuffer::getLong);
        MAP.put(Color.class, (Decoder<Color>) buffer -> new Color(buffer.getInt()));
        MAP.put(Point.class, (Decoder<Point>) buffer -> new Point(buffer.getInt(), buffer.getInt()));
    }

    private Decoders(){}

    public static Decoder[] get(final Class... classes){
        final Decoder[] decoders = new Decoder[classes.length];
        for(int i = 0; i < classes.length; i++)
            decoders[i] = MAP.get(classes[i]);
        return decoders;
    }
}
