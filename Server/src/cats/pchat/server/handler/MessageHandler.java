package cats.pchat.server.handler;

import cats.pchat.core.connection.Connection;
import cats.pchat.core.connection.packet.Packet;
import cats.pchat.server.Server;
import cats.pchat.server.profile.ServerProfile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Josh
 * 14/08/13
 * 9:21 PM
 */
public class MessageHandler extends AbstractHandler {

    private static final DateFormat FORMAT = new SimpleDateFormat("'['hh:mm:ss']'");

    public void handle(final Connection connection, final Packet packet){
        final String message = packet.get(String.class, 0);
        final ServerProfile profile = Server.profiles().get(connection);
        final String msg = String.format("%s %s: %s", timestamp(), profile.name, message);
        Server.profiles().forEach(p -> p.send(new Packet(MESSAGE, msg, profile.color)));
    }

    private static String timestamp(){
        return FORMAT.format(new Date());
    }
}
