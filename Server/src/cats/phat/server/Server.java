package cats.phat.server;

import cats.pchat.core.Constants;
import cats.pchat.core.connection.Connection;
import cats.pchat.core.connection.data.Data;
import cats.pchat.core.connection.data.Opcodes;
import cats.pchat.core.connection.data.type.impl.AssignData;
import cats.pchat.core.connection.data.type.impl.ChangeColorData;
import cats.pchat.core.connection.data.type.impl.ChangeNameData;
import cats.pchat.core.connection.data.type.impl.ChangePosData;
import cats.pchat.core.connection.data.type.impl.JoinData;
import cats.pchat.core.connection.data.type.impl.LeaveData;
import cats.pchat.core.connection.data.type.impl.Message;
import cats.pchat.core.connection.event.ConnectionListener;
import cats.pchat.core.connection.event.DataListener;
import cats.pchat.core.profile.Profile;
import cats.pchat.core.profile.Profiles;
import cats.phat.server.handler.AbstractHandler;
import cats.phat.server.handler.ChangeColorHandler;
import cats.phat.server.handler.ChangeNameHandler;
import cats.phat.server.handler.ChangePosHandler;
import cats.phat.server.handler.MessageHandler;
import java.awt.Color;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 7:10 PM
 */
public class Server extends Thread implements Runnable, Constants, Opcodes{
    
    private static final Map<Byte, AbstractHandler> HANDLERS = new HashMap<>();
    private static final Profiles PROFILES = new Profiles();

    private static byte uid = 0;
    
    static{
        HANDLERS.put(CHANGE_NAME, new ChangeNameHandler());
        HANDLERS.put(CHANGE_COLOR, new ChangeColorHandler());
        HANDLERS.put(MESSAGE, new MessageHandler());
        HANDLERS.put(CHANGE_POS, new ChangePosHandler());
    }

    public Server(){
        setPriority(MAX_PRIORITY);
    }
    
    public static Profiles profiles(){
        return PROFILES;
    }
   
    public static void send(final Data data){
        PROFILES.stream().forEach(p -> p.send(data));
    }
    
    public static void sendExcept(final Profile profile, final Data data){
        PROFILES.stream().filter(p -> !p.equals(profile)).forEach(p -> p.send(data));
    }

    public void run(){
        try{
            final ServerSocket server = new ServerSocket(PORT);
            while(true){
                final Socket socket = server.accept();
                final Connection connection = new Connection(socket);
                connection.addListener(
                        (ConnectionListener) e -> {
                            final Profile temp = PROFILES.profile(e.connection());
                            PROFILES.remove(temp);
                            send(new LeaveData(temp.uid().get()));
                            send(new Message(String.format("%s has just left %s", temp.name().get(), TITLE), temp.color().get()));
                        }
                );
                connection.addListener(
                        (DataListener) e -> {
                            if(HANDLERS.containsKey(e.data().opcode()))
                                HANDLERS.get(e.data().opcode()).handle(e.data());
                        }
                );
                final Profile profile = new Profile();
                profile.uid().addListener(
                        (prop, o, n) -> {
                                profile.send(new AssignData(n));
                        }
                );
                profile.name().addListener(
                        (prop, o, n) -> {
                            profile.send(new ChangeNameData(profile.uid().get(), n));
                            profile.send(new Message(String.format("You are now known as %s", n), profile.color().get()));
                            if(o == null){
                                PROFILES.stream().filter(p -> !p.equals(profile)).forEach(p -> profile.send(new JoinData(p.clone())));
                                sendExcept(profile, new JoinData(profile.clone()));
                                sendExcept(profile, new Message(String.format("%s has just joined %s", n, TITLE), Color.BLACK));
                            }else{
                                sendExcept(profile, new ChangeNameData(profile.uid().get(), n));
                                sendExcept(profile, new Message(String.format("%s is now known as %s", o, n), profile.color().get()));
                            }
                        }
                );
                profile.color().addListener(
                        (prop, o, n) -> {
                            send(new ChangeColorData(profile.uid().get(), n));
                            send(new Message(String.format("%s has changed their color to %s", profile.name().get(), n.getRGB()), n));
                        }
                );
                profile.pos().addListener(
                        (prop, o, n) -> {
                            send(new ChangePosData(profile.uid().get(), n));
                        }
                );
                profile.connection = connection;
                PROFILES.add(profile);
                profile.uid().set(uid++);
                profile.name().set("Guest" + System.currentTimeMillis());
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public static void main(String args[]){
        new Server().start();
    }
}
