package cats.phat.server.handler;

import cats.pchat.core.connection.data.Data;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 8:35 PM
 */
public abstract class AbstractHandler<T extends Data> {

    public abstract void handle(final T data);
}
