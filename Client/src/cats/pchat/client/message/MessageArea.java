package cats.pchat.client.message;

import cats.pchat.client.Client;
import cats.pchat.client.game.GameUtils;
import cats.pchat.core.connection.data.type.impl.ChangeColorData;
import cats.pchat.core.connection.data.type.impl.ChangeNameData;
import cats.pchat.core.connection.data.type.impl.Message;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 9:29 PM
 */
public class MessageArea extends JPanel{

    private static final String ACCEPTED = "!@#$%^&*()-=_+[]{};':\",.<>/? ";

    private final DefaultListModel<Message> model;
    private final JList<Message> list;
    private final JScrollPane scroll;

    private final JLabel nameLabel;
    private final JLabel colorLabel;
    private final JPanel namePanel;
    private final JTextField inputBox;
    private final JPanel southPanel;

    public MessageArea(){
        super(new BorderLayout());

        model = new DefaultListModel<>();

        list = new JList<>();
        list.setModel(model);
        list.setCellRenderer(
                new DefaultListCellRenderer(){
                    public Component getListCellRendererComponent(JList l, Object v, int i, boolean s, boolean f){
                        final Component c = super.getListCellRendererComponent(l, v, i, false, false);
                        if(v == null)
                            return c;
                        final Message m = (Message)v;
                        final JLabel label = (JLabel)c;
                        label.setText(m.formatted());
                        label.setForeground(m.color());
                        label.setIcon(GameUtils.icon(m.color()));
                        return label;
                    }
                }
        );

        scroll = new JScrollPane(list);

        nameLabel = new JLabel(Client.profile().name().get());
        nameLabel.setBorder(new EmptyBorder(0, 5, 0, 5));
        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        nameLabel.addMouseListener(
                new MouseAdapter(){
                    public void mousePressed(final MouseEvent e){
                        final String newName = JOptionPane.showInputDialog(null, "Enter new name");
                        if(newName == null || newName.isEmpty())
                            return;
                        Client.send(new ChangeNameData(Client.uid(), newName));
                    }
                }
        );

        colorLabel = new JLabel(GameUtils.icon(Color.BLACK));
        colorLabel.addMouseListener(
                new MouseAdapter(){
                    public void mousePressed(final MouseEvent e){
                        final Color newColor = JColorChooser.showDialog(null, "Select new color", Client.profile().color().get());
                        if(newColor != null && !newColor.equals(Client.profile().color().get()))
                            Client.send(new ChangeColorData(Client.uid(), newColor));
                    }
                }
        );

        namePanel = new JPanel(new BorderLayout());
        namePanel.add(colorLabel, BorderLayout.WEST);
        namePanel.add(nameLabel, BorderLayout.CENTER);

        inputBox = new JTextField(){
            public void processKeyEvent(final KeyEvent e){
                super.processKeyEvent(e);
                if(e.getKeyCode() != KeyEvent.VK_ENTER)
                    return;
                final String text = inputBox.getText().trim();
                if(text.isEmpty())
                    return;
                Client.send(new Message(Client.uid(), text));
                inputBox.setText("");
                inputBox.repaint();
            }
        };
        inputBox.setHorizontalAlignment(JLabel.CENTER);

        southPanel = new JPanel(new BorderLayout());
        southPanel.add(namePanel, BorderLayout.WEST);
        southPanel.add(inputBox, BorderLayout.CENTER);

        add(scroll, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }

    public void processKey(final KeyEvent e){
        final int keycode = e.getKeyCode();
        final String text = inputBox.getText();
        if(keycode == KeyEvent.VK_ENTER){
            if(text.isEmpty())
                return;
            Client.send(new Message(Client.uid(), text));
            inputBox.setText("");
            inputBox.repaint();
            return;
        }
        if(keycode == KeyEvent.VK_BACK_SPACE){
            if(text.isEmpty())
                return;
            inputBox.setText(text.substring(0, text.length() - 1));
            inputBox.repaint();
            return;
        }
        final char keychar = e.getKeyChar();
        if(Character.isLetterOrDigit(keychar) || ACCEPTED.indexOf(keychar) > -1){
            inputBox.setText(inputBox.getText() + keychar);
            inputBox.repaint();
            return;
        }
    }

    public void push(final Message message){
        model.addElement(message);
        list.repaint();
    }

    public void name(final String name){
        nameLabel.setText(name);
        nameLabel.repaint();
        nameLabel.revalidate();
        namePanel.revalidate();
        namePanel.repaint();
    }

    public void color(final Color c){
        colorLabel.setIcon(GameUtils.icon(c));
        colorLabel.repaint();
    }
}
