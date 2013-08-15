package cats.pchat.core.profile;

import java.awt.Color;
import java.awt.Point;

/**
 * Josh
 * 14/08/13
 * 11:40 AM
 */
public class BasicProfile {

    protected final long uid;

    public String name;
    public Color color;
    public Point location;

    public BasicProfile(final long uid){
        this.uid = uid;
        name = null;
        color = Color.BLACK;
        location = new Point();
    }

    public Object[] array(){
        return new Object[]{uid, name, color, location};
    }

    public long uid(){
        return uid;
    }

    public boolean equals(final Object o){
        if(o == null)
            return false;
        if(o instanceof String)
            return ((String) o).equalsIgnoreCase(name);
        else if(o instanceof Long)
            return o == uid || o.equals(uid);
        else if(o instanceof BasicProfile)
            return equals(((BasicProfile)o).uid);
        else
            return false;
    }
}
