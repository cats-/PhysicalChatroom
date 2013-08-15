package cats.pchat.core.utils;

import java.awt.Color;
import java.awt.Point;

/**
 * Josh
 * 14/08/13
 * 3:49 PM
 */
public final class CapacityUtils {

    private static final byte INT = 4;
    private static final byte LONG = 8;
    private static final byte COLOR = INT;
    private static final byte POINT = INT + INT;

    private CapacityUtils(){}

    public static int capacity(final Object... objects){
        int capacity = 0;
        for(final Object o : objects){
            if(o instanceof String)
                capacity += INT + ((String)o).length();
            else if(o instanceof Long)
                capacity += LONG;
            else if(o instanceof Color)
                capacity += COLOR;
            else if(o instanceof Point)
                capacity += POINT;
            else
                throw new IllegalArgumentException("Error finding capacity for: " + o.getClass());
        }
        return capacity;
    }
}
