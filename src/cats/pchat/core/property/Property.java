package cats.pchat.core.property;

import cats.pchat.core.property.event.PropertyValueChangeListener;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 6:39 PM
 */
public class Property<T> implements Serializable{

    private T value;

    private final List<PropertyValueChangeListener<T>> listeners;

    public Property(final T value){
        this.value = value;

        listeners = new LinkedList<>();
    }

    public Property(){
        this(null);
    }

    public void addListener(final PropertyValueChangeListener<T> l){
        listeners.add(l);
    }

    public void removeListener(final PropertyValueChangeListener<T> l){
        listeners.remove(l);
    }

    private void fireChange(final T o, final T n){
        listeners.forEach(l -> l.onPropertyValueChange(this, o, n));
    }

    public void set(final T value){
        final T old = this.value;
        this.value = value;
        fireChange(old, value);
    }

    public void privateSet(final T value){
        this.value = value;
    }

    public T get(){
        return value;
    }
}
