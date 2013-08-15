package cats.pchat.client;

import cats.pchat.client.auth.AuthWindow;
import cats.pchat.client.game.Game;
import cats.pchat.client.handler.AbstractHandler;
import cats.pchat.client.handler.AssignHandler;
import cats.pchat.client.handler.ChangeColorHandler;
import cats.pchat.client.handler.ChangeLocationHandler;
import cats.pchat.client.handler.JoinHandler;
import cats.pchat.client.handler.LeaveHandler;
import cats.pchat.client.handler.MessageHandler;
import cats.pchat.client.handler.PopupMessageHandler;
import cats.pchat.client.profile.ClientProfile;
import cats.pchat.core.Constants;
import cats.pchat.core.connection.Connection;
import cats.pchat.core.connection.packet.Opcodes;
import cats.pchat.core.connection.packet.Packet;
import cats.pchat.core.profile.Profiles;
import java.awt.BorderLayout;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;

/**
 * Josh
 * 14/08/13
 * 9:41 PM
 */
public class Client extends JFrame implements Constants, Opcodes{

    public static Connection connection;
    public static ClientProfile self;

    private static final Profiles<ClientProfile> PROFILES = new Profiles<>();

    public static boolean connected = false;

    public static Game game;
    public static ProfileList profileList;
    public static MessageList messageList;
    public static InputArea inputArea;

    private static final Map<Byte, AbstractHandler> HANDLERS = new HashMap<>();

    static{
        HANDLERS.put(ASSIGN, new AssignHandler());
        HANDLERS.put(CHANGE_COLOR, new ChangeColorHandler());
        HANDLERS.put(CHANGE_LOCATION, new ChangeLocationHandler());
        HANDLERS.put(JOIN, new JoinHandler());
        HANDLERS.put(LEAVE, new LeaveHandler());
        HANDLERS.put(MESSAGE, new MessageHandler());
        HANDLERS.put(POPUP_MESSAGE, new PopupMessageHandler());
    }

    public Client(){
        super(TITLE);
        setLayout(new BorderLayout());

        game = new Game();

        profileList = new ProfileList();
        messageList = new MessageList();

        inputArea = new InputArea();

        final JPanel chatPanel = new JPanel(new BorderLayout());
        chatPanel.add(messageList, BorderLayout.CENTER);
        chatPanel.add(inputArea, BorderLayout.SOUTH);

        final JSplitPane topSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, chatPanel, profileList);

        add(game, BorderLayout.CENTER);
        add(topSplit, BorderLayout.SOUTH);
    }

    public static Profiles<ClientProfile> profiles(){
        return PROFILES;
    }

    public static boolean send(final Packet packet){
        return connected && connection.send(packet);
    }

    public void display(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 700);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
        game.start();
    }

    public void start(){
        try{
            connection = new Connection(new Socket(HOST, PORT));
            connection.addConnectionListener(
                    c -> {
                        connected = false;
                        connection = null;
                        JOptionPane.showMessageDialog(null, "You have been disconnected");
                    }
            );
            connection.addPacketListener((c, p) -> HANDLERS.get(p.opcode()).handle(p));
            connected = true;
            AuthWindow.INSTANCE.display();
            while(AuthWindow.INSTANCE.isVisible());
            display();
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null, "Error connecting: " + ex.getMessage());
        }
    }

    public static void main(String args[]){
        new Client().start();
    }
}
