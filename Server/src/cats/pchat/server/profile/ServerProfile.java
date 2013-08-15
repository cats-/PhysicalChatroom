package cats.pchat.server.profile;

import cats.pchat.core.connection.Connection;
import cats.pchat.core.connection.packet.Packet;
import cats.pchat.core.connection.packet.decoder.Decoder;
import cats.pchat.core.connection.packet.decoder.Decoders;
import cats.pchat.core.connection.packet.encoder.Encoder;
import cats.pchat.core.connection.packet.encoder.Encoders;
import cats.pchat.core.profile.BasicProfile;
import cats.pchat.core.utils.CapacityUtils;
import java.awt.Color;
import java.awt.Point;
import java.nio.ByteBuffer;

/**
 * Josh
 * 14/08/13
 * 11:49 AM
 */
public class ServerProfile extends BasicProfile{

    private static final Class<?>[] CLASSES = {Long.class, String.class, String.class, Color.class, Point.class};

    public Connection connection;
    public String pass;

    public ServerProfile(final long uid){
        super(uid);
        pass = null;
        connection = null;
    }

    public boolean send(final Packet packet){
        if(connection == null)
            return false;
        return connection.send(packet);
    }

    public boolean equals(final Object o){
        if(o == null)
            return false;
        if(super.equals(o))
            return true;
        if(o instanceof Connection)
            return connection.equals(o);
        else
            return false;
    }

    public ByteBuffer encode(){
        final Object[] data = {uid, name, pass, color, location};
        final Encoder[] encoders = Encoders.get(CLASSES);
        final ByteBuffer buffer = ByteBuffer.allocate(CapacityUtils.capacity(data));
        for(int i = 0; i < data.length; i++)
            encoders[i].encode(buffer, data[i]);
        return buffer;
    }

    public static ServerProfile decode(final ByteBuffer buffer){
        final Decoder[] decoders = Decoders.get(CLASSES);
        final Object[] data = new Object[decoders.length];
        for(int i = 0; i < data.length; i++)
            data[i] = decoders[i].decode(buffer);
        final ServerProfile profile = new ServerProfile((Long)data[0]);
        profile.name = (String)data[1];
        profile.pass = (String)data[2];
        profile.color = (Color)data[3];
        profile.location = (Point)data[4];
        return profile;
    }
}
