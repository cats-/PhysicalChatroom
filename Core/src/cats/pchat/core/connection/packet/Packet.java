package cats.pchat.core.connection.packet;

import cats.pchat.core.connection.packet.decoder.Decoders;
import cats.pchat.core.connection.packet.encoder.Encoders;
import cats.pchat.core.utils.CapacityUtils;
import java.awt.Color;
import java.awt.Point;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * Josh
 * 14/08/13
 * 10:01 AM
 */
public class Packet implements Opcodes{

    private static final Map<Byte, Class<?>[]> CLASSES = new HashMap<>();

    static{
        CLASSES.put(REGISTER, classes(String.class, String.class));
        CLASSES.put(LOGIN, classes(String.class, String.class));
        CLASSES.put(POPUP_MESSAGE, classes(String.class));
        CLASSES.put(ASSIGN, classes(Long.class, String.class, Color.class, Point.class));
        CLASSES.put(JOIN, classes(Long.class, String.class, Color.class, Point.class));
        CLASSES.put(LEAVE, classes(Long.class));
        CLASSES.put(CHANGE_COLOR, classes(Long.class, Color.class));
        CLASSES.put(CHANGE_LOCATION, classes(Long.class, Point.class));
        CLASSES.put(MESSAGE, classes(String.class, Color.class));
    }

    private static Class<?>[] classes(final Class<?>... classes){
        return classes;
    }

    private final byte opcode;
    private final Object[] args;

    public Packet(final byte opcode, final Object... args){
        this.opcode = opcode;
        this.args = args;

        final Class<?>[] classes = CLASSES.get(opcode);
        assert classes.length == args.length;
        for(int i = 0; i < args.length; i++)
            assert args[i].getClass().equals(classes[i]);
    }

    public byte opcode(){
        return opcode;
    }

    public <T> T get(final Class<T> clazz, final int index){
        return clazz.cast(args[index]);
    }

    public ByteBuffer encode(){
        final ByteBuffer buffer = ByteBuffer.allocate(1 + CapacityUtils.capacity(args));
        buffer.put(opcode);
        for(final Object arg : args)
            Encoders.get(arg.getClass())[0].encode(buffer, arg);
        return buffer;
    }

    public static Packet decode(final ByteBuffer buffer){
        final byte opcode = buffer.get();
        final Class<?>[] classes = CLASSES.get(opcode);
        final Object[] args = new Object[classes.length];
        for(int i = 0; i < args.length; i++)
            args[i] = Decoders.get(classes[i])[0].decode(buffer);
        return new Packet(opcode, args);
    }
}
