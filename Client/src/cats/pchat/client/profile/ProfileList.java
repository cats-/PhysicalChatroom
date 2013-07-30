package cats.pchat.client.profile;

import cats.pchat.client.Client;
import cats.pchat.client.game.GameUtils;
import cats.pchat.core.profile.Profile;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Physical Chatroom
 * Josh
 * 29/07/13
 * 9:26 AM
 */
public class ProfileList extends JPanel{

    private final JList<Profile> list;
    private final JScrollPane scroll;

    public ProfileList(){
        super(new BorderLayout());

        list = new JList<>();
        list.setCellRenderer(
                new DefaultListCellRenderer(){
                    public Component getListCellRendererComponent(JList l, Object v, int i, boolean s, boolean f){
                        final Component c = super.getListCellRendererComponent(l, v, i, false, false);
                        if(v == null)
                            return c;
                        final Profile profile = (Profile)v;
                        final JLabel label = (JLabel)c;
                        label.setHorizontalAlignment(JLabel.CENTER);
                        label.setText(String.format("%s (%d,%d)", profile.name().get(), profile.pos().get().x, profile.pos().get().y));
                        label.setIcon(GameUtils.icon(profile.color().get()));
                        return label;
                    }
                }
        );

        scroll = new JScrollPane(list);

        add(scroll, BorderLayout.CENTER);
    }

    public void update(){
        list.setListData(Client.profiles().array());
        list.repaint();
    }
}
