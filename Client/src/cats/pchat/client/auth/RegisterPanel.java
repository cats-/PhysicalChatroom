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
 * 9:41 PM
 */
public class RegisterPanel extends JPanel implements Opcodes{

    public RegisterPanel(){
        super(new BorderLayout());

        final LabelBox nameBox = new LabelBox("Username");

        final LabelBox pass1Box = new LabelBox("Password", true);

        final LabelBox pass2Box = new LabelBox("Re-enter Password", true);

        final JPanel boxContainer = new JPanel();
        boxContainer.setLayout(new BoxLayout(boxContainer, BoxLayout.Y_AXIS));
        boxContainer.add(Spacing.v());
        Spacing.add(boxContainer, nameBox, true);
        Spacing.add(boxContainer, pass1Box, true);
        Spacing.add(boxContainer, pass2Box, true);

        final JButton registerButton = new JButton("Register");
        registerButton.addActionListener(
                e -> {
                    if(!Client.connected){
                        JOptionPane.showMessageDialog(null, "Not connected");
                        return;
                    }
                    final String name = nameBox.value();
                    final String pass1 = pass1Box.value();
                    final String pass2 = pass2Box.value();
                    if(name.isEmpty() || pass1.isEmpty() || pass2.isEmpty()){
                        JOptionPane.showMessageDialog(null, "Fill in all fields");
                        return;
                    }
                    if(!pass1.equals(pass2)){
                        JOptionPane.showMessageDialog(null, "Passwords don't match");
                        return;
                    }
                    Client.send(new Packet(REGISTER, name, pass1));
                    nameBox.clear();
                    pass1Box.clear();
                    pass2Box.clear();
                }
        );

        add(boxContainer, BorderLayout.CENTER);
        add(registerButton, BorderLayout.SOUTH);
    }
}
