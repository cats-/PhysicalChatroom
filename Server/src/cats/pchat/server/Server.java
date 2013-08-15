package cats.pchat.server;

import cats.pchat.core.Constants;
import cats.pchat.core.connection.Connection;
import cats.pchat.core.connection.packet.Opcodes;
import cats.pchat.core.connection.packet.Packet;
import cats.pchat.core.profile.Profiles;
import cats.pchat.server.cache.Cache;
import cats.pchat.server.handler.AbstractHandler;
import cats.pchat.server.handler.ChangeColorHandler;
import cats.pchat.server.handler.ChangeLocationHandler;
import cats.pchat.server.handler.LoginHandler;
import cats.pchat.server.handler.MessageHandler;
import cats.pchat.server.handler.RegisterHandler;
import cats.pchat.server.profile.ServerProfile;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Josh
 * 14/08/13
 * 11:48 AM
 */
public class Server extends Thread implements Runnable, Constants, Opcodes{

    private static final Profiles<ServerProfile> PROFILES = new Profiles<>();

    private static final Map<Byte, AbstractHandler> HANDLERS = new HashMap<>();

    static{
        HANDLERS.put(CHANGE_COLOR, new ChangeColorHandler());
        HANDLERS.put(CHANGE_LOCATION, new ChangeLocationHandler());
        HANDLERS.put(LOGIN, new LoginHandler());
        HANDLERS.put(MESSAGE, new MessageHandler());
        HANDLERS.put(REGISTER, new RegisterHandler());
    }

    static{
        Cache.loadProfiles();
    }

    public Server(){
        setPriority(MAX_PRIORITY);
    }

    public void run(){
        try{
            final ServerSocket server = new ServerSocket(PORT);
            while(true){
                try{
                    final Socket socket = server.accept();
                    final Connection connection = new Connection(socket);
                    connection.addConnectionListener(
                            c -> {
                                final ServerProfile profile = PROFILES.get(c);
                                if (profile == null)
                                    return;
                                profile.connection = null;
                                PROFILES.forEach(p -> p.send(new Packet(LEAVE, profile.uid())));
                                PROFILES.forEach(p -> p.send(new Packet(MESSAGE, String.format("%s has just left", profile.name), profile.color)));
                            }
                    );
                    connection.addPacketListener((c, p) -> HANDLERS.get(p.opcode()).handle(c, p));
                }catch(IOException ex){
                    ex.printStackTrace();
                }
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public static Profiles<ServerProfile> profiles(){
        return PROFILES;
    }

    public static void main(String args[]){
        new Server().start();
    }
}
