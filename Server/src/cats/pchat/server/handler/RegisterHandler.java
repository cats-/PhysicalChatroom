package cats.pchat.server.handler;

import cats.pchat.core.connection.Connection;
import cats.pchat.core.connection.packet.Packet;
import cats.pchat.server.Server;
import cats.pchat.server.cache.Cache;
import cats.pchat.server.profile.ServerProfile;
import cats.pchat.server.profile.validator.Validator;

/**
 * Josh
 * 14/08/13
 * 4:50 PM
 */
public class RegisterHandler extends AbstractHandler{

    public void handle(final Connection connection, final Packet packet){
        final String name = packet.get(String.class, 0);
        final String pass = packet.get(String.class, 1);
        if(Server.profiles().contains(name)){
            connection.send(new Packet(POPUP_MESSAGE, "That name is already taken"));
            return;
        }
        String msg = Validator.checkName(name);
        if(msg != null){
            connection.send(new Packet(POPUP_MESSAGE, msg));
            return;
        }
        msg = Validator.checkPass(pass);
        if(msg != null){
            connection.send(new Packet(POPUP_MESSAGE, msg));
            return;
        }
        final ServerProfile profile = new ServerProfile(Cache.uid());
        profile.name = name;
        profile.pass = pass;
        Server.profiles().add(profile);
        Cache.writeProfiles();
        connection.send(new Packet(POPUP_MESSAGE, "Name successfully registered"));
    }
}
