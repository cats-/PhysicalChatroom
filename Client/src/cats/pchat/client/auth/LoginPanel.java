package cats.pchat.client.auth;

import cats.pchat.client.Client;
import cats.pchat.client.comp.LabelBox;
import cats.pchat.client.utils.Spacing;
import cats.pchat.core.connection.packet.Opcodes;
import cats.pchat.core.connection.packet.Packet;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Josh
 * 14/08/13
 * 10:13 PM
 */
public class LoginPanel extends JPanel implements Opcodes{

    public LoginPanel(){
        super(new BorderLayout());

        final LabelBox nameBox = new LabelBox("Name");

        final LabelBox passBox = new LabelBox("Pass", true);

        final JPanel boxContainer = new JPanel();
        boxContainer.setLayout(new BoxLayout(boxContainer, BoxLayout.Y_AXIS));
        boxContainer.add(Spacing.v());
        Spacing.add(boxContainer, nameBox, true);
        Spacing.add(boxContainer, passBox, true);

        final JButton loginButton = new JButton("Login");
        loginButton.addActionListener(
                e -> {
                    if(!Client.connected){
                        JOptionPane.showMessageDialog(null, "Not connected");
                        return;
                    }
                    final String name = nameBox.value();
                    final String pass = passBox.value();
                    if(name.isEmpty() || pass.isEmpty()){
                        JOptionPane.showMessageDialog(null, "Fill in all fields");
                        return;
                    }
                    Client.send(new Packet(LOGIN, name, pass));
                    nameBox.clear();
                    passBox.clear();
                }
        );

        add(boxContainer, BorderLayout.CENTER);
        add(loginButton, BorderLayout.SOUTH);
    }
}
