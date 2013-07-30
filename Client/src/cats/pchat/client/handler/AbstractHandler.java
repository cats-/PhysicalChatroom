package cats.pchat.client.handler;

import cats.pchat.core.connection.data.Data;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 10:08 PM
 */
public abstract class AbstractHandler<T extends Data> {

    public abstract void handle(final T data);
}
