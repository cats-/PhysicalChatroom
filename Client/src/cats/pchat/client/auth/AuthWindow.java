package cats.pchat.client.auth;

import cats.pchat.core.Constants;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

/**
 * Josh
 * 14/08/13
 * 9:41 PM
 */
public class AuthWindow extends JFrame implements Constants{

    public static final AuthWindow INSTANCE = new AuthWindow();

    public AuthWindow(){
        super(String.format("%s - Login/Register", TITLE));
        setLayout(new BorderLayout());

        final LoginPanel loginPanel = new LoginPanel();
        final RegisterPanel registerPanel = new RegisterPanel();

        final JPanel viewPanel = new JPanel(new BorderLayout());
        viewPanel.add(loginPanel);

        final JToggleButton loginButton = new JToggleButton("Login", true);
        loginButton.addItemListener(
                e -> {
                    if(!loginButton.isSelected())
                        return;
                    viewPanel.removeAll();
                    viewPanel.add(loginPanel);
                    viewPanel.revalidate();
                    viewPanel.repaint();
                    revalidate();
                    repaint();
                    pack();
                }
        );

        final JToggleButton registerButton = new JToggleButton("Register", false);
        registerButton.addItemListener(
                e -> {
                    if(!registerButton.isSelected())
                        return;
                    viewPanel.removeAll();
                    viewPanel.add(registerPanel);
                    viewPanel.revalidate();
                    viewPanel.repaint();
                    revalidate();
                    repaint();
                    pack();
                }
        );

        final ButtonGroup group = new ButtonGroup();
        group.add(loginButton);
        group.add(registerButton);

        final JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 0));
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        final JPanel container = new JPanel(new BorderLayout());
        container.setBorder(new EmptyBorder(5, 5, 5, 5));
        container.add(buttonPanel, BorderLayout.NORTH);
        container.add(viewPanel, BorderLayout.CENTER);

        add(container, BorderLayout.CENTER);
    }

    public void display(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }
}
