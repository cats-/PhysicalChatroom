package cats.pchat.server.handler;

import cats.pchat.core.Constants;
import cats.pchat.core.connection.Connection;
import cats.pchat.core.connection.packet.Packet;
import cats.pchat.server.Server;
import cats.pchat.server.profile.ServerProfile;

/**
 * Josh
 * 14/08/13
 * 5:39 PM
 */
public class LoginHandler extends AbstractHandler{

    public void handle(final Connection connection, final Packet packet){
        final String name = packet.get(String.class, 0);
        final String pass = packet.get(String.class, 1);
        final ServerProfile profile = Server.profiles().get(name);
        if(profile == null){
            connection.send(new Packet(POPUP_MESSAGE, "That name does not exist"));
            return;
        }
        if(!profile.pass.equals(pass)){
            connection.send(new Packet(POPUP_MESSAGE, "Passwords do not match"));
            return;
        }
        if(profile.connection != null){
            connection.send(new Packet(POPUP_MESSAGE, "You are already logged in"));
            return;
        }
        profile.connection = connection;
        profile.send(new Packet(ASSIGN, profile.array()));
        profile.send(new Packet(MESSAGE, String.format("Welcome to %s", Constants.TITLE), profile.color));
        Server.profiles().stream().filter(p -> !p.equals(profile) && p.connection != null).forEach(
                p -> {
                    p.send(new Packet(JOIN, profile.array()));
                    p.send(new Packet(MESSAGE, String.format("%s has just joined", profile.name), profile.color));
                    profile.send(new Packet(JOIN, p.array()));
                }
        );
    }
}
