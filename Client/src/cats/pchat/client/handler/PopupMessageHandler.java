package cats.pchat.client.handler;

import cats.pchat.core.connection.packet.Packet;
import javax.swing.JOptionPane;

/**
 * Josh
 * 14/08/13
 * 11:30 PM
 */
public class PopupMessageHandler extends AbstractHandler{

    public void handle(final Packet packet){
        final String msg = packet.get(String.class, 0);
        JOptionPane.showMessageDialog(null, msg);
    }
}
