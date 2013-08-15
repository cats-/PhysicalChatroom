package cats.pchat.client;

import cats.pchat.core.connection.packet.Opcodes;
import cats.pchat.core.connection.packet.Packet;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Josh
 * 14/08/13
 * 11:32 PM
 */
public class MessageList extends JPanel {

    private final JList<Packet> list;
    private final DefaultListModel<Packet> model;

    public MessageList(){
        super(new BorderLayout());

        model = new DefaultListModel<>();

        list = new JList<>();
        list.setModel(model);
        list.setCellRenderer(
                (list, message, index, selected, focused) -> {
                    final String msg = message.get(String.class, 0);
                    final Color color = message.get(Color.class, 1);
                    final JLabel label = new JLabel(msg);
                    label.setForeground(color);
                    if(focused)
                        label.setBackground(color.brighter());
                    return label;
                }
        );

        add(new JScrollPane(list), BorderLayout.CENTER);
    }

    public void append(final Packet packet){
        assert packet.opcode() == Opcodes.MESSAGE;
        model.addElement(packet);
        list.repaint();
    }
}
