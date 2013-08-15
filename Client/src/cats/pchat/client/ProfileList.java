package cats.pchat.client;

import cats.pchat.client.profile.ClientProfile;
import cats.pchat.client.utils.IconUtils;
import java.awt.BorderLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Josh
 * 14/08/13
 * 11:36 PM
 */
public class ProfileList extends JPanel{

    private final JList<ClientProfile> list;
    private final DefaultListModel<ClientProfile> model;

    public ProfileList(){
        super(new BorderLayout());

        model = new DefaultListModel<>();

        list = new JList<>();
        list.setModel(model);
        list.setCellRenderer(
                (list, profile, index, selected, focused) -> {
                    final JLabel label = new JLabel(profile.name);
                    label.setForeground(profile.color);
                    if(focused)
                        label.setBackground(profile.color.brighter());
                    label.setIcon(IconUtils.icon(profile.color));
                    return label;
                }
        );

        add(new JScrollPane(list), BorderLayout.CENTER);
    }

    public void add(final ClientProfile profile){
        model.addElement(profile);
        list.repaint();
    }

    public void remove(final Object o){
        model.removeElement(o);
        list.repaint();
    }

    public void update(final Object o){
        final int index = model.indexOf(o);
        model.removeElement(o);
        final ClientProfile profile = o instanceof ClientProfile ? (ClientProfile)o : Client.profiles().get(o);
        model.add(index, profile);
        list.repaint();
    }
}
