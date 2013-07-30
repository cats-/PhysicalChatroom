package cats.pchat.core;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 5:57 PM
 */
public interface Constants extends Serializable{

    int PORT = 7864;
    String HOST = "localhost";

    String TITLE = "CatsPhysicalChat";

    DateFormat TIME_FORMAT = new SimpleDateFormat("hh:mm:ss");
}
