package cats.pchat.client;

import cats.pchat.client.utils.IconUtils;
import cats.pchat.core.connection.packet.Opcodes;
import cats.pchat.core.connection.packet.Packet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Josh
 * 14/08/13
 * 11:55 PM
 */
public class InputArea extends JPanel{

    private final JLabel userLabel;
    private final JTextField inputBox;

    public InputArea(){
        super(new BorderLayout());

        userLabel = new JLabel();
        userLabel.setHorizontalAlignment(JLabel.CENTER);
        userLabel.addMouseListener(
                new MouseAdapter(){
                    public void mousePressed(final MouseEvent e){
                        if(!Client.connected){
                            JOptionPane.showMessageDialog(null, "Not connected");
                            return;
                        }
                        final Color color = JColorChooser.showDialog(null, "Change color", Client.self.color);
                        if(color == null || color.equals(Client.self.color))
                            return;
                        Client.send(new Packet(Opcodes.CHANGE_COLOR, Client.self.uid(), color));
                    }
                }
        );

        inputBox = new JTextField();
        inputBox.addActionListener(
                e -> {
                    if(!Client.connected){
                        JOptionPane.showMessageDialog(null, "You are not connected");
                        return;
                    }
                    final String text = inputBox.getText().trim();
                    if(text.isEmpty())
                        return;
                    Client.send(new Packet(Opcodes.MESSAGE, text, Client.self.color));
                    inputBox.setText("");
                    inputBox.repaint();
                }
        );

        add(userLabel, BorderLayout.WEST);
        add(inputBox, BorderLayout.CENTER);
    }

    public void process(final KeyEvent e){
        final int keycode = e.getKeyCode();
        final char c = e.getKeyChar();
        final String text = inputBox.getText();
        if(c >= 32 && c <= 126){
            inputBox.setText(text + c);
            return;
        }
        if(keycode == KeyEvent.VK_ENTER)
            inputBox.postActionEvent();
        else if(keycode == KeyEvent.VK_BACK_SPACE){
            if(text.isEmpty())
                return;
            inputBox.setText(text.substring(0, text.length()-1));
        }else if(keycode == KeyEvent.VK_A && e.isControlDown())
            inputBox.selectAll();
    }

    public void update(){
        userLabel.setText(Client.self.name);
        userLabel.setForeground(Client.self.color);
        userLabel.setIcon(IconUtils.icon(Client.self.color));
        userLabel.repaint();
        inputBox.setForeground(Client.self.color);
        inputBox.repaint();
    }
}
