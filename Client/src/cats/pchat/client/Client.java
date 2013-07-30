package cats.pchat.client;

import cats.pchat.client.game.Game;
import cats.pchat.client.handler.AbstractHandler;
import cats.pchat.client.handler.AssignHandler;
import cats.pchat.client.handler.ChangeColorHandler;
import cats.pchat.client.handler.ChangeNameHandler;
import cats.pchat.client.handler.ChangePosHandler;
import cats.pchat.client.handler.JoinHandler;
import cats.pchat.client.handler.LeaveHandler;
import cats.pchat.client.handler.MessageHandler;
import cats.pchat.client.message.MessageArea;
import cats.pchat.client.profile.ProfileList;
import cats.pchat.core.Constants;
import cats.pchat.core.connection.Connection;
import cats.pchat.core.connection.data.Data;
import cats.pchat.core.connection.data.Opcodes;
import cats.pchat.core.connection.event.DataListener;
import cats.pchat.core.profile.Profile;
import cats.pchat.core.profile.Profiles;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 9:20 PM
 */
public class Client extends JFrame implements Constants, Opcodes{

    private static final Profile PROFILE = new Profile();
    private static final Profiles PROFILES = new Profiles();

    private static final Map<Byte, AbstractHandler> HANDLERS = new HashMap<>();

    static{
        HANDLERS.put(CHANGE_COLOR, new ChangeColorHandler());
        HANDLERS.put(CHANGE_NAME, new ChangeNameHandler());
        HANDLERS.put(CHANGE_POS, new ChangePosHandler());
        HANDLERS.put(JOIN, new JoinHandler());
        HANDLERS.put(LEAVE, new LeaveHandler());
        HANDLERS.put(MESSAGE, new MessageHandler());
        HANDLERS.put(ASSIGN, new AssignHandler());
    }

    private static MessageArea messageArea;
    private static ProfileList profileList;
    private static Game game;

    public Client() throws IOException{
        super(TITLE);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        game = new Game();

        messageArea = new MessageArea();
        profileList = new ProfileList();
        profileList.setPreferredSize(new Dimension(150, getPreferredSize().height));

        final JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, messageArea, profileList);

        add(game, BorderLayout.CENTER);
        add(split, BorderLayout.SOUTH);

        pack();
        setVisible(true);

        PROFILE.name().addListener((p, o, n) -> messageArea.name(n));
        PROFILE.color().addListener((p, o, n) -> messageArea.color(n));
        PROFILE.connection = new Connection(new Socket(HOST, PORT));
        PROFILE.connection.addListener(
                (DataListener) e -> {
                    HANDLERS.get(e.data().opcode()).handle(e.data());
                }
        );
    }

    public static ProfileList profileList(){
        return profileList;
    }

    public static MessageArea messageArea(){
        return messageArea;
    }

    public static Game game(){
        return game;
    }

    public static byte uid(){
        return PROFILE.uid().get();
    }

    public static Profile profile(){
        return PROFILE;
    }

    public static Profiles profiles(){
        return PROFILES;
    }

    public static boolean send(final Data data){
        return PROFILE.send(data);
    }

    public static void main(String args[]) throws IOException {
        new Client();
    }
}
