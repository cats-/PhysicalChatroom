package cats.pchat.core.profile;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Josh
 * 14/08/13
 * 11:46 AM
 */
public class Profiles<T extends BasicProfile> extends CopyOnWriteArrayList<T> {

    public T get(final Object o){
        return stream().filter(p -> p.equals(o)).findFirst().orElse(null);
    }

    public boolean contains(final Object o){
        return get(o) != null;
    }
}
