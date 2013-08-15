package cats.pchat.core.connection.packet.encoder;

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
public final class Encoders {

    private static final Map<Class<?>, Encoder<?>> MAP = new HashMap<>();

    static{
        MAP.put(String.class, (Encoder<String>) (buffer, string) -> {
            buffer.putInt(string.length());
            for(final char c : string.toCharArray())
                buffer.put((byte)c);
        });
        MAP.put(Long.class, (Encoder<Long>) ByteBuffer::putLong);
        MAP.put(Color.class, (Encoder<Color>) (buffer, color) -> buffer.putInt(color.getRGB()));
        MAP.put(Point.class, (Encoder<Point>) (buffer, point) -> {
            buffer.putInt(point.x);
            buffer.putInt(point.y);
        });
    }

    private Encoders(){}

    public static Encoder[] get(final Class... classes){
        final Encoder[] encoders = new Encoder[classes.length];
        for(int i = 0; i < classes.length; i++)
            encoders[i] = MAP.get(classes[i]);
        return encoders;
    }
}
