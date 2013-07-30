package cats.pchat.core.profile;

import cats.pchat.core.connection.Connection;
import cats.pchat.core.connection.data.Data;
import cats.pchat.core.property.Property;
import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 6:39 PM
 */
public class Profile implements Serializable, Cloneable{

    private static final Point DEFAULT_POS = new Point(0, 0);
    private static final Color DEFAULT_COLOR = Color.BLACK;

    public transient Connection connection;

    private final Property<Byte> uid;
    private final Property<Color> color;
    private final Property<Point> pos;
    private final Property<String> name;

    public Profile(){
        uid = new Property<>();
        color = new Property<>(DEFAULT_COLOR);
        pos = new Property<>(DEFAULT_POS);
        name = new Property<>();
    }

    public boolean send(final Data data){
        if(connection == null)
            return false;
        return connection.send(data);
    }
    
    public Profile clone(){
        final Profile clone = new Profile();
        clone.uid.privateSet(uid.get());
        clone.name.privateSet(name.get());
        clone.color.privateSet(color.get());
        clone.pos.privateSet(pos.get());
        return clone;
    }

    public Property<Byte> uid(){
        return uid;
    }

    public Property<Color> color(){
        return color;
    }

    public Property<Point> pos(){
        return pos;
    }

    public Property<String> name(){
        return name;
    }

    public boolean equals(final Object o){
        if(o == null)
            return false;
        if(o instanceof Byte)
            return uid.get().equals(o);
        else if(o instanceof Profile)
            return uid.get() == ((Profile)o).uid.get();
        else
            return false;
    }
}
