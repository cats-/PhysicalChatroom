package cats.pchat.core.property.event;

import cats.pchat.core.property.Property;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 6:43 PM
 */
public interface PropertyValueChangeListener<T> {

    public void onPropertyValueChange(final Property<T> property, final T oldValue, final T newValue);
}
